package com.creditharmony.loan.borrow.certification.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.BaseOutInfo;
import com.creditharmony.adapter.bean.in.thirdpay.PayCertificationSingleInBean;
import com.creditharmony.adapter.bean.in.thirdpay.PaySignReqSingleInBean;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.loan.borrow.certification.dao.BankDao;
import com.creditharmony.loan.borrow.certification.entity.LoanBank;

/**
 * 畅捷认证和签约接口的调用处理service
 * @Class Name KaLianCertifSignService
 * @author 朱静越
 * @Create In 2016年8月16日
 */
@Service
public class ChangJieCertifSignServiceTest {

	@Autowired
	private BankDao bankDao; 
	
	SimpleDateFormat  format = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat  format1 = new SimpleDateFormat("yyMMddHHmmss");
	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger(ChangJieCertifSignServiceTest.class);
	
	/**
	 * 畅捷认证签约处理，1.如果认证的结果为成功的，更新标识，调用签约接口，更新的时候，根据银行卡号进行更新；
	 * 2.获得签约的结果，进行签约标识的更新；
	 * 2016年8月16日
	 * By 朱静越
	 * @param cjDetail
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public PayCertificationSingleInBean changJieCertifSignService(PayCertificationSingleInBean cjDetail){
		ClientPoxy service = new ClientPoxy(ServiceType.Type.CJ_CERTIFICATION);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("畅捷认证接口===开始,流水号为：" + cjDetail.getTrSerialNo());
		// BaseOutInfo outInfo = (BaseOutInfo) service.callService(cjDetail);
		log.info("畅捷认证接口===结束,流水号为：" + cjDetail.getTrSerialNo());
		/*LoanBank loanBank = new LoanBank();
		loanBank.setAccNo(cjDetail.getAccountNo());
		loanBank.setTrSerialNo(cjDetail.getTrSerialNo());
		if ("0000".equals(outInfo.getRetCode())) {
			loanBank.setCjAuthen("1");
		}else {
			loanBank.setCjAuthen("2");
			log.info(outInfo.getRetMsg()+"实名认证流水号："+loanBank.getTrSerialNo());
		}
		log.debug("畅捷认证接口查询更新认证标识===开始,更新标识为：" + loanBank.getRealAuthen());
		bankDao.updateByAccNo(loanBank);
		log.debug("畅捷认证接口查询更新认证标识===结束,更新标识为：" + loanBank.getRealAuthen());
		
		if ("0000".equals(outInfo.getRetCode())) {
			ClientPoxy serviceSign = new ClientPoxy(ServiceType.Type.CJ_SIGNREQ);
			PaySignReqSingleInBean inParam = new PaySignReqSingleInBean();
			BeanUtils.copyProperties(cjDetail, inParam);
			inParam.setTrSerialNo(getFlowNo());
			inParam.setSubmitDate(new Date());
			inParam.setSn(getId());
			inParam.setProtocolNo(getFlowNo());
			inParam.setBeginDate(new Date());
			inParam.setEndDate(new Date());
			log.debug("畅捷调用签约接口===开始,流水号为：" + inParam.getTrSerialNo());
			 BaseOutInfo cyInfo = (BaseOutInfo) serviceSign.callService(inParam);
			log.debug("畅捷调用签约接口===结束,流水号为：" + inParam.getTrSerialNo());
			if ("0000".equals(cyInfo.getRetCode())) {
				loanBank.setCjSign("1");
			}else {
				loanBank.setCjSign("2");
				log.info(cyInfo.getRetMsg()+"签约流水号："+loanBank.getCjQyNo());
			}
			loanBank.setProtocolNo(inParam.getProtocolNo());
			loanBank.setCjQyNo(inParam.getTrSerialNo());
			log.debug("畅捷签约接口更新认证标识===结束,更新标识为：" + loanBank.getKlSign());
			bankDao.updateByAccNo(loanBank);
			log.debug("畅捷签约接口更新认证标识===结束,更新标识为：" + loanBank.getKlSign());
		}*/
		return cjDetail;
	}
	
	/**
	 * 随机产生指定长度的数据字符串
	 * @param length 指定长度
	 * @return 结果
	 */
     public String getId(){
		 UUID uuid= UUID.randomUUID();
     	 String batchNo=uuid.toString().replaceAll("-","");
      //batchNo=batchNo.substring(0, 16);
     	 return batchNo;
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
  			return "xinhe-chp"+format1.format(new Date())+getId2();
      }
	
}
