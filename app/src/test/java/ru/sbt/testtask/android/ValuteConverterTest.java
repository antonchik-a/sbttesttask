package ru.sbt.testtask.android;


import org.junit.Test;

import ru.sbt.testtask.android.models.Valute;
import ru.sbt.testtask.android.utils.ValuteUtils;

import static org.junit.Assert.assertEquals;

public class ValuteConverterTest {

    @Test
    public void testConvertRubleToAny() throws Exception {
        Valute toValute = new Valute();
        toValute.setNominal(100);
        toValute.setValue("1000.0");

        Float result = ValuteUtils.convert(Valute.createRubleValute(), toValute, 100.0f);
        assertEquals(10.0f, result.doubleValue());
    }
}
