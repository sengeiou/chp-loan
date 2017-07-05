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

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanStatus;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.channel.finance.dao.SettlementOfClaimsDao;
import com.creditharmony.loan.channel.finance.entity.SettlementOfClaimsEntity;
import com.creditharmony.loan.channel.finance.view.SettlementOfClaimsParams;
import com.google.common.collect.Maps;

@Service
public class SettlementOfClaimsService extends
		CoreManager<SettlementOfClaimsDao, SettlementOfClaimsEntity> {
	@Autowired
	private SettlementOfClaimsDao settlementOfClaimsDao;

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
	public Page<SettlementOfClaimsEntity> findSettlementOfClaimsList(Page<SettlementOfClaimsEntity> page, SettlementOfClaimsParams params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		Map<String,Object> queryMap = Maps.newHashMap();
		params.setStatus(ChannelFlag.P2P.getCode());
		queryMap.put("params", params);
		PageList<SettlementOfClaimsEntity> pageList = (PageList<SettlementOfClaimsEntity>) settlementOfClaimsDao
				.findSettlementOfClaimsList(pageBounds, queryMap);
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
	public void updateSettlementOfClaims(String cid) {
		Map<String,Object> queryMap = Maps.newHashMap();
		if (StringUtils.isEmpty(cid))
			return ;
		String[] loanCodes = cid.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = sdf.format(new Date()); 
		SettlementOfClaimsParams params = new SettlementOfClaimsParams();
		params.setStatus(LoanStatus.EARLY_SETTLE.getCode());
			try {
				params.setSettleConfirmDate(sdf.parse(ctime));
				params.setLoanCodes(cid);
				queryMap.put("params", params);
				queryMap.put("loanCodes", loanCodes);
				settlementOfClaimsDao.updateSettlementOfClaims(queryMap);
			
			} catch (ParseException e) {
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
			params.setStatus(ChannelFlag.P2P.getCode());
			params.setCreditExportDate(sdf.parse(ctime));
			queryMap.put("params", params);
			settlementOfClaimsDao.updateSettlementOfClaims(queryMap);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		return queryMap;
	}
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Map<String,Object> findSumSettlementOfClaimsList(SettlementOfClaimsParams params) {
		Map<String,Object> queryMap = Maps.newHashMap();
		params.setStatus(ChannelFlag.P2P.getCode());
		queryMap.put("params", params);
		
		Map<String,Object> mapSum = settlementOfClaimsDao
				.findSumSettlementOfClaimsList(queryMap);
		
		return mapSum;
	}
}
 