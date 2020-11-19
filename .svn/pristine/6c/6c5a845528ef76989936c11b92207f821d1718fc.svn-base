package com.springboot.service.impl.transfer;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.RedisUtil;
import com.springboot.common.SystemConstant;
import com.springboot.dao.transfer.YwDbRkdmxMapper;
import com.springboot.model.goods.DaSpBxbt;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.*;
import com.springboot.model.transfer.YwDbRkdhz;
import com.springboot.model.transfer.YwDbRkdmx;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.goods.DaSpSpdaMxService;
import com.springboot.service.purchase.YwCgRkdmxService;
import com.springboot.service.transfer.YwDbRkdhzService;
import com.springboot.service.transfer.YwDbRkdmxService;
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
 * @since 2020-05-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwDbRkdmxServiceImpl extends ServiceImpl<YwDbRkdmxMapper, YwDbRkdmx> implements YwDbRkdmxService {


    @Resource
    private YwDbRkdmxMapper ywDbRkdmxMapper;
    @Resource
    private YwCgRkdmxService ywCgRkdmxService;
    @Resource
    private YwDbRkdhzService ywDbRkdhzService;
    @Resource
    private DaSpSpdaMxService daSpSpdaMxService;
    @Resource
    private RedisUtil redisUtil;
    @Override
    public Map<String,Object> getPage(String sixCode, Map<String, String> param) {
        //获取所有入库单的明细
        Map<String,Object> param1=new HashMap<String,Object>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String pzh=param.get("pzh");
        Integer pageNo=Integer.parseInt(param.get("pageNo"));
        Integer pageSize=Integer.parseInt(param.get("pageSize"));
        //获取部门，供应商名称，日期
        YwDbRkdhz ywDbRkdhz=ywDbRkdhzService.findByPzh(sixCode,pzh);
        if(ywDbRkdhz!=null){
            param1.put("bmbh1mc",ywDbRkdhz.getBmbh1mc());
            param1.put("bmbh2mc",ywDbRkdhz.getBmbh2mc());
            param1.put("kdrq",dateFormat.format(ywDbRkdhz.getKdrq()));
            param1.put("bzxx",ywDbRkdhz.getBzxx());
            param1.put("cgy",ywDbRkdhz.getYgmc());
            param1.put("zjsl",ywDbRkdhz.getZjsl());
            param1.put("zjje",ywDbRkdhz.getZjje());

        }
        List<YwCgRkdmxJson> list=ywDbRkdmxMapper.getList(pzh,pageNo,pageSize);
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
        Integer flag=ywDbRkdmxMapper.batchDeleteByPzh(pzh);
        return flag;
    }

    @Override
    public YwCgRkdPrintmx getDcDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        YwCgRkdPrintmx ywCgRkdPrintmx=new YwCgRkdPrintmx();

        //凭证号
        String pzh=ywMxPrintCommon.getPzh();
        ywCgRkdPrintmx.setPzh(pzh);

        //获取部门，供应商名称，日期
        YwDbRkdhz ywDbRkdhz=ywDbRkdhzService.findByPzh(sixCode,ywMxPrintCommon.getPzh());
        //获取企业信息
        String companyName="";
        if(org!=null){
            companyName=org.getOrgName();
            ywCgRkdPrintmx.setCompanyName(companyName);
        }
        //获取单据名称
        String orderName= SystemConstant.YW_DB_ORDER_NAME;
        ywCgRkdPrintmx.setOrderName(orderName);

        if(ywDbRkdhz!=null){
            String gysbh="";
            ywCgRkdPrintmx.setGysbh(gysbh);
            String gysmc="";
            ywCgRkdPrintmx.setGysmc(gysmc);
            String bmbh="";
            ywCgRkdPrintmx.setBmbh(bmbh);
            String bmmc = "";//部门名称
            ywCgRkdPrintmx.setBmmc(bmmc);
            String kdrq=dateFormat.format(ywDbRkdhz.getKdrq());//开单日期
            ywCgRkdPrintmx.setKdrq(kdrq);
            String khmc="";//客户名称
            ywCgRkdPrintmx.setKhmc(khmc);
            String tel="";//客户电话
            ywCgRkdPrintmx.setTel(tel);
            String drbm=ywDbRkdhz.getBmbh1mc();;//调入部门名称
            ywCgRkdPrintmx.setDrbm(drbm);
            String dcbm=ywDbRkdhz.getBmbh2mc();;//调出部门名称
            ywCgRkdPrintmx.setDcbm(dcbm);
            String sgdj="";//手工单号
            ywCgRkdPrintmx.setSgdh(sgdj);

            String fhlx="";//发货类型---批发
            ywCgRkdPrintmx.setFhlx(fhlx);
            String cgy=ywDbRkdhz.getYgmc();//业务员
            ywCgRkdPrintmx.setCgy(cgy);
            String lydh=ywDbRkdhz.getLydh();
            if(StringUtils.isNotEmpty(lydh)){
                ywCgRkdPrintmx.setLydh(lydh);
            }else{
                ywCgRkdPrintmx.setLydh("");
            }
            String bzxx=ywDbRkdhz.getBzxx();//备注信息
            if(StringUtils.isNotEmpty(bzxx)){
                ywCgRkdPrintmx.setBzxx(bzxx);
            }else{
                ywCgRkdPrintmx.setBzxx("");
            }
            String acct="";//默认账户---批发
            ywCgRkdPrintmx.setAcct(acct);
            String qkje="";//欠款金额-----批发
            ywCgRkdPrintmx.setQkje(qkje);
            Double zje=ywDbRkdhz.getZjje();//单据总金额
            if(zje!=null){
                ywCgRkdPrintmx.setZje(String.format("%.2f", zje));
            }else{
                ywCgRkdPrintmx.setZje("");
            }
            String cgfsType=ywDbRkdhz.getCgfs();//调拨方式
            String name=this.getCgfs(cgfsType);
            ywCgRkdPrintmx.setRkfs(name);


            Double zsl=ywDbRkdhz.getZjsl();//单据总数量
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

            ywCgRkdPrintmx.setQkje("");

//            Double ljye=ywCgRkdhz.getLjqk();//累计余额-----采购（我欠供应商），批发（客户欠我）
            ywCgRkdPrintmx.setLjye("0.00");

            String kdr=daYgda.getYgmc();//制单人
            ywCgRkdPrintmx.setKdr(kdr);
            String printDate=dateFormat1.format(new Date());//打印时间
            ywCgRkdPrintmx.setPrintDate(printDate);

            String orderURL="";//单据二维码url
            ywCgRkdPrintmx.setOrderURL(orderURL);
            String shopURL="";//店铺二维码url
            ywCgRkdPrintmx.setShopURL(shopURL);

            List<YwCgRkdmxJson> list=null;
            //查询调拨台账中是否有数据，无数据表示只是保存未记账

                Integer count =ywDbRkdmxMapper.getCount(pzh);
                if(count!=null&&count>0){
                    if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())){
                        list=ywDbRkdmxMapper.selectZxList(pzh);
                    }else{
                        list=ywDbRkdmxMapper.selectYsHzList(pzh);
                    }
                }else{
                    //保存完直接打印，不用记账，查询的表和记完账打印查询的表不一样
                    if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())){
                        list=ywDbRkdmxMapper.selectSaveZxList(pzh);
                    }else{
                        list=ywDbRkdmxMapper.selectSaveYsHzList(pzh);
                    }
                    ywCgRkdPrintmx.setAcct("1");//目前设置成1是为了调拨保存直接打印，数据不往redis中缓存，因为a8修改时未更新redis中数据
                }
                if(list!=null&&list.size()>0){
                    int h=list.size();
                    for (int i = 0; i < h; i++) {
                        Double jj=list.get(i).getJj();
                        list.get(i).setDj(jj);
                        Double hjjze=Integer.valueOf(list.get(i).getSl()) * list.get(i).getLsj();
                        list.get(i).setHjjze(hjjze);
                    }
                }
                //转换格式
            if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())) {
                ywCgRkdmxService.getNewList(sixCode, list);
            }
            //纵向
            if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&"1".equals(ywMxPrintCommon.getPrintType())){
                //判断几属性
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
                                        newYwCgRkPrintDetailJson.setJsj(String.format("%.2f", list.get(j).getLsj()));
                                    }
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkPrintDetailJson.setLsj(String.format("%.0f", list.get(j).getJj()));
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
                //获取系统取价方式
                String csbh=redisUtil.get(sixCode,sixCode+"_systemPcJgCs");
                if(StringUtils.isEmpty(csbh)){
                    csbh=daSpSpdaMxService.findPcPriceByTmbh(sixCode);
                }
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
                                    if(StringUtils.isNotEmpty(csbh)&&"QDPJ".equals(csbh)){
                                        Double lsj=list.get(j).getDpj();
                                        Double hlsjze=lsj * sl;
                                        khlsj=khlsj+hlsjze;
                                    }else{
                                        Double lsj=list.get(j).getJj();
                                        Double hlsjze=lsj * sl;
                                        khlsj=khlsj+hlsjze;
                                    }
                                    //获取Lsj
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkListJson.setLsj(String.format("%.0f", list.get(j).getJj()));
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
            }else if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&"5".equals(ywMxPrintCommon.getPrintType())){//款式颜色汇总
                //获取系统取价方式
                String csbh=redisUtil.get(sixCode,sixCode+"_systemPcJgCs");
                if(StringUtils.isEmpty(csbh)){
                    csbh=daSpSpdaMxService.findPcPriceByTmbh(sixCode);
                }
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
                                    newYwCgRkListJson.setFhlx("");
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
                                    if(StringUtils.isNotEmpty(csbh)&&"QDPJ".equals(csbh)){
                                        Double lsj=list.get(j).getDpj();
                                        Double hlsjze=lsj * sl;
                                        khlsj=khlsj+hlsjze;
                                    }else{
                                        Double lsj=list.get(j).getJj();
                                        Double hlsjze=lsj * sl;
                                        khlsj=khlsj+hlsjze;
                                    }
                                    //获取Lsj
                                    if(list.get(j).getLsj()!=null){
                                        newYwCgRkListJson.setLsj(String.format("%.0f", list.get(j).getJj()));
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
    public String getCgfs(String cgfsType){
        String name="";
        if(StringUtils.isNotEmpty(cgfsType)){
            switch(cgfsType){
                case "CKDBCK" :
                    name="仓库调拨出库";
                    break;
                case "CKDBRK" :
                    name="仓库调拨入库";
                    break;
                case "DBTZD" :
                    name="调拨通知单";
                    break;
                case "MFCK" :
                    name="免费出库";
                    break;
                case "NBDB" :
                    name="内部调拨";
                    break;
                default :
                    name="";
                    break;
            }
        }
        return name;
    }
}
