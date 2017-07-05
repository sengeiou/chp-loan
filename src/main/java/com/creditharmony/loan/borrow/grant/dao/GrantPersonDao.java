package com.creditharmony.loan.borrow.grant.dao;
import java.util.List;
import java.util.Map;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.users.entity.UserInfo;
/**
 * 放款人员信息查询
 * @Class Name GrantPersonDao
 * @author 朱静越
 * @Create In 2015年12月10日
 */
@LoanBatisDao
public interface GrantPersonDao extends CrudDao<UserInfo>{
	/**
	 * 查找放款人员,根据岗位编码查找
	 * 2015年12月11日
	 * By 朱静越
	 * @param postCode 岗位编码
	 * @return
	 */
	public List<UserInfo> selectUserList(String postCode);
	/**
	 * 根据用户名和用户编号进行查询
	 * 2015年12月11日
	 * By 朱静越
	 * @param userCode 员工编码
	 * @param name 员工姓名
	 * @return
	 */
	public List<UserInfo> selectByUser(Map<String, Object> user);
}