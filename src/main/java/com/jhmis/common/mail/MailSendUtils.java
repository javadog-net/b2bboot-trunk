package com.jhmis.common.mail;
/**
 * 简单邮件（不带附件的邮件）发送器
 */

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import com.jhmis.common.beanvalidator.RegValidators;
import com.jhmis.common.wsClient.test.main;
import com.jhmis.core.persistence.UseUrl;


public class MailSendUtils {
    /**
     * 以文本格式发送邮件
     * @param mailInfo 待发送的邮件的信息
     */
    @Autowired
    private UseUrl baseUrl;

    public boolean sendTextMail(MailBody mailInfo) throws Exception {
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        //pro.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");//SSL证书Socket工厂
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        // logBefore(logger, "构造一个发送邮件的session");

        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getFromAddress());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        //Address to = new InternetAddress(mailInfo.getToAddress());
        String toEmails[] = mailInfo.getToAddress().split(";");
        if (toEmails.length > 1) {//多人发送
            Address to[] = new Address[toEmails.length];
            for (int i = 0; i < toEmails.length; i++) {
                String sTo = toEmails[i];
                to[i] = new InternetAddress(sTo);
            }
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipients(Message.RecipientType.TO, to);
        }
        if (toEmails.length == 1) {//单人发送
            Address to = new InternetAddress(toEmails[0]);
            mailMessage.setRecipient(Message.RecipientType.TO, to);
        }
        // 设置邮件消息的主题
        mailMessage.setSubject(mailInfo.getSubject());
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());
        // 设置邮件消息的主要内容
        String mailContent = mailInfo.getContent();
        mailMessage.setText(mailContent);
        // 发送邮件
        Transport.send(mailMessage);
        System.out.println("发送成功！");
        return true;
    }

    /**
     * 以HTML格式发送邮件
     * @param mailInfo 待发送的邮件信息
     */
    public boolean sendHtmlMail(MailBody mailInfo) throws Exception {
        // 判断是否需要身份认证
        MailAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();

        //如果需要身份认证，则创建一个密码验证器
        if (mailInfo.isValidate()) {
            authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);

        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address from = new InternetAddress(mailInfo.getFromAddress());
        // 设置邮件消息的发送者
        mailMessage.setFrom(from);
        // 创建邮件的接收者地址，并设置到邮件消息中
        //Address to = new InternetAddress(mailInfo.getToAddress());
        String toEmails[] = mailInfo.getToAddress().split(";");
        if (toEmails.length > 1) {//多人发送
            Address to[] = new Address[toEmails.length];
            for (int i = 0; i < toEmails.length; i++) {
                String sTo = toEmails[i];
                if (!RegValidators.isEmail(sTo)) {
                    continue;
                }
                to[i] = new InternetAddress(sTo);
            }
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipients(Message.RecipientType.TO, to);
        }
        if (toEmails.length == 1) {//单人发送
            Address to = new InternetAddress(toEmails[0]);
            mailMessage.setRecipient(Message.RecipientType.TO, to);
        }
        // 设置邮件消息的主题
        mailMessage.setSubject(mailInfo.getSubject());
        // 设置邮件消息发送的时间
        mailMessage.setSentDate(new Date());
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        String body = mailInfo.getContent().replaceAll("\n\r", "\r").replaceAll("\r\n", "\n").replaceAll("\r", "\n").replaceAll(
                "\n", "\r\n");
        //body=body.replaceAll("&lt;", "<").replaceAll("&gt;",">").replaceAll("&quot;", "\"");
        body = HtmlUtils.htmlUnescape(body);
        if (body.contains("<img") && body.contains("/userfiles")) {
            body = body.replaceAll("src=\"", "src=\"" + baseUrl.getBaseUrl());
        }
        // 设置HTML内容
        html.setContent(body, "text/html; charset=utf-8");
        html.setDisposition(Part.INLINE);
        mainPart.addBodyPart(html);
        // 将MiniMultipart对象设置为邮件内容
        mailMessage.setContent(mainPart);
        mailMessage.saveChanges();
        // 发送邮件
        try {

            Transport.send(mailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param SMTP
     *            邮件服务器
     * @param PORT
     *            端口
     * @param EMAIL
     *            本邮箱账号
     * @param PAW
     *            本邮箱密码
     * @param toEMAIL
     *            对方箱账号
     * @param TITLE
     *            标题
     * @param CONTENT
     *            内容
     * @param TYPE
     *            1：文本格式;2：HTML格式
     */
    public static boolean sendEmail(String SMTP, String PORT, String EMAIL,
                                    String PAW, String toEMAIL, String TITLE, String CONTENT,
                                    String TYPE) throws Exception {

        // 这个类主要是设置邮件
        MailBody mailInfo = new MailBody();

        mailInfo.setMailServerHost(SMTP);
        mailInfo.setMailServerPort(PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(EMAIL);
        mailInfo.setPassword(PAW);
        mailInfo.setFromAddress(EMAIL);
        mailInfo.setToAddress(toEMAIL);
        mailInfo.setSubject(TITLE);
        mailInfo.setContent(CONTENT);
        System.out.println(CONTENT + "content");
        // 这个类主要来发送邮件

        MailSendUtils sms = new MailSendUtils();
        try {
            if ("1".equals(TYPE)) {
                return sms.sendTextMail(mailInfo);
            } else {
                return sms.sendHtmlMail(mailInfo);
            }
        } catch (Exception e) {
            return false;
        }

    }

    public static void main(String[] args) {
        //String content="xxx这是女的xxxx<br/><a href='http://b2b.haier.com/upload/f0a872bf-b883-4a0c-a1bd-86f3c3a71615/b07c9382-4d44-4bdf-99cf-da79490cc9c4.jpg'>haha</a>";
        String content = "&lt;p&gt;&lt;a href=&quot;http://b2b.haier.com&quot; target=&quot;_blank&quot;&gt;b2b.haier.com&lt;/a&gt;&lt;/p&gt;";
        content = content.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"");
        System.out.println(content);
	 
	/*	try {
		boolean b=sendEmail("smtp.qiye.aliyun.com","465","postmaster@javadog.net","Hdxjhkhjc2012",
					"793929984@qq.com","测试",content,"2");
		System.out.println(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/


    }


}   
