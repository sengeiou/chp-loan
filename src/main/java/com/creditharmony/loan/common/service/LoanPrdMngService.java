package com.creditharmony.loan.common.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.common.type.UseFlag;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.common.dao.LoanPrdMngDao;
import com.creditharmony.loan.common.entity.CoeffRefer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.type.LoanProductCode;
import com.google.common.collect.Lists;

/**
 * 汇金产品管理-Service
 * 
 * @Class Name LoanPrdMngService
 * @author 周亮
 * @Create In 2015年12月9日
 */
@Service
public class LoanPrdMngService {

	@Autowired
	private LoanPrdMngDao prdDao;

	/**
	 * 检索产品
	 * 
	 * @param page
	 * @param selParam
	 * @return Page<LoanPrdMngEntity>
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public List<LoanPrdMngEntity> selPrd(LoanPrdMngEntity selParam) {
		// 检索产品
		if(StringUtils.isBlank(selParam.getProductStatus()))
		{
			selParam.setProductStatus(UseFlag.QY.value);
		}
		selParam.setProductType("products_type_loan_credit");
		List<LoanPrdMngEntity> ls = prdDao.selPrd(selParam);
		return ls;
	}
	
	/**
	 * 检索产品	过滤掉精英借A 和 精英借B
	 * 
	 * @param page
	 * @param selParam
	 * @return Page<LoanPrdMngEntity>
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public List<LoanPrdMngEntity> selPrd(String isBorrow) {
		// 检索产品
		LoanPrdMngEntity selParam = new LoanPrdMngEntity();
		selParam.setProductStatus(UseFlag.QY.value);
		selParam.setProductType("products_type_loan_credit");
		List<LoanPrdMngEntity> ls = prdDao.selPrd(selParam);
		//精英借A和精英借B 不显示
		List<LoanPrdMngEntity> productList = Lists.newArrayList();
		for(LoanPrdMngEntity product : ls){
			if(!"A001".equals(product.getProductCode()) && !"A002".equals(product.getProductCode())){
				productList.add(product);
			}
		}
		
		Iterator<LoanPrdMngEntity> iterator = productList.iterator();
	    while(iterator.hasNext()){//信易借的产品处理
	    	LoanPrdMngEntity lpme = iterator.next();
	    	if("1".equals(isBorrow)){//信易借
	    		if(!LoanProductCode.PRO_XYJ.equals(lpme.getProductCode())){//如果不是信易借，则全部删除
	    			iterator.remove();
	    		}
	    	}else{
	    		if(LoanProductCode.PRO_XYJ.equals(lpme.getProductCode())){//移除信易借
	    			iterator.remove();
	    		}
	    	}
	    }
		
		return productList;
	}
	
	/**
	 * 获取可申请期数
	 * 2016年4月23日
	 * By 王彬彬
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public List<String> getCoeffReferMonths()
	{
		CoeffRefer coeffRefer = new CoeffRefer();
		coeffRefer.setSystemFlag(YESNO.NO.getCode());//系统标识（0：汇金，1：汇诚）
		return prdDao.getCoeffReferMonths(coeffRefer);
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public List<String> getCoeffReferMonths(String createTime)
	{
		CoeffRefer coeffRefer = new CoeffRefer();
		Date createDate = DateUtils.convertStringToDate(createTime);
		Date onLineDate = DateUtils.convertStringToDate(SystemConfigConstant.LOAN_ONLINE_DATE);
		if(DateUtils.dateAfter(createDate,onLineDate)){
			coeffRefer.setRiskDelFlag(YESNO.NO.getCode());
		}
		coeffRefer.setSystemFlag(YESNO.NO.getCode());//系统标识（0：汇金，1：汇诚）
		return prdDao.getCoeffReferMonths(coeffRefer);
	}
}
