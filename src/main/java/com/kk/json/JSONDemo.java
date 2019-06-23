package com.kk.json;

import com.kk.reflect.entity.Address;
import com.kk.reflect.entity.Person;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//Map集合,javaBean,String->JSON对象
public class JSONDemo {

    private static String dir="src\\main\\resources\\json\\";
    private static String jsonArr="[{\"id\":\"4\"},{\"name\":\"si\"},{\"age\":44}]";

    //Map->JSON
    public static void demo_Map(){
        Map<String,String> map=new HashMap<>();
        map.put("s1","zs");
        map.put("s2","ww");
        map.put("s3","ls");

        JSONObject json=new JSONObject(map);
        //可以得到json格式:{"key":value,...}
        System.out.println(json);
    }

    //JavaBean->JSON
    public static void demo_JavaBean(){
        Address address=new Address("地球");
        Person person=new Person(1,"张三",33,address);

        JSONObject json=new JSONObject(person);
        System.out.println(json);
        /*
        {对象属性名:属性值,...}
            {"address":{"addressName":"地球"},
            "name":"张三",
            "id":1,"age":33}
         */
    }

    //String->JSON
    public static void demo_String(){
        String str="{\"0\":\"zero\",\"1\":\"one\"}";
        JSONObject json=new JSONObject(str);
        System.out.println(json);
    }

    //File->JSON(File->String->JSON)
    public static void demo_File()throws Exception{

        //第一种方式:
        FileReader fr=new FileReader(dir+"a.json");
        char[] ch=new char[1024*10];
        int len=fr.read(ch);
        //File->String
        String str=new String(ch,0,len);
        //String->json
        JSONObject json=new JSONObject(str);
        System.out.println(json);
        fr.close();

        //第二种方式:使用commons-io.jar
        String string=FileUtils.readFileToString(new File(dir+"a.json"));
        System.out.println(new JSONObject(string));

    }

    //JSON->File
    public static void demo_JSONFile()throws Exception{
        Map<String,Person> map=new HashMap<>();
        Person p1=new Person(3,"张三",33,new Address("地球"));
        Person p2=new Person(4,"李四",44,new Address("火星"));
        Person p3=new Person(5,"王五",55,new Address("水星"));

        map.put("p1",p1);
        map.put("p2",p2);
        map.put("p3",p3);

        //Map->json
        JSONObject json=new JSONObject(map);

        //生成json文件
        FileWriter fw=new FileWriter(dir+"pMap.json");
        json.write(fw);
        fw.close();
    }

    //Array->JSON
    public static void demo_Array(){
        //StringArray->jsonArray
        JSONArray jArray=new JSONArray(jsonArr);
//        System.out.println(jArray);

        Map<String,String> map=new HashMap<>();
        map.put("k1","v1");
        map.put("k1","v1");
        map.put("k1","v1");

        //对于json的类型转换,通常需要引入另一个json库:json-lib
        net.sf.json.JSONArray jsonArray=new net.sf.json.JSONArray();
        jsonArray=jsonArray.fromObject(map);
        System.out.println(jsonArray);

    }
    
    //JSONArray->Map
    public static void demo_JAM(){
        
        //JSONArray->获取每个json->key:value-Map
        net.sf.json.JSONArray jsonArray=new net.sf.json.JSONArray();
        jsonArray=jsonArray.fromObject(jsonArr);
        
        //JSONArray->获取每一个json
        Map<String,Object> map=new HashMap<>();

        for (Object o : jsonArray) {
            net.sf.json.JSONObject json=(net.sf.json.JSONObject)o;
            //获取每一个json的key/value->Map
            Set<String> set = json.keySet();
            for (String key : set) {
                Object value = json.get(key);
                map.put(key,value);
            }
        }

        System.out.println(map);
    }
    
}
