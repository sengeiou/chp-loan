package com.creditharmony.loan.borrow.serve.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.jinxin.agreement.JinxinSendAgreementInBean;
import com.creditharmony.adapter.bean.in.mail.MailInfo;
import com.creditharmony.adapter.bean.in.zcj.agreement.ZcjSendAgreementInBean;
import com.creditharmony.adapter.bean.out.jinxin.agreement.JinxinSendAgreementOutBean;
import com.creditharmony.adapter.bean.out.mail.MailOutInfo;
import com.creditharmony.adapter.bean.out.zcj.agreement.ZcjSendAgreementOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ListUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ContractType;
import com.creditharmony.core.loan.type.EmailTemplateType;
import com.creditharmony.core.loan.type.MailStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.serve.constants.ContractHisConst;
import com.creditharmony.loan.borrow.serve.dao.CustomerServeDao;
import com.creditharmony.loan.borrow.serve.entity.ContractFileSend;
import com.creditharmony.loan.borrow.serve.entity.ContractFileSendEmail;
import com.creditharmony.loan.borrow.serve.view.ContractFileIdAndFileNameView;
import com.creditharmony.loan.borrow.serve.view.ContractFileSendEmailView;
import com.creditharmony.loan.borrow.serve.view.ContractFileSendView;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.utils.ComUtils;
import com.creditharmony.loan.common.utils.Constants;

/**
 * 合同寄送
 * 
 * @Class Name CustomerServeService
 * @author 王俊杰
 * @Create In 2016年2月1日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class CustomerServeService {
	
	@Autowired
	private CustomerServeDao customerServeDao;
	@Autowired
	HistoryService historyservice;
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	protected Logger logger = LoggerFactory.getLogger(CustomerServeService.class);
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<ContractFileSend> selectContractSendList(Page<ContractFileSend> page, ContractFileSendView contractFileSendView){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<ContractFileSend> pageList = (PageList<ContractFileSend>) customerServeDao
				.selectContractSendList(pageBounds, contractFileSendView);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<ContractFileSendEmail> selectContractSendEmailList(Page<ContractFileSendEmail> page, ContractFileSendEmailView contractFileSendEmailView){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<ContractFileSendEmail> pageList = (PageList<ContractFileSendEmail>) customerServeDao
				.selectContractSendEmailList(pageBounds, contractFileSendEmailView);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<ContractFileSend> alreadyDeleteList(Page<ContractFileSend> page, ContractFileSendView contractFileSendView){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<ContractFileSend> pageList = (PageList<ContractFileSend>) customerServeDao
				.alreadyDeleteList(pageBounds, contractFileSendView);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 更新邮件状态和历史
	 * 2016年12月23日
	 * By WJJ
	 * @param contractFileSendView
	 * @return
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public int updateEmailStatus(ContractFileSendEmailView contractFileSendEmailView,boolean type){
		contractFileSendEmailView.setModifyBy(UserUtils.getUser().getId());
		contractFileSendEmailView.setModifyTime(new Date());
		int result = customerServeDao.updateEmailStatus(contractFileSendEmailView);
		String hisType = ContractHisConst.contractHis;
		if(contractFileSendEmailView.getFileType().equals(ContractHisConst.settledType))
		{
			hisType = ContractHisConst.contractSettleHis;
		}
		if (result == 1&&type == true){
			historyservice.saveContractStatusHis(contractFileSendEmailView.getApplyId(), contractFileSendEmailView.getLoanCode(),
					contractFileSendEmailView.getOperateStep(), "成功", contractFileSendEmailView.getRemarks(),hisType);
		} else {
			historyservice.saveContractStatusHis(contractFileSendEmailView.getApplyId(), contractFileSendEmailView.getLoanCode(),
					contractFileSendEmailView.getOperateStep(), "失败", contractFileSendEmailView.getRemarks(),hisType);
		}
		return result;
	}
	
	/**
	 * 更新邮件状态和历史
	 * 2016年5月25日
	 * By 王彬彬
	 * @param contractFileSendView
	 * @return
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public int updateMailStatus(ContractFileSendView contractFileSendView){
		int result = customerServeDao.updateMailStatus(contractFileSendView);
		String hisType = ContractHisConst.contractHis;
		if(contractFileSendView.getFileType().equals(ContractHisConst.settledType))
		{
			hisType = ContractHisConst.contractSettleHis;
		}
		if (result == 1){
			historyservice.saveContractStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "成功", contractFileSendView.getRemarks(),hisType);
		} else {
			historyservice.saveContractStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "失败", contractFileSendView.getRemarks(),hisType);
		}
		return result;
	}
	
	/**
	 * 
	 * 2016年5月25日
	 * By 王彬彬
	 * @param contractFileSendView
	 * @return
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public int updateDeleteStatus(ContractFileSendView contractFileSendView){
		int result = customerServeDao.updateDeleteStatus(contractFileSendView);
		String hisType = ContractHisConst.contractHis;
		if(contractFileSendView.getFileType().equals(ContractHisConst.settledType))
		{
			hisType = ContractHisConst.contractSettleHis;
		}
		if (result == 1){
			historyservice.saveContractStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "成功", contractFileSendView.getRemarks(),hisType);
		} else {
			historyservice.saveContractStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "失败", "",hisType);
		}
		return result;
	}
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	public ContractFileSendView getApplyIdAndLoanCode(String contractCode){
		return customerServeDao.getApplyIdAndLoanCode(contractCode);
	}
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	public ContractFileSendEmailView getEmailApplyIdAndLoanCode(String contractCode){
		return customerServeDao.getEmailApplyIdAndLoanCode(contractCode);
	}
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<ContractFileSend> getExcelDataList(ContractFileSendView contractFileSendView){
		return (List<ContractFileSend>)customerServeDao.getExcelDataList(contractFileSendView);
	}
	
	@Transactional(readOnly = false,value="loanTransactionManager")
	public int deleteData(ContractFileSendView contractFileSendView){
		int result = customerServeDao.deleteData(contractFileSendView);
		if (result == 1){
			historyservice.saveLoanStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "成功", "");
		} else {
			historyservice.saveLoanStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "失败", "");
		}
		return result;
	}
	
	/**
	 * 导入excel历史记录
	 * 2016年5月17日
	 * By 王彬彬
	 * @param contractFileSendView
	 * @return
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public int inputExpressNumber(ContractFileSendView contractFileSendView){
		int result = customerServeDao.inputExpressNumber(contractFileSendView);
		String hisType = ContractHisConst.contractHis;
		if(contractFileSendView.getFileType().equals(ContractHisConst.settledType))
		{
			hisType = ContractHisConst.contractSettleHis;
		}
		if (result > 0){
			historyservice.saveContractStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "成功", "",hisType);
		} else {
			historyservice.saveContractStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "失败", "",hisType);
		}
		return result;
	}
	
	/**
	 * 保存邮寄信息
	 * 2016年2月23日
	 * By 周怀富
	 * @param contractFileSendView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public int insertContractSendInfo(ContractFileSendView contractFileSendView){
		String step = "合同寄送申请";
		contractFileSendView.preInsert();
		
		String hisType = ContractHisConst.contractHis;
		if("1".equals(contractFileSendView.getFlag())){
			contractFileSendView.setFileType("0");//设置文件类型 0:合同 1：结清证明
			//1
			contractFileSendView.setSendStatus(MailStatus.TO_SEARCH.getCode());//设置邮件状态
			step = "合同寄送申请";
		}else{
			contractFileSendView.setFileType("1");
			//1
			contractFileSendView.setSendStatus(MailStatus.TO_MAKE.getCode());
			step = "结清证明申请";
			hisType = ContractHisConst.contractSettleHis;
		}
		
		historyservice.saveContractStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
				step, "成功", "成功",hisType);
		
		contractFileSendView.setIsDelete("0");
		return customerServeDao.insertContractSending(contractFileSendView);
	}

	/**
	 * 保存电子协议信息
	 * 2016年11月8日
	 * By 方强
	 * @param contractFileSendView
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public String insertContractSendEmailInfo(ContractFileSendEmailView contractFileSendEmailView) {
		contractFileSendEmailView.preInsert();
		String result = "申请出错";
		if("0".equals(contractFileSendEmailView.getFileType())){
			//添加调用金信或大金融生成借款协议
			if(ChannelFlag.JINXIN.getCode().equals(contractFileSendEmailView.getLoanFlag())){
				ClientPoxy service = new ClientPoxy(ServiceType.Type.JINXIN_AGREEMENT_SERVICE);	
				JinxinSendAgreementInBean bean = new JinxinSendAgreementInBean();
				bean.setContractCode(contractFileSendEmailView.getContractCode());
				bean.setType("jxJKXY");

				JinxinSendAgreementOutBean retInfo = (JinxinSendAgreementOutBean)service.callService(bean);
				if (ReturnConstant.SUCCESS.equals(retInfo.getRetCode())) {
					historyservice.saveContractStatusHis(contractFileSendEmailView.getApplyId(), contractFileSendEmailView.getLoanCode(),
							"电子协议申请", "成功", "成功",ContractHisConst.contractHis);
					
					contractFileSendEmailView.setIsDelete("0");
					int n = customerServeDao.insertContractSendingEmail(contractFileSendEmailView);
					if(n>0){
						result = "true";
					}else{
						result = "提交失败";
					}
				}else{
					result = retInfo.getRetMsg();
					logger.debug("调用金信获取借款协议异常");
				}
			}else if(ChannelFlag.ZCJ.getCode().equals(contractFileSendEmailView.getLoanFlag())){
				ClientPoxy service = new ClientPoxy(ServiceType.Type.ZCJ_AGREEMENT_SERVICE);	//修改为调用大金融接口获取借款协议
				ZcjSendAgreementInBean bean = new ZcjSendAgreementInBean();
				bean.setContractCode(contractFileSendEmailView.getContractCode());
				bean.setType("zcjJKXY");

				ZcjSendAgreementOutBean retInfo = (ZcjSendAgreementOutBean)service.callService(bean);
				if (ReturnConstant.SUCCESS.equals(retInfo.getRetCode())) {
					historyservice.saveContractStatusHis(contractFileSendEmailView.getApplyId(), contractFileSendEmailView.getLoanCode(),
							"电子协议申请", "成功", "成功",ContractHisConst.contractHis);
					
					contractFileSendEmailView.setIsDelete("0");
					int n = customerServeDao.insertContractSendingEmail(contractFileSendEmailView);
					if(n>0){
						result = "true";
					}else{
						result = "提交失败";
					}
				}else{
					logger.debug("调用大金融获取借款协议异常");
				}
			}else{
				historyservice.saveContractStatusHis(contractFileSendEmailView.getApplyId(), contractFileSendEmailView.getLoanCode(),
						"电子协议申请", "成功", "成功",ContractHisConst.contractHis);
				
				contractFileSendEmailView.setIsDelete("0");
				int n = customerServeDao.insertContractSendingEmail(contractFileSendEmailView);
				if(n>0){
					result = "true";
				}else{
					result = "提交失败";
				}
			}
		}else{
			historyservice.saveContractStatusHis(contractFileSendEmailView.getApplyId(), contractFileSendEmailView.getLoanCode(),
					"结清证明申请", "成功", "成功",ContractHisConst.contractSettleHis);
			
			contractFileSendEmailView.setIsDelete("0");
			int n = customerServeDao.insertContractSendingEmail(contractFileSendEmailView);
			if(n>0){
				result = "true";
			}else{
				result = "提交失败";
			}
		}
		return result;
	}
	
	/**
	 * 更新合同/结清证明状态
	 * 2016年5月25日
	 * By 王彬彬
	 * @param contractFileSendView
	 * @return
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public int updatedoOpencheck(ContractFileSendView contractFileSendView) {
		int result = customerServeDao.updatedoOpencheck(contractFileSendView);
		
		String hisType = ContractHisConst.contractHis;
		if(contractFileSendView.getFileType().equals(ContractHisConst.settledType))
		{
			hisType = ContractHisConst.contractSettleHis;
		}
		
		if (result == 1){
			
			contractFileSendView.getApplyId();//空
			contractFileSendView.getLoanCode();//空
			contractFileSendView.getOperateStep();//空
			
			
			historyservice.saveContractStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "成功", contractFileSendView.getRemarks(),hisType);
		} else {
			historyservice.saveContractStatusHis(contractFileSendView.getApplyId(), contractFileSendView.getLoanCode(),
					contractFileSendView.getOperateStep(), "失败", "",hisType);
		}
		return result;
	}
	
	/**
	 * 更新合同/结清证明状态
	 * 2016年11月8日
	 * By 方强
	 * @param contractFileSendEmailView
	 * @return
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public String updatedoOpencheckEmail(ContractFileSendEmailView contractFileSendEmailView) {
		int n = customerServeDao.updatedoOpencheckEmail(contractFileSendEmailView);
		String result = "提交失败";
		String hisType = ContractHisConst.contractHis;
		if(contractFileSendEmailView.getFileType().equals(ContractHisConst.settledType))
		{
			hisType = ContractHisConst.contractSettleHis;
		}
		
		if (n == 1){
			
			contractFileSendEmailView.getApplyId();//空
			contractFileSendEmailView.getLoanCode();//空
			contractFileSendEmailView.getOperateStep();//空
			
			
			historyservice.saveContractStatusHis(contractFileSendEmailView.getApplyId(), contractFileSendEmailView.getLoanCode(),
					contractFileSendEmailView.getOperateStep(), "成功", contractFileSendEmailView.getRemarks(),hisType);
			result = "true";
		} else {
			historyservice.saveContractStatusHis(contractFileSendEmailView.getApplyId(), contractFileSendEmailView.getLoanCode(),
					contractFileSendEmailView.getOperateStep(), "失败", "",hisType);
		}
		return result;
	}
	
	/**
	 * 查询合同/结清证明是否存在
	 * 2016年5月25日
	 * By 王彬彬
	 * @param contractFileSendView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<ContractFileSendView> findApplyByDealt(
			ContractFileSendView contractFileSendView) {
		return customerServeDao.findApplyByDealt(contractFileSendView);	
	}
	
	/**
	 * 查询电子协议/结清证明是否存在
	 * 2016年11月07日
	 * By 方强
	 * @param contractFileSendEmailView
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<ContractFileSendEmailView> findEmailApplyByDealt(
			ContractFileSendEmailView contractFileSendEmailView) {
		return customerServeDao.findEmailApplyByDealt(contractFileSendEmailView);	
	}
	
	/**
	 * 更新邮寄状态
	 * 2016年5月25日
	 * By 王彬彬
	 * @param contractFileSendView
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateMailLoan(ContractFileSendView contractFileSendView) {
		customerServeDao.updateMailLoan(contractFileSendView);
	}

	/**
	 * 
	 * 显示客户基本信息
	 */
	public List<ContractFileSendEmailView> getCustomerMsg(
			ContractFileSendEmailView info) {
		return customerServeDao.getCustomerMsg(info);
	}
	
	/**
	 * 电子协议申请列表
	 * 发送
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String updateSend(ContractFileSendEmailView ex){
		int result = 0;
		int error = 0;
//		//如果是批量发送，执行
		User user = UserUtils.getUser();
		if(StringUtils.isNotEmpty(ex.getIds())){
			ex.setIdsList(Arrays.asList(ex.getIds().split(",")));
			List<ContractFileSendEmailView> s = customerServeDao.getCustomerMsg(ex);
			if(ListUtils.isNotEmptyList(s)){
				for(ContractFileSendEmailView cex : s){
					List fileName = new ArrayList();//要发送邮件的合同名称
					String remark = "成功";
					String hisType = "";
					String operateStep = "";
					/** 根据不同渠道查询不同合同 */
					if("0".equals(ex.getFileType())){
						hisType = ContractHisConst.contractHis;
						operateStep = "发送电子协议";
						if(ChannelFlag.JINXIN.getCode().equals(cex.getLoanFlag())){
							fileName.add(ContractType.CONTRACT_MANAGE_JX.getName());
							fileName.add(ContractType.CONTRACT_RETURN_MANAGE.getName());
						}else if(ChannelFlag.ZCJ.getCode().equals(cex.getLoanFlag())){
							fileName.add(ContractType.CONTRACT_XX_MANAGE.getName());
							fileName.add(ContractType.CONTRACT_ZCJ_RETURN_MANAGE.getName());
							//fileName.add(ContractType.CONTRACT_PROTOCOL.getName());
						}else{
							fileName.add(ContractType.CONTRACT_XX_MANAGE.getName());
							fileName.add(ContractType.CONTRACT_RETURN_MANAGE.getName());
							fileName.add(ContractType.CONTRACT_PROTOCOL.getName());
						}
						cex.setFileName(fileName);
						List<ContractFileSendEmailView> fileMsgs = customerServeDao.getFileNameList(cex);//查询合同
						int size = 0;
						if(ChannelFlag.JINXIN.getCode().equals(cex.getLoanFlag())){
							//如果是金信，将文件ID容器加大一位（用来放金信借款协议）
							size = fileMsgs.size()+1;
						}else if(ChannelFlag.ZCJ.getCode().equals(cex.getLoanFlag())){
							size = fileMsgs.size()+1;
						}else{
							size = fileMsgs.size();
						}
						String [] docIds = new String[size];
						/** 放入合同ID */
						if(ListUtils.isNotEmptyList(fileMsgs)){
							for(int i=0;i<fileMsgs.size();i++){
								ContractFileSendEmailView carEx = fileMsgs.get(i);
								docIds[i] = carEx.getDocId();
							}
						}
						/** 如果是金信或是大金融 */
						if(ChannelFlag.JINXIN.getCode().equals(cex.getLoanFlag())||ChannelFlag.ZCJ.getCode().equals(cex.getLoanFlag())){
							/** 断断是否有推过来的借款协议,如果没有则不发送邮件 */
							if(cex.getPdfId()!=null&&!"".equals(cex.getPdfId())){
								docIds[fileMsgs.size()] = cex.getPdfId();
								ex.setId(cex.getId());
								ex.setContractCode(cex.getContractCode());
								//判断邮箱是否为空，不为空执行
								if(StringUtils.isNotEmpty(cex.getReceiverEmail())){
									//调用发送邮件方法，如果返回true，发送成功，如果返回false，发送失败
									//发送失败设置为协议类型为拒绝
									boolean b = sendMail(cex.getReceiverEmail(),docIds,cex.getCustomerName(),cex.getContractCode(),ex.getEmailTemplateType(),cex.getLoanFlag());
							        if(!b){
							        	error++;
							        	ex.setSendStatus("5");
							        	customerServeDao.updateSendOrReturn(ex);
							        	ex.setSendStatusName(YESNO.NO.getCode());
							        	remark = "失败";
							        }else{
							        	result ++;
										//更新电子协议状态
										cex.setSendStatus("6");
										customerServeDao.updateSendOrReturn(cex);
									}
								}
							}else{
								remark = "失败";
							}
						}else{
							ex.setId(cex.getId());
							ex.setContractCode(cex.getContractCode());
							//判断邮箱是否为空，不为空执行
							if(StringUtils.isNotEmpty(cex.getReceiverEmail())){
								//调用发送邮件方法，如果返回true，发送成功，如果返回false，发送失败
								//发送失败设置为协议类型为拒绝
								boolean b = sendMail(cex.getReceiverEmail(),docIds,cex.getCustomerName(),cex.getContractCode(),ex.getEmailTemplateType(),cex.getLoanFlag());
						        if(!b){
						        	error++;
						        	ex.setSendStatus("5");
						        	customerServeDao.updateSendOrReturn(ex);
						        	ex.setSendStatusName(YESNO.NO.getCode());
						        	remark = "失败";
						        }else{
						        	result ++;
						        	//更新电子协议状态
									cex.setSendStatus("6");
									customerServeDao.updateSendOrReturn(cex);
						        }
							}
						}
					}else if("1".equals(ex.getFileType())){
						//结清
						hisType = ContractHisConst.contractSettleHis;
						operateStep = "发送结清通知证明";
						boolean b = sendMail(cex.getReceiverEmail(),new String[]{cex.getPdfId()},cex.getCustomerName(),cex.getContractCode(),ex.getEmailTemplateType(),cex.getLoanFlag());
						if(!b){
				        	error++;
				        	cex.setSendStatus("9");
				        	customerServeDao.updateSendOrReturn(cex);
				        	ex.setSendStatusName(YESNO.NO.getCode());
				        	remark = "失败";
				        }else{
				        	result ++;
				        	//更新电子协议状态
				        	ex.setSendStatus("3");
							cex.setSendStatus(ex.getSendStatus());
							customerServeDao.updateSendOrReturn(cex);
				        }
					}
					/*ex.preInsert();
					ex.setOperateStep("批量发送");
					ex.setRemark(remark);
					ex.setOperateStep("发送电子协议");*/
					historyservice.saveContractStatusHis(cex.getApplyId(), cex.getLoanCode(),
							operateStep, "成功", remark,hisType);
					//loanStatusHisDao.insertAgrLog(ex);
				}
			}
		}
		//如果是单条
		else if(StringUtils.isNotEmpty(ex.getId())){
			String remark = "成功";
			String hisType = "";
			String operateStep = "";
				/** 根据不同渠道查询不同合同 */
				List fileName = new ArrayList();
				//如果是电子协议
				if("0".equals(ex.getFileType())){
					/** 根据不同渠道查询不同合同 */
					hisType = ContractHisConst.contractHis;
					operateStep = "发送电子协议";
					if(ChannelFlag.JINXIN.getCode().equals(ex.getLoanFlag())){
						fileName.add(ContractType.CONTRACT_MANAGE_JX.getName());
						fileName.add(ContractType.CONTRACT_RETURN_MANAGE.getName());
					}else if(ChannelFlag.ZCJ.getCode().equals(ex.getLoanFlag())){
						fileName.add(ContractType.CONTRACT_XX_MANAGE.getName());
						fileName.add(ContractType.CONTRACT_ZCJ_RETURN_MANAGE.getName());
						//fileName.add(ContractType.CONTRACT_PROTOCOL.getName());
					}else{
						fileName.add(ContractType.CONTRACT_XX_MANAGE.getName());
						fileName.add(ContractType.CONTRACT_RETURN_MANAGE.getName());
						fileName.add(ContractType.CONTRACT_PROTOCOL.getName());
					}
					ex.setFileName(fileName);
					
					List<ContractFileSendEmailView> fileMsgs = customerServeDao.getFileNameList(ex);
					int size = 0;
					if(ChannelFlag.JINXIN.getCode().equals(ex.getLoanFlag())){
						size = fileMsgs.size()+1;
					}else if(ChannelFlag.ZCJ.getCode().equals(ex.getLoanFlag())){
						size = fileMsgs.size()+1;
					}else{
						size = fileMsgs.size();
					}
					String [] docIds = new String[size];
					
					if(ListUtils.isNotEmptyList(fileMsgs)) {
						if(ListUtils.isNotEmptyList(fileMsgs)){
							for(int i=0;i<fileMsgs.size();i++){
								ContractFileSendEmailView carEx = fileMsgs.get(i);
								docIds[i] = carEx.getDocId();
							}
						}
					}
					if(ChannelFlag.JINXIN.getCode().equals(ex.getLoanFlag())||ChannelFlag.ZCJ.getCode().equals(ex.getLoanFlag())){
						/** 断断是否有推过来的借款协议,如果没有则不发送邮件 */
						if(ex.getPdfId()!=null&&!"".equals(ex.getPdfId())){
							docIds[fileMsgs.size()] = ex.getPdfId();
							boolean b = sendMail(ex.getReceiverEmail(),docIds,ex.getCustomerName(),ex.getContractCode(),ex.getEmailTemplateType(),ex.getLoanFlag());
					        if(!b){
					        	error++;
					        	ex.setSendStatus("5");
					        	customerServeDao.updateSendOrReturn(ex);
					        	ex.setSendStatusName(YESNO.NO.getCode());
						        remark = "失败";
					        }else{
					        	result ++;
					        	ex.setSendStatus("6");
								customerServeDao.updateSendOrReturn(ex);
					        }
						}else{
							remark = "失败";
						}
					}else{
						boolean b = sendMail(ex.getReceiverEmail(),docIds,ex.getCustomerName(),ex.getContractCode(),ex.getEmailTemplateType(),ex.getLoanFlag());
				        if(!b){
				        	error++;
				        	ex.setSendStatus("5");
				        	customerServeDao.updateSendOrReturn(ex);
				        	ex.setSendStatusName(YESNO.NO.getCode());
					        remark = "失败";
				        }else{
				        	result ++;
				        	ex.setSendStatus("6");
							customerServeDao.updateSendOrReturn(ex);
				        }
					}
				}else if("1".equals(ex.getFileType())){
					//结清证明
					hisType = ContractHisConst.contractSettleHis;
					operateStep = "发送结清通知证明";
					boolean b = sendMail(ex.getReceiverEmail(),new String[]{ex.getPdfId()},ex.getCustomerName(),ex.getContractCode(),ex.getEmailTemplateType(),ex.getLoanFlag());
			        if(!b){
			        	error++;
			        	ex.setSendStatus("9");
			        	customerServeDao.updateSendOrReturn(ex);
			        	ex.setSendStatusName(YESNO.NO.getCode());
				        remark = "失败";
			        }else{
			        	result ++;
			        	ex.setSendStatus("3");
						customerServeDao.updateSendOrReturn(ex);
			        }
				}
			/*ex.preInsert();
			ex.setOperateStep(step);
			ex.setRemark(remark+"<br/>原因："+ex.getDictDealReason());*/
			historyservice.saveContractStatusHis(ex.getApplyId(), ex.getLoanCode(),
					operateStep, "成功", remark,hisType);
			//customerServeDao.insertAgrLog(ex);
		}
		return "成功"+result+"条数据,失败"+error+"条数据。" ;
	}

	/**
	 * 电子协议申请列表
	 * 退回
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String updateReturn(ContractFileSendEmailView ex){
		int result = 0;
		//int error = 0;
//		//如果是批量退回，执行
		if(StringUtils.isNotEmpty(ex.getIds())){
			ex.setIdsList(Arrays.asList(ex.getIds().split(",")));
			List<ContractFileSendEmailView> s = customerServeDao.getCustomerMsg(ex);
			if(ListUtils.isNotEmptyList(s)){
				for(ContractFileSendEmailView cex : s){
					//更新电子协议状态
					ex.setId(cex.getId());
					String type = "";
					String operateStep = "";
					if("0".equals(ex.getFileType())){
						ex.setSendStatus("7");
					}else if("1".equals(ex.getFileType())){
						ex.setSendStatus("8");
					}
					int i = customerServeDao.updateSendOrReturn(ex);
					result += i;
					if("0".equals(cex.getFileType())){
						type = ContractHisConst.contractHis;
						operateStep = "电子协议申请退回";
					}else if("1".equals(cex.getFileType())){
						type = ContractHisConst.contractSettleHis;
						operateStep = "结清证明通知退回";
					}
					historyservice.saveContractStatusHis(cex.getApplyId(), cex.getLoanCode(),
							operateStep, "成功", "退回",type);
				}
			}
		}
		//如果是单个退回，执行
		else if(StringUtils.isNotEmpty(ex.getId())){
			if("0".equals(ex.getFileType())){
				ex.setSendStatus("7");
			}else if("1".equals(ex.getFileType())){
				ex.setSendStatus("8");
			}
			int i = customerServeDao.updateSendOrReturn(ex);
			result += i;
			String type = "";
			String operateStep = "";
			if("0".equals(ex.getFileType())){
				type = ContractHisConst.contractHis;
				operateStep = "电子协议申请退回";
			}else if("1".equals(ex.getFileType())){
				type = ContractHisConst.contractSettleHis;
				operateStep = "结清证明通知退回";
			}
			historyservice.saveContractStatusHis(ex.getApplyId(), ex.getLoanCode(),
					operateStep, "成功", "退回",type);
			//customerServeDao.insertAgrLog(ex);
		}
		return "成功"+result+"条数据";
	}
	
	/**
	 * 实时发送邮件
	 * @param recp 收件人地址
	 * @param attach 附件
	 * @param subject 主题
	 * @param content 邮件内容
	 */
	public boolean sendMail(String recp,String [] attach,String subject,String contractCode,String emailTemplateType,String loanFlag){
		logger.info("合同号："+contractCode+"发送邮件，发送邮箱："+recp+",发送文档ID"+attach);
		ClientPoxy service = new ClientPoxy(ServiceType.Type.SEND_MAIL);
		// 发送邮件
		MailInfo mailInfo = new MailInfo();
		// 收件人地址
		String[] toAddrArray = {recp};
		mailInfo.setToAddrArray(toAddrArray);
		// 邮件附件
		if(attach.length > 0){
			mailInfo.setDocIdArray(attach);
			// chp3.0附件
//			if (attach.startsWith("{")) {
				mailInfo.setDocType("1");
//			} else {
//				mailInfo.setDocType("2");
//			}
		}		
		// 邮件主题
		//mailInfo.setSubject(subject+MailUtil.SUBJECT);
		// 邮件内容
		/* String msg = "尊敬的"+subject+"客户您好：<br />"+
				  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的借款经信和汇金信息咨询（北京）有限公司、信和汇诚信用管理（北京）有限公司、信和惠民投资管理（北京）有限公司、信和财富投资管理（北京）有限公司撮合成功，协议编号为:"+contractCode+"的一系列电子协议已经以附件形式发送，请您查收。如有疑问可致电我司全国客服热线：400-090-1199。"+
				"<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;祝您生活愉快！";*/
		 List<Map<String,String>> emailList = new ArrayList();
			if("1".equals(emailTemplateType)){
				//结清证明
				if(ChannelFlag.JINXIN.getCode().equals(loanFlag)){
					emailList = this.findEmailTemplate(EmailTemplateType.THR.getName());
				}else if(ChannelFlag.ZCJ.getCode().equals(loanFlag)){
					emailList = this.findEmailTemplate(EmailTemplateType.TWO.getName());
				}else{
					emailList = this.findEmailTemplate(EmailTemplateType.ONE.getName());
				}
			}else if("2".equals(emailTemplateType)){
				//电子协议
				if(ChannelFlag.JINXIN.getCode().equals(loanFlag)){
					emailList = this.findEmailTemplate(EmailTemplateType.SIX.getName());
				}else if(ChannelFlag.ZCJ.getCode().equals(loanFlag)){
					emailList = this.findEmailTemplate(EmailTemplateType.FIV.getName());
				}else{
					emailList = this.findEmailTemplate(EmailTemplateType.FOU.getName());
				}
			}
			
			if(!emailList.isEmpty()){
				if(emailList.get(0)!=null&&emailList.get(0).get("template_content")!=null){
					// 邮件主题
					mailInfo.setSubject(emailList.get(0).get("email_description").replaceAll("customerName", subject));
					String emailStartImg = ComUtils.getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, "emailStartImg");
					String emailEndImg = ComUtils.getPropertyByName(Constants.JOB_SETTING_PROPERTY_NAME, "emailEndImg");
					String emailContent = emailList.get(0).get("template_content");
					emailContent = emailContent.replaceAll("startImg", "<img src='"+emailStartImg+"'>");
					emailContent = emailContent.replaceAll("customerName", subject);
					emailContent = emailContent.replaceAll("contractCode", contractCode);
					emailContent = emailContent.replaceAll("endImg", "<img src='"+emailEndImg+"'>");
					mailInfo.setContent(emailContent);
				}
			}
		//发送
		MailOutInfo mailOutInfo = (MailOutInfo) service.callService(mailInfo);
		if (ReturnConstant.SUCCESS.equals(mailOutInfo.getRetCode())) {
			logger.info("邮件发送成功");
			return true;
		} else {
			logger.info("邮件发送失败");
			return false;
		}
	}
	
	public ContractFileSendView getContractFile(
			ContractFileSend contractFileSend) {
		return customerServeDao.getContractFile(contractFileSend);
	}
	/**
	 * 更新邮件状态和历史
	 * @param contractFileSendView
	 * @return
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void updateMailStatusAll(ContractFileSendView contractFileSendView) {
		// 0表示合同
		 contractFileSendView.setFileType("0");
		 contractFileSendView.setSendStatus("'"+MailStatus.TO_SEARCH.getCode()+"','"+MailStatus.TO_SEND_RETURN.getCode()+"'");
		 List<ContractFileSendView> lists= customerServeDao.contractFileList(contractFileSendView);
		 contractFileSendView.preUpdate();
		 
		 customerServeDao.updateMailStatusAll(contractFileSendView);
		
		String hisType = ContractHisConst.contractHis;
		for(ContractFileSendView contract : lists){
			historyservice.saveContractStatusHis(contract.getApplyId(), contract.getLoanCode(),
					"合同寄送列表-接收", "成功", contract.getRemarks(),hisType);
		}
		
	}

	public int findContractCount(ContractFileSendView contractFileSendView) {
		
		return customerServeDao.findContractCount(contractFileSendView);
	}
	
	public LoanCustomer selectByLoanCode(String loanCode) {
		Map<String,Object> param = new HashMap<String,Object>();
    	param.put("loanCode", loanCode);
		return loanCustomerDao.selectByLoanCode(param);
	}
	
	public List<Map<String,Integer>> getMaxNumber(int num,String start) {
		Map param = new HashMap();
    	param.put("length", num);
    	param.put("start", start);
		return customerServeDao.getMaxNumber(param);
	}
	
	public List<Map<String,String>> findEmailTemplate(String templateType){
		return customerServeDao.findEmailTemplate(templateType);
	}
	
	public String getSettledDate(String loanCode){
		List<Map<String,String>> list = customerServeDao.getSettledDate(loanCode);
		if(!list.isEmpty()&&list.get(0)!=null){
			return list.get(0).get("settled_date");
		}else{
			return null;
		}
	}
	/**
	 * 批量下载文件名称需要
	 * 申阿伟
	 * @param id
	 * @return
	 */
	public ContractFileSendEmailView selectCustomerServeBydownLoad(String id){
		return customerServeDao.selectCustomerServeBydownLoad(id);
	}

	public String getChannelFlag(String loanCode) {

		return customerServeDao.getChannelFlag(loanCode);
	}
	/**
	 * zmq
	* @Title: getFileNameList
	* @Description: TODO(查询合同)
	* @param @param info
	* @param @return    设定文件
	* @return List<ContractFileIdAndFileNameView>    返回类型
	* @throws
	 */
	public List<ContractFileIdAndFileNameView> getFileNameAndFileIdList(ContractFileSendEmailView info){
		return customerServeDao.getFileNameAndFileIdList(info);
	}
	
}
