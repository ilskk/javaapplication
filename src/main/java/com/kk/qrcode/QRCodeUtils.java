package com.kk.qrcode;


import com.kk.qrcode.QRCodeImg;
import com.swetake.util.Qrcode;
import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeUtils {

    //加密:文字信息->二维码
    public static void encoderQRCode(String content,String imgPath,String imgType,int size)
            throws Exception{

        //BufferedImage:代表内存中的一张图片
        BufferedImage bufImg=qRcodeCommen(content,imgType,size);
        File file=new File(imgPath);

        //生成图片
        ImageIO.write(bufImg,imgType,file);
    }

    /*
        产生一个二维码的BufferedImage
            二维码中隐藏的信息
     */
    public static BufferedImage qRcodeCommen(String hidContent,String imgType,int size)
            throws Exception{
        //QRCode:String->boolean[][]
        Qrcode qrcode=new Qrcode();
        //设置二维码排错率:7% L<Q<H 30% ,拍错率越高,可存储的信息越少,但是对二维码清晰度要求越小
        qrcode.setQrcodeErrorCorrect('M');
        //可存放的信息类型:N-数字,A-数字+A-Z,B:所有
        qrcode.setQrcodeEncodeMode('B');
        //二维码取值范围:1~40
        qrcode.setQrcodeVersion(size);
        //String->boolean[][]
        boolean[][] booleans = qrcode.calQrcode(hidContent.getBytes("UTF-8"));

        int imgSize=67+12*(size-1);

        BufferedImage bufImg=
                new BufferedImage(imgSize,imgSize,BufferedImage.TYPE_INT_RGB);

        //创建一个画板
        Graphics2D graphics = bufImg.createGraphics();

        graphics.setBackground(Color.white); //设置背景颜色为白色
        graphics.clearRect(0,0,imgSize,imgSize); //初始化
        graphics.setColor(Color.black); //设置二维码颜色

        int pixoff=2;
        for(int x=0; x<booleans.length; x++) {
            for(int y=0; y<booleans.length; y++) {
                if(booleans[x][y]){
                    graphics.fillRect(x*3+pixoff,y*3+pixoff,3,3);
                }
            }
        }

        //为二维码增加logo,将硬盘中的图片加载为一个image对象
        Image logo=ImageIO.read(new File("src\\main\\resources\\qrcode\\logo.png"));
        int height = bufImg.getHeight();
        int width = bufImg.getWidth();

        //在二维码上画logo
        graphics.drawImage(logo,imgSize/5*2,imgSize/5*2,width/5,height/5,null);

        graphics.dispose(); //释放空间
        bufImg.flush(); //清理

        return bufImg;
    }

    //解密:二维码->文字信息
    public static String decoderQRCode(String imgPath)throws Exception{

        //BufferedImage读取硬盘中的二维码文件
        BufferedImage bufImg =ImageIO.read(new File(imgPath));
        //解密
        QRCodeDecoder decoder = new QRCodeDecoder();

        QRCodeImg img=new QRCodeImg(bufImg);
        byte[] buf = decoder.decode(img);

        return new String(buf,"UTF-8");
    }

}
