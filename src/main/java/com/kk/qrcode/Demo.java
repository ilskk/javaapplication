package com.kk.qrcode;

import com.kk.qrcode.util.QRCodeUtils;

public class Demo {

    public static void demo()throws Exception{
        /*
            生成二维码
                1,生成图片的路径
                2,文字信息,网址信息
         */

        String imgPath= "src\\main\\resources\\qrcode\\二维码.png";
        String content="http://www.bilibili.com";

        /*
               3,加密:文字信息->二维码
               4,解密:二维码->文字信息
         */
        QRCodeUtils.encoderQRCode(content,imgPath,"png",17);

        System.out.println(QRCodeUtils.decoderQRCode(imgPath));
    }

}
