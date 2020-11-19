package com.springboot.dao.goods;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.goods.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品商品明细 Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-04-21
 */
public interface DaSpSpdaMxMapper extends BaseMapper<DaSpSpdaMx> {

    DaSpSpDaJson findByTmbh(@Param("tmbh") String tmbh);

    DaSpSpDaMxJson findPicByTmbh(@Param("tmbh") String tmbh);

    DaSpSpDaMxJson findPicBySpkh(@Param("tmbh") String tmbh);

    DaSpSpDaMxJson findDaPicBySpkh(@Param("tmbh") String tmbh);

    List<GoodsPic> findPicUrl(@Param("spkh") String spkh);

    List<DaSpSpDaJson> findListByTmbh(@Param("tmbh") String tmbh);

    List<DaSpSpDaJson> findList();

    int findCount();

    String findPcPriceByTmbh();

    List<DaSpSpDaJson> getList(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

    List<SaoDaSpSpDaJson> findSaoSpDaMx(@Param("ygbh") String ygbh, @Param("spkh") String spkh, @Param("qybz") String qybz);

    List<SaoDaSpSpDaJson> findSaoDepartSpDaMx(@Param("ygbh") String ygbh, @Param("spkh") String spkh, @Param("qybz") String qybz);

    DaSpBxbt findDaSpBxbt();
}
