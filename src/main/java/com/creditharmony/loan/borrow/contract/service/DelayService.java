package com.creditharmony.loan.borrow.contract.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.contract.dao.DelayDao;
import com.creditharmony.loan.borrow.contract.entity.DelayEntity;

@Service("delayService")
@Transactional(readOnly = true,value = "loanTransactionManager")
public class DelayService extends CoreManager<DelayDao,DelayEntity>{
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<DelayEntity> findDelayList(DelayEntity delayEntity,Page<DelayEntity> page){
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
        PageList<DelayEntity> pageList = (PageList<DelayEntity>)dao.findList(delayEntity, pageBounds);
        PageUtil.convertPage(pageList, page);
		return page;
	}
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public Page<DelayEntity> findAllDelayList(DelayEntity delayEntity,Page<DelayEntity> page){
        List<DelayEntity> delayAll = dao.findList(delayEntity);
		return page;
	}
	
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public List<DelayEntity> postPoneListExport(DelayEntity delayEntity){
		return dao.postPoneListExport(delayEntity);
	}
}
