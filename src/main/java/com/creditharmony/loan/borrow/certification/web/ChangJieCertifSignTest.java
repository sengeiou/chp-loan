package com.creditharmony.loan.borrow.certification.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.fortune.type.OpenBankKL;
import com.creditharmony.loan.borrow.certification.dao.BankDao;
import com.creditharmony.loan.borrow.certification.entity.LoanBank;
import com.creditharmony.loan.borrow.certification.service.ChangJieCertifSignServiceTest;
import com.creditharmony.loan.borrow.certification.service.OnOff;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.type.OpenBankCJ;
/**
 * 使用多线程进行调用畅捷实名认证签约接口
 * @Class Name ChangjieCertifSignFace
 * @author 翁私
 * @Create In 2016年8月16日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/certifications")
public class ChangJieCertifSignTest {

	@Autowired
	private BankDao bankDao; 
	@Autowired
	private ChangJieCertifSignServiceTest changJieCertifSignService;
	
	@Autowired
	private RepaymentDateService dateService;
	
	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger(ChangJieCertifSignTest.class);
	SimpleDateFormat  format = new SimpleDateFormat("yyMMddHHmmss");
	
	 private ThreadPoolExecutor pool = new ThreadPoolExecutor(    
             4,    
             4,    
             0,    
             TimeUnit.MINUTES,    
             new LinkedBlockingQueue<Runnable>()); 
	 
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
		// 多线程声明
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
				for ( PayCertificationSingleInBean cjIn : cjList) {
						 pool.execute(new TaskThreadPool(cjIn));  
				}
			}
			log.info("畅捷实名认证结束"+format.format(new Date()));
			i++;
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
     public String getId(){
		 UUID uuid= UUID.randomUUID();
     	 String batchNo=uuid.toString().replaceAll("-","");
        batchNo=batchNo.substring(0, 15);
     	 return batchNo;
	  }
     
     /**
      * 获得流水号
      * @param no
      * @return
      */
     public String getFlowNo(){
 			return "xinhe"+format.format(new Date())+getId();
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
    	 String counts = bankDao.queryCount(map);
    	 return counts;
    	 
     }
     @RequestMapping(value = "stopSms")
     @ResponseBody
     public String stopSms(String flag){
    	 if("1".equals(flag)){
    		 OnOff.flag = false;
    	 }else{
    		 OnOff.flag = true;
    	 }
    	 return "success";
    	 
     }
     
     class TaskThreadPool implements Runnable
     {
         private PayCertificationSingleInBean bean;

         public TaskThreadPool(PayCertificationSingleInBean bean)
         {
             this.bean = bean;
         }
         public void run()
         {
        	 if(OnOff.flag){
        	 changJieCertifSignService.changJieCertifSignService(bean);
        	 }else{
        		 log.info("畅捷实名认证停止========="+format.format(new Date()));
        	 }
        	 System.out.println(Thread.currentThread() + " index:" + bean);
         }
     }
}
