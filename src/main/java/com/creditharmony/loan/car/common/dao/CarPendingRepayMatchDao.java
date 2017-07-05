package com.creditharmony.loan.car.common.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarPendingRepayMatchInfo;
import com.creditharmony.loan.car.common.entity.CarRepayMatchDetailInfo;

@LoanBatisDao
public interface CarPendingRepayMatchDao extends CrudDao<CarPendingRepayMatchInfo>
{
	/**
	 * 查询待还款匹配列表
	 * 2016年3月3日
	 * By 蒋力
	 * @param CarPendingRepayMatchInfo 查询待还款匹配列表信息
	 * @return 查询待还款匹配列表
	 */
  public  List <CarPendingRepayMatchInfo> selectPendingMatchList(PageBounds pageBounds,CarPendingRepayMatchInfo carPendingRepayMatchInfo);
  
  /**
	 * 查询待还款匹配明细列表
	 * 2016年3月4日
	 * By 蒋力
	 * @param carRefundInfo 查询待还款匹配明细列表信息
	 * @return 查询待还款匹配明细列表
	 */
  public  List <CarRepayMatchDetailInfo> selectMatchDetailList(CarRepayMatchDetailInfo carRepayMatchDetailInfo);
  
  /**
   * 待还款匹配提交
   * 2016年3月4日
   * By 蒋力
   * @param carRefundInfo 更新实体
   * @return 更新结果
   */
  public int updateRepayMatch(CarPendingRepayMatchInfo carPendingRepayMatchInfo);
  
  /**
   * 待还款匹配明细提交
   * 2016年3月5日
   * By 蒋力
   * @param carRefundInfo 更新实体
   * @return 更新结果
   */
  public int updateRepayDetailMatch(CarRepayMatchDetailInfo carRepayMatchDetailInfo);
  
  /**
	 * 加载选中待还款匹配列表
	 * 2016年3月5日
	 * By 蒋力
	 * @param CarRepayMatchDetailInfo 加载选中待还款匹配列表信息
	 * @return 待还款匹配列表
	 */
  public  List <CarPendingRepayMatchInfo> getCheckedMatchList(CarPendingRepayMatchInfo carPendingRepayMatchInfo);
  
  /**
	 * 待还款匹配批量退回
	 * 2016年3月5日
	 * By 蒋力
	 * @param CarRepayMatchDetailInfo 待还款匹配批量退回
	 * @return 待还款匹配批量退回
	 */
  public int updateRepayBatchBack(CarPendingRepayMatchInfo carPendingRepayMatchInfo);

}