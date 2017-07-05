package com.creditharmony.loan.common.workFlow.view;

import java.util.List;

import com.creditharmony.core.dict.entity.Dict;

/**
 * 字典扩展类
 * @Class Name DictEx
 * @Create In 2016年5月23日
 */
public class LoanFlowDictEx extends Dict{
	
	private static final long serialVersionUID = 7511847176682123591L;
	private List<LoanFlowDictEx> dictExs;
	
	public List<LoanFlowDictEx> getDictExs() {
		return dictExs;
	}
	
	public void setDictExs(List<LoanFlowDictEx> dictExs) {
		this.dictExs = dictExs;
	}

}
