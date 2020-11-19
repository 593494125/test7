package com.springboot.service.impl.finance;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.SystemConstant;
import com.springboot.dao.finance.CwNewXhfydMxMapper;
import com.springboot.model.finance.CwNewXhfyd;
import com.springboot.model.finance.CwNewXhfydMx;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.CwdListDetailJson;
import com.springboot.model.purchase.YwCgRkListJson;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.finance.CwNewXhfydMxService;
import com.springboot.service.finance.CwNewXhfydService;
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
 * 销货费用单明细 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CwNewXhfydMxServiceImpl extends ServiceImpl<CwNewXhfydMxMapper, CwNewXhfydMx> implements CwNewXhfydMxService {


    @Resource
    private CwNewXhfydMxMapper cwNewXhfydMxMapper;
    @Resource
    private CwNewXhfydService cwNewXhfydService;
    @Override
    public YwCgRkdPrintmx getCwXhfydDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org, String ywType) {
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
        orderName= SystemConstant.YW_XHFY_ORDER_NAME;
        ywCgRkdPrintmx.setOrderName(orderName);
        //获取部门，供应商名称，日期
        CwNewXhfyd cwNewXhfyd=null;
        if(StringUtils.isNotEmpty(ywType)&& SystemConstant.XHFY_TYPE.equals(ywType)){
            cwNewXhfyd=cwNewXhfydService.findByPzh(sixCode,ywMxPrintCommon.getPzh());

        }
        if(cwNewXhfyd!=null) {
            String bmbh=cwNewXhfyd.getBmbh();
            ywCgRkdPrintmx.setBmbh(bmbh);
            String bmmc = cwNewXhfyd.getBmmc();//部门名称
            ywCgRkdPrintmx.setBmmc(bmmc);
            String gysbh="";
            ywCgRkdPrintmx.setGysbh(gysbh);
            String gysmc="";
            ywCgRkdPrintmx.setGysmc(gysmc);
            String kdrq = dateFormat.format(cwNewXhfyd.getRq());//开单日期
            ywCgRkdPrintmx.setKdrq(kdrq);
            String khmc = cwNewXhfyd.getKhmc();//客户名称
            ywCgRkdPrintmx.setKhmc(khmc);
            String sgdj = "";//手工单号
            ywCgRkdPrintmx.setSgdh(sgdj);
            ywCgRkdPrintmx.setRkfs("欠款");//付款方式
            ywCgRkdPrintmx.setSkfh("欠款");//付款方式
            String sklx="";
            ywCgRkdPrintmx.setSklx(sklx);
            String cgy = cwNewXhfyd.getYwy();//业务员
            if(StringUtils.isNotEmpty(cgy)){
                ywCgRkdPrintmx.setCgy(cgy);
            }else{
                ywCgRkdPrintmx.setCgy("");
            }
            String lydh=cwNewXhfyd.getLydh();
            if(StringUtils.isNotEmpty(lydh)){
                ywCgRkdPrintmx.setLydh(lydh);
            }else{
                ywCgRkdPrintmx.setLydh("");
            }
            String bzxx = cwNewXhfyd.getBzxx();//备注信息
            if(StringUtils.isNotEmpty(bzxx)){
                ywCgRkdPrintmx.setBzxx(bzxx);
            }else{
                ywCgRkdPrintmx.setBzxx("");
            }
            ywCgRkdPrintmx.setAcct("");
            Double sdjy = cwNewXhfyd.getSdjy();//上单结余
            if(sdjy!=null){
                ywCgRkdPrintmx.setSdye(String.format("%.2f", sdjy));
            }else{
                ywCgRkdPrintmx.setSdye("");
            }
            Double bmsdjy = cwNewXhfyd.getBmsdjy();//部门上单结余
            if(bmsdjy!=null){
                ywCgRkdPrintmx.setBmsdjy(String.format("%.2f", bmsdjy));
            }else{
                ywCgRkdPrintmx.setBmsdjy("");
            }
            Double zje = cwNewXhfyd.getJe();//单据总金额
            if(zje!=null){
                ywCgRkdPrintmx.setZje(String.format("%.2f", zje));
                ywCgRkdPrintmx.setBdys(String.format("%.2f", zje));
            }else{
                ywCgRkdPrintmx.setZje("");
                ywCgRkdPrintmx.setBdys("0.00");
            }

            ywCgRkdPrintmx.setYf("0.00");
            ywCgRkdPrintmx.setDj("0.00");
            ywCgRkdPrintmx.setBzj("0.00");
            ywCgRkdPrintmx.setZsl(0d);
            Double ljye = cwNewXhfyd.getLjye();//累计余额-----采购（我欠供应商），批发（客户欠我）
            if(ljye!=null){
                ywCgRkdPrintmx.setLjye(String.format("%.2f", ljye));
            }else{
                ywCgRkdPrintmx.setLjye("0.00");
            }

            Double bmljye = cwNewXhfyd.getBmljye();//部门累计余额-----采购（我欠供应商），批发（客户欠我）
            if(bmljye!=null){
                ywCgRkdPrintmx.setBmljye(String.format("%.2f", bmljye));
            }else{
                ywCgRkdPrintmx.setBmljye("0.00");
            }
            String sm=cwNewXhfyd.getSm();
            if(StringUtils.isNotEmpty(sm)){
                ywCgRkdPrintmx.setSm(sm);
            }else{
                ywCgRkdPrintmx.setSm("");
            }
            String kdr = daYgda.getYgmc();//制单人
            ywCgRkdPrintmx.setKdr(kdr);
            String printDate = dateFormat1.format(new Date());//打印时间
            ywCgRkdPrintmx.setPrintDate(printDate);

            String orderURL = "";//单据二维码url
            ywCgRkdPrintmx.setOrderURL(orderURL);
            String shopURL = "";//店铺二维码url
            ywCgRkdPrintmx.setShopURL(shopURL);

            List<CwNewXhfydMx> list = cwNewXhfydMxMapper.selectZxList(pzh);

            //纵向
            if (StringUtils.isNotEmpty(ywMxPrintCommon.getPrintType()) && "1".equals(ywMxPrintCommon.getPrintType())) {
                if (list != null && list.size() > 0) {
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
