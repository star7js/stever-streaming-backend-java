package com.enterprises.baca.model;

import java.util.Deque;

public record TradeStats(
        double averagePrice,
        int totalVolume,
        double lastPrice,
        Deque<Double> recentPrices
) {
}
