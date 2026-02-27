package com.example.chatbot.service;

import com.example.chatbot.model.ChatMessage;
import com.example.chatbot.repository.ChatMessageRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final Map<String, String> learnedResponses = new ConcurrentHashMap<>();
    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public String getReply(String message) {
        String normalized = normalize(message);

        String learned = learnedResponses.get(normalized);
        if (learned != null) {
            saveMessage("user", message.trim());
            saveMessage("assistant", learned);
            return learned;
        }

        String response;
        if (normalized.contains("hello") || normalized.contains("hi")) {
            response = "Hello! I'm your companion assistant. How are you feeling today?";
        } else if (normalized.contains("medicine") || normalized.contains("medication")) {
            response = "I can help you set a medicine reminder. Tell me the medicine name and time.";
        } else if (normalized.contains("weather")) {
            response = "I can share weather updates. Tell me your city and I will help you check it.";
        } else if (normalized.contains("help") || normalized.contains("emergency")) {
            response = "If this is urgent, please contact your emergency services or trusted contact immediately.";
        } else {
            response = "Thank you for sharing. I am here to listen and help with reminders, health tips, and daily support.";
        }

        saveMessage("user", message.trim());
        saveMessage("assistant", response);
        return response;
    }

    public String train(String question, String answer) {
        String normalizedQuestion = normalize(question);
        learnedResponses.put(normalizedQuestion, answer.trim());
        return normalizedQuestion;
    }

    public List<ChatMessage> getRecentHistory() {
        return chatMessageRepository.findTop50ByOrderByCreatedAtDesc();
    }

    private void saveMessage(String role, String content) {
        ChatMessage message = new ChatMessage();
        message.setRole(role);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        chatMessageRepository.save(message);
    }

    private String normalize(String input) {
        return input.trim().toLowerCase(Locale.ROOT);
    }
}
