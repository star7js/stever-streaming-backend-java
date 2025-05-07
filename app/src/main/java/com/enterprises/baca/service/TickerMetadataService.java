package com.enterprises.baca.service;

import java.util.Map;
import java.util.Optional;

public interface TickerMetadataService {

    Optional<String> getCompanyName(String ticker);
    Map<String, String> getAllTickers();
}
