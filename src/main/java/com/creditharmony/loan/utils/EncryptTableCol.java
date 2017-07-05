package com.creditharmony.loan.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 任志远
 * @date 2016年12月9日
 */

public enum EncryptTableCol {
	
	/**
	 * 主借人手机号1
	 */
	LOAN_CUSTOMER_MOBILE_1("LoanCustomer","customerPhoneFirst","T_JK_LOAN_CUSTOMER", "customer_phone_first", EncryptColType.PHONE),
	/**
	 * 主借人手机号2
	 */
	LOAN_CUSTOMER_MOBILE_2("LoanCustomer","customerPhoneSecond","T_JK_LOAN_CUSTOMER", "customer_phone_second", EncryptColType.PHONE),
	/**
	 * 配偶手机号
	 */
	LOAN_MATE_MOBILE("LoanMate","mateTel","T_JK_LOAN_MATE", "mate_tel", EncryptColType.PHONE),
	/**
	 * 联系人手机号
	 */
	CONTACT_MOBILE("Contact","contactMobile","T_JK_CONTACT", "contact_mobile", EncryptColType.PHONE),
	/**
	 * 自然人保证人手机号1
	 */
	LOAN_COBORROWER_MOBILE_1("LoanCoborrower","coboMobile","T_JK_LOAN_COBORROWER", "cobo_mobile", EncryptColType.PHONE),
	/**
	 * 自然人保证人手机号2
	 */
	LOAN_COBORROWER_MOBILE_2("LoanCoborrower","coboMobile2","T_JK_LOAN_COBORROWER", "cobo_mobile2", EncryptColType.PHONE),
	/**
	 * 法定保证人手机号
	 */
	COMP_MANAGE_MOBILE("LoanCompManage","corporateRepresentMobile","t_jk_comp_manage", "corporate_represent_mobile", EncryptColType.PHONE),
	/**
	 * 变更记录表变更前
	 */
	CHANGE_INFO_BEGIN("ChangerInfo","changeBegin","t_jk_changer_info", "change_begin", EncryptColType.PHONE),
	/**
	 * 变更记录表变更后
	 */
	CHANGE_INFO_AFTER("ChangerInfo","changeAfter","t_jk_changer_info", "change_after", EncryptColType.PHONE),
	/**
	 * 主借人手机号
	 */
	LOAN_CUSTOMER_MOBILE_3("ContractBusiView","mobilePhone","T_JK_LOAN_CUSTOMER", "customer_phone_first", EncryptColType.PHONE),
	/**
	 * 信借数据列表手机号
	 */
	TRANSATE_EX_MOBILE("TransateEx","customerPhoneFirstTransate","T_JK_LOAN_CUSTOMER", "customer_phone_first", EncryptColType.PHONE);
	
	EncryptTableCol(String className, String fieldName, String table, String col, EncryptColType encryptColType){
		this.className = className;
		this.fieldName = fieldName;
		this.table = table;
		this.col = col;
		this.encryptColType = encryptColType;
	}
	
	private static Map<String, EncryptTableCol> fieldMap = new HashMap<String, EncryptTableCol>();
	
	static {
		for (EncryptTableCol encryptTableCol : EncryptTableCol.values()) {
			fieldMap.put(encryptTableCol.getFieldName(), encryptTableCol);
		}
	}

	/**
	 * 类名
	 */
	private String className;
	/**
	 * 字段名
	 */
	private String fieldName;
	/**
	 * 表名
	 */
	private String table;
	/**
	 * 列名
	 */
	private String col;
	/**
	 * 加密字段类型（手机号，身份证....）
	 */
	private EncryptColType encryptColType;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public static Map<String, EncryptTableCol> getFieldMap() {
		return fieldMap;
	}

	public static void setFieldMap(Map<String, EncryptTableCol> fieldMap) {
		EncryptTableCol.fieldMap = fieldMap;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public EncryptColType getEncryptColType() {
		return encryptColType;
	}

	public void setEncryptColType(EncryptColType encryptColType) {
		this.encryptColType = encryptColType;
	}

}
