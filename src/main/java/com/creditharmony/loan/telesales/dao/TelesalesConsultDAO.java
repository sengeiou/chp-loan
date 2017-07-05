package com.creditharmony.loan.telesales.dao;

import java.util.List;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.telesales.entity.TelesalesConsultInfo;

/**
 * 电销客户咨询Dao层
 * @Class Name TelesalesConsultDAO
 * @author 周怀富
 * @Create In 2016年3月11日
 */
@LoanBatisDao
public interface TelesalesConsultDAO extends CrudDao<TelesalesConsultInfo> {

	// 保存客户沟通记录
	public void insertConsultRecord(TelesalesConsultInfo telesalesCustomer);

	// 更新取单信息
	public void updateConsultOrderStatus(TelesalesConsultInfo telesalesCustomer);

	// 根据ID查询客户咨询信息
	public TelesalesConsultInfo findSingleTelesalesConsultInfo(String id);

	// 超过7天未取单的将状态改为门店放弃
	public void updateStoreGiveUpStatus();
	
	// 根据客户编号查询客户咨询列表
	public List<TelesalesConsultInfo> findTelesalesConsultList(TelesalesConsultInfo telesalesCustomer);
	
	// 根据组织机构ID、角色ID 查询出用户ID
	public TelesalesConsultInfo findUserIdByOrgIdAndRoleId(TelesalesConsultInfo telesalesCustomer);
}