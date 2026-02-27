package com.example.chatbot.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void statusEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    void actuatorHealthEndpointReturnsUp() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void chatEndpointReturnsReply() throws Exception {
        mockMvc.perform(post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"hello\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty());
    }

    @Test
    void trainEndpointLearnsAnswer() throws Exception {
        mockMvc.perform(post("/api/train")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"question\":\"what is your name\",\"answer\":\"I am Sathi bot\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("learned"))
                .andExpect(jsonPath("$.learnedQuestion").value("what is your name"));

        mockMvc.perform(post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"what is your name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("I am Sathi bot"));
    }

    @Test
    void trainEndpointRejectsBlankQuestion() throws Exception {
        mockMvc.perform(post("/api/train")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"question\":\"\",\"answer\":\"hello\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void chatEndpointRejectsBlankMessageWithFriendlyErrorBody() throws Exception {
        mockMvc.perform(post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"))
                .andExpect(jsonPath("$.details[0]").value("message: message is required"));
    }
}
