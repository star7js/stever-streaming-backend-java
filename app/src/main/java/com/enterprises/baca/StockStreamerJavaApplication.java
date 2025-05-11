package com.enterprises.baca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.enterprises.baca")
public class StockStreamerJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockStreamerJavaApplication.class, args);
	}

}
