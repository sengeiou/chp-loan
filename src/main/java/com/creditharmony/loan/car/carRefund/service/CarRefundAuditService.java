package com.creditharmony.loan.car.carRefund.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.carRefund.ex.RefundAuditEx;
import com.creditharmony.loan.car.common.consts.CarDeductWays;
import com.creditharmony.loan.car.common.consts.CarRefundStatus;
import com.creditharmony.loan.car.common.dao.CarRefundAuditDao;
import com.creditharmony.loan.car.common.entity.CarRefundInfo;

import filenet.vw.idm.panagon.com.fnnfo.idmCancelCheckout;

/**
 * 
 * @Class Name CarGrantSureService
 * @author 蒋力
 * @Create In 2016年2月29日
 * 退款审批service
 */
@Service("carRefundAuditService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarRefundAuditService extends CoreManager<CarRefundAuditDao, CarRefundInfo>{
	/**
	 * 退款审核列表查询
	 * 2016年2月29日
	 * By 蒋力
	 * @param page
	 * @param CarRefundInfo
	 * @return 分页
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarRefundInfo> selectCarRefundAuditList(Page<CarRefundInfo> page,CarRefundInfo carRefundInfo){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<CarRefundInfo> pageList = (PageList<CarRefundInfo>)dao.selectCarRefundAuditList(pageBounds, carRefundInfo);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 退款审核列表金额总数统计
	 * 2016年4月19日
	 * By 蒋力
	 * @param carRefundInfo
	 * @return carRefundInfo
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarRefundInfo CarRefundAuditCountSum(CarRefundInfo carRefundInfo){
		CarRefundInfo result = dao.CarRefundAuditCountSum(carRefundInfo);
		return result;
	}
	
	/**
	 * 更新退款审核结果
	 * 2016年3月1日
	 * By 蒋力
	 * @param urgeMoney 更新实体
	 * @return 更新结果
	 */
	@Transactional(readOnly = false, value = "transactionManager")
	public int updateCarRefundAudit(CarRefundInfo carRefundInfo){
		carRefundInfo.preUpdate();
		return dao.updateCarRefundAudit(carRefundInfo);
	}
	
	/**
	 * 退款列表查询
	 * 2016年3月1日
	 * By 蒋力
	 * @param page
	 * @param CarRefundInfo
	 * @return 分页
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarRefundInfo> selectCarRefundList(Page<CarRefundInfo> page,CarRefundInfo carRefundInfo){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<CarRefundInfo> pageList = (PageList<CarRefundInfo>)dao.selectCarRefundList(pageBounds, carRefundInfo);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 导出退款信息列表
	 * 2016年3月1日
	 * By 蒋力
	 * @param refundAuditEx
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<RefundAuditEx> exportCarRefundList(RefundAuditEx refundAuditEx){
		return dao.exportCarRefundList(refundAuditEx);
	}

	/**
	 * 根据导入EXCEL中的退款ID查询退款信息，更新其退回状态
	 * 2016年3月2日
	 * By 蒋力
	 * @param id 退款ID
	 * @return 更新结果
	 */
	@Transactional(readOnly = false, value = "transactionManager")
	public int updateRefundReturnStatus(RefundAuditEx refundAuditEx)
	{
		return dao.updateRefundReturnStatus(refundAuditEx);
	}
	
	/**
	 * 通过合同编号获取借款编码
	 * 2016年3月2日
	 * By 蒋力
	 * @param id 退款ID
	 * @return 借款编码
	 */
	@Transactional(readOnly = true, value = "transactionManager")
	public String getLoanCodeByContractCode(String id)
	{
		return dao.getLoanCodeByRefundContractCode(id);
	}
	/**
	 * 通过退款ID获取借款编码
	 * 2016年3月2日
	 * By 蒋力
	 * @param id 退款ID
	 * @return 借款编码
	 */
	@Transactional(readOnly = true, value = "transactionManager")
	public String getLoanCodeByRefundId(String id)
	{
		return dao.getLoanCodeByRefundId(id);
	}
	
	/**
	 * 查询要退款的数据  DeductReq
	 * @author 蒋力
	 * @Create In 2016年3月2日
	 * @param paramMap
	 * @return 退款数据 
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public DeductReq getDeductReq(HashMap<Object, Object> hashMap,String rule) {
	    //　取得规则
		DeductReq deductReq = dao.getDeductReq(hashMap);
		if(deductReq != null){
			// 设置划扣标志
			deductReq.setDeductFlag(DeductFlagType.PAY.getCode());
			// 设置划扣规则
			deductReq.setRule(rule);
			//  系统处理ID
			deductReq.setSysId(CarDeductWays.CJ_03.getCode());
		}
		return deductReq;
	}
	
	/**
	 * 线上退款处理-回调方法
	 * 2016年3月8日
	 * By 蒋力
	 * @param LoanDeductEntity 划扣后返回数据
	 * 
	 * @return
	 */
	public void refundDealBack(LoanDeductEntity loanDeductEntity){
		String businessId = loanDeductEntity.getBusinessId();//businessID，DeductReq传入的值返回
		String deductFailMoney = loanDeductEntity.getDeductFailMoney();//划扣失败金额，为0则划扣成功
		RefundAuditEx ex = new RefundAuditEx();
		if(!"".equals(deductFailMoney)&&deductFailMoney!=null)
		{
			double failAmount = Double.parseDouble(deductFailMoney);
			if(failAmount == 0){
				//退款成功，退款信息的退回状态修改为已退回,审核状态修改为已处理
				ex.setContractCode(businessId);
				ex.setReturnStatus(CarRefundStatus.CAR_RETURN_STATUS_Y.getCode());
				ex.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_P.getCode());
				dao.updateRefundReturnStatus(ex);
			}else{
				//退款失败，退款信息的回盘状态修改为待处理
				ex.setContractCode(businessId);
				ex.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_Y.getCode());
				dao.updateRefundReturnStatus(ex);
			}
		}else{
			//退款失败，退款信息的回盘状态修改为待处理
			ex.setId(businessId);
			ex.setAuditStatus(CarRefundStatus.CAR_AUDIT_STATUS_Y.getCode());
			dao.updateRefundReturnStatus(ex);
		}
	}

	/**
	 * 根据t_cj_service_charge_return ID改变回盘结果为处理中
	 * @param string
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void editRefundReturnById(String id) {
		dao.editRefundReturnById(id);
	}
}