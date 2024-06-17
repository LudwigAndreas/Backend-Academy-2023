# Tinkoff Backend Academy homework 16

Reactive microservice for working with topics.

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)

## Information

**Project Name:** Tinkoff Backend Academy homework 16 - Reactive microservice for working with topics

**Description:**
In this homework, you need to implement a service for working with topics from the seminar. We use mongoDb and radis for caching as databases. The application should work on the reactive stack. We don't send anything to Kafka yet. Test coverage should be at least 90%. Add validation of test coverage in .github actions.

**Technologies Used:**

- **Programming Language:** Java 17
- **Development Tools:** IntelliJ IDEA, Maven, Docker, Spring Boot, MongoDB, Redis, WebFlux
- **Version Control:** Git

**Key Features:**

- **Implementation of the topic service** - In this homework, you need to implement a service for working with topics from the seminar.
- **MongoDB and Redis** - We use mongoDb and radis for caching as databases.
- **Reactive stack** - The application should work on the reactive stack.
- **Test coverage** - Test coverage should be at least 90%. Add validation of test coverage in .github actions

**Implementation Details:**

- **MongoDB and Redis** - Configure connection to mongoDb and redis
- **Interaction with user service** - Configure interaction with the user service and keycloak for authentication and obtaining information about users

**Usage Instructions:**

1. Run the mongoDb and redis databases using docker-compose
2. Run the application using the command `mvn spring-boot:run`

**Project Goals:**

- Learn the reactive stack and its application in microservices development.
- Learn to work with NoSQL databases MongoDB and Redis.
- Acquire skills in configuring caching in Redis.

## Part 1. Implementation

You need to implement a service for working with topics from the seminar. Use mongoDb for data storage, radis for caching. The application should be launched via docker-compose. Test coverage should be at least 90%. Add validation of test coverage in .github actions. Authorization should be done through keycloak from the previous homework. The application should work on the reactive stack.
