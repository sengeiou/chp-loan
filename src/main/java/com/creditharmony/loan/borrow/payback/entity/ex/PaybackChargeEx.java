/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entity.exPaybackSplitConstant.java
 * @Create By zhaojinping
 * @Create In 2015年12月11日 下午3:34:43
 */
package com.creditharmony.loan.borrow.payback.entity.ex;

/**
 * @Class Name PaybackChargeEx
 * @author zhaojinping
 * @Create In 2015年12月11日
 */
public class PaybackChargeEx {
	// 门店组织机构父id
	private String parentId;
	// 门店名称
	private String name;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
