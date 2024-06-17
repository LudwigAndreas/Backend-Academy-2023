# Tinkoff Backend Academy homework 4

Simple web CRUD database for a store with categories.

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)

## Information

**Project Name:** Tinkoff Backend Academy Homework 4 - Simple Web CRUD Database with Categories

**Description:**
This project is a simple web application that stores and retrieves basic information about products: SKU, name, price, and quantity. The application provides HTTP endpoints for adding, updating, and deleting products, as well as retrieving a list of all products. The project uses two different storage options: in-memory storage and file storage. The user can choose the storage option by setting a property in the application configuration. In this version, the project has been extended to include categories for products. Each product can be assigned to a category, and products without a specified category are added to a default category. The application provides HTTP endpoints for managing categories, including adding, updating, and deleting categories, as well as retrieving a list of all categories and the products in each category.

**Technologies Used:**

- **Programming Language:** Java 17, Spring Framework
- **Development Tools:** IntelliJ IDEA, Gradle
- **Version Control:** Git

**Key Features:**

- **Add Product:** Add a new product with a unique SKU and assign it to a category.
- **List Products:** Display a list of all products in JSON format, including the category for each product.
- **Update Product:** Update the details of an existing product by its SKU.
- **Delete Product:** Delete a product from the database by its SKU.
- **Add Category:** Add a new category with a unique URL and ID.
- **List Categories:** Display a list of all categories in JSON format, including the products in each category.
- **Update Category:** Update the information of an existing category by its ID.
- **Delete Category:** Delete a category from the database by its ID.
- **Storage Options:** Choose between in-memory storage and file storage by setting a property in the application configuration.

**Implementation Details:**

- **Storage Options:** The application supports two storage options: in-memory storage and file storage.
- **HTTP Endpoints:** The application provides eight HTTP endpoints for managing products and categories: POST, GET, PUT, and DELETE for products and categories.
- **Validation:** The application validates the SKU and category URL to ensure they are unique and contain only valid characters.

**Usage Instructions:**

1. Run the application by executing the main class.
2. Use an HTTP client to send requests to the application.
3. Set the storage option in the application configuration.
4. Send POST, GET, PUT, and DELETE requests to manage products and categories.
5. View the response from the application to see the results of the requests.

**Project Goals:**

- Implement a simple web CRUD application using the Spring Framework.
- Learn to create HTTP endpoints for adding, updating, and deleting data.
- Practice working with different storage options in a web application.
- Extend the application to include categories for products and manage categories using HTTP endpoints.

## Part 1. Implementation

In the service from task 3 add an entity category which, when receiving a name, forms a url in Latin and gets a unique id (kitchen appliances -> cuhonnaya_technika). Goods without the specified category are added to the default category. In queries add a category field where the item will be added. If the category is not found, return an error.

Implement 5 http-endpoint:

1. `POST /category` - Add a new category. Information about the category is returned in the response body in json format.
2. `GET /category` - Display a list of all previously added categories. Categories should contain nested lists of products. The method should return a list of all products in json format.
3. `GET /category/$categoryId` - Display a list of all previously added products in this category. The category should contain a nested list of products. The method should return the category and a list of all products in json format.
4. `PUT /category/$categoryId` - Update information about the category by id.
5. `DELETE /category/$categoryId` - Delete the category from the database by its id.

Mandatory requirements:

- Category url should be unique
- Need to cover unit code and integration tests.

Optional requirements:

- Provide a unique id value for multiple simultaneous category creations without resorting to locks and cover this case study with a test.

## Part 2. Bonus

1. Implement postgres integration for the service.
2. Generate IDs by yourself without using bigSerial and other autoincremental types. Try to cover their competitive update with the test.
3. Use [liquibase](https://www.baeldung.com/liquibase-refactor-schema-of-java-app) for generating the database schema and subsequent migrations.
4. Cover the code working with the database with tests using [testContainer](https://www.baeldung.com/docker-test-containers)
