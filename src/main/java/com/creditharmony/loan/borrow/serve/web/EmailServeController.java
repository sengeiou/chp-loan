package com.creditharmony.loan.borrow.serve.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.excel.export.ExcelUtil;
import com.creditharmony.core.loan.type.EmailType;
import com.creditharmony.core.loan.type.SendEmailStatus;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.serve.entity.PaybackMonthSendEmail;
import com.creditharmony.loan.borrow.serve.service.EmailServeService;

/**
 * 汇金邮件管理
 * 
 * @Class Name EmailServeController
 * @author 于飞
 * @Create In 2017年3月2日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/borrow/serve/emailServe")
public class EmailServeController extends BaseController {
	
	@Autowired
	private EmailServeService emailService;
	
    /**
     * 还款提醒列表
     * @author 于飞
     * @Create 2017年3月2日
     * @param model
     * @param request
     * @param response
     * @param contractFileSendView
     * @return
     */
	@RequestMapping(value = "/paybackRemindList")
	public String paybackRemindList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			PaybackMonthSendEmail sendEmail) {
		//门店id分割
		if(sendEmail.getStoreId()!=null && !sendEmail.getStoreId().equals("")){
			sendEmail.setStoreId(appendString(sendEmail.getStoreId()));
		}
		//发送状态,查找未发送数据
		sendEmail.setSendEmailStatus("0");
		/*if(sendEmail.getSendEmailStatus()==null || sendEmail.getSendEmailStatus().equals("")){
			sendEmail.setSendEmailStatus("-1");
		}else if(!sendEmail.getSendEmailStatus().equals(SendEmailStatus.UNSEND.getCode())){
			sendEmail.setSendEmailStatus(appendString(sendEmail.getSendEmailStatus()));
		}*/
		sendEmail.setEmailType(EmailType.PAYBACKREMIND.getCode());
		Page<PaybackMonthSendEmail> page = emailService.findEmailList(
				new Page<PaybackMonthSendEmail>(request, response),sendEmail);
		//将发送状态设置为搜索状态
		if(sendEmail.getSendEmailStatus().equals("-1")){
			sendEmail.setSendEmailStatus("");
		}else if(sendEmail.getSendEmailStatus()==null){
			sendEmail.setSendEmailStatus(SendEmailStatus.UNSEND.getCode());
		}else{
			sendEmail.setSendEmailStatus(sendEmail.getSendEmailStatus().replace("'", ""));
		}
		//门店id分割
		if(sendEmail.getStoreId()!=null && !sendEmail.getStoreId().equals("")){
			sendEmail.setStoreId(sendEmail.getStoreId().replace("'", ""));
		}
		model.addAttribute("page", page);
		model.addAttribute("sendEmail", sendEmail);
		return "borrow/serve/paybackRemindList";
	}
	
	/**
     * 已发送邮件列表
     * @author 于飞
     * @Create 2017年3月2日
     * @param model
     * @param request
     * @param response
     * @param contractFileSendView
     * @return
     */
	@RequestMapping(value = "/sendList")
	public String sendList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			PaybackMonthSendEmail sendEmail) {
		//发送状态,查找发送成功和作废的数据
		if(sendEmail.getSendEmailStatus()==null || sendEmail.getSendEmailStatus().equals("")){
			sendEmail.setSendEmailStatus("'"+SendEmailStatus.SUCCESS.getCode()+"','"+SendEmailStatus.UNUSE.getCode()+"'");
		}else{
			sendEmail.setSendEmailStatus(appendString(sendEmail.getSendEmailStatus()));
		}
		//门店id分割
		if(sendEmail.getStoreId()!=null && !sendEmail.getStoreId().equals("")){
			sendEmail.setStoreId(appendString(sendEmail.getStoreId()));
		}
		//邮件类型
		if(sendEmail.getEmailType()!=null && !sendEmail.getEmailType().equals("")){
			sendEmail.setEmailType(appendString(sendEmail.getEmailType()));
		}
		//发送日期
		if(sendEmail.getModifyTime()!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			Date date=sendEmail.getModifyTime();  
			String str=sdf.format(date);
			sendEmail.setModifyTimeStr(str);
		}
		Page<PaybackMonthSendEmail> page = emailService.findSendEmailList(
				new Page<PaybackMonthSendEmail>(request, response),sendEmail);
		//将发送状态设置为搜索状态
		if(sendEmail.getSendEmailStatus().equals("'"+SendEmailStatus.SUCCESS.getCode()+"','"+SendEmailStatus.FAIL.getCode()+"'")){
			sendEmail.setSendEmailStatus(null);
		}else{
			sendEmail.setSendEmailStatus(sendEmail.getSendEmailStatus().replace("'", ""));
		}
		if(sendEmail.getStoreId()!=null && !sendEmail.getStoreId().equals("")){
			sendEmail.setStoreId(sendEmail.getStoreId().replace("'", ""));
		}
		//邮件类型
		if(sendEmail.getEmailType()!=null && !sendEmail.getEmailType().equals("")){
			sendEmail.setEmailType(sendEmail.getEmailType().replace("'", ""));
		}
		model.addAttribute("page", page);
		model.addAttribute("sendEmail", sendEmail);
		return "borrow/serve/sendList";
	}
	
	/**
	 * 跳转节假日提醒列表
	 * @author 于飞
	 * @Create 2017年3月10日
	 * @param model
	 * @param request
	 * @param response
	 * @param sendEmail
	 * @return
	 */
	@RequestMapping(value = "/holidayList")
	public String holidayList(Model model, HttpServletRequest request,
			HttpServletResponse response){
		return "borrow/serve/holidayList";
	}
	/**
	 * 获取节假日提醒列表
	 * @author 于飞
	 * @Create 2017年3月10日
	 * @param model
	 * @param request
	 * @param response
	 * @param sendEmail
	 * @return
	 */
	@RequestMapping(value = "/getHolidayList")
	public String getHolidayList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			PaybackMonthSendEmail sendEmail) {
		//如果借款状态为空，则查询借款状态除结清、提前结清的所有数据
		if(sendEmail.getLoanStatus()==null || sendEmail.getLoanStatus().equals("")){
			sendEmail.setLoanStatus("0");
		}else{
			sendEmail.setLoanStatus(appendString(sendEmail.getLoanStatus()));
		}
		//合同编号
		if(sendEmail.getContractCode()!=null && !sendEmail.getContractCode().equals("")){
			sendEmail.setContractCode(appendString(sendEmail.getContractCode()));
		}
		//门店id分割
		if(sendEmail.getStoreId()!=null && !sendEmail.getStoreId().equals("")){
			sendEmail.setStoreId(appendString(sendEmail.getStoreId()));
		}
		//排序
		sendEmail.setOrderBy("contractCode");
		Page<PaybackMonthSendEmail> page = emailService.findHolidayList(
				new Page<PaybackMonthSendEmail>(request, response),sendEmail);
		//去掉引号
		if(sendEmail.getContractCode()!=null && !sendEmail.getContractCode().equals("")){
			sendEmail.setContractCode(sendEmail.getContractCode().replace("'", ""));
		}
		if(sendEmail.getLoanStatus()!=null && sendEmail.getLoanStatus().equals("0")){
			sendEmail.setLoanStatus(null);
		}else{
			sendEmail.setLoanStatus(sendEmail.getLoanStatus().replace("'", ""));
		}
		if(sendEmail.getStoreId()!=null && !sendEmail.getStoreId().equals("")){
			sendEmail.setStoreId(sendEmail.getStoreId().replace("'", ""));
		}
		model.addAttribute("page", page);
		model.addAttribute("sendEmail", sendEmail);
		return "borrow/serve/holidayList";
	}
	/**
     * 邮件发送失败列表
     * @author 于飞
     * @Create 2017年3月2日
     * @param model
     * @param request
     * @param response
     * @param contractFileSendView
     * @return
     */
	@RequestMapping(value = "/sendFailList")
	public String sendFailList(Model model, HttpServletRequest request,
			HttpServletResponse response,
			PaybackMonthSendEmail sendEmail) {
		//发送失败的数据
		sendEmail.setSendEmailStatus("'"+SendEmailStatus.FAIL.getCode()+"'");
		//门店id分割
		if(sendEmail.getStoreId()!=null && !sendEmail.getStoreId().equals("")){
			sendEmail.setStoreId(appendString(sendEmail.getStoreId()));
		}
		//邮件类型
		if(sendEmail.getEmailType()!=null && !sendEmail.getEmailType().equals("")){
			sendEmail.setEmailType(appendString(sendEmail.getEmailType()));
		}
		Page<PaybackMonthSendEmail> page = emailService.findSendEmailList(
				new Page<PaybackMonthSendEmail>(request, response),sendEmail);
		if(sendEmail.getStoreId()!=null && !sendEmail.getStoreId().equals("")){
			sendEmail.setStoreId(sendEmail.getStoreId().replace("'", ""));
		}
		if(sendEmail.getEmailType()!=null && !sendEmail.getEmailType().equals("")){
			sendEmail.setEmailType(sendEmail.getEmailType().replace("'", ""));
		}
		model.addAttribute("page", page);
		model.addAttribute("sendEmail", sendEmail);
		return "borrow/serve/sendFailList";
	}
	/**
	 * 导出数据
	 * @author 于飞
	 * @Create 2017年3月2日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportExl")
	public void exportExl(HttpServletRequest request, HttpServletResponse response
			, String ids,PaybackMonthSendEmail sendEmail){
		try{
			if (StringUtils.isNotEmpty(ids)) {
				if(sendEmail.getExportType().equals("1")){
					sendEmail.setPaybackMonthId(appendString(ids));
				}else if(sendEmail.getExportType().equals("2") || sendEmail.getExportType().equals("3")){
					sendEmail.setId(appendString(ids));
				}else{
					sendEmail.setContractCode(appendString(ids));
				}
			}
			//门店id分割
			if(sendEmail.getStoreId()!=null && !sendEmail.getStoreId().equals("")){
				sendEmail.setStoreId(appendString(sendEmail.getStoreId()));
			}
			if(sendEmail.getEmailType()!=null && !sendEmail.getEmailType().equals("")){
				sendEmail.setEmailType(appendString(sendEmail.getEmailType()));
			}
			String fileName="";
			//还款提醒导出未发送和发送失败数据
			if(sendEmail.getExportType().equals("1")){
				fileName="还款提醒列表";
				/*if(sendEmail.getSendEmailStatus()==null || sendEmail.getSendEmailStatus().equals("")){
					sendEmail.setSendEmailStatus("-1");
				}else if(!sendEmail.getSendEmailStatus().equals(SendEmailStatus.UNSEND.getCode())){
					sendEmail.setSendEmailStatus(appendString(sendEmail.getSendEmailStatus()));
				}*/
				sendEmail.setSendEmailStatus("0");
				sendEmail.setEmailType(EmailType.PAYBACKREMIND.getCode());
			//已发送邮件导出发送成功和发送失败数据
			}else if(sendEmail.getExportType().equals("2")){
				fileName="已发送邮件列表";
				if(sendEmail.getSendEmailStatus()==null || sendEmail.getSendEmailStatus().equals("")){
					sendEmail.setSendEmailStatus("'"+SendEmailStatus.SUCCESS.getCode()+"','"+SendEmailStatus.UNUSE.getCode()+"'");
				}else{
					sendEmail.setSendEmailStatus(appendString(sendEmail.getSendEmailStatus()));
				}
			//发送失败导出
			}else if(sendEmail.getExportType().equals("3")){
				fileName="邮件发送失败列表";
				sendEmail.setSendEmailStatus("'"+SendEmailStatus.FAIL.getCode()+"'");
			}else if(sendEmail.getExportType().equals("4")){
				fileName="节假日提醒列表";
				//如果借款状态为空，则查询借款状态除结清、提前结清的所有数据
				if(sendEmail.getLoanStatus()==null || sendEmail.getLoanStatus().equals("")){
					sendEmail.setLoanStatus("0");
				}else{
					sendEmail.setLoanStatus(appendString(sendEmail.getLoanStatus()));
				}
				/*//合同编号
				if(sendEmail.getContractCode()!=null && !sendEmail.getContractCode().equals("")){
					sendEmail.setContractCode(appendString(sendEmail.getContractCode()));
				}*/
			}
			List<PaybackMonthSendEmail> list = new ArrayList<PaybackMonthSendEmail>();
			if(sendEmail.getExportType().equals("1")){
				list = emailService.findEmailList(sendEmail);
			}else if(sendEmail.getExportType().equals("2") || sendEmail.getExportType().equals("3")){
				list = emailService.findSendEmailList(sendEmail);
			}else {
				list = emailService.findHolidayList(sendEmail);
			}
			for (int i=0;i<list.size();i++) {
				PaybackMonthSendEmail pt = list.get(i);
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
				//是否无纸化
				if(StringUtils.isNotEmpty(pt.getPaperlessFlag())){
					if(pt.getPaperlessFlag().equals("1")){
						pt.setPaperlessFlag("是");
					}else{
						pt.setPaperlessFlag("否");
					}
				}
				//邮件类型
				if(StringUtils.isNotEmpty(pt.getEmailType())){
					String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
							"jk_email_type", pt.getEmailType());
					pt.setEmailTypeLabel(dictLoanStatusLabel);
				}
				if(pt.getAuditAmount()!=null)
					pt.setAuditAmount(pt.getAuditAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
				if(pt.getContractMonthRepayAmount()!=null)
					pt.setContractMonthRepayAmount(pt.getContractMonthRepayAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
				if(pt.getContractAmount()!=null)
					pt.setContractAmount(pt.getContractAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
				//发送时间赋值startTime
				if(pt.getModifyTime()!=null){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String d = df.format(pt.getModifyTime());
					pt.setStartTime(d);
					//pt.setModifyTime(df.parse(d));
				}
				list.set(i, pt);
			}
			ExcelUtil excelUtil = ExcelUtil.getInstance();
			//还款提醒导出
			if(sendEmail.getExportType().equals("1")){
				String[] titleArray={"客户名称","合同编号","门店名称","借款状态","放款金额","月还金额","合同金额","还款日","邮箱","发送状态"};
				String[] methodName={"customerName","contractCode","storeName","loanStatusLabel"
						,"auditAmount","contractMonthRepayAmount","contractAmount","paybackDay"
						,"customerEmail","sendEmailStatusLabel"};
				excelUtil.exportObj3Excel(titleArray,methodName,fileName
						,list,PaybackMonthSendEmail.class,false,response);
				//已发送导出
			}else if(sendEmail.getExportType().equals("2") || sendEmail.getExportType().equals("3")){
				String[] titleArray={"客户名称","合同编号","门店名称","借款状态","放款金额","月还金额","合同金额","还款日","邮箱","发送时间","邮件名称","发送状态"};
				String[] methodName={"customerName","contractCode","storeName","loanStatusLabel"
						,"auditAmount","contractMonthRepayAmount","contractAmount","paybackDay"
						,"customerEmail","startTime","emailTypeLabel","sendEmailStatusLabel"};
				excelUtil.exportObj3Excel(titleArray,methodName,fileName
						,list,PaybackMonthSendEmail.class,false,response);
			}else{
				String[] titleArray={"客户名称","合同编号","门店名称","借款状态","放款金额","月还金额","合同金额","还款日","是否无纸化","邮箱"};
				String[] methodName={"customerName","contractCode","storeName","loanStatusLabel"
						,"auditAmount","contractMonthRepayAmount","contractAmount","paybackDay"
						,"paperlessFlag","customerEmail"};
				excelUtil.exportObj3Excel(titleArray,methodName,fileName
						,list,PaybackMonthSendEmail.class,false,response);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("--文件导出错误"+e.getMessage());
		}
	}
	
	/**
	 * 更新发送状态
	 * @author 于飞
	 * @Create 2017年3月7日
	 * @param request
	 * @param response
	 * @param ids
	 * @param status
	 * @param type 1:根据期供id  2：根据已发送的邮件id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateSendEmailStatus")
	public String updateSendEmailStatus(HttpServletRequest request, HttpServletResponse response
			,String ids,String idType,PaybackMonthSendEmail sendEmail){
		Map<String,Object> map = new HashMap<String,Object>();
		if(ids!=null && !"".equals(ids)){
			ids = appendString(ids);
		}
		if(sendEmail.getStoreId()!=null && !"".equals(sendEmail.getStoreId())){
			sendEmail.setStoreId(appendString(sendEmail.getStoreId()));
		}
		if(sendEmail.getLoanStatus()!=null && !"".equals(sendEmail.getLoanStatus())){
			sendEmail.setLoanStatus(appendString(sendEmail.getLoanStatus()));
		}
		if(sendEmail.getEmailType()!=null && !sendEmail.getEmailType().equals("")){
			sendEmail.setEmailType(appendString(sendEmail.getEmailType()));
		}
		map = emailService.insertSendEmail(ids,idType,sendEmail);
		return JsonMapper.nonDefaultMapper().toJson(map);
	}
	
	/**
	 * 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public String appendString(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (int i =0;i<idArray.length;i++){
			String id  = idArray[i];
			if (i == 0){
				parameter.append("'" +id +"'");
			}else {
				parameter.append(",'" +id + "'");
			}
		}
		return parameter.toString();
	}
		
}
