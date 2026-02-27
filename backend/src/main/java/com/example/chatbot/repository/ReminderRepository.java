package com.example.chatbot.repository;

import com.example.chatbot.model.Reminder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findAllByOrderByReminderTimeAsc();
}
