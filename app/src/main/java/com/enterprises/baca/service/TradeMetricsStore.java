package com.enterprises.baca.service;

import com.enterprises.baca.model.TradeStats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TradeMetricsStore {

    private final Map<String, Deque<Double>> priceHistory = new ConcurrentHashMap<>();
    private final Map<String, Integer> totalVolumeMap = new ConcurrentHashMap<>();
    @Value("${metrics.window-size:10}")
    private int windowSize;

    public void recordTrade(String ticker, double price, int volume) {
        priceHistory.computeIfAbsent(ticker, k -> new ArrayDeque<>());
        Deque<Double> prices = priceHistory.get(ticker);

        if (prices.size() >= windowSize) {
            prices.pollFirst();
        }
        prices.add(price);

        totalVolumeMap.merge(ticker, volume, Integer::sum);
    }

    public Optional<TradeStats> getStats(String ticker) {
        Deque<Double> prices = priceHistory.get(ticker);
        if (prices == null || prices.isEmpty()) return Optional.empty();

        double averagePrice = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        int totalVolume = totalVolumeMap.getOrDefault(ticker, 0);
        double lastPrice = prices.getLast();

        return Optional.of(new TradeStats(averagePrice, totalVolume, lastPrice, new ArrayDeque<>(prices)));
    }

    public Set<String> getTrackedTickers() {
        return priceHistory.keySet();
    }
}
