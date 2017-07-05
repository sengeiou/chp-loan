package com.creditharmony.loan.common.entity;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.TreeEntity;
import com.creditharmony.core.users.entity.Area;

/**
 * 组织机构实体
 * 
 * @Class Name Org
 * @author 王彬彬
 * @Create In 2015年12月4日
 */
@SuppressWarnings("serial")
public class OrgGl extends TreeEntity<OrgGl> {

	@ExcelField(title = "机构名称", type = 0, align = 2, sort = 1, groups = 1)
	private String name;

	@ExcelField(title = "归属区域", type = 0, align = 2, sort = 10, groups = { 1, 2 })
	private Area area;

	@ExcelField(title = "机构编码", type = 0, align = 2, sort = 20)
	private String code;

	@ExcelField(title = "机构类型", type = 0, align = 2, sort = 30)
	private String type;

	@ExcelField(title = "机构等级", type = 0, align = 2, sort = 40)
	private String grade;

	@ExcelField(title = "联系地址", type = 0, align = 2, sort = 50)
	private String address;

	@ExcelField(title = "邮政编码", type = 0, align = 2, sort = 60)
	private String zipCode;

	@ExcelField(title = "负责人", type = 0, align = 2, sort = 70)
	private String master;

	private String creditPaperless; // 无纸化标识
	
	private String carPaperless; 	//车借无纸化

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Area getArea() {
		return area;
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public String getGrade() {
		return grade;
	}

	public String getAddress() {
		return address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getMaster() {
		return master;
	}

	public OrgGl getParent() {
		return parent;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public void setParent(OrgGl parent) {
		this.parent = parent;
	}

	public String getCreditPaperless() {
		return creditPaperless;
	}

	public void setCreditPaperless(String creditPaperless) {
		this.creditPaperless = creditPaperless;
	}

	public String getCarPaperless() {
		return carPaperless;
	}

	public void setCarPaperless(String carPaperless) {
		this.carPaperless = carPaperless;
	}

}