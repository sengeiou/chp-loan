package com.creditharmony.loan.channel.bigfinance.ws;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendFreezeInfoInBean;
import com.creditharmony.adapter.bean.out.djrcreditor.DjrSendFreezeInfoOutBean;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.channel.goldcredit.service.JxSendDataService;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;

@Service
public class BigFinanceExFreezeService{
	private Logger logger = LoggerFactory.getLogger(BigFinanceExFreezeService.class);
	@Autowired
	private JxSendDataService jxSendDataService;
	@Autowired
	private ContractDao contractDao;

	 	/**
		 * 1.声明一个需要发送的类，
		 * 2.通过sql查询出来需要发送的数据
		 * 3.将定义的类copy到接口类
		 * 4.发送成功之后，获得大金融返回的类，进行判断
		 * 5.门店申请冻结成功之后，进行调用
		 */
	// 门店进行申请冻结之后，同步债权冻结状态到大金融平台
	public DjrSendFreezeInfoOutBean exchangeDebtFreeze(LoanInfo loanInfo){
		DjrSendFreezeInfoInBean inBean = new DjrSendFreezeInfoInBean();
		ClientPoxy service = new ClientPoxy(ServiceType.Type.DJR_SEND_FREEZE_SERVICE);
		inBean.setDataTransferId(getId());
		logger.info("门店申请冻结，向大金融同步数据的id："+inBean.getDataTransferId());
		Contract contract = contractDao.findByLoanCode(loanInfo.getLoanCode());
		String contractCode = contract.getContractCode();
		logger.info("门店申请冻结，向大金融同步数据的合同编号："+contractCode);
		String remark = ""; // 冻结原因
		if(StringUtils.isNotEmpty(loanInfo.getFrozenName()) && (ApplyInfoConstant.OTHER1.equals(loanInfo.getFrozenName())||ApplyInfoConstant.OTHER2.equals(loanInfo.getFrozenName()))){
            remark = loanInfo.getFrozenReason();
        }else{
            remark = loanInfo.getFrozenName();
        }
		inBean.setContractCode(contractCode);
		inBean.setFreezeReason(remark);
		inBean.setFreezeTime(new Date());
		inBean.setFreezeStatus("1"); // 冻结状态，1为冻结
		logger.info("门店申请冻结，调用接口开始----"+inBean.getFreezeReason());
		DjrSendFreezeInfoOutBean outBean = (DjrSendFreezeInfoOutBean)service.callService(inBean);
		logger.info("门店申请冻结，调用接口结束----"+outBean.getRetMsg());
		return outBean;
	}
	
	/**
	 * 随机产生指定长度的数据字符串
	 * @param length 指定长度
	 * @return 结果
	 */
     public String getId(){
		 UUID uuid= UUID.randomUUID();
     	 String batchNo=uuid.toString().replaceAll("-","");
     	 batchNo=batchNo.substring(0, 16);
     	 return batchNo;
	  }
}
