package com.creditharmony.loan.borrow.grant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.dao.UrgeCheckDoneDao;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeCheckDoneEx;
import com.creditharmony.loan.channel.jyj.entity.UrgeCheckJYJDoneEx;


@Service("urgeCheckDoneService")
public class UrgeCheckDoneService extends CoreManager<UrgeCheckDoneDao ,UrgeCheckDoneEx> {
	
	/**
	 * 催收服务费查账已办列表初始化
	 * 2016年3月2日
	 * By 朱静越
	 * @param page
	 * @param urgeCheckDoneEx
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<UrgeCheckDoneEx> selCheckDone(Page<UrgeCheckDoneEx> page,UrgeCheckDoneEx urgeCheckDoneEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<UrgeCheckDoneEx> pageList = (PageList<UrgeCheckDoneEx>)dao.selCheckDone(pageBounds, urgeCheckDoneEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据查询条件查询
	 * 2016年3月3日
	 * By 朱静越
	 * @param urgeCheckDoneEx
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<UrgeCheckDoneEx> queryDoneList(UrgeCheckDoneEx urgeCheckDoneEx){
		return dao.selCheckDone(urgeCheckDoneEx);
	}

	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<UrgeCheckJYJDoneEx> selCheckJYJDone(Page<UrgeCheckJYJDoneEx> page,UrgeCheckJYJDoneEx urgeCheckDoneEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<UrgeCheckJYJDoneEx> pageList = (PageList<UrgeCheckJYJDoneEx>)dao.selCheckJYJDone(pageBounds, urgeCheckDoneEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<UrgeCheckJYJDoneEx> queryDoneJYJList(UrgeCheckJYJDoneEx urgeCheckDoneEx){
		return dao.selCheckJYJDone(urgeCheckDoneEx);
	}
}
