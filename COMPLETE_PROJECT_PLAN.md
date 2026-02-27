# Complete Project Plan: Elderly Companion Chatbot

This plan turns your current repo into a production-ready project in small, realistic phases.

## 1) Define MVP Scope (Week 1)

Focus on shipping a **small useful version** first.

### MVP features
- Chat interface (send/receive text)
- Backend chat endpoint integration (`POST /api/chat`)
- Reminder creation (title + time)
- Reminder list (upcoming + completed)
- Emergency help flow (keywords + guidance message)

### MVP non-functional goals
- API response < 1 second locally
- Mobile-friendly UI
- Basic accessibility (font size, contrast, keyboard)

## 2) Build Frontend App (Weeks 1-2)

Use Vue 3 + Vite + Pinia.

### Suggested pages
- `/chat` main conversation view
- `/reminders` reminder management
- `/settings` profile, trusted contact, text size toggle

### Components to build first
- `ChatWindow`
- `MessageBubble`
- `ChatComposer`
- `QuickReplyChips`
- `ReminderCard`
- `StatusBanner`

### Integration tasks
- API service layer with Axios/fetch
- Loading, error, retry states
- Env config for backend URL

## 3) Add Persistence Layer (Weeks 2-3)

Add PostgreSQL and JPA entities.

### Tables
- `users`
- `conversations`
- `messages`
- `reminders`
- `emergency_contacts`

### Backend changes
- Add `spring-boot-starter-data-jpa` and PostgreSQL driver
- Create repositories/services/controllers for reminders and messages
- Add DB migrations with Flyway or Liquibase

## 4) Improve Chat Intelligence Safely (Weeks 3-4)

Start with deterministic rules, then add LLM provider.

### Safety-first approach
- Intent router: greeting / reminder / health info / emergency
- Safety filters for harmful or unsafe outputs
- Emergency intent override response
- Prompt templates with strict response boundaries

## 5) Authentication + User Profiles (Week 4)

- Add sign-in (email/password or OTP)
- Store trusted emergency contacts
- Basic profile preferences (text size, tone)
- Protect private endpoints with Spring Security + JWT

## 6) Quality and Testing (Continuous)

### Backend
- Unit tests: services and validators
- Integration tests: controller + repository paths
- API contract tests for JSON schema consistency

### Frontend
- Unit tests for components (Vitest)
- E2E happy paths (Playwright/Cypress)
- Accessibility checks (axe)

## 7) Deploy and Operate (Week 5)

### Deployment target
- Backend: Render/Railway/AWS
- Frontend: Vercel/Netlify
- Database: managed PostgreSQL

### Minimum ops checklist
- Health endpoint monitoring
- Structured logs
- Error tracking
- `.env` secrets management
- Backup strategy for reminders/user data

## 8) Milestone Checklist

- [ ] Frontend scaffolded and connected to backend
- [ ] Reminder CRUD complete
- [ ] PostgreSQL integrated with migrations
- [ ] Basic authentication shipped
- [ ] Emergency flow validated
- [ ] Tests passing in CI
- [ ] App deployed to public URL

## 9) What to build next (immediately)

If you want fastest progress, do these next three tasks now:
1. Scaffold Vue app and build `ChatWindow + Composer`.
2. Add backend reminder endpoints (`POST/GET/PATCH /api/reminders`).
3. Create PostgreSQL schema + Flyway migration V1.
