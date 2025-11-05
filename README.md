# 🧠 Journal App (Spring Boot)

A **Spring Boot–powered Journal Management System** that enables users to securely register, log in, and manage personal journal entries with real-time sentiment analysis and external API integrations.

The application leverages **Redis** for intelligent caching of external API data, **Kafka** for event-driven email notifications, and **Google OAuth 2.0** for seamless social authentication. It also integrates **CRON-based scheduling** for automated background tasks like weekly sentiment summaries and cache updates.

Designed with a modular architecture, it incorporates **JWT-based security**, **MongoDB persistence**, and **Swagger API documentation** to deliver a scalable, secure, and production-ready backend solution.

---

## 🚀 Features

- RESTful APIs built with **Spring Boot**
- **JWT authentication** and **Google OAuth 2.0**
- **Role-based access control** for Admin and User
- **MongoDB Atlas** for persistent data storage  
- **Redis** caching for optimized external API calls  
- **Kafka** for event-driven email notifications  
- **CRON jobs** for automated task scheduling  
- **Swagger UI** for interactive API documentation

---

## 🧱 Tech Stack

| Category | Technology |
|-----------|-------------|
| Framework | Spring Boot 3 |
| Database | MongoDB Atlas |
| Cache | Redis |
| Message Broker | Apache Kafka |
| Security | Spring Security, JWT |
| OAuth | Google OAuth 2.0 |
| Task Scheduling | Spring Scheduler (CRON Jobs) |
| Documentation | Swagger / OpenAPI |
| Build Tool | Maven |
| Language | Java 17+ |

---

## 🔑 Authentication Flow

1. User registers via `/public/signup` or logs in via `/public/login`.  
2. A **JWT token** is generated and returned to the client.  
3. For Google login, `/auth/google/callback` handles OAuth response and issues JWT.  
4. Authenticated users can access secured routes like `/journal/**` and `/user/**`.

---

## ⚡ Redis, Kafka & CRON Overview

- **Redis** caches frequently used external API data (e.g., weather info) to enhance speed and reduce API hits.  
- **Kafka** handles asynchronous events such as email notifications triggered by journal activity.  
- **CRON jobs** automate tasks like:
  - Sending weekly sentiment reports
  - Refreshing cached data periodically
  - Performing maintenance or cleanup operations

---

## 🧩 Main API Endpoints

### 🟢 Public APIs
| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/public/health-check` | GET | Check application health |
| `/public/signup` | POST | Register new user |
| `/public/login` | POST | Log in user and get JWT |
| `/auth/google/callback` | GET | Google OAuth 2.0 callback |

### 🔵 User APIs
| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/user` | GET | Get all users |
| `/user` | PUT | Update logged-in user |
| `/user/user` | DELETE | Delete logged-in user |
| `/user/get-Weather` | GET | Get weather info (cached) |

### 🟣 Journal APIs
| Endpoint | Method | Description |
|-----------|---------|-------------|
| `/journal` | GET | Get all journal entries for the logged-in user |
| `/journal` | POST | Create new journal entry |
| `/journal/id/{id}` | GET | Get journal entry by ID |
| `/journal/id/{id}` | PUT | Update existing journal entry |
| `/journal/id/{id}` | DELETE | Delete journal entry by ID |

---

## 🧪 Running the Project

### Prerequisites
- Java 17+  
- Maven 3.8+  
- MongoDB Atlas credentials  
- Redis server running on port `6379`  
- Kafka broker (local or Confluent Cloud)

### Steps
```bash
# Clone repository
git clone https://github.com/<your-username>/journal-app.git
cd journal-app

# Build the project
mvn clean install

# Run the app
mvn spring-boot:run
