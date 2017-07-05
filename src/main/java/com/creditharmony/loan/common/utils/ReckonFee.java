package com.creditharmony.loan.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.FeeInfoEx;
import com.creditharmony.loan.common.dao.GlBillDao;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.RepayPlan;

/**
 *合同相关费用计算工具类 
 *@author 张灏
 *@version 1.0
 * 
 */
public class ReckonFee {
    
    private static final Integer FEBRUARY = 2;  // 二月
    private static final Integer ADD_ONE_MONTH = 1; // 增加一个月
	
	/**
	 *放款金额计算 
	 *计算实际放款金额(feePaymentAmount)、月还本息金额(auditMonthPaymoney)、预计还款总额 (contractExpectCount)
	 *
	 *@version 1.0
	 *@author 张灏 
	 *@param baseFee
	 *传参：借款期限(loanApplyMonth)、批复金额(auditMoney)、信访费(feePetitionFee)、加急费(feeExpeditedFee)、总费率(feeAllRaio)  
	 * 信访费、加急费用于计算实际放款金额 ,只要任何一个为NULL时,都不计算实际放款金额
	 */
	public static void ReckonFeeOneStep(Contract baseFee,ContractFee fee){
		BigDecimal loanLimit = baseFee.getContractMonths();     // 借款期限
		BigDecimal auditMoney = baseFee.getAuditAmount();        // 批复金额
		BigDecimal petitionFee = fee.getFeePetition();   // 信访费
		BigDecimal expeditedFee = fee.getFeeExpedited(); // 加急费
		BigDecimal feeAllRaio = fee.getFeeAllRaio().divide(new BigDecimal(100));        // 总费率
		
		BigDecimal principalMonthMoney = auditMoney.divide(loanLimit, ContractConstant.SCALE, BigDecimal.ROUND_HALF_UP); // 月还本金
		BigDecimal monthFee = auditMoney.multiply(feeAllRaio);    // 月费
		BigDecimal auditMonthPaymoney =principalMonthMoney.add(monthFee)  ;// 月还本息金额
		
		BigDecimal contractExpectAmount = auditMonthPaymoney.multiply(loanLimit); // 预计还款总额
		BigDecimal feePaymentAmount =auditMoney;
		if(petitionFee!=null){
		    feePaymentAmount = feePaymentAmount.subtract(petitionFee); 
		}
		if(expeditedFee!=null){
		    feePaymentAmount=feePaymentAmount.subtract(expeditedFee);  // 放款到手金额
			
		}
		fee.setFeePaymentAmount(feePaymentAmount);
		baseFee.setContractMonthRepayAmount(auditMonthPaymoney);
		baseFee.setContractExpectAmount(contractExpectAmount);
		return;
	}
	/**
	 *费用计算 
	 *计算合同金额(contractAmount)、综合费用(feeCount)、信息服务费 (feeInfoService)
	 *居间服务费(feeService)、审核费(feeAuditAmount)、咨询费(feeConsult)、催收服务费(feeUrgedService)
	 *@version 1.0
	 *@author 张灏 
	 *@param baseFee
	 *传参：借款期限(loanApplyMonth)、批复金额(auditMoney)、借款利率(feeLoanRate)、月还本息金额(auditMonthPaymoney) 
	 *
	 */
	public static  void ReckonFeeTwoStep(Contract baseFee,ContractFee fee){
	
		BigDecimal loanLimit = baseFee.getContractMonths();     // 借款期限
		BigDecimal auditMoney = baseFee.getAuditAmount();        // 批复金额
		BigDecimal loanRate =fee.getFeeLoanRate().divide(new BigDecimal(100));         // 借款利率
		BigDecimal auditMonthPaymoney =baseFee.getContractMonthRepayAmount();// 月还本息金额
	
		/********************************** 第二步推导计算 *****************************************************/
		
		BigDecimal _temp1 =(loanRate.add(new BigDecimal(1))).pow(loanLimit.intValue());//（1+月利率）^借款期限
		BigDecimal _temp2 = auditMonthPaymoney.divide(loanRate,ContractConstant.SCALE,BigDecimal.ROUND_HALF_UP);// 月还款额×（1/月利率）
		BigDecimal contractAmount = _temp2.subtract((_temp2.divide(_temp1,ContractConstant.SCALE,BigDecimal.ROUND_HALF_UP))); // 合同金额
		BigDecimal feeCount = contractAmount.subtract(auditMoney);     // 综合费用
		BigDecimal feeInfoService = feeCount.multiply(new BigDecimal(ContractConstant.FEEINFOSERVICE_RATE)); // 信息服务费
		BigDecimal feeService = feeCount.multiply(new BigDecimal(ContractConstant.FEESERVICE_RATE));   // 居间服务费
		BigDecimal feeAuditAmount = feeCount.multiply(new BigDecimal(ContractConstant.FEEAUDITAMOUNT_RATE));   // 审核费
		BigDecimal feeConsult = feeCount.multiply(new BigDecimal(ContractConstant.FEECONSULT_RATE));   // 咨询费
		BigDecimal feeUrgedService = contractAmount.multiply(new BigDecimal(ContractConstant.FEEURGEDSERVICE_RATE));  // 催收服务费
		baseFee.setContractAmount(contractAmount);
		fee.setFeeCount(feeCount);
		fee.setFeeInfoService(feeInfoService);
		fee.setFeeService(feeService);
		fee.setFeeAuditAmount(feeAuditAmount);
		fee.setFeeConsult(feeConsult);
		fee.setFeeUrgedService(feeUrgedService);
	}
	/**
	 *数字格式化 
	 * 
	 */
	public static String DecimalFormat(BigDecimal srcDecimal,String format){
	   	java.text.DecimalFormat myformat=new java.text.DecimalFormat(format);
		String formatVal = myformat.format(srcDecimal.doubleValue());
		return formatVal;
	}
	/**
     *合同金额、费率格式化 
     * 
     */
	public static FeeInfoEx FeeFormat(ContractFee ctrFee,Contract contract,String format){
	       FeeInfoEx feeInfoEx = new FeeInfoEx();
	       BigDecimal tempFee = null;
	       if(contract!=null){
	          tempFee = contract.getAuditAmount();
	          if(tempFee!=null){
	              feeInfoEx.setAuditAmount(ReckonFee.DecimalFormat(tempFee,format));
	          }
	          tempFee = contract.getContractAmount();
	          if(tempFee!=null){
                  feeInfoEx.setContractAmount(ReckonFee.DecimalFormat(tempFee,format));
              }
	          tempFee = contract.getContractExpectAmount();
	          if(tempFee!=null){
                  feeInfoEx.setContractExpectAmount(ReckonFee.DecimalFormat(tempFee,format));
              }
	          tempFee = contract.getContractMonthRepayAmount();
	          if(tempFee!=null){
                  feeInfoEx.setContractMonthRepayAmount(ReckonFee.DecimalFormat(tempFee,format));
              }
	          tempFee = contract.getMonthPayTotalAmount();
	          if(tempFee!=null){
	        	  feeInfoEx.setMonthPayTotalAmount(ReckonFee.DecimalFormat(tempFee, format));
	          }
	       }
	       if(ctrFee!=null){
	           tempFee = ctrFee.getFeeConsult();
	           if(tempFee!=null){
	               feeInfoEx.setFeeConsult(ReckonFee.DecimalFormat(tempFee, format)); 
	           }
	           tempFee = ctrFee.getFeeAuditAmount();
	           if(tempFee!=null){
	               feeInfoEx.setFeeAuditAmount(ReckonFee.DecimalFormat(tempFee, format));
	           }
	           tempFee = ctrFee.getFeeService();
	           if(tempFee!=null){
	               feeInfoEx.setFeeService(ReckonFee.DecimalFormat(tempFee, format));
	           }
               tempFee = ctrFee.getFeeUrgedService();
               if(tempFee!=null){
                   feeInfoEx.setFeeUrgedService(ReckonFee.DecimalFormat(tempFee, format));
               }
               tempFee = ctrFee.getFeeInfoService();
               if(tempFee!=null){
                   feeInfoEx.setFeeInfoService(ReckonFee.DecimalFormat(tempFee, format));
               }
               tempFee = ctrFee.getFeeCount();
               if(tempFee!=null){
                   feeInfoEx.setFeeCount(ReckonFee.DecimalFormat(tempFee, format));
               }
               tempFee = ctrFee.getFeePaymentAmount();
               if(tempFee!=null){
                   feeInfoEx.setFeePaymentAmount(ReckonFee.DecimalFormat(tempFee, format));
               }
               tempFee = ctrFee.getMonthFeeConsult();
               if(tempFee!=null){
            	   feeInfoEx.setMonthFeeConsult(ReckonFee.DecimalFormat(tempFee, format));
               }
               tempFee = ctrFee.getMonthMidFeeService();
               if(tempFee!=null){
            	   feeInfoEx.setMonthMidFeeService(ReckonFee.DecimalFormat(tempFee, format));
               }
               tempFee = ctrFee.getMonthFeeService();
               if(tempFee!=null){
            	   feeInfoEx.setMonthFeeService(ReckonFee.DecimalFormat(tempFee, format));
               }
               tempFee = ctrFee.getFeeExpedited();
               if(tempFee!=null){
            	   feeInfoEx.setFeeExpedited(ReckonFee.DecimalFormat(tempFee, format));
               }
	       }
	       return feeInfoEx;
	}
	
	/**
	 *根据信访距离计算信访费用 
	 * 
	 */
	public static Integer PetitionFee(Double distance){
	    
	    Integer petitionFee = ContractConstant.ZERO;
	    
	    if(ContractConstant.ZERO < distance &&  distance < ContractConstant.FIFTY_KILOMETERS){
	     
	        petitionFee = ContractConstant.TWO_HUNDRED;
	    
	    }else if(ContractConstant.FIFTY_KILOMETERS <= distance && distance < ContractConstant.ONE_HUNDRED_KILOMETERS){
	    
	        petitionFee = ContractConstant.THREE_HUNDRED;
	   
	    }else if(ContractConstant.ONE_HUNDRED_KILOMETERS <= distance && distance < ContractConstant.ONE_HUNDRED_FIFTY_KILOMETERS){
        
	        petitionFee = ContractConstant.FOUR_HUNDRED;
       
	    }else if(ContractConstant.ONE_HUNDRED_FIFTY_KILOMETERS <= distance){
        
	        petitionFee = ContractConstant.FIVE_HUNDRED;
        }
	    return petitionFee;
	}
	/**
	 *加急费 
	 * 
	 */
	public static Integer urgeFee(boolean isUrge){
	    Integer urgeFee = ContractConstant.ZERO;
	    
	    if(isUrge){
	        urgeFee = ContractConstant.TWO_HUNDRED;
	    }
	    return urgeFee;
	} 
	
	public static List<RepayPlan> createRepayPlan(Contract contract,ContractFee ctrFee,GlBillDao glBillDao){
        List<RepayPlan> repayPlans = new ArrayList<RepayPlan>();
        RepayPlan repayPlan = null;
        BigDecimal auditAmount = contract.getAuditAmount();    // 批借金额
        BigDecimal contractAmount = contract.getContractAmount(); // 合同金额
        BigDecimal contractMonths = contract.getContractMonths();  // 批借期限
        Integer loopCount = contractMonths.intValue();
        BigDecimal monthInitPayAmount = auditAmount.divide(contractMonths,5,BigDecimal.ROUND_HALF_UP);    // 初始本金
        BigDecimal monthPayAmountSum =contract.getContractMonthRepayAmount();                             // 应还月还款额
        BigDecimal curPrincipal = contract.getContractAmount();                                           // 当期本金
        BigDecimal feeMonthRate = ctrFee.getFeeMonthRate().divide(new BigDecimal(100));                   // 月利率
        BigDecimal monthInterestBackshould = curPrincipal.multiply(feeMonthRate); // 月利息
        BigDecimal monthPayAmount = monthPayAmountSum.subtract(monthInterestBackshould); // 月还本金
        BigDecimal huijinFactor = new BigDecimal(ContractConstant.HUIJIN_FACTOR);
        BigDecimal huiminFactor = new BigDecimal(ContractConstant.HUIMIN_FACTOR);
        // 当前综合费
        BigDecimal currTotalFee;
        // 惠民费用
        BigDecimal huiMinFee;
        // 汇金费用 
        BigDecimal huiJinFee;
        
        float yearsFloat = loopCount/ContractConstant.DIVIDE;
        Integer initCalcuYear = Math.round(yearsFloat)+ContractConstant.ADD_FACTOR;
        BigDecimal initPenalty = new BigDecimal(ContractConstant.MUTI_FACTOR).multiply(contractAmount);   // 提前结清金额 初始金额
        BigDecimal penalty = null; 
        Date contractReplayDay = contract.getContractReplayDay();     // 还款日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(contractReplayDay);
        boolean firstOper = true;
        for(int i=0 ; i<loopCount;i++){
            repayPlan = new RepayPlan();  
            repayPlan.setCurPrincipal(curPrincipal);
            repayPlan.setMonthInitPayAmount(monthInitPayAmount);
            repayPlan.setMonthInterestBackshould(monthInterestBackshould);
            repayPlan.setMonthPayAmount(monthPayAmount);
            repayPlan.setMonthPayAmountSum(monthPayAmountSum);
            repayPlan.setMonthPayDay(contractReplayDay);
            repayPlan.setPayBackCurrentMonth(i+1);
            
            currTotalFee = curPrincipal.subtract(monthInitPayAmount);
            huiMinFee = currTotalFee.multiply(huiminFactor);
            huiJinFee = currTotalFee.multiply(huijinFactor);
            repayPlan.setCurrTotalFee(currTotalFee);
            repayPlan.setHuiJinFee(huiJinFee);
            repayPlan.setHuiMinFee(huiMinFee);
            
            penalty = initPenalty.multiply(new BigDecimal(Math.max(0, initCalcuYear-(i+1))));
            repayPlan.setPenalty(penalty);
            
            repayPlans.add(repayPlan);
            // 计算下一期的当前本金、月利息、月还本金
            curPrincipal = curPrincipal.subtract(monthPayAmount);
            monthInterestBackshould = curPrincipal.multiply(feeMonthRate);
            monthPayAmount = monthPayAmountSum.subtract(monthInterestBackshould);
            // 二月的还款日计算比较特殊，所以要单独校验,二月之后的数据要恢复到之前的状态
            if(firstOper && calendar.get(Calendar.MONTH)==FEBRUARY){
                Date contractDueDay = contract.getContractDueDay();
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.setTime(contractDueDay);
                GlBill glBill = new GlBill();
                glBill.setSignDay(tempCalendar.get(Calendar.DAY_OF_MONTH));
                GlBill tagGlBill = glBillDao.findBySignDay(glBill);
                Integer billDay = tagGlBill.getBillDay();
                if(calendar.get(Calendar.DAY_OF_MONTH)!= billDay){
                    calendar.add(Calendar.MONTH,ADD_ONE_MONTH);
                    calendar.set(Calendar.DAY_OF_MONTH, billDay);
                }else{
                    calendar.add(Calendar.MONTH,ADD_ONE_MONTH);  
                }
                firstOper = false;  
            }else{
               calendar.add(Calendar.MONTH,ADD_ONE_MONTH);
            }
            contractReplayDay = calendar.getTime();
        }
        BigDecimal earlySettlement; // 修正结清金额
        BigDecimal fixBackFee = new BigDecimal(0);
        BigDecimal settlementOnce; // 不算违约金、罚息和期供的当期一次性结清金额
        for(int i=loopCount-1; i>=0;i--){
            repayPlan =  repayPlans.get(i);
            curPrincipal = repayPlan.getCurPrincipal();
            monthInterestBackshould = repayPlan.getMonthInterestBackshould();
            penalty = repayPlan.getPenalty();
            huiMinFee = repayPlan.getHuiMinFee();
            huiJinFee = repayPlan.getHuiJinFee();
            repayPlan.setFixBackFee(fixBackFee);
            earlySettlement = curPrincipal.add(monthInterestBackshould).add(penalty).subtract(fixBackFee);
            settlementOnce = monthPayAmountSum.multiply(new BigDecimal(i)).add(earlySettlement);
            fixBackFee = huiMinFee.add(huiJinFee);
            repayPlan.setEarlySettlement(earlySettlement);
            repayPlan.setSettlementOnce(settlementOnce);
            repayPlans.set(i, repayPlan);
        }
        return repayPlans;
    }
}
