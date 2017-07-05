/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.eventContractCodeCreateEx.java
 * @Create By 张灏
 * @Create In 2015年12月22日 下午7:56:06
 */
package com.creditharmony.loan.borrow.applyinfo.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.common.service.NumberMasterService;

/**
 * @Class Name ContractCodeCreateEx
 * @author 张灏
 * @Create In 2015年12月22日
 */
@Service("ex_hj_loanFlow_contractCreate")
public class ContractCodeCreateEx extends BaseService implements ExEvent {
  
    @Autowired
    private ReconsiderApplyDao reconsiderApplyDao;
    
    @Autowired
    private ContractDao contractDao;
    
    @Autowired
    private ContractFeeDao contractFeeDao;
    
    @Autowired
    private ApplyLoanInfoDao loanInfoDao ;
    
    @Autowired
    private NumberMasterService numberMasterService;
    
    @Override
    public void invoke(WorkItemView workItemView) {
       String applyId = workItemView.getBv().getApplyId();
       String contractCode = this.getContractCode(applyId);
       Contract contract = new Contract();
       contract.setContractCode(contractCode);
       contract.setApplyId(applyId);
       contract.preInsert();
       contractDao.insertSelective(contract);
    }
   
    private String getContractCode(String applyId){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("applyId", applyId);
        List<ReconsiderApply> reconsiderApplys = reconsiderApplyDao.findReconsiderApply(param);
        String oldContractNo = null;
        String loanCode = null;
        SerialNoType serialNoType = null;
        LoanInfo loanInfo =null;
        Contract contract = null;
        String contractCode = null;
        if(!ObjectHelper.isEmpty(reconsiderApplys)){
          loanCode = reconsiderApplys.get(0).getLoanCode();
          contract = contractDao.findByLoanCode(loanCode);
          serialNoType=SerialNoType.RECONSIDE;
          
          if(!ObjectHelper.isEmpty(contract)){
              oldContractNo = contract.getContractCode();
              contractFeeDao.deleteByContractCode(oldContractNo);
              contractDao.deleteByLoanCode(loanCode);
          }
          param.put("loanCode", loanCode);
          loanInfo = loanInfoDao.selectByLoanCode(param);
        }else{
          loanInfo = loanInfoDao.selectByApplyId(param);
          loanCode = loanInfo.getLoanCode();
          contract = contractDao.findByLoanCode(loanCode); 
          serialNoType=SerialNoType.CONTRACT;
          if(!ObjectHelper.isEmpty(contract)){
             contractCode = contract.getContractCode();
             contractFeeDao.deleteByContractCode(contractCode);
             contractDao.deleteByLoanCode(loanCode);
          }
        }
        
        if(StringUtils.isEmpty((contractCode))){
          numberMasterService.getContractNumber(loanInfo, serialNoType, oldContractNo);
        }
        return contractCode;
    }
}
