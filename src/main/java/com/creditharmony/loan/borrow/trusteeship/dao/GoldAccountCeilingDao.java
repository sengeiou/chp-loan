package com.creditharmony.loan.borrow.trusteeship.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.trusteeship.view.GoldAccountCeiling;

/**
 * 金账户上限列表
 * 
 * @Class Name GCCeilingDao
 * @author 张建雄
 * @Create In 2016年2月24日
 */
@LoanBatisDao
public interface GoldAccountCeilingDao extends CrudDao<GoldAccountCeiling> {
	/**
	 *  查询金账户上限列表数据信息
	 * @param pageBounds 分页参数
	 * @param conditions 检索参数
	 * @return 金信上限列表时数据
	 */
	public List<GoldAccountCeiling> getCeilingList(PageBounds pageBounds,Map<String,Object> conditions);
	public List<GoldAccountCeiling> getCeilingList(Map<String,Object> conditions);
	/**
	 *  查询金账户上限列表总金额
	 * @param conditions 检索参数
	 * @return 金信上限列表总金额
	 */
	public String getCeilingSum (Map<String,Object> conditions);
	/**
	 *  检索用户信贷申请信息
	 * @param applyId 检索参数
	 * @return 用户信贷申请信息
	 */
	public Map<String,String> findUserInfo(String applyId);
	/**
	 *  检索信用卡信息
	 * @param applyId 检索参数
	 * @return 信用卡信息
	 */
	public List<Map<String,String>> findCreditList(String applyId);
	/**
	 *  检索信贷用户联系人信息
	 * @param applyId 检索参数
	 * @return 信贷用户联系人信息
	 */
	public List<Map<String,String>> findContactList(String applyId);
	/**
	 *  设置金账户上限额度
	 * @param ceilingMoney 上限额度
	 * @return 
	 */
	public void setCeilingMoney(Map<String,Object> maps);
	/**
	 *  查询金账户上限额度数据信息
	 * @return 
	 */
	public String selectCeilingMoneyCount();
	
	public Map<String,String> selectCeilingMoney();
	/**
	 *  更新金账户上限额度
	 * @param ceilingMoney 上限额度
	 * @return 
	 */
	public void updateCeilingMoney(Map<String,Object> maps);
	public void updateGoldAccount ();
	public void updateData();
	public void insertGoldAccountData(String loanCode);
	public String selectLoanCode(String loanCode);
	
}
