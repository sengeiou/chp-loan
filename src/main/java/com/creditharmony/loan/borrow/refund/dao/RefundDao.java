package com.creditharmony.loan.borrow.refund.dao;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.refund.entity.AlreadyRefundExportExcel;
import com.creditharmony.loan.borrow.refund.entity.ExamineExportExcel;
import com.creditharmony.loan.borrow.refund.entity.PaybackHistory;
import com.creditharmony.loan.borrow.refund.entity.Refund;
import com.creditharmony.loan.borrow.refund.entity.RefundExportExcel;
import com.creditharmony.loan.borrow.refund.entity.RefundServiceFee;


/**
 * 退款Dao
 * @Class Name RefundDao
 * @author WJJ
 * @Create In 2016年4月19日
 */
@LoanBatisDao
public interface RefundDao extends CrudDao<Refund>{
	/**
	 * 添加退款数据
	 * 2016年4月19日
	 * By WJJ
	 */
	public int insert(Refund refund);
	
	/**
	 * 添加退款数据
	 * 2016年4月19日
	 * By WJJ
	 */
	public int update(Refund refund);
	/** 查询退款人信息 */
	public List<Map<String,Object>> getInfo(String contractCode);
	/** 添加退款历史信息 */
	public int insertHistory(PaybackHistory history);

	/**
	 * 退款(数据管理部)
	 * 2016年4月20日
	 * @param page
	 * @param urgeServicesMoneyEx
	 * @return 分页
	 */
	public  List <Refund> selectRefundList(PageBounds pageBounds,Refund refund);
	
	/**
	 * 统计退款总金额和笔数
	 * @param refund
	 * @return
	 */
	public Refund selectRefundListForSumAndCount(Refund refund);
	
	/**
	 * 查询夏总账户
	 */
	public  List <String> selectMiddlePerson(String middleName);
	
	/**
	 * 退票
	 */
	public void updateRefundTicket(Refund refund);
	
	/**
	 * 修改蓝补金额 
	 */
	public void updatePaybackBule(Refund refund);
	
	/**
	 * 查询蓝补金额  
	 */
	public BigDecimal getPaybackBule(String contractCode);
	
	/**
	 * 查询退款数据 
	 */
	public  List<Refund> selectRefundById(String id);


	/**
	 * 退款导出Excel
	 */
	public  List <RefundExportExcel> refundExportList(Refund refund);
	
	/**
	 * 已退款导出Excel
	 */
	public  List <AlreadyRefundExportExcel> alreadyRefundDataExcel(Refund refund);
	
	/**
	 * 退款导出Excel
	 */
	public  List <ExamineExportExcel> examineExportExcelList(Refund refund);
	
	/**
	 * 查询退款数据 
	 */
	public  List<Refund> selectRefundById(Refund refund);
	/**
	 * 查询催收服务费退款数据
	 */
	public List<RefundServiceFee> getRfundServiceFeeList(PageBounds pageBounds,RefundServiceFee refundServiceFee);
	
	/**
     *通过合同编号查询还款信息 
     *2016年5月27日
     *By wjj
     *@param payback
     *@return List
     * 
     */
	public List<Payback> findByContractCode(Payback payback);
	
	public void deleteRfundServiceFee(String id);
	
	public void udateRfundServiceFee(@Param(value = "returnStatus")String returnStatus,@Param(value = "returnTime")Date returnTime,@Param(value = "id")String id);
	
	
	/**
	 * 查询还款信息
	 */
	public List<Map<String,Object>> findPayback(@Param(value = "contractCode")String contractCode,@Param(value = "modifyTime")String modifyTime);

	/**
	 * 查询此数据是否有修改
	 */
	public List<Map<String,Object>> getInfoByModifyTime(@Param(value = "id")String id,@Param(value = "modifyTime")String modifyTime);
	
	/**
	 * 查询是否有刚提交过的催收服务费退款
	 */
	public List<Map<String,Object>> getServiceFeeInfo(@Param(value = "contractCode")String contractCode,@Param(value = "appType")String appType,@Param(value = "appStatus")String appStatus);
	
	/**
	 * 查询还款日当天,退款金额=蓝补余额-当期期供未还金额-未还违约金及罚息总额 
	 * @param contractCode
	 * @return
	 */
	public BigDecimal getTkMoney(@Param(value = "contractCode")String contractCode);
	
	/**
	 * 查询非还款日当天,退款金额 = 蓝补金额-逾期期供金额-未还违约金及罚息总额
	 * @param contractCode
	 * @return
	 */
	public BigDecimal getTkMoney2(@Param(value = "contractCode")String contractCode);
}
