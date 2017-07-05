package com.creditharmony.loan.common.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.TreeDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.OrgGl;

/**
 * 
 * @Class Name OrgGlDao
 * @author 王彬彬
 * @Create In 2015年12月7日
 */
@LoanBatisDao
public interface OrgGlDao extends TreeDao<OrgGl> {

	/**
	 * 条件查询组织机构数据(分页) 2015年12月7日 By 王彬彬
	 * 
	 * @param params
	 *            查询条件
	 * @param pageBounds
	 *            分页参数
	 * @return
	 */
	public List<OrgGl> findByParams(Map<String, Object> params,
			PageBounds pageBounds);

	/**
	 * 根据查询条件加载组织机构树 2015年12月7日 By 王彬彬
	 * 
	 * @param params
	 *            查询条件
	 * @return
	 */
	public List<OrgGl> queryByParams(Map<String, Object> params);
}
