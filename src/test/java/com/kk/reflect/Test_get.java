package com.kk.reflect;

import com.kk.reflect.entity.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

//反射:在运行状态中,对于任意类,都能知道这个类的结构
public class Test_get {

    public static Class<?> clazz = null;

    public static void separator(){ System.out.println("-------------------------------");}

    static {
        try {
            clazz =Class.forName("com.kk.reflect.entity.Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取反射对象(反射入口):
     * 1,Class.forName("全类名")
     * 2,类名.class
     * 3,对象.getClass()
     */
    static void getClazz() {

        //1,Class.forName("全类名")
        System.out.println(clazz);

        //2,类名.class
        Class<?> clazz2 = Person.class;
        System.out.println(clazz2);

        //3,对象.getClass()
        Person person = new Person();
        Class<?> clazz3 = person.getClass();
        System.out.println(clazz3 );
    }

    //获取所有方法
    static void getMethod() {

        //获取所有public方法(本类,父类,接口)
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }

        separator();

        //获取本类的所有方法(忽略访问修饰符限制)
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }
    }

    //获取所有接口
    static void getInterface(){
        Class<?>[] interfaces=clazz.getInterfaces();
        for (Object o : interfaces) {
            System.out.println(o);
        }
    }

    //获取父类
    static void getSuperClass(){
        Class<?> superClass=clazz.getSuperclass();
        System.out.println(superClass);
    }

    //获取所有构造方法
    static void getConstructor(){

        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
    }

    //获取所有字段(属性)
    static void getField(){

        //获取所有public字段
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        separator();

        //获取本类所有字段
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField);
        }

    }

    //获取本类反射的代表类(接口)的对象
    static void getObject()throws Exception{
        Object o = clazz.newInstance();
        System.out.println(o instanceof Person);
    }

    public static void main(String[] args)throws Exception {

//        getClazz();
//        getMethod();
//        getInterface();
//        getSuperClass();
//        getConstructor();
//        getMethod();
//        getField();
        getObject();

    }



}

