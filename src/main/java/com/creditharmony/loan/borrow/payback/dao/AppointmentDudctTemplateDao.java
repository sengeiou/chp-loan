package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.AppointmentTemplate;
import com.creditharmony.loan.borrow.payback.entity.AppointmentVO;

/**
 * 预约dao
 * @Class Name AppointmentDudctDao
 * @author 翁私
 * @Create In 2016年10月26日
 */
@LoanBatisDao
public interface AppointmentDudctTemplateDao extends CrudDao<AppointmentTemplate> {

	/**
	 * 查询预约列表
	 * @param pageBounds
	 * @param map
	 * @return
	 */
	PageList<AppointmentTemplate> queryList(PageBounds pageBounds,
			Map<String, Object> map);
	
	
	/**
	 * 查询预约list
	 * @param map
	 * @return
	 */
	PageList<AppointmentTemplate> queryList(
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
	void changEffect(AppointmentTemplate bean);


	/**
	 * 通过实体bean 查询
	 * @param bean
	 * @return
	 */
   List<AppointmentTemplate> queryList(AppointmentTemplate bean);


	List<AppointmentTemplate> queryParamList(AppointmentTemplate bean);


	void updateOrDelete(AppointmentTemplate bean);


	
}
