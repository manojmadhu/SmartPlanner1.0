package com.nipponit.manojm.smartplanner10.Achievement.CategoryFigure;

/**
 * Created by manojm on 10/19/2017.
 */

public class CategoryAchievementClass {
    private String categoryID_;
    private String category_;
    private String target_;
    private String sale_;
    private String precentage_;

    public CategoryAchievementClass(String catID, String category, String target, String sale,String precentage){
        super();
        this.setCategoryID_(catID);
        this.setCategory_(category);
        this.setTarget_(target);
        this.setSale_(sale);
        this.setPrecentage_(precentage);
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

    public String getSale_(){
        return sale_;
    }
    public void setSale_(String sale_){
        this.sale_ = sale_;
    }


    public String getPrecentage_() {
        return precentage_;
    }

    public void setPrecentage_(String precentage_) {
        this.precentage_ = precentage_;
    }
}
