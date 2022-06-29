# Illimity Code Challenge

Creates a Simple Spring Boot project to manage Customers and Movements through REST API's and MongoDB.

# Requirements

Java > 1.8 is mandatory to run the project. If you want also to check and debug the code, you can use an IDE like IntelliJ
(recommended) or Eclipse.

# Usage ℹ️

If you want to just execute the project, open a terminal in your installation folder and type:

```sh
java -jar illimitycodechallenge.jar
```

then if you use Postman, you may import `IllimityChallenge.postman_collection.json` to import the collection.

First you need to create Customers and Movements to test the *login* and *movements/paginated* APIs. You can simply
call the *init* API to populate the database with 2 customers (one ACTIVE and one BLOCKED) and 11 movements

if you don't have or don't want to use postman:
* The URL to call for customers: `http://localhost:8090/customers`
* The URL to call for movemets: `http://localhost:8090/movements`
* Params: `type` (has to be only GET or POST)
* Optional body: you can put whatever you want

# Docs 📚

You can find the detailed documentation in `Documentazione.pdf` and the OpenAPI 3.0 Docs at `http://localhost:8090/swagger-ui/index.html`.
