package com.springboot.service.impl.wholesale;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.RedisUtil;
import com.springboot.common.SystemConstant;
import com.springboot.dao.wholesale.YwPfRkdmxMapper;
import com.springboot.model.goods.DaSpBxbt;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkListJson;
import com.springboot.model.purchase.YwCgRkPrintDetailJson;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.model.wholesale.YwPfRkdhz;
import com.springboot.model.wholesale.YwPfRkdmx;
import com.springboot.service.goods.DaSpSpdaMxService;
import com.springboot.service.purchase.YwCgRkdmxService;
import com.springboot.service.wholesale.YwPfRkdhzService;
import com.springboot.service.wholesale.YwPfRkdmxService;
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
 * @since 2020-05-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwPfRkdmxServiceImpl extends ServiceImpl<YwPfRkdmxMapper, YwPfRkdmx> implements YwPfRkdmxService {

    @Resource
    private YwPfRkdmxMapper ywPfRkdmxMapper;
    @Resource
    private YwCgRkdmxService ywCgRkdmxService;
    @Resource
    private YwPfRkdhzService ywPfRkdhzService;
    @Resource
    private DaSpSpdaMxService daSpSpdaMxService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public Map<String,Object> getPage(String sixCode, Map<String, String> param) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取所有入库单的明细
        Map<String,Object> param1=new HashMap<String,Object>();
        //获取所有入库单的明细
        String pzh=param.get("pzh");
        Integer pageNo=Integer.parseInt(param.get("pageNo"));
        Integer pageSize=Integer.parseInt(param.get("pageSize"));
        //获取部门，供应商名称，日期
        YwPfRkdhz ywPfRkdhz=ywPfRkdhzService.findByPzh(sixCode,pzh);
        if(ywPfRkdhz!=null){
            param1.put("khmc",ywPfRkdhz.getKhmc());
            param1.put("bmmc",ywPfRkdhz.getBmmc());
            param1.put("kdrq",dateFormat.format(ywPfRkdhz.getKdrq()));
            param1.put("bzxx",ywPfRkdhz.getBzxx());
            param1.put("cgy",ywPfRkdhz.getYgmc());
            param1.put("zjsl",ywPfRkdhz.getZjsl());
            param1.put("zjje",ywPfRkdhz.getZjje());

        }
        List<YwCgRkdmxJson> list=ywPfRkdmxMapper.getList(pzh,pageNo,pageSize);
        if(list!=null&&list.size()>0){
            int k=list.size();
            for (int i = 0; i <k ; i++) {
                Double lsj=list.get(i).getLsj();
                list.get(i).setDj(lsj);
                Double hjjze=Integer.valueOf(list.get(i).getSl()) * list.get(i).getLsj();
                list.get(i).setHjjze(hjjze);
            }
        }
//        //转换格式
        List<YwCgRkdmxJson> newList=ywCgRkdmxService.getNewList(sixCode,list);
        param1.put("list",newList);

        return param1;
    }

    @Override
    public Integer batchDeleteByPzh(String sixCode, String pzh) {
        Integer flag=ywPfRkdmxMapper.batchDeleteByPzh(pzh);
        return flag;
    }

    @Override
    public YwCgRkdPrintmx getPfDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org,String ywType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        YwCgRkdPrintmx ywCgRkdPrintmx=new YwCgRkdPrintmx();

        //凭证号
        String pzh=ywMxPrintCommon.getPzh();
        ywCgRkdPrintmx.setPzh(pzh);

        //获取部门，供应商名称，日期
        YwPfRkdhz ywPfRkdhz=null;
        if(StringUtils.isNotEmpty(ywType)&&(SystemConstant.LC_TYPE.equals(ywType)||SystemConstant.LT_TYPE.equals(ywType))){
            ywPfRkdhz=ywPfRkdhzService.findLsByPzh(sixCode,ywMxPrintCommon.getPzh());
        }else{
            ywPfRkdhz=ywPfRkdhzService.findByPzh(sixCode,ywMxPrintCommon.getPzh());
        }
        //获取企业信息
        String companyName="";
        if(org!=null){
            companyName=org.getOrgName();
            ywCgRkdPrintmx.setCompanyName(companyName);
        }
        //获取单据名称
        String orderName= SystemConstant.YW_PF_ORDER_NAME;
        ywCgRkdPrintmx.setOrderName(orderName);

        if(ywPfRkdhz!=null) {
            String bmbh=ywPfRkdhz.getBmbh();
            ywCgRkdPrintmx.setBmbh(bmbh);
            String bmmc = ywPfRkdhz.getBmmc();//部门名称
            ywCgRkdPrintmx.setBmmc(bmmc);
            String gysbh="";
            ywCgRkdPrintmx.setGysbh(gysbh);
            String gysmc="";
            ywCgRkdPrintmx.setGysmc(gysmc);
            String kdrq = dateFormat.format(ywPfRkdhz.getKdrq());//开单日期
            ywCgRkdPrintmx.setKdrq(kdrq);
            String khmc = ywPfRkdhz.getKhmc();//客户名称
            ywCgRkdPrintmx.setKhmc(khmc);
            String tel = ywPfRkdhz.getSjhm();//客户电话
            ywCgRkdPrintmx.setTel(tel);
            String sgdj = "";//手工单号
            ywCgRkdPrintmx.setSgdh(sgdj);
            String cgfsType=ywPfRkdhz.getCgfs();//采购方式
            String name=this.getCgfs(cgfsType);
            ywCgRkdPrintmx.setRkfs(name);

            String fhlx = ywPfRkdhz.getFhlx();//发货类型---批发
            if(StringUtils.isNotEmpty(fhlx)){
                ywCgRkdPrintmx.setFhlx(fhlx);
            }else{
                ywCgRkdPrintmx.setFhlx("");
            }
            String cgy = ywPfRkdhz.getYgmc();//业务员
            ywCgRkdPrintmx.setCgy(cgy);
            String lydh=ywPfRkdhz.getLydh();
            if(StringUtils.isNotEmpty(lydh)){
                ywCgRkdPrintmx.setLydh(lydh);
            }else{
                ywCgRkdPrintmx.setLydh("");
            }

            String bzxx = ywPfRkdhz.getBzxx();//备注信息
            if(StringUtils.isNotEmpty(bzxx)){
                ywCgRkdPrintmx.setBzxx(bzxx);
            }else{
                ywCgRkdPrintmx.setBzxx("");
            }
            String acct = ywPfRkdhz.getZh();//默认账户---批发
            if(StringUtils.isNotEmpty(acct)){
                ywCgRkdPrintmx.setAcct(acct);
            }else{
                ywCgRkdPrintmx.setAcct("");
            }
            Double zje = ywPfRkdhz.getZjje();//单据总金额
            if(zje!=null){
                ywCgRkdPrintmx.setZje(String.format("%.2f", zje));
            }else{
                ywCgRkdPrintmx.setZje("");
            }
            Double zsl = ywPfRkdhz.getZjsl();//单据总数量
            ywCgRkdPrintmx.setZsl(zsl);
            String skfh = ywPfRkdhz.getSkfs();//收款方式/退款方式-----批发
            if(StringUtils.isNotEmpty(skfh)){
                ywCgRkdPrintmx.setSkfh(skfh);
            }else{
                ywCgRkdPrintmx.setSkfh("");
            }
            Double ssje = ywPfRkdhz.getBdsk();//实收金额(本单收款)/退款金额-----批发
            if(ssje!=null){
                ywCgRkdPrintmx.setSsje(String.format("%.2f", ssje));
            }else{
                ywCgRkdPrintmx.setSsje("0.00");
            }
            Double yhje = ywPfRkdhz.getBdyh();//优惠金额-----批发
            if(yhje!=null){
                ywCgRkdPrintmx.setYhje(String.format("%.2f", yhje));
            }else{
                ywCgRkdPrintmx.setYhje("0.00");
            }
            Double sdye = ywPfRkdhz.getSdjy();//上单余额-----批发
            if(sdye!=null){
                ywCgRkdPrintmx.setSdye(String.format("%.2f", sdye));
            }else{
                ywCgRkdPrintmx.setSdye("0.00");
            }
            Double bdys = ywPfRkdhz.getBcyshk();//本单应收
            if(bdys!=null){
                ywCgRkdPrintmx.setBdys(String.format("%.2f", bdys));
            }else{
                ywCgRkdPrintmx.setBdys("0.00");
            }
            Double yf = ywPfRkdhz.getDfyf();//运费---
            if(yf!=null){
                ywCgRkdPrintmx.setYf(String.format("%.2f", yf));
            }else{
                ywCgRkdPrintmx.setYf("0.00");
            }
            Double qkje=ywPfRkdhz.getSdjy();
            if(qkje!=null){
                ywCgRkdPrintmx.setQkje(String.format("%.2f", qkje));//欠款金额-----批发
            }else{
                ywCgRkdPrintmx.setQkje("0.00");
            }

            Double ljye = ywPfRkdhz.getLjqk();//累计余额-----采购（我欠供应商），批发（客户欠我）
            if(ljye!=null){
                ywCgRkdPrintmx.setLjye(String.format("%.2f", ljye));
            }else{
                ywCgRkdPrintmx.setLjye("0.00");
            }
            String kdr = daYgda.getYgmc();//制单人
            ywCgRkdPrintmx.setKdr(kdr);
            String printDate = dateFormat1.format(new Date());//打印时间
            ywCgRkdPrintmx.setPrintDate(printDate);

            String orderURL = "";//单据二维码url
            ywCgRkdPrintmx.setOrderURL(orderURL);
            String shopURL = "";//店铺二维码url
            ywCgRkdPrintmx.setShopURL(shopURL);

            List<YwCgRkdmxJson> list = null;
            if(StringUtils.isNotEmpty(ywType)&&(SystemConstant.LC_TYPE.equals(ywType)||SystemConstant.LT_TYPE.equals(ywType))){
                if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())){
                    list = ywPfRkdmxMapper.selectLsZxList(pzh);
                }else{
                    list = ywPfRkdmxMapper.selectLsysHzList(pzh);
                }
            }else{
                if(StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType())&&!"5".equals(ywMxPrintCommon.getPrintType())){
                    list = ywPfRkdmxMapper.selectZxList(pzh);
                }else{
                    list = ywPfRkdmxMapper.selectYsHzList(pzh);
                }
            }
            if (list != null && list.size() > 0) {
                int h = list.size();
                for (int i = 0; i < h; i++) {
                    Double jj = list.get(i).getJj();
                    list.get(i).setDj(jj);
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

                if (list != null && list.size() > 0) {
                    //判断
                    DaSpBxbt daSpBxbt = daSpSpdaMxService.findDaSpBxbt(sixCode);
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
                                    newYwCgRkListJson.setFhlx(list.get(j).getFhlx());
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
                                    newYwCgRkListJson.setFhlx(list.get(j).getFhlx());
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
            } else if (StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType()) && "5".equals(ywMxPrintCommon.getPrintType())) {//款式颜色汇总
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
                                    newYwCgRkListJson.setFhlx(list.get(j).getFhlx());
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
