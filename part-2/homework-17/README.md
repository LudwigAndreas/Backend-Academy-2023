# Tinkoff Backend Academy homework 17

Kafka Experiment.

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)

## Information

**Project Name:** Tinkoff Backend Academy homework 17 - Kafka Experiment

**Description:**
In this homework, you need to conduct a series of experiments with Kafka. To conduct the experiment, you need to create a publisher and consumer that will send and receive data from Kafka. The data to be sent needs to be generated from the `person` and `child` tables. The `person` table contains information about people, and the `child` table contains information about the relationship between parents and children. For the `person` table, you need to create 1,000,000 records with a description in the `about_me` field of no more than 10 characters and 1,000,000 records with a description in the `about_me` field of more than 1000 characters. For the `child` table, you need to create a relationship between people from the first table 500,000 to 1 child and people from the second table 20,000 to 5 children. The data should be sent in json format of the parent with a set of children.

**Technologies Used:**

- **Programming Language:** Java 17
- **Development Tools:** IntelliJ IDEA, Maven, Docker, Kafka, docker-compose, Avro
- **Version Control:** Git

**Key Features:**

- **Data Generation** - Generate data for sending from the `person` and `child` tables.
- **Relationship between tables** - Create a relationship between people from the `person` and `child` tables.
- **Data Sending** - Send data to Kafka in json (or Avro) format of the parent with a set of children.

**Implementation Details:**

- **Publisher and Consumer** - Create a publisher and consumer to send and receive data from Kafka.

**Usage Instructions:**

- Run Kafka and create topics for the experiment.
- Run the publisher and consumer to send and receive data from Kafka.
- Conduct experiments with different parameters for sending and reading data from Kafka.

**Project Goals:**

- Learn the principles of working with Kafka.
- Learn the principles of sending and reading data from Kafka.
- Learn the principles of working with Avro.

## Part 1. Implementation

You need to create a topic in Kafka for each experiment. Add a publisher and consumer to the project.
Generate data from the peson table object

```SQL
    create table person (
    id bigint,
    name varchar,
    about_me text,
    bithdate datetime
    )
```

and tables

```SQL
    create table child (
    id bigint,
    person_id bigint,
    name varchar,
    bithdate datetime
    )
```

Fill the table with 1,000,000 records with a description in about_me of no more than 10 characters (A).
Fill the table with 1,000,000 records with a description in about_me of more than 1000 characters (B).
Create a relationship between people from (A) 500,000 to 1 child.
Create a relationship between people from (B) 20,000 to 5 children.
Send data in json format of the parent with a set of children.

Fill the table with results of the experiment.

| Data Description                                                   | Reading RPS | Sending RPS  |
|--------------------------------------------------------------------|:-----------:|:------------:|
| Sending without reading guarantee                                  |             |              |
| Sending with reading guarantee                                     |             |              |
| Batch reading without reading guarantee                            |             |              |
| Batch reading with reading guarantee                               |             |              |
| Batch reading with filter in listener and with reading guarantee   |             |              |
| Batch reading with filter in consumer and without reading guarantee|             |              |
| Sending with Avro                                                  |             |              |
