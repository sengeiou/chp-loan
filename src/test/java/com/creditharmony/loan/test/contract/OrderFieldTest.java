/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.contractOrderFieldTest.java
 * @Create By 张灏
 * @Create In 2016年5月16日 下午3:26:15
 */
package com.creditharmony.loan.test.contract;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.dao.CustInfoDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.test.base.AbstractTestCase;

/**
 * @Class Name OrderFieldTest
 * @author 张灏
 * @Create In 2016年5月16日
 */
public class OrderFieldTest extends AbstractTestCase {
    
    @Autowired
    private ContractDao contractDao;

    @Autowired
    private CustInfoDao custInfoDao;

    @Autowired
    private ContractFeeDao contractFeeDao;

    @Autowired
    private ReconsiderApplyDao reconsiderApplyDao;

    @Autowired
    private ApplyLoanInfoDao loanInfoDao;
    @Test
    public void createOrderField(){
        String loanCode = "JK2016051300000008";
        Contract contract = new Contract();
        contract.setBackFlag("1");
        String backFlag = null;
        LoanInfo loanInfo = loanInfoDao.findStatusByLoanCode(loanCode);
        if(StringUtils.isEmpty(contract.getBackFlag())){
            backFlag  = "00";
        }else{
            backFlag = "0"+contract.getBackFlag();
        }
          
         String urgentFlag = loanInfo.getLoanUrgentFlag();
         String firstFlag = "00";  // 门店第一单标识
         String code = LoanApplyStatus.RATE_TO_VERIFY.getCode()+"-0"+urgentFlag+"-"+backFlag+"-"+firstFlag;
         OrderFiled filed = OrderFiled.parseByCode(code);
          String orderField = filed.getOrderId(); 
          if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
             orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
          }  
          System.out.println(orderField);
    }
}
