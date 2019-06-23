package com.kk.qrcode;

import jp.sourceforge.qrcode.data.QRCodeImage;

import java.awt.image.BufferedImage;

public class QRCodeImg implements QRCodeImage {

    BufferedImage bufImg;

    public QRCodeImg(BufferedImage bufImg) {
        this.bufImg = bufImg;
    }

    @Override
    public int getWidth() {
        return bufImg.getWidth();
    }

    @Override
    public int getHeight() {
        return bufImg.getHeight();
    }

    @Override
    public int getPixel(int i, int i1) {
        return bufImg.getRGB(i,i1);
    }
}
