package com.nipponit.manojm.smartplanner10.Achievement;

/**
 * Created by manojm on 11/13/2017.
 */

public class subweekclass {
    private String week_;
    private String target_;
    private String sale_;

    public subweekclass (String week,String target, String sale){
        super();
        this.setWeek_(week);
        this.setTarget_(target);
        this.setSale_(sale);
    }

    public String getWeek_() {
        return week_;
    }

    public void setWeek_(String week_) {
        this.week_ = week_;
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
