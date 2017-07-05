package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanRemark;

/**
 * 借款备注信息Dao
 * @Class Name LoanRemarkDao
 * @author zhanghao
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface LoanRemarkDao extends CrudDao<LoanRemark> {
  
    /**
     *新增备注信息 
     *@author zhanghao 
     *@Create In 2016年2月17日
     *@param record
     *@return none 
     *
     */
    public void insertRemark(LoanRemark record);
    
    /**
     *根据ID选择更新备注信息 
     *@author zhanghao
     *@Create In 2016年2月17日 
     *@param record 
     *@return  none  
     */
    public void updateByIdSelective(LoanRemark record);
    
    /**
     *跟据借款编号查询备注信息 
     *@author zhanghao
     *@Create In 2016年2月17日 
     *@param param
     *@return  List<LoanRemark> 
     */
    public List<LoanRemark> findByLoanCode(Map<String,Object> param);
}