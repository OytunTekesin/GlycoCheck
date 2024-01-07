package com.oytuntekesin.authenticationapp.dto;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.oytuntekesin.authenticationapp.R;

public class ExerciseHistory {
    private String EXERCISE_HISTORY_ID;
    private String EXERCISE_ID;
    private String EXERCISE_NAME;
    private String EXERCISE_DURATION;
    private String EXERCISE_CALORIES;
    private String EXERCISE_DESCR;
    private String EXERCISE_DATETIME;
    private String USER_ID;

    public String getEXERCISE_HISTORY_ID() {
        return EXERCISE_HISTORY_ID;
    }

    public void setEXERCISE_HISTORY_ID(String EXERCISE_HISTORY_ID) {
        this.EXERCISE_HISTORY_ID = EXERCISE_HISTORY_ID;
    }

    public String getEXERCISE_ID() {
        return EXERCISE_ID;
    }

    public void setEXERCISE_ID(String EXERCISE_ID) {
        this.EXERCISE_ID = EXERCISE_ID;
    }

    public String getEXERCISE_NAME() {
        return EXERCISE_NAME;
    }

    public void setEXERCISE_NAME(String EXERCISE_NAME) {
        this.EXERCISE_NAME = EXERCISE_NAME;
    }

    public String getEXERCISE_DURATION() {
        return EXERCISE_DURATION;
    }

    public void setEXERCISE_DURATION(String EXERCISE_DURATION) {
        this.EXERCISE_DURATION = EXERCISE_DURATION;
    }

    public String getEXERCISE_CALORIES() {
        return EXERCISE_CALORIES;
    }

    public void setEXERCISE_CALORIES(String EXERCISE_CALORIES) {
        this.EXERCISE_CALORIES = EXERCISE_CALORIES;
    }

    public String getEXERCISE_DESCR() {
        return EXERCISE_DESCR;
    }

    public void setEXERCISE_DESCR(String EXERCISE_DESCR) {
        this.EXERCISE_DESCR = EXERCISE_DESCR;
    }

    public String getEXERCISE_DATETIME() {
        return EXERCISE_DATETIME;
    }

    public void setEXERCISE_DATETIME(String EXERCISE_DATETIME) {
        this.EXERCISE_DATETIME = EXERCISE_DATETIME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }
}
