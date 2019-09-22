
# Introduction
Sample spring boot app, showcasing separation of unit and integration test.
> To run **integration test cases**, make sure redis is up and running.
> Use below docker command for redis.
```
$ docker run --name auzmor-redis -d -p 6379:6379 redis redis-server --appendonly yes
```

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
> Make sure current working directory have schema.sql and docker-compose.yml
```bash
$ cd deploy
$ docker-compose up -d
```

# AWS EC2 Deploy
https://docs.aws.amazon.com/AmazonECS/latest/developerguide/docker-basics.html
https://docs.docker.com/compose/install/

1. SSH into your EC2 instance
2. Run below commands in sequence
```bash
sudo yum update -y
sudo amazon-linux-extras install docker
sudo service docker start
sudo usermod -a -G docker ec2-user
sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
sudo yum install git -y
```
3. Logout and Login to your EC2 instance and run following commands
```bash
git clone https://github.com/sabirhussain/sms-service.git
cd sms-service\deploy
docker-compose up -d
```

# Monitoring
## Health Check
hit url https://<service>/actuator/health
