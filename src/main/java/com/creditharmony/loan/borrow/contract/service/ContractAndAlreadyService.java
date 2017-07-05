package com.creditharmony.loan.borrow.contract.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.ContractAndAlreadyDao;
import com.creditharmony.loan.borrow.contract.entity.ContractAndAlready;


/**
 * 我的已办Service 
 * @Class Name ContractPersonService
 * @create In 2016年3月4日
 * @author 尚军伟
 */

@Service("contractAndAlreadyService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class ContractAndAlreadyService extends CoreManager<ContractAndAlreadyDao,ContractAndAlready>{
	
	@Autowired
	private ContractAndAlreadyDao caaDao;
	
	
	/**
	 * 查询已办借款人信息
	 * 2016年11月2日
	 * By 申阿伟
	 * @param 
	 * @return ContractAndAlready
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<ContractAndAlready> findContractAndAlready(ContractAndAlready contractndalready,Page<ContractAndAlready> page){
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		pageBounds.setCountBy("loan_code");
		if(contractndalready.getAuditingTimeStart()!=null && !"".equals(contractndalready.getAuditingTimeStart())){
			contractndalready.setAuditingTimeStart(contractndalready.getAuditingTimeStart()+":00:00");
		}
		if(contractndalready.getAuditingTimeEnd()!=null && !"".equals(contractndalready.getAuditingTimeEnd())){
			contractndalready.setAuditingTimeEnd(contractndalready.getAuditingTimeEnd()+":59:59");
		}
        PageList<ContractAndAlready> pageList = (PageList<ContractAndAlready>)caaDao.findList(contractndalready, pageBounds);
        PageUtil.convertPage(pageList, page);
        if(contractndalready.getAuditingTimeStart()!=null && !"".equals(contractndalready.getAuditingTimeStart())){
			contractndalready.setAuditingTimeStart(contractndalready.getAuditingTimeStart().substring(0, 13));
		}
		if(contractndalready.getAuditingTimeEnd()!=null && !"".equals(contractndalready.getAuditingTimeEnd())){
			contractndalready.setAuditingTimeEnd(contractndalready.getAuditingTimeEnd().substring(0, 13));
		}
		return page;
	}
	
}
