package com.creditharmony.loan.car.common.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.common.entity.CarPaybackTransferDetail;
/**
 * 车借_还款转账信息记录明细表
 * @Class Name CarPaybackTransferDetailDao
 * @author 李静辉
 * @Create In 2016年3月9日
 */
@LoanBatisDao
public interface CarPaybackTransferDetailDao extends CrudDao<CarPaybackTransferDetail>{

    /**
     * 保存  申请查账凭条记录
     * 2016年3月4日
     * By 李静辉
     * @param carPaybackTransferDetailList
     */
	public void saveOrUpdate(
			@Param("list")List<CarPaybackTransferDetail> carPaybackTransferDetailList);
	
	/**
	 * 根据   关联转账信息表ID（车借_还款转账信息记录表） 获取查账凭条明细
	 * 2016年3月7日
	 * By 李静辉
	 * @param map
	 * @return
	 */
	public List<CarPaybackTransferDetail> findByTransferId(HashMap<String, String> map);
}