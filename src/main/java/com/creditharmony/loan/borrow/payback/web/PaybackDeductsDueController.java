package com.creditharmony.loan.borrow.payback.web;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackDeductsDue;
import com.creditharmony.loan.borrow.payback.service.PaybackDeductsDueService;
import com.google.common.collect.Lists;

/**
 * 预约划扣列表业务处理Controller
 * @Class Name PaybackDeductsDueController
 * @author zhaojinping
 * @Create In 2016年1月8日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/deductsDue")
public class PaybackDeductsDueController extends BaseController{
  
	@Autowired
	private PaybackDeductsDueService paybackDeductsDueService;
	
	/**
	 * 预约划扣列表
	 * 2015年12月12日
	 * By zhaojinping
	 * @param request
	 * @param response
	 * @param model
	 * @return page
	 */
	@RequestMapping(value="centerDeductsDue")
	public String centerDeductsDue(PaybackDeductsDue paybackDeductsDue,HttpServletRequest request,HttpServletResponse response,Model model){
		String bank =  paybackDeductsDue.getBank();
		if (!ObjectHelper.isEmpty(bank)) {
			paybackDeductsDue.setBankIds(this.splitString(bank));
			//paybackDeductsDue.setBankId(bank);
		}
		Page<PaybackDeductsDue> waitPage = paybackDeductsDueService.getDeductsDue(new Page<PaybackDeductsDue>(request, response),paybackDeductsDue);
		model.addAttribute("waitPage", waitPage);
		logger.debug("invoke PaybackDeductsDueController method: findContractData, consult.id is: "+ waitPage);
		return "borrow/payback/deductsdue/deductsDue";
	}
	
	/**
	 * 将预约划扣的时间置为有效
	 * 2015年12月12日
	 * By zhaojinping
	 * @param id
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value="toUse")
	public String toUse(PaybackDeductsDue paybackDeductsDue){
		// 向还款申请表中插入记录
		List<String> idList = Lists.newArrayList();
		String id = paybackDeductsDue.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(id != null && !"".equals(id)){
			paybackDeductsDue.setIds(this.splitString(id));
		}else{
			List<PaybackDeductsDue> list = paybackDeductsDueService.getDeductsDue(paybackDeductsDue);
			for(int i=0;i<list.size();i++){
				idList.add(list.get(i).getId());
			}
			paybackDeductsDue.setIds(idList);
		}
		paybackDeductsDue.setCreateDayStr(sdf.format(new Date()));
		paybackDeductsDueService.updateUse(paybackDeductsDue);
		/*for (int i = 0; i < idList.length; i++) {
			if (!ObjectHelper.isEmpty(idList[i])) {
				paybackDeductsDue.setId(idList[i]);
				paybackDeductsDue.setEffectiveFlag(YESNO.YES.getCode());
				paybackDeductsDueService.updateUse(paybackDeductsDue);
			}
		}*/
		return JsonMapper.nonDefaultMapper().toJson(paybackDeductsDue);
	}

	/**
	 * 将预约划扣的时间置为无效
	 * 2015年12月12日
	 * By zhaojinping
	 * @param id
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value="toUnUse")
	public String toUnUse(PaybackDeductsDue paybackDeductsDue){
		// 向还款申请表中插入记录
		List<String> idList = Lists.newArrayList();
		String id = paybackDeductsDue.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(id != null && !"".equals(id)){
			paybackDeductsDue.setIds(this.splitString(id));
		}else{
			List<PaybackDeductsDue> list = paybackDeductsDueService.getDeductsDue(paybackDeductsDue);
			for(int i=0;i<list.size();i++){
				idList.add(list.get(i).getId());
			}
			paybackDeductsDue.setIds(idList);
		}
		paybackDeductsDue.setCreateDayStr(sdf.format(new Date()));
		paybackDeductsDueService.updateUnUse(paybackDeductsDue);
		/*PaybackDeductsDue paybackDeductsDue = new PaybackDeductsDue();
		for (int i = 0; i < idList.length; i++) {
			if (!ObjectHelper.isEmpty(idList[i])) {
				paybackDeductsDue.setId(idList[i]);
				paybackDeductsDue.setEffectiveFlag(YESNO.NO.getCode());
				paybackDeductsDueService.updateUnUse(paybackDeductsDue);
			}
		}*/
		return JsonMapper.nonDefaultMapper().toJson(paybackDeductsDue);
	}
	
	/**
	 * 新增预约划扣时间
	 * 2016年1月8日
	 * By zhaojinping
	 * @param dueBank
	 * @param dueDateStr
	 * @param dueTimeStr
	 * @return json 数据
	 */
	@ResponseBody
	@RequestMapping(value="addDue",method=RequestMethod.POST)
	public void  addDue(HttpServletResponse response,String dueBank, String dueDateStr,String dueTimeStr) throws Exception {
		PrintWriter out = response.getWriter();
		String[] dueTimeArray = dueTimeStr.split(",");
		for(int i=0;i<dueTimeArray.length;i++){
			PaybackDeductsDue paybackDeductsDue = new PaybackDeductsDue();
			paybackDeductsDue.setBankIds(this.splitString(dueBank));
			String dueStrNew = dueDateStr + " " + dueTimeStr + ":00";
			//Date dueTime = DateUtils.parseDate(dueStrNew);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			paybackDeductsDue.setCreateDayStr(sdf.format(new Date()));
			paybackDeductsDue.setDueTimeStr(dueStrNew);
			List<PaybackDeductsDue> list = paybackDeductsDueService.selectIsAppointment(paybackDeductsDue);
			if(list.size()>0){
				out.print("{\"flag\":false,\"msg\":\"有已预约时间\"}");
				return;
			}
		}
		User user = UserUtils.getUser();
		Map<String,Object>  map= isAllowSubmitAppointment(dueDateStr,dueTimeArray);
		if((Boolean)map.get("flag")){
			paybackDeductsDueService.addDue( dueBank,dueDateStr,user.getId(),dueTimeArray);
			out.print("{\"flag\":true,\"msg\":\"新增预约成功！\"}");
		}else{
			//return (String)map.get("message");
			out.print("{\"flag\":false,\"msg\":\""+ (String)map.get("message") +"\"}");
		}
		//return "新增预约成功！";
	}
	
	
	/**
	 * 新增金账户预约
	 * 2016年1月8日
	 * By zhaojinping
	 * @param dueBank
	 * @param dueDateStr
	 * @param dueTimeStr
	 * @return json 数据
	 */
	@ResponseBody
	@RequestMapping(value="addGoldAccountDue",method=RequestMethod.POST)
	public void  addGoldAccountDue(HttpServletResponse response,String modeWay, String dueDateStr,String dueTimeStr) throws Exception {
		PrintWriter out = response.getWriter();
		String[] dueTimeArray = dueTimeStr.split(",");
		for(int i=0;i<dueTimeArray.length;i++){
			PaybackDeductsDue paybackDeductsDue = new PaybackDeductsDue();
			paybackDeductsDue.setModeWay(modeWay);
			String dueStrNew = dueDateStr + " " + dueTimeArray[i] + ":00";
			//Date dueTime = DateUtils.parseDate(dueStrNew);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//paybackDeductsDue.setCreateDayStr(sdf.format(new Date()));
			paybackDeductsDue.setDueTimeStr(dueStrNew);
			List<PaybackDeductsDue> list = paybackDeductsDueService.selectIsAppointment(paybackDeductsDue);
			if(list.size()>0){
				out.print("{\"flag\":false,\"msg\":\"有已预约时间\"}");
				return;
			}
		}
		User user = UserUtils.getUser();
		Map<String,Object>  map= isAllowSubmitAppointment(dueDateStr,dueTimeArray);
		if((Boolean)map.get("flag")){
			paybackDeductsDueService.addGoldAccountDue( modeWay,dueDateStr,user.getId(),dueTimeArray);
			out.print("{\"flag\":true,\"msg\":\"新增预约成功！\"}");
		}else{
			//return (String)map.get("message");
			out.print("{\"flag\":false,\"msg\":\""+ (String)map.get("message") +"\"}");
		}
		//return "新增预约成功！";
	}
	
	/**
	 * 查询这个时间是否已经预约了
	 * 2016年2月23日
	 * By wengsi
	 * @param dueBank
	 * @param dueDateStr
	 * @param dueTimeStr
	 * @return json数据
	 */
	@ResponseBody
	@RequestMapping(value="selectIsAppointment",method=RequestMethod.POST)
	public String selectIsAppointment(String dueBank, String dueDateStr,String dueTimeStr){
		PaybackDeductsDue paybackDeductsDue = new PaybackDeductsDue();
		//paybackDeductsDue.setDueBank(dueBank);
		paybackDeductsDue.setBankIds(this.splitString(dueBank));
		String dueStrNew = dueDateStr + " " + dueTimeStr + ":00";
		//Date dueTime = DateUtils.parseDate(dueStrNew);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		paybackDeductsDue.setCreateDayStr(sdf.format(new Date()));
		paybackDeductsDue.setDueTimeStr(dueStrNew);
		List<PaybackDeductsDue> list = paybackDeductsDueService.selectIsAppointment(paybackDeductsDue);
		if(list.size()>0){
			return "existence";
		}
		return "noexistence";
	}
	
	/**
	 * 判断是否允许进行提交
	 * 2016年2月23日
	 * By wengsi
	 * @param dueBank
	 * @param dueDateStr
	 * @param dueTimeStr
	 * @return json数据
	 * @return
	 */
	private Map<String, Object> isAllowSubmitAppointment(String appointmentDate,String[] appointmentTime) {
		Map<String,Object> mapMessage = new HashMap<String,Object>(); 
		try {
				mapMessage.put("flag",true);
				if (null == appointmentDate || null == appointmentTime || appointmentTime.length == 0) {
					mapMessage.put("flag",false);
					mapMessage.put("message", "新增预约失败。失败原因：日期为空。");
					return mapMessage;
				}
				Map<String, String> map = new HashMap<String, String>();
				for (int i = 0; i < appointmentTime.length; i++) {
					String schedule = appointmentDate + " " + appointmentTime[i] + ":00";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					map.put(schedule, "");
					// 预约时间
					Date scheduleDate = sdf.parse(schedule);
					Date nowDate = new Date();
					if (scheduleDate.before(nowDate)) {
						mapMessage.put("flag",false);
						mapMessage.put("message", "新增预约失败。失败原因：预约发起时间不能比当前时间早。");
					//modelAndView.addObject("ResponseMessage", JackUtils.createResponseMessage(false,"新增预约失败。失败原因：预约发起时间不能比当前时间早。", ""));
					return mapMessage;
					}
				}
				if (map.size() != appointmentTime.length) {
					mapMessage.put("flag",false);
				    mapMessage.put("message", "新增预约失败。失败原因：预约出现重复时间点。");
				    return mapMessage;
				}
			} catch (Exception e) {
				logger.error("预约时间校验出现异常", e);
				mapMessage.put("flag",false);
			    mapMessage.put("message", "新增预约失败。失败原因：发生未知异常。");
			}
		return mapMessage;
	}
	
	public List<String> splitString(String string) {
		String[] array = string.split(",");
		List<String> list = new ArrayList<String>(Arrays.asList(array));
		return list;
	}
	
}
