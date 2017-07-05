package com.creditharmony.loan.channel.finance.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.channel.finance.entity.SettlementOfClaimsEntity;
/**
 * 债权结清列表信息
 * @Class Name SettlementOfClaimsDao
 * @author 张建雄
 * @Create In 2016年2月19日
 */
@LoanBatisDao
public interface SettlementOfClaimsDao extends CrudDao<SettlementOfClaimsEntity> {
	/**
	 * 获取债权结清列表信息
	 * @Class Name SettlementOfClaimsDao
	 * @author 张建雄
	 * @Create In 2016年2月19日
	 */
	public List<SettlementOfClaimsEntity> findSettlementOfClaimsList(PageBounds pageBounds,Map<String,Object> queryMap);
	public List<SettlementOfClaimsEntity> exportSettlementOfClaimsList(Map<String,Object> queryMap);
	/**
	 * 更新选中的债权结清列表状态
	 * @Class Name updateSettlementOfClaims
	 * @author 张建雄
	 * @Create In 2016年2月19日
	 */
	public void updateSettlementOfClaims(Map<String,Object> queryMap);
	
	/**
	 * 计算结清总金额
	 * 2016年6月25日
	 * By 王彬彬
	 * @param queryMap
	 * @return
	 */
	public Map<String,Object> findSumSettlementOfClaimsList(Map<String,Object> queryMap);
}
