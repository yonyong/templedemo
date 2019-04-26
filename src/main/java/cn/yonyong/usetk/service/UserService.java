package cn.yonyong.usetk.service;


import cn.yonyong.usetk.pojo.Info;
import cn.yonyong.usetk.pojo.User;

import java.util.List;

public interface UserService {

    //查询usr表账号和密码 ---》验证登陆
    public User queryUser(User user);

    //查询表中是否已经存在该用户
    public User registerQueryUser(String tel);

    //注册用户
    public int registerUser(String tel, String password);

    //获取所有信息
    public List<Info> getAllInfo(int page,int rows,Info info);
}
