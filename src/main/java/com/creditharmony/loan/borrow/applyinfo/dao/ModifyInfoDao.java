package com.creditharmony.loan.borrow.applyinfo.dao;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.applyinfo.entity.ModifyInfo;

/**
 * 修改信息详细记录
 * @Class Name ModifyInfoDao
 * @author lirui
 * @Create In 2016年2月18日
 */
@LoanBatisDao
public interface ModifyInfoDao extends CrudDao<ModifyInfo> {
	
}
