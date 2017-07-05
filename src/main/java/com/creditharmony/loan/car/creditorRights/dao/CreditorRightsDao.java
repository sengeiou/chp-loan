package com.creditharmony.loan.car.creditorRights.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.claim.dto.SyncClaim;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.creditorRights.entity.Coborrower;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRightsImport;
import com.creditharmony.loan.car.creditorRights.view.CreditorLog;
import com.creditharmony.loan.car.creditorRights.view.CreditorRightView;
@LoanBatisDao
public interface CreditorRightsDao extends CrudDao<CreditorRights>{
   
	public List<CreditorRightView> getCreditorRights(CreditorRightView view ,PageBounds pageBounds);
	
	public CreditorRightView getCreditRight(String id);
	
	/** 
	 * 债权信息-编辑
	 * 获取共同借款人数据
	 * @param creditorRightId
	 * @return
	 */
	public List<SyncClaim> getCoborrowerData(String creditorRightId);
	
	public void updateLoanStatus(CreditorRights creditorRights);
	
	public void updateIssendWealth(CreditorRights creditorRights);
	/**
	 * 查询要推送到财富的数据对象
	 * 2016年3月8日
	 * By 张振强
	 * @param loanCode 参数
	 * @return 要同步的对象
	 */
	public SyncClaim syncFortune(String loanCode);
	
	/**
	 * 查询要推送到财富的数据对象   债权列表用
	 * 2016年3月8日
	 * By 张振强
	 * @param loanCode 参数
	 * @return 要同步的对象
	 */
	public List<SyncClaim> querySendFortune(CreditorRightView creditorRightView);
	
	public List<SyncClaim> querySendFortuneByLoanCode(List<String> list);
	
	public int insertCreditorCoborrower(Coborrower coborrower);

	/** 
	 * 获取借款编码
	 * @param contractCode
	 * @return
	 */
	public String getCjContractLoanCode(String contractCode);
	
	/** 
	 * 导入Excel 添加债权
	 * @param creditorRightsImport
	 * @return
	 */
	public int insertBatch(List<CreditorRightsImport> list);
	public int insertCreditorRightsImport(CreditorRightsImport creditorRightsImport);

	public void updateCarCreditorForConfirm(Map<String,Object> map);

	/**
	 * 插入债权历史数据
	 * @param log
	 */
	public void insertCreLog(CreditorLog log);
	
	/**
	 * 根据债权历史ID查询数据
	 * @param log
	 */
	public List<CreditorLog> getCreLog(String loanCode ,PageBounds pageBounds);
	
}