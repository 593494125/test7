package com.springboot.service.tm;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.model.tm.DaSpTmfaszPp;
import com.springboot.model.tm.DaSpTmfaszPpJson;
import com.springboot.model.tm.FjTmbhJson1;
import com.springboot.model.tm.SystemTmfa1;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjq
 * @since 2020-06-17
 */
public interface DaSpTmfaszPpService extends IService<DaSpTmfaszPp> {

    SystemTmfa1 getSystemTmbh(String sixCode);

    List<DaSpTmfaszPpJson> getPpTmbhList(String sixCode);

    FjTmbhJson1 getFjTmbh(String sixCode,String ppTmbh);

    List<FjTmbhJson1> getAllFjTmbh(String sixCode);


}
