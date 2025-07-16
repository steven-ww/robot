#!/bin/bash

# Robot Wars Game Client Runner
# This script builds and runs the Robot Wars application

set -e

echo "Building Robot Wars Game Client..."
./gradlew build

echo ""
echo "Starting Robot Wars Game Client..."
echo "=================================="

java -jar app/build/libs/app.jar
