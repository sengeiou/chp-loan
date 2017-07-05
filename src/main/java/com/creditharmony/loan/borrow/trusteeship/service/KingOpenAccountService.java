package com.creditharmony.loan.borrow.trusteeship.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.jzh.JzhRegisterInfo;
import com.creditharmony.adapter.bean.in.thirdpay.ProtocolLibraryInfo;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.common.type.SmsState;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.type.ProtocolLibraryAccountType;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanTrustState;
import com.creditharmony.core.loan.type.SmsType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.service.GrantCAService;
import com.creditharmony.loan.borrow.grant.service.GrantSureService;
import com.creditharmony.loan.borrow.stores.dao.AreaPreDao;
import com.creditharmony.loan.borrow.trusteeship.dao.KingOpenAccountDao;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.KingImportAccountInfo;
import com.creditharmony.loan.borrow.trusteeship.entity.ex.TrusteeshipAccount;
import com.creditharmony.loan.borrow.trusteeship.view.TrusteeshipView;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;
import com.creditharmony.loan.sms.dao.SmsDao;
import com.creditharmony.loan.sms.entity.SmsHis;
import com.creditharmony.loan.sms.entity.SmsTemplate;
import com.creditharmony.loan.sms.utils.SmsUtil;
import com.creditharmony.loan.utils.EncryptUtils;
import com.google.common.collect.Maps;

/**
 * 开户相关Service
 * @Class Name KingOpenAccountService
 * @author 王浩
 * @Create In 2016年3月3日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class KingOpenAccountService {
	
	@Autowired
	private AreaPreDao areaDao;
	@Autowired
	private KingOpenAccountDao kingOpenAccountDao;
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private SmsDao smsDao;
	@Autowired
	private GrantCAService grantCAService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantSureService grantSureService;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;
	@Autowired
	private AssistService assistService;
	/**
	 * 获得金账户开户信息列表数据，分页查询
	 * 2017年2月13日
	 * By 朱静越
	 * @param page
	 * @param loanFlowQueryParam 查询条件
	 * @return
	 */
	public Page<LoanFlowWorkItemView> getKingOpenList(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)kingOpenAccountDao.getKingOpenList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询金账户开户信息列表数据，不分页
	 * 2017年2月13日
	 * By 朱静越
	 * @param loanFlowQueryParam 查询条件
	 * @return
	 */
	public List<LoanFlowWorkItemView> getKingOpenList(LoanFlowQueryParam loanFlowQueryParam){
		return kingOpenAccountDao.getKingOpenList(loanFlowQueryParam);
	}
	
	/**
	 * 设置金账户开户所需参数
	 * 2016年3月3日
	 * By 王浩
	 * @param loanCode
	 * @return 
	 */
	public JzhRegisterInfo getJzhRegisterInfo(TrusteeshipAccount accountInfo){
		
		JzhRegisterInfo registerInfo = new JzhRegisterInfo();
		// 流水号
		registerInfo.setMchntTxnSsn(IdGen.uuid().substring(10));
		// 客户姓名
		registerInfo.setCustNm(accountInfo.getBankAccountName());
		// 证件类型（0:身份证7：其他证件）
		registerInfo.setCertifTp(CertificateType.SFZ.getCode().equals(accountInfo.getDictCertType()) ? "0" : "7");
		// 身份证号码/证件
		registerInfo.setCertifId(accountInfo.getCustomerCertNum());
		// 手机号码
		registerInfo.setMobileNo(accountInfo.getCustomerPhoneFirst());
		// 付款银行账号
		registerInfo.setCapAcntNo(accountInfo.getBankAccount() == null ? ""
				: accountInfo.getBankAccount());
		// 开户行地区代码
		registerInfo.setCityId(accountInfo.getBankJzhKhhqx() == null ? ""
				: accountInfo.getBankJzhKhhqx());
		// 开户行
		registerInfo.setParentBankId(accountInfo.getBankCode() == null ? ""
				: accountInfo.getBankCode());

		return registerInfo;
	}
	
	/**
	 * 2016年3月4日
	 * By 王浩
	 * @param loanCode
	 * @return 
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public TrusteeshipAccount getAccountInfo(String loanCode){		
		return loanCustomerDao.selectTrusteeshipAccount(loanCode);		
	}
	
	/**
	 * 2016年3月4日
	 * By 王浩
	 * @param applyIdList
	 * @return 
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<TrusteeshipAccount> getAllAccount(List<String> applyIdList){		
		return loanCustomerDao.selectAllTrusteeAccount(applyIdList);		
	}

	/**
	 * 协议库对接
	 * 2016年3月4日
	 * By 王浩
	 * @param accountInfo
	 * @return 
	 */
	public ProtocolLibraryInfo setLibraryInfo(TrusteeshipAccount accountInfo){
		ProtocolLibraryInfo info = new ProtocolLibraryInfo();
		//银行代码
		info.setBankCode(accountInfo.getBankCode());
		//账户名称
		info.setAccountName(accountInfo.getBankAccountName());
		//银行卡号
		info.setAcntNo(accountInfo.getBankAccount());
		//账户类型
		info.setAcntType(ProtocolLibraryAccountType.JJK.getCode());		
		//证件类型
		info.setCredtTp(CertificateType.SFZ.getCode().equals(accountInfo.getDictCertType()) ? "0" : "7");
		//证件编号
		info.setCredtNo(accountInfo.getCustomerCertNum());
		//银行卡绑定手机号
		info.setMobileNo(accountInfo.getCustomerPhoneFirst());
		
		return info;
	}
	
	/**
	 * 根据applyId查询借款编码
	 * 2016年3月4日
	 * By 王浩
	 * @param applyId
	 * @return 
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getLoanCodeByApplyId(String applyId) {
		if (StringUtils.isNotEmpty(applyId)) {
			String loanCode = areaDao.getLoanCodeByApplyId(applyId);
			return loanCode;		
		}else {
			return "";
		}
	}
	
	/**
	 * 根据身份证号和客户姓名，查出唯一一条记录
	 * 2016年3月7日
	 * By 王浩
	 * @param importAccount
	 * @return 
	 */
	public TrusteeshipView getApplyIdByIdentityNo(KingImportAccountInfo importAccount){
		Map<String, Object> map = Maps.newHashMap();
		if(StringUtils.isNotEmpty(importAccount.getCustomerCertNum())){
			map.put("customerCertNum", importAccount.getCustomerCertNum());
		}
		if(StringUtils.isNotEmpty(importAccount.getCustomerName())){
			map.put("customerName", importAccount.getCustomerName());
		}
		map.put("dictLoanStatus", LoanApplyStatus.KING_TO_OPEN.getCode());
		return loanCustomerDao.getApplyIdByIdentity(map);
	}
	
	/**
	 * 发送履历保存
	 * 2016年3月8日
	 * By 朱杰
	 * @param elem
	 * @param content
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void saveSmsHis(TrusteeshipAccount elem,String content){
		SmsHis smsHis = new SmsHis();
		//客户编号
		smsHis.setCustomerCode(elem.getCustomerCode());
		//客户姓名
		smsHis.setCustomerName(elem.getCustomerName());
		//借款编码
		smsHis.setLoanCode(elem.getLoanCode());
		//发送时间
		smsHis.setSmsSendTime(new Date());
		//短信内容
		smsHis.setSmsMsg(content);
		//短信模板名称
		smsHis.setSmsTempletId(SmsType.JZHOPEN.getName());
		//发送状态
		smsHis.setSmsSendStatus(SmsState.FSCG.value);
		smsHis.preInsert();
		smsDao.insertSmsHis(smsHis);
	}
	
	/**
	 * 金账户开户成功操作:1.有保证人的进行注册，2.更新借款状态，注册的状态 
	 * 3.添加历史 4.更新金账户账户 5.发送开户成功短信
	 * 2016年5月29日
	 * By 朱杰
	 * @param view
	 * @param workItem
	 * @param loanCode
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public String openSuccess(TrusteeshipView view, String loanCode) {
		// 点击金账户开户成功，流程跳转，到放款确认
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setLoanCode(loanCode);
		loanInfo.setApplyId(view.getApplyId());
		loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
		loanInfo.setKingOpenRespReason("");
		//金账户开户成功到 待款项确认 需要修改orderField 用于排序
		LoanInfo loanInfoOrder=new LoanInfo();
		loanInfoOrder.setApplyId(view.getApplyId());
		loanInfoOrder.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_CONFIRM.getCode());
		String orderField=createOrderFileldService.sendGrantSureByAccount(loanInfoOrder);
		loanInfo.setOrderField(orderField);
		
		// 更新金账户开户状态
		loanInfo.setKingStatus(LoanTrustState.KHCG.value);
		String message = "【" + loanCode + "】:开户成功<br>";
		// 保证人登记
		boolean caResult = false;
		Contract updContract = new Contract();
		Contract contract = contractDao.findByLoanCode(view.getLoanCode());
		updContract.setContractCode(contract.getContractCode());
		// TG执行保证人登记操作
		if (StringUtils.isNotEmpty(contract.getLegalMan())) {
			if (YESNO.NO.getCode().equals(contract.getIsRegister())) {
				Map<String, Object> resultMap = grantCAService.registCA(
						view.getApplyId(), contract.getLoanCode());
				caResult = (boolean) resultMap.get("registResult");
				if (caResult) {
					updContract.setIsRegister(YESNO.YES.getCode());
				} else {
					updContract.setIsRegister(YESNO.NO.getCode());
					loanInfo.setDictLoanStatus(null);
					loanInfo.setKingStatus(null);
					message = (String) resultMap.get("message");
					if (message.contains("companyRegisteredNo")) {
						message = message.replace("companyRegisteredNo",
								GrantCommon.COMPANY_REGISTER_NO);
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(loanInfo.getDictLoanStatus())) {
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			historyService.saveLoanStatusHis(loanInfo,LoanApplyStatus.KING_TO_OPEN.getName(),GrantCommon.SUCCESS, "");
			LoanCustomer loanCustomer = new LoanCustomer();
			// 取出手机号码,需要进行解密之后存入到数据库中，金账户户名
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loanCode", loanCode);
			loanCustomer = loanCustomerDao.selectByLoanCode(map);
			EncryptUtils.decrypt(loanCustomer);
			loanCustomer.setCustomerIsGold(YESNO.YES.getCode());
			loanCustomer.preUpdate();
			loanCustomerDao.updateGoldFlag(loanCustomer);
			// 发送短信
			TrusteeshipAccount accountInfo = this.getAccountInfo(loanCode);
			this.sendSms(accountInfo);
		}else {
			message = "开户失败";
		}
		if (StringUtils.isNotEmpty(updContract.getIsRegister())) {
			contractDao.updateContract(updContract);
		}
		// TG执行盖章操作
		return message;
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
			this.saveSmsHis(elem, content);
		}
	}
	
	/**
	 * 金账户开户退回到合同审核：更新借款状态，更新合同表 添加历史
	 * 2017年2月14日
	 * By 朱静越
	 * @param view
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void kingAccountBack(TrusteeshipView view){
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setDictLoanStatus(LoanApplyStatus.KING_RETURN.getCode());
		loanInfo.setLoanCode(view.getLoanCode());
		loanInfo.setApplyId(view.getApplyId());
		loanInfo.preUpdate();
		applyLoanInfoDao.updateLoanInfo(loanInfo);
		
		Contract contract = new Contract();
		contract.setLoanCode(view.getLoanCode());
		contract.setContractBackResult(view.getKingBackReason());
		contractDao.updateContract(contract);
		
		//退回到合同审核  添加分单功能
		assistService.updateAssistAddAuditOperator(view.getLoanCode()); 
		
		historyService.saveLoanStatusHis(loanInfo,"金账户开户退回",GrantCommon.SUCCESS, view.getKingBackReason());
		// 退回到合同审核需要重新分单
		assistService.updateAssistAddAuditOperator(view.getLoanCode());
	}
}
