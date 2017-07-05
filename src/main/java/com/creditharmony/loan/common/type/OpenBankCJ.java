package com.creditharmony.loan.common.type;

/***
 * 开户行
 * @Class Name
 * @author 赵红江
 * @Create In 2015年12月16日
 */
public enum OpenBankCJ {
	ZGGSYH("102", "中国工商银行", "中国工商银行总行清算中心"),
	ZGNYTH("103", "中国农业银行", "中国农业银行资金清算中心"),
	ZGYH("104", "中国银行", "中国银行总行"),
	ZGJSYH("105", "中国建设银行", "中国建设银行总行(不受理个人业务)"),
	ZXYH("302", "中信银行", "中信银行总行管理部（不受理储蓄业务）"),
	GDYH("303", "中国光大银行", "中国光大银行"),
	HXYH("304", "华夏银行", "华夏银行股份有限公司总行"),
	ZGMSYH("305", "中国民生银行", "中国民生银行总行"),
	GFYH("306", "广发银行", "广发银行"),
	PAYH("307", "平安银行", "平安银行"),
	ZSYH("308", "招商银行", "招商银行"),
	XYYH("309", "兴业银行", "兴业银行总行"),
	SHPDFZYH("310", "上海浦东发展银行", "上海浦东发展银行"),
	ZGYZCXYH("403", "中国邮政储蓄银行", "中国邮政储蓄银行总行"),
	JTYH("666", "交通银行", "交通银行");
	
	/** 银行Code. */
	private String bankKey;
	/** 银行名称(标准). */
	private String bankNameDef;
	/** 银行名称(卡联). */
	private String bankNameKL;
	
	private OpenBankCJ (String bankKey, String bankNameDef, String bankNameKL) {
		this.bankKey = bankKey;
		this.bankNameDef = bankNameDef;
		this.bankNameKL = bankNameKL;
	}
	
	/**
	 * 通过银行Code取得对应的[银行名称(标准)].
	 * 2016年8月12日
	 * By 武文涛
	 * @param value
	 * @return
	 */
	public static String getOpenBank(String value) {
		String bankName = "";

		for (OpenBankCJ bank : OpenBankCJ.values()) {
			if (value.equals(bank.getBankKey())) {
				bankName = bank.getBankNameDef();
			}
		}
		return bankName;
	}

	/**
	 * 通过银行Code取得对应的[银行名称(卡联)].
	 * 2016年8月12日
	 * By 武文涛
	 * @param value
	 * @return
	 */
	public static String getOpenBankByKL(String value) {
		String bankName = "";

		for (OpenBankCJ bank : OpenBankCJ.values()) {
			if (value.equals(bank.getBankKey())) {
				bankName = bank.getBankNameKL();
			}
		}
		return bankName;
	}
	
	/**
	 * 银行名称(标准) 取得处理.
	 * @return 银行名称(标准)
	 */
	public String getBankNameDef() {
		return bankNameDef;
	}

	/**
	 * 银行名称(卡联) 取得处理.
	 * @return 银行名称(卡联)
	 */
	public String getBankNameKL() {
		return bankNameKL;
	}

	/**
	 * 银行Code 取得处理.
	 * @return 银行Code
	 */
	public String getBankKey() {
		return bankKey;
	}

	/**
	 * @param bankKey the String bankKey to set
	 */
	public void setBankKey(String bankKey) {
		this.bankKey = bankKey;
	}
}