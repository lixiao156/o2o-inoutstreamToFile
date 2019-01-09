package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopSateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


/**
 * @author lixw
 * @date created in 14:12 2019/1/3
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution addShop(Shop shop, ImageHolder shopImgInputStream) throws ShopOperationException {
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
                    if (shopImgInputStream.getImage() != null) {
                        //储存图片 传引用
                        addShopImg(shop, shopImgInputStream);

                        //shop.getShopImg();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("addShopImg error:" + e.getMessage());

                }
                /**
                 * 更新图片的地址
                 */
                effectedNum = shopDao.updateShop(shop);
                /**
                 * 如果更新失败 effectedNum = -1;
                 */
                if (effectedNum <= 0) {
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
        return new ShopExecution(ShopSateEnum.CHECK, shop);
    }


    private void addShopImg(Shop shop, ImageHolder shopshopImgInputStreamImg) {
        //获取shop 图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        /**
         * 得到绝对路径将图片储存到里面 返回图片的相对子路径
         * 更新到数据库
         */
        String shopImgAddr = ImageUtil.generateThumbnail(shopshopImgInputStreamImg, dest);
        System.out.println(shopImgAddr);
        /**
         * 更新到数据库里面去
         */
        shop.setShopImg(shopImgAddr);
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    /**
     * 需要分成两步执行
     * 1.判断是否需要处理图片 修补修改图片
     * 更新图片需要删除旧的图片 需要一个删除图片的工具类
     * 2.更新店铺信息
     *
     * @param shop
     * @param shopImgInputStream
     * @param
     * @return
     * @throws ShopOperationException
     */
    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder shopImgInputStream) throws ShopOperationException {
        try {
            if (shop == null || shop.getShopId() == null) {
                return new ShopExecution(ShopSateEnum.NULL_SHOP);
            } else {
                /**
                 *  1.判断是否需要处理图片  filename 不为null 也不为空的字段
                 *
                 */
                if (shopImgInputStream.getImage() != null && shopImgInputStream.getImageName()!= null && !"".equals(shopImgInputStream.getImageName())) {
                    /**
                     *  //获取需要更新图片的shop的地址
                     */
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    //如果这个店铺之前有图片
                    if (tempShop.getShopImg() != null) {
                        //删除原来的图片
                        ImageUtil.deletFileOrPath(tempShop.getShopImg());
                    }
                    //删除原来的图片之后添加新的图片
                    addShopImg(shop, shopImgInputStream);

                }

                // 2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                //如果操作的行数为0 说明失败了
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopSateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopSateEnum.SUCCESS, shop);
                }
            }
        } catch (Exception e) {
            throw new ShopOperationException("modifyShop error: " + e.getMessage());
        }
    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        //将pageIndex转化为rowIndex
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        //返回需要的列表
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        //获取相同查询条件下的总数
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            //将用户列表保存到ShopExecution对象中去
            se.setShopList(shopList);
            //保存查询出的总数
            se.setCount(count);
        } else {
            //如果没有查到 ShopSateEnum设置成失败
            se.setState(ShopSateEnum.INNER_ERROR.getState());
        }
        return se;
    }
}
