package com.springboot.service.impl.purchase;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.SystemConstant;
import com.springboot.dao.purchase.YwCgRkdmxMapper;
import com.springboot.model.goods.DaSpBxbt;
import com.springboot.model.goods.DaSpCmbt;
import com.springboot.model.goods.DaSpCmdm;
import com.springboot.model.goods.DaSpCmzb;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.*;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.goods.DaSpCmbtService;
import com.springboot.service.goods.DaSpCmdmService;
import com.springboot.service.goods.DaSpCmzbService;
import com.springboot.service.goods.DaSpSpdaMxService;
import com.springboot.service.purchase.YwCgRkdhzService;
import com.springboot.service.purchase.YwCgRkdmxService;
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
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-05-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwCgRkdmxServiceImpl extends ServiceImpl<YwCgRkdmxMapper, YwCgRkdmx> implements YwCgRkdmxService {


    @Resource
    private YwCgRkdmxMapper ywCgRkdmxMapper;
    @Resource
    private DaSpCmbtService daSpCmbtService;
    @Resource
    private DaSpCmzbService daSpCmzbService;
    @Resource
    private DaSpCmdmService daSpCmdmService;
    @Resource
    private YwCgRkdhzService ywCgRkdhzService;
    @Resource
    private DaSpSpdaMxService daSpSpdaMxService;
    @Override
    public Map<String,Object> getPage(String sixCode, Map<String, String> param) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取所有入库单的明细
        Map<String,Object> param1=new HashMap<String,Object>();
        String pzh=param.get("pzh");
        Integer pageNo=Integer.parseInt(param.get("pageNo"));
        Integer pageSize=Integer.parseInt(param.get("pageSize"));
        //获取部门，供应商名称，日期
        YwCgRkdhz ywCgRkdhz=ywCgRkdhzService.findByPzh(sixCode,pzh);
        if(ywCgRkdhz!=null){
            param1.put("gysmc",ywCgRkdhz.getGysmc());
            param1.put("bmmc",ywCgRkdhz.getBmmc());
            param1.put("kdrq",dateFormat.format(ywCgRkdhz.getKdrq()));
            param1.put("bzxx",ywCgRkdhz.getBzxx());
            param1.put("cgy",ywCgRkdhz.getYgmc());
            param1.put("zjsl",ywCgRkdhz.getZjsl());
            param1.put("zjje",ywCgRkdhz.getZjje());

        }
        List<YwCgRkdmxJson> list=ywCgRkdmxMapper.getList(pzh,pageNo,pageSize);
        if(list!=null&&list.size()>0){
            int k=list.size();
            for (int i = 0; i < k; i++) {
                Double jj=list.get(i).getJj();
                list.get(i).setDj(jj);
                Double hjjze=Integer.valueOf(list.get(i).getSl()) * list.get(i).getJj();
                list.get(i).setHjjze(hjjze);
            }
        }
        //转换格式
        List<YwCgRkdmxJson> newList=this.getNewList(sixCode,list);
        param1.put("list",newList);

        return param1;
    }

    @Override
    public YwCgRkdPrintmx getCgDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        YwCgRkdPrintmx ywCgRkdPrintmx=new YwCgRkdPrintmx();

        //凭证号
        String pzh=ywMxPrintCommon.getPzh();
        ywCgRkdPrintmx.setPzh(pzh);

        //获取部门，供应商名称，日期
        YwCgRkdhz ywCgRkdhz=ywCgRkdhzService.findByPzh(sixCode,ywMxPrintCommon.getPzh());
        //获取企业信息
        String companyName="";
        if(org!=null){
            companyName=org.getOrgName();
            ywCgRkdPrintmx.setCompanyName(companyName);
        }
        //获取单据名称
        String orderName= SystemConstant.YW_CG_ORDER_NAME;
        ywCgRkdPrintmx.setOrderName(orderName);

        if(ywCgRkdhz!=null){
            String bmmc=ywCgRkdhz.getBmmc();
            String bmbh=ywCgRkdhz.getBmbh();
            ywCgRkdPrintmx.setBmmc(bmmc);
            ywCgRkdPrintmx.setBmbh(bmbh);
            String gysmc=ywCgRkdhz.getGysmc();
            String gysbh=ywCgRkdhz.getGysbh();
            ywCgRkdPrintmx.setGysmc(gysmc);
            ywCgRkdPrintmx.setGysbh(gysbh);
            String kdrq=dateFormat.format(ywCgRkdhz.getKdrq());//开单日期
            ywCgRkdPrintmx.setKdrq(kdrq);
            String khmc="";//客户名称
            ywCgRkdPrintmx.setKhmc(khmc);
            String tel="";//客户电话
            ywCgRkdPrintmx.setTel(tel);
            String drbm="";//调入部门名称
            ywCgRkdPrintmx.setDrbm(drbm);
            String dcbm="";//调出部门名称
            ywCgRkdPrintmx.setDcbm(dcbm);
            String sgdj="";//手工单号
            ywCgRkdPrintmx.setSgdh(sgdj);
            String cgfsType=ywCgRkdhz.getCgfs();//采购方式
            String name=this.getCgfs(cgfsType);
            ywCgRkdPrintmx.setRkfs(name);

            String fhlx="";//发货类型---批发
            ywCgRkdPrintmx.setFhlx(fhlx);
            String cgy=ywCgRkdhz.getYgmc();//业务员
            ywCgRkdPrintmx.setCgy(cgy);
            String lydh=ywCgRkdhz.getLydh();
            if(StringUtils.isNotEmpty(lydh)){
                ywCgRkdPrintmx.setLydh(lydh);
            }else{
                ywCgRkdPrintmx.setLydh("");
            }
            String bzxx=ywCgRkdhz.getBzxx();//备注信息
            if(StringUtils.isNotEmpty(bzxx)){
                ywCgRkdPrintmx.setBzxx(bzxx);
            }else{
                ywCgRkdPrintmx.setBzxx("");
            }
            String acct="";//默认账户---批发
            ywCgRkdPrintmx.setAcct(acct);
            String qkje="";//欠款金额-----批发
            ywCgRkdPrintmx.setQkje(qkje);
            Double zje=ywCgRkdhz.getZjje();//单据总金额
            if(zje!=null){
                ywCgRkdPrintmx.setZje(String.format("%.2f", zje));
            }else{
                ywCgRkdPrintmx.setZje("");
            }

            Double zsl=ywCgRkdhz.getZjsl();//单据总数量
            ywCgRkdPrintmx.setZsl(zsl);
            String skfh="";//收款方式/退款方式-----批发
            ywCgRkdPrintmx.setSkfh(skfh);
            String ssje="";//实收金额/退款金额-----批发
            ywCgRkdPrintmx.setSsje(ssje);
            String sdye="";//上单余额-----批发
            ywCgRkdPrintmx.setSdye(sdye);
            String bdys="";//本单应收
            ywCgRkdPrintmx.setBdys(bdys);
            String yf="";//运费
            ywCgRkdPrintmx.setYf(yf);
            if(ywCgRkdhz.getSdjy()!=null){
                ywCgRkdPrintmx.setQkje(String.format("%.2f", ywCgRkdhz.getSdjy()));
            }else{
                ywCgRkdPrintmx.setQkje("0.00");
            }

            Double ljye=ywCgRkdhz.getLjqk();//累计余额-----采购（我欠供应商），批发（客户欠我）
            if(ljye!=null){
                ywCgRkdPrintmx.setLjye(String.format("%.2f", ljye));
            }else{
                ywCgRkdPrintmx.setLjye("0.00");
            }
            String kdr=daYgda.getYgmc();//制单人
            ywCgRkdPrintmx.setKdr(kdr);
            String printDate=dateFormat1.format(new Date());//打印时间
            ywCgRkdPrintmx.setPrintDate(printDate);

            String orderURL="";//单据二维码url
            ywCgRkdPrintmx.setOrderURL(orderURL);
            String shopURL="";//店铺二维码url
            ywCgRkdPrintmx.setShopURL(shopURL);

            List<YwCgRkdmxJson> list=null;
            if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())){
                list=ywCgRkdmxMapper.selectZxList(pzh);
                if(list!=null&&list.size()>0){
                    int h=list.size();
                    for (int i = 0; i < h; i++) {
                        Double jj=list.get(i).getJj();
                        list.get(i).setDj(jj);
//                    Double hjjze=Integer.valueOf(list.get(i).getSl()) * list.get(i).getJj();
//                    list.get(i).setHjjze(hjjze);
                    }
                }
                //转换格式
                this.getNewList(sixCode,list);
            }
            if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&"1".equals(ywMxPrintCommon.getPrintType())){
                //判断
                DaSpBxbt daSpBxbt= daSpSpdaMxService.findDaSpBxbt(sixCode);
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

                    YwCgRkPrintDetailJson ywCgRkPrintDetailJson=new YwCgRkPrintDetailJson();
                    if(spkhList!=null&&spkhList.size()>0){
                        int s=spkhList.size();
                        for (int i = 0; i < s; i++) {
                            String spkh=spkhList.get(i);
                            //克隆spkh,spkm,jldw
                            YwCgRkListJson newYwCgRkListJson= (YwCgRkListJson) ywCgRkListJson.clone();
                            List<YwCgRkPrintDetailJson> ywCgRkPrintDetailJsonList=new ArrayList<YwCgRkPrintDetailJson>();
                            Integer khsl=0;
                            Double khje=0.00;
                            for (int j = 0; j < k; j++) {
                                if(spkh.equals(list.get(j).getSpkh())){
                                    YwCgRkPrintDetailJson newYwCgRkPrintDetailJson= (YwCgRkPrintDetailJson) ywCgRkPrintDetailJson.clone();
                                    newYwCgRkListJson.setSpkh(spkh);
                                    newYwCgRkListJson.setSpkm(list.get(j).getKsmc());
                                    newYwCgRkListJson.setJldw(list.get(j).getJldw());
                                    newYwCgRkListJson.setFhlx("");
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
                                    }
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkPrintDetailJson.setLsj(String.format("%.0f", list.get(j).getLsj()));
                                    }
                                    if(list.get(j).getSl()!=null){
                                        newYwCgRkPrintDetailJson.setSl(list.get(j).getSl());
                                    }
                                    if(list.get(j).getZkl()!=null){
                                        newYwCgRkPrintDetailJson.setZkl(String.format("%.2f", list.get(j).getZkl()));
                                    }
                                    if("0".equals(daSpBxbt.getQybz())) {//表示三属性--不包含版型
                                        newYwCgRkPrintDetailJson.setYsbxcm(list.get(j).getYsmc()+" "+list.get(j).getCmbt());
                                    }else{//表示四属性--包含版型
                                        newYwCgRkPrintDetailJson.setYsbxcm(list.get(j).getYsmc()+" "+list.get(j).getBxmc()+" "+list.get(j).getCmbt());
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
            }else if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&"3".equals(ywMxPrintCommon.getPrintType())){//款式汇总
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
                                    newYwCgRkListJson.setFhlx("");
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
            }else if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType()) && "4".equals(ywMxPrintCommon.getPrintType())){
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
                    List<LsYwCgRkListJson> ywCgRkListJsonList = new ArrayList<LsYwCgRkListJson>();
                    LsYwCgRkListJson lsYwCgRkListJson = new LsYwCgRkListJson();

                    LsYwCgRkPrintDetailJson lsYwCgRkPrintDetailJson = new LsYwCgRkPrintDetailJson();
                    if(spkhList!=null&&spkhList.size()>0){
                        int s=spkhList.size();
                        for (int i = 0; i < s; i++) {
                            String spkh=spkhList.get(i);
                            //克隆spkh,spkm,jldw
                            LsYwCgRkListJson newYwCgRkListJson = (LsYwCgRkListJson) lsYwCgRkListJson.clone();
                            List<LsYwCgRkPrintDetailJson> ywCgRkPrintDetailJsonList = new ArrayList<LsYwCgRkPrintDetailJson>();
                            Integer khsl=0;
                            Double khje=0.00;
                            for (int j = 0; j < k; j++) {
                                if(spkh.equals(list.get(j).getSpkh())){
                                    LsYwCgRkPrintDetailJson newYwCgRkPrintDetailJson = (LsYwCgRkPrintDetailJson) lsYwCgRkPrintDetailJson.clone();
                                    newYwCgRkListJson.setSpkh(spkh);
                                    newYwCgRkListJson.setSpkm(list.get(j).getKsmc());
                                    newYwCgRkListJson.setJldw(list.get(j).getJldw());
                                    newYwCgRkListJson.setFhlx("");
                                    if(StringUtils.isNotEmpty(list.get(j).getImg())){
                                        newYwCgRkListJson.setImg(list.get(j).getImg());
                                    }else{
                                        newYwCgRkListJson.setImg("");
                                    }
                                    newYwCgRkListJson.setLsj(String.format("%.0f", list.get(j).getLsj()));
                                    newYwCgRkListJson.setDpj(String.format("%.2f", list.get(j).getDpj()));
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
                                    }
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkPrintDetailJson.setLsj(String.format("%.0f", list.get(j).getLsj()));
                                    }
                                    if(list.get(j).getSl()!=null){
                                        newYwCgRkPrintDetailJson.setSl(list.get(j).getSl());
                                    }
                                    if(list.get(j).getZkl()!=null){
                                        newYwCgRkPrintDetailJson.setZkl(String.format("%.2f", list.get(j).getZkl()));
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
//                                    newYwCgRkPrintDetailJson.setZke(String.format("%.2f", list.get(j).getZke()));
                                    newYwCgRkPrintDetailJson.setDpj(String.format("%.2f", list.get(j).getDpj()));
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
            }else if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&"5".equals(ywMxPrintCommon.getPrintType())){//款式颜色汇总
                list=ywCgRkdmxMapper.selectYsHzList(pzh);

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
                                    newYwCgRkListJson.setSpkm(list.get(j).getKsmc());
                                    newYwCgRkListJson.setJldw(list.get(j).getJldw());
                                    newYwCgRkListJson.setFhlx("");
                                    newYwCgRkListJson.setSpkh(list.get(j).getSpkh());
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

    @Override
    public Integer batchDeleteByPzh(String sixCode,String pzh) {

        Integer flag=ywCgRkdmxMapper.batchDeleteByPzh(pzh);
        return flag;
    }
    @Override
    public List<YwCgRkdmxJson> getNewList(String sixCode,List<YwCgRkdmxJson> list){
        if(list!=null&&list.size()>0){
            int k=list.size();
            //先从redis中获取
//            String daSpCmzbJson=redisUtil.get(sixCode,SystemConstant.)
            //获取所有的尺码组
            List<DaSpCmzb> daSpCmzbList=daSpCmzbService.getList(sixCode);
            //获取所有的尺码代码
            List<DaSpCmdm> daSpCmdmList=daSpCmdmService.getList(sixCode);
            //获取所有的尺码标题
            List<DaSpCmbt> daSpCmbtList=daSpCmbtService.getList(sixCode);

            for (int i = 0; i < k; i++) {
                //一个采购入库单下的不同款可能用的是不同的尺码标题
                //通过尺码组编号和尺码编号查询尺码标题
                YwCgRkdmxJson bean=list.get(i);
//                DaSpCmbt daSpCmbt=daSpCmbtService.findByCmzbh(sixCode,bean.getCmzbh());
                if(daSpCmzbList!=null&&daSpCmzbList.size()>0){
                    int z=daSpCmzbList.size();
                    ok:
                    for (int j = 0; j <z ; j++) {
                        DaSpCmzb daSpCmzb=daSpCmzbList.get(j);
                        if(daSpCmzb.getCmzbm().equals(bean.getCmzbh())){
                            String cmdm=daSpCmzb.getCmdm();
                            if(daSpCmdmList!=null&&daSpCmdmList.size()>0){
                                int d=daSpCmdmList.size();
                                for (int l = 0; l <d ; l++) {
                                    DaSpCmdm daSpCmdm =daSpCmdmList.get(l);
                                    if(daSpCmdm.getCmdm().equals(cmdm)){
                                        String cmbt=daSpCmdm.getCmbt();
                                        if(daSpCmbtList!=null&&daSpCmbtList.size()>0){
                                            int b=daSpCmbtList.size();
                                            for (int m = 0; m <b ; m++) {
                                                DaSpCmbt daSpCmbt= daSpCmbtList.get(m);
                                                if(daSpCmbt.getCmbt().equals(cmbt)){//获取尺码标题
                                                    Method[] methods=daSpCmbt.getClass().getMethods();
                                                    for (int n = 0; n <methods.length ; n++) {
                                                        if(("get"+bean.getCm()).toLowerCase().equals(methods[n].getName().toLowerCase())){
                                                            try {
                                                                String cmbt1 =(String)methods[n].invoke(daSpCmbt);
                                                                bean.setCmbt(cmbt1);
                                                                break ok ;
                                                            } catch (IllegalAccessException e) {
                                                                e.printStackTrace();
                                                            } catch (InvocationTargetException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return list;
    }

    public String getCgfs(String cgfsType){
        String name="";
        if(StringUtils.isNotEmpty(cgfsType)){
            switch(cgfsType){
                case "1" :
                    name="期初入库";
                    break;
                case "2" :
                    name="代销入库";
                    break;
                case "3" :
                    name="退货入库";
                    break;
                case "4" :
                    name="调拨入库";
                    break;
                case "5" :
                    name="采购退库";
                    break;
                case "6" :
                    name="连锁出库";
                    break;
                case "7" :
                    name="采购入库";
                    break;
                case "8" :
                    name="批发出库";
                    break;
                case "9" :
                    name="渠道入库";
                    break;
                case "10" :
                    name="渠道出库";
                    break;
                case "11" :
                    name="调拨出库";
                    break;
                case "12" :
                    name="订单入库";
                    break;
                case "13" :
                    name="退货调整";
                    break;
                default :
                    name="";
                    break;
            }
        }
        return name;
    }


}
