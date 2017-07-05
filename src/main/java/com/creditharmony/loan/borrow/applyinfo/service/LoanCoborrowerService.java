/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.serviceLoanCoborrowerService.java
 * @Create By 张灏
 * @Create In 2016年2月23日 下午7:33:08
 */
package com.creditharmony.loan.borrow.applyinfo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;


/**
 * 共借人 Service
 * @Class Name LoanCoborrowerService
 * @author 张灏
 * @Create In 2016年2月23日
 */
@Service("loanCoborrowerService")
public class LoanCoborrowerService  extends CoreManager<LoanCoborrowerDao, LoanCoborrower>{
   
    @Autowired
    private LoanCoborrowerDao loanCoborrowerDao;
    
    /**
     *通过LoanCode查询共借人 
     *@author zhanghao
     *@Create In 2015年12月25日
     *@param loanCode
     *@return List<LoanCoborrower>
     */
    public List<LoanCoborrower> selectByLoanCode(String loanCode){
        
        return loanCoborrowerDao.selectByLoanCode(loanCode);
    }
    
    @Transactional(readOnly=false,value="loanTransactionManager")
    public void updatePaperlessMessage(Map<String,Object> param){
        loanCoborrowerDao.updatePaperlessMessage(param);
    }
    
    /**
	 * 通过loanCode查询自然人保证人信息，在签章的时候使用
	 * 2016年10月27日
	 * By 朱静越
	 * @param loanCode
	 * @return
	 */
	public LoanCoborrower getSecurityByLoanCode(String loanCode){
		return loanCoborrowerDao.getSecurityByLoanCode(loanCode);
	}
}
