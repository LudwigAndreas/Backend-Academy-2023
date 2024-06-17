# Tinkoff Backend Academy homework 9

Development of a web application for accounting students and courses (part 2).

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)

## Information

**Project Name:** Tinkoff Backend Academy Homework 9 - Development of a web application for accounting students and courses 2.

**Description:**

Development of a web application for accounting students and courses. Adding editing student data and their courses via api. Implementation of protection against parallel updates using optimistic-lock.

**Technologies Used:**

- **Programming Language:** Java 17
- **Development Tools:** IntelliJ IDEA, Maven, Postgres, Spring Boot
- **Version Control:** Git

**Key Features:**

- **Editing student data:** Adding editing student data and their courses via api.

**Implementation Details:**

- **Protection against parallel updates:** Implementation of protection against parallel updates using optimistic-lock.
- **Tests:** Cover new methods with tests. Tests should check that when trying to update in parallel, an exception will be thrown.

**Usage Instructions:**

1. Start the application by launching the main class.
2. Use the HTTP client to send requests to the application.
3. Send a POST request to create a new student.
4. Send a GET request to retrieve a list of all students.
5. Send a GET request to retrieve student information by student ID.
6. Send a POST request to add a student to one or more courses.
7. Send a POST request to create a new course.
8. Send a GET request to retrieve a list of all courses.
9. Send a GET request to retrieve course information by course ID.
10. Send a PUT request to edit the student's data and its courses.
11. Send a GET request to retrieve student information by its ID.
12. Send a GET request to retrieve course information by course ID.

**Project Goals:**

- Learn about the possibilities of protecting against parallel updates using optimistic-lock.
- Implement editing student data and their courses via api.
- Cover new methods with tests.

## Part 1. Implementation

Take the project from HW 7 as a basis. Add editing student data and their courses via api. Implement protection against parallel updates using optimistic-lock. Cover new methods with tests. Tests should check that when trying to update in parallel, an exception will be thrown. Optimistic lock can be implemented manually or using hibernate tools.
