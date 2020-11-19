package com.springboot.service.goods;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.AddReturnJson;
import com.springboot.model.goods.*;

import java.util.List;

/**
 * <p>
 * 商品商品明细 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-21
 */
public interface DaSpSpdaMxService extends IService<DaSpSpdaMx> {

    DaSpSpDaJson findByTmbh(String sixCode,String isStartTmFangan, DaSpSpdaMx daSpSpdaMx);

    DaSpSpDaJson newfindByTmbh(String sixCode,String isStartTmFangan, DaSpSpdaMx daSpSpdaMx);

    DaSpSpDaJson findPcByTmbh(String sixCode,String isStartTmFangan,DaSpSpdaMx daSpSpdaMx);

    DaSpSpDaMxJson findPicByTmbh(String sixCode,String ygbh,String isStartTmFangan, DaSpSpdaMx daSpSpdaMx);

    DaSpSpDaMxJson findDepartByTmbh(String sixCode,String ygbh,String isStartTmFangan, DaSpSpdaMx daSpSpdaMx);

    List<GoodsPic> findPicUrl(String sixCode, DaSpSpdaMx daSpSpdaMx);

    AddReturnJson uploadPic(String sixCode,String token, DaSpSpdaMx daSpSpdaMx,String hostAddress);

    String findPcPriceByTmbh(String sixCode);

    List<DaSpSpDaJson> findListByTmbh(String sixCode,DaSpSpdaMx daSpSpdaMx);

    List<DaSpSpDaJson> findList(String sixCode);

    int findCount(String sixCode);

    List<DaSpSpDaJson> getList(String sixCode,Integer pageNo,Integer pageSize);

    DaSpBxbt findDaSpBxbt(String sixCode);
}
