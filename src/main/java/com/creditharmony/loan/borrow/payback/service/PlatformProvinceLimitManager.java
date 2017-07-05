package com.creditharmony.loan.borrow.payback.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.payback.dao.PlatformProvinceLimitDao;
import com.creditharmony.loan.borrow.payback.entity.PlatformProvinceLimit;

@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class PlatformProvinceLimitManager extends CoreManager<PlatformProvinceLimitDao, PlatformProvinceLimit> {
	
	@Autowired
	private PlatformProvinceLimitDao platformProvinceLimitDao;
	
	/**
	 * 查询省份下不可跳转的平台集合
	 * 2016年3月4日
	 * By 周俊
	 * @param map
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<String> findPlatformIdLimit(String provinceCode){
		PlatformProvinceLimit platformProvinceLimit = new PlatformProvinceLimit();
		platformProvinceLimit.setProvince(provinceCode);
		//platformProvinceLimit.setStatus(String.valueOf(BorrowConstant.ZERO));
		List<PlatformProvinceLimit> platformProvinceLimitList = platformProvinceLimitDao.findPlatformProvinceLimit(platformProvinceLimit);
		if (ArrayHelper.isNotEmpty(platformProvinceLimitList)) {
			List<String> list = new ArrayList<String>();
			for (PlatformProvinceLimit temp : platformProvinceLimitList) {
				list.add(temp.getPlatformLimit());
			}
			return list;
		}
		return null;
	}
}
