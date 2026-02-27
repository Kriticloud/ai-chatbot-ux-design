package com.example.chatbot.controller;

import com.example.chatbot.dto.ChatRequest;
import com.example.chatbot.dto.ChatResponse;
import com.example.chatbot.dto.StatusResponse;
import com.example.chatbot.dto.ChatHistoryMessageResponse;
import com.example.chatbot.model.User;
import com.example.chatbot.service.ChatService;
import jakarta.validation.Valid;
import java.util.List;
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
    public ResponseEntity<StatusResponse> status() {
        return ResponseEntity.ok(new StatusResponse("UP"));
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(
        @AuthenticationPrincipal User user,
        @Valid @RequestBody ChatRequest req
    ) {
        return ResponseEntity.ok(chatService.chat(user, req.getMessage()));
    }

    @GetMapping("/chat/history")
    public ResponseEntity<List<ChatHistoryMessageResponse>> history(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(chatService.getHistory(user));
    }
}
