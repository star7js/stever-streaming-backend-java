plugins {
    java
    application
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

dependencies {
    implementation("org.apache.kafka:kafka-clients:4.0.0")
    implementation("org.json:json:20250107")
}

application {
    mainClass.set("com.enterprises.baca.producer.StockTradeProducer")
}