package com.nipponit.manojm.smartplanner10.Achievement;

/**
 * Created by manojm on 11/13/2017.
 */

public class subdayclass {
    private String day_;
    private String target_;
    private String sale_;

    public subdayclass (String day,String target, String sale){
        super();
        this.setDay_(day);
        this.setTarget_(target);
        this.setSale_(sale);
    }

    public String getDay_() {
        return day_;
    }

    public void setDay_(String day_) {
        this.day_ = day_;
    }

    public String getTarget_() {
        return target_;
    }

    public void setTarget_(String target_) {
        this.target_ = target_;
    }

    public String getSale_() {
        return sale_;
    }

    public void setSale_(String sale_) {
        this.sale_ = sale_;
    }
}
