package test.cn.yonyong.usetk.service.impl;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Describtion Todo
 * @Author yonyong
 * @Date 2019/4/26 14:48
 * @Version 1.0.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件位置
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class InfoTest {
}
