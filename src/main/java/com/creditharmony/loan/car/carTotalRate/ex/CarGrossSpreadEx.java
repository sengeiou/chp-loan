/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.car.carTotalRate.ex
 * @Create By 张进
 * @Create In 016年2月3号
 */
package com.creditharmony.loan.car.carTotalRate.ex;

import com.creditharmony.loan.car.common.entity.CarGrossSpread;

/**
 * 产品总费率 扩展
 * @author 张进
 * @create By 2016年2月3号
 *
 */
public class CarGrossSpreadEx extends CarGrossSpread {

	private static final long serialVersionUID = 1L;
	
	private String provinceCityId; //  区域编码
	
	
	public String getProvinceCityId() {
		return provinceCityId;
	}
	public void setProvinceCityId(String provinceCityId) {
		this.provinceCityId = provinceCityId;
	}
	
	
}
