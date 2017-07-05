package com.creditharmony.loan.borrow.certification.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.certification.entity.LoanBank;

@LoanBatisDao
public interface BankDao extends CrudDao<LoanBank>{
	/**
	 * 获得需要进行认证查询的数据,认证标识为【2】的单子
	 * 2016年8月2日
	 * By 朱静越
	 * @return
	 */
	public List<LoanBank> selectBankInfo(@Param("pagNo")int pagNo);
	
	/**
	 * 根据商户交易流水更新实名认证标识
	 * 2016年8月2日
	 * By 朱静越
	 * @param loanBank
	 * @return
	 */
	public int updateBySerialNo(LoanBank loanBank);
	
	/**
	 * 根据商户流水号进行查询要进行签约的数据
	 * 2016年8月3日
	 * By 朱静越
	 * @param trSerialNo 商户流水号
	 * @return
	 */
	public List<LoanBank> selectSignList(@Param(value = "pageNo") int pageNo);
	
	/**
	 * 查询要实名认证的数据 翁私
	 * @param pageNo 
	 * @return
	 */
	public List<LoanBank> queryList(@Param(value = "pageNo") int pageNo);

	/**
	 * 跟新状态
	 * @param bankquery
	 */
	public void updateBankByid(LoanBank bankquery);

	public void updateByNo(LoanBank loanBank);

	/**
	 * 查询要实名认证的数据(畅捷)
	 * @param paramMap
	 * @return
	 */
	public List<LoanBank> queryCjList(Map<String, Object> paramMap);

	/**
	 * 更具账号跟新实名认证是否成功
	 * @param loanBank
	 */
	public void updateByAccNo(LoanBank loanBank);
	
   /**
    * 查询数量
    * @param paramMap
    * @return
    */
	public String queryCount(Map<String, Object> paramMap);

	/**
	 * 上传合同实名认证
	 * @param loanCode
	 * @return
	 */
   public List<LoanBank> queryCjByCode(String loanCode);

   /**
	 * 账号变更
	 * @param loanCode
	 * @return
	 */
  public List<com.creditharmony.loan.borrow.certification.entity.LoanBank> queryCjById(String id);

  /**
   * 根据合同code 查询要实名认证的数据。
   * @param paramMap
   * @return
   */
  public List<LoanBank> queryCjListCode(Map<String, Object> paramMap);
}
