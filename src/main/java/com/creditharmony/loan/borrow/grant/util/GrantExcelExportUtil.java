package com.creditharmony.loan.borrow.grant.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel2;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.GrantExcel4;

/**
 * 放款导出excel工具类
 * 用途：在放款导出excel时使用
 * @Class Name GrantExcelExportUtil
 * @author 张永生
 * @Create In 2016年4月20日
 */
public class GrantExcelExportUtil {

	/**
	 * 组装List<GrantExcel4>数据
	 * 2016年4月20日
	 * By 张永生
	 * @param dataList
	 * @return
	 */
	public static List<GrantExcel4> assembleExcelDataList(List<GrantExcel2> dataList) {
		List<GrantExcel4> excel4List = new ArrayList<GrantExcel4>();
		GrantExcel4 excel4 = null;
		for (int i = 0; i < dataList.size(); i++) {
			GrantExcel2 excel2 = dataList.get(i);
			excel4 = new GrantExcel4();
			excel4.setIndex((i + 1) + "");
			excel4.setLoginName(excel2.getPayLoginName());
			excel4.setChianName(excel2.getPayChinaName());
			String grantMoney = excel2.getGrantMoney();			  // 放款金额
			String urgedServiceFee = excel2.getUrgedServiceFee(); // 催收服务费
			BigDecimal grantMoneyBiDecimal = (null != grantMoney
					&& !"".equals(grantMoney) ? new BigDecimal(grantMoney)
					: new BigDecimal("0"));
			BigDecimal urgedServiceFeeBiDecimal = (null != urgedServiceFee
					&& !"".equals(urgedServiceFee) ? new BigDecimal(
					urgedServiceFee) : new BigDecimal("0"));
			excel4.setMoney(grantMoneyBiDecimal.subtract(
					urgedServiceFeeBiDecimal).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			excel4.setMark(excel2.getContractCode() + "_委托提现_" + excel2.getId());
			excel4List.add(excel4);
		}
		return excel4List;
	}
}
