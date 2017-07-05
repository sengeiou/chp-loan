package com.creditharmony.loan.channel.goldcredit.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.channel.goldcredit.view.GCGrantView;
@LoanBatisDao
public interface GCGrantDao extends CrudDao<GCGrantView>{
	/**
	 * 导出金信金信待放款列表
	 * @param loanCodes 借款编号
	 */
	public List<GCGrantView> exportGrantList(List<String> loanCodes);
	
}
