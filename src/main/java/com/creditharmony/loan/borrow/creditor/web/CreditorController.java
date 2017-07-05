package com.creditharmony.loan.borrow.creditor.web;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.creditor.service.CreditorsService;
import com.creditharmony.loan.borrow.creditor.view.CreditorModel;
import com.creditharmony.loan.borrow.creditor.view.CreditorSearch;
import com.creditharmony.loan.borrow.creditor.view.LoanDebtModel;
import com.creditharmony.loan.common.utils.DateUtil;
import com.sun.star.io.IOException;

/**
 * 债权录入
 * @Class Name CreditorController
 * @author WJJ
 * @Create In 2016年3月11日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/creditor")
public class CreditorController extends BaseController {

	@Autowired
    private CreditorsService creditorService;

	/**
	 * 债权录入列表
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@RequestMapping(value = "getCreditorlist")
	public String getCreditorlist(Model model, CreditorSearch params,
			HttpServletRequest request,HttpServletResponse response) {
		Page<CreditorModel> page = new Page<CreditorModel>(request,response);
		if(params.getPageSize()!=0){
			page.setPageSize(params.getPageSize());
		}
		page.setPageNo(params.getPageNo());
		Page<CreditorModel> pageList = creditorService.getListByParam(page,params);
		List<Map<String,Object>> list = creditorService.getType();
		
		model.addAttribute("typeList",list);
		model.addAttribute("search", params);
		model.addAttribute("items", pageList);
		model.addAttribute("page", page);
		return "/borrow/creditor/creditorList";
	}
	
	/**
	 * 债权录入页面
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@RequestMapping(value = "toAddPage")
	public String toAddPage(Model model,HttpServletRequest request,HttpServletResponse response) {
		List<Map<String,Object>> list = creditorService.getType();
		model.addAttribute("typeList",list);
		return "/borrow/creditor/addCreditor";
	}
	
	/**
	 * 债权录入保存
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@RequestMapping(value = "save")
	public String save(Model model,  CreditorModel params,
			HttpServletRequest request,HttpServletResponse response) {
		String message = null;
		try{
			User user = UserUtils.getUser();
			params.setId(IdGen.uuid());
			params.setCreateBy(user.getId());
			params.setOperationUser(user.getId());
			params.setModifyBy(user.getId());
			params.setStatus("1");
			Date dt = params.getInitLoanDate();
			int rDate = params.getRepaymentDate();
			Calendar rightNow = Calendar.getInstance();
	        rightNow.setTime(dt);
	        rightNow.add(Calendar.MONTH,rDate-1);//日期加月
	        params.setEndDate(rightNow.getTime());
			if(params.getSurplusDate()!=null&&!"".equals(params.getSurplusDate())){
				boolean t = params.getSurplusDate().matches("[0-9]+"); 
				if(t){
					params.setSurplusDateInt(Integer.parseInt(params.getSurplusDate()));
				}
			}
			//params.setSurplusDate(0);//剩余期限
			//params.setEndDate(new Date());//还款截止日期
			creditorService.save(params);
			message = "添加成功";
		}catch(Exception e){
			e.printStackTrace();
			message = "添加失败";
		}
		
		
		model.addAttribute("message",message);
		return "redirect:" + adminPath + "/borrow/creditor/getCreditorlist";
	}
	
	/**
	 * 获取客户名称
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@RequestMapping(value = "getName")
	public void getName( HttpServletRequest request,HttpServletResponse response) throws Exception{
		String cerNum = request.getParameter("cerNum");
		List<Map<String,Object>> list = creditorService.getName(cerNum);
		Object json = JSON.toJSON(list);
		PrintWriter out = response.getWriter();
		out.print(json);
	}
	
	/**
	 * 获取职业信息
	 * @author WJJ
	 * @Create In 2016年3月11日
	 */
	@RequestMapping(value = "getOccupation")
	public void getOccupation( HttpServletRequest request,HttpServletResponse response) throws Exception{
		String type = request.getParameter("type");
		Map<String, String> typeMap = new HashMap<String, String>();
		typeMap.put("A001", "1");//精英借A--高薪职员
		typeMap.put("A002", "1");//精英借B--高薪职员
		typeMap.put("A003", "0");//薪水借--一般职员
		typeMap.put("A004", "3");//楼易借--中小业主
		typeMap.put("A005", "2");//老板借--业主/个体户
		typeMap.put("A006", "5");//小微企业借--微企业主
		
		typeMap.put("A009", "7");//质押--质押
		typeMap.put("A010", "6");//抵押物--抵押物
		typeMap.put("A011", "4");//大企业借--大企业主
		typeMap.put("A012", "8");//房易借--抵押房
		
		String value =  typeMap.get(type).toString();
		List<Map<String,Object>> list = creditorService.getOccupation("jk_prof_type",value);
		Object json = JSON.toJSON(list);
		PrintWriter out = response.getWriter();
		out.print(json);
	}
	
	/**
	 * 计算剩余期数
	 * @throws IOException 
	 */	
	@RequestMapping(value="/getLastsMonths") 
	public void getLastsMonths(HttpServletRequest request,HttpServletResponse response) throws Exception {	
		String payDateFri = request.getParameter("payDateFri"); 
		String debtTimes = request.getParameter("debtTimes");
		
		LoanDebtModel model = new LoanDebtModel();
		model.setPayDateFri(DateUtil.StringToDate(payDateFri));
		model.setDebtTimes(new BigDecimal(debtTimes));
		
		int lasts_months = 0;
		if(model.getPayDateFri() != null){
			if(model.getDebtTimes() != null){
				//计算剩余期数
			    Calendar calendar = Calendar.getInstance();
				calendar.setTime(model.getPayDateFri());
				
				//处理第一个还款日为2月28或者29
			    int dd= DateUtil.getDay(model.getPayDateFri());
			    if(dd == 28 || dd==29){
			    	dd=30;
			    }
			    
				int mm = 0;
				List<String> ls= new ArrayList<String>();// 存放所有的还款日
				ls.add(DateUtil.DateToString(model.getPayDateFri(), "yyyyMMdd") );
				for(int ii=1;ii<Integer.parseInt(String.valueOf(model.getDebtTimes()));ii++){
					calendar.add(Calendar.MONTH, 1);
					mm = DateUtil.getMonth(calendar.getTime());
					if(mm != 2){
						calendar.set(Calendar.DATE, dd);
					}
					ls.add(DateUtil.DateToString(calendar.getTime(), "yyyyMMdd") );
				}
				//所有的还款日与当前日期比较   大于则加1
				for( String s :ls){
					if(Integer.valueOf(s) > Integer.valueOf(DateUtil.DateToString(new Date(), "yyyyMMdd")) ){
						lasts_months ++;
					}
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.print("{\"value\":\""+lasts_months+"\"}");
	}
	
}
