/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.secretvisit.viewSecretView.java
 * @Create By 王彬彬
 * @Create In 2015年12月28日 下午9:12:34
 */
package com.creditharmony.loan.borrow.secretvisit.view;

/**
 * 暗访
 * 
 * @Class Name SecretView
 * @author 王彬彬
 * @Create In 2015年12月28日
 */
public class SecretView {

	// 客户姓名
	private String customerName;
	// 身份证号码
	private String certiNo;
	// 门店编号
	private String store;
	// 客户经理
	private String manager;
	// 客户经理
	private String managerCode;
	// 团队经理
	private String teamManager;
	// 团队经理
	private String teamManagerCode;
	// 产品名称
	private String productName;

	public String getCustomerName() {
		return customerName;
	}

	public String getCertiNo() {
		return certiNo;
	}

	public String getStore() {
		return store;
	}

	public String getManager() {
		return manager;
	}

	public String getTeamManager() {
		return teamManager;
	}

	public String getProductName() {
		return productName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public void setTeamManager(String teamManager) {
		this.teamManager = teamManager;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public String getTeamManagerCode() {
		return teamManagerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public void setTeamManagerCode(String teamManagerCode) {
		this.teamManagerCode = teamManagerCode;
	}
}
