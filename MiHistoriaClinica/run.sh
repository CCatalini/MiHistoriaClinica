#!/bin/bash
# Script to load .env and run the application

# Load .env file
if [ -f .env ]; then
    export $(cat .env | grep -v '^#' | xargs)
fi

# Run the application
./gradlew bootRun
