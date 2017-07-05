package com.creditharmony.loan.borrow.contract.event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.bean.BaseOutInfo;
import com.creditharmony.adapter.bean.in.thirdpay.PayCertificationSingleInBean;
import com.creditharmony.adapter.bean.in.thirdpay.PaySignReqSingleInBean;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.CertificateType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.certification.dao.BankDao;
import com.creditharmony.loan.borrow.certification.entity.LoanBank;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.common.service.KaLianBankService;
import com.creditharmony.loan.common.type.OpenBankCJ;
/**
 *上传合同确认的时候 畅捷实名认证 
 * @author 翁私
 *
 */
@Service("li_hj_cj_RealName_Authentication")
public class ChangJieRealNameAuthentication extends BaseService implements ExEvent {
	
	@Autowired
	private KaLianBankService kaLianBankService;
	@Autowired
	private BankDao bankDao; 
	private Logger logger = LoggerFactory.getLogger(getClass());
	SimpleDateFormat  format = new SimpleDateFormat("yyMMddHHmmss");
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
	                			   ClientPoxy service = new ClientPoxy(ServiceType.Type.CJ_CERTIFICATION);
	                				PayCertificationSingleInBean cjInfo = new PayCertificationSingleInBean();
		                	        List<LoanBank>  list = bankDao.queryCjByCode(loanCode);
		                	        if(list != null && list.size()>0){
		                	        	LoanBank bank = list.get(0);
		                	        	// 如果是已经实名认证过的不需要再次实名认证
		                	        	if(!"1".equals(bank.getCjSign())){
		                	        	 String bankFullName = OpenBankCJ.getOpenBank(bank.getBankFullName());
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
			                	        try {
			                	        	  logger.info("【上传合同】畅捷畅捷实名认证开始！流水号："+cjInfo.getTrSerialNo());
			                	        	  BaseOutInfo outInfo = (BaseOutInfo) service.callService(cjInfo);
			                	             // 如果提交成功则 更新流水号 提交日期 和实名认证状态
			                	             if("0000".equals(outInfo.getRetCode())){
			                	           /*  if("0000".equals("00")){*/
			                	            	contractBusiView.setCjAuthen("1");
			                	            	contractBusiView.setCjAuthenFailure("");
			                	            	LoanBank bankquery = new LoanBank();
			                	            	bankquery.setTrSerialNo(cjInfo.getTrSerialNo());
			                	            	bankquery.setCjAuthen("1");
			                	            	bankquery.setCjAuthenFailure("");
			                	            	bankquery.setAccNo(cjInfo.getAccountNo());
			                	            	bankDao.updateByAccNo(bankquery);
			                	            	logger.info("【上传合同】 畅捷实名认证结束！流水号："+cjInfo.getTrSerialNo());
			                	            	ClientPoxy serviceSign = new ClientPoxy(ServiceType.Type.CJ_SIGNREQ);
			                	    			PaySignReqSingleInBean inParam = new PaySignReqSingleInBean();
			                	    			BeanUtils.copyProperties(cjInfo, inParam);
			                	    			inParam.setTrSerialNo(getFlowNo());
			                	    			inParam.setSubmitDate(new Date());
			                	    			inParam.setSn(getIds());
			                	    			inParam.setProtocolNo(getFlowNo());
			                	    			inParam.setBeginDate(new Date());
			                	    			inParam.setEndDate(new Date());
			                	            	logger.info("【上传合同】 畅捷签约开始！流水号："+inParam.getTrSerialNo());
			                	                     // 获取开始时间
			                	            	   BaseOutInfo cyInfo = (BaseOutInfo) serviceSign.callService(inParam);
			                	                    if("0000".equals(cyInfo.getRetCode())){
			                	                    	  bankquery.setProtocolNo(inParam.getProtocolNo());
			                	                    	  bankquery.setCjQyNo(inParam.getTrSerialNo());
			                	                    	  bankquery.setCjSign("1");
			                	                    	  bankDao.updateByAccNo(bankquery);
			                	                      }else{
			                	                    	  // bankquery.setProtocolNo(inParam.getProtocolNo());
				                	                      bankquery.setCjQyNo(inParam.getTrSerialNo());
				                	                      bankquery.setCjSign("2");
				                	                      String retMsg ="";
						                	               if(!ObjectHelper.isEmpty(cyInfo.getRetMsg())){
						                	            	    int i = cyInfo.getRetMsg().length();
							               	            		if(i >= 200){
							               	            			retMsg = cyInfo.getRetMsg().substring(0,198);
							               	            		}else{
							               	            			retMsg = cyInfo.getRetMsg();
							               	            		}
						                	              }
				                	                      bankquery.setCjSignFailure(retMsg);
				                	                      bankDao.updateByAccNo(bankquery);
			                	                    	  
			                	                      logger.error("【上传合同 】畅捷签约处理异常！"+cyInfo.getRetMsg());
			                	                     }
			                	                      logger.info("【上传合同】 畅捷签约结束！流水号："+inParam.getTrSerialNo());
			                	            	
			                	              }else{
			                	            	    logger.error("【上传合同 】畅捷实名认证处理异常！"+outInfo.getRetMsg());
			                	            	   // 设置流程属性值
			                	            	    contractBusiView.setCjAuthen("2");
			                	            	    contractBusiView.setCjAuthenFailure(outInfo.getRetMsg());
			                	            	    
			                	            	    LoanBank bankquery = new LoanBank();
				                	            	bankquery.setTrSerialNo(cjInfo.getTrSerialNo());
				                	            	bankquery.setCjAuthen("2");
				                	            	bankquery.setAccNo(cjInfo.getAccountNo());
				                	            	String retMsg ="";
				                	            	if(!ObjectHelper.isEmpty(outInfo.getRetMsg())){
				                	            		   int i = outInfo.getRetMsg().length();
						               	            		if(i >= 200){
						               	            			retMsg = outInfo.getRetMsg().substring(0,198);
						               	            		}else{
						               	            			retMsg = outInfo.getRetMsg();
						               	            	  }
				                	            	}
				                	            	bankquery.setCjAuthenFailure(retMsg);
				                	            	bankDao.updateByAccNo(bankquery);
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
     * 获得流水号
     * @param no
     * @return
     */
    public String getFlowNo(){
			return "xinhe-chp"+format.format(new Date())+getId();
    }
    
    /**
	 * 随机产生指定长度的数据字符串
	 * @param length 指定长度
	 * @return 结果
	 */
     public String getId(){
		 UUID uuid= UUID.randomUUID();
     	 String batchNo=uuid.toString().replaceAll("-","");
        batchNo=batchNo.substring(0, 10);
     	 return batchNo;
	  }
     
     /**
 	 * 随机产生指定长度的数据字符串
 	 * @param length 指定长度
 	 * @return 结果
 	 */
      public String getIds(){
 		 UUID uuid= UUID.randomUUID();
      	 String batchNo=uuid.toString().replaceAll("-","");
      	 return batchNo;
 	  }
    
}