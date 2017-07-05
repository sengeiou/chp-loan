package com.creditharmony.loan.channel.finance.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;
import com.creditharmony.loan.channel.finance.dao.CarCreditorConfirmDao;
import com.creditharmony.loan.channel.finance.dao.CreditorConfirmDao;
import com.creditharmony.loan.channel.finance.entity.CreditorConfirmEntity;
import com.creditharmony.loan.channel.finance.excel.Excelutils;
import com.creditharmony.loan.channel.finance.view.CreditorConfirmParam;
import com.google.common.collect.Maps;
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class CarCreditorConfirmService extends
		CoreManager<CreditorConfirmDao, CreditorConfirmEntity> {
	@Autowired
	private CarCreditorConfirmDao carCreditorConfirmDao;
	/**
	 * 获取债权确认的数据列表 2016年5月17日 By 陈伟丽
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 债权确认的数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<CreditorConfirmEntity> findCarSettlementOfClaimsList(Page<CreditorConfirmEntity> page, CreditorConfirmParam params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		Map<String,Object> queryMap = Maps.newHashMap();
		params.setCreditType(CreditorRights.CREDIT_TYPE_HASLOAD);
		queryMap.put("params", params);
		PageList<CreditorConfirmEntity> pageList = (PageList<CreditorConfirmEntity>) carCreditorConfirmDao
				.findCarCreditorConfirmList(pageBounds, queryMap);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	

	/**
	 * 导出EXCEL
	 * 查询需要导出的列表
	 * @param cid
	 * @param params
	 * @param response
	 * @return
	 */
	public List<CreditorConfirmEntity> exportCarCreditorConfirmList(CreditorConfirmParam params){
		Map<String,Object> queryMap = Maps.newHashMap();
		params.setCreditType(CreditorRights.CREDIT_TYPE_HASLOAD);
		queryMap.put("params", params);
		List<CreditorConfirmEntity> s= carCreditorConfirmDao
		.findCarCreditorConfirmList(queryMap);
		return s;
	}
	/**
	 * 更新债权提前结清的数据列表 状态
	 * 2016年5月17日 
	 * By 陈伟丽
	 * 
	 * @return 债权提前结清的数据列表
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCarCreditorConfirm(CreditorConfirmParam params) {
		Map<String,Object> queryMap = Maps.newHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = sdf.format(new Date()); 
		try {
			params.setCreditConfirmDate(sdf.parse(ctime));
			params.setCreditType(CreditorRights.CREDIT_TYPE_HASLOAD);
			queryMap.put("params", params);
			carCreditorConfirmDao.updateCarCreditorConfirm(queryMap);
		} catch (ParseException e) {
		}
	}
}
