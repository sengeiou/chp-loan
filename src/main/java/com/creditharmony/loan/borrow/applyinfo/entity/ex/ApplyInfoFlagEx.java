package com.creditharmony.loan.borrow.applyinfo.entity.ex;


import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
/**
 * 客户资料录入页面切换标签页标记
 * @Class Name CustomerBaseInfo
 * @author 张平
 * @Create In 2015年12月3日
 */
public class ApplyInfoFlagEx extends CustomerBaseInfo {

	private static final long serialVersionUID = -8533950717781966154L;
	// 标识
	private String flag;
	// 信息
	private String message;	
	// 证件起始有效期
	private String idStartDayStr;
	// 证件终止有效期
	private String idEndDayStr;
	// 是否结婚
	private String isMarried;
	private String defTokenId;
    private String defToken;
    //借么标识
    private String isBorrow;
    //共借人 /自然人
    private String oneedition;
    
	public String getOneedition() {
		return oneedition;
	}

	public void setOneedition(String oneedition) {
		this.oneedition = oneedition;
	}

	public String getIdStartDayStr() {
        return idStartDayStr;
    }

    public void setIdStartDayStr(String idStartDayStr) {
        this.idStartDayStr = idStartDayStr;
    }

    public String getIdEndDayStr() {
        return idEndDayStr;
    }

    public void setIdEndDayStr(String idEndDayStr) {
        this.idEndDayStr = idEndDayStr;
    }

    public String getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }

    public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the defTokenId
     */
    public String getDefTokenId() {
        return defTokenId;
    }

    /**
     * @param defTokenId the String defTokenId to set
     */
    public void setDefTokenId(String defTokenId) {
        this.defTokenId = defTokenId;
    }

    /**
     * @return the defToken
     */
    public String getDefToken() {
        return defToken;
    }

    /**
     * @param defToken the String defToken to set
     */
    public void setDefToken(String defToken) {
        this.defToken = defToken;
    }

	public String getIsBorrow() {
		return isBorrow;
	}

	public void setIsBorrow(String isBorrow) {
		this.isBorrow = isBorrow;
	}
    
}
