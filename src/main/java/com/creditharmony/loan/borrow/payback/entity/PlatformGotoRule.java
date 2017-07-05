package com.creditharmony.loan.borrow.payback.entity;

import java.util.Date;
import java.util.List;

import com.creditharmony.core.fortune.type.DeductPlat;
import com.creditharmony.core.persistence.DataEntity;
import com.google.common.collect.Lists;

/**
 * 平台跳转规则表
 * @Class Name PlatformRule
 * @author 周俊
 * @Create In 2016年3月4日
 */
public class PlatformGotoRule extends DataEntity<PlatformGotoRule>{
	/**
	 * long serialVersionUID 
	 */
	private static final long serialVersionUID = 1L;

	private String id; // 主键
	
	private String platformId; //平台id   
	
	private String platformRuleName; // 规则名称, 
	
	private String platformRule; // 规则 
	
	private String platformRuleTitle; // 规则 
	
	private List<String> platformIdList; //可跳转平台id 
	
	private String status; // 状态; 
	
	private String createBy; 

    private Date createTime;
    
    private String isConcentrate;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getPlatformRuleName() {
		return platformRuleName;
	}

	public void setPlatformRuleName(String platformRuleName) {
		this.platformRuleName = platformRuleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getPlatformRule() {
		return platformRule;
	}

	public void setPlatformRule(String platformRule) {
		this.platformRule = platformRule;
	}

	public List<String> getPlatformIdList() {
		if(platformRule != null){
			platformIdList = Lists.newArrayList();
			String[] deductPlatform = platformRule.split(",");
//			StringBuffer str = new StringBuffer();
			for (String string : deductPlatform) {
				platformIdList.add(string);
			}
		}
		return platformIdList;
	}

	public void setPlatformIdList(List<String> platformIdList) {
		this.platformIdList = platformIdList;
	}

	private String modifyBy;

    private Date modifyTime;

	public String getPlatformRuleTitle() {
		//DeductPlat.initDeductPlat();
		if(platformRule != null){
			String[] deductPlatform = platformRule.split(",");
			StringBuffer str = new StringBuffer();
			for (String string : deductPlatform) {
				
				str.append(DeductPlat.getDeductPlat(string)).append("-->");
			}
			platformRuleTitle = str.substring(0, str.length()-3);
		}
		return platformRuleTitle;
	}

	public void setPlatformRuleTitle(String platformRuleTitle) {
		this.platformRuleTitle = platformRuleTitle;
	}

	public String getIsConcentrate() {
		return isConcentrate;
	}

	public void setIsConcentrate(String isConcentrate) {
		this.isConcentrate = isConcentrate;
	}
	
}
