# Finance Backend API

A simple but structured backend application built using Spring Boot to manage financial records like income and expenses. The goal of this project was to understand how real-world backend systems are designed with authentication, role-based access, and clean architecture.

---

## What this project does

This API allows users to:

* Register and log in securely
* Create and manage financial records
* View summarized financial data through a dashboard
* Restrict actions based on user roles

It follows a layered architecture and uses JWT for authentication.

---

## Tech Stack

* Java (Spring Boot)
* Spring Security (JWT-based authentication)
* Spring Data JPA (Hibernate)
* MySQL
* Lombok

---

## Project Structure

The project is divided into clear layers:

* **Controller** → Handles HTTP requests
* **Service** → Contains business logic
* **Repository** → Database operations
* **Entity** → Database models
* **DTO** → Request/response objects
* **Security** → JWT and authentication logic

---

## Roles and Access

There are three roles in the system:

* **ADMIN**
* **ANALYST**
* **VIEWER**

Permissions are defined as:

* ADMIN → Full access (create, view, delete)
* ANALYST → Can view records
* VIEWER → Can only access dashboard

---

## Authentication Flow

1. User registers with email and password
2. Logs in using credentials
3. Receives a JWT token
4. Sends the token in request headers

Example:

```
Authorization: Bearer <token>
```

---

## API Endpoints

### Auth APIs

**Register**

```
POST /api/auth/register
```

**Login**

```
POST /api/auth/login
```

---

### Financial Records

**Create Record (ADMIN only)**

```
POST /api/records
```

**Get Records (ADMIN, ANALYST)**

```
GET /api/records?page=0&size=5
```

**Delete Record (ADMIN)**

```
DELETE /api/records/{id}
```

---

### Dashboard

**Get Summary**

```
GET /api/dashboard
```

Returns total income, expense, and balance.

---

## Sample Request (Create Record)

```json
{
  "amount": 5000,
  "type": "INCOME",
  "category": "SALARY",
  "date": "2026-04-05",
  "description": "Monthly salary"
}
```








## Things I Focused On

* Keeping the code clean and readable
* Proper separation of concerns
* Implementing role-based security
* Handling basic validation and errors
* Making the project easy to understand and extend






