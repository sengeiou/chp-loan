package com.creditharmony.loan.channel.finance.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.channel.finance.dao.CreditorConfirmDao;
import com.creditharmony.loan.channel.finance.entity.CreditorConfirmEntity;
import com.creditharmony.loan.channel.finance.view.CreditorConfirmParam;
import com.google.common.collect.Maps;
@Service
public class CreditorConfirmService extends
		CoreManager<CreditorConfirmDao, CreditorConfirmEntity> {
	@Autowired
	private CreditorConfirmDao creditorConfirmDao;
	/**
	 * 获取债权确认的数据列表 2016年2月19日 By 张建雄
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 债权确认的数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<CreditorConfirmEntity> findSettlementOfClaimsList(Page<CreditorConfirmEntity> page, CreditorConfirmParam params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		Map<String,Object> queryMap = Maps.newHashMap();
		queryMap.put("params", params);
		PageList<CreditorConfirmEntity> pageList = (PageList<CreditorConfirmEntity>) creditorConfirmDao
				.findCreditorConfirmList(pageBounds, queryMap);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 导出债权结清状态列表或者是选中的债权结清列表数据列表信息
	 * 2016年2月21日 
	 * By 张建雄
	 * 
	 * @return 债权提前结清的数据列表
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public Map<String,Object> exportCreditorConfirmList(String cid,CreditorConfirmParam params,HttpServletResponse response){
		Map<String,Object> queryMap = Maps.newHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ctime = sdf.format(new Date());
		String[] loanCodes = {};
		if (StringUtils.isNotEmpty(cid)) {
			loanCodes = cid.split(",");
		}
		try {
			params.setCreditExportDate(sdf.parse(ctime));
//			params.setLoanCodes(cid);
			queryMap.put("params", params);
			if(ArrayHelper.isNotNull(loanCodes))
			{
				queryMap.put("loanCodes", loanCodes);
			}
			creditorConfirmDao.updateCreditorConfirm(queryMap);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return queryMap;
		//Excelutils.exportExcel(queryMap, response, sqlPath, fileName, header, body);	
		
	}
	/**
	 * 更新债权提前结清的数据列表 状态
	 * 2016年2月19日 
	 * By 张建雄
	 * 
	 * @return 债权提前结清的数据列表
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCreditorConfirm(String cid) {
		Map<String,Object> queryMap = Maps.newHashMap();
		if (StringUtils.isEmpty(cid))
			return ;
		String[] loanCodes = cid.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = sdf.format(new Date()); 
		CreditorConfirmParam params = new CreditorConfirmParam();
		try {
			params.setCreditConfirmDate(sdf.parse(ctime));
			queryMap.put("params", params);
			if(ArrayHelper.isNotNull(loanCodes))
			{
				queryMap.put("loanCodes", loanCodes);
			}
			
			creditorConfirmDao.updateCreditorConfirm(queryMap);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 计算总金额
	 * 2016年6月25日
	 * By 王彬彬
	 * @param queryMap
	 * @return
	 */
	public Map<String,Object> findSumCreditorConfirmList( CreditorConfirmParam params){
		Map<String,Object> queryMap = Maps.newHashMap();
		queryMap.put("params", params);
		return creditorConfirmDao.findSumCreditorConfirmList(queryMap);
	}
}
