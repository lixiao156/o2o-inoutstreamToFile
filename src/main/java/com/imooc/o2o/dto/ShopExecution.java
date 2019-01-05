package com.imooc.o2o.dto;

import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopSateEnum;

import java.util.List;

/**
 * @author lixw
 * @date created in 12:17 2019/1/3
 */
public class ShopExecution {
    /**
     * 结果状态
     */
    private int state;
    /**
     * 状态表示
     */
    private String stateInfo;
    /**
     * 店铺数量 有时候批量操作
     */
    private int count;
    /**
     * 操作Shop增删改店铺的时候用到
     */
    private Shop shop;
    /**
     * shop列表 查询店铺列表的时候使用
     */
    private List <Shop> shopList;


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public ShopExecution(){

    }

    /**
     * 店铺操作失败的构造器
     * @param sateEnum
     */
    public ShopExecution(ShopSateEnum sateEnum){
        this.state = sateEnum.getState();
        this.stateInfo = sateEnum.getStateInfo();
    }

    /**
     * 店铺操作成功单个  构造器
     * @param sateEnum
     * @param shop
     */
    public ShopExecution(ShopSateEnum sateEnum,Shop shop){
        this.state = sateEnum.getState();
        this.stateInfo = sateEnum.getStateInfo();
        this.shop = shop;
    }

    /**
     * 成功列表店铺操作  构造器
     * @param sateEnum
     * @param shopList
     */
    public ShopExecution(ShopSateEnum sateEnum,List<Shop> shopList){
        this.state = sateEnum.getState();
        this.stateInfo = sateEnum.getStateInfo();
        this.shopList = shopList;
    }

}
