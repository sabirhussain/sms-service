#zulu OpenJDK is tested, certified build of OpenJDK. https://www.azul.com/
#For google app engine it must run on 8080

FROM azul/zulu-openjdk:8

RUN useradd -ms /bin/bash auzmor
USER auzmor

WORKDIR /home/auzmor

COPY ./target/auzmor-sms-service.jar ./auzmor-sms-service.jar
VOLUME ["/home/auzmor"]
EXPOSE 8080 8080
CMD java -jar auzmor-sms-service.jar 
