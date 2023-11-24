# JungkoServer

This app was created with Bootify.io - tips on working with the code [can be found here](https://bootify.io/next-steps/).
Feel free to contact us for further questions.

## Prerequisites

You should have the run containerized applications on your machine.
```bash
docker compose down --rmi all && docker compose up --build -d
```


## Build

The application can be built using the following command:

```bash
chmod +x gradlew && ./gradlew clean build
```

Start your application with the following command - here with the profile `local`:

```bash
java -Dspring.profiles.active=local -jar ./build/libs/jungko-server-0.0.1-SNAPSHOT.jar
```

## Quickstart

You just run this bash script to build and start the application:

```bash
chmod +x quickstart.sh && bash quickstart.sh local
```

## Further readings

* [Gradle user manual](https://docs.gradle.org/)  
* [Spring Boot reference](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)  
* [Spring Data JPA reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)  
