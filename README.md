📱 Spring Social Media Blog API

Overview

This is a backend RESTful API for a micro-blogging application developed using the Spring Boot framework. The application allows users to register, log in, and manage short text-based posts (messages). Built with Spring MVC and Spring Data JPA, it showcases a standard layered architecture, separating concerns across controllers, services, and repositories.

Users can:

Register and authenticate accounts.

Post, edit, delete, and retrieve messages.

View all messages or filter messages by user.

🔧 Technologies Used

Java

Spring Boot

Spring MVC

Spring Data JPA

H2 (in-memory database)

Maven

🗃️ Database Schema

The database is auto-initialized on startup using Spring Boot configuration and a preloaded SQL script.

Account

accountId INTEGER PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255)


Message

messageId INTEGER PRIMARY KEY AUTO_INCREMENT,
postedBy INTEGER,
messageText VARCHAR(255),
timePostedEpoch LONG,
FOREIGN KEY (postedBy) REFERENCES Account(accountId)


✅ API Features
| Endpoint                         | Method | Description                         |
| -------------------------------- | ------ | ----------------------------------- |
| `/register`                      | POST   | Register a new user                 |
| `/login`                         | POST   | Authenticate an existing user       |
| `/messages`                      | POST   | Create a new message                |
| `/messages`                      | GET    | Retrieve all messages               |
| `/messages/{messageId}`          | GET    | Retrieve a message by ID            |
| `/messages/{messageId}`          | DELETE | Delete a message by ID              |
| `/messages/{messageId}`          | PATCH  | Update a message's text             |
| `/accounts/{accountId}/messages` | GET    | Get all messages by a specific user |


Business Rules

Usernames must be unique and non-empty.

Passwords must be at least 4 characters long.

Messages must be non-empty and ≤ 255 characters.

Only valid users can post messages.

🧪 Spring Framework Requirements

This project demonstrates:

Use of @RestController, @Service, and @Repository annotations.

Dependency injection via Spring’s IoC container.

Spring Data JPA with interfaces extending JpaRepository.

MVC structure for clean routing and response handling.

Application configuration via application.properties.

🚀 Getting Started

To run the application:

Clone the repo.

Run the Spring Boot application (SocialMediaApplication.java).

Use Postman or any REST client to interact with the endpoints on localhost:8080.


