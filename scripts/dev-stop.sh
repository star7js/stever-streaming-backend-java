#!/bin/bash

echo "🛑 Stopping Backend App (if running)..."
pkill -f "bootRun" || echo "Backend app was not running"

echo "🛑 Stopping Producer Simulator (if running)..."
pkill -f "producer-simulator" || echo "Producer simulator was not running"

echo "🧼 Stopping Kafka stack..."
docker compose down