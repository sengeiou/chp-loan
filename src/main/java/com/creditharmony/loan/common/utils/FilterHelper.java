package com.creditharmony.loan.common.utils;
public class FilterHelper {
	 /* 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public static String appendIdFilter(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (int i = 0 ; i < idArray.length; i++){
			if (i != 0) {
				parameter.append(",'" + idArray[i] + "'");
			}
			else
				parameter.append("'" + idArray[i] + "'");
			
		}
		return parameter.toString();
	}

}
