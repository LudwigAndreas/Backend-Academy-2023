# Tinkoff Backend Academy homework 6

Electronic queues project.

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)
3. [Part 2](#part-2-bonus)

## Information

**Project Name:** Tinkoff Backend Academy Homework 6 - Electronic Queues Project

**Description:**

The e-queue project is a program that allows users to create tasks, add them to a queue, process them and complete them. The program provides REST API for interaction with users and managers.

**Technologies Used:**

- **Programming Language:** Java 17
- **Development Tools:** IntelliJ IDEA, Gradle, Spring Framework
- **Version Control:** Git

**Key Features:**

- **Task Creation:** The method takes task data as input and adds it to the queue. The method returns the task number.
- **Getting the Next Task:** The method returns the next task from the queue for processing.
- **Getting All Existing Tasks:** The method returns all tasks, divided by status.
- **Getting a Task by Number:** The method returns a task by task number.
- **Changing the Task Status:** The method changes the task status by task number.
- **Deleting a Task:** The method deletes a task by task number.
- **Getting the Average Task Processing Time:** The method returns the average task processing time.
- **Getting Task Processing Time:** The method returns the task processing time by task number.
- **Getting Task Processing Time by Status:** The method returns the task processing time by status.

**Implementation Details:**

- **Task Statuses:**
  - NEW
  - WAITING
  - PROCESSED
  - CLOSE
  - CANCEL
- **Task Queue:** `ConcurrentLinkedQueue` is used to implement the task queue.
- **Task Number:** A unique string of several digits and letters.
- **Task Verification:** Tasks that are not in the `NEW` status, take longer than 30 minutes during verification once a minute by cron, transition to the `CANCEL` status.

**Usage Instructions:**

1. Run the application by running the main class.
2. Use an HTTP client to send requests to the application.
3. Send a POST request to create a task to move products from one category to another.
4. Send a GET request to get the task status.

**Project Goals:**

- Implement a program for creating, adding, processing, and completing tasks.
- Implement a REST API for interacting with the program.
- Ensure test coverage above 90%.
- Learn and use mapStruct, Swagger and Lombok.
- Implement contract first using Swagger.
- Implement SOAP API.
- Implement gRPC.
- Implement SecurityFilterChain with access to endpoints for creating and getting a list of tasks in the queue `permitAll`, and to the rest by roles `manager`, `user` with a password and login stored in `application.properties` or environment variable.
- Ensure test coverage SecurityFilterChain.
- Implement Swagger.yml

## Part 1. Implementation

Usage:

- queue for task processing. A person approaches the terminal and the terminal calls POST /task request to get the number in the queue. Getting the task object.
- The operator retrieves the next WAITING task in the queue by calling GET /task
- The operator changes the task processing status by task number PATCH /task/number
- The operator can delete a task by task number DELETE /task/number
- The screen shows a breakdown of tasks by status except for completed TaskStatus GET /tasks.

Objects:

  ```JSON
    Task {
        number,
        status,
        times: [status: time] 
    }
  ```

  ```JSON
    TaskStatus {
        status :[number] 
    }
  ```

User:

- Statuses:
  `NEW` -> `WAITING` -> `PROCESSED` -> `CLOSE`.
  The status to which any task can go is `CANCEL`. It is possible to move a task one step backward by status and one step forward. A task from the `PROCESSED` status transferred to `WAITING` is put to the end of the queue.
- Number:
  A unique string of multiple numbers and letters
  Use for queues of the `ConcurrentLinkedQueue` implementation
- Time:
  The processing time of the task in milliseconds

Manager:

- Manager can view average processing time by task /times
- Manager can view processing time by task /times/number
- Manager can view processing time by task status /times/number/status
- Manager can view average processing time by status /times/status

> Use mapStruct swagger lombok in the project. Ensure coverage above 90%
> Tasks in status other than NEW for more than 30 minutes are switched to CANCEL status by cron check once a minute.

## Part 2. Bonus

- +1 point To design swagger.yml for the project and implement contract first
- +1 point to implement SOAP service
- +1 point implement grpc
- +1 point [implement SecurityFilterChain](https://docs.spring.io/spring-security/reference/servlet/configuration/java.html) with access to endpoints to create and get the list of tasks in the permitAll queue, and to the rest by roles "manager", "user" with password and login stored in application.properties or env variable. And cover this behavior with tests.
