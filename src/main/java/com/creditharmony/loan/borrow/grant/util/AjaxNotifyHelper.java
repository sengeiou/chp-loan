package com.creditharmony.loan.borrow.grant.util;

import java.util.List;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.common.type.BooleanType;

/**
 * ajax调用时，返回前端页面的对象ajaxNotify的封装帮助类
 * @Class Name AjaxNotifyHelper
 * @author 张永生
 * @Create In 2016年4月22日
 */
public class AjaxNotifyHelper {

	/**
	 * 封装要返回到前台的json
	 * 2016年4月22日
	 * By 朱静越
	 * @param failedCodeList
	 * @param notify
	 * @param successNum
	 * @param failNum
	 */
	public static void setBackMes(List<String> failedCodeList, AjaxNotify notify,
			int successNum, int failNum, String mes) {
		StringBuffer message = new StringBuffer();
		message.append("成功条数：");
		message.append(successNum);
		message.append("，失败条数：");
		message.append(failNum);
		message.append("。");
		// 表示有失败的，
		if(ArrayHelper.isNotEmpty(failedCodeList)){
			message.append(mes);
			for(String failedCodeItem : failedCodeList){
				message.append(failedCodeItem);
				message.append("，");
			}
			message.append("请检查。");
		}
		notify.setSuccess(BooleanType.TRUE);
		notify.setMessage(message.toString());
		notify.setSuccessNum(successNum);
		notify.setFailNum(failNum);
	}
	
	public static void wrapperNotifyInfo(AjaxNotify ajaxNotify,
			List<String> failedCodeList, int successNum, int failNum) {
		StringBuffer message = new StringBuffer();
		message.append("成功条数：");
		message.append(successNum);
		message.append("，失败条数：");
		message.append(failNum);
		message.append("。");
		if(ArrayHelper.isNotEmpty(failedCodeList)){
			message.append("失败合同编号如下：");
			for(String failedCodeItem : failedCodeList){
				message.append(failedCodeItem);
				message.append("，");
			}
			message.append("请检查。");
		}
		ajaxNotify.setSuccess(BooleanType.TRUE);
		ajaxNotify.setMessage(message.toString());
		ajaxNotify.setSuccessNum(successNum);
		ajaxNotify.setFailNum(failNum);
	}

}
