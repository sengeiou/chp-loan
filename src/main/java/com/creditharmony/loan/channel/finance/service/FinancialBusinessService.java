package com.creditharmony.loan.channel.finance.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.channel.finance.dao.FinancialBusinessDao;
import com.creditharmony.loan.channel.finance.entity.FinancialBusiness;
import com.creditharmony.loan.channel.finance.entity.FinancialBusinessEntity;
import com.creditharmony.loan.channel.finance.view.FinancialBusinessView;
/**
 * 大金融业务查询列表
 * @Class Name FinancialBusinessService
 * @author 张建雄
 * @Create In 2016年2月18日
 */
@Service
public class FinancialBusinessService extends CoreManager<FinancialBusinessDao, FinancialBusinessEntity> {
	@Autowired
	private FinancialBusinessDao financialBusinessDao;
	/**
	 * 获取大数据状态为（已放款，债权已确认，提前结清，结清已确认）数据列表
	 * 2015年12月2日
	 * By 张建雄
	 * @param params 查询参数
	 * @param page 分页参数
	 * @return 信借数据列表集合
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<FinancialBusinessEntity> getFinancialBusinessList(Page<FinancialBusinessEntity> page,FinancialBusinessView params) {				
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		params.setStatus(LoanStatus.EARLY_SETTLE.getCode());
		PageList<FinancialBusinessEntity> pageList = (PageList<FinancialBusinessEntity>)financialBusinessDao.getFinancialBusinessList(pageBounds, params);
		PageUtil.convertPage(pageList, page);		
		return page;
	}
	/**
	 * 插入大金融的数据信息
	 * 2016年2月20日
	 * By 张建雄
	 * @param params 查询参数
	 * @param page 分页参数
	 * 
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void insertFinancialBusiness(FinancialBusiness finance) {
		if (finance != null && StringUtils.isNotEmpty(finance.getLoanCode()))
			financialBusinessDao.insertFinancialBusiness(finance);
	}
	
	/**
	 * 计算总金额
	 * 2016年6月25日
	 * By 王彬彬
	 * @param params
	 * @return
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public Map<String,Object> getSumFinancialBusinessList(FinancialBusinessView params)
	{
		return financialBusinessDao.getSumFinancialBusinessList(params);
	}
}
