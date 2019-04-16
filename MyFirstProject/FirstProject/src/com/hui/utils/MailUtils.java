package com.hui.utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
    //email是接受者的邮箱账号，emailmag是发送过去的激活码
    public static void sendMail(String email,String emailMsg) throws MessagingException {
        Properties props=new Properties();
        //设置发送的协议
        props.setProperty("mail.transport.protocol","SMTP");
        //设置'发送'邮件的服务器,发件人的
        props.setProperty("mail.host","smtp.163.com");
        //指定验证为true
        props.setProperty("mail.smtp.auth","true");

        //创建验证器
        Authenticator auth=new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication(){
                //设置发送人的账号和密码，密码必须使用授权码
                return new PasswordAuthentication("yeyuetiao0929@163.com","wang1234");
            }
        };

        Session session=Session.getInstance(props,auth);

        //创建一个Message，它相当于是邮件内容
        Message message=new MimeMessage(session);
        //设置发送者
        message.setFrom(new InternetAddress("yeyuetiao0929@163.com"));

        //设置发送方式与接受者
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));

        //设置邮箱主题
        message.setSubject("用户激活");
        //message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

        String url="http://localhost:8080/FirstProject_war_exploded/RegisterServlet?method=active&code="+emailMsg;
        String content="<h1>这不是一个垃圾邮箱</h1><h3><a href='"+url+"'>"+url+"</a></h3>"+"这不是垃圾邮箱，别屏蔽";
        //设置邮件内容
        message.setContent(content, "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }
}
