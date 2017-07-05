/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.webPaperLessUtilsWeb.java
 * @Create By 王彬彬
 * @Create In 2016年4月18日 下午10:08:52
 */
package com.creditharmony.loan.borrow.contract.web;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.ContractResult;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.SmsType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCoborrowerService;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCustomerService;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;
import com.creditharmony.loan.borrow.contract.entity.VerificationInfo;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.IDCardMessageEx;
import com.creditharmony.loan.borrow.contract.service.PaperLessService;
import com.creditharmony.loan.borrow.contract.service.PaperlessPhotoService;
import com.creditharmony.loan.borrow.contract.service.VerificationService;
import com.creditharmony.loan.borrow.contract.view.PaperLessView;
import com.creditharmony.loan.common.consts.SystemSetFlag;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.entity.CaCustomerSign;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.utils.CaUtil;
import com.creditharmony.loan.common.utils.ImageUtils;
import com.creditharmony.loan.sms.entity.SmsTemplate;
import com.creditharmony.loan.sms.utils.SmsUtil;

/**
 * @Class Name PaperLessUtilsWeb
 * @author 王彬彬
 * @Create In 2016年4月18日
 */
@Controller
@RequestMapping(value = "${adminPath}/paperless/confirminfo")
public class PaperLessUtilsWeb extends BaseController {
	
	@Autowired
	private PaperLessService paperLessService;
	
	@Autowired
	private PaperlessPhotoService paperlessPhotoService;
	
	@Autowired
	private LoanCustomerService loanCustomerService;
	
	@Autowired
	private LoanCoborrowerService loanCoborrowerService;
	
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	@Autowired
	private LoanCoborrowerDao loanCoborrowerDao;
	
	@Autowired
	private VerificationService verificationService;

	/**
	 * 发送验证码
	 * 2016年4月18日
	 * By 王彬彬
	 * @param loanCode 合同编号
	 * @param certNum 证件号码
	 * @param phone 手机号
	 * @param customerType 客户类型（主借人，共借人）
	 * @param customerCode 主借人Code/共借人id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("sendPin")
	public String sendPin(String loanCode, String customerCode, String phone,
			String customerType, String customerName)
	{
	        customerName = StringEscapeUtils.unescapeHtml4(customerName);
		try {
			String pin = SmsUtil.getRandNum(6);//6位验证码
			
			PaperLessView paperView = new PaperLessView();
			paperView.setLoanCode(loanCode);
			paperView.setCustomerCode(customerCode);
			paperView.setCobId(customerCode);
			paperView.setCustomerName(customerName);
			paperView.setPin(pin);
			paperView.setCaptchaIfConfirm(YESNO.NO.getCode());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			paperView.setConfirmTimeout(calendar.getTime());
			
			if(customerType.equals("0"))
			{
				paperLessService.updateCustomerPinByLoanCode(paperView);	//更新主借人验证码
			}
			else
			{
				paperLessService.updateCustomerPinById(paperView); 		//更新共借人验证码
			}
			SmsTemplate template = paperLessService
					.getSmsTemplate(SmsType.HJ_PIN_VERIFY);
			
			// 生成验证码短信
			String msg = template.getTemplateContent().replace("{#Pin#}", pin);
	
			logger.info("开始调用验证短信发送接口方法，当前电话号码为：" + phone);
			logger.info("当前短信信息： " + msg);
			// 完成后取消注释 发送短信
			SmsUtil.sendSms(phone, msg);
			logger.info("验证短信发送接口调用结束，当前电话号码为：" + phone);
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("loanCode", loanCode);//借款编码
			map.put("customerCode", customerCode);//借款编码or共借人Id
			map.put("customerName", customerName);//姓名
			
			paperLessService.saveSmsHis(map, msg, SmsType.HJ_PIN_VERIFY);
			
			return pin;
		} catch (Exception e) {
			e.printStackTrace();
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * 更新验证状态 2016年4月19日 By 王彬彬
	 * 
	 * @param loanCode 借款编码
	 * @param customerCode 客户编号/共借人ID
	 * @param customerType 客户累型（主借人/共借人）
	 * @return 更新结果
	 */
	@ResponseBody
	@RequestMapping("confirmPin")
	public String confirmPin(String loanCode, String customerCode,
			String customerType) {
		try {
			PaperLessView paperView = new PaperLessView();
			paperView.setLoanCode(loanCode);
            paperView.setCustomerCode(customerCode);
            paperView.setCobId(customerCode);
			paperView.setCaptchaIfConfirm(YESNO.YES.getCode());
			if (customerType.equals("0")) {
				paperLessService.updateCustomerPinByLoanCode(paperView); // 更新主借人验证码
			} else {
				paperLessService.updateCustomerPinById(paperView); // 更新共借人验证码
			}
			return "SUCCESS";
		} catch (Exception e) {
			return "FAILES";
		}
	}
	
	/**
	 *身份证发送公安进行信息校验
	 *@author zhanghao
	 *@Create In 2016年04月21日
	 *@param idPhoto  
	 *@param idCardMessage
	 *@return String
	 * 
	 */
	@RequestMapping(value="validIDCard")
	@ResponseBody
	// 公安对接还没有
	public Map<String,String> validIDCard(IDCardMessageEx idCardMessage){
	   String customerType = idCardMessage.getCustomerType();
	   Map<String,String> result = null;
	   MultipartFile idPhoto =null;
	   // 主借人身份证验证
	   if("0".equals(customerType)){
	       result = paperLessService.validMainBorrower(idPhoto, idCardMessage);
	   }else if("1".equals(customerType)){  // 共借人身份证验证
	       result = paperLessService.validCoborrower(idPhoto, idCardMessage); 
	   }
	   result.put("SYS_CERT_VERIFY_FAILCODE_PER", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PER);
	    return result;
	}
	/**
	 * 身份验证失败，手动操作
	 * @param model
	 * @param relationId
	 * @param photoType
	 * @param customerType
	 * @param loanCode
	 * @param contractCode
	 * @return
	 */
	@RequestMapping(value="openVerification")
	public String openVerification(Model model,String message,String customerId,String customerType,String code){
		try{
			if(message!=null){
				 message = new String(message.getBytes("ISO-8859-1"),"UTF-8");
				 message = java.net.URLDecoder.decode(message , "UTF-8");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("code",code);
		model.addAttribute("message",message);
	    model.addAttribute("customerId",customerId);
	    model.addAttribute("customerType", customerType);
	    model.addAttribute("SYS_CERT_VERIFY_FAILCODE_PER", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PER);
	    return "borrow/contract/verificateFail";
	}
	
	/**
	 * 手动验证通过，保存验证原因到历史中
	 * @param model
	 * @param relationId
	 * @param photoType
	 * @param customerType
	 * @param loanCode
	 * @param contractCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="saveHistory")
	public Map<String,String> saveHistory(Model model,String customerId,String customerType,String verificateReason){
		
		Map<String,String> result = new HashMap<String,String>();
		try{
			paperLessService.saveHistory(customerId, customerType, verificateReason);
			result.put("message", "操作成功");
			result.put("flag", "1");
		}catch(Exception e){
			e.printStackTrace();
			result.put("message", "操作失败");
			result.put("flag", "0");
		}
		
		
	    return result;
	}
	
	/**
	 * 进行手动验证前，先查询验证次数
	 * @param model
	 * @param customerId
	 * @param customerType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="findVerNum")
	public Map<String,String> findVerNum(Model model,String customerId,String customerType,String docId){
		
		Map<String,String> result = new HashMap<String,String>();
		try{
			 VerificationInfo verinfo=new VerificationInfo();
			 if("0".equals(customerType)){
				 LoanCustomer loanCustomer = loanCustomerDao.get(customerId);
				 verinfo.setCustomerCode(loanCustomer.getCustomerCode());
		       	 verinfo.setLoanCode(loanCustomer.getLoanCode());
		       	 //verinfo.setCustomerName(loanCustomer.getCustomerName());
		       	// verinfo.setCustomerCertNum(loanCustomer.getCustomerCertNum());
			 }else if("1".equals(customerType)){
				 LoanCoborrower loanCoborrower = loanCoborrowerDao.get(customerId);
				 //verinfo.setCustomerCode(loanCustomer.getCustomerCode());
		       	 verinfo.setLoanCode(loanCoborrower.getLoanCode());
		       	 //verinfo.setCustomerName(loanCoborrower.getCoboName());
		       //	 verinfo.setCustomerCertNum(loanCoborrower.getCoboCertNum());
			 }
			 verinfo.setCustomerId(customerId);
	       	 verinfo.setCustomerType(customerType);
	       	 List<VerificationInfo> listVer= verificationService.findList(verinfo);
	       	 if(listVer!=null&&listVer.size()>0){
	       		VerificationInfo verinfoTwo=listVer.get(0);
	       		String messge=null;
	       		if(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_QUE.equals(verinfoTwo.getFailCode())){
	       			messge="验证身份信息不在查询范围内";
	       			result.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_QUE);
	       		}else if(SystemSetFlag.SYS_CERT_FEATURE_LOSE.equals(verinfoTwo.getFailCode())){
	       			messge="特征提取失败";
	       			result.put("code", SystemSetFlag.SYS_CERT_FEATURE_LOSE);
	       		}else if(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PIC.equals(verinfoTwo.getFailCode())){
	       			messge="图片质量不合格";
	       			result.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PIC);
	       			//判断图片已经修改的 需要重新验证
	       			if(docId!=null&&!docId.equals(verinfoTwo.getDocId())){
	    				result.put("flag", "1");
	    				 return result;
	       			}
	       		}else if(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NOPIC.equals(verinfoTwo.getFailCode())){
	       			messge="姓名与身份证号一致，但身份证照片不存在";
	       			result.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NOPIC);
	       		}else if(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NMA.equals(verinfoTwo.getFailCode())){
	       			messge="身份证号一致但姓名不一致";
	       			result.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_NMA);
	       			String cusName="";
	       			String cusCert="";
	       			String cusNameOne=verinfoTwo.getCustomerName();
	       			String cusCertOne=verinfoTwo.getCustomerCertNum();
	       			if("0".equals(customerType)){
		   				 LoanCustomer loanCustomer = loanCustomerDao.get(customerId);
		   				 cusName=loanCustomer.getCustomerName();
		   				 cusCert=loanCustomer.getCustomerCertNum();
		   		       	 
		   			}else if("1".equals(customerType)){
		   				 LoanCoborrower loanCoborrower = loanCoborrowerDao.get(customerId);
		   				 cusName=loanCoborrower.getCoboName();
		   				 cusCert=loanCoborrower.getCoboCertNum();
		   			}
	       			//判断身份证或姓名是否修改
	       		    if(cusName!=null&&cusCert!=null){
	       		    	if(cusName.equals(cusNameOne)&&cusCert.equals(cusCertOne)&& verinfoTwo.getVerifyNumber()>0){
	       		    		result.put("verNum", verinfoTwo.getVerifyNumber()+"");
	       					//身份证或姓名没有修改
	       		    		result.put("flag", "2");
	       		    	}else if( verinfoTwo.getVerifyNumber() >0){
	       		    		//表示有验证机会 
	       		    		result.put("verNum", verinfoTwo.getVerifyNumber()-1+"");
	       		    		result.put("message", messge);
	       		    		result.put("flag", "1");
	       		    	}else{
	       		    		//表示没有验证机会  
	       		    		result.put("verNum", verinfoTwo.getVerifyNumber()-1+"");
	       		    		result.put("message", messge);
	       		    		result.put("flag", "3");
	       		    	}
	       		    }
	       		    return result;
	       		}else if(SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PER.equals(verinfoTwo.getFailCode())){
	       			result.put("code", SystemSetFlag.SYS_CERT_VERIFY_FAILCODE_PER);
	       			messge="正常返回(系统判断为不同人)";
	       		}
	       		result.put("message", messge);
	       		result.put("verNum", verinfoTwo.getVerifyNumber()+"");
				result.put("flag", "0");
				
	       	 }else{
	       		result.put("verNum", "0");
				result.put("flag", "1");
	       	 }
		}catch(Exception e){
			e.printStackTrace();
			result.put("verNum", "0");
			result.put("flag", "2");
		}
	    return result;
	}
	
	/**
	 * 当失败原因为验证身份信息不在查询范围内时，添加标示
	 * @param model
	 * @param customerId
	 * @param customerType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getVerification")
	public Map<String,String> getVerification(Model model,String loanCode){
		
		Map<String,String> result = new HashMap<String,String>();
		try{
			 //查询主借人错误的信息
			 VerificationInfo verinfo=new VerificationInfo();
	       	 verinfo.setLoanCode(loanCode);
	       	 verinfo.setCustomerType("0");
	       	 List<VerificationInfo> listVer= verificationService.findList(verinfo);
	         //查询主借人错误的信息
			 VerificationInfo verinfogongjie=new VerificationInfo();
			 verinfogongjie.setLoanCode(loanCode);
			 verinfogongjie.setCustomerType("1");
	       	 List<VerificationInfo> listVergongjie= verificationService.findList(verinfogongjie);
	       	 if(listVer!=null&&listVer.size()>0){
	       		VerificationInfo verinfoOne=listVer.get(0);
	       		result.put("flag", "0");
	       		result.put("failCode",verinfoOne.getFailCode());
	       	 }else{
	       		result.put("flag", "1");
	       	 }
	       	if(listVergongjie!=null&&listVergongjie.size()>0){
	       		VerificationInfo verinfoTwo=listVergongjie.get(0);
	       		result.put("flaggongjie", "0");
	       		result.put("failCodeGongjie",verinfoTwo.getFailCode());
	       	 }else{
	       		result.put("flaggongjie", "1");
	       	 }
		}catch(Exception e){
			e.printStackTrace();
			result.put("flag", "1");
			result.put("flaggongjie", "1");
		}
	    return result;
	}
	
	/**
	 * 查询是否手动验证过，没有手动验证不需要在合同审核页面显示手动验证
	 * @param model
	 * @param customerId
	 * @param customerType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getVerificationByIdCard")
	public Map<String,String> getVerificationByIdCard(Model model,String loanCode,HttpServletRequest request){
		Map<String,String> result = new HashMap<String,String>();
		
		try{
			String isCheck="";
			 //搜索主借人相关信息
			 Map<String,Object> param=new HashMap<String,Object>();
			 param.put("loanCode", loanCode);
			 LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
			 VerificationInfo verinfo=new VerificationInfo();
	       	 verinfo.setLoanCode(loanCode);
	       	 if(loanCustomer!=null){
	       		verinfo.setCustomerName(loanCustomer.getCustomerName());
		       	verinfo.setCustomerCertNum(loanCustomer.getCustomerCertNum());
	       	 }
	       	 List<VerificationInfo> listVer= verificationService.findList(verinfo);
	       	 //判断是否手动验证过
	       	 if(!listVer.isEmpty()&&listVer.size()>0){
	       		 for(VerificationInfo ver:listVer){
	       			 Integer num=ver.getVerifyNumber();
	       			 if("0".equals(num+"")){
	       				isCheck="1";
	       				break;
	       			 }
	       		 }
	       	 }
	       	 //搜索共借人相关信息
	       	 String isExistCob="";
	       	 List<LoanCoborrower> coborList = loanCoborrowerDao.selectByLoanCode(loanCode);
	       	 for(LoanCoborrower cobor:coborList){
	       		VerificationInfo verinfocob=new VerificationInfo();
	       		verinfocob.setLoanCode(loanCode);
	       		verinfocob.setCustomerName(cobor.getCoboName());
	       		verinfocob.setCustomerCertNum(cobor.getCoboCertNum());
		        List<VerificationInfo> listVerCob= verificationService.findList(verinfocob);
		        if(listVerCob!=null&&listVerCob.size()>0){
		        	for(VerificationInfo ver:listVerCob){
		        		String numcob=ver.getVerifyNumber()+"";
		        		if("0".equals(numcob)){
		        			isExistCob="1";
				        	break;
		        		}
		        	}
		        }
	       	 }
	       	 //手动验证过主借人或共借人   显示在合同审核页面
	       	 if(("1".equals(isCheck))||("1".equals(isExistCob))){
	       		result.put("flag", "0");
	       	 }else{
				result.put("flag", "1");
	       	 }
		}catch(Exception e){
			e.printStackTrace();
			result.put("flag", "1");
		}
	    return result;
	}
	
	/**
	 *保存合同签订节点生成的现场图片、身份证图片 
	 *@author zhanghao
	 *@create In 2016年04月21日
	 *@param  
	 *@return String 
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="savePhoto")
	// 图片未获取，docId没有抓到
	public Map<String,String> savePhoto(@RequestParam(value = "file", required = true)MultipartFile file,
	        String contractCode,PaperlessPhoto paperlessPhoto) throws IOException{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();    
		String webPath = request.getSession().getServletContext().getRealPath("/");
	    Map<String,String> param = new HashMap<String,String>();
	    Map<String,String> photoResult = new HashMap<String,String>();
	    param.put("relationId", paperlessPhoto.getRelationId());
	    PaperlessPhoto photo = paperlessPhotoService.findPaperlessPhoto(param); 
	    DocumentBean doc = null;
	    DmService dmService = null;
	    InputStream ins=file.getInputStream();
	    if(!ObjectHelper.isEmpty(file)){
	        String fileName = file.getOriginalFilename();
	        dmService = DmService.getInstance();
	        doc = dmService.createDocument("ORGINAL_"+System.currentTimeMillis()+fileName.substring(fileName.indexOf(".")),
	                file.getInputStream(), DmService.BUSI_TYPE_LOAN,CeFolderType.SIGN_UPLOAD.getName(),
	                contractCode, UserUtils.getUser().getId());
	    }
	    if("1".equals(paperlessPhoto.getPhotoType())){
	        paperlessPhoto.setIdPhotoId(doc.getDocId());
	        /*RecognizeResult result = RecognizeHelper.recognize("admin", file);
	        photoResult.put("idNumOCR", result.getCardnum());
	        photoResult.put("customerNameOCR", result.getName());*/
	    }
	    if("0".equals(paperlessPhoto.getPhotoType())){
	        paperlessPhoto.setSpotPhotoId(doc.getDocId());
	    }
	   if(ObjectHelper.isEmpty(photo)){
	        paperlessPhoto.preInsert();
	        paperlessPhotoService.savePaperlessPhoto(paperlessPhoto);
	    }else{
	        if("1".equals(paperlessPhoto.getPhotoType())){
	            Map<String,Object> validFlag = new HashMap<String,Object>();
	            validFlag.put("idValidFlag", YESNO.NO.getCode());
	            validFlag.put("customerId",paperlessPhoto.getRelationId());
                // 主借人身份证 更改为未验证
                if("0".equals(paperlessPhoto.getCustomerType())){
                    loanCustomerService.updatePaperlessMessage(validFlag);
                }else if("1".equals(paperlessPhoto.getCustomerType())){  // 共借人身份证 更改为未验证
                    loanCoborrowerService.updatePaperlessMessage(validFlag);
                }
                if(StringUtils.isNotEmpty(photo.getIdPhotoId())){
                    dmService.deleteDocument(photo.getIdPhotoId());
                }
	        }
	        if("0".equals(paperlessPhoto.getPhotoType()) && StringUtils.isNotEmpty(photo.getSpotPhotoId())){
                dmService.deleteDocument(photo.getSpotPhotoId());
            }
	        paperlessPhoto.preUpdate();
	        paperlessPhotoService.updatePaperlessPhoto(paperlessPhoto);
	    }
		   photoResult.put("docId", doc!=null?doc.getDocId():"");
		   File  file1 = new File(webPath+doc.getDocId()+".jpg");
		   if(!file1.exists()){
               file1.createNewFile();
           }
           OutputStream os = new FileOutputStream(file1);  
           int bytesRead = 0;  
           byte[] buffer = new byte[8192];  
           while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {  
              os.write(buffer, 0, bytesRead);  
           }  
           os.close();  
           ins.close(); 
	    return photoResult;
	}
	/**
	 *生成CA签字照 
	 *appSign==1   直接抓取  
	 *appSign==0 CA生成并抓取 
	 */
	@RequestMapping(value="getSignPhoto")
	// 分配卡号阶段调用
	public String getSignPhoto(String appSign,String signType,String pdfId,CaCustomerSign param){
	   if(ContractConstant.APP_SIGN.equals(appSign)){
	       //CaUtil.sign(signType, pdfId, param);
	   }else if(ContractConstant.NOT_APP_SIGN.equals(appSign)){
	       CaUtil.signCustomer(pdfId, param);
	   }
	    return "";
	}
	
	/**
	 *获取无纸化阶段缓存的图片
	 *@author zhanghao
	 *@Create in 2016年04月22日
	 *@param photoType
	 *@param customerId
	 *@return none 
	 * 
	 */
	@RequestMapping(value="getPreparePhoto")
	public void getPreparePhoto(HttpServletRequest request,HttpServletResponse response,String docId,String customerId){
	    OutputStream os = null;
	    String webPath = request.getSession().getServletContext().getRealPath("/");
        try {
            /**
             *如果docId不为空则抓取合成照片 
             *如果为空则查询paperlessPhoto表,查询签字照的文件ID
             * 
             */
            if(StringUtils.isEmpty(docId)){
               Map<String,String> param = new HashMap<String,String>();
               param.put("relationId", customerId);
               PaperlessPhoto photo = paperlessPhotoService.findPaperlessPhoto(param);
               docId = photo.getSignPhotoId();
            }
            DmService dmService = DmService.getInstance();
            InputStream in = dmService.downloadDocument(docId);
            
            
            /****************文件下载到服务器本地**********************/
            File  file1 = new File(webPath+docId+".jpg");
 		   	if(!file1.exists()){
                file1.createNewFile();
            }
            OutputStream os1 = new FileOutputStream(file1);  
            int bytesRead = 0;  
            byte[] buffer = new byte[8192];  
            while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {  
               os1.write(buffer, 0, bytesRead);  
            }  
            os1.close();
            in.close();
            InputStream ins=new FileInputStream(file1);
            /****************文件下载到服务器本地**********************/
            os = response.getOutputStream();
            com.creditharmony.dm.file.util.FileUtil.writeFile(os, ins);
            os.flush();
            os.close();
            os = null;
            ins.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 *证件照|现场照合成,并存储 
	 *@author zhanghao
	 * @throws IOException 
	 *@Create in 2016年04月22日 
	 * 
	 */
	@RequestMapping(value="saveComposePhotos")
	public void saveComposePhotos(String loanCode,String contractCode) throws IOException{
	    List<PaperlessPhoto> PaperlessPhotoList =  paperlessPhotoService.getByLoanCode(loanCode); 
	    DmService dmService = DmService.getInstance();
	    OutputStream os3 = null;
	    InputStream input1 = null;
	    InputStream input2 =null;
	    InputStream ceInput = null;
	    byte[] buf = new byte[1024*5];
	    for(PaperlessPhoto p:PaperlessPhotoList){
	        os3 = new ByteArrayOutputStream();
	        buf = new byte[1024*5];
	        input1 = dmService.downloadDocument(p.getIdPhotoId());
	        input2 = dmService.downloadDocument(p.getSpotPhotoId());
	        ImageUtils.xPic(input1, input2, os3);
	        os3.write(buf);
	        ceInput = new ByteArrayInputStream(buf);
	        DocumentBean doc = dmService.createDocument("COMPOSE_"+System.currentTimeMillis(),
	                ceInput, DmService.BUSI_TYPE_LOAN,CeFolderType.SIGN_UPLOAD.getName(),
	                contractCode, UserUtils.getUser().getId());
	        paperLessService.updateComposeDocId(p.getRelationId(),doc.getDocId(),p.getCustomerType());
	        os3.flush();
	        os3.close();
	        input1.close();
            input2.close();
            ceInput.close();
	        
	    }
	}
	
	/**
	 * 打开图片上传
	 * 2016年5月19日
	 * By 王彬彬
	 * @param model
	 * @param relationId
	 * @param photoType
	 * @param customerType
	 * @param loanCode
	 * @param contractCode
	 * @return
	 */
	@RequestMapping(value="openPhotoUpload")
	public String openPhotoUpload(Model model,String relationId,String photoType,String customerType,String loanCode,String contractCode){
	    model.addAttribute("relationId",relationId);
	    model.addAttribute("photoType",photoType);
	    model.addAttribute("customerType",customerType);
	    model.addAttribute("loanCode",loanCode);
	    model.addAttribute("contractCode", contractCode);
	    return "borrow/contract/openPhotoUpload";
	}
}
