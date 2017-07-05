package com.creditharmony.loan.borrow.trusteeship.service;

import org.springframework.stereotype.Service;

import com.creditharmony.adapter.service.jzh.JzhWtrechargeNotifyBaseService;
import com.creditharmony.adapter.service.jzh.bean.JzhWtrechargeNotifyInBean;
import com.creditharmony.adapter.service.jzh.bean.JzhWtrechargeNotifyOutBean;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.loan.borrow.payback.entity.ex.TrusteeImportEx;

@Service
public class JzhWtrechargeNotifyService extends JzhWtrechargeNotifyBaseService{

	@Override
	public JzhWtrechargeNotifyOutBean doExec(JzhWtrechargeNotifyInBean paramBean) {
		// 更新委托充值结果
		if(paramBean.isRespState()){
//			// 委托充值成功，更新数据库
//			TrusteeImportEx trusteeImport = new TrusteeImportEx();
//			// 申请id
//			trusteeImport.setPaybackApplyId(paybackApply.getId());
//			// 合同编号
//			trusteeImport.setContractCode(paybackApply.getContractCode());
//			// 委托充值金额
//			trusteeImport.setTrustAmount(paybackApply.getApplyAmount().toString());
//
//			if ("0000".equals(outInfo.getRetCode())) {
//
//				// 委托充值结果
//				trusteeImport.setReturnCode(CounterofferResult.PAYMENT_SUCCEED
//						.getCode());
//				// 委托充值失败原因
//				trusteeImport.setReturnMsg("");
//				dao.updateTrustRecharge(trusteeImport);
//			} 
		}else{
			// 委托充值失败
		}
		
		// 返回接口通知
		String param = paramBean.getParam();
		System.out.println(param);
		JzhWtrechargeNotifyOutBean jzhWtrechargeNotifyOutBean = new JzhWtrechargeNotifyOutBean();
		jzhWtrechargeNotifyOutBean.setRetCode("0000");
		return jzhWtrechargeNotifyOutBean;
	}
}
