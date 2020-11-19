package com.springboot.model.bluetooth;

import com.springboot.model.BasePageForm;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author zjq
 * @since 2020-07-08
 */
public class BlueToothSetting extends BasePageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String ppmc;
    private String serviceTz;
    private String tz;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPpmc() {
        return ppmc;
    }

    public void setPpmc(String ppmc) {
        this.ppmc = ppmc;
    }

    public String getServiceTz() {
        return serviceTz;
    }

    public void setServiceTz(String serviceTz) {
        this.serviceTz = serviceTz;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }
}
