package com.example.planningpokerprojekt10.Objects;

public class User {

    private String userName,sessionId,userVote;

    public User(String userName, String userVote) {
        this.userName = userName;
        this.userVote = userVote;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserVote() {
        return userVote;
    }

    public void setUserVote(String userVote) {
        this.userVote = userVote;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", userVote='" + userVote + '\'' +
                '}';
    }
}
