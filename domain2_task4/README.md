# 🚀 TaskFlow API

TaskFlow API is a production-ready RESTful web service built using Spring Boot. It provides secure CRUD operations for task management, integrates MySQL for data persistence, implements validation and exception handling, supports HATEOAS for API discoverability, and includes Swagger documentation for easy testing and exploration.

---

## 📌 Features

- ✅ Create, Read, Update, and Delete Tasks
- ✅ RESTful API Design
- ✅ MySQL Database Integration using Spring Data JPA
- ✅ Bean Validation with Custom Error Handling
- ✅ Global Exception Management
- ✅ HATEOAS Support
- ✅ Spring Security Authentication
- ✅ Swagger/OpenAPI Documentation
- ✅ Docker Support
- ✅ Unit Testing with JUnit 5
- ✅ Postman Collection Included

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|----------|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Data JPA | Database Access Layer |
| Spring Security | Authentication & Authorization |
| Hibernate | ORM Framework |
| MySQL | Database |
| Swagger/OpenAPI | API Documentation |
| Maven | Dependency Management |
| Docker | Containerization |
| JUnit 5 | Testing Framework |

---

## 📂 Project Structure

```text
taskflow-api
│
├── src
│   ├── main
│   │   ├── java/com/productivitypro
│   │   │   ├── config
│   │   │   ├── controller
│   │   │   ├── dto
│   │   │   ├── exception
│   │   │   ├── model
│   │   │   ├── repository
│   │   │   ├── service
│   │   │   └── TaskflowApiApplication.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│       └── java/com/productivitypro
│
├── postman
│   └── TaskFlow.postman_collection.json
│
├── Dockerfile
├── pom.xml
└── README.md
