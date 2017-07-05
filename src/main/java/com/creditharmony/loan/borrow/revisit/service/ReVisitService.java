package com.creditharmony.loan.borrow.revisit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.revisit.dao.ReVisitDao;
import com.creditharmony.loan.borrow.revisit.view.RevisitAndPersonInfo;


/**
 * 回访失败列表service
 * @Class Name ReVisitService
 * @create In 2016年10月28日
 * @author songfeng
 */

@Transactional(readOnly = true,value = "loanTransactionManager")
@Service
public class ReVisitService extends CoreManager<ReVisitDao,RevisitAndPersonInfo>{
	
	
	@Autowired
	private ReVisitDao reVisitDao;
	
	/**
	 * 查询回访失败列表信息
	 * 2016年10月28日
	 * By songfeng
	 * @param 
	 * @return RevisitAndPersonInfo
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<RevisitAndPersonInfo> findRevisitAndPersonInfo(RevisitAndPersonInfo revPersonInfo,Page<RevisitAndPersonInfo> page){
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		pageBounds.setCountBy("contract_code");
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
        PageList<RevisitAndPersonInfo> pageList = (PageList<RevisitAndPersonInfo>)dao.findList(revPersonInfo, pageBounds);
        PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询导出Excel信息
	 * 2016年10月28日
	 * By songfeng
	 * @param 
	 * @return RevisitAndPersonInfo
	 */
	public List<RevisitAndPersonInfo> findExportRevisit(RevisitAndPersonInfo revPersonInfo){
		
        List<RevisitAndPersonInfo> pageList = reVisitDao.findExportList(revPersonInfo);
		return pageList;
		
	}
	
	

}
