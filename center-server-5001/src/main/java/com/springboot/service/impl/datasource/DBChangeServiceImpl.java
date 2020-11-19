package com.springboot.service.impl.datasource;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.springboot.common.DBContextHolder;
import com.springboot.common.DynamicDataSource;
import com.springboot.model.datasource.BaseDatasource;
import com.springboot.service.datasource.BaseDatasourceService;
import com.springboot.service.datasource.DBChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class DBChangeServiceImpl implements DBChangeService {

    @Resource
    private BaseDatasourceService baseDatasourceService;
    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Override
    public boolean changeDb(String datasourceId){

        Map<String,String> param=new HashMap<String,String>();
        //默认切换到主数据源,进行整体资源的查找
        DBContextHolder.clearDataSource();

        List<BaseDatasource> dataSourcesList = baseDatasourceService.selectList(new EntityWrapper<BaseDatasource>());

        for (BaseDatasource dataSource : dataSourcesList) {
            if (dataSource.getId().equals(datasourceId)) {
                System.out.println("需要使用的的数据源已经找到,datasourceId是：" + dataSource.getId());
                //创建数据源连接&检查 若存在则不需重新创建
                try {
                    dynamicDataSource.createDataSourceWithCheck(dataSource);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //切换到该数据源
                DBContextHolder.setDataSource(dataSource.getId());
                return true;
            }
        }
        return false;
    }
}
