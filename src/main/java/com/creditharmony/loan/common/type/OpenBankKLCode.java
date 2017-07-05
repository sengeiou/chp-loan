package com.creditharmony.loan.common.type;

/***
 * 开户行
 * @Class Name
 * @author 赵红江
 * @Create In 2015年12月16日
 */
public enum OpenBankKLCode {
	ZGGSYH("102", "中国工商银行", "102100099996"),
	ZGNYTH("103", "中国农业银行", "103100000026"),
	ZGYH("104", "中国银行", "104100000004"),
	ZGJSYH("105", "中国建设银行", "105100000017"),
	ZXYH("302", "中信银行", "302100011000"),
	GDYH("303", "中国光大银行", "303100000006"),
	HXYH("304", "华夏银行", "304100040000"),
	ZGMSYH("305", "中国民生银行", "305100000013"),
	GFYH("306", "广东发展银行", "306581000003"),
	PAYH("307", "平安银行股份有限公司", "307584007998"),
	ZSYH("308", "招商银行", "308584000013"),
	XYYH("309", "兴业银行", "309391000011"),
	SHPDFZYH("310", "上海浦东发展银行", "310290000013"),
	ZGYZCXYH("403", "中国邮政储蓄银行有限责任公司", "403100000004"),
	JTYH("666", "交通银行", "301290000007");
	
	/** 银行Code. */
	private String bankKey;
	/** 银行名称(标准). */
	private String bankNameDef;
	/** 银行名称(卡联). */
	private String bankNameKL;
	
	private OpenBankKLCode (String bankKey, String bankNameDef, String bankNameKL) {
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

		for (OpenBankKLCode bank : OpenBankKLCode.values()) {
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

		for (OpenBankKLCode bank : OpenBankKLCode.values()) {
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