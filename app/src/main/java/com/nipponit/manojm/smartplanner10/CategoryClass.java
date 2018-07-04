package com.nipponit.manojm.smartplanner10;

/**
 * Created by manojm on 10/19/2017.
 */

public class CategoryClass {
    private String categoryID_;
    private String category_;
    private String target_;
    private String assign;

    public CategoryClass (String catID,String category,String target,String assign){
        super();
        this.setCategoryID_(catID);
        this.setCategory_(category);
        this.setTarget_(target);
        this.setAssign(assign);
    }


    public String getCategory_() {
        return category_;
    }

    public void setCategory_(String category_) {
        this.category_ = category_;
    }

    public String getTarget_() {
        return target_;
    }

    public void setTarget_(String target_) {
        this.target_ = target_;
    }

    public String getCategoryID_() {
        return categoryID_;
    }
    public void setCategoryID_(String categoryID_) {
        this.categoryID_ = categoryID_;
    }

    public String getAssign() {
        return assign;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }
}
