package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lixw
 * @date created in 17:51 2019/1/2
 */
public interface ShopDao {

    /**
     * 新增店铺
     * 返回改变的行数  如果返回：1插入一行 如果返回：-1 插入失败
     * 由mybatis 实现这个类 mapper中配置
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     * 通过shopId查询店铺信息
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺，可输入的条件有：店铺名（模糊查询）、店铺状态、店铺类别，区域id、owner
     *  @Param 注解需要 是因为接口中有多个参数  不注解配置文件不知道哪一个参数对应那一个参数
     *
     * @param shopCondition
     * @param rowIndex
     *            从第几行开始取：rowIndex
     * @param pageSize
     *            返回的条数：pageSize
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);


    /**
     * 返回queryShopList的总数
     *
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

}
