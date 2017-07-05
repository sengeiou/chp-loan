package com.creditharmony.loan.sync.data.remote;

import com.creditharmony.core.sync.data.entity.SyncDict;
import com.creditharmony.core.sync.data.entity.SyncOrg;
import com.creditharmony.core.sync.data.entity.SyncRole;
import com.creditharmony.core.sync.data.entity.SyncUser;
import com.creditharmony.core.sync.data.entity.SyncUserRoleOrg;

/**
 * 汇金端从系统管理模块同步数据发布的接口
 * @Class Name LoanSyncDataService
 * @author 张永生
 * @Create In 2015年12月7日
 */
public interface LoanSyncDataService {
	
	/**
	 * 执行同步组织机构接口
	 * 2015年12月7日
	 * By 张永生
	 * @param syncUser
	 * @return
	 */
	public boolean executeSyncOrg(SyncOrg syncOrg);

	/**
	 * 同步用户
	 * 2015年12月7日
	 * By 张永生
	 * @param user
	 * @return
	 */
	public boolean executeSyncUser(SyncUser syncUser);
	
	/**
	 * 执行同步用户组织结构接口
	 * @param syncUserOrg
	 * @return
	 */
	public boolean executeSyncUserRoleOrg(SyncUserRoleOrg syncUserRoleOrg);
	
	/**
	 * 执行同步角色接口
	 * 2015年12月25日
	 * By 陈伟东
	 * @param syncRole
	 * @return
	 */
	public boolean executeSyncRole(SyncRole syncRole);
	
	
	/**
	 * 执行同步数据字典接口
	 * 2015年12月28日
	 * By 陈伟东
	 * @param syncDict
	 * @return
	 */
	public boolean executeSyncDict(SyncDict syncDict);
	
	
	
}
