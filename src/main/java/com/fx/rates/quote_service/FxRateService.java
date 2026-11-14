package com.fx.rates.quote_service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class FxRateService {

    private static final Map<Currency, Map<Currency, Double>> RATES = new HashMap<>();
    private static final Map<Currency, Double> EUR_RATES = new HashMap<>();
    private static final Map<Currency, Double> USD_RATES = new HashMap<>();
    private static final Map<Currency, Double> GBP_RATES = new HashMap<>();
    private static final Map<Currency, Double> RON_RATES = new HashMap<>();

    static {
        EUR_RATES.put(Currency.GBP, 0.9);
        EUR_RATES.put(Currency.USD, 1.18);
        EUR_RATES.put(Currency.RON, 4.66);
        EUR_RATES.put(Currency.EUR, 1.0);
        RATES.put(Currency.EUR, EUR_RATES);

        USD_RATES.put(Currency.EUR, 0.85);
        USD_RATES.put(Currency.GBP, 0.76);
        USD_RATES.put(Currency.RON, 3.95);
        USD_RATES.put(Currency.USD, 1.0);
        RATES.put(Currency.USD, USD_RATES);

        GBP_RATES.put(Currency.EUR, 1.11);
        GBP_RATES.put(Currency.USD, 1.31);
        GBP_RATES.put(Currency.RON, 5.18);
        GBP_RATES.put(Currency.GBP, 1.0);
        RATES.put(Currency.GBP, GBP_RATES);

        RON_RATES.put(Currency.EUR, 0.21);
        RON_RATES.put(Currency.USD, 0.25);
        RON_RATES.put(Currency.GBP, 0.19);
        RON_RATES.put(Currency.RON, 1.0);
        RATES.put(Currency.RON, RON_RATES);
    }

    public RateDto getRate(String fromCcyStr, String toCcyStr) {
        Currency fromCcy = Currency.getByLabel(fromCcyStr.toUpperCase());
        Currency toCcy = Currency.getByLabel(toCcyStr.toUpperCase());

        // received unknown currencies
        if (fromCcy == null || toCcy == null) {
            return null;
        }

        Map<Currency, Double> fromRates = RATES.get(fromCcy);
        Double baseRate = fromRates.get(toCcy);

        // received unknown currency pair
        if (baseRate == null) {
            return null;
        }

        double buyRate = baseRate != 1 ? baseRate + getRandomizedDelta(0, baseRate) : 1;
        double sellRate = baseRate != 1 ? baseRate - getRandomizedDelta(0.1, baseRate) : 1;

        return new RateDto(buyRate, sellRate, new Date());
    }

    private static double getRandomizedDelta(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}