package com.example.quizapp;

public class Quiz {

    private String question;
    private String option1;
    private String option2;
    private String answer;

    public Quiz(String question, String option1, String option2, String answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getAnswer() {
        return answer;
    }
}
