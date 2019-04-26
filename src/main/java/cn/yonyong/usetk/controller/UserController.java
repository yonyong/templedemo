package cn.yonyong.usetk.controller;

import cn.yonyong.usetk.dao.InfoDao;
import cn.yonyong.usetk.message.CodeMsg;
import cn.yonyong.usetk.pojo.Info;
import cn.yonyong.usetk.pojo.Login;
import cn.yonyong.usetk.pojo.User;
import cn.yonyong.usetk.pojo.Validate;
import cn.yonyong.usetk.service.LoginService;
import cn.yonyong.usetk.service.UserService;
import cn.yonyong.usetk.service.ValidateService;
import cn.yonyong.usetk.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@Api(value = "/user",tags = "UseController", description = "登陆注册用户操作")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ValidateService validateService;

    @Autowired
    InfoDao infoDao;
    //登陆界面
    @RequestMapping("/tologin")
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息", httpMethod = "GET", response = User.class)
    public String toLogin(){
        return "login";
    }

    //注册界面
    @RequestMapping("/toregister")
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息", httpMethod = "GET", response = User.class)
    public String toRegister(){
        return "register";
    }

    //用户注册协议
    @RequestMapping("/success")
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息", httpMethod = "GET", response = User.class)
    public String toSuccess(){
        return "success";
    }

    //游客登录界面
    @RequestMapping("/toyoukeinfo")
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息", httpMethod = "GET", response = User.class)
    public String toYoukeInfo(Model model){
        List<Info> list=infoDao.queryInfo();
        model.addAttribute("list",list);
        logger.info("查询所有信息并展示。。。。");
        return "youkeInfo";
    }

//    登陆验证
    @RequestMapping("/login")
    @ResponseBody
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息", httpMethod = "GET", response = User.class)
    public CodeMsg login(@RequestParam(value="tel") String tel, @RequestParam(value="password") String password, HttpServletRequest request){

        User user=new User();
        user.setTel(tel);
        password= MD5Utils.getMD5(password);
        user.setPassword(password);

        //判断用户是否在数据库里，如果User不为空，则账号密码在数据库中有相应的匹配，即用户存在
        User rs_user=userService.queryUser(user);

        //判断用户是否在user数据库里
        User t=userService.registerQueryUser(tel);

        //判断错误登陆三次锁定的表login中是否已经有这个账户
        Login rs_login=loginService.queryUser(tel);
//        Date date=new Date();
//        Timestamp errorLoginTime=new Timestamp(date.getTime());
        Date errorLoginTime=new Date();
        if (rs_login==null){
            loginService.insertUser(tel,errorLoginTime,0);
            //login表和user表没有设置关联，如果user表中存在该用户而login表中不存在，则将其添加进去。
            rs_login=loginService.queryUser(tel);
        }
        int count=rs_login.getCount();
        System.out.println("count"+count);
        Date time=rs_login.getTime();
        long result=errorLoginTime.getTime()-time.getTime();

        if(result>180000)
            count=0;
        if (t!=null) {      //数据库里已经存在手机号为tel的用户

            if(count>2)//连续错误登陆次数已经超过三次
            {
//                String jsonStr = "{\"errorCode\":\"33\",\"errorMessage\":\"您由于错误登陆次数太多，系统已将您的账户锁定，请在三分钟后重新登录！\"}";
                loginService.updateUser(tel,errorLoginTime,count+1);
                return CodeMsg.INCORRECT_LOCKED;
            }
            else{
                if((null==rs_user)) {		//用户存在但密码输入错误，那么输出密码输入错误，并且如果当前和上次错误时间相隔不到1分钟则count+1;
//                    String jsonStr = "{\"errorCode\":\"22\",\"errorMessage\":\"密码输入错误，错误输入三次后您的账户将会被锁定！\"}";
                    loginService.updateUser(tel,errorLoginTime,count+1);
                    return CodeMsg.INCORRECT_PWD;
                }else {

                    loginService.updateUser(tel,errorLoginTime,0);
                    HttpSession session=request.getSession();
                    session.setAttribute("rs_user", rs_user);
//                    String jsonStr = "{\"errorCode\":\"00\",\"errorMessage\":\"登陆成功！\"}";
                    return CodeMsg.LOGIN_SUCCESS;
                }
            }
        }
        else {	//该数据库没有存储过该tel
            loginService.insertUser(tel,errorLoginTime,1);
            loginService.updateUser(tel,errorLoginTime,count+1);
//            String jsonStr = "{\"errorCode\":\"11\",\"errorMessage\":\"该用户不存在\"}";
            return CodeMsg.USER_NOEXIEST;
        }
    }


    //注册验证
    @RequestMapping("/register")
    @ResponseBody
    @ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息", httpMethod = "GET", response = User.class)
    public CodeMsg register(@RequestParam(value="tel") String tel, @RequestParam(value = "volidate")String volidate, @RequestParam(value="password")String password, HttpServletRequest request){
        Validate validateBean=validateService.queryYZM(tel);
        String yzm=validateBean.getValidate();
        User user=userService.registerQueryUser(tel);
        if(!volidate.equals(yzm)||yzm==null) {
            logger.info("验证码错误*****   voalidate为"+volidate+"  ,yzm为："+yzm);
//            String jsonStr = "{\"errorCode\":\"2\",\"errorMessage\":\"验证码错误\"}";
            return CodeMsg.CODE_INCORRECT;
        }
        else {
            if(user==null) {
                logger.info("进如此循环");
                password=MD5Utils.getMD5(password);
                int result=userService.registerUser(password, tel);
//                String jsonStr = "{\"errorCode\":\"1\",\"errorMessage\":\"success\"}";

                return  CodeMsg.REGISTER_SUCCESS;
            }
            else
            {
                 logger.info("进如此循环");
//                String jsonStr = "{\"errorCode\":\"0\",\"errorMessage\":\"phone or password is error\"}";
                return CodeMsg.FORMAT_INCORRECT;


            }
        }
    }

}



























