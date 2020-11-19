package com.springboot.service.impl.purchase;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.DBContextHolder;
import com.springboot.common.DynamicDataSource;
import com.springboot.dao.purchase.TInitImportCgywTxtMapper;
import com.springboot.model.purchase.TInitImportCgywTxt;
import com.springboot.service.purchase.TInitImportCgywTxtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class TInitImportCgywTxtServiceImpl extends ServiceImpl<TInitImportCgywTxtMapper, TInitImportCgywTxt> implements TInitImportCgywTxtService {


//    @Resource
//    private MyAsyncConfigurer myAsyncConfigurer;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    private DynamicDataSource dynamicDataSource;
    @Resource
    private TInitImportCgywTxtService tInitImportCgywTxtService;
    @Resource
    private TInitImportCgywTxtMapper tInitImportCgywTxtMapper;
    @Override
    public boolean saveBatch(String sixCode, List<TInitImportCgywTxt> list){
        //接收集合各段的 执行的返回结果
//        List<Future<String>> futureList = new ArrayList<Future<String>>();

        //集合总条数
        int size = list.size();
        //将集合切分的段数(2*CPU的核心数)
            int sunSum = 1*Runtime.getRuntime().availableProcessors();
//        int sunSum = 1;
        int listStart,listEnd;
        //当总条数不足sunSum条时 用总条数 当做线程切分值
        if(sunSum > size){
            sunSum = size;
        }
        Boolean flag = true;
        try {
            CountDownLatch countDownLatch = new CountDownLatch(sunSum);
            for (int i = 0; i < sunSum; i++) {
                    //计算切割  开始和结束
                    listStart = size / sunSum * i ;
                    listEnd = size / sunSum * ( i + 1 );
                    //最后一段线程会 出现与其他线程不等的情况
                    if(i == sunSum - 1){
                        listEnd = size;
                    }
                    //线程切断**/
                    List<TInitImportCgywTxt> sunList = list.subList(listStart,listEnd);

                    //每段数据集合并行入库
                    //声明future对象
//                    Future<String> result = new AsyncResult<String>("");
                    //循环遍历该段旅客集合
                    if(null != sunList && sunList.size() >0){
                        ImportTask task = new ImportTask(sixCode,sunList, countDownLatch);
                        taskExecutor.execute(task);
                    }
            }
            //等待所有线程完毕
            countDownLatch.await();
            log.info("数据操作完成!可以在此开始其它业务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            //最后关闭线程池，但执行以前提交的任务，不接受新任务
//            taskExecutor.shutdown();
        }
        //对各个线程段结果进行解析
//        for(Future<String> future : futureList){
//            String str ;
//            if(null != future ){
//                try {
//                    str = future.get().toString();
//                    log.info("current thread id ="+Thread.currentThread().getName()+",result="+str);
//
//                } catch (InterruptedException | ExecutionException e) {
//                    log.info("线程运行异常！");
//                }
//
//            }else{
//                log.info("线程运行异常！");
//            }
//
//        }

        return flag;
    }

    @Override
    public boolean threadPoolSaveData(String sixCode,List<TInitImportCgywTxt> sunList){
        Boolean flag=false;
        final String sql = "INSERT INTO t_init_import_cgyw_txt(spkh, ksmc, jldw, yslsh, ysbh, ysmc, bxbh, bxmc, cmzbh, cm1, cm2, cm3, cm4, cm5, cm6, cm7, cm8, cm9, cm10, cm11, cm12, cm13, cm14, cm15, cm16, cm17, cm18, cm19, cm20, cm21, cm22, cm23, cm24, cm25, cm26, cm27, cm28, cm29, cm30, cm31, cm32, cm33, cm34, cm35,hanghao,tgflag, bzxx, gysbh, yhbh, ywflag, bz1, bz2, bz3, lsj, dpj, jj, zkl,tmbh, sl, tmfa, EpcNo, spbh) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 获取数据库连接
            Map<Object, Object> param=dynamicDataSource.getDynamicTargetDataSources();
            String datasourceId= DBContextHolder.getDataSource();
            DruidDataSource druidDataSource=(DruidDataSource)param.get(datasourceId);
            conn=druidDataSource.getConnection();
//                conn = ds.getConnection();
            if (conn == null) {
                throw(new RuntimeException("获取数据库连接失败"));
            }
            // 预编译SQL
            ps = conn.prepareStatement(sql);
            // 关闭自动提交事务
            conn.setAutoCommit(false);
            for (TInitImportCgywTxt bean : sunList) {
                ps.setString(1, bean.getSpkh());
                ps.setString(2, bean.getKsmc());
                ps.setString(3, bean.getJldw());
                ps.setString(4, bean.getYslsh());
                ps.setString(5, bean.getYsbh());
                ps.setString(6, bean.getYsmc());
                ps.setString(7, bean.getBxbh());
                ps.setString(8, bean.getBxmc());
                ps.setString(9, bean.getCmzbh());
                ps.setInt(10, bean.getCm1());
                ps.setInt(11, bean.getCm2());
                ps.setInt(12, bean.getCm3());
                ps.setInt(13, bean.getCm4());
                ps.setInt(14, bean.getCm5());
                ps.setInt(15, bean.getCm6());
                ps.setInt(16, bean.getCm7());
                ps.setInt(17, bean.getCm8());
                ps.setInt(18, bean.getCm9());
                ps.setInt(19, bean.getCm10());
                ps.setInt(20, bean.getCm11());
                ps.setInt(21, bean.getCm12());
                ps.setInt(22, bean.getCm13());
                ps.setInt(23, bean.getCm14());
                ps.setInt(24, bean.getCm15());
                ps.setInt(25, bean.getCm16());
                ps.setInt(26, bean.getCm17());
                ps.setInt(27, bean.getCm18());
                ps.setInt(28, bean.getCm19());
                ps.setInt(29, bean.getCm20());
                ps.setInt(30, bean.getCm21());
                ps.setInt(31, bean.getCm22());
                ps.setInt(32, bean.getCm23());
                ps.setInt(33, bean.getCm24());
                ps.setInt(34, bean.getCm25());
                ps.setInt(35, bean.getCm26());
                ps.setInt(36, bean.getCm27());
                ps.setInt(37, bean.getCm28());
                ps.setInt(38, bean.getCm29());
                ps.setInt(39, bean.getCm30());
                ps.setInt(40, bean.getCm31());
                ps.setInt(41, bean.getCm32());
                ps.setInt(42, bean.getCm33());
                ps.setInt(43, bean.getCm34());
                ps.setInt(44, bean.getCm35());
                ps.setInt(45, bean.getHanghao());
                ps.setString(46, bean.getTgflag());
                ps.setString(47, bean.getBzxx());
                ps.setString(48, bean.getGysbh());
                ps.setString(49, bean.getYhbh());
                ps.setString(50, bean.getYwflag());
                ps.setString(51, bean.getBz1());
                ps.setString(52, bean.getBz2());
                ps.setString(53, bean.getBz3());
                ps.setDouble(54, bean.getLsj());
                ps.setDouble(55, bean.getDpj());
                ps.setDouble(56, bean.getJj());
                ps.setDouble(57, bean.getZkl());
                ps.setString(58, bean.getTmbh());
                ps.setInt(59, bean.getSl());
                ps.setString(60, bean.getTmfa());
                ps.setString(61, bean.getEpcNo());
                ps.setString(62, bean.getSpbh());
//                    ps=this.getPreparedStatement(bean,ps);
                ps.addBatch();
            }
            // 执行批量入库
            ps.executeBatch();
            // 手动提交事务
            conn.commit();
            flag=true;
        }catch (Exception e) {
            // 批量入库异常，回滚
            try {
                conn.rollback();
                flag=false;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                if(conn != null) {
                    conn.close();
                }
                if(ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    @Override
    public boolean deleteOldData(String sixCode, String yhbh, String ywflag) {
        boolean deleteFlag=tInitImportCgywTxtMapper.deleteOldData(yhbh,ywflag);
        return deleteFlag;
    }

    class ImportTask implements Runnable {

        private String sixCode;
        private List list;
        private CountDownLatch countDownLatch;
        public ImportTask(String sixCode,List list, CountDownLatch countDownLatch) {
           this.sixCode = sixCode;
           this.list = list;
           this.countDownLatch = countDownLatch;
        }

        @Override
         public void run() {
            if (null != list) {
                tInitImportCgywTxtService.threadPoolSaveData(sixCode, list);
             }
            // 发出线程任务完成的信号
            countDownLatch.countDown();
        }
    }
}
