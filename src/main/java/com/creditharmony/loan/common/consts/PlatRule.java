/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.constsPlatRule.java
 * @Create By 王彬彬
 * @Create In 2015年12月22日 上午8:51:19
 */
package com.creditharmony.loan.common.consts;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.loan.common.entity.PlatformRule;

/**
 * 平台规则数据列表
 * 
 * @Class Name PlatRule
 * @author 王彬彬
 * @Create In 2015年12月22日
 */
public class PlatRule {

	public static Map<String, BigDecimal> platMap = new HashMap<String, BigDecimal>();

	/**
	 * 平台规则初始化
	 * 
	 * @param platform
	 */
	public PlatRule(List<PlatformRule> platform) {
		if (ArrayHelper.isNotEmpty(platform)) {
			for (PlatformRule plat : platform) {
				String platInfo = plat.getDictDeductPlatformId()
						+ plat.getDictBankId()
						+ plat.getDictDeductInterfaceType();

				platMap.put(platInfo,
						new BigDecimal(plat.getSingleLimitMoney())
								.multiply(new BigDecimal("10000")));
			}
		}
	}

	public static Map<String, BigDecimal> getPlatMap() {
		return platMap;
	}

	public static void setPlatMap(Map<String, BigDecimal> platMap) {
		PlatRule.platMap = platMap;
	}

}
