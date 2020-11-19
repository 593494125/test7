package com.springboot.service.impl.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.CommonStaticBean;
import com.springboot.common.Pages;
import com.springboot.common.SystemConstant;
import com.springboot.dao.goods.DaSpSpdaMapper;
import com.springboot.model.bluetooth.BlueToothSetting;
import com.springboot.model.goods.DaSpSpda;
import com.springboot.model.goods.EsDaSpSpdaJson;
//import com.springboot.repositories.EsDaSpSpdaJsonRepository;
import com.springboot.service.goods.DaSpSpdaService;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.index.query.*;
//import org.elasticsearch.search.sort.FieldSortBuilder;
//import org.elasticsearch.search.sort.SortBuilders;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.data.elasticsearch.core.query.UpdateQuery;
//import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class DaSpSpdaServiceImpl extends ServiceImpl<DaSpSpdaMapper, DaSpSpda> implements DaSpSpdaService {


    @Resource
    private DaSpSpdaMapper daSpSpdaMapper;

    @Override
    public List<DaSpSpda> findList(String sixCode) {
        List<DaSpSpda> list=daSpSpdaMapper.selectList(new EntityWrapper<DaSpSpda>());
        return list;
    }

    @Override
    public List<EsDaSpSpdaJson> findEsList(String sixCode) {
        return null;
    }

    @Override
    public EsDaSpSpdaJson esSave(String sixCode, EsDaSpSpdaJson bean) {
        return null;
    }

    @Override
    public void esDelete(String sixCode, EsDaSpSpdaJson bean) {

    }

    @Override
    public void esUpdate(String sixCode, EsDaSpSpdaJson bean) {

    }

    @Override
    public void esDeleteAll(String sixCode) {

    }

    @Override
    public void esBatchSave(String sixCode, List<EsDaSpSpdaJson> list) {

    }

    @Override
    public List<EsDaSpSpdaJson> esSearch(String sixCode, String keyword) {
        return null;
    }

    @Override
    public Pages<EsDaSpSpdaJson> searchPage(String sixCode, EsDaSpSpdaJson esDaSpSpdaJson) {
        return null;
    }
//    @Resource
//    private EsDaSpSpdaJsonRepository esDaSpSpdaJsonRepository;
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;

//    @Override
//    public Pages<EsDaSpSpdaJson> searchPage(String sixCode, EsDaSpSpdaJson esDaSpSpdaJson) {
//        // 校验分页参数
//        if (esDaSpSpdaJson.getPageSize() == null || esDaSpSpdaJson.getPageSize() <= 0) {
//            esDaSpSpdaJson.setPageSize(SystemConstant.pageSize);
//        }
//        if (esDaSpSpdaJson.getPageNo() == null || esDaSpSpdaJson.getPageNo() < SystemConstant.pageNo) {
//            esDaSpSpdaJson.setPageNo(SystemConstant.pageNo);
//        }
//        // 构建搜索查询
//        SearchQuery searchQuery = getSearchQuery(sixCode,esDaSpSpdaJson.getPageNo(),esDaSpSpdaJson.getPageSize(),esDaSpSpdaJson);
//        log.info("searchLogPage: searchContent [{}] \n DSL  = \n {}",esDaSpSpdaJson.getSpkh(),searchQuery.getQuery().toString());
//        Page<EsDaSpSpdaJson> page = esDaSpSpdaJsonRepository.search(searchQuery);
//        Pages<EsDaSpSpdaJson> pages = new Pages<EsDaSpSpdaJson>();
//        pages.setPageNo(esDaSpSpdaJson.getPageNo());
//        pages.setPageSize(esDaSpSpdaJson.getPageSize());
//        pages.setRows(page.getContent());
//        pages.setTotal((int)page.getTotalElements());
//        pages.setTotalPages(page.getTotalPages());
//        //es集群环境下尽可能属性要少，首先是从缓存中获取，从磁盘中获取速度较慢，es显示值分页只能查询10000条数据
//
//        return pages;
//    }
//    private SearchQuery  getSearchQuery(String sixCode,Integer pageNumber, Integer pageSize,EsDaSpSpdaJson esDaSpSpdaJson) {
//        //创建builder
//        String keyword="";
//        BoolQueryBuilder boolQuery= QueryBuilders.boolQuery();
//        BoolQueryBuilder boolQuery1= QueryBuilders.boolQuery();
//        /**
//         *  must
//         所有的语句都 必须（must） 匹配，与 AND 等价。
//         must_not
//         所有的语句都 不能（must not） 匹配，与 NOT 等价。
//         should
//         至少有一个语句要匹配，与 OR 等价。
//         trem
//         精确查找 与= 号等价。
//         match
//         模糊匹配 与like 等价。
//         */
//        //设置多字段组合模糊搜索
//        WildcardQueryBuilder wildcardQueryBuilder=null;
//        QueryBuilder wildcardQueryBuilder2 =null;
//        if(StringUtils.isNotEmpty(esDaSpSpdaJson.getSpkh())){
//            keyword=esDaSpSpdaJson.getSpkh().toLowerCase();
////            keyword= CommonStaticBean.escapeQueryChars(keyword);
//            wildcardQueryBuilder=QueryBuilders.wildcardQuery("spkh.keyword",keyword+"*");
//            wildcardQueryBuilder2 =  QueryBuilders.matchPhrasePrefixQuery("spkh",keyword);
//            //通mq将redis缓存中的数据放入es中
//
//        }
//        if(StringUtils.isNotEmpty(esDaSpSpdaJson.getKsmc())){
//            keyword=esDaSpSpdaJson.getKsmc().toLowerCase();
////            keyword= CommonStaticBean.escapeQueryChars(keyword);
//            wildcardQueryBuilder=QueryBuilders.wildcardQuery("ksmc.keyword",keyword+"*");
//            wildcardQueryBuilder2 =  QueryBuilders.matchPhrasePrefixQuery("ksmc",keyword);
//        }
//        boolQuery1.should(wildcardQueryBuilder).should(wildcardQueryBuilder2);
//        boolQuery.must(boolQuery1);
//        MultiMatchQueryBuilder multiMatchQueryBuilder=QueryBuilders.multiMatchQuery(sixCode,"sixCode");
//        boolQuery.must(multiMatchQueryBuilder);
//
//
//        //时间范围
////        multiMatchQueryBuilder.operator(Operator.OR);
////        RangeQueryBuilder rangeQueryBuilder=QueryBuilders.rangeQuery("time");
////        rangeQueryBuilder.gte("2020-07-01");
////        rangeQueryBuilder.lte("2020-07-31");
////        boolQuery.must(rangeQueryBuilder);
//        //设置排序
//        FieldSortBuilder sort = SortBuilders.fieldSort("id").unmappedType("string").order(SortOrder.DESC);
//
//        //设置分页
//        Pageable pageable =PageRequest.of(pageNumber-1,pageSize);
//        SearchQuery searchQuery=new NativeSearchQueryBuilder().withQuery(boolQuery).withPageable(pageable).withSort(sort).build();
//
//        return searchQuery;
//    }
//    @Override
//    public List<EsDaSpSpdaJson> esSearch(String sixCode, String keyword) {
//        BoolQueryBuilder boolQuery= QueryBuilders.boolQuery();
//        BoolQueryBuilder boolQuery1= QueryBuilders.boolQuery();
//        if(StringUtils.isNotEmpty(keyword)){
//            keyword=keyword.toLowerCase();
//            WildcardQueryBuilder wildcardQueryBuilder=QueryBuilders.wildcardQuery("spkh.keyword",keyword+"*");
//
////            WildcardQueryBuilder wildcardQueryBuilder1 =  QueryBuilders.wildcardQuery("spkh",keyword+"*");
//            QueryBuilder wildcardQueryBuilder2 =  QueryBuilders.matchPhrasePrefixQuery("spkh",keyword);
//            boolQuery1.should(wildcardQueryBuilder).should(wildcardQueryBuilder2);
//            boolQuery.must(boolQuery1);
//        }
//        MultiMatchQueryBuilder multiMatchQueryBuilder=QueryBuilders.multiMatchQuery(sixCode,"sixCode");
//        boolQuery.must(multiMatchQueryBuilder);
//
//        log.info("Elasticsearch的格式是：===>"+boolQuery);
//        SearchQuery searchQuery=new NativeSearchQueryBuilder().withQuery(boolQuery).build();
//        List<EsDaSpSpdaJson> list=elasticsearchTemplate.queryForList(searchQuery,EsDaSpSpdaJson.class);
//
//        return list;
//    }
//
//    @Override
//    public List<EsDaSpSpdaJson> findEsList(String sixCode) {
//        List<EsDaSpSpdaJson> list=daSpSpdaMapper.findEsList();
//        return list;
//    }
//    @Override
//    public EsDaSpSpdaJson esSave(String sixCode, EsDaSpSpdaJson bean) {
//        EsDaSpSpdaJson daSpSpda =esDaSpSpdaJsonRepository.save(bean);
//        return daSpSpda;
//    }
//
//    @Override
//    public void esDelete(String sixCode, EsDaSpSpdaJson bean) {
//        //先查询出删除的是哪一条数据
//        EsDaSpSpdaJson newbean=getBean(sixCode,bean);
//        //删除单条数据
//        if(newbean!=null){
//            esDaSpSpdaJsonRepository.delete(newbean);
//        }
//    }
//
//    @Override
//    public void esUpdate(String sixCode, EsDaSpSpdaJson bean) {
//        //修改数据必须知道id
//        EsDaSpSpdaJson newbean=getBean(sixCode,bean);
//        if(newbean!=null){
//            Map<String,String> map=new HashMap<String,String>();
//            IndexRequest indexRequest = new IndexRequest();
//            map.put("ksmc",newbean.getKsmc());
//            indexRequest.source(map);
//            UpdateQuery updateQuery = new UpdateQueryBuilder()
//                    .withClass(BlueToothSetting.class)
//                    .withId(newbean.getId())
//                    .withIndexRequest(indexRequest)
//                    .build();
//            elasticsearchTemplate.update(updateQuery);
//        }
//    }
//
//    @Override
//    public void esDeleteAll(String sixCode) {
//        esDaSpSpdaJsonRepository.deleteAll();
//    }
//
//    @Override
//    public void esBatchSave(String sixCode, List<EsDaSpSpdaJson> list) {
//        if(list!=null&&list.size()>0){
//            int k=list.size();
//            for (int i = 0; i < k; i++) {
//                EsDaSpSpdaJson bean=list.get(i);
//                bean.setSixCode(sixCode);
//            }
//        }
//        esDaSpSpdaJsonRepository.saveAll(list);
//    }
//
//    public EsDaSpSpdaJson getBean(String sixCode,EsDaSpSpdaJson bean){
//        EsDaSpSpdaJson newbean=null;
//        BoolQueryBuilder boolQuery= QueryBuilders.boolQuery();
//        MultiMatchQueryBuilder multiMatchQueryBuilder=QueryBuilders.multiMatchQuery(sixCode,"sixCode");
//        MultiMatchQueryBuilder multiMatchQueryBuilder1=QueryBuilders.multiMatchQuery(bean.getSpkh(),"spkh");
//        boolQuery.must(multiMatchQueryBuilder).must(multiMatchQueryBuilder1);
//        SearchQuery searchQuery=new NativeSearchQueryBuilder().withQuery(boolQuery).build();
//        List<EsDaSpSpdaJson> list=elasticsearchTemplate.queryForList(searchQuery,EsDaSpSpdaJson.class);
//        if(list!=null&&list.size()>0){
//            newbean=list.get(0);
//        }
//        return newbean;
//    }
}
