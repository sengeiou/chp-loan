package com.creditharmony.loan.borrow.contract.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.SplitDao;
import com.creditharmony.loan.borrow.contract.entity.Split;

/**
 * 信息占比表Service 
 * @Class Name ContractService
 * @create In 2015年12月1日
 * @author 申阿伟
 */
@Service("splitService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class SplitService extends CoreManager<SplitDao, Split>{

	@Autowired
	private SplitDao splitDao;
	
	
	
	/**
	 * 查询当前有效占比
	 * 2017年02月20日
	 * By 申阿伟
	 * @return Split
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Split findBySplit(){
		return splitDao.findBySplit();
	}
	/**
	 * 占比历史
	 * 2017年02月20日
	 * By 申阿伟
	 * @return List<Split>
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<Split> findBySplitHis(Split split,Page<Split> page){
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
        PageList<Split> pageList = (PageList<Split>)splitDao.findList(split, pageBounds);
        PageUtil.convertPage(pageList, page);
		return page;
	}
	
}
