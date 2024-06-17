# Tinkoff Backend Academy homework 14

Integration Keycloak and Spring Boot

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)
3. [Links](#links)

## Information

**Project Name:** Tinkoff Backend Academy homework 14 - Integration Keycloak and Spring Boot

**Description:**
In this homework, you need to implement the integration of Keycloak and Spring Boot. Keycloak implements most of the authentication standards and is widely used. This is partially due to the fact that it is used in aws iam. In solving the homework, you need to get a docker-compose file with configurations for keycloak and an application that allows you to implement the behavior of the account service from the previous homework.

**Technologies Used:**

- **Programming Language:** Java 17
- **Development Tools:** IntelliJ IDEA, Maven, Docker, Spring Boot, Keycloak
- **Version Control:** Git

**Key Features:**

- **Integration Keycloak and Spring Boot** - In this homework, you need to implement the integration of Keycloak and Spring Boot.
- **Docker** - To simplify running the environment and tests, you need to add the `docker-compose.yml` file and test containers for integration tests.
- **Authorization** - Authorization must be implemented using Keycloak.
- **API** - The application must provide an API for working with users.

**Implementation Details:**

- **Keycloak** - To simplify running the environment and tests, you need to add the `docker-compose.yml` file and test containers for integration tests.
- **Spring Boot** - The application must provide an API for working with users.
- **Keycloak** configurations in `docker/keycloak/import/realm-export.json`

**Usage Instructions:**

- Docker and docker-compose must be installed on your computer
- Clone the repository
- Go to the `accounting/docker` directory
- Run the following command

```bash
docker-compose up
```

or

```bash
docker-compose -f docker-compose.yml up
```

- Open a browser and go to `http://localhost:8282`

- You should see the login page
- Use the following credentials to log in
  - Username: admin
  - Password: admin

- You should see control panel
- Now you can run the application
- Go to the `accounting` directory
- Run the following command

```bash
mvn spring-boot:run
```

- Now you can access the application at `http://localhost:8080/api/`

**Project Goals:**

- Learn how to integrate Keycloak and Spring Boot.
- Learn to work with Docker and docker-compose.
- Learn to work with Keycloak and Spring Boot.
- Learn the principles of authorization and authentication.

## Part 1. Implementation

In solving the homework, you need to get a docker-compose file with configurations for keycloak and an application that allows you to implement the behavior of the account service from the previous homework. Allowing you to register a user in the user base and keycloak. Get a token. Provide secure access to user accounts. Not secure for registration. The user can only edit their account.

## Links

[Good article series](https://habr.com/ru/articles/716232/), which may be useful. I also recommend to understand how client authorization differs from user authorization and basic fields in authorization tokens.
To be able to save the settings see [article](https://www.keycloak.org/server/importExport).
[To view](https://www.baeldung.com/spring-boot-keycloak) and if you have trouble creating a user via api [article](https://www.appsdeveloperblog.com/keycloak-rest-api-create-a-new-user/) may help.