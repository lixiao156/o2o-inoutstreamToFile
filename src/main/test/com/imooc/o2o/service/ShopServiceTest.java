package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopSateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.util.Date;
import static org.junit.Assert.*;
public class ShopServiceTest extends BaseTest {
    /**
     * 这个地方报错 接口实现类没有@service注解
     */
    @Autowired
    private ShopService shopService;


    @Test
    public void addShop() {
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
        shop.setShopName("测试的店铺1");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");
        shop.setShopImg("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopSateEnum.CHECK.getState());
        //接口报错 看是否将接口实现类注入
        shop.setAdvice("审核中");
        File shopImg = new File("G:/ssmpicture/picture.jpg");
        System.out.println(shopImg.toString());
        ShopExecution se = shopService.addShop(shop, shopImg);
        assertEquals(ShopSateEnum.CHECK.getState(),se.getState());
    }
}