package com.creditharmony.loan.borrow.transate.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.bean.in.mail.MailInfo;
import com.creditharmony.adapter.bean.out.mail.MailOutInfo;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.adapter.core.utils.DesUtils;
import com.creditharmony.common.util.Global;

@Service
public class LoanSendEmail {

	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 发送邮件
	·* 2016年11月30日
	·* by Huowenlong
	 * @param email
	 * @param url
	 * @return
	 */
	public String sendEmailMothed(String email,String url,String name){
		String returnStr = "";
		String startImg = "</br></br><img src='http://adapter.prod.creditharmony.cn/chp-adapter-web/static/images/hjlogo.jpg'></br></br>"; 
		String endImg = "<br></br></br><img src='http://adapter.prod.creditharmony.cn/chp-adapter-web/static/images/ranmeizhiji.jpg'>";
		ClientPoxy service = new ClientPoxy(ServiceType.Type.SEND_MAIL); 
		MailInfo mailParam = new MailInfo(); 
		String[] toAddrArray = {email}; 
		mailParam.setSubject("信和汇金预留邮箱更改验证");
		mailParam.setToAddrArray(toAddrArray); 
		mailParam.setContent(startImg + "</br></br>尊敬的"+name+"客户您好：</br>"+
		"请您点击下方链接进行邮箱验证，此链接仅用于信和汇金预留邮箱更改验证，请勿回复或转发。</br>"+
		//url+ "\n"+
		"<a href = \""+  url + "\">"+url+"</a></br>" +
		"如有疑问请详询400-090-1199。祝您生活愉快！</br></br>" + endImg);		
		try {
			MailOutInfo out = (MailOutInfo) service.callService(mailParam);
			if("0000".equals(out.getRetCode())){
				returnStr = "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送邮件失败  "+ e.getMessage());
		}
		return returnStr;
	}
	
	/**
	 * 邮箱确认的链接
	·* 2016年11月30日
	·* by Huowenlong
	 * @param emailId
	 * @return   

	 */
	public String makeEmailParam(String emailId){
		String contentj = "";
		String url = Global.getConfig("loan.email.confirm");
		String secretKey = Global.getConfig("loan.email.key"); 
		String secretparmKey = Global.getConfig("loan.email.paramKey");
		try {
			//String key =  DesUtils.encrypt(secretKey , secretKey);
			//String paramKey = DesUtils.encrypt(emailId , secretparmKey);
			String content="{'key':'"+secretKey+"', 'type':'"+"1"+"' , 'paramKey':'"+emailId+"','businessType':'1','sendEmailTime':'"+new Date().getTime()+"'} ";
			contentj = url + DesUtils.encrypt(content,secretparmKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contentj;
	}
}
