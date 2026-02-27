package com.example.chatbot.service;

import com.example.chatbot.model.Reminder;
import com.example.chatbot.model.User;
import com.example.chatbot.repository.ReminderRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public List<ReminderDto> getReminders(User user) {
        return reminderRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
            .stream().map(ReminderDto::from).toList();
    }

    @Transactional
    public ReminderDto createReminder(User user, CreateReminderRequest req) {
        Reminder reminder = new Reminder();
        reminder.setUser(user);
        reminder.setTitle(req.title());
        reminder.setReminderTime(req.time() != null ? LocalTime.parse(req.time()) : null);
        reminder.setDone(false);
        return ReminderDto.from(reminderRepository.save(reminder));
    }

    @Transactional
    public ReminderDto updateReminder(User user, Long id, UpdateReminderRequest req) {
        Reminder reminder = reminderRepository.findByIdAndUserId(id, user.getId())
            .orElseThrow(() -> new EntityNotFoundException("Reminder not found"));
        if (req.title() != null)  reminder.setTitle(req.title());
        if (req.time()  != null)  reminder.setReminderTime(LocalTime.parse(req.time()));
        if (req.done()  != null)  reminder.setDone(req.done());
        return ReminderDto.from(reminderRepository.save(reminder));
    }

    @Transactional
    public void deleteReminder(User user, Long id) {
        if (!reminderRepository.findByIdAndUserId(id, user.getId()).isPresent()) {
            throw new EntityNotFoundException("Reminder not found");
        }
        reminderRepository.deleteByIdAndUserId(id, user.getId());
    }

    public record CreateReminderRequest(String title, String time) {}
    public record UpdateReminderRequest(String title, String time, Boolean done) {}

    public record ReminderDto(Long id, String title, String time, boolean done) {
        static ReminderDto from(Reminder r) {
            return new ReminderDto(
                r.getId(), r.getTitle(),
                r.getReminderTime() != null ? r.getReminderTime().toString() : null,
                r.getDone()
            );
        }
    }
}
