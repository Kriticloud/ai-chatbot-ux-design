package com.example.chatbot.controller;

import com.example.chatbot.model.User;
import com.example.chatbot.service.ChatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(Map.of("status", "UP", "message", "Companion chatbot is running 🌿"));
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatService.ChatResponse> chat(
        @AuthenticationPrincipal User user,
        @Valid @RequestBody ChatRequest req
    ) {
        return ResponseEntity.ok(chatService.chat(user, req.message()));
    }

    @GetMapping("/chat/history")
    public ResponseEntity<List<ChatService.MessageDto>> history(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(chatService.getHistory(user));
    }

    record ChatRequest(@NotBlank String message) {}
}
