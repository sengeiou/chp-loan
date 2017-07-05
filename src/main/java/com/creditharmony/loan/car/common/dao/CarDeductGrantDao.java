package com.creditharmony.loan.car.common.dao;

import java.util.List;

import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.car.carGrant.ex.CarDeductCostRecoverEx;
import com.creditharmony.loan.car.carGrant.ex.CarPaybackTransferInfo;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesCheckApply;
/**
 * 获取划扣费用待追回列表
 * @Class Name CarDeductGrantDao
 * @author 李静辉
 * @Create In 2016年2月29日
 */
@LoanBatisDao
public interface CarDeductGrantDao extends CrudDao<CarDeductCostRecoverEx>{

	/**
	 * 根据条件 获取划扣费用待追回列表，同信借催收服务费待催收列表
	 * 2016年2月29日
	 * By 李静辉
	 * @param carDeductCostRecoverEx
	 * @param pageBounds
	 * @return
	 */
	public PageList<CarDeductCostRecoverEx> selectGuaranteeMoneyList(
			CarDeductCostRecoverEx carDeductCostRecoverEx, PageBounds pageBounds);
	
	/**
	 * 查询划扣待追回页面的详细信息，不带分页
	 * 2016年6月17日
	 * By 朱静越
	 * @param carDeductCostRecoverEx
	 * @return
	 */
	public List<CarDeductCostRecoverEx> selectGuaranteeMoneyList(CarDeductCostRecoverEx carDeductCostRecoverEx);

	/**
	 * 根据申请ID查询催收主表信息
	 * 2016年6月17日
	 * By 朱静越
	 * @param urgeApply
	 * @return
	 */
	public CarUrgeServicesCheckApply getUrgeApplyById(CarUrgeServicesCheckApply urgeApply);
	
	/**
	 * 查询查账账款列表
	 * 2016年6月17日
	 * By 朱静越
	 * @param carPaybackTransferInfo
	 * @return
	 */
	public List<CarPaybackTransferInfo> findUrgeTransfer(CarPaybackTransferInfo carPaybackTransferInfo);
	
	/**
	 * 插入催收服务费查账申请表
	 * 2016年6月17日
	 * By 朱静越
	 * @param carUrgeServicesCheckApply
	 */
	public void saveUrgeApply(CarUrgeServicesCheckApply carUrgeServicesCheckApply);
	
	/**
	 * 保存催收服务费汇款信息
	 * 2016年3月1日
	 * By zhangfeng
	 * @param carDeductCostRecoverEx
	 */
	public void savePayBackTransferInfo(CarDeductCostRecoverEx carDeductCostRecoverEx);
	
	/**
	 * 更新催收服务费查账申请表
	 * 2016年6月17日
	 * By 朱静越
	 * @param carUrgeServicesCheckApply
	 */
	public void updateUrgeApply(CarUrgeServicesCheckApply carUrgeServicesCheckApply);
	
	/**
	 * 修改的时候删除info
	 * 2016年6月17日
	 * By 朱静越
	 * @param info
	 */
	public void deletePaybackTransferInfo(CarPaybackTransferInfo info);
	
	/**
	 * 查询款项匹配列表
	 * 2016年6月20日
	 * By 朱静越
	 * @param carDeductCostRecoverEx
	 * @param pageBounds
	 * @return
	 */
	public PageList<CarDeductCostRecoverEx> selCheckInfo(CarDeductCostRecoverEx carDeductCostRecoverEx,PageBounds pageBounds);
	
	/**
	 * 查询不带分页的催收服务费查账匹配列表
	 * 2016年5月24日
	 * By 朱静越
	 * @param urgeServicesMoneyEx
	 * @return
	 */
	public List<CarDeductCostRecoverEx> selCheckInfo(CarDeductCostRecoverEx urgeServicesMoneyEx);
	
	/**
	 * 查询查账申请表,根据查账状态进行查询
	 * 2016年6月20日
	 * By 朱静越
	 * @param carUrgeServicesCheckApply
	 * @return
	 */
	public List<CarUrgeServicesCheckApply> findUrgeApplyList(CarUrgeServicesCheckApply carUrgeServicesCheckApply);
	
	/**
	 * 更新汇款上传列表数据状态
	 * 2016年6月20日
	 * By 朱静越
	 * @param paybackTransferInfo
	 */
	public void updateInfoStatus(CarPaybackTransferInfo paybackTransferInfo);
	
}
