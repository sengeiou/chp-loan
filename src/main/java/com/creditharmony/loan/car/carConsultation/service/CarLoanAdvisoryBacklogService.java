package com.creditharmony.loan.car.carConsultation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.car.carConsultation.ex.CarLoanAdvisoryBacklogEx;
import com.creditharmony.loan.car.common.dao.CarCustomerConsultationDao;

/**
 * 车借咨询待办、车借客户管理列表
 * @Class Name CarLoanAdvisoryBacklogService
 * @author ganquan
 * @Create In 2016年1月22日
 */
@Service("CarLoanAdvisoryBacklogService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarLoanAdvisoryBacklogService {

	@Autowired
    private CarCustomerConsultationDao carCustomerConsultationDao;
	
	/**
	 * 车借咨询待办搜索
	 * */
	@Transactional(readOnly = true, value = "loanTransactionManager")
    public Page<CarLoanAdvisoryBacklogEx> selectByCarLoanAdvisoryBacklog(Page<CarLoanAdvisoryBacklogEx> page,
    		CarLoanAdvisoryBacklogEx carLoanAdvisoryBacklogEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<CarLoanAdvisoryBacklogEx> pageList = (PageList<CarLoanAdvisoryBacklogEx>) carCustomerConsultationDao.
				selectByCarLoanAdvisoryBacklog(pageBounds,carLoanAdvisoryBacklogEx);
		PageUtil.convertPage(pageList, page);
        return page;
    
    }
	
	/**
	 * 车借OCR咨询待办搜索
	 * */
	@Transactional(readOnly = true, value = "loanTransactionManager")
    public Page<CarLoanAdvisoryBacklogEx> selectOcrByCarLoanAdvisoryBacklog(Page<CarLoanAdvisoryBacklogEx> page,
    		CarLoanAdvisoryBacklogEx carLoanAdvisoryBacklogEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<CarLoanAdvisoryBacklogEx> pageList = (PageList<CarLoanAdvisoryBacklogEx>) carCustomerConsultationDao.
				selectOcrByCarLoanAdvisoryBacklog(pageBounds,carLoanAdvisoryBacklogEx);
		PageUtil.convertPage(pageList, page);
        return page;
    
    }
	
	/**
	 * 车借客户管理列表 2016年2月25日 By 陈伟东
	 * 
	 * @param carLoanAdvisoryBacklogEx
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarLoanAdvisoryBacklogEx> getCustomerManagementList(
			Page<CarLoanAdvisoryBacklogEx> page,
			CarLoanAdvisoryBacklogEx carLoanAdvisoryBacklogEx) {
		String dictOperStatus = carLoanAdvisoryBacklogEx.getDictOperStatus();
		if(dictOperStatus == null || "".equals(dictOperStatus)){
			dictOperStatus = "'" + NextStep.CONTINUE_CONFIRM + "','" + NextStep.TO_APPLY + "','" + NextStep.CUSTOMER_INTO  + "'";
		}
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		PageList<CarLoanAdvisoryBacklogEx> pageList = (PageList<CarLoanAdvisoryBacklogEx>) carCustomerConsultationDao
				.getCustomerManagementList(pageBounds, carLoanAdvisoryBacklogEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
}
