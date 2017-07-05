package com.creditharmony.loan.borrow.grant.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeCheckDoneEx;
import com.creditharmony.loan.channel.jyj.entity.UrgeCheckJYJDoneEx;

/**
 * 催收服务费查账已办列表处理
 * @Class Name UrgeCheckDoneDao
 * @author 朱静越
 * @Create In 2016年3月2日
 */
@LoanBatisDao
public interface UrgeCheckDoneDao extends CrudDao<UrgeCheckDoneEx> {
	
	/**
	 * 催收服务费查账已办列表页面初始化
	 * 2016年3月2日
	 * By 朱静越
	 * @param urgeCheckDoneEx
	 * @return
	 */
	public List<UrgeCheckDoneEx> selCheckDone(PageBounds pageBounds,UrgeCheckDoneEx urgeCheckDoneEx);
	
	/**
	 * 催收服务费根据查询条件查询列表，不带分页
	 * 2016年3月3日
	 * By 朱静越
	 * @param urgeCheckDoneEx 查询条件
	 * @return 集合
	 */
	public List<UrgeCheckDoneEx> selCheckDone(UrgeCheckDoneEx urgeCheckDoneEx);

	public PageList<UrgeCheckJYJDoneEx> selCheckJYJDone(PageBounds pageBounds,
			UrgeCheckJYJDoneEx urgeCheckDoneEx);

	public List<UrgeCheckJYJDoneEx> selCheckJYJDone(UrgeCheckJYJDoneEx urgeCheckDoneEx);
	
}
