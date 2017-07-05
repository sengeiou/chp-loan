package com.creditharmony.loan.borrow.payback.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.BankPlantPort;
import com.creditharmony.loan.borrow.payback.service.BankPlantPortService;

/**
 * 银行平台接口
 * 
 * @Class Name BankPlantPortController
 * @author 翁私
 * @Create In 2016年4月20日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/payback/bankplantport")
public class BankPlantPortController extends BaseController{
	
	@Autowired
	private BankPlantPortService bankPlantPortService;
	
	/**
	 * 增加银行平台接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public String insert(BankPlantPort record){
		bankPlantPortService.insert(record);
		return "success";
	}
	
	/**
	 * 修改银行平台接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@ResponseBody
	@RequestMapping(value = "update")
	public String update(BankPlantPort record){
		bankPlantPortService.update(record);
		return "sccess";
	}

	/**
	 * 删除接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public String delete(BankPlantPort record){
		String id = appendString(record.getId());
		record.setId(id);
		bankPlantPortService.delete(record);
		return "success";
	}
	
	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@RequestMapping(value = "queryPage")
	public String queryPage(BankPlantPort record,HttpServletRequest request, HttpServletResponse response, Model model){
		Page<BankPlantPort> bankPage  = bankPlantPortService.queryPage(new Page<BankPlantPort>(request, response),record);
		model.addAttribute("page", bankPage);
		model.addAttribute("record", record);
		return "borrow/payback/centerdeduct/bankPlantPort";
	}
	@ResponseBody
	@RequestMapping(value = "getBean")
	public String getBean(BankPlantPort record){
		record =bankPlantPortService.selectByPrimaryKey(record);
		 return jsonMapper.toJson(record);
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
	
	@ResponseBody
	@RequestMapping(value = "querybank")
	public String querybank(BankPlantPort record){
		record =bankPlantPortService.selectByPrimaryKeyNotIn(record);
		if(ObjectHelper.isEmpty(record) ){
			return BooleanType.TRUE;
		}
		   return BooleanType.FALSE;
	}
}

