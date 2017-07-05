package com.creditharmony.loan.channel.goldcredit.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.channel.goldcredit.view.Summary;
import com.creditharmony.loan.channel.goldcredit.view.SettingCellingNumEntity;
import com.creditharmony.loan.channel.goldcredit.view.SummaryCount;

/**
 * 汇总表
* Description:
* @class SummaryDao
* @author wengsi 
* @date 2017年6月5日下午5:51:53
 */
@LoanBatisDao
public interface SummaryDao extends CrudDao<Summary> {
	/**
	 *  查询金信上限列表数据信息
	 * @param pageBounds 分页参数
	 * @param conditions 检索参数
	 * @return 金信上限列表时数据
	 */
	public List<Summary> getCeilingList(PageBounds pageBounds,Map<String,Object> conditions);
	public List<Summary> getCeilingList(Map<String,Object> conditions);
	/**
	 *  查询金信上限列表总金额
	 * @param conditions 检索参数
	 * @return 金信上限列表总金额
	 */
	public Map<String,String> getCeilingSum (Map<String,Object> conditions);
	/**
	 *  检索用户信贷申请信息
	 * @param applyId 检索参数
	 * @return 用户信贷申请信息
	 */
	public Map<String,String> findUserInfo(String loanCode);
	/**
	 *  检索信用卡信息
	 * @param applyId 检索参数
	 * @return 信用卡信息
	 */
	public List<Map<String,String>> findCreditList(String loanCode);
	/**
	 *  检索信贷用户联系人信息
	 * @param applyId 检索参数
	 * @return 信贷用户联系人信息
	 */
	public List<Map<String,String>> findContactList(String loanCode);
	/**
	 *  设置上限数量
	 * @param cellingNum 上先数量信息
	 * @return 
	 */
	public void setCeilingNum(SettingCellingNumEntity cellingNum);
	/**
	 *  查询金信上限额度数据信息
	 * @return 
	 */
	public String selectCeilingMoneyCount();
	
	public SettingCellingNumEntity selectCeilingMoney();
	/**
	 *  更新金信上限额度
	 * @param ceilingMoney 上限额度
	 * @return 
	 */
	public void updateCeilingMoney(Map<String,Object> maps);
	public void updateJINXIN ();
	public void updateData();
	public void updateJINXINCopy();
	public void insertJINXINData(String loanCode);
	public String selectLoanCode(String loanCode);
	/**
	 * TG额度失效后更新借款表
	 */
	public void updateTGData ();
	/**
	 * 合同审核中的金信上限信息
	 * @return
	 */
	public Map<String,Object> getJXCeillingData();
	/**
	 * 归档合同审核中的金信上限数据信息
	 */
	public void updateJXCeilling();
	//更新已用金信额度
	public void updusedLimit(Map<String,Object> map);
	/**
	 * 添加合同审核中的金信上限数据信息
	 */
	public void insertJXCeilling(Map<String,Object> maps);
	/**
	 * 查询指定的归档总笔数和批借金额
	 * @param conditions
	 * @return
	 */
	public Map<String, String> getCeilingSumForArchive(
			Map<String, Object> conditions);
	/**
	 * 分类查询指定的归档总笔数和批借金额
	 * @param conditions
	 * @return
	 */
	public List<Map<String, String>> getCeilingSumForArchiveChannel(
			Map<String, Object> conditions);
	
	/**
	 *  设置上限数量
	 * @param cellingNum 上先数量信息
	 * @return 
	 */
	public void setCeilingNumCopy(SettingCellingNumEntity cellingNum);
	/**
	 * 查询房产信息
	 * @param loanCode
	 * @return
	 */
	public List<Map<String, String>> findHouseList(String loanCode);
	public Map<String, Object> getCeilingSumExcel(Map<String, Object> conditions);
	public void saveSummaryExport(SummaryCount parm);
	public void updateSummaryExport(SummaryCount parm);
	public SummaryCount querySummaryExport(SummaryCount parm);
}
