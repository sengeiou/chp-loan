/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.serviceImageService.java
 * @Create By 张灏
 * @Create In 2016年3月15日 下午8:25:26
 */
package com.creditharmony.loan.car.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.loan.car.common.dao.CarChangerInfoDao;
import com.creditharmony.loan.car.common.dao.CarCustomerDao;
import com.creditharmony.loan.car.common.dao.CarLoanCoborrowerDao;
import com.creditharmony.loan.car.common.dao.OperateRecordMapper;
import com.creditharmony.loan.car.common.entity.CarChangerInfo;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.OperateRecord;

/**
 * 操作记录Service
 * 
 * @Class Name OperateRecordService
 * @author zhangqinan
 * @Create In 2016年9月28日
 */
@Service
public class OperateRecordService {

	@Autowired
	private OperateRecordMapper operateRecordMapper;

	
	// 插入操作记录
	@Transactional(readOnly = false, value = "loanTransactionManager")
    public  void insertRecord(OperateRecord record){
		operateRecordMapper.insert(record);
    }
    
	
}
