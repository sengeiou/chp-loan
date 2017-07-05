package com.creditharmony.loan.common.utils;

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	
	/**
	 * 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public static String appendString(String ids) {
		try {
			String[] idArray = null;
			StringBuilder parameter = new StringBuilder();
			idArray = ids.split(",");
			for (int i =0;i<idArray.length;i++){
				String id  = idArray[i];
					if (i == 0){
						parameter.append("'" +id +"'");
					}else {
						parameter.append(",'" +id + "'");
					}
			}
			return parameter.toString();
		} catch (Exception e) {
			logger.error("将ID转化为字符串类型异常。",e);
			return "";
		}
	}
	
	/**
	 * 空字符串转化为空格，用于导出Excel占位
	 * @param str
	 * @return
	 */
	public static String convertStrEmptyToBlank(String str) {
		try {
			if(StringUtils.isEmpty(str)) {
				str = " ";
			} else {
				return str;
			}
		} catch (Exception e) {
			logger.error("空字符串转化为空格，用于导出Excel占位发生异常。",e);
			str = " ";
		}
		return str;
	}
}
