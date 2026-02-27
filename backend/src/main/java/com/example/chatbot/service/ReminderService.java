package com.example.chatbot.service;

import com.example.chatbot.model.Reminder;
import com.example.chatbot.model.User;
import com.example.chatbot.repository.ReminderRepository;
import com.example.chatbot.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    public ReminderService(ReminderRepository reminderRepository, UserRepository userRepository) {
        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
    }

    public List<ReminderDto> getReminders(User user) {
        User resolved = ensureUser(user);
        return reminderRepository.findByUserIdOrderByCreatedAtDesc(resolved.getId())
            .stream().map(ReminderDto::from).toList();
    }

    @Transactional
    public ReminderDto createReminder(User user, CreateReminderRequest req) {
        User resolved = ensureUser(user);
        Reminder reminder = new Reminder();
        reminder.setUser(resolved);
        reminder.setTitle(req.title());
        reminder.setReminderTime(req.time() != null && !req.time().isBlank() ? LocalTime.parse(req.time()) : null);
        reminder.setDone(false);
        return ReminderDto.from(reminderRepository.save(reminder));
    }

    @Transactional
    public ReminderDto updateReminder(User user, Long id, UpdateReminderRequest req) {
        User resolved = ensureUser(user);
        Reminder reminder = reminderRepository.findByIdAndUserId(id, resolved.getId())
            .orElseThrow(() -> new EntityNotFoundException("Reminder not found"));
        if (req.title() != null) {
            reminder.setTitle(req.title());
        }
        if (req.time() != null) {
            reminder.setReminderTime(req.time().isBlank() ? null : LocalTime.parse(req.time()));
        }
        if (req.done() != null) {
            reminder.setDone(req.done());
        }
        return ReminderDto.from(reminderRepository.save(reminder));
    }

    @Transactional
    public void deleteReminder(User user, Long id) {
        User resolved = ensureUser(user);
        if (reminderRepository.findByIdAndUserId(id, resolved.getId()).isEmpty()) {
            throw new EntityNotFoundException("Reminder not found");
        }
        reminderRepository.deleteByIdAndUserId(id, resolved.getId());
    }

    private User ensureUser(User user) {
        if (user != null && user.getId() != null) {
            return user;
        }
        return userRepository.findByEmail("demo@local")
            .orElseGet(() -> {
                User demo = new User();
                demo.setName("Demo User");
                demo.setEmail("demo@local");
                demo.setPassword("demo123");
                demo.setRole("USER");
                return userRepository.save(demo);
            });
    }

    public record CreateReminderRequest(String title, String time) {}
    public record UpdateReminderRequest(String title, String time, Boolean done) {}

    public record ReminderDto(Long id, String title, String time, boolean done) {
        static ReminderDto from(Reminder r) {
            return new ReminderDto(
                r.getId(), r.getTitle(),
                r.getReminderTime() != null ? r.getReminderTime().toString() : null,
                Boolean.TRUE.equals(r.getDone())
            );
        }
    }
}
