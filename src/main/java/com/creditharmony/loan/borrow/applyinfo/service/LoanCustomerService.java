/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.serviceLoanCustomerService.java
 * @Create By 张灏
 * @Create In 2015年12月19日 下午4:35:43
 */
package com.creditharmony.loan.borrow.applyinfo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanCustomer;

/**
 * 操作借款用户表
 * @Class Name LoanCustomerService
 * @author 张灏
 * @Create In 2015年12月19日
 */
@Service("loanCustomerService")
public class LoanCustomerService extends CoreManager<LoanCustomerDao, LoanCustomer> {

    @Autowired
    private LoanCustomerDao loanCustomerDao;
    
    /**
     * 提交时更新申请信息的流程Id 
     * In 2015年12月19日
     * By 张灏
     * @param param
     * @return
     */
   @Transactional(readOnly=false,value = "loanTransactionManager")
    public void updateApplyId(Map<String,Object> param){
       loanCustomerDao.updateApplyId(param);
    }
   
   @Transactional(readOnly=false,value="loanTransactionManager")
   public void updatePaperlessMessage(Map<String,Object> param){
       loanCustomerDao.updatePaperlessMessage(param);
   }
   
   /**
	 * 根据证件号查询客户
	 * @param certNum
	 * @return
	 */
	public LoanCustomer getByCertNum(String certNum){
		return loanCustomerDao.selectByCertNum(certNum);
	}
}
