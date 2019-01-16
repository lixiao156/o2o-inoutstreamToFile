package com.imooc.o2o.dao;

import com.imooc.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商城首页展示信息
 * @author lixw
 * @date created in 12:27 2019/1/15
 */
public interface HeadLineDao {
    /**
     * 根据传入的查询条件查询头条信息
     * 返回 headLineCondition 列表
     *
     * 参数注解是放mybatis识别
     * @param headLineCondition
     * @return
     */
   List <HeadLine> queryHeadLine(
           @Param("headLineCondition")
           HeadLine headLineCondition);

    /**
     *
     * @param LineId
     * @return
     */
   int insertHeadLine(long LineId);


}
