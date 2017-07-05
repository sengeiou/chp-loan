package com.creditharmony.loan.borrow.payback.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.system.util.DataScopeUtil;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.loan.borrow.payback.dao.CentralizedListDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.common.utils.FilterHelper;

/**
 * 集中划扣已办、划扣已办
 * Service实现支持类
 * @Class Name CentralizedDeductionService 
 * @author 李强
 * @Create In 2015年12月4日
 */
@Service
public class CentralizedListService {
	@Autowired
	private CentralizedListDao centralizedDeductionToDao;

	/**
	 * 集中划扣已办（实时数据）
	 * 2016年7月15日
	 * By 王彬彬
	 * @param page 分页信息
	 * @param paybackApply 查询条件
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackApply> getCentralizedInfo(Page<PaybackApply> page,PaybackApply paybackApply)
	{
		try {
			String bank = paybackApply.getBank();
			if (!ObjectHelper.isEmpty(bank)) {
				paybackApply.setBankId(FilterHelper.appendIdFilter(bank));
			}
			String stores = paybackApply.getStores();
			if (!ObjectHelper.isEmpty(stores)) {
				paybackApply.setStoresId(FilterHelper.appendIdFilter(stores));
			}
			String dayName = paybackApply.getPaybackDay();
			if (!ObjectHelper.isEmpty(dayName)) {
				paybackApply
						.setPaybackDay(FilterHelper.appendIdFilter(dayName));
			}
			// 如果是电销的不进行组织机构过滤
			if(ObjectHelper.isEmpty(paybackApply.getPhoneSaleSign())){
				String queryRight = DataScopeUtil.getDataScope("jli",
						SystemFlag.LOAN.value);
				paybackApply.setQueryRight(queryRight);
			}
			

			int cnt =centralizedDeductionToDao.centerDeductionListCnt(paybackApply);
			page.setCount(cnt);
			// 执行数据库查询语句，带条件
			paybackApply.setLimit(page.getPageSize());
			if (page.getPageNo() <= 1) {
				paybackApply.setOffset(0);
			} else {
				paybackApply.setOffset((page.getPageNo() - 1)
						* page.getPageSize());
			}
			List<PaybackApply> list = centralizedDeductionToDao
					.centerDeductionList(paybackApply);
			if (ArrayHelper.isNotEmpty(list)) {
				page.setList(list);
			} else {
				list = new ArrayList<PaybackApply>();
				page.setList(list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
}
