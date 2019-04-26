package cn.yonyong.usetk.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class ClientUtil {

	
	
	
	/**
	 * 封装JSON数据
	 * @param obj 数据
	 * @param flag 成功失败标识 0:失败 1:成功
	 * @return
	 */
	public String getMsgArrayJson(Object obj, boolean flag) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("rec", flag == true ? "SUC" : "FAL");
		//Map<String, Object> returnData = new LinkedHashMap<String, Object>();
		if(!flag){
			map.put("body", obj.toString());
			//returnData.put("error", obj.toString());
		}else{
			try {
				map.put("body", JSONArray.fromObject(obj));
				//returnData.put("success", JSONArray.fromObject(obj));
			} catch (Exception e) {
				map.put("body", obj);
				//returnData.put("success", obj);
			}
			
		}
		
		JSONObject jsonObjectFromMap = JSONObject.fromObject(map);
		return jsonObjectFromMap.toString();
	}
	
	/**
	 * 返回数据到客户端
	 * @param response
	 * @param jsonData
	 */
	public void flashDataToClient(HttpServletResponse response,String jsonData){
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json; charset=utf-8");
			out = response.getWriter();
			out.print(jsonData);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out != null){
				out.close();
			}
			out = null;
		}
	}
	
	/** 
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址, 
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值 
     *  
     * @return ip
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for"); 
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
            System.out.println("Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
            System.out.println("X-Real-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            System.out.println("getRemoteAddr ip: " + ip);
        } 
        System.out.println("获取客户端ip: " + ip);
        return ip;  
    }
	
}
