package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopSateEnum;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;



/**
 * @author lixw
 * @date created in 14:12 2019/1/3
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution addShop(Shop shop, File shopImg) {
        /**
         * 如果对象为空 返回 NULL shop
         * 也可以加 地址 电话 非空判断
         */
        if (shop == null) {
            return new ShopExecution(ShopSateEnum.NULL_SHOP);
        }
        /**
         * 店铺的注册逻辑
         */
        try {
            //审核状态；给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //获取id
            int effectedNum = shopDao.insertShop(shop);
            //判断是否插入成功
            if (effectedNum <= 0) {
                //使用runtime才会回滚，如果直接使用Exception 该提交还是会提交
                throw new RuntimeException("店铺创建失败");
            } else {
                try {
                    if (shopImg != null) {
                        //储存图片 传引用
                        addShopImg(shop, shopImg);

                        //shop.getShopImg();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("addShopImg error:"+e.getMessage());

                }
                /**
                 * 更新图片的地址
                 */
                effectedNum = shopDao.updateShop(shop);
                /**
                 * 如果更新失败 effectedNum = -1;
                 */
                if(effectedNum <= 0){
                    throw new RuntimeException("图片更新地址失败");
                }
            }
        } catch (Exception e) {
            //需要   将日志打印到txt中去
            throw new RuntimeException("addShop error:" + e.getMessage());

        }
        /**
         *确认该类其他没有问题
         * 返回一个成功的标识 状态值为 待审
         */
        return new ShopExecution(ShopSateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, File shopImg) {
        //获取shop 图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        /**
         * 得到绝对路径将图片储存到里面 返回图片的相对子路径
         * 更新到数据库
         */
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg,dest);
        System.out.println(shopImgAddr);
        /**
         * 更新到数据库里面去
         */
        shop.setShopImg(shopImgAddr);
    }
}
