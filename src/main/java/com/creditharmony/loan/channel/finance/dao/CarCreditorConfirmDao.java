package com.creditharmony.loan.channel.finance.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.channel.finance.entity.CreditorConfirmEntity;
@LoanBatisDao
public interface CarCreditorConfirmDao extends CrudDao<CreditorConfirmEntity> {
	
	public List<CreditorConfirmEntity> findCarCreditorConfirmList(PageBounds pageBounds,Map<String,Object> queryMap);
	
	/**
	 * 导出EXCEL查询列表
	 * @param queryMap
	 * @return
	 */
	public List<CreditorConfirmEntity> findCarCreditorConfirmList(Map<String,Object> queryMap);
	
	/**
	 * 更新选中的债权确认列表状态
	 * @Class Name updateSettlementOfClaims
	 * @author 陈伟丽
	 * @Create In 2016年5月17日
	 */
	public void updateCarCreditorConfirm(Map<String,Object> queryMap);
	
	/**
	 * 导出债权确认列表
	 * @param queryMap
	 * @return
	 */
	public List<CreditorConfirmEntity> exportCarCreditorConfirmList(Map<String,Object> queryMap);
}
