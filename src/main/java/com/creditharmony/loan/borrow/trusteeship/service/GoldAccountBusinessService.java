package com.creditharmony.loan.borrow.trusteeship.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.trusteeship.dao.GoldAccountBusinessDao;
import com.creditharmony.loan.borrow.trusteeship.util.ExcelExportAccountBusinessHelper;
import com.creditharmony.loan.borrow.trusteeship.view.GoldAccountBusiness;
import com.google.common.collect.Maps;
@Service
public class GoldAccountBusinessService extends CoreManager<GoldAccountBusinessDao, GoldAccountBusiness>{
	@Autowired
	private GoldAccountBusinessDao dao;
	/**
	 * 获取金账户上限数据列表 2016年3月26日 By 张建雄
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 金账户上限数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<GoldAccountBusiness> findGoldAccountCeilingList(Page<GoldAccountBusiness> page, GoldAccountBusiness params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		//params.setChannel(LoanModel.TG.getCode());
		String [] storeOrgIds = {};
		if (StringUtils.isNotEmpty(params.getStoreOrgId())){
			storeOrgIds = params.getStoreOrgId().split(",");
		}
		Map<String ,Object> conditions = Maps.newHashMap();
		conditions.put("params", params);
		conditions.put("storeOrgIds", storeOrgIds);
		PageList<GoldAccountBusiness> pageList = (PageList<GoldAccountBusiness>) dao.getGoldAccountBusinessList(pageBounds, conditions);
		
		PageUtil.convertPage(pageList, page);
		return page;
	}
	/**
	 * 导出
	 * 2016年2月24日 
	 * By 张建雄
	 * 
	 * @return 金账户业务数据列表
	 */
	//@Transactional(readOnly = true,value = "loanTransactionManager")
	public void exportBusiness(String cid,GoldAccountBusiness params,HttpServletResponse response){
		params.setChannel(LoanModel.TG.getCode());
		Map<String ,Object> queryMap = Maps.newHashMap();
		params.setApplyIds(cid);
		String [] applyIds={};
		if (StringUtils.isNotEmpty(params.getStoreOrgId())){
			String []storeOrgIds = params.getStoreOrgId().split(",");
			queryMap.put("storeOrgIds", storeOrgIds);
		}
		if (StringUtils.isNotEmpty(cid)) {
			 applyIds = cid.split(",");
		}
		queryMap.put("params", params);
		queryMap.put("applyIds", applyIds);
		ExcelExportAccountBusinessHelper.exportData(queryMap, response);
	}
}
