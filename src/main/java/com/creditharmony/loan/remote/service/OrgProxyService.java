package com.creditharmony.loan.remote.service;

/**
 * 组织机构代理接口
 * 调用远端的接口
 * @Class Name OrgProxyService
 * @author 张永生
 * @Create In 2016年2月25日
 */
public interface OrgProxyService {

	/**
	 * 根据orgId更新其车借编码
	 * 2016年2月25日
	 * By 张永生
	 * @param orgId
	 * @param carLoanCode
	 * @return
	 */
	public boolean updateCarLoanCode(String orgId, String carLoanCode);
}
