/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.daoRateInfoDao.java
 * @Create By 张灏
 * @Create In 2016年4月13日 上午9:56:01
 */
package com.creditharmony.loan.borrow.contract.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.VerificationInfo;

/**
 * 身份验证操作dao层
 * @Class Name RateInfoDao
 * @author 宋锋
 * @Create In 2016年11月28日
 */
@LoanBatisDao
public interface VerificationDao extends CrudDao<VerificationInfo> {
    /**
     * 插入身份验证失败信息
     * @param verificationInfo
     */
    public void insertSelective(VerificationInfo verificationInfo);
    /**
     * 修改验证次数
     * @param verificationInfo
     */
    public void updateVer(VerificationInfo verificationInfo);
}
