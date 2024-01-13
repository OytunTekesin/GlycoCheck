package com.oytuntekesin.authenticationapp.dto;

import java.util.Date;

public class Nutrition {
    private String NUTRITION_ID;
    private String NUTRITION_NAME;
    private String NUTRITION_CAL;
    private String NUTRITION_DURATION;
    private String NUTRITION_DIETICIAN;

    public String getNUTRITION_ID() {
        return NUTRITION_ID;
    }

    public void setNUTRITION_ID(String NUTRITION_ID) {
        this.NUTRITION_ID = NUTRITION_ID;
    }

    public String getNUTRITION_NAME() {
        return NUTRITION_NAME;
    }

    public void setNUTRITION_NAME(String NUTRITION_NAME) {
        this.NUTRITION_NAME = NUTRITION_NAME;
    }

    public String getNUTRITION_CAL() {
        return NUTRITION_CAL;
    }

    public void setNUTRITION_CAL(String NUTRITION_CAL) {
        this.NUTRITION_CAL = NUTRITION_CAL;
    }

    public String getNUTRITION_DURATION() {
        return NUTRITION_DURATION;
    }

    public void setNUTRITION_DURATION(String NUTRITION_DURATION) {
        this.NUTRITION_DURATION = NUTRITION_DURATION;
    }

    public String getNUTRITION_DIETICIAN() {
        return NUTRITION_DIETICIAN;
    }

    public void setNUTRITION_DIETICIAN(String NUTRITION_DIETICIAN) {
        this.NUTRITION_DIETICIAN = NUTRITION_DIETICIAN;
    }
}
