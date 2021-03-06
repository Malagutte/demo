# Demo API
[![Build](https://github.com/Malagutte/demo/actions/workflows/buildl.yml/badge.svg)](https://github.com/Malagutte/demo/actions/workflows/buildl.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Malagutte_demo&metric=alert_status)](https://sonarcloud.io/dashboard?id=Malagutte_demo)

Spring boot challenge:

1. download apache kafka locally, (just need to unzip) and run it (zookeeper & kafka servers)
2. create 1 kafka topic called "novice-players"

3. create the controllers, services, repositories and entities needed to do the following:

- a POST endpoint that receives an array of players
```json
{
  "players": [
    {
      "name": "Sub zero",
      "type": "expert"
    },
    {
      "name": "Scorpion",
      "type": "novice"
    },
    {
      "name": "Reptile",
      "type": "meh"
    }
  ]
}
```

- for each "player" object you need to check its "type" property
  - if it is "expert" you need to store the player in a H2 database.
  - if it is "novice" you need to send that object via a kafka event to the "novice-players" topic
  - if the type does not fit one of the above, you will let the user know in the endpoint response
the response for the above payload would be:

```json
{
  "result": [
    "player Sub zero stored in DB",
    "player Scorpion sent to Kafka topic",
    "player Reptile did not fit"
  ]
}
```



## Prerequisites

- Git
- Java 15+
- Maven 3.6.1+
- Docker 20.10.3+
- Docker Compose 1.24.0+

## Service versions
| Service Name | Version 
| :---------: | :-----: |
| Zookeeper   | 6.10.0  |
| Kafka       | 6.10.0  |
| H2 database | 1.4.199 |




## Installation
Build project
```bash
mvn clean install
```
or
```bash
./mvnw clean install
```

Start services
```bash
docker-compose up -d
```

## Setup enviroment 

#### Create topic at kafka(Optional) :
The project will create the topic  **novice-players**, as it was configured to perform such action but if you want to create manually this is the command
```bash
docker-compose exec kafka kafka-topics --create --bootstrap-server \
localhost:9092 --replication-factor 1 --partitions 1 --topic novice-players
```


## Search messages at Kafka
There are two ways to consult the messages within the topic:
Using the command:
```bash
docker-compose exec kafka kafka-console-consumer  --bootstrap-server localhost:9092 --topic novice-players --from-beginning
```

or 

Using [kafdrop](http://localhost:19000/topic/novice-players/messages)

![kafdrop](https://raw.githubusercontent.com/obsidiandynamics/kafdrop/master/docs/images/overview.png)


## Run application
```bash
mvn spring-boot:run -P local
```
 or 

 ```bash
./mvnw spring-boot:run -P local
```
 or 
```bash
java -jar -Dspring.profiles.active=local target/demo-0.0.1-SNAPSHOT.jar
```

Acess to swagger [here!](http:localhost:8080/swagger-ui.html)