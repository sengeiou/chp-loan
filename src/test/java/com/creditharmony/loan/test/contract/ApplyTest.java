package com.creditharmony.loan.test.contract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.adapter.service.jxcredit.bean.CreditCreditorsRightsOutBean;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.loan.channel.goldcredit.service.JxSendDataService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.test.base.AbstractTestCase;

public class ApplyTest extends AbstractTestCase {
	@Autowired
	private LoanPrdMngService svc;

	@Autowired
	private JxSendDataService jxSendDataService;
	public void testApply() {
		System.out.println("Test Start");
		List<String> jj=new ArrayList<String>();
		jj.add("40000032563668200048");
		jj.add("1011800064");
		List<CreditCreditorsRightsOutBean> list = jxSendDataService.findJINXINPush(jj);
		System.out.println(list.toString());
	}
	
	@Test
	public void testTime(){
		for(int i=0;i<5;i++)
		{
			System.out.println(IdGen.uuid());
		}
	}
}
