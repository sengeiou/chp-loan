package com.creditharmony.loan.channel.goldcredit.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.channel.goldcredit.dao.GCBusinessDao;
import com.creditharmony.loan.channel.goldcredit.view.GCBusiness;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
@Service
public class GCBusinessService extends CoreManager<GCBusinessDao, GCBusiness>{
	@Autowired
	private GCBusinessDao dao;
	/**
	 * 获取金信上限数据列表 2016年2月19日 By 张建雄
	 * 
	 * @param params
	 *            查询参数
	 * @param page
	 *            分页参数
	 * @return 金信上限数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<GCBusiness> findGCCeilingList(Page<GCBusiness> page, GCBusiness params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		params.setChannel(ChannelFlag.JINXIN.getCode());
		String [] storeOrgIds = {};
		if (StringUtils.isNotEmpty(params.getStoreOrgId())){
			storeOrgIds = params.getStoreOrgId().split(",");
		}
		Map<String ,Object> conditions = Maps.newHashMap();
		conditions.put("params", params);
		conditions.put("storeOrgIds", storeOrgIds);
		PageList<GCBusiness> pageList = (PageList<GCBusiness>) dao.getGCBusinessList(pageBounds, conditions);
		
		PageUtil.convertPage(pageList, page);
		return page;
	}
	/**
	 * 导出
	 * 2016年2月24日 
	 * By 张建雄
	 * 
	 * @return 金信业务数据列表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<GCBusiness> exportBusiness(String cid,GCBusiness params){
		params.setChannel(ChannelFlag.JINXIN.getCode());
		List<GCBusiness> lists = Lists.newArrayList();
		Map<String ,Object> conditions = Maps.newHashMap();
		if (StringUtils.isNotEmpty(params.getStoreOrgId())){
			String []storeOrgIds = params.getStoreOrgId().split(",");
			conditions.put("storeOrgIds", storeOrgIds);
		}
		if (StringUtils.isNotEmpty(cid)) {
			String [] loanCodes = cid.split(",");
			
			for (int i = 0; i < loanCodes.length; i++) {
				String loanCode = loanCodes[i];
				params.setApplyId(loanCode);	
				conditions.put("params", params);
				List<GCBusiness> pageList = dao
						.getGCBusinessList(conditions);
				if (pageList.size()!= 0)
					lists.add(pageList.get(0));
			}
		}else {
			conditions.put("params", params);
			List<GCBusiness> pageList = dao
					.getGCBusinessList(conditions);
			lists = pageList;
		}
		return lists;
	}
}
