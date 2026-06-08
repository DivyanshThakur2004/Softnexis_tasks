# Persistent Contact Manager

## Overview

A Java Servlet + JSP application upgraded with JDBC and PostgreSQL for persistent contact management.

## Features

- JDBC CRUD Operations
- DAO Pattern
- HikariCP Connection Pool
- Search Contacts
- Prepared Statements
- Email Duplicate Detection
- Database Validation
- Persistent Storage

## Technologies

- Java
- JSP
- JSTL
- JDBC
- PostgreSQL
- HikariCP
- Apache Tomcat

## Database Setup

Run:

```sql
schema.sql
```

before starting the application.

## Deployment

1. Configure PostgreSQL.
2. Create database:

```sql
CREATE DATABASE contactdb;
```

3. Execute schema.
4. Configure context.xml.
5. Deploy WAR to Tomcat.
6. Open:

http://localhost:8080/ContactManager/contacts

## Project Structure

- model
- dao
- servlet
- util
- jsp
- META-INF

## Performance Features

- Connection Pooling
- Prepared Statements
- Search Indexes
- SQL Injection Protection
