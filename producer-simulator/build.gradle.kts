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
//    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
//    implementation("com.fasterxml.jackson.core:jackson-core:2.17.0")
//    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.0")
}

application {
    mainClass.set("com.enterprises.baca.producer.StockTradeProducer")
}