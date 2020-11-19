package com.springboot.configurer;//package com.springboot.configurer;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.jjy.modules.common.RedisUtil;
//import com.jjy.modules.model.stock.Stock;
//import com.jjy.modules.service.stock.StockService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class ScheduledService {
//
//    private static Logger log= LoggerFactory.getLogger(ScheduledService.class);
//    @Scheduled(cron = "0/2 * * * * ?")//每个2秒执行一次
//    @Async
//    public void scheduled(){
//        log.info("=============>开始执行任务");
//        long start=System.currentTimeMillis();
//        //定时任务
//            //查询库存表
//            //每两秒轮训一次查询库存表
//            //库存表的数据有可能是新插入的，也有可能是修改的
//            //每次分页查询500条，如果查询结果等于500接着进行下一页查询，小于500不继续查找
//        //将查询的每条数据放入reids
//        Integer pageNum=1;
//        Integer pageSize=500;
//        List<Stock> stockList=stockService.selectStockList(pageNum,pageSize);
//        int len=stockList.size();
//        if(stockList!=null&&len>0&&len<pageSize){//只查询一遍数据库
//            for (int i = 0; i <len ; i++) {
//                List<Stock> groupStockList=new ArrayList<Stock>();
//                Stock bean=stockList.get(i);
//                //redis存储数据（按照款号存）
//                // 取出所有数据的款号
//                    //所有款号分组
//                    //查询每个款号的数据
//                for (int j = 0; j <len ; j++) {
//                    if(bean.getGoodsNo().equals(stockList.get(j).getGoodsNo())){
//                        groupStockList.add(stockList.get(j));
//                        stockList.remove(stockList.get(j));
//                    }
//                }
//                redisUtil.set("goodsNo_"+bean.getGoodsNo(), JSONObject.toJSONString(groupStockList));
//            }
//        }else if(stockList!=null&&len>0&&len>=pageSize){//继续分页查询数据库
//            List<Stock> newStockList=this.getStockList(pageNum,pageSize);
//            newStockList.addAll(stockList);
//            for (int i = 0,len1=newStockList.size(); i <len1 ; i++) {
//                List<Stock> groupStockList=new ArrayList<Stock>();
//                Stock bean=newStockList.get(i);
//                //redis存储数据（按照款号存）
//                // 取出所有数据的款号
//                //所有款号分组
//                //查询每个款号的数据
//                for (int j = 0; j <len ; j++) {
//                    if(bean.getGoodsNo().equals(newStockList.get(j).getGoodsNo())){
//                        groupStockList.add(stockList.get(j));
//                        newStockList.remove(stockList.get(j));
//                    }
//                }
//                redisUtil.set("goodsNo_"+bean.getGoodsNo(), JSONObject.toJSONString(groupStockList));
//            }
//        }
//    }
//    //递归分页查询
//    public List<Stock> getStockList(Integer pageNum,Integer pageSize){
//        List<Stock> newStockList=new ArrayList<Stock>();
//        pageNum++;
//        List<Stock> stockList=stockService.selectStockList(pageNum,pageSize);
//        newStockList.addAll(stockList);
//        int k=stockList.size();
//        if(Integer.valueOf(k)<pageSize){
//            return newStockList;
//        }else{
//            return getStockList(pageNum,pageSize);
//        }
//    }
//    @Autowired
//    private RedisUtil redisUtil;
//    @Autowired
//    private StockService stockService;
//}
