/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsWorkDayCheck.java
 * @Create By 王彬彬
 * @Create In 2016年5月28日 下午10:03:36
 */
package com.creditharmony.loan.common.utils;

import java.util.Calendar;

/**
 * 各种日期类型计算
 * @Class Name WorkDayCheck
 * @author 王彬彬
 * @Create In 2016年5月28日
 */
public class WorkDayCheck {

	/**
	 * 获取当前天的下几个工作日是几号
	 * 2016年5月28日
	 * By 王彬彬
	 * @param cal
	 * @param checkDays 工作日计算的天数
	 * @return
	 */
	public static int getConfirmStartTime(Calendar cal,int checkDays) {
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.FRIDAY) {
			cal.add(Calendar.DAY_OF_MONTH, 2);
		}
		if (dayOfWeek == Calendar.SATURDAY) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		if (dayOfWeek == Calendar.SUNDAY) {
			cal.add(Calendar.DAY_OF_MONTH, 0);
		}
		return cal.get(Calendar.DAY_OF_MONTH) + 1;
	}
}
