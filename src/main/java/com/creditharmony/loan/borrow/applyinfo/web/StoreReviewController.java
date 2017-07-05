package com.creditharmony.loan.borrow.applyinfo.web;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.Marriage;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.dao.CustomerBaseInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCoborrowerService;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCustomerService;
import com.creditharmony.loan.borrow.applyinfo.service.LoanRemarkService;
import com.creditharmony.loan.borrow.applyinfo.service.ModifyInfoService;
import com.creditharmony.loan.borrow.applyinfo.service.StoreReviewService;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.contract.entity.ex.LoanCustomerEx;
import com.creditharmony.loan.borrow.reconsider.view.ReconsiderBusinessView;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.AuditBack;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.ex.ApproveDictEx;
import com.creditharmony.loan.common.service.BackStoreService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;


/**
 * 门店复核
 * @Class Name StoreReviewController
 * @author lirui
 * @Create In 2016年1月22日
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/storeReviewController")
public class StoreReviewController extends BaseController {

	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	
	@Autowired
	private StoreReviewService storeReviewService;
	
	@Autowired
    private LoanStatusHisDao loanStatusHisDao;
		
	@Autowired
    private AreaService areaService;
	
	@Autowired
	private ModifyInfoService modifyInfoService;
	
	@Autowired
	private LoanRemarkService loanRemarkService;
	
	@Autowired
	private CustomerBaseInfoDao customerBaseInfoDao;
	
	@Autowired
    private BackStoreService backStoreService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private LoanCoborrowerService loanCoborrowerService;
	
	@Autowired
	private LoanCustomerService loanCustomerService;
	/**
	 * 客户信息修改*******此方法适用于旧版申请表
	 * 2016年1月15日
	 * By lirui
	 * @param redirectAttributes
	 * @param workItem 重定向数据参数
	 * @param method 方法标识
	 * @param launchView 修改后的数据
	 * @param redirectUrl 重定向地址
	 * @return 重定向回门店复核页面
	 */
	@RequestMapping(value = "storeReviewUpdate")
	public String customerSave(RedirectAttributes redirectAttributes,WorkItemView workItem,String method,LaunchView launchView,String redirectUrl) {		
		// 重定向到门店复核页面参数
		String dealType = "0";
		String msg = "";
		String preResponse = launchView.getPreResponse();
		redirectUrl = "/bpm/flowController/openForm";
		redirectAttributes.addAttribute("applyId", launchView.getApplyId());
		redirectAttributes.addAttribute("wobNum", workItem.getWobNum());
		redirectAttributes.addAttribute("dealType", dealType);
		redirectAttributes.addAttribute("token", workItem.getToken());		
			// 方法标识是customer则进行客户资料编辑
			if ("customer".equals(method)) {
				int cusFlag = storeReviewService.updateLoanCustomer(launchView.getLoanCustomer(),preResponse);
				int livFlag = storeReviewService.updateCustomerLivings(launchView.getCustomerLivings());
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
				    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb);                   
                }else {
                    launchView.setOperType(YESNO.NO.getCode());
                    workItem.setBv(launchView);
                }
				
				flowService.saveData(workItem);
				// 修改结果提示信息	
				if (cusFlag != 0 && livFlag != 0) {
					msg = "客户资料修改成功!";
					addMessage(redirectAttributes, msg);				
				}else{
					msg = "客户资料修改失败!";
					addMessage(redirectAttributes, msg);
				}
			} 			
			// 方法标识是mate,进行配偶资料编辑 
			else if ("mate".equals(method)) {				
				int mateFlag = storeReviewService.updateLoanMate(launchView,preResponse);
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb);                   
                }else {
                    launchView.setOperType(YESNO.NO.getCode());
				    workItem.setBv(launchView);
                }
				flowService.saveData(workItem);
				// 修改结果提示信息
				if (mateFlag != 0) {
					msg = "配偶信息修改成功!";
					addMessage(redirectAttributes, msg);
				} else {
					msg = "配偶信息修改失败!";
					addMessage(redirectAttributes, msg);
				}
			}
			// 方法标识是apply,进行申请信息编辑 
			else if ("apply".equals(method)) {
				int applyFlag = storeReviewService.updateLoanInfo(launchView.getLoanInfo());
				if (!ObjectHelper.isEmpty(launchView.getLoanRemark())) {
					loanRemarkService.updateByIdSelective(launchView.getLoanRemark());					
				}
				if (launchView.getLoanInfo().getLoanApplyAmount() !=null && launchView.getLoanInfo().getLoanApplyAmount().compareTo(BigDecimal.ZERO)>0) {
					float loanApplyAmountf = launchView.getLoanInfo().getLoanApplyAmount().floatValue();
					launchView.getLoanInfo().setLoanApplyAmountf(loanApplyAmountf);
				}
/*				workItem.setBv(launchView);
				flowService.saveData(workItem);*/
				// 更新流程属性
				if (StringUtils.isNotEmpty(launchView.getLoanInfo().getProductType())) {
					launchView.setProductCode(launchView.getLoanInfo().getProductType());
					launchView.setProductName(areaService.getProductNameByCode(launchView.getLoanInfo().getProductType()));
				}
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb);                   
                }else {
                    launchView.setOperType(YESNO.NO.getCode());
                    workItem.setBv(launchView);
                }
				flowService.saveData(workItem);
				// 修改结果提示信息
				if (applyFlag != 0) {
					msg = "申请信息修改成功!";
					addMessage(redirectAttributes, msg);
				} else {
					msg = "申请信息修改失败!";
					addMessage(redirectAttributes, msg);
				}
			}
			// 方法标识是company,进行职业信息/公司资料编辑
			else if ("company".equals(method)) {
				launchView.getCustomerLoanCompany().setLoanCode(launchView.getLoanInfo().getLoanCode());
				launchView.getCustomerLoanCompany().setDictrCustomterType(LoanManFlag.MAIN_LOAN.getCode());
				int companyFlag = storeReviewService.updateLoanCompany(launchView.getCustomerLoanCompany(),preResponse);
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb);                   
                }else {
                    launchView.setOperType(YESNO.NO.getCode());
				    workItem.setBv(launchView);
                }
				flowService.saveData(workItem);
				// 修改结果提示信息
				if (companyFlag != 0) {
					msg = "职业信息/公司资料修改成功!";
					addMessage(redirectAttributes, msg);
				} else {
					msg = "职业信息/公司资料修改失败!";
					addMessage(redirectAttributes, msg);
				}
			}
			// 方法标识是bank,进行银行卡资料编辑
			else if ("bank".equals(method)) {
				int bankFlag = storeReviewService.updateLoanBank(launchView.getLoanBank());
				Integer bankIsRareword = launchView.getLoanBank().getBankIsRareword();
				if(ObjectHelper.isNotEmpty(bankIsRareword) && bankIsRareword.intValue()==1){
	                launchView.setBankIsRareword(YESNO.YES.getCode());
	            }else{
	                launchView.setBankIsRareword(YESNO.NO.getCode());
	            }
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb);                   
                }else {
                    launchView.setOperType(YESNO.NO.getCode());
                    workItem.setBv(launchView);
                }
				flowService.saveData(workItem);
				// 修改结果提示信息
				if (bankFlag != 0) {
					msg = "银行卡资料修改成功!";
					addMessage(redirectAttributes, msg);
				} else {
					msg = "银行卡资料修改失败!";
					addMessage(redirectAttributes, msg);
				}
			}
			// 方法标识是credit,进行信用资料编辑
			else if ("credit".equals(method)) {
				int creditFlag = 0;
				for (int i = 0; i < launchView.getLoanCreditInfoList().size(); i++) {
					launchView.getLoanCreditInfoList().get(i).setLoanCode(launchView.getLoanInfo().getLoanCode());
					creditFlag = storeReviewService.updateLoanCreditInfo(launchView.getLoanCreditInfoList().get(i));
				}
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb); 					
				}else {
				    launchView.setOperType(YESNO.NO.getCode());
					workItem.setBv(launchView);					
				}
				flowService.saveData(workItem);
				if (creditFlag != 0) {
					msg = "信用资料修改成功!";
					addMessage(redirectAttributes, msg);
				} else {
					msg = "信用资料修改失败!";
					addMessage(redirectAttributes, msg);
				}
			}
			// 方法标识是house,进行房产资料编辑
			else if ("house".equals(method)) {
				int houseFlag = 0;
				for (int i = 0; i < launchView.getCustomerLoanHouseList().size(); i++) {
					launchView.getCustomerLoanHouseList().get(i).setLoanCode(launchView.getLoanInfo().getLoanCode());
					launchView.getCustomerLoanHouseList().get(i).setRcustomerCoborrowerId(launchView.getLoanCustomer().getId());
					launchView.getCustomerLoanHouseList().get(i).setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
					houseFlag = storeReviewService.updateLoanLoanHouse(launchView.getCustomerLoanHouseList().get(i));
				}
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb); 					
				}else {
				    launchView.setOperType(YESNO.NO.getCode());
					workItem.setBv(launchView);					
				}
				flowService.saveData(workItem);
				if (houseFlag != 0) {
					msg = "房产资料修改成功!";
					addMessage(redirectAttributes, msg);
				} else {
					msg = "房产资料修改失败!";
					addMessage(redirectAttributes, msg);
				}
			}
			// 方法标识是contract,进行联系人资料编辑
			else if ("contact".equals(method)) {
				int contactFlag = 0;
				for (int i = 0; i < launchView.getCustomerContactList().size(); i++) {
					launchView.getCustomerContactList().get(i).setRcustomerCoborrowerId(launchView.getLoanCustomer().getId());
					launchView.getCustomerContactList().get(i).setLoanCode(launchView.getLoanInfo().getLoanCode());
					launchView.getCustomerContactList().get(i).setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
					contactFlag = storeReviewService.updateContact(launchView.getCustomerContactList().get(i),preResponse);
				}
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb); 					
				}else {
				    launchView.setOperType(YESNO.NO.getCode());
					workItem.setBv(launchView);					
				}
				flowService.saveData(workItem);
				if (contactFlag != 0) {
					msg = "联系人料修改成功!";
					addMessage(redirectAttributes, msg);
				} else {
					msg = "联系人资料修改失败!";
					addMessage(redirectAttributes, msg);
				}
			}
			// 方法标识是cobo,进行共借人资料编辑
			else if ("cobo".equals(method)) {
				int coboFlag = 0;
				coboFlag = storeReviewService.updateLoanCoborrower(launchView,preResponse);
				this.setCoborrowers(launchView);
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb); 				
				}else {
				    launchView.setOperType(YESNO.NO.getCode());
					workItem.setBv(launchView);					
				}
				flowService.saveData(workItem);
				if (coboFlag != 0) {
					msg = "共借人资料修改成功!";
					addMessage(redirectAttributes, msg);
				} else {
					msg = "共借人资料修改失败!";
					addMessage(redirectAttributes, msg);
				}
			}	
			// 方法标识是coboUpdate,进行共借人修改(退回门店)
			else if ("coboUpdate".equals(method)) {
				int coboUpdateFlag = 0;
				coboUpdateFlag = storeReviewService.updateLoanCoborrower(launchView,preResponse);
				this.setCoborrowers(launchView);
				if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                    rb.setOperType(YESNO.NO.getCode());
                    workItem.setBv(rb); 				
				}else {
				    launchView.setOperType(YESNO.NO.getCode());
					workItem.setBv(launchView);					
				}
				flowService.saveData(workItem);
				// 插入修改详细信息记录
				if (launchView.getLoanCoborrower().size() != 0) {
					modifyInfoService.addModifyInfo(launchView);					
				}
				if (coboUpdateFlag != 0) {
					msg = "共借人资料修改成功!";
					addMessage(redirectAttributes, msg);
				} else {
					msg = "共借人资料修改失败!";
					addMessage(redirectAttributes, msg);
				}
			}
		return "redirect:" + adminPath + redirectUrl;
	} 
	/**
	 * 此方法适用于新版申请表
	 */
	@RequestMapping(value = "storeReviewUpdate_new")
	public String customerSave_new(RedirectAttributes redirectAttributes,WorkItemView workItem,String method,LaunchView launchView,String flag,String viewName) {		
		//重定向到门店复核页面的不同页签
		String redirectUrl=null;
		if(flag.equals(ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER)){
			redirectUrl="/bpm/flowController/openForm";
			redirectAttributes.addAttribute("applyId", launchView.getApplyId());
			redirectAttributes.addAttribute("wobNum", workItem.getWobNum());
			redirectAttributes.addAttribute("token", workItem.getToken());
			redirectAttributes.addAttribute("loanInfoOldOrNewFlag", "1");
		}else{
			redirectUrl="/apply/dataEntry/getApplyInfo_new_storeView";
			redirectAttributes.addAttribute("flag", flag);
			redirectAttributes.addAttribute("viewName", viewName);
			redirectAttributes.addAttribute("customerCode", launchView.getCustomerCode());
			redirectAttributes.addAttribute("consultId", launchView.getConsultId());
			redirectAttributes.addAttribute("loanCode", launchView.getLoanCode());
			redirectAttributes.addAttribute("loanCustomer.id", launchView.getLoanCustomer().getId());
			redirectAttributes.addAttribute("applyId", launchView.getApplyId());
			redirectAttributes.addAttribute("preResponse", launchView.getPreResponse());
			redirectAttributes.addAttribute("wobNum", workItem.getWobNum());
			redirectAttributes.addAttribute("token", workItem.getToken());
			redirectAttributes.addAttribute("lastLoanStatus", launchView.getLastLoanStatus());
			redirectAttributes.addAttribute("loanCustomer.loanCode", launchView.getLoanCustomer().getLoanCode());
			try {
		    	redirectAttributes.addAttribute("flowName", URLEncoder.encode(workItem.getFlowName(), "UTF-8"));
		    	redirectAttributes.addAttribute("stepName", URLEncoder.encode(workItem.getStepName(), "UTF-8"));
		    } catch (UnsupportedEncodingException e) {
		    	logger.error("新版门店复核页面get传参中文处理传入参数异常", e);
		    	e.printStackTrace();
		    }
		}
		String msg = "";
		String preResponse = launchView.getPreResponse();
		// 方法标识是customer则进行客户资料编辑
		if ("customer".equals(method)) {
			//由何处了解到我公司字段
		    String[] dictCustomerSourceNewStr=launchView.getLoanCustomer().getDictCustomerSourceNewStr();
		    if(dictCustomerSourceNewStr!=null){
		    	String temp="";
		    	for (String dictCustomerSourceNew : dictCustomerSourceNewStr) {
		    		temp+=dictCustomerSourceNew+",";
		    	}
		    	launchView.getLoanCustomer().setDictCustomerSourceNew(temp);
		    	//由何处了解到我公司选择其他时的备注字段
		    	if(temp.indexOf("6")==-1){
		    		launchView.getLoanCustomer().setDictCustomerSourceNewOther("");
		    	}
		    }
		    //住宅类别
		    String customerHouseHoldProperty=launchView.getCustomerLivings().getCustomerHouseHoldProperty();
		    if(!"7".equals(customerHouseHoldProperty)){ //7表示其他
		    	launchView.getCustomerLivings().setCustomerHouseHoldPropertyNewOther("");
		    }
			int cusFlag = storeReviewService.updateLoanCustomer(launchView.getLoanCustomer(),preResponse);
			int livFlag = storeReviewService.updateCustomerLivings(launchView.getCustomerLivings());
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
			    rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb);                   
            }else {
                launchView.setOperType(YESNO.NO.getCode());
                workItem.setBv(launchView);
            }
			flowService.saveData(workItem);
			// 修改结果提示信息	
			if (cusFlag != 0 && livFlag != 0) {
				msg = "客户资料修改成功!";
				addMessage(redirectAttributes, msg);				
			}else{
				msg = "客户资料修改失败!";
				addMessage(redirectAttributes, msg);
			}
		} 			
		// 方法标识是apply,进行申请信息编辑 
		else if ("apply".equals(method)) {
			//主要借款用途
	    	String dictLoanUse=launchView.getLoanInfo().getDictLoanUse();
			if(!"12".equals(dictLoanUse)){ //12表示其他
				launchView.getLoanInfo().setDictLoanUseNewOther("");
			}
			//主要还款来源
			String dictLoanSource=launchView.getLoanInfo().getDictLoanSource();
			if(!"7".equals(dictLoanSource)){ //7表示其他
				launchView.getLoanInfo().setDictLoanSourceOther("");
			}
			//其他收入来源
			String dictLoanSourceElse=launchView.getLoanInfo().getDictLoanSourceElse();
			if(dictLoanSourceElse!=null){
				//其他收入来源选择其他时的备注字段
				if(dictLoanSourceElse.indexOf("5")==-1){
					launchView.getLoanInfo().setDictLoanSourceElseOther("");
				}
			}else{
				launchView.getLoanInfo().setDictLoanSourceElse("");
				launchView.getLoanInfo().setDictLoanSourceElseOther("");
			}
			//其他月收入
			if(launchView.getLoanInfo().getOtherMonthIncome()==null){
				launchView.getLoanInfo().setOtherMonthIncome(new BigDecimal(0));
			}
			//同业在还借款总笔数
			if(launchView.getLoanInfo().getOtherCompanyPaybackCount().equals("")){
				launchView.getLoanInfo().setOtherCompanyPaybackCount("0");
			}
			//月还款总额
			if(launchView.getLoanInfo().getOtherCompanyPaybackTotalmoney()==null){
				launchView.getLoanInfo().setOtherCompanyPaybackTotalmoney(new BigDecimal(0));
			}
			int applyFlag = storeReviewService.updateLoanInfo(launchView.getLoanInfo());
			if (!ObjectHelper.isEmpty(launchView.getLoanRemark())) {
				loanRemarkService.updateByIdSelective(launchView.getLoanRemark());					
			}
			if (launchView.getLoanInfo().getLoanApplyAmount() !=null && launchView.getLoanInfo().getLoanApplyAmount().compareTo(BigDecimal.ZERO)>0) {
				float loanApplyAmountf = launchView.getLoanInfo().getLoanApplyAmount().floatValue();
				launchView.getLoanInfo().setLoanApplyAmountf(loanApplyAmountf);
			}
			// 更新流程属性
			if (StringUtils.isNotEmpty(launchView.getLoanInfo().getProductType())) {
				launchView.setProductCode(launchView.getLoanInfo().getProductType());
				launchView.setProductName(areaService.getProductNameByCode(launchView.getLoanInfo().getProductType()));
			}
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
				ReconsiderBusinessView  rb =areaService.copyView(launchView);
				rb.setOperType(YESNO.NO.getCode());
				workItem.setBv(rb);                   
			}else {
				launchView.setOperType(YESNO.NO.getCode());
				workItem.setBv(launchView);
			}
			flowService.saveData(workItem);
			// 修改结果提示信息
			if (applyFlag != 0) {
				msg = "申请信息修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "申请信息修改失败!";
				addMessage(redirectAttributes, msg);
			}
		}
		// 方法标识是company,进行职业信息/公司资料编辑
		else if ("company".equals(method)) {
			launchView.getCustomerLoanCompany().setLoanCode(launchView.getLoanInfo().getLoanCode());
			launchView.getCustomerLoanCompany().setDictrCustomterType(LoanManFlag.MAIN_LOAN.getCode());
			int companyFlag = storeReviewService.updateLoanCompany(launchView.getCustomerLoanCompany(),preResponse);
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb);                   
            }else {
                launchView.setOperType(YESNO.NO.getCode());
			    workItem.setBv(launchView);
            }
			flowService.saveData(workItem);
			// 修改结果提示信息
			if (companyFlag != 0) {
				msg = "职业信息/公司资料修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "职业信息/公司资料修改失败!";
				addMessage(redirectAttributes, msg);
			}
		}
		// 方法标识是bank,进行银行卡资料编辑
		else if ("bank".equals(method)) {
			Integer bankIsRareword = launchView.getLoanBank().getBankIsRareword();
			if(ObjectHelper.isNotEmpty(bankIsRareword) && bankIsRareword.intValue()==1){
				launchView.setBankIsRareword(YESNO.YES.getCode());
				launchView.getLoanBank().setBankIsRareword(Integer.parseInt(YESNO.YES.getCode()));
			}else{
				launchView.setBankIsRareword(YESNO.NO.getCode());
				launchView.getLoanBank().setBankIsRareword(Integer.parseInt(YESNO.NO.getCode()));
			}
			int bankFlag = storeReviewService.updateLoanBank(launchView.getLoanBank());
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb);                   
            }else {
                launchView.setOperType(YESNO.NO.getCode());
                workItem.setBv(launchView);
            }
			flowService.saveData(workItem);
			// 修改结果提示信息
			if (bankFlag != 0) {
				msg = "银行卡资料修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "银行卡资料修改失败!";
				addMessage(redirectAttributes, msg);
			}
		}
		// 方法标识是credit,进行信用资料编辑
		else if ("credit".equals(method)) {
			int creditFlag = 0;
			for (int i = 0; i < launchView.getLoanCreditInfoList().size(); i++) {
				launchView.getLoanCreditInfoList().get(i).setLoanCode(launchView.getLoanInfo().getLoanCode());
				creditFlag = storeReviewService.updateLoanCreditInfo(launchView.getLoanCreditInfoList().get(i));
			}
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb); 					
			}else {
			    launchView.setOperType(YESNO.NO.getCode());
				workItem.setBv(launchView);					
			}
			flowService.saveData(workItem);
			if (creditFlag != 0) {
				msg = "信用资料修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "信用资料修改失败!";
				addMessage(redirectAttributes, msg);
			}
		}
		// 方法标识是house,进行房产资料编辑
		else if ("house".equals(method)) {
			int houseFlag = 0;
			for (int i = 0; i < launchView.getCustomerLoanHouseList().size(); i++) {
				launchView.getCustomerLoanHouseList().get(i).setLoanCode(launchView.getLoanInfo().getLoanCode());
				launchView.getCustomerLoanHouseList().get(i).setRcustomerCoborrowerId(launchView.getLoanCustomer().getId());
				launchView.getCustomerLoanHouseList().get(i).setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
				houseFlag = storeReviewService.updateLoanLoanHouse(launchView.getCustomerLoanHouseList().get(i));
			}
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb); 					
			}else {
			    launchView.setOperType(YESNO.NO.getCode());
				workItem.setBv(launchView);					
			}
			flowService.saveData(workItem);
			if (houseFlag != 0) {
				msg = "房产资料修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "房产资料修改失败!";
				addMessage(redirectAttributes, msg);
			}
		}
		// 方法标识是contract,进行联系人资料编辑
		else if ("contact".equals(method)) {
			
			LoanCustomer loanCustomer = loanCustomerService.get(launchView.getLoanCustomer().getId());
			
			boolean updateSuccess = true;
			
			// 方法标识是mate,进行配偶资料编辑 
			if (loanCustomer != null && Marriage.MARRIED.getCode().equals(loanCustomer.getDictMarry())) {				
				int mateFlag = storeReviewService.updateLoanMate(launchView,preResponse);
				// 修改结果提示信息
				if (mateFlag == 0) {
					updateSuccess = false; 
				}
			}
			
			//修改主借人
			if(updateSuccess){
				
				launchView.getLoanCustomer().setLoanCode(launchView.getLoanInfo().getLoanCode());
				int loanCustomerFlag = storeReviewService.updateLoanCustomer(launchView.getLoanCustomer());
			
				if(loanCustomerFlag == 0){
					updateSuccess = false;
				}
			}
			
			//修改联系人
			int contactFlag = 0;
			if(updateSuccess){
				for (int i = 0; i < launchView.getCustomerContactList().size(); i++) {
					launchView.getCustomerContactList().get(i).setRcustomerCoborrowerId(launchView.getLoanCustomer().getId());
					launchView.getCustomerContactList().get(i).setLoanCode(launchView.getLoanInfo().getLoanCode());
					launchView.getCustomerContactList().get(i).setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
					contactFlag = storeReviewService.updateContact(launchView.getCustomerContactList().get(i),preResponse);
				}
			}
			
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb);				
			}else {
			    launchView.setOperType(YESNO.NO.getCode());
				workItem.setBv(launchView);				
			}
			flowService.saveData(workItem);
			if (updateSuccess && contactFlag != 0) {
				msg = "联系人料修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "联系人资料修改失败!";
				addMessage(redirectAttributes, msg);
			}
		}
		// 方法标识是cobo,进行共借人资料编辑
		else if ("cobo".equals(method)) {
			int coboFlag = 0;
			coboFlag = storeReviewService.updateNaturalGuarantor(launchView,preResponse);
			this.setCoborrowers(launchView);
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb); 				
			}else {
			    launchView.setOperType(YESNO.NO.getCode());
				workItem.setBv(launchView);					
			}
			flowService.saveData(workItem);
			if (coboFlag != 0) {
				msg = "自然人保证人资料修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "自然人保证人资料修改失败!";
				addMessage(redirectAttributes, msg);
			}
		}	
		// 方法标识是coboUpdate,进行共借人修改(退回门店)
		else if ("coboUpdate".equals(method)) {
			int coboUpdateFlag = 0;
			coboUpdateFlag = storeReviewService.updateNaturalGuarantor(launchView,preResponse);
			this.setCoborrowers(launchView);
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb); 				
			}else {
			    launchView.setOperType(YESNO.NO.getCode());
				workItem.setBv(launchView);					
			}
			flowService.saveData(workItem);
			// 插入修改详细信息记录
			if (launchView.getLoanCoborrower().size() != 0) {
				modifyInfoService.addModifyInfo(launchView);					
			}
			if (coboUpdateFlag != 0) {
				msg = "自然人保证人资料修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "自然人保证人资料修改失败!";
				addMessage(redirectAttributes, msg);
			}
		}
		//保存证件信息
		else if("certificate".equals(method)){
			
			int certificateFlag = 0;
			launchView.getLoanPersonalCertificate().setLoanCode(launchView.getLoanInfo().getLoanCode());
			certificateFlag = storeReviewService.updateCertificate(launchView.getLoanPersonalCertificate());
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb); 				
			}else {
			    launchView.setOperType(YESNO.NO.getCode());
				workItem.setBv(launchView);					
			}
			flowService.saveData(workItem);
			if (certificateFlag != 0) {
				msg = "证件信息修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "证件信息修改失败!";
				addMessage(redirectAttributes, msg);
			}
			
		}
		//保存经营信息
		else if("manager".equals(method)){
			int managerFlag = 0;
			launchView.getLoanCompManage().setLoanCode(launchView.getLoanInfo().getLoanCode());
			managerFlag = storeReviewService.updateManager(launchView.getLoanCompManage(), preResponse);
			if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
			    ReconsiderBusinessView  rb =areaService.copyView(launchView);
                rb.setOperType(YESNO.NO.getCode());
                workItem.setBv(rb); 				
			}else {
			    launchView.setOperType(YESNO.NO.getCode());
				workItem.setBv(launchView);					
			}
			flowService.saveData(workItem);
			if (managerFlag != 0) {
				msg = "经营信息修改成功!";
				addMessage(redirectAttributes, msg);
			} else {
				msg = "经营信息修改失败!";
				addMessage(redirectAttributes, msg);
			}
			
		}
		return "redirect:" + adminPath + redirectUrl;
	}
	
	@ResponseBody
    @RequestMapping(value = "delCoborrower")
	public String delCoborrower(WorkItemView workItem,String coboId,LaunchView launchView){
	    String preResponse = launchView.getPreResponse();
        String loanCode = storeReviewService.delCoborrower(coboId,preResponse);
        List<LoanCoborrower> list = loanCoborrowerService.selectByLoanCode(loanCode);
        launchView.setLoanCoborrower(list);
        this.setCoborrowers(launchView);
        if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
            ReconsiderBusinessView  rb =areaService.copyView(launchView);
            rb.setOperType(YESNO.NO.getCode());
            workItem.setBv(rb);                  
        }else {
            launchView.setOperType(YESNO.NO.getCode());
            workItem.setBv(launchView);                 
        }
        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("coborrowerName", launchView.getCoborrowerNames());
        flowService.saveDataByApplyId(launchView.getLoanCustomer().getApplyId(), param);
	    return "success";
	}
	
	/**
	 * 删除自然人保证人，及其关联的联系人，工作信息，借款意愿
	 * By 任志远	2016年09月26日
	 * 
	 * @param workItem
	 * @param coboId	自然人保证人ID
	 * @param launchView
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "delNaturalGuarantor")
	public String delNaturalGuarantor(WorkItemView workItem,String coboId,LaunchView launchView){
	    String preResponse = launchView.getPreResponse();
        String loanCode = storeReviewService.delNaturalGuarantor(coboId,preResponse);
        List<LoanCoborrower> list = loanCoborrowerService.selectByLoanCode(loanCode);
        launchView.setLoanCoborrower(list);
        this.setCoborrowers(launchView);
        if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(workItem.getStepName())) {
            ReconsiderBusinessView  rb =areaService.copyView(launchView);
            rb.setOperType(YESNO.NO.getCode());
            workItem.setBv(rb);                  
        }else {
            launchView.setOperType(YESNO.NO.getCode());
            workItem.setBv(launchView);                 
        }
        HashMap<String,Object> param = new HashMap<String,Object>();
        param.put("coborrowerName", launchView.getCoborrowerNames());
        flowService.saveDataByApplyId(launchView.getLoanCustomer().getApplyId(), param);
	    return "success";
	}
	
	/**
	 * 根据id删除联系人
	 * 2016年1月28日
	 * By lirui
	 * @param contactId 联系人id
	 */
	@ResponseBody
	@RequestMapping(value = "delContact")
	public String delContact(String contactId,String preResponse) {
		storeReviewService.delContact(contactId,preResponse);
		return BooleanType.TRUE;
	} 
	
	/**
	 * 获取共借人姓名集合
	 * 2016年2月24日
	 * By lirui
	 * @param launchView
	 * return none
	 */
	private void setCoborrowers(LaunchView launchView) {
		List<LoanCoborrower> coborrowers = launchView.getLoanCoborrower();
		StringBuffer coborrowerBuffer = new StringBuffer();
		for (LoanCoborrower cur : coborrowers) {
			if (coborrowerBuffer.length() == 0) {
				coborrowerBuffer.append(cur.getCoboName());
			} else {
				coborrowerBuffer.append("," + cur.getCoboName());
			}
		}
		if (coborrowerBuffer.length() == 0) {
			coborrowerBuffer.append("");
		}
		launchView.setCoborrowerNames(coborrowerBuffer.toString());
	}
	
	/**
	 * 通过客户编号查询客户年龄
	 * 2016年3月17日
	 * By lirui
	 * @param customerCode 客户编号
	 * @return 客户年龄
	 */
	@ResponseBody
	@RequestMapping(value = "wokeAgeCheck")
	public String wokeAgeCheck(String customerCode) {
		String age = "";
		if (StringUtils.isNotEmpty(customerCode)) {	
			ApplyInfoFlagEx applyInFoFlagEx = customerBaseInfoDao.selectByPrimaryKey(customerCode);
			if (StringUtils.isNotEmpty(applyInFoFlagEx.getMateCertNum())) {
				String year = applyInFoFlagEx.getMateCertNum().substring(6,10);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				String nowYear = sdf.format(date);
				age = String.valueOf(Integer.parseInt(nowYear)-Integer.parseInt(year));				
			}			
		}
		return age;
	}
	
	@RequestMapping(value="getBackDetail")
	public String getBackDetail(Model model,String loanCode,String relationId){
	    List<ApproveDictEx> dicts = backStoreService.getBackStoreDicts();
	    LoanStatusHis status = new LoanStatusHis();
	    status.setLoanCode(loanCode);
	    status.setDictSysFlag(ModuleName.MODULE_APPROVE.value);
	    String dictLoanStatus="'"+LoanApplyStatus.RECONSIDER_BACK_STORE.getCode()+"','"+LoanApplyStatus.BACK_STORE.getCode()+"'";
	    status.setDictLoanStatus(dictLoanStatus);
	    List<LoanStatusHis> statusList = historyService.findByLoanCodeAndStatus(status);
	    if(!ObjectHelper.isEmpty(statusList)){
	        LoanStatusHis result =  statusList.get(0);
	        model.addAttribute("relationId",result.getId());
	    }
        model.addAttribute("dicts",dicts);
 	    return "borrow/storereview/backStoreView";
	}
	@ResponseBody
    @RequestMapping(value = "getById")
    public AuditBack getById(String relationId){
        AuditBack auditBack = backStoreService.getById(relationId);
        return auditBack;
	}
}

