package com.creditharmony.loan.borrow.transate.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.service.ContractFileService;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.borrow.reconsider.service.ReconsiderApplyService;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanMinuteEx;
import com.creditharmony.loan.borrow.transate.service.LoanInfoService;
import com.creditharmony.loan.borrow.transate.service.LoanMinuteService;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.service.ImageService;
/**
 * 信借审批信息详情
 * @Class Name LoanMinuteController
 * @author lirui
 * @Create In 2015年12月3日
 */
@Controller
@RequestMapping(value="${adminPath}/borrow/transate")
public class LoanMinuteController extends BaseController {
	
    private static final String OPERATION_TRUST_FLAG = "3";
	@Autowired
	private LoanMinuteService lms;
	
	@Autowired
	private LoanInfoService ls;
	
	@Autowired 
	private ImageService imageService;
	/**
     * FlowService 查询流程待办列表、提交流程
     */
    @Resource(name = "appFrame_flowServiceImpl")
    private FlowService flowService;
    
    @Autowired
    private ReconsiderApplyService reconsiderApplyService;
    
    @Autowired
    private ContractFileService contractFileService;
	
	/**
	 * 信借审批信息详情
	 * 2015年12月21日
	 * By lirui
	 * @param loanCode 借款编码
	 * @param m Model模型
	 * @return 详情页面 
	 */
	@RequestMapping(value = "loanMinute")
	public String loanMinute(String loanCode,String status,Model m,String query,Boolean isManager) {
		List<Map<String,Object>> list = lms.getCoborrower(loanCode,null);
		LoanMinuteEx loanMinute = lms.loanMinute(loanCode);
		loanMinute.setLoanCode(loanCode);
		// 缓存取码值
		loanMinute.setCustomerCertTypeLabel(DictCache.getInstance().getDictLabel("com_certificate_type", loanMinute.getCustomerCertType()));
		loanMinute.setCoroCertTypeLabel(DictCache.getInstance().getDictLabel("com_certificate_type", loanMinute.getCoroCertType()));
		loanMinute.setDictRepayMethodLabel(DictCache.getInstance().getDictLabel("jk_repay_interest_way", loanMinute.getDictRepayMethod()));	
		// 存入Model
		m.addAttribute("status", status);
		m.addAttribute("lm", loanMinute);
		m.addAttribute("cob", list);
		// TODO 借款状态获取
		LoanInfo lf=this.ls.findStatusByLoanCode(loanCode);	
		String stepName = "";
        User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
        String orgId = currentUser.getDepartment().getId();
        Org org = OrgCache.getInstance().get(orgId);
        String orgType = org != null ? org.getType() : "";
        if (LoanOrgType.TEAM.key.equals(orgType)){
            stepName = FlowStep.TEAM_MANAGER_IMAGE_VIEW.getName();
        }else{
            stepName = FlowStep.IMAGE_VIEW.getName();
        }
		String url = imageService.getImageUrl(stepName, loanCode);
		Map<String,Object> param = new HashMap<String,Object>();
        param.put("loanCode",loanCode);
        List<ContractFile> files = contractFileService.getContractFileByParam(param);
        if(!ObjectHelper.isEmpty(files)){
           String protoColUrl = imageService.getImageUrl(FlowStep.PROTOCOL_VIEW.getName(), loanCode);
           m.addAttribute("protoColUrl", protoColUrl);
           m.addAttribute("procBtn", "1"); 
        }else{
            m.addAttribute("procBtn", "0");  
        }
		m.addAttribute("isManager", isManager);
		m.addAttribute("query", query);
		m.addAttribute("url",url);
		m.addAttribute("dictLoanStatus", lf.getDictLoanStatus());
		m.addAttribute("trustCashEnable", this.isEnableTrustCash(lf.getDictLoanStatus()));
		m.addAttribute("trustRechargeEnable", this.isEnableTrustRecharge(lf.getDictLoanStatus()));
		return "transate/loanMinute";
	}
	
	/**
	 * 委托提现标识变更
	 * 2016年3月15日
	 * By 朱杰
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "updateTrustCash")
	@ResponseBody
	public String updateTrustCash(String loanCode,String applyId,String dictLoanStatusCode,String trustCash){
	    Map<String,Object> queryParam = new HashMap<String,Object>();
        queryParam.put("loanCode", loanCode);
	    ReconsiderApply  reconsiderApply = reconsiderApplyService.selectByParam(queryParam);
        if(!ObjectHelper.isEmpty(reconsiderApply)){
            applyId = reconsiderApply.getApplyId();
        }
        if(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode().equals(dictLoanStatusCode)||                        // 放款明细确认
             LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode().equals(dictLoanStatusCode)||                   // 分配卡号
               LoanApplyStatus.LOAN_TO_SEND.getCode().equals(dictLoanStatusCode)||                         // 待放款
                 LoanApplyStatus.LOAN_SEND_AUDITY.getCode().equals(dictLoanStatusCode)||                   // 待放款审核
                   LoanApplyStatus.LOAN_SEND_AUDITYRETURN.getCode().equals(dictLoanStatusCode)||           // 放款审核退回
                     LoanApplyStatus.LOAN_DEALED.getCode().equals(dictLoanStatusCode)||                    // 已处理
                           LoanApplyStatus.KING_TO_OPEN.getCode().equals(dictLoanStatusCode)               // 金账户待开户
                                                                                            ){           
            // 查询流程数据
            WorkItemView srcWorkItem = flowService.loadWorkItemViewForAdmin(applyId);
            GrantDealView gdv = new GrantDealView();
            gdv.setTrustCash(trustCash);
            gdv.setLoanCode(loanCode);
            // 设置操作类型为2，表明更新资金托管标识
            gdv.setOperateType(OPERATION_TRUST_FLAG);    
            gdv.setTrustFlag(YESNO.NO.getCode());
            srcWorkItem.setBv(gdv);
            flowService.saveDataForAdmin(srcWorkItem);
        }else if(LoanApplyStatus.LOAN_SEND_RETURN.getCode().equals(dictLoanStatusCode)                    // 放款退回
                    ||LoanApplyStatus.KING_RETURN.getCode().equals(dictLoanStatusCode)){                   // 金账户退回         
            // 查询流程数据
            WorkItemView srcWorkItem = flowService.loadWorkItemViewForAdmin(applyId);
            ContractBusiView gdv = new ContractBusiView();
            gdv.setTrustCash(trustCash);
            gdv.setLoanCode(loanCode);
            // 设置操作类型为2，表明更新资金托管标识
            gdv.setOperType(OPERATION_TRUST_FLAG);    
            gdv.setTrustFlag(YESNO.NO.getCode());
            srcWorkItem.setBv(gdv);
            flowService.saveDataForAdmin(srcWorkItem);
        }else if(LoanApplyStatus.LOAN_SENDED.getCode().equals(dictLoanStatusCode)                             // 已放款
                  /* || LoanApplyStatus.REPAYMENT.getCode().equals(dictLoanStatusCode)                          // 还款中
                      || LoanApplyStatus.OVERDUE.getCode().equals(dictLoanStatusCode)                         // 逾期
                        || LoanApplyStatus.SETTLE_CONFIRM.getCode().equals(dictLoanStatusCode)                // 结清待确认      
                             || LoanApplyStatus.EARLY_SETTLE.getCode().equals(dictLoanStatusCode)             // 提前结清
                                  || LoanApplyStatus.EARLY_SETTLE_VERIFY.getCode().equals(dictLoanStatusCode) // 提前结清待审核
                                       || LoanApplyStatus.SETTLE.getCode().equals(dictLoanStatusCode)*/){       // 结清     
            lms.updateTrustCash(loanCode);
        }
		
		return jsonMapper.toJson("");
	}
	
	/**
	 * 委托充值标识变更
	 * 2016年3月15日
	 * By 朱杰
	 * @param applyId
	 * @return
	 */
	@RequestMapping(value = "updateTrustRecharge")
	@ResponseBody
	public String updateTrustRecharge(String loanCode,String applyId,String dictLoanStatusCode,String trustRecharge){
	    Map<String,Object> queryParam = new HashMap<String,Object>();
        queryParam.put("loanCode", loanCode);
        ReconsiderApply  reconsiderApply = reconsiderApplyService.selectByParam(queryParam);
        if(!ObjectHelper.isEmpty(reconsiderApply)){
            applyId = reconsiderApply.getApplyId();
        }
        if(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode().equals(dictLoanStatusCode)||            // 放款明细确认
             LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode().equals(dictLoanStatusCode)||       // 分配卡号
               LoanApplyStatus.LOAN_TO_SEND.getCode().equals(dictLoanStatusCode)||                         // 待放款
                 LoanApplyStatus.LOAN_SEND_AUDITY.getCode().equals(dictLoanStatusCode)||                   // 待放款审核
                   LoanApplyStatus.LOAN_SEND_AUDITYRETURN.getCode().equals(dictLoanStatusCode)||           // 放款审核退回
                     LoanApplyStatus.LOAN_DEALED.getCode().equals(dictLoanStatusCode)||                    // 已处理
                       LoanApplyStatus.KING_TO_OPEN.getCode().equals(dictLoanStatusCode)                // 金账户待开户
                                                                                         ){             
            // 查询流程数据
            WorkItemView srcWorkItem = flowService.loadWorkItemViewForAdmin(applyId);
            GrantDealView gdv = new GrantDealView();
            gdv.setLoanCode(loanCode);
            gdv.setTrustRecharge(trustRecharge);
            // 设置操作类型为1，表明更新资金托管标识
            gdv.setOperateType(OPERATION_TRUST_FLAG);   
            gdv.setTrustFlag(YESNO.YES.getCode());
            srcWorkItem.setBv(gdv);
            flowService.saveDataForAdmin(srcWorkItem);  
        }else if(LoanApplyStatus.LOAN_SEND_RETURN.getCode().equals(dictLoanStatusCode)                    // 放款退回
                ||LoanApplyStatus.KING_RETURN.getCode().equals(dictLoanStatusCode)){                      // 金账户退回                    
            // 查询流程数据
            WorkItemView srcWorkItem = flowService.loadWorkItemViewForAdmin(applyId);
            ContractBusiView gdv = new ContractBusiView();
            gdv.setLoanCode(loanCode);
            gdv.setTrustRecharge(trustRecharge);
            // 设置操作类型为2，表明更新资金托管标识
            gdv.setOperType(OPERATION_TRUST_FLAG);    
            gdv.setTrustFlag(YESNO.YES.getCode());
            srcWorkItem.setBv(gdv);
            flowService.saveDataForAdmin(srcWorkItem);
        }else if(LoanApplyStatus.LOAN_SENDED.getCode().equals(dictLoanStatusCode)                             // 已放款
                   || LoanApplyStatus.REPAYMENT.getCode().equals(dictLoanStatusCode)                          // 还款中
                     || LoanApplyStatus.OVERDUE.getCode().equals(dictLoanStatusCode)                          // 逾期
                        || LoanApplyStatus.SETTLE_CONFIRM.getCode().equals(dictLoanStatusCode)                // 结清待确认      
                             || LoanApplyStatus.EARLY_SETTLE.getCode().equals(dictLoanStatusCode)             // 提前结清
                                  || LoanApplyStatus.EARLY_SETTLE_VERIFY.getCode().equals(dictLoanStatusCode) // 提前结清待审核
                                       || LoanApplyStatus.SETTLE.getCode().equals(dictLoanStatusCode)){       // 结清     
            lms.updateTrustRecharge(loanCode);
        }
 		return jsonMapper.toJson("");
	}
	/**
	 * 判断可否变更委托充值标识。 1为可变更
	 * 2016年3月15日
	 * By 朱杰
	 * @return
	 */
	private String isEnableTrustRecharge(String status){
		if (LoanApplyStatus.LOAN_SEND_CONFIRM.getCode().equals(status)
				|| LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode().equals(status)
				|| LoanApplyStatus.LOAN_TO_SEND.getCode().equals(status)
				|| LoanApplyStatus.LOAN_SEND_AUDITY.getCode().equals(status)
				|| LoanApplyStatus.LOAN_SEND_AUDITYRETURN.getCode().equals(status)
				|| LoanApplyStatus.LOAN_DEALED.getCode().equals(status)
				|| LoanApplyStatus.LOAN_SENDED.getCode().equals(status)
				|| LoanApplyStatus.LOAN_SEND_RETURN.getCode().equals(status)
				|| LoanApplyStatus.REPAYMENT.getCode().equals(status)
				|| LoanApplyStatus.OVERDUE.getCode().equals(status)
				|| LoanApplyStatus.SETTLE_CONFIRM.getCode().equals(status)
				|| LoanApplyStatus.EARLY_SETTLE.getCode().equals(status)
				|| LoanApplyStatus.EARLY_SETTLE_VERIFY.getCode().equals(status)
				|| LoanApplyStatus.SETTLE.getCode().equals(status)
				|| LoanApplyStatus.KING_TO_OPEN.getCode().equals(status)
				|| LoanApplyStatus.KING_RETURN.getCode().equals(status)) {
			// 待放款确认，待分配放款，待放款，待放款审核，待审核退回，放款中，已放款，放款退回，
			//还款中，逾期，结清待审核，提前结清，结清，金账户开户，金账户开户退回
			return "1";
		}
		return "0";
	}
	
	/**
	 * 判断可否变更委托提现标识。1为可变更
	 * 2016年3月15日
	 * By 朱杰
	 * @return
	 */
	private String isEnableTrustCash(String status){
		if (LoanApplyStatus.LOAN_SEND_CONFIRM.getCode().equals(status)
				|| LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode().equals(status)
				|| LoanApplyStatus.LOAN_TO_SEND.getCode().equals(status)
				|| LoanApplyStatus.LOAN_SEND_AUDITY.getCode().equals(status)
				|| LoanApplyStatus.LOAN_SEND_AUDITYRETURN.getCode().equals(status)
				|| LoanApplyStatus.LOAN_DEALED.getCode().equals(status)
				|| LoanApplyStatus.LOAN_SENDED.getCode().equals(status)
				|| LoanApplyStatus.LOAN_SEND_RETURN.getCode().equals(status)
				|| LoanApplyStatus.KING_TO_OPEN.getCode().equals(status)
				|| LoanApplyStatus.KING_RETURN.getCode().equals(status)) {
			// 待放款确认，待分配放款，待放款，待放款审核，待审核退回，放款中，已放款，放款退回，金账户开户，金账户开户退回
			return "1";
		}
		return "0";
	}
}
