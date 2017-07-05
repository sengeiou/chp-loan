package com.creditharmony.loan.borrow.delivery.entity.ex;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 门店信息视图
 * @Class Name OrgView
 * @author lirui
 * @Create In 2015年12月9日
 */
@SuppressWarnings("serial")
public class OrgView extends DataEntity<OrgView> {
	private String orgCode;// 机构编码
	private String orgName;// 机构名
	private String storesCode;// 门店编码
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getStoresCode() {
		return storesCode;
	}
	public void setStoresCode(String storesCode) {
		this.storesCode = storesCode;
	}
	
	
}
