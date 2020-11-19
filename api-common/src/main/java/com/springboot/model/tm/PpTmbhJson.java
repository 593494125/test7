package com.springboot.model.tm;

import com.springboot.model.BaseSixCode;

import java.io.Serializable;
import java.util.List;

public class PpTmbhJson extends BaseSixCode implements Serializable {

    private String state;
    private List<DaSpTmfaszPpJson> list;


    public List<DaSpTmfaszPpJson> getList() {
        return list;
    }

    public void setList(List<DaSpTmfaszPpJson> list) {
        this.list = list;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public PpTmbhJson() {
    }
}
