package com.springboot.common;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;

public class SHA256Util {

    public static String mySHA256(String str){
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("SHA-256");
            md.update((str + ";sunway*%323cvbacdao&%$@#_)Nv4sadf8a_*^2#&*@123abco!2342323fasf")
                    .getBytes("UTF-8"));
            byte[] encodedPassword = md.digest();
            for (int i = 0; i < encodedPassword.length; i++)
            {
                encodedPassword[i] = (byte)(encodedPassword[i] & 0xff);
            }
            byte[] byteOfDecode = Base64.encodeBase64(encodedPassword);// 调用encodeBase64方法
            String decode = new String(byteOfDecode).replace("+", "_").replace("/", "_");
            return decode;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
}
