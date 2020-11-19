package com.springboot.web.mxcommon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.common.RedisUtil;
import com.springboot.common.RequestUtils;
import com.springboot.common.SystemConstant;
import com.springboot.core.Result;
import com.springboot.core.ResultGenerator;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.posparm.YwPosSaleDay;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.finance.*;
import com.springboot.service.org.BaseOrgService;
import com.springboot.service.posparm.YwPosMxService;
import com.springboot.service.purchase.YwCgRkdmxService;
import com.springboot.service.stock.YwKcKcpcdMxService;
import com.springboot.service.transfer.YwDbRkdmxService;
import com.springboot.service.wholesale.YwPfRkdmxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/api/ywMxPrintCommon")
@Slf4j
public class YwMxPrintCommonController{

    @Resource
    private YwCgRkdmxService ywCgRkdmxService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private BaseOrgService baseOrgService;
    @Resource
    private YwDbRkdmxService ywDbRkdmxService;
    @Resource
    private YwPfRkdmxService ywPfRkdmxService;
    @Resource
    private YwKcKcpcdMxService ywKcKcpcdMxService;
    @Resource
    private YwPosMxService ywPosMxService;
    @Resource
    private CwNewXhskdService cwNewXhskdService;
    @Resource
    private CwNewXhfydMxService cwNewXhfydMxService;
    @Resource
    private CwNewJhfkdService cwNewJhfkdService;
    @Resource
    private CwNewJhfydMxService cwNewJhfydMxService;
    @Resource
    private YwPosMxSpkhService ywPosMxSpkhService;
    @PostMapping("/ywDetail")
    public Result list(@RequestBody YwMxPrintCommon ywMxPrintCommon) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Result result=null;
        YwCgRkdPrintmx bean =null;
        if(StringUtils.isNotEmpty(ywMxPrintCommon.getSixCode())&&StringUtils.isNotEmpty(ywMxPrintCommon.getPzh())&&StringUtils.isNotEmpty(ywMxPrintCommon.getYwType())&&StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())){
            String orgId=redisUtil.get(SystemConstant.DEFAULT_REDIS_KEY,"orgId_"+ywMxPrintCommon.getSixCode());//通过六位码获取组织id
            BaseOrg org=null;
            if(StringUtils.isNotEmpty(orgId)){
                 org=baseOrgService.findById("",orgId);
            }else{
                return ResultGenerator.genErrorSetMsgErrorResult("缓存中根据五位码获取orgId为空!");
            }
            if(SystemConstant.CR_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.CT_TYPE.equals(ywMxPrintCommon.getYwType())){//采购入和采购退
                if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgZx");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgZx");
                    }else{
                        bean = ywCgRkdmxService.getCgDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }else if("2".equals(ywMxPrintCommon.getPrintType())){//横向
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgHx");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgHx");
                    }else{
                        result=ResultGenerator.genErrorSetMsgErrorResult("缓存中无该凭证号:"+ywMxPrintCommon.getPzh()+"对应的缓存数据!");
                    }
                }else if("3".equals(ywMxPrintCommon.getPrintType())){//汇总
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgHz");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgHz");
                    }else{
                        bean = ywCgRkdmxService.getCgDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }else if("5".equals(ywMxPrintCommon.getPrintType())){//颜色汇总
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgysHz");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgysHz");
                    }else{
                        bean = ywCgRkdmxService.getCgDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgysHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }
//                else if("4".equals(ywMxPrintCommon.getPrintType())){//采购退货
//                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgHz");
//                    if(StringUtils.isNotEmpty(json)){
//                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
//                    }else{
//                        result=ResultGenerator.genErrorSetMsgErrorResult("缓存中无该凭证号:"+ywMxPrintCommon.getPzh()+"对应的缓存数据!");
//                    }
//                }
            }else if(SystemConstant.DC_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.DR_TYPE.equals(ywMxPrintCommon.getYwType())){//调拨出
                if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDcZx");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDcZx");
                    }else{
                        bean = ywDbRkdmxService.getDcDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        if(StringUtils.isEmpty(bean.getAcct())){
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDcZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        }
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }else if("2".equals(ywMxPrintCommon.getPrintType())){//横向
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDbHx");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDbHx");
                    }else{
                        result=ResultGenerator.genErrorSetMsgErrorResult("缓存中无该凭证号:"+ywMxPrintCommon.getPzh()+"对应的缓存数据!");
                    }
                }else if("3".equals(ywMxPrintCommon.getPrintType())){//汇总
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDcHz");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDcHz");
                    }else{
                        bean = ywDbRkdmxService.getDcDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        if(StringUtils.isEmpty(bean.getAcct())){
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDcHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        }
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }else if("5".equals(ywMxPrintCommon.getPrintType())){//颜色汇总
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDcysHz");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDcysHz");
                    }else{
                        bean = ywDbRkdmxService.getDcDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        if(StringUtils.isEmpty(bean.getAcct())){
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printDcysHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        }
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }
            }else if(SystemConstant.PF_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.PT_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.LC_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.LT_TYPE.equals(ywMxPrintCommon.getYwType())){
                if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
                    if(StringUtils.isNotEmpty(ywMxPrintCommon.getYwType())&&(SystemConstant.LC_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.LT_TYPE.equals(ywMxPrintCommon.getYwType()))){
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsZx");//连锁
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsZx");
                        }else{
                            bean = ywPfRkdmxService.getPfDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                            result=ResultGenerator.genSuccessResult(bean);
                            log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                        }
                    }else{
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfZx");//批发
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfZx");
                        }else{
                            bean = ywPfRkdmxService.getPfDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                            result=ResultGenerator.genSuccessResult(bean);
                            log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                        }
                    }
                }else if("2".equals(ywMxPrintCommon.getPrintType())){//横向
                    if(StringUtils.isNotEmpty(ywMxPrintCommon.getYwType())&&(SystemConstant.LC_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.LT_TYPE.equals(ywMxPrintCommon.getYwType()))){
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsHx");//连锁
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsHx");
                        }else{
                            result=ResultGenerator.genErrorSetMsgErrorResult("缓存中无该凭证号:"+ywMxPrintCommon.getPzh()+"对应的缓存数据!");
                        }
                    }else{
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfHx");
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfHx");
                        }else{
                            result=ResultGenerator.genErrorSetMsgErrorResult("缓存中无该凭证号:"+ywMxPrintCommon.getPzh()+"对应的缓存数据!");
                        }
                    }
                }else if("3".equals(ywMxPrintCommon.getPrintType())){//汇总
                    if(StringUtils.isNotEmpty(ywMxPrintCommon.getYwType())&&(SystemConstant.LC_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.LT_TYPE.equals(ywMxPrintCommon.getYwType()))){
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsHz");
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsHz");
                        }else{
                            bean = ywPfRkdmxService.getPfDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                            result=ResultGenerator.genSuccessResult(bean);
                            log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                        }
                    }else{
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfHz");
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfHz");
                        }else{
                            bean = ywPfRkdmxService.getPfDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                            result=ResultGenerator.genSuccessResult(bean);
                            log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                        }
                    }
                }else if("5".equals(ywMxPrintCommon.getPrintType())) {//颜色汇总
                    if(StringUtils.isNotEmpty(ywMxPrintCommon.getYwType())&&(SystemConstant.LC_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.LT_TYPE.equals(ywMxPrintCommon.getYwType()))){
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsysHz");
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsysHz");
                        }else{
                            bean = ywPfRkdmxService.getPfDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsysHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                            result=ResultGenerator.genSuccessResult(bean);
                            log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                        }
                    }else{
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfysHz");
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfysHz");
                        }else{
                            bean = ywPfRkdmxService.getPfDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPfysHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                            result=ResultGenerator.genSuccessResult(bean);
                            log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                        }
                    }
                }
            }else if(SystemConstant.KP_TYPE.equals(ywMxPrintCommon.getYwType())||SystemConstant.KPJZ_TYPE.equals(ywMxPrintCommon.getYwType())){//库存盘存单
                if(StringUtils.isEmpty(ywMxPrintCommon.getSgdj())){
                    result= ResultGenerator.genErrorSetMsgErrorResult("sgdj参数为空!");
                }else{
                    if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcZx");
//                        if(StringUtils.isNotEmpty(json)){
//                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
//                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcZx");
//                        }else{
                            bean = ywKcKcpcdMxService.getKcDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,"");
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                            result=ResultGenerator.genSuccessResult(bean);
                            log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
//                        }
                    }else if("2".equals(ywMxPrintCommon.getPrintType())){//横向
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcHx");
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcHx");
                        }else{
                            result=ResultGenerator.genErrorSetMsgErrorResult("缓存中无该凭证号:"+ywMxPrintCommon.getPzh()+"对应的缓存数据!");
                        }
                    }else if("3".equals(ywMxPrintCommon.getPrintType())){//汇总
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcHz");
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcHz");
                        }else{
                            bean = ywKcKcpcdMxService.getKcDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,"");
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                            result=ResultGenerator.genSuccessResult(bean);
                            log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                        }
                    }else if("5".equals(ywMxPrintCommon.getPrintType())) {//颜色汇总
                        String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcysHz");
                        if(StringUtils.isNotEmpty(json)){
                            result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                            log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcysHz");
                        }else{
                            bean = ywKcKcpcdMxService.getKcDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,"");
                            redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printKcysHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                            result=ResultGenerator.genSuccessResult(bean);
                            log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                        }
                    }
                }
            }else if(SystemConstant.LSPOS_TYPE.equals(ywMxPrintCommon.getYwType())){//零售单
                if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposZx");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposZx");
                    }else{
                        bean = ywPosMxService.getLsposDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }else if("3".equals(ywMxPrintCommon.getPrintType())){//零售汇总
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposHz");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposHz");
                    }else{
                        bean = ywPosMxService.getLsposDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }else if("4".equals(ywMxPrintCommon.getPrintType())){//零售退货
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposTh");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposTh");
                    }else{
                        bean = ywPosMxService.getLsposDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposTh", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }else if("5".equals(ywMxPrintCommon.getPrintType())) {//颜色汇总
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposysHz");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposysHz");
                    }else{
                        bean = ywPosMxService.getLsposDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org);
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printLsposysHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }
            }else if(SystemConstant.SY_TYPE.equals(ywMxPrintCommon.getYwType())){//损溢打印
                if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyZx");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyZx");
                    }else{
                        bean = ywKcKcpcdMxService.getKcDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }else if("2".equals(ywMxPrintCommon.getPrintType())){//横向
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyHx");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyHx");
                    }else{
                        result=ResultGenerator.genErrorSetMsgErrorResult("缓存中无该凭证号:"+ywMxPrintCommon.getPzh()+"对应的缓存数据!");
                    }
                }else if("3".equals(ywMxPrintCommon.getPrintType())){//汇总
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyHz");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyHz");
                    }else{
                        bean = ywKcKcpcdMxService.getKcDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }else if("5".equals(ywMxPrintCommon.getPrintType())){//汇总
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyysHz");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyysHz");
                    }else{
                        bean = ywKcKcpcdMxService.getKcDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printSyysHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }
            //财务单据
            }else if(SystemConstant.XHSK_TYPE.equals(ywMxPrintCommon.getYwType())){
                //财务收款单，财务费用单，进货付款单，进货费用单
                if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
//                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printXhskZx");
//                    if(StringUtils.isNotEmpty(json)){
//                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
//                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printXhskZx");
//                    }else{
                        bean = cwNewXhskdService.getCwXhskdDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
//                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printXhskZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
//                    }
                }
            }else if(SystemConstant.XHFY_TYPE.equals(ywMxPrintCommon.getYwType())){
                if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
//                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printXhfyZx");
//                    if(StringUtils.isNotEmpty(json)){
//                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
//                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printXhfyZx");
//                    }else{
                        bean = cwNewXhfydMxService.getCwXhfydDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
//                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printXhfyZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
//                    }
                }
            }else if(SystemConstant.JHFK_TYPE.equals(ywMxPrintCommon.getYwType())){
                if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
//                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printJhfkZx");
//                    if(StringUtils.isNotEmpty(json)){
//                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
//                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printJhfkZx");
//                    }else{
                        bean = cwNewJhfkdService.getCwJhfkdDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
//                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printJhfkZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
//                    }
                }
            }else if(SystemConstant.JHFY_TYPE.equals(ywMxPrintCommon.getYwType())){
                if("1".equals(ywMxPrintCommon.getPrintType())){//纵向
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printJhfyZx");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printJhfyZx");
                    }else{
                        bean = cwNewJhfydMxService.getCwJhfydDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printJhfyZx", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
                }
            //联营结算
            }else if(SystemConstant.POSSPKH_TYPE.equals(ywMxPrintCommon.getYwType())){
                    String json=redisUtil.get(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPosspkhHz");
                    if(StringUtils.isNotEmpty(json)){
                        result=ResultGenerator.genSuccessResult(JSONObject.parse(json));
                        log.info("该数据是从redis缓存中获取key为:"+ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPosspkhHz");
                    }else{
                        bean = ywPosMxSpkhService.getYwPosMxSpkhDetailByPzh(ywMxPrintCommon.getSixCode(),ywMxPrintCommon,daYgda,org,ywMxPrintCommon.getYwType());
                        redisUtil.setEx(ywMxPrintCommon.getSixCode(),ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printPosspkhHz", JSON.toJSONString(bean),7*24*60*60, TimeUnit.SECONDS);
                        result=ResultGenerator.genSuccessResult(bean);
                        log.info("该数据是从数据库中获取pzh为:"+ywMxPrintCommon.getPzh());
                    }
            }
        }else{
            result= ResultGenerator.genErrorSetMsgErrorResult("参数为空!");
        }
        return result;
    }
    //销售日结单
    @PostMapping("/ywSaleDayDetail")
    public Result ywSaleDayDetail(@RequestBody String jsons) {
        DaQxYhda daYgda = RequestUtils.getRequestUserId();
        Result result=null;
        YwPosSaleDay bean =null;
        if(StringUtils.isNotEmpty(jsons)){
            JSONObject jsonObject=JSONObject.parseObject(jsons);
            if(jsonObject!=null){
                String sixCode=jsonObject.getString("sixCode");
                String bmbh=jsonObject.getString("bmbh");
                String jqbh=jsonObject.getString("jqbh");
                String rq=jsonObject.getString("rq");
                if(StringUtils.isNotEmpty(sixCode)&&StringUtils.isNotEmpty(bmbh)&&StringUtils.isNotEmpty(jqbh)&&StringUtils.isNotEmpty(rq)){
                    bean = ywPosMxService.findYwSaleDayDetail(sixCode,bmbh,jqbh,rq,daYgda,null);
                    result=ResultGenerator.genSuccessResult(bean);
                }else{
                    result= ResultGenerator.genErrorSetMsgErrorResult("参数中存在空值!");
                }
            }
        }else{
            result= ResultGenerator.genErrorSetMsgErrorResult("参数为空!");
        }
        return result;
    }
}
