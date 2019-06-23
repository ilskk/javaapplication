package com.kk.stream;

import java.io.*;
import java.util.*;

//拆分与合并文件
public class SplitAndMergeFile {

    //拆分后的目录
    public static String partDir="src\\main\\resources\\stream\\";
    //原文件
    private static File resource = new File(partDir+"resource.jpg");
    //碎片文件名
    private static String partName="源文件碎片-";
    //碎片文件长度
    private static int partLength=(int)resource.length()/5;

    //拆分文件
    public static void split()throws Exception{
        //思路:1个输入流,多个输出流
        FileInputStream fis=new FileInputStream(resource);

        FileOutputStream fos=null;
        int count=1; //碎片文件个数
        byte[] buf=new byte[partLength]; //定义约源文件5/1的长度
        int len=0;
        while((len=fis.read(buf))!=-1){
            fos=new FileOutputStream(partDir+partName+count++ +".part");
            fos.write(buf,0,len);
            fos.close();
        }
        fis.close();
    }

    //生成源文件和碎片文件的配置文件
    public static void config()throws Exception{
        //方式1:纯用IO流
//        FileWriter fw=new FileWriter(partDir+"碎片文件6.conf");
//        fw.write("fileName="+resource.getName());
//        //系统换行符
//        String systemNewline=System.getProperties().getProperty("line.separator");
//        fw.write(systemNewline);
//        fw.write("partCount="+6);
//        fw.close();

        /*
        方式2:
            Properties:IO流+Map,以key=value的形式写入硬盘
         */
        Properties prop=new Properties();
        prop.setProperty("fileName",resource.getName());
        //自动换行
        prop.setProperty("partCount","6");
        //写入硬盘(持久化)
        prop.store(new FileWriter(partDir+"碎片6.properties"),"file config");

    }

    //合并文件
    public static void merge()throws Exception{
        //第一种方式:循环读每个文件
        //读取多个拆分后的文件
        List<FileInputStream> inputStreams=new ArrayList<>();
        for(int x=1;x<=6;x++){
            inputStreams.add(new FileInputStream(partDir+partName+x+".part"));
        }
        //指定合并后的文件输出流
//        OutputStream out=new FileOutputStream(partDir+"合并后的碎片文件.jpg");
//
//        //将多个输入流,依次读入内存,最后一次性输出合并文件中
//        byte[] buf=new byte[partLength];
//        for (FileInputStream in: inputStreams) {
//            int len=in.read(buf);
//            out.write(buf,0,len);
//            in.close();
//        }
//        out.close();

        //第二种方式:SequenceInputStream-一次性读多个文件
        //多个流->一个流
        Enumeration<FileInputStream> e= Collections.enumeration(inputStreams);
        SequenceInputStream sis=new SequenceInputStream(e);
        byte[] buf=new byte[partLength];
        int lne=0;
        OutputStream out=new FileOutputStream(partDir+"sis-合并后的碎片文件.jpg");
        while ((lne=sis.read(buf))!=-1){
            out.write(buf,0,lne);
        }

        out.close();
        sis.close();
    }

    //读取配置文件
    public static void configReader()throws Exception{
        File confFile=new File(SplitAndMergeFile.partDir+"碎片6.properties");

        Properties prop=new Properties();
        prop.load(new FileReader(confFile));
        for (Object o : prop.keySet()) {
            System.out.println(o+"="+prop.getProperty(o+""));
        }
    }

}
