package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 名字升序的方法进行执行 字典顺序
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    @Ignore
    public void testAInsertProduct() {
        Shop shop1 = new Shop();
        shop1.setShopId(31L);
        Shop shop2 = new Shop();
        shop2.setShopId(31L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(24L);
        ProductCategory pc2 = new ProductCategory();
        pc2.setProductCategoryId(24L);
        ProductCategory pc3 = new ProductCategory();
        pc3.setProductCategoryId(24L);
        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试Desc1");
        product1.setImgAddr("test1");
        product1.setPriority(0);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);
        Product product2 = new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试Desc2");
        product2.setImgAddr("test2");
        product2.setPriority(0);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc2);
        Product product3 = new Product();
        product3.setProductName("测试3");
        product3.setProductDesc("测试Desc3");
        product3.setImgAddr("test3");
        product3.setPriority(0);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop2);
        product3.setProductCategory(pc3);
        int effectedNum = productDao.insertProduct(product1);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product2);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product3);
        assertEquals(1, effectedNum);
    }

    /**
     * 根据商品的id获取图片详情列表
     */
    @Test
    public void queryProductByProductId() {
        long productId = 20L;
        //图片
        ProductImg productImg = new ProductImg();
        //日期
        productImg.setCreateTime(new Date());
        productImg.setImgDesc("测试图片描述");
        productImg.setImgAddr("图片地址");
        productImg.setPriority(1);
        //属于哪件商品
        productImg.setProductId(productId);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg.setImgDesc("测试图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg);
        productImgList.add(productImg2);
        int effecNum = productImgDao.bathInsertProductImg(productImgList);
        assertEquals(2, effecNum);
        Product product = productDao.queryProductByProductId(productId);
        // assertEquals(38, product.getProductImgList().size());
        effecNum = productImgDao.deleteProductImgByProductId(productId);
        //   assertEquals(2, effecNum);

    }


    @Test
    public void updateProduct() {
        Shop shop = new Shop();
        shop.setShopId(31L);
        Product product = new Product();
        //由内到外
        product.setProductId(20L);
        product.setProductName("第一个产品");
        product.setShop(shop);
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1, effectedNum);

    }

    @Test
    public void queryProductList() {
        //这商品对象是为空
        Product product = new Product();
        if (product == null) {
            System.out.println("product为null");
        } else {
            System.out.println("product引用对象不为空");
        }
        //优先级排序后分页
        List<Product> productList = productDao.queryProductList(product, 0, 999);
        for (Product p : productList
        ) {
            System.out.println(p.toString());
        }
        // assertEquals(3, productList.size());
        //统计数据的条数
        int count = productDao.queryProductCount(product);
        // assertEquals(17, count);
        //更改相关数据的信息 这里只是传递参数进去 具体的逻辑是 mapper xml里面的SQL语句
        product.setProductName("测试");
        productList = productDao.queryProductList(product, 0, 3);
        //  assertEquals(3, productList.size());
        //
        count = productDao.queryProductCount(product);
        //    assertEquals(17, count);
        Shop shop = new Shop();
        //mapper 里面的sql设计了为null的数据不输入不替换
        shop.setShopId(32L);
        //商品与商店相关联   需要看之前是否将商品分类关联好 以及商品Id已经确认
        product.setShop(shop);
        productList = productDao.queryProductList(product, 0, 3);
        //   assertEquals(1, productList.size());
        count = productDao.queryProductCount(product);
        //    assertEquals(1, count);

    }

    @Test
    public void deleteProduct() {
        int effectedNum = productDao.deleteProduct(18, 31);
        assertEquals(1, effectedNum);
    }

    @Test
    public void queryProductCount() {

    }

    /**
     *  Preparing: update tb_product set product_category_id = null where product_category_id = ?
     * 根据数据库的意思就是将  商品表中的所有商品分类为 ？ 的值全部替换为null
     * 如果店铺不一样会不会出现问题   会不会操作到其他店铺 的相同的分类 Id
     */
    @Test
    public void updateProductCategoryToNull() {
        //能运行可以对数据库进行操作
//        Product product = new Product();
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setProductCategoryId(24L);
//        productCategory.setShopId(31L);
//        product.setProductCategory(productCategory);
//        Long id = product.getProductCategory().getProductCategoryId();
//        int i = productDao.updateProductCategoryToNull(id);


        //下面的方式也能够达到相同的效果
        int effectNUm = productDao.updateProductCategoryToNull(24);
        System.out.println(effectNUm);

    }
}