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
}
