package com.creditharmony.loan.borrow.zhongjin.web;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.type.AccountType;
import com.creditharmony.core.loan.type.CpcnStatus;
import com.creditharmony.core.loan.type.DeductPlatBank;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.zhongjin.entity.PaybackCpcnIn;
import com.creditharmony.loan.borrow.zhongjin.entity.PaybackCpcnOut;
import com.creditharmony.loan.borrow.zhongjin.service.ZhongJinService;
import com.creditharmony.loan.borrow.zhongjin.view.History;
import com.creditharmony.loan.borrow.zhongjin.view.PaybackCpcn;
import com.creditharmony.loan.borrow.zhongjin.view.PaybackCpcnModel;
import com.creditharmony.loan.borrow.zhongjin.view.PaybackOrder;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.utils.ExcelUtils;
import com.google.common.collect.Lists;


/**
 * 中金划扣
 * @Class Name ZhongJinController
 * @author WJJ
 * @Create In 2016年3月3日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/zhongjin")
public class ZhongJinController extends BaseController {

	@Autowired
    private ZhongJinService zhongJinService;
	@Autowired
	private CityInfoService cityManager;
	
	/**
	 * 中金划扣列表
	 * 2016年3月3日
	 * By WJJ
	 * @return 
	 */
	@RequestMapping(value = "nolist")
	public String nolist(Model model,  PaybackCpcnModel params,
			HttpServletRequest request,HttpServletResponse response) {
		Page<PaybackCpcn> page = new Page<PaybackCpcn>(request,response);
		page.setPageSize(params.getPageSize());
		page.setPageNo(params.getPageNo());
		params.setDeductStatus(YESNO.YES.getCode());
		if(params.getBanknum()!=null&&!"".equals(params.getBanknum())){
			params.setBankIds(params.getBanknum().split(","));
		}
		Page<PaybackCpcn> pageList = zhongJinService.getListByParam(page,params);
		List<Map<String,Object>> list = zhongJinService.getSelectCount(params);
		List<CityInfo> provinceList = cityManager.findProvince();
		
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cpcn", params);
		model.addAttribute("items", pageList);
		model.addAttribute("deductsAmount", list.get(0).get("round"));
		model.addAttribute("totalNum", pageList.getCount());
		model.addAttribute("page", page);
		return "/borrow/zhongjin/paycpcnNoList";
	}
	
	/**
	 * 中金划扣已办列表
	 * 2016年3月3日
	 * By WJJ
	 * @return 
	 */
	@RequestMapping(value = "donelist")
	public String donelist(Model model,  PaybackCpcnModel params,
			HttpServletRequest request,HttpServletResponse response) {
		Page<PaybackCpcn> page = new Page<PaybackCpcn>(request,response);
		page.setPageSize(params.getPageSize());
		page.setPageNo(params.getPageNo());
		params.setDeductStatus("2");
		if(params.getBanknum()!=null&&!"".equals(params.getBanknum())){
			params.setBankIds(params.getBanknum().split(","));
		}
		Page<PaybackCpcn> pageList = zhongJinService.getListByParam(page,params);
		List<Map<String,Object>> list = zhongJinService.getSelectCount(params);
		List<CityInfo> provinceList = cityManager.findProvince();
		
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cpcn", params);
		model.addAttribute("items", pageList);
		model.addAttribute("deductsAmount", list.get(0).get("round"));
		model.addAttribute("applyReallyAmount", list.get(0).get("applyreallyamount"));
		model.addAttribute("totalNum", pageList.getCount());
		model.addAttribute("page", page);
		return "/borrow/zhongjin/doneList";
	}
	
	/**
	 * 添加或修改中金划扣数据
	 * 2016年3月3日
	 * By WJJ
	 * @return 
	 */
	@RequestMapping("/saveOrUpdate")
	public String saveOrUpdate(HttpServletRequest request, Model model, PaybackCpcn params)
	{
		User user = UserUtils.getUser();
		try {
			//校验合同编号是否过长，过长返回提示信息
			if(params != null && params.getContractCode() != null){
				if(getLength(params.getContractCode()) > 45){
					return "合同编号太长（15个汉字），请检查调整！";
				}
			}
			if(params != null && StringUtils.isEmpty(params.getCpcnId()))
			{
				params.setCreateTime(new Date());
				params.setCreatuserId(user.getId());
				params.setAppoint("0");
				params.setStatus(CpcnStatus.TO_PROCESS.getCode());
				//params.setBankNum(zhongJinService.getBankValue(params.getBankName()));
				zhongJinService.save(params);
			}
			else if(params != null){
				params.setCreatuserId(user.getId());
				//params.setBankNum(zhongJinService.getBankValue(params.getBankName()));
				zhongJinService.update(params);
				History h = new History();
				h.setId(IdGen.uuid());
				h.setCpcnId(params.getCpcnId());
				h.setOperName("修改债权信息");
				h.setOperResult("成功");
				h.setOperNotes("修改房借债权信息");
				h.setCreateBy(params.getCreatuserId());
				h.setModifyBy(params.getCreatuserId());
				zhongJinService.addHistory(h);
			}
			
		} catch (Exception e) {
			return "更新失败："+ e.getMessage();
		} 
		model.addAttribute("message", "更新成功");
		return "redirect:" + adminPath + "/borrow/zhongjin/nolist";
	}
	
	/**
	 * 导入中金数据
	 * 2016年3月5日
	 * By WJJ
	 * @param file
	 * @param redirectAttributes
	 * @return redirect list
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "importExcel", method = RequestMethod.POST)
	public String importExcel(MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
		ExcelUtils excelutil = new ExcelUtils();
		List<PaybackCpcnIn> outList = new ArrayList<PaybackCpcnIn>();
		List<PaybackCpcnIn> insertList = new ArrayList<PaybackCpcnIn>();
		User user = UserUtils.getUser();
		String msg = null;
		boolean t = true; 
		outList = (List<PaybackCpcnIn>) excelutil.importExcel(file, 0, 0, PaybackCpcnIn.class);
		if (ArrayHelper.isNotEmpty(outList)) {
			for (int i = 0; i < outList.size(); i++) {
				int row = i + 1;
				PaybackCpcnIn pc = outList.get(i);
					pc.setCreatuserId(user.getId());
					if("个人账户".equals(outList.get(i).getAccountType())){
						outList.get(i).setAccountType(AccountType.PERSONAL.getCode());
					} else if("企业账户".equals(outList.get(i).getAccountType())) {
						outList.get(i).setAccountType(AccountType.COMPANY.getCode());
					} else {
						msg = "账号类型无法识别（第"+ row +"行数据有问题）";
						t = false;
						break;
					}
					if(!org.springframework.util.StringUtils.hasText(outList.get(i).getSerialNum())){
						msg = "EXCEL数据验证失败：序号必填（第"+ row +"行数据有问题）"; 
						t = false;
						break;
					}
					if(!org.springframework.util.StringUtils.hasText(outList.get(i).getDealMoneyStr())){
						msg = "EXCEL数据验证失败：金额必填（第"+ row +"行数据有问题）"; 
						t = false;
						break;
					}else{
						outList.get(i).setDealMoney(new BigDecimal(outList.get(i).getDealMoneyStr()));
					}
					if(!org.springframework.util.StringUtils.hasText(outList.get(i).getAccountNum())){
						msg = "EXCEL数据验证失败：账户号码必填（第"+ row +"行数据有问题）";
						t = false;
						break;
					}
					if(!org.springframework.util.StringUtils.hasText(outList.get(i).getAccountName())){
						msg = "EXCEL数据验证失败：账户姓名必填（第"+ row +"行数据有问题）";
						t = false;
						break;
					}
					if(!org.springframework.util.StringUtils.hasText(outList.get(i).getBankName())){
						msg = "EXCEL数据验证失败：银行名称必填（第"+ row +"行数据有问题）";
						t = false;
						break;
					}
					String bankNum = zhongJinService.getBankValue(outList.get(i).getBankName());
					if(bankNum==null){
						msg = "银行名称无法识别:"+ outList.get(i).getBankName() +"（第"+ row +"行数据有问题）";
						t = false;
						break;
					}else{
						outList.get(i).setBankNum(bankNum);
					}
					if(DeductPlatBank.BOC.getCode().equals(outList.get(i).getBankNum()))
					{
						if(!org.springframework.util.StringUtils.hasText(outList.get(i).getAccounProvice())){
							msg = "EXCEL数据验证失败：分支行省份必填（第"+ row +"行数据有问题）";
							t = false;
							break;
						}
						if(!org.springframework.util.StringUtils.hasText(outList.get(i).getAccounCity())){
							msg = "EXCEL数据验证失败：分支行城市必填（第"+ row +"行数据有问题）";
							t = false;
							break;
						}
						List<String> proviceList = isHaveProvince(outList.get(i).getAccounProvice());
						if(proviceList.isEmpty()){
							msg = "省份无法识别:"+ outList.get(i).getAccounProvice() +"（第"+ row +"行数据有问题）";
							t = false;
							break;
						}else{
							outList.get(i).setAccounProvice(proviceList.get(0));
						}
						List<String> cityList = isHaveCity(outList.get(i).getAccounProvice(), outList.get(i).getAccounCity());
						if(cityList.isEmpty()){
							msg = "城市无法识别:"+ outList.get(i).getAccounCity() +"（第"+ row +"行数据有问题）";
							t = false;
							break;
						}else{
							outList.get(i).setAccounCity(cityList.get(0));
						}
					}
					String typeNum = zhongJinService.getCerTypeValue(outList.get(i).getCertType());
					if(typeNum==null){
						msg = "证件类型无法识别:"+ outList.get(i).getCertType() +"（第"+ row +"行数据有问题）";
						t = false;
						break;
					}else{
						outList.get(i).setCertType(typeNum);
					}
					if(AccountType.PERSONAL.getCode().equals(outList.get(i).getAccountType()))
					{
						if(!org.springframework.util.StringUtils.hasText(outList.get(i).getCertType())) outList.get(i).setCertType("0");
						if(!org.springframework.util.StringUtils.hasText(outList.get(i).getCertNum())){
							msg = "EXCEL数据验证失败：证件号码必填（第"+ row +"行数据有问题）";
							t = false;
							break;
						}
						
					}
					
					if(AccountType.COMPANY.getCode().equals(outList.get(i).getAccountType()))
					{
						if(!org.springframework.util.StringUtils.hasText(outList.get(i).getCertType())) outList.get(i).setCertType("A");
						//民生银行
						if(DeductPlatBank.CMBC.getCode().equals(outList.get(i).getBankNum()))
							if(!org.springframework.util.StringUtils.hasText(outList.get(i).getCertNum())){
								msg = "EXCEL数据验证失败：证件号码必填（第"+ row +"行数据有问题）";
								t = false;
								break;
							}
					}
					
					if(!org.springframework.util.StringUtils.hasText(outList.get(i).getContractCode())){
						msg = "EXCEL数据验证失败：合同号码必填（第"+ row +"行数据有问题）";
						t = false;
						break;
					}
					if(isHaveSerialnum(outList.get(i).getSerialNum())){
						msg = "序号重复:"+ outList.get(i).getSerialNum() +"（第"+ row +"行数据有问题）";
						t = false;
						break;
					}
					
					outList.get(i).preInsert();
					insertList.add(outList.get(i));
					msg = "导入成功!";
				
			}
			if (ArrayHelper.isNotEmpty(insertList) && t) {
				try{
					zhongJinService.insertList(insertList);
				}catch(Exception e){
					msg = "保存出错";
				}
				
			}
			// 删除临时文件
			File f = new File(file.getOriginalFilename());
			f.delete();
		} else {
			msg = "文档没有数据！";
		}
		addMessage(redirectAttributes, msg);
		return "redirect:" + adminPath + "/borrow/zhongjin/nolist";
	}
	
	/**
	 * 发送中金数据
	 * 2016年3月3日
	 * By WJJ
	 * @return 
	 */
	@RequestMapping("/sendStatus")
	public void sendStatus(HttpServletRequest request, PaybackCpcnModel params, HttpServletResponse response) throws Exception
	{
		String message = "";
		PrintWriter out = response.getWriter();
		User user = UserUtils.getUser();
		List<PaybackCpcn> cpcnList = new ArrayList<PaybackCpcn>();
		String status = params.getStatus();
		try {
			if(params.getCheckIds()==null)
			{ 
				params.setStatus(null);
				params.setDeductStatus("1");
				if(params.getBanknum()!=null&&!"".equals(params.getBanknum())){
					params.setBankIds(params.getBanknum().split(","));
				}
				cpcnList = zhongJinService.getListByParam(params);
				for(PaybackCpcn entity : cpcnList)
				{
					entity.setStatus(status);
				}
			}
			else
			{
				for(String id : params.getCheckIds())
				{
					PaybackCpcn entity = new PaybackCpcn();
					entity.setCpcnId(id);
					entity.setStatus(params.getStatus());
					cpcnList.add(entity);
				}
			}
			params.setStatus(status);
			zhongJinService.bathUpdateStatus(cpcnList, user);

			message="操作成功";
		} catch (Exception e) {
			//logger.error(e);
			message="发送失败";
		} 
		out.print("{\"msg\":\""+message+"\"}");
	}
	
	/**
	 * 放弃
	 * 2016年3月3日
	 * By WJJ
	 * @return 
	 */
	@RequestMapping("/giveupStatus")
	public void giveupStatus(HttpServletRequest request, PaybackCpcnModel params, HttpServletResponse response) throws Exception
	{
		String message = "";
		PrintWriter out = response.getWriter();
		User user = UserUtils.getUser();
		List<PaybackCpcn> cpcnList = new ArrayList<PaybackCpcn>();
		String status = params.getStatus();
		try {
			if(params.getCheckIds()==null)
			{ 
				params.setStatus(null);
				params.setDeductStatus("1");
				if(params.getBanknum()!=null&&!"".equals(params.getBanknum())){
					params.setBankIds(params.getBanknum().split(","));
				}
				cpcnList = zhongJinService.getListByParam(params);
				for(PaybackCpcn entity : cpcnList)
				{
					entity.setStatus(status);
				}
			}
			else
			{
				for(String id : params.getCheckIds())
				{
					PaybackCpcn entity = new PaybackCpcn();
					entity.setCpcnId(id);
					entity.setStatus(params.getStatus());
					cpcnList.add(entity);
				}
			}
			zhongJinService.giveUpdateStatus(cpcnList, user);
			message="操作成功";
		} catch (Exception e) {
			//logger.error(e);
			message="操作失败";
		} 
		out.print("{\"msg\":\""+message+"\"}");
	}
	
	/**
	 * 添加预约划扣
	 * @param request
	 * @param params
     * @return
     */
	@RequestMapping("/orderDeduct")
	public void orderDeduct(HttpServletRequest request, PaybackCpcnModel params, HttpServletResponse response) throws Exception {
		User user = UserUtils.getUser();
		String msg = null;
		PaybackOrder paybackOrder = null;

		String deductType = params.getDeductType();
		String deductDate = params.getDeductDate();
		String[] deductTime = params.getDeductTime();
		String[] ids = params.getCheckIds();
		PrintWriter out = response.getWriter();
		//校验预约划扣信息
		if((deductType == null || "".equals(deductType.trim())) || (deductDate == null || "".equals(deductDate.trim())) ||
				(deductTime == null || deductTime.length == 0)) {
			msg = "预约划扣信息（划扣方式、预约日期、预约时间）不能为空，请检查确认！";
			out.print("{\"msg\":\""+msg+"\"}");
			return ;
		}
		if(deductTime != null && deductTime.length != 0){
			String tmp = "";
			for(String time : deductTime){
				if(time == null || "".equals(time.trim())){
					msg = "预约划扣信息（预约时间）不能为空，请检查！";
					out.print("{\"msg\":\""+msg+"\"}");
					return ;
				}
				if(tmp.contains(time.trim())){
					msg = "预约时间中有重复时间（" + time + "），请检查确认！";
					out.print("{\"msg\":\""+msg+"\"}");
					return ;
				}
				tmp += time.trim();
			}
		}

		params.setStatus(params.getDeductStatus());
		params.setDeductType(deductType);//暂时移除划扣方式
		List<PaybackOrder> orderList = Lists.newArrayList();
		//查询符合搜索条件的数据
		//params.setPage(1);
		if(!(params.getCheckIds() != null && params.getCheckIds().length > 0)){
			params.setDeductStatus("1");
			if(params.getBanknum()!=null&&!"".equals(params.getBanknum())){
				params.setBankIds(params.getBanknum().split(","));
			}
			List<PaybackCpcn> cpcnList = zhongJinService.getListByParam(params);
			if (cpcnList != null && cpcnList.size() != 0) {
				for(PaybackCpcn paybackCpcn : cpcnList){
					if(deductTime != null && deductTime.length > 0) {
						String timeStr = " (";
						for(String time : deductTime) {
							paybackOrder = new PaybackOrder();
							paybackOrder.setId(IdGen.uuid());
							paybackOrder.setCpcnId(paybackCpcn.getCpcnId());
							paybackOrder.setDeductDate(deductDate);
							paybackOrder.setDeductTime(time);
							orderList.add(paybackOrder);
							zhongJinService.insertPaybackOrder(paybackOrder);//保存预约记录
							timeStr += time + "/";
						}

						History history = new History();
						history.setId(IdGen.uuid());
						history.setCpcnId(paybackOrder.getCpcnId());
						if(DeductTime.RIGHTNOW.getCode().equals(deductType)){
							history.setOperName("预约划扣(实时)");
						}else if(DeductTime.BATCH.getCode().equals(deductType)){
							history.setOperName("预约划扣(批量)");
						}else{
							history.setOperName("预约划扣");
						}
						history.setOperResult("成功");
						history.setOperNotes("预约时间:"+deductDate+"("+timeStr+")");
						history.setCreateBy(user.getId());
						history.setModifyBy(user.getId());
						zhongJinService.addHistory(history);
						
						paybackCpcn.setStatus(CpcnStatus.ORDER.getCode());//操作状态为预约中
						paybackCpcn.setBackResult("0");//回盘结果为待处理
						paybackCpcn.setDeductType(deductType);//划扣方式
						paybackCpcn.setOpearTime(new Date());//操作时间
						paybackCpcn.setBackTime(null);
						zhongJinService.update(paybackCpcn);//修改状态为预约中
						//记录操作日志
						//writeFlowLog(user, String.valueOf(paybackCpcn.getId()),"中金线下预约划扣("+("1".equals(deductType) ? "实时" : "批量")+")","预约时间："+deductDate+timeStr.substring(0,timeStr.length()-1)+")");
					}

				}

				msg = "预约划扣成功！";
				out.print("{\"msg\":\""+msg+"\"}");
			}else{
				msg = "没有查询到符合条件的数据！";
				out.print("{\"msg\":\""+msg+"\"}");
				return;
			}
			
		}else{
			if(ids!=null&&ids.length!=0){
				for(int i=0;i<ids.length;i++){
					String timeStr = " (";
					if(deductTime != null && deductTime.length > 0) {
						
						for(String time : deductTime) {
							paybackOrder = new PaybackOrder();
							paybackOrder.setId(IdGen.uuid());
							paybackOrder.setCpcnId(ids[i]);
							paybackOrder.setDeductDate(deductDate);
							paybackOrder.setDeductTime(time);
							orderList.add(paybackOrder);
							zhongJinService.insertPaybackOrder(paybackOrder);//保存预约记录
							
							timeStr+=time+"/";
						}
					}
					
					History h = new History();
					h.setId(IdGen.uuid());
					h.setCpcnId(paybackOrder.getCpcnId());
					if(DeductTime.RIGHTNOW.getCode().equals(deductType)){
						h.setOperName("预约划扣(实时)");
					}else if(DeductTime.BATCH.getCode().equals(deductType)){
						h.setOperName("预约划扣(批量)");
					}else{
						h.setOperName("预约划扣");
					}
					h.setOperResult("成功");
					h.setOperNotes("预约时间:"+deductDate+"("+timeStr+")");
					h.setCreateBy(user.getId());
					h.setModifyBy(user.getId());
					zhongJinService.addHistory(h);
					
					PaybackCpcn paybackCpcn = new PaybackCpcn();
					paybackCpcn.setCpcnId(ids[i]);
					paybackCpcn.setStatus(CpcnStatus.ORDER.getCode());//操作状态为预约中
					paybackCpcn.setBackResult("0");//回盘结果为待处理
					paybackCpcn.setDeductType(deductType);//划扣方式
					paybackCpcn.setOpearTime(new Date());//操作时间
					zhongJinService.update(paybackCpcn);//修改状态为预约中
				}
			}
			
		}
		
		

		out.print("{\"msg\":\"预约成功\"}");
	}
	
	/**
	 * 取消预约划扣
	 * @param request
	 * @param redirectAttributes
     * @return
     */
	@RequestMapping("/cancelOrderDeduct")
	public String cancelOrderDeduct(HttpServletRequest request, PaybackCpcnModel params,RedirectAttributes redirectAttributes){
		String msg = "";
		User user = UserUtils.getUser();
		params.setStatus(CpcnStatus.ORDER.getCode());//预约中
		params.setBackResult("0");//回盘状态为待划扣
		if(params.getBanknum()!=null&&!"".equals(params.getBanknum())){
			params.setBankIds(params.getBanknum().split(","));
		}
		List<PaybackCpcn> list = zhongJinService.getListByParam(params);
		try{
			zhongJinService.delOrder(list,user.getId());
			msg = "取消成功";
		}catch(Exception e){
			msg = "取消失败";
		}
		addMessage(redirectAttributes, msg);
		return  "redirect:" + adminPath + "/borrow/zhongjin/donelist";
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取中金详情信息
	 */
	@ResponseBody
	@RequestMapping("/getInfo")
	public String getInfo(HttpServletRequest request, PaybackCpcnModel model, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		model.setId(id);
		List<PaybackCpcn> list = zhongJinService.getListByParam(model);
		PrintWriter out = response.getWriter();
		if(!list.isEmpty()){
			return jsonMapper.toJson(list.get(0));
		}
		return "";
	}
	
	/**
	 * 获取中金历史
	 * @param request
	 * @param redirectAttributes
     * @return
     */
	@RequestMapping("/getHistory")
	public String getHistory(HttpServletRequest request, Model model, HttpServletResponse response) throws Exception {
		String cpcnId = request.getParameter("cpcnId");
		List<History> list = zhongJinService.getHistory(cpcnId);
		model.addAttribute("items", list);
		return "/borrow/zhongjin/hitory";
	}
	
	/**
	 * 中金划扣已办导出
	 * 2016年3月16日
	 * By WJJ
	 * @param request
	 * @param response
	 * @param 
	 */
	@RequestMapping(value = "export")
	public void export(HttpServletRequest request, HttpServletResponse response,
			PaybackCpcnModel params,RedirectAttributes redirectAttributes) throws Exception{
			User user = UserUtils.getUser();
			ExcelUtils excelutil = new ExcelUtils();
			List<PaybackCpcnOut> list = Lists.newArrayList();
			// 如果有进行选择，获得选中单子的list
		String[] checkIds = params.getCheckIds();
		if (ArrayHelper.isNotNull(checkIds)) {
			PaybackCpcnModel paybackModel = new PaybackCpcnModel();
			paybackModel.setCheckIds(checkIds);
			list = zhongJinService.exportList(paybackModel);
		} else {
			params.setDeductStatus("2");
			list = zhongJinService.exportList(params);
		}
		for (int i = 0; i < list.size(); i++) {
			History h = new History();
			h.setId(IdGen.uuid());
			h.setCpcnId(list.get(i).getCpcnId());
			h.setOperName("导出");
			h.setOperResult("成功");
			h.setOperNotes("中金划扣导出");
			h.setCreateBy(user.getId());
			h.setModifyBy(user.getId());
			zhongJinService.addHistory(h);
		}
		// 如果没有进行选择，
		excelutil.exportExcel(list,
				FileExtension.PAYBACK_EXPORT + System.currentTimeMillis(),
				PaybackCpcnOut.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_TEMPLATE, response, null);
		// return "redirect:" + adminPath + "/borrow/zhongjin/donelist";
	}
	
	private static int getLength(String htbh){
		return htbh.replaceAll("\\W", "aaa").length();
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 是否有此序号
	 */
	private boolean isHaveSerialnum(String serialnum){
		long count = zhongJinService.getCount(serialnum);
		return count>0;
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取省编号
	 */
	private List<String> isHaveProvince(String provinceName) throws Exception
	{
		List<String> list = zhongJinService.getProvinceName(provinceName);
		return list;
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取市级编号
	 */
	private List<String> isHaveCity(String provinceName, String cityName) throws Exception
	{
		List<String> list = zhongJinService.getCityName(provinceName,cityName);
		return list;
	}
	
}
