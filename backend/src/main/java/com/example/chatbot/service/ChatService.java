package com.example.chatbot.service;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final Map<String, String> learnedResponses = new ConcurrentHashMap<>();

    public String getReply(String message) {
        String normalized = normalize(message);

        String learned = learnedResponses.get(normalized);
        if (learned != null) {
            return learned;
        }

        if (normalized.contains("hello") || normalized.contains("hi")) {
            return "Hello! I'm your companion assistant. How are you feeling today?";
        }

        if (normalized.contains("medicine") || normalized.contains("medication")) {
            return "I can help you set a medicine reminder. Tell me the medicine name and time.";
        }

        if (normalized.contains("weather")) {
            return "I can share weather updates. Tell me your city and I will help you check it.";
        }

        if (normalized.contains("help") || normalized.contains("emergency")) {
            return "If this is urgent, please contact your emergency services or trusted contact immediately.";
        }

        return "Thank you for sharing. I am here to listen and help with reminders, health tips, and daily support.";
    }

    public String train(String question, String answer) {
        String normalizedQuestion = normalize(question);
        learnedResponses.put(normalizedQuestion, answer.trim());
        return normalizedQuestion;
    }

    private String normalize(String input) {
        return input.trim().toLowerCase(Locale.ROOT);
    }
}
