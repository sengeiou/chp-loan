/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.webHistoryController.java
 * @Create By 朱静越
 * @Create In 2015年12月1日 下午2:39:02
 */
/**
 * @Class Name HistoryController
 * @author 朱静越
 * @Create In 2015年12月1日
 */
package com.creditharmony.loan.car.carGrant.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carGrant.service.CarUrgeHistoryService;
import com.creditharmony.loan.car.common.entity.CarUrgeOpe;
@Controller
@RequestMapping(value = "${adminPath}/car/grant/carUrgeHistory")
public class CarUrgeOpeController extends BaseController {
	@Autowired
	private CarUrgeHistoryService carUrgeHistoryService;
	
	/**
	 * 催收服务费操作历史获取
	 * 2016年6月22日
	 * By 朱静越
	 * @param request
	 * @param response
	 * @param model
	 * @param rUrgeId
	 * @return
	 */
	@RequestMapping(value = "showCarUrgeHistory")
	public String showCarUrgeHistory(HttpServletRequest request,
			HttpServletResponse response, Model model, 
			String rUrgeId ) {
		try {
			Page<CarUrgeOpe> page = new Page<CarUrgeOpe>(request, response);
			// 数据库查询列表数据
			page = carUrgeHistoryService.getUrgeOpe(rUrgeId, TargetWay.SERVICE_FEE, page);
			// 传递数据到前台页面展示
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("催收服务费操作历史获取失败,ID："+rUrgeId);
		}
		model.addAttribute("payBackApplyId", rUrgeId);
		return "car/grant/carUrgeHis";
	}

}