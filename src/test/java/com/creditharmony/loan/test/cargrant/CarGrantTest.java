package com.creditharmony.loan.test.cargrant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.utils.SpringUtil;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.bean.out.PaybackSplitEntityEx;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.loan.car.carGrant.service.CarGrantRecepicService;

/**
 * 
 * @Class Name CarGrantTest
 * @author 张振强
 * @Create In 2016年3月23日
 */
@ContextConfiguration(locations ={"classpath*:/spring-context*.xml", 
"classpath:applicationContext-ruleManage.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "loanTransactionManager", defaultRollback = false)
public class CarGrantTest{
	
	@Autowired
	CarGrantRecepicService carGrantRecepicService;
	@Autowired
	protected ApplicationContext ctx;
	
	@Test
	public void testCarGrant() {
		SpringUtil.setCtx(ctx);
		//carGrantRecepicService.grantReceic("车(闽)借字(2016)第350600555550006号", "0");
		LoanDeductEntity LoanDeductEntity = new LoanDeductEntity();
		PaybackSplitEntityEx paybackSplitEntityEx1 = new PaybackSplitEntityEx();
		List<PaybackSplitEntityEx> plist= new ArrayList<PaybackSplitEntityEx>();
		
		paybackSplitEntityEx1.setId("111");
		//paybackSplitEntityEx2.setId("222");
		paybackSplitEntityEx1.setPaybackApplyId("2e5f77e08068453e9a754914ba43e3e8");
		//paybackSplitEntityEx2.setPaybackApplyId("2e5f77e08068453e9a754914ba43e3e8");
		
		paybackSplitEntityEx1.setSplitAmount(new BigDecimal(Double.toString(500.0627)));
		//paybackSplitEntityEx2.setSplitAmount(new BigDecimal(523));
		
		paybackSplitEntityEx1.setSplitBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
		//paybackSplitEntityEx2.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
		plist.add(paybackSplitEntityEx1);
		//plist.add(paybackSplitEntityEx2);
		LoanDeductEntity.setSplitData(plist);
		carGrantRecepicService.deductReceic(LoanDeductEntity);
		
		
	}
	
}
