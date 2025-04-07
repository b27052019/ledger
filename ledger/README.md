# Project Name

    Simple Ledger project

---

# Features

- Able to view balance for particular user/userId.
- Able to add new transaction (DEPOSIT or WITHDRAWAL) for a specific user/userId.
- Able to view transaction history for a particular user/userId.
- Able to view all transaction history.

---

# Tech Stack

- Java 21
- Spring Boot
- Maven
- JUnit 5

---
# Compile

- .\mvnw compile or mvnw compile


# Build and Run

- .\mvnw spring-boot:run or mvnw spring-boot:run
- Manually run from LedgerApplication class
---

# Run Tests
- ./mvnw test or mvnw test

---

# Endpoint info 

Do note that UUID userId changes in each run as it's automatically initialized at startup.

Build and Run. Go to Postman or similar app to test endpoints.

To view all the transaction history. 
- GET http://localhost:8080/ledger/history

To view balance for particular user. 
- First run GET http://localhost:8080/ledger/history'
- Then copy userId for one of the user. (For example userid = 56977d3f-5f6a-42fb-997f-28d84d9fb7d1) Do note that userId changes in each run as it's automatically initialized.
- run GET http://localhost:8080/ledger/balance/{userid} (http://localhost:8080/ledger/balance/56977d3f-5f6a-42fb-997f-28d84d9fb7d1)

To create transaction for particular user.
- You can only do DEPOSIT or WITHDRAWAL transaction type.
- First run GET http://localhost:8080/ledger/history'
- Then copy userId for one of the user. (For example userid = 56977d3f-5f6a-42fb-997f-28d84d9fb7d1) Do note that userId changes in each run as it's automatically initialized.
- Then run POST http://localhost:8080/ledger/transaction
- On the body of the POST, choose JSON raw data. And pass
{
  "userId": {userId}, <- substitute {userId} with existing user userId (such as "56977d3f-5f6a-42fb-997f-28d84d9fb7d1")
  "type": "DEPOSIT",
  "amount": 5000.00
}

To check the transaction history for particular user
- First run GET http://localhost:8080/ledger/history'
- Then copy userId for one of the user. (For example userid = 56977d3f-5f6a-42fb-997f-28d84d9fb7d1)
- Then run http://localhost:8080/ledger/history/{userId} (http://localhost:8080/ledger/history/56977d3f-5f6a-42fb-997f-28d84d9fb7d1)
