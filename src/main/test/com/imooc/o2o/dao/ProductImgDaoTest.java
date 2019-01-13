package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ProductImg;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 按照名字字典顺序
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void bathInsertProductImg()  {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(18l);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg1.setImgDesc("测试图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(18l);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = 0;
        try {
            effectedNum = productImgDao.bathInsertProductImg(productImgList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(2, effectedNum);
    }



    @Test
    public void queryProductImgList() {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(38L);
        for (ProductImg p:productImgList
             ) {
            System.out.println(p);
        }
    }

    @Test
    public void deleteProductImgByProductId() {

    }
}