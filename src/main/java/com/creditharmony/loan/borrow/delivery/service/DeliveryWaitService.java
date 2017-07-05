package com.creditharmony.loan.borrow.delivery.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.delivery.dao.DeliveryWaitDao;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryParamsEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewEx;
/**
 * 交割待办列表
 * @Class Name DeliveryWaitService
 * @author lirui
 * @Create In 2015年12月7日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class DeliveryWaitService extends CoreManager<DeliveryWaitDao,DeliveryViewEx> {		
    
    @Autowired
    private ApplyLoanInfoDao loanInfoDao;
	/**
	 * 获得所有待办列表
	 * 2015年12月14日
	 * By lirui
	 * @param params 检索条件
	 * @param page 分页信息
	 * @return 交割待办列表信息
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<DeliveryViewEx> deliveryWait(Page<DeliveryViewEx> page,DeliveryParamsEx params) {	
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("loan_code");
		PageList<DeliveryViewEx> pageList = (PageList<DeliveryViewEx>)dao.deliveryWait(pageBounds, params);
		PageUtil.convertPage(pageList, page);		
		return page;		
	}
	
	/**
	 * 根据借款编码获得当前要办理的交割信息
	 * 2015年12月11日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 交割信息
	 */
	public DeliveryViewEx deliveryInfo(String loanCode) {
		return dao.deliveryInfo(loanCode).get(0);
	}
	
	/**
	 * 交割办理结果提交
	 * 2015年12月15日
	 * By lirui
	 * @param dv 结果参数容器
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void deliveryResult(DeliveryViewEx dv) {
		if (StringUtils.isNotEmpty(dv.getLoanCode())) {
			dv.preUpdate();
			dv.setAuditCode(dv.getModifyBy());
			dv.setCheckTime(dv.getModifyTime());
			dao.deliveryResult(dv);			
		}
	}
	
	/**
	 * 交割审核通过
	 * 2015年12月17日
	 * By lirui 
	 * @param lioanCodes 通过的借款编码
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void passDelivery(String[] loanCodes) {	
	    DeliveryViewEx dv = null;
	    DeliveryViewEx curDelivery = null;
	    LoanInfo deliveryInfo = null; 
		for (int i = 0; i < loanCodes.length; i++) {
			if (StringUtils.isNotEmpty(loanCodes[i])) {
			    curDelivery = dao.deliveryInfo(loanCodes[i]).get(0);
				dv = new DeliveryViewEx();
				dv.setLoanCode(loanCodes[i]);
				dv.preUpdate();
				dv.setAuditCode(dv.getModifyBy());
				dv.setCheckTime(dv.getModifyTime());
				deliveryInfo = new LoanInfo();
				deliveryInfo.setLoanCode(loanCodes[i]);
				deliveryInfo.setLoanStoreOrgId(curDelivery.getNewStoresId());
				deliveryInfo.setLoanManagerCode(curDelivery.getNewManagercode());
				deliveryInfo.setLoanCustomerService(curDelivery.getNewCustomerServicescode());
				deliveryInfo.setLoanSurveyEmpId(curDelivery.getNewOutboundBy());
				deliveryInfo.setLoanTeamManagerCode(curDelivery.getNewTeamManagercode());
				dao.passDelivery(dv);	
				loanInfoDao.update(deliveryInfo);
			}
		}
	}
	
	/**
	 * 交割申请驳回(批量用)
	 * 2015年12月17日
	 * By lirui
	 * @param loanCode 借款编码
	 * @param reason 驳回原因
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void regected(String[] loanCodes,String reason) {		
		for (int i = 0; i < loanCodes.length; i++) {
			if (StringUtils.isNotEmpty(loanCodes[i])) {
				DeliveryViewEx dv = new DeliveryViewEx();
				dv.setRejectedReason(reason);
				dv.setLoanCode(loanCodes[i]);
				dv.preUpdate();
				dv.setAuditCode(dv.getModifyBy());
				dv.setCheckTime(dv.getModifyTime());
				dao.regected(dv);
			}
		}
	}
}
