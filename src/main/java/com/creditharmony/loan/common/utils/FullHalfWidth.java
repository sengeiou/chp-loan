package com.creditharmony.loan.common.utils;

/**
 * 全角半角互换
 * @Class Name FullHalfWidth
 * @author 申诗阔
 * @Create In 2016年5月25日
 */
public class FullHalfWidth {

	// 半角和全角的关系-->除空格外的字符偏移量是65248
	private static final int SUB = 65248;

	// 这个类不能实例化
	private FullHalfWidth() {

	}

	/**
	 * 全角转半角
	 * 2016年5月25日
	 * By 申诗阔
	 * @param fullWidthStr 全角字符串
	 * @return 转换后的半角字符串
	 */
	public static String full2Half(String fullWidthStr) {
		if (null == fullWidthStr || fullWidthStr.length() <= 0) {
			return "";
		}
		char[] full = fullWidthStr.toCharArray();
		for (int i = 0; i < full.length; ++i) {
			int charIntValue = (int) full[i];
			// 半角字符是从33开始到126结束，与半角字符对应的全角字符是从65281开始到65374结束
			// 半角的空格是32，对应的全角空格是12288
			if (charIntValue >= 65281 && charIntValue <= 65374) {
				full[i] = (char) (charIntValue - SUB);
			} else if (charIntValue == 12288) {
				full[i] = (char) 32;
			}
		}
		return new String(full);
	}

	/**
	 * 半角转全角
	 * 2016年5月25日
	 * By 申诗阔
	 * @param halfWidthStr 半角字符串
	 * @return 转换后的全角字符串
	 */
	public static String half2Full(String halfWidthStr) {
		if (null == halfWidthStr || halfWidthStr.length() <= 0) {
			return "";
		}
		char[] half = halfWidthStr.toCharArray();
		for (int i = 0; i < half.length; i++) {
			if (half[i] == ' ') {
				half[i] = '\u3000';
			} else if (half[i] < '\177') {
				half[i] = (char) (half[i] + SUB);
			}
		}
		return new String(half);
	}
	
	/**
	 * 把传入字符串中的半角  小括号  转成  全角  小括号
	 * 2016年6月16日
	 * By 申诗阔
	 * @param halfWidthStr 半角字符串
	 * @return 把半角小括号 转换成 全角小括号 后的 全角字符串
	 */
	public static String half2FullBrackets(String halfWidthStr) {
		if (null == halfWidthStr || halfWidthStr.length() <= 0) {
			return "";
		}
		return halfWidthStr.replaceAll("\\(", "\\（").replaceAll("\\)", "\\）");
	}

}