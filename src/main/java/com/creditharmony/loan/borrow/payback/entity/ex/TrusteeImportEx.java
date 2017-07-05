package com.creditharmony.loan.borrow.payback.entity.ex;

import org.apache.commons.lang3.StringUtils;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 委托充值导入
 * @Class Name TrustImportEx
 * @author 王浩
 * @Create In 2016年3月10日
 */
@SuppressWarnings("serial")
public class TrusteeImportEx extends DataEntity<TrusteeImportEx>{
	// 合同编号
	private String contractCode;
	// 还款申请id
	private String paybackApplyId;
	// 开户行
	@ExcelField(title = "序号", type = 0, align = 2, sort = 10)
	private String seq;
	@ExcelField(title = "委托充值目标登录名", type = 0, align = 2, sort = 20)
	private String customerPhoneFirst;
	// 中文名称
	@ExcelField(title = "委托充值目标中文名称", type = 0, align = 2, sort = 30)
	private String customerName;
	// 金额(单位:元)
	@ExcelField(title = "委托充值金额", type = 0, align = 2, sort = 40)
	private String trustAmount;
	// 备注
	@ExcelField(title = "备注信息", type = 0, align = 2, sort = 60)
	private String remarks;		
	// 返回码
	@ExcelField(title = "返回码", type = 0, align = 2, sort = 70)
	private String returnCode;
	// 返回描述
	@ExcelField(title = "返回描述", type = 0, align = 2, sort = 80)
	private String returnMsg;
	
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCustomerPhoneFirst() {
		return customerPhoneFirst;
	}

	public void setCustomerPhoneFirst(String customerPhoneFirst) {
		this.customerPhoneFirst = customerPhoneFirst;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTrustAmount() {
		return StringUtils.isEmpty(trustAmount) ? "0" : trustAmount.trim();
	}

	public void setTrustAmount(String trustAmount) {
		this.trustAmount = trustAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPaybackApplyId() {
		return paybackApplyId;
	}

	public void setPaybackApplyId(String paybackApplyId) {
		this.paybackApplyId = paybackApplyId;
	}
		
}
