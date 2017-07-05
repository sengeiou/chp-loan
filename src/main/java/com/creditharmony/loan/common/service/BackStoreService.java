package com.creditharmony.loan.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.loan.common.dao.ApproveDictDao;
import com.creditharmony.loan.common.dao.AuditBackDao;
import com.creditharmony.loan.common.entity.AuditBack;
import com.creditharmony.loan.common.entity.ex.ApproveDictEx;

/**
 * 审批退回门店
 * @Class Name BackStoreService
 * @author 赖敏
 * @Create In 2015年12月28日
 */
@Service
public class BackStoreService {
	
	@Autowired
	private AuditBackDao auditBackDao;
	@Autowired
	private ApproveDictDao dictDao;
	
	/**
	 * 获取外访清单
	 * 2015年12月25日
	 * By 赖敏
	 * @return 回退清单
	 */
	public List<ApproveDictEx> getBackStoreDicts(){
     	return dictDao.getDictsByType("jk_backloan_reason");
	
    }
	
	/**
     * 根据历史ID获取回退清单
     * 2015年12月26日
     * By 赖敏
     * @param relationId 关联ID(变更历史表)
     * @return 历史回退清单
     */
	public AuditBack getById(String relationId){
		return auditBackDao.getById(relationId);
	}
	
}
