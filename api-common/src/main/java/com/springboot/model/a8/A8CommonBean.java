package com.springboot.model.a8;

import java.io.Serializable;
import java.util.Map;

public class A8CommonBean implements Serializable {

    private String sixCode;
    private String key;
    private Map<String,String> map;

    public String getSixCode() {
        return sixCode;
    }

    public void setSixCode(String sixCode) {
        this.sixCode = sixCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public A8CommonBean() {
    }
}
