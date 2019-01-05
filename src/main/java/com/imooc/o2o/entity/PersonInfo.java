package com.imooc.o2o.entity;

import java.util.Date;

/**
 * 用户信息实体类
 * @author lixw
 * @date created in 14:20 2018/12/30
 */
public class PersonInfo {
    /**
     * 可能用户比较多Long 范围大
     */
    private Long userId;
    private String name;
    /**
     * 用户头像地址
     */
    private String profileImg;
    private String email;
    /**
     * 性别
     */
    private String gender;
    /**
     * 用户状态 是否被禁止使用
     */
    private Integer enableStatus;
    /**
     * 约定 1：顾客，2：店家，3：超级管理员
     * 用户的身份标示
     */
    private Integer userType;

    private Date creatTime;
    /**
     * 更新时间
     */
    private Date lastEditTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
