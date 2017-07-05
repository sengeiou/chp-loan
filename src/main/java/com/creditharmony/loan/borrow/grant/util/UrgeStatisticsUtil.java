package com.creditharmony.loan.borrow.grant.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.UrgeRepay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.grant.constants.ResultConstants;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeStatisticsView;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 催收统计工具类
 * @Class Name UrgeStatisticsUtil
 * @author 张永生
 * @Create In 2016年4月25日
 */
public class UrgeStatisticsUtil {

	/**
	 * 包装催收统计view对象
	 * 2016年4月25日
	 * By 张永生
	 * @param urgeStatisticsList
	 * @param dictMap
	 */
	public static void wrapperUrgeStatisticsView(
			List<UrgeStatisticsView> urgeStatisticsList, Map<String, Dict> dictMap) {
		for (UrgeStatisticsView usViewItem : urgeStatisticsList) {
			BigDecimal receivableFeeUrgedService = usViewItem.getReceivableFeeUrgedService();
			BigDecimal receivedfeeUrgedService = usViewItem.getReceivedfeeUrgedService();
			if (receivableFeeUrgedService.compareTo(receivedfeeUrgedService) == -1
					|| receivableFeeUrgedService.compareTo(receivedfeeUrgedService) == 0) {
				usViewItem.setColResult(ResultConstants.SUCCESS_DESC);
			} else if (receivedfeeUrgedService.compareTo(BigDecimal.ZERO) == 0) {
				usViewItem.setColResult(ResultConstants.FAIL_DESC);
			} else if (receivedfeeUrgedService.compareTo(BigDecimal.ZERO) == 1
					&& receivedfeeUrgedService.compareTo(receivableFeeUrgedService) == -1) {
				usViewItem.setColResult(ResultConstants.PART_SUCCESS_DESC);
			}
			String repayLabel = DictUtils.getLabel(dictMap, LoanDictType.URGE_REPAY_STATUS, usViewItem.getDictPayStatus());
			if (UrgeRepay.REPAIED.getName().equals(repayLabel)) {
				usViewItem.setDictPayStatusLabel(YESNO.YES.getName());
			} else {
				usViewItem.setDictPayStatusLabel(YESNO.NO.getName());
			}
			int days = usViewItem.getMonthOverdueDaysMax();
			if (days > 29) {
				usViewItem.setBackMoneyDesc(YESNO.NO.getName());
			} else {
				usViewItem.setBackMoneyDesc(YESNO.YES.getName());
			}
			String payStatus = DictUtils.getLabel(dictMap, LoanDictType.PAY_STATUS, usViewItem.getPayStatus());
			if(LoanApplyStatus.SETTLE.getName().equals(payStatus)){
				usViewItem.setPayStatusLabel(LoanApplyStatus.SETTLE.getName());
			}else if(LoanApplyStatus.EARLY_SETTLE.getName().equals(payStatus)){
				usViewItem.setPayStatusLabel(LoanApplyStatus.EARLY_SETTLE.getName());
			}else if(LoanApplyStatus.REPAYMENT.getName().equals(payStatus)){
				usViewItem.setPayStatusLabel(LoanApplyStatus.REPAYMENT.getName());
			}else if(LoanApplyStatus.OVERDUE.getName().equals(payStatus)){
				usViewItem.setPayStatusLabel(LoanApplyStatus.OVERDUE.getName());
			}else if(LoanApplyStatus.SETTLE_CONFIRM.getName().equals(payStatus)){
				usViewItem.setPayStatusLabel(LoanApplyStatus.SETTLE_CONFIRM.getName());
			}
		}
	}
}
