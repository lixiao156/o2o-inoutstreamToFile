package com.imooc.o2o.entity;

import java.util.Date;

/**
 * 本地账号的实体类
 * @author lixw
 * @date created in 15:04 2018/12/30
 */
public class LocalAuth {
    /**
     * 本地账号的id
     */
    private Long localAuthId;
    private String username;
    private String password;
    private Date creatTime;
    private Date lastEditInfo ;
    private PersonInfo personInfo;

    public Long getLocalAuthId() {
        return localAuthId;
    }

    public void setLocalAuthId(Long localAuthId) {
        this.localAuthId = localAuthId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getLastEditInfo() {
        return lastEditInfo;
    }

    public void setLastEditInfo(Date lastEditInfo) {
        this.lastEditInfo = lastEditInfo;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
