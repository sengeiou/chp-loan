/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsListSortUtils.java
 * @Create By 王彬彬
 * @Create In 2015年12月23日 下午7:33:48
 */
package com.creditharmony.loan.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.creditharmony.loan.common.consts.SortConst;

/**
 * list排序工具类
 * 
 * @Class Name ListSortUtil
 * @author 王彬彬
 * @Create In 2015年12月23日
 * @param <T>
 *            排序对象
 */
public class ListSortUtil<T> {

	/**
	 * list排序 2015年12月23日 By 王彬彬
	 * 
	 * @param targetList
	 *            目标排序List
	 * @param sortField
	 *            排序字段(实体类属性名)
	 * @param sortMode
	 *            排序方式（asc or desc）
	 */
	@SuppressWarnings({ "unchecked" })
	public void sort(List<T> list, final String sortMode,
			final String... method) {
		Collections.sort(list, new Comparator<Object>() {
			public int compare(Object arg1, Object arg2) {
				int result = 0;
				try {
					int methodSize = 1;
					if (method.length > 0) {
						methodSize = method.length;
					}
					for (int i = 0; i < methodSize; i++) {
						// 首字母转大写
						String newStr = method[i].substring(0, 1).toUpperCase()
								+ method[i].replaceFirst("\\w", "");

						String methodStr = "get" + newStr;

						Method m1 = ((T) arg1).getClass().getMethod(methodStr,
								null);
						Method m2 = ((T) arg2).getClass().getMethod(methodStr,
								null);

						Object obj1 = m1.invoke(((T) arg1), null);
						Object obj2 = m2.invoke(((T) arg2), null);

						result = methodsCompare(obj1, obj2);

						if (result != 0) {
							break;
						}

					}

					if (sortMode.equals(SortConst.DESC)) {
						// 倒序
						result = -result;
					}

				} catch (NoSuchMethodException nsme) {
					nsme.printStackTrace();
				} catch (IllegalAccessException iae) {
					iae.printStackTrace();
				} catch (InvocationTargetException ite) {
					ite.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				return result;
			}
		});
	}

	private int methodsCompare(Object obj1, Object obj2) {
		int result = 0;

		if (obj1 instanceof String) {
			// 字符串
			result = obj1.toString().compareTo(
					obj2.toString());
			
		} else if (obj1 instanceof Date) {
			// 日期
			long l = ((Date) obj1).getTime() - ((Date) obj2).getTime();
			if (l > 0) {
				result = 1;
			} else if (l < 0) {
				result = -1;
			} else {
				result = 0;
			}
		} else if (obj1 instanceof Integer) {
			result = (Integer) obj1 - (Integer) obj2;
		} else if (obj1 instanceof BigDecimal) {
			result = ((BigDecimal) obj1).compareTo((BigDecimal) obj2);
		} else {
			result = obj1.toString().compareTo(obj2.toString());
			System.err
					.println("ListSortUtil.sort方法接受到不可识别的对象类型，转换为字符串后比较返回...");
		}

		return result;

	}
}
