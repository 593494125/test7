package com.springboot.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.springboot.configurer.CorsInterceptor;
import com.springboot.configurer.JsonOtherReturnHandler;
import com.springboot.configurer.JsonReturnHandler;
import com.springboot.core.Result;
import com.springboot.core.ResultCode;
import com.springboot.core.ResultGenerator;
import com.springboot.core.ServiceException;
import com.springboot.jwt.JwtTokenUtil;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.redisdatasource.BaseRedisDatasource;
import com.springboot.model.user.BaseUser;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.org.BaseOrgService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Spring MVC 配置
 */
@Configuration
@CrossOrigin
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

//    @Autowired
//    private ProperiesFile prop;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BaseOrgService baseOrgService;
    @Value("${web.filepath}")
    private String filepath;

    private String logfilepath="d:/yimatong_log/mylog/";
    private String logfilepath1="d:/ymt_log/mylog/";


    String[] includeUrls = new String[]{
            "/api/app/getToken","/api/a8/getToken","/api/app/uploadFile","/api/app/findFile","/api/app/autoCreateVersion", "/api/app/authorize","/api/app/getSixCode","/api/app/login","/api/app/appLogin","/Face/GetToken","/api/callBack/msg","/ymtfile/**","/yimatong_log/**","/ymt_log/**",
            "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/webjars/*/*"};
    final String[] notLoginInterceptPaths = {"/api/app/getToken","/api/a8/getToken","/api/app/uploadFile","/api/app/findFile", "/api/app/autoCreateVersion","/api/app/authorize","/api/app/getSixCode","/api/app/login","/api/app/appLogin","/Face/GetToken","/api/callBack/msg","/swagger-resources/**", "/webjars/**","/webjars/*/*", "/v2/**", "/swagger-ui.html/**","/ymtfile/**","/yimatong_log/**","/ymt_log/**","/error"};

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);

    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件

    //使用阿里 FastJson 作为JSON MessageConverter
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);//保留空的字段
        //SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
        //SerializerFeature.WriteNullNumberAsZero//Number null -> 0
        // 按需配置，更多参考FastJson文档哈
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        converters.add(converter);
        converters.add(responseBodyConverter());
        addDefaultHttpMessageConverters(converters);
        super.configureMessageConverters(converters);
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setAttribute("version","1.0.1");
        super.setServletContext(servletContext);
    }
    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                Result result = new Result();
                if (e instanceof ServiceException) {//业务失败的异常，如“账号或密码错误”
                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                    logger.info(e.getMessage());
                } else if (e instanceof NoHandlerFoundException) {
                    result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
                } else if (e instanceof ServletException) {
                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                } else if (e instanceof NumberFormatException) {
                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                } else if(e instanceof CustomeException) {
                    result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                } else{
                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                    String message;
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                    } else {
                        message = e.getMessage();
                    }
                    logger.error(message, e);
                }
                responseResult(response, result);
                return new ModelAndView();
            }

        });
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }


    //添加拦截器
    @Override
    @CrossOrigin
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");
        //登录拦截器
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                //验证登录
                return validateLogin(request, response);

            }
        }).addPathPatterns("/**").excludePathPatterns(notLoginInterceptPaths);
    }


    /**
     * 增加返回值json过滤
     * @param returnValueHandlers
     */
    @Override
    protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new JsonReturnHandler()); //添加自定义的json返回值处理
        returnValueHandlers.add(new JsonOtherReturnHandler()); //添加自定义的json返回值处理
        super.addReturnValueHandlers(returnValueHandlers);
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //读取配置文件中的上传路径
        registry.addResourceHandler("/ymtfile/file/**")
                .addResourceLocations("file:"+filepath);//WID系统
        registry.addResourceHandler("/yimatong_log/mylog/**")
                .addResourceLocations("file:"+logfilepath);//WID系统
        registry.addResourceHandler("/ymt_log/mylog/**")
                .addResourceLocations("file:"+logfilepath1);//WID系统
        //读取配置文件中的上传路径
//        String goodsPath=prop.getGoodsPath();
//        registry.addResourceHandler("/files/SPPics/SP/**")
//                .addResourceLocations("file:"+goodsPath);//WID系统
//        registry.addResourceHandler("/SPPics/SP/**")
//                .addResourceLocations("file:"+goodsPath);//inux系统
//        super.addResourceHandlers(registry);
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
            logger.info("返回的json格式=====>"+JSON.toJSONString(result));
            logger.info("**********************************************************************************************************结束*********************************************************************************************************************");
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    private boolean validateLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start=System.currentTimeMillis();
        logger.info("**********************************************************************************************************开始("+start+")*********************************************************************************************************************");
        logger.info("startTime=====>"+JBDate.getNowDate(new Date().getTime()));
        String uri = request.getRequestURI();

        //不需要过滤
        boolean needFilter = isNeedFilter(uri);
        if (needFilter) { //不需要过滤直接传给下一个过滤器
            String requestSign = request.getHeader("token");//获得请求签名，如sign=19e907700db7ad91318424a97c54ed57

            if (StringUtils.isEmpty(requestSign)) {
                logger.warn("token 为空 登录验证失败，请求接口：{}，请求IP：{}，请求参数：{},请求类型:{}",
                        request.getRequestURI(), getIpAddress(request), JSON.toJSONString(request.getParameterMap()),request.getMethod());
                Result result = new Result();
                result.setCode(ResultCode.Token_NOT).setMessage("Token_not_find");
                responseResult(response, result);
                return false;
            }else{
                //验证token是否正确
                String userName= JwtTokenUtil.getUsername(requestSign);
                boolean flag = JwtTokenUtil.verify(requestSign,userName);
                if(flag==true){
                    //验证token是否过期
                    boolean expirFlag=JwtTokenUtil.canTokenBeRefreshed(requestSign);
                    if(expirFlag==false){
                        Result result = new Result();
                        result.setCode(ResultCode.Token_IS_LOSE).setMessage(ResultGenerator.LOSE_EFFECT_TOKEN);
                        responseResult(response, result);
                        return false;
                    }else{

                          BaseUser baseUser = JSONObject.parseObject(redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"system_user_login_"+userName),BaseUser.class);
                            RequestWrapper myRequestWrapper = new RequestWrapper((HttpServletRequest) request);
                            String body = myRequestWrapper.getBody();
//                            logger.info("请求参数:"+body);
                            //获取六位码
                            if(StringUtils.isNotEmpty(body)){
                                JSONObject jsonObject=JSONObject.parseObject(body);
                                if(jsonObject!=null){
                                    String sixCode=jsonObject.getString("sixCode");
                                    if(StringUtils.isNotEmpty(sixCode)){
                                        logger.info("当前登录用户信息=====>"+redisUtil.get(sixCode,sixCode+"_system_user_login_"+userName));
                                        DaQxYhda sixCode_daYgda = JSONObject.parseObject(redisUtil.get(sixCode,sixCode+"_system_user_login_"+userName.toLowerCase()),DaQxYhda.class);
                                        if(sixCode_daYgda!=null){
                                            // 验证区域服务器路径是否正确(只有区域应用程序和a8Redis应用程序随着区域服务器改变)
                                            BaseOrg org=null;
                                            String orgId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode);//通过五位码获取组织id
                                            if(!SystemConstant.DEFAULT_REDIS_KEY.equals(sixCode)){
                                                String jsonBaseRedisDatasource1=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,orgId+"_baseRedisDatasource");
                                                if(StringUtils.isNotEmpty(jsonBaseRedisDatasource1)){
                                                    BaseRedisDatasource baseRedisDatasource1= JSONObject.parseObject(jsonBaseRedisDatasource1, BaseRedisDatasource.class);
                                                    if(baseRedisDatasource1!=null){
                                                        logger.info("当前redis地址={}",baseRedisDatasource1.getHost()+":"+baseRedisDatasource1.getPort()+","+baseRedisDatasource1.getDbIndex()+"号库");
                                                    }
                                                }
                                            }
                                            String orgJson=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,orgId+"_data");
                                            if(StringUtils.isNotEmpty(orgJson)){
                                                org=JSONObject.parseObject(orgJson,BaseOrg.class);
                                                logger.info("areaServiceUrl是从redis中获取:=====>"+org.getAreaServiceUrl());
                                            }else{
                                                org=baseOrgService.findById("",orgId);
                                                redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,org.getId()+"_data", JSONObject.toJSONString(org));
                                                logger.info("areaServiceUrl是从数据库中获取:=====>"+org.getAreaServiceUrl());
                                            }
                                            if(org!=null){
                                                String areaServiceUrl=org.getAreaServiceUrl();
                                                if(StringUtils.isNotEmpty(areaServiceUrl)){
                                                    URI ip =null;
                                                    try {
                                                        StringBuffer requestURL = request.getRequestURL();
                                                        ip = this.getIP(new URI(requestURL.toString()));

                                                    } catch (URISyntaxException e) {
                                                        e.printStackTrace();
                                                    }
                                                    areaServiceUrl=areaServiceUrl.toLowerCase();
                                                    if(ip.toString().toLowerCase().contains(areaServiceUrl)){
                                                    }else{
                                                        logger.info("areaServiceUrl:=====>"+areaServiceUrl);
                                                        logger.info("ip:=====>"+ip.toString().toLowerCase());
                                                        Result result = new Result();
                                                        result.setCode(ResultCode.FAIL).setMessage("更换区域服务器地址,请重试!");
                                                        responseResult(response, result);
                                                        return false;
                                                    }
                                                }
                                            }
                                            request.getSession().setAttribute("sixCode_daYgda",sixCode_daYgda);
                                            return true;
                                        }else{
                                            Result result = new Result();
                                            result.setCode(ResultCode.FAIL).setMessage("缓存中当前登录用户信息为空，请重新登录!");
                                            responseResult(response, result);
                                            return false;
                                        }
                                    }
                                }
                            }
                            request.getSession().setAttribute("baseUser",baseUser!=null?baseUser:new BaseUser());
                            return true;
                    }
                }else{
                    Result result = new Result();
                    result.setCode(ResultCode.Token_IS_LOSE).setMessage(ResultGenerator.LOSE_EFFECT_TOKEN);
                    responseResult(response, result);
                    return false;
                }
            }

        }

        return true;
    }

    private String getIpAddress(HttpServletRequest request) {
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



    /**
     * @param uri
     * @Author: xxxxx
     * @Description: 是否需要过滤
     * @Date: 2018-03-12 13:20:54
     */
    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if (includeUrl.equals(uri)) {
                return false;
            }
        }

        return true;
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
//    @Bean
//    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
//        logger.info("RequestMappingHandlerMapping:");
//        RequestMappingHandlerMapping handlerMapping = new CustomRequestMappingHandlerMapping();
//        handlerMapping.setOrder(0);
//        handlerMapping.setInterceptors(getInterceptors());
//        return handlerMapping;
//    }


}
