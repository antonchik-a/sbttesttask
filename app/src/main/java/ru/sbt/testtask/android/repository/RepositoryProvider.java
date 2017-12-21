package ru.sbt.testtask.android.repository;


import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

public class RepositoryProvider {

    private static ValuteRepository sValuteRepository;


    @NonNull
    public static ValuteRepository provideValuteRepository(){
        if(sValuteRepository == null){
            sValuteRepository = new DefaultValuteRepository();
        }
        return sValuteRepository;
    }

    @MainThread
    public static void init(Context context){
        DefaultValuteRepository defaultValuteRepository = new DefaultValuteRepository();
        defaultValuteRepository.init(context);
        sValuteRepository = defaultValuteRepository;
    }
}
