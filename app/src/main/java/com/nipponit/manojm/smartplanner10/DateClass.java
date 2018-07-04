package com.nipponit.manojm.smartplanner10;

/**
 * Created by manojm on 10/25/2017.
 */

public class DateClass {
    private String date_;
    private String target_;
    private String assign_;
    private String day_;

    public DateClass(String date,String day, String target, String assign){
        super();
        this.setDate_(date);
        this.setTarget_(target);
        this.setAssign_(assign);
        this.setDay_(day);
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

    public String getDay_() {
        return day_;
    }

    public void setDay_(String day_) {
        this.day_ = day_;
    }
}
