package com.creditharmony.loan.borrow.payback.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.type.DeductType;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.OpenBank;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.entity.AppointmentTemplate;
import com.creditharmony.loan.borrow.payback.service.AppointmentDudctTemplateService;
import com.creditharmony.loan.common.constants.AppointmentConstant;
import com.creditharmony.loan.common.constants.AppointmentRule;
import com.creditharmony.loan.common.constants.LoanStatus;
import com.creditharmony.loan.common.utils.ExportHelper;
import com.creditharmony.loan.common.vo.DefaultServiceVO;
import com.creditharmony.loan.common.vo.Errors;

@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/appointmenttemplate")
public class AppointmentDudctTemplateController {
	
	
	@Autowired
	private AppointmentDudctTemplateService appointmentDudctService;
	
	@Autowired
	private Validator validator;
	
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");  
	
	/**
	 * 查询预约划扣列表
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "queryList")
	public String queryList(@RequestParam Map<String,Object> map,HttpServletRequest request,
			HttpServletResponse response,Model model){
		Page<AppointmentTemplate> appointmentPage = appointmentDudctService.queryList(map, request, response);
		List<String>  list =  appointmentDudctService.queryOverCount();
		Map<String,String> mapRule = AppointmentRule.getAppointmentRuleMap();
		model.addAttribute("mapRule",mapRule);
		model.addAttribute("page", appointmentPage);
		model.addAttribute("overCountList",list);
		map.put("appointmentDay", getDate((String)map.get("appointmentDay")));
		model.addAttribute("paramMap",map);
		
		return "borrow/payback/appointmenttemplate/appointmentTemplateList";
	}
	
	/**
	 * 新增或者修改
	 * @param appointment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertOrUpdate")
	public DefaultServiceVO insertOrUpdate(AppointmentTemplate bean,Model model,
			HttpServletRequest request,	HttpServletResponse response,BindingResult errors){
		 validator.validate(bean, errors);
		if (errors.hasErrors()) {
			return DefaultServiceVO.errorMsg(Errors.errorMsg(errors));
		}
		if(judgeDate(bean)){
			return DefaultServiceVO.errorMsg("预约时间不是整点和半点!");
		}
		if(judgeChange(bean)){
			return DefaultServiceVO.errorMsg("已有符合此规则数据，请重新提交!");
		}
	    if(ObjectHelper.isEmpty(bean.getId())){
			// id为空 新增
	    	    bean.setStatus(YESNO.NO.getCode());
				appointmentDudctService.insert(bean);
				return DefaultServiceVO.createSuccess("success");
		}else{
		 	//id 不为空 修改
		        bean.setStatus(YESNO.NO.getCode());
			    appointmentDudctService.update(bean);
			    return DefaultServiceVO.createSuccess("success");
		}
	}
	
	public Date getDate(String date){
		Date newDate = null;
		if(!ObjectHelper.isEmpty(date)){
			  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");  
			  try {
				newDate = sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		}
		return newDate;
	}
	
	/**
	 * 去跳转到新增修改页面
	 * @return
	 */
	@RequestMapping(value = "goDetail")
	public String goDetail(AppointmentTemplate appointment,Model model){
		// 如果有id去查询
		if(!ObjectHelper.isEmpty(appointment.getId())){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", appointment.getId());
		    List<AppointmentTemplate> list = appointmentDudctService.queryList(map);
		    AppointmentTemplate  bean = list.get(0);
		    model.addAttribute("bean",bean);
		}
		List<String>  list =  appointmentDudctService.queryOverCount();
		Map<String,String> mapRule = AppointmentRule.getAppointmentRuleMap();
		model.addAttribute("mapRule",mapRule);
		model.addAttribute("overCountList",list);
		model.addAttribute("loanMap",LoanStatus.getLoanStatusMap());
		return "borrow/payback/appointmenttemplate/appointmentTemplateDetail";
		
	}
	
	@ResponseBody
	@RequestMapping(value = "changSingleEffect")
	public DefaultServiceVO  changSingleEffect(AppointmentTemplate bean,Model model,
			HttpServletRequest request,	HttpServletResponse response){
		appointmentDudctService.changEffect(bean);
		String msg ="";
		if(AppointmentConstant.effect.equals(bean.getStatus())){
			msg = "生效成功";
		}else{
			msg = "失效成功";
		}
		return DefaultServiceVO.createSuccess(msg);
	}
	
	/**
	 * 批量生效失效
	 * @param map
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changEffect")
	public  DefaultServiceVO  changEffect(@RequestParam Map<String,Object> map,HttpServletRequest request,
			HttpServletResponse response,Model model){
		
		queryCriteria(map);
		List<AppointmentTemplate> list = appointmentDudctService.queryList(map);
		String  sign  = (String) map.get("signStatus");
		String msg ="";
		String errorMsg ="";
		if(AppointmentConstant.effect.equals(sign)){
			msg = "生效成功";
			errorMsg = "存在不允许生效数据，请重新选择数据";
		}else{
			msg = "失效成功";
			errorMsg = "存在不允许失效数据，请重新选择数据";
		}
		
		if(list.size()>0){
			if(verificationStatus(list,sign)){
				appointmentDudctService.changEffect(list,sign);
				return DefaultServiceVO.createSuccess(msg);
			}else{
				return DefaultServiceVO.createSuccess(errorMsg);
			}
		}
		return  DefaultServiceVO.createSuccess("没有要操作的数据!");
	}
	
	/**
	 * 删除数据
	 * @param map
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteData")
	public  DefaultServiceVO  deleteData(@RequestParam Map<String,Object> map,HttpServletRequest request,
			HttpServletResponse response,Model model){
		    queryCriteria(map);
		    List<AppointmentTemplate> list = appointmentDudctService.queryList(map);
		   //注释掉有效的数据不能删除的判断
		    if(list.size()>0){
		    	/*if(verificationStatus(list)){*/
		    		appointmentDudctService.deleteData(list);
		    		return DefaultServiceVO.createSuccess("删除成功!");
		    	/*}else{
		    		return DefaultServiceVO.createSuccess("存在不允许删除的数据,请重新选择数据!");
		    	}*/
		    }
		    return  DefaultServiceVO.createSuccess("没有要操作的数据!");
	}
	
	
	/**
	 * 判断数据中是否有不符合规则的数据
	 * @param list
	 * @param sign
	 * @return
	 */
	public Boolean verificationStatus(List<AppointmentTemplate> list,String sign){
		Boolean b  = true;
		for(AppointmentTemplate bean : list){
			if(bean.getStatus().equals(sign)){
				b = false;
				break; 
			}
		}
		return b;
	}
	
	/**
	 * 判断数据中是否有不符合规则的数据
	 * @param list
	 * @param sign
	 * @return
	 */
	public Boolean verificationStatus(List<AppointmentTemplate> list){
		Boolean b  = true;
		for(AppointmentTemplate bean : list){
			if(bean.getStatus().equals(AppointmentConstant.effect)){
				b = false;
				break; 
			}
		}
		return b;
	}

	public void queryCriteria(Map<String,Object> map){
		 String ids = (String)map.get("ids");
		 if (StringUtils.isNotEmpty(ids) && ids.split(",").length > 0) {
			// 有勾选数据,id参数添加,存入List<String>
			map.put("ids", Arrays.asList(ids.split(",")));
		 }
	}
	
	// 判断是否有已经存在的数据
	public boolean  judgeChange(AppointmentTemplate bean){
		String id = "";
		if(!ObjectHelper.isEmpty(bean.getId())){
			id = bean.getId();
		}
		bean.setId("");
		
		List<AppointmentTemplate> list = appointmentDudctService.queryList(bean);
		bean.setId(id);
		if(list.size()>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断时间符合正点和半点
	 * @param bean
	 * @return
	 */
	public boolean judgeDate(AppointmentTemplate bean){
		String dateFomat = format.format(bean.getAppointmentDay());
		String[] date = dateFomat.split(":");
		if("00".equals(date[1]) || "30".equals(date[1]) ){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 导入规则 2017年3月23日 By wengsi
	 * @param request
	 * @param response
	 * @param plat
	 * @param file
	 * @return 成功：success 失败：error
	 */
	@RequestMapping(value = "importRule", method = RequestMethod.POST)
	@ResponseBody
	public String  importRule(HttpServletRequest request,HttpServletResponse response,
			@RequestParam MultipartFile file) {
		 try {
				     ExportHelper ex = new ExportHelper();
					 Sheet sheet = ex.getSheet(file,0);
					 List<AppointmentTemplate> list = new ArrayList<AppointmentTemplate>();
					 for (int i = sheet.getFirstRowNum() +1; i <= sheet.getLastRowNum(); i++) {
						Row row = sheet.getRow(i);
						changeData(row,list);
					 }
					 
					 if(ObjectHelper.isEmpty(list)){
						     return  "没有导入的数据";
					  }else{
						  
						     for(AppointmentTemplate temp : list){
						    	   if(ObjectHelper.isEmpty(temp.getRuleCode())){
						    		   return "导入规则不能为空！" ;
						    	   }
						    	   Map<String,Object> paramMap = new HashMap<String,Object>();
						    	   paramMap.put("ruleCode", temp.getRuleCode());
						    	   List<AppointmentTemplate> plist = appointmentDudctService.queryList(paramMap);
						    	   if(!ObjectHelper.isEmpty(plist)){
						    		   return "规则已经存在！请导出新规则!";
						    	   }
						    	 
						     }
							 appointmentDudctService.insertImportRuleData(list);  
							 return "导入成功！";
					  }
						

			   } catch (Exception e) {
				e.printStackTrace();
				 return "导入失败！";
				
			}
	}

	
	  /**
	   * 转化为实体，并放在list 里面
	   * @param row
	   * @param list
	   */
      public void changeData(Row row,List<AppointmentTemplate> list){
    	  if(row != null){
    		  AppointmentTemplate entity = new AppointmentTemplate();	
    		    // 签约平台
  			    String  signPlant  = row.getCell(0)!=null ? row.getCell(0).toString() : "";
  			    entity.setSignPlat(plantNameToCode(signPlant));
  			    //银行
  			    String   bank = row.getCell(1)!=null ? row.getCell(1).toString() : "";
  			    entity.setBank(bankNameToCode(bank));
  			    //预约时间
  			    String    appointmentDay  = row.getCell(2)!=null ? row.getCell(2).toString() : "";
  			    try {
				entity.setAppointmentDay(format.parse(appointmentDay));
				} catch (ParseException e) {
					e.printStackTrace();
				}
  			    //划扣平台
  			    String deductPlat = row.getCell(3)!=null ? row.getCell(3).toString() : "";
  			    entity.setDeductPlat(plantNameToCode(deductPlat));
  			    // 通联是否签约
  			    String  tlSign = row.getCell(4)!=null ? row.getCell(4).toString() : "";
  			    entity.setTlSign(signNameToCode(tlSign));
  			     // 卡联是否签约
  			     String  klSign = row.getCell(5)!=null ? row.getCell(5).toString() : "";
			    entity.setKlSign(signNameToCode(klSign));
			     // 畅捷是否签约
 			     String  cjSign = row.getCell(6)!=null ? row.getCell(6).toString() : "";
			    entity.setCjSign(signNameToCode(cjSign));
			     // 借款状态
			    String  loanStatus = row.getCell(7)!=null ? row.getCell(7).toString() : "";
			    entity.setLoanStatus(loanStatusNameToCode(loanStatus));
			    //累计逾期次数
			    String  overCount = row.getCell(8)!=null ? row.getCell(8).toString() : "";
			    if(!ObjectHelper.isEmpty(overCount)){
			    	String[] overs = overCount.split("\\.");
			    	  if(!ObjectHelper.isEmpty(overs)){
					    	 entity.setOverCount(overs[0]);
					    }
			    }
			   
			    //标示
			    String lateMark = row.getCell(9)!=null ? row.getCell(9).toString() : "";
			    entity.setLateMark(gtltToCode(lateMark));
			    //逾期天数
			    String  overdueDays = row.getCell(10) != null ? row.getCell(10).toString() : "";
			    if(!ObjectHelper.isEmpty(overdueDays)){
			    	entity.setOverdueDays(Double.valueOf(overdueDays).intValue());
			    }
			    //接口  deductTypeNameToCode
			    String deducttype = row.getCell(11)!=null ? row.getCell(11).toString() : "";
			    entity.setDeducttype(deductTypeNameToCode(deducttype));
			    // 划扣金额 “<” ">="
			    String  amountMark  =  row.getCell(12)!=null ? row.getCell(12).toString() : "";
			    entity.setAmountMark(gtltToCode(amountMark));
			    //划扣金额
			    String applyReallyAmount =  row.getCell(13)!=null ? row.getCell(13).toString() : "";
			    if(!ObjectHelper.isEmpty(applyReallyAmount)){
			    entity.setApplyReallyAmount(new BigDecimal(applyReallyAmount));
			    }
			    // 规则
			    String ruleCode = row.getCell(14)!=null ? row.getCell(14).toString() : "";
			    if(!"".equals(ruleCode)){
			     entity.setRuleCode(Double.valueOf(ruleCode).intValue()+"");
			    }
			    list.add(entity);
  		}
	 }
       
      /**
       * 平台名称转化为code
       * @param Strname
       * @return
       */
     public String plantNameToCode(String strName){
    	 StringBuilder  code = new StringBuilder();
    	 String codes = "";
    	 if(!ObjectHelper.isEmpty(strName)){
    		 String[] names  = strName.split(",");
    		 for(String name : names){
    			 name = name.replace(" ", "");
    			 DeductPlat plat = DeductPlat.parseByName(name);
    			 if(plat != null){
    				 code.append(plat.getCode()).append(",");
    			 }
    		 }
    		 if(code != null){
    			  codes =code.substring(0, code.length()-1);
    		 }
    		 
    	 }
    	 return codes;
     }
     
     /**
      * 银行名称转化为code
      * @param strName
      * @return
      */
     public String bankNameToCode(String strName){
    	 StringBuilder  code = new StringBuilder();
    	 String codes = "";
    	 if(!ObjectHelper.isEmpty(strName)){
    		 String[] names  = strName.split(",");
    		 for(String name : names){
    			 name = name.replace(" ", "");
    			 String bank = OpenBank.getOpenBankByName(name);
    			 code.append(bank).append(",");
    		 }
    		 codes =code.substring(0, code.length()-1);
    	 }
    	 return codes;
     }
     
     /**
      * 借款name 转化为 code
      * @param args
      */
     
     public String loanStatusNameToCode(String strName){
    	 StringBuilder  code = new StringBuilder();
    	 String codes = "";
    	 if(!ObjectHelper.isEmpty(strName)){
    		 String[] names  = strName.split(",");
    		 for(String name : names){
    			 name = name.replace(" ", "");
    			 String status = LoanStatus.getLoanStatusNameToCode(name);
    			 code.append(status).append(",");
    		 }
    		 codes =code.substring(0, code.length()-1);
    	 }
    	 return codes;
     }
     
     /**
      * 通联/卡联/畅捷是否签约
      * @param args
      */
     
     public String signNameToCode(String name){
    	 String  code = "";
    	 if(!ObjectHelper.isEmpty(name)){
    		 if("否".equals(name)){
    			 code = "0";
    		 }
             if("是".equals(name)){
    			 code = "1";
    		 }
    	 }
    	 return code;
     }
     
     /**
      * 实时批量转化
      * @param args
      */
     
     public String deductTypeNameToCode(String strName){
    	 StringBuilder  code = new StringBuilder();
    	 String codes = "";
    	 if(!ObjectHelper.isEmpty(strName)){
    		 String[] names  = strName.split(",");
    		 for(String name : names){
    			 name = name.replace(" ", "");
    			 DeductType type = DeductType.parseByName(name);
    			 if(type != null){
    				 code.append(type.getCode()).append(",");
    			 }
    		 }
    		 if(code != null){
    			  codes =code.substring(0, code.length()-1);
    		 }
    		 
    	 }
    	 return codes;
     }
     
     /**
      * 将大于小于转化为code
      * @param str
      * @return
      */
     public String gtltToCode(String str){
    	       String code = "";
    	  if(!ObjectHelper.isEmpty(str)){
    		  if("<".equals(str)){
    			  code = "0";
    		  }
    		  if(">=".equals(str)){
    			  code = "1";
    		  }
    		   
    	  }
    	  return code;
     }
     
     
     /**
 	 * 导出
 	 * @param map
 	 * @return
 	 */
 	@RequestMapping(value = "exportExcel")
 	public void exportExcel(@RequestParam Map<String,Object> map,HttpServletRequest request,
 			HttpServletResponse response,Model model){
 		 String idVal  = (String) map.get("ids");
 		 if (StringUtils.isNotEmpty(idVal) && idVal.split(",").length > 0) {
			// 有勾选数据,id参数添加,存入List<String>
 			map.put("ids", Arrays.asList(idVal.split(",")));
		 }
 		 List<AppointmentTemplate> appointmentList  = appointmentDudctService.queryList(map);
	     String[] header = {"签约平台","银行","预约时间","划扣平台","通联是否批量签约","卡联是否签约","畅捷是否签约","借款状态",
	    		             "累计逾期期数","标示","逾期天数","接口","金额标示","划扣金额","规则"};
	     ExportAppointmentDeductHelper.exportExcels(appointmentList, header, response);
 	}
}
