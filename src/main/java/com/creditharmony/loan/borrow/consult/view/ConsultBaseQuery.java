package com.creditharmony.loan.borrow.consult.view;

import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.loan.borrow.consult.groups.ConsultRuleGroup;
import com.creditharmony.loan.common.validate.annotation.Identity;

/**
 * 咨询查询条件封装
 * 
 * @author 任志远
 * @date 2017年4月10日
 */
public class ConsultBaseQuery {

	/**
	 * 客户身份证号码
	 */
	@Identity(certificateType = CertificateType.SFZ, groups = {ConsultRuleGroup.class}, message="身份证号码错误")
	private String certNum;

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	@Override
	public String toString() {
		return "ConsultBaseQuery [certNum=" + certNum + "]";
	}
}
