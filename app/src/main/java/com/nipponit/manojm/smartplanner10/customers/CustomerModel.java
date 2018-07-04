package com.nipponit.manojm.smartplanner10.customers;

/**
 * Created by manojm on 10/16/2017.
 */

public class CustomerModel {
    private int id_;
    private boolean isSelected;
    private String ccode_;
    private String cname_;
    private String ccity_;

    public CustomerModel(int id,boolean isSelected,String ccode, String cname, String ccity){
        super();
        this.setId_(id);
        this.setSelected(isSelected);
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }
}
