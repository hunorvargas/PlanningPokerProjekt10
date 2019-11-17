package com.example.planningpokerprojekt10.Methods;

import java.util.ArrayList;

public class Session {

    private String sessionID;
    private ArrayList<Question> questions;
    private ArrayList<User> users;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionID='" + sessionID + '\'' +
                ", questions=" + questions +
                ", users=" + users +
                '}';
    }
}
