/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.serviceGlBillService.java
 * @Create By 张灏
 * @Create In 2016年1月4日 下午7:31:10
 */
package com.creditharmony.loan.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.GlBillDao;
import com.creditharmony.loan.common.entity.GlBill;

/**
 * 账单日Service
 * @Class Name GlBillService
 * @author 张灏
 * @Create In 2016年1月4日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class GlBillService extends CoreManager<GlBillDao, GlBill> {
  
    @Autowired
    private GlBillDao glBillDao;
    
    /**
     * 查询所有的账单日 
     * @author 张灏
     * @Create In 2016年1月27日
     * @param none
     * @return List<GlBill> 
     */
    @Transactional(readOnly = true, value = "loanTransactionManager")
    public List<GlBill> findAllBillDay(){
        return glBillDao.findAllBillDay();
    
    }
}
