package com.enterprises.baca.controller;

import com.enterprises.baca.model.TradeStats;
import com.enterprises.baca.service.TradeMetricsStore;
import com.enterprises.baca.service.TradeProcessorService;
import com.enterprises.baca.service.TradeRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class TradeMetricsController {

    private final TradeMetricsStore metricsStore;
    private final TradeProcessorService processorService;
    private final TradeRankingService rankingService;

    @GetMapping("/ticker")
    public ResponseEntity<TradeStats> getMetricForTicker(@PathVariable String ticker) {
        Optional<TradeStats> stats = metricsStore.getStats(ticker.toUpperCase());

        return stats.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Map<String, TradeStats> getAllMetrics() {
        Map<String, TradeStats> result = new HashMap<>();

        for (String ticker : metricsStore.getTrackedTickers()) {
            metricsStore.getStats(ticker).ifPresent(stats -> result.put(ticker, stats));
        }

        return result;
    }

    @GetMapping("/rank/volume")
    public Map<String, TradeStats> getRankedByVolume(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "desc") String order
    ) {
        boolean ascending = order.equalsIgnoreCase("asc");
//        return getRanked(Comparator.comparingDouble(TradeStats::totalVolume), limit, order);
        return rankingService.rankBy(TradeStats::totalVolume, limit, ascending);
    }

    @GetMapping("/rank/average")
    public Map<String, TradeStats> getRankedByAveragePrice(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(defaultValue = "desc") String order
    ) {
        boolean ascending = order.equalsIgnoreCase("asc");
        return rankingService.rankBy(TradeStats::averagePrice, limit, ascending); // move to service
    }
}
