# stock-streaming-backend-java
A project that will simulate stock market data to practice ingesting and processing streams of data.

Use `start-dev-stack.sh`, this will start Kafka, check to make sure it's running, start the trade producer, 
and start Spring Boot.

At the moment, you will just see the `producer-simulator` print to the console that it sent trades at an offset,
so you know that Kafka is streaming, and the Spring app will print out the trades that it is reading from the stream.

Use `stop-dev-stack.sh` to shut down everything gracefully.
