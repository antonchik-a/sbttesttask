package ru.sbt.testtask.android.repository;


import android.content.Context;
import android.content.SharedPreferences;

import ru.sbt.testtask.android.models.ValCurs;
import ru.sbt.testtask.android.utils.ValuteUtils;

public class DefaultValuteRepository implements ValuteRepository {

    private static final String APP_PREFERENCES = "valute_preferences";
    private static final String CACHE_KEY  = "VALUTECURS";

    private SharedPreferences mPreferences;

    @Override
    public ValCurs getValuteCurs() {
        if(mPreferences == null){
            return null;
        }

        if(mPreferences.contains(CACHE_KEY)) {
            return ValuteUtils.valCursFromString(mPreferences.getString(CACHE_KEY, ""));
        }

        return null;
    }

    @Override
    public void cacheValuteCurs(String valCurs) {
        if(mPreferences == null){
            return;
        }

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(CACHE_KEY, valCurs);
        editor.apply();
    }

    public void init(Context context) {
        mPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }
}
