package com.creditharmony.loan.borrow.serve.until;

import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.collections.MapUtils;

import com.creditharmony.dm.service.DmService;

/** 
 * 邮箱配置
 * @author Admin
 *
 */
public class MailUtil {
	//服务器地址
	public final static String HOST = "mail.creditharmony.cn";
	//发件人用户名
	public final static String USERNAME = "c.s.c14";
	//发件人密码
	public final static String PASSWORD = "qwer-7890";
	//设置邮件发送源邮箱
	public final static String FROM = "c.s.c14@creditharmony.cn";
	//主题
	public final static String SUBJECT = "的借款协议";
	

    String to = "";// 收件人  
    String from = "";// 发件人  
    String host = "";// smtp主机  
    String username = "";  
    String password = "";  
    String filename = "";// 附件文件名  
    String subject = "";// 邮件主题  
    String content = "";// 邮件正文  
    Vector file = new Vector();// 附件文件集合  
  
    /** 
     * <br> 
     * 方法说明：默认构造器 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public MailUtil() {  
    }  
  
    /** 
     * <br> 
     * 方法说明：构造器，提供直接的参数传入 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public MailUtil(String to, String from, String smtpServer,  
            String username, String password, String subject, String content) {  
        this.to = to;  
        this.from = from;  
        this.host = smtpServer;  
        this.username = username;  
        this.password = password;  
        this.subject = subject;  
        this.content = content;  
    }  
  
    /** 
     * <br> 
     * 方法说明：设置邮件服务器地址 <br> 
     * 输入参数：String host 邮件服务器地址名称 <br> 
     * 返回类型： 
     */  
    public void setHost(String host) {  
        this.host = host;  
    }  
  
    /** 
     * <br> 
     * 方法说明：设置登录服务器校验密码 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public void setPassWord(String pwd) {  
        this.password = pwd;  
    }  
  
    /** 
     * <br> 
     * 方法说明：设置登录服务器校验用户 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public void setUserName(String usn) {  
        this.username = usn;  
    }  
  
    /** 
     * <br> 
     * 方法说明：设置邮件发送目的邮箱 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public void setTo(String to) {  
        this.to = to;  
    }  
  
    /** 
     * <br> 
     * 方法说明：设置邮件发送源邮箱 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public void setFrom(String from) {  
        this.from = from;  
    }  
  
    /** 
     * <br> 
     * 方法说明：设置邮件主题 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public void setSubject(String subject) {  
        this.subject = subject;  
    }  
  
    /** 
     * <br> 
     * 方法说明：设置邮件内容 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public void setContent(String content) {  
        this.content = content;  
    }  
  
    /** 
     * <br> 
     * 方法说明：把主题转换为中文 <br> 
     * 输入参数：String strText <br> 
     * 返回类型： 
     */  
    public String transferChinese(String strText) {  
        try {  
//            strText = MimeUtility.encodeText(new String(strText.getBytes(),  
//                    "gb2312"), "gb2312", "B");  
            strText=MimeUtility.encodeText(strText,"gb2312",null);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return strText;  
    }  
  
    /** 
     * <br> 
     * 方法说明：往附件组合中添加附件 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public void attachfile(String fname) {  
        file.addElement(fname);  
    }  
  
    /** 
     * <br> 
     * 方法说明：发送邮件 <br> 
     * 输入参数： <br> 
     * 返回类型：boolean 成功为true，反之为false 
     */  
    public boolean sendMail() {  
        // 构造mail session  
        Properties props = new Properties() ;  
        props.put("mail.smtp.host", host);  
        props.put("mail.smtp.auth", "true");  
        Session session = Session.getDefaultInstance(props,  
                new Authenticator() {  
                    public PasswordAuthentication getPasswordAuthentication() {  
                        return new PasswordAuthentication(username, password);  
                    }  
                });  
        try {  
            // 构造MimeMessage 并设定基本的值  
            MimeMessage msg = new MimeMessage(session);  
            //MimeMessage msg = new MimeMessage();  
            msg.setFrom(new InternetAddress(from));  
           
              
            //msg.addRecipients(Message.RecipientType.TO, address); //这个只能是给一个人发送email  
            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(to)) ;  
            subject = transferChinese(subject);  
            msg.setSubject(subject);  
//            msg.setSubject(subject, "text/html;charset=gb2312");  
  
            // 构造Multipart  
            Multipart mp = new MimeMultipart();  
  
            // 向Multipart添加正文  
            MimeBodyPart mbpContent = new MimeBodyPart();  
            mbpContent.setContent(content, "text/html;charset=gb2312");  
              
            // 向MimeMessage添加（Multipart代表正文）  
            mp.addBodyPart(mbpContent);  
  
            // 向Multipart添加附件  
            Enumeration efile = file.elements();  
            while (efile.hasMoreElements()) {  
  
                MimeBodyPart mbpFile = new MimeBodyPart();  
                filename = efile.nextElement().toString();  
                FileDataSource fds = new FileDataSource(filename);  
                mbpFile.setDataHandler(new DataHandler(fds));  
//                <span style="color: #ff0000;">//这个方法可以解决附件乱码问题。</span>    
                String filename= new String(fds.getName().getBytes(),"ISO-8859-1");  
  
                mbpFile.setFileName(filename);  
                // 向MimeMessage添加（Multipart代表附件）  
                mp.addBodyPart(mbpFile);  
  
            }  
  
            file.removeAllElements();  
            // 向Multipart添加MimeMessage  
            msg.setContent(mp);  
            msg.setSentDate(new Date());  
            msg.saveChanges() ;  
            // 发送邮件  
              
            Transport transport = session.getTransport("smtp");  
            transport.connect(host, username, password);  
            transport.sendMessage(msg, msg.getAllRecipients());  
            transport.close();  
        } catch (Exception mex) {  
            mex.printStackTrace();  
            return false;  
        }  
        return true;  
    }
  
 public boolean sendMail(List<String> docIds) {  
    	// 构造mail session  
    	Properties props = new Properties() ;  
    	props.put("mail.smtp.host", host);  
    	props.put("mail.smtp.auth", "true");  
    	Session session = Session.getDefaultInstance(props,  
    	 new Authenticator() {  
	    	 public PasswordAuthentication getPasswordAuthentication() {  
	    		 return new PasswordAuthentication(username, password);  
	    	 }  
    	});  
    	
    	try {  
	    	 // 构造MimeMessage 并设定基本的值  
	    	 MimeMessage msg = new MimeMessage(session);  
	    	 //MimeMessage msg = new MimeMessage();  
	    	 msg.setFrom(new InternetAddress(from));  
	    	 msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(to)) ;  
	    	 subject = transferChinese(subject);  
	    	 msg.setSubject(subject);  
	    	 // 构造Multipart  
	    	 Multipart mp = new MimeMultipart();  
	    	 // 向Multipart添加正文  
	    	 MimeBodyPart mbpContent = new MimeBodyPart();  
	    	 mbpContent.setContent(content, "text/html;charset=gb2312");  
	    	 // 向MimeMessage添加（Multipart代表正文）  
	    	 mp.addBodyPart(mbpContent);  
	    	 DmService dmService = DmService.getInstance();
	    	 Map<String, InputStream> map = dmService.downloadDocuments(docIds);
	    	 if(MapUtils.isNotEmpty(map)){
	    		 Iterator i=map.entrySet().iterator();
	    		 while(i.hasNext()){//只遍历一次,速度快
	    			 Map.Entry e=(Map.Entry)i.next();
	    			 System.out.println(e.getKey()+"="+e.getValue());
	    			 InputStream is = map.get(e.getKey());
	    	    	 MimeBodyPart messageBodyPart=new MimeBodyPart();
	    	                DataSource dataSource1 =new ByteArrayDataSource(is, "application/pdf");
	    	                DataHandler dataHandler=new DataHandler(dataSource1);
	    	                messageBodyPart.setDataHandler(dataHandler);
	    	                messageBodyPart.setFileName(MimeUtility.encodeText((String) e.getKey()));
	    	    	 mp.addBodyPart(messageBodyPart);  
	    		 }
	    	 }
	    	 // 向Multipart添加MimeMessage  
	    	 msg.setContent(mp);  
	    	 msg.setSentDate(new Date());  
	    	 msg.saveChanges() ;  
	    	 // 发送邮件  
	    	 Transport transport = session.getTransport("smtp");  
	    	 transport.connect(host, username, password);  
	    	 transport.sendMessage(msg, msg.getAllRecipients());  
	    	 transport.close();  
    	} catch (Exception mex) {  
	    	 mex.printStackTrace();  
	    	 return false;  
    	}  
    		return true;  
    }
    
    
    /** 
     * <br> 
     * 方法说明：主方法，用于测试 <br> 
     * 输入参数： <br> 
     * 返回类型： 
     */  
    public static void main(String[] args) {  
/*        MailUtil sendmail = new MailUtil();  
        sendmail.setHost("mail.creditharmony.cn");  
        sendmail.setUserName("c.s.c14");  
        sendmail.setPassWord("qwer-7890");  
        sendmail.setTo("760875301@qq.com");  
        sendmail.setFrom("c.s.c14@creditharmony.cn");  
        sendmail.setSubject("你好，这是测试！");  
        sendmail.setContent("你好这是一个带多附件的测试！");  
        sendmail.attachfile("d:\\sqlite3.zip");  
        sendmail.attachfile("d:\\sqlite31.zip");  
        System.out.println(sendmail.sendMail());  */
    }  
}
