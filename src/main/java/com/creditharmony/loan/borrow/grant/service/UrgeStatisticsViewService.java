package com.creditharmony.loan.borrow.grant.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.dao.UrgeStatisticsViewDao;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeStatisticsView;


/**
 * 已制作合同Service 
 * @Class Name ContractPersonService
 * @create In 2016年3月4日
 * @author 尚军伟
 */
@Service("urgeStatisticsViewService")
public class UrgeStatisticsViewService extends CoreManager<UrgeStatisticsViewDao, UrgeStatisticsView>{
	
	@Autowired
	private UrgeStatisticsViewDao statisticsViewDao;
	
	
	/**
	 * 查询催收服务费统计表
	 * 2016年4月21日
	 * By 尚军伟
	 * @param 
	 * @return 
	 */
	public Page<UrgeStatisticsView> findUrgeStatisticsView(UrgeStatisticsView urgeTongJiView,Page<UrgeStatisticsView> page){
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
        PageList<UrgeStatisticsView> pageList = (PageList<UrgeStatisticsView>)statisticsViewDao.findList(urgeTongJiView, pageBounds);
        PageUtil.convertPage(pageList, page);
		return page;
	}
	
	public List<UrgeStatisticsView> findByContractCode(Map<String,Object> params){
		return statisticsViewDao.findByContractCode(params);
	}

}
