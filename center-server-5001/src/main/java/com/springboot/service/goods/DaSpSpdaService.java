package com.springboot.service.goods;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.common.Pages;
import com.springboot.model.goods.DaSpSpda;
import com.springboot.model.goods.EsDaSpSpdaJson;

import java.util.List;

/**
 * <p>
 * 商品商品档案 服务类
 * </p>
 *
 * @author zjq
 * @since 2020-04-21
 */
public interface DaSpSpdaService extends IService<DaSpSpda> {

    List<DaSpSpda> findList(String sixCode);

    List<EsDaSpSpdaJson> findEsList(String sixCode);

    EsDaSpSpdaJson esSave(String sixCode, EsDaSpSpdaJson bean);

    void esDelete(String sixCode, EsDaSpSpdaJson bean);

    void esUpdate(String sixCode, EsDaSpSpdaJson bean);

    void esDeleteAll(String sixCode);

    void esBatchSave(String sixCode,List<EsDaSpSpdaJson> list);

    /**
     * @Name search
     * @Description 无分页条件检索
     * @Author wen
     * @Date 2019/5/24
     * @param
     */
    List<EsDaSpSpdaJson> esSearch(String sixCode, String keyword);
    /**
     * @Name search
     * @Description 有分页条件检索
     * @Author wen
     * @Date 2019/5/24
     * @param
     */
    Pages<EsDaSpSpdaJson> searchPage(String sixCode, EsDaSpSpdaJson esDaSpSpdaJson);

}
