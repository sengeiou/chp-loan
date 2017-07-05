package com.creditharmony.loan.borrow.serve.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.adapter.bean.in.zcj.ZcjSendSettleSignInfoInBean;
import com.creditharmony.adapter.bean.out.ca.Ca_UnitSignOutBean;
import com.creditharmony.adapter.bean.out.zcj.ZcjSendSettleSignInfoOutBean;
import com.creditharmony.adapter.constant.CaUnitSignType;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ListUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ContractType;
import com.creditharmony.core.loan.type.EmailTemplateType;
import com.creditharmony.core.loan.type.MailStatus;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.file.util.Zip;
import com.creditharmony.dm.filenet.CEContextHolder;
import com.creditharmony.dm.filenet.service.ce.CEHelper;
import com.creditharmony.dm.filenet.service.util.UserContextUtil;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.serve.constants.ContractHisConst;
import com.creditharmony.loan.borrow.serve.constants.MailCompany;
import com.creditharmony.loan.borrow.serve.entity.ContractFileJqSend;
import com.creditharmony.loan.borrow.serve.entity.ContractFileSend;
import com.creditharmony.loan.borrow.serve.entity.ContractFileSendEmail;
import com.creditharmony.loan.borrow.serve.service.CustomerServeService;
import com.creditharmony.loan.borrow.serve.view.ContractFileIdAndFileNameView;
import com.creditharmony.loan.borrow.serve.view.ContractFileSendEmailView;
import com.creditharmony.loan.borrow.serve.view.ContractFileSendView;
import com.creditharmony.loan.borrow.serve.excel.ExportHelper;
import com.creditharmony.loan.borrow.transate.service.LoanMinuteService;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.NumberMasterService;
import com.creditharmony.loan.common.utils.CaUtil;
import com.creditharmony.loan.common.utils.ComUtils;
import com.creditharmony.loan.common.utils.Constants;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.FilterHelper;
import com.filenet.api.core.Document;
import com.google.common.collect.Maps;

/**
 * 客户服务管理
 * 
 * @Class Name CustomerServeController
 * @author 王俊杰
 * @Create In 2016年1月29日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/borrow/serve/customerServe")
public class CustomerServeController extends BaseController {

	@Autowired
	private CustomerServeService customerServeService;
	@Autowired
	private NumberMasterService numberMasterService;
	@Autowired
	private LoanMinuteService lms;
	
	@Autowired
	HistoryService historyservice;
    //结清证明
	@RequestMapping(value = "/settleProofList")
	public String settleProofList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			ContractFileSendView contractFileSendView) {
		// 1表示结清证明
		contractFileSendView.setFileType("1");
		// 发送状态2 7 4  MailStatus.TO_SEARCH.getCode()  '7'
		contractFileSendView.setSendStatus("'"+MailStatus.TO_MAKE.getCode()+"','"+MailStatus.TO_SEND_RETURN.getCode()+"','"+MailStatus.MAKING.getCode()+"'");
		if(contractFileSendView.getMark()!=null && !contractFileSendView.getMark().equals("")){
			contractFileSendView.setMark(FilterHelper.appendIdFilter(contractFileSendView.getMark()));
		}
		Page<ContractFileSend> page = customerServeService
				.selectContractSendList(new Page<ContractFileSend>(request,
						response), contractFileSendView);
		model.addAttribute("page", page);
		List<ContractFileSend> lst = new ArrayList<ContractFileSend>();

		for (ContractFileSend cf : page.getList()) {
			ContractFileSend c = cf;
			String creditStatus = DictCache.getInstance().getDictLabel(
					"jk_loan_status", cf.getCreditStatus());
			c.setCreditStatusName(creditStatus);

			String emergentLevel = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_urgent_flag", cf.getEmergentLevel());
			c.setEmergentLevelName(emergentLevel);

			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_mail_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			
			String loanFlag = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getLoanFlag());
			c.setLoanFlag(loanFlag);
	
			String lmodel = DictCache.getInstance().getDictLabel(
					"jk_loan_model", cf.getModel());
			c.setModel(lmodel);
			
			String markName = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getMark());
			c.setMarkName(markName);
			lst.add(c);
		}
		model.addAttribute("contractFileSendViewlist", lst);
		model.addAttribute("contractFileSendView", contractFileSendView);
		return "borrow/serve/settleProofList";
	}

	//电子结清证明
		@RequestMapping(value = "/settleEmailProofList")
		public String settleEmailProofList(Model model, HttpServletRequest request,
				HttpServletResponse response,
				ContractFileSendEmailView contractFileSendEmailView) {
			// 1表示结清证明
			contractFileSendEmailView.setFileType("1");
			contractFileSendEmailView.setSendStatusLab("'"+MailStatus.TO_MAKE.getCode()+"','"+MailStatus.MAKING.getCode()+"','"+MailStatus.MAKED_FIALED.getCode()+"'");
			// 发送状态2 7 4  MailStatus.TO_SEARCH.getCode()  '7'
			contractFileSendEmailView.setSendStatus(contractFileSendEmailView.getSendStatus());
			Page<ContractFileSendEmail> page = customerServeService
					.selectContractSendEmailList(new Page<ContractFileSendEmail>(request,
							response), contractFileSendEmailView);
			model.addAttribute("page", page);
			List<ContractFileSendEmail> lst = new ArrayList<ContractFileSendEmail>();

			for (ContractFileSendEmail cf : page.getList()) {
				ContractFileSendEmail c = cf;
				String creditStatus = DictCache.getInstance().getDictLabel(
						"jk_loan_status", cf.getCreditStatus());
				c.setCreditStatusName(creditStatus);
				
				String sendStatus = DictCache.getInstance().getDictLabel(
						"jk_cm_admin_email_status", cf.getSendStatus());
				c.setSendStatusName(sendStatus);
				
				String loanFlag = DictCache.getInstance().getDictLabel(
						"jk_channel_flag", cf.getLoanFlag());
				c.setLoanFlagName(loanFlag);
				
				String lmodel = DictCache.getInstance().getDictLabel(
						"jk_loan_model", cf.getModel());
				c.setModel(lmodel);;
				lst.add(c);

			}
			model.addAttribute("contractFileSendEmailViewlist", lst);
			model.addAttribute("contractFileSendEmailView", contractFileSendEmailView);
			return "borrow/serve/settleEmailProofList";
		}
		
	/**
	 * 合同寄送列表申请列表 2016年5月17日
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param contractFileSendView
	 *            合同文件基本信息
	 * @return
	 */
	@RequestMapping(value = "/contractSendList")
	public String contractSendList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			ContractFileSendView contractFileSendView) {
		// 0表示合同
		contractFileSendView.setFileType("0");
		// 1表示待查找7
		contractFileSendView.setSendStatus("'"+MailStatus.TO_SEARCH.getCode()+"','"+MailStatus.TO_SEND_RETURN.getCode()+"'");
		Page<ContractFileSend> page = customerServeService
				.selectContractSendList(new Page<ContractFileSend>(request,
						response), contractFileSendView);
		model.addAttribute("page", page);
		List<ContractFileSend> lst = new ArrayList<ContractFileSend>();

		for (ContractFileSend cf : page.getList()) {
			ContractFileSend c = cf;
			String creditStatus = DictCache.getInstance().getDictLabel(
					"jk_loan_status", cf.getCreditStatus());
			c.setCreditStatusName(creditStatus);

			String emergentLevel = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_urgent_flag", cf.getEmergentLevel());
			c.setEmergentLevelName(emergentLevel);

			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_mail_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			
			String loanFlag = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getLoanFlag());
			c.setLoanFlag(loanFlag);
	
			String lmodel = DictCache.getInstance().getDictLabel(
					"jk_loan_model", cf.getModel());
			c.setModel(lmodel);
			
			String markName = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getMark());
			c.setMarkName(markName);
			
			lst.add(c);
		}
		model.addAttribute("contractFileSendViewlist", lst);
		model.addAttribute("contractFileSendView", contractFileSendView);
		return "borrow/serve/contractSendList";
	}

	/**
	 * 电子协议申请列表 2016年11月8日
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param contractFileSendEmailView
	 *            合同文件基本信息
	 * @return
	 */
	@RequestMapping(value = "/contractSendEmailList")
	public String contractSendEmailList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			ContractFileSendEmailView contractFileSendEmailView) {
		// 0表示合同
		contractFileSendEmailView.setFileType("0");
		contractFileSendEmailView.setSendStatusLab("'4','5'");
		Page<ContractFileSendEmail> page = customerServeService
				.selectContractSendEmailList(new Page<ContractFileSendEmail>(request,
						response), contractFileSendEmailView);
		model.addAttribute("page", page);
		List<ContractFileSendEmail> lst = new ArrayList<ContractFileSendEmail>();

		for (ContractFileSendEmail cf : page.getList()) {
			ContractFileSendEmail c = cf;
			String creditStatus = DictCache.getInstance().getDictLabel(
					"jk_loan_status", cf.getCreditStatus());
			c.setCreditStatusName(creditStatus);
			
			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_email_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			
			String loanFlag = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getLoanFlag());
			c.setLoanFlagName(loanFlag);
			
			String lmodel = DictCache.getInstance().getDictLabel(
					"jk_loan_model", cf.getModel());
			c.setModel(lmodel);
			lst.add(c);

		}
		model.addAttribute("contractFileSendEmailViewlist", lst);
		model.addAttribute("contractFileSendEmailView", contractFileSendEmailView);
		return "borrow/serve/contractSendEmailList";
	}
	
	@RequestMapping(value = "/waitSendList")
	public String waitSendList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			ContractFileSendView contractFileSendView) {
		// 2表示待邮寄 6
		contractFileSendView.setSendStatus("'"+MailStatus.TO_SEND.getCode()+"','"+MailStatus.SEND_RETURN.getCode()+"'");
		if(contractFileSendView.getMark()!=null && !contractFileSendView.getMark().equals("")){
			contractFileSendView.setMark(FilterHelper.appendIdFilter(contractFileSendView.getMark()));
		}
		Page<ContractFileSend> page = customerServeService
				.selectContractSendList(new Page<ContractFileSend>(request,
						response), contractFileSendView);
		model.addAttribute("page", page);
		List<ContractFileSend> lst = new ArrayList<ContractFileSend>();

		for (ContractFileSend cf : page.getList()) {
			ContractFileSend c = cf;
			String fileType = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_file_type", cf.getFileType());
			c.setFileTypeName(fileType);

			String emergentLevel = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_urgent_flag", cf.getEmergentLevel());
			c.setEmergentLevelName(emergentLevel);

			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_mail_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			
			String loanFlag = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getLoanFlag());
			c.setLoanFlag(loanFlag);
	
			String lmodel = DictCache.getInstance().getDictLabel(
					"jk_loan_model", cf.getModel());
			c.setModel(lmodel);
			
			String markName = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getMark());
			c.setMarkName(markName);
			lst.add(c);
		}
		model.addAttribute("contractFileSendViewlist", lst);
		model.addAttribute("contractFileSendView", contractFileSendView);
		if("0".equals(contractFileSendView.getFileType())){//合同
			return "borrow/serve/contractWaitSendList";
		}
		return "borrow/serve/waitSendList";
	}

	@RequestMapping(value = "/waitSendEmailList")
	public String waitSendEmailList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			ContractFileSendEmailView contractFileSendEmailView) {
		// 2表示待邮寄 6
		contractFileSendEmailView.setFileType("1");
		contractFileSendEmailView.setSendStatusLab("'"+MailStatus.TO_SEND.getCode()+"','9'");
		Page<ContractFileSendEmail> page = customerServeService
				.selectContractSendEmailList(new Page<ContractFileSendEmail>(request,
						response), contractFileSendEmailView);
		model.addAttribute("page", page);
		List<ContractFileSendEmail> lst = new ArrayList<ContractFileSendEmail>();

		for (ContractFileSendEmail cf : page.getList()) {
			ContractFileSendEmail c = cf;
			String fileType = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_file_type", cf.getFileType());
			c.setFileTypeName(fileType);

			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_email_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			String loanFlag = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getLoanFlag());
			c.setLoanFlag(loanFlag);
			
			String lmodel = DictCache.getInstance().getDictLabel(
					"jk_loan_model", cf.getModel());
			c.setModel(lmodel);
			
			String loanStatus = DictCache.getInstance().getDictLabel(
					"jk_loan_apply_status", cf.getCreditStatus());
			c.setCreditStatusName(loanStatus);
			
			lst.add(c);
		}
		model.addAttribute("contractFileSendEmailViewlist", lst);
		model.addAttribute("contractFileSendEmailView", contractFileSendEmailView);
		return "borrow/serve/waitSendEmailList";
	}
	
	@RequestMapping(value = "/alreadyHandleList")
	public String alreadyHandleList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			ContractFileSendView contractFileSendView) {
		// 3表示已邮寄
		contractFileSendView.setSendStatus("'"+MailStatus.SENDED.getCode()+"'");
		if(contractFileSendView.getMark()!=null && !contractFileSendView.getMark().equals("")){
			contractFileSendView.setMark(FilterHelper.appendIdFilter(contractFileSendView.getMark()));
		}
		Page<ContractFileSend> page = customerServeService
				.selectContractSendList(new Page<ContractFileSend>(request,
						response), contractFileSendView);
		model.addAttribute("page", page);

		List<ContractFileSend> lst = new ArrayList<ContractFileSend>();

		for (ContractFileSend cf : page.getList()) {
			ContractFileSend c = cf;
			String fileType = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_file_type", cf.getFileType());
			c.setFileTypeName(fileType);
			
			String sendCompanyLoabe=DictCache.getInstance().getDictLabel("jk_cm_admin_deliver",cf.getSendCompany());
			c.setSendCompanyLoabe(sendCompanyLoabe);

			String emergentLevel = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_urgent_flag", cf.getEmergentLevel());
			c.setEmergentLevelName(emergentLevel);

			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_mail_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			
			String loanFlag = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getLoanFlag());
			c.setLoanFlag(loanFlag);
	
			String lmodel = DictCache.getInstance().getDictLabel(
					"jk_loan_model", cf.getModel());
			c.setModel(lmodel);
			
			String markName = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getMark());
			c.setMarkName(markName);
			lst.add(c);
		}
		model.addAttribute("contractFileSendViewlist", lst);
		model.addAttribute("contractFileSendView", contractFileSendView);
		if("0".equals(contractFileSendView.getFileType())){//合同
			return "borrow/serve/contractAlreadyHandleList";
		}
		return "borrow/serve/alreadyHandleList";
	}

	/*
	 * 
	 * 电子协议发送已办列表
	 */
	@RequestMapping(value = "/contractEmailList")
	public String contractEmailList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			ContractFileSendEmailView contractFileSendEmailView) {
		// 3表示已发送
		contractFileSendEmailView.setFileType("0");
		contractFileSendEmailView.setSendStatusLab("'6'");
		Page<ContractFileSendEmail> page = customerServeService
				.selectContractSendEmailList(new Page<ContractFileSendEmail>(request,
						response), contractFileSendEmailView);
		model.addAttribute("page", page);

		List<ContractFileSendEmail> lst = new ArrayList<ContractFileSendEmail>();

		for (ContractFileSendEmail cf : page.getList()) {
			ContractFileSendEmail c = cf;
			String fileType = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_file_type", cf.getFileType());
			c.setFileTypeName(fileType);

			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_email_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			
			String loanFlag = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getLoanFlag());
			c.setLoanFlag(loanFlag);
			
			String lmodel = DictCache.getInstance().getDictLabel(
					"jk_loan_model", cf.getModel());
			c.setModel(lmodel);

			String creditStatusName = DictCache.getInstance().getDictLabel(
					"jk_loan_apply_status", cf.getCreditStatus());
			c.setCreditStatusName(creditStatusName);
			lst.add(c);
			lst.add(c);
		}
		model.addAttribute("contractFileSendEmailViewlist", lst);
		model.addAttribute("contractFileSendEmailView", contractFileSendEmailView);
		return "borrow/serve/contractEmailList";
	}
	
	/*
	 * 
	 * 结清电子协议发送已办列表
	 */
	@RequestMapping(value = "/alreadyHandleEmailList")
	public String alreadyHandleEmailList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			ContractFileSendEmailView contractFileSendEmailView) {
		// 3表示已发送
		contractFileSendEmailView.setFileType("1");
		contractFileSendEmailView.setSendStatusLab("'"+MailStatus.SENDED.getCode()+"','"+MailStatus.SEND_RETURN.getCode()+"'");
		Page<ContractFileSendEmail> page = customerServeService
				.selectContractSendEmailList(new Page<ContractFileSendEmail>(request,
						response), contractFileSendEmailView);
		model.addAttribute("page", page);

		List<ContractFileSendEmail> lst = new ArrayList<ContractFileSendEmail>();

		for (ContractFileSendEmail cf : page.getList()) {
			ContractFileSendEmail c = cf;
			String fileType = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_file_type", cf.getFileType());
			c.setFileTypeName(fileType);

			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_email_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			
			String loanFlag = DictCache.getInstance().getDictLabel(
					"jk_channel_flag", cf.getLoanFlag());
			c.setLoanFlag(loanFlag);
			
			String lmodel = DictCache.getInstance().getDictLabel(
					"jk_loan_model", cf.getModel());
			c.setModel(lmodel);
			
			String loanStatus = DictCache.getInstance().getDictLabel(
					"jk_loan_apply_status", cf.getCreditStatus());
			c.setCreditStatusName(loanStatus);
			
			lst.add(c);
		}
		model.addAttribute("contractFileSendEmailViewlist", lst);
		model.addAttribute("contractFileSendEmailView", contractFileSendEmailView);
		return "borrow/serve/alreadyHandleEmailList";
	}
	
	@RequestMapping(value = "/alreadyDeleteList")
	public String alreadyDeleteList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			ContractFileSendView contractFileSendView) {
		Page<ContractFileSend> page = customerServeService.alreadyDeleteList(
				new Page<ContractFileSend>(request, response),
				contractFileSendView);
		model.addAttribute("page", page);

		List<ContractFileSend> lst = new ArrayList<ContractFileSend>();

		for (ContractFileSend cf : page.getList()) {
			ContractFileSend c = cf;
			String fileType = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_file_type", cf.getFileType());
			c.setFileTypeName(fileType);

			String emergentLevel = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_urgent_flag", cf.getEmergentLevel());
			c.setEmergentLevelName(emergentLevel);

			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_mail_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			lst.add(c);
		}
		model.addAttribute("contractFileSendViewlist", lst);

		model.addAttribute("contractFileSendView", contractFileSendView);
		return "borrow/serve/alreadyDeleteList";
	}

	@RequestMapping(value = "makeSettleProve")
	@ResponseBody
	public String makeSettleProve(ContractFileSendView contractFileSendView,String type) {
		boolean t = false;
		if("email".equals(type)){
			customerServeService.updateMailLoan(contractFileSendView);
			//添加制作协议
			DmService dmService = DmService.getInstance();
			String ceUser = ComUtils.getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.CE_HJ_BATCH_NAME);
			String cePass = ComUtils.getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, Constants.CE_HJ_BATCH_PASSWORD);
			dmService.auth4nonWeb(ceUser,cePass);
			FileInputStream pdf = null;
			String loanFile = null;
//			InputStream word = null;
			//String date = customerServeService.getSettledDate(contractFileSendView.getLoanCode());//"201612";//
			ContractFileSendEmailView contractFileSendEmailView = new ContractFileSendEmailView();
			try {
				
				//date换成t_jk_loan_info.settled_date
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				String date = sdf.format(new Date());
				
				if(date!=null){
					String code = "";
					if(ChannelFlag.ZCJ.getCode().equals(contractFileSendView.getLoanFlag())){
						loanFile = Constants.Loan_FILE02;
						//如果是资产家生成资产家的结清证明电子号
						code = date + ChannelFlag.ZCJ.name() + "JQZMDZ001";
						List<Map<String,Integer>> list = customerServeService.getMaxNumber(16, date + ChannelFlag.ZCJ.getName() + "JQZMDZ");
						if(!list.isEmpty()&&list.get(0)!=null){
							int num = list.get(0).get("num") + 1;
							if(num>9){
								if(num>99){
									code = date + ChannelFlag.ZCJ.getName() + "JQZMDZ" + num;
								}else{
									code = date + ChannelFlag.ZCJ.getName() + "JQZMDZ0" + num;
								}
							}else{
								code = date + ChannelFlag.ZCJ.getName() + "JQZMDZ00" + num;
							}
						}
						contractFileSendEmailView.setEmailNumber(code);
					}else{
						loanFile = Constants.Loan_FILE01;
						//如果不是资产家生成非资产家的结清证明电子号
						code = date + "JQZMDZ001";
						List<Map<String,Integer>> list = customerServeService.getMaxNumber(13, date + "JQZMDZ");
						if(!list.isEmpty()&&list.get(0)!=null){
							int num = list.get(0).get("num") + 1;
							if(num>9){
								if(num>99){
									code = date + "JQZMDZ" + num;
								}else{
									code = date + "JQZMDZ0" + num;
								}
							}else{
								code = date + "JQZMDZ00" + num;
							}
						}
						contractFileSendEmailView.setEmailNumber(code);
					}
					String pdfUrl = ComUtils.getCommonLoanPdfURL(Constants.LOAN_PDF_URLID,loanFile,"op=view","loan_code", contractFileSendView.getLoanCode(),"xy_code=" + code);
					//String pdfUrl = "http://10.167.210.185:9080/WebReport/ReportServer?reportlet=DOC%2F01HTL%2FJKHT%2FJKXYZZTZS_DZ.cpt&op=view&loan_code="+contractFileSendView.getLoanCode()+"&xy_code=ZCJ001&format=pdf ";
					// 拼接URL
					URL pUrl = new URL(pdfUrl);
					URLConnection pdfUrlcon = pUrl.openConnection();
					// 获取连接
					pdfUrlcon.connect();
					// 获取流
					HashMap<String, Object> tmpResult =this.inputStreamToFileInputStream(pdfUrlcon.getInputStream());
					pdf =(FileInputStream) tmpResult.get("fileInputStream");
					String fileName = Constants.PDF + ComUtils.dateToString(
							new Date(), 
							"yyyyMMddhhmmss");
					if(!ChannelFlag.ZCJ.getCode().equals(contractFileSendView.getLoanFlag())){
						fileName = "借款协议终止通知书_" + ComUtils.dateToString(
								new Date(), 
								"yyyyMMddhhmmss");
					}
					DocumentBean doc = dmService.createDocument(
							 fileName + ".pdf", 
									pdf, 
									DmService.BUSI_TYPE_LOAN, 
									CeFolderType.CONTRACT_MAKE.getName(), 
									contractFileSendView.getLoanCode(), 
									Constants.BATCH_NAME
							);
					File tmpfile = (File)tmpResult.get("tmpfile");
					tmpfile.delete();
					Ca_UnitSignOutBean caOutUnit = new Ca_UnitSignOutBean();
					if(ChannelFlag.ZCJ.getCode().equals(contractFileSendView.getLoanFlag())){
						/** 将doc.getDocId()传给大金融接口盖章 */
						ZcjSendSettleSignInfoInBean bean = new ZcjSendSettleSignInfoInBean();
						bean.setContractCode(contractFileSendView.getContractCode());
						bean.setDocId(doc.getDocId());
						bean.setType("zcjJQZM");
						
						// 客户端服务代理申明	
						ClientPoxy service = new ClientPoxy(ServiceType.Type.ZCJ_SENDSETTLESIGNINFO);
						// 请求发送	
						ZcjSendSettleSignInfoOutBean  retInfo =  (ZcjSendSettleSignInfoOutBean) service.callService(bean);
						if(doc.getDocId()!=null&&ReturnConstant.SUCCESS.equals(retInfo.getRetCode())){
							contractFileSendEmailView.setContractCode(contractFileSendView.getContractCode());
							contractFileSendEmailView.setDelFlag(contractFileSendView.getDelFlag());
							contractFileSendEmailView.setId(contractFileSendView.getId());
							contractFileSendEmailView.setIsDelete(contractFileSendView.getIsDelete());
							contractFileSendEmailView.setLoanCode(contractFileSendView.getLoanCode());
							contractFileSendEmailView.setFileType(contractFileSendView.getFileType());
							contractFileSendEmailView.setDocId(doc.getDocId());
							contractFileSendEmailView.setSendStatus("4");
							contractFileSendEmailView.setPdfId("");
							contractFileSendEmailView.setOperateStep("结清证明列表-制作结清证明");
							customerServeService.updateEmailStatus(contractFileSendEmailView,true);
							t = true;
						}
					}else{
						caOutUnit = CaUtil.signEndCompany(doc.getDocId(), 
						CaUnitSignType.HM, contractFileSendView.getContractCode(), 
						"信和惠民投资管理（北京）有限公司"); 
						//4
						if(caOutUnit.getDocId()!=null){
							contractFileSendEmailView.setContractCode(contractFileSendView.getContractCode());
							contractFileSendEmailView.setDelFlag(contractFileSendView.getDelFlag());
							contractFileSendEmailView.setId(contractFileSendView.getId());
							contractFileSendEmailView.setIsDelete(contractFileSendView.getIsDelete());
							contractFileSendEmailView.setLoanCode(contractFileSendView.getLoanCode());
							contractFileSendEmailView.setFileType(contractFileSendView.getFileType());
							contractFileSendEmailView.setDocId(doc.getDocId());
							contractFileSendEmailView.setSendStatus("2");
							contractFileSendEmailView.setPdfId(caOutUnit.getDocId());
							contractFileSendEmailView.setOperateStep("结清证明列表-制作结清证明");
							customerServeService.updateEmailStatus(contractFileSendEmailView,true);
							t = true;
						}
					}
				}
			}catch(Exception e){
				contractFileSendEmailView.setContractCode(contractFileSendView.getContractCode());
				contractFileSendEmailView.setDelFlag(contractFileSendView.getDelFlag());
				contractFileSendEmailView.setId(contractFileSendView.getId());
				contractFileSendEmailView.setIsDelete(contractFileSendView.getIsDelete());
				contractFileSendEmailView.setLoanCode(contractFileSendView.getLoanCode());
				contractFileSendEmailView.setFileType(contractFileSendView.getFileType());
				contractFileSendEmailView.setSendStatus("5");
				contractFileSendEmailView.setOperateStep("结清证明列表-制作结清证明");
				customerServeService.updateEmailStatus(contractFileSendEmailView,false);
				e.printStackTrace();
			}
			
		}else{
			//4
			contractFileSendView.setSendStatus(MailStatus.MAKING.getCode());
			contractFileSendView.preUpdate();
			contractFileSendView.setOperateStep("结清证明列表-制作结清证明");
			customerServeService.updateMailStatus(contractFileSendView);
			String loanCode=contractFileSendView.getLoanCode();
			String channelFlag = customerServeService.getChannelFlag(loanCode);
			if(ChannelFlag.ZCJ.getCode().equals(channelFlag)){
				//协议编号
				contractFileSendView.setProtocolId(numberMasterService.getZCJJQZMNumber());
			}else{
				//协议编号
				contractFileSendView.setProtocolId(numberMasterService.getJQZMNumber());
				
			}
			// 插入更新合同结清原因数据
			customerServeService.updateMailLoan(contractFileSendView);
			t = true;
		}

		return String.valueOf(t);
	}

	/**
	 * 输入流转换文件输出流
	 * @throws IOException 
	 * @throws Exception 
	 * 
	 */
	public  HashMap<String, Object>  inputStreamToFileInputStream(InputStream is) throws IOException{
		File tmpfile=new File(UUID.randomUUID()+"tmp.pdf");
		OutputStream os =null;
		try {
			os = new FileOutputStream(tmpfile);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			os.close();
			is.close();
		}
		FileInputStream fileInputStream = new FileInputStream(tmpfile);
		HashMap<String, Object> result  = new HashMap<String, Object>();
		result.put("fileInputStream", fileInputStream);
		result.put("tmpfile",tmpfile);
		return result;
	}
	
	/**
	 * 查询合同申请历史记录 2016年5月17日 By 王彬彬
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/historyList")
	public String historyList(Model model, HttpServletRequest request,
			HttpServletResponse response,String fileType) {
		LoanStatusHis loanStatusHis = new LoanStatusHis();
		// loanStatusHis.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		String loanCode = request.getParameter("loanCode");
		loanStatusHis.setLoanCode(loanCode);
		if(ContractHisConst.contractType.equals(fileType))
		{
			loanStatusHis.setDictSysFlag(ContractHisConst.contractHis);
		}
		else
		{
			loanStatusHis.setDictSysFlag(ContractHisConst.contractSettleHis);
		}
		
		Page<LoanStatusHis> page = historyservice
				.findContractHisPageByLoanCode(new Page<LoanStatusHis>(request,
						response), loanStatusHis);
		model.addAttribute("page", page);
		model.addAttribute("loanCode", loanCode);
		return "borrow/serve/contractSendHistory";
	}

	/**
	 * 查询电子协议申请历史记录 2016年11月10日 By 方强
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/historyEmailList")
	public String historyEmailList(Model model, HttpServletRequest request,
			HttpServletResponse response,String fileType) {
		LoanStatusHis loanStatusHis = new LoanStatusHis();
		// loanStatusHis.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		String loanCode = request.getParameter("loanCode");
		loanStatusHis.setLoanCode(loanCode);
		if(ContractHisConst.contractType.equals(fileType))
		{
			loanStatusHis.setDictSysFlag(ContractHisConst.contractHis);
		}
		else
		{
			loanStatusHis.setDictSysFlag(ContractHisConst.contractSettleHis);
		}
		
		Page<LoanStatusHis> page = historyservice
				.findContractHisPageByLoanCode(new Page<LoanStatusHis>(request,
						response), loanStatusHis);
		model.addAttribute("page", page);
		model.addAttribute("loanCode", loanCode);
		return "borrow/serve/contractSendHistory";
	}
	
	/**
	 * 删除和信息
	 * 2016年5月25日
	 * By 王彬彬
	 * @param contractFileSendView
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteItem")
	public String deleteItem(ContractFileSendView contractFileSendView) {
		contractFileSendView.setIsDelete("1");
		contractFileSendView.preUpdate();
		int result = customerServeService
				.updateDeleteStatus(contractFileSendView);
		return result + "";
	}

	@ResponseBody
	@RequestMapping(value = "/receiveContract")
	public String receiveContract(ContractFileSendView contractFileSendView) {
		String[] ids = contractFileSendView.getIds().split(",");
		for (String id : ids) {
			ContractFileSend cotraFileSend = customerServeService
					.getApplyIdAndLoanCode(id);
			if (cotraFileSend != null) {
				ContractFileSendView contract = new ContractFileSendView();
				contract.setFileType(contractFileSendView.getFileType());
				// 状态修改为待邮寄2
				contract.setSendStatus(MailStatus.TO_SEND.getCode());
				contract.preUpdate();
				contract.setId(id);
				contract.setOperateStep("合同寄送列表-接收");
				contract.setApplyId(cotraFileSend.getApplyId());
				contract.setLoanCode(cotraFileSend.getLoanCode());
				customerServeService.updateMailStatus(contract);
			}
		}
		return "";
	}
	@ResponseBody
	@RequestMapping(value = "/receiveAllContract")
	public String receiveAllContract(ContractFileSendView contractFileSendView) {
		
		customerServeService.updateMailStatusAll(contractFileSendView);
		
		
		/*String[] ids = contractFileSendView.getIds().split(",");
		for (String id : ids) {
			ContractFileSend cotraFileSend = customerServeService
					.getApplyIdAndLoanCode(id);
			if (cotraFileSend != null) {
				ContractFileSendView contract = new ContractFileSendView();
				contract.setFileType(contractFileSendView.getFileType());
				// 状态修改为待邮寄2
				contract.setSendStatus(MailStatus.TO_SEND.getCode());
				contract.preUpdate();
				contract.setId(id);
				contract.setOperateStep("合同寄送列表-接收");
				contract.setApplyId(cotraFileSend.getApplyId());
				contract.setLoanCode(cotraFileSend.getLoanCode());
				customerServeService.updateMailStatus(contract);
			}
		}*/
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(HttpServletResponse response,
			ContractFileSendView contractFileSendView) {
		ExcelUtils excelUtils = new ExcelUtils();
		List<ContractFileSend> list = null;
		//结清通知书
		if("1".equals(contractFileSendView.getFileType())){
			// 0表示结清通知书
			contractFileSendView.setFileType("1");
			//  2 6
			contractFileSendView.setSendStatus("'"+MailStatus.SEND_RETURN.getCode()+"','"+MailStatus.TO_SEND.getCode()+"'");
			list = customerServeService.getExcelDataList(contractFileSendView);
			excelUtils.exportExcel(list, "结清通知书信息表", ContractFileJqSend.class,
					".xlsx", 1, response, 2);
		}else if("0".equals(contractFileSendView.getFileType())){
			if ("1".equals(contractFileSendView.getExcelFlag())) {
				// 0表示合同
				contractFileSendView.setFileType("0");
				// 1表示待查找 7
				contractFileSendView.setSendStatus("'"+MailStatus.TO_SEARCH.getCode()+"','"+MailStatus.TO_SEND_RETURN.getCode()+"'");
				list = customerServeService.getExcelDataList(contractFileSendView);
				excelUtils.exportExcel(list, "合同寄送申请信息表", ContractFileSend.class,
						".xlsx", 1, response, 1);
			} else if ("2".equals(contractFileSendView.getExcelFlag())) {
				// 2表示待邮寄 6
				contractFileSendView.setSendStatus("'"+MailStatus.TO_SEND.getCode()+"','"+MailStatus.SEND_RETURN.getCode()+"'");
				list = customerServeService.getExcelDataList(contractFileSendView);
				excelUtils.exportExcel(list, "客户邮寄信息表", ContractFileSend.class,
						".xlsx", 1, response, 2);
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/restoreItem")
	public String restoreItem(ContractFileSendView contractFileSendView) {
		contractFileSendView.setIsDelete("0");
		contractFileSendView.setOperateStep("已删除合同列表-还原");
		contractFileSendView.preUpdate();
		int result = customerServeService
				.updateDeleteStatus(contractFileSendView);
		return result + "";
	}

	@ResponseBody
	@RequestMapping(value = "/permanentDelete")
	public String permanentDelete(ContractFileSendView contractFileSendView) {
		contractFileSendView.setOperateStep("已删除合同列表-删除");
		contractFileSendView.preUpdate();
		int result = customerServeService.deleteData(contractFileSendView);
		return result + "";
	}

	@ResponseBody
	@RequestMapping(value = "/inputExpressNumber")
	public String inputExpressNumber(ContractFileSendView contractFileSendView) {
		contractFileSendView.setOperateStep("待办邮寄列表-录入快递编号");
		contractFileSendView.setSendStatus("'" +contractFileSendView.getSendStatus() + "'");
		contractFileSendView.preUpdate();
		int result = customerServeService
				.inputExpressNumber(contractFileSendView);
		return result + "";
	}

	/**
	 * 导入合同等邮寄基本信息 2016年5月17日 By 王彬彬
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/importExcel")
	public String importExcel(@RequestParam(value = "file", required = true) MultipartFile file,String fileType,
			RedirectAttributes redirectAttributes) {
		try {
			ExcelUtils excelUtils = new ExcelUtils();
			List<ContractFileSend> list = (List<ContractFileSend>) excelUtils.importExcel(file, 0, 0, ContractFileSend.class, 3);
			int successSum = 0; // 成功条数
			int failureSum = 0; // 失败条数
			for (ContractFileSend contractFileSend : list) {
				if(contractFileSend.getContractCode()==null || "".equals(contractFileSend.getContractCode())){
					continue;
				}
				if (StringUtils.isNotEmpty(contractFileSend.getContractCode())) {
					contractFileSend.setFileType(fileType);
					ContractFileSendView contract = customerServeService.getContractFile(contractFileSend);
					if (!ObjectHelper.isEmpty(contract) && contract.getFileType()!=null && contract.getFileType().equals(fileType)) {
						if (ObjectHelper.isEmpty(MailCompany.parseByName(contractFileSend.getSendCompany()))) {
							addMessage(redirectAttributes, "导入文件中有不支持的快递公司，请核对");
							failureSum++;
							continue;
						} else {
							ContractFileSendView contractFileSendView = new ContractFileSendView();
							contractFileSendView.setSendCompany(MailCompany.getCodeByname(contractFileSend.getSendCompany()));
							contractFileSendView.setApplyId(contract.getApplyId());
							contractFileSendView.setLoanCode(contract.getLoanCode());
							contractFileSendView.setFileType(contract.getFileType());
							contractFileSendView.setContractCode(contractFileSend.getContractCode());
							contractFileSendView.setExpressNumber(contractFileSend.getExpressNumber());
							contractFileSendView.setOperateStep("待办邮寄列表-导入excel");
							contractFileSendView.preUpdate();
							contractFileSendView.setSendStatus("'"+MailStatus.TO_SEND.getCode()+"','"+MailStatus.SEND_RETURN.getCode()+"'");
							if (customerServeService.inputExpressNumber(contractFileSendView) > 0) {
								successSum++;
							}else{
								failureSum++;
							}
						}
					}else{
						failureSum++;
					}
				}else{
					failureSum++;
				}
			}
			addMessage(redirectAttributes, "导入成功，成功条数：" + successSum + "。导入失败，失败条数：" + failureSum);
		} catch (Exception e) {
			logger.error(e.getMessage());
			addMessage(redirectAttributes, "导入文件有误，请检查");
		}
		return "redirect:" + adminPath + "/borrow/serve/customerServe/waitSendList?fileType=" + fileType;
	}

	/**
	 * 添加历史备注
	 * 2016年5月17日
	 * By 王彬彬
	 * @param contractFileSendView
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/remarksItem")
	public String remarksItem(ContractFileSendView contractFileSendView) {
		String his = ContractHisConst.contractHis;
		if(ContractHisConst.settledType.equals(contractFileSendView.getFileType()))
		{
			his = ContractHisConst.contractSettleHis;
		}
		int result = historyservice.saveContractStatusHis(
				contractFileSendView.getApplyId(),
				contractFileSendView.getLoanCode(), "已办邮寄列表-备注", "操作成功",
				contractFileSendView.getRemarks(),his);
		return result + "";
	}

	@RequestMapping(value = "/downloadProof")
	public void downloadProof(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("pdfId");
		if (StringUtils.isNotEmpty(id)) {
			String name = request.getParameter("name");
			String protocolId = request.getParameter("protocolId");
			try {
				name = new String((name + ".pdf").getBytes(), "iso8859-1");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			}
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("iso8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ protocolId+name);
			DmService dmService = DmService.getInstance();
			try {
				dmService.download(response.getOutputStream(), id);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
	/**
	 *结清证明批量下载
	 *@author 申阿伟
	 *@create In 2017年4月11日
	 *@param request
	 *@param response
	 *@param loanCode
	 *@return none 
	 */
	@RequestMapping(value="downLoadWaitSend")
    public void downLoadWaitSend(HttpServletRequest request, HttpServletResponse response,String ids){
	    try {
	    	SimpleDateFormat f=new SimpleDateFormat("yyyyMMddHHmmss");
	            String[] Id = null;
	            List<ContractFileSendEmailView> cse=new ArrayList<ContractFileSendEmailView>();
	            if(!StringUtils.isEmpty(ids)){
	                if(ids.indexOf(",")!=-1){
	                	Id = ids.split(",");
	                } else{
	                	Id = new String[1];
	                	Id[0] = ids;
	                }
	                response.setContentType("application/zip");
	                response.addHeader("Content-disposition", "attachment;filename=" + new String(("结清证明"+f.format(new Date())+".zip").getBytes("gbk"), "ISO-8859-1"));
	                if(Id != null && Id.length >= 1){
	                	for (int i = 0; i < Id.length; i++) {
	                		ContractFileSendEmailView c =customerServeService.selectCustomerServeBydownLoad(Id[i]);
	                		cse.add(c);
						}
	                    Map<String, InputStream> map = this.downloadDocuments(cse);
	                    Zip.zipFiles(response.getOutputStream(), map);
	                }
	              }
	           
	        } catch (UnsupportedEncodingException e1) {	            
	            e1.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }
	/**
	 * 结清证明下载
	 * @author 申阿伟
	 * @create In 2017年4月11日
	 * @param cse
	 * @return
	 */
	public Map<String, InputStream> downloadDocuments(List<ContractFileSendEmailView> cse){
		try {
			UserContextUtil.pushSubject(CEContextHolder.getContext()
					.getSubject());
			Map<String, InputStream> map = new HashMap<String, InputStream>();
			if(cse != null && cse.size() > 0){
				for (ContractFileSendEmailView c:cse) {
					Document document = CEHelper.getInstance().getDocument(CEContextHolder.getContext().getOs(),c.getDocId());
					if(!document.get_ContentElements().isEmpty()){
						String fileName =c.getProtocolId()+c.getCustomerName()+".pdf";
						InputStream is = document.accessContentStream(0);
						map.put(fileName, is);
					}
				}
			}
			return map;
		}catch (Exception e) {
	    	logger.error("downloadDocuments exception：", e);
	    	throw new RuntimeException(e);
		} finally {
			UserContextUtil.popSubject();
		}
	}

	@RequestMapping(value = "/settleProveLook")
	public String settleProveLook(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		if (StringUtils.isNotEmpty(id)) {
			String path = request.getSession().getServletContext()
					.getRealPath("settleProofs");
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path = path + "/" + id + ".pdf";
			file = new File(path);
			if (!file.exists()) {
				try {
					OutputStream os = new FileOutputStream(file);
					DmService dmService = DmService.getInstance();
					dmService.download(os, id);
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage());
				}
			}
		}
		model.addAttribute("id", id + ".pdf");
		return "borrow/serve/settleProveLook";
	}

	/**
	 * 邮寄信息初始化 2016年2月23日 By 周怀富
	 * 
	 * @param contractFileSendView
	 * @return
	 */
	@RequestMapping(value = "/openContractSendInfo")
	public String openContractSendInfo(
			ContractFileSendView contractFileSendView, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String flag = request.getParameter("flag") == null ? "" : request
				.getParameter("flag");
		String contractcode = request.getParameter("contractcode") == null ? ""
				: request.getParameter("contractcode");
		String loanCode = request.getParameter("loanCode") == null ? ""
				: request.getParameter("loanCode");
		String fileType = request.getParameter("fileType") == null ? ""
				: request.getParameter("fileType");
		contractFileSendView.setFileType(fileType);
		contractFileSendView.setFlag(flag);
		contractFileSendView.setContractCode(contractcode);
		contractFileSendView.setLoanCode(loanCode);
		model.addAttribute("contractFileSendView", contractFileSendView);
		return "borrow/serve/contractSendForm";
	}

	/**
	 * 电子协议信息初始化 2016年11月07日 By 方强
	 * 
	 * @param contractFileSendView
	 * @return
	 */
	@RequestMapping(value = "/openContractSendEmailInfo")
	public String openContractSendEMailInfo(
			ContractFileSendEmailView contractFileSendEmailView, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		/*String flag = request.getParameter("flag") == null ? "" : request
				.getParameter("flag");
		String contractcode = request.getParameter("contractcode") == null ? ""
				: request.getParameter("contractcode");
		String loanCode = request.getParameter("loanCode") == null ? ""
				: request.getParameter("loanCode");
		String fileType = request.getParameter("fileType") == null ? ""
				: request.getParameter("fileType");
		String receiverName = request.getParameter("receiverName") == null ? ""
				: request.getParameter("receiverName");
		String receiverSex = request.getParameter("receiverSex") == null ? ""
				: request.getParameter("receiverSex");
		String coboCertNum = request.getParameter("coboCertNum") == null ? ""
				: request.getParameter("coboCertNum");
		String receiverEmail = request.getParameter("loanCode") == null ? ""
				: request.getParameter("receiverEmail");
		contractFileSendEmailView.setFileType(fileType);
		contractFileSendEmailView.setFlag(flag);
		contractFileSendEmailView.setContractCode(contractcode);
		contractFileSendEmailView.setLoanCode(loanCode);
		contractFileSendEmailView.setReceiverName(receiverName);
		contractFileSendEmailView.setReceiverSex(receiverSex);
		contractFileSendEmailView.setCoboCertNum(coboCertNum);
		contractFileSendEmailView.setReceiverEmail(receiverEmail);
		model.addAttribute("contractFileSendEmailView", contractFileSendEmailView);*/
		LoanCustomer loanCustomer = customerServeService.selectByLoanCode(contractFileSendEmailView.getLoanCode());
		loanCustomer.setCustomerSexLabel(DictCache.getInstance().getDictLabel("jk_sex", loanCustomer.getCustomerSex()));
		model.addAttribute("loanMinute", loanCustomer);
		model.addAttribute("loanFlag", contractFileSendEmailView.getLoanFlag());
		model.addAttribute("contractCode", contractFileSendEmailView.getContractcode());
		model.addAttribute("contractFileSendEmailView", contractFileSendEmailView);
		return "borrow/serve/contractSendEmailForm";
	}
	
	/**
	 * 保存邮寄信息 2016年2月23日 By 周怀富
	 * 
	 * @param contractFileSendView
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveContractSendInfo")
	public String saveContractSendInfo(
			ContractFileSendView contractFileSendView, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		// 先查询该合同 寄送状态为 8 的是否是 已经寄送退回的
		List<ContractFileSendView> paybackList = new ArrayList<ContractFileSendView>();
		
		contractFileSendView.setSendStatusLab("8");
		paybackList = customerServeService
				.findApplyByDealt(contractFileSendView);
		if(paybackList.size()>0){
			return "该数据已经提交过寄送申请，不能再次提交!";
		}
		contractFileSendView.setSendStatusLab(null);
		// 查询该合同编号是否已经提交过寄送申请 8
		contractFileSendView.setSendStatus(MailStatus.TO_STORE.getCode());
		paybackList = customerServeService
				.findApplyByDealt(contractFileSendView);
		if (paybackList != null && !paybackList.isEmpty()) {
			// 如果有 就修改
			String id = paybackList.get(0).getId();
			contractFileSendView.setId(id);
			contractFileSendView.setSendStatus("1");
			contractFileSendView.setFileType("0");
			contractFileSendView.setOperateStep("重新申请合同寄送");
			int result = customerServeService
					.updatedoOpencheck(contractFileSendView);
			return result + "";
		} else {
			// 如果没有就新增
			User user = UserUtils.getUser();
			contractFileSendView.setCreateBy(user.getUserCode());
			contractFileSendView.setCreateTime(new Date());
			//发送状态
			if("0".equals(contractFileSendView.getFileType())){
				//1
				contractFileSendView.setSendStatus(MailStatus.TO_SEARCH.getCode());
			}else{
				//0
				contractFileSendView.setSendStatus(MailStatus.TO_MAKE.getCode());
			}
			//合同 OR结清证明
			int result = customerServeService
					.insertContractSendInfo(contractFileSendView);
			return result + "";
		}
	}

	/**
	 * 保存电子邮件信息 2016年11月8日 By 方强
	 * 
	 * @param contractFileSendEmailView
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveContractSendEmailInfo")
	public String saveContractSendEmailInfo(
			ContractFileSendEmailView contractFileSendEmailView, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		String type = "";
		if("0".equals(contractFileSendEmailView.getFileType())){
			type = "7";
		}else if("1".equals(contractFileSendEmailView.getFileType())){
			type="8";
		}
		List<Map<String, Object>> emailStatusMap = lms.getSendEMail(contractFileSendEmailView.getLoanCode(),contractFileSendEmailView.getFileType(),type);
		if(!emailStatusMap.isEmpty()&&!"0".equals(emailStatusMap.get(0).get("num").toString())){
			if("0".equals(contractFileSendEmailView.getFileType())){
				return "已经申请过协议"; 
			}else if("1".equals(contractFileSendEmailView.getFileType())){
				return "已经申请过结清证明";
			}else {
				return "提交出错";
			}
		}else{
			contractFileSendEmailView.setSendStatus("7");
			List<ContractFileSendEmailView> paybackList = customerServeService
					.findEmailApplyByDealt(contractFileSendEmailView);
			String result = "已经申请过协议";
			if (paybackList != null && !paybackList.isEmpty()) {
				// 如果有 就修改
				String id = paybackList.get(0).getId();
				contractFileSendEmailView.setId(id);
				if("0".equals(contractFileSendEmailView.getFileType())){
					contractFileSendEmailView.setSendStatus("4");
				}else if("1".equals(contractFileSendEmailView.getFileType())){
					contractFileSendEmailView.setSendStatus("0");
				}
				contractFileSendEmailView.setOperateStep("重新申请电子协议");
				result = customerServeService
						.updatedoOpencheckEmail(contractFileSendEmailView);
			}else{
				User user = UserUtils.getUser();
				contractFileSendEmailView.setApplyFor(user.getUserCode());
				contractFileSendEmailView.setApplyTime(new Date());
				contractFileSendEmailView.setModifyBy(user.getUserCode());
				contractFileSendEmailView.setModifyTime(new Date());
				if("0".equals(contractFileSendEmailView.getFileType())){
					contractFileSendEmailView.setSendStatus("4");
				}else if("1".equals(contractFileSendEmailView.getFileType())){
					contractFileSendEmailView.setSendStatus("0");
				}
				result = customerServeService
						.insertContractSendEmailInfo(contractFileSendEmailView);
			}
			return result;
		}
		/*// 先查询该合同 寄送状态为 13 的是否是 已经寄送退回的
		List<ContractFileSendEmailView> paybackList = new ArrayList<ContractFileSendEmailView>();
		// 查询该合同编号是否已经提交过电子协议申请 
		contractFileSendEmailView.setSendStatus(MailStatus.TO_STORE.getCode());
		paybackList = customerServeService
				.findEmailApplyByDealt(contractFileSendEmailView);
		if (paybackList != null && !paybackList.isEmpty()) {
			// 如果有 就修改
			String id = paybackList.get(0).getId();
			contractFileSendEmailView.setId(id);
			contractFileSendEmailView.setSendStatus("1");
			contractFileSendEmailView.setFileType("0");
			contractFileSendEmailView.setOperateStep("重新申请电子协议");
			int result = customerServeService
					.updatedoOpencheckEmail(contractFileSendEmailView);
			return result + "";
		} else {
			// 如果没有就新增
			User user = UserUtils.getUser();
			contractFileSendEmailView.setApplyFor(user.getUserCode());
			contractFileSendEmailView.setApplyTime(new Date());
			//寄送状态
			if("0".equals(contractFileSendEmailView.getFileType())){
				//1
				contractFileSendEmailView.setSendStatus(MailStatus.TO_SEARCH.getCode());
				System.out.println("**********************"+MailStatus.TO_SEARCH.getCode());
				System.out.println("**********************"+MailStatus.TO_MAKE.getCode());
			}else{
				//0
				contractFileSendEmailView.setSendStatus(MailStatus.TO_MAKE.getCode());
			}
			//合同 OR结清证明
			int result = customerServeService
					.insertContractSendEmailInfo(contractFileSendEmailView);
			return result + "";
		}*/
	}
	
	/**
	 * 退回 2016年2月23日 By 周怀富
	 * 
	 * @param contractFileSendView
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doOpencheck")
	public String doOpencheck(ContractFileSendView contractFileSendView,
			String id, String sendStatus) {
		contractFileSendView.setId(id);
		contractFileSendView.setSendStatus(sendStatus);
		contractFileSendView.preUpdate();

		ContractFileSend cotraFileSend = customerServeService
				.getApplyIdAndLoanCode(id);
		contractFileSendView.setApplyId(cotraFileSend.getApplyId());
		contractFileSendView.setLoanCode(cotraFileSend.getLoanCode());
		if ("0".equals(contractFileSendView.getFileType())) {
			if ("6".equals(sendStatus)) {
				contractFileSendView.setOperateStep("合同寄送已办列表-退回");
			} else if ("7".equals(sendStatus)) {
				contractFileSendView.setOperateStep("合同寄送待办列表-退回");
			} else if ("8".equals(sendStatus)) {
				contractFileSendView.setOperateStep("合同寄送申请列表-退回");
			}
		} else if ("1".equals(contractFileSendView.getFileType())) {
			if ("6".equals(sendStatus)) {
				contractFileSendView.setOperateStep("结清通知书已办列表-退回");
			} else if ("7".equals(sendStatus)) {
				contractFileSendView.setOperateStep("结清通知书待办列表-退回");
			} else if ("8".equals(sendStatus)) {
				contractFileSendView.setOperateStep("结清通知书待办列表-退回");
			}
		}
		//
		int result = customerServeService
				.updatedoOpencheck(contractFileSendView);
		return result + "";
	}

	/**
	 * 退回 2016年11月17日 By 方强
	 * 
	 * @param contractFileSendEmailView
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doOpenEmailcheck")
	public String doOpenEmailcheck(ContractFileSendEmailView contractFileSendEmailView,
			String id, String sendStatus) {
		contractFileSendEmailView.setId(id);
		contractFileSendEmailView.setSendStatus(sendStatus);
		contractFileSendEmailView.preUpdate();

		ContractFileSendEmailView cotraFileSendEmail = customerServeService
				.getEmailApplyIdAndLoanCode(id);
		contractFileSendEmailView.setApplyId(cotraFileSendEmail.getApplyId());
		contractFileSendEmailView.setLoanCode(cotraFileSendEmail.getLoanCode());
		contractFileSendEmailView.setOperateStep("电子协议发送申请列表-退回");
		/*if ("0".equals(contractFileSendEmailView.getFileType())) {
			if ("6".equals(sendStatus)) {
				contractFileSendEmailView.setOperateStep("电子协议发送已办列表-退回");
			}  else if ("8".equals(sendStatus)) {
				contractFileSendEmailView.setOperateStep("电子协议发送申请列表-退回");
			}
		} else if ("1".equals(contractFileSendEmailView.getFileType())) {
			if ("6".equals(sendStatus)) {
				contractFileSendEmailView.setOperateStep("电子结清通知书已办列表-退回");
			} else if ("7".equals(sendStatus)) {
				contractFileSendEmailView.setOperateStep("电子结清通知书待办列表-退回");
			}
		}*/
		//
		String result = customerServeService
				.updatedoOpencheckEmail(contractFileSendEmailView);
		return result;
	}
	
	/**
	 * 根据合同编号查询是否在门店代办中有未完成的POS还款信息 2016年2月16日 By guanhongchang
	 * 
	 * @param payback
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "findApplyByDealt", method = RequestMethod.POST)
	public String findApplyByDealt(ContractFileSendView contractFileSendView) {
		List<ContractFileSendView> paybackList = new ArrayList<ContractFileSendView>();
		// 查询该合同编号/结清证明 是否已经提交过寄送申请
		contractFileSendView.setSendStatusLab("8");
		paybackList = customerServeService
				.findApplyByDealt(contractFileSendView);
		if("0".equals(contractFileSendView.getFileType())){
			contractFileSendView.setSendStatus("7");
		}else if("1".equals(contractFileSendView.getFileType())){
			contractFileSendView.setSendStatus("8");
		}
		//List<Map<String, Object>> emailStatusMap = lms.getSendEMail(contractFileSendView.getLoanCode(),contractFileSendView.getFileType(),"6");
		if (paybackList != null && !paybackList.isEmpty()) {
			if("0".equals(contractFileSendView.getFileType())){
				return "已经提交过寄送申请，不能再次提交!";
			}else{
				return "已经提交过纸质结清通知书，不能再次提交!";
			}
		} /*else if(!emailStatusMap.isEmpty()&&!"0".equals(emailStatusMap.get(0).get("num").toString())){
			if("0".equals(contractFileSendView.getFileType())){
				return "已经提交过电子协议，不能再次提交!";
			}else{
				return "已经提交过电子结清通知书，不能再次提交!";
			}
		}*/ else {
			return "true";
		}
	}

	/**
	 * 根据合同编号查询是否在门店代办中有未完成的POS还款信息 2016年2月16日 By guanhongchang
	 * 
	 * @param payback
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "findEmailApplyByDealt ", method = RequestMethod.POST)
	public String findEmailApplyByDealt(ContractFileSendEmailView contractFileSendEmailView) {
		List<ContractFileSendEmailView> paybackList = new ArrayList<ContractFileSendEmailView>();
		// 查询该合同编号/结清证明 是否已经提交过寄送申请
		if("0".equals(contractFileSendEmailView.getFileType())){
			contractFileSendEmailView.setSendStatus("7");
		}else if("1".equals(contractFileSendEmailView.getFileType())){
			contractFileSendEmailView.setSendStatus("8");
		}
		List<Map<String, Object>> emailStatusMap = lms.getSendEMail(contractFileSendEmailView.getLoanCode(),contractFileSendEmailView.getFileType(),contractFileSendEmailView.getSendStatus());
		ContractFileSendView contractFileSendView = new ContractFileSendView();
		
		contractFileSendView.setFileType(contractFileSendEmailView.getFileType());
		contractFileSendView.setContractCode(contractFileSendEmailView.getContractCode());
		contractFileSendView.setSendStatusLab("8");
		List<ContractFileSendView> list = customerServeService.findApplyByDealt(contractFileSendView);
		if((!emailStatusMap.isEmpty()&&!"0".equals(emailStatusMap.get(0).get("num").toString()))){
			if("0".equals(contractFileSendView.getFileType())){
				return "已经提交过电子协议申请，不能再次提交!";
			}else{
				return "已经提交过电子结清通知书申请，不能再次提交!";
			}
		} else if(list != null && !list.isEmpty()){
			if("0".equals(contractFileSendView.getFileType())){
				return "已经提交过合同寄送申请，不能再次提交!";
			}else{
				return "已经提交过纸制结清证明通知申请，不能再次提交!";
			}
		} else {
			return "true";
		}
		/*contractFileSendEmailView.setSendStatusLab("8");
		paybackList = customerServeService
				.findEmailApplyByDealt(contractFileSendEmailView);
		if (paybackList != null && !paybackList.isEmpty()) {
			return JsonMapper.nonDefaultMapper().toJson(1);
		} else {
			return JsonMapper.nonDefaultMapper().toJson(-1);
		}*/
	}
	
	/**
	 * 结清证明弹出选择结清证明项 by 管洪昌
	 * 
	 * @param model
	 * @param id
	 * @param contractCode
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value = { "findServeConsult" })
	public String findServeConsult(Model model, String id, String contractCode,String loanCode,
			String creditStatus,String type,String loanFlag) {
		model.addAttribute("id", id);
		model.addAttribute("contractCode", contractCode);
		model.addAttribute("creditStatus", creditStatus);
		model.addAttribute("loanCode", loanCode);
		String settleCauseElse = "";
		model.addAttribute("settleCauseElse", settleCauseElse);
		model.addAttribute("type", type);
		model.addAttribute("loanFlag", loanFlag);
		return "borrow/serve/serveManagementForm";
	}

	/**
	 * 显示客户基本信息
	 */
	@ResponseBody
	@RequestMapping(value = "/showCustomerMsg")
	public ContractFileSendEmailView showCustomerMsg(Model model, ContractFileSendEmailView info){
		ContractFileSendEmailView customerMsg = null;
		info.setIdsList(Arrays.asList(info.getIds()));
		List<ContractFileSendEmailView> list = customerServeService.getCustomerMsg(info);
		if(list.isEmpty()){
			model.addAttribute("customerMsg", null);
		}else{
			customerMsg = list.get(0);
			List<Map<String,String>> emailList = new ArrayList();
			if("1".equals(info.getEmailTemplateType())){
				//结清证明
				if(ChannelFlag.JINXIN.getCode().equals(customerMsg.getLoanFlag())){
					emailList = customerServeService.findEmailTemplate(EmailTemplateType.THR.getName());
				}else if(ChannelFlag.ZCJ.getCode().equals(customerMsg.getLoanFlag())){
					emailList = customerServeService.findEmailTemplate(EmailTemplateType.TWO.getName());
				}else{
					emailList = customerServeService.findEmailTemplate(EmailTemplateType.ONE.getName());
				}
			}else if("2".equals(info.getEmailTemplateType())){
				//电子协议
				if(ChannelFlag.JINXIN.getCode().equals(customerMsg.getLoanFlag())){
					emailList = customerServeService.findEmailTemplate(EmailTemplateType.SIX.getName());
				}else if(ChannelFlag.ZCJ.getCode().equals(customerMsg.getLoanFlag())){
					emailList = customerServeService.findEmailTemplate(EmailTemplateType.FIV.getName());
				}else{
					emailList = customerServeService.findEmailTemplate(EmailTemplateType.FOU.getName());
				}
			}
			
			if(!emailList.isEmpty()){
				if(emailList.get(0)!=null&&emailList.get(0).get("template_content")!=null){
					// 邮件主题
					customerMsg.setSubject(emailList.get(0).get("email_description").replaceAll("customerName", customerMsg.getCustomerName()));
					String emailStartImg = ComUtils.getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, "emailStartImg");
					String emailEndImg = ComUtils.getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, "emailEndImg");
					String emailContent = emailList.get(0).get("template_content");
					emailContent = emailContent.replaceAll("startImg", "<img src='"+emailStartImg+"'>");
					emailContent = emailContent.replaceAll("customerName", customerMsg.getCustomerName());
					emailContent = emailContent.replaceAll("contractCode", customerMsg.getContractCode());
					emailContent = emailContent.replaceAll("endImg", "<img src='"+emailEndImg+"'>");
					customerMsg.setEmailContent(emailContent);
					
					//添加邮件附件
					/** 根据不同渠道查询不同合同 */
					List fileName = new ArrayList();
					//如果是电子协议
					if("0".equals(customerMsg.getFileType())){
						/** 根据不同渠道查询不同合同 */
						if(ChannelFlag.JINXIN.getCode().equals(customerMsg.getLoanFlag())){
							fileName.add(ContractType.CONTRACT_MANAGE_JX.getName());
							fileName.add(ContractType.CONTRACT_RETURN_MANAGE.getName());
						}else if(ChannelFlag.ZCJ.getCode().equals(customerMsg.getLoanFlag())){
							fileName.add(ContractType.CONTRACT_XX_MANAGE.getName());
							fileName.add(ContractType.CONTRACT_ZCJ_RETURN_MANAGE.getName());
							//fileName.add(ContractType.CONTRACT_PROTOCOL.getName());
						}else{
							fileName.add(ContractType.CONTRACT_XX_MANAGE.getName());
							fileName.add(ContractType.CONTRACT_RETURN_MANAGE.getName());
							fileName.add(ContractType.CONTRACT_PROTOCOL.getName());
						}
						customerMsg.setFileName(fileName);
						
						List<ContractFileIdAndFileNameView> fileMsgs = customerServeService.getFileNameAndFileIdList(customerMsg);
						customerMsg.setFileNameOne(fileMsgs);
						
					}	
				}
			}
			model.addAttribute("customerMsg", customerMsg);
		}
		return customerMsg;
	}
	
	/**
	 * 电子协议
	 * 发送
	 */
	@RequestMapping(value = "/updateSendOrReturn")
	public String updateSendOrReturn(Model model, HttpServletRequest request,ContractFileSendEmailView info){
		if("6".equals(info.getSendStatus())){
			String result = customerServeService.updateSend(info);
			model.addAttribute("message", result);
		}else if("7".equals(info.getSendStatus())){
			String result = customerServeService.updateReturn(info);
			model.addAttribute("message", result);
		}
		return "redirect:" + adminPath + "/borrow/serve/customerServe/contractSendEmailList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateSendOrReturns")
	public String updateSendOrReturns(Model model, HttpServletRequest request,ContractFileSendEmailView info){
		String result = "";
		if("6".equals(info.getSendStatus())){
			result = customerServeService.updateSend(info);
		}else if("7".equals(info.getSendStatus())){
			result = customerServeService.updateReturn(info);
		}
		return result;
		//return "redirect:" + adminPath + "/borrow/serve/customerServe/contractSendEmailList";
	}
	
	/**
	 * 电子协议
	 * 发送/退回
	 */
	@RequestMapping(value = "/jqUpdateSendOrReturn")
	public String jqUpdateSendOrReturn(Model model, HttpServletRequest request,ContractFileSendEmailView info){
		if("4".equals(info.getSendStatus())){
			customerServeService.updateSend(info);
		}else if("3".equals(info.getSendStatus())){
			customerServeService.updateReturn(info);
		}
		return "redirect:" + adminPath + "/borrow/serve/customerServe/waitSendEmailList";
	}
	
	@ResponseBody
	@RequestMapping(value = "/jqUpdateSendOrReturns")
	public String jqUpdateSendOrReturns(Model model, HttpServletRequest request,ContractFileSendEmailView info){
		String result = "";
		if("4".equals(info.getSendStatus())){
			result = customerServeService.updateSend(info);
		}else if("3".equals(info.getSendStatus())){
			result = customerServeService.updateReturn(info);
		}
		return result;
		//return "redirect:" + adminPath + "/borrow/serve/customerServe/waitSendEmailList";
	}
	
	/**
	 * 导入合同等邮寄基本信息 2016年5月17日 By 王彬彬
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkAll")
	public String checkAll(@RequestParam(value = "file", required = true) MultipartFile file,String fileType,
			RedirectAttributes redirectAttributes,Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ContractFileSendView contractFileSendView=new ContractFileSendView();
		int count=0;//已匹配
		int loseCount=0;//未匹配
		String cons="";
		List<ContractFileSend> list=null;
		try {
			ExcelUtils excelUtils = new ExcelUtils();
			list = (List<ContractFileSend>) excelUtils.importExcel(file, 0, 0, ContractFileSend.class, 3);
			// 0表示合同
			contractFileSendView.setFileType("0");
			// 1表示待查找7
			contractFileSendView.setSendStatus("'"+MailStatus.TO_SEARCH.getCode()+"','"+MailStatus.TO_SEND_RETURN.getCode()+"'");
			if(list!= null && list.size() > 0){
				for (ContractFileSend contractFileSend : list) {
					cons=cons+"'"+contractFileSend.getContractCode()+"',";
					contractFileSendView.setConts("'"+contractFileSend.getContractCode()+"'");
					if(customerServeService.findContractCount(contractFileSendView)>0){
						count++;
					}else{
						loseCount++;
					}
				}
				cons=cons.substring(0,cons.length()-1);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			addMessage(redirectAttributes, "导入文件有误，请检查");
		}
		contractFileSendView.setConts(cons);
		Page<ContractFileSend> page = customerServeService
				.selectContractSendList(new Page<ContractFileSend>(request,
						response), contractFileSendView);
		model.addAttribute("page", page);
		List<ContractFileSend> lst = new ArrayList<ContractFileSend>();
		for (ContractFileSend cf : page.getList()) {
			ContractFileSend c = cf;
			String creditStatus = DictCache.getInstance().getDictLabel(
					"jk_loan_status", cf.getCreditStatus());
			c.setCreditStatusName(creditStatus);

			String emergentLevel = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_urgent_flag", cf.getEmergentLevel());
			c.setEmergentLevelName(emergentLevel);

			String sendStatus = DictCache.getInstance().getDictLabel(
					"jk_cm_admin_mail_status", cf.getSendStatus());
			c.setSendStatusName(sendStatus);
			lst.add(c);
		}
		if("".equals(cons)){
			cons="1";
		}
		model.addAttribute("cons",cons);
		if(list == null || list.size()==0){
			model.addAttribute("msg", "导入数据为空，请重新导入");
		}else{
			model.addAttribute("msg", "已匹配"+count+"条，未匹配"+loseCount+"条");
		}
		model.addAttribute("contractFileSendViewlist", lst);
		model.addAttribute("contractFileSendView", contractFileSendView);
		return "borrow/serve/contractSendList";
		
	}
	
	//汇总表导出
		@RequestMapping(value = "exports")
		public void exports(Model model, HttpServletRequest request,
				HttpServletResponse response,
				ContractFileSendEmailView contractFileSendEmailView) {
			
			if(!StringUtils.isEmpty(contractFileSendEmailView.getIds())){
				//info.setIdsList(Arrays.asList(info.getIds()));
				Map<String,Object> map = Maps.newHashMap();
				map.put("fileType", "1");
				map.put("sendStatusLab", "'3','6'");
				map.put("ids", contractFileSendEmailView.getIds());
				map.put("idsList", Arrays.asList(contractFileSendEmailView.getIds().split(",")));
				ExportHelper.export(map, response, "结清导出");
			}else{
				Map<String,Object> map = Maps.newHashMap();
				map.put("fileType", "1");
				map.put("sendStatusLab", "'3','6'");
				map.put("customerName", contractFileSendEmailView.getCustomerName());
				String orgCode = contractFileSendEmailView.getOrgCode();
				String orgCodeIds = "";
				map.put("orgCode", orgCode);
				if (orgCode != null){
					String[] strArray = orgCode.split(",");
					StringBuffer sb = new StringBuffer();
					for (String str : strArray){
						sb.append("'" + str + "',");
					}
					orgCodeIds = sb.toString().substring(0, sb.toString().length() - 1);
				}
				map.put("orgCodeIds",orgCodeIds);
				
				map.put("storeName", contractFileSendEmailView.getStoreName());
				map.put("contractCode", contractFileSendEmailView.getContractCode());
				map.put("sendStatus", contractFileSendEmailView.getSendStatus());
				map.put("loanFlag", contractFileSendEmailView.getLoanFlag());
				map.put("model", contractFileSendEmailView.getModel());
				ExportHelper.export(map, response, "结清导出");
			}
			Page<ContractFileSendEmail> page = customerServeService
					.selectContractSendEmailList(new Page<ContractFileSendEmail>(request,
							response), contractFileSendEmailView);
			}
		
}
