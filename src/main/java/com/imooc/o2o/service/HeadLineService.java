package com.imooc.o2o.service;

import com.imooc.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @author lixw
 * @date created in 15:17 2019/1/15
 */
public interface HeadLineService {
    /**
     * 根据查询的条件传入指定的头条列表
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition)throws IOException;
}
