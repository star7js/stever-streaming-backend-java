#!/bin/bash

set -e

echo "🚀 Starting Kafka stack via Docker Compose..."
docker compose up -d

echo "⏳ Waiting for Kafka to be ready on localhost:9092..."
while ! nc -z localhost 9092; do
  echo "  🔄 Kafka not ready yet... sleeping 2s"
  sleep 2
done
echo "✅  Kafka is up!"

echo "📈 Starting Producer Simulator..."
./gradlew :producer-simulator:run &
PRODUCER_PID=$!

# Health check loop to confirm the producer is alive
echo "⏳  Waiting for Producer Simulator to report activity..."
RETRY_COUNT=0
until pgrep -f "producer-simulator" > /dev/null || [ $RETRY_COUNT -ge 10 ]; do
  echo "  🔄 Checking if producer process is up..."
  sleep 1
  ((RETRY_COUNT++))
done

if ! ps -p $PRODUCER_PID > /dev/null; then
  echo "❌ Producer Simulator failed to start. Exiting."
  exit 1
fi
echo "✅  Producer Simulator is running (PID: $PRODUCER_PID)"

echo "🌱 Starting Spring Boot backend..."
./gradlew :app:bootRun