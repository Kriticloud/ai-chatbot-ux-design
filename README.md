# AI Chatbot UX Design

A complete starter package for an **Elderly Companion Chatbot** project, including:
- Product and UX documentation
- Conversation and prototype guidance
- A runnable Spring Boot backend API

## Project Structure

```text
/ai-chatbot-ux-design
├── backend/                            # Spring Boot API
│   ├── src/main/java/com/example/chatbot
│   ├── src/main/resources
│   └── src/test/java/com/example/chatbot
├── frontend/                           # Browser UI
│   ├── index.html
│   ├── app.js
│   └── styles.css
├── CHATBOT_PROJECT_OVERVIEW.md
├── USER_PERSONAS_RESEARCH.md
├── CONVERSATION_FLOWS.md
├── WIREFRAMES.md
├── MOBILE_DESKTOP_MOCKUPS.md
├── DESIGN_SYSTEM.md
└── PROTOTYPE_SPECIFICATIONS.md
```

## Backend API (Implemented)

### Endpoints
- `GET /api/status` — health endpoint
- `POST /api/chat` — chatbot response endpoint
- `POST /api/train` — teach chatbot a custom question+answer pair
- `GET /api/messages` — fetch recent chat history
- `POST /api/reminders` — create reminder
- `GET /api/reminders` — list reminders
- `PATCH /api/reminders/{id}` — update reminder (mark complete)

Example request:

```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"hello"}'
```

Example response:

```json
{
  "response": "Hello! I'm your companion assistant. How are you feeling today?"
}
```

## Local Setup

### Prerequisites
- Java 11+
- Maven 3.8+

### Run backend

```bash
cd backend
mvn spring-boot:run
```

### Run frontend

In a new terminal:

```bash
cd frontend
npm start
```

Then open `http://localhost:5173`.

The frontend now includes a refreshed, accessible card-based layout optimized for readability, with dedicated panels for connection, training, reminders, and conversation.

### Run tests

```bash
cd backend
mvn test
```

## Documentation

Use these files to drive product and UX decisions:
- `CHATBOT_PROJECT_OVERVIEW.md` for scope and architecture
- `USER_PERSONAS_RESEARCH.md` for user needs and behaviors
- `CONVERSATION_FLOWS.md` for intent handling and edge cases
- `WIREFRAMES.md` and `MOBILE_DESKTOP_MOCKUPS.md` for interface direction
- `DESIGN_SYSTEM.md` for reusable visual standards
- `PROTOTYPE_SPECIFICATIONS.md` for build-ready implementation specs

## Complete-Project Roadmap

If your goal is to fully ship this project (not just a demo backend), follow `COMPLETE_PROJECT_PLAN.md` for a phase-by-phase implementation plan covering frontend, database, AI safety, auth, testing, and deployment.


## What new things are needed to make the app fully work?

To move from a backend-only starter to a working product that users can use daily, you still need:

1. **Frontend app** (Vue + chat UI) connected to `/api/chat` and `/api/status`.
2. **Database** (PostgreSQL + JPA + Flyway) for users, messages, and reminders.
3. **Reminder APIs** (`/api/reminders`) with CRUD support.
4. **Authentication** (Spring Security + JWT) for private user data.
5. **Deployment setup** (frontend hosting + backend hosting + managed DB + env secrets).

### Quick backend verification commands

```bash
# 1) Start backend
cd backend
mvn spring-boot:run

# 2) Verify app status
curl http://localhost:8080/api/status

# 3) Verify chatbot response
curl -X POST http://localhost:8080/api/chat   -H "Content-Type: application/json"   -d '{"message":"hello"}'

# 4) Verify runtime health endpoint
curl http://localhost:8080/actuator/health
```



## How to train your chatbot

This project now supports lightweight training through a custom Q&A endpoint.

### Option A: train from frontend
1. Start backend (`cd backend && mvn spring-boot:run`).
2. Start frontend (`cd frontend && npm start`).
3. Open `http://localhost:5173`.
4. In **Train chatbot (custom Q&A)**, enter a question and answer, then click **Train**.
5. Ask the same question in chat and the bot will return your trained answer.

### Option B: train with API

```bash
curl -X POST http://localhost:8080/api/train \
  -H "Content-Type: application/json" \
  -d '{"question":"what is my clinic number?","answer":"Your clinic number is +1-555-0100."}'

curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message":"what is my clinic number?"}'
```

> Note: current training is in-memory (prototype). Restarting backend clears learned Q&A.


## New fully-fledged features added

The app now includes practical product features beyond basic chat:
- **Persistent reminders** with create/list/update APIs using JPA + H2 file database.
- **Recent chat history** endpoint so conversations are not just ephemeral in the UI.
- **Frontend reminders panel** to add and complete reminders.

### Reminder API example

```bash
curl -X POST http://localhost:8080/api/reminders \
  -H "Content-Type: application/json" \
  -d '{"title":"Take medicine","reminderTime":"2026-02-27T21:30:00","completed":false}'

curl http://localhost:8080/api/reminders
```

### Data persistence note
- Local data is stored in H2 file DB (`./backend/data/chatbotdb*`).
- Restarting server keeps reminders/history unless the DB files are removed.
