package com.shariful.generalknowledge;

public class User2 {
    private String mobile, name, tempScore, view;
    private int score;



    public User2(String mobile, String name, int score, String tempScore, String view) {
        this.mobile = mobile;
        this.name = name;
        this.score = score;
        this.tempScore = tempScore;
        this.view = view;
    }

    public User2() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTempScore() {
        return tempScore;
    }

    public void setTempScore(String tempScore) {
        this.tempScore = tempScore;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
