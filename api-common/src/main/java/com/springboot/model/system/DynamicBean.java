package com.springboot.model.system;

import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DynamicBean {
    private Object object = null;
    private BeanMap beanMap = null;

    public DynamicBean() {
        super();
    }

    @SuppressWarnings("unchecked")
    public DynamicBean(HashMap propertyMap) {
        this.object = generateBean(propertyMap);
        this.beanMap = BeanMap.create(this.object);
    }

    /**
     * 给bean属性赋值
     *
     * @param property 属性名
     * @param value    值
     */
    public void setValue(String property, Object value) {
        beanMap.put(property, value);
    }

    /**
     * 通过属性名得到属性值
     *
     * @param property 属性名
     * @return 值
     */
    public Object getValue(String property) {
        return beanMap.get(property);
    }

    /**
     * 得到该实体bean对象
     *
     * @return
     */
    public Object getObject() {
        return this.object;
    }

    /**
     * 创建bean方法
     *
     * @param propertyMap
     * @return
     */
    @SuppressWarnings("unchecked")
    private Object generateBean(HashMap propertyMap) {
        BeanGenerator generator = new BeanGenerator();
        Set keySet = propertyMap.keySet();
        for (Iterator i = keySet.iterator(); i.hasNext(); ) {
            String key = (String) i.next();
            generator.addProperty(key, (Class) propertyMap.get(key));
        }
        return generator.create();
    }
}
