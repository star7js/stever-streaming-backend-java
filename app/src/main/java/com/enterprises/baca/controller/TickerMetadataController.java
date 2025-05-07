package com.enterprises.baca.controller;

import com.enterprises.baca.exception.TickerNotFoundException;
import com.enterprises.baca.service.TickerMetadataService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TickerMetadataController {

    public static final String METADATA_PATH = "/api/v1/metadata";
    public static final String METADATA_PATH_ID = METADATA_PATH + "/{ticker}";

    private final TickerMetadataService tickerMetadataService;

    @GetMapping(METADATA_PATH_ID)
    public String getCompanyName(@PathVariable("ticker") String ticker, HttpServletRequest request) {
        return tickerMetadataService.getCompanyName(ticker.toUpperCase())
                .orElseThrow(() -> new TickerNotFoundException(ticker));
    }

    @GetMapping(METADATA_PATH)
    public Map<String, String> getAllTickers() {
        return tickerMetadataService.getAllTickers();
    }

}
