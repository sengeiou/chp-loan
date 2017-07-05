/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.serviceGrantExportTLService.java
 * @Create By 张灏
 * @Create In 2016年2月26日 上午11:23:55
 */
package com.creditharmony.loan.borrow.grant.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.grant.dao.GrantExportTLDao;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantExportTLEx;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.common.service.PaymentSplitService;

/**
 * 导出通联Service
 * @Class Name GrantExportTLService
 * @author 张灏
 * @Create In 2016年2月26日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class GrantExportTLService  extends CoreManager<GrantExportTLDao, GrantExportTLEx>{

    @Autowired
    private GrantExportTLDao grantExportDao;
    @Autowired
	private PaymentSplitService paymentSplitService;
    
    public List<GrantExportTLEx> findGrantInfo(Map<String,Object> param){
        
        return grantExportDao.findGrantInfo(param);
    }
    // 事务控制，添加拆分
    @Transactional(readOnly = false, value = "loanTransactionManager")
    public Map<String, Object> getSplitTl(Map<String, Object> param){
    	BigDecimal amountTotal = BigDecimal.ZERO;
    	Map<String, Object> returnMap = new HashMap<String, Object>();
		String provinceName = null;
		String cityName = null;
		Integer size = 0;
    	List<PaybackApply> splitList = dao.getSplitTl(param);
    	// 先删除拆分表中处理中（导出）的数据，根据applyId
    	for (PaybackApply paybackApply : splitList) {
    		paybackApply.setSplitBackResult(UrgeCounterofferResult.PROCESSED.getCode());
			dao.deleteSplitDeal(paybackApply);
		}
    	// 拆分
    	paymentSplitService.splitList(splitList,
				TargetWay.GRANT.getCode(), DeductTime.BATCH, DeductPlat.TONGLIAN);
    	List<GrantExportTLEx> grantExportTLExs = dao.findGrantInfo(param);
    	if (!ObjectHelper.isEmpty(grantExportTLExs)) {
			for (GrantExportTLEx cur : grantExportTLExs) {
				cur.setIdentityCode(""); // 通联导出，证件号码设置为空
				amountTotal = amountTotal.add(new BigDecimal(cur
						.getGrantAmount()));
				provinceName = cur.getProvinceName();
				if(StringUtils.isNotEmpty(provinceName)){
				    provinceName = provinceName.replace("省", "").replace("市", "");
				    cur.setProvinceName(provinceName);
				}
				cityName = cur.getCityName();
				if(StringUtils.isNotEmpty(cityName)){
				    cityName = cityName.replace("自治区", "").replace("市", "");
                    cur.setCityName(cityName);
                }
			}
			size = grantExportTLExs.size();
		}
    	returnMap.put("size", size);
    	returnMap.put("amountTotal", amountTotal);
    	returnMap.put("grantExportTLExs", grantExportTLExs);
    	return returnMap;
    }
    
}
