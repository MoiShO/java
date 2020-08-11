package com.company;

import java.text.DecimalFormat;

public class CurrencyAmount {
    String currency;
    int amount;
    double rateToUSD;

    CurrencyAmount(String inCurrency, int inAmount, double isRateToUSD) {
        currency = inCurrency;
        amount = inAmount;
        rateToUSD = isRateToUSD;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setRateToUSD(double rateToUSD) {
        this.rateToUSD = rateToUSD;
    }

    @Override
    public String toString() {

        if (rateToUSD != 0 && !currency.equals("USD")) {
            DecimalFormat df = new DecimalFormat("#.##");
            return "· " + currency +
                   " " + amount + " "
                   + "(USD " + df.format(amount / rateToUSD) + ")";
        }

        return "· " + currency + " " + amount;
    }
}
