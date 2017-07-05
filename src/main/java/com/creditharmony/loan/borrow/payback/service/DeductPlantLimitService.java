package com.creditharmony.loan.borrow.payback.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cms.constants.YesNo;
import com.creditharmony.core.common.type.DeleteFlagType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.dict.util.DictUtils;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.type.LoanOrgType;
import com.creditharmony.loan.borrow.payback.dao.DeductPlantLimitDao;
import com.creditharmony.loan.borrow.payback.entity.DeductPlantLimit;

@Service("DeductPlantLimitService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class DeductPlantLimitService extends CoreManager<DeductPlantLimitDao,DeductPlantLimit>{
	

	/**
	 * 增加银行平台接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insert(DeductPlantLimit record) {
		if (record.getIsNewRecord()){
			record.preInsert();
			dao.insert(record);
		}else{
			record.preUpdate();
			dao.updateByPrimaryKey(record);
		}
	}

	/**
	 * 修改银行平台接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
    @Transactional(readOnly = false, value = "loanTransactionManager")
	public void update(DeductPlantLimit record) {
		dao.updateByPrimaryKey(record);
		
	}

	/**
	 * 删除接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
    @Transactional(readOnly = false, value = "loanTransactionManager")
	public void delete(DeductPlantLimit record) {
		dao.deleteByPrimaryKey(record.getId());
		
	}

	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param page 
	 * @param record
	 * @return string
	 */
    @Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<DeductPlantLimit> queryPage(Page<DeductPlantLimit> page, DeductPlantLimit record) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<DeductPlantLimit> pageList = (PageList<DeductPlantLimit>)dao.queryPage(pageBounds,record);
		Map<String,Dict> dictMap = DictCache.getInstance().getMap();

		for(DeductPlantLimit limit : pageList){
			limit.setPlantName(DictUtils.getLabel(dictMap,"jk_deduct_plat", limit.getPlantCode()));
			limit.setDeductType1(DictUtils.getLabel(dictMap,"com_deduct_type", limit.getDeductType1()));
			limit.setDeductType2(DictUtils.getLabel(dictMap,"com_deduct_type", limit.getDeductType2()));
			if(YesNo.YES.equals(limit.getMoneySymbol1())){
				limit.setMoneySymbol1("&gt;");
			}else if (YesNo.NO.equals(limit.getMoneySymbol1())){
				limit.setMoneySymbol1("&lt;=");
			}
			if(YesNo.YES.equals(limit.getMoneySymbol2())){
				limit.setMoneySymbol2("&gt;");
			}else if(YesNo.NO.equals(limit.getMoneySymbol2())){
				limit.setMoneySymbol2("&lt;=");
			}
		}
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 根据id查询  Create In 2016年4月20日 by 翁私
	 * @param record 
	 * @return DeductPlantLimit
	 */
    @Transactional(readOnly = true, value = "loanTransactionManager")
	public DeductPlantLimit selectByPrimaryKey(DeductPlantLimit record) {
		return dao.selectByPrimaryKey(record.getId());
	}
    
    /**
	 * 查询银行接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return list
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public PageList<DeductPlantLimit> findPlantList(DeductPlantLimit record) {
		return dao.findPlantList(record);
	}
	
	/**
	 * 查询该平台是否存在
	 * @param record
	 * @return
	 */
	public DeductPlantLimit querydataByPlant(DeductPlantLimit record) {
		 List<DeductPlantLimit> list = dao.queryPage(record);
		 if(ObjectHelper.isEmpty(list)){
			 return null;
		 }else{
			 return list.get(0);
		 }
	}

	/**
	 * 查询门店划扣申请限制列表
	 * @param page
	 * @param record
	 * @return
	 */
	public Page<DeductPlantLimit> queryOrgPage(Page<DeductPlantLimit> page,
			DeductPlantLimit record) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<DeductPlantLimit> pageList = (PageList<DeductPlantLimit>)dao.queryOrgPage(pageBounds,record);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 删除发起还款申请配置
	 * @param record
	 */
    @Transactional(readOnly = false, value = "loanTransactionManager")
	public void deleteOrg(DeductPlantLimit record) {
		 dao.deleteOrg(record);
	}

	/**
	 * 查询组织机构
	 * @param page
	 * @param org
	 * @return
	 */
    @Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<Org> findStoresPage(Page<Org> page, Org org) {
		org.setDelFlag(DeleteFlagType.NORMAL);
		org.setType(LoanOrgType.STORE.key);
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		PageList<Org> pageList = (PageList<Org>)dao.findOrgsByParams(org,pageBounds);
        PageUtil.convertPage(pageList, page);
        return page;
	}
	
	/**
	 * 查询组织机构
	 * @param page
	 * @param org
	 * @return
	 */
    @Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Org> findStores(Org org) {
		org.setDelFlag(DeleteFlagType.NORMAL);
		org.setType(LoanOrgType.STORE.key);
		List<Org> list = dao.findOrgsByParams(org);
        return list;
	}

	/**
	 * 保存门店发起还款申请配置
	 * @param record
	 */
    @Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveOrg(DeductPlantLimit record) {
		if (record.getIsNewRecord()){
			record.preInsert();
			record.setPutDate(new Date());
			dao.saveOrg(record);
		}else{
			record.preUpdate();
			record.setPutDate(new Date());
			dao.updateOrg(record);
		}
	}

	/**
	 * 查询该门店限制是否存在
	 * @param org
	 * @return
	 */
    @Transactional(readOnly = true, value = "loanTransactionManager")
	public List<DeductPlantLimit> queryOrgLimit(Org org) {
		return dao.queryOrgLimit(org);
	}

    /**
     * 门店划扣统计数据
     * @param page
     * @param record
     * @return
     */
	public Page<DeductPlantLimit> queryOrgStatisticsPage(
			Page<DeductPlantLimit> page, DeductPlantLimit record) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		PageList<DeductPlantLimit> pageList = (PageList<DeductPlantLimit>)dao.queryOrgStatisticsPage(pageBounds,record);
		PageUtil.convertPage(pageList, page);
		return page;
	}
}
