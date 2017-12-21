package ru.sbt.testtask.android.models;


import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="ValCurs")
public class ValCurs {

    @ElementList(inline=true, name="Valute")
    public List<Valute> valuteList;

    public List<Valute> getValuteList() {
        return valuteList;
    }
}
