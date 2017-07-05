package com.creditharmony.loan.borrow.trusteeship.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.trusteeship.view.GoldAccountBusiness;
/**
 * 金信业务列表
 * 
 * @Class Name GoldAccountBusinessDao
 * @author 张建雄
 * @Create In 2016年2月24日
 */
@LoanBatisDao
public interface GoldAccountBusinessDao extends CrudDao<GoldAccountBusiness> {
	/**
	 *  查询金信业务列表数据信息
	 * @param pageBounds 分页参数
	 * @param params 检索参数
	 * @return 金信业务列表时数据
	 */
	public List<GoldAccountBusiness> getGoldAccountBusinessList(PageBounds pageBounds,Map<String ,Object> params);
	public List<GoldAccountBusiness> getGoldAccountBusinessList(Map<String ,Object> params);
	
}
