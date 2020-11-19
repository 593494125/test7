package com.springboot.service.impl.employee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.RedisUtil;
import com.springboot.common.SystemConstant;
import com.springboot.dao.department.DaBmdaMapper;
import com.springboot.dao.employee.DaYgdaMapper;
import com.springboot.model.MoKuaiAuthJson;
import com.springboot.model.department.DaBmda;
import com.springboot.model.employee.DaYgda;
import com.springboot.model.employee.DaYgdaJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.employee.DaYgdaService;
import com.springboot.service.posparm.YwPosParmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 员工档案 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DaYgdaServiceImpl extends ServiceImpl<DaYgdaMapper, DaYgda> implements DaYgdaService {

    @Resource
    private DaYgdaMapper daYgdaMapper;
    @Resource
    private DaBmdaMapper daBmdaMapper;
    @Resource
    private YwPosParmService ywPosParmService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public DaYgda findByNameAndPassWord(String orgId, String userName, String passWord) {
        DaYgda queryBean=new DaYgda();
        queryBean.setYgbh(userName);
        queryBean.setYhmm(passWord);
        DaYgda bean=daYgdaMapper.selectOne(queryBean);
        if(bean!=null){
            DaBmda queryBmdaBean=new DaBmda();
            queryBmdaBean.setBmbh(bean.getSsbm());
            DaBmda daBmda=daBmdaMapper.selectOne(queryBmdaBean);
            if(daBmda!=null){
                bean.setBmmc(daBmda.getBmmc());
            }
        }
        return bean;
    }

    @Override
    public List<DaYgdaJson> findCgy(String sixCode) {
        List<DaYgdaJson> list=daYgdaMapper.findCgy();
        return list;
    }
    @Override
    public Map<String,String> findMoKuaiAuth1(String sixCode,String groupId) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("YGBH","");
        List<MoKuaiAuthJson> list=daYgdaMapper.findMoKuaiAuth1(groupId);
        List<String> codeList=list.stream().map(MoKuaiAuthJson::getCode).collect(Collectors.toList());
        map=this.getParam(map,codeList);
        return map;
    }

    @Override
    public List<String> getGroupIds(String sixCode) {
        List<String> list=daYgdaMapper.getGroupIds();
        return list;
    }

    @Override
    public Map<String,String> findMoKuaiAuth(String sixCode, String yhbh) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("YGBH",yhbh);

        List<MoKuaiAuthJson> list=daYgdaMapper.findMoKuaiAuth(yhbh);
        List<String> codeList=list.stream().map(MoKuaiAuthJson::getCode).collect(Collectors.toList());
        map=this.getParam(map,codeList);

//        MoKuaiAuth bean=daYgdaMapper.findMoKuaiAuth(yhbh);

        return map;
    }
    public Map<String,String> getParam(Map<String,String> map,List<String> codeList){
        if(codeList!=null&&codeList.size()>0){
            if(codeList.contains("CR")){
                map.put("CR","1");
            }else{
                map.put("CR","0");
            }
            if(codeList.contains("CT")){
                map.put("CT","1");
            }else{
                map.put("CT","0");
            }
            if(codeList.contains("DC")){
                map.put("DC","1");
            }else{
                map.put("DC","0");
            }
            if(codeList.contains("DR")){
                map.put("DR","1");
            }else{
                map.put("DR","0");
            }
            if(codeList.contains("PF")){
                map.put("PF","1");
            }else{
                map.put("PF","0");
            }
            if(codeList.contains("PT")){
                map.put("PT","1");
            }else{
                map.put("PT","0");
            }
            if(codeList.contains("LC")){
                map.put("LC","1");
            }else{
                map.put("LC","0");
            }
            if(codeList.contains("LT")){
                map.put("LT","1");
            }else{
                map.put("LT","0");
            }
            if(codeList.contains("QC")){
                map.put("QC","1");
            }else{
                map.put("QC","0");
            }
            if(codeList.contains("QT")){
                map.put("QT","1");
            }else{
                map.put("QT","0");
            }
            if(codeList.contains("KP")){
                map.put("KP","1");
            }else{
                map.put("KP","0");
            }
            if(codeList.contains("KPJZ")){
                map.put("KPJZ","1");
            }else{
                map.put("KPJZ","0");
            }
            if(codeList.contains("SY")){
                map.put("SY","1");
            }else{
                map.put("SY","0");
            }
            if(codeList.contains("POS")){
                map.put("POS","1");
            }else{
                map.put("POS","0");
            }
            if(codeList.contains("XS")){
                map.put("XS","1");
            }else{
                map.put("XS","0");
            }
            if(codeList.contains("XF")){
                map.put("XF","1");
            }else{
                map.put("XF","0");
            }
            if(codeList.contains("FK")){
                map.put("FK","1");
            }else{
                map.put("FK","0");
            }
            if(codeList.contains("JF")){
                map.put("JF","1");
            }else{
                map.put("JF","0");
            }
            if(codeList.contains("KHDZD")){
                map.put("KHDZD","1");
            }else{
                map.put("KHDZD","0");
            }
            if(codeList.contains("GYSDZD")){
                map.put("GYSDZD","1");
            }else{
                map.put("GYSDZD","0");
            }
            if(codeList.contains("SPDA")){
                map.put("SPDA","1");
            }else{
                map.put("SPDA","0");
            }
            if(codeList.contains("BMDA")){
                map.put("BMDA","1");
            }else{
                map.put("BMDA","0");
            }
            if(codeList.contains("KHDA")){
                map.put("KHDA","1");
            }else{
                map.put("KHDA","0");
            }
            if(codeList.contains("GYSDA")){
                map.put("GYSDA","1");
            }else{
                map.put("GYSDA","0");
            }
            if(codeList.contains("YGDA")){
                map.put("YGDA","1");
            }else{
                map.put("YGDA","0");
            }

        }
        return map;
    }

    @Override
    public Map<String,String> findGnMoKuaiAuth(String sixCode, DaQxYhda daYgda,String code,String qtjqm) {
        Map<String,String> map=new HashMap<String,String>();
        Map<String,String> param=new HashMap<String,String>();
        //获取当前用户的模块权限
        //从redis中通过yhbh获取groupId
        String groupId=daYgda.getGroupId();
        if(StringUtils.isEmpty(groupId)){
            String userJson=redisUtil.get(sixCode,sixCode+ SystemConstant.REDIS_YHDA_TABLE);
            if(StringUtils.isNotEmpty(userJson)){
                List<DaQxYhda> userJsonList= JSONArray.parseArray(userJson,DaQxYhda.class);
                if(userJsonList!=null&&userJsonList.size()>0){
                    for (DaQxYhda daQxYhda:userJsonList) {
                        if(daYgda.getYhbh().toLowerCase().equals(daQxYhda.getYhbh().toLowerCase())){
                            groupId=daQxYhda.getGroupId();
                            break;
                        }
                    }
                }
            }
        }
        String moKuaiJson=redisUtil.get(sixCode,sixCode+SystemConstant.REDIS_MOKUAI_TABLE+groupId);
        if(StringUtils.isNotEmpty(moKuaiJson)){
            JSONObject moKuaiJsonObject=JSONObject.parseObject(moKuaiJson);
            param=this.getMap("",moKuaiJsonObject);
            boolean containsKey = param.containsKey(code);
            if(containsKey){
                map.put(code,"1");
            }else{
                map.put(code,"0");
            }
            if(StringUtils.isNotEmpty(qtjqm)){
                String bmbh=ywPosParmService.findBmbhByQtjqm(sixCode,qtjqm);
                map.put("ywPosbmbh",bmbh);
            }else{
                map.put("ywPosbmbh","");
            }
        }else{
            List<MoKuaiAuthJson> list=daYgdaMapper.findMoKuaiAuth(daYgda.getYgbh());
            List<String> codeList=list.stream().map(MoKuaiAuthJson::getCode).collect(Collectors.toList());
            if(codeList!=null&&codeList.size()>0){
                if(codeList.contains(code)){
                    map.put(code,"1");
                }else{
                    map.put(code,"0");
                }
                //获取前台部门编号
                if(StringUtils.isNotEmpty(qtjqm)){
                    String bmbh=ywPosParmService.findBmbhByQtjqm(sixCode,qtjqm);
                    map.put("ywPosbmbh",bmbh);
                }else{
                    map.put("ywPosbmbh","");
                }
            }
        }
        return map;
    }

    @Override
    public List<MoKuaiAuthJson> newFindMoKuaiAuth(String sixCode,String yhbh) {
        List<MoKuaiAuthJson> list=daYgdaMapper.findMoKuaiAuth(yhbh);
        return list;
    }

    @Override
    public List<DaQxYhda> getList(String sixCode) {
        List<DaQxYhda> list=daYgdaMapper.getList();

        return list;
    }

    @Override
    public Map<String,String> getMap(String sixCode,JSONObject moKuaiJsonObject){
        Map<String,String> map=new HashMap<String,String>();
        String CR=moKuaiJsonObject.getString("CR");
        String CT=moKuaiJsonObject.getString("CT");
        String DC=moKuaiJsonObject.getString("DC");
        String DR=moKuaiJsonObject.getString("DR");
        String PF=moKuaiJsonObject.getString("PF");
        String PT=moKuaiJsonObject.getString("PT");
        String LC=moKuaiJsonObject.getString("LC");
        String LT=moKuaiJsonObject.getString("LT");
        String QC=moKuaiJsonObject.getString("QC");
        String QT=moKuaiJsonObject.getString("QT");
        String KP=moKuaiJsonObject.getString("KP");
        String KPJZ=moKuaiJsonObject.getString("KPJZ");
        String SY=moKuaiJsonObject.getString("SY");
        String POS=moKuaiJsonObject.getString("POS");
        String XS=moKuaiJsonObject.getString("XS");
        String XF=moKuaiJsonObject.getString("XF");
        String FK=moKuaiJsonObject.getString("FK");
        String JF=moKuaiJsonObject.getString("JF");
        String KHDZD=moKuaiJsonObject.getString("KHDZD");
        String GYSDZD=moKuaiJsonObject.getString("GYSDZD");
        String SPDA=moKuaiJsonObject.getString("SPDA");
        String BMDA=moKuaiJsonObject.getString("BMDA");
        String KHDA=moKuaiJsonObject.getString("KHDA");
        String GYSDA=moKuaiJsonObject.getString("GYSDA");
        String YGDA=moKuaiJsonObject.getString("YGDA");
        map.put("CR",CR);
        map.put("CT",CT);
        map.put("DC",DC);
        map.put("DR",DR);
        map.put("PF",PF);
        map.put("PT",PT);
        map.put("LC",LC);
        map.put("LT",LT);
        map.put("QC",QC);
        map.put("QT",QT);
        map.put("KP",KP);
        map.put("KPJZ",KPJZ);
        map.put("SY",SY);
        map.put("POS",POS);
        map.put("XS",XS);
        map.put("XF",XF);
        map.put("FK",FK);
        map.put("JF",JF);
        map.put("KHDZD",KHDZD);
        map.put("GYSDZD",GYSDZD);
        map.put("SPDA",SPDA);
        map.put("BMDA",BMDA);
        map.put("KHDA",KHDA);
        map.put("GYSDA",GYSDA);
        map.put("YGDA",YGDA);
        return map;
    }
}
