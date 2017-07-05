/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.webPaperLessUtilsWeb.java
 * @Create By 王彬彬
 * @Create In 2016年4月18日 下午10:08:52
 */
package com.creditharmony.loan.car.carContract.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.CarExtendLoanSteps;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.SmsType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.IDCardMessageEx;
import com.creditharmony.loan.borrow.contract.service.PaperLessService;
import com.creditharmony.loan.borrow.contract.service.PaperlessPhotoService;
import com.creditharmony.loan.borrow.contract.view.PaperLessView;
import com.creditharmony.loan.car.common.service.CarImageService;
import com.creditharmony.loan.common.entity.CaCustomerSign;
import com.creditharmony.loan.common.utils.CaUtil;
import com.creditharmony.loan.common.utils.ImageUtils;
import com.creditharmony.loan.sms.entity.SmsTemplate;
import com.creditharmony.loan.sms.utils.SmsUtil;

/**
 * @Class Name PaperLessUtilsWeb
 * @author 葛志超
 * @Create In 2016年5月6日
 */
@Controller
@RequestMapping(value = "${adminPath}/carpaperless/confirminfo")
public class CarPaperLessUtilsWeb extends BaseController {
	
	@Autowired
	private PaperLessService paperLessService;
	
	@Autowired
	private PaperlessPhotoService paperlessPhotoService;
	
	@Autowired
    private  CarImageService  carImageService;

	/**
	 * 发送验证码
	 * 2016年5月5日
	 * By 葛志超
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
			calendar.add(Calendar.MONTH, 1);
			paperView.setConfirmTimeout(calendar.getTime());
			
 			if(customerType.equals("0"))
			{
				paperLessService.updateCustomerPinByLoanCode(paperView);	//更新主借人验证码
			}
			else
			{
				paperLessService.updateCarCustomerPinById(paperView); 		//更新共借人验证码
			}
			SmsTemplate template = paperLessService
					.getSmsTemplate(SmsType.HJ_CAR_PIN_SIGN);
			
			// 生成验证码短信
			String msg = template.getTemplateContent().replace("{#Pin#}", pin);
			
			// TODO 完成后取消注释 发送短信
			SmsUtil.sendSms(phone, msg);
			
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("loanCode", loanCode);//借款编码
			map.put("customerCode", customerCode);//借款编码or共借人Id
			map.put("customerName", customerName);//姓名
			
			paperLessService.saveSmsHis(map, msg, SmsType.HJ_CAR_PIN_SIGN);
			
			return pin;
		} catch (Exception e) {
			e.printStackTrace();
			return StringUtils.EMPTY;
		}
	}
	
	/**
	 * 更新验证状态 
	 * 
	 * 2016年5月5日
	 * By 葛志超
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
				paperLessService.updateCarCustomerPinById(paperView); // 更新共借人验证码
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
	   // 主借人身份证验证
	   CommonsMultipartFile idPhoto = null;
	   if("0".equals(customerType)){
	       result = paperLessService.validMainBorrower(idPhoto, idCardMessage);
	   }else if("1".equals(customerType)){  // 共借人身份证验证
	       result = paperLessService.validCarCoborrower(idPhoto, idCardMessage); 
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
	public String savePhoto(@RequestParam(value = "file", required = true)MultipartFile file,
	        String contractCode,PaperlessPhoto paperlessPhoto) throws IOException{
	    Map<String,String> param = new HashMap<String,String>();
	    param.put("relationId", paperlessPhoto.getRelationId());
	    PaperlessPhoto photo = paperlessPhotoService.findPaperlessPhoto(param); 
	    DocumentBean doc = null;
	    DmService dmService = null;
	    if(!ObjectHelper.isEmpty(file)){
	        String fileName = file.getOriginalFilename();
	        dmService = DmService.getInstance();
	        doc = dmService.createDocument("ORGINAL_"+System.currentTimeMillis()+fileName.substring(fileName.indexOf(".")),
	                file.getInputStream(), DmService.BUSI_TYPE_LOAN,CeFolderType.SIGN_UPLOAD.getName(),
	                contractCode, UserUtils.getUser().getId());
	    }
	    if("1".equals(paperlessPhoto.getPhotoType())){
	        paperlessPhoto.setIdPhotoId(doc.getDocId());
	    }
	    if("0".equals(paperlessPhoto.getPhotoType())){
	        paperlessPhoto.setSpotPhotoId(doc.getDocId());
	    }
	   if(ObjectHelper.isEmpty(photo)){
	        paperlessPhoto.preInsert();
	        paperlessPhotoService.savePaperlessPhoto(paperlessPhoto);
	    }else{
	        if("1".equals(paperlessPhoto.getPhotoType()) && StringUtils.isNotEmpty(photo.getIdPhotoId())){
	            dmService.deleteDocument(photo.getIdPhotoId());
	        }
	        if("0".equals(paperlessPhoto.getPhotoType()) && StringUtils.isNotEmpty(photo.getSpotPhotoId())){
                dmService.deleteDocument(photo.getSpotPhotoId());
            }
	        paperlessPhoto.preUpdate();
	        paperlessPhotoService.updatePaperlessPhoto(paperlessPhoto);
	    }
	    return null!=doc?doc.getDocId():null;
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
            os = response.getOutputStream();
            com.creditharmony.dm.file.util.FileUtil.writeFile(os, in);
            os.flush();
            os.close();
            os = null;
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
	
	@RequestMapping(value="openPhotoUpload")
	public String openPhotoUpload(Model model,String relationId,String photoType,String customerType,String loanCode,String contractCode){
	    model.addAttribute("relationId",relationId);
	    model.addAttribute("photoType",photoType);
	    model.addAttribute("customerType",customerType);
	    model.addAttribute("loanCode",loanCode);
	    model.addAttribute("contractCode", contractCode);
	    return "car/contract/openPhotoUpload";
	}
	

	@ResponseBody
	@RequestMapping(value="getImageUrl")
	public String getImageUrl(String loancode){
		String imageurl = carImageService.getExendImageUrl("20", loancode);
	    return imageurl;
	}
	
	@ResponseBody
	@RequestMapping(value="getExendImageUrl")
	public String getExendImageUrl(String loancode,String status){
		String imageurl = carImageService.getExendImageUrl(CarExtendLoanSteps.parseByCode(status).getName(), loancode);
		return imageurl;
	}
	
	@ResponseBody
	@RequestMapping(value="getLargeAmountImageUrl")
	public String getLargeAmountImageUrl(String loancode){
		String imageurl = carImageService.getLargeAmountImageUrl("20", loancode);
	    return imageurl;
	}
	
	
	/**
	 *身份证手动校验
	 *@author 葛志超 
	 *@Create In 2016年08月10日
	 *@param idCardMessage 
	 *@return String
	 * 
	 */
	@RequestMapping(value="artificialIDCard")
	@ResponseBody
	// 公安对接还没有
	public Map<String,String> artificialIDCard(IDCardMessageEx idCardMessage){
	   String customerType = idCardMessage.getCustomerType();
	   Map<String,String> result = null;
	   // 主借人身份证验证
	   CommonsMultipartFile idPhoto = null;
	   if("0".equals(customerType)){
	       result = paperLessService.artificiaMainBorrower(idCardMessage);
	   }else if("1".equals(customerType)){  // 共借人身份证验证
	       result = paperLessService.artificiaCarCoborrower(idCardMessage); 
	   }
	   
	    return result;
	}
	
}
