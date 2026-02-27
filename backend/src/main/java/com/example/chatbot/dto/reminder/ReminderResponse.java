package com.example.chatbot.dto.reminder;

import java.time.LocalDateTime;

public class ReminderResponse {

    private final Long id;
    private final String title;
    private final LocalDateTime reminderTime;
    private final boolean completed;

    public ReminderResponse(Long id, String title, LocalDateTime reminderTime, boolean completed) {
        this.id = id;
        this.title = title;
        this.reminderTime = reminderTime;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public boolean isCompleted() {
        return completed;
    }
}
