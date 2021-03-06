package com.kk.reflect.entity;

import com.kk.reflect.interfaces.MyInterface;
import com.kk.reflect.interfaces.MyInterface2;

import java.io.Serializable;

public class Person implements MyInterface, MyInterface2, Serializable {

    private static final long serialVersionUID=1234L;

    private Integer id;
    private String name;
    private Integer age;
    private Address address;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person() {
    }


    private Person(Integer id) {
        this.id = id;
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(Integer id, String name, Integer age, Address address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    private static void privateMethod(){
        System.out.println("private method ...");
    }

    public static void staticMethod(){
        System.out.println("static method ...");
    }

    @Override
    public void method() {
        System.out.println("实现接口方法");
    }

    @Override
    public void method2(String str) {
        System.out.println("实现接口方法-2..."+str);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
