package com.creditharmony.loan.borrow.payback.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.dao.PhoneSaleHandleDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PhoneSale;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;

/**
 * 电销还款提醒待办service
 * @Create In 2017年3月3日
 * @author 翁私
 *
 */
@Service
@Transactional(value = "loanTransactionManager", readOnly = true)
public class PhoneSaleHandleService {

	@Autowired
	private  PhoneSaleHandleDao dao;
	
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	
	/**
	 * 查询分页查询电销还款待办数据
	 * @param page
	 * @param sale
	 * @return
	 */
	public Page<PhoneSale> phoneSaleHandle(Page<PhoneSale> page, PhoneSale sale) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<PhoneSale> pageList = (PageList<PhoneSale>)dao.queryPage(pageBounds,sale);
		List<Dict> dictList = DictCache.getInstance().getList();
		for (PhoneSale paybackList : pageList) {
			for(Dict dict:dictList){
				if("jk_open_bank".equals(dict.getType())&&dict.getValue().equals(paybackList.getApplyBankName())){
					paybackList.setApplyBankNameLabel(dict.getLabel());
				}
				if("jk_loan_status".equals(dict.getType())&&dict.getValue().equals(paybackList.getDictLoanStatus())){
					paybackList.setDictLoanStatusLabel(dict.getLabel());
				}
				if("jk_channel_flag".equals(dict.getType())&&dict.getValue().equals(paybackList.getMark())){
					paybackList.setMarkLabel(dict.getLabel());
				}
				if("jk_deduct_plat".equals(dict.getType())&&dict.getValue().equals(paybackList.getDictDealType())){
					paybackList.setDictDealTypeLabel(dict.getLabel());
				}
			}
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 标记提醒状态
	 * @param bean
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void signRemindupdate(PhoneSale bean) {
		String ids = bean.getIds();
		if(!ObjectHelper.isEmpty(ids)){
			bean.setIdList(Arrays.asList(ids.split(",")));
		}
		List<PhoneSale> plist = dao.queryList(bean);
		for(PhoneSale  sale : plist){
			sale.preUpdate();
			sale.setStatus(bean.getRemindStatusFlag());
		    dao.signRemindupdate(sale);
		    insertPaybackOpe(sale);
		}
	}
	
	/**
	 * 插入历史
	 * @param bean
	*/
	public void insertPaybackOpe(PhoneSale bean) {
		PaybackOpe paybackOpe = new PaybackOpe();
		paybackOpe.setrPaybackApplyId(bean.getId());
		if("1".equals(bean.getStatus())){
			paybackOpe.setDictLoanStatus("已提醒");
		}
		if("2".equals(bean.getStatus())){
			paybackOpe.setDictLoanStatus("提醒失败");
		}
		paybackOpe.preInsert();
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		dao.insertRemindOpe(paybackOpe);
	}

	public Page<PaybackOpe> getPaybackRemindOpe(String id,
			Page<PaybackOpe> page) {
		Map<String, String> filter = new HashMap<String, String>();
		if (StringUtils.isNotEmpty(id) && id.length()>0) {
			filter.put("rPaybackApplyId", id);
		}
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		List<PaybackOpe> opeList = dao.getPaybackRemindOpe(filter,pageBounds);
		PageList<PaybackOpe> pageList = (PageList<PaybackOpe>) opeList;
		PageUtil.convertPage(pageList, page);
		return page;	
	}
}
