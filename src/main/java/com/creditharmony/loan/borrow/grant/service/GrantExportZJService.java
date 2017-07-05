/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.serviceGrantExportService.java
 * @Create By 张灏
 * @Create In 2016年2月25日 下午4:42:10
 */
package com.creditharmony.loan.borrow.grant.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.dao.GrantExportZJDao;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantExportZJEx;

/**
 * 放款导出Service
 * @Class Name GrantExportService
 * @author 张灏
 * @Create In 2016年2月25日
 */
@Service

public class GrantExportZJService extends CoreManager<GrantExportZJDao, GrantExportZJEx>{
   
    @Autowired
    private GrantExportZJDao grantExportDao;
    
    @Transactional(readOnly = true, value = "loanTransactionManager")
    public List<GrantExportZJEx> findGrantInfo(Map<String,Object> param){
        
        return grantExportDao.findGrantInfo(param);
    }
}
