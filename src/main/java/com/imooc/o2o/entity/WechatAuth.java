package com.imooc.o2o.entity;

import java.util.Date;

/**
 * 微信登录的实体类
 *
 * @author lixw
 * @date created in 14:59 2018/12/30
 */
public class WechatAuth {
    /**
     * 用户id
     */
    private Long wechatAuthId;
    /**
     * 微信账号关联本地商城账号的唯一标示
     */
    private String openId;

    private Date createTime;
    /**
     * 这里不是简单的基础类
     * 是要与实体类相关联的
     */
    private PersonInfo personInfo;

    public Long getWechatAuthId() {
        return wechatAuthId;
    }

    public void setWechatAuthId(Long wechatAuthId) {
        this.wechatAuthId = wechatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }
}
