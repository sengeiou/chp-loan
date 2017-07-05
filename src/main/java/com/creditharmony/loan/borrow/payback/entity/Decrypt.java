package com.creditharmony.loan.borrow.payback.entity;

import org.apache.poi.ss.formula.functions.T;

import com.creditharmony.core.persistence.DataEntity;
import com.creditharmony.loan.utils.EncryptTableCol;
import com.creditharmony.loan.utils.InnerBean;
import com.creditharmony.loan.utils.PhoneSecretSerivice;

@SuppressWarnings("hiding")
public class Decrypt<T> extends DataEntity<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 解密
	 * @param decrypt
	 * @return
	 */
	public String decrypt(String decrypt) {
		InnerBean innerBean = new InnerBean();
		innerBean.setMobileNums(decrypt);
		innerBean.setTableName(EncryptTableCol.LOAN_CUSTOMER_MOBILE_1.getTable());
		innerBean.setCol(EncryptTableCol.LOAN_CUSTOMER_MOBILE_1.getCol());
		String phone = PhoneSecretSerivice.disDecryptStatic(innerBean);
		if(phone == null || "null".equals(phone)) phone = decrypt;
		return phone;
	}
}
