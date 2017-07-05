package com.creditharmony.loan.borrow.trusteeship.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.trusteeship.dao.GoldAccountCeilingDao;
import com.creditharmony.loan.borrow.trusteeship.util.ExcelExportCeilingHelper;
import com.creditharmony.loan.borrow.trusteeship.view.GoldAccountCeiling;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
@Service
public class GoldAccountCeilingService extends CoreManager<GoldAccountCeilingDao, GoldAccountCeiling>{
	@Autowired
	private GoldAccountCeilingDao dao;
	/**
	 * 获取金账户上限数据列表 2016年2月19日 By 张建雄
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 金账户上限数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<Object> findGCCeilingList(Page<GoldAccountCeiling> page, GoldAccountCeiling params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		String [] storeOrgIds = {};
		if (StringUtils.isNotEmpty(params.getStoreOrgId())){
			storeOrgIds = params.getStoreOrgId().split(",");
		}
		Map<String ,Object> conditions = Maps.newHashMap();
		conditions.put("params", params);
		conditions.put("storeOrgIds", storeOrgIds);
		PageList<GoldAccountCeiling> pageList = (PageList<GoldAccountCeiling>) dao.getCeilingList(pageBounds, conditions);
		String ceilingSum = dao.getCeilingSum(conditions);
		List<Object> lists = Lists.newArrayList();
		
		PageUtil.convertPage(pageList, page);
		lists.add(page);
		lists.add(ceilingSum);
		return lists;
	}
	/**
	 * 导出
	 * 2016年2月24日 
	 * By 张建雄
	 * 
	 * @return 债权提前结清的数据列表
	 */
	//@Transactional(readOnly = true,value = "loanTransactionManager")
	public void exportCeiling(String cid,GoldAccountCeiling params,HttpServletResponse response){
		//List<GoldAccountCeiling> lists = Lists.newArrayList();
		Map<String ,Object> queryMap = Maps.newHashMap();
		params.setApplyIds(cid);
		String [] applyIds = {};
		if (StringUtils.isNotEmpty(params.getStoreOrgId())){
			String []storeOrgIds = params.getStoreOrgId().split(",");
			queryMap.put("storeOrgIds", storeOrgIds);
		}
		if (StringUtils.isNotEmpty(cid)) {
			applyIds = cid.split(",");
		}
		queryMap.put("applyIds", applyIds);
		queryMap.put("params", params);
		
		ExcelExportCeilingHelper.exportData(queryMap, response);
	}
	/**
	 * 获取用户申请信贷信息
	 *  2016年2月24日 
	 *  By 张建雄
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 金账户上限数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Map<String,Object> findUserInfo(String applyId) {
		if (StringUtils.isEmpty(applyId)){
			return null;
		}
		Map<String,Object> maps = Maps.newHashMap();
		Map<String,String> pageList =  dao.findUserInfo(applyId);
		List<Map<String,String>> creditList = dao.findCreditList(applyId);
		List<Map<String,String>> contactList = dao.findContactList(applyId);
		
		pageList.put("dict_customer_source", DictCache.getInstance().getDictLabel("jk_cm_src", pageList.get("dict_customer_source")));
		pageList.put("dict_loan_use", DictCache.getInstance().getDictLabel("jk_loan_use", pageList.get("dict_loan_use")));
		pageList.put("dict_comp_type", DictCache.getInstance().getDictLabel("jk_industry_type", pageList.get("dict_comp_type")));
		
		for (Map<String, String> map : creditList) {
			map.put("dict_mortgage_type", DictCache.getInstance().getDictLabel("yes_no", pageList.get("dict_mortgage_type")));
		}
		for (Map<String, String> map : contactList) {
			map.put("contact_relation", DictCache.getInstance().getDictLabel("jk_workemate_relation", pageList.get("contact_relation")));
			
		}
		pageList.put("dict_customer_source", DictCache.getInstance().getDictLabel("jk_cm_src",pageList.get("dict_customer_source")));
		pageList.put("dict_loan_use", DictCache.getInstance().getDictLabel("jk_loan_use",pageList.get("dict_loan_use")));
		pageList.put("dict_comp_type", DictCache.getInstance().getDictLabel("jk_industry_type",pageList.get("dict_comp_type")));
		maps.put("info", pageList);
		maps.put("creditList", creditList);
		maps.put("contactList", contactList);
		return maps;
	}
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Map<String,String> selectCeilingMoney (){
		return dao.selectCeilingMoney();
	}
	/**
	 *  设置金账户上限额度
	 * @param ceilingMoney 上限额度
	 * @return 
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void setCeilingMoney(BigDecimal ceilingMoney,String user) {
		if (ceilingMoney == null){
			return;
		}
		Map<String,Object> maps = Maps.newHashMap();
		//String ceilingId  = dao.selectCeilingMoneyCount();
		maps.put("kinnobuQuotaLimit", ceilingMoney);
		maps.put("kinnobuUsingFlag", 0);
		maps.put("version", 0);
		maps.put("modifyBy", user);
		//if (StringUtils.isEmpty(ceilingId)){
			
		maps.put("createBy", user);
		maps.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
		maps.put("useMoney", 0);
		dao.updateGoldAccount();
		dao.setCeilingMoney(maps);
		/*} else{
			maps.put("id", ceilingId);
			dao.updateCeilingMoney(maps);
		}*/
	}
	/**
	 *  金账户数据归档
	 * @return 
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCeiling() {
		dao.updateData();
		dao.updateGoldAccount();
	}
	/**向金账户数据列表中插入数据信息
	 * @param loanCode 借款编号
	 */
	public void insertGoldAccount(String loanCode){
		if (StringUtils.isEmpty(dao.selectLoanCode(loanCode))){
			dao.insertGoldAccountData(loanCode);
		}
	}
}
