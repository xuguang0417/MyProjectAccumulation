package com.example.xuguang0417.myprojectaccumulation.entity;

/**
 * Created by xuguang on 2016/9/19.
 */
public class StickyModel {

    public String sticky;           //头部
    public String name;             //名字
    public String sex;              //性别
    public String profession;       //职业

    public StickyModel(String sticky, String sex, String name, String profession) {
        this.sticky = sticky;
        this.sex = sex;
        this.name = name;
        this.profession = profession;
    }

    public String getSticky() {
        return sticky;
    }

    public void setSticky(String sticky) {
        this.sticky = sticky;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
