package com.fx.rates.quote_service;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
    EUR("EUR"),
    RON("RON"),
    GBP("GBP"),
    USD("USD");

    private static final Map<String, Currency> MAP = new HashMap<>();

    private final String label;

    static {
        for (Currency currency : Currency.values()) {
            MAP.put(currency.getLabel(), currency);
        }
    }

    public static Currency getByLabel(String label) {
        return MAP.get(label);
    }
}
