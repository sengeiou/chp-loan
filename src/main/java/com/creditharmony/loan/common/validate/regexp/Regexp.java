package com.creditharmony.loan.common.validate.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式常量
 * @author 任志远
 * @date 2017年1月9日
 */
public class Regexp {

	/**
	 * 主借人姓名校验正则
	 */
	public final static String NAME = "^[a-zA-Z\u4e00-\u9fa5 .·]{1,30}$";
	/**
	 * 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
	 */
	public final static String ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
	/**
	 * 邮箱校验正则
	 */
	public final static String EMAIL = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
	/**
	 * 固话校验正则
	 */
	public final static String TEL = "^0\\d{2,3}-\\d{7,8}$";
	/**
	 * 匹配最后4位
	 */
	public final static String LAST_FOUR_STR = ".{4}$";
	/**
	 * 匹配手机号码
	 */
	public final static String MOBILE_PHONE_NUM = "^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$";
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(Regexp.LAST_FOUR_STR);
		Matcher matcher = pattern.matcher("1802228332");
		boolean b = matcher.matches();
		while(matcher.find()){
			System.out.println(matcher.group());
		}
//		System.out.println(b);
		System.out.println("1234A".replaceAll(LAST_FOUR_STR,"****"));
	}
	
}
