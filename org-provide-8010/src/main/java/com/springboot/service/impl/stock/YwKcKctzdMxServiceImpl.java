package com.springboot.service.impl.stock;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.dao.stock.YwKcKctzdMxMapper;
import com.springboot.model.purchase.YwCgRkdmxJson;
import com.springboot.model.stock.YwKcKctzdHz;
import com.springboot.model.stock.YwKcKctzdMx;
import com.springboot.service.purchase.YwCgRkdmxService;
import com.springboot.service.stock.YwKcKctzdHzService;
import com.springboot.service.stock.YwKcKctzdMxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-05-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwKcKctzdMxServiceImpl extends ServiceImpl<YwKcKctzdMxMapper, YwKcKctzdMx> implements YwKcKctzdMxService {

    @Resource
    private YwKcKctzdMxMapper ywKcKctzdMxMapper;
    @Resource
    private YwCgRkdmxService ywCgRkdmxService;
    @Resource
    private YwKcKctzdHzService ywKcKctzdHzService;

    @Override
    public Map<String,Object> getPage(String sixCode,Map<String, String> param) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String,Object> param1=new HashMap<String,Object>();
        //获取所有入库单的明细
        String pzh=param.get("pzh");
        String sgdj=param.get("sgdj");
        Integer pageNo=Integer.parseInt(param.get("pageNo"));
        Integer pageSize=Integer.parseInt(param.get("pageSize"));

        //获取部门，供应商名称，日期
        YwKcKctzdHz ywKcKctzdHz=ywKcKctzdHzService.findByPzh(sixCode,pzh,sgdj);
        if(ywKcKctzdHz!=null){
            param1.put("bmmc",ywKcKctzdHz.getBmmc());
            param1.put("tzrq",dateFormat.format(ywKcKctzdHz.getTzrq()));
            param1.put("bzxx",ywKcKctzdHz.getBz());
            param1.put("czy",ywKcKctzdHz.getYgmc());
            param1.put("sgdj",ywKcKctzdHz.getSgdj());
            param1.put("zjsl",ywKcKctzdHz.getZjsl());
            param1.put("zjje",ywKcKctzdHz.getZjje());
        }
        List<YwCgRkdmxJson> list=ywKcKctzdMxMapper.getList(pzh,sgdj,pageNo,pageSize);
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
        Integer flag=ywKcKctzdMxMapper.batchDeleteByPzh(pzh);
        return flag;
    }
}
