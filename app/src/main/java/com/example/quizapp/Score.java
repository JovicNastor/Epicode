package com.example.quizapp;

public class Score {

    public String user_id, subject, date_taken, final_score;

    public Score() {

    }

    public Score(String user_id, String subject, String date_taken, String final_score) {
        this.user_id = user_id;
        this.subject = subject;
        this.date_taken = date_taken;
        this.final_score = final_score;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate_taken() {
        return date_taken;
    }

    public String getFinal_score() {
        return final_score;
    }
}
