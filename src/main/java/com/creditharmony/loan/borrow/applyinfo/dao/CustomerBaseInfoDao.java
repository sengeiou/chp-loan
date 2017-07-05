package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
/**
 * 客户基本信息Dao
 * @Class Name CustomerBaseInfoDao
 * @author 张平
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface CustomerBaseInfoDao extends CrudDao<CustomerBaseInfo> {
    int deleteByPrimaryKey(String customerCode);

    int insert(CustomerBaseInfo record);

    int insertSelective(CustomerBaseInfo record);

    ApplyInfoFlagEx selectByPrimaryKey(String customerCode);

    int updateByPrimaryKeySelective(CustomerBaseInfo record);

    int updateByPrimaryKey(CustomerBaseInfo record);
    /**
     *新增客户基本信息 
     *@param CustomerBaseInfo 
     *@return  
     */
    public void insertCustomerBaseInfo(CustomerBaseInfo baseInfo);
    
    /**
     *更新客户基本信息 
     *@param CustomerBaseInfo
     *@return
     */
    public void updateCustomerBaseInfo(CustomerBaseInfo baseInfo);
    
    /**
     *通过身份证号查询客户基本信息
     *@param key mateCertNum 
     *@return ApplyInfoFlagEx
     */
    public ApplyInfoFlagEx findByCertNum(Map<String,Object> param);
}