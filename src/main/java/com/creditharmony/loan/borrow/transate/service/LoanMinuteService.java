package com.creditharmony.loan.borrow.transate.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.transate.dao.LoanMinuteDao;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanMinuteEx;
/**
 * 信借审批信息详情Dao
 * @Class Name LoanMinuteService
 * @author lirui
 * @Create In 2015年12月3日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class LoanMinuteService extends CoreManager<LoanMinuteDao,LoanMinuteEx> {
	/**
	 * 根据信借编号获取信借审批信息详情
	 * 2015年12月14日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 信借数据对象
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public LoanMinuteEx loanMinute(String loanCode) {		
		return dao.loanMinute(loanCode);
	}
	
	/**
	 * 更新委托提现标识
	 * 2016年3月15日
	 * By 朱杰
	 * @param loanCode
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateTrustCash(String loanCode){
		dao.updateTrustCash(loanCode);
	}
	
	/**
	 * 更新委托充值标识
	 * 2016年3月15日
	 * By 朱杰
	 * @param loanCode
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateTrustRecharge(String loanCode){
		dao.updateTrustRecharge(loanCode);
	}
	
	public List<Map<String,Object>> getCoborrower(String loanCode,String loanInfoOldOrNewFlag){
		if("1".equals(loanInfoOldOrNewFlag)){//最优自然人
			return dao.getCoborrowerOne(loanCode);
		}else{
			return dao.getCoborrower(loanCode);
		}
	}
	
	public List<Map<String,Object>> getSendEMail(String loanCode,String fileType,String status){
		Map map = new HashMap();
		map.put("loanCode", loanCode);
		map.put("fileType",fileType);
		map.put("status",status);
		return dao.getSendEMail(map);
	}
	
	public List<Map<String,Object>> getSend(String loanCode,String fileType){
		Map map = new HashMap();
		map.put("loanCode", loanCode);
		map.put("fileType",fileType);
		return dao.getSend(map);
	}
	
}
