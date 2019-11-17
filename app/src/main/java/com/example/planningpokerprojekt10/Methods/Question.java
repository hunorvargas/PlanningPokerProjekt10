package com.example.planningpokerprojekt10.Methods;

public class Question {

    private String question;
    private String questionDesc;

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


    @Override
    public String toString() {
        return "Question{" +
                ", question='" + question + '\'' +
                ", questionInf='" + questionDesc + '\'' +
                '}';
    }
}
