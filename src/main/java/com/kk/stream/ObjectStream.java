package com.kk.stream;

import com.kk.reflect.entity.Person;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/*
对象操作流
    序列化/反序列化过程中:都会自动为此类生成一个serialVersionUID,
    序列化id和反序列化id会进行校验,如果相等就是同一个对象,则可以对其进行操作,否则报错;
    如果改变类结构,就不能再读取该类了;
    也可以通过自定义serialVersionUID来避免自动生成;
 */
public class ObjectStream {

    //对象文件后缀
    private static String objectSuffix=".object";

    //序列化对象(将对象保存到硬盘)
    public static void Serialization()throws Exception{
        Person person=new Person("张三");
        ObjectOutputStream oos=new ObjectOutputStream(
                new FileOutputStream(SplitAndMergeFile.partDir+"Person"+objectSuffix));
        //序列化的对象需要实现Serializable接口(标记接口)
        oos.writeObject(person);
        oos.close();
    }

    //读取序列化对象
    public static void read()throws Exception{
        ObjectInputStream ois=new ObjectInputStream(
                new FileInputStream(SplitAndMergeFile.partDir+"Person"+objectSuffix));

        Person person = (Person) ois.readObject();
        System.out.println(person);

        ois.close();
    }

    //集合操作
    public static void setOperations()throws Exception{

        List<Person> list=new ArrayList<>();
        list.add(new Person("哈哈"));
        list.add(new Person("呵呵"));
        list.add(new Person("嘿嘿"));

        ObjectOutputStream oos=new ObjectOutputStream(new
                FileOutputStream(SplitAndMergeFile.partDir+"List"+objectSuffix));
        oos.writeObject(list);
        oos.close();

        ObjectInputStream ois=new ObjectInputStream(
                new FileInputStream(SplitAndMergeFile.partDir+"List"+objectSuffix));

        List<Person> list1 = (List<Person>) ois.readObject();
        System.out.println(list1);

    }
}
