package com.springboot.service.impl.purchase;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.common.DBContextHolder;
import com.springboot.common.DynamicDataSource;
import com.springboot.dao.purchase.YwCgDdmxMapper;
import com.springboot.model.purchase.YwCgDdmx;
import com.springboot.service.purchase.YwCgDdmxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * (采购退货申请明细单)采购明细单 服务实现类
 * </p>
 *
 * @author zjq
 * @since 2020-04-26
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class YwCgDdmxServiceImpl extends ServiceImpl<YwCgDdmxMapper, YwCgDdmx> implements YwCgDdmxService {

    @Resource
    private DynamicDataSource dynamicDataSource;
    @Resource
    private YwCgDdmxMapper ywCgDdmxMapper;

    @Override
    public String initCgDdmx(String sixCode,String flag,String tgflag){
        CallableStatement cstmt = null;
        Connection con=null;

        try {
//            TimeUnit.MILLISECONDS.sleep(50);
            Map<Object, Object> param=dynamicDataSource.getDynamicTargetDataSources();
            String datasourceId=DBContextHolder.getDataSource();
            DruidDataSource druidDataSource=(DruidDataSource)param.get(datasourceId);
            con=druidDataSource.getConnection();
            if("DC".equals(flag)){
                cstmt = con.prepareCall("{call dbo.PYMT_DB(?,?,?)}");
            }else if("PF".equals(flag)||"PT".equals(flag)){
                cstmt = con.prepareCall("{call dbo.PYMT_PF(?,?,?)}");
            }else if("KT".equals(flag)||"KP".equals(flag)){
                cstmt = con.prepareCall("{call dbo.PYMT_KC(?,?,?)}");
            }else{
                cstmt = con.prepareCall("{call dbo.PYMT_CG(?,?,?)}");
//                cstmt = con.prepareCall("{call dbo.prc_init_rkddr_cg_TXT(?,?,?)}");
//                cstmt = con.prepareCall("{call dbo.prc_init_rkddr_cg_TXT_app(?,?,?)}");
            }
            cstmt.setString("flag", flag);
            cstmt.setString("flag2", tgflag);
            cstmt.registerOutParameter("msg", Types.VARCHAR);
            cstmt.execute();
            log.info("initCgDdmx=======>"+cstmt.getString(3));
            return cstmt.getString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                cstmt.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @Override
    public boolean saveBatch(String sixCode,List<YwCgDdmx> ywCgDdmxList) {
        boolean flag=ywCgDdmxMapper.insertBatch(ywCgDdmxList);
        return flag;
    }
}
