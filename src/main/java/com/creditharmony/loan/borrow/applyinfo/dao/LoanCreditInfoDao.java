package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCreditInfo;

/**
 * 信用资料信息Dao
 * @Class Name LoanCreditInfoDao
 * @author zhangping
 * @Create In 2015年12月3日
 */
@LoanBatisDao
public interface LoanCreditInfoDao extends CrudDao<LoanCreditInfo> {
   
    /**
     * 通过主键删除信用资料 
     * @author zhanghao
     * @Create In 2015年12月3日
     * @param id
     * @return int
     * 
     */
    public int deleteByPrimaryKey(String id);

    /**
     * 新增信用资料 
     * @author zhanghao
     * @Create In 2015年12月3日
     * @param record
     * @return int
     * 
     */
    public int insert(LoanCreditInfo record);

    /**
     * 选择新增信用资料
     * @author zhanghao
     * @Create In 2015年12月3日
     * @param record
     * @return  int
     * 
     */
    public int insertSelective(LoanCreditInfo record);

    /**
     * 通过主键查询信用资料
     * @author zhanghao
     * @Create In 2015年12月3日
     * @param id
     * @return LoanCreditInfo
     * 
     */
    public LoanCreditInfo selectByPrimaryKey(String id);

    /**
     * 通过主键选择更新信用资料
     * @author zhanghao
     * @Create In 2015年12月3日
     * @param record
     * @return int 
     */
    public int updateByPrimaryKeySelective(LoanCreditInfo record);

    /**
     * 通过主键全部更新信用资料
     * @author zhanghao
     * @Create In 2015年12月3日
     * @param record
     * @return int
     */
    public int updateByPrimaryKey(LoanCreditInfo record);

    /**
     * 通过LoanCode查询所有的信用资料
     * @author zhanghao
     * @Create In 2015年12月3日
     * @param loanCode
     * @return List<LoanCreditInfo>
     */
    public List<LoanCreditInfo> findListByLoanCode(String loanCode);

    /**
     * 通过指定参数删除信用资料
     * @author zhanghao
     * @Create In 2015年12月3日
     * @param param
     * @return none
     */
	public void deleteByCondition(Map<String,Object> param);
}