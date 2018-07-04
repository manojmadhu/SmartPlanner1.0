package com.nipponit.manojm.smartplanner10.Achievement.CustomerFigure;

/**
 * Created by manojm on 10/16/2017.
 */

public class CustomerAchievementClass_target {

    private String ccode_;
    private String cname_;
    private String ccity_;
    private String target_;

    public CustomerAchievementClass_target(String ccode, String cname, String ccity, String target){
        super();
        this.setCcode_(ccode);
        this.setCname_(cname);
        this.setCcity_(ccity);
        this.setTarget_(target);
    }

    public String getCcode_() {
        return ccode_;
    }

    public void setCcode_(String ccode_) {
        this.ccode_ = ccode_;
    }

    public String getCname_() {
        return cname_;
    }

    public void setCname_(String cname_) {
        this.cname_ = cname_;
    }

    public String getCcity_() {
        return ccity_;
    }

    public void setCcity_(String ccity_) {
        this.ccity_ = ccity_;
    }

    public String getTarget_() {
        return target_;
    }
    public void setTarget_(String target) {
        this.target_ = target;
    }
}
