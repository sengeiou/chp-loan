package com.creditharmony.loan.car.common.util;

import java.math.BigDecimal;

import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;

/**
 * 需要更新借款信息表 的工具类
 * @Class Name CarLoanPreUpdateUtil
 * @author 申诗阔
 * @Create In 2016年2月19日
 */
public class CarLoanPreUpdateUtil {

	/**
	 * 更新借款信息表的工具类
	 * 2016年2月19日
	 * By 申诗阔
	 * @param loanCode 借款编号
	 * @param status 借款状态code值
	 * @param mortgagee 抵押权人（中间人，对应于中间人表）的id值
	 * @return
	 */
	public static CarLoanInfo UpdateCarLoanInfo(String loanCode, String status, String mortgagee) {
		CarLoanInfo info = new CarLoanInfo();
		// 更新借款信息表中的借款状态
		info.setDictLoanStatus(status);
		// 设置借款编号
		info.setLoanCode(loanCode);
		// 设置抵押权人id
		info.setMortgagee(mortgagee);;
		// 更新 更改用户及更改时间
		info.preUpdate();
		return info;
	}

	/**
	 * 更新合同表的工具类
	 * 2016年2月19日
	 * By 申诗阔
	 * @param loanCode 借款编号
	 * @param centerUser 中间人
	 * @return
	 */
	public static CarContract UpdateCarContract(String loanCode, String centerUser) {
		CarContract carContract = new CarContract();
		// 更新借款信息表中的借款状态
		carContract.setMidId(centerUser);
		// 设置借款编号
		carContract.setLoanCode(loanCode);
		// 更新 更改用户及更改时间
		carContract.preUpdate();
		return carContract;
	}
	
	/**
	 * 更新合同表的工具类
	 * 2016年2月19日
	 * By 申诗阔
	 * @param loanCode 借款编号
	 * @param centerUser 中间人
	 * @return
	 */
	public static CarContract UpdateCarContract2(String loanCode, BigDecimal extFee) {
		CarContract carContract = new CarContract();
		// 更新借款信息表中的借款状态
		carContract.setExtensionFee(extFee);
		// 设置借款编号
		carContract.setLoanCode(loanCode);
		// 更新 更改用户及更改时间
		carContract.preUpdate();
		return carContract;
	}
	
	/**
	 * 封装借款状态变更历史信息
	 * 2016年2月19日
	 * By 申诗阔
	 * @param loanCode 借款编号
	 * @param status 借款状态
	 * @param step 操作步骤
	 * @param operResultName 审批结果 中文
	 * @param backReason 退回原因
	 * @return
	 */
	public static CarLoanStatusHis updateStatusChangeRecord(String loanCode,
			String status, String step, String operResultName, String backReason) {
		CarLoanStatusHis carLoanStatusHis = new CarLoanStatusHis();
		User user = UserUtils.getUser();
		// 设置系统类型(系统机构名称)
		carLoanStatusHis.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 设置借款编号
		carLoanStatusHis.setLoanCode(loanCode);
		// 设置借款状态
		carLoanStatusHis.setDictLoanStatus(status);
		// 设置操作步骤
		carLoanStatusHis.setOperateStep(step);
		// 设置操作结果
		carLoanStatusHis.setOperateResult(operResultName);
		// 设置操作人
		carLoanStatusHis.setOperator(user.getName());
		// 设置操作人角色 
		// user.getRole().getId()
		// TODO 设置操作人角色
		carLoanStatusHis.setOperatorRoleId("11");
		// 设置结构编码
		carLoanStatusHis.setOrgCode(user.getOrgIds());
		// 设置变更历史的id，创建人，创建时间，更新人，更新时间
		carLoanStatusHis.preInsert();
		// 设置操作时间
		carLoanStatusHis.setOperateTime(carLoanStatusHis.getCreateTime());
		
		carLoanStatusHis.setRemark(backReason);
		return carLoanStatusHis;
	}
}
