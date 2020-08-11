package com.company;

import java.util.*;

public class PrintCurrencyAmount extends Thread {
    final ArrayList<CurrencyAmount> printInTerminal;

    PrintCurrencyAmount (ArrayList<CurrencyAmount> dataToPrint) {
        printInTerminal = dataToPrint;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            synchronized (printInTerminal) {
                task();
            }
        }
    }

    private void task(){
        for (CurrencyAmount item : printInTerminal) {
            System.out.println(item.toString());
        }
        System.out.println("_\n");

        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
