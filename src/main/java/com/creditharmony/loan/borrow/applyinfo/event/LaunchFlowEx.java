/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.eventLaunchFlowEx.java
 * @Create By 张灏
 * @Create In 2015年12月17日 下午8:32:47
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
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.lend.type.LoanAccountType;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.NextStep;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.consult.dao.ConsultDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.OrderFiled;

/**
 * @Class Name LaunchFlowEx
 * @author 张灏
 * @Create In 2015年12月17日
 */
@Service("ex_hj_loanFlow_launch")
public class LaunchFlowEx extends BaseService implements ExEvent {

    @Autowired
    private ApplyLoanInfoDao applyLoanInfoDao;
    
    @Autowired
    private LoanCustomerDao loanCustomerDao;
    
    @Autowired
    private ConsultDao consultDao;
    
    @Autowired
    private PaybackDao paybackDao;
   
    @Autowired
    private LoanStatusHisDao loanStatusHisDao;
    /**
     *发起后，流程事件回调方法
     *插入applyId
     *更改咨询状态 
     *@param WorkItemView
     *@return 
     * 
     */
    @Override
    public void invoke(WorkItemView workItem) {
        LaunchView launchView =  (LaunchView) workItem.getBv();
        LoanInfo loanInfo = launchView.getLoanInfo();
        LoanCustomer loanCustomer = launchView.getLoanCustomer();
        Map<String,Object> param = new HashMap<String,Object>();
        Map<String,Object> payBackParam = new HashMap<String,Object>();
        Map<String,Object> operationParam = new HashMap<String,Object>();
        String applyId = launchView.getApplyId();
        String loanCode = loanInfo.getLoanCode(); 
        String customerCode = loanCustomer.getCustomerCode();
        Payback payback = new Payback();
        operationParam.put("consultId", loanInfo.getRid());
        operationParam.put("tagConsultStatus",NextStep.CUSTOMER_INTO.getCode());
        param.put("applyId",applyId);
        param.put("loanCode",loanCode);
        param.put("dictLoanStatus", LoanApplyStatus.INFORMATION_UPLOAD.getCode());
        payback.setCustomerCode(customerCode);
        payback.setEffectiveFlag(YESNO.YES.getCode());
        payBackParam.put("customerCertNum", loanCustomer.getCustomerCertNum());
        payBackParam.put("effectiveFlag", YESNO.YES.getCode());
        List<Payback> paybacks = paybackDao.findByIdentityCode(payBackParam);
        String dictLoanType = null;
        String dictLoanTypeName = null;
        Integer loanNum = 0;
        if(ObjectHelper.isEmpty(paybacks)){
            dictLoanType = LoanAccountType.LOAN_TYPE_FIRST.getCode();
            dictLoanTypeName = LoanAccountType.LOAN_TYPE_FIRST.getName();
        }else{
            dictLoanType = LoanAccountType.LOAN_TYPE_AGAIN.getCode();
            dictLoanTypeName = LoanAccountType.LOAN_TYPE_AGAIN.getName();
            loanNum = paybacks.size();
        }
        param.put("dictLoanType",dictLoanType);
        param.put("loanNum", loanNum);
        launchView.setDictLoanType(dictLoanType);
        launchView.setDictLoanTypeName(dictLoanTypeName);
        LoanStatusHis loanStatus = new LoanStatusHis();
        loanStatus.preInsert();
        loanStatus.setApplyId(applyId);
        loanStatus.setLoanCode(loanCode);
        loanStatus.setDictLoanStatus(LoanApplyStatus.LANUCH.getCode());
        loanStatus.setOperateStep(LoanApplyStatus.LANUCH.getName());
        loanStatus.setRemark("成功");
        loanStatus.setOperateResult("成功");
        // 系统标识
        loanStatus.setDictSysFlag(ModuleName.MODULE_LOAN.value);
        // 操作时间
        loanStatus.setOperateTime(loanStatus.getCreateTime());
        User user = UserUtils.getUser();
        if (user != null) {
            loanStatus.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
            if (!ObjectHelper.isEmpty(user)) {
                loanStatus.setOperateRoleId(user.getId());// 操作人角色
            }
            if (!ObjectHelper.isEmpty(user.getDepartment())) {
                loanStatus.setOrgCode(user.getDepartment().getId()); // 机构编码
            }
        }
        String statusCode = LoanApplyStatus.INFORMATION_UPLOAD.getCode()+"-0"+launchView.getLoanInfo().getLoanUrgentFlag()+"-00";
        OrderFiled filed=OrderFiled.parseByCode(statusCode);         
        launchView.setOrderField(filed.getOrderId()+"-"+DateUtils.formatDate(
                launchView.getLoanInfo().getCustomerIntoTime(), "yyyy-MM-dd HH:mm:ss"));
        loanStatusHisDao.insertSelective(loanStatus);
        applyLoanInfoDao.updateApplyIdAndStatus(param);
        loanCustomerDao.updateApplyId(param);
        consultDao.updateConsultStatus(operationParam);
        workItem.setBv(launchView);
    }

}
