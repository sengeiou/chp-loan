package com.creditharmony.loan.borrow.stores.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.common.type.DeleteFlagType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.OrgGl;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.service.OrgGlService;
import com.creditharmony.loan.common.utils.ExcelUtils;

/**
 * 门店分配列表
 * 
 * @Class Name StoresAllotController
 * @author lirui
 * @Create In 2015年12月4日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/stores")
public class StoresAllotController extends BaseController {

	@Autowired
	private CityInfoService cityManager;

	@Autowired
	private OrgGlService orgService;

	@Autowired
	private LoanPrdMngService svc;

	/**
	 * 门店列表展示页面 2015年12月7日 By 王彬彬
	 * 
	 * @param model
	 * @param org
	 * @return
	 */
	@RequestMapping(value = "storesAllotList")
	public String stroesAllotList(HttpServletRequest request,
			HttpServletResponse response, Model model, OrgGl org) {
		// 省份获取
		List<CityInfo> provinceList = cityManager.findProvince();
		model.addAttribute("provinceList", provinceList);

		// 取得门店列表
		Map<String, Object> filter = new HashMap<String, Object>();

		filter.put("deleteFlag", DeleteFlagType.NORMAL);

		Page<OrgGl> orgPage = orgService.findOrg(new Page<OrgGl>(request,
				response), filter);

		model.addAttribute("orgPage", orgPage);

		model.addAttribute("org", org);
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		// loanPrd.setClassType(classType);
		List<LoanPrdMngEntity> productList = svc.selPrd(loanPrd);
		
		model.addAttribute("productList", productList);
		
		return "stores/storesAllotList";
	}

	/**
	 * 下载门店信息 2015年12月19日 By 王彬彬
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param org
	 * @return
	 */
	@RequestMapping(value = "storesOut")
	public void getExcelDataList(HttpServletRequest request,
			HttpServletResponse response, Model model, OrgGl org) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("deleteFlag", DeleteFlagType.NORMAL);

		Page<OrgGl> orgPage = orgService.findOrg(new Page<OrgGl>(request,
				response), filter);

		ExcelUtils excelutil = new ExcelUtils();
		excelutil.exportExcel(orgPage.getList(), FileExtension.STORE_NAME
				+ System.currentTimeMillis(), OrgGl.class, FileExtension.XLSX,
				FileExtension.OUT_TYPE_TEMPLATE, response, 1);
	}
}
