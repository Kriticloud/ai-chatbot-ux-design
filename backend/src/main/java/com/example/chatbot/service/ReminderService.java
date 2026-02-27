package com.example.chatbot.service;

import com.example.chatbot.dto.reminder.ReminderRequest;
import com.example.chatbot.model.Reminder;
import com.example.chatbot.repository.ReminderRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public Reminder create(ReminderRequest request) {
        Reminder reminder = new Reminder();
        reminder.setTitle(request.getTitle().trim());
        reminder.setReminderTime(request.getReminderTime());
        reminder.setCompleted(request.isCompleted());
        return reminderRepository.save(reminder);
    }

    public List<Reminder> list() {
        return reminderRepository.findAllByOrderByReminderTimeAsc();
    }

    public Reminder update(Long id, ReminderRequest request) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reminder not found"));
        reminder.setTitle(request.getTitle().trim());
        reminder.setReminderTime(request.getReminderTime());
        reminder.setCompleted(request.isCompleted());
        return reminderRepository.save(reminder);
    }
}
