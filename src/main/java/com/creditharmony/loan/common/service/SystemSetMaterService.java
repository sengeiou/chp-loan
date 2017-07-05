package com.creditharmony.loan.common.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.SystemSetting;

@Service
public class SystemSetMaterService extends CoreManager<SystemSetMaterDao,SystemSetting>{

    @Transactional(readOnly = false, value = "loanTransactionManager")
   public SystemSetting insertSysSetting(SystemSetting sys){
       sys.preInsert();
       dao.insert(sys);
       return sys;
   }
    
    public SystemSetting get(SystemSetting systemSetting){
    	return dao.get(systemSetting);
    }
    
    @Transactional(readOnly = false, value = "loanTransactionManager")
    public void updateBySysFlag(SystemSetting systemSetting){
    	dao.updateBySysFlag(systemSetting);
    }
}
