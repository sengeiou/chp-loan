package com.creditharmony.loan.test.contract;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.common.dao.GlBillDao;
import com.creditharmony.loan.common.entity.RepayPlanNew;
import com.creditharmony.loan.common.utils.ReckonFeeNew;
import com.creditharmony.loan.test.base.AbstractTestCase;

public class ReckonFee extends AbstractTestCase {
  
	@Autowired
	private GlBillDao glBillDao;
	@Test
	public void createPlan() throws Exception{
//		Contract baseFee = new Contract();
//    	baseFee.setContractMonths(new BigDecimal(36));
//        baseFee.setAuditAmount(new BigDecimal(200000));
//        baseFee.setContractDueDay(new Date());
//        Date contractDueDay = baseFee.getContractDueDay();
//        Calendar tempCalendar = Calendar.getInstance();
//        tempCalendar.setTime(contractDueDay);
//        tempCalendar.add(Calendar.MONTH,1);
//        baseFee.setContractReplayDay(tempCalendar.getTime());
//        ContractFee fee = new ContractFee();
//        fee.setFeePetition(new BigDecimal(Double.toString(200)));
//        fee.setFeeExpedited(new BigDecimal(Double.toString(0)));
//        fee.setFeeAllRaio(new BigDecimal(Double.toString(2.56)));
//        fee.setFeeMonthRate(new BigDecimal(Double.toString(1.00)));
//        ReckonFeeNew.ReckonFeeOneStep(baseFee, fee);
//        List<RepayPlanNew> repayPlans =  ReckonFeeNew.createRepayPlanNew(baseFee, fee, glBillDao);
//        StringBuffer rpBuf = new StringBuffer();
//        rpBuf.append("当期期数   计息当期本金   月还本金   月还利息   月还本息金额   分期服务费   应还款总额   一次性还款违约罚金  一次性还款总额   （还款后）剩余本金");
//        System.out.println(rpBuf.toString());
//        for(RepayPlanNew rp:repayPlans){
//        	
//        	rpBuf = new StringBuffer();
//        	rpBuf.append(rp.getPayBackCurrentMonth()+" "+rp.getCurPrincipal()+" "+rp.getMonthPayAmount()+" ")
//        	.append(" "+rp.getMonthInterestBackshould()+" "+rp.getMonthPayAmountSum())
//        	.append(" "+rp.getStageServiceFee()+" "+rp.getMonthPaySum())
//            .append(" "+rp.getOncePayPenalty()+" "+rp.getOncePaySum()+" "+rp.getResidualPrincipal());
//        	System.out.println(rpBuf.toString());
//        }
//       Integer PetitionFee= ReckonFeeNew.PetitionFee(new Double("50"));
//       System.out.println("信访费："+PetitionFee);
	}
}
