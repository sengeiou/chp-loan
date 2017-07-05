package com.creditharmony.loan.common.dao;

import java.util.List;

import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.common.entity.CoeffRefer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;

/**
 * 汇金产品管理-Dao
 * 
 * @Class Name LoanPrdMngDao
 * @author 周亮
 * @Create In 2015年11月30日
 */
@LoanBatisDao
public interface LoanPrdMngDao {
	/**
	 * 检索产品
	 * 
	 * @param key
	 * @return List<LoanPrdTypeMngEntity>
	 */
	List<LoanPrdMngEntity> selPrd(LoanPrdMngEntity key);
	
	/**
	 * 获取系数参照表（期数、费率、前期综合费）获取期数
	 * 2016年4月23日
	 * By 王彬彬
	 * @param coeffRefer 系数参照表（期数、费率、前期综合费）
	 * @return
	 */
	List<String> getCoeffReferMonths(CoeffRefer coeffRefer);
	
	/**
	 *根据借款期限、费率查询前期综合服务费系数
	 *2016年4月27日 
	 *By zhanghao
	 *@param coeffRefer 
	 *@return CoeffRefer
	 */
	public CoeffRefer getCoeffReferByParam(CoeffRefer coeffRefer);
}