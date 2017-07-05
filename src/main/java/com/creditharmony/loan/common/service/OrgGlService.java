package com.creditharmony.loan.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.TreeManager;
import com.creditharmony.loan.common.dao.OrgGlDao;
import com.creditharmony.loan.common.entity.OrgGl;

/**
 * 组织机构管理Service
 * 
 * @Class Name OrgManager
 * @author 王彬彬
 * @Create In 2015年12月4日
 */
@Service
public class OrgGlService extends TreeManager<OrgGlDao, OrgGl> {

	/**
	 * 组织结构分页查询 2015年12月7日 
	 * By 王彬彬
	 * 
	 * @param page
	 *            分页信息
	 * @param filter
	 *            查询调价
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<OrgGl> findOrg(Page<OrgGl> page, Map<String, Object> filter) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		PageList<OrgGl> pageList = (PageList<OrgGl>) dao.findByParams(filter,
				pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 门店插入
	 * 2015年12月8日
	 * By 王彬彬
	 * @param org
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insert(OrgGl org) {
		dao.insert(org);
	}

	/**
	 * 获取组织机构
	 * 2016年4月16日
	 * By 王彬彬
	 * @param orgId 机构ID
	 * @return 组织机构
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public OrgGl getOrgByOrgid(String orgId)
	{
		return dao.get(orgId);
	}
}
