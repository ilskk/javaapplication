package com.kk.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLParseUtils {

    //输入xml文件路径,输出一个List<Dog>
    public static List<Dog> parseXmlToList(String fileName)throws Exception{
        List<Dog> dogs=new ArrayList<>();
        //DOM解析方式:入口(工厂)
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //产品
        DocumentBuilder builder = factory.newDocumentBuilder();
        //解析为一个可以用java处理的document对象
        Document document = builder.parse(new FileInputStream(fileName));
        //获取所有文档的节点
        Element element = document.getDocumentElement();
        //获取dog节点
        NodeList nodeList = element.getElementsByTagName("dog");
        for (int i=0;i<nodeList.getLength();i++) {
            Dog dog=new Dog();
            //获取每一个dog
            Element dogElement = (Element)nodeList.item(i);
            //获取dog的id属性
            Integer id=Integer.parseInt(dogElement.getAttribute("id"));
            dog.setId(id);
            //获取dog的子节点
            NodeList childNodes = dogElement.getChildNodes();
            //遍历每一个子节点
            for (int j = 0; j <childNodes.getLength() ; j++) {
                //获取每一个子节点
                Node item = childNodes.item(j);
                //只拿<xxx>形式的子节点
                if(item.getNodeType()==Node.ELEMENT_NODE){
                    if(item.getNodeName().equals("name")){
                        String name = item.getFirstChild().getNodeValue();
                        dog.setName(name);
                    }else if (item.getNodeName().equals("age")){
                        String age = item.getFirstChild().getNodeValue();
                        dog.setAge(Integer.parseInt(age));
                    }
                }
            }
            dogs.add(dog);
        }
        return dogs;
    }

}
