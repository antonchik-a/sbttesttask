package ru.sbt.testtask.android.screen;


import java.util.ArrayList;

public interface MainView {
    void showLoadingProgress();

    void showError(int error_network);

    void hideLoadingProgress();

    void setSpinnersData(ArrayList<String> strings);

    void showConvertResult(String convertResult);

    void setInitialSelections(int i, int i1);

    void closeKeyboard();
}
