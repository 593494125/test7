package com.springboot.dao.tm;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.springboot.model.tm.DaSpTmfaszPp;
import com.springboot.model.tm.DaSpTmfaszPpJson;
import com.springboot.model.tm.FjTmbhJson1;
import com.springboot.model.tm.SystemTmfa1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjq
 * @since 2020-06-17
 */
public interface DaSpTmfaszPpMapper extends BaseMapper<DaSpTmfaszPp> {

    SystemTmfa1 getSystemTmbh();

    List<DaSpTmfaszPpJson> getPpTmbhList();

    FjTmbhJson1 getFjTmbh(@Param("ppTmbh") String ppTmbh);

    List<FjTmbhJson1> getAllFjTmbh();
}
