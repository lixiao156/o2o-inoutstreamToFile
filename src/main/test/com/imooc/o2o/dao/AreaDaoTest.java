package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;


//每次加载这个类都会先加载BaseTest里面的Spring-dao的配置文件

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    /**
     * 判断是不是两个数据
     */
    @Test
    public void queryArea() {
        List<Area> areaList = areaDao.queryArea();
        System.out.println(areaList.get(0).toString());
    }
}
