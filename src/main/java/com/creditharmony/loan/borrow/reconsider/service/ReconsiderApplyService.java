/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.reconsider.serviceReconsiderApplyService.java
 * @Create By 张灏
 * @Create In 2015年12月25日 下午2:43:12
 */
package com.creditharmony.loan.borrow.reconsider.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderExDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.borrow.reconsider.entity.ex.ReconsiderEx;

/**
 * 复议发起 Service
 * @Class Name ReconsiderApplyService
 * @author 张灏
 * @Create In 2015年12月25日
 */
@Service("reconsiderApplyService")
@Transactional(readOnly=true,value="")
public class ReconsiderApplyService extends
        CoreManager<ReconsiderApplyDao, ReconsiderApply> {
    
    @Autowired
    private ReconsiderExDao reconsiderExDao;
    
    @Autowired
    private LoanCoborrowerDao coborrowerDao;
    /**
     *获取复议发起待办列表 
     * @author 张灏
     * @Create In 2015年12月25日
     * @param param 检索条件封装类 
     * @return List<ReconsiderEx>
     * 
     */
    public List<ReconsiderEx> queryReconsiderList(ReconsiderEx param){
        List<ReconsiderEx> reconsiderList = null;
        reconsiderList = reconsiderExDao.findList(param);
        return reconsiderList;
    }
    
   /**
    * 查询复议申请信息
    * @author zhanghao
    * @Create In 2016年04月08日
    * @param param loanCode 借款编号
    * @return ReconsiderApply
    * 
    */
    public ReconsiderApply selectByParam(Map<String,Object> param){
        return dao.selectByParam(param);
    }
}
