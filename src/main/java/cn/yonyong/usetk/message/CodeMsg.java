package cn.yonyong.usetk.message;

public class CodeMsg {
	private int code;
	private String msg;

	//测试查询失败
	public static final CodeMsg QUERY_FAILED = new CodeMsg(500100,"查询失败");

	//登录
	public static final CodeMsg LOGIN_SUCCESS = new CodeMsg(500200,"登陆成功");
	public static final CodeMsg INCORRECT_PWD = new CodeMsg(500400,"密码错误");
	public static final CodeMsg INCORRECT_LOCKED = new CodeMsg(500401,"错误登录次数太多");
	public static final CodeMsg USER_NOEXIEST = new CodeMsg(500402,"用户不存在");

	//注册
	public static final CodeMsg REGISTER_SUCCESS = new CodeMsg(500500,"注册成功");
	public static final CodeMsg CODE_INCORRECT = new CodeMsg(500501,"验证码错误");
	public static final CodeMsg FORMAT_INCORRECT = new CodeMsg(500502,"手机或密码格式错误");
	/*-----------------------------------------------------constructor------------------------------------------------*/
	public CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	/*-----------------------------------------------------getter/setter------------------------------------------------*/
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
