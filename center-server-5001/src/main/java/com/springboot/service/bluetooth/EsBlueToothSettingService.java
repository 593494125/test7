//package com.springboot.service.bluetooth;
//
//import com.springboot.model.bluetooth.BlueToothSetting;
//
//import java.util.List;
//
//public interface EsBlueToothSettingService {
//
//    /**
//     * @Name deleteById
//     * @Description 删除 by id
//     * @Author wen
//     * @Date 2019/5/24
//     * @param id
//     * @return void
//     */
//    void deleteById(String sixCode,String id);
//
//    /**
//     * @Name delete
//     * @Description 删除
//     * @Author wen
//     * @Date 2019/5/24
//     * @param bean
//     * @return void
//     */
//    void delete(String sixCode,BlueToothSetting bean);
//
//    /**
//     * @Name save
//     * @Description 保存操作
//     * @Author wen
//     * @Date 2019/5/24
//     * @param bean
//     */
//    BlueToothSetting save(String sixCode,BlueToothSetting bean);
//
//    void esUpdate(String sixCode, BlueToothSetting bean);
//
//    /**
//     * @Name getById
//     * @Description 获取by id
//     * @Author wen
//     * @Date 2019/5/24
//     * @param id
//     */
//    BlueToothSetting getById(String sixCode,String id);
//
//    /**
//     * @Name batchInsert
//     * @Description 批量新增
//     * @Author wen
//     * @param list
//     * @return void
//     */
//    void batchSave(String sixCode,List<BlueToothSetting> list);
//
//    /**
//     * @Name batchDelete
//     * @Description 删除所有
//     * @Author wen
//     * @Date 2019/5/24
//     * @param
//     * @return void
//     */
//    void deleteAll(String sixCode);
//
//    /**
//     * @Name search
//     * @Description 无分页条件检索
//     * @Author wen
//     * @Date 2019/5/24
//     * @param
//     */
//    List<BlueToothSetting> search(String sixCode,String keyword);
//
//    /**
//     * @Name search
//     * @Description 分页条件检索
//     * @Author wen
//     * @Date 2019/5/24
//     * @param
//     */
////    Page<BlueToothSetting> search(String sixCode,String keyword, Page<BlueToothSetting> page);
//}
