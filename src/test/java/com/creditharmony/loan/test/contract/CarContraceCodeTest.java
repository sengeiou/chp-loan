package com.creditharmony.loan.test.contract;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.loan.car.carTotalRate.service.CarGrossSpreadService;
import com.creditharmony.loan.common.consts.NumberManager;
import com.creditharmony.loan.common.entity.NumberMaster;
import com.creditharmony.loan.common.service.ContractCommonService;
import com.creditharmony.loan.test.base.AbstractTestCase;

/**
 * 
 * @Class Name CarContraceCodeTest
 * @author 陈伟东
 * @Create In 2016年2月24日
 */
public class CarContraceCodeTest extends AbstractTestCase {
	
	@Autowired
	ContractCommonService service;
	
	@Autowired
	CarGrossSpreadService grossSpreaService;

	@Test
	public void testGetContractCode() {
		NumberMaster numberMaster = new NumberMaster();

		numberMaster.setDealDate(NumberManager.DATE_INIT);
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC);
		numberMaster.setDealPart(NumberManager.CONTRACT_TYPE);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int count = service.getNumberMaster(numberMaster);
		
		System.out.println(count);
	}
	
	@Test
	public void testGetRate(){
		//Double carGrossSpread = grossSpreaService.getCarGrossSpread("3", "CJ03");
		//System.out.println(carGrossSpread);
	}
	

}
