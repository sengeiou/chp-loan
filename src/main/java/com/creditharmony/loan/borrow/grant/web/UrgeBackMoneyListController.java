package com.creditharmony.loan.borrow.grant.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.UrgeRepay;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.grant.dto.UrgeBackMoneyNotify;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeBackMoneyEx;
import com.creditharmony.loan.borrow.grant.service.UrgeBackMoneyService;
import com.creditharmony.loan.borrow.grant.service.UrgeServicesBackMoneyService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.type.LoanDictType;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;
import com.google.common.collect.Lists;

/**
 * 催收服务费费返款操作
 * @Class Name UrgeBackMoneyListController
 * @author 张振强
 * @Create In 2016年1月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/urgeBackList")
public class UrgeBackMoneyListController extends BaseController {
	
	@Autowired
	private UrgeBackMoneyService urgeBackMoneyService;
	@Autowired							 
	private UrgeServicesBackMoneyService urgeServicesBackMoneyService;
	
	/**
	 * 催收返还列表初始化，查询的状态为：待返款，已返款
	 * 2015年12月24日
	 * By 张振强
	 * @param model
	 * @param urgeBackMoneyEx
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value="urgeBackListInfo")
    public String  urgeBackListInfo(Model model,UrgeBackMoneyEx urgeBackMoneyEx,
    		HttpServletRequest request,HttpServletResponse response){
		// 初始化时的返款状态为：待返款，已返款
		setStoreId(urgeBackMoneyEx);
		//不显示ZCJ数据
		urgeBackMoneyEx.setChannelFlag(ChannelFlag.ZCJ.getCode());
		Page<UrgeBackMoneyEx> urgeListPage = urgeBackMoneyService
				.selectBackMoneyList(new Page<UrgeBackMoneyEx>(request,
						response), urgeBackMoneyEx);
		Map<String,Dict> dictMap = null;
		if (ArrayHelper.isNotEmpty(urgeListPage.getList())) {
			dictMap = DictCache.getInstance().getMap();
			for(UrgeBackMoneyEx ub:urgeListPage.getList()){
				ub.setDictPayStatusLabel(DictUtils.getLabel(dictMap,LoanDictType.URGE_REPAY_STATUS, ub.getDictPayStatus()));
				ub.setDictPayResultLabel(DictUtils.getLabel(dictMap,LoanDictType.PAYBACK_FEE_RESULT, ub.getDictPayResult()));
			  }
		}
		model.addAttribute("urgeListPage", urgeListPage);
		model.addAttribute("UrgeBackMoneyEx", urgeBackMoneyEx);
    	return "borrow/grant/urgeBackList";
    }
	
	/**
	 * 设置门店组织机构和开户行搜索
	 * 2016年7月7日
	 * By 朱静越
	 * @param urgeServicesMoneyEx
	 */
	public void setStoreId(UrgeBackMoneyEx urgeServicesMoneyEx) {
		if (ObjectHelper.isEmpty(urgeServicesMoneyEx.getStoresCode()) || urgeServicesMoneyEx.getStoresCode()[0].equals("")) {	
			urgeServicesMoneyEx.setStoresCode(null);
		}
		if (ObjectHelper.isEmpty(urgeServicesMoneyEx.getBankNameCode()) || urgeServicesMoneyEx.getBankNameCode()[0].equals("")) {	
			urgeServicesMoneyEx.setBankNameCode(null);
		}
	}
	
	
	/**
	 * 导出催收服务费返款
	 * 2015年12月24日
	 * By 张振强
	 * @param request
	 * @param response
	 * @param checkVal
	 * @return none
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="exportBackList")
	public String exportBackList(HttpServletRequest request,
			UrgeBackMoneyEx urgeListRe, HttpServletResponse response,
			String checkVal, RedirectAttributes redirectAttributes) {
		ExcelUtils excelUtil = new ExcelUtils();
		List<UrgeBackMoneyEx> exportUrgeBackList = new ArrayList<UrgeBackMoneyEx>();
		boolean success = true;
		try {
			setStoreId(urgeListRe);
			// 默认为空时，根据查询条件进行导出
			if (StringUtils.isEmpty(checkVal)) {
				exportUrgeBackList = urgeBackMoneyService.selectBackMoneyListNo(urgeListRe);
			}else {
				String[] ids = checkVal.split(",");
				// 有选中的单子时
				for (int i = 0; i < ids.length; i++) {
					UrgeBackMoneyEx urgeBackMoneyEx = new UrgeBackMoneyEx();
					urgeBackMoneyEx.setId(ids[i]);
					exportUrgeBackList.add(urgeBackMoneyService.selectBackMoneyListNo(urgeBackMoneyEx).get(0));
				}
			}
			excelUtil.exportExcel(exportUrgeBackList,FileExtension.URGE_BACK, UrgeBackMoneyEx.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, 1);
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			logger.error("导出催收服务费返款发生异常", e);
			addMessage(redirectAttributes, "导出催收服务费返款发生异常");
		} finally {
			if(success){
				return null;
			}else{
				return "redirect:"
		                + adminPath
		                + "/borrow/grant/urgeBackList/urgeBackListInfo";
			}
		}
	}
	
	/**
	 * 导入催收服务费返款
	 * 2015年12月24日
	 * By 张振强
	 * @param request
	 * @param response
	 * @param filePath
	 * @param model
	 * @param file
	 * @return  要进行跳转的页面
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="importBackList")
	public String importBackList(HttpServletRequest request,
			HttpServletResponse response, String filePath, Model model,
			MultipartFile file, UrgeBackMoneyEx uBackMoneyEx) throws Exception {
		ExcelUtils excelUtil = new ExcelUtils();
		UrgeBackMoneyEx urgeBackMoneyEx = new UrgeBackMoneyEx();
		StringBuffer message = new StringBuffer();
		File sourceFile = new File(file.getOriginalFilename());
		LoanFileUtils.inputstreamtofile(file.getInputStream(), sourceFile);
		List<UrgeBackMoneyEx> urgeBackExList = (List<UrgeBackMoneyEx>) excelUtil.importExcel(sourceFile, 0, 0, UrgeBackMoneyEx.class,2);
		UrgeBackMoneyNotify notify = new UrgeBackMoneyNotify();
		try {
			notify = urgeBackMoneyService.saveUrgeBackMoney(urgeBackExList, new Page<UrgeBackMoneyEx>(request,response), urgeBackMoneyEx);
			if (notify.getFlag() > 0) {
				message.append("请注意合同号、金额、返款结果不为空！！已返款数据不能导入！！");
			}else if (StringUtils.isNotEmpty(notify.getMessage())) {
				message.append("如下合同号的数据输入有误：" + notify.getMessage());
			}else{
				message.append("导入成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入催收服务费返款发生异常", e);
			message.append("导入催收服务费返款发生异常");
		}
		setStoreId(uBackMoneyEx);
		//不显示ZCJ数据
		uBackMoneyEx.setChannelFlag(ChannelFlag.ZCJ.getCode());
	    Page<UrgeBackMoneyEx> urgeListPage = urgeBackMoneyService.selectBackMoneyList(new Page<UrgeBackMoneyEx>(request,response), uBackMoneyEx);
	    addMessage(model, message.toString());
	    model.addAttribute("urgeListPage", urgeListPage);
	    model.addAttribute("urgeBackMoneyEx", uBackMoneyEx);
	    return "borrow/grant/urgeBackList";
	}

	/**
	 * 返款退回弹框
	 * 2015年12月17日
	 * By 张振强
	 * @param  backReason
	 * @param checkVal
	 * @return 要进行跳转的页面
	 */
	@ResponseBody
	@RequestMapping(value="urgeMoneyBack")
	public String urgeMoneyBack(String backReason, String checkVal,
			UrgeBackMoneyEx urgeListRe) {
		StringBuffer message = new StringBuffer();
		List<String> failedCodeList = Lists.newArrayList(); 
		setStoreId(urgeListRe);
		if (StringUtils.isEmpty(checkVal)) {
			// 如果没有选中的单子
			List<UrgeBackMoneyEx> urgeBackList = urgeBackMoneyService.selectBackMoneyListNo(urgeListRe);
			for(UrgeBackMoneyEx backMoneyItem : urgeBackList){
				if (backMoneyItem.getDictPayStatus().equals(UrgeRepay.REPAY_TO.getCode())) {
					try {
						urgeBackMoneyService.saveUrgeBack(backMoneyItem, backReason);
					} catch (Exception e) {
						logger.error("保存催收返款退回发生异常", e);
						failedCodeList.add(backMoneyItem.getContractCode());
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(checkVal)){
			String[] ids = checkVal.split(",");
			// 有选中的单子时进行的操作,
			for(String idItem : ids){
				UrgeBackMoneyEx urgeBack = urgeBackMoneyService.get(idItem);
				if(!ObjectHelper.isEmpty(urgeBack)){
					if (urgeBack.getDictPayStatus().equals(UrgeRepay.REPAY_TO.getCode())) {
						try {
							urgeBackMoneyService.saveUrgeBack(urgeBack, backReason);
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("保存催收返款退回发生异常", e);
							failedCodeList.add(urgeBack.getContractCode());
						}
					}		
				}
			}
		}
		if(ArrayHelper.isNotEmpty(failedCodeList)){
			message.append("保存催收返款退回发生异常，合同编号：");
			for(String codeItem : failedCodeList){
				message.append(codeItem);
				message.append("，");
			}
			message.append("请检查。");
		}else{
			message.append(BooleanType.TRUE);
		}
		return message.toString();
    }
}
