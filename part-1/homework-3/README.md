# Tinkoff Backend Academy homework 3

Simple web CRUD database for a store.

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)

## Information

**Project Name:** Tinkoff Backend Academy Homework 3 - Simple Web CRUD Database

**Description:**
This project is a simple web application that stores and retrieves basic information about products: SKU, name, price, and quantity. The application provides HTTP endpoints for adding, updating, and deleting products, as well as retrieving a list of all products. The project uses two different storage options: in-memory storage and file storage. The user can choose the storage option by setting a property in the application configuration.

**Technologies Used:**

- **Programming Language:** Java 17, Spring Framework
- **Development Tools:** IntelliJ IDEA, Gradle
- **Version Control:** Git

**Key Features:**

- **Add Product:** Add a new product with a unique SKU.
- **List Products:** Display a list of all products in JSON format.
- **Update Product:** Update the details of an existing product by its SKU.
- **Delete Product:** Delete a product from the database by its SKU.
- **Storage Options:** Choose between in-memory storage and file storage by setting a property in the application configuration.

**Implementation Details:**

- **Storage Options:** The application supports two storage options: in-memory storage and file storage.
- **HTTP Endpoints:** The application provides four HTTP endpoints for managing products: POST, GET, PUT, and DELETE.
- **Validation:** The application validates the SKU to ensure it is unique and contains only digits and uppercase Latin letters.

**Usage Instructions:**

1. Run the application by executing the main class.
2. Use an HTTP client to send requests to the application.
3. Set the storage option in the application configuration.
4. Send POST, GET, PUT, and DELETE requests to manage products.
5. View the response from the application to see the results of the requests.

**Project Goals:**

- Implement a simple web CRUD application using the Spring Framework.
- Learn to create HTTP endpoints for adding, updating, and deleting data.
- Practice working with different storage options in a web application.

## Part 1. Implementation

1. Implement four HTTP endpoints:
   - POST /product: Add a new product.
   - GET /product: Display a list of all products.
   - PUT /product/$sku: Update the details of a product by its SKU.
   - DELETE /product/$sku: Delete a product by its SKU.
2. Implement two data storage options:
   - In-memory.
   - File.

Mandatory requirements:

- The SKU must be unique. Only one product with the same SKU can be added.
- The SKU can contain only digits and uppercase Latin letters.
