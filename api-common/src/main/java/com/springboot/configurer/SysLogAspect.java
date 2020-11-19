package com.springboot.configurer;


import com.alibaba.fastjson.JSONObject;
import com.springboot.common.JBDate;
import com.springboot.common.RequestWrapper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * Created by jack on 2017/7/10.
 * 记录调用Controller的日志
 */

@Aspect
@Component
public class SysLogAspect {


    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final ThreadLocal<Long> timeTreadLocal = new ThreadLocal<>();


    /**
     * 这里我们使用注解的形式 当然，我们也可以通过切点表达式直接指定需要拦截的package,需要拦截的class 以及 method 切点表达式:
     * execution(...)
     */
    @Pointcut("within(com.springboot.web..*)")
    public void log() {
    }

    @Before("log()")
    public void Before(JoinPoint joinPoint) {
        long start=System.currentTimeMillis();
        timeTreadLocal.set(start);
        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        //获取请求的request
        HttpServletRequest request = attributes.getRequest();
        //获取所有请求的参数，封装为map对象
        RequestWrapper myRequestWrapper = null;
        String body ="";
        try {
            myRequestWrapper = new RequestWrapper((HttpServletRequest) request);
            body = myRequestWrapper.getBody();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Map<String,Object> parameterMap = getParameterMap(request);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取被拦截的方法
        Method method = methodSignature.getMethod();
        //获取被拦截的方法名
        String methodName = method.getName();

        StringBuffer requestURL = request.getRequestURL();
        URI ip =null;
        try {
            ip = this.getIP(new URI(requestURL.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        String ip= null;
//        try {
//            ip = this.getIpAddress(request);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if ("dev".equals(env)) {
        //登陆接口独立使用
        String url=request.getRequestURI();
        if(StringUtils.isNotEmpty(url)&&(url.contains("/api/app/appLogin")||url.contains("/api/app/login")||url.contains("/api/app/findFile")||url.contains("/api/app/uploadFile")
                ||url.contains("/api/app/authorize"))){
            logger.info("**********************************************************************************************************开始("+start+")*********************************************************************************************************************");
        }
        logger.info("请求参数:"+body);
        logger.info("请求的域名= = {}",ip.toString());
        logger.info("请求url = {}",request.getRequestURI());
        logger.info("AOP begin ,请求开始方法  :{}", method.getDeclaringClass() + "." + methodName + "()");
        logger.info("当前token======>"+request.getHeader("token"));
//        }
    }

    @After("log()")
    public void after() {
//        logger.info("aop的after()方法");
    }

    //controller请求结束返回时调用
    @AfterReturning(returning = "result", pointcut = "log()")
    public void afterReturn(Object result) {
//        if ("dev".equals(env)) {
//            logger.info("AOP afterReturn,返回值result = {}", result.toString());
//             System.out.println("返回值="+result.toString());
        long startTime = timeTreadLocal.get();
        double callTime = (System.currentTimeMillis() - startTime) / 1000.0;
        logger.info("调用controller花费时间time = {}s", callTime);
//        }
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     */
    public String JsonReq(HttpServletRequest request) {
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
            return jsonObject.toJSONString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }


    public URI getIP(URI uri) {
        URI effectiveURI = null;
        try {
            // URI(String scheme, String userInfo, String host, int port, String
            // path, String query,String fragment)
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable var4) {
            effectiveURI = null;
        }

        return effectiveURI;
    }
    public String getIpAddress(HttpServletRequest request) throws IOException {
        // 获取nginx代理前的ip地址
        String ip = request.getHeader("X-Real-IP");
        if (logger.isInfoEnabled()) {
            logger.info("getIpAddress(X-real-ip) - X-real-ip - String ip=" + ip);
        }
        // 获取所有代理记录的ip地址
        String refererIps = request.getHeader("x-forwarded-for");
        String[] split = refererIps.trim().split(",");
        if (split != null && split.length >= 2) {
            // 获取请求最开始的ip
            ip = split[0];
            logger.info("getIpAddress(x-forwarded-for) - x-forwarded-for - String ip=" + refererIps);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
                if (logger.isInfoEnabled()) {
                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
                }
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        logger.info("final request ip : {}", ip);
        return ip;
    }
}

