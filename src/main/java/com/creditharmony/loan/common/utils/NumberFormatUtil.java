package com.creditharmony.loan.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NumberFormatUtil {

	/***
	 * 按格式生成序号，如0001,0002...9999
	 * 
	 * @param idx
	 *            序号号码
	 * @param scale
	 *            位数
	 * @return 按位数格式化的序号，如0019
	 */
	public static String buildSerial(int num, int scale) {
		// 格式化的位数小于等于0则抛出参数异常
		if (scale <= 0)
			throw new IllegalArgumentException("scale：" + scale);
		// 计算序列号的位数
		int count = 0;
		int aIdx = num;
		while ((aIdx = aIdx / 10) > 0)
			count++;
		count++;
		// 序列号的位数大于格式化位数，或者序列号的值小于等于0时，抛出参数异常
		if (count > scale || num <= 0)
			throw new IllegalArgumentException("idx：" + num);
		// 在序列前空出的位上添加0
		StringBuffer buf = new StringBuffer(scale);
		for (int i = scale - count; --i >= 0;)
			buf.append(0);
		// 添加序列号值
		buf.append(num);
		return buf.toString();
	}

	/**
	 * 四舍五入保留两位小数并三位分割（如：12,123.12）
	 * 
	 * @param num
	 * @return
	 */
	public static String format(String num) {
		if (num == null || "".equals(num) || num.trim().equals("")) {
			return "";
		}

		return format(new BigDecimal(num));
	}

	public static String format(BigDecimal num) {
		// 四舍五入保留两位小数
		BigDecimal bigDecimalTemp = num.setScale(2, BigDecimal.ROUND_HALF_UP);

		double numTemp = bigDecimalTemp.doubleValue();
		// 设置format格式
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

		return decimalFormat.format(numTemp);

	}

	/**
	 * 导出时进行公共的非空验证并拼接字符串
	 * 
	 * @param sb
	 * @param obj
	 */
	public static void notNullAppend(StringBuffer sb, Object obj) {
		if (obj == null) {
			sb.append(",");
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat decimalF = new DecimalFormat("0.00");
		if (obj instanceof BigDecimal) {
			sb.append(decimalF.format(obj)).append(",");
			return;
		} else if (obj instanceof Date) {
			sb.append(df.format(obj)).append(",");
			return;
		} else {
			sb.append(obj).append(",");
			return;
		}
	}
}
