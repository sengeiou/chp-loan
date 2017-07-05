/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.daoGuaranteeRegisterDao.java
 * @Create By 张灏
 * @Create In 2016年5月21日 下午2:56:03
 */
package com.creditharmony.loan.borrow.contract.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.GuaranteeRegister;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;


/**
 * 保证人信息
 * @Class Name GuaranteeRegisterDao
 * @author 张灏
 * @Create In 2016年5月21日
 */
@LoanBatisDao
public interface GuaranteeRegisterDao extends CrudDao<GuaranteeRegister> {

    
    public GuaranteeRegister getFromApproveById(String relateId);

	public Map getRegisterByloancode(Contract contractBusiView);
}
