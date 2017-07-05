/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.webUserInfoController.java
 * @Create By 王彬彬
 * @Create In 2015年12月28日 下午11:17:35
 */
package com.creditharmony.loan.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.entity.UserRoleOrg;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.service.UserRoleOrgManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;

/**
 * @Class Name UserInfoController
 * @author 王彬彬
 * @Create In 2015年12月28日
 */
@Controller
@RequestMapping(value = "${adminPath}/common/userinfo")
public class UserInfoController extends BaseController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
    private UserManager userManager;
	@Autowired
	private UserRoleOrgManager userRoleOrgManager;

	@ResponseBody
	@RequestMapping(value = "userdata")
	public List<UserInfo> getUser(HttpServletRequest request,
			HttpServletResponse response, String userCode, String userName) {
		UserInfo user = new UserInfo();
		user.setUserCode(userCode);
		if (StringUtils.isNotBlank(userCode)) {
			user.setUserCode(userCode);
		} else if (StringUtils.isNotBlank(userName)) {
			user.setName(userName);
		}
		Page<UserInfo> userPage = userInfoService.findUserInfoPage(
				new Page<UserInfo>(request, response), user);

		return userPage.getList();
	}
	
	/**
	 * 获取机构下角色用户
	 * 2016年1月20日
	 * By 王彬彬
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRoleUser")
	public List<UserInfo>  getRoleUser()
	{
		Map<String,String> userMap = new HashMap<String,String>();
		String departmentId = UserUtils.getUser().getDepartment().getId();
		
		userMap.put("departmentId", departmentId);//部门id
		userMap.put("roleId", LoanRole.FINANCING_MANAGER.id);//团队经理
		
		return userInfoService.getRoleUser(userMap);
	}

	@ResponseBody
	@RequestMapping(value = "getCustomerManagerUser", method=RequestMethod.GET)
	public List<User> getCustomerManagerUser(String parentOrgId, String teamManagerId) {
		
		List<User> userList = new ArrayList<User>();
		if(StringUtils.isEmpty(parentOrgId) || StringUtils.isEmpty(teamManagerId)){
			return userList;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userId", teamManagerId);
		params.put("roleId", LoanRole.TEAM_MANAGER.id);
		List<UserRoleOrg> uroList = userRoleOrgManager.findList(params);
		for(UserRoleOrg uro : uroList){
			userList.addAll(userManager.findUserByRoleAndParentOrg(LoanRole.FINANCING_MANAGER.id, parentOrgId, uro.getOrgId()));
		}
		return userList;
	}
	
	@ResponseBody
	@RequestMapping(value = "findCustomerManagerUser", method=RequestMethod.GET)
	public List<User> getCustomerManagerUser(String parentOrgId) {
		
		List<User> userList = new ArrayList<User>();
		if(StringUtils.isEmpty(parentOrgId)){
			return userList;
		}
		userList = userManager.findUserByRoleAndParentOrg(LoanRole.FINANCING_MANAGER.id, parentOrgId);
		return userList;
	}
}
