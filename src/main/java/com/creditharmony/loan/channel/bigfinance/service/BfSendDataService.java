package com.creditharmony.loan.channel.bigfinance.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorAppInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorBankCardInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorBaseInfoInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorCoborrowerInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorContactInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorContractInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorContractSubInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorPersonalAddrInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorPersonalCreditInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorPersonalInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorPersonalJobInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorPersonalPartnerInBean;
import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendCreditorRepayPlanInBean;
import com.creditharmony.adapter.bean.out.djrcreditor.DjrSendCreditorOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.ContractType;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.service.BaseManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.channel.bigfinance.dao.BfSendDataDao;
import com.creditharmony.loan.common.service.HistoryService;

@Service
public class BfSendDataService  extends BaseManager{
	
	@Autowired
	private BfSendDataDao bfSendDataDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private HistoryService historyService;
	
	/**
	 * 大金融待款项确认向大金融发送债券：1.发送大金融需要的借款信息 ，发送成功之后，修改单子的借款状态 ；
	 * 2.更新放款表的提交批次和提交时间
	 * 3.添加历史
	 * 2017年2月17日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @param curBigFinanceLetter 大金融的提交批次
	 * @param curUrgentFlag 加急的提交批次
	 * @param strUrgeFlag 加急标识
	 * @param submitDate 提交时间
	 * @return
	 */
	@SuppressWarnings("finally")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public Map<String, String> sendExchangeDebtInfo (String contractCode,String curBigFinanceLetter,
			String curUrgentFlag,String strUrgeFlag,Date submitDate){
		logger.info("大金融推送数据Start,合同编号为："+contractCode);
		Map<String, String> result = new HashMap<String, String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = format.format(new Date());
		try {
			if (StringUtils.isEmpty(contractCode)){
				logger.info("大金融推送数据合同编号为空");
				result.put(contractCode, "没有合同编号");
				return result;
			}
			Contract contract = contractDao.findByContractCode(contractCode);
			if(null == contract){
				logger.info("大金融推送数据合同编号【"+contractCode+"】未找到合同数据");
				result.put(contractCode, "未找到合同数据");
				return result;
			}
			DjrSendCreditorInBean bean = new DjrSendCreditorInBean();
			bean.setDataTransferId(dateString+"-"+contractCode);
			// 债权基本信息
			List<DjrSendCreditorBaseInfoInBean> debtBaseInfos = bfSendDataDao.getDebtBaseInfo( contract.getLoanCode());
			if(debtBaseInfos.size()>0){
				bean.setDebtBaseInfo(debtBaseInfos.get(0));
			}
			 // 还款计划
			List<DjrSendCreditorRepayPlanInBean> repaymentPlanTables = bfSendDataDao.getRepaymentPlanTables( contract.getContractCode());
			if(repaymentPlanTables.size()>0){
				bean.setRepaymentPlanTables(repaymentPlanTables);
			}
			 // 个人资料信息
			List<DjrSendCreditorPersonalInBean> personalInfos = bfSendDataDao.getpersonalInfo( contract.getContractCode());
			if(personalInfos.size()>0){
				bean.setPersonalInfo(personalInfos.get(0));
			}
			 // 配偶资料
			List<DjrSendCreditorPersonalPartnerInBean> spouseInfos = bfSendDataDao.getSpouseInfo( contract.getContractCode());
			if(spouseInfos.size()>0){
				bean.setSpouseInfo(spouseInfos.get(0));
			}
			 // 申请信息
			List<DjrSendCreditorAppInBean> applicationInfos = bfSendDataDao.getApplicationInfo( contract.getLoanCode());
			if(applicationInfos.size()>0){
				bean.setApplicationInfo(applicationInfos.get(0));
			}
			 // 共同借款人信息
			List<Map<String,String>> isNew = bfSendDataDao.getInfoIsNew(contract.getLoanCode());
			if(!isNew.isEmpty()&&isNew.size()>0){
				//为0是老版咨询，1是新版咨询（如果是新版咨询的话不传送共同借款人信息）
				if(isNew.get(0)==null||"0".equals(isNew.get(0).get("loaninfo_oldornew_flag"))){
					List<DjrSendCreditorCoborrowerInBean> coborrowers = bfSendDataDao.getCoborrower( contract.getLoanCode());
					if(coborrowers.size()>0){
						bean.setCoborrower(coborrowers.get(0));
					}
				}
			}
			 // 信用资料
			List<DjrSendCreditorPersonalCreditInBean> creditInfos = bfSendDataDao.getcreditInfo( contract.getLoanCode());
			if(creditInfos.size()>0){
				bean.setCreditInfo(creditInfos.get(0));
			}
			 // 职业信息
			List<DjrSendCreditorPersonalJobInBean> occupationalInfos = bfSendDataDao.getOccupationalInfo ( contract.getLoanCode());
			if(occupationalInfos.size()>0){
				bean.setOccupationalInfo(occupationalInfos.get(0));
			}
			 // 居住信息
			List<DjrSendCreditorPersonalAddrInBean> residenceInfos = bfSendDataDao.getResidenceInfo( contract.getLoanCode());
			if(residenceInfos.size()>0){
				bean.setResidenceInfo(residenceInfos.get(0));
			}
			 // 联系人信息
			List<DjrSendCreditorContactInBean> contactInfos = bfSendDataDao.getContactInfo( contract.getLoanCode());
			if(contactInfos.size()>0){
				bean.setContactInfo(contactInfos.get(0));
			}
			 // 银行卡资料 
			List<DjrSendCreditorBankCardInBean> bankCardInfos = bfSendDataDao.getBankCardInfo( contract.getLoanCode());
			if(bankCardInfos.size()>0){
				bean.setBankCardInfo(bankCardInfos.get(0));
			}
			 // 合同信息
			Map<String,Object> conditions = new HashMap<String,Object>();
			conditions.put("contractCode", contract.getContractCode());
			
			List<String> conditionsList = new ArrayList<String>();
			String contractname1 = ContractType.CONTRACT_XX_MANAGE.getName();
			String contractname2 = ContractType.CONTRACT_ZCJ_RETURN_MANAGE.getName();
			//TODO 如果是每天都是还款日的需求,只发送【信用咨询管理协议】
			if(!contract.getContractVersion().equals(ContractVer.VER_ONE_ONE_ZCJ.getCode()) 
					&& !contract.getContractVersion().equals(ContractVer.VER_ONE_ZERO_ZCJ.getCode())){
				contractname2 = "";
			}
			conditionsList.add(contractname1);
			conditionsList.add(contractname2);
			conditions.put("contract_djr_filename", conditionsList);
			logger.info("合同文件名称:"+contractname1);
			logger.info("合同文件名称:"+contractname2);
			List<DjrSendCreditorContractSubInBean> contracts = bfSendDataDao.getContractInfo(conditions);
			for(int x=0;x<contracts.size();x++){
				DjrSendCreditorContractSubInBean sub = contracts.get(x);
				if(contractname1.equals(sub.getFileName())){
					contracts.get(x).setFileName("pdf-10001");
				} else if(contractname2.equals(sub.getFileName())){
					contracts.get(x).setFileName("pdf-10002");
				}
			}
			if(contracts.size()>0){
				DjrSendCreditorContractInBean ht = new DjrSendCreditorContractInBean(); 
				ht.setContracts(contracts); 
				ht.setContractCode(contractCode);
				bean.setContractInfos(ht);
			}
			
			// 客户端服务代理申明	
			ClientPoxy service = new ClientPoxy(ServiceType.Type.DJR_SEND_CREDITOR_SERVICE);	
			// 请求发送	
			DjrSendCreditorOutBean  retInfo =  (DjrSendCreditorOutBean) service.callService(bean);	
			// 请求成功判断	
			if (ReturnConstant.SUCCESS.equals(retInfo.getRetCode())) {
				logger.info("大金融推送数据合同编号【"+contractCode+"】推送成功");
				result.put(contractCode, BooleanType.TRUE);
				// 业务处理：数据流转  更新放款表,提交批次和提交时间  添加历史
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setLoanCode(contract.getLoanCode());
				loanInfo.setApplyId(contract.getApplyId());
				loanInfo.setDictLoanStatus(LoanApplyStatus.BIGFINANCE_TO_SNED.getCode());
				loanInfo.preUpdate();
				applyLoanInfoDao.updateLoanInfo(loanInfo);
				LoanGrant loanGrant = new LoanGrant();
				loanGrant.setContractCode(contractCode);
				if (YESNO.YES.getCode().equals(strUrgeFlag)) {
					loanGrant.setGrantPch(curUrgentFlag);
				}else {
					loanGrant.setGrantPch(curBigFinanceLetter);
				}
				loanGrant.setSubmissionsDate(submitDate);
				loanGrantDao.updateLoanGrant(loanGrant);
				historyService.saveLoanStatusHis(loanInfo,"待款项确认","成功","发送大金融");
				logger.debug("大金融推送成功数据："+contractCode+"业务处理完成");
			}else {
				// 取得错误信息
				String retMsg = retInfo.getRetMsg();
				logger.info("大金融推送数据合同编号【"+contractCode+"】推送失败【"+retMsg+"】");
				result.put(contractCode, "推送失败:失败原因："+retMsg);
			}
		} catch (Exception e) {
				logger.error("大金融推送数据第推送异常"+contractCode,e);
				logger.error("大金融推送数据合同编号【"+contractCode+"】推送异常【"+e.getMessage()+"】");
				result.put(contractCode, "推送异常");
				e.printStackTrace();
				throw new ServiceException("大金融推送数据发生异常："+e);
			}finally{
				logger.info("大金融推送数据End=============");
				return result;
			}
		}

	public static void main(String[] agrs){
		
	}
}
