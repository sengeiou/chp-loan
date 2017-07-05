/**
 *
 */
package com.creditharmony.loan.borrow.pushdata.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.creditharmony.core.loan.type.ContractVer;


/**
 * 批处理泛用公共方法类
 */
public class ComUtils {
	

	/**
	 * 从property文件中取得特定项目的值
	 * @param path 配置文件的名称
	 * @param name 项目名称
	 * @return 项目值
	 */
   public static String getPropertyByName(String path, String name) {
       String result = "";
       try {
           result = ResourceBundle.getBundle(path).getString(name);
       } catch (Exception e) {
    	   System.out.println(e.getMessage());
           return null;
       }
       return result;
   }
   
   /**
    * 借款URL取得处理(通用)   PDF
    * @param columnName 字段名字
    * @param columnValue 字段值
    * @param fileId 文件标号
    * @param urlId  url编号
    * @return URL
    */
   public static String getCommonLoanPdfURL(String columnName,String columnValue,String fileId,String urlId, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, urlId + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, urlId);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, fileId);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String encolumnValue = "";
   	   try {
   		encolumnValue = URLEncoder.encode(columnValue, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", columnName+"=" + encolumnValue);
   	   return baseUrl;
   }
   
   /**
    * 借款URL取得处理(通用)    word
    * @param columnName 字段名字
    * @param columnValue 字段值
    * @param fileId 文件标号
    * @param urlId  url编号
    * @return URL
    */
   public static String getCommonLoanWordURL(String columnName,String columnValue,String fileId,String urlId, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, urlId + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, urlId);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, fileId);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
 	   String encolumnValue = "";
 	   try {
 		encolumnValue = URLEncoder.encode(columnValue, Constants.UTF8);
 	   } catch (UnsupportedEncodingException e) {
 		   return "";
 	   }
 	   baseUrl = baseUrl.replace("{1}", columnName+"=" + encolumnValue);
 	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 借款协议（带保证人）
    * @param loanCode
    * @param contractCode
    * @return URL
    */
   public static String getLoanWordURL1(String loanCode, String contractCode, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID1 + "C" +cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID1);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE01);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String enloanCode = "";
   	   String encontractCode = "";
   	   try {
   		   enloanCode = URLEncoder.encode(loanCode, Constants.UTF8);
   		   encontractCode = URLEncoder.encode(contractCode, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "loan_code=" + enloanCode);
   	   baseUrl = baseUrl.replace("{2}", "contract_code=" + encontractCode);
   	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 借款协议
    * @param loanCode
    * @param contractCode
    * @return URL
    */
   public static String getLoanWordURL2(String loanCode, String contractCode, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID2 + "C" + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID2);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE02);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String enloanCode = "";
   	   String encontractCode = "";
   	   try {
   		   enloanCode = URLEncoder.encode(loanCode, Constants.UTF8);
   		   encontractCode = URLEncoder.encode(contractCode, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "loan_code=" + enloanCode);
   	   baseUrl = baseUrl.replace("{2}", "contract_code=" + encontractCode);
   	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 还款管理服务说明确认
    * @param contractCode
    * @return URL
    */
   public static String getLoanWordURL3(String contractCode, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID3 + "C" + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID3);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE03);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String encontractCode = "";
   	   try {
   		   encontractCode = URLEncoder.encode(contractCode, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "contract_code=" + encontractCode);
   	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 借款人委托扣款授权书
    * @param loanCode
    * @return URL
    */
   public static String getLoanWordURL4(String loanCode, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID4  + "C" + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID4);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE04);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String enloanCode = "";
   	   try {
   		   enloanCode = URLEncoder.encode(loanCode, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "loan_code=" + enloanCode);
   	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 信用咨询及管理服务协议
    * @param loanCode
    * @param contractCode
    * @return URL
    */
   public static String getLoanWordURL5(String loanCode, String contractCode, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID5 + "C" + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID5);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE05);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String enloanCode = "";
   	   String encontractCode = "";
   	   try {
   		   enloanCode = URLEncoder.encode(loanCode, Constants.UTF8);
   		   encontractCode = URLEncoder.encode(contractCode, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "loan_code=" + enloanCode);
   	   baseUrl = baseUrl.replace("{2}", "contract_code=" + encontractCode);
   	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 信用咨询及管理服务协议（带保证人）
    * @param loanCode
    * @param contractCode
    * @return URL
    */
   public static String getLoanWordURL55(String loanCode, String contractCode, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID5 + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID5);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE055);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String enloanCode = "";
   	   String encontractCode = "";
   	   try {
   		   enloanCode = URLEncoder.encode(loanCode, Constants.UTF8);
   		   encontractCode = URLEncoder.encode(contractCode, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "loan_code=" + enloanCode);
   	   baseUrl = baseUrl.replace("{2}", "contract_code=" + encontractCode);
   	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 催收服务费收取通知书
    * @param loanCode
    * @return URL
    */
   public static String getLoanWordURL6(String loanCode, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID6 + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID6);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE06);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String enloanCode = "";
   	   try {
   		   enloanCode = URLEncoder.encode(loanCode, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "loan_code=" + enloanCode);
   	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 《借款协议》终止通知书
    * @param loanCode
    * @return URL
    */
   public static String getLoanWordURL7(String loanCode, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID7 + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID7);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE07);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String enloanCode = "";
   	   try {
   		   enloanCode = URLEncoder.encode(loanCode, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "loan_code=" + enloanCode);
   	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 隐私保护声明	
    * @param loanCode
    * @return URL
    */
   public static String getLoanWordURL8(String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID8 + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID8);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE08);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   return baseUrl;
   }
   
   /**
    * 汇金合同URL取得处理    
    * 富友-信和财富专用账户协议
    * @param loanCode
    * @return URL
    */
   public static String getLoanWordURL9(String loanCode, String cv) {
	   String baseUrl = "";
	   if (StringUtils.isNotEmpty(cv) && ContractVer.VER_ONE_SIX.getCode().equals(cv)) {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID7 + cv);
	   } else {
		   baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_WORD_URLID7);
	   }
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.LOAN_RPT_FILE09);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String enloanCode = "";
   	   try {
   		   enloanCode = URLEncoder.encode(loanCode, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "loan_code=" + enloanCode);
   	   return baseUrl;
   }
   
   /**
    * 财富债权URL取得处理 PDF
    * @param custName
    * @param contractCode
    * @return
    */
   public static String getFortunePdfURL(String matchingId) {
   	   String baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.FROTUNE_RPT_PDF_URLID);
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.RPT_FILE02);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   String enmatchingId = "";
   	   try {
   		   enmatchingId = URLEncoder.encode(matchingId, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "matching_id=" + enmatchingId);
   	   return baseUrl;
   }   
   
   /**
    * 财富债权URL取得处理  word
    * @param custName
    * @param contractCode
    * @return
    */
   public static String getFortuneWordURL(String matchingId) {
   	   String baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.FROTUNE_RPT_WORD_URLID);
   	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.RPT_FILE02);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);

   	   String enmatchingId = "";
   	   try {
   		   enmatchingId = URLEncoder.encode(matchingId, Constants.UTF8);
   	   } catch (UnsupportedEncodingException e) {
   		   return "";
   	   }
   	   baseUrl = baseUrl.replace("{1}", "matching_id=" + enmatchingId);
   	   return baseUrl;
   }   
   
   
   
   
   /**
    * 批处理消息取得处理
    * @param messageId 消息ID（参照批处理消息一览）
    * @param param 消息参数（所要替换的字符串）
    * @return 消息
    */
   public static String getMessage(String messageId, String... param) {
	   String ret = "";
	   String message = getPropertyByName(Constants.MESSAHE_PROPERTY_NAME, messageId);
	   if (StringUtils.isEmpty(message)) {
		   return ret;
	   }
	   if (param != null && param.length > 0) {
		   for (int i = 0; i < param.length; i++) {
			   ret = message.replace("{" + i + "}", param[i]);
		   }
	   }
	   return message;
   }
   
   
   /**
    * 日期变换（字符型转日期型）
    * @param date 日期
    * @param patten 格式
    * @return 结果
    */
   public static Date stringToDate(String date, String patten) {
	   DateFormat df = new SimpleDateFormat(patten);
	   Date d = null;
	   try {
		   d = df.parse(date);
	   } catch (ParseException e) {
		   e.printStackTrace();
	   }
	   return d;
   }
   /**
    * null转空
    * @param v 参数
    * @return 结果
    */
   public static String nullToBlank(String v) {
	   if (StringUtils.isEmpty(v)) {
		   return "";
	   } else {
		   return v;
	   }
   }

   /**
    * 小于10整数型补零转字符型
    * @param v 参数
    * @return 结果
    */
	public static String zeroPlus(Integer v) {
		if (v < 10) {
			return "0" + String.valueOf(v);
		} else {
			return String.valueOf(v);
		}
	}


	/**
	 * 判断List是否为空
	 * @param list
	 * @return 空为true, 反之为false
	 */
	public static boolean isEmptyList(List<? extends Object> list) {
		if (list == null || list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 判断List是否不为空
	 * @param list
	 * @return 空为false, 反之为true
	 */
	public static boolean isNotEmptyList(List<? extends Object> list) {
		return !isEmptyList(list);
	}
	

	/**
	 * 获取当前系统时间
	 * @param patten yyyy/MM/dd 或  "yyyy-MM-dd HH:mm:ss"
	 * @return String
	 */
	public static String nowTime(String patten) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(new Date().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
		return dateFormat.format(c.getTime());
	}
	
	/**
	 * 获取指定时间前N个月的时间
	 * @param s 字符串类型的时间
	 * @param patten s的格式
	 * @param n 提前N个月，输入 -n 为指定时间后N个月
	 * @return
	 */
	public static Date changeDate(String s, String patten, int n) {
		Date date = stringToDate(s,patten);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, -n);
		return c.getTime();
	}
	/**
	 * 获取指定时间后 N天的时间
	 * @param s 字符串类型的时间
	 * @param patten s的格式
	 * @param n 制定时间后的N天
	 * @return
	 */
	public static Date daysLater(String s, String patten, int n) {
		Date date = stringToDate(s, patten);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, n);
		return calendar.getTime();
	}

	/**
	 * 获取String类型的日期。
	 * @param date 时间
	 * @param patten date的格式
	 * @return
	 */
	public static String dateToString(Date date, String patten) {
		DateFormat df = new SimpleDateFormat(patten);
		return df.format(date);
	}
	/**
	 * 获取制定时间N天前或者N天后日期
	 * By zhaojunlei
	 * @param date 初始日期
	 * @param n 偏移量
	 * @return N天后日期 n 如果是负数，那么就是n天前的日期
	 */
	public static Date daysLater(Date date,int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, n);
		return calendar.getTime();
	   }
	
	/**
	 * 比较时间大小（HH:mm）
	 * @param time1 被比较时间
	 * @param time2 基准时间
	 * @return 被比较时间>基准时间为true, 反之为false
	 */
	public static boolean isAfterTime(String time1, String time2) {
		DateFormat df = new SimpleDateFormat(Constants.DATAFORMAT[4]);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(time1));
			c2.setTime(df.parse(time2));
		} catch (Exception e) {
			return false;
		}
		int result = c1.compareTo(c2);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
   /**
	* 提供精确的乘法运算。
	* 2016年1月5日
	* By liushikang
	* @param bigDecimal  被乘数
	* @param v2 乘数 
	* @return  两个参数的积 
	*/
	public static int mul(BigDecimal bigDecimal, int v2) {
	   BigDecimal b1 = bigDecimal;
	   BigDecimal b2 = new BigDecimal(v2);
	   return b1.multiply(b2).intValue();
	}  
	
	/**
	 * 除法运算
	 * 2016年1月5日
	 * By liushikang
	 * @param bigDecimal 被除数 
	 * @param bigDecimal2 除数
	 * @param scale
	 * @return 表示表示需要精确到小数点以后几位。
	 */
	public static double div(BigDecimal bigDecimal, BigDecimal bigDecimal2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException( "The scale must be a positive integer or zero");
		}
		BigDecimal b1 = bigDecimal;
		BigDecimal b2 = bigDecimal2;
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 随机产生指定长度的字符串
	 * @param length 指定长度
	 * @return 结果
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		Random strGen = new Random();
		char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[strGen.nextInt(61)];
		}
		return new String(randBuffer);
	}
	  
	/**
	 * 随机产生指定长度的数据字符串
	 * @param length 指定长度
	 * @return 结果
	 */
	public static final String randomNumStr(int length) {
		if (length < 1) {
			return null;
		}
		Random numGen = new Random();
		char[] numbers = ("0123456789").toCharArray();
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbers[numGen.nextInt(9)];
		}
		return new String(randBuffer);
	}
	
	/**
	 * 将金额从元为单位转为以分为单位
	 * @param money 金额（元）
	 * @return 结果
	 */
	public static String convertMoney(BigDecimal money) {
		String ret = "";
		if (money == null) {
			return ret;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String s = df.format(money);
		double d = Double.valueOf(s);
		ret = String.valueOf(d * 100).replace(".0", "");
		return ret;
	}
	
	/**
	 * 将金额从分为单位转为以元为单位
	 * @param money 金额（分）
	 * @return 结果
	 */
	public static BigDecimal convertMoney(String money) {
		if (StringUtils.isEmpty(money) || Boolean.FALSE == StringUtils.isNumeric(money)) {
			return null;
		}
		double d = Double.valueOf(money) / 100;
		return new BigDecimal(d);
	}
	
	/**
	 * 将List按设定大小拆分并返回
	 * @param list 被拆分list
	 * @return 结果
	 */
	public static <T> List<List<T>> getSplitedList(List<T> list, int size) {
		List<List<T>> retList = new ArrayList<List<T>>();
		if (isEmptyList(list)) {
			return retList;
		}
		int listSize = list.size();
		if (listSize <= size) {
			retList.add(list);
			return retList;
		}
		int batchSize = listSize / size;
		int remain = listSize % size;
		for (int i = 0; i < batchSize; i++) {
			int fromIndex = i * size;
			int toIndex = fromIndex + size;
			retList.add(list.subList(fromIndex, toIndex));
		}
		if (remain > 0) {
			retList.add(list.subList(listSize - remain, listSize));
		}
		return retList;
	} 
	
	/**
	 * 将List按默认大小拆分并返回
	 * @param list 被拆分list
	 * @return 结果
	 */
	public static <T> List<List<T>> getSplitedList(List<T> list) {
		List<List<T>> retList = new ArrayList<List<T>>();
		if (isEmptyList(list)) {
			return retList;
		}
		String max = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.ZJ_ADAPTER_MAX);
		if (StringUtils.isEmpty(max)) {
			return retList;
		}
		int m = Integer.parseInt(max);
		int listSize = list.size();
		if (listSize <= m) {
			retList.add(list);
			return retList;
		}
		int batchSize = listSize / m;
		int remain = listSize % m;
		for (int i = 0; i < batchSize; i++) {
			int fromIndex = i * m;
			int toIndex = fromIndex + m;
			retList.add(list.subList(fromIndex, toIndex));
		}
		if (remain > 0) {
			retList.add(list.subList(listSize - remain, listSize));
		}
		return retList;
	} 
	
	/**
	 * 获取一个bean对象的所有的属性和对应的值
	 * zhaojunlei 
	 * @param obj 
	 * @return Map<String, Object> 值是对象的属性名，值是对象的对应的属性的值
	 */
	public  static final Map<String, Object> getAllAttributesByBean(Object obj){
		Map<String, Object> map=new HashMap<String, Object>();
		if (obj!=null) {
			Field[] f=obj.getClass().getDeclaredFields();
			for (int i = 0; i < f.length; i++) {
				Field field = f[i];
				String nam=field.getName();
				String n=nam.substring(0, 1).toUpperCase();
				String name=n+nam.substring(1, nam.length());
				try {
					Method m= obj.getClass().getMethod("get"+name);
					Object o= m.invoke(obj);
					map.put(nam, o);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
		}
		return map;
		}
	
		/**
		 * 截取className最后一段，并大写首字母 得到beanName
		 * @param length className
		 * @return beanName
		 */
		public static String classNameToBeanName(String className) {
			StringBuffer sb =new  StringBuffer(className);
			StringBuffer tempsb =new  StringBuffer(sb.substring(sb.lastIndexOf(".")+1, sb.length()));
			String beanName =tempsb.toString().replaceFirst(tempsb.substring(0, 1),tempsb.substring(0, 1).toLowerCase());
			return beanName ;
			
		}
		/**
		 * 截取className最后一段
		 * @param length className
		 * @return beanName
		 */
		public static String classNameToShort(String className) {
			StringBuffer sb =new  StringBuffer(className);
			StringBuffer beanName =new  StringBuffer(sb.substring(sb.lastIndexOf(".")+1, sb.length()));
			return beanName.toString() ;
			
		}
	}
