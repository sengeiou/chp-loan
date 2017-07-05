package com.creditharmony.loan.test.contract;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.loan.borrow.grant.dao.GrantUrgeBackDao;
import com.creditharmony.loan.borrow.grant.dao.UrgeServicesMoneyDao;
import com.creditharmony.loan.car.common.consts.CarRefundStatus;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarLoanGrantDao;
import com.creditharmony.loan.car.common.dao.CarLoanStatusHisDao;
import com.creditharmony.loan.car.common.dao.CarRefundAuditDao;
import com.creditharmony.loan.car.common.dao.CarUrgeServicesMoneyDao;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarLoanGrant;
import com.creditharmony.loan.car.common.entity.CarRefundInfo;
import com.creditharmony.loan.car.common.entity.CarUrgeServicesMoney;
import com.creditharmony.loan.test.base.AbstractTestCase;

/**
 * 
 * @Class Name CarContraceCodeTest
 * @author 陈伟东
 * @Create In 2016年2月24日
 */
public class CarGrantTest extends AbstractTestCase {
	

	@Autowired
	private CarContractDao carContractDao;

//	@Autowired
//	CarGrantDao dao;
//	
//	@Test
//	public void testGetContractCode() {
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
//		map.put("applyId", "HJ00032016020400029");
//		CarGrantEx carGrantList = dao.getCarGrantList(map);
//		System.out.println(carGrantList.getContractCode());
//	}
	
//	@Autowired
//	CarLoanGrantDao dao;
	
	@Autowired
	private CarLoanGrantDao loanGrantDao;	
	@Autowired
	private GrantUrgeBackDao grantUrgeBackDao;
	@Autowired
	private CarLoanStatusHisDao carLoanStatusHisDao;
	@Autowired
	private UrgeServicesMoneyDao urgeServicesMoneyDao;
	@Autowired
	CarUrgeServicesMoneyDao carUrgeServicesMoneyDao;
	@Autowired
	CarRefundAuditDao carRefundAuditDao;
	
	@Test
	public void testGetContractCode() {
//		HashMap<Object, Object> map = new HashMap<Object, Object>();
//		map.put("applyId", "HJ00032016020400029");
//		CarLoanGrant en = new CarLoanGrant();
//		en.setContractCode("车(京)借字(2016)第3506001111110001号");
//		CarLoanGrantEx en2 = dao.findGrant(en);
//		System.out.println(en2.getApplyId());
		
//		CarLoanGrant carLoanGrant = new CarLoanGrant();
//		carLoanGrant.setId("7654321");
//		carLoanGrant.setContractCode("wew");
//		dao.insertGrant(carLoanGrant);
		
//		CarLoanGrant carLoanGrant = new CarLoanGrant();
//		carLoanGrant.setContractCode("wew");
//		carLoanGrant.setGrantAmount(new BigDecimal(3.333));
//		dao.updateLoanGrant(carLoanGrant);
		
//		CarLoanInfo info = new CarLoanInfo();
//		info.setApplyId("HJ00032016020400005");
//		info.setDictLoanStatus("6");
//		dao.updateCarStatus(info);
		
//		UrgeMoneyInfoEx selectUrgeMoney = dao.selectUrgeMoney("质(闽)借字(2016)第350600110001号");
//		System.out.println(selectUrgeMoney.getDbName());
		
		
	   CarLoanGrant carLoanGran = new CarLoanGrant();
	   carLoanGran.setLoanCode("JKKH20160218000030");
	   CarLoanGrant carLoanGrantEx = loanGrantDao.findGrantByLoanCode(carLoanGran);
//	   System.out.println(carLoanGrantEx.getBankCardNo());
	   
	   CarUrgeServicesMoney urg = new CarUrgeServicesMoney();
	   urg.setrGrantId(carLoanGrantEx.getId());
	   CarUrgeServicesMoney carUrgeServicesMoney = carUrgeServicesMoneyDao.findCurrentCarUrgeMoneyByGrantId(urg);
	   System.out.println(carUrgeServicesMoney.getCreateBy());
	   
	   CarRefundInfo refundInfo = new CarRefundInfo();
	   refundInfo.preInsert();
	   refundInfo.setrChargeId(carLoanGrantEx.getId());
	   refundInfo.setReturnAmount(carUrgeServicesMoney.getUrgeDecuteMoeny().doubleValue());
	   refundInfo.setReturnStatus(CarRefundStatus.CAR_RETURN_STATUS_W.getCode());
	   refundInfo.setReturnIntermediaryId(carLoanGrantEx.getMidId());
	   refundInfo.setContractCode(carLoanGrantEx.getContractCode());
	   refundInfo.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_W.getCode());
	   carRefundAuditDao.insertCarRefundInfo(refundInfo);
	}
	

	@Test
	public void uptSignFlag(){
		CarContract carContract = new CarContract();
		carContract.setLoanCode("JK2016060800000052");
		carContract.setSignFlag("1");
		carContractDao.update(carContract);
	}

}
