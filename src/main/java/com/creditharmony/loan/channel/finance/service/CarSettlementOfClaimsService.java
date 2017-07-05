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
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;
import com.creditharmony.loan.channel.finance.dao.CarSettlementOfClaimsDao;
import com.creditharmony.loan.channel.finance.dao.SettlementOfClaimsDao;
import com.creditharmony.loan.channel.finance.entity.CarSettlementOfClaimsEntity;
import com.creditharmony.loan.channel.finance.entity.SettlementOfClaimsEntity;
import com.creditharmony.loan.channel.finance.view.SettlementOfClaimsParams;
import com.google.common.collect.Maps;

@Service
@Transactional(readOnly = true, value = "transactionManager")
public class CarSettlementOfClaimsService extends
		CoreManager<CarSettlementOfClaimsDao, CarSettlementOfClaimsEntity> {
	@Autowired
	private CarSettlementOfClaimsDao carSettlementOfClaimsDao;

	/**
	 * 获取债权提前结清的数据列表 2016年2月19日 By 张建雄
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 债权提前结清的数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<CarSettlementOfClaimsEntity> findSettlementOfClaimsList(Page<CarSettlementOfClaimsEntity> page, SettlementOfClaimsParams params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		Map<String,Object> queryMap = Maps.newHashMap();
		params.setCreditType(CreditorRights.CREDIT_TYPE_EARLYSETTLE);
		queryMap.put("params", params);
		PageList<CarSettlementOfClaimsEntity> pageList = (PageList<CarSettlementOfClaimsEntity>) carSettlementOfClaimsDao
				.findCarSettlementOfClaimsList(pageBounds, queryMap);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	/**
	 * 更新债权提前结清的数据列表 状态
	 * 2016年2月19日 
	 * By 张建雄
	 * 
	 * @return 债权提前结清的数据列表
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateCarSettlementConfirm(SettlementOfClaimsParams params) {
		Map<String,Object> queryMap = Maps.newHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = sdf.format(new Date());
		params.setStatus(CreditorRights.CREDIT_TYPE_HASSETTLE);
		params.setCreditType(CreditorRights.CREDIT_TYPE_EARLYSETTLE);
		try {
			params.setSettleConfirmDate(sdf.parse(ctime));
			queryMap.put("params", params);
			carSettlementOfClaimsDao.updateCarSettlementConfirm(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出债权结清状态列表或者是选中的债权结清列表数据列表信息
	 * 2016年2月21日 
	 * By 张建雄
	 * 
	 * @return 债权提前结清的数据列表
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public Map<String,Object> exportSettlementOfClaims(String cid,SettlementOfClaimsParams params,HttpServletResponse response){
		Map<String,Object> queryMap = Maps.newHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ctime = sdf.format(new Date());
		String[] loanCodes = {};
		if (StringUtils.isNotEmpty(cid)) {
			loanCodes = cid.split(",");
		}
		queryMap.put("loanCodes", loanCodes);
		try {
			params.setLoanCodes(cid);
			params.setStatus(LoanStatus.EARLY_SETTLE.getCode());
			params.setCreditExportDate(sdf.parse(ctime));
			queryMap.put("params", params);
			carSettlementOfClaimsDao.updateCarSettlementOfClaims(queryMap);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return queryMap;
	}
	

	/**
	 * 获取债权提前结清的数据列表 2016年2月19日 By 张建雄
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 债权提前结清的数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<CarSettlementOfClaimsEntity> findSettlementOfClaimsList(SettlementOfClaimsParams params) {
		Map<String,Object> queryMap = Maps.newHashMap();
		params.setCreditType(CreditorRights.CREDIT_TYPE_EARLYSETTLE);
		queryMap.put("params", params);
		List<CarSettlementOfClaimsEntity> pageList =  carSettlementOfClaimsDao
				.findCarSettlementOfClaimsList(queryMap);
		return pageList;
	}
}
 