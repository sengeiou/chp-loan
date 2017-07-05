/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.car.carTotalRate.web
 * @Create By 张进
 * @Create In 016年2月3号
 */
package com.creditharmony.loan.car.carTotalRate.web;


import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.Reflections;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.lend.type.LendConstants;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carTotalRate.ex.CarGrossSpreadEx;
import com.creditharmony.loan.car.carTotalRate.ex.CarSpreadProvinceCityRelationEx;
import com.creditharmony.loan.car.carTotalRate.service.CarGrossSpreadService;
import com.creditharmony.loan.car.common.entity.CarGrossSpread;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanPrdEntity;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.LoanPrdService;

@Controller
@RequestMapping(value = "${adminPath}/car/carGrossSpread/carGrossSpread")
public class CarGrossSpreadController extends BaseController {

	@Autowired
	private CarGrossSpreadService carGrossSpreadService;
	@Autowired
	private CityInfoService cityInfoService;
	@Autowired
	private LoanPrdService loanPrdService;
	
	/**
	 * 		进入新增总费率页面
	 * @return
	 */
	@RequestMapping(value = "goAddPage")
	public String goAddPage(Model m){
		System.out.println("正在进入费率增加页面");
		addProducts(m);
		return "car/grossSpread/insertCarGrossSpread";
	}
	
	/**
	 * 		新增一条车借总费率
	 * @param carGrossSpread
	 */
	@ResponseBody
	@RequestMapping(value = "insertGrossSpread")
	public String insertGrossSpread(CarGrossSpread carGrossSpread){
		carGrossSpreadService.insertCarGrossSpread(carGrossSpread);
		return "success";
	}
	
	/**
	 * 		查询车借总费率列表
	 * @param carGrossSpread
	 * @param modelMap
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value = "findCarGrossSpreadList")
	public String findCarGrossSpreadList(HttpServletRequest request,
			HttpServletResponse response,Model m,CarGrossSpread carGrossSpread) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Page<CarGrossSpread> page = new Page<CarGrossSpread>(request, response);
		page.setCountBy("rate_id");
		//数据库查询列表数据
		page=carGrossSpreadService.findCarGrossSpreadList(page, carGrossSpread);
		if(null != page.getList()){
			List<CarGrossSpread> itemList = page.getList();
			Map<String, String> map = new HashMap<String, String>();
			map.put("dictOperStatus", "jk_next_step");
			map.put("telesalesFlag", "jk_telemarketing");
			map.put("loanStatusCode", "jk_car_loan_status");
			map.put("dictDealStatus", "jk_counteroffer_result");
			//map.put("productTypeContract", "products_type_car_credit");
			map.put("auditStatus", "jk_counteroffer_result");
			for (Map.Entry<String, String> entry : map.entrySet()) {
				for (CarGrossSpread listItem : itemList) {
					PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(listItem.getClass()).getPropertyDescriptors();
					for (int i = 0; i < propertyDescriptors.length; i++) {
		                PropertyDescriptor descriptor = propertyDescriptors[i];
		                String propertyName = descriptor.getName();
		                if (!propertyName.equals("class")) {
		                    Method readMethod = descriptor.getReadMethod();
		                    if (readMethod != null) {
		                    	if (entry.getKey().equalsIgnoreCase(propertyName)) {
		                    		Object result = readMethod.invoke(listItem, new Object[0]);
		                    		if (result != null) {
		                    			Reflections.setFieldValue(listItem, propertyName, DictCache.getInstance().getDictLabel(entry.getValue(), result.toString()));
		                    		}
		                    	}
		                    }
		                }
		            }
				}
			}
			for (CarGrossSpread listItem : itemList) {
				listItem.setDictInitiate(DictCache.getInstance().getDictLabel("com_use_flag", listItem.getDictInitiate()));
			}
		}
		//传递数据到前台页面展示
		m.addAttribute("info", carGrossSpread);
		
		addProducts(m);
		
//		if(carGrossSpread.getDictProductType() !=  null &&  !"".equals(carGrossSpread.getDictProductType().trim())){
//			LoanPrdEntity loanPrdEntity = loanPrdService.getPrdByTypeAndCode(LendConstants.PRODUCTS_TYPE_CAR_CREDIT, carGrossSpread.getDictProductType());
//			if(loanPrdEntity != null){
//				if(loanPrdEntity.getProductMonths() != null){
//					m.addAttribute("productMonths",loanPrdEntity.getProductMonths().split(","));
//				}
//			}
//		}
		
		m.addAttribute("page", page);
		return "car/grossSpread/carGrossSpread";
	}
	
	/**
	 * 		 根据rateId查询一条车借总费率
	 * @param rateId
	 * @param modelMap
	 * @param carGrossSpread
	 * @return
	 */
	@RequestMapping(value = "findByRateId")
	public String findByRateId(String rateId, Model modelMap, CarGrossSpread carGrossSpread){
		carGrossSpread = carGrossSpreadService.findByRateId(rateId);
		carGrossSpread.setDictInitiate(DictCache.getInstance().getDictLabel("com_use_flag", carGrossSpread.getDictInitiate()));
		modelMap.addAttribute("carGrossSpread", carGrossSpread);
		List<CarSpreadProvinceCityRelationEx> ctiys=carGrossSpreadService.getCarSpreadProvinceCityRelationByRateId(rateId);
		modelMap.addAttribute("ctiys", ctiys);
		addProducts(modelMap);
		return "car/grossSpread/showCarGrossSpread";
	}
	
	/**
	 * 		根据rateId修改一条车借总费率（先查询出来数据然后再修改）
	 * @param rateId
	 * @param modelMap
	 * @param carGrossSpread
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "updateByRateId")
	public String updateByRateId(String rateId, Model modelMap, CarGrossSpread carGrossSpread){
		carGrossSpread = carGrossSpreadService.findByRateId(rateId);
		modelMap.addAttribute("carGrossSpread", carGrossSpread);
		addProducts(modelMap);
		if(carGrossSpread.getDictProductType() !=  null &&  !"".equals(carGrossSpread.getDictProductType().trim())){
			LoanPrdEntity loanPrdEntity = loanPrdService.getPrdByTypeAndCode(LendConstants.PRODUCTS_TYPE_CAR_CREDIT, carGrossSpread.getDictProductType());
			if(loanPrdEntity != null){
				if(loanPrdEntity.getProductMonths() != null){
					modelMap.addAttribute("productMonths",loanPrdEntity.getProductMonths().split(","));
				}
			}
		}
		return "car/grossSpread/updateCarGrossSpread";
	}
	/**
	 * 更新总费率信息
	 * @param carGrossSpread 总费率信息
	 */
	@ResponseBody
	@RequestMapping(value = "updateCarGrossSpread")
	public void updateCarGrossSpread(CarGrossSpread carGrossSpread){
		carGrossSpreadService.updateCarGrossSpread(carGrossSpread);
	}
	
	/**
	 * 检查总费率是否已经存在 
	 * @param carGrossSpread 总费率信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkCarGrossSpread")
	public int checkCarGrossSpread(CarGrossSpread carGrossSpread){
		return carGrossSpreadService.findCarGrossSpreadCount(carGrossSpread);
	}
	
	/**
	 * 		启用/停用  一条或者多条车借总费率
	 * @param rateId
	 * @param mark
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateDictInitiate")
	public String updateDictInitiate(String rateId , String mark){
		carGrossSpreadService.updateDictInitiate(rateId,mark);
		return "success";
	}
	
	/**
	 * 		根据rateId进入城市分配页面，带回参数
	 * @param rateId
	 * @param modelMap
	 * @param carGrossSpread
	 * @return
	 */
	@RequestMapping(value = "showSpreadProvinceCity")
	public String showSpreadProvinceCity(String rateId, Model modelMap, CarGrossSpread carGrossSpread){
		carGrossSpread = carGrossSpreadService.showSpreadProvinceCity(rateId);
		carGrossSpread.setDictInitiate(DictCache.getInstance().getDictLabel("com_use_flag", carGrossSpread.getDictInitiate()));
		modelMap.addAttribute("carGrossSpread", carGrossSpread);
		List<CarSpreadProvinceCityRelationEx> ctiys=carGrossSpreadService.getCarSpreadProvinceCityRelationByRateId(rateId);
		modelMap.addAttribute("ctiys", ctiys);
		addProducts(modelMap);
		return "car/grossSpread/spreadProvinceCity";
	}
	
	/**
	 * 跳转到选择城市界面
	 * @param modelMap ModelMap对象
	 * @param carGrossSpread CarGrossSpread对象
	 * @return 返回跳转界面
	 */
	@RequestMapping(value = "toGrossSpreadAddCitys")
	public String toGrossSpreadAddCitys( ModelMap modelMap,CarGrossSpread carGrossSpread){
		modelMap.put("carGrossSpread", carGrossSpread);
		List<CityInfo> provinces = cityInfoService. findProvince();
		modelMap.put("provinces", provinces);
		return "car/grossSpread/carGrossSpreadAddCitys";
	}
	/**
	 * 跳转到选择城市界面
	 * @param modelMap ModelMap对象
	 * @param carGrossSpread CarGrossSpread对象
	 * @return 返回跳转界面
	 */
	@ResponseBody
	@RequestMapping(value = "grossSpreadAddCitys")
	public Map<String, String> grossSpreadAddCitys(String rateId,String citys){
		int count = carGrossSpreadService.grossSpreadAddCitys(rateId, citys);
		int recount = rateId.split(",").length - count;
		Map<String, String> map = new HashMap<String, String>();
		map.put("count", count + "");
		map.put("recount", recount + "");
		return map;
	}
	
	
	/**
	 * 批量删除关联城市
	 * @param modelMap ModelMap对象
	 * @param carGrossSpread CarGrossSpread对象
	 * @return 返回跳转界面
	 */
	@ResponseBody
	@RequestMapping(value = "batchDeleteCitys")
	public String batchDeleteCitys(String linkIds){
		int count = carGrossSpreadService.batchDeleteCitys(linkIds);
		return count + "";
	}
	
	/**
	 * 查找关联城市信息总费率信息
	 * @param grossSpreadEx 总费率扩展信息  <br>
	 * 必须包含： <br>
	 * 区域编码： provinceCityId <br>
	 * 产品类型：dictProductType <br> 
	 * 期限：dictDeadline
	 * @return  总费率值  若异常返回null
	 */
	@ResponseBody
	@RequestMapping(value = "getCarGrossSpread")
	public String getCarGrossSpreadByCarGrossSpreadEx(CarGrossSpreadEx grossSpreadEx){
		CarGrossSpread carGrossSpread= carGrossSpreadService.getCarGrossSpreadByCarGrossSpreadEx(grossSpreadEx);
		if(carGrossSpread != null){
			return carGrossSpread.getGrossRate().toString();
		}
		return "null";
	}
	
	/**
	 * 加载产品信息
	 * @param m
	 */
	@SuppressWarnings("static-access")
	public void addProducts(Model m){
		m.addAttribute("products", loanPrdService.getPrd(LendConstants.PRODUCTS_TYPE_CAR_CREDIT));
	}
}
