package com.springboot.model.tm;

import com.springboot.model.BaseSixCode;

import java.io.Serializable;
import java.util.List;

public class FjTmbhJson extends BaseSixCode implements Serializable {

    private String state;
    private List<FjTmbhJson1> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<FjTmbhJson1> getList() {
        return list;
    }

    public void setList(List<FjTmbhJson1> list) {
        this.list = list;
    }

    public FjTmbhJson() {
    }

}
