package com.example.chatbot.dto;

import java.time.LocalDateTime;

public class ChatHistoryMessageResponse {

    private final Long id;
    private final String role;
    private final String content;
    private final LocalDateTime createdAt;

    public ChatHistoryMessageResponse(Long id, String role, String content, LocalDateTime createdAt) {
        this.id = id;
        this.role = role;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
