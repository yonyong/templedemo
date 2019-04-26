package test.cn.yonyong.usetk.service.impl; 

import cn.yonyong.usetk.dao.InfoDao;
import cn.yonyong.usetk.pojo.Info;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 
* UserServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 26, 2019</pre> 
* @version 1.0 
*/

public class UserServiceImplTest extends InfoTest{
    @Autowired
    InfoDao infoDao;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 
@Test
public void testTestTk() throws Exception {
    int current=0,rowCount=10;
    Info info = new Info();
    info.setId(1781015);
    info.setTitle("装载");
    info.setDate( new SimpleDateFormat().format(new Date()));
    info.setContent("");
    PageHelper.startPage(current,rowCount);//分页
    Example example = new Example(Info.class);

    //根据info id排序
//    example.setOrderByClause("id");
    example.orderBy("id");
    //模糊查询
//    Example.Criteria criteria = example.createCriteria();
//    if (StringUtil.isNotEmpty(info.getTitle())){
//        criteria.andLike("title","%"+info.getTitle()+"%");
//    }
//    if (StringUtil.isNotEmpty(info.getTitle())){
//        criteria.andLike("content","%"+info.getContent()+"%");
//    }
//    //条件相等
//    criteria.andEqualTo("title","老杨");
//    //大于
//    criteria.andGreaterThan("id","1");
//    criteria.andLessThan("id","10");
//    criteria.andIsNotNull("title");
//
//    //加各种条件都可以 = like <,可以代替全部的
//    criteria.andCondition("title=","技术人员如何搭建自己的专业博客");
//
//    //数据库  in
//    List<String> values = new ArrayList<String>();
//    values.add("文章A");
//    values.add("文章B");
//    values.add("文章C");
//    criteria.andIn("title",values);
//
//    //时间间隔
//    criteria.andBetween("date","2019/01/26","2019/04/26");
//    Example.Criteria criteria12 = example.createCriteria();
//    criteria12.andCondition("id=","1001");
//    example.or().andCondition("id=","1001");
//    example.or(criteria12);

    List<Info> infoList = infoDao.selectByExample(example);
    System.out.println("====================================================");
    System.out.println("输出输出输出输出输出输出输出输出输出输出输出输出输出");
    System.out.println("====================================================");
    for (int i=0;i<infoList.size();i++){
        System.out.println("id:"+infoList.get(i).getId()+"  title:"+infoList.get(i).getTitle()+"  date:"+infoList.get(i).getDate()+"  content:"+infoList.get(i).getContent());
    }
} 

} 
