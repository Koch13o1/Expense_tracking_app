# 💸 Shared Expense Manager (Microservices Architecture)

A full-stack backend system that allows users to manage group-based expenses — ideal for roommates, friends traveling together, or any shared budget scenario. Built using **Spring Boot**, **Kafka**, and **microservices** with **Docker**, this system tracks expenses, manages users and groups, and will soon support **settlement/balance calculations**.

---

## 🛠️ Technologies Used

| Category        | Stack |
|----------------|-------|
| Language        | Java 17 |
| Backend Framework | Spring Boot |
| Messaging Queue | Apache Kafka |
| Databases       | PostgreSQL (per service) |
| Architecture    | Microservices |
| API Style       | RESTful APIs |
| Containerization| Docker + Docker Compose |
| Dev Tools       | IntelliJ IDEA, Postman |
| Build Tool      | Maven |
| DTO Sharing     | Custom `shared-dto` Maven module |
| ORM             | Spring Data JPA |
| Dependency Injection | Spring Boot Starter |
| Others          | Lombok, SLF4J

---

## 📦 Microservices Overview

This system is split into multiple independent services:

### ✅ 1. **User Service**
- **Responsibilities**:
    - Create and manage users
    - Store user metadata (name, email)
- **Database**: PostgreSQL
- **REST APIs**:
    - `POST /users` — Add a new user
    - `GET /users/{id}` — Fetch user by ID

### ✅ 2. **Group Service**
- **Responsibilities**:
    - Create and manage groups
    - Assign users to groups
    - Publish `GroupCreatedEvent` to Kafka
- **Database**: PostgreSQL
- **Kafka Producer**: Publishes to `group-created-topic`
- **REST APIs**:
    - `POST /groups` — Create a new group with members

### ✅ 3. **Expense Service**
- **Responsibilities**:
    - Track expenses for groups
    - Consume group creation events from Kafka
    - Store expenses related to a group
- **Database**: PostgreSQL
- **Kafka Consumer**: Listens for `GroupCreatedEvent`
- **REST APIs**:
    - `POST /expenses` — Add an expense
    - `GET /expenses/{groupId}` — List expenses for a group

### ⏳ 4. **Settlement Service** *(Planned / In Progress)*
- **Responsibilities**:
    - Calculate net balances for users within a group
    - Determine how much each member owes to others
    - Will expose a `/settlements/{groupId}` API
- **Future Plans**:
    - Graph-based debt simplification algorithm
    - Optional payment integration hooks

---

## 🔄 Inter-Service Communication

| From Service | To Service | Method |
|--------------|------------|--------|
| Group Service | Expense Service | Kafka (`GroupCreatedEvent`) |
| Expense Service | Settlement Service | REST or internal Kafka (future) |

---

## 🧱 Shared DTO Module (`shared-dto`)

A custom Maven module to hold all shared data transfer objects (DTOs) between services. This ensures compatibility, decouples services, and avoids code duplication.

### ✅ Included DTOs
- `GroupCreatedEvent.java`
- `ExpenseDTO.java`
- (planned) `UserDTO.java`, `SettlementDTO.java`

---

## 🧪 Testing & Validation

Each service includes:
- Unit tests for service logic
- Integration tests for REST controllers
- Kafka topic testing (in progress)

Use **Postman** or **cURL** to interact with endpoints. A `postman_collection.json` can be added later.

---

## 🐳 Docker Setup (Coming Soon)

Each microservice will be Dockerized and orchestrated using Docker Compose:

```yaml
version: '3.8'
services:
  user-service:
    build: ./user_service
    ports:
      - 8081:8081
    depends_on:
      - user-db
  # More services...
