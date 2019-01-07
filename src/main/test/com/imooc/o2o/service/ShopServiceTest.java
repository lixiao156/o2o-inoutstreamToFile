package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopSateEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import static org.junit.Assert.*;
public class ShopServiceTest extends BaseTest {
    /**
     * 这个地方报错 接口实现类没有@service注解
     */
    @Autowired
    private ShopService shopService;


    @Test
    public void addShop() throws FileNotFoundException {
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
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setShopImg("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopSateEnum.CHECK.getState());
        //接口报错 看是否将接口实现类注入
        shop.setAdvice("审核中");
        File shopImg = new File("G:/ssmpicture/picture.jpg");
        System.out.println(shopImg.toString());
        InputStream is = new FileInputStream(shopImg);
        ShopExecution se = shopService.addShop(shop, is,shopImg.getName());
        assertEquals(ShopSateEnum.CHECK.getState(),se.getState());
    }

    @Test
    @Ignore
    public void modifyShop() {
        Shop shop = shopService.getByShopId(31);
        System.out.println(shop);
        shop.setShopName("修改后的店铺名");
        File shopImg = new File("G:\\ssmpicture2\\youxiang.jpg");
        try {
            InputStream is = new FileInputStream(shopImg);
            ShopExecution shopExecution = shopService.modifyShop(shop,is,shopImg.getName());
            System.out.println("新的图片地址为："+shopExecution.getShop().getShopImg());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getShopList() {
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
        System.out.println("店铺列表数为: " + se.getShopList().size());
        System.out.println("店铺总数为: " + se.getCount());
    }
}