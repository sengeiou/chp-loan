package com.creditharmony.loan.borrow.payback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.entity.PlatformBankEntity;
import com.creditharmony.core.deduct.service.PlatformBankService;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.type.SystemFlag;
import com.creditharmony.loan.borrow.payback.dao.PlatformGotoRuleDao;
import com.creditharmony.loan.borrow.payback.entity.PlatformGotoRule;

@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class PlatformGotoRuleManager extends CoreManager<PlatformGotoRuleDao, PlatformGotoRule> {
	
	@Autowired
	private PlatformGotoRuleDao platformGotoRuleDao;
	@Autowired
	private PlatformProvinceLimitManager platformProvinceLimitManager;
	@Autowired
	private PlatformBankService platformBankService;
	
	/**
	 * 查询单个实例
	 * 2016年3月4日
	 * By 周俊
	 * @param map
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<PlatformGotoRule> findPlatformGotoRule(PlatformGotoRule PlatformGotoRule){
		return platformGotoRuleDao.findPlatformGotoRule(PlatformGotoRule);
	}
	
	/**
	 * 获得可跳转的平台集合
	 * 2016年3月4日
	 * By 周俊
	 * @param platformId 平台id
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<String> findPlatformIdList(String platformId){
		
		PlatformGotoRule platformGotoRule = new PlatformGotoRule();
		platformGotoRule.setPlatformId(platformId);
		//platformGotoRule.setStatus(String.valueOf(BorrowConstant.FIRST));// 可用
		List<PlatformGotoRule> list = platformGotoRuleDao.findPlatformGotoRule(platformGotoRule);
		
		if (ArrayHelper.isNotEmpty(list)) {
			platformGotoRule = list.get(0);
			List<String> platformIdList = new ArrayList<String>();
			String platformRule = platformGotoRule.getPlatformRule();
			String[] array = platformRule.split(",");
			for (String temp : array) {
				platformIdList.add(temp);
			}
			return platformIdList;
		}
		return null;
	}
	
	/**
	 * 获取跳转规则
	 * 2016年3月8日
	 * By 周俊
	 * @param platId
	 * @param provinceCode
	 * @param bankId
	 * @return
	 */
	public String getDeductRule(String platId, String provinceCode,
			String bankId) {
		// 根据平台获得此平台可以跳转的平台
		List<String> platformIdList = findPlatformIdList(platId);
		if (!ArrayHelper.isNotEmpty(platformIdList)) {
			return "";
		}
		// 根据城市code获得该城市下不可跳转的平台
		List<String> platformIdLimitList = platformProvinceLimitManager.findPlatformIdLimit(provinceCode);
		/*if (ArrayHelper.isNotEmpty(platformIdLimitList)) {
				for (int i = 1; i < platformIdList.size(); i++) {
					if (platformIdLimitList.contains(platformIdList.get(i))) {
						platformIdList.remove(i);
					}
				}
		}*/
		if (ArrayHelper.isNotEmpty(platformIdLimitList)) {
			List<String> platformIdListTemp = new ArrayList<String>();
			for (int i = 1; i < platformIdList.size(); i++) {
				platformIdListTemp.add(platformIdList.get(i));
			}
			platformIdListTemp.retainAll(platformIdLimitList);
			platformIdList.removeAll(platformIdListTemp);
		}
		String deductRule = "";
		for (String tempPlatId : platformIdList) {
			PlatformBankEntity pb = new PlatformBankEntity();
			pb.setSysId(String.valueOf(SystemFlag.FORTUNE.value));
			pb.setPlatformId(tempPlatId);
			pb.setBankId(bankId);
			pb.setDeductFlag(DeductFlagType.COLLECTION.getCode());
			// PlatformBankEntity platformBank = platformBankService.getPlatformBank(tempPlatId, bankId, DeductFlagType.COLLECTION.getCode(),null);
			PlatformBankEntity platformBank = platformBankService.getPlatformBank(pb);
			if (!ObjectHelper.isEmpty(platformBank)&&!ObjectHelper.isEmpty(platformBank.getDayLimitMoney())) {
				if(StringUtils.isBlank(deductRule)){
					deductRule = tempPlatId+":"+platformBank.getDeductType()+":"+String.valueOf(platformBank.getDayLimitMoney());
				}else{
					deductRule = deductRule+","+tempPlatId+":"+platformBank.getDeductType()+":"+String.valueOf(platformBank.getDayLimitMoney());
				}
			}
		}
		return deductRule;
	}

}
