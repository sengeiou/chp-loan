package com.creditharmony.loan.channel.goldcredit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.channel.goldcredit.dao.GCGrantDao;
import com.creditharmony.loan.channel.goldcredit.view.GCGrantView;
@Service
public class GCGrantService extends CoreManager<GCGrantDao, GCGrantView> {
	@Autowired
	private GCGrantDao grantDao;

	/**
	 * 导出金信金信待放款列表
	 * 
	 * @param loanCodes
	 *            借款编号
	 */
	public List<GCGrantView> exportGrantList(List<String> loanCodes) {
		return grantDao.exportGrantList(loanCodes);
	}
}
