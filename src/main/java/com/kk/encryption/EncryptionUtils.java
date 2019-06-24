package com.kk.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Method;

public class EncryptionUtils {

    //异或实现加密和解密:传入String->String
    public static String xor(String param){//"xxx"->{'x','x','x'}
        char[] chars = param.toCharArray();
        for (int x=0;x<chars.length;x++){
            chars[x]=(char)(chars[x]^6666); //对每一个字符进行加密x->a
        }
        return new String(chars);
    }

    public static String md5(String param){
       return DigestUtils.md5Hex(param.getBytes());
    }

    public static String sha256(String param){
        return DigestUtils.sha256Hex(param.getBytes());
    }

    //Base64加密和解密
    public static String base64Encode(String param)throws Exception{
        Class clazz = Class.forName(
                "com.sun.org.apache.xerces.internal.impl.dv.util.Base64");

        Method encode = clazz.getMethod("encode", byte[].class);

        return (String)encode.invoke(null,param.getBytes());
    }

    public static byte[] base64Decode(String param)throws Exception{
        Class clazz = Class.forName(
                "com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
        Method decode = clazz.getMethod("decode", String.class);
        return (byte[]) decode.invoke(null,param);
    }

    public static void demo()throws Exception{
//        String str="hello";
//        str=xor(str); //第一次异或:加密
//        System.out.println(str);
//        str=xor(str); //第二次异或:解密,可逆操作
//        System.out.println(str);

        /*
            MD5:不可逆,速度快
            SHA256:不可逆,安全性较高
         */
        String str ="world";
//        str = md5("world");
//        str=sha256("world");
//        System.out.println(str);

        str=base64Encode(str);
        System.out.println(str);
        System.out.println(new String(base64Decode(str)));
    }
}
