# Tinkoff Backend Academy homework 1

Asynchronous task processing for an online store.


## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)

## Information

**Project Name:** Tinkoff Backend Academy Homework 5 - Asynchronous task processing for an online store

**Description:**
This project is an extension of the online store from previous homework assignments. The online store already has thousands of different products, and new product categories are constantly being added. In this assignment, you need to implement asynchronous transfer of all products from one specified category to another. Two HTTP methods need to be implemented.

**Technologies Used:**

- **Programming Language:** Java 17
- **Development Tools:** IntelliJ IDEA, Gradle, Spring Framework
- **Version Control:** Git

**Key Features:**

- **Create a transfer task:** The method takes two ids as input: the id of the category from which the products need to be transferred and the id of the category to which the products need to be transferred. The method creates a transfer task, and the transfer itself should be performed asynchronously in the background. The method returns the task id.

    `POST /move-products-task`
    Input data:

    ```JSON
    {
        "sourceCategoryId": 1
        "targetCategoryId": 2
    }
    ```

    Request result:

    ```JSON
    {
        "taskId": 432
    }
    ```

- **Get the status of a running task:**

    `GET /task-status?taskId=432`

    Request result:

    ```JSON
    {
        "taskId": 432
        "taskStatus": "WAITING"
    }
    ```

    Possible task statuses: `WAITING` / `IN_PROGRESS` / `DONE` / `ERROR`

**Implementation Details:**

- **Asynchronous Processing:** The project uses asynchronous processing to transfer products between categories.
- **HTTP Endpoints:** The project provides two HTTP endpoints for creating and checking the status of transfer tasks.
- **Task Status:** Started task have status `WAITING`, which changes to `IN_PROGRESS` during processing and then to `DONE` or `ERROR` upon completion.
- **Validation:** The project validates the input data to ensure that the category ids are valid and that the categories exist in the database.

**Usage Instructions:**

1. Start the application by running the main class.
2. Use an HTTP client to send requests to the application.
3. Send a POST request to create a transfer task.
4. Send a GET request to check the status of the task.

**Project Goals:**

- Implement asynchronous task processing for moving products between categories in an online store.
- Learn how to create HTTP endpoints for creating and checking the status of tasks.
- Practice working with asynchronous processing in a web application.
- Learn the principles of writing asynchronous code in the Spring Framework.

## Part 1. Implementation

You need to implement 2 http methods.

1. Creating a transfer task:

2. Obtaining the status of the running task

Mandatory requirements:

- Don't forget the SOLID principles, especially the single responsibility principle.
- Asynchronous task processing logic should not be mixed with the business logic of products and categories.
