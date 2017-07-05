/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.loanagainLoanType.java
 * @Create By 张灏
 * @Create In 2016年5月18日 下午2:24:42
 */
package com.creditharmony.loan.test.loanagain;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.lend.type.LoanAccountType;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.test.base.AbstractTestCase;

/**
 * @Class Name LoanType
 * @author 张灏
 * @Create In 2016年5月18日
 */
public class LoanType extends AbstractTestCase {

    @Autowired
    private PaybackDao paybackDao;
    @Test
    public void loanType(){
        String customerCode = "JKKH20160512000035";
        Payback payback = new Payback();
        payback.setCustomerCode(customerCode);
        List<Payback> paybacks = paybackDao.findByCustomerCode(payback);
        String dictLoanType = null;
        String dictLoanTypeName = null;
        if(ObjectHelper.isEmpty(paybacks)){
            dictLoanType = LoanAccountType.LOAN_TYPE_FIRST.getCode();
            dictLoanTypeName = LoanAccountType.LOAN_TYPE_FIRST.getName();
        }else{
            dictLoanType = LoanAccountType.LOAN_TYPE_AGAIN.getCode();
            dictLoanTypeName = LoanAccountType.LOAN_TYPE_AGAIN.getName();
        }
        System.out.println("dictLoanType : "+dictLoanType);
        System.out.println("dictLoanTypeName : "+dictLoanTypeName);
    }
}
