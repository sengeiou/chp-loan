package com.creditharmony.loan.channel.goldcredit.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.trusteeship.service.GoldAccountCeilingService;
import com.creditharmony.loan.channel.goldcredit.excel.ExportSummaryHelper;
import com.creditharmony.loan.channel.goldcredit.service.SummaryService;
import com.creditharmony.loan.channel.goldcredit.view.SettingCellingNumEntity;
import com.creditharmony.loan.channel.goldcredit.view.Summary;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.google.common.collect.Maps;

/**
 * 汇总表 （上线设置）
* Description:
* @class SummaryController
* @author wengsi 
* @date 2017年6月5日下午5:49:31
 */
@Controller
@Component
@RequestMapping(value = "${adminPath}/channel/goldcredit/summary")
public class SummaryController extends BaseController {
	@Autowired
	private SummaryService service;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private ProvinceCityManager provinceCityManager;
	@Autowired
	private GoldAccountCeilingService goldService;
	
	@Autowired
	private LoanPrdMngService loanPrdMngService;
	
	@Autowired
	private ExportSummaryHelper exportSummaryHelper;
	
	
	@RequestMapping(value = "init")
	public String init(HttpServletRequest request,HttpServletResponse response, Summary params,Model model) {
		
		if (params != null){
			if(ObjectHelper.isEmpty(params.getDataStatus())){
				params.setDataStatus("0");

			}
			List<Object> ceilings = service.findGCCeilingList(new Page<Summary>(request,response), params);
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
			model.addAttribute("productList", productList);
			@SuppressWarnings("unchecked")
			Page<Summary> ceiling = (Page<Summary>) ceilings.get(0);
			@SuppressWarnings("unchecked")
			Map<String,String> totalDeducts =  (Map<String, String>) ceilings.get(1);
			model.addAttribute("totalDeducts", totalDeducts);
			model.addAttribute("ceiling", ceiling);
			model.addAttribute("params", params);

		}
		return "channel/goldcredit/summaryList";
		
	}
	
	/**
	 * 保存金信上限额度2016年2月19日 By 张建雄
	 * 
	 * @param request
	 * @param response
	 * @param cid
	 *            要进行导出的单子的id
	 */
	@RequestMapping(value = "exportCeiling")
	public void exportCeiling(HttpServletRequest request,
			HttpServletResponse response, String cid,Summary params) {
		Map<String ,Object> conditions = Maps.newHashMap();
		if (StringUtils.isNotEmpty(params.getStoreOrgId())){
			String []storeOrgIds = params.getStoreOrgId().split(",");
			conditions.put("storeOrgIds", storeOrgIds);
		}
		if (StringUtils.isNotEmpty(cid)) {
			String [] loanCodes = cid.split(",");
			params.setLoanCode(cid);
			conditions.put("loanCodes", loanCodes);
		}
		conditions.put("params", params);
		Map<String,Object> totalDeducts =  (Map<String, Object>) service.getCeilingSumExcel(conditions);
		exportSummaryHelper.exportData(conditions,totalDeducts, response);
		
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
			Model model,String loanCode) {
		Map<String,Object> maps =  service.findUserInfo(loanCode);
		Map<String,String> pageList = (Map<String, String>) maps.get("info");
		List<Map<String,String>> creditList = (List<Map<String, String>>) maps.get("creditList");
		List<Map<String,String>> contactList = (List<Map<String, String>>) maps.get("contactList");
		List<CityInfo> provinceList = cityManager.findProvince();
		List<Map<String,String>> houseList=service.findHouseList(loanCode);
		if(houseList!=null && houseList.size()>0){
			for(Map<String,String> map : houseList){
				String proviceInfo = provinceCityManager.getProvinceCity(map.get("house_province"), 
						map.get("house_city"), map.get("house_area"));
				map.put("house_base_info", proviceInfo + map.get("house_address"));
			}
		}
		model.addAttribute("house", houseList);
		model.addAttribute("info", pageList);
		model.addAttribute("creditList", creditList);
		model.addAttribute("contactList", contactList);
		model.addAttribute("provinceList", provinceList);
		return "channel/goldcredit/checkUserInfoList";
	}	
	/**
	 * 保存上限额度2016年2月19日 By 张建雄
	 * 
	 * @param request
	 * @param response
	 * @param ceilingMoney
	 *            上限额度
	 */
	@ResponseBody
	@RequestMapping(value = "settingCeilingNum")
	public String settingCeilingNum(HttpServletRequest request,
			HttpServletResponse response,
			SettingCellingNumEntity cellingNum) {
		String flag = BooleanType.TRUE;
		User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
		//防止设置的额度超过了数据的上限字段的长度
		try {
			service.setCeilingMoney(cellingNum,user.getUserCode());
		} catch (Exception e) {
			flag = BooleanType.FALSE;
			e.printStackTrace();
			logger.error(e.toString());
		}
		return flag;
	}
	/**
	 * 查询指定的总笔数和批借金额  2016年5月26日 add by wudongyue
	 * 
	 * @param request
	 * @param response
	 * @param ceilingMoney
	 *            
	 */
	@ResponseBody
	@RequestMapping(value = "ceilingSum")
	public List<Map<String,String>> ceilingSum(HttpServletRequest request,
			HttpServletResponse response,
			Summary cellingNum) {
		String model = request.getParameter("model");
		cellingNum.setModel(model);
		List<Map<String,String>> list = service.queryCellingNum(cellingNum);
		return list;
	}
	
	/**
	 * 分渠道查询指定的总笔数和批借金额  2016年5月26日 add by wudongyue
	 * 
	 * @param request
	 * @param response
	 * @param ceilingMoney
	 *            
	 */
	@ResponseBody
	@RequestMapping(value = "ceilingSumChannel")
	public String ceilingSumChannel(HttpServletRequest request,
			HttpServletResponse response,
			Summary cellingNum) {
		String model = request.getParameter("model");
		cellingNum.setModel(model);
		List<Map<String, String>> list = service.queryCellingNum(cellingNum);
		String message="";
		if(list!=null && list.size()>0){
			for(Map<String, String> obj : list){
				message=message+obj.get("label")+"批借金额="+String.valueOf(obj.get("ceilingsum"))+"总笔数="+String.valueOf(obj.get("account"))+";";
			}
		}
		message=message+"是否确认上限数量失效?";
		return message;
	}
	/**
	 * 数据归档
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "archive")
	public String jinxin(HttpServletRequest request,
			HttpServletResponse response) {
		String flag = BooleanType.TRUE;
		try {
			service.updateCeiling();
		} catch (Exception e) {
			logger.error(e.toString());
			flag = BooleanType.FALSE;
		}
		return flag;
	}	
	/**
	 * TG数据归档
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "goldAccount")
	public String jinxinForTG(HttpServletRequest request,
			HttpServletResponse response) {
		service.updateCeilingForTG();
		return BooleanType.TRUE;
	}
}
