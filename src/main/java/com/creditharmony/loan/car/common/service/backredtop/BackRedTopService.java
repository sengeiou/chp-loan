package com.creditharmony.loan.car.common.service.backredtop;

import java.text.SimpleDateFormat;

public interface BackRedTopService {

	/**
	 * 从退回状态节点恢复到退回时的节点
	 */
	public void recovery(SimpleDateFormat sdf);
	
	/**
	 * 在恢复到退回时节点前进行下一步提交
	 */
	public void commitBeforeRecovery(SimpleDateFormat sdf);
	
	/**
	 * 正常提交下一步。没有退回业务或者从退回节点恢复到退回之后的节点后的提交
	 */
	public void commit(SimpleDateFormat sdf);
}
