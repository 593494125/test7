//package com.springboot.service.impl.bluetooth;
//
//import com.springboot.model.bluetooth.BlueToothSetting;
//import com.springboot.model.goods.EsDaSpSpdaJson;
//import com.springboot.repositories.EsBlueToothSettingRepository;
//import com.springboot.service.bluetooth.EsBlueToothSettingService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.index.query.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.*;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@Transactional(rollbackFor = Exception.class)
//@Slf4j
//public class EsBlueToothSettingServiceImpl implements EsBlueToothSettingService {
//
//
//    @Resource
//    private EsBlueToothSettingRepository esBlueToothSettingRepository;
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    @Override
//    public void deleteById(String sixCode, String id) {
//        esBlueToothSettingRepository.deleteById(id);
//    }
//
//    @Override
//    public void delete(String sixCode, BlueToothSetting bean) {
//        esBlueToothSettingRepository.delete(bean);
//    }
//
//    @Override
//    public BlueToothSetting save(String sixCode, BlueToothSetting bean) {
//        //新建索引
////        String index="bluetooth".toLowerCase();
////        IndexQuery indexQuery=new IndexQueryBuilder().withIndexName(index).withObject(bean).build();
////        elasticsearchTemplate.index(indexQuery);
//        BlueToothSetting blueToothSetting =esBlueToothSettingRepository.save(bean);
//        return blueToothSetting;
//    }
//
//    @Override
//    public void esUpdate(String sixCode, BlueToothSetting bean) {
//        Map<String,String> map=new HashMap<String,String>();
//        IndexRequest indexRequest = new IndexRequest();
////        map.put("name",bean.getName());
//        indexRequest.source(map);
//        UpdateQuery updateQuery = new UpdateQueryBuilder()
//                .withClass(BlueToothSetting.class)
//                .withId(bean.getId())
//                .withIndexRequest(indexRequest)
//                .build();
//        elasticsearchTemplate.update(updateQuery);
//    }
//
//    @Override
//    public BlueToothSetting getById(String sixCode, String id) {
//        BlueToothSetting blueToothSetting =esBlueToothSettingRepository.findById(id).get();
//        return blueToothSetting;
//    }
//
//    @Override
//    public void batchSave(String sixCode, List<BlueToothSetting> list) {
//        esBlueToothSettingRepository.saveAll(list);
//    }
//
//    @Override
//    public void deleteAll(String sixCode) {
//        esBlueToothSettingRepository.deleteAll();
//    }
//
//    @Override
//    public List<BlueToothSetting> search(String sixCode, String keyword) {
//        BoolQueryBuilder boolQuery= QueryBuilders.boolQuery();
//        MultiMatchQueryBuilder multiMatchQueryBuilder=QueryBuilders.multiMatchQuery(sixCode,"sixCode");
//        boolQuery.must(multiMatchQueryBuilder);
//        if(StringUtils.isNotEmpty(keyword)){
//            keyword=keyword.toLowerCase();
//            WildcardQueryBuilder wildcardQueryBuilder=QueryBuilders.wildcardQuery("name.keyword",keyword+"*");
//            WildcardQueryBuilder wildcardQueryBuilder1 =  QueryBuilders.wildcardQuery("name",keyword+"*");
////            multiMatchQueryBuilder2.operator(Operator.OR);
////            RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("time");
////            rangeQueryBuilder.gte("2020-07-01");
////            rangeQueryBuilder.lte("2020-07-31");
//            boolQuery.should(wildcardQueryBuilder).should(wildcardQueryBuilder1);
////            boolQuery.must(rangeQueryBuilder);
//        }
//        log.info("Elasticsearch的格式是：===>"+boolQuery);
//
//        SearchQuery searchQuery=new NativeSearchQueryBuilder().withQuery(boolQuery).build();
//        List<BlueToothSetting> list=elasticsearchTemplate.queryForList(searchQuery,BlueToothSetting.class);
//        return list;
//    }
//
////    @Override
////    public Page<BlueToothSetting> search(String sixCode, String keyword, Page<BlueToothSetting> page) {
////        return null;
////    }
//}
