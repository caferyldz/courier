## Requirements

For building and running the application you need:

- [JDK 15+](https://www.oracle.com/java/technologies/downloads/#java15)
- [Maven 3+](https://maven.apache.org)

## Running the application locally
There are several ways to run a Spring Boot application on your local machine.
1) One way is to execute the main method in the com.migros.courier.CourierApplication class from your IDE.

2) Alternatively you can use the Spring Boot Maven plugin like so:
```shell
mvn spring-boot:run
```

3) Open terminal goto project directory then run
```shell
mvn clean install
```
goto /target director and run
```shell
java -jar courier-0.0.1-SNAPSHOT.jar
```

For Test
-Open your browser
-enter url http://localhost:8080/api/courier/swagger-ui/#/
