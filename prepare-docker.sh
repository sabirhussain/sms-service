#!/bin/bash

mvn clean install -DskipTests
cp target/sms-*.jar target/auzmor-sms-service.jar
docker build --tag="sabirhussain79/sms-service:v1.0.0" .
