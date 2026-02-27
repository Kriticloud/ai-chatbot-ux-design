# Elderly Companion Chatbot Project Overview

## Project Goal
Build an elderly-focused companion chatbot that provides emotional support, simple information, reminders, and emergency-oriented guidance through a highly accessible interface.

## Current Implementation Status
- ✅ Product and UX planning documents completed.
- ✅ Backend starter API implemented with chat and status endpoints.
- ✅ Automated backend tests added.
- ⏳ Frontend implementation pending.
- ⏳ Database and authentication integration pending.

## Technology Stack
- **Frontend (planned):** Vue.js
- **Backend (implemented):** Java + Spring Boot
- **Database (planned):** PostgreSQL
- **Deployment (recommended):** Render, Railway, AWS, or Heroku alternatives

## Core Features
1. **Conversational interaction** for companionship and guidance.
2. **Reminders and alerts** for daily routines.
3. **Information support** (weather, routine wellness prompts, FAQs).
4. **Emergency escalation messaging** with safe guidance.
5. **Accessible interface patterns** tailored for older adults.

## API Scope (Current)
- `GET /api/status`: Service health check.
- `POST /api/chat`: Accepts `{ "message": "..." }` and returns chatbot response.

## Next Milestones
1. Build Vue frontend chat interface connected to backend APIs.
2. Add reminder persistence (PostgreSQL + JPA).
3. Add user profile and trusted emergency contact management.
4. Integrate LLM/NLU provider behind safety and prompt policies.
5. Conduct usability sessions with elderly users and iterate.
