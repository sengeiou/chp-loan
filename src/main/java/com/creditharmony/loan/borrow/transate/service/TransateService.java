package com.creditharmony.loan.borrow.transate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.stores.dao.AreaPreDao;
import com.creditharmony.loan.borrow.transate.dao.TransateDao;
import com.creditharmony.loan.borrow.transate.entity.ex.TraParamsEx;
import com.creditharmony.loan.borrow.transate.entity.ex.TransateEx;

/**
 * 信借已办列表Service
 * @Class Name TransateService
 * @author lirui
 * @Create In 2015年12月2日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class TransateService extends CoreManager<TransateDao,TransateEx> {			
	 
	@Autowired
	private AreaPreDao areaPreDao;
	
	/**
	 * 获得所有已办列表
	 * 2015年12月2日
	 * By lirui
	 * @param traParams 查询参数
	 * @param page 分页参数
	 * @return 已办列表
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public Page<TransateEx> loanInfo(Page<TransateEx> page,TraParamsEx traParams) {	
		// 获得员工编号并封装到traParams参数中
		User user = UserUtils.getUser();
		try {
			traParams.setUserCode(user.getLoginName());					
		} catch (Exception e) {
			// TODO: NullPoint exception
		}
		String orgCode = traParams.getOrgCode();
		if(orgCode!=null&&!"".equals(orgCode)){
			traParams.setOrgId(orgCode.split(","));
		}
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<TransateEx> pageList = (PageList<TransateEx>)dao.getTransact(pageBounds, traParams);
		// 省市门店id转化成名字
		if (pageList.size() != 0) {
			for (int i = 0; i < pageList.size(); i++) {
				if (StringUtils.isNotEmpty(pageList.get(i).getProvinceId())) {
					String province = areaPreDao.getAreaName(pageList.get(i).getProvinceId());
					pageList.get(i).setProvinceId(province);					
				}
				if (StringUtils.isNotEmpty(pageList.get(i).getCityId())) {					
					String city = areaPreDao.getAreaName(pageList.get(i).getCityId());
					pageList.get(i).setCityId(city);
				}
				if (StringUtils.isNotEmpty(pageList.get(i).getStoreId())) {
					String store = areaPreDao.getOrgName(pageList.get(i).getStoreId());
					pageList.get(i).setStoreId(store);					
				}
			}			
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询数据是否是利率审核之后的数据
	 * 2016年4月13日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return loanCode
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String checkUrl(String loanCode) {
		return dao.checkUrl(loanCode);
	}
}
