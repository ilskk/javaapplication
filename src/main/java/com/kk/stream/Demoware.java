package com.kk.stream;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

//试用软件
public class Demoware {

    public static void use()throws Exception{
        Properties prop=new Properties();

        File file=new File(SplitAndMergeFile.partDir+"试用软件.properties");
        if(file.exists()){
            prop.load(new FileReader(file));
        }

        int count=0;
        String value=prop.getProperty("count");
        if(value!=null){
            count=Integer.parseInt(prop.getProperty("count"));
            if(count>=5){
                System.out.println("试用次数已用尽,请前往xxx.com资讯");
                return;
            }else{
                System.out.println("试用软件...");
            }
        }
        prop.setProperty("count",++count+"");
        prop.store(new FileWriter(file),"试用记录");

    }

}
