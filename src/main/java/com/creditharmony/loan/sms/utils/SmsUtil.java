package com.creditharmony.loan.sms.utils;

import java.util.Date;
import java.util.Random;

import com.creditharmony.adapter.bean.in.sms.SmsInfo;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.DateUtils;

/**
 * 短信发送工具类
 * 
 * @Class Name SmsUtil
 * @author 朱杰
 * @Create In 2016年3月8日
 */
public class SmsUtil {
	
	/**
	 * 生成短信发送seqId，规则："jk" + yyyyMMdd + 9位随机字符串 
	 * 2016年1月20日 
	 * By 陈广鹏
	 * @return
	 */
	public static String getSeqId() {
		String seqId = "";
		Date date = new Date();
		seqId = DateUtils.formatDate(date, "yyyyMMddHHmmssSSS");
		// seqId = "jk" + seqId + randomString(7);
		return seqId;
	}

	/**
	 * 随机产生指定长度的字符串
	 * 
	 * @param length
	 *            指定长度
	 * @return 结果
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		Random strGen = new Random();
		char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
				.toCharArray();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[strGen.nextInt(61)];
		}
		return new String(randBuffer);
	}
	
	/**
	 * 实时发送短信
	 * 2016年3月6日
	 * By 朱杰
	 * @param phone   手机号
	 * @param msg 发送内容
	 */
	public static final void sendSms(String phone,String msg){
		ClientPoxy service = new ClientPoxy(ServiceType.Type.SEND_SMS_FAST);
		SmsInfo param = new SmsInfo();
		param.setSeqId(SmsUtil.getSeqId());
		param.setPhoneNo(phone);
		param.setContent(msg);
		// 短信息发送
		service.callService(param);
	}
	
	/**
	 * 随机生成6位验证码
	 * 2016年4月18日
	 * By 王彬彬
	 * @return
	 */
	public static String getRandNum(int charCount) {
        String charValue = "";
        for (int i = 0; i < charCount; i++) {
            char c = (char) (randomInt(0, 10) + '0');
            charValue += String.valueOf(c);
        }
        return charValue;
    }

	private static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}
}
