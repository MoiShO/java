package com.company;

import java.util.*;

public class PrintCurrencyAmount extends TimerTask {
    static ArrayList<CurrencyAmount> arrayCurrencyAmount;

    PrintCurrencyAmount(ArrayList<CurrencyAmount> printedArg) {
        arrayCurrencyAmount = printedArg;
    }

    @Override
    public void run() {
        completeTask();
    }

    private void completeTask() {
        for (CurrencyAmount item : arrayCurrencyAmount) {
            System.out.println(item.toString());
        }
        System.out.println("_\n");
    }
}
