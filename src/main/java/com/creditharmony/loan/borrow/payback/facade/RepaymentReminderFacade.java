package com.creditharmony.loan.borrow.payback.facade;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.core.loan.type.EmailType;
import com.creditharmony.core.loan.type.SendEmailStatus;
import com.creditharmony.core.thread.ProcessorHandler;
import com.creditharmony.loan.borrow.payback.entity.ex.RepaymentReminderEx;
import com.creditharmony.loan.borrow.payback.service.RepaymentReminderService;
import com.creditharmony.loan.borrow.serve.entity.PaybackMonthSendEmail;
import com.creditharmony.loan.borrow.serve.service.EmailServeService;

/**
 * 发起还款申请 Facade
 * @author ws
 */
@Service
public class RepaymentReminderFacade implements ProcessorHandler<RepaymentReminderEx>{
	
	protected Logger logger = LoggerFactory.getLogger(RepaymentReminderFacade.class);
	private final ExecutorService executor = Executors.newFixedThreadPool(16);
	@Autowired
	private RepaymentReminderService repaymentReminderService;
	@Autowired
	private EmailServeService emailServeService;
	
	@SuppressWarnings({ "finally", "unused" })
	public String submitData(List<RepaymentReminderEx> deductApplyList) {
		StringBuffer message = new StringBuffer();
		CompletionService<RepaymentReminderEx> completionService = new ExecutorCompletionService<RepaymentReminderEx>(
				executor);
		for ( final RepaymentReminderEx apply :deductApplyList) {
			completionService.submit(new Callable<RepaymentReminderEx>() {
				public RepaymentReminderEx call() {
					return sendRemindMsg(apply);
				}
			});
		}
		int successNum = 0;
		int failNum = 0;
		BigDecimal totalAmount = new BigDecimal(0);
		try {
			//插入邮件提醒记录
			PaybackMonthSendEmail email = new PaybackMonthSendEmail();
			email.setSendEmailStatus(SendEmailStatus.UNSEND.getCode());
			email.setEmailType(EmailType.PAYBACKREMIND.getCode());
			//将之前未发送的提醒数据清空
			emailServeService.deleteEmailByStatus(email);
			for (RepaymentReminderEx apply : deductApplyList) {
				Future<RepaymentReminderEx> future = completionService.take();
				RepaymentReminderEx fapply = future.get();
					if(fapply.isSuccess()){
						successNum++;
						
						insertPaybackRemind(apply);
					}else{
						failNum++;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e){
			e.printStackTrace();
		} finally{
			if (successNum > 0) {
				message.append("推送成功条数：");
				message.append(successNum);
				message.append(",");
			}
			if (failNum > 0) {
				message.append("推送失败条数：");
				message.append(failNum);
				message.append("。");
			}
			return message.toString();
		}
	}
	
	@Override
	public void run(RepaymentReminderEx deductApply) {
		sendRemindMsg(deductApply);
	}
	public RepaymentReminderEx sendRemindMsg(
			final RepaymentReminderEx deductApply) {
			try {
				repaymentReminderService.sendRemindMsg(deductApply);
				deductApply.setSuccess(true);
			} catch (Exception e) {
				deductApply.setSuccess(false);
				e.printStackTrace();
				logger.error("短信推送：期供id"+deductApply.getId()+"出错");
			}
		return deductApply;
	}
	
	/**
	 * 将数据插入到汇金邮件管理中的还款提醒列表中
	 * @author 于飞
	 * @Create 2017年4月17日
	 * @param apply
	 */
	public void insertPaybackRemind(RepaymentReminderEx apply){
		PaybackMonthSendEmail email = new PaybackMonthSendEmail();
		email.setSendEmailStatus(SendEmailStatus.UNSEND.getCode());
		email.setEmailType(EmailType.PAYBACKREMIND.getCode());
		email.preInsert();
		email.setContractCode(apply.getContractCode());
		email.setPaybackMonthId(apply.getId());
		emailServeService.insertSendEmail(email);
	}
}
