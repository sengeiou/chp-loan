package com.creditharmony.loan.borrow.trusteeship.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.trusteeship.service.GoldAccountCeilingService;
import com.creditharmony.loan.borrow.trusteeship.view.GoldAccountCeiling;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;
/**
 * 金账户上限列表控制器
 * 
 * @Class GoldAccountCeilingController
 * @author 张建雄
 * @Create In 2016年3月26日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/trusteeship/ceiling")
public class GoldAccountCeilingController extends BaseController {
	@Autowired
	private GoldAccountCeilingService service;
	@Autowired
	private CityInfoService cityManager;
	@RequestMapping(value = "init")
	public String init(HttpServletRequest request,
			HttpServletResponse response, GoldAccountCeiling params,
			Model model) {
			
		List<Object> ceilings = service.findGCCeilingList(new
				  Page<GoldAccountCeiling>(request,response), params);
		@SuppressWarnings("unchecked")
		Page<GoldAccountCeiling> ceiling = (Page<GoldAccountCeiling>) ceilings.get(0);
		String totalDeducts = (String) ceilings.get(1);
		model.addAttribute("totalDeducts", totalDeducts);
		model.addAttribute("ceiling", ceiling);
		model.addAttribute("params", params);
		return "borrow/trusteeship/goldAccountCeilingList";
	}
	/**
	 * 获取金账户上限额度
	 * 
	 * @Class getCeiling
	 * @author 张建雄
	 * @Create In 2016年2月24日
	 */
	@RequestMapping(value = "getCeiling")
	public String getCeiling(HttpServletRequest request,
			HttpServletResponse response,
			Model model) {
		Map<String,String> ceiling = service.selectCeilingMoney();
		model.addAttribute("ceiling", ceiling);
		return "borrow/trusteeship/settingCeiling";
	}
	/**
	 * 保存金账户上限额度2016年2月19日 By 张建雄
	 * 
	 * @param request
	 * @param response
	 * @param cid
	 *            要进行导出的单子的id
	 */
	@RequestMapping(value = "exportCeiling")
	public void exportCeiling(HttpServletRequest request,
			HttpServletResponse response, String cid,GoldAccountCeiling params) {
		  service.exportCeiling(cid, params, response);
		//ExcelUtils excelutil = new ExcelUtils();
		//try {
			// 如果有进行选择，获得选中单子的list
			//List<GoldAccountCeiling> impSettlementOfClaimsList = service.exportCeiling(cid,params);
			// 如果没有进行选择，默认为当页中的list
			/*excelutil.exportExcel(impSettlementOfClaimsList, FileExtension.TRUSTEESHIP_CHANNEL
					+ System.currentTimeMillis(),
					GoldAccountCeiling.class, FileExtension.XLSX,
					FileExtension.OUT_TYPE_TEMPLATE, response, null);*/
		//} catch (Exception e) {
			//e.printStackTrace();
		//}
	}
	/**
	 * 查看用户信借申请信息
	 * 2016年2月24日 By 张建雄
	 * @param request
	 * @param response
	 * @param cid
	 *            
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "checkInfo")
	public String checkUserInfo(HttpServletRequest request,
			HttpServletResponse response,
			Model model,String applyId) {
		Map<String,Object> maps =  service.findUserInfo(applyId);
		Map<String,String> pageList = (Map<String, String>) maps.get("info");
		List<Map<String,String>> creditList = (List<Map<String, String>>) maps.get("creditList");
		List<Map<String,String>> contactList = (List<Map<String, String>>) maps.get("contactList");
		List<CityInfo> provinceList = cityManager.findProvince();
		model.addAttribute("info", pageList);
		model.addAttribute("creditList", creditList);
		model.addAttribute("contactList", contactList);
		model.addAttribute("provinceList", provinceList);
		return "borrow/trusteeship/checkUserInfoList";
	}	
	/**
	 * 保存金账户上限额度2016年2月19日 By 张建雄
	 * 
	 * @param request
	 * @param response
	 * @param ceilingMoney
	 *            金账户上限额度
	 */
	@ResponseBody
	@RequestMapping(value = "gaCeiling")
	public String jinxinCeiling(HttpServletRequest request,
			HttpServletResponse response,
			BigDecimal ceilingMoney) {
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		
		service.setCeilingMoney(ceilingMoney,user.getUserCode());
		return BooleanType.TRUE;
	}
	/**
	 * 金账户数据归档
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "goldAccount")
	public String jinxin(HttpServletRequest request,
			HttpServletResponse response) {
		service.updateCeiling();
		return BooleanType.TRUE;
	}	
}
