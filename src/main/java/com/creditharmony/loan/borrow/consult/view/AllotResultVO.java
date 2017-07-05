package com.creditharmony.loan.borrow.consult.view;

import com.creditharmony.loan.common.vo.AbstractServiceVO;

/**
 * 分配团队经理和客户经理结果
 * @author 任志远
 * @date 2017年5月7日
 */
public class AllotResultVO extends AbstractServiceVO<AllotResultVO>{

	public AllotResultVO(){}
	
	public AllotResultVO(String code, String desc){
		super.code = code;
		super.msg = desc;
	}
	
}
