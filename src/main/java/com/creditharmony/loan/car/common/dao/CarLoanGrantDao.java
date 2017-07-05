/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.daoLoanGrantDao.java
 * @Create By 张振强
 * @Create In 2015年11月28日 下午6:19:34
 */
/**
 * @Class Name LoanGrantDao
 * @author 张振强
 * @Create In 2015年11月28日
 */
package com.creditharmony.loan.car.common.dao;

import java.util.HashMap;

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.carGrant.ex.CarLoanGrantEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeMoneyInfoEx;
import com.creditharmony.loan.car.common.entity.CarLoanGrant;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;
@LoanBatisDao
public interface CarLoanGrantDao extends CrudDao<CarLoanGrantEx>{
	
	/**
	 * 查询LoanGrant
	 * 2016年1月18日
	 * @param loanGrant
	 * @return
	 */
	public CarLoanGrantEx findGrant(CarLoanGrant carLoanGrant);
	
	
	/**
	 * 根据借款编码获取放款记录
	 * 2016年3月4日
	 * By 陈伟东
	 * @param carLoanGrant
	 * @return
	 */
	public CarLoanGrant findGrantByLoanCode(CarLoanGrant carLoanGrant);
	
	/**
	 * 对放款表进行插入
	 * 2015年12月30日
	 * @param loanGrant
	 * @return
	 */
	public int insertGrant(CarLoanGrant carLoanGrant);
	
	/**
	 * 更新放款记录表
	 * 2015年12月11日
	 * @param loanGrant 放款实体
	 * @return
	 */
	public int updateLoanGrant(CarLoanGrant carLoanGrant);
	
	/**
	 * 更新放款记录表  车借
	 * 2016年2月17日
	 * @param loanGrant 放款实体
	 * @return
	 */
	public int updateCarStatus(CarLoanInfo loanInfo);
	
	/**
	 * 查询划扣信息  车借，用于向催收服务费表插入数据
	 * 2016年2月23日
	 * @param contractCode 
	 * @return
	 */
	public CarUrgeMoneyInfoEx selectUrgeMoney(String contractCode);
	
	/**
	 * 分配卡号页面信息查询  车借
	 * 2016年2月17日
	 * @param applyId
	 * @return
	 */
	public CarLoanGrantEx queryDisDeal(String applyId);
	
	/**
	 *  查询要放款的数据  DeductReq  车借
	 * @Create In 2016年2月17日
	 * @param paramMap
	 * @return DeductReq 要放款的数据 
	 */
	public DeductReq queryDeductReq(HashMap<Object, Object> hashMap);
	
	/**
	 * 根据合同编号查询applyId  车借
	 * 2016年2月16日
	 * @param contractCode 合同编号
	 * @return applyId
	 */
	public String selectCarApplyId(String contractCode);
	
	/**
	 * 根据applyId 查询车借债权信息
	 * 2016年2月16日
	 * @return applyId
	 */
	public CreditorRights selectCreditorRights(String applyId);
}