package com.creditharmony.loan.borrow.payback.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.loan.borrow.payback.entity.DeductPlantLimit;
@LoanBatisDao
public interface DeductPlantLimitDao extends CrudDao<DeductPlantLimit>{
    int deleteByPrimaryKey(String id);

    int insert(DeductPlantLimit record);

    int insertSelective(DeductPlantLimit record);

    DeductPlantLimit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DeductPlantLimit record);

    int updateByPrimaryKey(DeductPlantLimit record);
    
    /**
	 * 分页查询
	 * @param pageBounds
	 * @param paramMap
	 * @return list
	 */
	public PageList<DeductPlantLimit> queryPage(PageBounds pageBounds,
			DeductPlantLimit record);
	public PageList<DeductPlantLimit> findPlantList(DeductPlantLimit record);
	
	public List<DeductPlantLimit> queryPage(DeductPlantLimit record);

	/**
	 *  查询门店划扣申请限制列表
	 * @param pageBounds
	 * @param record
	 * @return
	 */
	PageList<DeductPlantLimit> queryOrgPage(PageBounds pageBounds,
			DeductPlantLimit record);
	/**
	 * 删除发起还款申请配置
	 * @param record
	 */
	void deleteOrg(DeductPlantLimit record);

	/**
	 * 查询组织机构
	 * @param page
	 * @param org
	 * @return
	 */
	PageList<Org> findOrgsByParams(Org org, PageBounds pageBounds);

	/**
	 * 查询组织机构
	 * @param org
	 * @return
	 */
	List<Org> findOrgsByParams(Org org);

	/**
	 * 保存门店发起还款申请限制
	 * @param record
	 */
	void saveOrg(DeductPlantLimit record);

	/**
	 * 查询该门店限制是否存在
	 * @param org
	 * @return
	 */
	List<DeductPlantLimit> queryOrgLimit(Org org);

	/**
	 * 修改门店限制
	 * @param record
	 */
	void updateOrg(DeductPlantLimit record);

	/**
	 * 门店划扣统计数据
	 * @param pageBounds
	 * @param record
	 * @return
	 */
	PageList<DeductPlantLimit> queryOrgStatisticsPage(PageBounds pageBounds,
			DeductPlantLimit record);

}