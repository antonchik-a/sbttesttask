package ru.sbt.testtask.android.screen;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import ru.sbt.testtask.android.R;
import ru.sbt.testtask.android.models.ValCurs;
import ru.sbt.testtask.android.models.Valute;
import ru.sbt.testtask.android.repository.ValuteRepository;
import ru.sbt.testtask.android.utils.ValuteUtils;

import static ru.sbt.testtask.android.Config.CURS_URL;

public class MainPresenter {

    private MainView mView;
    private List<Valute> mValutes;
    private ValuteRepository mValuteRepository;
    private Valute mFromValute;
    private Valute mToValute;

    public MainPresenter(MainView view, ValuteRepository valuteRepository) {
        mView = view;
        mValutes = new ArrayList<>();
        mValuteRepository = valuteRepository;
    }

    public void init() {
        ValCurs cachedValCurs = mValuteRepository.getValuteCurs();
        if (cachedValCurs != null) {
            mValutes = cachedValCurs.getValuteList();
            mValutes.add(0, Valute.createRubleValute());
            setValutesData();
            mView.setInitialSelections(0, 11);
        }
        new LoadCursTask(cachedValCurs == null).execute();
    }

    public void onConvertClick(@NonNull  CharSequence text) {
        mView.closeKeyboard();

        if(text.toString().length() == 0){
            mView.showError(R.string.input_sum);
            return;
        }

        if(mFromValute == null){
            mView.showError(R.string.choose_from);
            return;
        }

        if(mToValute == null){
            mView.showError(R.string.chose_to);
            return;
        }

        try {
            Float sum = new Float(text.toString());
            Float result = ValuteUtils.convert(mFromValute, mToValute, sum);
            NumberFormat nf = NumberFormat.getInstance(); // get instance
            nf.setMaximumFractionDigits(2); // set decimal places
            String covertResult = nf.format(result) + " " + mToValute.getCharCode();
            mView.showConvertResult(covertResult);

        }catch (NumberFormatException ex){

        }
    }

    public void onFromValuteSelected(int i) {
        if(i < 0 || i > mValutes.size()) return;

        mFromValute = mValutes.get(i);
        mView.showConvertResult("");
    }

    public void onToValuteSelected(int i) {
        if(i < 0 || i > mValutes.size()) return;

        mToValute = mValutes.get(i);
        mView.showConvertResult("");
    }

    private void setValutesData() {
        ArrayList<String> strings = new ArrayList<>();

        for (Valute valute : mValutes) {
            strings.add(valute.getName() + " (" + valute.getCharCode() + ")");
        }

        mView.setSpinnersData(strings);
    }


    String getXMLString() throws MalformedURLException, IOException {
        URL xml = new URL(CURS_URL);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        xml.openStream(),"windows-1251"));

        StringBuffer buffer = new StringBuffer();
        String readedLine;

        while ((readedLine = in.readLine()) != null)
            buffer.append(readedLine);

        in.close();
        in = null;
        return buffer.toString();
    }

    class LoadCursTask extends AsyncTask<Void, Void, String> {

        private boolean firstLoad = false;

        public LoadCursTask(boolean firstLoad) {
            this.firstLoad = firstLoad;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (firstLoad) {
                mView.showLoadingProgress();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
               return getXMLString();
            } catch (IOException e) {
                mView.showError(R.string.error_network);
            }
            return null;
        }

        @Override
        protected void onPostExecute(@Nullable String result) {
            super.onPostExecute(result);
            mView.hideLoadingProgress();
            if (result != null) {
                onLoadXMLString(result);
                if(!firstLoad) {
                    mView.showError(R.string.success_update_valute_curs);
                }
            }
        }
    }

    private void onLoadXMLString(@NonNull String result) {
        ValCurs valCurs = ValuteUtils.valCursFromString(result);

        if (valCurs != null) {
            mValutes = valCurs.getValuteList();
            mValutes.add(0, Valute.createRubleValute());
            mValuteRepository.cacheValuteCurs(result);
            setValutesData();
        } else {
            mView.showError(R.string.error_curs_save);
        }
    }


}
