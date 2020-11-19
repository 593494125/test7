package com.springboot.service.impl.finance;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.SystemConstant;
import com.springboot.dao.finance.CwNewXhskdMapper;
import com.springboot.model.finance.CwNewXhskd;
import com.springboot.model.mxcommon.YwMxPrintCommon;
import com.springboot.model.org.BaseOrg;
import com.springboot.model.purchase.YwCgRkdPrintmx;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.user.DaQxYhda;
import com.springboot.service.finance.CwNewXhskdService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 财务销货收款单 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-08-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class CwNewXhskdServiceImpl extends ServiceImpl<CwNewXhskdMapper, CwNewXhskd> implements CwNewXhskdService {

    @Resource
    private CwNewXhskdMapper cwNewXhskdMapper;

    @Override
    public CwNewXhskd findByLydh(String sixCode,String lydh) {

        CwNewXhskd bean= cwNewXhskdMapper.findByLydh(lydh);
        return bean;
    }

    @Override
    public YwCgRkdPrintmx getCwXhskdDetailByPzh(String sixCode, YwMxPrintCommon ywMxPrintCommon, DaQxYhda daYgda, BaseOrg org, String ywType) {
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
        //获取部门，供应商名称，日期
        CwNewXhskd cwNewXhskd=cwNewXhskdMapper.findByPzh(ywMxPrintCommon.getPzh());
        //获取单据名称
        orderName= SystemConstant.YW_XHSK_ORDER_NAME;
        ywCgRkdPrintmx.setOrderName(orderName);

        if(cwNewXhskd!=null) {
            String bmbh=cwNewXhskd.getBmbh();
            ywCgRkdPrintmx.setBmbh(bmbh);
            String bmmc = cwNewXhskd.getBmmc();//部门名称
            ywCgRkdPrintmx.setBmmc(bmmc);
            String gysbh="";
            ywCgRkdPrintmx.setGysbh(gysbh);
            String gysmc="";
            ywCgRkdPrintmx.setGysmc(gysmc);
            String kdrq = dateFormat.format(cwNewXhskd.getRq());//开单日期
            ywCgRkdPrintmx.setKdrq(kdrq);
            String khmc = cwNewXhskd.getKhmc();//客户名称
            ywCgRkdPrintmx.setKhmc(khmc);
            String sgdj = "";//手工单号
            ywCgRkdPrintmx.setSgdh(sgdj);
            String skfsType=cwNewXhskd.getSkfs();//收款方式
            String name=this.getSkfs(skfsType);
            ywCgRkdPrintmx.setRkfs(name);
            ywCgRkdPrintmx.setSkfh(name);


            String sklx=cwNewXhskd.getSklx();
            if(StringUtils.isNotEmpty(sklx)&&"1".equals(sklx)){
                ywCgRkdPrintmx.setSklx("应收款");
            }else if(StringUtils.isNotEmpty(sklx)&&"2".equals(sklx)){
                ywCgRkdPrintmx.setSklx("应付款");
            }else if(StringUtils.isNotEmpty(sklx)&&"3".equals(sklx)){
                ywCgRkdPrintmx.setSklx("定金");
            }else{
                ywCgRkdPrintmx.setSklx("");
            }
            String cgy = cwNewXhskd.getYwy();//业务员
            ywCgRkdPrintmx.setCgy(cgy);
            String lydh=cwNewXhskd.getLydh();
            if(StringUtils.isNotEmpty(lydh)){
                ywCgRkdPrintmx.setLydh(lydh);
            }else{
                ywCgRkdPrintmx.setLydh("");
            }
            String bzxx = cwNewXhskd.getBzxx();//备注信息
            if(StringUtils.isNotEmpty(bzxx)){
                ywCgRkdPrintmx.setBzxx(bzxx);
            }else{
                ywCgRkdPrintmx.setBzxx("");
            }
            ywCgRkdPrintmx.setAcct("");
            Double sdjy = cwNewXhskd.getSdjy();//上单结余
            if(sdjy!=null){
                ywCgRkdPrintmx.setSdye(String.format("%.2f", sdjy));
            }else{
                ywCgRkdPrintmx.setSdye("");
            }
            Double bmsdjy = cwNewXhskd.getBmsdjy();//部门上单结余
            if(bmsdjy!=null){
                ywCgRkdPrintmx.setBmsdjy(String.format("%.2f", bmsdjy));
            }else{
                ywCgRkdPrintmx.setBmsdjy("");
            }

            Double bcyshk=cwNewXhskd.getBcyshk();//本次应收货款
            if(bcyshk!=null){
                ywCgRkdPrintmx.setBdys(String.format("%.2f", bcyshk));
            }else{
                ywCgRkdPrintmx.setBdys("0.00");
            }
            Double bcsk=cwNewXhskd.getBcsk();//本次货款
            if(bcsk!=null){
                ywCgRkdPrintmx.setSsje(String.format("%.2f", bcsk));
            }else{
                ywCgRkdPrintmx.setSsje("0.00");
            }
            Double yf = cwNewXhskd.getDfyf();//运费---
            if(yf!=null){
                ywCgRkdPrintmx.setYf(String.format("%.2f", yf));
            }else{
                ywCgRkdPrintmx.setYf("0.00");
            }
            Double dj=cwNewXhskd.getDj();//定金
            if(dj!=null){
                ywCgRkdPrintmx.setDj(String.format("%.2f", dj));
            }else{
                ywCgRkdPrintmx.setDj("0.00");
            }
            Double bzj=cwNewXhskd.getBzj();//保证金
            if(bzj!=null){
                ywCgRkdPrintmx.setBzj(String.format("%.2f", bzj));
            }else{
                ywCgRkdPrintmx.setBzj("0.00");
            }
            Double zsl = cwNewXhskd.getSl();//单据总数量
            ywCgRkdPrintmx.setZsl(zsl);
            Double ljye = cwNewXhskd.getLjye();//累计余额-----采购（我欠供应商），批发（客户欠我）
            if(ljye!=null){
                ywCgRkdPrintmx.setLjye(String.format("%.2f", ljye));
            }else{
                ywCgRkdPrintmx.setLjye("0.00");
            }
            Double bmljye = cwNewXhskd.getBmljye();//部门累计余额-----采购（我欠供应商），批发（客户欠我）
            if(bmljye!=null){
                ywCgRkdPrintmx.setBmljye(String.format("%.2f", bmljye));
            }else{
                ywCgRkdPrintmx.setBmljye("0.00");
            }
            Double yhje = cwNewXhskd.getYhje();//
            if(yhje!=null){
                ywCgRkdPrintmx.setYhje(String.format("%.2f", yhje));
            }else{
                ywCgRkdPrintmx.setYhje("0.00");
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

        }
        return ywCgRkdPrintmx;
    }
    public String getSkfs(String cgfsType){
        String name="";
        if(StringUtils.isNotEmpty(cgfsType)){
            switch(cgfsType){
                case "CS01" :
                    name="支付宝";
                    break;
                case "CS02" :
                    name="微信";
                    break;
                case "CS03" :
                    name="汇款";
                    break;
                case "CS04" :
                    name="云闪付";
                    break;
                case "CS05" :
                    name="11";
                    break;
                case "hk" :
                    name="代收货款";
                    break;
                case "lyjs" :
                    name="联营应收款";
                    break;
                case "qk" :
                    name="欠款";
                    break;
                case "sk" :
                    name="刷卡";
                    break;
                case "xj" :
                    name="现金";
                    break;
                case "yfzk" :
                    name="应付账款";
                    break;
                case "yhzz" :
                    name="银行转账";
                    break;
                case "yszk" :
                    name="应收账款";
                    break;
                default :
                    name="";
                    break;
            }
        }
        return name;
    }
}
