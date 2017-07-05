package com.creditharmony.loan.borrow.secretvisit.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.secretvisit.dao.SecretVisitDao;
import com.creditharmony.loan.borrow.secretvisit.entity.ex.SecretInfoEx;
import com.creditharmony.loan.borrow.secretvisit.entity.ex.SecretVisitEx;
import com.creditharmony.loan.borrow.stores.service.AreaService;

/**
 * 暗访信息service
 * @Class Name SecretVisitService
 * @author 王彬彬
 * @Create In 2015年12月26日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class SecretVisitService extends
		CoreManager<SecretVisitDao, SecretVisitEx> {
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private LoanCoborrowerDao loanCoborrowerDao;

	/**
	 * 查询暗访信息 2015年12月26日 By 王彬彬
	 * @param page
	 * @param filter
	 * @return 暗访信息
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<SecretVisitEx> findloaninfo(Page<SecretVisitEx> page,
			Map<String, Object> filter) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		PageList<SecretVisitEx> pageList = (PageList<SecretVisitEx>) dao
				.findSecretList(filter, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据合同编号查询期供列表
	 * 2015年12月30日
	 * By lirui
	 * @param page 分页
	 * @param contractCode 合同编号
	 * @return 期供分页列表
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<SecretInfoEx> supplyInfo(Page<SecretInfoEx> page,String contractCode) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<SecretInfoEx> pageList = (PageList<SecretInfoEx>) dao.supplyInfo(pageBounds, contractCode);
		PageUtil.convertPage(pageList, page);
		return page;		
	}
	
	/**
	 * 根据合同编号查询暗访信息
	 * 2015年12月30日
	 * By lirui
	 * @param contractCode 合同编号
	 * @return 暗访信息对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public SecretInfoEx secretInfo(String contractCode) {
		SecretInfoEx secInfo = dao.secretInfo(contractCode).get(0);
		if (StringUtils.isNotEmpty(secInfo.getLoanCode())) {
			// 获得共借人列表
			List<LoanCoborrower> loanCoborrower = loanCoborrowerDao.selectByLoanCode(secInfo.getLoanCode());			
			LaunchView launchView =new LaunchView();
			// 有共借人就把共借人姓名存成字符串形式
			if (loanCoborrower != null) {
				launchView.setLoanCoborrower(loanCoborrower);
				areaService.setCoborrowers(launchView);
			}
			// 将字符串形式的共借人存入secInfo
			if (StringUtils.isNotEmpty(launchView.getCoborrowerNames())) {
				secInfo.setCoboName(launchView.getCoborrowerNames());				
			}
		}
		return secInfo;
	}
	
	/**
	 * 通过借款编码改变暗访标识
	 * 2015年12月31日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateSecret(String loanCode) {
		dao.updateSecret(loanCode);
	}
}
