package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private static final Map<String, CurrencyAmount> mapCurrencyAmount = new TreeMap();

    public static void main(String[] args) throws IOException {
        System.out.println("Write a currency and amount separated whitespace" +
                "example input USD 100, or HKD 100 0.25 ");
        /*
        Запускаем таймер печати в Terminal
         */
        startTerminalPrinter();

        /*
        Если есть аргумент при запуске программы
         */
        if(args.length > 0) {
            readFromFile(args[0]);
        }

        /*
        Слушаем Terminal
         */
        readFromCMD();
    }

    /**
     *
     * @param fileName имя файла с расширением
     * @throws IOException
     */

    private static void readFromFile(String fileName) throws IOException {
            /*
            Читаем данные из файла
             */
            String basePath = System.getProperty("user.dir");
            try (BufferedReader objReader = new BufferedReader(
                    new FileReader(basePath + "\\" + fileName)
            )) {

            String strCurrentLine;
            while ((strCurrentLine = objReader.readLine()) != null) {
                addCurrencyAmount(strCurrentLine);
            }
        }
    }

    /**
     * Читает input в Terminal
     * @throws IOException
     */
    private static void readFromCMD() throws IOException{

        Thread inputThread = new Thread() {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()) {
                        String inputVal = reader.readLine();

                        Thread.yield(); //Передать управление другим потокам

                        /*
                        Выход из программы
                         */
                        if (inputVal.equals("quit")) {
                            reader.close();
                            Thread.currentThread().interrupt();

                        } else {
                            synchronized (mapCurrencyAmount) {
                                addCurrencyAmount(inputVal);
                            }
                        }
                    }
                } catch (IOException ex) {
                    System.err.println(ex.toString());
                }
            }
        };

        inputThread.start();
    }

    /**
     * Запуск таймера печати в Terminal
     */
    private static void startTerminalPrinter() {
        Thread printInTerminal = new PrintCurrencyAmount(mapCurrencyAmount);
        /*
        ничего лучше что бы поток откинулся я не придумал :( (не нашел)
         */
        printInTerminal.setDaemon(true);
        printInTerminal.start();
    }

    /**
     *
     * @param inputVal - входная строка формата "HKD 100 0.25" или "HKD 100"
     */
    private static void addCurrencyAmount(String inputVal) {
        String[] currencyAmount = inputVal.trim().split(" ", 3);

        if (currencyAmount.length < 2 || currencyAmount.length > 3) {
            System.out.println("Oops try again,\n" +
                    "input format 'HKD 100 0.25',\n" +
                    "HKD - currency\n" +
                    "100 - amount\n" +
                    "0.25 - optional rated to USD\n" +
                    "quit - program exit");
        } else {
            try {
                String currency = currencyAmount[0].toUpperCase();
                int amount = Integer.parseInt(currencyAmount[1]);
                double rateToUSD = currencyAmount.length >= 3 ? Double.parseDouble(currencyAmount[2]) : 0;

                /*
                Находим текущую Валюту что бы преобразовать
                 */
                CurrencyAmount result = mapCurrencyAmount.get(currency);

               /*
               Если в mapCurrencyAmount уже имеется такая валюта
                */
                if (result != null) {
                    int currentAmount = result.getAmount();
                    int sumAmount = amount + currentAmount;

                    if (sumAmount == 0) {
                        mapCurrencyAmount.remove(currency);

                    } else {
                        result.setAmount(amount + currentAmount);
                        if (rateToUSD != 0.0) {
                            result.setRateToUSD(rateToUSD);
                        }
                    }

                } else {
                    mapCurrencyAmount.put(currency, new CurrencyAmount(currency, amount, rateToUSD));
                }

            } catch (Exception ex) {
                System.err.println(ex.toString());
            }
        }
    }
}
