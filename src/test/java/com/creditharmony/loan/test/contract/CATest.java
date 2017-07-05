package com.creditharmony.loan.test.contract;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.adapter.constant.CaUnitSignType;
import com.creditharmony.loan.channel.goldcredit.service.JxSendDataService;
import com.creditharmony.loan.common.consts.CAKeyWord;
import com.creditharmony.loan.common.entity.CaCustomerSign;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.CaUtil;
import com.creditharmony.loan.test.base.AbstractTestCase;

public class CATest extends AbstractTestCase {
	@Autowired
	private LoanPrdMngService svc;

	@Autowired
	private JxSendDataService jxSendDataService;
	
	@Test
	public void testTime(){
		CaCustomerSign customerSign = new CaCustomerSign("Eson EsonA EsonB",
				CAKeyWord.CUSTOMER_SIGN, "00100100271", "110101198001010037","");
		//（以下无正文）
		CaUtil.signCompany("{00D98454-0000-CDC4-8684-73D1BF049DB8}",CaUnitSignType.CF,"00100100271",
"（以下无正文）");
		
		//（以下无正文）
		CaUtil.signCustomer("{00D98454-0000-CDC4-8684-73D1BF049DB8}",customerSign);
	}
}
