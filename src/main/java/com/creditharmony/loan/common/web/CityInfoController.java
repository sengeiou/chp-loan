/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.webUserInfoController.java
 * @Create By 王彬彬
 * @Create In 2015年12月28日 下午11:17:35
 */
package com.creditharmony.loan.common.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.service.CityInfoService;

/**
 * 城市基本信息
 * @Class Name CityInfoController
 * @author 王彬彬
 * @Create In 2015年12月28日
 */
@Controller
@RequestMapping(value = "${adminPath}/common/cityinfo")
public class CityInfoController extends BaseController {
	
	@Autowired
	private CityInfoService cityInfoService;

	/**
	 * 异步加载城市 2015年12月10日 
	 * By 张永生
	 * @param provinceId
	 * @return 返回城市信息
	 */
	
	@ResponseBody
	@RequestMapping(value = "asynLoadCity", method = RequestMethod.POST)
	public String asynLoadCity(String provinceId) {
			List<CityInfo> cityList = cityInfoService.findCity(provinceId);
			return jsonMapper.toJson(cityList);
	}

	/**
	 * 异步加载区县 2015年12月10日
	 * By 张永生
	 * 
	 * @param cityId
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "asynLoadDistrict", method = RequestMethod.POST)
	public String asynLoadDistrict(String cityId) {
		List<CityInfo> districtList = cityInfoService.findDistrict(cityId);
		return jsonMapper.toJson(districtList);
	}
	
	
	/**
	 * 异步加载省 2016年3月2日 
	 * By 管洪昌
	 * @param provinceId
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "provinceCity", method = RequestMethod.POST)
	public String provinceCity(String provinceId) {
			List<CityInfo> provinceList = cityInfoService.findProvince();
			return jsonMapper.toJson(provinceList);
		
	}

	
	/**
	 * 异步加载城市 2015年12月10日  银行卡下专有省市
	 * By guanhongchang
	 * @param provinceId
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "asynLoadCityCmb", method = RequestMethod.POST)
	public String asynLoadCityCmb(String provinceId) {
		List<CityInfo> provinceListCmb = cityInfoService.findCityCmb(provinceId);
		return jsonMapper.toJson(provinceListCmb);
	}

}
