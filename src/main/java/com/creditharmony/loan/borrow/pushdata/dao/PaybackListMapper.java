package com.creditharmony.loan.borrow.pushdata.dao;

import java.util.List;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.pushdata.entity.PaybackList;

/**
 * 还款_待还款列表  
 * @Class Name PaybackListMapper
 * @author zhaojunlei
 * @Create In 2015年12月15日
 */
@LoanBatisDao
public interface PaybackListMapper {
	
    /**
     * 根据id删除相应的待还款列表记录
     * By zhaojunlei
     * @param id 主键id
     * @return int 成功删除的条数
     */
    public int deleteByPrimaryKey(String id);

    /**
     * 插入一条记录
     * By zhaojunlei
     * @param record 待还款序列记录
     * @return int 成功插入的条数
     */
   
    public int insertSelective(PaybackList record);

    /**
     * 根据id 查询一条数据
     * By zhaojunlei
     * @param id 主键id
     * @return PaybackList 待还款列表对应的记录
     */ 
    public PaybackList selectByPrimaryKey(String id);

    /**
     * 根据给定的待还款序列记录更新一条数据
     * By zhaojunlei
     * @param record 还款序列记录
     * @return int 更新成功的条数
     */
    public int updateByPrimaryKeySelective(PaybackList record);
    
    /**
     * 根据日期得到当日的待还款列表
     * 2015年12月15日
     * By zhaojunlei
     * @param date
     * @return List<PaybackList> getPaybackListByDate对应的待还款列表
     */
    public List<PaybackList> getPaybackListByDate(String date);
    
    /**
     * 需要登录还款_待还款列表的数据
     * 2015年12月21日
     * By 施大勇
     * @param record 输入参数
     * @return 还款_待还款列表的数据List
     */
    public List<PaybackList> selectDataForPaybackList(PaybackList record);

    /**
     * 根据合同编号更新催收方式
     * 2016年2月29日
     * By liushikang
     * @param payback
     */
	public int updateByContractCode(PaybackList payback);
	/**
	 * 根据合同编号检索数据
	 * @param contractCode 合同编号
	 * @return 结果
	 */
	public List<PaybackList> selectByContractCode(String contractCode);
	
	/**
	 * 以合同编号删除数据
	 * @param contractCode 合同编号
	 * @return 结果
	 */
	public int deleteByContractCode(String contractCode);
}