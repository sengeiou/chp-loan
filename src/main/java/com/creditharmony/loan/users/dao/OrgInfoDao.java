package com.creditharmony.loan.users.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.users.entity.OrgInfo;


/**
 * 组织机构dao
 * @Class Name OrgInfoDao
 * @author 张永生
 * @Create In 2015年12月8日
 */
@LoanBatisDao
public interface OrgInfoDao extends CrudDao<OrgInfo> {

	public OrgInfo getByCityIdAndCarLoanCode(Map<String, Object> map);
	
	/**
	 * 根据type查机构
	 * By 任志远 2016年11月23日
	 *
	 * @param map
	 * @return
	 */
	public List<OrgInfo> queryOrgByType(Map<String, Object> map);
	
	/** 
	 * 根据父ID和机构类型查询下级机构
	 * By 任志远 2016年11月23日
	 *
	 * @param map
	 * @return
	 */
	public List<OrgInfo> queryOrgByParentIdAndType(Map<String, Object> map);
	
	/**
	 * 查询组织机构下所有门店
	 */
	public List<OrgInfo> queryDepartmentByOrgId(String orgId);
	/**
	 * 查询组织机构下对应机构类型下的所有机构
	 */
	public List<OrgInfo> selectDeptByOrgidAndOrgtype(String orgId, String orgType);
	/**
	 * zmq
	 * 查询组织机构下所有门店
	 */
	public List<OrgInfo> queryDeliveryByOrgId(String orgId);
}
