# loan-platform


### Build application

mvn clean install

### Run application

docker-compose up

### Stop application

docker stop <pid>


### Mysql setup

1. Setup Mysql 5.7
2. Create schema using /db/schema.sql file

### Rabbitmq setup

1. Setup RabbitMQ 3.8.8

## swagger UI URL for APIs contract:

http://localhost:8080/swagger-ui.html

## swagger enabled/disbled:
-Dspring.profiles.active=swagger //enabled
-Dspring.profiles.active=prod //disabled
-Dspring.profiles.active=prod,swagger //disabled
-Dspring.profiles.active=stage,swagger //enabled

## actuator health URL:
http://localhost:8080/actuator
http://localhost:8080/actuator/health