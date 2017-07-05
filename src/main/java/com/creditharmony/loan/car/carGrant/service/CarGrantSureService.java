package com.creditharmony.loan.car.carGrant.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.type.CardOrBookType;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.dao.GrantCustomerDao;
import com.creditharmony.loan.borrow.grant.dao.SendMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantCustomerEx;
import com.creditharmony.loan.borrow.grant.entity.ex.SendMoneyEx;
import com.creditharmony.loan.car.carGrant.ex.CarGrantEx;
import com.creditharmony.loan.car.carGrant.ex.CarLoanGrantEx;
import com.creditharmony.loan.car.common.consts.CarDeductWays;
import com.creditharmony.loan.car.common.dao.CarGrantDao;
import com.creditharmony.loan.car.common.dao.CarLoanGrantDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarLoanGrant;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;

/**
 * 
 * @Class Name CarGrantSureService
 * @Create In 2016年2月5日
 * 放款确认service，用来声明放款确认过程中的各种方法
 */
@Service("carGrantSureService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarGrantSureService extends CoreManager<CarLoanGrantDao, CarLoanGrantEx>{
	// 导出客户信息表
	@Autowired
	private GrantCustomerDao grantCustomerDao;
	// 导出打款表
	@Autowired
	private SendMoneyDao sendMoneyDao;

	// 放款表导出
	@Autowired
	private CarGrantDao grantDao;
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
	
	
	/**
	 * 查询LoanGrant
	 * 2016年1月18日
	 * @param loanGrant
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarLoanGrantEx findGrant(CarLoanGrant carLoanGrant){
		return dao.findGrant(carLoanGrant);
	}
	
	
	/**
	 * 查询要放款的数据  DeductReq
	 * @Create In 2016年2月17日
	 * @param paramMap
	 * @return 划扣记录 
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public DeductReq queryDeductReq(HashMap<Object, Object> hashMap,String rule) {
		// 　取得规则
		DeductReq deductReq;
		deductReq = dao.queryDeductReq(hashMap);
		if (deductReq != null) {
			// 设置划扣标志
			deductReq.setDeductFlag(DeductFlagType.PAY.getCode());
			// 设置划扣规则
			deductReq.setRule(rule);
			// 系统处理ID
			deductReq.setSysId(CarDeductWays.CJ_01.getCode());
			// 设置账户类型
			deductReq.setAccountType(CardOrBookType.BANKCARD.getCode());
		}
		return deductReq;
	}

	
	/**
	 * 分配卡号
	 * 2015年12月31日
	 * @param applyCode
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarLoanGrantEx queryDisDeal(String applyCode){
		return dao. queryDisDeal(applyCode);
	}
	


	/**
	 * 导出客户信息表
	 * 2015年12月21日
	 * @param id 放款id
	 * @return
	 */
	public GrantCustomerEx getCustomerList(String applyId){
		return null;
		//return grantCustomerDao.getCustomerList(applyId);
	}
	
	/**
	 * 导出打款表
	 * 2015年12月22日
	 * @param id 放款id
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public SendMoneyEx getMoneyList(String id){
		return null;
//		return sendMoneyDao.getMoneyList(id);
	}
	

	/**
	 * 线下放款表导出
	 * 2016年2月15日
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarGrantEx getGrantList(HashMap<Object, Object> hashMap){
		return grantDao.getCarGrantList(hashMap);
	}
	


	/**
     *查询车借借款信息
     *@param String 
     *@return   String
     */
	@Transactional(readOnly = true, value = "loanTransactionManager")
    public CarLoanInfo selectByApplyId(String applyId){
    	
    	return carLoanInfoDao.selectByApplyId(applyId);
    }
    

	
	/**
	 * 更新借款状态、退回原因、备注   车借
	 * 2016年2月17日
	 * @param loanGrant 放款实体
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateCarStatus(CarLoanInfo loanInfo){
		
		return dao.updateCarStatus(loanInfo);
	};
}
