package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.Appointment;
import com.creditharmony.loan.borrow.payback.entity.AppointmentVO;

/**
 * 预约dao
 * @Class Name AppointmentDudctDao
 * @author 翁私
 * @Create In 2016年10月26日
 */
@LoanBatisDao
public interface AppointmentDudctDao extends CrudDao<Appointment> {

	/**
	 * 查询预约列表
	 * @param pageBounds
	 * @param map
	 * @return
	 */
	PageList<Appointment> queryList(PageBounds pageBounds,
			Map<String, Object> map);
	
	
	/**
	 * 查询预约list
	 * @param map
	 * @return
	 */
	PageList<Appointment> queryList(
			Map<String, Object> map);

	/**
	 * 查询逾期期数
	 * @return
	 */
	List<String> queryOverCount();

   /**
    * 生效失效方法
    * @param bean
    */
	void changEffect(Appointment bean);


	/**
	 * 通过实体bean 查询
	 * @param bean
	 * @return
	 */
   List<Appointment> queryList(Appointment bean);


	List<Appointment> queryParamList(Appointment bean);


	void updateOrDelete(Appointment bean);


	List<Appointment> queryRuleList(Appointment bean);

	List<Appointment> queryRuleDataList(Appointment bean);


	
}
