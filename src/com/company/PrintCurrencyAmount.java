package com.company;

import java.util.*;

public class PrintCurrencyAmount extends Thread{
    final Map<String, CurrencyAmount> printInTerminal;

    PrintCurrencyAmount (Map<String, CurrencyAmount> dataToPrint) {
        printInTerminal = dataToPrint;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(1000 * 5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            synchronized (printInTerminal) {
                task();
            }
        }
    }

    private void task(){
        printInTerminal.forEach((key, value) -> System.out.println(value.toString()));
        System.out.println("_\n");
    }
}
