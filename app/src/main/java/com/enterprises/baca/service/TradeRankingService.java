package com.enterprises.baca.service;

import com.enterprises.baca.model.TradeStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeRankingService {

    private final TradeMetricsStore metricsStore;

    public <U extends Comparable<? super U>> Map<String, TradeStats> rankBy(
            Function<TradeStats, U> keyExtractor,
            int limit,
            boolean ascending
    ) {
        Comparator<TradeStats> comparator = Comparator.comparing(keyExtractor);
        if (!ascending) comparator = comparator.reversed();

        return metricsStore.getTrackedTickers().stream()
                .map( ticker -> Map.entry(ticker, metricsStore.getStats(ticker)))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().get()))
                .sorted(Map.Entry.comparingByValue(comparator))
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> b,
                        LinkedHashMap::new
                ));
    }
}
