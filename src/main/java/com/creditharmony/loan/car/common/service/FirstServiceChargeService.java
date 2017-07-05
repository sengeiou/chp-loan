package com.creditharmony.loan.car.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.loan.car.common.dao.FirstServiceChargeMapper;
import com.creditharmony.loan.car.common.entity.FirstServiceCharge;

/**
 * 汇金首期服务费率管理-Service
 * 
 * @Class Name FirstServiceChargeService
 * @author 张庆安
 */
@Service
@Transactional(value = "loanTransactionManager", readOnly = true)
public class FirstServiceChargeService {

	//private static FirstServiceChargeMapper dao = SpringContextHolder.getBean(FirstServiceChargeMapper.class);
	@Autowired
	private FirstServiceChargeMapper dao;
	
	/**
	 * 检索首期服务费率信息
	 * 
	 * @return List<FirstServiceCharge>
	 */
	public List<FirstServiceCharge> findFirstServiceChargeList(FirstServiceCharge charge) {
		return dao.findFirstServiceChargeList(charge);
	}
	
	
	public FirstServiceCharge findFirstServiceChargeById(String id){
		return dao.selectByPrimaryKey(id);
	}
}
