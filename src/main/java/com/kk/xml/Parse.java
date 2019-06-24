package com.kk.xml;

import java.util.List;

//XML解析
public class Parse {

    public static void demo_1()throws Exception{
        List<Dog> dogs = XMLParseUtils.parseXmlToList("src\\main\\resources\\xml\\Dogs.xml");
        for (Dog dog : dogs) {
            System.out.println(dog);
        }
    }

}
