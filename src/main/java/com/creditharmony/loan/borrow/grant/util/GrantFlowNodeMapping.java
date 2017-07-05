package com.creditharmony.loan.borrow.grant.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.creditharmony.loan.borrow.grant.constants.GrantCommon;

/**
 * 放款流程节点映射
 * @Class Name GrantFlowNodeMapping
 * @author 张永生
 * @Create In 2016年4月22日
 */
public class GrantFlowNodeMapping {

	/**
	 * 待款项确认
	 */
	public static final Integer GRANT_DETAIL_SURE = 1;
	/**
	 * 分配卡号
	 */
	public static final Integer DIS_CARD = 2;
	/**
	 * 放款
	 */
	public static final Integer GRANT = 3;
	/**
	 * 放款确认
	 */
	public static final Integer GRANT_SURE = 4;
	
	public static Map<String, Integer> concurrentMap = new ConcurrentHashMap<String, Integer>();
	
	/**
	 * 获取流程节点的index
	 * index:每个流程节点对应的Integer数值
	 * 2016年4月22日
	 * By 张永生
	 * @param nodeName
	 * @return
	 */
	public static Integer getNodeIndex(String nodeName){
		if(concurrentMap.isEmpty()){
			concurrentMap.put(GrantCommon.GRANT_DETAIL_SURE, GRANT_DETAIL_SURE);
			concurrentMap.put(GrantCommon.DIS_CARD, DIS_CARD);
			concurrentMap.put(GrantCommon.GRANT, GRANT);
			concurrentMap.put(GrantCommon.GRANT_SURE, GRANT_SURE);
		}
		return concurrentMap.get(nodeName);
	}
}
