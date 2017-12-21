package ru.sbt.testtask.android.utils;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;

import ru.sbt.testtask.android.models.ValCurs;
import ru.sbt.testtask.android.models.Valute;

public class ValuteUtils {

    @Nullable
    public static ValCurs valCursFromString(@NonNull String cursString){

        Reader reader = new StringReader(cursString);
        Persister serializer = new Persister();
        try {
            ValCurs valCurs = serializer.read(ValCurs.class, reader, false);
            return valCurs;
        } catch (Exception e) {
            return null;
        }
    }

    @NonNull
    public static Float convert(@NonNull Valute from,@NonNull Valute to,@NonNull Float sum){
        try {
            Float fromValue = from.getValue()/from.getNominal();
            Float toValue = to.getValue() / to.getNominal();

            Float result = fromValue/toValue * sum;
            return result;

        }catch (NumberFormatException ex){

        }

        return 0.0f;
    }
}
