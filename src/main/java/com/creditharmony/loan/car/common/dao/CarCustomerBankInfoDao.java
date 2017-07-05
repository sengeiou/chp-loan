package com.creditharmony.loan.car.common.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.account.view.RepayAccountApplyView;
import com.creditharmony.loan.car.carBankInfo.view.CarCustomerBankInfoView;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;

/**
 * 银行卡信息
 * @Class Name CarCustomerBankInfoDao
 * @author 安子帅
 * @Create In 2016年2月16日
 */
@LoanBatisDao
public interface CarCustomerBankInfoDao extends CrudDao<CarCustomerBankInfo>{
	/**
	 * 新增银行卡
	 * 2016年2月16日
	 * By 安子帅
	 * @param carCustomerCompany
	 */
    public void insertCarCustomerBankInfo(CarCustomerBankInfo carCustomerBankInfo);
    
    /**
   	 * 查询工作信息
	 * 2016年2月16日
	 * By 安子帅
	 * @param carCustomerCompany
	 */
   	public CarCustomerBankInfo selectCarCustomerBankInfo(String loanCode);
   	
//-----------------------------------车借还款账号--------------------------------------\\
    /**
   	 * 查询车借还款账号维护申请列表
	 * @param carCustomerCompany
	 */
   	public List<CarCustomerBankInfoView> getCarCustomerBankInfoList(PageBounds pageBounds,CarCustomerBankInfoView info);
   	/**
   	 * 查询车借还款账号维护数据
   	 * 去掉分页条件
	 * @param carCustomerCompany
	 */
   	public List<CarCustomerBankInfoView> getCarCustomerBankInfoList(CarCustomerBankInfoView info);

    /**
   	 * 查询车借客户信息
	 * @param carCustomerCompany
	 */
	public CarCustomerBankInfoView getCustomerMsg(String loanCode);
	
	/**
	 * 新建车借还款账号(在用账号)
	 * @param carCustomerCompany
	 */
	public void insertBankInfo(CarCustomerBankInfoView info);
	
	/**
	 * 新建车借还款账号(历史账号)
	 * @param carCustomerCompany
	 */
	public void insertBankInfoAdd(CarCustomerBankInfoView info);

	/**
	 * 获取车借还款账号基本数据信息，包括用户名，合同编号 
	 * @param info
	 */
	public CarCustomerBankInfoView getBankInfoMsg(String id);
	
	/**
	 * 通过ID查询还款账号信息，用于插入数据到修改手机号或银行卡号
	 * @param info
	 */
	public CarCustomerBankInfoView getCjBankInfoById(String id);
	
	/**
	 * 修改还款账号表的维护状态
	 * @param info
	 */
	public void updateBankInfoStatus(CarCustomerBankInfoView info);
	
	/**
	 * 车借还款账号置顶
	 * @param CarCustomerBankInfoView
	 */
	public void updateAccountTop(CarCustomerBankInfoView info);
	
    /**
   	 * 车借还款账号已办列表
	 * @param CarCustomerBankInfoView
	 */
   	public List<CarCustomerBankInfoView> getBankInfoList(PageBounds pageBounds,CarCustomerBankInfoView info);
   	
   	/** 
   	 * 查询原数据
   	 * 客户账号相关信息
   	 * 包括手机号和邮箱
   	 * @param id
   	 * @return
   	 */
   	public CarCustomerBankInfoView getOldMsg(String id);

   	/** 
   	 * 查询原数据
   	 * 共借人
   	 * @param id
   	 * @return
   	 */
   	public CarCustomerBankInfoView getOldCoborrowerMsg(String id);
   	
   	/** 
   	 * 查询所有共借人
   	 * @param id
   	 * @return
   	 */
   	public List<CarCustomerBankInfoView> getCoborrowerList(String loanCode);
   	
   	/**
   	 * 车借还款维护账号
   	 * 审核拒绝，修改数据
   	 * @param CarCustomerBankInfoView
   	 */
   	public void checkBankInfoRefuse(CarCustomerBankInfoView info);
   	
	/**
   	 * 车借还款维护账号
   	 * 车借还款维护账号-审核
   	 * 修改原账号数据，并删除新增账号数据
   	 * @param CarCustomerBankInfoView
   	 */
   	public void checkTrueUpdateAndDelete(CarCustomerBankInfoView info);
   	
   	
   	/**
   	 * 车借还款维护账号审核
   	 * 删除在用账号
   	 * @param CarCustomerBankInfoView
   	 */
   	public void deleteBankInfo(CarCustomerBankInfoView info);
   	
   	/**
   	 * 车借还款维护账号审核
   	 * 删除历史账号
   	 * @param CarCustomerBankInfoView
   	 */
   	public void deleteBankInfoAdd(CarCustomerBankInfoView info);
   	
   	/**
   	 * 车借还款维护账号
   	 * 审核通过，修改手机号或邮箱地址数据
   	 * @param CarCustomerBankInfoView
   	 */
   	public void updatePhoneOrEmail(CarCustomerBankInfoView info);
   	
   	/**
   	 * 车借还款维护账号
   	 * 添加历史
   	 * @param CarCustomerBankInfoView
   	 */
   	public void insertCjBankInfoLog(CarCustomerBankInfoView log);
	/**
	 * 根据合同编号查询维护历史
	 * 2016年3月2号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	List<CarCustomerBankInfoView> getHistoryList(CarCustomerBankInfoView view);

	/** 
	 * 查询车借_客户银行卡历史账号信息
	 * @param id
	 * @return
	 */
   	public List<CarCustomerBankInfoView> getBankInfoByLoanCode(String loanCode);
   	
   	/** 
   	 * 查询共借人原数据
   	 * @param info
   	 * @return
   	 */
   	public CarCustomerBankInfoView getCoboMsg(CarCustomerBankInfoView info);
   	/**
   	 * 根据id修改银行卡信息
   	 * @param carCustomerBankInfo
   	 */
   	public void upadteCarCustomerBankInfoById(CarCustomerBankInfo carCustomerBankInfo);
   	
}