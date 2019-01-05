package com.imooc.o2o.entity;

import java.util.Date;
import java.util.List;

/**
 * 商品 信息
 * @author lixw
 * @date created in 19:04 2018/12/30
 */
public class Product {
    private Long productId;
    private String productName;
    /**
     * 商品描述
     */
    private String productDesc;
    /**
     * 简略图
     */
    private String imgAddr;
    /**
     * 商品原价
     */
    private String normalPrice;
    /**
     * 商品折扣价
     */
    private String promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    /**
     * 显示权级？？
     *  不是
     *
     *  这个 0：代表已经下架  1：表示正常展示
     */
    private Integer enableStatus;
    /**
     * 商品详情图片列表  商品与详情图是一对多的关系
     */

    private List<ProductImg> productImgList;
    /**
     * 商品的类别
     */
    private ProductCategory productCategory;
    /**
     * 记录商品属于哪家店铺
     */
    private Shop shop;
}
