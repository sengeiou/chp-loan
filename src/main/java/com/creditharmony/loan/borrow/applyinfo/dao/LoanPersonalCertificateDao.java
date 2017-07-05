package com.creditharmony.loan.borrow.applyinfo.dao;

import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanPersonalCertificate;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
/**
 * 客户证件信息Dao
 * @Class Name LoanPersonalCertificateDao
 * @author 卢建学
 * @Create In 2016年09月13日
 */
@LoanBatisDao
public interface LoanPersonalCertificateDao extends CrudDao<LoanPersonalCertificate>{
   
    int insert(LoanPersonalCertificate record);

    int update(LoanPersonalCertificate record);

    LoanPersonalCertificate findByLoanCode(String loanCode);
	
}