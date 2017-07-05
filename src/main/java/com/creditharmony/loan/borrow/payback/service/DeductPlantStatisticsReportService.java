package com.creditharmony.loan.borrow.payback.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.payback.dao.DeductPlantStatisticsReportDao;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
/**
 * 划扣平台报表统计service
 * @author xh
 *
 */
@Service("DeductPlantStatisticsReportService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class DeductPlantStatisticsReportService extends CoreManager<DeductPlantStatisticsReportDao,DeductStatistics>{

	SimpleDateFormat  format = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 查询统计信息
	 * @param page
	 * @param record
	 * @return
	 */	
	public Page<DeductStatistics> queryPage(Page<DeductStatistics> page,
			DeductStatistics record) {
		
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<DeductStatistics> pageList = (PageList<DeductStatistics>)dao.queryList(pageBounds,record);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	//查询当日划扣统计数据
	public List<DeductStatistics> queryDeductStatistics() {
		return dao.queryDeductStatistics();
	}

	/**
	 * 查询统计列表
	 * @param record
	 * @return
	 */
	public List<DeductStatistics> queryList(DeductStatistics record) {
		return dao.queryList(record);
	}

	/**
	 * 统计一段时间的余额不足和失败率
	 * @param page
	 * @param record
	 * @return
	 */
	public Page<DeductStatistics> periodStatistics(Page<DeductStatistics> page,
			DeductStatistics record) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<DeductStatistics> pageList = (PageList<DeductStatistics>)dao.periodStatistics(pageBounds,record);
		String beginDate = "";
		String endDate = "";
		for(DeductStatistics ts :pageList){
			if(!ObjectHelper.isEmpty(record.getBeginDate())){
				 beginDate = format.format(record.getBeginDate());
			}
			
			if(!ObjectHelper.isEmpty(record.getEndDate())){
				endDate = format.format(record.getEndDate());
			}
			
			if(ObjectHelper.isEmpty(record.getBeginDate()) && ObjectHelper.isEmpty(record.getEndDate())){
				ts.setCreateDate("所有日期");
			}else{
				 ts.setCreateDate(beginDate +"-"+ endDate);
			}
			
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 统计导出
	 * @param record
	 * @return
	 */
	public List<DeductStatistics> periodStatistics(DeductStatistics record) {
		List<DeductStatistics>   list =   dao.periodStatistics(record);
	 	String beginDate = "";
		String endDate = "";
		for(DeductStatistics ts :list){
			if(!ObjectHelper.isEmpty(record.getBeginDate())){
				 beginDate = format.format(record.getBeginDate());
			}
			
			if(!ObjectHelper.isEmpty(record.getEndDate())){
				endDate = format.format(record.getEndDate());
			}
			
			if(ObjectHelper.isEmpty(record.getBeginDate()) && ObjectHelper.isEmpty(record.getEndDate())){
				ts.setCreateDate("所有日期");
			}else{
				 ts.setCreateDate(beginDate +"-"+ endDate);
			}
		}
		return list;
	}
    
}
