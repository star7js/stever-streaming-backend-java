package com.enterprises.baca.model;

public record Trade(
        String ticker,
        double price,
        int volume,
        long timestamp
) {

}
