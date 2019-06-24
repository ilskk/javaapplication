package com.kk.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jp.sourceforge.qrcode.util.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ZXingUtils {

    //加密:文字->二维码
    public static void encodeImg(String imgPath,String format,String centent,int width,int height,String logo)
            throws Exception{

        //加密需要的参数
        Map<EncodeHintType,Object> map=new HashMap();

        //排错率:L<M<Q<H
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //编码
        map.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        //外边距
        map.put(EncodeHintType.MARGIN,1);

        //加密content,BarcodeFormat.QR_CODE:要解析的类型(二维码)
        BitMatrix bitMatrix = new MultiFormatWriter().
                encode(centent, BarcodeFormat.QR_CODE,width,height,map);


        //内存中的一张图:boolean[][]->BitMatrix
        BufferedImage bufImg=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for(int x=0; x<width; x++) {
            for(int y=0; y<height; y++) {
                bufImg.setRGB(x,y,(bitMatrix.get(x,y)? Color.BLACK:Color.WHITE));
            }
        }

        //画logo
        bufImg=LogoUtils.logoMatrix(bufImg,logo);

        File img=new File(imgPath);

        //生成图片
        ImageIO.write(bufImg,format,img);
    }

    //解密:二维码->文字
    public static void  decodeImg(String imgPath)throws Exception{
        //file->内存中的一张图片
        BufferedImage img = ImageIO.read(new File(imgPath));

        MultiFormatReader formatReader = new MultiFormatReader();
        LuminanceSource source=new BufferedImageLuminanceSource(img);
        Binarizer binarizer=new HybridBinarizer(source);
        BinaryBitmap bitmap=new BinaryBitmap(binarizer);
        //图片->result
        Map map=new HashMap();
        map.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        Result decode = formatReader.decode(bitmap,map);
        System.out.println("解析结果:"+decode.toString());
    }

}
