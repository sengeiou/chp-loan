package com.creditharmony.loan.car.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.IdGen;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.loan.car.common.dao.CarDeductUpdateDao;

/**
 * 车借批处理返回数据插入
 * @Class Name CarBatchBackInsertService
 * @author 李虎城
 * @Create In 2016年12月20日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarBatchBackInsertService {
	
	@Autowired
	private CarDeductUpdateDao dao;
	
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
