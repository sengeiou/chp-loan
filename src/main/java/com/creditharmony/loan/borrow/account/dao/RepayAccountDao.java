package com.creditharmony.loan.borrow.account.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.account.entity.KingAccountChangeExport;
import com.creditharmony.loan.borrow.account.view.RepayAccountApplyView;

/**
 * 还款账号
 * 
 * @Class Name RepayAccountDao
 * @author 王俊杰
 * @Create In 2016年2月22日
 */
@LoanBatisDao
public interface RepayAccountDao {

	/**
	 * 获取所有可用版本号
	 * 2016年2月23号
	 * by 王俊杰
	 * @return
	 */
	List<String> selectVersionList();
	
	/**
	 * 根据合同编号查询所有账户
	 * 2016年2月23号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	List<RepayAccountApplyView> selectAccountList(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 获取还款账号管理list
	 * 2016年2月23号
	 * by 王俊杰
	 * @param pageBounds
	 * @param repayAccountApplyView
	 * @return
	 */
	List<RepayAccountApplyView> selectAccountList(PageBounds pageBounds, RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 根据合同编号获取借款状态和借款标示
	 * 2016年2月25号
	 * by 王俊杰
	 * @param contractCode
	 * @return
	 */
	RepayAccountApplyView selectLoanStatusAndFlag(String contarctCode);
	
	/**
	 * 根据合同编号查询是否有账户处于待审核的状态
	 * 2016年3月3号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	List<String> selectMaintainStatus(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 根据合同编号获取添加还款账户需要的回填信息
	 * 2016年2月25号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	RepayAccountApplyView selectAddAccountMassage(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 根据id获取账户信息
	 * 2016年12月2号
	 * by huowenlong
	 * @param repayAccountApplyView
	 * @return
	 */
	RepayAccountApplyView selectAccountInfoById(RepayAccountApplyView repayAccountApplyView);

	/**
	 * 添加还款银行卡号
	 * 2016年2月26号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int insertAccount(RepayAccountApplyView repayAccountApplyView);
	/**
	 * 添加账户修改记录表
	 * 2016年3月15号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int insertAccountChangeLog(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 插入还款账号变更历史
	 * 2016年2月26号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int insertChangeHistory(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 根据ID获取省市名称
	 * 2016年2月26号
	 * by 王俊杰
	 * @param id
	 * @return
	 */
	String getAreaName(String id);
	
	/**
	 * 修改手机号
	 * 2016年2月29号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int updateCustomerMobilePhone(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 根据合同编号查询维护历史
	 * 2016年3月2号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	List<RepayAccountApplyView> getHistoryList(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 修改手机号审核拒绝
	 * 2016年3月2号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int refusePhone(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 修改手机号审核通过
	 * 2016年3月2号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int throughPhone(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 账号审核拒绝
	 * 2016年3月2号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int refuseAccount(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 添加账号审核通过
	 * 2016年3月7号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int throughAddAccount(RepayAccountApplyView repayAccountApplyView);

	/**
	 * 修改账号审核通过
	 * 2016年3月7号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int throughEditAccount(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 金账户初审
	 * 2016年3月7号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int saveGoldFirstCheck(RepayAccountApplyView repayAccountApplyView);
	/**
	 * 金账户终审
	 * 2016年3月7号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int editBankAccount(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 置顶操作
	 * 2016年3月3号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 */
	void updateAccountTop(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 修改金账户卡号
	 * 2016年3月9号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int editGoldAccount(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 获取还款日
	 * 2016年3月24号
	 * by WJJ
	 * @param getBillDay
	 * @return
	 */
	List<Map<String,Object>> getBillDay();
	
	/**
	 * 金账户审批信息下载
	 * 2016年06月11日
	 * by zhanghao
	 * @param map
	 * @return List<KingAccountChangeExport>
	 * 
	 */
	List<KingAccountChangeExport>  getTGdownload(Map<String,Object> map);

	/**
	 * 查询金账号待审核的数量
	 * @param repayAccountApplyView
	 * @return
	 */
	String selectAccountCount(RepayAccountApplyView repayAccountApplyView);

	/**
	 * 查询当前登录人是否有金账户专员，金账户管理员 权限
	 * @param userId
	 * @return
	 */
	int selectRoleCount(String userId);
	
	/**
	 * 修改Email审核拒绝
	 * 2016年3月2号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int refuseEmail(RepayAccountApplyView repayAccountApplyView);
	
	/**
	 * 修改Email审核通过
	 * 2016年3月2号
	 * by 王俊杰
	 * @param repayAccountApplyView
	 * @return
	 */
	int throughEmail(RepayAccountApplyView repayAccountApplyView);
	
}
