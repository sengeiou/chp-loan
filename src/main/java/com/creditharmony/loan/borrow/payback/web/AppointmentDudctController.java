package com.creditharmony.loan.borrow.payback.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.borrow.payback.entity.Appointment;
import com.creditharmony.loan.borrow.payback.service.AppointmentDudctService;
import com.creditharmony.loan.common.constants.AppointmentConstant;
import com.creditharmony.loan.common.constants.AppointmentRule;
import com.creditharmony.loan.common.constants.LoanStatus;
import com.creditharmony.loan.common.vo.DefaultServiceVO;
import com.creditharmony.loan.common.vo.Errors;

@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/appointment")
public class AppointmentDudctController {
	
	
	@Autowired
	private AppointmentDudctService appointmentDudctService;
	
	@Autowired
	private Validator validator;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  

	
	/**
	 * 查询预约划扣列表
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "queryList")
	public String queryList(@RequestParam Map<String,Object> map,HttpServletRequest request,
			HttpServletResponse response,Model model){
		map.put("appointmentDayLabel", map.get("appointmentDay"));
		map.put("appointmentDay", null);
		Page<Appointment> appointmentPage = appointmentDudctService.queryList(map, request, response);
		List<String>  list =  appointmentDudctService.queryOverCount();
		Map<String,String> mapRule = AppointmentRule.getAppointmentRuleMap();
		model.addAttribute("mapRule",mapRule);
		model.addAttribute("page", appointmentPage);
		model.addAttribute("overCountList",list);
		map.put("appointmentDay", getDate((String)map.get("appointmentDayLabel")));
		model.addAttribute("paramMap",map);
		
		return "borrow/payback/appointment/appointmentList";
	}
	
	/**
	 * 新增或者修改
	 * @param appointment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertOrUpdate")
	public DefaultServiceVO insertOrUpdate(Appointment bean,Model model,
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
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
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
	public String goDetail(Appointment appointment,Model model){
		// 如果有id去查询
		if(!ObjectHelper.isEmpty(appointment.getId())){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", appointment.getId());
		    List<Appointment> list = appointmentDudctService.queryList(map);
		    Appointment  bean = list.get(0);
		    model.addAttribute("bean",bean);
		}
		List<String>  list =  appointmentDudctService.queryOverCount();
		
		model.addAttribute("overCountList",list);
		model.addAttribute("loanMap",LoanStatus.getLoanStatusMap());
		return "borrow/payback/appointment/appointmentDetail";
		
	}
	
	@ResponseBody
	@RequestMapping(value = "changSingleEffect")
	public DefaultServiceVO  changSingleEffect(Appointment bean,Model model,
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
		List<Appointment> list = appointmentDudctService.queryList(map);
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
		    List<Appointment> list = appointmentDudctService.queryList(map);
		    if(list.size()>0){
		    	if(verificationStatus(list)){
		    		appointmentDudctService.deleteData(list);
		    		return DefaultServiceVO.createSuccess("删除成功!");
		    	}else{
		    		return DefaultServiceVO.createSuccess("存在不允许删除的数据,请重新选择数据!");
		    	}
		    }
		    return  DefaultServiceVO.createSuccess("没有要操作的数据!");
	}
	
	
	/**
	 * 判断数据中是否有不符合规则的数据
	 * @param list
	 * @param sign
	 * @return
	 */
	public Boolean verificationStatus(List<Appointment> list,String sign){
		Boolean b  = true;
		for(Appointment bean : list){
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
	public Boolean verificationStatus(List<Appointment> list){
		Boolean b  = true;
		for(Appointment bean : list){
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
	public boolean  judgeChange(Appointment bean){
		String id = "";
		if(!ObjectHelper.isEmpty(bean.getId())){
			id = bean.getId();
		}
		bean.setId("");
		
		List<Appointment> list = appointmentDudctService.queryList(bean);
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
	public boolean judgeDate(Appointment bean){
		String dateFomat = format.format(bean.getAppointmentDay());
		String[] date = dateFomat.split(":");
		if("00".equals(date[1]) || "30".equals(date[1]) ){
			return false;
		}else{
			return true;
		}
	}
	
	/*
	 * 添加固定规则的数据
	 */
	
	@ResponseBody
	@RequestMapping(value = "appointmentRule")
	public DefaultServiceVO appointmentRule(@RequestParam Map<String,Object> map,HttpServletRequest request,
			HttpServletResponse response,Model model){
		
		 if(ObjectHelper.isEmpty(map.get("ruleCode"))){
			 return DefaultServiceVO.errorMsg("规则不能为空!");
		 }
		    
		 if(ObjectHelper.isEmpty(map.get("appointmentDay"))){
			 return DefaultServiceVO.errorMsg("日期不能为空!");
		 }
		 
		 Appointment bean = new Appointment();
		 bean.setRuleCode((String)map.get("ruleCode"));
		 try {
			bean.setAppointmentDay(format1.parse((String)map.get("appointmentDay")));
			bean.setAppointmentDayLabel((String)map.get("appointmentDay"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		 List<Appointment> list = appointmentDudctService.queryRuleList(bean);
		 if(!ObjectHelper.isEmpty(list)){
			 return DefaultServiceVO.errorMsg("数据已经存在!");
		  }
		 List<Appointment> rulelist =   appointmentDudctService.queryRuleDataList(bean);
		 if(ObjectHelper.isEmpty(rulelist)){
			 return DefaultServiceVO.errorMsg("没有可以用的规则数据!");
		  }
		 try{
			 appointmentDudctService.appointmentRule(bean);
			 return DefaultServiceVO.createSuccess("添加成功!");
		 }catch(Exception e){
			 e.printStackTrace();
			 return DefaultServiceVO.errorMsg("添加失败!");
		 }
	}
}
