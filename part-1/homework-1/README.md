# Tinkoff Backend Academy homework 1

Simple console CRUD database of a shop.

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)

## Information

**Project Name:** Tinkoff Backend Academy Homework 1 - Simple In-Memory Store Database

**Description:**
This project is a simple CRUD (Create, Read, Update, Delete) in-memory database for a store, implemented as a console application. It allows users to manage basic information about products, including SKU, name, price, and quantity. The application is designed to run in a command-line interface, waiting for user commands and executing them accordingly.

**Technologies Used:**

- **Programming Language:** Java 17
- **Development Tools:** IntelliJ IDEA
- **Version Control:** Git

**Key Features:**

- **Create Product:** Add new products with a unique SKU.
- **Read Products:** Display a list of all products in a tabular format.
- **Update Product:** Modify details of an existing product by its SKU.
- **Delete Product:** Remove a product from the database by its SKU.
- **Exit Command:** Terminate the application gracefully.

**Implementation Details:**

- **Data Storage:** All product information is stored in memory (no external databases are used).
- **User Input:** Commands are read from the console input.
- **Validation:** Ensures SKU is unique and consists only of digits and uppercase Latin letters.

**Usage Instructions:**

1. **Start the application** by running the Java program.
2. **Enter commands** as described in the implementation section.
3. **Follow the command structure** to perform various operations.
4. **Exit the program** by typing `exit`.

**Project Goals:**

- **Learn and implement CRUD operations** in a console application.
- **Understand and handle in-memory data storage** effectively.
- **Practice input validation** and error handling in Java.

## Part 1. Implementation

Simple CRUD database of a shop:
Write a console program that stores and retrieves basic information about products: SKU, name, price, and quantity.

Store data in memory.

The program waits for the user to enter a command in the console.

After executing one command, it waits for the next one.
If the user enters `exit`, the program terminates.

The user can enter 5 commands:

1) `create $SKU $name $price $quantity`
    - add a new product

2) `read`
    - Display a list of all previously added products
      (all information in tabular form)

3) `update $SKU $name $price $quantity`
    - update information about a product with the specified SKU

4) `delete $SKU`
    - delete the specified product from the database

5) `exit`
    - terminate the program

Mandatory requirements:

- The SKU must be unique. Only one product with the same SKU can be added.
- The SKU can only contain digits and uppercase Latin letters.

Optional:

- The name can consist of multiple words (separated by spaces) in quotes.
