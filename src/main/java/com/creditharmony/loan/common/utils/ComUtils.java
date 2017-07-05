/**
 *
 */
package com.creditharmony.loan.common.utils;

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
   public static String getCommonLoanPdfURL(String urlId,String fileId,String view,String columnName,String columnValue, String cv) {
	   String baseUrl = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, urlId);
	   String rptFileName = getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, fileId);
   	   baseUrl = baseUrl.replace("{0}", rptFileName);
   	   
   	   baseUrl = baseUrl.replace("{1}", view);
   	   String encolumnValue = "";
	   try {
		encolumnValue = URLEncoder.encode(columnValue, Constants.UTF8);
	   } catch (UnsupportedEncodingException e) {
		   return "";
	   }
   	   baseUrl = baseUrl.replace("{2}", columnName+"=" + encolumnValue);
	  
   	   baseUrl = baseUrl.replace("{3}", cv);
   	   
   	   
   	   return baseUrl;
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
	
}
