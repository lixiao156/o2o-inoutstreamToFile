package com.imooc.o2o.websuperadmin;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 可以有对Area编辑权限的管理员
 *
 * @author lixw
 * @date created in 16:52 2019/1/1
 */
@Controller
//url路由 调用这个方法的路径
@RequestMapping("/superadmin")
public class AreaController {
    Logger logger = LoggerFactory.getLogger(AreaController.class);
    @Autowired
    private AreaService areaService;

    /**
     * 相当于需要new 一个service层的对象
     *
     * @return 再加一个路由指明需要访问类路径下的具体的哪一个方法
     * 都小写便于写访问路径
     * ResponseBody自动将值转化为json返回给前端
     */
    @RequestMapping(value = "/listarea", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listArea() {

        logger.info("====start====");
        long startTime = System.currentTimeMillis();
        /**
         * 用来存放方法返回值
         *
         */
        //类型推断
        Map<String, Object> modelMap = new HashMap<>(4);
        /**
         * 用来获得service层返回的区域列表
         */
        List<Area> list = new ArrayList<Area>();
        /**
         * 这里的key value 不一定是要rows total list
         * 但是后面使用的控件是默认的rows 和 total
         */
        modelMap.put("rows", list);
        modelMap.put("total", list.size());
        try {
            list = areaService.getAreaList();
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        logger.error("test error");
        long endTime = System.currentTimeMillis();
        //留一个占位符 花了多少时间
        logger.debug("costTime:[{}ms]",endTime-startTime);
        logger.info("====end====");
        return modelMap;

    }
}
