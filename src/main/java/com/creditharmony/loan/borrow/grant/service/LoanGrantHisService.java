package com.creditharmony.loan.borrow.grant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantHisDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrantHis;
/**
 * 放款历史记录表的操作
 * @Class Name LoanGrantHisService
 * @author 朱静越
 * @Create In 2015年12月9日
 */
@Service("loanGrantHisService")
public class LoanGrantHisService extends CoreManager<LoanGrantHisDao, LoanGrantHis>{
	
	/**
	 * 插入放款历史记录表
	 * 2016年3月31日
	 * By 朱静越
	 * @param loanGrantHis
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int insertGrantHis(LoanGrantHis loanGrantHis){
		loanGrantHis.preInsert();
		return dao.insertGrantHis(loanGrantHis);
	}
	
	/**
	 * 更新放款历史记录表
	 * 2016年4月1日
	 * By 朱静越
	 * @param loanGrantHis
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateLoanGrantHis(LoanGrantHis loanGrantHis){
		loanGrantHis.preUpdate();
		return dao.updateLoanGrantHis(loanGrantHis);
	}
	
	/**
	 * 查找放款历史记录表
	 * 2016年5月10日
	 * By 朱静越
	 * @param contractCode
	 * @return
	 */
	public Page<LoanGrantHis> getGrantHis(Page<LoanGrantHis> page,String contractCode){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<LoanGrantHis> pageList = (PageList<LoanGrantHis>)dao.getGrantHis(pageBounds, contractCode);
		PageUtil.convertPage(pageList, page);
		return page;
	}
}
