package com.creditharmony.loan.channel.finance.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.channel.finance.entity.FinancialBusiness;
import com.creditharmony.loan.channel.finance.entity.FinancialBusinessEntity;
import com.creditharmony.loan.channel.finance.view.FinancialBusinessView;

/**
 * 大金融业务查询
 * @Class Name FinancialBusinessDao
 * @author 张建雄
 * @Create In 2016年2月18日
 */
@LoanBatisDao
public interface FinancialBusinessDao extends CrudDao<FinancialBusinessEntity> {
	/**
	 * 查询符合条件的大金融数据列表信息
	 * @param pageBounds 检索参数
	 * @param params 分页数据
	 * @return 数据信息列表集合
	 */
	public List<FinancialBusinessEntity> getFinancialBusinessList(PageBounds pageBounds,FinancialBusinessView params);
	/**
	 * 向数据库中插入大金融的数据信息
	 */
	public void insertFinancialBusiness(FinancialBusiness finance);
	
	/**
	 * 计算总金额
	 * 2016年6月25日
	 * By 王彬彬
	 * @param params
	 * @return 
	 */
	public Map<String,Object> getSumFinancialBusinessList(FinancialBusinessView params);
}
