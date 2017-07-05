package com.creditharmony.loan.common.ws;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.djrcreditor.DjrReceiveRefundInfoBaseService;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveRefundInfoInParam;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveRefundInfoOutParam;
import com.creditharmony.loan.common.service.ReceiveRefundInfoService;

/**
 * 大金融退款完成后，接收退款结果
 * @author yufei
 */
@Service
public class DjrReceiveRefundInfoService extends DjrReceiveRefundInfoBaseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ReceiveRefundInfoService receiveRefundInfoService;
	
	@Override
	public DjrReceiveRefundInfoOutParam doExec(DjrReceiveRefundInfoInParam paramBean) {
		logger.info("--------接收到的大金融退款结果"+new Date()+"，合同编号是："+paramBean.getContractCode()
			+";退款类型："+paramBean.getType()
			+";退款金额："+paramBean.getMoney()
			+";退款结果："+paramBean.getResult()
			+";交易流水号："+paramBean.getOrderId()
			+";备注："+paramBean.getRemarks()+"-----------");
		DjrReceiveRefundInfoOutParam param = new DjrReceiveRefundInfoOutParam();
		//更新划扣申请、蓝补、增加蓝补对账单信息
		boolean result = receiveRefundInfoService.updateRefundInfo(paramBean);
		if(result){
			param.setRetCode(ReturnConstant.SUCCESS);
			param.setRetMsg("chp处理大金融返回退款结果成功");
			logger.info("chp处理大金融返回退款结果成功");
		}else{
			param.setRetCode(ReturnConstant.FAIL);
			param.setRetMsg("chp处理大金融返回退款结果失败");
			logger.info("chp处理大金融返回退款结果失败");
		}
		return param;
	}

}
