/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.serviceOtherFeeService.java
 * @Create By 张灏
 * @Create In 2015年12月3日 下午4:08:07
 */
package com.creditharmony.loan.borrow.contract.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.common.dao.OutsideCheckInfoDao;
import com.creditharmony.loan.common.entity.OutsideCheckInfo;

/**
 * 外访信息服务类
 * @Class Name OutsideCheckInfoService
 * @author 张灏
 * @Create In 2015年12月3日
 */
@Service("outsideCheckInfoService")
public class OutsideCheckInfoService extends CoreManager<OutsideCheckInfoDao, OutsideCheckInfo>{
  
    @Autowired
    private OutsideCheckInfoDao outsideCheckInfoDao;
    
    /**
     *通过贷款Code查询外访信息 
     *@author zhanghao
     *@Create In 2016年2月1日
     *@param loanCode 借款编号
     *@return List<OutsideCheckInfo> 
     *
     */
    @Transactional(readOnly=true,value = "loanTransactionManager")
    public List<OutsideCheckInfo> selectByLoanCode(String loanCode){
       
        return outsideCheckInfoDao.selectByLoanCode(loanCode);
    }
    
    /**
     *通过loanCode 查询最大的外访距离 
     *@author zhanghao
     *@Create In 2016年2月1日
     *@param loanCode 
     *@return List<OutsideCheckInfo>
     */
    @Transactional(readOnly=true,value = "loanTransactionManager")
    public List<OutsideCheckInfo> selectMaxDistance(String loanCode){
        
        return outsideCheckInfoDao.selectMaxDistance(loanCode);
   
    }
}
