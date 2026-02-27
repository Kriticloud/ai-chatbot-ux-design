-- V3: Create reminders table
CREATE TABLE reminders (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT               NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title       VARCHAR(255)         NOT NULL,
    reminder_time TIME,
    done        BOOLEAN              NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_reminders_user_id ON reminders(user_id);
CREATE INDEX idx_reminders_done    ON reminders(done);
