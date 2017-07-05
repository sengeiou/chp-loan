package com.creditharmony.loan.borrow.reconsider.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;

/**
 * 复议发起 Dao
 * @Class Name ReconsiderExDao
 * @author 张灏
 * @Create In 2015年12月25日
 * @version 1.0
 */
@LoanBatisDao
public interface ReconsiderApplyDao extends CrudDao<ReconsiderApply>{
    
    /**
     * 新增复议记录
     * @author 张灏
     * @Create In 2015年12月25日
     * @param record
     * @return int
     * 
     */
    int insert(ReconsiderApply record);
  
    /**
     * 选择新增复议记录
     * @author 张灏
     * @Create In 2015年12月25日
     * @param record
     * @return int
     * 
     */
    int insertSelective(ReconsiderApply record);
  
    /**
     *查询复议次数 
     * @author 张灏
     * @Create In 2015年12月25日
     * @param param key applyId
     * @return List<ReconsiderApply>
     * 
     */
    public List<ReconsiderApply> findReconsiderApply(Map<String,Object> param);
    
    /**
     * 查询复议申请信息
     * @author zhanghao
     * @Create In 2016年04月08日
     * @param param
     * @return ReconsiderApply
     * 
     */
     public ReconsiderApply selectByParam(Map<String,Object> param);
     
     /**
      * 根据applyID获取数据
      * 2016年5月19日
      * By 李文勇
      * @param applyId
      * @return
      */
     public ReconsiderApply selectByApply(String applyId);

}