package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<String, Integer> hashMapCurrencyAmount = new HashMap<>();

    private static void calculate(String inputVal) {
        String[] currencyAmount = inputVal.split(" ", 2);

        if (currencyAmount.length != 2) {
            System.out.println("Oops try again, input format 'USD 100', quit - program exit");
        } else {
            try{
                String currency = currencyAmount[0].toUpperCase();
                int amount = Integer.parseInt(currencyAmount[1]);
                    /*
                    Если в hashMap уже имеется такая валюта
                     */
                if (hashMapCurrencyAmount.containsKey(currency)) {
                    int currentAmountValue = hashMapCurrencyAmount.get(currency);
                    hashMapCurrencyAmount.put(currency, amount + currentAmountValue);
                } else {
                    hashMapCurrencyAmount.put(currency, amount);
                }
            } catch (Throwable e){
                throw new IllegalArgumentException(e.toString());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Write a currency and amount separated whitespace, example input USD 100, ");

        if(args.length > 0) {
            /*
            Читаем данные из файла
             */
            String basePath = System.getProperty("user.dir");
            BufferedReader objReader = new BufferedReader(new FileReader(basePath+"\\"+args[0]));
            String strCurrentLine;
            while ((strCurrentLine = objReader.readLine()) != null) {
                calculate(strCurrentLine);
            }
        }

        while(true) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String inputVal = reader.readLine();
            /*
            Выход из программы
             */
            if (inputVal.equals("quit")) {
                System.exit(0);
            }

            calculate(inputVal);

            for (Map.Entry<String, Integer> entry : hashMapCurrencyAmount.entrySet()) {
                if (entry.getValue() == 0) {
                    hashMapCurrencyAmount.remove(entry.getKey());
                } else {
                    System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
                }
            }


        }
    }
}
