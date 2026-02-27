package com.example.chatbot.dto;

import javax.validation.constraints.NotBlank;

public class TrainRequest {

    @NotBlank(message = "question is required")
    private String question;

    @NotBlank(message = "answer is required")
    private String answer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
