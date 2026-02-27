# Prototype Specifications

## Core Feature Specifications

### Feature 1: Conversational Assistant
**Purpose:** Provide daily companionship and practical help.

**Requirements:**
- User can send a text prompt.
- System returns a contextual response in under 2 seconds (prototype target).
- Conversation history persists during active session.
- Empty messages are blocked with validation feedback.

### Feature 2: Reminder Support
**Purpose:** Help elderly users with routines (medicine, hydration, appointments).

**Requirements:**
- User can create reminder with title + date/time.
- Reminder appears in a dedicated list and in chat confirmations.
- Reminder supports complete/snooze actions.

### Feature 3: Safety & Escalation
**Purpose:** Handle urgent intents responsibly.

**Requirements:**
- Detect emergency keywords (e.g., "help", "emergency").
- Respond with emergency guidance and trusted-contact prompt.
- Log escalation event for audit in future releases.

## Interaction and Animation Guidelines

### Animation set
- Chat message entry: 150ms fade + 10px slide-up.
- Typing indicator pulse: 900ms ease-in-out loop.
- Button press: 100ms scale to 0.98.

### Motion rules
- Use subtle motion only; avoid distracting transitions.
- Respect reduced-motion settings.

## State Management Architecture

### Frontend (planned)
- Local UI state for input, typing indicator, and panel visibility.
- Centralized store for conversation list and reminders.
- Service layer for API requests and error normalization.

### Backend (implemented starter)
- Stateless REST API with request validation.
- Service class for response logic.
- Extendable for persistence and AI provider integration.

## Technical Requirements
- Backend: Java 11, Spring Boot 2.7.x, Maven
- API format: JSON over HTTP
- Validation: required `message` field for `/api/chat`
- Test coverage target: controller endpoint coverage for happy path and validation errors

## Non-Functional Requirements
- Availability target (prototype): 99% during demo hours
- Performance target: P95 API response under 500ms for local environment
- Accessibility target: WCAG 2.1 AA for final UI implementation
