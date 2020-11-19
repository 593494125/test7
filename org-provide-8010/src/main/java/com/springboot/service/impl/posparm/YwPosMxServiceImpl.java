package com.springboot.service.impl.posparm;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.SystemConstant;
import com.springboot.dao.posparm.YwPosMxMapper;
import com.springboot.model.goods.DaSpBxbt;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.posparm.YwPosHz;
import com.springboot.model.posparm.YwPosMx;
import com.springboot.model.posparm.YwPosSaleDay;
import com.springboot.model.purchase.*;
import com.springboot.model.system.DaQjCsb;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.goods.DaSpSpdaMxService;
import com.springboot.service.posparm.YwPosHzService;
import com.springboot.service.posparm.YwPosMxService;
import com.springboot.service.purchase.YwCgRkdmxService;
import com.springboot.service.system.DaQjCsbService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * POS明细表 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-07-10
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwPosMxServiceImpl extends ServiceImpl<YwPosMxMapper, YwPosMx> implements YwPosMxService {

    @Resource
    private YwPosMxMapper ywPosMxMapper;
    @Resource
    private YwPosHzService ywPosHzService;
    @Resource
    private YwCgRkdmxService ywCgRkdmxService;
    @Resource
    private DaSpSpdaMxService daSpSpdaMxService;
    @Resource
    private DaQjCsbService daQjCsbService;
    @Override
    public YwCgRkdPrintmx getLsposDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        YwCgRkdPrintmx ywCgRkdPrintmx=new YwCgRkdPrintmx();

        //凭证号
        String pzh=ywMxPrintCommon.getPzh();
        ywCgRkdPrintmx.setPzh(pzh);

        //获取部门，供应商名称，日期
        YwPosHz ywPosHz=ywPosHzService.findByPzh(sixCode,ywMxPrintCommon.getPzh());
        //获取企业信息
        String companyName="";
        if(org!=null){
            companyName=org.getOrgName();
            ywCgRkdPrintmx.setCompanyName(companyName);
        }
        //获取单据名称
        String orderName= SystemConstant.YW_LSPOS_ORDER_NAME;
        ywCgRkdPrintmx.setOrderName(orderName);

        if(ywPosHz!=null) {
            String hyh=ywPosHz.getHyh();
            if(StringUtils.isNotEmpty(hyh)){
                ywCgRkdPrintmx.setHyh(hyh);
            }else{
                ywCgRkdPrintmx.setHyh("");
            }
            String bmmc = ywPosHz.getBmmc();//部门名称
            String bmbh = ywPosHz.getBmbh();//部门编号
            if(StringUtils.isNotEmpty(bmbh)){
                ywCgRkdPrintmx.setBmbh(bmbh);
            }else{
                ywCgRkdPrintmx.setBmbh("");
            }
            if(StringUtils.isNotEmpty(bmmc)){
                ywCgRkdPrintmx.setBmmc(bmmc);
            }else{
                ywCgRkdPrintmx.setBmmc("");
            }
            String kdrq = dateFormat.format(ywPosHz.getRq());//开单日期
            ywCgRkdPrintmx.setKdrq(kdrq);
            String tel = ywPosHz.getLxdh();//客户电话
            if(StringUtils.isNotEmpty(tel)){
                ywCgRkdPrintmx.setTel(tel);
            }else{
                ywCgRkdPrintmx.setTel("");
            }
            if(ywPosHz.getZl()!=null){//零售找零
                ywCgRkdPrintmx.setZl(String.format("%.2f", ywPosHz.getZl()));
            }else{
                ywCgRkdPrintmx.setZl("0.00");
            }
            String lydh=ywPosHz.getLydh();
            if(StringUtils.isNotEmpty(lydh)){
                ywCgRkdPrintmx.setLydh(lydh);
            }else{
                ywCgRkdPrintmx.setLydh("");
            }
            String bzxx = ywPosHz.getBzxx();//备注信息
            if(StringUtils.isNotEmpty(bzxx)){
                ywCgRkdPrintmx.setBzxx(bzxx);
            }else{
                ywCgRkdPrintmx.setBzxx("");
            }
            Double zje = ywPosHz.getLsje();//单据总金额
            if(zje!=null){
                ywCgRkdPrintmx.setZje(String.format("%.2f", zje));
            }else{
                ywCgRkdPrintmx.setZje("");
            }

            Double zsl = ywPosHz.getSl();//单据总数量
            ywCgRkdPrintmx.setZsl(zsl);
            if(StringUtils.isNotEmpty(ywPosHz.getHymc())){
                ywCgRkdPrintmx.setHymc(ywPosHz.getHymc());
            }else{
                ywCgRkdPrintmx.setHymc("");
            }
            if(StringUtils.isNotEmpty(ywPosHz.getHykh())){
                ywCgRkdPrintmx.setHykh(ywPosHz.getHykh());
            }else{
                ywCgRkdPrintmx.setHykh("");
            }
            if(ywPosHz.getJf()!=null){
                ywCgRkdPrintmx.setHyjf(String.format("%.0f", ywPosHz.getJf()));
            }else{
                ywCgRkdPrintmx.setHyjf("0");
            }
            if(ywPosHz.getDqjf()!=null){
                ywCgRkdPrintmx.setDqjf(String.format("%.0f", ywPosHz.getDqjf()));
            }else{
                ywCgRkdPrintmx.setDqjf("0");
            }
            if(ywPosHz.getDqye()!=null){
                ywCgRkdPrintmx.setHyczkye(String.format("%.2f", ywPosHz.getDqye()));
            }else{
                ywCgRkdPrintmx.setHyczkye("0.00");
            }
            //收款方式
            Double xj=ywPosHz.getXj();
            Double hyk=ywPosHz.getHyk();
            String zfqd=ywPosHz.getZfqd();
            Double wx=ywPosHz.getWx();
            Double ali=ywPosHz.getAli();
            LsSkfh lsSkfh=new LsSkfh();
            if(ali!=null){
                lsSkfh.setAli(String.format("%.2f", ali));
            }else{
                lsSkfh.setAli("");
            }
            if(wx!=null){
                lsSkfh.setWx(String.format("%.2f", wx));
            }else{
                lsSkfh.setWx("");
            }
            if(hyk!=null){
                lsSkfh.setHyk(String.format("%.2f", hyk));
            }else{
                lsSkfh.setHyk("");
            }
            if(xj!=null){
                lsSkfh.setXj(String.format("%.2f", xj));
            }else{
                lsSkfh.setXj("");
            }
            if(StringUtils.isNotEmpty(zfqd)){
                lsSkfh.setZfqd(ywPosHz.getZfqd());
            }else{
                lsSkfh.setZfqd("");
            }
            ywCgRkdPrintmx.setLsSkfh(lsSkfh);
            //支付返参调整
            //根据参数分类获取全局参数表中POS_FKLX
            List<LsSkfhJson> lsSkfhJsonList=new ArrayList<LsSkfhJson>();
            LsSkfhJson lsSkfhJson=new LsSkfhJson();
            List<DaQjCsb> daQjCsbList=daQjCsbService.findByCsfl(sixCode,SystemConstant.LSZF_CSFL_TYPE);
            String name1="";
            if(daQjCsbList!=null&&daQjCsbList.size()>0){
                lsSkfhJson= (LsSkfhJson) lsSkfhJson.clone();
                lsSkfhJson.setMc("现金");
                Double newxj=ywPosHz.getXj()+ywPosHz.getZl();
                lsSkfhJson.setVal(String.format("%.2f", newxj));
                lsSkfhJsonList.add(lsSkfhJson);

                lsSkfhJson= (LsSkfhJson) lsSkfhJson.clone();
                lsSkfhJson.setMc("会员卡");
                lsSkfhJson.setVal(String.format("%.2f", ywPosHz.getHyk()));
                lsSkfhJsonList.add(lsSkfhJson);

                for (DaQjCsb daQjCsb : daQjCsbList) {
                    String qybz=daQjCsb.getQybz();
                    String csbh=daQjCsb.getCsbh();
                    if(StringUtils.isNotEmpty(qybz)&&"1".equals(qybz)){
                        switch(csbh){
                            case "01" :
                                name1="xyk";
                                break;
                            case "02" :
                                name1="ng";
                                break;
                            case "03" :
                                name1="ngq";
                                break;
                            case "04" :
                                name1="yhe";
                                break;
                            case "05" :
                                name1="zdyzf";
                                break;
                            case "06" :
                                name1="zdyzf1";
                                break;
                            case "07" :
                                name1="zdyzf2";
                                break;
                            case "08" :
                                name1="zdyzf3";
                                break;
                            case "09" :
                                name1="zdyzf4";
                                break;
                            case "10" :
                                name1="zdyzf5";
                                break;
                            default :
                                break;
                        }
                        String qjbzxx=name1;

                        if(StringUtils.isNotEmpty(qjbzxx)&&"ng".equals(qjbzxx)){
                            String ngname=daQjCsb.getCsmc();
                            lsSkfhJson= (LsSkfhJson) lsSkfhJson.clone();
                            if("WX".equals(ywPosHz.getZfqd())){
                                lsSkfhJson.setMc(ngname+"-微");
                            }else if("ALI".equals(ywPosHz.getZfqd())){
                                lsSkfhJson.setMc(ngname+"-支");
                            }else{
                                lsSkfhJson.setMc(ngname);
                            }
                            lsSkfhJson.setVal(String.format("%.2f", ywPosHz.getWx()));
                            lsSkfhJsonList.add(lsSkfhJson);


                        }else{
                            lsSkfhJson= (LsSkfhJson) lsSkfhJson.clone();
                            lsSkfhJson.setMc(daQjCsb.getCsmc());
                            Method[] methods=ywPosHz.getClass().getMethods();
                            ok:
                            for (int n = 0; n <methods.length ; n++) {
                                if(("get"+qjbzxx).toLowerCase().equals(methods[n].getName().toLowerCase())){
                                    Double val = 0.00;
                                    try {
                                        val = (Double)methods[n].invoke(ywPosHz);
                                        if(val!=null){
                                            lsSkfhJson.setVal(String.format("%.2f", val));
                                        }else{
                                            lsSkfhJson.setVal("0.00");
                                        }
                                        break ok;
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            lsSkfhJsonList.add(lsSkfhJson);
                        }

                    }
                }
                lsSkfhJson= (LsSkfhJson) lsSkfhJson.clone();
                lsSkfhJson.setMc("找零");
                Double newzl=ywPosHz.getZl();
                lsSkfhJson.setVal(String.format("%.2f", newzl));
                lsSkfhJsonList.add(lsSkfhJson);
            }
            ywCgRkdPrintmx.setSklb(lsSkfhJsonList);

            String kdr = ywPosHz.getSkymc();//收款人
            ywCgRkdPrintmx.setKdr(kdr);
            String printDate = dateFormat1.format(new Date());//打印时间
            ywCgRkdPrintmx.setPrintDate(printDate);

            String orderURL = "";//单据二维码url
            ywCgRkdPrintmx.setOrderURL(orderURL);
            String shopURL = "";//店铺二维码url
            ywCgRkdPrintmx.setShopURL(shopURL);

            List<YwCgRkdmxJson> list = null;
            if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())){
                list = ywPosMxMapper.selectZxList(pzh);
            }else{
                list = ywPosMxMapper.selectYsHzList(pzh);
            }
//            if (list != null && list.size() > 0) {
//                int h = list.size();
//                for (int i = 0; i < h; i++) {
//                    Double jj = list.get(i).getJj();
//                    list.get(i).setDj(jj);
//                    Double hjjze=Integer.valueOf(list.get(i).getSl()) * list.get(i).getLsj();
//                    list.get(i).setHjjze(hjjze);
//                }
//            }
            //转换格式
            if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())){
                ywCgRkdmxService.getNewList(sixCode, list);
            }
            //纵向
            if (StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType()) && "1".equals(ywMxPrintCommon.getPrintType())) {
                //判断
                DaSpBxbt daSpBxbt = daSpSpdaMxService.findDaSpBxbt(sixCode);

                if (list != null && list.size() > 0) {
                    int k = list.size();
                    //获取spkh集合
                    List<String> spkhList = list.stream().map(YwCgRkdmxJson::getSpkh).collect(Collectors.toList());
                    //spkh去重
                    // 利用list中的元素创建HashSet集合，此时set中进行了去重操作
                    HashSet set = new HashSet(spkhList);
                    // 清空list集合
                    spkhList.clear();
                    // 将去重后的元素重新添加到list中
                    spkhList.addAll(set);
                    List<YwCgRkListJson> ywCgRkListJsonList = new ArrayList<YwCgRkListJson>();
                    YwCgRkListJson ywCgRkListJson = new YwCgRkListJson();

                    YwCgRkPrintDetailJson ywCgRkPrintDetailJson = new YwCgRkPrintDetailJson();
                    if (spkhList != null && spkhList.size() > 0) {
                        int s = spkhList.size();
                        for (int i = 0; i < s; i++) {
                            String spkh = spkhList.get(i);
                            //克隆spkh,spkm,jldw
                            YwCgRkListJson newYwCgRkListJson = (YwCgRkListJson) ywCgRkListJson.clone();
                            List<YwCgRkPrintDetailJson> ywCgRkPrintDetailJsonList = new ArrayList<YwCgRkPrintDetailJson>();
                            Integer khsl=0;
                            Double khje=0.00;
                            for (int j = 0; j < k; j++) {
                                if (spkh.equals(list.get(j).getSpkh())) {
                                    YwCgRkPrintDetailJson newYwCgRkPrintDetailJson = (YwCgRkPrintDetailJson) ywCgRkPrintDetailJson.clone();

                                    newYwCgRkListJson.setSpkh(spkh);
                                    newYwCgRkListJson.setSpkm(list.get(j).getKsmc());
                                    newYwCgRkListJson.setJldw(list.get(j).getJldw());
                                    if(StringUtils.isNotEmpty(list.get(j).getFhlx())){
                                        newYwCgRkListJson.setFhlx(list.get(j).getFhlx());
                                    }else{
                                        newYwCgRkListJson.setFhlx("");
                                    }
                                    if(StringUtils.isNotEmpty(list.get(j).getImg())){
                                        newYwCgRkListJson.setImg(list.get(j).getImg());
                                    }else{
                                        newYwCgRkListJson.setImg("");
                                    }


                                    ywCgRkdPrintmx.setCgy(list.get(j).getDgmc());//获取导购员
                                    //获取该款总数量
                                    Integer sl=Integer.parseInt(list.get(j).getSl());
                                    khsl=khsl+sl;
                                    newYwCgRkListJson.setKhsl(String.valueOf(khsl));
                                    //获取该款总金额
                                    Double je=list.get(j).getHjjze();
                                    khje=khje+je;
                                    newYwCgRkListJson.setKhje(String.format("%.2f", khje));
                                    //拼接YwCgRkPrintDetailJson
                                    if(list.get(j).getHjjze()!=null){
                                        newYwCgRkPrintDetailJson.setJe(String.format("%.2f", list.get(j).getHjjze()));
                                    }
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkPrintDetailJson.setLsj(String.format("%.0f", list.get(j).getLsj()));
                                    }

                                    if(list.get(j).getJj()!=null){
                                        Double hjjze=list.get(j).getHjjze();
                                        Integer newsl=Integer.parseInt(list.get(j).getSl());
                                        Double jj=hjjze/newsl;
                                        newYwCgRkPrintDetailJson.setJsj(String.format("%.2f", jj));
                                    }
//                                    if(list.get(j).getLsj()!=null){
//                                        Double hjjze=list.get(j).getHjjze();
//                                        Integer newsl=Integer.parseInt(list.get(j).getSl());
//                                        Double jj=hjjze/newsl;
//                                        newYwCgRkPrintDetailJson.setLsj(String.format("%.0f", jj));
//                                    }
                                    if(list.get(j).getSl()!=null){
                                        newYwCgRkPrintDetailJson.setSl(list.get(j).getSl());
                                    }
                                    if(list.get(j).getZkl()!=null){
                                        newYwCgRkPrintDetailJson.setZkl(String.format("%.2f", list.get(j).getZkl()));
                                    }
                                    if ("0".equals(daSpBxbt.getQybz())) {//表示三属性--不包含版型
                                        newYwCgRkPrintDetailJson.setYsbxcm(list.get(j).getYsmc() + " " + list.get(j).getCmbt());
                                    } else {//表示四属性--包含版型
                                        newYwCgRkPrintDetailJson.setYsbxcm(list.get(j).getYsmc() + " " + list.get(j).getBxmc() + " " + list.get(j).getCmbt());
                                    }
                                    if(list.get(j).getDanjia()!=null){//单价（从数据库中获取的字段值其实和上面的算出来的lsj值是一样的）
                                        newYwCgRkPrintDetailJson.setDanjia(String.format("%.2f", list.get(j).getDanjia()));
                                    }else{
                                        newYwCgRkPrintDetailJson.setDanjia("0.00");
                                    }
                                    ywCgRkPrintDetailJsonList.add(newYwCgRkPrintDetailJson);
                                }
                            }
                            ywCgRkListJsonList.add(newYwCgRkListJson);
                            newYwCgRkListJson.setDetailList(ywCgRkPrintDetailJsonList);
                        }
                        ywCgRkdPrintmx.setList(ywCgRkListJsonList);

                    }
                }
//                redisUtil.set(ywMxPrintCommon.getSixCode()+"_"+ywMxPrintCommon.getPzh()+"_printCgZx", JSON.toJSONString(ywCgRkdPrintmx));
            } else if (StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType()) && "3".equals(ywMxPrintCommon.getPrintType())) {//款式汇总
                //获取系统取价方式
                if(list!=null&&list.size()>0){
                    int k=list.size();
                    //获取spkh集合
                    List<String> spkhList=list.stream().map(YwCgRkdmxJson::getSpkh).collect(Collectors.toList());
                    //spkh去重
                    // 利用list中的元素创建HashSet集合，此时set中进行了去重操作
                    HashSet set = new HashSet(spkhList);
                    // 清空list集合
                    spkhList.clear();
                    // 将去重后的元素重新添加到list中
                    spkhList.addAll(set);
                    List<YwCgRkListJson> ywCgRkListJsonList=new ArrayList<YwCgRkListJson>();
                    YwCgRkListJson ywCgRkListJson=new YwCgRkListJson();

                    if(spkhList!=null&&spkhList.size()>0){
                        int s=spkhList.size();
                        for (int i = 0; i < s; i++) {
                            String spkh=spkhList.get(i);
                            //克隆spkh,spkm,jldw
                            YwCgRkListJson newYwCgRkListJson= (YwCgRkListJson) ywCgRkListJson.clone();
                            Integer khsl=0;
                            Double khje=0.00;
                            Double khlsj=0.00;
                            Double newjj=0.00;
                            Double newzkl=0.00;
                            for (int j = 0; j < k; j++) {
                                if(spkh.equals(list.get(j).getSpkh())){
                                    newYwCgRkListJson.setSpkm(list.get(j).getKsmc());
                                    newYwCgRkListJson.setJldw(list.get(j).getJldw());
                                     if(StringUtils.isNotEmpty(list.get(j).getFhlx())){
                                        newYwCgRkListJson.setFhlx(list.get(j).getFhlx());
                                    }else{
                                        newYwCgRkListJson.setFhlx("");
                                    }
                                    if(StringUtils.isNotEmpty(list.get(j).getImg())){
                                        newYwCgRkListJson.setImg(list.get(j).getImg());
                                    }else{
                                        newYwCgRkListJson.setImg("");
                                    }
                                    ywCgRkdPrintmx.setCgy(list.get(j).getDgmc());//获取导购员
                                    //获取该款总数量
                                    Integer sl=Integer.parseInt(list.get(j).getSl());
                                    khsl=khsl+sl;
                                    //获取该款总金额
                                    Double je=list.get(j).getHjjze();
                                    khje=khje+je;
                                    //获取该款总零售价金额
                                    Double lsj=list.get(j).getLsj();
                                    Double hlsjze=lsj * sl;
                                    khlsj=khlsj+hlsjze;

                                    //获取Lsj
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkListJson.setLsj(String.format("%.0f", list.get(j).getJj()));
                                    }else{
                                        newYwCgRkListJson.setLsj("0.00");
                                    }
                                }
                            }
                            newYwCgRkListJson.setSpkh(spkh);
                            newYwCgRkListJson.setKhsl(String.valueOf(khsl));
                            newYwCgRkListJson.setKhje(String.format("%.2f", khje));
                            //获取jj,通过总金额除以数量
                            if(khje!=null&&khsl!=0&&khje!=0.0&&khje!=0.00){
                                newjj=khje/khsl;
                                newYwCgRkListJson.setJj(String.format("%.2f", newjj));
                            }else{
                                newYwCgRkListJson.setJj("0.00");
                            }
                            //获取平均zkl,通过进价总额除以零售价总额
                            if(khje!=null&&khlsj!=null&&khje!=0.0&&khje!=0.00&&khlsj!=0.0&&khlsj!=0.00){
                                newzkl=khje/khlsj;
                                newYwCgRkListJson.setZkl(String.format("%.2f", newzkl));
                            }else{
                                newYwCgRkListJson.setZkl("1.00");
                            }
                            ywCgRkListJsonList.add(newYwCgRkListJson);
                        }
                        ywCgRkdPrintmx.setList(ywCgRkListJsonList);
                    }
                }
            }else if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType()) && "4".equals(ywMxPrintCommon.getPrintType())){//零售退货
                //判断
//                DaSpBxbt daSpBxbt = daSpSpdaMxService.findDaSpBxbt(sixCode);

                if (list != null && list.size() > 0) {
                    int k = list.size();
                    //获取spkh集合
                    List<String> spkhList = list.stream().map(YwCgRkdmxJson::getSpkh).collect(Collectors.toList());
                    //spkh去重
                    // 利用list中的元素创建HashSet集合，此时set中进行了去重操作
                    HashSet set = new HashSet(spkhList);
                    // 清空list集合
                    spkhList.clear();
                    // 将去重后的元素重新添加到list中
                    spkhList.addAll(set);
                    List<LsYwCgRkListJson> ywCgRkListJsonList = new ArrayList<LsYwCgRkListJson>();
                    LsYwCgRkListJson lsYwCgRkListJson = new LsYwCgRkListJson();

                    LsYwCgRkPrintDetailJson lsYwCgRkPrintDetailJson = new LsYwCgRkPrintDetailJson();
                    if (spkhList != null && spkhList.size() > 0) {
                        int s = spkhList.size();
                        for (int i = 0; i < s; i++) {
                            String spkh = spkhList.get(i);
                            //克隆spkh,spkm,jldw
                            LsYwCgRkListJson newYwCgRkListJson = (LsYwCgRkListJson) lsYwCgRkListJson.clone();
                            List<LsYwCgRkPrintDetailJson> ywCgRkPrintDetailJsonList = new ArrayList<LsYwCgRkPrintDetailJson>();
                            Integer khsl=0;
                            Double khje=0.00;
                            for (int j = 0; j < k; j++) {
                                if (spkh.equals(list.get(j).getSpkh())) {
                                    LsYwCgRkPrintDetailJson newYwCgRkPrintDetailJson = (LsYwCgRkPrintDetailJson) lsYwCgRkPrintDetailJson.clone();

                                    newYwCgRkListJson.setSpkh(spkh);
                                    newYwCgRkListJson.setSpkm(list.get(j).getKsmc());
                                    newYwCgRkListJson.setJldw(list.get(j).getJldw());
//                                    newYwCgRkListJson.setFhlx(list.get(j).getFhlx());
                                    newYwCgRkListJson.setImg(list.get(j).getImg());
                                    newYwCgRkListJson.setLshyj("0.00");
                                    newYwCgRkListJson.setLshyzk("1.00");
                                    newYwCgRkListJson.setJgzzpzh(list.get(j).getJgzzpzh());
                                    if(list.get(j).getGszdj()!=null){
                                        newYwCgRkListJson.setGszdj(String.format("%.2f", list.get(j).getGszdj()));
                                    }else{
                                        newYwCgRkListJson.setGszdj("0.00");
                                    }
                                    newYwCgRkListJson.setZklb("");

                                    ywCgRkdPrintmx.setCgy(list.get(j).getDgmc());//获取导购员
                                    //获取该款总数量
                                    Integer sl=Integer.parseInt(list.get(j).getSl());
                                    khsl=khsl+sl;
                                    newYwCgRkListJson.setKhsl(String.valueOf(khsl));
                                    //获取该款总金额
                                    Double je=list.get(j).getHjjze();
                                    khje=khje+je;
                                    newYwCgRkListJson.setKhje(String.format("%.2f", khje));
                                    //拼接YwCgRkPrintDetailJson
                                    if(list.get(j).getHjjze()!=null){
                                        newYwCgRkPrintDetailJson.setJe(String.format("%.2f", list.get(j).getHjjze()));
                                    }
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkPrintDetailJson.setLsj(String.format("%.0f", list.get(j).getLsj()));
                                    }

                                    if(list.get(j).getJj()!=null){
                                        Double hjjze=list.get(j).getHjjze();
                                        Integer newsl=Integer.parseInt(list.get(j).getSl());
                                        Double jj=hjjze/newsl;
                                        newYwCgRkPrintDetailJson.setJsj(String.format("%.2f", jj));
                                    }
                                    if(list.get(j).getSl()!=null){
                                        newYwCgRkPrintDetailJson.setSl(list.get(j).getSl());
                                    }
                                    if(list.get(j).getZkl()!=null){
                                        newYwCgRkPrintDetailJson.setZkl(String.format("%.2f", list.get(j).getZkl()));
                                    }
                                    if(list.get(j).getDanjia()!=null){//单价（从数据库中获取的字段值其实和上面的算出来的lsj值是一样的）
                                        newYwCgRkPrintDetailJson.setDanjia(String.format("%.2f", list.get(j).getDanjia()));
                                    }else{
                                        newYwCgRkPrintDetailJson.setDanjia("0.00");
                                    }
                                    newYwCgRkPrintDetailJson.setSpbh(list.get(j).getSpbh());
                                    newYwCgRkPrintDetailJson.setYsbh(list.get(j).getYsbh());
                                    newYwCgRkPrintDetailJson.setYsmc(list.get(j).getYsmc());
                                    newYwCgRkPrintDetailJson.setYslsh(list.get(j).getYslsh());
                                    newYwCgRkPrintDetailJson.setBxbh(list.get(j).getBxbh());
                                    newYwCgRkPrintDetailJson.setBxmc(list.get(j).getBxmc());
                                    newYwCgRkPrintDetailJson.setCmbh(list.get(j).getCmbh());
                                    newYwCgRkPrintDetailJson.setBqkc("0");
                                    newYwCgRkPrintDetailJson.setTmbh(list.get(j).getTmbh());
                                    newYwCgRkPrintDetailJson.setCmzbm(list.get(j).getCmzbh());
                                    newYwCgRkPrintDetailJson.setCmbt(list.get(j).getCmbt());
                                    newYwCgRkPrintDetailJson.setYyyh(list.get(j).getYyyh());
                                    newYwCgRkPrintDetailJson.setZke(String.format("%.2f", list.get(j).getZke()));
                                    newYwCgRkPrintDetailJson.setCmzlwz(list.get(j).getCm());



                                    ywCgRkPrintDetailJsonList.add(newYwCgRkPrintDetailJson);
                                }
                            }
                            ywCgRkListJsonList.add(newYwCgRkListJson);
                            newYwCgRkListJson.setDetailList(ywCgRkPrintDetailJsonList);
                        }
                        ywCgRkdPrintmx.setLslist(ywCgRkListJsonList);

                    }
                }
            } else if (StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType()) && "5".equals(ywMxPrintCommon.getPrintType())) {//款式颜色汇总
                if(list!=null&&list.size()>0){
                    int k=list.size();
                    List<YwCgRkdmxJson> spkhList= list.stream().sorted(Comparator.comparing(YwCgRkdmxJson ::getYsspkh)).collect(Collectors.toList());
                    List<String> spkhList1=new ArrayList<String>();
                    for (int i = 0; i < spkhList.size(); i++) {
                        if (!spkhList1.contains(spkhList.get(i).getYsspkh())) {
                            spkhList1.add(spkhList.get(i).getYsspkh());
                        }
                    }
                    List<YwCgRkListJson> ywCgRkListJsonList=new ArrayList<YwCgRkListJson>();
                    YwCgRkListJson ywCgRkListJson=new YwCgRkListJson();

                    if(spkhList1!=null&&spkhList1.size()>0){
                        int s=spkhList1.size();
                        for (int i = 0; i < s; i++) {
                            String ysspkh=spkhList1.get(i);
                            //克隆spkh,spkm,jldw
                            YwCgRkListJson newYwCgRkListJson= (YwCgRkListJson) ywCgRkListJson.clone();
                            Integer khsl=0;
                            Double khje=0.00;
                            Double khlsj=0.00;
                            Double newjj=0.00;
                            Double newzkl=0.00;
                            for (int j = 0; j < k; j++) {
                                if(ysspkh.equals(list.get(j).getYsspkh())){
                                    newYwCgRkListJson.setSpkh(list.get(j).getSpkh());
                                    newYwCgRkListJson.setSpkm(list.get(j).getKsmc());
                                    newYwCgRkListJson.setJldw(list.get(j).getJldw());
                                    if(StringUtils.isNotEmpty(list.get(j).getYsbh())){
                                        newYwCgRkListJson.setYsbh(list.get(j).getYsbh());
                                    }else{
                                        newYwCgRkListJson.setYsbh("");
                                    }
                                    if(StringUtils.isNotEmpty(list.get(j).getYslsh())){
                                        newYwCgRkListJson.setYslsh(list.get(j).getYslsh());
                                    }else{
                                        newYwCgRkListJson.setYslsh("");
                                    }
                                    if(StringUtils.isNotEmpty(list.get(j).getYsmc())){
                                        newYwCgRkListJson.setYsmc(list.get(j).getYsmc());
                                    }else{
                                        newYwCgRkListJson.setYsmc("");
                                    }
                                    if(StringUtils.isNotEmpty(list.get(j).getFhlx())){
                                        newYwCgRkListJson.setFhlx(list.get(j).getFhlx());
                                    }else{
                                        newYwCgRkListJson.setFhlx("");
                                    }
                                    if(StringUtils.isNotEmpty(list.get(j).getImg())){
                                        newYwCgRkListJson.setImg(list.get(j).getImg());
                                    }else{
                                        newYwCgRkListJson.setImg("");
                                    }
                                    ywCgRkdPrintmx.setCgy(list.get(j).getDgmc());//获取导购员
                                    //获取该款总数量
                                    Integer sl=Integer.parseInt(list.get(j).getSl());
                                    khsl=khsl+sl;
                                    //获取该款总金额
                                    Double je=list.get(j).getHjjze();
                                    khje=khje+je;
                                    //获取该款总零售价金额
                                    Double lsj=list.get(j).getLsj();
                                    Double hlsjze=lsj * sl;
                                    khlsj=khlsj+hlsjze;

                                    //获取Lsj
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkListJson.setLsj(String.format("%.0f", list.get(j).getJj()));
                                    }else{
                                        newYwCgRkListJson.setLsj("0.00");
                                    }
                                }
                            }
                            newYwCgRkListJson.setKhsl(String.valueOf(khsl));
                            newYwCgRkListJson.setKhje(String.format("%.2f", khje));
                            //获取jj,通过总金额除以数量
                            if(khje!=null&&khsl!=0&&khje!=0.0&&khje!=0.00){
                                newjj=khje/khsl;
                                newYwCgRkListJson.setJj(String.format("%.2f", newjj));
                            }else{
                                newYwCgRkListJson.setJj("0.00");
                            }
                            //获取平均zkl,通过进价总额除以零售价总额
                            if(khje!=null&&khlsj!=null&&khje!=0.0&&khje!=0.00&&khlsj!=0.0&&khlsj!=0.00){
                                newzkl=khje/khlsj;
                                newYwCgRkListJson.setZkl(String.format("%.2f", newzkl));
                            }else{
                                newYwCgRkListJson.setZkl("1.00");
                            }
                            ywCgRkListJsonList.add(newYwCgRkListJson);
                        }
                        ywCgRkdPrintmx.setList(ywCgRkListJsonList);
                    }
                }
            }
        }
        return ywCgRkdPrintmx;
    }

    @Override
    public YwPosSaleDay findYwSaleDayDetail(String sixCode, String bmbh, String jqbh, String rq, DaQxYhda daYgda, BaseOrg org) {
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //获取开单人(就是当前登录人)
        YwPosSaleDay bean=ywPosMxMapper.findYwSaleDayDetail(bmbh,jqbh,rq);
        String printDate=dateFormat1.format(new Date());//打印时间
        bean.setPrintDate(printDate);
        bean.setKdr(daYgda.getYgmc());
        return bean;
    }
}
