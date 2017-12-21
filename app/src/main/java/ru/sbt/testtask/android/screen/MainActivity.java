package ru.sbt.testtask.android.screen;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ru.sbt.testtask.android.R;
import ru.sbt.testtask.android.repository.RepositoryProvider;
import ru.sbt.testtask.android.screen.MainPresenter;
import ru.sbt.testtask.android.screen.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter mMainPresenter;
    private Spinner mFromSpinner;
    private Spinner mToSpinner;
    private TextView mResultTextView;
    private FloatingActionButton mFloatingActionButton;
    private ArrayAdapter<String> mFromAdapter;
    private ArrayAdapter<String> mToAdapter;
    private EditText mSumEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFromSpinner = (Spinner) findViewById(R.id.fromSpinner);
        mToSpinner = (Spinner) findViewById(R.id.toSpinner);
        mResultTextView = (TextView) findViewById(R.id.convertResult);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mSumEditText = (EditText) findViewById(R.id.sumEdit);

        setupSpinners();

        mFloatingActionButton.setOnClickListener(v -> {
            mMainPresenter.onConvertClick(mSumEditText.getText());
        });

        mMainPresenter = new MainPresenter(this, RepositoryProvider.provideValuteRepository());
        mMainPresenter.init();
    }

    private void setupSpinners() {
        mFromAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner, new ArrayList<>());
        mFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFromSpinner.setAdapter(mFromAdapter);
        mFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mMainPresenter.onFromValuteSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mToAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner, new ArrayList<>());
        mToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mToSpinner.setAdapter(mToAdapter);
        mToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mMainPresenter.onToValuteSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void showError(int error_network) {
        Snackbar.make(findViewById(R.id.bottomPanel), error_network, Snackbar.LENGTH_LONG)
                .show(); // Don’t forget to show!
    }

    @Override
    public void hideLoadingProgress() {

    }

    @Override
    public void setSpinnersData(ArrayList<String> strings) {
        mFromAdapter.clear();
        mToAdapter.clear();
        mFromAdapter.addAll(strings);
        mToAdapter.addAll(strings);
    }

    @Override
    public void showConvertResult(String convertResult) {
        mResultTextView.setText(convertResult);
    }

    @Override
    public void setInitialSelections(int i, int i1) {
        mFromSpinner.setSelection(i);
        mToSpinner.setSelection(i1);
    }

    @Override
    public void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
