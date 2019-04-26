package cn.yonyong.usetk.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yonyong.usetk.utils.ClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author yonyong
 *
 * 全局异常处理器
 *
 */
@Component
public class ExceptionHandler implements HandlerExceptionResolver{

	@Autowired
	ClientUtil clientUtil;
	
	@Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object obj,Exception ex) {
		if(ex instanceof GlobalException){
			GlobalException globalException = (GlobalException)ex;
			clientUtil.flashDataToClient(response,JSONObject.toJSONString(globalException.getCodeMsg()));
		}
        return null;
    }
}
