package com.creditharmony.loan.borrow.sms.dao;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.sms.entity.SmsHis;

/**
 * 汇金短信发送历史记录表
 * 
 * @Class Name SmsHisMapper
 * @author zhaojunlei
 * @Create In 2015年12月17日
 */
@LoanBatisDao
public interface SmsHisMapper {
	
	/**
	 * 根据主键删除一条数据 2015年12月17日 
	 * By zhaojunlei
	 * @param id
	 * @param loanCode
	 * @param customerCode
	 * @return none
	 */
	public int deleteByPrimaryKey(@Param("id") String id,
			@Param("loanCode") String loanCode,
			@Param("customerCode") String customerCode);

	/**
	 * 插入一条记录 
	 * By zhaojunlei
	 * @param record
	 * @return none
	 */
	public int insertSelective(SmsHis record);

	/**
	 * 根据主键进行更新 
	 * By zhaojunlei
	 * @param id
	 * @param loanCode借款编号
	 * @param customerCode
	 *            客户编号
	 * @return none
	 */
	public SmsHis selectByPrimaryKey(@Param("id") String id,
			@Param("loanCode") String loanCode,
			@Param("customerCode") String customerCode);

}