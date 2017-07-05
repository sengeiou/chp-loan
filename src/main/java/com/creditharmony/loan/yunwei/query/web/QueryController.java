package com.creditharmony.loan.yunwei.query.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.yunwei.query.entity.QueryObj;
import com.creditharmony.loan.yunwei.query.service.QueryDataBySqlService;

/**
 * 查询数据
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/yunwei/query")
public class QueryController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(QueryController.class);
	
	@Autowired
	private QueryDataBySqlService queryDataBySqlService;
	
	/**
	 * 跳转至查询页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goPageQueryData")
	public String goPageLoanUpdateSql(Model model, HttpServletRequest request) {
		return "yunwei/query/queryData";
	}

	
	/**
	 * 根据SQL查询数据
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "queryDataBySql")
	public String queryDataBySql(QueryObj queryOj,Model model, HttpServletRequest request) {
		if(!(UserUtils.getUser().getLoginName().equals("609295")
			|| UserUtils.getUser().getLoginName().equals("609471")
			|| UserUtils.getUser().getLoginName().equals("609348")
			|| UserUtils.getUser().getLoginName().equals("60000879"))) {
			model.addAttribute("error", "norights");
			return "yunwei/query/queryData";
		}
		String sqlStr = request.getParameter("sqlStr");
		String pageNoStr = request.getParameter("pageNo"); 
		String pageSizeStr = request.getParameter("pageSize");
		int pageNo = 0 ;
		int pageSize = 0;
		// 如果查询SQL为空，返回异常。
		if(StringUtils.isEmpty(sqlStr)) {
			model.addAttribute("error", "isNull");
			return "yunwei/query/queryData";
		}
		// 如果查询SQL不以SELECT开头，返回异常。
		if(!StringUtils.startsWith(sqlStr.trim().toUpperCase(), "SELECT ")) {
			model.addAttribute("error", "sqlError");
			return "yunwei/query/queryData";
		}
		// 当前页码
		if(StringUtils.isEmpty(pageNoStr)) {
			pageNoStr = "1";
			pageNo = 1;
		} else {
			pageNo = Integer.valueOf(pageNoStr);
		}
		// 每页大小
		if(StringUtils.isEmpty(pageSizeStr)) {
			pageSizeStr = "50";
			pageSize = 50;
		} else {
			pageSize = Integer.valueOf(pageSizeStr);
		}
		try {
			// 将SQL语句格式化为大写
			sqlStr = sqlStr.trim();
			// 构造查询对象
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("page", pageNo);
			params.put("maxResult", pageSize);
			params.put("sqlStr", sqlStr);
			// 查询数据
			List<Map<String,Object>> dataList = queryDataBySqlService.queryDataBySql(params);
			// 总记录条数
			long count = queryDataBySqlService.queryDataCount(sqlStr);
			// 封装Page对象
			Page<Map<String, Object>> queryPage = new Page<Map<String, Object>>(pageNo, pageSize, count, dataList);
			queryPage.initialize();
			model.addAttribute("sqlStr", sqlStr);
			model.addAttribute("pageNoStr", pageNoStr);
			model.addAttribute("pageSizeStr", pageSizeStr);
			model.addAttribute("queryPage", queryPage);
			model.addAttribute("dataList", dataList);
		} catch (Exception e) {
			// 查询数据出现异常
			model.addAttribute("error", "sqlQueryError");
			logger.error("查询数据出现异常。", e);
		}
		return "yunwei/query/queryData";
	}
	
}
