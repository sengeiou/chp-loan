package com.creditharmony.loan.car.carContract.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carContract.ex.CarFirstDeferEx;
import com.creditharmony.loan.car.carContract.service.CarContractService;

/**
 * 客户信息
 * @Class Name CarFirstDeferController
 * @author ganquan
 * @Create In 2016年3月4日
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/car/carContract/firstDefer")
public class CarFirstDeferController  extends BaseController {
	@Autowired
	private CarContractService carContractService;
	
	private static final String formatStr = "yyyy-MM-dd 00:00:00";
	/**
	 * 根据查询条件获得首次展期列表
	 * 2016年3月4日
	 * By 甘泉
	 * @param CarFirstDeferEx
	 * @return String
	 */
	@RequestMapping(value = "selectDeferList")
	public String selectDeferList(Model model,@ModelAttribute(value = "carFirstDeferEx") CarFirstDeferEx carFirstDeferEx,
			HttpServletRequest request,HttpServletResponse response){
		Page<CarFirstDeferEx> page = new Page<CarFirstDeferEx>(request, response);
		// 查询本门店下的展期待办列表
		User currentUser = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		Org org = currentUser.getDepartment();
		String orgType = org != null ? org.getType() : "";
		if(LoanOrgType.TEAM.key.equals(orgType)){
			org = OrgCache.getInstance().get(org.getParentId());
		}
		carFirstDeferEx.setStoreCode(org.getId());
		
		page=carContractService.selectDefer(page, carFirstDeferEx);
		
		if(page.getList().size() > 0 && page.getList() != null){
			for (CarFirstDeferEx p : page.getList()) {
				p.setProductType(CarLoanProductType.parseByCode(p.getProductType()).getName());
				p.setConditionalThroughFlag(DictCache.getInstance().getDictLabel("jk_car_loan_flag", p.getConditionalThroughFlag()));
				p.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", p.getLoanFlag()));
				p.setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_car_loan_status", p.getDictLoanStatus()));
				p.setContractEndDay(getContractExpirationDate(p.getContractEndDay()));
			}
		}
		model.addAttribute("page",page);
		return "car/carExtend/carExtendApply/carFirstDeferList";
	}
	
	/**
	 * 
	 * 2016年6月14日
	 * By 申诗阔
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	private Date getContractExpirationDate(Date date) {
		if (date == null) {
			return null;
		}
		Date currDate = convertStringToDate(formatStr, DateUtils.formatDate(date, formatStr));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDate);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		return calendar.getTime();
	}
	
	public static Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (date);
	}
}
