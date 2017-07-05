package com.creditharmony.loan.common.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;

import com.creditharmony.common.util.DateUtils;


public class DateUtil {
	
	private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

	private static final Object object = new Object();
	
	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static Date StringToDate(String date) {
		DateStyle dateStyle = getDateStyle(date);
		return StringToDate(date, dateStyle);
	}
	
	/**
	 * 获取日期字符串的日期风格。失敗返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期风格
	 */
	public static DateStyle getDateStyle(String date) {
		DateStyle dateStyle = null;
		Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
		List<Long> timestamps = new ArrayList<Long>();
		for (DateStyle style : DateStyle.values()) {
			if (style.isShowOnly()) {
				continue;
			}
			Date dateTmp = null;
			if (date != null) {
				try {
					ParsePosition pos = new ParsePosition(0);
					dateTmp = getDateFormat(style.getValue()).parse(date, pos);
					if (pos.getIndex() != date.length()) {
						dateTmp = null;
					}
				} catch (Exception e) {
				}
			}
			if (dateTmp != null) {
				timestamps.add(dateTmp.getTime());
				map.put(dateTmp.getTime(), style);
			}
		}
		Date accurateDate = getAccurateDate(timestamps);
		if (accurateDate != null) {
			dateStyle = map.get(accurateDate.getTime());
		}
		return dateStyle;
	}
	
	/**
	 * 获取SimpleDateFormat
	 * 
	 * @param pattern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String pattern)
			throws RuntimeException {
		SimpleDateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			synchronized (object) {
				if (dateFormat == null) {
					dateFormat = new SimpleDateFormat(pattern);
					dateFormat.setLenient(false);
					threadLocal.set(dateFormat);
				}
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}
	
	/**
	 * 获取精确的日期
	 * 
	 * @param timestamps
	 *            时间long集合
	 * @return 日期
	 */
	private static Date getAccurateDate(List<Long> timestamps) {
		Date date = null;
		long timestamp = 0;
		Map<Long, long[]> map = new HashMap<Long, long[]>();
		List<Long> absoluteValues = new ArrayList<Long>();

		if (timestamps != null && timestamps.size() > 0) {
			if (timestamps.size() > 1) {
				for (int i = 0; i < timestamps.size(); i++) {
					for (int j = i + 1; j < timestamps.size(); j++) {
						long absoluteValue = Math.abs(timestamps.get(i)
								- timestamps.get(j));
						absoluteValues.add(absoluteValue);
						long[] timestampTmp = { timestamps.get(i),
								timestamps.get(j) };
						map.put(absoluteValue, timestampTmp);
					}
				}

				// 有可能有相等的情况。如12-11和12-11-01。时间戳是相等的。此时minAbsoluteValue为0
				// 因此不能将minAbsoluteValue取默认值0
				long minAbsoluteValue = -1;
				if (!absoluteValues.isEmpty()) {
					minAbsoluteValue = absoluteValues.get(0);
					for (int i = 1; i < absoluteValues.size(); i++) {
						if (minAbsoluteValue > absoluteValues.get(i)) {
							minAbsoluteValue = absoluteValues.get(i);
						}
					}
				}

				if (minAbsoluteValue != -1) {
					long[] timestampsLastTmp = map.get(minAbsoluteValue);

					long dateOne = timestampsLastTmp[0];
					long dateTwo = timestampsLastTmp[1];
					if (absoluteValues.size() > 1) {
						timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne
								: dateTwo;
					}
				}
			} else {
				timestamp = timestamps.get(0);
			}
		}

		if (timestamp != 0) {
			date = new Date(timestamp);
		}
		return date;
	}
	
	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param dateStyle
	 *            日期风格
	 * @return 日期
	 */
	public static Date StringToDate(String date, DateStyle dateStyle) {
		Date myDate = null;
		if (dateStyle != null) {
			myDate = StringToDate(date, dateStyle.getValue());
		}
		return myDate;
	}
	
	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return 日期
	 */
	public static Date StringToDate(String date, String pattern) {
		Date myDate = null;
		if (date != null) {
			try {
				myDate = getDateFormat(pattern).parse(date);
			} catch (Exception e) {
			}
		}
		return myDate;
	}
	
	/**
	 * 获取日期的天数。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 天
	 */
	public static int getDay(Date date) {
		return getInteger(date, Calendar.DATE);
	}
	
	/**
	 * 获取日期中的某数值。如获取月份
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            日期格式
	 * @return 数值
	 */
	public static int getInteger(Date date, int dateType) {
		int num = 0;
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			num = calendar.get(dateType);
		}
		return num;
	}
	
	/**
	 * 将日期转化为日期字符串。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, String pattern) {
		String dateString = null;
		if (date != null) {
			try {
				dateString = getDateFormat(pattern).format(date);
			} catch (Exception e) {
			}
		}
		return dateString;
	}

	/**
	 * 获取日期的月份。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		return getInteger(date, Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取两个日期相差年份
	 * 2016年4月8日
	 * By 申诗阔
	 * @param d1  第一个日期参数
	 * @param d2  第二个日期参数
	 * @return
	 */
	public static int diffYear(Date d1, Date d2) {
		int diff = Integer.parseInt(DateUtils.getYear(d1)) - Integer.parseInt(DateUtils.getYear(d2));
		return diff;
	}
	
	/**
	 * 指定日期d1+num之后与当前时间的差值（天和小时）
	 * @param d1
	 * @param num
	 * @return
	 */
	public static String getDayAndHours(Date d1, int num){
		if(d1==null){
			return null;
		}
		String retStr = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d1);
		calendar.add(Calendar.DAY_OF_YEAR,num);
		Date date = calendar.getTime();
		Date datetemp = new Date();
		long temp = date.getTime()-datetemp.getTime();
		if(temp >0){
			long hours = temp / 1000 / 3600;
			long daynum = hours / 24;
			long hoursnum = hours % 24;
			retStr = daynum+"天"+hoursnum +"小时";
		}
		return retStr;
	}
	
	/**
     * 获得当前时间所在月份的起始时间
     * By 任志远 2016年11月22日
     *
     * @param date		Tue Nov 22 00:00:00 CST 2016
     * @return			Tue Nov 01 00:00:00 CST 2016
     */
    public static Date getMonthBegin(Date date) {
        
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
    
    /**
     * 获得当前时间所在月份的最后一天
     * By 任志远 2016年11月22日
     *
     * @param date		Tue Nov 22 00:00:00 CST 2016
     * @return			Tue Nov 30 00:00:00 CST 2016
     */
    public static Date getMonthLastDay(Date date) {
        
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
    
    /**
     * 获得当前时间所在天的起始时间  eg:Tue Nov 01 00:00:00 CST 2016
     * By 任志远 2016年11月22日
     *
     * @param date	Tue Nov 22 16:18:00 CST 2016
     * @return		Tue Nov 22 00:00:00 CST 2016
     */
    public static Date getDayBegin(Date date) {
        
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
	
    /**
     * 两个日期之间相差的月份
     * @author 于飞
     * @Create 2017年4月11日
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpaceByDate(Date date1, Date date2){     
        int iMonth = 0;     
        int flag = 0;     
        try{     
            Calendar objCalendarDate1 = Calendar.getInstance();     
            objCalendarDate1.setTime(date1);     
     
            Calendar objCalendarDate2 = Calendar.getInstance();     
            objCalendarDate2.setTime(date2);     
     
            if (objCalendarDate2.equals(objCalendarDate1))     
                return 0;     
            if (objCalendarDate1.after(objCalendarDate2)){     
                Calendar temp = objCalendarDate1;     
                objCalendarDate1 = objCalendarDate2;     
                objCalendarDate2 = temp;     
            }     
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH))     
                flag = 1;     
     
            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR))     
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR))     
                        * 12 + objCalendarDate2.get(Calendar.MONTH) - flag)     
                        - objCalendarDate1.get(Calendar.MONTH);     
            else    
                iMonth = objCalendarDate2.get(Calendar.MONTH)     
                        - objCalendarDate1.get(Calendar.MONTH) - flag;     
     
        } catch (Exception e){     
         e.printStackTrace();     
        }     
        return iMonth;     
    }  
	
	public static void main(String arg[]){
		//getDayAndHours(new Date("2016/05/08 17:25:08"),7);
		
		try {
			SimpleDateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
			System.out.println(getMonthSpaceByDate(df.parse("2016-04-15"),new Date()));
			//System.out.println(getMonthSpace("2015-04-15","2017-04-12"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}