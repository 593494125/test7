package com.springboot.common;

import com.alibaba.fastjson.JSONObject;
import com.springboot.core.ServiceException;
import com.springboot.model.datasource.BaseDatasource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Aspect
@Component
@Order(-1)
@Slf4j
public class IdAspect {

    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private DynamicDataSource dynamicDataSource;

//    @Pointcut("execution(* com.jjy.modules.core.*.save*(..)) || execution(* com.jjy.modules.service.impl..*.save*(..))")
    @Pointcut("execution(* com.springboot.service.impl..*.*(..))")
    public void appPointCut() {
    }
//    @Pointcut("execution(* com.springboot.service.impl..*.insert*(..))")
//    public void logPointCut() {
//    }

    @Before("appPointCut()")
    public void appPointCut(JoinPoint joinPoint) throws Throwable {
        //添加id
        String methodName=joinPoint.getSignature().getName();
//        log.info("方法名:=====>"+methodName);
        if(methodName.startsWith("insert")){
            IdWork idWorker = new IdWork(1, 0);

            long newid = idWorker.nextId();
            String id=String.valueOf(newid);
            //获取当前
            Object object=joinPoint.getArgs()[1];
            Class<? extends Object> clz=object.getClass();
            clz.getMethod("setId",String.class).invoke(object, id);//获得方\
        }
        //获取当前参数值
        Object object=joinPoint.getArgs()[0];
        //通过组织id获取数据源id
        if(StringUtils.isNotEmpty(object.toString())){
                String sixCode=object.toString();
                //默认切换到主数据源,进行整体资源的查找
                DBContextHolder.clearDataSource();
                //此处数据源从redis
               if(StringUtils.isNotEmpty(sixCode)){
                   //创建数据源连接&检查 若存在则不需重新创建
                    try {
                        //获取数据源信息
                        String json=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"dataSourcesList");
                        String orgId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+sixCode);//通过五位码获取组织id
                        List<BaseDatasource> dataSourcesList = JSONObject.parseArray(json,BaseDatasource.class);
                        if(dataSourcesList!=null&&dataSourcesList.size()>0){
                            for (BaseDatasource dataSource : dataSourcesList) {
                                if(dataSource.getOrgId().equals(orgId)){

                                        boolean flag1=dynamicDataSource.createDataSourceWithCheck(dataSource);
                                        if(flag1==false){
                                            int k=1/0;
                                        }
                                        //切换到该数据源
                                        DBContextHolder.setDataSource(dataSource.getId());
                                        break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new CustomeException("应用数据库连接异常,请稍后重试!");
                    }
               }
//            }
        }else{
            //默认切换到主数据源,进行整体资源的查找
            DBContextHolder.clearDataSource();
        }

    }

}

