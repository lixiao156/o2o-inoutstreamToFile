package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductImg;

import java.util.List;

/**
 * @author lixw
 * @date created in 22:20 2019/1/8
 */
public interface ProductImgDao {
    /**
     * 批量插入商品图片
     * @param productImgList
     * @return
     */
    int bathInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品的所有详情图 与之前删除店铺的缩略图是不一样的
     * @param productId
     * @return
     */



    /**
     * 根据商品的id获取商品的详情图
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(long productId);

    /**
     * 删除图片
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);


}
