/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsReckonFeeNew.java
 * @Create By 张灏
 * @Create In 2016年3月23日 下午6:07:38
 */
package com.creditharmony.loan.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.RateInfoDao;
import com.creditharmony.loan.borrow.contract.entity.CoeffReferJYJ;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractFeeTemp;
import com.creditharmony.loan.borrow.contract.entity.ContractTemp;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.FeeInfoEx;
import com.creditharmony.loan.borrow.contract.util.ProductUtil;
import com.creditharmony.loan.common.dao.GlBillDao;
import com.creditharmony.loan.common.dao.GlBillHzDao;
import com.creditharmony.loan.common.dao.LoanPrdMngDao;
import com.creditharmony.loan.common.entity.CoeffRefer;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.GlBillHz;
import com.creditharmony.loan.common.entity.RepayPlanNew;

/**
 * @Class Name ReckonFeeNew
 * @author 张灏
 * @Create In 2016年3月23日o
 */
public class ReckonFeeNew {
    private static final Integer FEBRUARY = 2;      // 二月
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
     * @throws Exception 
     */
    public static void ReckonFeeOneStep(Contract baseFee,ContractFee fee,Contract con,List<CoeffReferJYJ> coeffReferJYJList) throws Exception{
        BigDecimal loanLimit = baseFee.getContractMonths();     // 借款期限
        BigDecimal auditMoney = baseFee.getAuditAmount();        // 批复金额
        BigDecimal petitionFee = fee.getFeePetition();           // 外访费
        Double feeAllRaioWithPercent =0.0;
        BigDecimal feeAllRaio=new BigDecimal("0.0");
        if(fee.getFeeAllRaio()!=null){
        	 feeAllRaioWithPercent = fee.getFeeAllRaio().doubleValue();  //带百分号的费率
        	 feeAllRaio = fee.getFeeAllRaio().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);     
        }       
           // 总费率
        Double preServiceFeeRate = null;
        CoeffRefer result = null;
        Double stateServiceFeeRate =null;//分期服务费率
            if(!con.getProductType().equals(ProductUtil.PRODUCT_JYJ.getCode()) && !con.getProductType().equals(ProductUtil.PRODUCT_NXD.getCode())){
            	LoanPrdMngDao loanPrdMngDao = SpringContextHolder.getBean(LoanPrdMngDao.class);
                RateInfoDao rateInfoDao = SpringContextHolder.getBean(RateInfoDao.class);
                CoeffRefer coeffRefer = new CoeffRefer();
                coeffRefer.setMonths(loanLimit.intValue());
	            Date consultDate = LoanConsultDateUtils.findTimeByLoanCode(baseFee.getLoanCode());
	    		Date onLineDate = DateUtils.convertStringToDate(SystemConfigConstant.LOAN_ONLINE_DATE);
	    		Date FLLineDate = DateUtils.convertStringToDate(SystemConfigConstant.LOAN_FL_DATE);
	    		if(DateUtils.dateAfter(consultDate,onLineDate)){//根据咨询时间来判断
	    			List<Map<String,String>> list = rateInfoDao.getRiskLevel(baseFee.getLoanCode());
	                String riskLevel = "";
	                if(ObjectHelper.isNotEmpty(list) && !ObjectHelper.isEmpty(list.get(0))){
	                    if(list.get(0).get("reconsiderrisklevel")!=null&&!"".equals(list.get(0).get("reconsiderrisklevel"))){
	                        riskLevel = list.get(0).get("reconsiderrisklevel");
	                    }else{
	                        riskLevel = list.get(0).get("verifyrisklevel");
	                    }
	                    coeffRefer.setRiskLevel(riskLevel);
	                }
	    		}else{
	    			coeffRefer.setProductUsableRate(feeAllRaioWithPercent);
	    		}
	    		if(DateUtils.dateAfter(consultDate,FLLineDate)){//根据咨询时间来判断
	    			coeffRefer.setSystemFlag("0");
	    		}else{
	    			if(coeffRefer.getRiskLevel().equals("A1") || coeffRefer.getRiskLevel().equals("A2")||
	    					coeffRefer.getRiskLevel().equals("B1")||coeffRefer.getRiskLevel().equals("C1")||
	    					coeffRefer.getRiskLevel().equals("C2")||coeffRefer.getRiskLevel().equals("D1")||
	    					coeffRefer.getRiskLevel().equals("D2")||coeffRefer.getRiskLevel().equals("E1")||
							coeffRefer.getRiskLevel().equals("F1")){
	    				coeffRefer.setSystemFlag("2");
	    			}else{
	    				coeffRefer.setSystemFlag("0");
	    			}    			
	    		}
	            result = loanPrdMngDao.getCoeffReferByParam(coeffRefer);
	            preServiceFeeRate= result.getComprehensiveFeeCoeff();// 前期综合服务费率
	            feeAllRaio = new BigDecimal(result.getProductUsableRate());
	            stateServiceFeeRate = result.getMonthGatherRation(); // 分期服务费率
          }else{
        	  preServiceFeeRate= coeffReferJYJList.get(0).getComprehensiveFeeCoeff();// 前期综合服务费率
	          feeAllRaio = new BigDecimal(coeffReferJYJList.get(0).getProductRate());  //总费率
	          stateServiceFeeRate = coeffReferJYJList.get(0).getMonthGatherRate(); // 分期服务费率
          }
      
            BigDecimal monthFee=new BigDecimal("0.0");//月利息
            BigDecimal loanRate =fee.getFeeMonthRate().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);         // 借款利率
            if(con.getProductType().equals(ProductUtil.PRODUCT_NXD.getCode())){//农信贷
            	
            
            	monthFee = 	baseFee.getAuditAmount().multiply(new BigDecimal(coeffReferJYJList.get(0).getRate())).divide(new BigDecimal(ContractConstant.PERCENT),2,BigDecimal.ROUND_HALF_UP);		//月利息
            	fee.setMonthFee(monthFee);
            }
       
        BigDecimal _temp1 = (loanRate.add(new BigDecimal(1))).pow(loanLimit.intValue());//（1+月利率）^借款期限
        BigDecimal _temp2 = _temp1.subtract(new BigDecimal(1));
        BigDecimal preServiceFee = auditMoney.multiply(
        		 new BigDecimal(preServiceFeeRate).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP);  // 前期综合服务费
        BigDecimal contractAmount = auditMoney.add(preServiceFee);  //  合同金额（批复金额  + 前期综合服务费）
        // 分期服务费(合同金额*分期服务费率)
        BigDecimal stageServiceFee = contractAmount.multiply(new BigDecimal(stateServiceFeeRate).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP); 
        // 催收服务费（合同金额*1%）
        BigDecimal feeUrgedService = contractAmount.multiply(new BigDecimal(ContractConstant.FEEURGEDSERVICE_RATE)).setScale(2, BigDecimal.ROUND_HALF_UP); 
        // 月还本息金额
        BigDecimal auditMonthPaymoney = contractAmount.multiply(loanRate).multiply(_temp1).divide(_temp2,2,BigDecimal.ROUND_HALF_UP); 
        // 月还款总额
        BigDecimal monthPayTotalAmount = auditMonthPaymoney.add(stageServiceFee);
        BigDecimal contractExpectAmount = monthPayTotalAmount.multiply(loanLimit).setScale(2, BigDecimal.ROUND_HALF_UP); // 预计还款总额
        if(con.getProductType().equals(ProductUtil.PRODUCT_NXD.getCode())){//农信贷
        	monthPayTotalAmount = monthFee.add(stageServiceFee);
        	auditMonthPaymoney = new BigDecimal(0.00);
        	contractExpectAmount = monthPayTotalAmount.multiply(loanLimit).add(auditMoney).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        BigDecimal feePaymentAmount =contractAmount;
        if(petitionFee!=null){
            feePaymentAmount = feePaymentAmount.subtract(petitionFee); 
        }
        feePaymentAmount=feePaymentAmount.subtract(preServiceFee);
        // 咨询费
        BigDecimal feeConsult = preServiceFee.multiply(new BigDecimal(ContractConstant.FEECONSULT_RATE)).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 审核费
        BigDecimal feeAuditAmount = preServiceFee.multiply(new BigDecimal(ContractConstant.FEEAUDITAMOUNT_RATE)).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 信息服务费
        BigDecimal feeInfoService = preServiceFee.multiply(new BigDecimal(ContractConstant.FEEINFOSERVICE_RATE)).setScale(2, BigDecimal.ROUND_HALF_UP); 
        // 居间服务费
        BigDecimal feeService = preServiceFee.subtract(feeConsult).subtract(feeAuditAmount).subtract(feeInfoService); 
        // 分期咨询费
        BigDecimal stageFeeConsult = stageServiceFee.multiply(new BigDecimal(0.5)).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 分期居间服务费
        BigDecimal stageFeeService = stageServiceFee.subtract(stageFeeConsult);
        
        
        fee.setFeePaymentAmount(feePaymentAmount);
        fee.setFeeService(feeService);
        fee.setFeeUrgedService(feeUrgedService);
        fee.setFeeAllRaio(feeAllRaio);
        fee.setFeeCount(preServiceFee);
        fee.setFeeConsult(feeConsult);
        fee.setFeeAuditAmount(feeAuditAmount);
        fee.setFeeInfoService(feeInfoService);
        fee.setMonthFeeService(stageServiceFee);
        fee.setMonthFeeConsult(stageFeeConsult);
        fee.setMonthMidFeeService(stageFeeService);
        fee.setMonthRateService(new BigDecimal(stateServiceFeeRate));
        fee.setComprehensiveServiceRate(new BigDecimal(preServiceFeeRate));
        baseFee.setContractAmount(contractAmount);
        baseFee.setContractMonthRepayAmount(auditMonthPaymoney);
        baseFee.setMonthPayTotalAmount(monthPayTotalAmount);
        baseFee.setContractExpectAmount(contractExpectAmount);
        return;
    }
    
    /**
     *放款金额计算 
     *计算实际放款金额(feePaymentAmount)、月还本息金额(auditMonthPaymoney)、预计还款总额 (contractExpectCount)
     *
     *@version 1.0
     *@author 张灏 
     *@param baseFee
     *传参：借款期限(loanApplyMonth)、批复金额(auditMoney)、信访费(feePetitionFee)、加急费(feeExpeditedFee)、总费率(feeAllRaio)  
     * 信访费、加急费用于计算实际放款金额 ,只要任何一个为NULL时,都不计算实际放款金额
     * @throws Exception 
     */
    public static void ReckonFeeOneStepTemp(ContractTemp baseFee,ContractFeeTemp fee,Contract con,List<CoeffReferJYJ> coeffReferJYJList) throws Exception{
        BigDecimal loanLimit = baseFee.getContractMonths();     // 借款期限
        BigDecimal auditMoney = baseFee.getAuditAmount();        // 批复金额
        BigDecimal petitionFee = fee.getFeePetition();           // 外访费
        Double feeAllRaioWithPercent = fee.getFeeAllRaio().doubleValue();  //带百分号的费率
        BigDecimal feeAllRaio = fee.getFeeAllRaio().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);        // 总费率
        Double preServiceFeeRate = null;
        CoeffRefer result = null;
        Double stateServiceFeeRate=null;
        try{
        	 if(!con.getProductType().equals(ProductUtil.PRODUCT_JYJ.getCode())){
	            LoanPrdMngDao loanPrdMngDao = SpringContextHolder.getBean(LoanPrdMngDao.class);
	            RateInfoDao rateInfoDao = SpringContextHolder.getBean(RateInfoDao.class);
	            CoeffRefer coeffRefer = new CoeffRefer();
	            coeffRefer.setMonths(loanLimit.intValue());
	            Date consultDate = LoanConsultDateUtils.findTimeByLoanCode(baseFee.getLoanCode());
	    		Date onLineDate = DateUtils.convertStringToDate(SystemConfigConstant.LOAN_ONLINE_DATE);
	    		Date FLLineDate = DateUtils.convertStringToDate(SystemConfigConstant.LOAN_FL_DATE);
	    		if(DateUtils.dateAfter(consultDate,onLineDate)){//根据咨询时间来判断
	    			List<Map<String,String>> list = rateInfoDao.getRiskLevel(baseFee.getLoanCode());
	                String riskLevel = "";
	                if(ObjectHelper.isNotEmpty(list) && !ObjectHelper.isEmpty(list.get(0))){
	                    if(list.get(0).get("reconsiderrisklevel")!=null&&!"".equals(list.get(0).get("reconsiderrisklevel"))){
	                        riskLevel = list.get(0).get("reconsiderrisklevel");
	                    }else{
	                        riskLevel = list.get(0).get("verifyrisklevel");
	                    }
	                    coeffRefer.setRiskLevel(riskLevel);
	                }
	    		}else{
	    			coeffRefer.setProductUsableRate(feeAllRaioWithPercent);
	    		}
	    		if(DateUtils.dateAfter(consultDate,FLLineDate)){//根据咨询时间来判断
	    			coeffRefer.setSystemFlag("0");
	    		}else{
	    			if(coeffRefer.getRiskLevel().equals("A1") || coeffRefer.getRiskLevel().equals("A2")||
	    					coeffRefer.getRiskLevel().equals("B1")||coeffRefer.getRiskLevel().equals("C1")||
	    					coeffRefer.getRiskLevel().equals("C2")||coeffRefer.getRiskLevel().equals("D1")||
	    					coeffRefer.getRiskLevel().equals("D2")||coeffRefer.getRiskLevel().equals("E1")||
							coeffRefer.getRiskLevel().equals("F1")){
	    				coeffRefer.setSystemFlag("2");
	    			}else{
	    				coeffRefer.setSystemFlag("0");
	    			}    			
	    		}
	            result = loanPrdMngDao.getCoeffReferByParam(coeffRefer);
	            preServiceFeeRate= result.getComprehensiveFeeCoeff();// 前期综合服务费率
	            feeAllRaio = new BigDecimal(result.getProductUsableRate());
	            stateServiceFeeRate = result.getMonthGatherRation(); // 分期服务费率
        	 }else{
        		 preServiceFeeRate= coeffReferJYJList.get(0).getComprehensiveFeeCoeff();// 前期综合服务费率
	   	         feeAllRaio = new BigDecimal(coeffReferJYJList.get(0).getProductRate());  //总费率
	   	         stateServiceFeeRate = coeffReferJYJList.get(0).getMonthGatherRate(); // 分期服务费率
        	 }
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("费率错误！");
        }
        BigDecimal loanRate =fee.getFeeMonthRate().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);         // 借款利率
        BigDecimal _temp1 = (loanRate.add(new BigDecimal(1))).pow(loanLimit.intValue());//（1+月利率）^借款期限
        BigDecimal _temp2 = _temp1.subtract(new BigDecimal(1));
        BigDecimal preServiceFee = auditMoney.multiply(
        		 new BigDecimal(preServiceFeeRate).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP);  // 前期综合服务费
        BigDecimal contractAmount = auditMoney.add(preServiceFee);  //  合同金额（批复金额  + 前期综合服务费）
        // 分期服务费(合同金额*分期服务费率)
        BigDecimal stageServiceFee = contractAmount.multiply(new BigDecimal(stateServiceFeeRate).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP); 
        // 催收服务费（合同金额*1%）
        BigDecimal feeUrgedService = contractAmount.multiply(new BigDecimal(ContractConstant.FEEURGEDSERVICE_RATE)).setScale(2, BigDecimal.ROUND_HALF_UP); 
        // 月还本息金额
        BigDecimal auditMonthPaymoney = contractAmount.multiply(loanRate).multiply(_temp1).divide(_temp2,2,BigDecimal.ROUND_HALF_UP); 
        // 月还款总额
        BigDecimal monthPayTotalAmount = auditMonthPaymoney.add(stageServiceFee);
        BigDecimal contractExpectAmount = monthPayTotalAmount.multiply(loanLimit).setScale(2, BigDecimal.ROUND_HALF_UP); // 预计还款总额
        BigDecimal feePaymentAmount =contractAmount;
        if(petitionFee!=null){
            feePaymentAmount = feePaymentAmount.subtract(petitionFee); 
        }
        feePaymentAmount=feePaymentAmount.subtract(preServiceFee);
        // 咨询费
        BigDecimal feeConsult = preServiceFee.multiply(new BigDecimal(ContractConstant.FEECONSULT_RATE)).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 审核费
        BigDecimal feeAuditAmount = preServiceFee.multiply(new BigDecimal(ContractConstant.FEEAUDITAMOUNT_RATE)).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 信息服务费
        BigDecimal feeInfoService = preServiceFee.multiply(new BigDecimal(ContractConstant.FEEINFOSERVICE_RATE)).setScale(2, BigDecimal.ROUND_HALF_UP); 
        // 居间服务费
        BigDecimal feeService = preServiceFee.subtract(feeConsult).subtract(feeAuditAmount).subtract(feeInfoService); 
        // 分期咨询费
        BigDecimal stageFeeConsult = stageServiceFee.multiply(new BigDecimal(0.5)).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 分期居间服务费
        BigDecimal stageFeeService = stageServiceFee.subtract(stageFeeConsult);
        
        
        fee.setFeePaymentAmount(feePaymentAmount);
        fee.setFeeService(feeService);
        fee.setFeeUrgedService(feeUrgedService);
        fee.setFeeAllRaio(feeAllRaio);
        fee.setFeeCount(preServiceFee);
        fee.setFeeConsult(feeConsult);
        fee.setFeeAuditAmount(feeAuditAmount);
        fee.setFeeInfoService(feeInfoService);
        fee.setMonthFeeService(stageServiceFee);
        fee.setMonthFeeConsult(stageFeeConsult);
        fee.setMonthMidFeeService(stageFeeService);
        fee.setMonthRateService(new BigDecimal(stateServiceFeeRate));
        fee.setComprehensiveServiceRate(new BigDecimal(preServiceFeeRate));
        baseFee.setContractAmount(contractAmount);
        baseFee.setContractMonthRepayAmount(auditMonthPaymoney);
        baseFee.setMonthPayTotalAmount(monthPayTotalAmount);
        baseFee.setContractExpectAmount(contractExpectAmount);
        return;
    }
   
    public static List<RepayPlanNew> createRepayPlanNew(Contract contract,ContractFee ctrFee,GlBillDao glBillDao){
    	List<RepayPlanNew> repayPlans = new ArrayList<RepayPlanNew>();
    	RepayPlanNew repayPlanNew = null;
        BigDecimal contractAmount = contract.getContractAmount();  // 合同金额
        BigDecimal contractMonths = contract.getContractMonths();  // 批借期限
        BigDecimal monthRate = ctrFee.getFeeMonthRate().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);           // 月利率
        Integer loopCount = contractMonths.intValue();
        BigDecimal curPrincipal = contractAmount;                  // 当期初始本金
        // 月还本息金额
        BigDecimal auditMonthPaymoney = contract.getContractMonthRepayAmount();
        // 分期服务费
        BigDecimal stageServiceFee = ctrFee.getMonthFeeService();
        // 分期咨询费
        BigDecimal stageFeeConsult = ctrFee.getMonthFeeConsult();
        // 分期居间服务费
        BigDecimal stageFeeService = ctrFee.getMonthMidFeeService();
        // 应还款总额
        BigDecimal monthPayTotalAmount = contract.getMonthPayTotalAmount();
        // 月还利息
        BigDecimal monthInterest = null;    
        // 月还本金
        BigDecimal monthPayAmount = null;
        // 剩余本金
        BigDecimal residualPrincipal = null;
        // 一次性还款违约罚金
        BigDecimal oncePayPenalty = null;
        // 违约系数
        Double defaultFactor = null;
        Date contractReplayDay = contract.getContractReplayDay();     // 还款日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(contractReplayDay);
        for(int i=0;i<loopCount;i++){
        	repayPlanNew = new RepayPlanNew();
        	// 初始本金
        	repayPlanNew.setCurPrincipal(curPrincipal);
        	// 月还本息金额
        	repayPlanNew.setMonthPayAmountSum(auditMonthPaymoney);
        	// 月还总额
        	repayPlanNew.setMonthPaySum(monthPayTotalAmount);
        	// 分期服务费
        	repayPlanNew.setStageServiceFee(stageServiceFee);
        	// 还款日
        	repayPlanNew.setMonthPayDay(contractReplayDay);
        	repayPlanNew.setPayBackCurrentMonth(i+1);
        	if(i<loopCount-1){
        		monthInterest = curPrincipal.multiply(monthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        		monthPayAmount = auditMonthPaymoney.subtract(monthInterest);
        	}else if(i==loopCount-1){
        		monthPayAmount = curPrincipal;
        		monthInterest = auditMonthPaymoney.subtract(monthPayAmount);
        	}
        	// 剩余本金
        	residualPrincipal = curPrincipal.subtract(monthPayAmount); 
        	// 月还利息
        	repayPlanNew.setMonthInterestBackshould(monthInterest);
        	// 月还本金
        	repayPlanNew.setMonthPayAmount(monthPayAmount);
        	// 分期咨询费
        	repayPlanNew.setStageFeeConsult(stageFeeConsult);
        	// 分期居间服务费
        	repayPlanNew.setStageFeeService(stageFeeService);
        	// 违约系数
        	defaultFactor = FeeRateUtils.getDefaultFactor(i+1);
        	oncePayPenalty = new BigDecimal(defaultFactor).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP)
        			.multiply(monthPayTotalAmount).multiply(new BigDecimal(loopCount-i-1)).setScale(2, BigDecimal.ROUND_HALF_UP);
        	// 一次性还款违约罚金
        	repayPlanNew.setOncePayPenalty(oncePayPenalty);
        	// 剩余本金
        	repayPlanNew.setResidualPrincipal(residualPrincipal);
        	// 一次性还款总额
        	repayPlanNew.setOncePaySum(oncePayPenalty.add(residualPrincipal).add(monthPayTotalAmount));
        
        	curPrincipal = residualPrincipal;   // 设置下一期初始金额
        	// 二月的还款日计算比较特殊，所以要单独校验,二月之后的数据要恢复到之前的状态
            if(calendar.get(Calendar.MONTH)+1==FEBRUARY){
                Date contractDueDay = contract.getContractDueDay();
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.setTime(contractDueDay);
                GlBill glBill = new GlBill();
                glBill.setSignDay(tempCalendar.get(Calendar.DAY_OF_MONTH));
                GlBill tagGlBill = glBillDao.findBySignDay(glBill);
                Integer billDay = tagGlBill.getBillDay();
                // 还款日为30号，2月份做特殊处理
                if(calendar.get(Calendar.DAY_OF_MONTH)!= billDay){
                    calendar.add(Calendar.MONTH,ADD_ONE_MONTH);
                    calendar.set(Calendar.DAY_OF_MONTH, billDay);
                }else{
                    calendar.add(Calendar.MONTH,ADD_ONE_MONTH);  
                }
            }else{
                calendar.add(Calendar.MONTH,ADD_ONE_MONTH);
            }
            contractReplayDay = calendar.getTime();
            repayPlans.add(repayPlanNew);
        }
    	return repayPlans;
    }
    
    /**
     * 农信贷还款计算
    ·* 2017年5月19日
    ·* by Huowenlong
     * @param contract
     * @param ctrFee
     * 
     * @return
     */
    public static List<RepayPlanNew> createNXDRepayPlanNew(Contract contract,ContractFee ctrFee){
    	List<RepayPlanNew> repayPlans = new ArrayList<RepayPlanNew>();
    	RepayPlanNew repayPlanNew = null;
        BigDecimal contractAmount = contract.getContractAmount();  // 合同金额
        BigDecimal contractMonths = contract.getContractMonths();  // 批借期限
        BigDecimal monthRate = ctrFee.getFeeMonthRate().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);           // 月利率
        Integer loopCount = contractMonths.intValue();
        BigDecimal curPrincipal = contractAmount;                  // 当期初始本金
        // 月还本息金额
        BigDecimal auditMonthPaymoney = new BigDecimal(0);
        // 分期服务费
        BigDecimal stageServiceFee = ctrFee.getMonthFeeService().setScale(2, BigDecimal.ROUND_HALF_UP);
        // 分期咨询费
        BigDecimal stageFeeConsult = new BigDecimal(0);
        // 分期居间服务费
        BigDecimal stageFeeService = new BigDecimal(0);
        // 月应还款总额
        BigDecimal monthPayTotalAmount = new BigDecimal(0);
        // 月还利息
        BigDecimal monthInterest = null;    
        // 月还本金
        BigDecimal monthPayAmount = new BigDecimal(0);
        // 剩余本金
        BigDecimal residualPrincipal = new BigDecimal(0);
        // 一次性还款违约罚金
        BigDecimal oncePayPenalty = null;
        // 违约系数
        Double defaultFactor = null;
        Date contractReplayDay = contract.getContractReplayDay();     // 还款日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(contractReplayDay);
        if(calendar.get(Calendar.DATE) == 31){
        	calendar.set(Calendar.DATE, 30);
        	contractReplayDay = calendar.getTime();
        }
        for(int i=0;i<loopCount;i++){
        	repayPlanNew = new RepayPlanNew();
        	// 初始本金
        	repayPlanNew.setCurPrincipal(curPrincipal);
        	// 月还本息金额
        	repayPlanNew.setMonthPayAmountSum(auditMonthPaymoney);
        	// 分期服务费
        	repayPlanNew.setStageServiceFee(stageServiceFee);
        	// 还款日
        	repayPlanNew.setMonthPayDay(contractReplayDay);
        	repayPlanNew.setPayBackCurrentMonth(i+1);
        	monthInterest = curPrincipal.multiply(monthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        	if(i<loopCount-1){
        		monthPayTotalAmount = monthInterest.add(stageServiceFee);
        	}else if(i==loopCount-1){
        		monthPayTotalAmount = curPrincipal.add(monthInterest).add(stageServiceFee);
        		monthPayAmount = curPrincipal;
        	}
        	// 月还总额
        	repayPlanNew.setMonthPaySum(monthPayTotalAmount);
        	// 剩余本金
        	residualPrincipal = curPrincipal; 
        	// 月还利息
        	repayPlanNew.setMonthInterestBackshould(monthInterest);
        	// 月还本金
        	repayPlanNew.setMonthPayAmount(monthPayAmount);
        	
        	// 违约系数
        	defaultFactor = ContractConstant.DEFAULTFACTOR;
        	oncePayPenalty = new BigDecimal(defaultFactor).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP)
        			.multiply(curPrincipal).setScale(2, BigDecimal.ROUND_HALF_UP);
        	// 一次性还款违约罚金
        	repayPlanNew.setOncePayPenalty(oncePayPenalty);
        	// 剩余本金
        	repayPlanNew.setResidualPrincipal(residualPrincipal);
        	// 一次性还款总额  （月利息+分期服务费）/30天*当期实际借款使用天数 + 违约罚金 + 借款本金
        	BigDecimal passDay = new BigDecimal(1);//new BigDecimal(DateUtils.getDistanceOfTwoDate(new Date(),contractReplayDay));
        	BigDecimal temp = monthInterest.add(stageServiceFee);
        	BigDecimal oncePaySum = temp.divide(new BigDecimal(30),2,BigDecimal.ROUND_HALF_UP).multiply(passDay).add(oncePayPenalty).add(curPrincipal).setScale(2, BigDecimal.ROUND_HALF_UP);
        	repayPlanNew.setOncePaySum(new BigDecimal(0));
        	Calendar currentMonth = Calendar.getInstance();
        	currentMonth.setTime(contractReplayDay);
        	int currentReplayDay = currentMonth.get(Calendar.DATE);
    		
        	Calendar nextMonth = Calendar.getInstance();
    		nextMonth.setTime(contractReplayDay);
    		nextMonth.set(Calendar.MONTH, currentMonth.get(Calendar.MONTH)+1 );
    		int nextMonthMaxDay = nextMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
    		if(currentReplayDay > nextMonthMaxDay){
    			contractReplayDay = nextMonth.getTime();
    		}else{
    			nextMonth.set(Calendar.DATE, currentReplayDay);
    			contractReplayDay = nextMonth.getTime();
    		}
            repayPlans.add(repayPlanNew);
        }
    	return repayPlans;
    }
    /**
     * 实际放款日期计算
     * @param contract
     * @param ctrFee
     * @param glBillHzDao
     * @return
     */
    public static List<RepayPlanNew> createRepayPlanNewHz(Contract contract,ContractFee ctrFee,GlBillHzDao glBillHzDao,Date lendingTime){
    	List<RepayPlanNew> repayPlans = new ArrayList<RepayPlanNew>();
    	RepayPlanNew repayPlanNew = null;
        BigDecimal contractAmount = contract.getContractAmount();  // 合同金额
        BigDecimal contractMonths = contract.getContractMonths();  // 批借期限
        BigDecimal monthRate = ctrFee.getFeeMonthRate().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);           // 月利率
        Integer loopCount = contractMonths.intValue();
        BigDecimal curPrincipal = contractAmount;                  // 当期初始本金
        // 月还本息金额
        BigDecimal auditMonthPaymoney = contract.getContractMonthRepayAmount();
        // 分期服务费
        BigDecimal stageServiceFee = ctrFee.getMonthFeeService();
        // 分期咨询费
        BigDecimal stageFeeConsult = ctrFee.getMonthFeeConsult();
        // 分期居间服务费
        BigDecimal stageFeeService = ctrFee.getMonthMidFeeService();
        // 应还款总额
        BigDecimal monthPayTotalAmount = contract.getMonthPayTotalAmount();
        // 月还利息
        BigDecimal monthInterest = null;    
        // 月还本金
        BigDecimal monthPayAmount = null;
        // 剩余本金
        BigDecimal residualPrincipal = null;
        // 一次性还款违约罚金
        BigDecimal oncePayPenalty = null;
        // 违约系数
        Double defaultFactor = null;
        Date contractReplayDay = contract.getContractReplayDay();     // 还款日
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(contractReplayDay);
        for(int i=0;i<loopCount;i++){
        	repayPlanNew = new RepayPlanNew();
        	// 初始本金
        	repayPlanNew.setCurPrincipal(curPrincipal);
        	// 月还本息金额
        	repayPlanNew.setMonthPayAmountSum(auditMonthPaymoney);
        	// 月还总额
        	repayPlanNew.setMonthPaySum(monthPayTotalAmount);
        	// 分期服务费
        	repayPlanNew.setStageServiceFee(stageServiceFee);
        	// 还款日
        	repayPlanNew.setMonthPayDay(contractReplayDay);
        	repayPlanNew.setPayBackCurrentMonth(i+1);
        	if(i<loopCount-1){
        		monthInterest = curPrincipal.multiply(monthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        		monthPayAmount = auditMonthPaymoney.subtract(monthInterest);
        	}else if(i==loopCount-1){
        		monthPayAmount = curPrincipal;
        		monthInterest = auditMonthPaymoney.subtract(monthPayAmount);
        	}
        	// 剩余本金
        	residualPrincipal = curPrincipal.subtract(monthPayAmount); 
        	// 月还利息
        	repayPlanNew.setMonthInterestBackshould(monthInterest);
        	// 月还本金
        	repayPlanNew.setMonthPayAmount(monthPayAmount);
        	// 分期咨询费
        	repayPlanNew.setStageFeeConsult(stageFeeConsult);
        	// 分期居间服务费
        	repayPlanNew.setStageFeeService(stageFeeService);
        	// 违约系数
        	defaultFactor = FeeRateUtils.getDefaultFactor(i+1);
        	oncePayPenalty = new BigDecimal(defaultFactor).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP)
        			.multiply(monthPayTotalAmount).multiply(new BigDecimal(loopCount-i-1)).setScale(2, BigDecimal.ROUND_HALF_UP);
        	// 一次性还款违约罚金
        	repayPlanNew.setOncePayPenalty(oncePayPenalty);
        	// 剩余本金
        	repayPlanNew.setResidualPrincipal(residualPrincipal);
        	// 一次性还款总额
        	repayPlanNew.setOncePaySum(oncePayPenalty.add(residualPrincipal).add(monthPayTotalAmount));
        
        	curPrincipal = residualPrincipal;   // 设置下一期初始金额
        	// 二月的还款日计算比较特殊，所以要单独校验,二月之后的数据要恢复到之前的状态
            if(calendar.get(Calendar.MONTH)+1==FEBRUARY){
//                Date contractfactDay = contract.getContractFactDay();
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.setTime(lendingTime);
                GlBillHz glBillHz = new GlBillHz();
                glBillHz.setSignDay(tempCalendar.get(Calendar.DAY_OF_MONTH));
                GlBillHz tagGlBill = glBillHzDao.findBySignDay(glBillHz);
                Integer billDay = tagGlBill.getBillDay();
                // 还款日为30号，2月份做特殊处理
                if(calendar.get(Calendar.DAY_OF_MONTH)!= billDay){
                    calendar.add(Calendar.MONTH,ADD_ONE_MONTH);
                    calendar.set(Calendar.DAY_OF_MONTH, billDay);
                }else{
                    calendar.add(Calendar.MONTH,ADD_ONE_MONTH);  
                }
            }else{
                calendar.add(Calendar.MONTH,ADD_ONE_MONTH);
            }
            contractReplayDay = calendar.getTime();
            repayPlans.add(repayPlanNew);
        }
    	return repayPlans;
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
               tempFee = contract.getMonthPayTotalAmount();
               if(tempFee!=null){
                   feeInfoEx.setMonthPayTotalAmount(ReckonFee.DecimalFormat(tempFee, format));
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
        
        if(ContractConstant.ZERO < distance &&  distance <=ContractConstant.FIFTY_KILOMETERS){
         
            petitionFee = ContractConstant.TWO_HUNDRED;
        
        }else if(ContractConstant.FIFTY_KILOMETERS < distance && distance <=ContractConstant.ONE_HUNDRED_KILOMETERS){
        
            petitionFee = ContractConstant.THREE_HUNDRED;
       
        }else if(ContractConstant.ONE_HUNDRED_KILOMETERS < distance && distance <=ContractConstant.ONE_HUNDRED_FIFTY_KILOMETERS){
        
            petitionFee = ContractConstant.FOUR_HUNDRED;
       
        }else if(ContractConstant.ONE_HUNDRED_FIFTY_KILOMETERS < distance){
        
            petitionFee = ContractConstant.FIVE_HUNDRED;
        }
        return petitionFee;
    }
    /**
     *加急费 
     * 
     */
    public static BigDecimal urgeFee(boolean isUrge,Contract contract,ContractFee fee){
    	 BigDecimal feeExpedited = new BigDecimal(0.00);
        if(isUrge){
            BigDecimal contractAmount = contract.getContractAmount();
			feeExpedited = contractAmount.multiply(
					new BigDecimal(Double.toString(0.01))).setScale(2,
					BigDecimal.ROUND_HALF_UP);
        }
        fee.setFeeExpedited(feeExpedited);
        return feeExpedited;
    }
    
    /**
     *加急费-临时
     * 
     */
    public static BigDecimal urgeFeeTemp(boolean isUrge,ContractTemp contract,ContractFeeTemp fee){
    	 BigDecimal feeExpedited = new BigDecimal(0.00);
        if(isUrge){
            BigDecimal contractAmount = contract.getContractAmount();
			feeExpedited = contractAmount.multiply(
					new BigDecimal(Double.toString(0.01))).setScale(2,
					BigDecimal.ROUND_HALF_UP);
        }
        fee.setFeeExpedited(feeExpedited);
        return feeExpedited;
    }
    
}
