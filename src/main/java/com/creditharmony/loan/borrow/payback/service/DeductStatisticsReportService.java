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
import com.creditharmony.loan.borrow.payback.dao.DeductStatisticsReportDao;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
import com.creditharmony.loan.borrow.payback.entity.DeductStatisticsReport;
/**
 * 
* Description:划扣统计报表service
* @class DeductStatisticsReportService
* @author wengsi 
* @date 2017年5月25日上午9:43:16
*/
@Service("DeductStatisticsReportService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class DeductStatisticsReportService extends CoreManager<DeductStatisticsReportDao,DeductStatisticsReport>{

	SimpleDateFormat  format = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 查询统计信息
	 * @param page
	 * @param record
	 * @return
	 */	
	public Page<DeductStatisticsReport> queryPage(Page<DeductStatisticsReport> page,
			DeductStatisticsReport record) {
		
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<DeductStatisticsReport> pageList = (PageList<DeductStatisticsReport>)dao.queryList(pageBounds,record);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	//查询当日划扣统计数据
	public List<DeductStatisticsReport> queryDeductStatistics() {
		return dao.queryDeductStatistics();
	}

	/**
	 * 查询统计列表
	 * @param record
	 * @return
	 */
	public List<DeductStatisticsReport> queryList(DeductStatisticsReport record) {
		return dao.queryList(record);
	}

	
	/**
	 * 统计导出
	 * @param record
	 * @return
	 */
	public List<DeductStatisticsReport> periodStatistics(DeductStatisticsReport record) {
		List<DeductStatisticsReport>   list =   dao.periodStatistics(record);
	 	String beginDate = "";
		String endDate = "";
		for(DeductStatisticsReport ts :list){
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
	
	/**
	 * 统计一段时间的余额不足和失败率
	 * @param page
	 * @param record
	 * @return
	 */
	public Page<DeductStatisticsReport> periodStatistics(Page<DeductStatisticsReport> page,
			DeductStatisticsReport record) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<DeductStatisticsReport> pageList = (PageList<DeductStatisticsReport>)dao.periodStatistics(pageBounds,record);
		String beginDate = "";
		String endDate = "";
		for(DeductStatisticsReport ts :pageList){
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
    
}
