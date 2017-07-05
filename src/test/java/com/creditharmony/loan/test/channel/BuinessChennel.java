package com.creditharmony.loan.test.channel;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.loan.channel.finance.entity.CarFinancialBusiness;
import com.creditharmony.loan.channel.finance.entity.FinancialBusiness;
import com.creditharmony.loan.channel.finance.service.CarFinancialBusinessService;
import com.creditharmony.loan.channel.finance.service.FinancialBusinessService;
import com.creditharmony.loan.test.base.AbstractTestCase;

public class BuinessChennel extends AbstractTestCase{
	@Autowired
	FinancialBusinessService service;
	
	@Autowired
	CarFinancialBusinessService carservice;
	
	@Ignore
	public void test() {
		FinancialBusiness finance =new FinancialBusiness();
		//finance.preInsert();
		finance.setLoanCode("HJ00012016020400004");
		service.insertFinancialBusiness(finance);
	}
	
	@Test
	public void cartest() {
		CarFinancialBusiness finance =new CarFinancialBusiness();
		//finance.preInsert();
		finance.setLoanCode("HJ00012016020400004");
		carservice.insertFinancialBusiness(finance);
	}

}
