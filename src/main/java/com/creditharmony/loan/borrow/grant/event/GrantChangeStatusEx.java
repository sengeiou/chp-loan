/**
 * @author zhujingyue
 * 更新单子的状态
 */
package com.creditharmony.loan.borrow.grant.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowStepName;
import com.google.common.collect.Maps;

@Service("ex_hj_loanflow_grantChangeStatus")
public class GrantChangeStatusEx extends BaseService implements ExEvent {
	
    @Autowired
	private LoanGrantDao loanGrantDao;	
	
    @Autowired
	private ContractDao contractDao;
	
    @Autowired
	private LoanStatusHisDao loanStatusHisDao;
	
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	
	/**
	 * 更新数据库中的借款状态
	 */
	@Override
	public void invoke(WorkItemView workItem) {
	   String response=workItem.getResponse();
	   GrantDealView gdv=(GrantDealView)workItem.getBv();
	   String listFlag = gdv.getListFlag();
	   String operateType = gdv.getOperateType();
	   
	   if(!GrantCommon.METHOD_TYPE_SAVEDATE.equals(operateType) && !YESNO.YES.getCode().equals(operateType)){
	       if (StringUtils.isNotEmpty(response)) {
	           if (response.equals(LoanFlowRoute.DELIVERYCARD)||"TG".equals(listFlag)) {
	               updateStatus(workItem);
	               if (!ChannelConstants.GOLD_CREDIT.equals(gdv.getLoanFlag())){
	            	   updateGrantPch(workItem);
		               saveLoanStatusHisXJ(gdv.getApplyId(),loanGrantDao.selLoanCode(gdv.getApplyId()),gdv.getDictLoanStatusCode(),"待款项确认","成功","");
	               }else{
	            	   saveLoanStatusHis(gdv.getApplyId(),loanGrantDao.selLoanCode(gdv.getApplyId()),gdv.getDictLoanStatusCode(),"金信接收确认","成功","");
	               }
	           }else if (LoanFlowRoute.CONTRACTCHECK.equals(response)) {
	               // 放款明细确认退回，金信
	               String applyId=gdv.getApplyId();
	               // 调用方法，更新状态
	               Contract contract = contractDao.findByApplyId(applyId);
	               if (gdv.getGrantSureBackReason()!= null) {
	                   contract.setContractBackResult(gdv.getGrantSureBackReason());
	                   contract.setBackFlag(YESNO.YES.getCode());
	                   contractDao.updateContract(contract);
	               }  
	               String loanCode = contract.getLoanCode();
	               Map<String,Object> param = Maps.newHashMap();
	               param.put("loanCode", loanCode);
	               LoanInfo loanInfo = loanInfoDao.selectByLoanCode(param);
	               LoanStatusHis loanStatusHis = new LoanStatusHis();
	               loanStatusHis.setLoanCode(loanCode);
	               loanStatusHis.setDictSysFlag(ModuleName.MODULE_LOAN.value);
	               loanStatusHis.setDictLoanStatus(LoanApplyStatus.CONTRACT_AUDIFY.getCode());
	               List<LoanStatusHis> hisList = loanStatusHisDao.findWantedNoteByLoanCode(loanStatusHis);
	       
	               // 更新排序字段
	               String urgentFlag = loanInfo.getLoanUrgentFlag();
	               String frozenFlag = null;
	               if(StringUtils.isEmpty(loanInfo.getFrozenCode())){
	                   frozenFlag = "00";
	               }else{
	                   frozenFlag = "01";
	               }
	               LoanApplyStatus loanApplyStatus = LoanApplyStatus.parseByName(gdv.getDictLoanStatus());
	               String dictStatus=loanApplyStatus.getCode();
	               String code = dictStatus+"-0"+urgentFlag+"-"+frozenFlag+"-01";
	               OrderFiled filed = OrderFiled.parseByCode(code);
	               String orderField = filed.getOrderId(); 
	               if(!ObjectHelper.isEmpty(hisList) && !ObjectHelper.isEmpty(hisList.get(0))){
	                       orderField +="-"+DateUtils.formatDate(hisList.get(0).getOperateTime(), "yyyy.MM.dd HH:mm:ss");
	                   }
	               gdv.setOrderField(orderField);
	               workItem.setBv(gdv);
	               // 更新状态
	               updateStatus(workItem);
	               // 退回历史
	               if(LoanApplyStatus.PAYMENT_BACK.getCode().equals(gdv.getDictLoanStatusCode())){
	            	   saveLoanStatusHis(gdv.getApplyId(),loanCode,gdv.getDictLoanStatusCode(),"待款项确认退回","成功",gdv.getGrantSureBackReason());
	        	   }else{
		               saveLoanStatusHisXJ(gdv.getApplyId(),loanCode,gdv.getDictLoanStatusCode(),"待款项确认退回","成功",gdv.getGrantSureBackReason());
	        	   }
	           } else if(LoanFlowRoute.LIABILITIES_RETURN.equals(response)){
	        	   updateStatus(workItem);
	        	   //金信历史
	        	   saveLoanStatusHis(gdv.getApplyId(),gdv.getLoanCode(),gdv.getDictLoanStatusCode(),gdv.getDictLoanStatus(),"成功",gdv.getGrantSureBackReason());
	           } else if(LoanFlowRoute.PAYMENT.equals(response)&&LoanApplyStatus.BIGFINANCE_TO_SNED.getCode().equals(gdv.getDictLoanStatusCode())){
	        	   updateStatus(workItem);
	        	   //添加修改推介日期和批次
	        	   updateRecommend(workItem);
	        	   //历史
	        	   saveLoanStatusHis(gdv.getApplyId(),gdv.getLoanCode(),gdv.getDictLoanStatusCode(),LoanApplyStatus.LOAN_SEND_CONFIRM.getName(),"成功",gdv.getGrantSureBackReason());
	           }
	       } 
	   }
	   if (LoanFlowStepName.LOAN_CONFIRM.equals(workItem.getStepName()) && StringUtils.isNotBlank(gdv.getFlagStatus())) {
    	   Map<String,Object> param = Maps.newHashMap();
           param.put("loanCode", gdv.getLoanCode());
           LoanInfo loanInfo = loanInfoDao.selectByLoanCode(param);
           gdv.setDictLoanStatusCode(loanInfo.getDictLoanStatus());
           String remark = gdv.getGrantSureBackReason();
			if (StringUtils.isNotEmpty(gdv.getRejectFrozenReason())) {
				remark = gdv.getRejectFrozenReason();
			}
			saveLoanStatusHisXJ(gdv.getApplyId(),gdv.getLoanCode(),gdv.getDictLoanStatusCode(),gdv.getFlagStatus(),"成功",remark);
       }
 }
	
	/**
	 * 更新单子的状态
	 * 2015年12月30日
	 * By 朱静越
	 * @param workItem
	 */
	public void updateStatus(WorkItemView workItem){
		   GrantDealView gdv=(GrantDealView)workItem.getBv();
		   LoanInfo loanInfo=new LoanInfo();
		   // 获得借款申请状态的code，存入数据库中
		   LoanApplyStatus loanApplyStatus = LoanApplyStatus.parseByName(gdv.getDictLoanStatus());
		   String dictStatus=loanApplyStatus.getCode();
		   loanInfo.setDictLoanStatus(dictStatus);
		   loanInfo.setLoanCode(loanGrantDao.selLoanCode(gdv.getApplyId()));
		   // 调用方法，更新状态
		   loanGrantDao.updateStatus(loanInfo);
	}
	
	/**
	 * 更新数据库中的提交批次
	 * 2016年4月24日
	 * By 朱静越
	 * @param workItem
	 */
	public void updateGrantPch(WorkItemView workItem){
		GrantDealView gdv=(GrantDealView)workItem.getBv();
		LoanGrant loanGrant = new LoanGrant();
		loanGrant.setContractCode(gdv.getContractCode());
		if (StringUtils.isNotEmpty(gdv.getGrantPch())) {
			loanGrant.setGrantPch(gdv.getGrantPch());
		}
		if (StringUtils.isNotEmpty(gdv.getSubmissionDate())) {
			try {
				Date subTime = DateUtils.parseDate(gdv.getSubmissionDate(), "yyyy-MM-dd HH:mm:ss");
				loanGrant.setSubmissionsDate(subTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		loanGrantDao.updateLoanGrant(loanGrant);
	}
	
	/**
	 * 历史
	 * 2016年4月12日
	 * By 朱静越
	 * @param loaninfo
	 * @param operateStep
	 * @param operateResult
	 * @param remark
	 */
	private void saveLoanStatusHis(String applyId,String loanCode,String loanStatus, String operateStep,
			String operateResult, String remark) {
		LoanStatusHis record = new LoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setApplyId(applyId);
		// 借款编号
		record.setLoanCode(loanCode);
		//借款状态
		record.setDictLoanStatus(loanStatus);
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(operateStep);
		// 操作结果
		record.setOperateResult(operateResult);
		
		if("100".equals(loanStatus)){//金信复审拒绝
			record.setRemark("金信复审拒绝");
		}else if("99".equals(loanStatus)){
			record.setRemark("金信初审拒绝");
		}else if("66".equals(loanStatus)){
			record.setRemark("金信接收");
		}else{
			// 备注
			record.setRemark(remark);
		}
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 操作时间
		record.setOperateTime(record.getCreateTime());
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
		if("99".equals(loanStatus)||"99".equals(operateStep)||//金信初审拒绝
				"100".equals(loanStatus)||"100".equals(operateStep)||//金信复审拒绝
				"66".equals(loanStatus)){//待分配卡号
			record.setOperator("金信");// 操作人记录当前登陆系统用户名称
		}
		
		loanStatusHisDao.insertSelective(record);
	}
	
	
	private void saveLoanStatusHisXJ(String applyId,String loanCode,String loanStatus, String operateStep,
			String operateResult, String remark) {
		LoanStatusHis record = new LoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setApplyId(applyId);
		// 借款编号
		record.setLoanCode(loanCode);
		//借款状态
		record.setDictLoanStatus(loanStatus);
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
		loanStatusHisDao.insertSelective(record);
	}
	
	/**
	 * 修改大金融推介日期与批次
	 * 2016年9月5日
	 * By WUJJ
	 */
	public void updateRecommend(WorkItemView workItem){
		   GrantDealView gdv=(GrantDealView)workItem.getBv();
		   Map map = new HashMap();
		   String loanCode = loanGrantDao.selLoanCode(gdv.getApplyId());
		   map.put("loanCode", loanCode);
		   LoanInfo list = loanInfoDao.selectByLoanCode(map);
		   if(list!=null){
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			   String date = sdf.format(new Date());
			   map.put("recommendTime", new Date());
			   if("1".equals(list.getLoanUrgentFlag())){
				   //加急
				   map.put("batch", "加急"+gdv.getUrgentYes());
			   }else{
				   //不加急
				   map.put("batch", "大金融"+gdv.getUrgentNo());
			   }
			   loanInfoDao.updateRecommend(map);
		   }
	}
	
}