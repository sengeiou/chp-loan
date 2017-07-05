package com.creditharmony.loan.borrow.payback.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.borrow.payback.entity.PhoneSale;
import com.creditharmony.loan.borrow.payback.service.PhoneSaleHandleService;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.RepaymentDateService;
import com.creditharmony.loan.common.vo.DefaultServiceVO;

@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/phonesale")
public class PhoneSaleHandleController extends BaseController{

	  @Autowired
	  private PhoneSaleHandleService service;
	  @Autowired
	  private RepaymentDateService dateService;
	  
	  @Autowired
	  private HistoryService historyService;
	
	/**
	 * 查询 电销还款提醒待办 2017年3月3日 By 翁私
	 * @param model
	 * @param request
	 * @param map
	 * @return 跳转路径
	 */
	@RequestMapping(value = "phoneSaleHandle")
	public String phoneSaleHandle(Model model, HttpServletRequest request,	HttpServletResponse response,  PhoneSale sale){
	Page<PhoneSale> page = service.phoneSaleHandle(new Page<PhoneSale>(request, response),sale);
		// 查询还款日
		List<GlBill> dayList = dateService.getRepaymentDate();
		model.addAttribute("dayList", dayList);
		model.addAttribute("page", page);
		model.addAttribute("bean", sale);
		return "borrow/payback/phonesale/phoneSaleHandle";
	}
	
	/**
	 * 标记提醒
	 * @param bean
	 * @param model
	 * @param request
	 * @param response
	 * @param errors
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "signRemindupdate")
	public DefaultServiceVO  signRemindupdate(PhoneSale bean,Model model,
			HttpServletRequest request,	HttpServletResponse response,BindingResult errors){
		service.signRemindupdate(bean);
	    return DefaultServiceVO.createSuccess("success");
		
	}
	
	/**
	 * 历史操作
	 * 2017年3月6日
	 * By wengsi 
	 * @param request 请求
	 * @param response 返回
	 * @param model model
	 * @param id 提醒id
	 */
	@RequestMapping(value = "showPayBackHis")
	public String showPayBackHis(HttpServletRequest request,
			HttpServletResponse response, Model model, String id,
			String payBackApplyId,String lisi) {
		try {
			Page<PaybackOpe> pageOpe = new Page<PaybackOpe>(request, response);
				pageOpe = service.getPaybackRemindOpe(id, pageOpe);
			// 传递数据到前台页面展示
				model.addAttribute("page", pageOpe);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("id", id);
		return "/borrow/payback/phonesale/paybackhis";
	}
}
