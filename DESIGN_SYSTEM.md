# Design System Documentation

## Design Principles
- **Calm and reassuring:** soft contrast and predictable patterns for user confidence.
- **Readable first:** large type, generous spacing, plain language.
- **Accessible by default:** WCAG AA contrast, keyboard access, and clear focus states.
- **Low cognitive load:** limited options per screen and guided actions.

## Design Tokens

### Color tokens
- `--color-primary`: `#2F80ED`
- `--color-primary-strong`: `#1D5FBF`
- `--color-success`: `#27AE60`
- `--color-warning`: `#F2994A`
- `--color-danger`: `#EB5757`
- `--color-bg`: `#F7FAFC`
- `--color-surface`: `#FFFFFF`
- `--color-text-primary`: `#1F2937`
- `--color-text-secondary`: `#4B5563`
- `--color-border`: `#D1D5DB`

### Typography tokens
- Font family: `Inter`, `Arial`, `sans-serif`
- `--font-size-xxl`: `2rem` (32px)
- `--font-size-xl`: `1.5rem` (24px)
- `--font-size-lg`: `1.25rem` (20px)
- `--font-size-md`: `1rem` (16px)
- `--font-size-sm`: `0.875rem` (14px)

### Spacing tokens
- 4px scale: `4, 8, 12, 16, 24, 32, 48`

### Radius and elevation
- `--radius-sm`: `6px`
- `--radius-md`: `10px`
- `--radius-lg`: `14px`
- `--shadow-card`: `0 2px 8px rgba(0,0,0,0.08)`

## Component Library

### 1) Chat bubble
- Variants: user, assistant, system
- Max width: 75% desktop, 90% mobile
- Typography: `--font-size-md`

### 2) Composer
- Input with minimum height 48px
- Send button min target size 44x44
- Supports voice and quick replies (optional)

### 3) Quick-reply chips
- Single-line concise labels
- Horizontal scroll on mobile

### 4) Reminder card
- Fields: title, time, repeat badge, completion state
- Actions: edit, snooze, complete

### 5) Status banner
- Success/info/warning/error with icon + single action

## Layout Guidelines
- Content max width: 960px desktop
- Sidebar optional above 1200px
- Mobile first layout with bottom-safe padding

## Accessibility Requirements
- Minimum body text size: 16px
- Contrast ratio: at least 4.5:1 for text
- Keyboard navigation for all interactive controls
- Focus ring visible and high contrast
- All icons and buttons include accessible labels

## Voice & Tone Guidelines
- Friendly and respectful
- Avoid jargon and complex phrasing
- Always offer next-step prompts when possible
- Use empathetic fallback responses when intent is unclear
