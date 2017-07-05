package com.creditharmony.loan.users.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.users.dao.RoleInfoDao;
import com.creditharmony.loan.users.entity.RoleInfo;

/**
 * 角色管理Service
 * @Class Name RoleInfoService
 * @author 陈伟东
 * @Create In 2015年12月25日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class RoleInfoService extends CoreManager<RoleInfoDao, RoleInfo> {

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public RoleInfo getRole(String id){
		return dao.get(id);
	}

	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveRole(RoleInfo roleInfo){
		dao.insert(roleInfo);
	}

	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void update(RoleInfo roleInfo){
		dao.update(roleInfo);
	}
}
