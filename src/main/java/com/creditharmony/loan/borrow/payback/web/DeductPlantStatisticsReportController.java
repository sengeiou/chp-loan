package com.creditharmony.loan.borrow.payback.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.DeductPlantStatisticsReport;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
import com.creditharmony.loan.borrow.payback.service.DeductPlantStatisticsReportService;

/**
 * 划扣平台统计报表
 * 
 * @Class Name DeductPlantStatisticsReportController
 * @author 翁私
 * @Create In 2017年5月2日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/deductPlantStatisticsReport")
public class DeductPlantStatisticsReportController extends BaseController{
	
	@Autowired
	private DeductPlantStatisticsReportService deductPlantService;
	SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "queryPage")
	public String queryPage(DeductStatistics record,HttpServletRequest request, HttpServletResponse response, Model model){
		
		
		if(ObjectHelper.isEmpty(record.getBeginDate())&&ObjectHelper.isEmpty(record.getEndDate())
		  && ObjectHelper.isEmpty(record.getPlantCode())){
			Date date = new Date();
			String dateString = format.format(date);
			record.setCreateDate(dateString);
		}
		List<DeductStatistics> list = deductPlantService.queryDeductStatistics();
		DeductPlantStatisticsReport report = new DeductPlantStatisticsReport();
		
		for(DeductStatistics statistics : list){
			if(DeductPlat.ZHONGJIN.getCode().equals(statistics.getPlantCode())){
				report.setZjdeductNumber(statistics.getDeductNumber());
				report.setZjfailureNumber(statistics.getFailureNumber());
				report.setZjfailureRate(statistics.getFailureRate());
				report.setZjnotEnoughProportion(statistics.getNotEnoughProportion());
				report.setZjnotEnoughNumber(statistics.getNotEnoughNumber());
			}
			if(DeductPlat.FUYOU.getCode().equals(statistics.getPlantCode())){
				report.setFydeductNumber(statistics.getDeductNumber());
				report.setFyfailureNumber(statistics.getFailureNumber());
				report.setFyfailureRate(statistics.getFailureRate());
				report.setFynotEnoughProportion(statistics.getNotEnoughProportion());
				report.setFynotEnoughNumber(statistics.getNotEnoughNumber());
			}
			if(DeductPlat.KALIAN.getCode().equals(statistics.getPlantCode())){
				report.setKldeductNumber(statistics.getDeductNumber());
				report.setKlfailureNumber(statistics.getFailureNumber());
				report.setKlfailureRate(statistics.getFailureRate());
				report.setKlnotEnoughProportion(statistics.getNotEnoughProportion());
				report.setKlnotEnoughNumber(statistics.getNotEnoughNumber());
			}
			
			if(DeductPlat.CHANGJIE.getCode().equals(statistics.getPlantCode())){
				report.setCjdeductNumber(statistics.getDeductNumber());
				report.setCjfailureNumber(statistics.getFailureNumber());
				report.setCjfailureRate(statistics.getFailureRate());
				report.setCjnotEnoughProportion(statistics.getNotEnoughProportion());
				report.setCjnotEnoughNumber(statistics.getNotEnoughNumber());
			}
			if(DeductPlat.TONGLIAN.getCode().equals(statistics.getPlantCode())){
				report.setTldeductNumber(statistics.getDeductNumber());
				report.setTlfailureNumber(statistics.getFailureNumber());
				report.setTlfailureRate(statistics.getFailureRate());
				report.setTlnotEnoughProportion(statistics.getNotEnoughProportion());
				report.setTlnotEnoughNumber(statistics.getNotEnoughNumber());
			}
		}
		
		Page<DeductStatistics> bankPage  = deductPlantService.queryPage(new Page<DeductStatistics>(request, response),record);
		model.addAttribute("page", bankPage);
		model.addAttribute("record", record);
		model.addAttribute("report", report);

		return "borrow/payback/centerdeduct/deductPlantStatisticsReport";
	}
	
   
	/**
	 * 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public String appendString(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (String id : idArray) {
			if (id != null && !"".equals(id)) {
				parameter.append("'" + id + "',");
			}
		}
		String idstring = null;
		if (parameter != null) {
			idstring = parameter.toString();
			idstring = idstring.substring(0, parameter.lastIndexOf(","));
		}
		return idstring;
	}
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping(value = "exportExcel")
	public String   exportExcel(DeductStatistics record,HttpServletRequest request, HttpServletResponse response, Model model){
		try{

		   if(ObjectHelper.isEmpty(record.getBeginDate())&&ObjectHelper.isEmpty(record.getEndDate())
			  && ObjectHelper.isEmpty(record.getPlantCode())){
				Date date = new Date();
				String dateString = format.format(date);
				record.setCreateDate(dateString);
			}
			
			List<DeductStatistics> list  = deductPlantService.queryList(record);
			ExportDeductStatisticsHelper.exportData(list,response);
			return null;
		 }catch(Exception e){
			e.printStackTrace();
			return "redirect:" + adminPath + "borrow/payback/centerdeduct/deductPlantStatisticsReport";
		}
	 }
	
	
	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "periodStatistics")
	public String periodStatistics(DeductStatistics record,HttpServletRequest request, HttpServletResponse response, Model model){
		
		
		if(ObjectHelper.isEmpty(record.getBeginDate())&&ObjectHelper.isEmpty(record.getEndDate())
		  && ObjectHelper.isEmpty(record.getPlantCode())){
			Date date = new Date();
			String dateString = format.format(date);
			record.setCreateDate(dateString);
		}
		List<DeductStatistics> list = deductPlantService.queryDeductStatistics();
		DeductPlantStatisticsReport report = new DeductPlantStatisticsReport();
		
		for(DeductStatistics statistics : list){
			if(DeductPlat.ZHONGJIN.getCode().equals(statistics.getPlantCode())){
				report.setZjdeductNumber(statistics.getDeductNumber());
				report.setZjfailureNumber(statistics.getFailureNumber());
				report.setZjfailureRate(statistics.getFailureRate());
				report.setZjnotEnoughProportion(statistics.getNotEnoughProportion());
				report.setZjnotEnoughNumber(statistics.getNotEnoughNumber());
			}
			if(DeductPlat.FUYOU.getCode().equals(statistics.getPlantCode())){
				report.setFydeductNumber(statistics.getDeductNumber());
				report.setFyfailureNumber(statistics.getFailureNumber());
				report.setFyfailureRate(statistics.getFailureRate());
				report.setFynotEnoughProportion(statistics.getNotEnoughProportion());
				report.setFynotEnoughNumber(statistics.getNotEnoughNumber());
			}
			if(DeductPlat.KALIAN.getCode().equals(statistics.getPlantCode())){
				report.setKldeductNumber(statistics.getDeductNumber());
				report.setKlfailureNumber(statistics.getFailureNumber());
				report.setKlfailureRate(statistics.getFailureRate());
				report.setKlnotEnoughProportion(statistics.getNotEnoughProportion());
				report.setKlnotEnoughNumber(statistics.getNotEnoughNumber());
			}
			
			if(DeductPlat.CHANGJIE.getCode().equals(statistics.getPlantCode())){
				report.setCjdeductNumber(statistics.getDeductNumber());
				report.setCjfailureNumber(statistics.getFailureNumber());
				report.setCjfailureRate(statistics.getFailureRate());
				report.setCjnotEnoughProportion(statistics.getNotEnoughProportion());
				report.setCjnotEnoughNumber(statistics.getNotEnoughNumber());
			}
			if(DeductPlat.TONGLIAN.getCode().equals(statistics.getPlantCode())){
				report.setTldeductNumber(statistics.getDeductNumber());
				report.setTlfailureNumber(statistics.getFailureNumber());
				report.setTlfailureRate(statistics.getFailureRate());
				report.setTlnotEnoughProportion(statistics.getNotEnoughProportion());
				report.setTlnotEnoughNumber(statistics.getNotEnoughNumber());
			}
		}
		
		Page<DeductStatistics> bankPage  = deductPlantService.periodStatistics(new Page<DeductStatistics>(request, response),record);
		model.addAttribute("page", bankPage);
		model.addAttribute("record", record);
		model.addAttribute("report", report);

		return "borrow/payback/centerdeduct/deductPlantStatisticsReport";
	}
	
	
	/**
	 * 统计导出
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping(value = "exportPeriodExcel")
	public String   exportPeriodExcel(DeductStatistics record,HttpServletRequest request, HttpServletResponse response, Model model){
		try{
			List<DeductStatistics> list  = deductPlantService.periodStatistics(record);
			ExportDeductStatisticsHelper.exportData(list,response);
			return null;
		 }catch(Exception e){
			e.printStackTrace();
			return "redirect:" + adminPath + "borrow/payback/centerdeduct/deductPlantStatisticsReport";
		}
	 }
	
	
}

