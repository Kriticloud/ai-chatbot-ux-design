package com.example.chatbot.dto;

public class TrainResponse {

    private final String status;
    private final String learnedQuestion;

    public TrainResponse(String status, String learnedQuestion) {
        this.status = status;
        this.learnedQuestion = learnedQuestion;
    }

    public String getStatus() {
        return status;
    }

    public String getLearnedQuestion() {
        return learnedQuestion;
    }
}
