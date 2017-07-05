package com.creditharmony.loan.borrow.transate.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.stores.dao.AreaPreDao;
import com.creditharmony.loan.borrow.transate.dao.LoanInfoDao;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanParamsEx;
import com.creditharmony.loan.borrow.transate.entity.ex.TransateEx;

/**
 * 信借数据查询列表
 * @Class Name LoanInfoService
 * @author lirui
 * @Create In 2015年12月2日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class LoanInfoService extends CoreManager<LoanInfoDao,TransateEx> {
	
	@Autowired
	private AreaPreDao areaPreDao;
	
	@Autowired
	private ApplyLoanInfoDao ado;
	
	/**
	 * 获取信借数据列表
	 * 2015年12月2日
	 * By lirui
	 * @param params 查询参数
	 * @param page 分页参数
	 * @return 信借数据列表集合
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<TransateEx> loanInfo(Page<TransateEx> page,LoanParamsEx params) {		
		/*User user = UserUtils.getUser();*/
	/*	if (!ObjectHelper.isEmpty(user.getRoleList())) {
			for (int i = 0; i < user.getRoleList().size(); i++) {
				Role role = user.getRoleList().get(i);
				if (StringUtils.isNotEmpty(role.getId())) {
					String roleId = role.getId();
					// 门店人员只能看到自己门店的数据,非门店人员可看到全部数据	
					if (LoanRole.CITY_MANAGER.id.equals(roleId) || 
							LoanRole.STORE_MANAGER.id.equals(roleId) ||
							LoanRole.STORE_ASSISTANT.id.equals(roleId) || 
							LoanRole.CUSTOMER_SERVICE.id.equals(roleId) ||
							LoanRole.VISIT_PERSON.id.equals(roleId) ||
							LoanRole.TEAM_MANAGER.id.equals(roleId) ||
							LoanRole.FINANCING_MANAGER.id.equals(roleId)) {
						params.setUserCode(user.getUserCode());
						if (StringUtils.isNotEmpty(user.getUserCode())) {
							break;
						}
					}else {
						params.setUserCode(null);
					}	
				}				
			}
		}*/
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<TransateEx> pageList = (PageList<TransateEx>)dao.loanInfo(pageBounds, params);
//		// 省市门店id转化成名字
//		if (pageList.size() != 0) {
//			for (int i = 0; i < pageList.size(); i++) {
//				if (StringUtils.isNotEmpty(pageList.get(i).getProvinceId())) {
//					String province = areaPreDao.getAreaName(pageList.get(i).getProvinceId());
//					pageList.get(i).setProvinceId(province);					
//				}
//				if (StringUtils.isNotEmpty(pageList.get(i).getCityId())) {					
//					String city = areaPreDao.getAreaName(pageList.get(i).getCityId());
//					pageList.get(i).setCityId(city);
//				}
//				if (StringUtils.isNotEmpty(pageList.get(i).getStoreId())) {
//					String store = areaPreDao.getOrgName(pageList.get(i).getStoreId());
//					pageList.get(i).setStoreId(store);					
//				}
//				if (StringUtils.isNotEmpty(pageList.get(i).getUserName())) {
//					String manageName = areaPreDao.getUserName(pageList.get(i).getUserName());
//					pageList.get(i).setUserName(manageName);
//				}
//			/*	if (StringUtils.isNotEmpty(pageList.get(i).getTeamManagerName())) {
//					String teamManageName = areaPreDao.getUserName(pageList.get(i).getTeamManagerName());
//					pageList.get(i).setTeamManagerName(teamManageName);
//				}*/
//				// 获得共借人
//				if (StringUtils.isNotEmpty(pageList.get(i).getLoanCode())) {
//					List<String> coboName = dao.getCobos(pageList.get(i).getLoanCode());
//					StringBuffer sbf = new StringBuffer();
//					for (String str : coboName) {
//						if (sbf.length() == 0) {
//							sbf.append(str);
//						} else {
//							sbf.append("，"+str);
//						}
//					}
//					if (sbf.length() == 0) {
//						sbf.append("");
//					}
//					pageList.get(i).setCoroName(sbf.toString());
//				}
//			}			
//		}
		PageUtil.convertPage(pageList, page);		
		return page;
	}
	
	/**
	 * 获得产品列表
	 * 2015年12月2日
	 * By lirui
	 * @return 产品列表集合
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<String> products() {
		return dao.products();
	}
	
	/**
	 * 根据借款编码获得借款状态
	 * 2016年3月18日
	 * By shangjunwei
	 * @param loanCode 借款编码
	 * @return 借款状态
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public LoanInfo findStatusByLoanCode(String loanCode){
		return ado.findStatusByLoanCode(loanCode);
	}
	
	/**
	 * 根据借款编码获得进件时间
	 * 2016年3月18日
	 * By shangjunwei
	 * @param loanCode 借款编码
	 * @return 借款状态
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String findCustomerIntoTime(String loanCode){
		return ado.getCustomerIntoTimeByLoanCode(loanCode);
	}
	
	/**查询借款状态*/
	public List<Map<String,String>> getStatus(String applyId){
		return ado.getStatus(applyId);
	}
	
}
