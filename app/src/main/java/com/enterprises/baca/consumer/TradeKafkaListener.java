package com.enterprises.baca.consumer;

import com.enterprises.baca.model.Trade;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TradeKafkaListener {

    @KafkaListener(
            topics = "trades",
            groupId = "trade-consumer-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(Trade trade) {
        System.out.printf("Consumed Trade: %s - $%.2f (%d shares)%n",
                trade.ticker(), trade.price(), trade.volume(), trade.timestamp()
        );
    }
}
