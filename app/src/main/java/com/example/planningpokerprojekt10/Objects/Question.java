package com.example.planningpokerprojekt10.Objects;

import java.util.ArrayList;

public class Question {

    private String question;
    private String ID;
    private String questionDesc;
    private String questionVisibility;
    private String questionTime;
    ArrayList<User> users = new ArrayList<>();

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionInf) {
        this.questionDesc = questionInf;
    }

    public String getQuestionVisibility() {
        return questionVisibility;
    }

    public void setQuestionVisibility(String questionVisibility) {
        this.questionVisibility = questionVisibility;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                ", questionVisibility='" + questionVisibility + '\'' +
                ", questionTime='" + questionTime + '\'' +
                '}';
    }
}
