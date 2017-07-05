/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityFileDiskInfo.java
 * @Create By 张灏
 * @Create In 2016年3月24日 上午11:26:26
 */
package com.creditharmony.loan.common.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 影像文件磁盘信息封装对象
 * @Class Name FileDiskInfo
 * @author 张灏
 * @Create In 2016年3月24日
 */
public class FileDiskInfo extends DataEntity<FileDiskInfo>{
      
    /**
     * long serialVersionUID 
     */
    private static final long serialVersionUID = 1L;
    
    private String sysIp;//影响平台IP
    
    private Date startDate;
    
    private Date endDate;
    
    private String flagHj;
    
    private String flagHc;

    private String secretKey; //影响平台加密key
    
    private String uId;// 登录ID
    
    private String sysPwd;//系统密码
    
    private String appId; //影像appId
    
    private String sysFlag; //系统标记
    
    private String orgId; //系统标记
    
    private String orgName; //系统标记
    
    private String my;//加密KEY
    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the Date startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the Date endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the flag
     */
    public String getFlagHj() {
        return flagHj;
    }

    /**
     * @param flag the String flag to set
     */
    public void setFlagHj(String flagHj) {
        this.flagHj = flagHj;
    }

    /**
     * @return the flagHc
     */
    public String getFlagHc() {
        return flagHc;
    }

    /**
     * @param flagHc the String flagHc to set
     */
    public void setFlagHc(String flagHc) {
        this.flagHc = flagHc;
    }

	public String getSysIp() {
		return sysIp;
	}

	public void setSysIp(String sysIp) {
		this.sysIp = sysIp;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getSysPwd() {
		return sysPwd;
	}

	public void setSysPwd(String sysPwd) {
		this.sysPwd = sysPwd;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSysFlag() {
		return sysFlag;
	}

	public void setSysFlag(String sysFlag) {
		this.sysFlag = sysFlag;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getMy() {
		return my;
	}

	public void setMy(String my) {
		this.my = my;
	}
    
    
}
