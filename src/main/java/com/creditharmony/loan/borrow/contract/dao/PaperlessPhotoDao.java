/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.daoPaperlessPhotoDao.java
 * @Create By 张灏
 * @Create In 2016年4月22日 下午5:07:34
 */
package com.creditharmony.loan.borrow.contract.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;

/**
 * 无纸化合同图片数据库操作层
 * @Class Name PaperlessPhotoDao
 * @author 张灏
 * @Create In 2016年4月22日
 */
@LoanBatisDao
public interface PaperlessPhotoDao  extends CrudDao<PaperlessPhoto>{
   
    /**
     *通过关联ID获取paperlessPhoto信息 
     *@author zhanghao
     *@create in 2016年04月22日
     *@param param relationId
     *@return PaperlessPhoto
     * 
     */
    public PaperlessPhoto getByRelationId(Map<String,String> param);
    
    /**
     *通过关联ID更新paperlessPhoto信息 
     *@author zhanghao
     *@create in 2016年04月22日
     *@param paperlessPhoto
     *@return none
     * 
     */
    public void updateByRelationId(PaperlessPhoto paperlessPhoto);
    
    /**
     *新增paperlessPhoto信息 
     *@author zhanghao
     *@create in 2016年04月22日
     *@param paperlessPhoto
     *@return none
     * 
     */
    public void insertPaperlessPhoto(PaperlessPhoto paperlessPhoto);
    
    /**
     *通过loanCode获取所有的图片缓存信息 
     *@author zhanghao
     *@Create In 2016年04月23日
     *@param loanCode
     *@return List<PaperlessPhoto>
     * 
     */
    public List<PaperlessPhoto> getByLoanCode(String loanCode);
}
