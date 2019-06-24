package com.kk.email;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocketFactory;
import java.util.Date;
import java.util.Properties;

public class JavaEmail {

    public static void demo()throws Exception{
        Properties prop=new Properties();
        prop.setProperty("mail.transport.protocol","smtp"); //使用协议
        prop.setProperty("mail.smtp.host","smtp.qq.com"); //协议地址
        prop.setProperty("mail.smtp.port","465"); //协议端口
        prop.setProperty("mail.smtp.auth","true"); //需要授权
        //QQ：SSL安全认证
        prop.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback","false");
        prop.setProperty("mail.smtp.socketFactory.port","465");

        Session session=Session.getInstance(prop);
        session.setDebug(true); //开启日志提示

        //创建邮件
        MimeMessage email=createMimeMessage(session,"824363370@qq.com","827612831@qq.com");
        Transport transport = session.getTransport(); //创建连接对象
        transport.connect("824363370@qq.com","hxryobgjuybkbcda"); //建立连接,其中密码以授权码形式体现
        transport.sendMessage(email,email.getAllRecipients());
        transport.close();
    }

    //邮件
    static MimeMessage createMimeMessage(Session session,String send,String receive)throws Exception{
        MimeMessage message=new MimeMessage(session);
        //邮件内容:标题,正文,收件人,发件人
        Address address=new InternetAddress(send,"发件人名字","UTF-8");
        message.setFrom(address);
        message.setSubject("这是标题...","UTF-8");
        message.setContent("正文内容...","text/html;charset=utf-8");
        //收件人类型:TO-普通,CC-抄送,BCC-密送
        message.setRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(receive,"收件人A","UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.CC,
                new InternetAddress(receive,"抄送人B","UTF-8"));

        message.setSentDate(new Date()); //设置发送时间
        message.saveChanges();

        return message;
    }

}
