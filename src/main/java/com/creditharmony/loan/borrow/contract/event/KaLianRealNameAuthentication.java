package com.creditharmony.loan.borrow.contract.event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.bean.in.thirdpay.KalianCertificationSingleInBean;
import com.creditharmony.adapter.bean.in.thirdpay.KalianSignReqSingleInBean;
import com.creditharmony.adapter.bean.out.thirdpay.KalianCertificationSingleOutBean;
import com.creditharmony.adapter.bean.out.thirdpay.KalianSignReqSingleOutBean;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.fortune.type.OpenBankKL;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.service.KaLianBankService;
/**
 *上传合同确认的时候 卡联实名认证 
 * @author 翁私
 *
 */
@Service("li_hj_RealName_Authentication")
public class KaLianRealNameAuthentication extends BaseService implements ExEvent {
	
	@Autowired
	private KaLianBankService kaLianBankService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	SimpleDateFormat  format = new SimpleDateFormat("yyyyMMdd");
	@Override
	public void invoke(WorkItemView workItem) {
		 ContractBusiView contractBusiView = (ContractBusiView)workItem.getBv();
		 String operType = contractBusiView.getOperType();
		    /**
		     *正常提交、退回时operType 为空 
		     *更新渠道标识时 operType为0
		     *更新冻结标识时operType为1 
		     *正常提交、退回才走下面的逻辑 
		     */
	        if(!YESNO.NO.getCode().equals(operType) && !YESNO.YES.getCode().equals(operType)){
	        	Contract contract = contractBusiView.getContract();
	            String dictOperateResult = contractBusiView.getDictOperateResult();
	            String stepName = workItem.getStepName();
	            String dictCheckStatus = contractBusiView.getDictLoanStatusCode();
	            contract.setDictCheckStatus(dictCheckStatus);
	            if(!ObjectHelper.isEmpty(contract)){
	                String loanCode = contract.getLoanCode();
	                if(ContractConstant.CUST_SERVICE_SIGN.equals(stepName)){
	                	 if(ContractConstant.CONTRACT_SUBMIT.equals(dictOperateResult)){ 
	                		   if(!ObjectHelper.isEmpty(loanCode)){
	                			   ClientPoxy service = new ClientPoxy(ServiceType.Type.KL_CERTIFICATION_SINGLE);
	                			    KalianCertificationSingleInBean detail = new KalianCertificationSingleInBean();
		                	        List<LoanBank>  list = kaLianBankService.getBankList(loanCode);
		                	        if(list != null && list.size()>0){
		                	        	LoanBank bank = list.get(0);
		                	        	String bankName = null;
		                	        	bankName = OpenBankKL.getOpenBankByKL(bank.getBankName());
		                	        	detail.setAccName(bank.getBankAccountName());
		                	        	detail.setAccBank(bankName);
		                	            detail.setCardType(bank.getCardType());
		                	            detail.setAccNo(bank.getBankAccount());
		                	            detail.setBankCode(bank.getBankNo());
		                	            detail.setBankName(bankName);
		                	            detail.setCertType(changeNum(bank.getIdType()));
		                	            detail.setCertNo(bank.getIdNo());
		                	            detail.setPhone(bank.getMobile());
			                	        detail.setTrSerialNo(randomNumStr());
			                	        detail.setSubmitDate(new Date());
			                	        try {
			                	        	  logger.info("【上传合同】卡联实名认证开始！流水号："+detail.getTrSerialNo());
			                	        	  KalianCertificationSingleOutBean  outInfo = (KalianCertificationSingleOutBean ) service.callService(detail);
			                	             // 如果提交成功则 更新流水号 提交日期 和实名认证状态
			                	             if("0000".equals(outInfo.getRetCode())){
			                	            	LoanBank bankquery = new LoanBank();
			                	            	bankquery.setTrSerialNo(detail.getTrSerialNo());
			                	            	bankquery.setTransDate(new Date());
			                	            	bankquery.setRealAuthen("1");
			                	            	bankquery.setLoanCode(loanCode);
			                	            	kaLianBankService.updateBankByLoanCode(bankquery);
			                	            	logger.info("【上传合同】 卡联实名认证结束！流水号："+detail.getTrSerialNo());
			                	            	ClientPoxy kaLiservice = new ClientPoxy(ServiceType.Type.KL_SIGNREQ_SINGLE);
			                	                KalianSignReqSingleInBean inParam = new KalianSignReqSingleInBean();
			                	                inParam.setTrSerialNo(randomNumStr());
			                	                inParam.setSubmitDate(new Date());
			                	                inParam.setAccName(detail.getAccName());
			                	                inParam.setCardType("0");
			                	                inParam.setAccNo(detail.getAccNo());
			                	                inParam.setBankCode(detail.getBankCode());
			                	                inParam.setBankName(detail.getBankName());
			                	                inParam.setCertType(detail.getCertType());
			                	                inParam.setCertNo(detail.getCertNo());
			                	                inParam.setPhone(detail.getPhone());
			                	                inParam.setBeginDate(format.format(new Date()));
			                	                inParam.setEndDate("20170701");
			                	            	logger.info("【上传合同】 卡联签约开始！流水号："+inParam.getTrSerialNo());
			                	                     // 获取开始时间
			                	                   KalianSignReqSingleOutBean siInfo = (KalianSignReqSingleOutBean) kaLiservice.callService(inParam);
			                	                    if("0000".equals(siInfo.getRetCode())){
			                	                    	LoanBank bankSikl  = new LoanBank();
			                	                    	bankSikl.setTrSerialNo(detail.getTrSerialNo());
			                	                    	bankSikl.setTransDate(new Date());
			                	                    	bankSikl.setKlSign("1");
			                	                    	bankSikl.setLoanCode(loanCode);
					                	            	kaLianBankService.updateBankByLoanCode(bankSikl);
			                	                      }else{
			                	                    	LoanBank bankSikl  = new LoanBank();
			                	                    	bankSikl.setTrSerialNo(detail.getTrSerialNo());
			                	                    	bankSikl.setTransDate(new Date());
			                	                    	bankSikl.setKlSign("2");
			                	                    	bankSikl.setLoanCode(loanCode);
					                	            	kaLianBankService.updateBankByLoanCode(bankquery); 
			                	                    	  
			                	                      logger.error("【上传合同 】卡联签约处理异常！"+siInfo.getRetMsg());
			                	                     }
			                	                   logger.info("【上传合同】 卡联签约结束！流水号："+inParam.getTrSerialNo());
			                	            	
			                	              }else{
			                	            	    logger.error("【上传合同 】卡联实名认证处理异常！"+outInfo.getRetMsg());
			                	            	    LoanBank bankquery = new LoanBank();
				                	            	bankquery.setTrSerialNo(detail.getTrSerialNo());
				                	            	bankquery.setTransDate(new Date());
				                	            	bankquery.setRealAuthen("2");
				                	            	bankquery.setLoanCode(loanCode);
				                	            	kaLianBankService.updateBankByLoanCode(bankquery);
			                	            }
			                	           } catch (Exception e) {
			                	            e.printStackTrace();
			                	        }
		                	        }
	                		   }
	                	  }
	                 }
	            }
	        } 
	    }
	
	/**
	 * 将系统的证件类型转为上传的证件类型
	 * 2016年3月14日
	 * By 翁私
	 * @param cum
	 * @return
	 */
	public String changeNum(String cum){
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
	public String randomNumStr(){
		  UUID uuid= UUID.randomUUID();
		  String batchNo=uuid.toString().replaceAll("-","");
		  batchNo=batchNo.substring(0, 16);
		  return batchNo;
	}
}