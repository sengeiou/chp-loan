package com.creditharmony.loan.app.consult.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.app.consult.entity.AppConsult;
import com.creditharmony.loan.app.consult.view.AppConsultView;
import com.creditharmony.loan.app.consult.view.AssistantConsultView;

/**
 * app客户咨询的操作
 * @Class Name AppConsultDao
 * @author 朱静越
 * @Create In 2016年6月10日
 */
@LoanBatisDao
public interface AppConsultDao extends CrudDao<AppConsult> {

	/**
	 * 获得app页面咨询列表，条件为：is_new为 0，为最新数据
	 * 2016年6月10日
	 * By 朱静越
	 * @return
	 */
	public List<AppConsult> findList(PageBounds pageBounds,
			AppConsultView appConsultView);
	
	/**
	 * 根据id查询详细页面
	 * 2016年6月10日
	 * By 朱静越
	 * @param id
	 * @return
	 */
	public AppConsult getConsult(String id);
	
	/**
	 * 更新app主表
	 * 2016年6月11日
	 * By 朱静越
	 * @param appConsult
	 */
	public void updateAppConsult(AppConsult appConsult);
	
	/**
	 * 获取门店ocr客户咨询列表页面
	 * 2016年6月10日
	 * By 赵金平
	 * @param pageBounds
	 * @param AppConsult
	 * @return
	 */
	public List<AppConsult> findAssistantConsult(PageBounds pageBounds,AssistantConsultView assistantConsultView);

	/**
	 * 更新ocr客户咨询详情页的信息
	 * 2016年6月10日
	 * By 赵金平
	 * @param AssistantConsultView
	 */
	public void updateAssistantConsult(AppConsult appConsult);
}
