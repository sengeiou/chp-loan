package com.creditharmony.loan.borrow.grant.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.dao.GrantPersonDao;
import com.creditharmony.loan.users.entity.UserInfo;
/**
 * 用来对放款人员进行操作
 * @Class Name DisGrantPersonService
 * @author 朱静越
 * @Create In 2015年12月4日
 */
@Service("disGrantPersonService")
public class DisGrantPersonService extends CoreManager<GrantPersonDao, UserInfo>{
	
	/**
	 * 根据岗位编码查询放款人员，
	 * 2015年12月10日
	 * By 朱静越
	 * @param postCode 岗位编码
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<UserInfo> selectGrantPerson(String postCode){
		return dao.selectUserList(postCode);
	}
	
	/**
	 * 根据员工编号和员工姓名进行检索，姓名支持模糊查询
	 * 2015年12月11日
	 * By 朱静越
	 * @param userCode 员工编码
	 * @param name 员工姓名
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<UserInfo> selectGrantPersonByUser(String userCode,String name,String postCode){
		Map<String, Object> userMap=new HashMap<String, Object>();
		userMap.put("userCode", userCode);
		userMap.put("name", name);
		userMap.put("postCode", postCode);
		return dao.selectByUser(userMap);
	}
}
