package com.nipponit.manojm.smartplanner10;

/**
 * Created by manojm on 10/25/2017.
 */

public class WeekClass {
    private String weekno_;
    private String target_;
    private String sale_;

    public WeekClass (String week,String target,String sale){
        super();
        this.setWeekno_(week);
        this.setTarget_(target);
        this.setSale_(sale);
    }


    public String getWeekno_(){
        return weekno_;
    }

    public void setWeekno_(String weekno_){
        this.weekno_ = weekno_;
    }

    public String getTarget_(){
        return target_;
    }
    public void setTarget_(String target_){
        this.target_ = target_;
    }

    public String getSale_(){
        return sale_;
    }
    public void setSale_(String sale_){
        this.sale_ = sale_;
    }
}
