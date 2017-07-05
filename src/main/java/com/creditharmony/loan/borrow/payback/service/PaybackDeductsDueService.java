package com.creditharmony.loan.borrow.payback.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.dao.PaybackDeductsDueDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeductsDue;

/**
 * 预约划扣列表业务Service
 * @Class Name PaybackDeductsDueService
 * @author zhaojinping
 * @Create In 2015年12月16日
 */
@Service
@Transactional(value = "loanTransactionManager", readOnly = true)
public class PaybackDeductsDueService {

	@Autowired
	private PaybackDeductsDueDao paybackDeductsDueDao;
	
	/**
	 * 查询预约划扣时间列表 
	 * 2015年12月12日
	 * By zhaojinping
	 * @param page
	 * @param paramMap
	 * @return page
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public Page<PaybackDeductsDue> getDeductsDue(Page<PaybackDeductsDue> page,PaybackDeductsDue paybackDeductsDue){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		paybackDeductsDue.setCreateDayStr(sdf.format(new Date()));
		PageList<PaybackDeductsDue> pageList = (PageList<PaybackDeductsDue>)paybackDeductsDueDao.getDeductsDue(pageBounds,paybackDeductsDue);
		List<Dict> dictList = DictCache.getInstance().getList();
		for (PaybackDeductsDue payback : pageList) {
			for(Dict dict:dictList){
				if("jk_open_bank".equals(dict.getType()) && dict.getValue()!=null && dict.getValue().equals(payback.getDueBank())){
					payback.setDueBankLabel(dict.getLabel());
				}
			}
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询预约划扣时间列表 
	 * 2015年12月12日
	 * By zhaojinping
	 * @param page
	 * @param paramMap
	 * @return page
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public List<PaybackDeductsDue> getDeductsDue(PaybackDeductsDue paybackDeductsDue){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		paybackDeductsDue.setCreateDayStr(sdf.format(new Date()));
		List<PaybackDeductsDue> list = paybackDeductsDueDao.getDeductsDue(paybackDeductsDue);
		for (PaybackDeductsDue payback : list) {
			payback.setDueBankLabel(DictCache.getInstance().getDictLabel("jk_open_bank", payback.getDueBank()));
		}
		return list;
	}
	
	/**
	 * 将预约划扣的时间置为有效
	 * 2016年1月8日
	 * By zhaojinping
	 * @param paybackDeductsDue
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateUse(PaybackDeductsDue paybackDeductsDue){
		paybackDeductsDueDao.updateUse(paybackDeductsDue);
	}
	
	/**
	 * 将预约划扣的时间置为无效
	 * 2016年1月8日
	 * By zhaojinping
	 * @param paybackDeductsDue
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void updateUnUse(PaybackDeductsDue paybackDeductsDue){
		paybackDeductsDueDao.updateUnUse(paybackDeductsDue);
	}
	
	/**
	 * 新增预约划扣时间
	 * 2015年12月12日
	 * By zhaojinping
	 * @param dueBank 银行id
	 * @param dueDateStr 预约日期
	 * @param dueTimeStr  预约时间
	 * @return none
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void addDue(String dueBank, String dueDateStr, String userId, String[] dueTimeStr){
		String[] bankIds = dueBank.split(",");
		for(int x = 0;x < bankIds.length;x++){
			if(bankIds[x]!=null&&!"".equals(bankIds[x])){
				for (int i = 0; i < dueTimeStr.length; i++) {
					String schedule = dueDateStr + " " + dueTimeStr[i] + ":00";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date scheduleDate = sdf.parse(schedule);
						PaybackDeductsDue paybackDeductsDue = new PaybackDeductsDue();
						paybackDeductsDue.setDueTime(scheduleDate);
						paybackDeductsDue.setDueBank(bankIds[x]);
						paybackDeductsDue.setCreateBy(userId);
						paybackDeductsDue.setModifyBy(userId);
						paybackDeductsDue.setIsExecute("0");
						//paybackDeductsDue.setCreateTime(new Date());
						paybackDeductsDue.setEffectiveFlag(YESNO.YES.getCode());
						paybackDeductsDue.preInsert();
						paybackDeductsDueDao.addDue(paybackDeductsDue);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				}
			}
		}
	}

	/**
	 * 查询这个时间是否已经预约了
	 * 2016年2月23日
	 * By wengsi
	 * @param dueBank
	 * @param dueDateStr
	 * @param dueTimeStr
	 * @return 预约列表
	 */
	public List<PaybackDeductsDue> selectIsAppointment(
			PaybackDeductsDue paybackDeductsDue) {
		return paybackDeductsDueDao.getDeductsDue(paybackDeductsDue);
	}

	/**
	 * 增加金账户预约
	 * @param modeWay
	 * @param dueDateStr
	 * @param userId
	 * @param dueTimeArray
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void addGoldAccountDue(String modeWay, String dueDateStr, String userId,String[] dueTimeArray) {
		for (int i = 0; i < dueTimeArray.length; i++) {
			String schedule = dueDateStr + " " + dueTimeArray[i] + ":00";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date scheduleDate = sdf.parse(schedule);
				PaybackDeductsDue paybackDeductsDue = new PaybackDeductsDue();
				paybackDeductsDue.setDueTime(scheduleDate);
				paybackDeductsDue.setCreateBy(userId);
				paybackDeductsDue.setModifyBy(userId);
				paybackDeductsDue.setIsExecute("0");
				paybackDeductsDue.setModeWay(modeWay);
				//paybackDeductsDue.setCreateTime(new Date());
				paybackDeductsDue.setEffectiveFlag(YESNO.YES.getCode());
				paybackDeductsDue.preInsert();
				paybackDeductsDueDao.addDue(paybackDeductsDue);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
