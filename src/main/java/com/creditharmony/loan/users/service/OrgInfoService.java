package com.creditharmony.loan.users.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.users.dao.OrgInfoDao;
import com.creditharmony.loan.users.entity.OrgInfo;

/**
 * 组织机构服务
 * @Class Name OrgInfoService
 * @author 王彬彬
 * @Create In 2016年3月11日
 */
@Component
@Service
@Transactional(readOnly = false, value = "loanTransactionManager")
public class OrgInfoService extends CoreManager<OrgInfoDao, OrgInfo> {
	
	@Autowired
	private OrgInfoDao orgInfoDao;
	
	/**
	 * 组织机构
	 * 2016年3月11日
	 * By 王彬彬
	 * @param id 机构ID
	 * @return 组织机构
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public OrgInfo getOrg(String id){
		return dao.get(id);
	}
	
	/**
	 * 保存组织机构
	 * 2016年3月11日
	 * By 王彬彬
	 * @param orgInfo 保存的组织机构
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveOrg(OrgInfo orgInfo){
		dao.insert(orgInfo);
	}
	
	/**
	 * 更新组织机构
	 * 2016年3月11日
	 * By 王彬彬
	 * @param orgInfo 更新对象
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void update(OrgInfo orgInfo){
		dao.update(orgInfo);
	}
	
	/**
	 * 删除组织机构
	 * 2016年3月11日
	 * By 王彬彬
	 * @param id 删除机构id
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void delete(String id){
		dao.delete(id);
	}
	
	/**
	 * 根据 城市Id 和 门店车借编码 获取 机构
	 * 2016年6月4日
	 * By 申诗阔
	 * @param cityId
	 * @param carLoanCode
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public OrgInfo getByCityIdAndCarLoanCode(String cityId, String carLoanCode){
		Map<String, Object> map = new HashMap<String, Object>();
		return orgInfoDao.getByCityIdAndCarLoanCode(map);
	}
	
	/**
	 * 根据type查机构
	 * By 任志远 2016年11月23日
	 *
	 * @param map
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<OrgInfo> queryOrgByType(Map<String, Object> map){
		
		return orgInfoDao.queryOrgByType(map);
	}
	
	/**
	 * 根据父ID查门店
	 * By 任志远 2016年11月23日
	 *
	 * @param map	{'byType':'6200000001', 'id':'业务部ID'}  或者 {'byType':'6200000002', 'id':'省分公司ID'}
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<OrgInfo> queryOrgByParentIdAndType(Map<String, Object> map){
		
		return orgInfoDao.queryOrgByParentIdAndType(map);
	}
	
	/**
	 * 查询组织机构下所有门店
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<OrgInfo> queryDepartmentByOrgId(String orgId) {
		return orgInfoDao.queryDepartmentByOrgId(orgId);
	}
	
	/**
	 * 查询组织机构下对应机构类型下的所有机构
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<OrgInfo> selectDeptByOrgidAndOrgtype(String orgId, String orgType) {
		return orgInfoDao.selectDeptByOrgidAndOrgtype(orgId,orgType);
	}
	
	/**
	 * zmq
	 * 查询组织机构下所有门店
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<OrgInfo> queryDeliveryByOrgId(String orgId) {
		return orgInfoDao.queryDeliveryByOrgId(orgId);
	}
}
