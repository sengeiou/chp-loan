package com.creditharmony.loan.borrow.serve.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.serve.entity.ContractFileSend;
import com.creditharmony.loan.borrow.serve.entity.ContractFileSendEmail;
import com.creditharmony.loan.borrow.serve.view.ContractFileIdAndFileNameView;
import com.creditharmony.loan.borrow.serve.view.ContractFileSendEmailView;
import com.creditharmony.loan.borrow.serve.view.ContractFileSendView;

/**
 * 合同寄送
 * 
 * @Class Name CustomerServeDao
 * @author 王俊杰
 * @Create In 2016年2月1日
 */
@LoanBatisDao
public interface CustomerServeDao {
	
	/**
	 * 客户服务管理列表
	 * 2016年2月3号
	 * by 王俊杰
	 * @param pageBounds
	 * @param contractFileSendView
	 * @return
	 */
	List<ContractFileSend> selectContractSendList(PageBounds pageBounds, ContractFileSendView contractFileSendView);
	
	/**
	 * 电子协议列表
	 * 2016年11月8号
	 * by 方强
	 * @param pageBounds
	 * @param contractFileSendEmailView
	 * @return
	 */
	List<ContractFileSendEmail> selectContractSendEmailList(PageBounds pageBounds, ContractFileSendEmailView contractFileSendEmailView);
	
	/**
	 * 已删除合同列表
	 * 2016年2月19号
	 * by 王俊杰
	 * @param pageBounds
	 * @param contractFileSendView
	 * @return
	 */
	List<ContractFileSend> alreadyDeleteList(PageBounds pageBounds, ContractFileSendView contractFileSendView);
	/**
	 * 更新邮寄状态
	 * 2016年2月3号
	 * by 王俊杰
	 * @param contractFileSendView
	 * @return
	 */
	int updateMailStatus(ContractFileSendView contractFileSendView);
	
	/**
	 * 更新邮寄状态
	 * 2016年12月23号
	 * by WJJ
	 * @param contractFileSendEmailView
	 * @return
	 */
	int updateEmailStatus(ContractFileSendEmailView contractFileSendEmailView);
	
	/**
	 * 更新删除状态
	 * 2016年2月4号
	 * by 王俊杰
	 * @param contractFileSendView
	 * @return
	 */
	int updateDeleteStatus(ContractFileSendView contractFileSendView);
	
	/**
	 * 更新接收状态
	 * 2016年2月15号
	 * by 王俊杰
	 * @param contractFileSendView
	 */
	int updateReceiveStatus(String id);
	
	/**
	 * 根据id或合同编号获取applyId
	 * 2016年2月15号
	 * by 王俊杰
	 * @param id
	 * @return
	 */
	ContractFileSendView getApplyIdAndLoanCode(String id);
	
	/**
	 * 根据id或合同编号获取applyId
	 * 2016年11月10号
	 * by 方强
	 * @param id
	 * @return
	 */
	ContractFileSendEmailView getEmailApplyIdAndLoanCode(String id);
	
	/**
	 * 获取导出Excel的数据
	 * 2016年2月15号
	 * by 王俊杰
	 * @param contractFileSendView
	 * @return
	 */
	List<ContractFileSend> getExcelDataList(ContractFileSendView contractFileSendView);
	
	/**
	 * 永久删除数据
	 * 2016年2月16号
	 * by 王俊杰
	 * @param contractFileSendView
	 * @return
	 */
	int deleteData(ContractFileSendView contractFileSendView);
	
	/**
	 * 录入快递编号
	 * 2016年2月16
	 * by 王俊杰
	 * @param contractFileSendView
	 * @return
	 */
	int inputExpressNumber(ContractFileSendView contractFileSendView);
	
	/**
	 * 保存邮寄信息
	 * 2016年2月23日
	 * By 周怀富
	 * @param contractFileSendView
	 */
	public int insertContractSending(ContractFileSendView contractFileSendView);

	int updatedoOpencheck(ContractFileSendView contractFileSendView);

	/**
	 * 保存电子协议信息
	 * 2016年11月8日
	 * By 方强
	 * @param contractFileSendView
	 */
	public int insertContractSendingEmail(ContractFileSendEmailView contractFileSendView);

	int updatedoOpencheckEmail(ContractFileSendEmailView contractFileSendEmailView);
	
	/**
	 * 查询该合同编号是否已经寄送
	 * 2016年2月23日
	 * By 管洪昌
	 * @param contractFileSendView
	 */
	List<ContractFileSendView> findApplyByDealt(
			ContractFileSendView contractFileSendView);

	/**
	 * 查询该合同编号的电子协议是否已经发送
	 * 2016年11月07日
	 * By 方强
	 * @param contractFileSendEmailView
	 */
	List<ContractFileSendEmailView> findEmailApplyByDealt(
			ContractFileSendEmailView contractFileSendEmailView);
	
	/**
	 * 查询该合同编号是否已经寄送
	 * 2016年2月23日
	 * By 管洪昌
	 * @param contractFileSendView
	 */
	void updateMailLoan(ContractFileSendView contractFileSendView);
	/**
	 * 批量更新合同申请
	 * @param contractFileSendView
	 */
	void updateMailStatusAll(ContractFileSendView contractFileSendView);

	/**
	 * 查询合同寄送表
	 * @param contractFileSend
	 * @return
	 */
	ContractFileSendView getContractFile(ContractFileSend contractFileSend);

	/**
	 * 
	 * 客户信息查询
	 */
	List<ContractFileSendEmailView> getCustomerMsg(ContractFileSendEmailView info);

	/** 
	 * 获取合同文件
	 * @param ex
	 * @return
	 */
	List<ContractFileSendEmailView> getFileNameList(ContractFileSendEmailView ex);

	/** 
	 * 电子协议申请列表
	 * 发送/拒绝
	 * @param ex
	 */
	public int updateSendOrReturn(ContractFileSendEmailView ex);

	/** 
	 * 插入信借电子协议历史数据
	 * @param ex
	 */
	public void insertAgrLog(ContractFileSendEmailView ex);
	
	List<ContractFileSendView> contractFileList(
			ContractFileSendView contractFileSendView);

	int findContractCount(ContractFileSendView contractFileSendView);
	
	public List<Map<String,String>> findEmailTemplate(String templateType);
	
	public List<Map<String,Integer>> getMaxNumber(Map map);
	
	public List<ContractFileSendEmail> export(Map map);
	
	public List<Map<String,String>> getSettledDate(String loanCode);
	
	int updatePdfId(ContractFileSendEmailView contractFileSendEmailView);
	
	int updateDocId(ContractFileSendEmailView contractFileSendEmailView);

	public ContractFileSendEmailView selectCustomerServeBydownLoad(String  id);
	
	public String getChannelFlag(String loanCode);
	
	/** 
	 * zmq
	 * 获取合同文件名称
	 * @param ex
	 * @return
	 */
	List<ContractFileIdAndFileNameView> getFileNameAndFileIdList(ContractFileSendEmailView ex);
}
