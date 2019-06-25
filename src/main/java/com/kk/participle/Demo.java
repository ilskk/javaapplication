package com.kk.participle;

import org.ictclas4j.bean.SegResult;
import org.ictclas4j.segment.SegTag;

public class Demo {

    public static void demo(){
        SegTag tag=new SegTag(1); //分词路径
        String str="今天天气不错,我想出去玩,你呢?";
        //TODO:此处有BUG,会抛出空指针异常,暂未在网上找到对应源码!
        SegResult result = tag.split(str);

        System.out.println(result.getFinalResult());

    }
}
