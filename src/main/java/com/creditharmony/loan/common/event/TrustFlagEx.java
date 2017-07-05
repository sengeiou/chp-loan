/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.eventTrustFlagEx.java
 * @Create By 张灏
 * @Create In 2016年4月8日 下午5:59:47
 */
package com.creditharmony.loan.common.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.borrow.transate.dao.LoanMinuteDao;

/**
 * 委托标识更新
 * @Class Name TrustFlagEx
 * @author 张灏
 * @Create In 2016年4月8日
 */
@Service("ex_hj_loanflow_trustFlag")
public class TrustFlagEx extends BaseService implements ExEvent {
    
    private static final String OPERATION_TRUST_FLAG = "3";
   
    @Autowired
    private LoanMinuteDao loanMinuteDao;
    /* (non-Javadoc)
     * @see com.creditharmony.bpm.frame.face.ExEvent#invoke(com.creditharmony.bpm.frame.view.WorkItemView)
     */
    @Override
    public void invoke(WorkItemView workItem) {
       String loanCode = null;
       String operType = null;
       String trustFlag = null;
        if(workItem.getBv() instanceof ContractBusiView){
            ContractBusiView contractBusiView =(ContractBusiView)workItem.getBv();
            loanCode = contractBusiView.getLoanCode();
            operType = contractBusiView.getOperType();
            trustFlag = contractBusiView.getTrustFlag();
        }else if(workItem.getBv() instanceof GrantDealView){
            GrantDealView grantDealView = (GrantDealView)workItem.getBv();
            loanCode = grantDealView.getLoanCode();
            operType = grantDealView.getOperateType();
            trustFlag = grantDealView.getTrustFlag();
        }
        if(OPERATION_TRUST_FLAG.equals(operType)){
            if(YESNO.NO.getCode().equals(trustFlag)){
                loanMinuteDao.updateTrustCash(loanCode);
            }else if(YESNO.YES.getCode().equals(trustFlag)){
                loanMinuteDao.updateTrustRecharge(loanCode);
            }
        }
    }

}
