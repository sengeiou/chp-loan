package com.creditharmony.loan.car.carRefund.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.common.dao.CarPendingRepayMatchDao;
import com.creditharmony.loan.car.common.entity.CarPendingRepayMatchInfo;
import com.creditharmony.loan.car.common.entity.CarRepayMatchDetailInfo;

/**
 * 
 * @Class Name carPendingRepayMatchService
 * @author 蒋力
 * @Create In 2016年3月3日
 * 待还款匹配service
 */
@Service("carPendingRepayMatchService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarPendingRepayMatchService extends CoreManager<CarPendingRepayMatchDao, CarPendingRepayMatchInfo>{
	/**
	 * 待还款匹配列表查询
	 * 2016年3月3日
	 * By 蒋力
	 * @param page
	 * @param CarPendingRepayMatchInfo
	 * @return 分页
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarPendingRepayMatchInfo> selectPendingMatchList(Page<CarPendingRepayMatchInfo> page,CarPendingRepayMatchInfo carPendingRepayMatchInfo){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<CarPendingRepayMatchInfo> pageList = (PageList<CarPendingRepayMatchInfo>)dao.selectPendingMatchList(pageBounds, carPendingRepayMatchInfo);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询待还款匹配明细列表
	 * 2016年3月4日
	 * By 蒋力
	 * @param carRefundInfo 查询待还款匹配明细列表信息
	 * @return 查询待还款匹配明细列表
	 */
  public  List <CarRepayMatchDetailInfo> selectMatchDetailList(CarRepayMatchDetailInfo carRepayMatchDetailInfo){
	  return dao.selectMatchDetailList(carRepayMatchDetailInfo);
  }
  
  /**
   * 待还款匹配提交
   * 2016年3月4日
   * By 蒋力
   * @param carRefundInfo 更新实体
   * @return 更新结果
   */
  @Transactional(readOnly = false, value = "loanTransactionManager")
  public int updateRepayMatch(CarPendingRepayMatchInfo carPendingRepayMatchInfo){
	  return dao.updateRepayMatch(carPendingRepayMatchInfo);
  }
  
  /**
   * 待还款匹配明细提交
   * 2016年3月5日
   * By 蒋力
   * @param carRefundInfo 更新实体
   * @return 更新结果
   */
  @Transactional(readOnly = false, value = "loanTransactionManager")
  public int updateRepayDetailMatch(CarRepayMatchDetailInfo carRepayMatchDetailInfo){
	  return dao.updateRepayDetailMatch(carRepayMatchDetailInfo);
  }
  
  /**
	 * 加载选中待还款匹配列表
	 * 2016年3月5日
	 * By 蒋力
	 * @param CarRepayMatchDetailInfo 加载选中待还款匹配列表信息
	 * @return 待还款匹配列表
	 */
	public  List <CarPendingRepayMatchInfo> getCheckedMatchList(CarPendingRepayMatchInfo carPendingRepayMatchInfo){
		  return dao.getCheckedMatchList(carPendingRepayMatchInfo);
	}
	
	/**
	 * 待还款匹配批量退回
	 * 2016年3月5日
	 * By 蒋力
	 * @param CarRepayMatchDetailInfo 待还款匹配批量退回
	 * @return 待还款匹配批量退回
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateRepayBatchBack(CarPendingRepayMatchInfo carPendingRepayMatchInfo){
		return dao.updateRepayBatchBack(carPendingRepayMatchInfo);
	}

}