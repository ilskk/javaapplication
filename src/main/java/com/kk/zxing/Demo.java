package com.kk.zxing;

public class Demo {

    private static String imgPath="src\\main\\resources\\zxing\\";
    private static String content="哈喽,世界!";

    public static void demo()throws Exception{
        ZXingUtils.encodeImg(imgPath+"z二维码.png","png",
                content,430,430,imgPath+"logo.jpg");

        ZXingUtils.decodeImg(imgPath+"z二维码.png");
    }

}
