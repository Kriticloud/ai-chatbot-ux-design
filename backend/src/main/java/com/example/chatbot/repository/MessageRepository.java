package com.example.chatbot.repository;

import com.example.chatbot.model.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserIdOrderByCreatedAtAsc(Long userId);
}
