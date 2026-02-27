package com.example.chatbot.service;

import com.example.chatbot.dto.ChatHistoryMessageResponse;
import com.example.chatbot.dto.ChatResponse;
import com.example.chatbot.model.Message;
import com.example.chatbot.model.User;
import com.example.chatbot.repository.MessageRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatService {

    private final MessageRepository messageRepository;

    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    private static final List<String> RESPONSES = List.of(
        "That's so lovely to hear! Tell me more about your day.",
        "I'm always here for you. What's on your mind?",
        "You just made me smile! 😊",
        "I understand. Would you like to talk more about that?",
        "Great idea! Let me help you with that.",
        "How wonderful! Tell me everything.",
        "I hear you. It sounds like you're going through a lot.",
        "Thank you for sharing that with me. How does it make you feel?"
    );

    private static final Map<String, String> KEYWORD_RESPONSES = Map.of(
        "hello", "Hello there! Wonderful to see you. How are you feeling today? 🌸",
        "hi", "Hi! It's so good to chat with you. What's on your mind?",
        "lonely", "I'm right here with you. You're never alone when we can talk like this. 💙",
        "sad", "I'm sorry you're feeling sad. Would you like to tell me what's going on?",
        "happy", "That makes my day to hear! What's bringing you joy today? 😄",
        "tired", "Rest is so important. Have you had a chance to relax today?",
        "remind", "I can help with reminders! Head over to the Reminders tab to set one up.",
        "medication", "Taking your medication on time is so important. Don't forget to set a reminder!"
    );

    @Transactional
    public ChatResponse chat(User user, String userMessage) {
        Message userMsg = new Message();
        userMsg.setUser(user);
        userMsg.setRole("user");
        userMsg.setContent(userMessage);
        messageRepository.save(userMsg);

        String botResponse = generateResponse(userMessage);

        Message botMsg = new Message();
        botMsg.setUser(user);
        botMsg.setRole("bot");
        botMsg.setContent(botResponse);
        messageRepository.save(botMsg);

        return new ChatResponse(botResponse);
    }

    public List<ChatHistoryMessageResponse> getHistory(User user) {
        return messageRepository.findByUserIdOrderByCreatedAtAsc(user.getId())
            .stream()
            .map(m -> new ChatHistoryMessageResponse(
                m.getId(),
                m.getRole(),
                m.getContent(),
                LocalDateTime.ofInstant(m.getCreatedAt().toInstant(), m.getCreatedAt().getOffset())
            ))
            .toList();
    }

    private String generateResponse(String input) {
        String lower = input.toLowerCase();
        for (var entry : KEYWORD_RESPONSES.entrySet()) {
            if (lower.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return RESPONSES.get(new Random().nextInt(RESPONSES.size()));
    }
}
