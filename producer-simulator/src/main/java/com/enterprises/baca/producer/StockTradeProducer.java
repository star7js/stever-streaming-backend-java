package com.enterprises.baca.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Instant;
import java.util.*;

import org.json.JSONObject;

public class StockTradeProducer {

    private static final List<String> SYMBOLS = List.of("AAPL", "GOOG", "MSFT", "TSL", "AMZ");
    private static final Random random = new Random();

    public static void main(String[] args) {
        String topic = "trades";

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put("linger.ms", 1);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");


        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nShutting down producer...");
                producer.close();
            }));

            System.out.println("Starting StockTradeProducer...");

            while (true) {
                try {
                    JSONObject tradeJSON = generateFakeTrade();

                    ProducerRecord<String, String> record = new ProducerRecord<>(topic, tradeJSON.getString("symbol"), tradeJSON.toString());
                    producer.send(record, ((metadata, exception) -> {
                        if (exception != null) {
                            System.err.println("Failed to send: " + exception.getMessage());
                        } else {
                            System.out.println("Sent to " + metadata.topic() + " at offest " + metadata.offset());
                        }
                    }));

                    Thread.sleep(100);
                } catch (Exception e) {
                    System.err.println("Error during trade generation or sending: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception fatal) { // TODO: add better logging, review exception strategy
            System.err.println("Fatal error in producer setup: " + fatal.getMessage());
            fatal.printStackTrace();
        }
    }

    private static JSONObject generateFakeTrade() {
        String symbol = SYMBOLS.get(random.nextInt(SYMBOLS.size()));
        double price = 100 + (random.nextDouble() * 100);
        int volume = 1 + random.nextInt(1000);
        long timestamp = Instant.now().toEpochMilli();

        JSONObject json = new JSONObject();
        json.put("symbol", symbol);
        json.put("price", price);
        json.put("volume", volume);
        json.put("timestamp", timestamp);

        return json;

    }
}