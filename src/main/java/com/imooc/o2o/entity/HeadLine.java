package com.imooc.o2o.entity;

import java.util.Date;

/**
 * 头条首页实体类
 *
 * @author lixw
 * @date created in 16:38 2018/12/30
 */
public class HeadLine {
    private Long LineId;
    private String lineName;
    private String linrLink;
    private String lineImg;
    /**
     * 展示的优先级
     */
    private Integer priority;
    /**
     * 状态：0:为不可用状态，1：可用状态
     */
    private Integer enableStatus;
    private Date creatTime;
    private Date lastEditTime;

    public Long getLineId() {
        return LineId;
    }

    public void setLineId(Long lineId) {
        LineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLinrLink() {
        return linrLink;
    }

    public void setLinrLink(String linrLink) {
        this.linrLink = linrLink;
    }

    public String getLineImg() {
        return lineImg;
    }

    public void setLineImg(String lineImg) {
        this.lineImg = lineImg;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
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
