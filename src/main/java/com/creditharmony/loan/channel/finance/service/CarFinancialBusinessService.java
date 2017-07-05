package com.creditharmony.loan.channel.finance.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;
import com.creditharmony.loan.channel.finance.dao.CarFinancialBusinessDao;
import com.creditharmony.loan.channel.finance.dao.FinancialBusinessDao;
import com.creditharmony.loan.channel.finance.entity.CarFinancialBusiness;
import com.creditharmony.loan.channel.finance.entity.CarFinancialBusinessEntity;
import com.creditharmony.loan.channel.finance.entity.FinancialBusinessEntity;
import com.creditharmony.loan.channel.finance.view.CarFinancialBusinessView;
/**
 * 大金融业务查询列表
 * @Class Name FinancialBusinessService
 * @author 张建雄
 * @Create In 2016年2月18日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarFinancialBusinessService extends CoreManager<FinancialBusinessDao, FinancialBusinessEntity> {
	@Autowired
	private CarFinancialBusinessDao carFinancialBusinessDao;
	/**
	 * 获取大数据状态为（已放款，债权已确认，提前结清，结清已确认）数据列表
	 * 2015年12月2日
	 * By 张建雄
	 * @param params 查询参数
	 * @param page 分页参数
	 * @return 信借数据列表集合
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<CarFinancialBusinessEntity> getFinancialBusinessList(Page<CarFinancialBusinessEntity> page,CarFinancialBusinessView params) {				
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<CarFinancialBusinessEntity> pageList = (PageList<CarFinancialBusinessEntity>)carFinancialBusinessDao.getCarFinancialBusinessList(pageBounds, params);
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
	public void insertFinancialBusiness(CarFinancialBusiness finance) {
		if (finance != null && StringUtils.isNotEmpty(finance.getLoanCode()))
			carFinancialBusinessDao.insertCarFinancialBusiness(finance);
	}
}
