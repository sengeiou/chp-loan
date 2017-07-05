package com.creditharmony.loan.channel.finance.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.channel.finance.entity.CarSettlementOfClaimsEntity;
import com.creditharmony.loan.channel.finance.entity.SettlementOfClaimsEntity;
/**
 * 债权结清列表信息
 * @Class Name SettlementOfClaimsDao
 * @author 张建雄
 * @Create In 2016年2月19日
 */
@LoanBatisDao
public interface CarSettlementOfClaimsDao extends CrudDao<CarSettlementOfClaimsEntity> {
	/**
	 * 获取债权结清列表信息
	 * @Class Name SettlementOfClaimsDao
	 * @author 张建雄
	 * @Create In 2016年2月19日
	 */
	public List<CarSettlementOfClaimsEntity> findCarSettlementOfClaimsList(PageBounds pageBounds,Map<String,Object> queryMap);
	/**
	 * 获取债权结清列表信息
	 * 用于导出
	 * 去掉查询分页功能
	 * @Class Name SettlementOfClaimsDao
	 * @author 张建雄
	 * @Create In 2016年2月19日
	 */
	public List<CarSettlementOfClaimsEntity> findCarSettlementOfClaimsList(Map<String,Object> queryMap);
	public List<CarSettlementOfClaimsEntity> exportCarSettlementOfClaimsList(Map<String,Object> queryMap);
	/**
	 * 更新选中的债权结清列表状态
	 * @Class Name updateSettlementOfClaims
	 * @author 张建雄
	 * @Create In 2016年2月19日
	 */
	public void updateCarSettlementOfClaims(Map<String,Object> queryMap);
	/** 
	 *  车借债权结清(确认) 
	 *  更新车借借款信息表(状态为结清)
	 * @param queryMap
	 */
	public void updateCarSettlementConfirm(Map<String,Object> queryMap);
}
