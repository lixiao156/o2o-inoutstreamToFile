package com.imooc.o2o.service.impl;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceImplTest extends BaseTest {
    @Autowired
    ProductService productService;

    @Test
    public void modifyProduct() {
        //测试商品的id为20 分类为24的 商品实例并给其他的成员变量赋值
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(31L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(24L);
        product.setProductId(20L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("正式名");
        product.setProductDesc("正式商品描述");
        //获取本地图片文件加载到输入流之中
        File thumbnail = new File("G:\\ssmpicture\\1.jpg");
        InputStream is = null;
        try {
            is = new FileInputStream(thumbnail);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageHolder thumbna = new ImageHolder(is,thumbnail.getName());
        File productImg1 = new File("G:\\ssmpicture\\2.jpg");
        InputStream is1 = null;
        try {
            is1 = new FileInputStream(productImg1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File productImg2 = new File("G:\\ssmpicture\\3.jpg");
        InputStream is2 = null;
        try {
            is2 = new FileInputStream(productImg1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(is1,productImg1.getName()));
        productImgList.add(new ImageHolder(is2,productImg2.getName()));
        ProductExecution pe = productService.modifyProduct(product,thumbna,productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
     }
}