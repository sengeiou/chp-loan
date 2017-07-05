package com.creditharmony.loan.borrow.contract.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.service.CustInfoService;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;

/**
 *更改标识
 *<p>在利率审核节点更改标识</p>
 *<p>合同审核节点更改标识</p> 
 *@author 张灏
 *@version 1.0
 *
 * 
 */
@Service("ex_hj_loanflow_changeFlag")
public class ChangeFlagEx extends BaseService implements ExEvent {

	@Autowired
	private CustInfoService custInfoService;
	
	/**
	 *流程事件回调方法 
	 * @param workItem
	 * 
	 */
	@Override
	public void invoke(WorkItemView workItem) {
		ContractBusiView view = (ContractBusiView) workItem.getBv();
		String[] applyIds= new String[1];
		applyIds[0] = view.getApplyId();
		String attr = view.getAttrName();
		String value = null;
		if(ContractConstant.ATTR_LOANFLAG.equals(attr)){
			value = view.getLoanFlag();
		}
		String userCode = view.getCurrUserCode();
		custInfoService.changeFlag(applyIds, attr, value, userCode);  	
	}

}
