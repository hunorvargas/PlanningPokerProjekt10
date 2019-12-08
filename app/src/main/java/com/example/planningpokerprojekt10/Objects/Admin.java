package com.example.planningpokerprojekt10.Objects;

import java.util.ArrayList;

public class Admin {
    private  String adminName;
    ArrayList<String> groupIDs = new ArrayList<>();

    public Admin() {
    }

    public Admin(String adminName) {
        this.adminName = adminName;
    }

    public Admin(String adminName, ArrayList<String> groupIDs) {
        this.adminName = adminName;
        this.groupIDs = groupIDs;
    }

    public ArrayList<String> getGroupIDs() {
        return groupIDs;
    }


    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminName='" + adminName + '\'' +
                ", groupIDs=" + groupIDs +
                '}';
    }
}
