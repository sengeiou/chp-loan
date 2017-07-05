package com.creditharmony.loan.common.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.djrcreditor.DjrReceiveReturnBaseService;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveReturnInParam;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveReturnOutParam;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.FrozenReason;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.zcj.service.ZcjService;
/**
 * 1.03 接收大金融退回/撤销
 * 
 * @author wenlongliu
 *
 */
@Service
public class DjrReceiveReturnService extends DjrReceiveReturnBaseService {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ContractService contractService;
	@Autowired
	ApplyLoanInfoService applyLoanInfoService;
	@Autowired
	private ZcjService ZcjService;
	
	@SuppressWarnings("finally")
	@Override
	public DjrReceiveReturnOutParam doExec(DjrReceiveReturnInParam paramBean) {
		logger.info("++++++++DjrReceiveReturnService.doExec大金融退回撤销参数："+JSONObject.toJSONString(paramBean));
		DjrReceiveReturnOutParam param = new DjrReceiveReturnOutParam();
		try
		{
			Contract contract = contractService.findByContractCode(paramBean.getContractCode());
			if(contract==null) throw new Exception("合同号没找到");
			if("2".equals(paramBean.getType())){ // 大金融拒绝,大金融需要将拒绝的原因推送过来，记录到历史中。
				logger.info("大金融拒绝发送过来的拒绝的原因为："+ paramBean.getRemarks());
				ZcjService.djrRejectDeal(contract, paramBean.getRemarks());
				logger.info("大金融拒绝处理完成，合同编号为："+ paramBean.getContractCode());
			}else if("1".equals(paramBean.getType())){ // 大金融退回,退回到合同审核的时候，需要取到 frozen_reason存入到合同的退回原因中，显示在列表中
				// 根据合同编号查找冻结原因
				String bigFinanceBackReason = null;
				LoanInfo loanInfo = applyLoanInfoService.selectByLoanCode(contract.getLoanCode());
				if (ObjectHelper.isNotEmpty(loanInfo) && YESNO.NO.getCode().equals(loanInfo.getIssplit())) {
					if (FrozenReason.OTHER.getCode().equals(loanInfo.getFrozenCode())) {
						bigFinanceBackReason = "其他："+loanInfo.getFrozenReason();
					}else {
						bigFinanceBackReason = loanInfo.getFrozenReason();
					}
					logger.info("大金融退回到合同审核处理开始，合同编号为："+contract.getContractCode());
					ZcjService.djrReturnDeal(contract, bigFinanceBackReason);
					logger.info("大金融退回到合同审核处理完成，合同编号为："+contract.getContractCode());
					param.setRetCode(ReturnConstant.SUCCESS);
					param.setRetMsg("成功");
					logger.info("接受大金融退回/撤销处理，成功。合同编号："+paramBean.getContractCode());
				}else {
					logger.info("已经拆分过的大金融数据不能进行退回，合同编号为："+contract.getContractCode());
					param.setRetCode(ReturnConstant.ERROR);
					param.setRetMsg("拆分过的单子不能进行合同审核退回");
				}
			}
		}catch(Exception ex){
			logger.error("接收大金融退回/撤销，发生异常",ex);
			param.setRetCode(ReturnConstant.ERROR);
			param.setRetMsg(ex.getMessage());
		}finally{
			return param;
		}
		
	}

}


