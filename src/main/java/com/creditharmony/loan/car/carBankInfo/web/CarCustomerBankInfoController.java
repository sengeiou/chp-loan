package com.creditharmony.loan.car.carBankInfo.web;

import static com.creditharmony.loan.car.common.util.CryptoUtils.decryptPhones;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ListUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.MaintainStatus;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.account.constants.DownLoadFileType;
import com.creditharmony.loan.borrow.account.constants.FileType;
import com.creditharmony.loan.borrow.account.service.RepayAccountService;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carBankInfo.view.CarCustomerBankInfoView;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.type.LoanDictType;

/**
 * 车借还款账号管理
 * @Class Name RepayAccountController
 * @Create In 2016年7月4日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/carBankInfo/carCustomerBankInfo")
public class CarCustomerBankInfoController extends BaseController {

	@Autowired
	CarCustomerBankInfoService carBankInfoService;
	@Autowired
	RepayAccountService repayAccountService;
	@Autowired
	CityInfoService cityInfoService;
	
	/**
	 * 车借还款账号维护申请
	 * @param req
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@RequestMapping(value = "/accountMaintainList")
	public String accountMaintainList(HttpServletRequest request, HttpServletResponse response,
				Model model, CarCustomerBankInfoView info){
		info.setLoanStatus("'"+CarLoanStatus.REPAYMENT_IN.getCode()+"',"+
				"'"+CarLoanStatus.REPAYMENT_APPLICATION.getCode()+"'");
		if(StringUtils.isEmpty(info.getContractCode()) && 
				StringUtils.isEmpty(info.getCustomerName()) &&
				StringUtils.isEmpty(info.getCustomerCertNum())){
			info.setMaintainStatusArray("-1");
		}
		Page<CarCustomerBankInfoView> bankInfoList = carBankInfoService.getCarCustomerBankInfoList(
				new Page<CarCustomerBankInfoView>(request, response), info);
		//页面转码
		Map<String,Dict> dictMap   = DictCache.getInstance().getMap();
		for (CarCustomerBankInfoView ex : bankInfoList.getList()) {
			ex.setCardBank(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, ex.getCardBank()));
			ex.setDictMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, ex.getDictMaintainStatus()));
		}
		info.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
		if(StringUtils.isEmpty(info.getMaintainStatusArray()))
		{
			List<CarCustomerBankInfoView> s= carBankInfoService.getCarCustomerBankInfoList(info);
			if(ListUtils.isEmptyList(s) ){
				info.setDictMaintainStatus(MaintainStatus.TO_MAINTAIN.getCode());
			}
		}
		List<CityInfo> provinceList = cityInfoService.findProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("bankInfoList", bankInfoList);
		model.addAttribute("info", info);
		return "car/carBankInfo/bankInfoMaintainList";
	}

	/**
	 * 车借还款账户新增页面跳转
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/newAccount")
	public CarCustomerBankInfoView newAccount(Model model, CarCustomerBankInfoView info){
		CarCustomerBankInfoView customerMsg = carBankInfoService.getCustomerMsg(info.getLoanCode());
		model.addAttribute("customerMsg", customerMsg);
		return customerMsg;
	}

	/**
	 * 新建车借还款账号
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/saveAccountMessage")
	public String saveAccountMessage(CarCustomerBankInfoView info, HttpServletRequest 
				request, @RequestParam(value = "file", required = true)MultipartFile file){
		DmService dmService = DmService.getInstance();
		InputStream in = null;
		try {
			in = file.getInputStream();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		DocumentBean documentBean = dmService.createDocument(file
				.getOriginalFilename(), in, DmService.BUSI_TYPE_LOAN,
				CeFolderType.ACCOUNT_CHANGE.getName(), info
						.getLoanCode(), UserUtils.getUser().getId());
		info.setFileId(documentBean.getDocId());
		info.setFileName(file.getOriginalFilename());
		//设置置顶值为0，不置顶
		info.setTop(MaintainType.ADD.getCode());
		//设置维护类型为 新建
		info.setDictMaintainType(MaintainType.ADD.getCode());
		//设置维护状态为"待审核"
		info.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
		carBankInfoService.insertBankInfo(info);
		return "redirect:" + adminPath + "/car/carBankInfo/carCustomerBankInfo/accountMaintainList";
	}

	/**
	 * 车借还款
	 * 账号编辑页面
	 * @param CarCustomerBankInfoView
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editGoldAccount")
	public CarCustomerBankInfoView editGoldAccount(CarCustomerBankInfoView info, HttpServletRequest request){
		info = carBankInfoService.getBankInfoMsg(info.getId());
		if(StringUtils.isNotEmpty(info.getLoanCode())){
			List<CarCustomerBankInfoView> coborrowers = carBankInfoService.getCoborrowerList(info.getLoanCode());
			if(ListUtils.isNotEmptyList(coborrowers)){
				CarCustomerBankInfoView v = coborrowers.get(0);
				info.setCoboId(v.getCoboId());
				info.setCoboName(v.getCoboName());
				info.setCoboCertNum(v.getCoboCertNum());
				info.setCoboMobile(v.getCoboMobile());
				info.setCoboEmail(v.getCoboEmail());
			}
		}
		info.setCardBankName(DictCache.getInstance().getDictLabel("jk_open_bank",info.getCardBank()));
		info.setCityList(cityInfoService.findProvince());
		return info;
	}
	

	/**
	 * 车借还款账号
	 * 修改手机号
	 * 修改银行卡号
	 * 修改邮箱
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/updateBankInfoData")
	public String updateBankInfoData(CarCustomerBankInfoView info, HttpServletRequest 
				request, @RequestParam(value = "file", required = true)MultipartFile file){
		DmService dmService = DmService.getInstance();
		InputStream in = null;
		try {
			in = file.getInputStream();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		DocumentBean documentBean = dmService.createDocument(file
				.getOriginalFilename(), in, DmService.BUSI_TYPE_LOAN,
				CeFolderType.ACCOUNT_CHANGE.getName(), info
						.getLoanCode(), UserUtils.getUser().getId());
		info.setFileId(documentBean.getDocId());
		info.setFileName(file.getOriginalFilename());
		//updateType(1、修改手机号 2、修改银行卡号 3、修改邮箱)
		if(info.getUpdateType().equals(MaintainStatus.TO_REFUSE.getCode())){
			carBankInfoService.saveNewCjBankInfoPhone(info);
		}else if(info.getUpdateType().equals(MaintainStatus.TO_MAINTAIN.getCode())){
			carBankInfoService.saveNewCjBankInfoAccount(info);
		}else if(info.getUpdateType().equals(MaintainStatus.TO_FIRST.getCode())){
			carBankInfoService.saveNewCjBankInfoEmail(info);
		}
		return "redirect:" + adminPath + "/car/carBankInfo/carCustomerBankInfo/accountMaintainList";
	}

	/**
	 * 车借还款
	 * 账户置顶
	 * @param repayAccountApplyView
	 */
	@ResponseBody
	@RequestMapping(value = "/setTop")
	public void setTop(CarCustomerBankInfoView info){
		carBankInfoService.updateAccountTop(info);
	}
	
//----------------------------------车借还款账号已办列表--------------------------------------\\
	/**
	 * 车借还款账号
	 * 已办列表
	 * @param model
	 * @param CarCustomerBankInfoView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/accountHandleList")
	public String accountHandleList(Model model, CarCustomerBankInfoView info,
					HttpServletRequest request, HttpServletResponse response){
	    String maintainStatus = "'"+MaintainStatus.TO_CHECK.getCode()+"','" +
	    							MaintainStatus.TO_REFUSE.getCode()+"','" + 
	    							MaintainStatus.TO_MAINTAIN.getCode()+"'";
	    info.setMaintainStatusArray(maintainStatus);
		Page<CarCustomerBankInfoView> bankInfoList = carBankInfoService.getBankInfoList(
				new Page<CarCustomerBankInfoView>(request, response), info);
//		//页面转码
		Map<String,Dict> dictMap   = DictCache.getInstance().getMap();
		for (CarCustomerBankInfoView ex : bankInfoList.getList()) {
			ex.setLoanStatus(DictUtils.getLabel(dictMap,LoanDictType.CAR_LOAN_STATUS, ex.getLoanStatus()));
			ex.setDictMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, ex.getDictMaintainType()));
			ex.setDictMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, ex.getDictMaintainStatus()));
		}
		model.addAttribute("page", bankInfoList);
		List<Map<String,Object>> billDay = repayAccountService.getBillDay();
		model.addAttribute("billDayList", billDay);
		model.addAttribute("bankInfo", info);
		return "car/carBankInfo/bankInfoHandleList";
	}
	
//----------------------------------还款账号维护审核-------------------------------------------\\
	/**
	 * 获取还款维护账户审核列表
	 * @param model
	 * @param CarCustomerBankInfoView
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/accountManageList")
	public String accountManageList(Model model, CarCustomerBankInfoView info,
					HttpServletRequest request, HttpServletResponse response){
	    String maintainStatus = "'"+MaintainStatus.TO_CHECK.getCode()+"','"+
	    		MaintainStatus.TO_REFUSE.getCode()+"','"+
	    		MaintainStatus.TO_MAINTAIN.getCode()+"'";
	    info.setMaintainStatusArray(maintainStatus);
		Page<CarCustomerBankInfoView> bankInfoList = carBankInfoService.getBankInfoList(
				new Page<CarCustomerBankInfoView>(request, response), info);
//		//页面转码
		Map<String,Dict> dictMap   = DictCache.getInstance().getMap();
		for (CarCustomerBankInfoView ex : bankInfoList.getList()) {
			ex.setLoanStatus(DictUtils.getLabel(dictMap,LoanDictType.CAR_LOAN_STATUS, ex.getLoanStatus()));
			ex.setDictMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, ex.getDictMaintainType()));
			ex.setDictMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, ex.getDictMaintainStatus()));
		}
		model.addAttribute("page", bankInfoList);
		List<Map<String,Object>> billDay = repayAccountService.getBillDay();
		model.addAttribute("billDayList", billDay);
		model.addAttribute("bankInfo", info);
		return "car/carBankInfo/bankInfoManageList";
	}

	/**
	 * 还款账户维护审核列表
	 * 操作-审核跳转审核页面
	 * 
	 * @param repayAccountApplyView
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/examineMessage")
	public CarCustomerBankInfoView examineMessage(CarCustomerBankInfoView info, 
			HttpServletRequest request, HttpServletResponse response){
		info.setDictMaintainStatus(MaintainStatus.TO_CHECK.getCode());
		Page<CarCustomerBankInfoView> bankInfoList = carBankInfoService.getBankInfoList(
				new Page<CarCustomerBankInfoView>(request, response), info);
		CarCustomerBankInfoView infoView = bankInfoList.getList().get(0);
		//客户账号原数据
		if(StringUtils.isNotEmpty(infoView.getOldBankAccountId())){
			CarCustomerBankInfoView v = carBankInfoService.getOldMsg(infoView.getOldBankAccountId());
			if(null != v){
				infoView.setOldApplyBankName(v.getOldApplyBankName());
				infoView.setOldBankSigningPlatform(v.getOldBankSigningPlatform());
				infoView.setOldBankCardNo(v.getOldBankCardNo());
				infoView.setOldPhone(v.getOldPhone());
				infoView.setOldEmail(v.getOldEmail());
			}
		}
		//共借人原数据
		if(StringUtils.isNotEmpty(infoView.getCoboId())){
			CarCustomerBankInfoView v = carBankInfoService.getOldCoborrowerMsg(infoView.getCoboId());
			if(null != v){
				infoView.setOldCoboMobile(v.getOldCoboMobile());
				infoView.setOldCoboEmail(v.getOldCoboEmail());
			}
		}
//		//页面转码
		Map<String,Dict> dictMap   = DictCache.getInstance().getMap();
		infoView.setLoanStatus(DictUtils.getLabel(dictMap,LoanDictType.CAR_LOAN_STATUS, infoView.getLoanStatus()));
		infoView.setDictMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, infoView.getDictMaintainType()));
		infoView.setDictMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, infoView.getDictMaintainStatus()));
		infoView.setBankSigningPlatformName(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, infoView.getBankSigningPlatform()));
		infoView.setCardBankName(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, infoView.getCardBank()));
		infoView.setUpdateTypeName(DictUtils.getLabel(dictMap,LoanDictType.CAR_UPDATE_TYPE, infoView.getUpdateType()));
		return infoView;
	}

	/**
	 * 下载文件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		try {
			name = new String(name.getBytes(), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("iso8859-1");
		response.addHeader("Content-Disposition", "attachment;filename=" + name);
		DmService dmService = DmService.getInstance();
		try {
			dmService.download(response.getOutputStream(), id);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
 	}
	
	/**
	 * 还款账户维护审核列表
	 * 审核确认
	 * @param CarCustomerBankInfoView
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/saveExamineResult")
	public String saveExamineResult(CarCustomerBankInfoView info, HttpServletRequest request){
		carBankInfoService.updateBankInfoCheckMsg(info);
		return "redirect:" + adminPath + "/car/carBankInfo/carCustomerBankInfo/accountManageList";
	}

	/**
	 * 显示维护历史
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@RequestMapping(value = "/showMaintainHistory")
	public String showMaintainHistory(Model model, CarCustomerBankInfoView view){
		List<CarCustomerBankInfoView> list = carBankInfoService.getHistoryList(view);
		for(CarCustomerBankInfoView v : list){
			if(("修改手机号").equals(v.getOperateStep())){
				Pattern p = Pattern.compile("(###(.*?)###)"); 
				Matcher m = p.matcher(v.getNewData()); 
				int i=0;
				while(m.find()){ 
					if(i == 0){
						v.setNewData(v.getNewData().replace(m.group(1), decryptPhones(m.group(2),"t_cj_customer_bank_info","new_customer_phone")));
					}
					if(i == 1){
						v.setNewData(v.getNewData().replace(m.group(1), decryptPhones(m.group(2),"t_cj_customer_bank_info_add","cobo_mobile")));
					}
					i++;
				}
				
				m = p.matcher(v.getOldData()); 
				i = 0;
				while(m.find()){ 
					if(i == 0){
						v.setOldData(v.getOldData().replace(m.group(1), decryptPhones(m.group(2),"T_JK_LOAN_CUSTOMER","customer_phone_first")));
					}
					if(i == 1){
						v.setOldData(v.getOldData().replace(m.group(1), decryptPhones(m.group(2),"t_cj_customer_bank_info_add","cobo_mobile")));
					}
					i++;
				}
			}
		}
		model.addAttribute("list", list);
		return "car/carBankInfo/bankInfoShowLog";
	}

	/**
	 * 车借还款维护账户
	 * 查看
	 * @param model
	 * @param repayAccountApplyView
	 * @return
	 */
	@RequestMapping(value = "/showMessage")
	public String showMessage(CarCustomerBankInfoView info,Model model,HttpServletRequest request, HttpServletResponse response){
		Page<CarCustomerBankInfoView> bankInfoList = carBankInfoService.getBankInfoList(
				new Page<CarCustomerBankInfoView>(request, response), info);
		CarCustomerBankInfoView infoView = bankInfoList.getList().get(0);
//		//页面转码
		Map<String,Dict> dictMap   = DictCache.getInstance().getMap();
		infoView.setLoanStatus(DictUtils.getLabel(dictMap,LoanDictType.CAR_LOAN_STATUS, infoView.getLoanStatus()));
		infoView.setDictMaintainTypeName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_TYPE, infoView.getDictMaintainType()));
		infoView.setDictMaintainStatusName(DictUtils.getLabel(dictMap,LoanDictType.MAINTAIN_STATUS, infoView.getDictMaintainStatus()));
		infoView.setBankSigningPlatform(DictUtils.getLabel(dictMap,LoanDictType.DEDUCT_PLAT, infoView.getBankSigningPlatform()));
		infoView.setCardBankName(DictUtils.getLabel(dictMap,LoanDictType.OPEN_BANK, infoView.getCardBank()));
		infoView.setUpdateTypeName(DictUtils.getLabel(dictMap,LoanDictType.CAR_UPDATE_TYPE, infoView.getUpdateType()));
		model.addAttribute("infoView", infoView);
		return "car/carBankInfo/bankInfoShowMessage";
	}

	/**
	 * 资料包下载
	 * @param fileType
	 * @return
	 */
	@RequestMapping(value = "/fileDownLoad")
	public String fileDownLoad(String fileType, HttpServletRequest request,HttpServletResponse response) {
		ServletContext servletContext = request.getSession().getServletContext(); 
		//获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载  
        String path = servletContext.getRealPath("/");  
		try {
			String fileName = FileType.JCRBG.getName();
			//新建、修改手机号码、修改邮箱地址 变更模板
			if (DownLoadFileType.JCRBG.equals(fileType)) {
				path = path + "/static/carBankInfo/6.pdf";
			}
			//修改银行卡号 变更模板
			if (DownLoadFileType.JCRBGYH.equals(fileType)) {
				path = path + "/static/carBankInfo/7.zip";
			}
			// path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
			response.addHeader("Content-disposition", "attachment;filename="
					+ new String((fileName + "."+ext).getBytes("gbk"),
							"ISO-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
