package com.enterprises.baca.service;

import jakarta.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileBasedTickerMetadataService implements TickerMetadataService {

    private final Map<String, String> tickerToCompanyMap = new HashMap<>();

    @PostConstruct
    public void loadTickerData() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tickers.json"))
                )
        )) {
            String json = reader.lines().collect(Collectors.joining());
            JSONObject obj = new JSONObject(json);
            for (String key : obj.keySet()) {
                tickerToCompanyMap.put(key, obj.getString(key));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load tickers.json", e);
        }
    }

    @Override
    public Optional<String> getCompanyName(String ticker) {
        return Optional.ofNullable(tickerToCompanyMap.get(ticker));
    }

    @Override
    public Map<String, String> getAllTickers() {
        return Collections.unmodifiableMap(tickerToCompanyMap);
    }
}
