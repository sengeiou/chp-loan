/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.car.carTotalRate.ex
 * @Create By 张进
 * @Create In 016年2月3号
 */
package com.creditharmony.loan.car.carTotalRate.ex;

import com.creditharmony.loan.car.common.entity.CarSpreadProvinceCityRelation;
/**
 * 产品关联城市扩展类
 * @author 张进
 * @create By 2016年2月3号
 */

public class CarSpreadProvinceCityRelationEx extends CarSpreadProvinceCityRelation {
 
	private static final long serialVersionUID = 1L;
	private String linkId;//关联ID

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	
	
	
}
