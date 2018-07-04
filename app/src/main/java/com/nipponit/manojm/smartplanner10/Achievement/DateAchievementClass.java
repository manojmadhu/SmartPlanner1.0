package com.nipponit.manojm.smartplanner10.Achievement;

/**
 * Created by manojm on 10/25/2017.
 */

public class DateAchievementClass {
    private String date_;
    private String target_;
    private String assign_;
    private String precentage_;
    private String day_;

    public DateAchievementClass(String date,String day, String target, String assign,String precentage){
        super();
        this.setDate_(date);
        this.setDay_(day);
        this.setTarget_(target);
        this.setAssign_(assign);
        this.setPrecentage_(precentage);
    }


    public String getDate_() {
        return date_;
    }

    public void setDate_(String date_) {
        this.date_ = date_;
    }

    public String getTarget_() {
        return target_;
    }

    public void setTarget_(String target_) {
        this.target_ = target_;
    }

    public String getAssign_() {
        return assign_;
    }

    public void setAssign_(String assign_) {
        this.assign_ = assign_;
    }

    public String getPrecentage_() {
        return precentage_;
    }

    public void setPrecentage_(String precentage_) {
        this.precentage_ = precentage_;
    }

    public String getDay_() {
        return day_;
    }

    public void setDay_(String day_) {
        this.day_ = day_;
    }
}
