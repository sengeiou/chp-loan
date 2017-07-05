package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 省市级实体
 * 
 * @Class Name City
 * @author 张永生
 * @Create In 2015年11月17日
 */
public class CityInfo extends DataEntity<CityInfo> {

	private static final long serialVersionUID = -7338836374301816767L;

	private String areaName;
	private String shortName;
	private String areaCode;
	private String areaType;
	private String parentId;
	private String cityCarCode;
	public String getAreaName() {
		return areaName;
	}
	public String getShortName() {
		return shortName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public String getAreaType() {
		return areaType;
	}
	public String getParentId() {
		return parentId;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCityCarCode() {
		return cityCarCode;
	}
	public void setCityCarCode(String cityCarCode) {
		this.cityCarCode = cityCarCode;
	}

}
