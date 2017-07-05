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
import org.springframework.web.bind.annotation.RequestParam;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.DeductPlantStatisticsReport;
import com.creditharmony.loan.borrow.payback.entity.DeductStatisticsReport;
import com.creditharmony.loan.borrow.payback.service.DeductStatisticsReportService;

/**
 * 划扣统计报表
 * @Class Name DeductStatisticsReportController
 * @author 翁私
 * @Create In 2017年5月25日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/deductStatisticsReport")
public class DeductStatisticsReportController extends BaseController{
	
	@Autowired
	private DeductStatisticsReportService deductService;
	SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "queryPage")
	public String queryPage(DeductStatisticsReport record,HttpServletRequest request, HttpServletResponse response, Model model){
		
		if(ObjectHelper.isEmpty(record.getBeginDate())&&ObjectHelper.isEmpty(record.getEndDate())){
			
			Date date = new Date();
			String dateString = format.format(date);
			record.setCreateDate(dateString);
			
		}
		
		List<DeductStatisticsReport> list = deductService.queryDeductStatistics();
		// List<CodeName> codeNames = new ArrayList<CodeName>();
		DeductStatisticsReport report = new DeductStatisticsReport();
		
		if(list.size()>0){
			report =  list.get(0);
			
		}
		
		Page<DeductStatisticsReport> bankPage  = deductService.queryPage(new Page<DeductStatisticsReport>(request, response),record);
		model.addAttribute("page", bankPage);
		model.addAttribute("record", record);
		model.addAttribute("report", report);

		return "borrow/payback/centerdeduct/deductStatisticsReport";
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
	public String   exportExcel(DeductStatisticsReport record,HttpServletRequest request, HttpServletResponse response, Model model){
		try{

		   if(ObjectHelper.isEmpty(record.getBeginDate())&&ObjectHelper.isEmpty(record.getEndDate())){
				Date date = new Date();
				String dateString = format.format(date);
				record.setCreateDate(dateString);
			}
			
			List<DeductStatisticsReport> list  = deductService.queryList(record);
			//ExportDeductStatisticsHelper.exportData(list,response);
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
	public String periodStatistics(DeductStatisticsReport record,HttpServletRequest request, HttpServletResponse response, Model model){
		
		
		if(ObjectHelper.isEmpty(record.getBeginDate())&&ObjectHelper.isEmpty(record.getEndDate())){
			Date date = new Date();
			String dateString = format.format(date);
			record.setCreateDate(dateString);
		}
		List<DeductStatisticsReport> list = deductService.queryDeductStatistics();
		DeductPlantStatisticsReport report = new DeductPlantStatisticsReport();
		
		for(DeductStatisticsReport statistics : list){
			
		}
		
		Page<DeductStatisticsReport> bankPage  = deductService.periodStatistics(new Page<DeductStatisticsReport>(request, response),record);
		model.addAttribute("page", bankPage);
		model.addAttribute("record", record);
		model.addAttribute("report", report);

		return "borrow/payback/centerdeduct/deductPlantStatisticsReport";
	}
	
	
	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "saveOrupdate")
	public String saveOrupdate( @RequestParam("groupProportion[]") List<String> groupList,HttpServletRequest request, HttpServletResponse response, Model model){
		for(String proportion : groupList){
			System.out.println(proportion);
		}
		return null;
	}
	
	
	
	/**
	 * 统计导出
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping(value = "exportPeriodExcel")
	public String   exportPeriodExcel(DeductStatisticsReport record,HttpServletRequest request, HttpServletResponse response, Model model){
		try{
			List<DeductStatisticsReport> list  = deductService.periodStatistics(record);
			// ExportDeductStatisticsHelper.exportData(list,response);
			return null;
		 }catch(Exception e){
			e.printStackTrace();
			return "redirect:" + adminPath + "borrow/payback/centerdeduct/deductPlantStatisticsReport";
		}
	 }
	
	
}

