package com.springboot.service.impl.stock;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.SystemConstant;
import com.springboot.dao.stock.YwKcKcpcdMxMapper;
import com.springboot.model.goods.DaSpBxbt;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkListJson;
import com.springboot.model.purchase.YwCgRkPrintDetailJson;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.stock.YwKcKcpcdHz;
import com.springboot.model.stock.YwKcKcpcdMx;
import com.springboot.model.user.DaQxYhda;
import com.springboot.model.wholesale.YwPfRkdhz;
import com.springboot.service.goods.DaSpSpdaMxService;
import com.springboot.service.purchase.YwCgRkdmxService;
import com.springboot.service.stock.YwKcKcpcdHzService;
import com.springboot.service.stock.YwKcKcpcdMxService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-05-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwKcKcpcdMxServiceImpl extends ServiceImpl<YwKcKcpcdMxMapper, YwKcKcpcdMx> implements YwKcKcpcdMxService {

    @Resource
    private YwKcKcpcdMxMapper ywKcKcpcdMxMapper;
    @Resource
    private YwCgRkdmxService ywCgRkdmxService;
    @Resource
    private YwKcKcpcdHzService ywKcKcpcdHzService;
    @Resource
    private DaSpSpdaMxService daSpSpdaMxService;

    @Override
    public Map<String,Object> getPage(String sixCode,Map<String, String> param) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取所有入库单的明细
        Map<String,Object> param1=new HashMap<String,Object>();
        //获取所有入库单的明细
        String pzh=param.get("pzh");
        String sgdj=param.get("sgdj");
        Integer pageNo=Integer.parseInt(param.get("pageNo"));
        Integer pageSize=Integer.parseInt(param.get("pageSize"));
        //获取部门，供应商名称，日期
        YwKcKcpcdHz ywKcKcpcdHz=ywKcKcpcdHzService.findByPzh(sixCode,pzh,sgdj);
        if(ywKcKcpcdHz!=null){
            param1.put("bmmc",ywKcKcpcdHz.getBmmc());
            param1.put("tzrq",dateFormat.format(ywKcKcpcdHz.getTzrq()));
            param1.put("bzxx",ywKcKcpcdHz.getBz());
            param1.put("cgy",ywKcKcpcdHz.getYgmc());
            param1.put("zjsl",ywKcKcpcdHz.getZjsl());
            param1.put("zjje",ywKcKcpcdHz.getZjje());
            param1.put("sgdj",ywKcKcpcdHz.getSgdj());

        }
        List<YwCgRkdmxJson> list=ywKcKcpcdMxMapper.getList(pzh,sgdj,pageNo,pageSize);
        if(list!=null&&list.size()>0){
            int k=list.size();
            for (int i = 0; i <k ; i++) {
                Double lsj=list.get(i).getLsj();
                list.get(i).setDj(lsj);
                Double hjjze=Integer.valueOf(list.get(i).getSl()) * list.get(i).getLsj();
                list.get(i).setHjjze(hjjze);
            }
        }
        //转换格式
        List<YwCgRkdmxJson> newList=ywCgRkdmxService.getNewList(sixCode,list);
        param1.put("list",newList);

        return param1;
    }

    @Override
    public Integer batchDeleteByPzh(String sixCode, String pzh) {
        Integer flag=ywKcKcpcdMxMapper.batchDeleteByPzh(pzh);
        return flag;
    }

    @Override
    public YwCgRkdPrintmx getKcDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org,String ywType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        YwCgRkdPrintmx ywCgRkdPrintmx=new YwCgRkdPrintmx();

        //凭证号
        String pzh=ywMxPrintCommon.getPzh();
        ywCgRkdPrintmx.setPzh(pzh);

        //获取部门，供应商名称，日期
        YwKcKcpcdHz ywKcKcpcdHz=null;
        if(StringUtils.isNotEmpty(ywType)&&SystemConstant.SY_TYPE.equals(ywType)){
            ywKcKcpcdHz=ywKcKcpcdHzService.findSyByPzh(sixCode,ywMxPrintCommon.getPzh());
        }else{
            ywKcKcpcdHz=ywKcKcpcdHzService.findByPzh(sixCode,ywMxPrintCommon.getPzh(),ywMxPrintCommon.getSgdj());
        }
        //获取企业信息
        String companyName="";
        if(org!=null){
            companyName=org.getOrgName();
            ywCgRkdPrintmx.setCompanyName(companyName);
        }
        //获取单据名称
        String orderName= SystemConstant.YW_KC_ORDER_NAME;
        ywCgRkdPrintmx.setOrderName(orderName);

        if(ywKcKcpcdHz!=null) {
            String bmbh=ywKcKcpcdHz.getTzbm();
            ywCgRkdPrintmx.setBmbh(bmbh);
            String bmmc = ywKcKcpcdHz.getBmmc();//部门名称
            ywCgRkdPrintmx.setBmmc(bmmc);
            String gysbh="";
            ywCgRkdPrintmx.setGysbh(gysbh);
            String gysmc="";
            ywCgRkdPrintmx.setGysmc(gysmc);
            String khmc="";//客户名称
            ywCgRkdPrintmx.setKhmc(khmc);
            String tel="";//客户电话
            ywCgRkdPrintmx.setTel(tel);

            String drbm="";//调入部门名称
            ywCgRkdPrintmx.setDrbm(drbm);
            String dcbm="";;//调出部门名称
            ywCgRkdPrintmx.setDcbm(dcbm);

            String kdrq = dateFormat.format(ywKcKcpcdHz.getTzrq());//开单日期
            ywCgRkdPrintmx.setKdrq(kdrq);
            String sgdj = ywMxPrintCommon.getSgdj();//手工单号
            ywCgRkdPrintmx.setSgdh(sgdj);
            String fhlx="";//发货类型---批发
            ywCgRkdPrintmx.setFhlx(fhlx);
            String cgy = ywKcKcpcdHz.getYgmc();//业务员
            ywCgRkdPrintmx.setCgy(cgy);
            String bzxx = ywKcKcpcdHz.getBz();//备注信息
            if(StringUtils.isNotEmpty(bzxx)){
                ywCgRkdPrintmx.setBzxx(bzxx);
            }else{
                ywCgRkdPrintmx.setBzxx("");
            }
            Double zje = ywKcKcpcdHz.getZjje();//单据总金额
            if(zje!=null){
                ywCgRkdPrintmx.setZje(String.format("%.2f", zje));
            }else{
                ywCgRkdPrintmx.setZje("");
            }
            ywCgRkdPrintmx.setRkfs("");


            Double zsl = ywKcKcpcdHz.getZjsl();//单据总数量
            ywCgRkdPrintmx.setZsl(zsl);
            String kdr = daYgda.getYgmc();//制单人
            ywCgRkdPrintmx.setKdr(kdr);
            String printDate = dateFormat1.format(new Date());//打印时间
            ywCgRkdPrintmx.setPrintDate(printDate);

            String orderURL = "";//单据二维码url
            ywCgRkdPrintmx.setOrderURL(orderURL);
            String shopURL = "";//店铺二维码url
            ywCgRkdPrintmx.setShopURL(shopURL);

            List<YwCgRkdmxJson> list = null;
            if(StringUtils.isNotEmpty(ywType)&&SystemConstant.SY_TYPE.equals(ywType)){
                if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())){
                    list = ywKcKcpcdMxMapper.selectSyZxList(pzh);
                }else{
                    list = ywKcKcpcdMxMapper.selectSyysHzList(pzh);
                }
            }else{
                if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())){
                    list = ywKcKcpcdMxMapper.selectZxList(pzh,ywMxPrintCommon.getSgdj());
                }else{
                    list = ywKcKcpcdMxMapper.selectYsHzList(pzh,ywMxPrintCommon.getSgdj());
                }
            }
            if(list!=null&&list.size()>0){
                int k=list.size();
                for (int i = 0; i <k ; i++) {
                    Double lsj=list.get(i).getLsj();
                    list.get(i).setDj(lsj);
                    Double hjjze=Integer.valueOf(list.get(i).getSl()) * list.get(i).getLsj();
                    list.get(i).setHjjze(hjjze);
                }
            }
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
                                    if(list.get(j).getJj()!=null){
                                        newYwCgRkPrintDetailJson.setJsj(String.format("%.2f", list.get(j).getJj()));
                                    }else{
                                        newYwCgRkPrintDetailJson.setJsj("");
                                    }
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkPrintDetailJson.setLsj(String.format("%.0f", list.get(j).getLsj()));
                                    }
                                    if(list.get(j).getSl()!=null){
                                        newYwCgRkPrintDetailJson.setSl(list.get(j).getSl());
                                    }
                                    if(list.get(j).getZkl()!=null){
                                        newYwCgRkPrintDetailJson.setZkl(String.format("%.2f", list.get(j).getZkl()));
                                    }else{
                                        newYwCgRkPrintDetailJson.setZkl("");
                                    }
                                    if ("0".equals(daSpBxbt.getQybz())) {//表示三属性--不包含版型
                                        newYwCgRkPrintDetailJson.setYsbxcm(list.get(j).getYsmc() + " " + list.get(j).getCmbt());
                                    } else {//表示四属性--包含版型
                                        newYwCgRkPrintDetailJson.setYsbxcm(list.get(j).getYsmc() + " " + list.get(j).getBxmc() + " " + list.get(j).getCmbt());
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
                                        newYwCgRkListJson.setLsj(String.format("%.0f", list.get(j).getLsj()));
                                    }
                                }
                            }
                            newYwCgRkListJson.setSpkh(spkh);
                            newYwCgRkListJson.setKhsl(String.valueOf(khsl));
                            newYwCgRkListJson.setKhje(String.format("%.2f", khje));
                            //获取jj,通过总金额除以数量
                            if(khje!=null&&khsl!=0&&khje!=0.0){
                                newjj=khje/khsl;
                                newYwCgRkListJson.setJj(String.format("%.2f", newjj));
                            }else{
                                newYwCgRkListJson.setJj("0.00");
                            }
                            //获取平均zkl,通过进价总额除以零售价总额
                            if(khje!=null&&khlsj!=null&&khje!=0.0&&khlsj!=0.0){
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
            } else if (StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType()) && "5".equals(ywMxPrintCommon.getPrintType())) {//款式颜色汇总
                if(list!=null&&list.size()>0){
                    int k=list.size();
                    //获取spkh集合
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
                                        newYwCgRkListJson.setLsj(String.format("%.0f", list.get(j).getLsj()));
                                    }
                                }
                            }
                            newYwCgRkListJson.setKhsl(String.valueOf(khsl));
                            newYwCgRkListJson.setKhje(String.format("%.2f", khje));
                            //获取jj,通过总金额除以数量
                            if(khje!=null&&khsl!=0&&khje!=0.0){
                                newjj=khje/khsl;
                                newYwCgRkListJson.setJj(String.format("%.2f", newjj));
                            }else{
                                newYwCgRkListJson.setJj("0.00");
                            }
                            //获取平均zkl,通过进价总额除以零售价总额
                            if(khje!=null&&khlsj!=null&&khje!=0.0&&khlsj!=0.0){
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

    public String getTzfs(String cgfsType){
        String name="";
        if(StringUtils.isNotEmpty(cgfsType)){
            switch(cgfsType){
                case "phtz" :
                    name="盘活调整";
                    break;
                case "ympd" :
                    name="月末盘点";
                    break;
                default :
                    name="";
                    break;
            }
        }
        return name;
    }
}
