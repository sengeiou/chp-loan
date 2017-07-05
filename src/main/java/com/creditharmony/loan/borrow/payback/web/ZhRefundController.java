package com.creditharmony.loan.borrow.payback.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.creditharmony.adapter.bean.in.jinxin.creditor.JinxinCreditorClaimsInBean;
import com.creditharmony.adapter.bean.out.jinxin.creditor.JinxinCreditorClaimsOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.ZhrefundStatus;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.payback.entity.Zhrefund;
import com.creditharmony.loan.borrow.payback.service.ZhrefundService;
import com.creditharmony.loan.common.utils.ExcelUtils;

/**
 * 中和东方不可退款业务处理Controller
 * @Class Name PaybackTransferOutController
 * @author zhangfeng
 * @Create In 2015年11月9日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/zhrefund")
public class ZhRefundController extends BaseController {

	@Autowired
	private ZhrefundService zhrefundService;
	
	private static String CODE = "code";
	private static String MSG = "msg";
	private static String SUCCESS_STATUS_CODE = "0";
	private static String REPEAT_STATUS_CODE = "1";
	private static String ERROR_STATUS_CODE = "2";
	
	@Autowired
	private ContractDao contractDao;
	
	/**
	 * 跳转到中和东方不可退款列表
	 * @author 于飞
	 * @Create 2017年2月8日
	 * @param request
	 * @param response
	 * @param model
	 * @param zhrefund
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request,HttpServletResponse response, Model model,
			Zhrefund zhrefund) {
		//门店id分割
		if(zhrefund.getStoreId()!=null && !zhrefund.getStoreId().equals("")){
			zhrefund.setStoreId(appendString(zhrefund.getStoreId()));
		}
		Page<Zhrefund> pageList = zhrefundService.getZhrefundList(
				new Page<Zhrefund>(request, response),zhrefund);
		List<Zhrefund> list = new ArrayList<Zhrefund>();
		for (int i=0;i<pageList.getList().size();i++) {
			Zhrefund pt = pageList.getList().get(i);
			if(StringUtils.isNotEmpty(pt.getDictLoanStatus())){
				String dictLoanStatusLabel = DictCache.getInstance().getDictLabel(
						"jk_loan_apply_status", pt.getDictLoanStatus());
				pt.setDictLoanStatusLabel(dictLoanStatusLabel);
				
			}
			if(StringUtils.isNotEmpty(pt.getZhrefundStatus())){
				if(pt.getZhrefundStatus().equals(ZhrefundStatus.YES.getCode()))
					pt.setZhrefundStatusLabel(ZhrefundStatus.YES.getName());
				else if(pt.getZhrefundStatus().equals(ZhrefundStatus.NO.getCode()))
					pt.setZhrefundStatusLabel(ZhrefundStatus.NO.getName());
				
			}
			list.add(pt);
		}
		pageList.setList(list);
		model.addAttribute("pageList", pageList);
		model.addAttribute("zhrefund", zhrefund);
		logger.debug("invoke AuditedController method: getAuditedList");
		return "borrow/payback/zhrefund/zhrefundList";
	}
	
	/**
	 * 导入不可退款的合同编号
	 * @author 于飞
	 * @Create 2017年2月8日
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "importData")
	public String importData(MultipartFile file, RedirectAttributes redirectAttributes) {
		Map<String, String> map = new HashMap<String, String>();
		ExcelUtils excelutil = new ExcelUtils();
		boolean importFlag = true;
		String msg = null;
		String code = null;
		try{
			List<Zhrefund> list = (List<Zhrefund>) excelutil.importExcel(file, 0, 0, Zhrefund.class);
			List<Zhrefund> resultList = new ArrayList();
			if (ArrayHelper.isNotEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i)!=null && list.get(i).getContractCode()!=null && !"".equals(list.get(i).getContractCode())){
						//查找要导入的数据是否已经存在
						Zhrefund zhrefund = zhrefundService.findByContractCode(list.get(i));
						if(zhrefund!=null){
							code = REPEAT_STATUS_CODE;
							msg = "合同编号重复，请重新导入！";
							importFlag = false;
							break;
						}
						Zhrefund refund = list.get(i);
						refund.setId(IdGen.uuid());
						refund.setZhrefundStatus(ZhrefundStatus.YES.getCode());
						refund.setCreateBy(UserUtils.getUser().getId());
						refund.setModifyBy(UserUtils.getUser().getId());
						resultList.add(refund);
					}
				}
				//确定能导入后批量导入
				if(importFlag){
					zhrefundService.insertZhrefund(resultList);
					code = SUCCESS_STATUS_CODE;
					msg = "导入成功！";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("中和东方不可退款数据导入失败："+e.getMessage());	
			code = ERROR_STATUS_CODE;
			msg = "导入失败！";
		}
		
		map.put(CODE, code);
		map.put(MSG, msg);
		return JsonMapper.nonDefaultMapper().toJson(map);
	}
	
	/**
	 * 
	 * @author 于飞
	 * @Create 2017年2月8日
	 * @param contractCodes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateStatus")
	public String updateStatus(HttpServletRequest request, String contractCodes,String zhrefundStatus){
		try{
			Zhrefund zhrefund = new Zhrefund();
			if(contractCodes!=null && !contractCodes.equals("")){
				zhrefund.setContractCode(appendString(contractCodes));
			}
			zhrefund.setModifyTime(new Date());
			zhrefund.setModifyBy(UserUtils.getUser().getId());
			zhrefund.setZhrefundStatus(zhrefundStatus);
			zhrefundService.updateZhrefundStatus(zhrefund);
			return "1";
		}catch(Exception e){
			logger.error("更新中和东方数据不可退款失败"+e.getMessage());
			return "2";
		}
		
	}
	
	/**
	 * 判断合同编号是否属于中和数据，如果属于并且状态是生效，则不允许进行蓝补退款
	 * @author 于飞
	 * @Create 2017年2月8日
	 * @param contractCodes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "blueRefundBefore")
	public String blueRefundBefore(HttpServletRequest request, String contractCode){
		Zhrefund zhrefund = new Zhrefund();
		zhrefund.setContractCode(contractCode);
		zhrefund.setZhrefundStatus(ZhrefundStatus.YES.getCode());
		zhrefund = zhrefundService.findByContractCode(zhrefund);
		if(zhrefund!=null){
			return REPEAT_STATUS_CODE;
		}else{
			return SUCCESS_STATUS_CODE;
		}
	}
	/**
	 * 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public String appendString(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (int i =0;i<idArray.length;i++){
			String id  = idArray[i];
			if (i == 0){
				parameter.append("'" +id +"'");
			}else {
				parameter.append(",'" +id + "'");
			}
		}
		return parameter.toString();
	}
	
	/**
	 * 结清申请
	 * @author 于飞
	 * @Create 2017年4月26日
	 * @param request
	 * @param contractCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "settleApply")
	public String settleApply(HttpServletRequest request, String contractCode){
		try{
			zhrefundService.settleApply(contractCode);
			return "1";
		}catch(Exception e){
			return "0";
		}
	}
	/*@ResponseBody
	@RequestMapping(value = "pushToJinXin")
	public void pushToJinXin(String loanCode){
		JinxinCreditorClaimsInBean in = contractDao.findJinXinInfo(loanCode);
		ClientPoxy service = new ClientPoxy(ServiceType.Type.JINXIN_CREDITOR_CLAIMS_SERVICE);
		JinxinCreditorClaimsOutBean outParam = (JinxinCreditorClaimsOutBean) service.callService(in); 
		if (ReturnConstant.SUCCESS.equals(outParam.getRetCode())) { 
			// TODO 成功 
			logger.info("------给金信推送债权成功"+new Date()+"，借款编号是"+loanCode+"------");
		} else { 
			// TODO 失败 
			logger.info("------给金信推送债权失败"+new Date()+"，借款编号是"+loanCode+"------");
		} 
	}*/
}
