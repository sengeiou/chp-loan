/**
 * 催收服务费操作历史
 */
package com.creditharmony.loan.car.carGrant.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeOpeEx;
import com.creditharmony.loan.car.common.dao.CarUrgeOpeDao;
import com.creditharmony.loan.car.common.entity.CarUrgeOpe;

@Service
public class CarUrgeHistoryService extends CoreManager<CarUrgeOpeDao, CarUrgeOpe>{

	/**
	 * 插入催收服务费操作流水历史
	 * 2016年6月22日
	 * By 朱静越
	 * @param paybackOpeEx
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertUrgeOpe(CarUrgeOpeEx paybackOpeEx) {
		CarUrgeOpe paybackOpe = new CarUrgeOpe();
		paybackOpe.setrUrgeId(paybackOpeEx.getrUrgeId());
		paybackOpe.setDictLoanStatus(paybackOpeEx.getDictLoanStatus());
		paybackOpe.setDictRDeductType(paybackOpeEx.getDictRDeductType());
		paybackOpe.setRemarks(paybackOpeEx.getRemark());
		paybackOpe.setOperateResult(paybackOpeEx.getOperateResult());
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		paybackOpe.preInsert();
		dao.insert(paybackOpe);
	}

	/**
	 * 催收服务费操作历史修改
	 * 2016年6月22日
	 * By 朱静越
	 * @param rUrgeId 催收id
	 * @param targetWay 关联类型
	 * @param page
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarUrgeOpe> getUrgeOpe(String rUrgeId,
			TargetWay targetWay, Page<CarUrgeOpe> page) {

		Map<String, String> filter = new HashMap<String, String>();
		
		if (StringUtils.isNotEmpty(rUrgeId)) {
			filter.put("rUrgeId", rUrgeId);
		}
		if(!ObjectHelper.isEmpty(targetWay)){
			filter.put("dictRDeductType", targetWay.getCode());
		}

		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		
		List<CarUrgeOpe> opeList = dao.getUrgeOpe(filter,
				pageBounds);
		for(CarUrgeOpe ope: opeList){
			if (StringUtils.isNotEmpty(ope.getOperateResult())) {
				ope.setOperateResult(PaybackOperate.parseByCode(ope.getOperateResult()).getName());
			}
			if (StringUtils.isNotEmpty(ope.getDictLoanStatus())) {
			ope.setDictLoanStatus(RepaymentProcess.parseByCode(ope.getDictLoanStatus()).getName());
			}
		}

		PageList<CarUrgeOpe> pageList = (PageList<CarUrgeOpe>) opeList;
		PageUtil.convertPage(pageList, page);

		return page;
	}
}