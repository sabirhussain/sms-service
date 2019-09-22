
# Introduction
Sample spring boot app, showcasing speration of unit and integration test.

# Setup
## Prerequisite
1. git
2. docker
3. jdk8 [for Development]
## Checkout source
```bash
$ git clone https://github.com/sabirhussain/sms-service.git
```
## Build
>**Note:** Run below commands inside project directory
```bash
$ mvnw clean install
```
## Build with Integration Test
```bash
$ mvnw clean install -Denv=int-test
```
## Build with All Test
```bash
$ mvnw clean install -Denv=test-all
```
## Build Docker Image
Run below script to build docker image. [Modify script to update tag]
```bash
$ prepare-docker.sh
```

# Deploy
```bash
$ docker-compose up -d
```
