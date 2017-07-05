package com.creditharmony.loan.borrow.certification.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.creditharmony.adapter.bean.in.thirdpay.PayCertificationSingleInBean;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.fortune.type.OpenBankKL;
import com.creditharmony.core.loan.type.OperateType;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.loan.borrow.certification.dao.BankDao;
import com.creditharmony.loan.borrow.certification.entity.LoanBank;
import com.creditharmony.loan.borrow.certification.service.ChangJieCertifSignService;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.type.OpenBankCJ;
import com.creditharmony.loan.common.utils.FilterHelper;
/**
 * 使用多线程进行调用畅捷实名认证签约接口
 * @Class Name ChangjieCertifSignFace
 * @author 翁私
 * @Create In 2016年8月16日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/certification")
public class ChangJieCertifSignFace {

	@Autowired
	private BankDao bankDao; 
	@Autowired
	private ChangJieCertifSignService changJieCertifSignService;
	
	@Autowired
	private RepaymentDateService dateService;
	
	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger(ChangJieCertifSignFace.class);
	SimpleDateFormat  format = new SimpleDateFormat("yyMMddHHmmss");
	
	private final ExecutorService executor = Executors.newFixedThreadPool(5);
	/**
	 * 调用畅捷的认证接口，如果每次查询2000条数据，同时使用多线程调用认证接口，
	 * 2016年9月7日
	 * By 翁私
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	 */
	@RequestMapping(value = "ChangJieSignService")
	public String  ChangJieSignService(Model model, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map) throws InterruptedException, ExecutionException{
		int i =0;
		List<LoanBank> loanBankSignList = new ArrayList<LoanBank>();
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		prepareSearchCondition(paramMap);
		// 多线程声明
		CompletionService<PayCertificationSingleInBean> completionService = new ExecutorCompletionService<PayCertificationSingleInBean>(executor);
	/*	do {*/
		    log.info("畅捷实名认证开始"+format.format(new Date()));
			loanBankSignList = bankDao.queryCjList(paramMap);
			if (loanBankSignList != null && loanBankSignList.size() > 0) {
				List<PayCertificationSingleInBean> cjList = new ArrayList<PayCertificationSingleInBean>();
				for (LoanBank bank : loanBankSignList) {
					PayCertificationSingleInBean cjInfo = new PayCertificationSingleInBean();
					 String bankFullName = null;
					 bankFullName = OpenBankCJ.getOpenBank(bank.getBankFullName());
					 cjInfo.setTrSerialNo(getFlowNo());
					 cjInfo.setSubmitDate(new Date());
					 cjInfo.setSn(getFlowNo());
					 cjInfo.setBankGeneralName(bankFullName);
					 cjInfo.setBankName(bankFullName);
					 cjInfo.setBankCode(bank.getBankCode());
					 cjInfo.setAccountType("00");
					 cjInfo.setAccountName(bank.getAccName());
					 cjInfo.setAccountNo(bank.getAccNo());
					 cjInfo.setIdType(bank.getCertType());
					 //BeanUtils.copyProperties(bank, cjInfo);
					 cjInfo.setId(bank.getCertNo());
					 cjInfo.setTel(bank.getPhone());
					 cjList.add(cjInfo);
				}
				for (final PayCertificationSingleInBean cjIn : cjList) {
					try {
						completionService.submit(new Callable<PayCertificationSingleInBean>() {
							public PayCertificationSingleInBean call() {
								PayCertificationSingleInBean cjDealIn = null;
								try {
									log.debug("多线程调用认证接口,流水号为：" +  cjIn.getTrSerialNo());
									cjDealIn = changJieCertifSignService.changJieCertifSignService(cjIn);
								} catch(Exception e) {
									log.error("实时调用畅捷接口发生异常,请求流水号为:" +  cjIn.getTrSerialNo());
									e.printStackTrace();
								}
								return cjDealIn;
							}
						});
					} catch (Exception e) {
						log.error("实时调用畅捷接口发生异常,请求流水号为:" + cjIn.getTrSerialNo());
					}
				}
				
				for(int j =0; j< cjList.size(); j++){
					Future<PayCertificationSingleInBean> future = completionService.take();
					PayCertificationSingleInBean deductReqEntity = future.get();
					log.debug("实时划扣结果返回，请求ID为：" + deductReqEntity.getTrSerialNo());
				}
			}
			log.info("畅捷实名认证结束"+format.format(new Date()));
			i++;
		/*} while (ListUtils.isNotEmptyList(loanBankSignList));*/
	     model.addAttribute("complete", "完成");
		return "borrow/certification/complete";
			
	}
	
	/**
	 * 讲系统的证件类型转为上传的证件类型
	 * 2016年3月14日
	 * By 翁私
	 * @param cum
	 * @return
	 */
	public String changeNum(String cum){
		if(cum == null || "".equals(cum)){
			return "";
		}
		//0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7. 临时身份证,8. 外国人居留证,9. 警官证, X.其他证件
		if(cum.equals(CertificateType.SFZ.getCode())){
			return "ZR01";
		}else if(cum.equals(CertificateType.JGZ.getCode())){
			return "ZR04";
		}else if(cum.equals(CertificateType.HZ.getCode())){
			return "ZR13";
		}else if(cum.equals(CertificateType.HKB.getCode())){
			return "ZR03";
		}else if(cum.equals(CertificateType.GAJMLWNDTXZ.getCode())){
			return "ZR09";
		}
		return "";
	}
	
	 /**
 	 * 随机产生指定长度的数据字符串
 	 * @param length 指定长度
 	 * @return 结果
 	 */
      public String getId2(){
 		 UUID uuid= UUID.randomUUID();
      	 String batchNo=uuid.toString().replaceAll("-","");
         batchNo=batchNo.substring(0, 10);
      	 return batchNo;
 	  }
      
      /**
       * 获得流水号
       * @param no
       * @return
       */
      public String getFlowNo(){
  			return "xinhe-chp"+format.format(new Date())+getId2();
      }
 
     public static void main(String[] args){
    	 
    	 
    	String  bankFullName = OpenBankKL.getOpenBank("403");
    	System.out.print(bankFullName);
     }
     
     @RequestMapping(value = "goChangJieCertifSign")
     public String goChangJieCertifSign(Model model, HttpServletRequest request,
 			HttpServletResponse response, Map<String, Object> map){
    	    // 查询还款日
	 		List<GlBill> dayList = dateService.getRepaymentDate();
	 		model.addAttribute("dayList", dayList);
	    	return "borrow/certification/changJieCertifSign";
     }
     
     @RequestMapping(value = "queryCount")
     @ResponseBody
     public String  queryCount(Model model, HttpServletRequest request,
 			HttpServletResponse response,@RequestParam  Map<String, Object> map){
    	 prepareSearchCondition(map);
    	 String counts = bankDao.queryCount(map);
    	 return counts;
    	 
     }
     
     
     /**
 	 * 封装查询参数 2016年12月21
 	 * 
 	 * @param paramMap
 	 */
 	private void prepareSearchCondition(Map<String, Object> paramMap) {
 		
 		String queryRule = (String) paramMap.get("queryRule");
 		String overdueDays = (String) paramMap.get("overdueDays");
 		
 		if(!ObjectHelper.isEmpty(queryRule)){
 			if(!ObjectHelper.isEmpty(overdueDays)){
 				String queryoverdueDaysString;
 				if(queryRule.equals("0")){
 					 queryoverdueDaysString = "< cast("+overdueDays+" as numeric)";
 				}else {
 					 queryoverdueDaysString = ">= cast("+overdueDays+" as numeric)";
 				}
 				paramMap.put("queryoverdueDaysString", queryoverdueDaysString);
 			}
 		}
 	}
 	
 	
 	 @RequestMapping(value = "goChangJieCertifSignByCode")
     public String goChangJieCertifSignByCode(Model model, HttpServletRequest request,
 			HttpServletResponse response, Map<String, Object> map){
    	    // 查询还款日
	 		List<GlBill> dayList = dateService.getRepaymentDate();
	 		model.addAttribute("dayList", dayList);
	    	return "borrow/certification/changjiecertifsigncode";
     }
 	
 	/**
	 * 调用畅捷的认证接口，用合同号进行查询
	 * 2016年9月7日
	 * By 翁私
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	 */
	@RequestMapping(value = "ChangJieSignByContractCode")
	public String  ChangJieSignByContractCode(Model model, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map) throws InterruptedException, ExecutionException{
		List<LoanBank> loanBankSignList = new ArrayList<LoanBank>();
		Map<String, Object> paramMap = WebUtils.getParametersStartingWith(
				request, "search_");
		// 多线程声明
		String contractCode = (String) paramMap.get("contractCode");
		if (StringUtils.isNotEmpty(contractCode) && contractCode.split(",").length > 0) {
			 // 有勾选数据,id参数添加,存入List<String>
			 contractCode = contractCode.replaceAll("\\s*", "");   
			 paramMap.put("contractCode", Arrays.asList(contractCode.split(",")));
		}else{
			  model.addAttribute("complete", "合同号为空！！！");
			  return "borrow/certification/complete";
		}
		CompletionService<PayCertificationSingleInBean> completionService = new ExecutorCompletionService<PayCertificationSingleInBean>(executor);
	/*	do {*/
		    log.info("畅捷实名认证开始"+format.format(new Date()));
			loanBankSignList = bankDao.queryCjListCode(paramMap);
			if (loanBankSignList != null && loanBankSignList.size() > 0) {
				List<PayCertificationSingleInBean> cjList = new ArrayList<PayCertificationSingleInBean>();
				for (LoanBank bank : loanBankSignList) {
					PayCertificationSingleInBean cjInfo = new PayCertificationSingleInBean();
					 String bankFullName = null;
					 bankFullName = OpenBankCJ.getOpenBank(bank.getBankFullName());
					 cjInfo.setTrSerialNo(getFlowNo());
					 cjInfo.setSubmitDate(new Date());
					 cjInfo.setSn(getFlowNo());
					 cjInfo.setBankGeneralName(bankFullName);
					 cjInfo.setBankName(bankFullName);
					 cjInfo.setBankCode(bank.getBankCode());
					 cjInfo.setAccountType("00");
					 cjInfo.setAccountName(bank.getAccName());
					 cjInfo.setAccountNo(bank.getAccNo());
					 cjInfo.setIdType(bank.getCertType());
					 //BeanUtils.copyProperties(bank, cjInfo);
					 cjInfo.setId(bank.getCertNo());
					 cjInfo.setTel(bank.getPhone());
					 cjList.add(cjInfo);
				}
				for (final PayCertificationSingleInBean cjIn : cjList) {
					try {
						completionService.submit(new Callable<PayCertificationSingleInBean>() {
							public PayCertificationSingleInBean call() {
								PayCertificationSingleInBean cjDealIn = null;
								try {
									log.debug("多线程调用认证接口,流水号为：" +  cjIn.getTrSerialNo());
									cjDealIn = changJieCertifSignService.changJieCertifSignService(cjIn);
								} catch(Exception e) {
									log.error("实时调用畅捷接口发生异常,请求流水号为:" +  cjIn.getTrSerialNo());
									e.printStackTrace();
								}
								return cjDealIn;
							}
						});
					} catch (Exception e) {
						log.error("实时调用畅捷接口发生异常,请求流水号为:" + cjIn.getTrSerialNo());
					}
				}
				
				for(int j =0; j< cjList.size(); j++){
					Future<PayCertificationSingleInBean> future = completionService.take();
					PayCertificationSingleInBean deductReqEntity = future.get();
					log.debug("实时划扣结果返回，请求ID为：" + deductReqEntity.getTrSerialNo());
				}
			}
			log.info("畅捷实名认证结束"+format.format(new Date()));
		/*} while (ListUtils.isNotEmptyList(loanBankSignList));*/
	     model.addAttribute("complete", "完成");
		return "borrow/certification/complete";
			
	}
}
