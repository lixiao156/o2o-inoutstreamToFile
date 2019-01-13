package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    @Ignore
    public void insertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setEnableStatus(1);
        shop.setCreateTime(new Date());
        shop.setAdvice("审核中");
        int effectNum = shopDao.insertShop(shop);
        assertEquals(1,effectNum);


    }
    @Test

    public void updateShop() {
        Shop shop = new Shop();
        shop.setShopId(31L);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectNum = shopDao.updateShop(shop);
        assertEquals(1,effectNum);
    }

    @Test

    public void queryByShopId(){
        long shopId = 31;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println("areaId :"+shop.getArea().getAreaId());
        System.out.println("areaName:"+shop.getArea().getAreaName());

    }

    @Test
    public void QueryShopList() {
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1l);
        shopCondition.setOwner(owner);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
        System.out.println(shopList.size());
        System.out.println("店铺列表的大小："+shopList.size());
        System.out.println();

        int count = shopDao.queryShopCount(shopCondition);


    }
}