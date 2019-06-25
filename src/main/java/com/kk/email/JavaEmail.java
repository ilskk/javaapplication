package com.kk.email;

import com.kk.stream.SplitAndMergeFile;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
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

    //邮件(含附件,图片)
    static MimeMessage createMimeMessage(Session session,String send,String receive)throws Exception{
        MimeMessage message=new MimeMessage(session);
        //邮件内容:标题,正文,收件人,发件人
        Address address=new InternetAddress(send,"发件人名字","UTF-8");
        message.setFrom(address);
        message.setSubject("这是标题(含图片+附件)...","UTF-8");

        //增加图片节点
        MimeBodyPart imagePart=new MimeBodyPart();
        DataHandler handler=new DataHandler(
                new FileDataSource(SplitAndMergeFile.partDir+"resource.jpg")); //图片地址
        imagePart.setDataHandler(handler);
        imagePart.setContentID("MyPic");

        //创建文本节点,目的是为了加载图片节点
        MimeBodyPart textPart=new MimeBodyPart();
        textPart.setContent("img:<img src='cid=MyPic' />","text/html;charset=UTF-8");

        //将文本节点,图片节点->封装成一个复合节点
        MimeMultipart mimeMultipart=new MimeMultipart();
        mimeMultipart.addBodyPart(imagePart);
        mimeMultipart.addBodyPart(textPart);
        mimeMultipart.setSubType("related"); //关联关系

        //注意!
        MimeBodyPart part=new MimeBodyPart();
        part.setContent(mimeMultipart);

        //附件
        MimeBodyPart attach=new MimeBodyPart();
        DataHandler handler2=new DataHandler(
                new FileDataSource(SplitAndMergeFile.partDir+"List.object")); //附件地址
        attach.setDataHandler(handler2);
        //给附件设置用户名
        attach.setFileName(MimeUtility.encodeText(handler2.getName()));

        //将刚才的文本,图片,附件节点设置成一个混合节点
        MimeMultipart mm=new MimeMultipart();
        mm.addBodyPart(part);
        mm.addBodyPart(attach);
        mm.setSubType("mixed"); //混合关系

        message.setContent(mm);

        //收件人类型:TO-普通,CC-抄送,BCC-密送
        message.setRecipient(MimeMessage.RecipientType.TO,
                new InternetAddress(receive,"收件人A","utf-8"));
        message.setRecipient(MimeMessage.RecipientType.CC,
                new InternetAddress(receive,"抄送人B","utf-8"));

        message.setSentDate(new Date()); //设置发送时间
        message.saveChanges();

        return message;
    }
}
