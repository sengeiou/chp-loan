package com.creditharmony.loan.car.common.dao;
import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarUrgeOpe;
@LoanBatisDao
public interface CarUrgeOpeDao extends CrudDao<CarUrgeOpe>{
     
    /**
	 * 插入还款操作流水记录
	 * 2015年12月22日
	 * By zhangfeng
	 * @param paybackOpe
	 * @return none
	 */
	public int insert(CarUrgeOpe paybackOpe);
	
	/**
	 * 获取还款操作流水记录
	 * 2016年2月18日
	 * By 王彬彬
	 * @param mapParam
	 * @return 操作记录
	 */
	public List<CarUrgeOpe> getUrgeOpe(Map<String,String> mapParam,
			PageBounds pageBounds);


}