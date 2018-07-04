package com.nipponit.manojm.smartplanner10;

/**
 * Created by manojm on 10/16/2017.
 */

public class CustomerClass {
    private String ccode_;
    private String cname_;
    private String ccity_;

    public CustomerClass(String ccode, String cname, String ccity){
        super();
        this.setCcode_(ccode);
        this.setCname_(cname);
        this.setCcity_(ccity);
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
}
