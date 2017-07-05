package com.creditharmony.loan.borrow.account.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.email.EmailConfirmBaseService;
import com.creditharmony.adapter.service.email.bean.EmailConfirmInParam;
import com.creditharmony.adapter.service.email.bean.EmailConfirmOutParam;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.loan.borrow.account.dao.LoanBankEditDao;
import com.creditharmony.loan.borrow.account.entity.LoanBankEditEntity;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.service.HistoryService;
@Service
public class EmailConfirmService extends EmailConfirmBaseService{
	@Autowired
	LoanBankEditDao loanBankEditDao;
	
	@Autowired
	private LoanCustomerDao customerDao;
	
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	
	@Autowired
	private HistoryService historyService;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public EmailConfirmOutParam doExec(EmailConfirmInParam paramBean) {
		EmailConfirmOutParam ecop = new EmailConfirmOutParam();
		if(ObjectHelper.isNotEmpty(paramBean)){
			if("1".equals(paramBean.getBusinessType())){
				//修改邮箱邮箱确认
				ecop = emailUpdateConfirm(paramBean);
			}else if("2".equals(paramBean.getBusinessType())){
				//信借待办和申请页面邮箱确认
				ecop = emailUpdateConfirm2(paramBean);
			}else if("3".equals(paramBean.getBusinessType())){
				ecop = emailUpdateConfirm3(paramBean);
			}else if("4".equals(paramBean.getBusinessType())){
				//信借数据列表
				ecop = emailUpdateConfirm4(paramBean);
			}
			
		}else{
			ecop.setRetCode(ReturnConstant.FAIL );
			ecop.setRetMsg("箱邮箱确认返回参数为null");
			logger.error("箱邮箱确认返回参数为null");
		}
		return ecop;
	}
	
	public EmailConfirmOutParam emailUpdateConfirm(EmailConfirmInParam paramBean){
		EmailConfirmOutParam ep = new EmailConfirmOutParam();
		try {
			LoanBankEditEntity lbecheck = loanBankEditDao.selectByPrimaryKey(paramBean.getParamKey());
			if(ObjectHelper.isNotEmpty(lbecheck)){
				if("1".equals(lbecheck.getEmailFlag())){
					ep.setRetCode(ReturnConstant.FAIL);
					ep.setRetMsg("该邮箱已确认成功");
					return ep;
				}
			}
			Long sendTime = Long.valueOf(paramBean.getSendEmailTime());
			Long nowTime = new Date().getTime();
			if(nowTime-sendTime < 150000){
				LoanBankEditEntity lee = new LoanBankEditEntity();
				lee.setId(paramBean.getParamKey());
				lee.setEmailFlag("1");
				loanBankEditDao.updateByPrimaryKeySelective(lee);
				ep.setRetCode(ReturnConstant.SUCCESS);
				ep.setRetMsg("确认成功");
			}else{
				ep.setRetCode(ReturnConstant.FAIL);
				ep.setRetMsg("邮箱确认回调链接超时");
			}
		} catch (Exception e) {
			logger.error("邮箱确认回调失败"+e.getMessage());
			ep.setRetCode(ReturnConstant.FAIL);
			ep.setRetMsg("邮箱确认回调失败"+e.getMessage());
		}
		return ep;
	}
	
	public EmailConfirmOutParam emailUpdateConfirm2(EmailConfirmInParam paramBean){
		logger.info("-----邮箱验证返回信息：关键id："+paramBean.getParamKey()+" ;时间是："+paramBean.getSendEmailTime());
		EmailConfirmOutParam ep = new EmailConfirmOutParam();
		try {
			//如果有借款编号，查找对应的借款用户信息
			LoanCustomer cus = customerDao.getById(paramBean.getParamKey());
			LoanCustomer customer = new LoanCustomer();
			if(ObjectHelper.isNotEmpty(cus)){
				//根据合同编号获取客户基本信息表数据
				customer.setCustomerCode(cus.getCustomerCode());
				customer.setLoanCode(cus.getLoanCode());
				customer = customerDao.checkIfEmailConfirm(customer);
				if("1".equals(cus.getEmailIfConfirm()) && "1".equals(customer.getTempEmailIfConfirm())){
					ep.setRetCode(ReturnConstant.FAIL);
					ep.setRetMsg("该邮箱已确认成功");
					return ep;
				}
			}else{
				//根据合同编号获取客户基本信息表数据
				customer.setCustomerCode(paramBean.getParamKey());
				customer = customerDao.checkIfEmailConfirm(customer);
				if(customer!=null && "1".equals(customer.getEmailIfConfirm()) 
						&& customer.getTempEmailIfConfirm()!=null
						&& "1".equals(customer.getTempEmailIfConfirm())){
					ep.setRetCode(ReturnConstant.FAIL);
					ep.setRetMsg("该邮箱已确认成功");
					return ep;
				}
			}
			Long sendTime = Long.valueOf(paramBean.getSendEmailTime());
			Long nowTime = new Date().getTime();
			if(nowTime-sendTime < 150000){
				
				LoanCustomer temp = new LoanCustomer();
				if(cus!=null){
					temp.setId(paramBean.getParamKey());
					temp.setEmailIfConfirm("1");
					customerDao.updateCustomerEmailConfirm(temp);
					//插入历史
					insertLoanStatusHis(cus.getLoanCode());
				}
				//更新客户基本信息表中邮箱验证临时标识
				temp.setCustomerCode(customer.getCustomerCode());
				temp.setEmailIfConfirm("1");
				customerDao.updateEmailConfirm(temp);
				
				ep.setRetCode(ReturnConstant.SUCCESS);
				ep.setRetMsg("确认成功");
			}else{
				ep.setRetCode(ReturnConstant.FAIL);
				ep.setRetMsg("邮箱确认回调链接超时");
			}
		} catch (Exception e) {
			logger.error("邮箱确认回调失败",e);
			ep.setRetCode(ReturnConstant.FAIL);
			ep.setRetMsg("邮箱确认回调失败"+e.getMessage());
		}
		return ep;
	}
	
	public void insertLoanStatusHis(String loanCode){
		if(loanCode!=null && !"".equals(loanCode)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("loanCode", loanCode);
			LoanInfo loanInfo = loanInfoDao.selectByLoanCode(map);
			// 插入历史
			String operateStep ="邮箱变更验证"; //LoanApplyStatus.SIGN_CONFIRM.getName();
			String operateResult = "成功";
			String remark = operateStep;
			historyService.saveLoanStatusHis(loanInfo, operateStep, operateResult, remark);
		}
	}
	
	public EmailConfirmOutParam emailUpdateConfirm3(EmailConfirmInParam paramBean){
		logger.info("-----批量邮箱验证返回信息：关键id："+paramBean.getParamKey()+" ;时间是："+paramBean.getSendEmailTime());
		EmailConfirmOutParam ep = new EmailConfirmOutParam();
		try {
			LoanCustomer cus = customerDao.getById(paramBean.getParamKey());
			if(ObjectHelper.isNotEmpty(cus)){
				if("1".equals(cus.getEmailIfConfirm())){
					ep.setRetCode(ReturnConstant.FAIL);
					ep.setRetMsg("该邮箱已确认成功");
					return ep;
				}
			}
			Long sendTime = Long.valueOf(paramBean.getSendEmailTime());
			Long nowTime = new Date().getTime();
			if(nowTime-sendTime < 1000*60*60*24*7){
				LoanCustomer temp = new LoanCustomer();
				if(cus!=null){
					temp.setId(paramBean.getParamKey());
					temp.setEmailIfConfirm("1");
					customerDao.updateCustomerEmailConfirm(temp);
				}
				ep.setRetCode(ReturnConstant.SUCCESS);
				ep.setRetMsg("确认成功");
			}else{
				ep.setRetCode(ReturnConstant.FAIL);
				ep.setRetMsg("邮箱确认回调链接超时");
			}
		} catch (Exception e) {
			logger.error("邮箱确认回调失败"+e.getMessage());
			ep.setRetCode(ReturnConstant.FAIL);
			ep.setRetMsg("邮箱确认回调失败"+e.getMessage());
		}
		return ep;
	}
	
	public EmailConfirmOutParam emailUpdateConfirm4(EmailConfirmInParam paramBean){
		logger.info("-----邮箱验证返回信息：关键id："+paramBean.getParamKey()+" ;时间是："+paramBean.getSendEmailTime());
		EmailConfirmOutParam ep = new EmailConfirmOutParam();
		try {
			LoanCustomer cus = customerDao.getById(paramBean.getParamKey());
			if(ObjectHelper.isNotEmpty(cus)){
				if("1".equals(cus.getEmailIfConfirm())){
					ep.setRetCode(ReturnConstant.FAIL);
					ep.setRetMsg("该邮箱已确认成功");
					return ep;
				}
			}
			Long sendTime = Long.valueOf(paramBean.getSendEmailTime());
			Long nowTime = new Date().getTime();
			if(nowTime-sendTime < 1000*300){
				LoanCustomer temp = new LoanCustomer();
				if(cus!=null){
					temp.setId(paramBean.getParamKey());
					temp.setEmailIfConfirm("1");
					customerDao.updateCustomerEmailConfirm(temp);
				}
				ep.setRetCode(ReturnConstant.SUCCESS);
				ep.setRetMsg("确认成功");
			}else{
				ep.setRetCode(ReturnConstant.FAIL);
				ep.setRetMsg("邮箱确认回调链接超时");
			}
		} catch (Exception e) {
			logger.error("邮箱确认回调失败 " + paramBean.getParamKey() +e.getMessage());
			ep.setRetCode(ReturnConstant.FAIL);
			ep.setRetMsg("邮箱确认回调失败"+e.getMessage());
		}
		return ep;
	}
}
