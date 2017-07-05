package com.creditharmony.loan.channel.bigfinance.event;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.google.common.collect.Maps;


/**
 * 大金融债权退回列表操作添加历史，同时同步数据库
 * @Class Name BigFinanceCreditChangeEx
 * @author 朱静越
 * @Create In 2016年8月24日
 */
@Service("ex_jx_loanflow_bigFinanceCredit")
public class BigFinanceCreditChangeEx extends BaseService implements ExEvent {
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private ContractDao contractDao;

	/**
	 * 大金融债权退回列表处理
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		GrantDealView gdv = (GrantDealView) workItem.getBv();
		// 只有大金融的调用这个业务处理
		if (ChannelFlag.ZCJ.getName().equals(gdv.getLoanFlag())) {
			if (StringUtils.isNotEmpty(gdv.getDictLoanStatusCode())) {
				Map<String,Object> loanStatusByLoanCode = Maps.newHashMap();
				loanStatusByLoanCode.put("dictLoanStatus", gdv.getDictLoanStatusCode());
				loanStatusByLoanCode.put("loanCode", gdv.getLoanCode());
				loanInfoDao.updateLoanStatus(loanStatusByLoanCode);
			}
			// 大金融债权退回到合同审核
			if (LoanFlowRoute.GOLDCREDIT_TO_CONTRACT_CHECK.equals(workItem.getResponse())) {
				saveLoanStatusHis(gdv.getApplyId(),gdv.getLoanCode(), gdv.getDictLoanStatusCode(),"大金融债权退回到合同审核", "成功", gdv.getGrantSureBackReason());
				updateContract(gdv.getApplyId(), gdv.getGrantSureBackReason());
			// 大金融债权到返款待确认
			} else if (LoanFlowRoute.GOLDCREDIT_TO_CONFIRM.equals(workItem.getResponse())){
				saveLoanStatusHis(gdv.getApplyId(),gdv.getLoanCode(),gdv.getDictLoanStatusCode(), "大金融返回到待款项确认", "成功", gdv.getGrantSureBackReason());
			// 驳回申请
			} else if(StringUtils.isNotEmpty(gdv.getFlagStatus())){
				saveLoanStatusHis(gdv.getApplyId(), gdv.getLoanCode(), gdv.getFlagStatus(), gdv.getFlagStatus(), "驳回成功", "");
				// 更新数据库 将loan_info 表中的冻结code和冻结原因更新为空。
				LoanInfo loanInfo=new LoanInfo();
				loanInfo.setLoanCode(gdv.getLoanCode());
				loanInfo.setFrozenReason(gdv.getFrozenReason());
		        loanInfo.setFrozenCode(" ");
		        loanInfo.setFrozenLastApplyTime(new Date());
		        loanGrantDao.updateFlag(loanInfo);
			}
		}
	}
	
	/**
	 * 更新合同表中的最后一次退回原因
	 * @param applyId applyId
	 * @param returnReson 退回原因
	 */
	private  void updateContract(String applyId,String returnReson){
		Contract contract = contractDao.findByApplyId(applyId);
        if (StringUtils.isNotEmpty(returnReson)) {
            contract.setContractBackResult(returnReson);
            contract.setBackFlag(YESNO.YES.getCode());
            contractDao.updateContract(contract);
        }  
	}
	
	/**
	 * 添加操作历史 2016年2月17日 By 王彬彬
	 * 
	 * @param applyId
	 *            
	 * @param loanCode 借款编号
	 * @param operateStep
	 *            操作步骤
	 * @param operateResult
	 *            操作结果
	 * @param remark
	 *            备注
	 * @return
	 */
	private int saveLoanStatusHis(String applyId,String loanCode,String dictLoanStatus, String operateStep,
			String operateResult, String remark) {
		LoanStatusHis record = new LoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setApplyId(applyId);
		// 借款编号
		record.setLoanCode(loanCode);
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(operateStep);
		// 操作结果
		record.setOperateResult(operateResult);
		// 备注
		record.setRemark(remark);
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		//借款状态
		record.setDictLoanStatus(dictLoanStatus);
		User user = UserUtils.getUser();
		if (user != null) {
			record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
			if (!ObjectHelper.isEmpty(user)) {
				record.setOperateRoleId(user.getId());// 操作人角色
			}
			if (!ObjectHelper.isEmpty(user.getDepartment())) {
				record.setOrgCode(user.getDepartment().getId()); // 机构编码
			}
		}
		return loanStatusHisDao.insertSelective(record);
	}
}