package com.kk.reflect;

import com.kk.reflect.entity.Person;
import com.kk.reflect.util.PropertyUtils;

import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

public class Test_call {

    private static Class<?> clazz=Test_get.clazz;
    private static Person person=null;

    static {
        try {
            person=(Person) clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //获取对象实例,并操作方法
    static void callMethod()throws Exception{
        System.out.println(person);
        person.setId(3);
        person.setName("张三");
        System.out.println(person);

        Test_get.separator();

        Method method = clazz.getDeclaredMethod("privateMethod", null);
        method.setAccessible(true);
        //调用方法invoke(对象,参数值)
        method.invoke(person,null);

        Test_get.separator();

        Method method2 = clazz.getDeclaredMethod("method2", String.class);
        method2.invoke(person,"哈哈");
    }

    //操作字段
    static void callField()throws Exception{
        Field name = clazz.getDeclaredField("name");
        System.out.println(person.getName());
        //访问的是private字段,只有本类能访问,修改字段访问权限
        //name.setAccessible(true):将字段权限修改public
        name.setAccessible(true);
        name.set(person,"王五");
        System.out.println(person.getName());
    }

    //操作构造方法
    static void callConstructor()throws Exception{
//        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
//        for (Constructor<?> constructor : declaredConstructors) {
//            System.out.println(constructor);
//        }

        //获取指定构造方法
        Constructor<?> constructor = clazz.getConstructor(String.class);
//        System.out.println(constructor);

        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(Integer.class);
        declaredConstructor.setAccessible(true);
//        System.out.println(declaredConstructor);
        Person person1 = (Person) declaredConstructor.newInstance(3);
        System.out.println(person1);

        Person p=(Person) clazz.getConstructor().newInstance();
        System.out.println(p);

        Person person = (Person) constructor.newInstance("呵呵");
        System.out.println(person);
    }

    //动态加载类和方法
    static void dynamicLoad()throws Exception{

        Properties prop=new Properties();
        prop.load(new FileReader("src\\main\\resources\\reflect\\class.txt"));

        String className = prop.getProperty("className");
        String methodName = prop.getProperty("methodName");

        Class<?> clazz=Class.forName(className);

        Method method = clazz.getMethod(methodName);
        method.invoke(clazz.newInstance(),null);

    }

    //反射可以忽略泛型检查
    //不建议这样使用
    static void ignoreGenericity()throws Exception{
        ArrayList<Integer> list=new ArrayList();
        list.add(1);
        list.add(22);
        list.add(333);
//        list.add("string");

        System.out.println(list);
        Test_get.separator();

        Class<?> lClass = list.getClass();
        Method add = lClass.getMethod("add", Object.class);
        add.invoke(list,"String...");
        System.out.println(list);

    }

    //通过反射对对象属性进行赋值
    static void universal()throws Exception{

        Person person=new Person();
        System.out.println(person);
        Test_get.separator();

        PropertyUtils.setProperty(person,"id",3);
        PropertyUtils.setProperty(person,"name","张三");
        PropertyUtils.setProperty(person,"age",33);
        System.out.println(person);
    }


    public static void main(String[] args)throws Exception{

//        callMethod();
//        callField();
//        callConstructor();
//        dynamicLoad();
//        ignoreGenericity();
        universal();
    }

}
