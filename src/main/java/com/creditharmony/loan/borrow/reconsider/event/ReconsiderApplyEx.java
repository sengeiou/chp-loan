/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.reconsider.eventReconsiderApplyEx.java
 * @Create By 张灏
 * @Create In 2015年12月24日 下午6:32:12
 */
package com.creditharmony.loan.borrow.reconsider.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.borrow.reconsider.view.ReconsiderBusinessView;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.service.HistoryService;

/**
 * @Class Name ReconsiderApplyEx
 * @author 张灏
 * @Create In 2015年12月24日
 */
@Service("ex_hj_reconsiderApply_launch")
public class ReconsiderApplyEx extends BaseService implements ExEvent {
  
   @Autowired
   private ReconsiderApplyDao reconsiderApplyDao;
   
   @Autowired
   private  HistoryService historyService;
   
   @Autowired
   private ApplyLoanInfoDao loanInfoDao;
   
   @Autowired
   private LoanCustomerDao loanCustomerDao;
   
   @Autowired
   private ContractDao contractDao;
   
   @Autowired
   private LoanStatusHisDao loanStatusHisDao;
   
   @Override
    public void invoke(WorkItemView workItem){
       
       ReconsiderBusinessView view = (ReconsiderBusinessView) workItem.getBv();
       String applyId = view.getApplyId();
       ReconsiderApply record = view.getReconsiderApply();
       record.preInsert();
       reconsiderApplyDao.insert(record);
       LoanInfo loanInfo = new LoanInfo();
       loanInfo.setLoanCode(record.getLoanCode());
       String dictLoanStatus = LoanApplyStatus.RECONSIDER_CHECK.getCode();
       loanInfo.setDictLoanStatus(dictLoanStatus);
       loanInfo.setVisitFlag(" ");
       loanInfo.setNodeFlag(view.getNodeFlag());
       loanInfo.preUpdate();
       loanInfoDao.updateLoanInfo(loanInfo);
      /* Map<String,String> param = new HashMap<String,String>();
       param.put("applyId", applyId);
       param.put("oldApplyId", view.getOldApplyId());
       loanInfoDao.updateApplyIdByOldApplyId(param);
       loanCustomerDao.updateApplyIdByOldApplyId(param);
       contractDao.updateApplyIdByOldApplyId(param);*/
       
       LoanStatusHis loanStatus = new LoanStatusHis();
       loanStatus.preInsert();
       loanStatus.setApplyId(applyId);
       loanStatus.setLoanCode(record.getLoanCode());
       loanStatus.setDictLoanStatus(LoanApplyStatus.LANUCH_RE.getCode());
       loanStatus.setOperateStep(LoanApplyStatus.LANUCH_RE.getName());
       loanStatus.setRemark(record.getSecondReconsiderMsg());
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
       loanStatusHisDao.insertSelective(loanStatus);
   }

}
