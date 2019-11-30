package com.example.planningpokerprojekt10.Objects;

import java.util.ArrayList;

public class Admin {
    private  String adminName;
    ArrayList<String> groupIDs = new ArrayList<>();

    public ArrayList<String> getGroupIDs() {
        return groupIDs;
    }

    public void setGroupIDs(ArrayList<String> groupIDs) {
        this.groupIDs = groupIDs;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
