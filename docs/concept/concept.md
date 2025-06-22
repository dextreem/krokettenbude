# 🥐 Croqueteria – Concept

Croqueteria is a service for discovering, describing, rating, and discussing the most delicious croquettes from around the globe.

---

## 📋 Requirements

Requirements were derived from the project challenge and fictional stakeholder interviews.

### ✅ Functional Requirements

| Priority | Requirement                                             |
| -------- | ------------------------------------------------------- |
| **P1**   | Entities: `Croquette`, `Rating`, `Conversation`, `User` |
| **P1**   | Full CRUD operations exposed via a RESTful API          |
| **P1**   | User registration and login                             |
| **P1**   | Persistent storage for all entities                     |
| **P1**   | API documentation using Swagger/OpenAPI                 |
| **P2**   | Search croquettes by description text                   |
| **P2**   | Filter croquettes based on entity criteria              |
| **P2**   | Recommendation system using croquette attributes        |
| **P3**   | Frontend UI for exploring and discussing croquettes     |
| **P3**   | Request caching layer                                   |

### 🔐 Security Requirements

| Priority | Requirement                                                    |
| -------- | -------------------------------------------------------------- |
| **P1**   | Croquettes: Readable by all, modifiable by `MANAGER` users     |
| **P1**   | Ratings: Readable by all, writable by registered users         |
| **P1**   | Conversations: Readable by all, writable by registered users   |
| **P2**   | Users: `MANAGER`s can promote/demote users and delete accounts |

### 📈 Non-Functional Requirements

- Should handle \~200 requests per minute
- Modular, maintainable, and scalable architecture
- Adequate unit and integration testing coverage

---

## 🧱 Entities

Each entity includes `created_at` and `updated_at` timestamps managed by the database.

<details>
 <summary><strong>Croquette</strong></summary>

| Field       | Type    | Description                                |
| ----------- | ------- | ------------------------------------------ |
| id          | Long    | Primary identifier                         |
| country     | String  | Country of origin                          |
| name        | String  | Croquette name                             |
| description | String  | Description and ingredients                |
| crunchiness | Int     | Crunchiness rating (1–5)                   |
| spiciness   | Int     | Spiciness rating (1–5)                     |
| vegan       | Boolean | Whether the croquette is vegan             |
| form        | String  | One of: cylindric, disk, ball, oval, other |
| imageUrl?   | String  | Optional image URL                         |

</details>

<details>
 <summary><strong>Rating</strong></summary>

| Field        | Type | Description                |
| ------------ | ---- | -------------------------- |
| id           | Long | Primary identifier         |
| croquette_id | Long | Foreign key to a croquette |
| user_id      | Long | Foreign key to a user      |
| rating       | Int  | Rating value (1–5)         |

</details>

<details>
 <summary><strong>Conversation</strong></summary>

| Field        | Type   | Description                |
| ------------ | ------ | -------------------------- |
| id           | Long   | Primary identifier         |
| croquette_id | Long   | Foreign key to a croquette |
| user_id      | Long   | Foreign key to a user      |
| comment      | String | User comment               |

</details>

<details>
 <summary><strong>User</strong></summary>

| Field    | Type   | Description           |
| -------- | ------ | --------------------- |
| id       | Long   | Primary identifier    |
| email    | String | User email (login ID) |
| password | String | Hashed password       |
| role     | String | `USER` or `MANAGER`   |

</details>

---

## 🛠️ Technology Stack

- **Backend**: Kotlin + Spring Boot
- **Frontend**: React + Vite + Styled Components
- **Dev/Test DB**: H2 (in-memory)
- **Prod DB**: PostgreSQL
- **Security**: JWT + Spring Security
- **Docs**: Swagger/OpenAPI

> JPA is used to support DB-agnostic development and enable seamless test execution across environments.

### 🧪 Why Not X?

#### Next.js (Fullstack)

Good for prototyping, but lacks scalable backend patterns and API robustness required for enterprise readiness.

#### AWS Lambda (Serverless)

Scales well and is cost-effective, but adds local dev complexity and lacks mature debugging/testability tools.

---

## 🏗️ Architecture Overview

Croqueteria follows a **modular monolith** approach:

- Encourages fast iteration with clean separation of concerns
- Can be transitioned to microservices or event-driven architecture later
- Avoids the premature complexity of distributed systems

![Architecture Big Picture](img/architecure_bp.svg)

### 🔐 Access & Security

- Access via RESTful API
- Role-based access control using JWT
- Roles: `ANON`, `USER`, `MANAGER`

![Architecture Components](img/architecture_components.svg)

---

## 📡 API Endpoints

<details><summary><strong>/croquettes</strong></summary>

| Method | Endpoint | Role    | Description                       |
| ------ | -------- | ------- | --------------------------------- |
| GET    | `/{id?}` | Any     | Fetch all or a specific croquette |
| POST   | `/`      | Manager | Create a new croquette            |
| PUT    | `/{id}`  | Manager | Update a croquette                |
| DELETE | `/{id}`  | Manager | Delete a croquette                |

- Sortable: `rating`, `spiciness`, `crunchiness`, `name`
- Filterable: `mean_rating`, `vegan`, `form`, `description` (keyword)

</details>

<details><summary><strong>/ratings</strong></summary>

| Method | Endpoint | Role | Description               |
| ------ | -------- | ---- | ------------------------- |
| GET    | `/{id?}` | Any  | Fetch all or one rating   |
| POST   | `/`      | User | Create a new rating       |
| PUT    | `/{id}`  | User | Update an existing rating |
| DELETE | `/{id}`  | User | Delete a rating           |

- Filterable by: `croquette_id`

</details>

<details><summary><strong>/comments</strong></summary>

| Method | Endpoint | Role | Description              |
| ------ | -------- | ---- | ------------------------ |
| GET    | `/{id?}` | Any  | Fetch all or one comment |
| POST   | `/`      | User | Create a new comment     |
| PUT    | `/{id}`  | User | Update a comment         |
| DELETE | `/{id}`  | User | Delete a comment         |

- Filterable by: `croquette_id`

</details>

<details><summary><strong>/users</strong></summary>

| Method | Endpoint | Role         | Description                                    |
| ------ | -------- | ------------ | ---------------------------------------------- |
| GET    | `/{id?}` | User/Manager | Fetch all or a specific user (with permission) |
| POST   | `/`      | Any          | Register a new user                            |
| PUT    | `/{id}`  | Manager      | Update a user                                  |
| DELETE | `/{id}`  | Manager      | Delete a user                                  |

- Filterable by: `role`

</details>

<details><summary><strong>/recommendations</strong></summary>

| Method | Endpoint | Role | Description                                                      |
| ------ | -------- | ---- | ---------------------------------------------------------------- |
| GET    | `/`      | Any  | Get croquettes based on attribute filtering                      |
| GET    | `/text`  | Any  | Get croquettes recommended by a local LLM based on a text prompt |

- Supported filters: `spiciness`, `crunchiness`, `vegan`, `form`

</details>

---

## 🧪 Testing Strategy

A pragmatic testing approach combining **TDD** and **TLD**:

- **Integration tests**: Validate key workflows and API behavior
- **Controller tests**: Focus on request validation and response contracts
- **Unit tests**: Applied where business logic is non-trivial

---

## 📚 Documentation

- **Developer Guide**: [GitHub Repo](https://github.com/dextreem/krokettenbude)
- **API Docs**: [Swagger UI](http://localhost:8080/swagger-ui/index.html)

---

## 🎨 UI Sketch

The frontend consists of 5 main views:

1. **Login/Sign-up View**
   Simple form to authenticate the user.

2. **Croquette Overview**
   Displays all croquettes with options to search, filter, and sort.

   ![Croquettes Overview](img/frontend_overview.svg)

3. **Croquette Detail View**
   Shows full croquette details, including ratings and discussions.

   ![Croquette Details](img/frontend_details.svg)

4. **Recommendation View**
   Allows the user to enter data (text or "questionnaire-style") and returns a set of recommended croquettes.

5. **Croquette Creation**
   "Questionnaire-style" view to easily create new croquettes.
