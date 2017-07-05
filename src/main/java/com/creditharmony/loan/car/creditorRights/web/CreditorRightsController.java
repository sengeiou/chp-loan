package com.creditharmony.loan.car.creditorRights.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.BigDecimalTools;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.claim.dto.SyncClaim;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.fortune.type.CreditSrc;
import com.creditharmony.core.fortune.type.CreditState;
import com.creditharmony.core.fortune.type.Node;
import com.creditharmony.core.fortune.type.ZjtrMark;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanType;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.sync.data.util.SyncDataTypeUtil;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carApply.ex.CoborrowerCreditJson;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carContract.service.CarContractService;
import com.creditharmony.loan.car.carContract.view.CarRateCheckLaunchView;
import com.creditharmony.loan.car.common.entity.CarGrantCommon;
import com.creditharmony.loan.car.creditorRights.entity.Coborrower;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRightsImport;
import com.creditharmony.loan.car.creditorRights.service.CreditorRightsService;
import com.creditharmony.loan.car.creditorRights.view.CreditorLog;
import com.creditharmony.loan.car.creditorRights.view.CreditorRightView;
import com.creditharmony.loan.common.dao.MiddlePersonDao;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.exception.LoanImportXlsException;
import com.creditharmony.loan.common.service.NumberMasterService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.creditharmony.loan.sync.data.fortune.ForuneSyncCreditorService;
import com.google.common.collect.Lists;


@Controller
@RequestMapping(value="${adminPath}/car/creditorRight")
public class CreditorRightsController extends BaseController {
	
	@Autowired
	CreditorRightsService service;
	@Autowired
	private MiddlePersonDao middlePersonDao;
	@Autowired
	private ForuneSyncCreditorService foruneSyncCreditorService;
	@Autowired
	private CarContractService carContractService;
	@Autowired
    private NumberMasterService numberMasterService;
	@Autowired
	private CarLoanInfoService carLoanInfoService;
	
	@RequestMapping(value="list")
	public String list(@ModelAttribute("creditorRightView")CreditorRightView view,HttpServletRequest request,HttpServletResponse response,Model model){
		if(null == view.getStatus()){
			view.setStatus(Global.NO);
		}
		Page<CreditorRightView> page = service.list(new Page<CreditorRightView>(request, response), view);  
		model.addAttribute("page", page);
		return "car/creditorRights/creditorRightList";
	}
	
	@ResponseBody
	@RequestMapping(value="settle")
	public String settle(String id,String settleType,String channelType){
		boolean flag = true;
		CreditorRightView creditorRightView = new CreditorRightView();
		creditorRightView.setId(id);
		List<SyncClaim> syncClaimList = service.querySendFortune(creditorRightView);
		if(null != syncClaimList && syncClaimList.size()==1){
			if(!CarLoanThroughFlag.P2P.getName().equals(channelType)){//财富的数据需要调用接口处理
				flag = foruneSyncCreditorService.executeSyncEarlySettlement(syncClaimList.get(0));
			}
			if(flag){
				service.settleCarCreditor(id, syncClaimList.get(0).getLoanCode(),settleType);
			}
		}
		return "sucess";
	}
	
	@RequestMapping(value="toAddPage")
	public String toAddPage(@ModelAttribute("creditorRight")CreditorRights entity,Model model){
		CarRateCheckLaunchView rateView = new CarRateCheckLaunchView();
    	MiddlePerson middlePerson = new MiddlePerson();
    	List<MiddlePerson> middlePersons = middlePersonDao.selectMiddlePerson(middlePerson);
    	rateView.setMiddlePersons(middlePersons);
    	model.addAttribute("mp", rateView);
		model.addAttribute("hasCobo", Global.NO);
		return "car/creditorRights/creditorRightAdd";
	}
	
	@ResponseBody
	@RequestMapping(value="addCoborrower")
	public String addCoborrower(CreditorRights creditor,CoborrowerCreditJson json){
		List<Map<String,Object>> paramList = Lists.newArrayList();
		if(creditor!=null){
    		String loanCode = service.getCjContractLoanCode(creditor.getContractCode());
    		if(null == loanCode){
    			loanCode = numberMasterService.getLoanNumber(SerialNoType.LOAN);
    		}
    		creditor.setLoanCode(loanCode);
			service.saveCreditorRights(creditor,paramList,json);
		}
		return "true";
	}
	
	@RequestMapping(value="add")
	public String add(@ModelAttribute("creditorRight")CreditorRights entity){
//		List<SyncClaim> syncClaimList = service.querySendFortune(creditorRightView);
//		if(syncClaimList !=null && syncClaimList.size()>0){
//			throw new LoanImportXlsException("合同编号已存在，");
//		}
//		String loanCode = service.getCjContractLoanCode(cr.getContractCode());
//		if(null != loanCode){
//			loanCode = numberMasterService.getLoanNumber(SerialNoType.LOAN);
//		}
//		cr.setLoanCode(loanCode);
		service.insert(entity);
		return "redirect:" + adminPath + "/car/creditorRight/list";
	}
	
	@RequestMapping(value="toModifyPage/{id}")
	public String toModifyPage(@ModelAttribute("creditorRight")CreditorRights entity,@PathVariable("id")String id,Model model){
		CreditorRightView creditRight = service.getCreditRight(id);
		String customName = creditRight.getLoanCustomerName();
		String customCert = creditRight.getCustomerCertNum();
		if(null != customName && customName.split(";").length>0){
			creditRight.setLoanCustomerName(customName.split(";")[0]);
			creditRight.setCustomerCertNum(customCert.split(";")[0]);
			List<Coborrower> coborrowers = Lists.newArrayList();
			for(int i=1;i<customName.split(";").length;i++){
				Coborrower cobo = new Coborrower();
				cobo.setLoanCustomerName(customName.split(";")[i]);
				cobo.setCustomerCertNum(customCert.split(";")[i]);
				coborrowers.add(cobo);
			}
			model.addAttribute("hasCobo", Global.YES);
			model.addAttribute("coborrowers", coborrowers);
		}else{
			model.addAttribute("hasCobo", Global.NO);
		}
		//债权信息-编辑 获取共同借款人数据
//		List<SyncClaim> coborrowers= service.getCoborrowerData(id);
//		model.addAttribute("coborrowers", coborrowers);
		model.addAttribute("creditRight", creditRight);
		return "car/creditorRights/creditorRightAdd";
	}

	@RequestMapping(value="modify")
	public String modify(@ModelAttribute("creditorRight")CreditorRights entity){
		entity.preUpdate();
		service.update(entity);
		return "redirect:" + adminPath + "/car/creditorRight/list";
	}
	
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "batchImport")
	public String batchImport(HttpServletRequest request,
			String returnCheckFlag, HttpServletResponse response,
			@RequestParam MultipartFile file,RedirectAttributes redirectAttributes) {
		ExcelUtils excelutil = new ExcelUtils();
		StringBuilder failureMsg = new StringBuilder();
		int successNum = 0;
		int failureNum = 0;
		int rowNum = 0;
			List<CreditorRightsImport> datalist = (List<CreditorRightsImport>) excelutil
				.importExcel(LoanFileUtils.multipartFile2File(file), 0, 0,
						CreditorRightsImport.class);
			if (!ArrayHelper.isNotEmpty(datalist)) {
				return  "上传文件没有数据";
			}
		    //加载字典
			List<CreditorRightsImport> datas = Lists.newArrayList();
//			List<Coborrower> coborrowers = Lists.newArrayList();
        	for(CreditorRightsImport cr:datalist){
        		rowNum++;
        		try {
        			excelutil.validateField(CreditorRightsImport.class, cr);
        			CreditorRightView creditorRightView = new CreditorRightView();
        			creditorRightView.setContractCode(cr.getContractCode());
        			List<SyncClaim> syncClaimList = service.querySendFortune(creditorRightView);
        			if(syncClaimList !=null && syncClaimList.size()>0){
        				throw new LoanImportXlsException("合同编号已存在，");
        			}
	        		String loanCode = service.getCjContractLoanCode(cr.getContractCode());
	        		if(null == loanCode){
	        			loanCode = numberMasterService.getLoanNumber(SerialNoType.LOAN);
	        		}
	        		cr.setLoanCode(loanCode);
	        		cr.preInsert();
//	        		String customName = cr.getLoanCustomerName();
//	        		String certNum = cr.getCustomerCertNum();
//	        		if(null != customName && customName.split(";").length>0){
//	        			cr.setLoanCustomerName(customName.split(";")[0]);
//	        			cr.setCustomerCertNum(certNum.split(";")[0]);
//	        			for(int i=1;i<customName.split(";").length;i++){
//	        				Coborrower coborrower = new Coborrower();
//	        				coborrower.setLoanCustomerName(customName.split(";")[i]);
//	        				coborrower.setCustomerCertNum(certNum.split(";")[i]);
//	        				coborrower.setCreditor(cr.getId());
//	        				coborrower.preInsert();
//	        				coborrowers.add(coborrower);
//	        			}
//	        		}
	        		//设置证件类型为'身份证'(没找到证件类型字典，只能先找一个替代的了)
	        		cr.setCertType(LoanType.HONOUR_LOAN.getCode());
	        		//导入Excel列中文需要转为字典
	        		cr.setCreditorRigthSource(CreditSrc.CJ.value);
	        		cr.setProductType(CarLoanProductType.parseByName(cr.getProductType()).getCode());
	        		cr.setRightsType(CreditorRights.RIGHTS_TYPE_INPUT);
	        		cr.setLoanStatus(CarLoanStatus.REPAYMENT_IN.getCode());
	        		cr.setIssendWealth(Global.NO);
//	        		cr.setContractDay(sdf.format(cr.getContractDay()+""));
//	        		cr.setContractEndDay(sdf.parse(cr.getContractEndDay()+""));
//	        		cr.setDownPaymentDay(sdf.parse(cr.getDownPaymentDay()+""));
//	        		cr.setCreateTime(sdf.parse(cr.getCreateTime()+""));
//	        		cr.setModifyTime(sdf.parse(cr.getModifyTime()+""));
	        		successNum++;
	        		datas.add(cr);
        		}catch(LoanImportXlsException ex){  
    	        	failureMsg.append("序号为"+rowNum+" 导入失败："+ex.getMessage()+";\n");
                    failureNum++;  
    	        }catch (Exception e) {
    	        	e.printStackTrace();
    			}
        	 }
        	if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条数据，导入信息如下：\n");
			}
        	if(datas.size()>0){
        		service.insertBatch(datas);
        	}
			return  "已成功导入 "+successNum+" 条数据"+failureMsg;
	}
	
	@ResponseBody
	@RequestMapping(value="batchSend")
	public String batchSend(CreditorRightView creditorRightView,String idVal){
		creditorRightView.setRightsType(CreditorRights.RIGHTS_TYPE_INPUT);
		creditorRightView.setStatus(YESNO.NO.getCode());
		List<String> list = Lists.newArrayList();
		List<SyncClaim> syncClaimList = null;
			if (StringUtils.isNotEmpty(idVal)) {
				if (idVal.indexOf(",") != CarGrantCommon.ONE) {
					String[] loanCodes = idVal.split(",");
					for(String str:loanCodes){
						list.add(str);
					}
				} else {
					list.add(idVal);
				}
				syncClaimList = service.querySendFortuneByLoanCode(list);
			}else{
				syncClaimList = service.querySendFortune(creditorRightView);
			}
			for (SyncClaim syncClaim:syncClaimList) {
				boolean flag = false;
				CreditorRights creditorRights = new CreditorRights();
				creditorRights.setLoanCode(syncClaim.getLoanCode());
				 if(CarLoanThroughFlag.HARMONY.getCode().equals(syncClaim.getChannelType())){//渠道为财富推送财富债权
					 syncClaim.setSyncType(SyncDataTypeUtil.VALUE_ADD);
					 syncClaim.setDictLoanType(LoanType.CAR_LOAN.getCode());
						// 是否可用，1可用，0不可用，2冻结
					 syncClaim.setDictLoanFreeFlag(CreditState.KY.value);
					 syncClaim.setLoanValueYear(BigDecimalTools.mul(syncClaim.getLoanMonthRate(), (12+""))+"");
					 // 资金托管标识，0托管，空非托管
					 if (ChannelFlag.TG.getCode().equals(syncClaim.getLoanTrusteeFlag())) {
						syncClaim.setLoanTrusteeFlag(ZjtrMark.TG.value);
					 }else {
						syncClaim.setLoanTrusteeFlag(ZjtrMark.FTG.value);
					 }
					 // 月满盈标志，1月满盈，0非月满盈
					 syncClaim.setLoanTrusteeFlag(Node.YMYKY.value);
					 syncClaim.setSyncTableName("borrow33");
					 // 最后编辑时间
					 syncClaim.setLoanModifiedDay(new Date());
					 syncClaim.setModifyBy(UserUtils.getUser().getId());
					 syncClaim.setModifyTime(new Date());
					 flag = foruneSyncCreditorService.executeSyncLoan(syncClaim);
				  }else if(CarLoanThroughFlag.P2P.getCode().equals(syncClaim.getChannelType())){//渠道为p2p推送大金融列表
					  flag = true;
					  creditorRights.setRightsType(CreditorRights.RIGHTS_TYPE_INPUT);
					  creditorRights.setCreditType(CreditorRights.CREDIT_TYPE_HASLOAD);
				  }
				  service.updateIssendWealth(flag, creditorRights);
			}
		return "true";
	}
	

	/** 
	 * 
	 * @param logId 历史表iD
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="getCalCreLog")
	public String getCalCreLog(String loanCode,HttpServletRequest request,HttpServletResponse response,Model model){
		Page<CreditorLog> page = service.getCreLog(new Page<CreditorLog>(request, response), loanCode);  
		model.addAttribute("page", page);
		return "car/creditorRights/creditorLogList";
	}
}
