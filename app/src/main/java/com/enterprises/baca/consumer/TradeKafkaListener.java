package com.enterprises.baca.consumer;

import com.enterprises.baca.model.Trade;
import com.enterprises.baca.service.TradeProcessorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeKafkaListener {

    private final TradeProcessorService tradeProcessor;

    @KafkaListener(
            topics = "trades",
            groupId = "trade-consumer-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(Trade trade) {
        tradeProcessor.process(trade);
    }
}
