package com.fx.rates.quote_service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FxRateController {

    private final FxRateService fxRateService;

    @GetMapping("/currencies")
    public List<Currency> getCurrencies() {
        return List.of(Currency.values());
    }

    @GetMapping("/fx-rate")
    public ResponseEntity<RateDto> getRate(@RequestParam String primaryCcy, @RequestParam String secondaryCcy) {
        RateDto rate = fxRateService.getRate(primaryCcy, secondaryCcy);
        if (rate == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(rate);
    }
}
