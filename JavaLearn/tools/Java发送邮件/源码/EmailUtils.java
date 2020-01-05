
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Email工具类
 */
public class EmailUtils {
    public static void main(String[] args) {

    }
    /**
      * 邮件服务器类型：
      * qq-qq邮箱服务器
      * wy-网易邮箱服务器
      */
    public static enum Type{
        qq,wy
    }
    /**
     *
     * @param from 发件人邮箱
     * @param fromAuthorizationCode 发件人授权码
     * @param to 收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param ptype 邮件服务器：Type.qq-qq的邮件服务器；Type.wy-网易的邮件服务器
     * @throws Exception
     */
    public static void sendEmail(String from,String fromAuthorizationCode,String to,String subject,String content,Type ptype) throws Exception {
        String type = ptype.toString();
        Properties pro = new Properties();
        if(type.equals("wy")){
            type = "163";
        }
        pro.setProperty("mail.host","smtp."+type+".com");//设置QQ邮件服务器
        pro.setProperty("mail.transport.protocol","smtp");//设置传输协议
        pro.setProperty("mail.smtp.auth","true");//需要验证用户名密码

        MailSSLSocketFactory sslSocketFactory = new MailSSLSocketFactory();
        sslSocketFactory.setTrustAllHosts(true);
        pro.put("mail.smtp.ssl.enable","true");
        pro.put("mail.smtp.ssl.socketFactory",sslSocketFactory);

        //Java发送邮件的5个步骤
        //1.创建应用程序所需的环境信息的Session对象
        Session session = Session.getDefaultInstance(pro, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from,fromAuthorizationCode);//发件人邮箱及授权码
            }
        });
        session.setDebug(true);//开启debug模式，查看进度状态

        //2.通过session得到transport对象
        Transport ts = session.getTransport();
        //3.使用邮箱用户名及授权码连接上邮件服务器
        ts.connect("smtp."+type+".com", from, fromAuthorizationCode);
        //4.创建邮件
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);

        //指定邮件发件人
        message.setFrom(new InternetAddress(from));

        //指定邮件收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));

        //邮件标题
        message.setSubject(subject);

        //邮件的文本内容
        message.setContent(content,"text/html;charset=UTF-8");

        //发送邮件
        ts.sendMessage(message,message.getAllRecipients());
        ts.close();
    }
}
