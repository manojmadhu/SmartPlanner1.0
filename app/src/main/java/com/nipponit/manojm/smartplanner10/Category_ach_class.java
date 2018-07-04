package com.nipponit.manojm.smartplanner10;

/**
 * Created by manojm on 10/24/2017.
 */

public class Category_ach_class  {
    private String category_;
    private String target_;
    private String achievement_;

    public Category_ach_class(String category,String target,String achievement){
        super();
        this.setCategory_(category);
        this.setTarget_(target);
        this.setAchievement_(achievement);

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

    public String getAchievement_() {
        return achievement_;
    }

    public void setAchievement_(String achievement_) {
        this.achievement_ = achievement_;
    }
}
