package com.example.chatbot.controller;

import com.example.chatbot.model.User;
import com.example.chatbot.service.ReminderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping
    public ResponseEntity<List<ReminderService.ReminderDto>> list(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reminderService.getReminders(user));
    }

    @PostMapping
    public ResponseEntity<ReminderService.ReminderDto> create(
        @AuthenticationPrincipal User user,
        @Valid @RequestBody CreateRequest req
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(reminderService.createReminder(user, new ReminderService.CreateReminderRequest(req.title(), req.time())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderService.ReminderDto> update(
        @AuthenticationPrincipal User user,
        @PathVariable Long id,
        @RequestBody UpdateRequest req
    ) {
        return ResponseEntity.ok(
            reminderService.updateReminder(user, id, new ReminderService.UpdateReminderRequest(req.title(), req.time(), req.done()))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal User user, @PathVariable Long id) {
        reminderService.deleteReminder(user, id);
        return ResponseEntity.noContent().build();
    }

    record CreateRequest(@NotBlank String title, String time) {}
    record UpdateRequest(String title, String time, Boolean done) {}
}
