package com.springboot.web.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.springboot.common.DynamicDataSource;
import com.springboot.common.RedisUtil;
import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.datasource.BaseDatasource;
import com.springboot.model.user.BaseUser;
import com.springboot.service.datasource.BaseDatasourceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/datasource")
public class BaseDatasourceController {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Resource
    private BaseDatasourceService baseDatasourceService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    @Value("${server.port}")
    private String port;

    @PostMapping("/add")
    public Result add(@RequestBody BaseDatasource baseDatasource) {
        Result result=null;
        BaseUser baseUser_op = RequestUtils.getRequestUser();
        Map<String,String> param=new HashMap<String,String>();
//        if(StringUtils.isNotEmpty(baseUser_op.getOrgId())){
            baseDatasource.setType("sqlServer");
            baseDatasource.setUrl("jdbc:sqlserver://"+baseDatasource.getName()+";DatabaseName="+baseDatasource.getCode());
            baseDatasource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            baseDatasourceService.insertBean("",baseDatasource);
            //新增数据源后放入redis缓存中
            List<BaseDatasource> list=baseDatasourceService.getList("",param);
            redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"dataSourcesList", JSON.toJSON(list).toString());
            //创建数据库连接
            try {
                dynamicDataSource.createDataSourceWithCheck(baseDatasource);
            } catch (Exception e) {
                e.printStackTrace();
            }
            result= ResultGenerator.genSuccessResult();
//        }else{
//            result= ResultGenerator.genErrorSetMsgErrorResult("用户所属组织为空!");
//        }
        return result;
    }
    @PostMapping("/delete")
    public Result delete(@RequestBody BaseDatasource baseDatasource) {

        baseDatasourceService.deleteById("",baseDatasource.getId());

        return ResultGenerator.genSuccessResult();
    }
    @PostMapping("/update")
    public Result update(@RequestBody BaseDatasource baseDatasource) {
        Result result=null;
        Map<String,String> param=new HashMap<String,String>();
        BaseUser baseUser_op = RequestUtils.getRequestUser();
//        if(StringUtils.isNotEmpty(baseUser_op.getOrgId())){
            baseDatasource.setUrl("jdbc:sqlserver://"+baseDatasource.getName()+";DatabaseName="+baseDatasource.getCode());

            baseDatasourceService.updateById("",baseDatasource);
            List<BaseDatasource> list=baseDatasourceService.getList("",param);
            redisUtil.set(SystemConstant.DEFAULT_REDIS_KEY,"dataSourcesList", JSON.toJSON(list).toString());
            //删除数据源
            dynamicDataSource.delDatasources(baseDatasource.getId());
            result= ResultGenerator.genSuccessResult();
//        }else{
//            result= ResultGenerator.genErrorSetMsgErrorResult("用户所属组织为空!");
//        }
        return result;
    }
    @PostMapping("/list")
    @com.springboot.configurer.JSON(type = BaseDatasource.class, filter = "yhbh,pageNo,pageSize")
    public Result list(@RequestBody BaseDatasource baseDatasource) {
        BaseUser baseUser = RequestUtils.getRequestUser();
        Map<String,String> param=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(baseDatasource.getOrgId())){
            param.put("orgId",baseDatasource.getOrgId());
        }
        Page<BaseDatasource> page = new Page<BaseDatasource>(baseDatasource.getPageNo(), baseDatasource.getPageSize());
        Page<BaseDatasource> baseDatasourceList = baseDatasourceService.getPage("",page,param);

        return ResultGenerator.genSuccessResult(baseDatasourceList);
    }

    //验证数据库连接
    @PostMapping("/checkDataSource")
    public Result checkDataSource(@RequestBody BaseDatasource baseDatasource) {
        Result result=null;
        String driverClassName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://"+baseDatasource.getName()+";DatabaseName="+baseDatasource.getCode();
        String userName=baseDatasource.getUserName();
        String passWord=baseDatasource.getPassWord();
        String databasetype="sqlServer";
        Boolean flag=dynamicDataSource.testDatasource("",driverClassName,url,userName,passWord);
        if(flag==true){
            result= ResultGenerator.genSuccessResult();
        }else{
            result= ResultGenerator.genErrorSetMsgErrorResult("失败");
        }
        return result;
    }
    @PostMapping("/releaseConnection")
    public Result releaseConnection(@RequestBody String jsons) {
        Result result=null;
        JSONObject jsonObject=JSONObject.parseObject(jsons);
        if(jsonObject!=null){
            String orgId=jsonObject.getString("orgId");
            BaseDatasource baseDatasource=baseDatasourceService.getBeanByOrgId("",orgId);
            if(baseDatasource!=null){
                Map<Object, Object> param=dynamicDataSource.getDynamicTargetDataSources();
                String datasourceId= baseDatasource.getId();
                DruidDataSource druidDataSource=(DruidDataSource)param.get(datasourceId);
                param.remove(datasourceId);
                if(druidDataSource!=null){
                    druidDataSource.close();
                }
            }
            result= ResultGenerator.genSuccessResult();
        }
        return result;
    }
//    @PostMapping(value="/findByUserName")
//    public Result findByUserName(@RequestBody String jsons) {
//        JSONObject json = JSONObject.parseObject(jsons);
//        System.out.println("请求的端口号为=========》"+port);
//        String count = baseUserService.findByUserName(json.get("id").toString(),json.get("name").toString());
//        return ResultGenerator.genSuccessResult(count);
//    }


}
