package com.creditharmony.loan.common.utils;

import java.util.Date;

import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.loan.borrow.consult.dao.ConsultDao;
/**
 * 获取咨询时间工具类
 * @Class Name LoanConsultDateUtils
 * @author 何军
 * @Create In 2016年7月18日
 */
public class LoanConsultDateUtils {

	/**
	 * 根据借款编码获取咨询时间
	 * 2016年7月18日
	 * By 何军
	 * @param loanCode
	 * @return
	 */
	public static Date findTimeByLoanCode(String loanCode){
		 ConsultDao consultDao = SpringContextHolder.getBean(ConsultDao.class);
		 Date consultTime = consultDao.findTimeByLoanCode(loanCode);
		 return consultTime;
	}
	
	
	public static Date findTimeByApplyId(String applyId){
		 ConsultDao consultDao = SpringContextHolder.getBean(ConsultDao.class);
		 Date consultTime = consultDao.findTimeByApplyId(applyId);
		 return consultTime;
	}
}
