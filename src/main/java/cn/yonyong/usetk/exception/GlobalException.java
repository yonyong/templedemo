package cn.yonyong.usetk.exception;


import cn.yonyong.usetk.message.CodeMsg;

/**
 * 
 * @author yonyong
 * 全局自定义异常
 *
 */
public class GlobalException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private CodeMsg codeMsg;

	public GlobalException(CodeMsg codeMsg) {
		super();
		this.codeMsg = codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}

}
