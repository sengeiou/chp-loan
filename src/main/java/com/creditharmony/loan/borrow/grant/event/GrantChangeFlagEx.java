/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.grantChangeFlagEx.java
 * @Create By 朱静越
 * @Create In 2015年11月28日 下午6:20:53
 */
/**
 * @Class Name GrantChangeFlagEx
 * @author 朱静越
 * @Create In 2015年11月28日
 * 该方法用来修改单子的标识，放款过程中的修改单子的标识
 */
package com.creditharmony.loan.borrow.grant.event;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;

@Service("ex_hj_loanflow_grantChangeFlag")
public class GrantChangeFlagEx extends BaseService implements ExEvent {
	@Autowired
	private LoanGrantDao loanGrantDao;	
	@Autowired
	private ContractDao contractDao;
	/**
	 * 修改业务中的单子的标识，和驳回申请的驳回code和驳回原因
	 */
	@Override
	public void invoke(WorkItemView workItem) {
	   GrantDealView gdv=(GrantDealView)workItem.getBv();
	   // 调用方法，进行更新标识
	   LoanInfo loanInfo=new LoanInfo();
	   Contract contract = new Contract();
	   contract.setLoanCode(gdv.getLoanCode());
	   loanInfo.setLoanCode(gdv.getLoanCode());
	   String operType = gdv.getOperateType();
	   if (StringUtils.isNotEmpty(gdv.getLoanFlag())) {
		   String loanMarking=gdv.getLoanFlag();
		   // 标识更改，同时需要更改合同表中的标识
		   //loanInfo.setLoanFlag(loanMarking);
		   ChannelFlag flag =  ChannelFlag.parseByName(loanMarking);
		   loanMarking = flag.getCode();
		   loanInfo.setLoanFlag(loanMarking);
	       contract.setChannelFlag(loanMarking);
		   loanGrantDao.updateFlag(loanInfo);
		   contractDao.updateContract(contract);
	  }
	   if(YESNO.YES.getCode().equals(operType)){
	       if(StringUtils.isNotEmpty(gdv.getFrozenFlag()) && YESNO.NO.getCode().equals(gdv.getFrozenFlag())){
    		   String frozenReason=gdv.getFrozenReason();
	           loanInfo.setFrozenReason(frozenReason);
	           loanInfo.setFrozenCode(" ");
	           loanInfo.setFrozenLastApplyTime(new Date());
	           loanGrantDao.updateFlag(loanInfo); 
	       }
	   }
	}

}