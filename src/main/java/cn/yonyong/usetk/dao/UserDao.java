package cn.yonyong.usetk.dao;

import cn.yonyong.usetk.pojo.User;
import cn.yonyong.usetk.tk.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User>{
    //查询usr表账号和密码 ---》验证登陆
    //传递参数为一个对象时，不加@Param注解会报错
    public User queryUser(@Param("user") User user);

    //查询表中是否已经存在该用户
    public User registerQueryUser(String tel);

    //注册用户
    public int registerUser(@Param("tel") String tel, @Param("password") String password);
}
