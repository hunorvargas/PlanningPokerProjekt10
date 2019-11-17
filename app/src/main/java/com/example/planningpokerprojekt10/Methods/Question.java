package com.example.planningpokerprojekt10.Methods;

public class Question {
    private String questionIDl;
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

    public String getQuestionIDl() {
        return questionIDl;
    }

    public void setQuestionIDl(String questionIDl) {
        this.questionIDl = questionIDl;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionIDl='" + questionIDl + '\'' +
                ", question='" + question + '\'' +
                ", questionDesc='" + questionDesc + '\'' +
                '}';
    }
}
