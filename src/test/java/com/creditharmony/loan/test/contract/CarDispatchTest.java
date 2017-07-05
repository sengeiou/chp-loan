package com.creditharmony.loan.test.contract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.creditharmony.bpm.utils.SpringUtil;
import com.creditharmony.loan.car.common.util.BPMDispatchService;

/**
 * 
 * @Class Name CarContraceCodeTest
 * @author 陈伟东
 * @Create In 2016年2月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-context*.xml", "classpath:applicationContext-ruleManage.xml" })
public class CarDispatchTest{
	
	@Autowired
	protected ApplicationContext ctx;
	@Autowired
	BPMDispatchService dispatchService;

	@Test
	public void test(){
		SpringUtil.setCtx(ctx);
		dispatchService.dispatch("ABC", "ABCD",null);
		
	}

}
