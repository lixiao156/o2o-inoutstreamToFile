package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.AreaDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lixw
 * @date created in 15:05 2019/1/1
 */
@Service
public class AreaServiceImpl implements AreaService {
    /**
     * Autowired标签表示希望spring容器将dao接口的实现类注入到这里来
     */
    @Autowired
    private AreaDao areaDao;

    /**
     * 取出区域列表
     *
     * @return
     */
    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
