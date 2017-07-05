package com.creditharmony.loan.channel.goldcredit.event;

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
 * 金信添加历史数据信息
 * @Class Name GrantLoanChangeEx
 * @author 张建雄
 * @Create In 2016年3月15日
 */
@Service("ex_jx_loanflow_goldCredit")
public class GoldCreditChangeEx extends BaseService implements ExEvent {
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private ContractDao contractDao;
	/**
	 * 数据从金信债权退回列表中返回到金信待放款确认列表中是写的留根记录
	 * 修改数据库中的字段信息
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		GrantDealView gdv = (GrantDealView) workItem.getBv();
		// 标识不等于大金融的进行调用
		if (!ChannelFlag.ZCJ.getName().equals(gdv.getLoanFlag())) {
			LoanInfo loanInfo=new LoanInfo();
			loanInfo.setApplyId(gdv.getApplyId());
			Map<String,Object> loanStatusByLoanCode = Maps.newHashMap();
			loanStatusByLoanCode.put("dictLoanStatus", gdv.getDictLoanStatusCode());
			loanStatusByLoanCode.put("loanCode", gdv.getLoanCode());
			//金信债权退回到合同审核
			if (LoanFlowRoute.GOLDCREDIT_TO_CONFIRM.equals(workItem.getResponse())) {
				saveLoanStatusHis(gdv.getApplyId(),gdv.getLoanCode(), gdv.getDictLoanStatusCode(),gdv.getDictLoanStatus(), "成功", gdv.getGrantSureBackReason());
				updateContract(gdv.getApplyId(), gdv.getGrantSureBackReason());
				//取消数据库中的金信标识
				if (StringUtils.isNotEmpty(gdv.getLoanFlag())) {
					loanInfo.setLoanCode(gdv.getLoanCode());//借款编号
					loanInfo.setLoanFlag(gdv.getLoanFlagCode()==null?ChannelFlag.JINXIN.getCode():gdv.getLoanFlagCode());
					
					loanGrantDao.updateFlag(loanInfo);
				}
			//金信债权到返款待确认
			} else if (LoanFlowRoute.GOLDCREDIT_TO_CONTRACT_CHECK.equals(workItem.getResponse())){
				saveLoanStatusHis(gdv.getApplyId(),gdv.getLoanCode(),gdv.getDictLoanStatusCode(), gdv.getDictLoanStatus(), "成功", gdv.getGrantSureBackReason());
			}else if (LoanFlowRoute.LIABILITIES_RETURN.equals(workItem.getResponse())) {//金信复审拒绝
				saveLoanStatusHis(gdv.getApplyId(),gdv.getLoanCode(),gdv.getDictLoanStatusCode(), gdv.getDictLoanStatus(), "成功", gdv.getGrantSureBackReason());
				updateContract(gdv.getApplyId(), gdv.getGrantSureBackReason());
				//取消数据库中的金信标识
				if (StringUtils.isNotEmpty(gdv.getLoanFlag())) {
					loanInfo.setLoanCode(gdv.getLoanCode());//借款编号
					loanInfo.setLoanFlag(gdv.getLoanFlagCode()==null?ChannelFlag.JINXIN.getCode():gdv.getLoanFlagCode());
					
					loanGrantDao.updateFlag(loanInfo);
				}
			}
			loanInfoDao.updateLoanStatus(loanStatusByLoanCode);
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
            contract.setChannelFlag(ChannelFlag.CHP.getCode());
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