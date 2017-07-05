package com.creditharmony.loan.borrow.stores.dao;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;

/**
 * 区域相关功能
 * @Class Name AreaDao
 * @author lirui
 * @Create In 2016年1月26日
 */
@LoanBatisDao
public interface AreaPreDao {
	
	/**
	 * 根据地区id获取地区名
	 * 2016年1月26日
	 * By lirui
	 * @param addressId 地区id
	 * @return 地区名称
	 */
	public String getAreaName(String addressId);
	
	/**
	 * 根据用户id查询用户姓名
	 * 2016年1月27日
	 * By lirui
	 * @param userId
	 * @return 用户姓名
	 */
	public String getUserName(String userId);
	
	/**
	 * 根据组织机构id获取组织机构名字
	 * 2016年1月27日
	 * By lirui
	 * @param orgId 组织机构id
	 * @return 组织机构名字
	 */
	public String getOrgName(String orgId);
	
	/**
	 * 根据applyId获得当前流程状态
	 * 2016年2月15日
	 * By lirui
	 * @param applyId
	 * @return 当前流程code
	 */
	public String getStatusByApplyId(String applyId);
	
	/**
	 * 根据借款编码查询退回门店原因
	 * 2016年2月18日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 退回原因code组成的字符串
	 */
	public String getBackReason(String loanCode);
	
	/**
	 * 根据产品code获取产品名
	 * 2016年2月25日
	 * By lirui
	 * @param productCode 产品编号
	 * @return 产品名称
	 */
	public String getProductNameByCode(String productCode);
	
	/**
	 * 根据applyId查询借款编码
	 * 2016年3月1日
	 * By lirui
	 * @param applyId
	 * @return 借款编码
	 */
	public String getLoanCodeByApplyId(String applyId);
	
	/**
	 * 根据loanCode获得applyId
	 * 2016年5月13日
	 * t_jk_reconsider_apply复议申请表，
	 * 根据loanCode查询复议申请表是否存在记录，如果存在取复议申请表中的applyid，如果不存在取t_jk_loan_info借款表的applyid
	 * By chenyl
	 * @param loanCode
	 * @return applyId
	 */
	public String getApplyIdByLoanCode(String loanCode);
	
	/**
	 * 根据门店id查询门店省份id
	 * 2016年3月5日
	 * By lirui
	 * @param orgId 门店id
	 * @return 省份id
	 */
	public String getProvinceId(String orgId);
	
	/**
	 * 根据门店id查询城市id
	 * 2016年3月5日
	 * By lirui
	 * @param orgId 门店id
	 * @return 城市id
	 */
	public String getCityId(String orgId);
	
	/**
	 * 根据复议applyId查询信审applyId
	 * 2016年3月7日
	 * By lirui
	 * @param applyId 复议流程id
	 * @return 信审applyId
	 */
	public String getApplyId(String applyId);
	
	/**
	 * 通过用户code获得用户姓名
	 * 2016年4月18日
	 * By lirui
	 * @param userCode 用户code
	 * @return 用户姓名
	 */
	public String selectNameByCode(String userCode);
}
