package com.creditharmony.loan.borrow.applyinfo.event;

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
import com.creditharmony.core.loan.type.RejectDepartment;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.applyinfo.view.UploadView;
import com.creditharmony.loan.borrow.contract.entity.ex.LoanCustomerEx;
import com.creditharmony.loan.borrow.reconsider.view.ReconsiderBusinessView;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.sms.service.CheckRemindSmsService;

/**
 * @Class Name FileUploadEx
 * @author zhangerwei
 * @Create In 2015年12月23日
 */
@Service("ex_hj_loanFlow_fileUpload")
public class FileUploadEx extends BaseService implements ExEvent {

	@Autowired
	private ApplyLoanInfoDao loaninfoDao;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private LoanStatusHisDao loanStatusHisDao;

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private CheckRemindSmsService checkRemindMsmService;

	/**
	 * 流程回调函数实现
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		String stepName = workItem.getStepName();
		// 如果流程名称是资料上传,更新主表流程状态信息,插入历史
		if (LoanCustomerEx.INFORMATION_UPLOAD.equals(stepName)) {
			UploadView uploadView = (UploadView) workItem.getBv();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("loanCode", uploadView.getLoanCustomer().getLoanCode());
			if (StringUtils.isEmpty(uploadView.getLoanCustomer().getLoanCode())) {
				throw new RuntimeException("FileUploadEx----66----loanCode is emtpy");
			}
			LoanInfo loanInfo = new LoanInfo();
			if ("TO_STORECHECK".equals(workItem.getResponse())) {
				param.put("dictLoanStatus", LoanApplyStatus.STORE_REVERIFY.getCode());
				// 更改流程状态
				uploadView.setDictLoanStatus(LoanApplyStatus.STORE_REVERIFY.getName());
				uploadView.setDictLoanStatusCode(LoanApplyStatus.STORE_REVERIFY.getCode());
				// 插入历史
				String operateStep = LoanApplyStatus.INFORMATION_UPLOAD.getName();
				String operateResult = "成功";
				String remark = "上传资料到待门店复核";
				if (StringUtils.isNotEmpty(uploadView.getApplyId())) {
					loanInfo.setApplyId(uploadView.getApplyId());
				}
				if (!ObjectHelper.isEmpty(uploadView.getLoanCustomer())) {
					if (StringUtils.isNotEmpty(uploadView.getLoanCustomer().getLoanCode())) {
						loanInfo.setLoanCode(uploadView.getLoanCustomer().getLoanCode());
					}
				}
				LoanInfo curLoanInfo = loaninfoDao.findStatusByLoanCode(uploadView.getLoanCustomer().getLoanCode());
				String statusCode = LoanApplyStatus.STORE_REVERIFY.getCode() + "-0" + curLoanInfo.getLoanUrgentFlag() + "-00";
				OrderFiled filed = OrderFiled.parseByCode(statusCode);
				uploadView.setOrderField(filed.getOrderId() + "-" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				loanInfo.setDictLoanStatus(LoanApplyStatus.INFORMATION_UPLOAD.getCode());
				historyService.saveLoanStatusHis(loanInfo, operateStep, operateResult, remark);
				
			} else if ("TO_GIVEUP".equals(workItem.getResponse())) {
				param.put("dictLoanStatus", LoanApplyStatus.CUSTOMER_GIVEUP.getCode());
				// 更改流程状态
				uploadView.setDictLoanStatus(LoanApplyStatus.CUSTOMER_GIVEUP.getName());
				uploadView.setDictLoanStatusCode(LoanApplyStatus.CUSTOMER_GIVEUP.getCode());
				// 插入历史
				String operateStep = LoanApplyStatus.INFORMATION_UPLOAD.getName();
				String operateResult = "成功";
				String remark = "客户放弃";
				if (StringUtils.isNotEmpty(uploadView.getApplyId())) {
					loanInfo.setApplyId(uploadView.getApplyId());
				}
				if (!ObjectHelper.isEmpty(uploadView.getLoanCustomer())) {
					if (StringUtils.isNotEmpty(uploadView.getLoanCustomer().getLoanCode())) {
						loanInfo.setLoanCode(uploadView.getLoanCustomer().getLoanCode());
					}
				}
				loanInfo.setDictLoanStatus(LoanApplyStatus.INFORMATION_UPLOAD.getCode());
				historyService.saveLoanStatusHis(loanInfo, operateStep, operateResult, remark);
			}
			// 更新主表流程状态
			loaninfoDao.updateLoanStatus(param);
			/*if("TO_STORECHECK".equals(workItem.getResponse())){
				//上传资料成功以后， 进行短信提醒
				checkRemindMsmService.sendMsm(uploadView.getLoanCustomer().getLoanCode());
			}*/
		}
		// 流程名称是门店复核
		else if (LoanCustomerEx.STORE_REVERIFY.equals(stepName) || LoanCustomerEx.APPLY_ENGINE_BACK.equals(stepName) || LoanCustomerEx.BACK_STORE.equals(stepName)) {
			LaunchView launchView = (LaunchView) workItem.getBv();
			String operType = launchView.getOperType();
			if (!YESNO.NO.getCode().equals(operType)) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("loanCode", launchView.getLoanCustomer().getLoanCode());
				if (StringUtils.isEmpty(launchView.getLoanCustomer().getLoanCode())) {
					throw new RuntimeException("FileUploadEx----124----loanCode is emtpy");
				}
				String dictLoanStatus = areaService.getStatusByApplyId(launchView.getApplyId());
				LoanInfo loanInfo = new LoanInfo();
				if ("TO_GIVEUP".equals(workItem.getResponse())) {
					String operateStep = "";
					String remark = "";
					if (StringUtils.isNotEmpty(launchView.getRejectReason())) {
						param.put("dictLoanStatus", LoanApplyStatus.STORE_REJECT.getCode());
						param.put("visitFlag", RejectDepartment.LOAN_REJECT.getCode());
						// 更改流程状态
						launchView.setDictLoanStatus(LoanApplyStatus.STORE_REJECT.getName());
						launchView.setDictLoanStatusCode(LoanApplyStatus.STORE_REJECT.getCode());
						operateStep = LoanApplyStatus.STORE_REJECT.getName();
						remark = launchView.getRejectReason();

					} else {
						param.put("dictLoanStatus", LoanApplyStatus.CUSTOMER_GIVEUP.getCode());
						param.put("visitFlag", RejectDepartment.LOAN_GIVE.getCode());
						// 更改流程状态
						launchView.setDictLoanStatus(LoanApplyStatus.CUSTOMER_GIVEUP.getName());
						launchView.setDictLoanStatusCode(LoanApplyStatus.CUSTOMER_GIVEUP.getCode());
						operateStep = LoanApplyStatus.CUSTOMER_GIVEUP.getName();
						remark = "客户放弃";
					}
					String operateResult = "成功";
					if (StringUtils.isNotEmpty(launchView.getApplyId())) {
						loanInfo.setApplyId(launchView.getApplyId());
					}
					if (!ObjectHelper.isEmpty(launchView.getLoanCustomer())) {
						if (StringUtils.isNotEmpty(launchView.getLoanCustomer().getLoanCode())) {
							loanInfo.setLoanCode(launchView.getLoanCustomer().getLoanCode());
						}
					}
					if (LoanCustomerEx.STORE_REVERIFY.equals(stepName)) {
						loanInfo.setDictLoanStatus(LoanApplyStatus.STORE_REVERIFY.getCode());
					} else if (LoanCustomerEx.APPLY_ENGINE_BACK.equals(stepName)) {
						loanInfo.setDictLoanStatus(LoanApplyStatus.STORE_REVERIFY.getCode());
					} else if (LoanCustomerEx.BACK_STORE.equals(stepName)) {
						loanInfo.setDictLoanStatus(LoanApplyStatus.STORE_REVERIFY.getCode());
					}
					historyService.saveLoanStatusHis(loanInfo, operateStep, operateResult, remark);
					// 更新主表流程状态
					loaninfoDao.updateLoanStatus(param);
				} else {
					if (LoanApplyStatus.STORE_REVERIFY.getCode().equals(dictLoanStatus) || LoanApplyStatus.APPLY_ENGINE_BACK.getCode().equals(dictLoanStatus)) {
						param.put("dictLoanStatus", LoanApplyStatus.RULE_ENGINE.getCode());
						// 更新主表流程状态
						loaninfoDao.updateLoanStatus(param);
						// 更改流程状态
						launchView.setDictLoanStatus(LoanApplyStatus.RULE_ENGINE.getName());
						launchView.setDictLoanStatusCode(LoanApplyStatus.RULE_ENGINE.getCode());
						// 插入历史
						String operateStepNew = "";
						String operateResult = "成功";
						String remark = "";
						if (LoanApplyStatus.STORE_REVERIFY.getCode().equals(dictLoanStatus)) {
							remark = "待门店复核到待汇诚审核";
							operateStepNew = LoanApplyStatus.STORE_REVERIFY.getName();
							loanInfo.setDictLoanStatus(LoanApplyStatus.STORE_REVERIFY.getCode());
							// 更新出汇金时间
							Date outtoLoanTime = new Date();
							launchView.setOuttoLoanTime(outtoLoanTime);
							param.put("outtoLoanTime", launchView.getOuttoLoanTime());
							loaninfoDao.updateOuttoLoanTime(param);
						} else if (LoanApplyStatus.APPLY_ENGINE_BACK.getCode().equals(dictLoanStatus)) {
							remark = "规则引擎退回重新提交汇诚审核";
							operateStepNew = "门店复核（规则引擎退回）";
							loanInfo.setDictLoanStatus(LoanApplyStatus.APPLY_ENGINE_BACK.getCode());
						}
						if (StringUtils.isNotEmpty(launchView.getApplyId())) {
							loanInfo.setApplyId(launchView.getApplyId());
						}
						if (!ObjectHelper.isEmpty(launchView.getLoanCustomer())) {
							if (StringUtils.isNotEmpty(launchView.getLoanCustomer().getLoanCode())) {
								loanInfo.setLoanCode(launchView.getLoanCustomer().getLoanCode());
							}
						}
						historyService.saveLoanStatusHis(loanInfo, operateStepNew, operateResult, remark);
					} else if (LoanApplyStatus.BACK_STORE.getCode().equals(dictLoanStatus)) {
						param.put("dictLoanStatus", LoanApplyStatus.BACK_FINISH_CHECK.getCode());
						// 更新主表流程状态
						loaninfoDao.updateLoanStatus(param);
						// 判断该笔单子是否走过确认签署 复议的查复议的确认签署、信借的查信借的确认签署
						LoanStatusHis queryParam = new LoanStatusHis();
						queryParam.setApplyId(launchView.getApplyId());
						queryParam.setDictSysFlag(ModuleName.MODULE_LOAN.value);
						queryParam.setDictLoanStatus(LoanApplyStatus.SIGN_CONFIRM.getCode());
						List<LoanStatusHis> hisList = loanStatusHisDao.findWantedNoteByApplyId(queryParam);
						// 更改流程状态
						launchView.setDictLoanStatus(LoanApplyStatus.BACK_FINISH_CHECK.getName());
						launchView.setDictLoanStatusCode(LoanApplyStatus.BACK_FINISH_CHECK.getCode());
						// 停止签约时间检测
						if (ObjectHelper.isEmpty(hisList) || hisList.size() == 0) {
							launchView.setTimeOutFlag(YESNO.NO.getCode());
						}
						// 插入历史
						String operateStepNew = LoanApplyStatus.BACK_STORE.getName();
						String operateResult = "成功";
						String remark = "";
						remark = "退回门店到待初审";
						if (StringUtils.isNotEmpty(launchView.getApplyId())) {
							loanInfo.setApplyId(launchView.getApplyId());
						}
						if (!ObjectHelper.isEmpty(launchView.getLoanCustomer())) {
							if (StringUtils.isNotEmpty(launchView.getLoanCustomer().getLoanCode())) {
								loanInfo.setLoanCode(launchView.getLoanCustomer().getLoanCode());
							}
						}
						loanInfo.setDictLoanStatus(LoanApplyStatus.BACK_STORE.getCode());
						historyService.saveLoanStatusHis(loanInfo, operateStepNew, operateResult, remark);
					}
				}
				workItem.setBv(launchView);
			}
		}
		// 流程名是复议退回
		else if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(stepName)) {
			ReconsiderBusinessView rb = (ReconsiderBusinessView) workItem.getBv();
			String operType = rb.getOperType();
			if (!YESNO.NO.getCode().equals(operType)) {

				// 判断该笔单子是否走过确认签署 复议的查复议的确认签署、信借的查信借的确认签署
				LoanStatusHis queryParam = new LoanStatusHis();
				queryParam.setApplyId(rb.getApplyId());
				queryParam.setDictSysFlag(ModuleName.MODULE_LOAN.value);
				queryParam.setDictLoanStatus(LoanApplyStatus.SIGN_CONFIRM.getCode());
				List<LoanStatusHis> hisList = loanStatusHisDao.findWantedNoteByApplyId(queryParam);
				if (ObjectHelper.isEmpty(hisList) || hisList.size() == 0) {
					// 停止签约时间检测
					rb.setTimeOutFlag(YESNO.NO.getCode());
				}
				Map<String, Object> param = new HashMap<String, Object>();
				String oldApplyId = areaService.getApplyId(rb.getApplyId());
				param.put("loanCode", rb.getReconsiderApplyEx().getLoanCode());
				if (StringUtils.isEmpty(rb.getReconsiderApplyEx().getLoanCode())) {
					throw new RuntimeException("FileUploadEx----267----loanCode is emtpy");
				}
				// param.put("applyId", rb.getApplyId());
				String dictLoanStatus = areaService.getStatusByApplyId(oldApplyId);
				LoanInfo loanInfo = new LoanInfo();
				if ("TO_GIVEUP".equals(workItem.getResponse())) {
					String operateStep = "";
					String remark = "";
					if (StringUtils.isNotEmpty(rb.getRejectReason())) {
						param.put("dictLoanStatus", LoanApplyStatus.STORE_REJECT.getCode());
						param.put("visitFlag", RejectDepartment.LOAN_REJECT.getCode());
						// 更改流程状态
						rb.setDictLoanStatus(LoanApplyStatus.STORE_REJECT.getName());
						rb.setDictLoanStatusCode(LoanApplyStatus.STORE_REJECT.getCode());
						operateStep = LoanApplyStatus.STORE_REJECT.getName();
						remark = rb.getRejectReason();
					} else {
						param.put("visitFlag", RejectDepartment.LOAN_GIVE.getCode());
						param.put("dictLoanStatus", LoanApplyStatus.CUSTOMER_GIVEUP.getCode());
						// 更改流程状态
						rb.setDictLoanStatus(LoanApplyStatus.CUSTOMER_GIVEUP.getName());
						rb.setDictLoanStatusCode(LoanApplyStatus.CUSTOMER_GIVEUP.getCode());
						// 插入历史
						operateStep = LoanApplyStatus.RECONSIDER_BACK_STORE.getName();
						remark = "客户放弃";
					}
					String operateResult = "成功";
					if (StringUtils.isNotEmpty(rb.getApplyId())) {
						loanInfo.setApplyId(rb.getApplyId());
					}
					if (!ObjectHelper.isEmpty(rb.getLoanCustomer())) {
						if (StringUtils.isNotEmpty(rb.getLoanCustomer().getLoanCode())) {
							loanInfo.setLoanCode(rb.getLoanCustomer().getLoanCode());
						}
					}
					loanInfo.setDictLoanStatus(LoanApplyStatus.RECONSIDER_BACK_STORE.getCode());
					historyService.saveLoanStatusHis(loanInfo, operateStep, operateResult, remark);
					// 更新主表流程状态
					loaninfoDao.updateLoanStatus(param);
				} else {
					if (LoanApplyStatus.RECONSIDER_BACK_STORE.getCode().equals(dictLoanStatus)) {
						param.put("dictLoanStatus", LoanApplyStatus.RECONSIDER_BACK_FINISH_CHECK.getCode());
						// 更新主表流程状态
						loaninfoDao.updateLoanStatus(param);
						// 更改流程状态
						rb.setDictLoanStatus(LoanApplyStatus.RECONSIDER_BACK_FINISH_CHECK.getName());
						rb.setDictLoanStatusCode(LoanApplyStatus.RECONSIDER_BACK_FINISH_CHECK.getCode());
						// 插入历史
						String operateStepNew = LoanApplyStatus.RECONSIDER_BACK_STORE.getName();
						String operateResult = "成功";
						String remark = "";
						remark = "复议退回门店到复议待初审";
						if (StringUtils.isNotEmpty(rb.getApplyId())) {
							loanInfo.setApplyId(rb.getApplyId());
						}
						if (!ObjectHelper.isEmpty(rb.getReconsiderApplyEx())) {
							if (StringUtils.isNotEmpty(rb.getReconsiderApplyEx().getLoanCode())) {
								loanInfo.setLoanCode(rb.getReconsiderApplyEx().getLoanCode());
							}
						}
						loanInfo.setDictLoanStatus(LoanApplyStatus.RECONSIDER_BACK_STORE.getCode());
						historyService.saveLoanStatusHis(loanInfo, operateStepNew, operateResult, remark);
					}
				}
				workItem.setBv(rb);
			}
		}
	}
}
