-- V2: Create messages table
CREATE TABLE messages (
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT               NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role       VARCHAR(10)          NOT NULL CHECK (role IN ('user', 'bot')),
    content    TEXT                 NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_messages_user_id ON messages(user_id);
CREATE INDEX idx_messages_created_at ON messages(created_at);
