/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.servicePaperLessService.java
 * @Create By 王彬彬
 * @Create In 2016年4月18日 下午10:20:06
 */
package com.creditharmony.loan.borrow.contract.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.creditharmony.adapter.bean.in.security.Scy_PortraitHDInBean;
import com.creditharmony.adapter.bean.out.security.Scy_PortraitHDOutBean;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.SmsState;
import com.creditharmony.core.loan.type.ContractResult;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.SmsType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCustomerService;
import com.creditharmony.loan.borrow.contract.dao.PaperLessDao;
import com.creditharmony.loan.borrow.contract.entity.VerificationInfo;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.IDCardMessageEx;
import com.creditharmony.loan.borrow.contract.view.PaperLessView;
import com.creditharmony.loan.borrow.transate.service.LoanInfoService;
import com.creditharmony.loan.car.common.dao.CarLoanCoborrowerDao;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.common.consts.SystemSetFlag;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.sms.dao.SmsDao;
import com.creditharmony.loan.sms.entity.SmsHis;
import com.creditharmony.loan.sms.entity.SmsTemplate;

/**
 * @Class Name PaperLessService
 * @author 王彬彬
 * @Create In 2016年4月18日
 */
@Service
public class PaperLessService {
	
	@Autowired
	private PaperLessDao paperLessDao;
	
	@Autowired
	private SmsDao smsDao;

	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	@Autowired
	private LoanCoborrowerDao loanCoborrowerDao;

	@Autowired
	private CarLoanCoborrowerDao carloanCoborrowerDao;
	
	@Autowired
	private SystemSetMaterDao systemSetMaterDao;
	
	@Autowired
	private VerificationService verificationService;
	
	@Autowired
	private LoanCustomerService loanCustomerService;
	
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	@Resource
	private LoanInfoService infoService;
	/**
	 * 更新主借人验证码
	 * 2016年4月18日
	 * By 王彬彬
	 * @param map
	 */
	public void updateCustomerPinByLoanCode(PaperLessView paperView)
	{
		paperLessDao.updateCustomerPinByLoanCode(paperView);
	}
	
	/**
	 * 更新共借人借人验证码
	 * 2016年4月18日
	 * By 王彬彬
	 * @param map
	 */
	public void updateCustomerPinById(PaperLessView paperView)
	{
		paperLessDao.updateCustomerPinById(paperView);
	}
	/**
	 * 更新车借共借人借人验证码
	 * 2016年5月7日
	 * By 葛志超
	 * @param map
	 */
	public void updateCarCustomerPinById(PaperLessView paperView)
	{
		paperLessDao.updateCarCustomerPinById(paperView);
	}
	
	/**
	 * 获取短信内容
	 * 2016年4月19日
	 * By 王彬彬
	 * @param smsType 短信类型
	 * @return 短信模板内容
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public SmsTemplate getSmsTemplate(SmsType smsType)
	{
		return smsDao.getSmsTemplate(smsType.getCode());
	}
	
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void saveSmsHis(Map<String,String> elem,String content,SmsType smsType){
		SmsHis smsHis = new SmsHis();
		//客户编号
		smsHis.setCustomerCode(elem.get("customerCode"));
		//客户姓名
		smsHis.setCustomerName(elem.get("customerName"));
		//借款编码
		smsHis.setLoanCode(elem.get("loanCode"));
		//发送时间
		smsHis.setSmsSendTime(new Date());
		//短信内容
		smsHis.setSmsMsg(content);
		//短信模板名称
		smsHis.setSmsTempletId(smsType.getName());
		//发送状态
		smsHis.setSmsSendStatus(SmsState.FSCG.value);
		smsHis.preInsert();
		smsDao.insertSmsHis(smsHis);
	}
	
	/**
     *检测主借人身份证信息 
     *@author zhanghao
     *@Create in 2016年04月22日
     *@param idPhoto
     *@param idCardMessage
     *@return Map
     * 
     */
    @Transactional(readOnly = false,value="loanTransactionManager")
	public void updateComposeDocId(String customerId,String composePhotoId,String custType){
        Map<String, Object> param = new HashMap<String,Object>();
        param.put("customerId", customerId);
        param.put("composePhotoId", composePhotoId);
	    if("0".equals(custType)){
	      loanCustomerDao.updatePaperlessMessage(param);  
	    }
	    if("1".equals(custType)){
	      loanCoborrowerDao.updatePaperlessMessage(param);  
	    }
	}
	/**
	 *检测主借人身份证信息 
	 *@author zhanghao
	 *@Create in 2016年04月22日
	 *@param idPhoto
	 *@param idCardMessage
	 *@return Map
	 * 
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public Map<String,String> validMainBorrower(MultipartFile idPhoto,IDCardMessageEx idCardMessage){
	  Map<String,String> resultMap = new HashMap<String,String>();
	  /*String customerNameOCR = idCardMessage.getCustomerNameOCR();
	  String idNumOCR = idCardMessage.getIdNumOCR();*/
	  String docId = idCardMessage.getDocId();
	  LoanCustomer loanCustomer = loanCustomerDao.get(idCardMessage.getCustomerId());
	  String score = "";
	  if(!ObjectHelper.isEmpty(loanCustomer)){
	     /* if(!customerNameOCR.equals(loanCustomer.getCustomerName()) || idNumOCR.equals(loanCustomer.getCustomerCertNum())){
	          resultMap.put("flag",YESNO.NO.getCode());  
	          resultMap.put("message", "OCR信息跟系统录入信息不匹配");
	          return resultMap;
	      }else{*/
	          Scy_PortraitHDInBean inputBean = new Scy_PortraitHDInBean();
	          inputBean.setPhotoDocId(docId);
	          inputBean.setCustomerCretNum(loanCustomer.getCustomerCertNum());
	          inputBean.setCustomerName(loanCustomer.getCustomerName());
	          Scy_PortraitHDOutBean outBean = validPolice(inputBean);
	          
	          this.validVerification(loanCustomer,null, docId, "0", outBean, resultMap);
	  }else{
	      
	      resultMap.put("flag", YESNO.NO.getCode());
	      resultMap.put("message", "没有主借人信息");
	      resultMap.put("artificialIDCard","1");  
	  }
	  
	  return resultMap;
	}
	
	/**
     *检测共借人身份证信息 
     *@author zhanghao
     *@Create in 2016年04月22日
     *@param idPhoto
     *@param idCardMessage
     *@return Map
     * 
     */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public Map<String,String> validCoborrower(MultipartFile idPhoto,IDCardMessageEx idCardMessage){
	    Map<String,String> resultMap = new HashMap<String,String>();
	      /*String customerNameOCR = idCardMessage.getCustomerNameOCR();
	      String idNumOCR = idCardMessage.getIdNumOCR();*/
	      String docId = idCardMessage.getDocId();
	      LoanCoborrower loanCoborrower = loanCoborrowerDao.get(idCardMessage.getCustomerId());
	      if(!ObjectHelper.isEmpty(loanCoborrower)){
	          /*if(!customerNameOCR.equals(loanCoborrower.getCoboName()) || idNumOCR.equals(loanCoborrower.getCoboCertNum())){
	              resultMap.put("flag", YESNO.NO.getCode());  
	              resultMap.put("message", "OCR信息跟系统录入信息不匹配");
	              return resultMap;
	          }else{*/
	              Scy_PortraitHDInBean inputBean = new Scy_PortraitHDInBean();
	              inputBean.setPhotoDocId(docId);
	              inputBean.setCustomerCretNum(loanCoborrower.getCoboCertNum());
	              inputBean.setCustomerName(loanCoborrower.getCoboName());
	              Scy_PortraitHDOutBean outBean = validPolice(inputBean);
	              this.validVerification(null,loanCoborrower, docId, "1", outBean, resultMap);
	      }else{
	                 resultMap.put("flag", YESNO.NO.getCode());
	                 resultMap.put("message", "缺少共借人信息");
	      }
	      return resultMap;
	}
	/**
	 * 公安验证失败后的操作
	 * @param loanCustomer
	 * @param docId
	 * @param customerType
	 * @param outBean
	 * @param resultMap
	 * @return
	 */
	public Map<String, String> validVerification(LoanCustomer loanCustomer,LoanCoborrower loanCoborrower, String docId, String customerType,
			Scy_PortraitHDOutBean outBean, Map<String, String> resultMap) {
		
		SystemSetting querySetting = new SystemSetting();
        querySetting.setSysFlag(SystemSetFlag.SYS_CERT_VERIFY);
        String tagScore = null;
        try{
            SystemSetting resultSetting =  systemSetMaterDao.get(querySetting);
            tagScore = resultSetting.getSysValue();
            if(StringUtils.isEmpty(tagScore)){
                tagScore = SystemSetFlag.SYS_CERT_VERIFY_SCORE;
            }
        }catch(Exception e){
            e.printStackTrace();
            tagScore = SystemSetFlag.SYS_CERT_VERIFY_SCORE; 
        }
        Integer tagScoreInt = Integer.valueOf(tagScore);
        // 公安信息验证
        String score =outBean.getScore();
        
		VerificationInfo verification = new VerificationInfo();
		if("0".equals(customerType)){
			verification.setLoanCode(loanCustomer.getLoanCode());
			verification.setCustomerCode(loanCustomer.getCustomerCode());
			verification.setCustomerName(loanCustomer.getCustomerName());
			verification.setCustomerCertNum(loanCustomer.getCustomerCertNum());
			verification.setCustomerId(loanCustomer.getId());
		}else{
			verification.setLoanCode(loanCoborrower.getLoanCode());
			verification.setCustomerName(loanCoborrower.getCoboName());
			verification.setCustomerCertNum(loanCoborrower.getCoboCertNum());
			verification.setCustomerId(loanCoborrower.getId());

		}
		verification.setDocId(docId);
		// 0表示主借人
		verification.setCustomerType(customerType);

		// 首先查询验证失败表，不存在就插入，存在查询验证次数
		VerificationInfo verinfo = new VerificationInfo();
		
		if("0".equals(customerType)){
			verinfo.setLoanCode(loanCustomer.getLoanCode());
			verinfo.setCustomerCode(loanCustomer.getCustomerCode());
		}else{
			verinfo.setLoanCode(loanCoborrower.getLoanCode());
		}
		verinfo.setCustomerType(customerType);
		verinfo.setCustomerId(verification.getCustomerId());
//		verinfo.setCustomerCertNum(verification.getCustomerCertNum());
		verinfo.setFailCode(outBean.getRetCode());
		List<VerificationInfo> listVer = verificationService.findList(verinfo);

		// 验证身份信息不在查询范围内(2014)
		if (null != outBean.getRetCode() && SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_QUE.equals(outBean.getRetCode())) {
			verification.setVerifyNumber(0);
			verification.setFailCode(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_QUE);
			if (listVer == null || listVer.size() == 0) {
				// 插入身份验证信息
				verificationService.insertVerification(verification);
				resultMap.put("verifyNumber", "0");
			}

			resultMap.put("artificialIDCard", "1");
			resultMap.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_QUE);
			resultMap.put("flag", YESNO.NO.getCode());
			resultMap.put("message", outBean.getRetMsg());
			return resultMap;
		}
		// 图片质量不合格 可以有8次操作机会(9998)
		if (null != outBean.getRetCode() && SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PIC.equals(outBean.getRetCode().equals("9997")?"9998":outBean.getRetCode())) {
			verification.setVerifyNumber(7);
			verification.setFailCode(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PIC);
			VerificationInfo verinfoTwo = new VerificationInfo();
			// 存在就将验证次数减一
			VerificationInfo verinfoThr = new VerificationInfo();
			if (listVer != null && listVer.size() > 0) {
				verinfoTwo = listVer.get(0);
				Integer verifyNum = null;
				// 重新上传文件则重置验证次数且更新doc_id
				if (docId != null && docId.equals(verinfoTwo.getDocId())) {
					verifyNum = verinfoTwo.getVerifyNumber() - 1;
				} else if (docId != null && !docId.equals(verinfoTwo.getDocId())) {
					verifyNum = 7;
					verinfoThr.setDocId(docId);
				}

				verinfoThr.setId(verinfoTwo.getId());
				verinfoThr.setVerifyNumber(verifyNum);
				verinfoThr.preUpdate();
				verificationService.updateVer(verinfoThr);
				resultMap.put("verifyNumber", verifyNum + "");
			} else {
				// 插入身份验证信息
				verificationService.insertVerification(verification);
				resultMap.put("verifyNumber", "7");
			}

			resultMap.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PIC);
			resultMap.put("flag", YESNO.NO.getCode());
			resultMap.put("message", "图像质量太低，请上传高清人脸图像");
			return resultMap;

		}
		// 特征提取失败2030
		//if (null != outBean.getRetCode() && SystemSetFlag.SYS_CERT_FEATURE_LOSE.equals(outBean.getRetCode())) {
			if((loanCustomer!=null && loanCustomer.getCustomerName().equals("大大"))||loanCoborrower!=null && loanCoborrower.getCoboName().equals("大大") ){
			verification.setVerifyNumber(0);
			verification.setFailCode(SystemSetFlag.SYS_CERT_FEATURE_LOSE);
			if (listVer == null || listVer.size() == 0) {
				// 插入身份验证信息
				verificationService.insertVerification(verification);
				resultMap.put("verifyNumber", "0");
			}

			resultMap.put("code", SystemSetFlag.SYS_CERT_FEATURE_LOSE);
			resultMap.put("flag", YESNO.NO.getCode());
			resultMap.put("message", "特征提取失败");
			return resultMap;
		}
		// 姓名与身份证号一致，但身份证照片不存在(2012)
		if (null != outBean.getRetCode() && SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NOPIC.equals(outBean.getRetCode())) {
			verification.setVerifyNumber(0);
			verification.setFailCode(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NOPIC);
			if (listVer == null || listVer.size() == 0) {
				// 插入身份验证信息
				verificationService.insertVerification(verification);
				resultMap.put("verifyNumber", "0");
			}

			resultMap.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NOPIC);
			resultMap.put("flag", YESNO.NO.getCode());
			resultMap.put("message", outBean.getRetMsg());
			return resultMap;
		}
		// 身份证号一致但姓名不一致 有3次机会 (2011)
		if (null != outBean.getRetCode() && SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NMA.equals(outBean.getRetCode())) {
			verification.setVerifyNumber(2);
			verification.setFailCode(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NMA);
			VerificationInfo verinfoTwo = new VerificationInfo();
			// 存在就将验证次数减一
			VerificationInfo verinfoThr = new VerificationInfo();
			if (listVer != null && listVer.size() > 0) {
				verinfoTwo = listVer.get(0);
				Integer verifyNum = null;
				verifyNum = verinfoTwo.getVerifyNumber() - 1;
				if("0".equals(customerType)){
					verinfoThr.setCustomerCertNum(loanCustomer.getCustomerCertNum());
					verinfoThr.setCustomerName(loanCustomer.getCustomerName());
				}else{
					verinfoThr.setCustomerCertNum(loanCoborrower.getCoboCertNum());
					verinfoThr.setCustomerName(loanCoborrower.getCoboName());
					
				}
				verinfoThr.setId(verinfoTwo.getId());
				verinfoThr.setVerifyNumber(verifyNum);
				verinfoThr.preUpdate();
				verificationService.updateVer(verinfoThr);
				resultMap.put("verifyNumber", verifyNum + "");
				// 改变idValidFlag状态为2，目的为了将按钮置灰
				if (verifyNum == 0) {
					if("0".equals(customerType)){
						Map<String, Object> param = new HashMap<String, Object>();

						param.put("idValidFlag", "2");
						param.put("customerId", loanCustomer.getId());
						loanCustomerDao.updatePaperlessMessage(param);
					}else if("1".equals(customerType)){
						  Map<String,Object> param = new HashMap<String,Object>();
						  param.put("idValidFlag", "2");
						  param.put("customerId", loanCoborrower.getId());
			              loanCoborrowerDao.updatePaperlessMessage(param);
					}
					
				}
			}else {
				// 插入身份验证信息
				verificationService.insertVerification(verification);
				resultMap.put("verifyNumber", "2");
			}
			resultMap.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NMA);
			resultMap.put("flag", YESNO.NO.getCode());
			resultMap.put("message", outBean.getRetMsg());
			return resultMap;
		}
		
		if(StringUtils.isEmpty(score)){
            resultMap.put("flag", YESNO.NO.getCode());  
            resultMap.put("message",outBean.getRetMsg());  
            return resultMap;
        }
        //正常返回（系统判定为不同人）(1001)
        if(Integer.valueOf(score)<=tagScoreInt){
      	  verification.setVerifyNumber(0);
      	  verification.setFailCode(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PER);
      	  if(listVer==null||listVer.size()==0){
      		//插入身份验证信息
	        	  verificationService.insertVerification(verification);
	        	  resultMap.put("verifyNumber", "0"); 
      	  }
      	  
      	  resultMap.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PER); 
      	  resultMap.put("flag", YESNO.NO.getCode());  
          resultMap.put("message","正常返回（系统判定为不同人）"); 
          return resultMap;
      	  
        }else{
  		  resultMap.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PER); 
          resultMap.put("flag", YESNO.YES.getCode());  
          resultMap.put("message", "验证通过");
          if("0".equals(customerType)){
        	  Map<String,Object> param = new HashMap<String,Object>();
              if(StringUtils.isNotEmpty(score)){
                  param.put("idValidScore", Float.valueOf(score));
              }
              param.put("idValidFlag", YESNO.YES.getCode());
              param.put("customerId",loanCustomer.getId());
              loanCustomerDao.updatePaperlessMessage(param);
          }else if("1".equals(customerType)){
              Map<String,Object> param = new HashMap<String,Object>();
              if(StringUtils.isNotEmpty(score)){
                  param.put("idValidScore", Float.valueOf(score));
              }
              param.put("idValidFlag", YESNO.YES.getCode());
              param.put("customerId",loanCoborrower.getId());
              loanCoborrowerDao.updatePaperlessMessage(param);
          }
          return resultMap;
      }
		
	}
	
    private Scy_PortraitHDOutBean validPolice(Scy_PortraitHDInBean inputBean){

	    ClientPoxy service = new ClientPoxy(ServiceType.Type.SCY_PORTRAIT_HD);
	    Scy_PortraitHDOutBean outInfo = (Scy_PortraitHDOutBean) service.callService(inputBean);
	    return outInfo;
	}
    /**
     * 手动验证 记录原因到历史表 同时更新身份验证字段
     * @param customerId
     * @param customerType
     * @param verificateReason
     */
    @Transactional(readOnly = false,value="loanTransactionManager")
    public void saveHistory(String customerId,String customerType,String verificateReason){
    	User user = UserUtils.getUser();
    	
		LoanStatusHis record = new LoanStatusHis();
		if("0".equals(customerType)){
			LoanCustomer customer=loanCustomerService.get(customerId);
			// APPLY_ID
			if(customer!=null){
				record.setApplyId(customer.getApplyId());
				record.setLoanCode(customer.getLoanCode());
			}
		}else if("1".equals(customerType)){
			LoanCoborrower loanCoborrower = loanCoborrowerDao.get(customerId);
			if(loanCoborrower!=null){
				LoanInfo info = infoService.findStatusByLoanCode(loanCoborrower.getLoanCode());
				record.setApplyId(info.getApplyId());
				record.setLoanCode(loanCoborrower.getLoanCode());
			}
		}
		
		// 状态
		record.setDictLoanStatus(LoanApplyStatus.CONTRACT_UPLOAD.getCode());
		// 操作步骤(回退,放弃,拒绝 等)
		record.setOperateStep(ContractConstant.CUST_SERVICE_SIGN);
		// 操作结果
		record.setOperateResult(ContractResult.CONTRACT_SUCCEED.getName());
		// 备注
		if("0".equals(customerType)){
			verificateReason="手动验证原因："+verificateReason+"（主借人）";
		}else{
			verificateReason="手动验证原因："+verificateReason+"（自然人保证人）";
		}
		record.setRemark(verificateReason);
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 设置Crud属性值
		record.preInsert();
		// 操作时间
		record.setOperateTime(record.getCreateTime());
		// 操作人记录当前登陆系统用户名称
		record.setOperator(user.getName());
		if (!ObjectHelper.isEmpty(user.getRole())) {
			// 操作人角色
			record.setOperateRoleId(user.getRole().getId());
		}
		if (!ObjectHelper.isEmpty(user.getDepartment())) {
			// 机构编码
			record.setOrgCode(user.getDepartment().getId());
		}
		loanStatusHisDao.insertSelective(record);
		//同时更新主借人或共借人的身份验证状态
		if("0".equals(customerType)){
			Map<String,Object> param = new HashMap<String,Object>();
            
            param.put("idValidFlag", YESNO.YES.getCode());
            param.put("customerId",customerId);
            loanCustomerDao.updatePaperlessMessage(param);
		}else{
			 Map<String,Object> param = new HashMap<String,Object>();
             param.put("idValidFlag", YESNO.YES.getCode());
             param.put("customerId",customerId);
             loanCoborrowerDao.updatePaperlessMessage(param);
		}
    }
    /**
     *检测车借共借人身份证信息 
     *@author gezhichao	
     *@Create in 2016年05月9日
     *@param idPhoto
     *@param idCardMessage
     *@return Map
     * 
     */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public Map<String,String> validCarCoborrower(CommonsMultipartFile idPhoto,IDCardMessageEx idCardMessage){
		Map<String,String> resultMap = new HashMap<String,String>();
	      /*String customerNameOCR = idCardMessage.getCustomerNameOCR();
	      String idNumOCR = idCardMessage.getIdNumOCR();*/
	      String docId = idCardMessage.getDocId();
	      CarLoanCoborrower loanCoborrower = carloanCoborrowerDao.get(idCardMessage.getCustomerId());
	      if(!ObjectHelper.isEmpty(loanCoborrower)){
	              Scy_PortraitHDInBean inputBean = new Scy_PortraitHDInBean();
	              inputBean.setPhotoDocId(docId);
	              inputBean.setCustomerCretNum(loanCoborrower.getCertNum());
	              inputBean.setCustomerName(loanCoborrower.getCoboName());
	              Scy_PortraitHDOutBean outBean = validPolice(inputBean);
	              SystemSetting querySetting = new SystemSetting();
	              querySetting.setSysFlag(SystemSetFlag.SYS_CERT_VERIFY);
	              String tagScore = null;
	              try{
	                  SystemSetting resultSetting =  systemSetMaterDao.get(querySetting);
	                  tagScore = resultSetting.getSysValue();
	                  if(StringUtils.isEmpty(tagScore)){
	                      tagScore = SystemSetFlag.SYS_CERT_VERIFY_SCORE;
	                  }
	              }catch(Exception e){
	                  e.printStackTrace();
	                  tagScore = SystemSetFlag.SYS_CERT_VERIFY_SCORE; 
	              }
	              Integer tagScoreInt = Integer.valueOf(tagScore);
	              // 公安信息验证
	              String score =outBean.getScore();
	              if(null!=outBean.getRetCode()&&"2014".equals(outBean.getRetCode())){
                	  resultMap.put("artificialIDCard","1");  
                  }
	              if(StringUtils.isEmpty(score)){
	                  resultMap.put("flag", YESNO.NO.getCode());  
	                  resultMap.put("message",outBean.getRetMsg());  
	                  return resultMap;
	              }
	              if(Integer.valueOf(score)<=tagScoreInt){
	                  resultMap.put("flag", YESNO.NO.getCode());  
	                  resultMap.put("message","公安验证不通过");
	                  return resultMap; 
	              }
	              else{
	                  resultMap.put("flag", YESNO.YES.getCode());  
	                  resultMap.put("message","验证通过");
	                  Map<String,Object> param = new HashMap<String,Object>();
	                  if(StringUtils.isNotEmpty(score)){
	                      param.put("idValidScore", score);
	                  }
	                  param.put("idValidFlag", YESNO.YES.getCode());
	                  param.put("customerId",idCardMessage.getCustomerId());
	                  carloanCoborrowerDao.updatePaperlessMessage(param);
	              }
	      }else{
	                 resultMap.put("flag", YESNO.NO.getCode());
	                 resultMap.put("message", "缺少共借人信息");
	      }
	      return resultMap;
	}
	/**
     *检测车借主借人身份证信息 
     *@author 葛志超
     *@Create in 2016年6月3日
     *@param idPhoto
     *@param idCardMessage
     *@return Map
     * 
     */
    @Transactional(readOnly = false,value="loanTransactionManager")
	public void updateCarComposeDocId(String customerId,String composePhotoId,String custType){
        Map<String, Object> param = new HashMap<String,Object>();
        param.put("customerId", customerId);
        param.put("composePhotoId", composePhotoId);
	    if("0".equals(custType)){
	    	loanCustomerDao.updatePaperlessMessage(param);  
	    }
	    if("1".equals(custType)){
	    	carloanCoborrowerDao.updatePaperlessMessage(param);  
	    }
	}

	public Map<String, String> artificiaMainBorrower(
			IDCardMessageEx idCardMessage) {
		  Map<String,Object> param = new HashMap<String,Object>();
          param.put("idValidScore", Float.valueOf("40"));
          param.put("idValidFlag", YESNO.YES.getCode());
          param.put("customerId",idCardMessage.getCustomerId());
          param.put("idArtificiaFlag",YESNO.YES.getCode());
          loanCustomerDao.updatePaperlessMessage(param);
          Map<String,String> resultMap = new HashMap<String,String>();
          resultMap.put("flag", YESNO.YES.getCode());  
          resultMap.put("message","验证通过");
		 return resultMap;
	}

	public Map<String, String> artificiaCarCoborrower(
			 IDCardMessageEx idCardMessage) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("idValidScore", Float.valueOf("40"));
        param.put("idValidFlag", YESNO.YES.getCode());
        param.put("customerId",idCardMessage.getCustomerId());
        param.put("idArtificiaFlag",YESNO.YES.getCode());
        carloanCoborrowerDao.updatePaperlessMessage(param);
        Map<String,String> resultMap = new HashMap<String,String>();
        resultMap.put("flag", YESNO.YES.getCode());  
        resultMap.put("message","验证通过");
		return resultMap;
	}
    
}
