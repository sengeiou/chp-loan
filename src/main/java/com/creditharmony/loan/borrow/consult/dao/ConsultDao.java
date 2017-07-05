package com.creditharmony.loan.borrow.consult.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.payback.entity.Payback;

/**
 * 客户咨询DAO
 * @Class Name ConsultDao
 * @author 张平
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface ConsultDao extends CrudDao<Consult> {

    /**
     *新增客户基本信息
     *@author zhanghao
     *@Create In 2016年02月20日
     *@param consult
     *@return none
     */
	public void insertCustomerBaseInfo(Consult consult);

	/**
     *更新客户基本信息
     *@author zhanghao
     *@Create In 2016年02月20日
     *@param consult
     *@return none
     */
	public void updateCustomerBaseInfo(Consult consult);

	/**
     *插入咨询记录
     *@author zhanghao
     *@Create In 2016年02月20日
     *@param consult
     *@return none
     */
	public void insertConsultRecord(Consult consult);

    /**
     *通过证件号码获取客户编号
     *@author zhanghao
     *@Create In 2016年02月20日
     *@param mateCertNum
     *@return String
     */
	public String findCustomerByMcNum(String mateCertNum);
 
    /**
     *通过客户编号获取咨询信息 
     *@author zhanghao
     *@Create In 2016年02月20日
     *@param customerCode
     *@return String
     */
	public String getConsultByCustomerCode(String customerCode);
	
	/**
	 *更改客户咨询状态 
	 *@author zhanghao
	 *@Create In 2016年02月20日
	 *@param param key customerCode客户编号，curConsultStatus当前咨询状态 tagConsultStatus目标状态
	 *@return none
	 */
	public void updateConsultStatus(Map<String,Object> param);
	
	/**
	 *通过用户编号查询客户的结清状态 
	 *by zhanghao
	 *Create In 2016-01-09
	 *@param key customerCode , payStatus 数组 
	 *@return List<Payback>
	 */
	public List<Payback> findUnsettledByCustomerCode(Map<String,Object> param);
	
	public Date findTimeByApplyId(String applyId);
	
	public Date findTimeByLoanCode(String loanCode);
	

}
