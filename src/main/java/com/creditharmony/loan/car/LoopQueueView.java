package com.creditharmony.loan.car;

import com.creditharmony.bpm.frame.task.bean.BaseBusinessBean;

public final class LoopQueueView extends BaseBusinessBean {
	private String loanCode;//借款编号
	private String antifraudEngine;//反欺诈引擎判定结果

	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getAntifraudEngine() {
		return antifraudEngine;
	}
	public void setAntifraudEngine(String antifraudEngine) {
		this.antifraudEngine = antifraudEngine;
	}
}
