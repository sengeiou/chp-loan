/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.black.serviceBlackService.java
 * @Create By 张灏
 * @Create In 2015年12月15日 上午9:54:05
 */
package com.creditharmony.loan.borrow.black.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.black.dao.BlackDao;
import com.creditharmony.loan.borrow.black.entity.Black;

/**
 * 黑名单 Service
 * @Class Name BlackService
 * @author 张灏
 * @Create In 2015年12月15日
 */
@Service("blackService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class BlackService extends CoreManager<BlackDao, Black> {

    @Autowired
    private BlackDao blackDao;
   
    /**
     *通过指定的参数查询黑名单
     *@param param
     *@return List<Black> 
     */
    public List<Black> findBlackByIdentification(Black param){
        return blackDao.findBlackByIdentification(param);
    }
}
