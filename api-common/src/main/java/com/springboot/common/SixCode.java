package com.springboot.common;

import java.util.Random;

public class SixCode {

    //生成随机数字和字母,
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();
        //length为几位密码
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
    //生产的密码包括位数
    public static void  main(String[] args) {
//        SixCode test = new SixCode();
//        System.out.println(test.getStringRandom(6));
        for (int i = 0; i <1000 ; i++) {
            System.out.println(SixCode.getStringRandom(6));
        }
    }
}