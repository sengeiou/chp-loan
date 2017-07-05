/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.webRateInfoController.java
 * @Create By 张灏
 * @Create In 2016年4月13日 下午1:38:44
 */
package com.creditharmony.loan.sync.data.proxy;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.common.entity.AuditResult;
import com.creditharmony.core.common.entity.ProductRate;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.loan.borrow.contract.dao.CoeffReferJYJDao;
import com.creditharmony.loan.borrow.contract.dao.RateInfoDao;
import com.creditharmony.loan.borrow.contract.entity.CoeffReferJYJ;
import com.creditharmony.loan.borrow.contract.entity.RateInfo;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.util.ProductUtil;
import com.creditharmony.loan.common.dao.LoanPrdMngDao;
import com.creditharmony.loan.common.entity.CoeffRefer;
import com.creditharmony.loan.common.utils.LoanConsultDateUtils;
import com.creditharmony.loan.sync.data.remote.MonthRateDataService;

/**
 * 产品费率
 * 
 * @Class Name RateInfoController
 * @author 申阿伟
 * @Create In 2017年5月6日
 */
@Component
public class MonthRateDataProxy implements MonthRateDataService {

	private CoeffReferJYJDao coeffReferJYJDao = SpringContextHolder.getBean(CoeffReferJYJDao.class);
	private LoanPrdMngDao loanPrdMngDao = SpringContextHolder.getBean(LoanPrdMngDao.class);
	private RateInfoDao rateInfoDao = SpringContextHolder.getBean(RateInfoDao.class);

	/**
	 * 返回给汇诚期供金额以及总费率 2017年5月6日 By 申阿伟
	 * 
	 * @param auditResult
	 * @return List<ProductRate>
	 */
	@Override
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public AuditResult getProductRateList(AuditResult auditResult) {
		try {
			// 判断产品
			if (auditResult.getProductCode().equals(ProductUtil.PRODUCT_JYJ.getCode())) {
				// 简易借产品
				CoeffReferJYJ coeffRefer = new CoeffReferJYJ();
				coeffRefer.setMonths(auditResult.getMonths());
				coeffRefer.setProductCode(auditResult.getProductCode());
				List<CoeffReferJYJ> coeffReferList = coeffReferJYJDao.selectCoeffRefer(coeffRefer);
				List<ProductRate> productRate = new ArrayList<ProductRate>();
				// 多条记录计算
				for (int i = 0; i < coeffReferList.size(); i++) {
					CoeffReferJYJ c = coeffReferList.get(i);
					ProductRate p = new ProductRate();
					// 产品总费率
					p.setRate(c.getProductRate()+"");
					BigDecimal auditMoney = new BigDecimal(auditResult.getAuditAmount()); // 批复金额
					BigDecimal preServiceFeeRate = BigDecimal.valueOf(c.getComprehensiveFeeCoeff()); // 前期服务费率
					BigDecimal preServiceFee = auditMoney.multiply(preServiceFeeRate
							.divide(new BigDecimal(ContractConstant.PERCENT), 8, BigDecimal.ROUND_HALF_UP))
							.setScale(2, BigDecimal.ROUND_HALF_UP); // 前期综合服务费
					BigDecimal contractAmount = auditMoney.add(preServiceFee); // 合同金额（批复金额
																				// +
																				// 前期综合服务费）
					BigDecimal loanRate =new BigDecimal(c.getRate()).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);         // 借款利率
					BigDecimal _temp1 = (loanRate.add(new BigDecimal(1)))
							.pow(c.getMonths().intValue());// （1+月利率）^借款期限
					BigDecimal _temp2 = _temp1.subtract(new BigDecimal(1));
					Double stateServiceFeeRate =c.getMonthGatherRate(); // 分期服务费率
					 // 分期服务费(合同金额*分期服务费率)
			        BigDecimal stageServiceFee = contractAmount.multiply(new BigDecimal(stateServiceFeeRate).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP); 
					// 月还本息金额
					BigDecimal auditMonthPaymoney = contractAmount.multiply(loanRate)
							.multiply(_temp1).divide(_temp2, 2, BigDecimal.ROUND_HALF_UP);
					p.setPeriodAmount(auditMonthPaymoney.add(stageServiceFee).toString());
					productRate.add(p);
				}
				auditResult.setProductRateList(productRate);

			}else if(auditResult.getProductCode().equals(ProductUtil.PRODUCT_NXD.getCode())){
				CoeffReferJYJ coeffRefer = new CoeffReferJYJ();
				coeffRefer.setMonths(auditResult.getMonths());
				coeffRefer.setProductCode(auditResult.getProductCode());
				List<CoeffReferJYJ> coeffReferList = coeffReferJYJDao.selectCoeffRefer(coeffRefer);
				List<ProductRate> productRate = new ArrayList<ProductRate>();
				// 多条记录计算
				for (int i = 0; i < coeffReferList.size(); i++) {
					BigDecimal monthServiceFee=new BigDecimal("0.0");//月服务费
		            BigDecimal monthServiceRate=new BigDecimal("0.0");//月服务费率
		            BigDecimal monthRate=new BigDecimal("0.0");//月利率
		            BigDecimal monthFee=new BigDecimal("0.0");//月利息
					CoeffReferJYJ c = coeffReferList.get(i);
					ProductRate p = new ProductRate();
					// 产品总费率
					p.setRate(c.getProductRate()+"");
					BigDecimal auditMoney = new BigDecimal(auditResult.getAuditAmount()); // 批复金额
					monthRate = new BigDecimal(coeffReferList.get(i).getRate()).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP); //月利率
					monthServiceRate = new BigDecimal(coeffReferList.get(i).getMonthGatherRate()).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);		//月服务费率
	            	monthServiceFee = auditMoney.multiply(monthServiceRate).setScale(2,BigDecimal.ROUND_HALF_UP);		//月服务费
	            	monthFee = 	auditMoney.multiply(monthRate).setScale(2,BigDecimal.ROUND_HALF_UP);		//月利息
	            	p.setPeriodAmount(monthFee.add(monthServiceFee).toString());
					productRate.add(p);
				}
				auditResult.setProductRateList(productRate);
				
			} else {
				// 其他产品
				CoeffRefer coeffRefer = new CoeffRefer();
				Date consultDate = LoanConsultDateUtils.findTimeByLoanCode(auditResult.getLoanCode());
				Date FLLineDate = DateUtils.convertStringToDate(SystemConfigConstant.LOAN_FL_DATE);
				if (DateUtils.dateAfter(consultDate, FLLineDate)) {// 根据咨询时间来判断
					coeffRefer.setSystemFlag("0");
				} else {
					if (auditResult.getRiskLevel().equals("A1") || auditResult.getRiskLevel().equals("A2")
							|| auditResult.getRiskLevel().equals("B1") || auditResult.getRiskLevel().equals("C1")
							|| auditResult.getRiskLevel().equals("C2") || auditResult.getRiskLevel().equals("D1")
							|| auditResult.getRiskLevel().equals("D2") || auditResult.getRiskLevel().equals("E1")
							|| auditResult.getRiskLevel().equals("F1")) {
						coeffRefer.setSystemFlag("2");
					} else {
						coeffRefer.setSystemFlag("0");
					}
				}
				coeffRefer.setMonths(auditResult.getMonths());
				coeffRefer.setRiskLevel(auditResult.getRiskLevel());
				CoeffRefer result = loanPrdMngDao.getCoeffReferByParam(coeffRefer);
				List<ProductRate> productRate = new ArrayList<ProductRate>();
				ProductRate p = new ProductRate();
				// 产品总费率
				p.setRate(BigDecimal.valueOf(result.getProductUsableRate()).toString());
				BigDecimal auditMoney = new BigDecimal(auditResult.getAuditAmount()); // 批复金额
				BigDecimal preServiceFeeRate = BigDecimal.valueOf(result.getComprehensiveFeeCoeff()); // 前期服务费率
				BigDecimal preServiceFee = auditMoney.multiply(
						preServiceFeeRate.divide(new BigDecimal(ContractConstant.PERCENT), 8, BigDecimal.ROUND_HALF_UP))
						.setScale(2, BigDecimal.ROUND_HALF_UP); // 前期综合服务费
				BigDecimal contractAmount = auditMoney.add(preServiceFee); // 合同金额（批复金额
																			// +
																			// 前期综合服务费）
				RateInfo rateInfo = rateInfoDao.findRateInfoByMonths(BigDecimal.valueOf(auditResult.getMonths()));
		        BigDecimal loanRate =new BigDecimal(rateInfo.getRate()).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);         // 借款利率
				BigDecimal _temp1 = (loanRate.add(new BigDecimal(1))).pow(auditResult.getMonths());// （1+月利率）^借款期限
				BigDecimal _temp2 = _temp1.subtract(new BigDecimal(1));
				Double stateServiceFeeRate = result.getMonthGatherRation(); // 分期服务费率
				 // 分期服务费(合同金额*分期服务费率)
		        BigDecimal stageServiceFee = contractAmount.multiply(new BigDecimal(stateServiceFeeRate).divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP)).setScale(2, BigDecimal.ROUND_HALF_UP); 
				// 月还本息金额
				BigDecimal auditMonthPaymoney = contractAmount.multiply(loanRate)
						.multiply(_temp1).divide(_temp2, 2, BigDecimal.ROUND_HALF_UP);
				p.setPeriodAmount(auditMonthPaymoney.add(stageServiceFee).toString());
				productRate.add(p);
				auditResult.setProductRateList(productRate);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return auditResult;
	}

}
