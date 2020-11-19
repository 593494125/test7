package com.springboot.service.impl.finance;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.SystemConstant;
import com.springboot.dao.finance.CwNewJhfydMxMapper;
import com.springboot.model.finance.CwNewJhfyd;
import com.springboot.model.finance.CwNewJhfydMx;
import com.springboot.model.finance.CwNewXhfyd;
import com.springboot.model.finance.CwNewXhfydMx;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.*;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.finance.CwNewJhfydMxService;
import com.springboot.service.finance.CwNewJhfydService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 进货费用单明细 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CwNewJhfydMxServiceImpl extends ServiceImpl<CwNewJhfydMxMapper, CwNewJhfydMx> implements CwNewJhfydMxService {

    @Resource
    private CwNewJhfydMxMapper cwNewJhfydMxMapper;
    @Resource
    private CwNewJhfydService cwNewJhfydService;
    @Override
    public YwCgRkdPrintmx getCwJhfydDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org, String ywType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        YwCgRkdPrintmx ywCgRkdPrintmx=new YwCgRkdPrintmx();

        //凭证号
        String pzh=ywMxPrintCommon.getPzh();
        ywCgRkdPrintmx.setPzh(pzh);
        //获取企业信息
        String companyName="";
        String orderName="";
        if(org!=null){
            companyName=org.getOrgName();
            ywCgRkdPrintmx.setCompanyName(companyName);
        }
        orderName= SystemConstant.YW_JHFY_ORDER_NAME;
        ywCgRkdPrintmx.setOrderName(orderName);
        //获取部门，供应商名称，日期
        CwNewJhfyd cwNewJhfyd=cwNewJhfydService.findByPzh(sixCode,ywMxPrintCommon.getPzh());

        if(cwNewJhfyd!=null) {
            String bmbh=cwNewJhfyd.getBmbh();
            if(StringUtils.isNotEmpty(bmbh)){
                ywCgRkdPrintmx.setBmbh(bmbh);
            }else{
                ywCgRkdPrintmx.setBmbh("");
            }
            String bmmc = cwNewJhfyd.getBmmc();//部门名称
            if(StringUtils.isNotEmpty(bmmc)){
                ywCgRkdPrintmx.setBmmc(bmmc);
            }else{
                ywCgRkdPrintmx.setBmmc("");
            }
            String gysbh=cwNewJhfyd.getGysbh();
            if(StringUtils.isNotEmpty(gysbh)){
                ywCgRkdPrintmx.setGysbh(gysbh);
            }else{
                ywCgRkdPrintmx.setGysbh("");
            }
            String gysmc=cwNewJhfyd.getGysmc();
            if(StringUtils.isNotEmpty(gysmc)){
                ywCgRkdPrintmx.setGysmc(gysmc);
            }else{
                ywCgRkdPrintmx.setGysmc("");
            }
            String kdrq = dateFormat.format(cwNewJhfyd.getRq());//开单日期
            ywCgRkdPrintmx.setKdrq(kdrq);

            String khmc = "";//客户名称
            ywCgRkdPrintmx.setKhmc(khmc);
            String tel="";
            ywCgRkdPrintmx.setTel(tel);
            String drbm="";
            ywCgRkdPrintmx.setDrbm(drbm);
            String dcbm="";
            ywCgRkdPrintmx.setDcbm(dcbm);
            String sgdj = "";//手工单号
            ywCgRkdPrintmx.setSgdh(sgdj);
            String fhlx="";
            ywCgRkdPrintmx.setFhlx(fhlx);
            String cgy = cwNewJhfyd.getYwy();//业务员
            if(StringUtils.isNotEmpty(cgy)){
                ywCgRkdPrintmx.setCgy(cgy);
            }else{
                ywCgRkdPrintmx.setCgy("");
            }
            String bzxx = cwNewJhfyd.getBzxx();//备注信息
            if(StringUtils.isNotEmpty(bzxx)){
                ywCgRkdPrintmx.setBzxx(bzxx);
            }else{
                ywCgRkdPrintmx.setBzxx("");
            }
            String acct="";
            ywCgRkdPrintmx.setAcct(acct);
            String qkje="";
            ywCgRkdPrintmx.setQkje(qkje);
            Double zje = cwNewJhfyd.getJe();//单据总金额
            if(zje!=null){
                ywCgRkdPrintmx.setZje(String.format("%.2f", zje));
                ywCgRkdPrintmx.setBdys(String.format("%.2f", zje));
            }else{
                ywCgRkdPrintmx.setZje("");
                ywCgRkdPrintmx.setBdys("0.00");
            }
            ywCgRkdPrintmx.setZsl(0d);
            ywCgRkdPrintmx.setRkfs("欠款");//付款方式
            ywCgRkdPrintmx.setSkfh("欠款");//付款方式

            String sklx="";
            if(StringUtils.isNotEmpty(sklx)&&"1".equals(sklx)){
                ywCgRkdPrintmx.setSklx("应收款");
            }else if(StringUtils.isNotEmpty(sklx)&&"2".equals(sklx)){
                ywCgRkdPrintmx.setSklx("应付款");
            }else if(StringUtils.isNotEmpty(sklx)&&"3".equals(sklx)){
                ywCgRkdPrintmx.setSklx("定金");
            }else{
                ywCgRkdPrintmx.setSklx("");
            }
            LsSkfh lsSkfh=null;
            ywCgRkdPrintmx.setLsSkfh(lsSkfh);
            Double bcsk=null;//本次货款
            if(bcsk!=null){
                ywCgRkdPrintmx.setSsje(String.format("%.2f", bcsk));
            }else{
                ywCgRkdPrintmx.setSsje("0.00");
            }
            Double sdjy = cwNewJhfyd.getSdjy();//上单结余
            if(sdjy!=null){
                ywCgRkdPrintmx.setSdye(String.format("%.2f", sdjy));
            }else{
                ywCgRkdPrintmx.setSdye("");
            }
            ywCgRkdPrintmx.setBcyshk("0.00");


            Double yhje = null;//
            if(yhje!=null){
                ywCgRkdPrintmx.setYhje(String.format("%.2f", yhje));
            }else{
                ywCgRkdPrintmx.setYhje("0.00");
            }
            Double yf = null;//运费---
            if(yf!=null){
                ywCgRkdPrintmx.setYf(String.format("%.2f", yf));
            }else{
                ywCgRkdPrintmx.setYf("0.00");
            }
            Double ljye = cwNewJhfyd.getLjye();//累计余额-----采购（我欠供应商），批发（客户欠我）
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
            List<LsYwCgRkListJson> lslist=null;
            ywCgRkdPrintmx.setLslist(lslist);
            String hymc="";
            ywCgRkdPrintmx.setHymc(hymc);//会员名称
            String hykh="";
            ywCgRkdPrintmx.setHykh(hykh);
            String hyjf="";
            ywCgRkdPrintmx.setHyjf(hyjf);
            String dqjf="";
            ywCgRkdPrintmx.setDqjf(dqjf);
            String hyczkye="";
            ywCgRkdPrintmx.setHyczkye(hyczkye);
            String zl="";
            ywCgRkdPrintmx.setZl(zl);
            String hyh="";
            ywCgRkdPrintmx.setHyh(hyh);
            String lydh=cwNewJhfyd.getLydh();
            if(StringUtils.isNotEmpty(lydh)){
                ywCgRkdPrintmx.setLydh(lydh);
            }else{
                ywCgRkdPrintmx.setLydh("");
            }

            Double dj=null;//定金
            if(dj!=null){
                ywCgRkdPrintmx.setDj(String.format("%.2f", dj));
            }else{
                ywCgRkdPrintmx.setDj("0.00");
            }
            Double bzj=null;//保证金
            if(bzj!=null){
                ywCgRkdPrintmx.setBzj(String.format("%.2f", bzj));
            }else{
                ywCgRkdPrintmx.setBzj("0.00");
            }
            Double bmsdjy = cwNewJhfyd.getBmsdjy();//部门上单结余
            if(bmsdjy!=null){
                ywCgRkdPrintmx.setBmsdjy(String.format("%.2f", bmsdjy));
            }else{
                ywCgRkdPrintmx.setBmsdjy("");
            }
            Double bmljye = cwNewJhfyd.getBmljye();//部门累计余额-----采购（我欠供应商），批发（客户欠我）
            if(bmljye!=null){
                ywCgRkdPrintmx.setBmljye(String.format("%.2f", bmljye));
            }else{
                ywCgRkdPrintmx.setBmljye("0.00");
            }
            String sm=cwNewJhfyd.getSm();
            if(StringUtils.isNotEmpty(sm)){
                ywCgRkdPrintmx.setSm(sm);
            }else{
                ywCgRkdPrintmx.setSm("");
            }


            List<CwNewJhfydMx> list = cwNewJhfydMxMapper.selectZxList(pzh);

            //纵向
            if (StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType()) && "1".equals(ywMxPrintCommon.getPrintType())) {
                if (list != null && list.size() > 0) {
                    //判断
                    int k = list.size();
                    List<YwCgRkListJson> ywCgRkListJsonList = new ArrayList<YwCgRkListJson>();
                    CwdListDetailJson cwdListDetailJson = new CwdListDetailJson();
                    YwCgRkListJson newYwCgRkListJson = new YwCgRkListJson();
                    List<CwdListDetailJson> ywCgRkPrintDetailJsonList = new ArrayList<CwdListDetailJson>();
                    for (int j = 0; j < k; j++) {
                        CwdListDetailJson newCwdListDetailJson = (CwdListDetailJson) cwdListDetailJson.clone();
                        //拼接YwCgRkPrintDetailJson
                        if(StringUtils.isNotEmpty(list.get(j).getSfkm())){
                            newCwdListDetailJson.setSfkm(list.get(j).getSfkm());
                        }else{
                            newCwdListDetailJson.setSfkm("");
                        }
                        if(StringUtils.isNotEmpty(list.get(j).getKmmc())){
                            newCwdListDetailJson.setKmmc(list.get(j).getKmmc());
                        }else{
                            newCwdListDetailJson.setKmmc("");
                        }
                        if(list.get(j).getJe()!=null){
                            newCwdListDetailJson.setJe(String.format("%.2f", list.get(j).getJe()));
                        }else{
                            newCwdListDetailJson.setJe("0.00");
                        }
                        if(StringUtils.isNotEmpty(list.get(j).getSm())){
                            newCwdListDetailJson.setSm(list.get(j).getSm());
                        }else{
                            newCwdListDetailJson.setSm("");
                        }
                        ywCgRkPrintDetailJsonList.add(newCwdListDetailJson);
                    }
                    ywCgRkListJsonList.add(newYwCgRkListJson);
                    newYwCgRkListJson.setCwDetailList(ywCgRkPrintDetailJsonList);
                    ywCgRkdPrintmx.setList(ywCgRkListJsonList);

                }
            }
        }
        return ywCgRkdPrintmx;
    }
}
