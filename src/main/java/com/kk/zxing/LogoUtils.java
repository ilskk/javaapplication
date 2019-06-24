package com.kk.zxing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class LogoUtils {

    //传入logo,二维码->带logo的二维码
    public static BufferedImage logoMatrix(BufferedImage bufImg,String logo)
            throws Exception{
        //在二维码上画logo:产生二维码画板
        Graphics2D graphics = bufImg.createGraphics();

        //画logo:String->BufferImage
        BufferedImage logoImg= ImageIO.read(new File(logo));

        int height = bufImg.getHeight();
        int width = bufImg.getWidth();

        //logo图片
        graphics.drawImage(logoImg,width*2/5,height*2/5,
                width/5,height/5,null);
        //产生一个白色圆角正方形的画笔
        BasicStroke stroke=new BasicStroke(5,
                BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        //将画板和笔关联
        graphics.setStroke(stroke);

        //创建一个正方形
        RoundRectangle2D.Float round=new RoundRectangle2D.Float(width*2/5,height*2/5,width/5,
                height/5, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        graphics.setColor(Color.WHITE);

        graphics.draw(round);

        //灰色边框
        BasicStroke stroke2=new BasicStroke(1,
                BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        graphics.setStroke(stroke2);

        RoundRectangle2D.Float round2=new RoundRectangle2D.Float(width*2/5+2,height*2/5+2,width/5-4,
                height/5-4, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        graphics.setColor(Color.WHITE);
        graphics.setColor(Color.GRAY);
        graphics.draw(round2);

        graphics.dispose();
        bufImg.flush();
        return bufImg;
    }

}
