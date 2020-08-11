package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;

public class Main {
    private static final ArrayList<CurrencyAmount> arrayCurrencyAmount = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Write a currency and amount separated whitespace" +
                "example input USD 100, or HKD 100 0.25 ");
        /*
        Запускаем таймер печати в Terminal
         */
        Thread printer = startTerminalPrinter();

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

        /*
        завершаем выполнение
         */
        printer.interrupt();
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
        boolean isStopRequested = false;

        while(!isStopRequested) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String inputVal = reader.readLine();

            /*
            Выход из программы
             */
            if (inputVal.equals("quit")) {
                reader.close();
                isStopRequested = true;

            } else  {
                addCurrencyAmount(inputVal);
            }
        }
    }

    /**
     * Запуск таймера печати в Terminal
     */
    private static Thread startTerminalPrinter() {
        Thread printInTerminal = new PrintCurrencyAmount(arrayCurrencyAmount);
        printInTerminal.start();
        return printInTerminal;
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
                Optional<CurrencyAmount> result = arrayCurrencyAmount
                        .stream().parallel()
                        .filter(cA -> cA.getCurrency().equals(currency)).findFirst();

               /*
               Если в arrayCurrencyAmount уже имеется такая валюта
                */
                if (result.isPresent()) {
                    CurrencyAmount currentCurrencyAmount = result.get();
                    int sumAmount = amount + currentCurrencyAmount.getAmount();

                    if (sumAmount == 0) {
                        arrayCurrencyAmount.remove(currentCurrencyAmount);

                    } else {
                        currentCurrencyAmount.setAmount(amount + currentCurrencyAmount.getAmount());
                        if (rateToUSD != 0.0) {
                            currentCurrencyAmount.setRateToUSD(rateToUSD);
                        }
                    }

                } else {
                    arrayCurrencyAmount.add(new CurrencyAmount(currency, amount, rateToUSD));
                }

            } catch (Exception ex) {
                System.err.println(ex.toString());
            }
        }
    }
}
