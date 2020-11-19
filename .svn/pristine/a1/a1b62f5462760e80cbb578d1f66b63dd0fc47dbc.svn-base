package com.springboot.common;

import java.util.Random;

public class GenerateId {
    public static String generIds(){
        long millis = System.currentTimeMillis();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(99999);
        //如果不足三位前面补0
        String id = millis + String.format("%05d", end3);
        return id;
    }
}
