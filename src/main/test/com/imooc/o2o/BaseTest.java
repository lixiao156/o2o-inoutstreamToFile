package com.imooc.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lixw
 * @date created in 14:25 2019/1/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告訴junit spring配置文件在哪个位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {
}
