package com.example.chatbot.controller;

import com.example.chatbot.dto.ChatHistoryMessageResponse;
import com.example.chatbot.dto.ChatRequest;
import com.example.chatbot.dto.ChatResponse;
import com.example.chatbot.dto.StatusResponse;
import com.example.chatbot.dto.TrainRequest;
import com.example.chatbot.dto.TrainResponse;
import com.example.chatbot.dto.reminder.ReminderRequest;
import com.example.chatbot.dto.reminder.ReminderResponse;
import com.example.chatbot.model.ChatMessage;
import com.example.chatbot.model.Reminder;
import com.example.chatbot.service.ChatService;
import com.example.chatbot.service.ReminderService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;
    private final ReminderService reminderService;

    public ChatController(ChatService chatService, ReminderService reminderService) {
        this.chatService = chatService;
        this.reminderService = reminderService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        String reply = chatService.getReply(request.getMessage());
        return ResponseEntity.ok(new ChatResponse(reply));
    }

    @PostMapping("/train")
    public ResponseEntity<TrainResponse> train(@Valid @RequestBody TrainRequest request) {
        String learnedQuestion = chatService.train(request.getQuestion(), request.getAnswer());
        return ResponseEntity.ok(new TrainResponse("learned", learnedQuestion));
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatHistoryMessageResponse>> messages() {
        List<ChatHistoryMessageResponse> messages = chatService.getRecentHistory().stream()
                .sorted(Comparator.comparing(ChatMessage::getCreatedAt))
                .map(msg -> new ChatHistoryMessageResponse(msg.getId(), msg.getRole(), msg.getContent(), msg.getCreatedAt()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/reminders")
    public ResponseEntity<ReminderResponse> createReminder(@Valid @RequestBody ReminderRequest request) {
        Reminder reminder = reminderService.create(request);
        return ResponseEntity.ok(toReminderResponse(reminder));
    }

    @GetMapping("/reminders")
    public ResponseEntity<List<ReminderResponse>> reminders() {
        List<ReminderResponse> reminders = reminderService.list().stream()
                .map(this::toReminderResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reminders);
    }

    @PatchMapping("/reminders/{id}")
    public ResponseEntity<ReminderResponse> updateReminder(@PathVariable Long id, @Valid @RequestBody ReminderRequest request) {
        Reminder reminder = reminderService.update(id, request);
        return ResponseEntity.ok(toReminderResponse(reminder));
    }

    @GetMapping("/status")
    public ResponseEntity<StatusResponse> status() {
        return ResponseEntity.ok(new StatusResponse("ok"));
    }

    private ReminderResponse toReminderResponse(Reminder reminder) {
        return new ReminderResponse(reminder.getId(), reminder.getTitle(), reminder.getReminderTime(), reminder.isCompleted());
    }
}
