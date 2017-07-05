package com.creditharmony.loan.users.dao;

import java.util.List;
import java.util.Map;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.core.users.entity.UserRoleOrg;
import com.creditharmony.loan.users.entity.UserInfo;

/**
 * 用户dao
 * 
 * @Class Name UserInfoDao
 * @author 张永生
 * @Create In 2015年12月8日
 */
@LoanBatisDao
public interface UserInfoDao extends CrudDao<UserInfo> {

	/**
	 * 建立用户与机构组的关系
	 * 
	 * @param user
	 * @return
	 */
	public void insertUserOrg(UserInfo userInfo);

	/**
	 * 建立用户、角色、组织间关系 2016年1月7日 By 陈伟东
	 * 
	 * @param userRoleOrg
	 */
	public void insertUserRoleOrg(UserRoleOrg userRoleOrg);

	/**
	 * 删除用户与机构组的关系
	 * 
	 * @param userId
	 * @return
	 */
	public void deleteUserOrg(String userId);

	/**
	 * 删除用户角色的关系
	 * 
	 * @param userId
	 * @return
	 */
	public void deleteUserRole(String userId);

	/**
	 * 删除用户角色组织的关系 2016年1月7日 By 陈伟东
	 * 
	 * @param userId
	 */
	public void deleteUserRoleOrg(String userId);

	/**
	 * 新增用户与角色的关系
	 * 
	 * @param userInfo
	 * @return
	 */
	public void insertUserRole(UserInfo userInfo);

	/**
	 * 分页查询User列表
	 * 
	 * @param user
	 * @return
	 */
	PageList<UserInfo> findUserInfoPage(UserInfo user);

	/**
	 * 查询User列表 不分页 2016年1月26日 By 张振强
	 * 
	 * @param user
	 * @return
	 */
	List<UserInfo> findUserInfo(UserInfo user);

	/**
	 * 获取固定角色用户 2016年1月20日 By 王彬彬
	 * 
	 * @param userMap
	 *            查询条件
	 * @return 用户列表
	 */
	public List<UserInfo> getRoleUser(Map<String, String> userMap,PageBounds pageBounds);
	/**
	 * 获取外访专员
	 */
	public List<UserInfo> getOutPeopleList(Map<String, String> userMap,PageBounds pageBounds);
	
	/**
	 * 获取固定角色用户 
	 * 2016年1月20日 By 王彬彬
	 * 
	 * @param userMap
	 *            查询条件
	 * @return 用户列表
	 */
	public List<UserInfo> getRoleUser(Map<String, String> userMap);
	
	public List<UserInfo> getRoleUserByMaps(Map<String, String> userMap);

	/**
	 * 分页查询User列表
	 * 
	 * @param userMap 查询条件（用户roleid和orgid）
	 * @param pageBounds 分页信息
	 * @return 用户信息
	 */
	public List<UserInfo> findUserInfoByRole(Map<String,String> userMap,PageBounds pageBounds);
	
	/**
	 * 获取某一门店下的所有团队经理
	 * 2016年7月8日
	 * By 申诗阔
	 * @param userMap
	 * @return
	 */
	public List<UserInfo> getStoreAllTermManager(Map<String, String> userMap);
	
	/**
	 * 获取团队经理下的所有客户经理
	 * 2016年7月8日
	 * By 申诗阔
	 * @param userMap
	 * @return
	 */
	public List<UserInfo> getTermAllCustomerManager(Map<String, String> userMap);
}
