/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsCommonDateUtils.java
 * @Create By 王彬彬
 * @Create In 2016年6月25日 上午1:07:45
 */
package com.creditharmony.loan.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期计算
 * @Class Name CommonDateUtils
 * @author 王彬彬
 * @Create In 2016年6月25日
 */
public class CommonDateUtils {
	/**
	 * 计算多少天以后日期
	 * 2016年6月25日
	 * By 王彬彬
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date daysLater(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, n);
		return calendar.getTime();
	}
	
	/**
	 * 计算多上月后日期
	 * 2016年6月25日
	 * By 王彬彬
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date monthsLater(Date date, int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, n);
		return calendar.getTime();
	}
	

	/**
	 * 计算多上月后日期
	 * 2016年6月25日
	 * By 王彬彬
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date monthsAndDayLater(Date date, int monthsCount,int dayCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthsCount);
		calendar.add(Calendar.DAY_OF_YEAR, dayCount);
		return calendar.getTime();
	}
}
