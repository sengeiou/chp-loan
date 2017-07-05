package com.creditharmony.loan.common.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.OutsideCheckInfo;

/**
 * 外访信息dao层
 * @Class Name OutsideCheckInfoDao
 * @author 张灏
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface OutsideCheckInfoDao extends CrudDao<OutsideCheckInfo>{
   
   /**
    *通过主键删除外访信息 
    * @author 张灏
    * @Create In 2015年12月3日
    * @param id 主键ID 
    * @return int 
    */
   public int deleteByPrimaryKey(String id);

   /**
    *新增外访信息 
    * @author 张灏
    * @Create In 2015年12月3日
    * @param  record
    * @return int 
    * 
    */
   public int insertOutsideCheck(OutsideCheckInfo record);
   
   /**
    *新增字段不为空的外访信息  
    * @author 张灏
    * @Create In 2015年12月3日
    * @param  record
    * @return int  
    */
   public int insertSelective(OutsideCheckInfo record);

   /**
    *通过主键查询外访信息 
    * @author 张灏
    * @Create In 2015年12月3日
    * @param id 
    * @return OutsideCheckInfo
    */
   public OutsideCheckInfo selectByPrimaryKey(String id);
    
   /**
    *通过借款编号查询外访信息 
    * @author 张灏
    * @Create In 2015年12月3日
    * @param loanCode 
    * @return List<OutsideCheckInfo>
    * 
    */
   public List<OutsideCheckInfo> selectByLoanCode(String loanCode);
    
   /**
    *通过主键更新不为空的外访信息 
    * @author 张灏
    * @Create In 2015年12月3日
    * @param  record
    * @return int 
    * 
    */
   public int updateByPrimaryKeySelective(OutsideCheckInfo record);
   
   /**
    *通过主键更新外访信息 
    * @author 张灏
    * @Create In 2015年12月3日
    * @param record 
    * @return int 
    * 
    */
   public int updateByPrimaryKey(OutsideCheckInfo record);
   
    /**
     * 查询最大的外访距离
     * @author 张灏
     * @Create In 2015年12月3日
     * @param loanCode
     * @return List<OutsideCheckInfo>
     */
   public List<OutsideCheckInfo> selectMaxDistance(String loanCode);
}