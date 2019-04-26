package cn.yonyong.usetk.service.impl;

import cn.yonyong.usetk.dao.InfoDao;
import cn.yonyong.usetk.dao.UserDao;
import cn.yonyong.usetk.pojo.Info;
import cn.yonyong.usetk.pojo.User;
import cn.yonyong.usetk.service.UserService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    InfoDao infoDao;

    @Override
    public User queryUser(User user) {
        return userDao.queryUser(user);
    }

    @Override
    public User registerQueryUser(String tel) {
        return userDao.registerQueryUser(tel);
    }

    @Override
    public int registerUser(String tel,String password) {
        return userDao.registerUser(tel,password);
    }

    @Override
    public List<Info> getAllInfo(int page,int rows,Info info) {
        List<Info> list = new ArrayList<>();
        list = infoDao.selectAll();
        return list;
    }

    //tk example的用法
    private void testTk(int current,int rowCount,Info info,String sort,String nane,String ph){
        /**
         * 数据库表 info
         * 数据库字段 id,title,content,date
         */
        PageHelper.startPage(current,rowCount);//分页
        Example example = new Example(Info.class);

        //根据info id排序
        example.setOrderByClause("id");
        //模糊查询
        Example.Criteria criteria = example.createCriteria();
            if (StringUtil.isNotEmpty(info.getTitle())){
                criteria.andLike("title","%"+info.getTitle()+"%");
            }
            if (StringUtil.isNotEmpty(info.getTitle())){
                criteria.andLike("content","%"+info.getContent()+"%");
            }
         //条件相等
         criteria.andEqualTo("title","老杨");
         //大于
        criteria.andGreaterThan("id","1");
        criteria.andLessThan("id","10");
        criteria.andIsNotNull("title");

        //加各种条件都可以 = like <,可以代替全部的
        criteria.andCondition("title=","技术人员如何搭建自己的专业博客");

        //数据库  in
        List<String> values = new ArrayList<String>();
        values.add("文章A");
        values.add("文章B");
        values.add("文章C");
        criteria.andIn("title",values);

        //时间间隔
        criteria.andBetween("date","2019/01/26","2019/04/26");
        Example.Criteria criteria12 = example.createCriteria();
        criteria12.andCondition("id=","1001");
        example.or().andCondition("id=","1001");
        example.or(criteria12);

        List<Info> infoList = infoDao.selectByExample(example);
    }
}