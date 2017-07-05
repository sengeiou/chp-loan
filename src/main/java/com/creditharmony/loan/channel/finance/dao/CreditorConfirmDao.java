package com.creditharmony.loan.channel.finance.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.channel.finance.entity.CreditorConfirmEntity;
@LoanBatisDao
public interface CreditorConfirmDao extends CrudDao<CreditorConfirmEntity> {
	
	public List<CreditorConfirmEntity> findCreditorConfirmList(PageBounds pageBounds,Map<String,Object> queryMap);
	/**
	 * 更新选中的债权确认列表状态
	 * @Class Name updateSettlementOfClaims
	 * @author 张建雄
	 * @Create In 2016年2月19日
	 */
	public void updateCreditorConfirm(Map<String,Object> queryMap);
	
	/**
	 * 计算总金额
	 * 2016年6月25日
	 * By 王彬彬
	 * @param queryMap
	 */
	public Map<String,Object> findSumCreditorConfirmList(Map<String,Object> queryMap);
	
	public List<CreditorConfirmEntity> exportCreditorConfirmList(Map<String,Object> queryMap);
}
