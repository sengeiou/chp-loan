package com.creditharmony.loan.sync.data.fortune;

import com.creditharmony.core.claim.dto.SyncClaim;

/**
 * 汇金同步数据到财富债权
 * @Class Name ForuneSyncCreditorProxy
 * @author 韩龙
 * @Create In 2015年12月25日
 */
public interface ForuneSyncCreditorService {
	
	/**
	 * 汇金同步数据到财富可用债权池与月浪花满盈债权池方法
	 * 2015年12月25日
	 * By 韩龙
	 * @param loanSync
	 * @return
	 */
	public boolean executeSyncLoan(SyncClaim loanSync);
	
	/**
	 * 汇金同步提前结清（可用债权池）方法
	 * 2016年1月13日
	 * By 韩龙
	 * @param loanSync
	 * @return
	 */
	public boolean executeSyncEarlySettlement(SyncClaim loanSync);
	
	/**
	 * 测试方法
	 * 2015年12月25日
	 * By 韩龙
	 * @param name
	 * @return
	 */
	public String testExecuteSyncLoan(String name);
}
