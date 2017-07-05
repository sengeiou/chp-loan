package com.creditharmony.loan.borrow.contract.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.ContractAndPersonInfoDao;
import com.creditharmony.loan.borrow.contract.entity.ContractAndPersonInfo;


/**
 * 已制作合同Service 
 * @Class Name ContractPersonService
 * @create In 2016年3月4日
 * @author 尚军伟
 */

@Service("contractPersonService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class ContractAndPersonInfoService extends CoreManager<ContractAndPersonInfoDao,ContractAndPersonInfo>{
	
	@Autowired
	private ContractAndPersonInfoDao capDao;
	
	
	/**
	 * 查询已制作合同的借款人信息
	 * 2016年3月4日
	 * By 尚军伟
	 * @param 
	 * @return ContractAndPersonInfo
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<ContractAndPersonInfo> findContractAndPerson(ContractAndPersonInfo ctrPersonInfo,Page<ContractAndPersonInfo> page){
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		pageBounds.setCountBy("loan_code");
        PageList<ContractAndPersonInfo> pageList = (PageList<ContractAndPersonInfo>)dao.findList(ctrPersonInfo, pageBounds);
        PageUtil.convertPage(pageList, page);
		return page;
	}
	
	
	
	//查找借款申请状态
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List findLabel(){
		return capDao.findLabel();
	}
	
	/**
	 * 根据合同编号和文件名字查询文件docId
	 * 2016年5月29日
	 * By 尚军伟
	 * @param contractCode
	 * @param contractFileName
	 * @return
	 */
	public String findDocIdByContractCode(String contractCode,String contractFileName){
		return capDao.findDocIdByContractCode(contractCode, contractFileName);
	}

}
