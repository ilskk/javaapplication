package com.kk.encryption;

import java.util.Scanner;

public class Application {

    static String uname;
    static String pwd;

    static Scanner in=new Scanner(System.in);

    public static boolean register(){
        boolean flag=false;
        try {
            System.out.println("请输入用户名:");
            uname=in.nextLine();
            System.out.println("请输入密码:");
            pwd=in.nextLine();
            pwd=EncryptionUtils.md5(pwd); //加密
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("注册信息如下:\r\nuname:"+uname+"\r\npwd:"+pwd);

        return flag;
    }

    public static boolean login(){
        boolean flag=false;
        System.out.println("登录...");
        System.out.println("请输入用户名:");
        String logUname=in.nextLine();
        System.out.println("请输入密码:");
        String logPwd=in.nextLine();
        logPwd=EncryptionUtils.md5(logPwd); //加密

        System.out.println("登录信息如下:\r\nuname:"+logUname+"\r\npwd:"+logPwd);

        if(uname.equals(logUname) && pwd.equals(logPwd)){
            flag=true;
        }

        return flag;
    }

    public static void demo(){
        register();
        System.out.println(login()?"登录成功":"登录失败");
    }

}
