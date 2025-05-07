package com.enterprises.baca.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TickerNotFoundException extends RuntimeException {

    private final String ticker;

    @Override
    public String getMessage() {
        return "Ticker not found: " + ticker;
    }
}
