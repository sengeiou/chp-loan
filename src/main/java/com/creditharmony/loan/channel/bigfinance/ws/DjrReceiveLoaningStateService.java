package com.creditharmony.loan.channel.bigfinance.ws;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.service.djrcreditor.DjrReceiveLoaningStateBaseService;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveLoaningStateInParam;
import com.creditharmony.adapter.service.djrcreditor.bean.DjrReceiveLoaningStateOutParam;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.common.service.HistoryService;

@Service
public class DjrReceiveLoaningStateService extends DjrReceiveLoaningStateBaseService{
	private Logger logger = LoggerFactory.getLogger(DjrReceiveLoaningStateService.class);
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private HistoryService historyService;
	
	 /**
	  * 大金融发送放款中的状态的处理方法：
	  * 1.获得合同编号之后，查找合同表
	  * 2.对该条数据进行更新借款状态，
	  * 3.记录历史。
	  */
	@Override
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public DjrReceiveLoaningStateOutParam doExec(DjrReceiveLoaningStateInParam paramBean) {
		DjrReceiveLoaningStateOutParam outBean = new DjrReceiveLoaningStateOutParam();
		logger.info("大金融满标之后推送放款中的状态到CHP，接收到的的参数为："+JSONObject.toJSONString(paramBean));
		String contractCode = paramBean.getContractCode();
		if (StringHelper.isEmpty(contractCode)) {
			outBean.setRetCode(ReturnConstant.ERROR);
			outBean.setRetMsg("参数解析出错！参数信息不能够为空。");
			return outBean;
		}
		logger.info("大金融同步到3.0系统的需要修改状态的合同编号为："+contractCode);
		try {
			Contract contract = contractDao.findByContractCode(contractCode);
			if (ObjectHelper.isEmpty(contract)) {
				throw new Exception("在数据库中没有找到该合同编号对应的单子信息");
			}
			String bakApplyId = contract.getApplyId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loanCode", contract.getLoanCode());
			LoanInfo loanInfo = applyLoanInfoDao.selectByLoanCode(map);
			if (LoanApplyStatus.BIGFINANCE_TO_SNED.getCode().equals(loanInfo.getDictLoanStatus())) {
				LoanInfo updLoanInfo = new LoanInfo();
				LoanGrant loanGrant = new LoanGrant();
				loanGrant.setLoanCode(loanInfo.getLoanCode());
				loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
						.getCode());
				updLoanInfo.setApplyId(bakApplyId);
				updLoanInfo.setLoanCode(loanInfo.getLoanCode());
				// 设置状态为【大金融放款中】
				updLoanInfo.setDictLoanStatus(LoanApplyStatus.BIGFINANCE_GRANTING  
						.getCode());
				logger.info("接收大金融放款中的状态，更新---->开始");
				applyLoanInfoDao.updateLoanInfo(updLoanInfo);
				loanGrantDao.updateLoanGrant(loanGrant);
				historyService.saveLoanStatusHis(updLoanInfo,"大金融放款中", GrantCommon.SUCCESS,"大金融发送放款中的处理状态");
				logger.info("接收大金融放款中的状态，更新---->结束");
				outBean.setRetCode(ReturnConstant.SUCCESS);
				outBean.setRetMsg("执行成功，大金融放款中的状态同步到CHP3.0系统更新完成");
				logger.info("大金额放款中的状态同步更新到3.0系统，成功，contractCode:" + contractCode);
			}else {
				outBean.setRetCode(ReturnConstant.ERROR);
				outBean.setRetMsg("该数据在库中状态不为大金融待放款");
				logger.info("接收大金融放款中的状态处理失败，该数据在库中状态不为大金融待放款，contractCode为：" + contractCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			outBean.setRetCode(ReturnConstant.ERROR);
			outBean.setRetMsg("执行失败，大金融放款中状态同步到CHP3.0系统更新失败，失败原因："+e.getMessage());
			logger.error("接收大金融放款中的业务处理失败，请检查contractCode:" + contractCode+",原因为：",e);
		}
		return outBean;
	}
}
