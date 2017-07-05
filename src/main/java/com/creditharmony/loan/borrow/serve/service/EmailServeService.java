package com.creditharmony.loan.borrow.serve.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.serializer.FilterUtils;
import com.creditharmony.adapter.bean.in.mail.MailInfo;
import com.creditharmony.adapter.bean.out.mail.MailOutInfo;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.EmailType;
import com.creditharmony.core.loan.type.SendEmailStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.serve.dao.EmailServeDao;
import com.creditharmony.loan.borrow.serve.entity.PaybackMonthSendEmail;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.EmailOpe;
import com.creditharmony.loan.common.utils.FilterHelper;

import ch.qos.logback.core.filter.Filter;

/**
 * 汇金邮件
 * 
 * @Class Name EmailServeService
 * @author yufei
 * @Create In 2017年3月6日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class EmailServeService {
	
	@Autowired
	private EmailServeDao emailServeDao;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	
	private static String[] cusEmailArray= {"jiekuan@creditharmony.cn", "yunniu@creditharmony.cn","yingyinglu1@creditharmony.cn"};
	
	/**
	 * 获取还款提醒列表
	 * @author 于飞
	 * @Create 2017年3月6日
	 * @param page
	 * @param sendEmail
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<PaybackMonthSendEmail> findEmailList(Page<PaybackMonthSendEmail> page
			,PaybackMonthSendEmail sendEmail) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("paybackMonthId");
		PageList<PaybackMonthSendEmail> pageList = (PageList<PaybackMonthSendEmail>) emailServeDao
				.findEmailList(sendEmail,pageBounds);
		PageUtil.convertPage(pageList, page);
		
		for (int i=0;i<page.getList().size();i++) {
			PaybackMonthSendEmail pt = page.getList().get(i);
			//借款状态
			if(StringUtils.isNotEmpty(pt.getLoanStatus())){
				String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
						"jk_loan_apply_status", pt.getLoanStatus());
				pt.setLoanStatusLabel(dictLoanStatusLabel);
				
			}
			//发送状态
			if(StringUtils.isNotEmpty(pt.getSendEmailStatus())){
				if(pt.getSendEmailStatus().equals(SendEmailStatus.UNUSE.getCode())){
					pt.setSendEmailStatusLabel(SendEmailStatus.FAIL.getName());
				}else{
					String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
							"jk_send_email_status", pt.getSendEmailStatus());
					pt.setSendEmailStatusLabel(dictLoanStatusLabel);
				}
			}else{
				pt.setSendEmailStatusLabel(SendEmailStatus.UNSEND.getName());
			}
			//邮件类型
			if(StringUtils.isNotEmpty(pt.getEmailType())){
				String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
						"jk_email_type", pt.getEmailType());
				pt.setEmailTypeLabel(dictLoanStatusLabel);
			}
		}
		return page;
	}
	
	/**
	 * 获取还款提醒列表
	 * @author 于飞
	 * @Create 2017年3月6日
	 * @param page
	 * @param sendEmail
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<PaybackMonthSendEmail> findSendEmailList(Page<PaybackMonthSendEmail> page
			,PaybackMonthSendEmail sendEmail) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("id");
		
		PageList<PaybackMonthSendEmail> pageList = (PageList<PaybackMonthSendEmail>) emailServeDao
				.findSendEmailList(sendEmail,pageBounds);
		PageUtil.convertPage(pageList, page);
		
		for (int i=0;i<page.getList().size();i++) {
			PaybackMonthSendEmail pt = page.getList().get(i);
			//借款状态
			if(StringUtils.isNotEmpty(pt.getLoanStatus())){
				String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
						"jk_loan_apply_status", pt.getLoanStatus());
				pt.setLoanStatusLabel(dictLoanStatusLabel);
				
			}
			//发送状态
			if(StringUtils.isNotEmpty(pt.getSendEmailStatus())){
				if(pt.getSendEmailStatus().equals(SendEmailStatus.UNUSE.getCode())){
					pt.setSendEmailStatusLabel(SendEmailStatus.FAIL.getName());
				}else{
					String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
							"jk_send_email_status", pt.getSendEmailStatus());
					pt.setSendEmailStatusLabel(dictLoanStatusLabel);
				}
			}else{
				pt.setSendEmailStatusLabel(SendEmailStatus.UNSEND.getName());
			}
			//邮件类型
			if(StringUtils.isNotEmpty(pt.getEmailType())){
				String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
						"jk_email_type", pt.getEmailType());
				pt.setEmailTypeLabel(dictLoanStatusLabel);
			}
		}
		return page;
	}
	
	/**
	 * 获取还款提醒列表
	 * @author 于飞
	 * @Create 2017年3月6日
	 * @param page
	 * @param sendEmail
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<PaybackMonthSendEmail> findHolidayList(Page<PaybackMonthSendEmail> page
			,PaybackMonthSendEmail sendEmail) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("contractCode");
		
		PageList<PaybackMonthSendEmail> pageList = (PageList<PaybackMonthSendEmail>) emailServeDao
				.findHolidayList(sendEmail,pageBounds);
		PageUtil.convertPage(pageList, page);
		
		for (int i=0;i<page.getList().size();i++) {
			PaybackMonthSendEmail pt = page.getList().get(i);
			//借款状态
			if(StringUtils.isNotEmpty(pt.getLoanStatus())){
				String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
						"jk_loan_apply_status", pt.getLoanStatus());
				pt.setLoanStatusLabel(dictLoanStatusLabel);
				
			}
			
		}
		return page;
	}
	
	
	/**
	 * 获取还款提醒列表
	 * @author 于飞
	 * @Create 2017年3月6日
	 * @param page
	 * @param sendEmail
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<PaybackMonthSendEmail> findEmailList(PaybackMonthSendEmail sendEmail) {
		return emailServeDao.findEmailList(sendEmail);
	}
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<PaybackMonthSendEmail> findHolidayList(PaybackMonthSendEmail sendEmail) {
		return emailServeDao.findHolidayList(sendEmail);
	}
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<PaybackMonthSendEmail> findSendEmailList(PaybackMonthSendEmail sendEmail) {
		return emailServeDao.findSendEmailList(sendEmail);
	}
	/**
	 * 发送邮件或作废
	 * @author 于飞
	 * @Create 2017年3月8日
	 * @param ids
	 * @param status
	 * @param type 1:根据期供id  2：根据已发送的邮件id
	 * @return
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public Map<String,Object> insertSendEmail(String ids,String idType,PaybackMonthSendEmail sendEmail){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//发送失败个数
		int failNum=0;
		//设置查询条件
		List<PaybackMonthSendEmail> paybackList = new ArrayList<PaybackMonthSendEmail>();
		PaybackMonthSendEmail searchEmail = new PaybackMonthSendEmail();
		searchEmail.setContractCode(sendEmail.getContractCode());
		searchEmail.setCustomerEmail(sendEmail.getCustomerEmail());
		searchEmail.setCustomerName(sendEmail.getCustomerName());
		searchEmail.setStoreId(sendEmail.getStoreId());
		searchEmail.setLoanStatus(sendEmail.getLoanStatus());
		searchEmail.setEmailType(sendEmail.getEmailType());
		searchEmail.setSendEmailStatus(sendEmail.getSendEmailStatus());
		searchEmail.setPaperlessFlag(sendEmail.getPaperlessFlag());
		
		//获取需要修改状态的数据
		if(idType.equals("1")){
			searchEmail.setSendEmailStatus(SendEmailStatus.UNSEND.getCode());
			if(ids!=null && !"".equals(ids)){
				searchEmail.setPaybackMonthId(ids);
			}
			paybackList = emailServeDao.findEmailList(searchEmail);
			//虚拟邮箱发送邮件
			searchEmail.setEmailType(searchEmail.getEmailType().replace("'", ""));
			sendVirtualEmail(searchEmail);
			
		}else if(idType.equals("2")){
			searchEmail.setSendEmailStatus("'"+SendEmailStatus.FAIL.getCode()+"'");
			if(ids!=null && !"".equals(ids)){
				searchEmail.setId(ids);
			}
			paybackList = emailServeDao.findSendEmailList(searchEmail);
		}else if(idType.equals("3")){
			if(ids!=null && !"".equals(ids)){
				searchEmail.setContractCode(ids);
			}else if(sendEmail.getContractCode()!=null && !"".equals(sendEmail.getContractCode())){
				searchEmail.setContractCode(FilterHelper.appendIdFilter(sendEmail.getContractCode()));
			}
			paybackList = emailServeDao.findHolidayList(searchEmail);
			//虚拟邮箱发送邮件
			searchEmail.setEmailType(searchEmail.getEmailType().replace("'", ""));
			sendVirtualEmail(searchEmail);
		}
		
		for(int i=0;i<paybackList.size();i++){
			PaybackMonthSendEmail temp = paybackList.get(i);
			if(temp.getBankName()!=null && !temp.getBankName().equals("")){
				temp.setBankName(DictCache.getInstance().getDictLabel(
						"jk_open_bank", temp.getBankName()));
			}
			if(temp.getEmailType()==null || "".equals(temp.getEmailType())){
				temp.setEmailType(searchEmail.getEmailType());
			}
			temp.setStartTime(sendEmail.getStartTime());
			temp.setEndTime(sendEmail.getEndTime());
			String sendResult = SendEmailStatus.SUCCESS.getCode();
			String msg = null;
			//发送邮件
			if(sendEmail.getSendEmailStatus().equals(SendEmailStatus.SUCCESS.getCode())){
				boolean result = false;
				//只有邮箱验证通过的才能发送邮件
				if(temp.getCustomerEmail()!=null && !temp.getCustomerEmail().equals("") 
						&& temp.getEmailIfConfirm()!=null && temp.getEmailIfConfirm().equals("1")){
					result = sendEmail(temp);
					if(!result){
						sendResult = SendEmailStatus.FAIL.getCode();
						msg = "邮件发送失败";
						failNum++;
					}else{
						msg = "邮件发送成功";
					}
				}else if(temp.getCustomerEmail()==null || temp.getCustomerEmail().equals("")){
					sendResult = SendEmailStatus.FAIL.getCode();
					msg = "邮箱未设置";
					failNum++;
				}else{
					sendResult = SendEmailStatus.FAIL.getCode();
					msg = "邮箱未验证";
					failNum++;
				}
				
			}else{//作废
				sendResult = SendEmailStatus.UNUSE.getCode();
				msg = "作废成功";
			}
			PaybackMonthSendEmail email = new PaybackMonthSendEmail();
			//插入发送记录
			if(temp.getId()==null){
				email.preInsert();
				email.setPaybackMonthId(temp.getPaybackMonthId());
				email.setSendEmailStatus(sendResult);
				email.setEmailType(temp.getEmailType());
				email.setContractCode(temp.getContractCode());
				email.setCustomerCode(temp.getCustomerCode());
				emailServeDao.insertSendEmail(email);
			}else{//更新发送状态
				email.preUpdate();
				email.setSendEmailStatus(sendResult);
				email.setId(temp.getId());
				emailServeDao.updateSendEmailStatus(email);
			}
			//插入历史
			insertEmailOpe(sendEmail.getSendEmailStatus(),email.getId(),sendResult,msg);
		}
		resultMap.put("totalNum", paybackList.size());
		resultMap.put("failNum", failNum);
		return resultMap;
	}
	
	/**
	 * 发送邮件
	 * @author 于飞
	 * @Create 2017年3月7日
	 * @param p
	 */
	
	public boolean sendEmail(PaybackMonthSendEmail p){
		String imgPre="https://adapter.prod.creditharmony.cn/chp-adapter-web/static/images/";
		String startImg = "<div style='width:60%;margin:0 auto'></br></br><img src='"+imgPre+"hjlogo.jpg'></br></br>"; 
		String endImg = "<div width='100%' align='right'><img src='"+imgPre+"ranmeizhiji.jpg'></div></div>";
		//标题
		String subject="";
		//内容
		String content="";
		//System.out.println(imgPath);
		//还款提醒邮件发送
		if(p.getEmailType().equals(EmailType.PAYBACKREMIND.getCode())){
			subject="【信和汇金】还款提醒";
			//银行账号后四位
			String endBankNo = "";
			if(p.getBankAccount()!=null && !p.getBankAccount().equals("")){
				endBankNo=p.getBankAccount().substring(p.getBankAccount().length()-4, p.getBankAccount().length());
			}
			if(p.getContractMonthRepayAmount()!=null){
				p.setContractMonthRepayAmount(p.getContractMonthRepayAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			content=startImg + "尊敬的"+p.getCustomerName()+"客户您好：</br>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本月"+p.getPaybackDay()+"日（零点前）是您的还款日，还款金额"+p.getContractMonthRepayAmount()
					+"元，请在还款日前确保您"+p.getBankName()+"尾号"+endBankNo+"账户余额充足或将款项存入出借人账户。"
							+ "存入他人的账户或交由他人代还导致的经济损失由您本人承担！（如您本月已收到过我司为您发送的还款提醒短信， 请忽略此邮件）"
					+"<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如有疑问可致电我司全国客服热线：400-090-1199。"
					+"<br>祝您生活愉快！" 
					+"<br><br>信和汇金客户服务中心" + endImg;
		//春节业务邮件提醒
		}else if(p.getEmailType().equals(EmailType.SPRINGBUSINESS.getCode())){
			subject="【信和汇金】春节期间业务安排提醒";
			content=startImg + "尊敬的"+p.getCustomerName()+"客户您好：</br>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我司于"+p.getStartTime()+"至"+p.getEndTime()+"期间正式放假，放假期间汇金客户还款日正常划扣，其他业务暂停办理。"
					+ "如您有业务需要办理，请提前到门店进行咨询处理。由此给您带来的不便敬请谅解！借此佳节之际，祝您及家人节日快乐，阖家幸福！" 
					+"</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如有疑问可致电我司全国客服热线：400-090-1199。"
					+"<br>祝您生活愉快！" 
					+"<br><br>信和汇金客户服务中心" + endImg;
		//元旦祝福
		}else if(p.getEmailType().equals(EmailType.NEWYEARBLESS.getCode())){
			subject="【信和汇金】祝您元旦快乐！";
			content=startImg + "尊敬的"+p.getCustomerName()+"客户您好：</br>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值此元旦佳节来临之际，衷心祝您元旦快乐，阖家幸福！"
					+"<br><br>信和汇金客户服务中心" 
					+"<br><br><div align='center' width='100%'><img src='"+imgPre+"newyear.gif'/></div></br></br>"
					+ endImg;
		//中秋邮件提醒
		}else if(p.getEmailType().equals(EmailType.ZHONGQIUBLESS.getCode())){
			subject="【信和汇金】祝您中秋节快乐！";
			content=startImg + "尊敬的"+p.getCustomerName()+"客户您好：</br>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值此中秋佳节来临之际，衷心祝您中秋快乐，阖家幸福！"
					+"<br><br>信和汇金客户服务中心" 
					+"<br><br><div align='center' width='100%'><img src='"+imgPre+"zhongqiu.gif'/></div></br></br>"
					+ endImg;
		//生日提醒
		}else if(p.getEmailType().equals(EmailType.BIRTHDAYBLESS.getCode())){
			subject="【信和汇金】祝您生日快乐！";
			content=startImg + "尊敬的"+p.getCustomerName()+"客户您好：</br>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;今天是您的生日，衷心祝福您生日快乐！感谢您一直以来对信和汇金的支持，我们将一如既往地为您提供优质服务！"
					+"<br><br>信和汇金客户服务中心" 
					+"<br><br><div align='center' width='100%'><img src='"+imgPre+"birthday.gif'/></div></br></br>"
					+ endImg;
		//国庆业务提醒
		}else if(p.getEmailType().equals(EmailType.NEWYEARBLESS.getCode())){
			subject="【信和汇金】国庆节期间业务安排提醒";
			content=startImg + "尊敬的"+p.getCustomerName()+"客户您好：</br>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我司于"+p.getStartTime()+"至"+p.getEndTime()+"期间正式放假，放假期间汇金客户还款日正常划扣，其他业务暂停办理。"
					+ "如您有业务需要办理，请提前到门店进行咨询处理。由此给您带来的不便敬请谅解！借此佳节之际，祝您及家人节日快乐，阖家幸福！" 
					+"</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如有疑问可致电我司全国客服热线：400-090-1199。"
					+"<br>祝您生活愉快！" 
					+"<br><br>信和汇金客户服务中心" + endImg;
		//春节祝福
		}else if(p.getEmailType().equals(EmailType.SPRINGBLESS.getCode())){
			subject="【信和汇金】祝您新春快乐！";
			content=startImg + "尊敬的"+p.getCustomerName()+"客户您好：</br>"+
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值此新春佳节来临之际，衷心祝您新春快乐，阖家幸福！"
			+"<br><br>信和汇金客户服务中心" 
			+"<br><br><div align='center' width='100%'><img src='"+imgPre+"spring.gif'/></div></br></br>"
			+ endImg;
		//端午祝福
		}else if(p.getEmailType().equals(EmailType.DUANWUBLESS.getCode())){
			subject="【信和汇金】祝您端午节安康！";
			content=startImg + "尊敬的"+p.getCustomerName()+"客户您好：</br>"+
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值此端午佳节来临之际，衷心祝您端午安康，阖家幸福！"
					+"<br><br>信和汇金客户服务中心" 
					+"<br><br><div align='center' width='100%'><img src='"+imgPre+"duanwu.gif'/></div></br></br>"
					+ endImg;
		}
		
		return sendCustomerEmail(p.getCustomerEmail(),subject,content);
	}
	
	/**
	 * 发送邮件
	 * @author 于飞
	 * @Create 2017年3月11日
	 * @param customerEmail
	 * @param subject
	 * @param content
	 * @return
	 */
	public boolean sendCustomerEmail(String customerEmail,String subject,String content){
		//发送邮件
		ClientPoxy service = new ClientPoxy(ServiceType.Type.SEND_MAIL); 
		MailInfo mailParam = new MailInfo(); 
		String[] toAddrArray = { customerEmail };
		if(customerEmail.equals("")){
			toAddrArray = cusEmailArray;
		}
		mailParam.setToAddrArray(toAddrArray); 
		mailParam.setDocType("1"); 
		mailParam.setSubject(subject); 
		mailParam.setContent(content);
		try {
			MailOutInfo out = (MailOutInfo) service.callService(mailParam);
			if("0000".equals(out.getRetCode())){
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	
	/**
	 * 邮件操作历史
	 * @author 于飞
	 * @Create 2017年3月8日
	 * @param status 1：发送  2：作废
	 * @param paybackMonthId 期供id
	 * @param result 1：成功 2：失败
	 */
	public void insertEmailOpe(String status,String emailId,String result,String msg){
		EmailOpe ope = new EmailOpe();
		ope.preInsert();
		ope.setEmailId(emailId);
		ope.setOperateTime(new Date());
		ope.setOperator(ope.getCreateBy());
		ope.setOperateCode(ope.getCreateBy());
		ope.setRemark(msg);
		if(status.equals(SendEmailStatus.SUCCESS.getCode()) && result.equals("1")){
			ope.setOperateStep("发送");
			ope.setOperateResult("成功");
		}else if(status.equals(SendEmailStatus.SUCCESS.getCode()) && result.equals("3")){
			ope.setOperateStep("发送");
			ope.setOperateResult("失败");
		}else{
			ope.setOperateStep("作废");
			ope.setOperateResult("成功");
		}
		loanStatusHisDao.insertEmailOpe(ope);
	}
	
	/**
	 * 根据状态删除邮件
	 * @author 于飞
	 * @Create 2017年4月17日
	 * @param email
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void deleteEmailByStatus(PaybackMonthSendEmail email){
		emailServeDao.deleteEmailByStatus(email);
	}
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void insertSendEmail(PaybackMonthSendEmail email){
		emailServeDao.insertSendEmail(email);
	}
	
	/**
	 * 虚拟账户发送邮件
	 * @author 于飞
	 * @Create 2017年5月11日
	 * @param emailType
	 */
	public void sendVirtualEmail(PaybackMonthSendEmail p){
		p.setCustomerName("鲁莹莹");
		p.setBankAccount("1111");
		p.setBankName("中国工商银行");
		p.setContractMonthRepayAmount(new BigDecimal("0"));
		p.setCustomerEmail("");
		//生日祝福只有10、18、25日发送邮件
		Calendar now =Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_MONTH);
		if(p.getEmailType().equals(EmailType.BIRTHDAYBLESS.getCode())){
			if(day==10 || day==18 || day==25){
				p.setPaybackDay(day);
				sendEmail(p);
			}
		//还款提醒提前三个工作日发送
		}else if(p.getEmailType().equals(EmailType.PAYBACKREMIND.getCode())){
			List<String> dateArray = emailServeDao.selectNowDayAfterDate(
					new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			if(dateArray.get(3)!=null && (dateArray.get(3).equals("10")
					|| dateArray.get(3).equals("18")
					|| dateArray.get(3).equals("25"))){
				p.setPaybackDay(Integer.parseInt(dateArray.get(2)));
				sendEmail(p);
			}
		}else{
			sendEmail(p);
		}
	}
}
