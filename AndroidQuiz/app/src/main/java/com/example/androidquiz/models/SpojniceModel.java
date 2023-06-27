package com.example.androidquiz.models;

import java.util.ArrayList;
import java.util.List;

public class SpojniceModel {
    List<SpojniceAnswerModel> answers = new ArrayList<>();
    String theme;
    String title;

    public List<SpojniceAnswerModel> getAnswers() {
        return answers;
    }

    public String getTheme() {
        return theme;
    }

    public String getTitle() {
        return title;
    }

    public void setAnswers(List<SpojniceAnswerModel> answers) {
        this.answers = answers;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SpojniceModel() {
    }
}

