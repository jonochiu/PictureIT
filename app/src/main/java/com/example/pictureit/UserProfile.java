package com.example.pictureit;

public class UserProfile {
    String points;
    String userId;
    public UserProfile(String userId, String points){
        this.userId = userId;
        this.points = points;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
