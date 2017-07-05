package com.creditharmony.loan.borrow.zcj.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.zcj.view.ZcjEntity;

/**
 * 大金融资产家
 * @Class Name ZcjDao
 * @author wujj
 * @Create In 2016年8月24日
 */
@LoanBatisDao
public interface ZcjDao extends CrudDao<ZcjEntity> {
	
	public List<ZcjEntity> getFinanceInfo(PageBounds pageBounds,ZcjEntity zcj);
	
	public List<ZcjEntity> getConfirmInfo(ZcjEntity zcj);
	
	public List<ZcjEntity> getAmountSum(ZcjEntity zcj);
	
	public List<Map<String,Integer>> getRecommend(Map map);
	
	public List<Map<String,String>> getIsFrozenInfo(String contractCode);
	
}
