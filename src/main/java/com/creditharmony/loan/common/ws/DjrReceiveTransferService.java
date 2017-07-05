package com.creditharmony.loan.common.ws;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.djrcreditor.DjrReceiveTransferBaseService;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveTransferResutInParam;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveTransferResutOutParam;
import com.creditharmony.loan.common.service.ReceiveTransferService;

/**
 * 大金融划扣完成后，接收划扣结果（集中划扣、非集中划扣）
 * @author yufei
 */
@Service
public class DjrReceiveTransferService extends DjrReceiveTransferBaseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ReceiveTransferService receiveTransferService;
	
	@Override
	public DjrReceiveTransferResutOutParam doExec(DjrReceiveTransferResutInParam paramBean) {
		logger.info("----大金融返回划扣结果"+new Date()+"，划扣流水："+paramBean.getApplyid()
				+";合同编号："+paramBean.getContractCode()
				+";划扣类型："+paramBean.getType()
				+";划扣结果："+paramBean.getResult()
				+";划扣金额："+paramBean.getMoney()
				+";备注："+paramBean.getRemarks());
		DjrReceiveTransferResutOutParam param = new DjrReceiveTransferResutOutParam();
		//更新划扣申请、蓝补、增加蓝补对账单信息
		boolean result = receiveTransferService.updateDeductData(paramBean);
		if(result){
			param.setRetCode(ReturnConstant.SUCCESS);
			param.setRetMsg("成功");
		}else{
			param.setRetCode(ReturnConstant.FAIL);
			param.setRetMsg("失败");
		}
		return param;
	}

}
