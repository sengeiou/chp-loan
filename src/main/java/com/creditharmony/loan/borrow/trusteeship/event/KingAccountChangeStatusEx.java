
package com.creditharmony.loan.borrow.trusteeship.event;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.SmsType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.TrusteeshipAccount;
import com.creditharmony.loan.borrow.trusteeship.service.KingOpenAccountService;
import com.creditharmony.loan.borrow.trusteeship.view.TrusteeshipView;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.creditharmony.loan.sms.dao.SmsDao;
import com.creditharmony.loan.sms.entity.SmsTemplate;
import com.creditharmony.loan.sms.utils.SmsUtil;
import com.creditharmony.loan.utils.EncryptUtils;

/**
 * 金账户开户 更新借款申请的状态
 * @Class Name KingAccountChangeStatusEx
 * @author 王浩
 * @Create In 2016年2月29日
 */
@Service("ex_hj_loanflow_updKingAccState")
public class KingAccountChangeStatusEx extends BaseService implements ExEvent {
	
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	@Autowired
	private KingOpenAccountService openAccountService;
	
	@Autowired
	private SmsDao smsDao;
	
	/**
	 * 更新数据库中的借款状态
	 * 金账户退回、金账户开户成功后调用该接口
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		TrusteeshipView trusteeshipView = (TrusteeshipView) workItem.getBv();
		String response = workItem.getResponse();
		User user = UserUtils.getUser();
		if (StringUtils.isNotEmpty(response)) {
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setLoanCode(trusteeshipView.getLoanCode());	
			loanInfo.setDictLoanStatus(trusteeshipView.getDictLoanStatusCode());		
			// update借款信息
			loanInfo.preUpdate();
			loanInfoDao.updateLoanInfo(loanInfo);
			
			if (response.equals(LoanFlowRoute.PAYCONFIRM)) {
				LoanCustomer loanCustomer = new LoanCustomer();
				// 取出手机号码,需要进行解密之后存入到数据库中，金账户户名
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("loanCode", trusteeshipView.getLoanCode());
				loanCustomer = loanCustomerDao.selectByLoanCode(map);
				EncryptUtils.decrypt(loanCustomer);
				loanCustomer.setCustomerIsGold(YESNO.YES.getCode());
				loanCustomer.preUpdate();
				loanCustomerDao.updateGoldFlag(loanCustomer);
				// 发送短信
				TrusteeshipAccount accountInfo = openAccountService.getAccountInfo(trusteeshipView.getLoanCode());
				this.sendSms(accountInfo);
			}
			
			// 插入历史记录
			LoanStatusHis record = new LoanStatusHis();
			// APPLY_ID
			record.setApplyId(workItem.getBv().getApplyId());
			// 借款编号
			record.setLoanCode(trusteeshipView.getLoanCode());
			// 操作结果
			record.setOperateResult(DictCache.getInstance().getDictLabel("jk_king_state", trusteeshipView.getKingStatus()));
			// 历史记录 步骤名
			// 备注
			String remark = "";
			if(LoanFlowRoute.CONTRACTCHECK.equals(workItem.getResponse())){
				record.setOperateStep("金账户开户退回");
				remark = StringUtils.isNotEmpty(trusteeshipView.getKingBackReason()) ? 
						trusteeshipView.getKingBackReason() : "";
			}else{
				record.setOperateStep("金账户开户");
			}
			record.setRemark(remark);
			// 系统标识
			record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
			// 设置Crud属性值
			record.preInsert();
			// 操作时间
			record.setOperateTime(record.getCreateTime());
			if (user != null) {
				record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
				if (ArrayHelper.isNotEmpty(user.getRoleList())) {
					record.setOperateRoleId(user.getRoleList().get(0).getId());// 操作人角色
				}
				if (user.getDepartment() != null) {
					record.setOrgCode(user.getDepartment().getId()); // 机构编码
				}
			}
			// 操作结果
			record.setOperateResult("成功");
			loanStatusHisDao.insertSelective(record);
		}
	}
	
	/**
	 * 金账户开户成功发送短信，并且保存发送履历
	 * 2016年3月8日
	 * By 朱杰
	 * @param elem
	 * @return none
	 */
	public void sendSms(TrusteeshipAccount elem){
		if (elem != null) {
			// 手机号
			String phone = elem.getCustomerPhoneFirst();
			if (StringUtils.isEmpty(phone) || phone.length() <= 4) {
				return;
			}
			// 取后四位
			String phoneLast4 = phone.substring(phone.length() - 4);
			SmsTemplate template = smsDao.getSmsTemplate(SmsType.JZHOPEN
					.getCode());
			String content = template.getTemplateContent()
					.replace("{#Name#}", elem.getCustomerName())
					.replace("{#PhoneLast4#}", phoneLast4);
			SmsUtil.sendSms(phone, content);

			// 发送履历保存
			openAccountService.saveSmsHis(elem, content);
		}
	}
	
}