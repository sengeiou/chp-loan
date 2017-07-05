package com.creditharmony.loan.car.common.dao;

import java.util.HashMap;
import java.util.List;




import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.carRefund.ex.RefundAuditEx;
import com.creditharmony.loan.car.common.entity.CarRefundInfo;
@LoanBatisDao
public interface CarRefundAuditDao extends CrudDao<CarRefundInfo>{
	
	/**
	 * 添加催收服务费退款记录
	 * 2016年3月4日
	 * By 陈伟东
	 * @param carRefundInfo
	 */
	public void insertCarRefundInfo(CarRefundInfo carRefundInfo);

	/**
	 * 查询退款审核列表
	 * 2016年2月29日
	 * By 蒋力
	 * @param carRefundInfo 退款审核列表信息
	 * @return 退款审核列表
	 */
  public  List <CarRefundInfo> selectCarRefundAuditList(PageBounds pageBounds,CarRefundInfo carRefundInfo);
  
  /**
	 * 退款审核列表金额总数统计
	 * 2016年4月19日
	 * By 蒋力
	 * @param carRefundInfo
	 * @return carRefundInfo
	 */
	public CarRefundInfo CarRefundAuditCountSum(CarRefundInfo carRefundInfo);
	
  /**
   * 更新退款审核结果
   * 2016年3月1日
   * By 蒋力
   * @param carRefundInfo 更新实体
   * @return 更新结果
   */
  public int updateCarRefundAudit(CarRefundInfo carRefundInfo);
  
	/**
	 * 查询退款列表
	 * 2016年3月1日
	 * By 蒋力
	 * @param carRefundInfo 退款列表信息
	 * @return 退款列表
	 */
  public  List <CarRefundInfo> selectCarRefundList(PageBounds pageBounds,CarRefundInfo carRefundInfo);

  
  /**
	 * 导出退款列表
	 * 2016年3月1日
	 * By 蒋力
	 * @param refundAuditEx 退款列表导出
	 * @return 退款列表
	 */
  public  List <RefundAuditEx> exportCarRefundList(RefundAuditEx refundAuditEx);

  /**
	 * 根据导入EXCEL中的退款ID查询退款信息，更新其退回状态
	 * 2016年3月2日
	 * By 蒋力
	 * @param id 退款ID
	 * @return 更新结果
	 */
	public int updateRefundReturnStatus(RefundAuditEx refundAuditEx);
	
	/**
	 * 通过退款ID获取借款编码
	 * 2016年3月2日
	 * By 蒋力
	 * @param id 退款ID
	 * @return 借款编码
	 */
	public String getLoanCodeByRefundId(String id);
	
	/**
	 *  查询要退款的数据  DeductReq  车借
	 * @author 蒋力
	 * @Create In 2016年3月2日
	 * @param paramMap
	 * @return DeductReq 要退款的数据 
	 */
	public DeductReq getDeductReq(HashMap<Object, Object> hashMap);

	/**
	 * 通过合同编号获取借款编码
	 * @param id 合同编号
	 * @return 借款编号 loan_code
	 */
	public String getLoanCodeByRefundContractCode(String id);

	/**
	 * 根据t_cj_service_charge_return ID改变回盘结果为处理中
	 * @param id
	 */
	public void editRefundReturnById(String id);
	
	
}