/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.black.daoBlackDao.java
 * @Create By 张灏
 * @Create In 2015年12月15日 上午9:47:26
 */
package com.creditharmony.loan.app.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskInfo;
import com.creditharmony.loan.borrow.outvisit.enity.LoanOutsideTaskList;
import com.creditharmony.loan.common.entity.OutsideCheckInfo;

/**
 * APP业务处理Dao
 * @Class Name AppDao
 * @author zhangfeng
 * @Create In 2016年3月11日
 */
@LoanBatisDao
public interface AppDao extends CrudDao<OutsideCheckInfo> {

	/**
	 * 查询外访信息
	 * 2016年2月1日
	 * By zhangfeng
	 * @param map
	 * @return list
	 */
	public List<OutsideCheckInfo> getOutsideTaskList(Map<String, String> map);

	/**
	 * 更新外访信息
	 * 2016年2月2日
	 * By zhangfeng
	 * @param out
	 * @return none
	 */
	public void updateOutSideTask(LoanOutsideTaskList out);

	/**
	 * 获取共借人主借人信息
	 * 2016年3月11日
	 * By zhangfeng
	 * @param outInfo
	 * @return list
	 */
	public List<LoanOutsideTaskInfo> getOutsideTaskListInfo(LoanOutsideTaskInfo outInfo);

	/**
	 * 获取主借共借人拍摄要求
	 * 2016年3月14日
	 * By zhangfeng
	 * @param dict
	 * @return String
	 */
	public Dict getOutCheck(Dict dict);

	/**
	 * 是否重复上传判断
	 * 2016年3月21日
	 * By zhangfeng
	 * @param mapCustomer
	 * @return list
	 */
	public List<LoanOutsideTaskInfo> getOutListForDistance(Map<String, String> mapCustomer); 

}
