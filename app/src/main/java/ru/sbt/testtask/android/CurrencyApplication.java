package ru.sbt.testtask.android;


import android.app.Application;

import ru.sbt.testtask.android.repository.RepositoryProvider;

public class CurrencyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RepositoryProvider.init(this);
    }
}
