package com.creditharmony.loan.borrow.contract.event;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.fortune.type.ProductType;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ContractResult;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.PeriodStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeTempDao;
import com.creditharmony.loan.borrow.contract.dao.ContractTempDao;
import com.creditharmony.loan.borrow.contract.dao.GuaranteeRegisterDao;
import com.creditharmony.loan.borrow.contract.dao.PaperlessPhotoDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractFeeTemp;
import com.creditharmony.loan.borrow.contract.entity.ContractTemp;
import com.creditharmony.loan.borrow.contract.entity.GuaranteeRegister;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.service.ContractFeeService;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contract.util.ProductUtil;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.channel.finance.dao.FinancialBusinessDao;
import com.creditharmony.loan.channel.goldcredit.dao.GCCeilingDao;
import com.creditharmony.loan.channel.jyj.dao.JyjBorrowBankConfigureDao;
import com.creditharmony.loan.channel.jyj.entity.JyjBorrowBankConfigure;
import com.creditharmony.loan.common.dao.GlBillDao;
import com.creditharmony.loan.common.dao.GlBillHzDao;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.dao.PaybackMonthDao;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.GlBillHz;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.RepayPlanNew;
import com.creditharmony.loan.common.type.LoanProductCode;
import com.creditharmony.loan.common.utils.ReckonFeeNew;
import com.creditharmony.loan.utils.EncryptUtils;

@Service("ex_hj_loanflow_updContract")
public class LoanFlowUpdCtrEx extends BaseService implements ExEvent {

	private static final Logger log = LoggerFactory.getLogger(LoanFlowUpdCtrEx.class);
	@Autowired
	private ContractService contractService;

	@Autowired
	private ContractFeeService contractFeeService;

	@Autowired
	private ContractDao contractDao;

	@Autowired
	private ContractFeeDao contractFeeDao;

	@Autowired
	private PaybackMonthDao paybackMonthDao;

	@Autowired
	private GlBillDao glBillDao;
	
	@Autowired
	private GlBillHzDao glBillHzDao;

	@Autowired
	private LoanGrantDao loanGrantDao;

	@Autowired
	private PaybackDao paybackDao;

	@Autowired
	private LoanCustomerDao loanCustomerDao;

	@Autowired
	private LoanBankDao loanBankDao;

	@Autowired
	private ApplyLoanInfoDao loanInfoDao;

	@Autowired
	private LoanStatusHisDao loanStatusHisDao;

	@Autowired
	private FinancialBusinessDao financialBusinessDao;

	@Autowired
	private PaperlessPhotoDao paperlessPhotoDao;

	@Autowired
	private LoanCoborrowerDao loanCoborrowerDao;
	@Autowired
	private GCCeilingDao gcCeilingDao;
	@Autowired
	private GuaranteeRegisterDao registerDao;
	
	@Autowired
	private ContractTempDao contractTempDao;
	
	@Autowired
	private ContractFeeTempDao contractFeeTempDao;
	@Autowired
	private JyjBorrowBankConfigureDao jyjBorrowBankConfigureDao;

	@Override
	public void invoke(WorkItemView workItem) {
		ContractBusiView contractBusiView = (ContractBusiView) workItem.getBv();
		String operType = contractBusiView.getOperType();
		/**
		 * 正常提交、退回时operType 为空 更新渠道标识时 operType为0 更新冻结标识时operType为1
		 * 正常提交、退回才走下面的逻辑
		 */
		if (!YESNO.NO.getCode().equals(operType) && !YESNO.YES.getCode().equals(operType)) {
			Contract contract = contractBusiView.getContract();
			ContractFee contractFee = contractBusiView.getCtrFee();
			String dictOperateResult = contractBusiView.getDictOperateResult();
			String stepName = workItem.getStepName();
			String dictCheckStatus = contractBusiView.getDictLoanStatusCode();
			contract.setDictCheckStatus(dictCheckStatus);

			if (!ObjectHelper.isEmpty(contract)) {
				String loanCode = contract.getLoanCode();
				if (ContractConstant.RATE_AUDIT.equals(stepName)) {
					if (ContractConstant.CONTRACT_SUBMIT.equals(dictOperateResult)) {
						LoanInfo loaninfo=loanInfoDao.findStatusByLoanCode(contract.getLoanCode());
						if(loaninfo.getIssplit().equals(ContractConstant.ISSPLIT_1)){
							//zcj
							ContractTemp contractZCJ=contractBusiView.getContractZCJ();
							ContractFeeTemp contractFeeZCJ=contractBusiView.getCtrFeeZCJ();
							contractZCJ.setDictCheckStatus(dictCheckStatus);
							if (StringUtils.isEmpty(contractZCJ.getContractCode())) {
								String contractCode = String.valueOf(new Date().getTime());
								contractZCJ.setContractCode(contractCode);
							}
							String contractCodeZCJ=contract.getContractCode()+"-1";
							ContractTemp contractTempZCJ=contractTempDao.selectByContractCode(contractCodeZCJ);
							if (contractTempZCJ != null) {
								contractFeeTempDao.deleteByContractCode(contractCodeZCJ);
								contractTempDao.deleteByContractCode(contractCodeZCJ);
							}
							contractZCJ.setApplyId(contractBusiView.getApplyId());
							if (!ObjectHelper.isEmpty(contractTempZCJ)) {
								contractZCJ.setContractBackResult(contractTempZCJ.getContractBackResult());
								contractZCJ.setAuditCount(contractTempZCJ.getAuditCount());
								String backFlag = contractTempZCJ.getBackFlag();
								if (StringUtils.isEmpty(backFlag)) {
									backFlag = YESNO.NO.getCode();
								}
								contractZCJ.setBackFlag(backFlag);
								contractZCJ.setContractVersion(contractTempZCJ.getContractVersion());
								contractZCJ.setPaperLessFlag(contractTempZCJ.getPaperLessFlag());
							}
							contractZCJ.preInsert();
							contractTempDao.insertSelective(contractZCJ);
							if (!ObjectHelper.isEmpty(contractFeeZCJ)) {
								contractFeeZCJ.setContractCode(contractZCJ.getContractCode());
								contractFeeZCJ.preInsert();
								contractFeeTempDao.insertSelective(contractFeeZCJ);
							}
							
							//jinxin
							ContractTemp contractJINXIN=contractBusiView.getContractJINXIN();
							ContractFeeTemp contractFeeJINXIN=contractBusiView.getCtrFeeJINXIN();
							contractJINXIN.setDictCheckStatus(dictCheckStatus);
							if (StringUtils.isEmpty(contractJINXIN.getContractCode())) {
								String contractCode = String.valueOf(new Date().getTime());
								contractJINXIN.setContractCode(contractCode);
							}
							String contractCodeJINXIN=contract.getContractCode()+"-2";
							ContractTemp contractTempJINXIN=contractTempDao.selectByContractCode(contractCodeJINXIN);
							if (contractTempJINXIN != null) {
								contractFeeTempDao.deleteByContractCode(contractCodeJINXIN);
								contractTempDao.deleteByContractCode(contractCodeJINXIN);
							}
							contractJINXIN.setApplyId(contractBusiView.getApplyId());
							if (!ObjectHelper.isEmpty(contractTempJINXIN)) {
								contractJINXIN.setContractBackResult(contractTempJINXIN.getContractBackResult());
								contractJINXIN.setAuditCount(contractTempJINXIN.getAuditCount());
								String backFlag = contractTempJINXIN.getBackFlag();
								if (StringUtils.isEmpty(backFlag)) {
									backFlag = YESNO.NO.getCode();
								}
								contractJINXIN.setBackFlag(backFlag);
								contractJINXIN.setContractVersion(contractTempJINXIN.getContractVersion());
								contractJINXIN.setPaperLessFlag(contractTempJINXIN.getPaperLessFlag());
							}
							contractJINXIN.preInsert();
							contractTempDao.insertSelective(contractJINXIN);
							if (!ObjectHelper.isEmpty(contractFeeJINXIN)) {
								contractFeeJINXIN.setContractCode(contractJINXIN.getContractCode());
								contractFeeJINXIN.preInsert();
								contractFeeTempDao.insertSelective(contractFeeJINXIN);
							}
							//更新费率提交属性
							LoanInfo lf=new LoanInfo();
							lf.setLoanCode(loaninfo.getLoanCode());
	                        lf.setFlFlag(ContractConstant.FLFLAG_1);
	                        loanInfoDao.updateLoanInfo(lf);
						}else{
							if (StringUtils.isEmpty(contract.getContractCode())) {
								String contractCode = String.valueOf(new Date().getTime());
								contract.setContractCode(contractCode);

							}
							Contract oldContract = contractService.findByLoanCode(loanCode);
							if (oldContract != null) {
								contractFeeDao.deleteByLoanCode(loanCode);
								contractDao.deleteByLoanCode(loanCode);
							}
							contract.setApplyId(contractBusiView.getApplyId());
							if (!ObjectHelper.isEmpty(oldContract)) {
								contract.setContractBackResult(oldContract.getContractBackResult());
								contract.setAuditCount(oldContract.getAuditCount());
								String backFlag = oldContract.getBackFlag();
								if (StringUtils.isEmpty(backFlag)) {
									backFlag = YESNO.NO.getCode();
								}
								contract.setBackFlag(backFlag);
								contract.setContractVersion(oldContract.getContractVersion());
								contract.setPaperLessFlag(oldContract.getPaperLessFlag());
							}
							contract.preInsert();
							contractDao.insertSelective(contract);
							if (StringUtils.isEmpty(contract.getLegalMan())) {
								contractBusiView.setEnsureManFlag(YESNO.NO.getCode());
							} else {
								contractBusiView.setEnsureManFlag(YESNO.YES.getCode());
							}
							if (!ObjectHelper.isEmpty(contractFee)) {
								contractFee.setContractCode(contract.getContractCode());
								contractFee.preInsert();
								contractFeeDao.insertContractFee(contractFee);
							}
						}
						
						contractBusiView.setMonthRate(contractFee.getFeeMonthRate().toString());
					    
						// 查询并保存保证人信息
						String relateId = contractBusiView.getBusinessProveId();
						GuaranteeRegister queryRegister = new GuaranteeRegister();
						queryRegister.setLoanCode(loanCode);
						if (StringUtils.isNotEmpty(relateId)) {
							GuaranteeRegister curRegister = registerDao.get(queryRegister);
							// 如果之前存在保证人且跟当前的ID不一致则删除之前的保证人信息并插入信息值
							if (ObjectHelper.isEmpty(curRegister) || !relateId.equals(curRegister.getRelateId())) {
								if (!ObjectHelper.isEmpty(curRegister)
										&& StringUtils.isNotEmpty(curRegister.getRelateId())) {
									registerDao.delete(loanCode);
								}
								GuaranteeRegister register = registerDao.getFromApproveById(relateId);
								Map<String, Object> param = new HashMap<String, Object>();
								param.put("loanCode", loanCode);
								LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
								register.setGuaranteeIdNum(loanCustomer.getCustomerCertNum());
								register.setGuaranteeMail(loanCustomer.getCustomerEmail());
								register.setGuaranteeMobile(loanCustomer.getCustomerPhoneFirst());
								register.setGuaranteeTel(loanCustomer.getCustomerPhoneFirst());
								register.setContractCode(contract.getContractCode());
								register.setLoanCode(loanCode);
								register.preInsert();
								registerDao.insert(register);

							}
						}
					}
					// 合同签署操作
					// 设置
				} else if (ContractConstant.CONFIRM_SIGN.equals(stepName)) {
					if (ContractConstant.CONTRACT_SUBMIT.equals(dictOperateResult)) {
						Date contractDueDay = contract.getContractDueDay(); // 预约签署日期
						contractBusiView.setConfirmSignDate(DateUtils.formatDate(contractDueDay, "yyyy-MM-dd"));
						// 更新银行卡信息
						LoanBank loanBank = contractBusiView.getLoanBank();
						if (!ObjectHelper.isEmpty(loanBank)) {
							if (ObjectHelper.isEmpty(loanBank.getBankIsRareword())) {
								loanBank.setBankIsRareword(0);
							}
							if (StringUtils.isNotEmpty(loanBank.getId())) {
								loanBank.preUpdate();
								loanBankDao.update(loanBank);
							} else {
								loanBank.preInsert();
								loanBankDao.insert(loanBank);
							}
						if (contractDueDay != null) {
							Contract c=contractDao.findByContractCode(contract.getContractCode());
							Calendar calendar = Calendar.getInstance();
							if(ProductUtil.PRODUCT_NXD.getCode().equals(c.getProductType())){
								calendar.setTime(contractDueDay);
								calendar.add(Calendar.MONTH, 1);
							}else{
								calendar = this.getBillDay(contractDueDay);
							}
							
							contract.setContractReplayDay(calendar.getTime());
							calendar.add(Calendar.MONTH, contract.getContractMonths().intValue() - 1);
							contract.setContractEndDay(calendar.getTime());						
							contract.setContractFactDay(contractDueDay); // 默认的设置实际签署日期为预定的时间
							
							Calendar calendar1 =this.getBillHzDayZCJ(contractDueDay);
							calendar1.add(Calendar.MONTH, contract.getContractMonths().intValue() - 1+3);
							contract.setContractEndDayTemp(calendar1.getTime());
							contract.preUpdate();
							
                        	if("1".equals(contractBusiView.getLoanInfo().getIssplit())){
                        		contract.setContractCodeTemp("'"+contract.getContractCode()+"-1',"+"'"+contract.getContractCode()+"-2'");
                        		contractDao.updateContractTemp(contract);
                        		if(ProductUtil.PRODUCT_JYJ.getCode().equals(c.getProductType())){
	                        		JyjBorrowBankConfigure j=new JyjBorrowBankConfigure();
		    						j.setFlag(1);
		    						j.setLoanCode(contract.getLoanCode());
		    						j.setProductCode(c.getProductType());
		    						JyjBorrowBankConfigure jb=jyjBorrowBankConfigureDao.selectBank(j);	 
		    						BigDecimal firstLoan=jb.getFirstLoanProportion().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);
		    						BigDecimal endloan=jb.getEndLoanProportion().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);
	                        		ContractFeeTemp contractfeeTemp1=contractFeeTempDao.selectByContractCode(contract.getContractCode()+"-1");
	                        		ContractTemp contractTemp1=contractTempDao.selectByContractCode(contract.getContractCode()+"-1");
		    						  						
		    						contractfeeTemp1.setFirstGrantAmount(contractTemp1.getContractAmount().multiply(firstLoan));
		    						contractfeeTemp1.setLastGrantAmount(contractTemp1.getContractAmount().multiply(endloan));
		    						contractFeeTempDao.updateByBankCode(contractfeeTemp1);
		    						
		    						ContractFeeTemp contractfeeTemp2=contractFeeTempDao.selectByContractCode(contract.getContractCode()+"-1");
	                        		ContractTemp contractTemp2=contractTempDao.selectByContractCode(contract.getContractCode()+"-1");    						
	                        	
	                        		contractfeeTemp2.setFirstGrantAmount(contractTemp2.getContractAmount().multiply(firstLoan));
	                        		contractfeeTemp2.setLastGrantAmount(contractTemp2.getContractAmount().multiply(endloan));
	                        		contractFeeTempDao.updateByBankCode(contractfeeTemp2);
                        		}
                        	}else{
	                            contractDao.updateContract(contract);
	                            ContractFee contractfee=contractFeeDao.findByContractCode(contract.getContractCode());
	    						Map<String,Object> param=new HashMap<String,Object>();
	    	                    param.put("loanCode", loanCode);
	    	                	if(LoanProductCode.PRO_JIAN_YI_JIE.equals(c.getProductType())){
	    	                		JyjBorrowBankConfigure j=new JyjBorrowBankConfigure();
	    	                		j.setFlag(1);
	    	                		j.setLoanCode(c.getLoanCode());
	    	                		j.setProductCode(c.getProductType());
		    						JyjBorrowBankConfigure jb=jyjBorrowBankConfigureDao.selectBank(j);
		    						BigDecimal firstLoan=jb.getFirstLoanProportion().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);
		    						BigDecimal endloan=jb.getEndLoanProportion().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);
		    						contractfee.setFirstGrantAmount(c.getContractAmount().multiply(firstLoan));
		    						contractfee.setLastGrantAmount(c.getContractAmount().multiply(endloan));
		    						contractFeeDao.updateByBankCode(contractfee);
	    	                	}
                        	}
                        }
						
							Integer bankIsRareword = loanBank.getBankIsRareword();
							if (ObjectHelper.isEmpty(bankIsRareword) || bankIsRareword.intValue() == 0) {
								contractBusiView.setBankIsRareword(YESNO.NO.getCode());
							} else {
								contractBusiView.setBankIsRareword(YESNO.YES.getCode());
							}
						}
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("loanCode", contract.getLoanCode());
						LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
						contractBusiView.setCustBankAccountName(loanBank.getBankAccountName());
						contractBusiView.setEmail(loanCustomer.getCustomerEmail());
						// TODO 解密 更新工作流
						contractBusiView.setMobilePhone(loanCustomer.getCustomerPhoneFirst());
						EncryptUtils.decrypt(contractBusiView);
						// 设置门店提交时间
						contractBusiView.setLastDealTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
						
						
						
						
					} else {
						Contract contractNew = new Contract();
						contractNew.setContractCode(contract.getContractCode());
						contractNew.setDictCheckStatus(dictCheckStatus);
						contractNew.setBackFlag(contract.getBackFlag());
						contractNew.preUpdate();
                        contractDao.updateContract(contractNew);
                    }
				} else if (ContractConstant.CTR_CREATE.equals(stepName)) {
					Map<String,Object> param=new HashMap<String,Object>();
                    param.put("loanCode", loanCode);
                	LoanInfo loanInfo=loanInfoDao.selectByLoanCode(param);
                    String contractCode = contractBusiView.getContract().getContractCode();
                    String contractVersion = contractBusiView.getContractVersion();
                    loanInfoDao.updateOldLoanCodeByLoanCode(loanCode);
                    //联合放款，需拆分
                    if(ContractConstant.ISSPLIT_1.equals(loanInfo.getIssplit())){
                    	if(ContractConstant.CONTRACT_SUBMIT.equals(dictOperateResult)){//合同提交时，需拆分
                    		contractService.splitContract(contractCode, loanCode,contract.getBackFlag());
                    	}
                    	param.put("issplit", ContractConstant.ISSPLIT_1);
                    	List<Contract> list = contractDao.searchContractByoldLoanCodeAndIssplit(param);
                    	for(Contract contractTemp : list){
                    		contractTemp.setBackFlag(contract.getBackFlag());
                    		makeContractBefor(contractTemp.getContractCode(),dictOperateResult,contractTemp,loanCode,
                    				dictCheckStatus,contractVersion);
                    	}
                    }else if(ContractConstant.ISSPLIT_0.equals(loanInfo.getIssplit())){
                    	//合同制作前信息准备
                        makeContractBefor(contractCode,dictOperateResult,contract,loanCode,dictCheckStatus,contractVersion);
                    }
				} else if (ContractConstant.CUST_SERVICE_SIGN.equals(stepName)) {
					if (ContractConstant.CONTRACT_SUBMIT.equals(dictOperateResult)) {
						contract.setContractFactDay(new Date());
						contract.preUpdate();
						contractDao.updateContract(contract); 
						LoanBank loanBank = contractBusiView.getLoanBank();
						if (!ObjectHelper.isEmpty(loanBank)) {
							if (StringUtils.isNotEmpty(loanBank.getId())) {
								loanBank.preUpdate();
								loanBankDao.update(loanBank);
							} else {
								loanBank.preInsert();
								loanBankDao.insert(loanBank);
							}
						}
						/**
						 * 图片合成|App签字标识处理 并更新数据表
						 * 
						 * 
						 */
						List<PaperlessPhoto> photoes = paperlessPhotoDao.getByLoanCode(loanCode);
						String docId = null;
						Map<String, Object> param = new HashMap<String, Object>();
						String appSignFlag = contractBusiView.getAppSignFlag();
						if (StringUtils.isEmpty(appSignFlag)) {
							appSignFlag = YESNO.NO.getCode();
						}
						for (PaperlessPhoto photo : photoes) {
							if (LoanManFlag.MAIN_LOAN.getCode().equals(photo.getCustomerType())) {
								// 图片合成 暂缺
								param.put("appSignFlag", appSignFlag);
								param.put("composePhotoId", docId);
								param.put("customerId", photo.getRelationId());
								loanCustomerDao.updatePaperlessMessage(param);
							} else if (LoanManFlag.COBORROWE_LOAN.getCode().equals(photo.getCustomerType())) {
								// 图片合成 暂缺
								param.put("appSignFlag", appSignFlag);
								param.put("composePhotoId", docId);
								param.put("customerId", photo.getRelationId());
								loanCoborrowerDao.updatePaperlessMessage(param);
							}
						}
						// loanCustomerDao.updatePaperlessMessage(param);

						contractBusiView.setLastDealTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					} else {
						Contract contractNew = new Contract();
						contractNew.setContractCode(contract.getContractCode());
						contractNew.setDictCheckStatus(dictCheckStatus);
						contractNew.setBackFlag(contract.getBackFlag());
						contractNew.preUpdate();
						contractDao.updateContract(contractNew);
					}
				} else if (ContractConstant.CTR_AUDIT.equals(stepName)) {

					if (ContractConstant.CONTRACT_SUBMIT.equals(dictOperateResult)) {
						contractBusiView.setGrantFailAmount(0.0);
						contractBusiView.setRegistFlag("");
						contractBusiView.setSignUpFlag("");
						Contract oldContract = contractService.findByLoanCode(loanCode);
						LoanGrant loanGrant = new LoanGrant();
						loanGrant.setContractCode(contract.getContractCode());
						loanGrant.setLoanCode(loanCode);
						BigDecimal feePaymentAmount = contractFee.getFeePaymentAmount();
						if (feePaymentAmount != null) {
							loanGrant.setGrantAmount(feePaymentAmount);
							loanGrant.setGrantFailAmount(new BigDecimal(0));
						}
						loanGrant.setMidId(contract.getMidId());
						LoanGrantEx loan = loanGrantDao.findGrant(loanGrant);
						if (ObjectHelper.isEmpty(loan)) {
							loanGrant.preInsert();
							loanGrantDao.insertGrant(loanGrant);
						} else {
							loanGrant.setGrantBackMes(" ");
							loanGrant.preUpdate();
							loanGrantDao.updateLoanGrant(loanGrant);
						}
						Integer auditCount = oldContract.getAuditCount();
						if (ObjectHelper.isEmpty(auditCount)) {
							auditCount = 1;
						} else {
							auditCount += 1;
						}
						if (YESNO.NO.getCode().equals(contractBusiView.getUpLimit())) {
							Map<String, Object> maps = gcCeilingDao.getJXCeillingData();
							float usedLimit = ((BigDecimal) maps.get("kinnobu_used_limit")).floatValue();
							Map<String, Object> updMap = new HashMap<String, Object>();
							Integer version = (Integer) maps.get("version");
							updMap.put("id", contractBusiView.getJxId());

							updMap.put("srcVersion", version);
							updMap.put("tagVersion", version + 1);
							updMap.put("usedLimit", usedLimit + contractBusiView.getFeePaymentAmount());
							gcCeilingDao.updusedLimit(updMap);
						} else if (StringUtils.isEmpty(contractBusiView.getUpLimit())) {
							LoanInfo loanInfo = new LoanInfo();
							loanInfo.setLoanFlag(contract.getChannelFlag());
							loanInfo.setLoanCode(contract.getLoanCode());
							loanInfoDao.updateLoanInfo(loanInfo);
						} else if (YESNO.YES.getCode().equals(contractBusiView.getUpLimit())) {
							Contract curContract = contractDao.findByLoanCode(contract.getLoanCode());
							LoanInfo loanInfo = new LoanInfo();
							loanInfo.setLoanFlag(contract.getChannelFlag());
							loanInfo.setLoanCode(contract.getLoanCode());
							loanInfoDao.updateLoanInfo(loanInfo);
							String oldChannelFlag = curContract.getChannelFlag();
							contractBusiView.setOldChannelFlag(ChannelFlag.parseByCode(oldChannelFlag).getName());
							contractBusiView.setChannelFlagAdd(YESNO.NO.getCode());
							this.insertHistory(contractBusiView);
						}
						contract.setAuditCount(auditCount);
						contract.preUpdate();
						contractDao.updateContract(contract);
					} else {
						Contract contractNew = new Contract();
						contractNew.setContractCode(contract.getContractCode());
						contractNew.setDictCheckStatus(dictCheckStatus);
						contractNew.setBackFlag(contract.getBackFlag());
						contractNew.preUpdate();
						contractDao.updateContract(contractNew);
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("loanCode", contract.getLoanCode());
						LoanInfo curLoanInfo = loanInfoDao.selectByLoanCode(param);
						if (StringUtils.isNotEmpty(curLoanInfo.getFrozenCode())) { // 合同审核退回的时候，自动将已经冻结的单子驳回冻结申请。
							LoanInfo loanInfo = new LoanInfo();
							loanInfo.setLoanCode(contract.getLoanCode());
							loanInfo.setFrozenCode(" ");
							loanInfo.setFrozenReason(" ");
							loanInfo.setFrozenLastApplyTime(new Date());
							loanInfo.setFrozenFlag(YESNO.NO.getCode());
							loanInfo.preUpdate();
							loanInfoDao.update(loanInfo);
							contractBusiView.setFrozenFlag(YESNO.NO.getCode());
							contractBusiView.setLoanCode(contract.getLoanCode());
						}

					}
					contractBusiView.setGrantSureBackReason(" ");
				}
			}
		} else {

			String attrName = contractBusiView.getAttrName();
			if (StringUtils.isNotEmpty(attrName)) {
				LoanInfo loanInfo = new LoanInfo();
				Contract contract = new Contract();
				// loanInfo.setApplyId(contractBusiView.getApplyId());
				loanInfo.setLoanCode(contractBusiView.getLoanCode());

				if ("loanFlag".equals(attrName)) {

					String channelFlag = contractBusiView.getLoanFlagCode();
					String loanCode = contractBusiView.getLoanCode();
					String contractVersion = contractBusiView.getContractVersion();
					Contract curContract = contractDao.findByLoanCode(loanCode);
					contract.setLoanCode(loanCode);
					contract.setChannelFlag(channelFlag);
					loanInfo.setLoanFlag(channelFlag);
					contract.setContractVersion(contractVersion);
					loanInfoDao.update(loanInfo);
					contract.preUpdate();
					contractDao.updateContract(contract);
					String oldChannelFlag = curContract.getChannelFlag();
					if (StringUtils.isNotEmpty(oldChannelFlag)) {
						contractBusiView.setOldChannelFlag(ChannelFlag.parseByCode(oldChannelFlag).getName());
					}
					this.insertHistory(contractBusiView);
				} else if ("frozenFlag".equals(attrName)) {
					loanInfo.setFrozenCode(contractBusiView.getFrozenCode());
					loanInfo.setFrozenReason(contractBusiView.getFrozenReason());
					loanInfo.setDictLoanStatus(contractBusiView.getDictLoanStatusCode());
					loanInfo.setFrozenFlag(contractBusiView.getFrozenFlag());
					loanInfo.setFrozenLastApplyTime(new Date());
					loanInfo.preUpdate();
					loanInfoDao.update(loanInfo);
					this.insertHistory(contractBusiView);
				} else if ("paperLessFlag".equals(attrName)) {
					contract.setLoanCode(contractBusiView.getLoanCode());
					contract.setPaperLessFlag(contractBusiView.getPaperLessFlag());
					contract.preUpdate();
					contractDao.updateContract(contract);
				}

			}
		}
		workItem.setBv(contractBusiView);
	}

	private Calendar getBillDay(Date contractDueDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(contractDueDay);
		GlBill glBill = new GlBill();
		Integer signDay = calendar.get(Calendar.DAY_OF_MONTH);
		glBill.setSignDay(signDay);
		GlBill tagGlBill = glBillDao.findBySignDay(glBill);
		Integer billDay = tagGlBill.getBillDay();

		// 针对1 2号签署的合同作特别处理.当月的最后一天就得还款
		if (signDay.intValue() != ContractConstant.FIRST_DAY_OF_MONTH
				&& signDay.intValue() != ContractConstant.SECOND_DAY_OF_MONTH) {
			calendar.add(Calendar.MONTH, 1);
			Integer lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (lastDayOfMonth < billDay) {
				calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			} else {
				calendar.set(Calendar.DAY_OF_MONTH, billDay);
			}
		} else if (signDay.intValue() == ContractConstant.FIRST_DAY_OF_MONTH
				|| signDay.intValue() == ContractConstant.SECOND_DAY_OF_MONTH) {
			Integer lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			if (lastDayOfMonth < billDay) {
				calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
			} else {
				calendar.set(Calendar.DAY_OF_MONTH, billDay);
			}
		}
		return calendar;
	}
	//买金网-金信
	private  Calendar getBillHzDay(Date contractDueDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(contractDueDay);
		GlBillHz glBillhz = new GlBillHz();
		Integer signDay = calendar.get(Calendar.DAY_OF_MONTH);
		glBillhz.setSignDay(signDay);
		GlBillHz tagGlBillhz = glBillHzDao.findBySignDay(glBillhz);
		Integer billDay = tagGlBillhz.getBillDay();
		//针对7、12月份
	    if(calendar.get(Calendar.DAY_OF_MONTH)==7 || calendar.get(Calendar.DAY_OF_MONTH)==12){
	    	 if(signDay.intValue()==31){
	    		 calendar.set(Calendar.DAY_OF_MONTH, 31);
	    	 }else{
	    		 calendar.set(Calendar.DAY_OF_MONTH, billDay);
	    	 }
	    }else{
	    	calendar.add(Calendar.MONTH, 1);
	    	Integer lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    	if (lastDayOfMonth < billDay) {
	    		calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
	   		}else{
	   			calendar.set(Calendar.DAY_OF_MONTH, billDay);
	   		}
	    }

		return calendar;
	}
	// 资产家还款日处理
	private Calendar getBillHzDayZCJ(Date contractfactDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(contractfactDay);
		GlBillHz glBillhz = new GlBillHz();
		Integer signDay = calendar.get(Calendar.DAY_OF_MONTH);
		glBillhz.setSignDay(signDay);
		GlBillHz tagGlBillhz = glBillHzDao.findBySignDay(glBillhz);
		Integer billDay = tagGlBillhz.getBillDay();		
		calendar.add(Calendar.MONTH, 1);
		Integer lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (lastDayOfMonth < billDay) {
			calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
		} else {
			calendar.set(Calendar.DAY_OF_MONTH, billDay);
		}

		return calendar;
	}
	// 插入历史记录
	public void insertHistory(ContractBusiView contractBusiView) {
		String dictLoanStatus = null;
		String operateStep = null;
		String operFlag = contractBusiView.getChannelFlagAdd();
		if (StringUtils.isEmpty(operFlag)) {
			dictLoanStatus = ContractConstant.REJECT_FROZEN;
			operateStep = ContractConstant.REJECT_FROZEN;
		} else if (YESNO.YES.getCode().equals(operFlag)) {
			String channelFlag = contractBusiView.getLoanFlag();
			dictLoanStatus = ContractConstant.ADD_CHANNELFLAG.replace("${CHANNELFLAG}", channelFlag);
			operateStep = ContractConstant.ADD_CHANNELFLAG.replace("${CHANNELFLAG}", channelFlag);
		} else if (YESNO.NO.getCode().equals(operFlag)) {
			String oldChannelFlag = contractBusiView.getOldChannelFlag();
			contractBusiView.setLoanFlagCode(ChannelFlag.CHP.getCode()); // 取消金信标识
			contractBusiView.setLoanFlag(ChannelFlag.CHP.getName()); // 取消金信标识
			if (StringUtils.isEmpty(oldChannelFlag)) {
				oldChannelFlag = "";
			}
			dictLoanStatus = ContractConstant.CANCEL_CHANNELFLAG.replace("${CHANNELFLAG}", oldChannelFlag);
			operateStep = ContractConstant.CANCEL_CHANNELFLAG.replace("${CHANNELFLAG}", oldChannelFlag);
		}
		User user = UserUtils.getUser();
		LoanStatusHis record = new LoanStatusHis();
		// APPLY_ID
		record.setApplyId(contractBusiView.getApplyId());
		// 借款Code
		record.setLoanCode(contractBusiView.getLoanCode());
		// 状态
		record.setDictLoanStatus(dictLoanStatus);
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(operateStep);
		// 操作结果
		record.setOperateResult(ContractResult.CONTRACT_SUCCEED.getName());
		// 备注
		String remarks = contractBusiView.getRemarks();
		if (StringUtils.isNotEmpty(remarks)) {
			if (remarks.trim().startsWith(",") || remarks.trim().endsWith(",")) {
				remarks = remarks.trim().replace(",", "");

			}
			record.setRemark(remarks);
		}

		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 设置Crud属性值
		record.preInsert();
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		// 操作人记录当前登陆系统用户名称
		record.setOperator(user.getName());
		if (!ObjectHelper.isEmpty(user.getRole())) {
			// 操作人角色
			record.setOperateRoleId(user.getRole().getId());
		}
		if (!ObjectHelper.isEmpty(user.getDepartment())) {
			// 机构编码
			record.setOrgCode(user.getDepartment().getId());
		}
		loanStatusHisDao.insertSelective(record);
	}
	
	
	public void makeContractBefor(String contractCode,String dictOperateResult,Contract contract,
    		String loanCode,String dictCheckStatus,String contractVersion){

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("contractCode", contractCode);
        // 删除旧有的还款期供信息
        List<PaybackMonth> paybackMonths = paybackMonthDao.findByContractCode(param);
        if(!ObjectHelper.isEmpty(paybackMonths)){
            paybackMonthDao.deleteByContractCode(param);  
        }                
        Payback payback = new Payback();
        Map<String,Object> paybackParam = new HashMap<String,Object>();
          paybackParam.put("contractCode", contractCode);
         Payback apply = paybackDao.selectpayBack(paybackParam);
        // 删除旧有的还款信息
        if(!ObjectHelper.isEmpty(apply)){
            paybackDao.deletePayback(contractCode); 
        }
        if(ContractConstant.CONTRACT_SUBMIT.equals(dictOperateResult)){
            Contract tempContract = contractService.findByContractCode(contractCode);
            ContractFee tempContractFee = contractFeeService.findByContractCode(contractCode);
            // 设置合同表中的状态为合同制作中，以供批处理扫描
            contract.setDictCheckStatus(LoanApplyStatus.CONTRACT_MAKING.getCode());
            contract.preUpdate();
            contractDao.updateContract(contract);
            List<RepayPlanNew> repayPlan = null;
            if(LoanProductCode.PRO_NONG_XIN_JIE.equals(tempContract.getProductType())){
            	repayPlan = ReckonFeeNew.createNXDRepayPlanNew(tempContract,tempContractFee);
            }else{
            	repayPlan = ReckonFeeNew.createRepayPlanNew(tempContract,tempContractFee,glBillDao); 
            }
             
            PaybackMonth paybackMonth = null;
            param.put("loanCode", loanCode);
            LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
            String customerCode = loanCustomer.getCustomerCode();
            RepayPlanNew curPlan = repayPlan.get(0);
            payback.setContractCode(contractCode);
            payback.setCustomerCode(customerCode);
            payback.setPaybackCurrentMonth(curPlan.getPayBackCurrentMonth());
            if(LoanProductCode.PRO_NONG_XIN_JIE.equals(tempContract.getProductType())){
            	payback.setPaybackMonthAmount(new BigDecimal(0));
            }else{
            	payback.setPaybackMonthAmount(curPlan.getMonthPaySum());
            }
            payback.setEffectiveFlag(YESNO.NO.getCode());
            payback.setPaybackBuleAmount(BigDecimal.ZERO);
            Date contractDueDay = tempContract.getContractDueDay();     // 预约签署日期
            Calendar calendar = this.getBillDay(contractDueDay);
            calendar.setTime(contractDueDay);
            GlBill glBill = new GlBill();
            glBill.setSignDay(calendar.get(Calendar.DAY_OF_MONTH));
            GlBill tagGlBill = glBillDao.findBySignDay(glBill);
            Integer billDay = tagGlBill.getBillDay();
     		payback.setPaybackDay(billDay);
            payback.preInsert();
            paybackDao.insertPayback(payback);
            for(RepayPlanNew curr:repayPlan){
                paybackMonth = new PaybackMonth();
                // 合同编号
                paybackMonth.setContractCode(contractCode);
                // 总期数
                paybackMonth.setMonths(curr.getPayBackCurrentMonth());
                // 每期还款日
                paybackMonth.setMonthPayDay(curr.getMonthPayDay());
                // 每月还款总额
                paybackMonth.setMonthPayTotal(curr.getMonthPaySum());
                // 分期咨询费
                paybackMonth.setMonthFeeConsult(curr.getStageFeeConsult());
                // 分期居间服务费
                paybackMonth.setMonthMidFeeService(curr.getStageFeeService());
                // 分期服务费
                paybackMonth.setMonthFeeService(curr.getStageServiceFee());
                // 应还违约金(1.3系统单算罚息,1.4一次性还款违约罚金)
                paybackMonth.setMonthPenaltyShould(curr.getOncePayPenalty());
                // 应还利息
                paybackMonth.setMonthInterestBackshould(curr.getMonthInterestBackshould());
                // 应还本金
                paybackMonth.setMonthPayAmount(curr.getMonthPayAmount());
                // 一次性应还总额
                paybackMonth.setMonthBeforeFinishAmount(curr.getOncePaySum());
                // month_residue_payactual		还款后剩余本金
                paybackMonth.setMonthResiduePayactual(curr.getResidualPrincipal());
                paybackMonth.setDictMonthStatus(PeriodStatus.REPAYMENT.getCode());
                paybackMonth.preInsert();
                paybackMonthDao.insertPaybackMonth(paybackMonth);
            }
        }else{
            Contract contractNew = new Contract();
            contractNew.setContractCode(contract.getContractCode());
            contractNew.setDictCheckStatus(dictCheckStatus);
            contractNew.setBackFlag(contract.getBackFlag());
            contractNew.preUpdate();
            contractDao.updateContract(contractNew);
        }
    
    }
	
}
