package com.creditharmony.loan.borrow.delivery.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.delivery.dao.DeliveryApplyDao;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryParamsEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.DeliveryViewEx;
import com.creditharmony.loan.borrow.delivery.entity.ex.OrgView;
import com.creditharmony.loan.borrow.delivery.entity.ex.UserView;


/**
 * 交割申请列表Service
 * @Class Name DeliveryApplyService
 * @author lirui
 * @Create In 2015年12月3日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class DeliveryApplyService extends CoreManager<DeliveryApplyDao,DeliveryViewEx> {
	
	/**
	 * 待交割申请列表Service
	 * 2015年12月3日
	 * By lirui
	 * @param params 检索参数
	 * @param page 分页信息
	 * @return 待交割申请列表
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<DeliveryViewEx> deliveryApplyList(Page<DeliveryViewEx> page,DeliveryParamsEx params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("loan_survey_emp_id"); 
		PageList<DeliveryViewEx> pageList = (PageList<DeliveryViewEx>)dao.deliveryApplyList(pageBounds, params);
		PageUtil.convertPage(pageList, page);		
		return page;
	}
	
	
	/**
	 * 根据借款编码获得交割申请信息
	 * 2015年12月14日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 交割申请信息
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public DeliveryViewEx applyInfoByloanCode(String loanCode) {
		return dao.applyInfoByloanCode(loanCode).get(0);
	}
	
	/**
	 * 获得所有门店信息
	 * 2015年12月9日
	 * By lirui
	 * @param orgKey 门店枚举
	 * @return 门店组成的集合
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<OrgView> orgs() {	
		return dao.orgs(LoanOrgType.STORE.key);
	}
	
	/**
	 * 查询所有团队经理
	 * 2015年12月9日
	 * By lirui
	 * @param orgCode 门店ID
	 * @return 团队经理集合
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<UserView> teamManager(String orgCode) {
		Map<String,String> map  = new HashMap<String,String>();
		map.put("orgCode", orgCode);
		map.put("teamManagerKey", LoanRole.TEAM_MANAGER.id);
		
		return dao.teamManager(map);
	}
	
	/**
	 * 查询所有客户经理
	 * 2015年12月9日
	 * By lirui
	 * @param userCode 团队经理工号
	 * @return 客户经理集合
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<UserView> manager(String userCode) {
		Map<String,String> map  = new HashMap<String,String>();
		map.put("userCode", userCode);
		map.put("managerKey", LoanRole.FINANCING_MANAGER.id);
		return dao.manager(map);
	}
	
	/**
	 * 所有客服人员
	 * 2015年12月9日
	 * By lirui
	 * @param orgCode 门店ID
	 * @return 客服人员集合
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<UserView> service(String orgCode) {
		Map<String,String> map  = new HashMap<String,String>();
		map.put("orgCode", orgCode);
		map.put("serviceKey", LoanRole.CUSTOMER_SERVICE.id);
		return dao.service(map);
	}
	
	/**
	 * 门店下所有外访人员
	 * 2015年12月9日
	 * By lirui
	 * @param orgCode 门店ID
	 * @return 外访人员集合
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<UserView> outBound(String orgCode) {
		Map<String,String> map  = new HashMap<String,String>();
		map.put("orgCode", orgCode);
		map.put("outBoundKey", LoanRole.VISIT_PERSON.id);
		return dao.outBound(map);
	}
	
	/**
	 * 办理交割申请
	 * 2015年12月14日
	 * By lirui
	 * @param dv 前台传递参数
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void insertDelivery(DeliveryViewEx dv) {
		// 插入基础数据
		dv.preInsert();
		// 申请人为当前登录人,申请时间当前时间
		if (StringUtils.isNotEmpty(UserUtils.getUser().getUserCode())) {
			dv.setEntrustMan(UserUtils.getUser().getUserCode());
			dv.setDeliveryApplyTime(dv.getModifyTime());
		}
		dao.insertDelivery(dv);
	}
	
	/**
	 * 批量办理交割申请
	 * 2015年12月18日
	 * By lirui
	 * @param dv 封装交割信息
	 * @param loanCodes 要办理的借款编码
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void batchApply(DeliveryViewEx dv,String[] loanCodes) {
		// 如果存放借款编码的数组不为空,将几款编码遍历封装到借款申请信息的对象中,并插入交割记录
		if (loanCodes.length != 0) {
			for (int i = 0; i < loanCodes.length; i++) {
				dv.setLoanCode(loanCodes[i]);
				dv.setDeliveryResult("1");
				dv.setOrgCode(applyInfoByloanCode(loanCodes[i]).getOrgCode());
				dv.setTeamManagerCode(applyInfoByloanCode(loanCodes[i]).getTeamManagerCode());
				dv.setManagerCode(applyInfoByloanCode(loanCodes[i]).getManagerCode());
				dv.setCustomerServiceCode(applyInfoByloanCode(loanCodes[i]).getManagerCode());
				dv.setOutbountByCode(applyInfoByloanCode(loanCodes[i]).getCustomerServiceCode());
				dv.setDictLoanStatus(applyInfoByloanCode(loanCodes[i]).getDictLoanStatus());
				dv.setContractCode(applyInfoByloanCode(loanCodes[i]).getContractCode());
				dv.preInsert();
				// 申请人为当前登录人,申请时间当前时间
				if (StringUtils.isNotEmpty(UserUtils.getUser().getUserCode())) {
					dv.setEntrustMan(UserUtils.getUser().getUserCode());
					dv.setDeliveryApplyTime(dv.getModifyTime());
				}
				dao.insertDelivery(dv);
			}			
		}
	}
}
