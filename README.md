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
