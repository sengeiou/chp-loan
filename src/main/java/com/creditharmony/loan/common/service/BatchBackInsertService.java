package com.creditharmony.loan.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.IdGen;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.loan.common.dao.DeductUpdateDao;

/**
 * 批处理返回数据插入
 * @Class Name BatchBackInsertService
 * @author 张永生
 * @Create In 2016年5月20日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class BatchBackInsertService {
	
	@Autowired
	private DeductUpdateDao dao;
	
	//TODO 待hessian调用测试通过之后，删除此方法
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int batchInsert(List<LoanDeductEntity> retList) {
		int i = 0;
		for(LoanDeductEntity entity : retList){
			try{
				entity.setId(IdGen.uuid());
				dao.insert(entity);
			}catch(Exception e){
				e.printStackTrace();
			}
			i++;
		}
		return i;
	}
}
