package com.springboot.common;

import com.springboot.jwt.JwtTokenUtil;
import com.springboot.model.employee.DaYgda;
import com.springboot.model.user.BaseUser;
import com.springboot.model.user.DaQxYhda;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * HttpServletRequest帮助类
 */
public class RequestUtils {
	private static final Logger log = LoggerFactory
			.getLogger(RequestUtils.class);

	public static Map<String,String> getSignMap(HttpServletRequest request){
		Map<String,String> param=new HashMap<String, String>();
		Enumeration penum=(Enumeration) request.getParameterNames();
		while(penum.hasMoreElements()){
			String pKey=(String) penum.nextElement();
			String value=request.getParameter(pKey);
			//sign和uploadFile不参与 值为空也不参与
			if(!pKey.equals("sign")&&!pKey.equals("uploadFile")
					&& StringUtils.isNotBlank(value)){
				param.put(pKey,value);
			}
		}
		return param;
	}

//

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 如果是多级代理，那么取第一个ip为客户端ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(0, ip.indexOf(",")).trim();
		}

		return ip;
	}
	public static BaseUser getRequestUser(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		BaseUser user=(BaseUser)request.getSession().getAttribute("baseUser");
		return user;
	}
	public static DaQxYhda getRequestUserId(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		DaQxYhda daYgda= (DaQxYhda) request.getSession().getAttribute("sixCode_daYgda");
		return daYgda;
	}
	public static void main(String[] args) {
	}
}
