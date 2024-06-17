# Tinkoff Backend Academy homework 7

Development of a web application for tracking students and courses

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)

## Information

**Project Name:** Tinkoff Backend Academy Homework 7 - Development of a web application for tracking students and courses

**Description:**
Web application for tracking students and courses using Java, Spring Boot, and a database. The application contains two entities: "Student" and "Course". Each student is associated with one or more courses. The application provides an API for the following actions: creating a new student, getting a list of all students, getting information about a student by their identifier, adding a student to one or more courses, creating a new course, getting a list of all courses, and getting information about a course by its identifier. Spring Data and JPA tools are used to interact with the database. Data validation ensures that students and courses have the necessary attributes. Exception handling provides error handling and informative error messages. Unit tests are written for controllers and services.

**Technologies Used:**

- **Programming Language:** Java 17
- **Development Tools:** IntelliJ IDEA, Maven, Postgres, Spring Boot
- **Version Control:** Git

**Key Features:**

- **Creating a new student:** The method takes student data as input and adds them to the database. The method returns the student's identifier.
- **Getting a list of all students:** The method returns a list of all students from the database.
- **Getting information about a student by identifier:** The method returns information about a student by their identifier.
- **Adding a student to one or more courses:** The method adds a student to one or more courses.
- **Creating a new course:** The method takes course data as input and adds them to the database. The method returns the course's identifier.
- **Getting a list of all courses:** The method returns a list of all courses from the database.
- **Getting information about a course by identifier:** The method returns information about a course by its identifier.

**Implementation Details:**

- **Tools for interacting with the database:** Spring Data, JPA
- **Database:** Postgres
- **Data Validation:** To ensure that students and courses have the necessary attributes
- **Exception Handling:** For error handling and providing informative error messages
- **Unit Tests:** Written for controllers and services

**Usage Instructions:**

1. Run the application by running the main class.
2. Use an HTTP client to send requests to the application.
3. Send a POST request to create a new student.
4. Send a GET request to get a list of all students.
5. Send a GET request to get information about a student by their identifier.
6. Send a POST request to add a student to one or more courses.
7. Send a POST request to create a new course.
8. Send a GET request to get a list of all courses.
9. Send a GET request to get information about a course by its identifier.

**Project Goals:**

- Implement a web application for tracking students and courses.
- Implement an API for interacting with the application.
- Provide data validation and exception handling.
- Write unit tests for controllers and services.
- Learn Spring Data and JPA tools.

## Part 1. Implementation

1. Create a new Spring Boot project.
2. Define two entities: "Student" and "Course". Each student should be associated with one or more courses.
3. Create a student and course API.
4. Use any of the following tools to interact with the database: Spring Data, JPA, Hibernate, or JdbcTemplate. Choose the database you prefer (e.g., H2, MySQL, PostgreSQL).
5. Implement data validation to ensure that students and courses have the required attributes.
6. Implement exception handling to handle errors and provide informative error messages.
7. Write unit tests for your controllers and services.
