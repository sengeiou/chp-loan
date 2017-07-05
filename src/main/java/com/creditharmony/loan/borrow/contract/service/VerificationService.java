/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.serviceRateInfoService.java
 * @Create By 张灏
 * @Create In 2016年4月13日 上午10:34:00
 */
package com.creditharmony.loan.borrow.contract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.VerificationDao;
import com.creditharmony.loan.borrow.contract.entity.VerificationInfo;

/**
 * 身份验证Service层
 * @Class Name RateInfoService
 * @author 宋锋
 * @Create In 2016年11月28日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class VerificationService extends CoreManager<VerificationDao, VerificationInfo>{
  
	@Autowired
	private VerificationDao verificationDao;
    
    @Transactional(readOnly = false,value = "loanTransactionManager")
    public void insertVerification(VerificationInfo verificationInfo){
    		verificationInfo.preInsert();
    		verificationDao.insertSelective(verificationInfo);
    }
    /**
     * 修改验证次数
     * @param verificationInfo
     */
    @Transactional(readOnly = false,value = "loanTransactionManager")
    public void updateVer(VerificationInfo verificationInfo){
    	verificationDao.updateVer(verificationInfo);
    }
}
