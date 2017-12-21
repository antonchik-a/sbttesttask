package ru.sbt.testtask.android.repository;


import ru.sbt.testtask.android.models.ValCurs;

public interface ValuteRepository {

    ValCurs getValuteCurs();

    void cacheValuteCurs(String valCurs);
}
