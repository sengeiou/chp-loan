package com.creditharmony.loan.sync.data.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.common.type.DeleteFlagType;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.sync.data.entity.SyncDict;
import com.creditharmony.core.sync.data.entity.SyncOrg;
import com.creditharmony.core.sync.data.entity.SyncRole;
import com.creditharmony.core.sync.data.entity.SyncUser;
import com.creditharmony.core.sync.data.entity.SyncUserRoleOrg;
import com.creditharmony.core.sync.data.type.SyncType;
import com.creditharmony.core.users.entity.UserRoleOrg;
import com.creditharmony.loan.dict.entity.DictInfo;
import com.creditharmony.loan.dict.service.DictInfoManager;
import com.creditharmony.loan.sync.data.remote.LoanSyncDataService;
import com.creditharmony.loan.users.entity.OrgInfo;
import com.creditharmony.loan.users.entity.RoleInfo;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.OrgInfoService;
import com.creditharmony.loan.users.service.RoleInfoService;
import com.creditharmony.loan.users.service.UserInfoService;

/**
 * 汇金端同步从系统管理模块同步数据接口代理类
 * @Class Name LoanSyncDataProxy
 * @author 张永生
 * @Create In 2015年12月7日
 */
@Component
public class LoanSyncDataProxy implements LoanSyncDataService{
	
	protected Logger logger = LoggerFactory.getLogger(LoanSyncDataProxy.class);

	private UserInfoService userInfoService = SpringContextHolder.getBean(UserInfoService.class);
	
	private OrgInfoService orgInfoService = SpringContextHolder.getBean(OrgInfoService.class);
	
	private RoleInfoService roleInfoService = SpringContextHolder.getBean(RoleInfoService.class);
	
	private DictInfoManager dictInfoManager = SpringContextHolder.getBean(DictInfoManager.class);
	
	
	@SuppressWarnings("finally")
	@Override
	@Transactional(value="loanTransactionManager", readOnly = false)
	public boolean executeSyncUser(SyncUser syncUser) {
		boolean success = true;
		try {
			if(SyncType.TYPE_ADD.value.equals(syncUser.getSyncType())){
				UserInfo userInfo = userInfoService.get(syncUser.getUserId());
				if(ObjectHelper.isEmpty(userInfo)){
					userInfo = new UserInfo();
					BeanUtils.copyProperties(syncUser, userInfo);
					userInfo.setId(syncUser.getUserId());
					userInfoService.saveUser(userInfo);
				}
			}else if(SyncType.TYPE_MODIFY.value.equals(syncUser.getSyncType())){
				UserInfo userInfo = userInfoService.get(syncUser.getUserId());
				if(ObjectHelper.isEmpty(userInfo)){
					userInfo = new UserInfo();
					BeanUtils.copyProperties(syncUser, userInfo);
					userInfo.setId(syncUser.getUserId());
					userInfoService.saveUser(userInfo);
				}else{
					BeanUtils.copyProperties(syncUser, userInfo);
					userInfo.setId(syncUser.getUserId());
					userInfo.setPosPwd(null); //系统管理sms更新用户时，不再更新posPwd
					userInfoService.update(userInfo);
				}
			}else if(SyncType.TYPE_DELETE.value.equals(syncUser.getSyncType())){
				UserInfo userInfo = userInfoService.get(syncUser.getUserId());
				if(ObjectHelper.isEmpty(userInfo)){
					userInfo = new UserInfo();
					BeanUtils.copyProperties(syncUser, userInfo);
					userInfo.setId(syncUser.getUserId());
					userInfo.setDelFlag(DeleteFlagType.DELETE);
					userInfoService.saveUser(userInfo);
				}else{
					userInfoService.delete(userInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("executeSyncUser exception, syncUserId={}, userId={}",
					new Object[] { syncUser.getId(), syncUser.getUserId() });
			success = false;
			throw new ServiceException("executeSyncUser exception");
		}finally{
			return success;
		}
		
	}

	@SuppressWarnings("finally")
	@Override
	@Transactional(value="loanTransactionManager", readOnly = false)
	public boolean executeSyncUserRoleOrg(SyncUserRoleOrg syncUserRoleOrg) {
		boolean success = true;
		String syncType = syncUserRoleOrg.getSyncType();
		try {
			if(SyncType.TYPE_DELETE.value.equals(syncType)){
				userInfoService.deleteUserRoleOrg(syncUserRoleOrg.getUserId());
			}else if(SyncType.TYPE_ADD.value.equals(syncType)){
				UserRoleOrg  userRoleOrg = new UserRoleOrg();
				BeanUtils.copyProperties(syncUserRoleOrg, userRoleOrg);
				userInfoService.insertUserRoleOrg(userRoleOrg);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("executeSyncUserRoleOrg exception, syncUserId={}, orgId={},roleId={}",
					new Object[] { syncUserRoleOrg.getUserId(),syncUserRoleOrg.getOrgId(),syncUserRoleOrg.getRoleId()});
			success = false;
			throw new ServiceException("executeSyncUserRoleOrg exception");
		}finally{
			return success;
		}
	}

	@SuppressWarnings("finally")
	@Override
	@Transactional(value="loanTransactionManager", readOnly = false)
	public boolean executeSyncOrg(SyncOrg syncOrg) {
		boolean success = true;
		try {
			if(SyncType.TYPE_ADD.value.equals(syncOrg.getSyncType())){
				OrgInfo orgInfo = orgInfoService.get(syncOrg.getOrgId());
				if(ObjectHelper.isEmpty(orgInfo)){
					orgInfo = new OrgInfo();
					BeanUtils.copyProperties(syncOrg, orgInfo);
					orgInfo.setId(syncOrg.getOrgId());
					orgInfoService.saveOrg(orgInfo);
				}
			}else if(SyncType.TYPE_MODIFY.value.equals(syncOrg.getSyncType())){
				OrgInfo orgInfo = orgInfoService.get(syncOrg.getOrgId());
				if(ObjectHelper.isEmpty(orgInfo)){
					orgInfo = new OrgInfo();
					BeanUtils.copyProperties(syncOrg, orgInfo);
					orgInfo.setId(syncOrg.getOrgId());
					orgInfoService.saveOrg(orgInfo);
				}else{
					BeanUtils.copyProperties(syncOrg, orgInfo);
					orgInfo.setId(syncOrg.getOrgId());
					orgInfoService.update(orgInfo);
				}
			}else if(SyncType.TYPE_DELETE.value.equals(syncOrg.getSyncType())){
				OrgInfo orgInfo = orgInfoService.get(syncOrg.getOrgId());
				if(ObjectHelper.isEmpty(orgInfo)){
					orgInfo = new OrgInfo();
					BeanUtils.copyProperties(syncOrg, orgInfo);
					orgInfo.setId(syncOrg.getOrgId());
					orgInfo.setDelFlag(DeleteFlagType.DELETE);
					orgInfoService.saveOrg(orgInfo);
				}else{
					orgInfoService.delete(orgInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("executeSyncOrg exception, syncOrgId={}, orgId={}",
					new Object[] { syncOrg.getId(), syncOrg.getOrgId() });
			success = false;
			throw new ServiceException("executeSyncOrg exception");
		}finally{
			return success;
		}
	}

	@SuppressWarnings("finally")
	@Override
	@Transactional(value="loanTransactionManager", readOnly = false)
	public boolean executeSyncRole(SyncRole syncRole) {
		boolean success = true;
		try {
			if(SyncType.TYPE_ADD.value.equals(syncRole.getSyncType())){
				RoleInfo roleInfo = roleInfoService.get(syncRole.getRoleId());
				if(ObjectHelper.isEmpty(roleInfo)){
					roleInfo = new RoleInfo();
					BeanUtils.copyProperties(syncRole, roleInfo);
					roleInfo.setId(syncRole.getRoleId());
					roleInfoService.saveRole(roleInfo);
				}
			}else if(SyncType.TYPE_MODIFY.value.equals(syncRole.getSyncType())){
				RoleInfo roleInfo = roleInfoService.get(syncRole.getRoleId());
				if(ObjectHelper.isEmpty(roleInfo)){
					roleInfo = new RoleInfo();
					BeanUtils.copyProperties(syncRole, roleInfo);
					roleInfo.setId(syncRole.getRoleId());
					roleInfoService.saveRole(roleInfo);
				}else{
					BeanUtils.copyProperties(syncRole, roleInfo);
					roleInfo.setId(syncRole.getRoleId());
					roleInfoService.update(roleInfo);
				}
			}else if(SyncType.TYPE_DELETE.value.equals(syncRole.getSyncType())){
				RoleInfo roleInfo = roleInfoService.get(syncRole.getRoleId());
				if(ObjectHelper.isEmpty(roleInfo)){
					roleInfo = new RoleInfo();
					BeanUtils.copyProperties(syncRole, roleInfo);
					roleInfo.setId(syncRole.getRoleId());
					roleInfo.setDelFlag(DeleteFlagType.DELETE);
					roleInfoService.saveRole(roleInfo);
				}else{
					roleInfoService.delete(roleInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("executeSyncRole exception, syncRoleId={}, RoleId={}",
					new Object[] { syncRole.getId(), syncRole.getRoleId() });
			success = false;
			throw new ServiceException("executeSyncRole exception");
		}finally{
			return success;
		}
	}
	
	/**
	 * 执行同步数据字典接口
	 * 2015年12月28日
	 * By 陈伟东
	 * @param syncDict
	 * @return
	 */
	@SuppressWarnings("finally")
	@Override
	@Transactional(value="loanTransactionManager", readOnly = false)
	public boolean executeSyncDict(SyncDict syncDict){
		boolean success = true;
		try {
			if(SyncType.TYPE_ADD.value.equals(syncDict.getSyncType())){
				DictInfo dictInfo = dictInfoManager.get(syncDict.getDictId());
				if(ObjectHelper.isEmpty(dictInfo)){
					dictInfo = new DictInfo();
					BeanUtils.copyProperties(syncDict, dictInfo);
					dictInfo.setId(syncDict.getDictId());
					dictInfoManager.saveDictInfo(dictInfo);
				}
			}else if(SyncType.TYPE_MODIFY.value.equals(syncDict.getSyncType())){
				DictInfo dictInfo = dictInfoManager.get(syncDict.getDictId());
				if(ObjectHelper.isEmpty(dictInfo)){
					dictInfo = new DictInfo();
					BeanUtils.copyProperties(syncDict, dictInfo);
					dictInfo.setId(syncDict.getDictId());
					dictInfoManager.saveDictInfo(dictInfo);
				}else{
					BeanUtils.copyProperties(syncDict, dictInfo);
					dictInfo.setId(syncDict.getDictId());
					dictInfoManager.update(dictInfo);
				}
			}else if(SyncType.TYPE_DELETE.value.equals(syncDict.getSyncType())){
				DictInfo dictInfo = dictInfoManager.get(syncDict.getDictId());
				if(ObjectHelper.isEmpty(dictInfo)){
					dictInfo = new DictInfo();
					BeanUtils.copyProperties(syncDict, dictInfo);
					dictInfo.setId(syncDict.getDictId());
					dictInfo.setDelFlag(DeleteFlagType.DELETE);
					dictInfoManager.saveDictInfo(dictInfo);
				}else{
					dictInfoManager.delete(dictInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("executeSyncDict exception, syncDictId={}, dictId={}",
					new Object[] { syncDict.getId(), syncDict.getDictId() });
			success = false;
			throw new ServiceException("executeSyncDict exception");
		}finally{
			return success;
		}
	}
	
}
