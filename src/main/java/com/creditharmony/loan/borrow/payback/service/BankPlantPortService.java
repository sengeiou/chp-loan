package com.creditharmony.loan.borrow.payback.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.payback.dao.BankPlantPortDao;
import com.creditharmony.loan.borrow.payback.entity.BankPlantPort;

@Service("BankPlantPortService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class BankPlantPortService extends CoreManager<BankPlantPortDao,BankPlantPort>{
	

	/**
	 * 增加银行平台接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insert(BankPlantPort record) {
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
	public void update(BankPlantPort record) {
		dao.updateByPrimaryKey(record);
		
	}

	/**
	 * 删除接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return string
	 */
    @Transactional(readOnly = false, value = "loanTransactionManager")
	public void delete(BankPlantPort record) {
		dao.deleteByPrimaryKey(record);
		
	}

	/**
	 * 分页查询接口  Create In 2016年4月20日 by 翁私
	 * @param page 
	 * @param record
	 * @return string
	 */
    @Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<BankPlantPort> queryPage(Page<BankPlantPort> page, BankPlantPort record) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<BankPlantPort> pageList = (PageList<BankPlantPort>)dao.queryPage(pageBounds,record);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 根据id查询  Create In 2016年4月20日 by 翁私
	 * @param record 
	 * @return BankPlantPort
	 */
    @Transactional(readOnly = true, value = "loanTransactionManager")
	public BankPlantPort selectByPrimaryKey(BankPlantPort record) {
		return dao.selectByPrimaryKey(record);
	}
    
    /**
	 * 查询银行接口  Create In 2016年4月20日 by 翁私
	 * @param record
	 * @return list
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public PageList<BankPlantPort> findPlantList(BankPlantPort record) {
		return dao.findPlantList(record);
	}
	
	/**
	 *	查询除对象数据外的数据
	 * @param record 
	 * @return BankPlantPort
	 */
    @Transactional(readOnly = true, value = "loanTransactionManager")
	public BankPlantPort selectByPrimaryKeyNotIn(BankPlantPort record) {
		return dao.selectByPrimaryKeyNotIn(record);
	}
    
}
