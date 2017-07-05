package com.creditharmony.loan.car.common.util;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.common.consts.CarSystemConfigConstant;
import com.creditharmony.loan.car.common.dao.CarCustomerConsultationDao;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
/**
 * 合同相关 的工具类
 * @Class Name CarCommonUtil
 * @author 葛志超
 * @Create In 2016年5月27日
 */
public class CarCommonUtil {

	/**
	 * 获取最新的合同版本号
	 * @author 葛志超
	 * @param type 类型
	 * @Create In 2016年5月27日
	 */
	public static String getNewContractVer(){
		List<Dict> dlist = DictCache.getInstance().getListByType("jk_car_contract_version");
		String contractVer="1.0";
		if(null!=dlist&&dlist.size()>0){
			contractVer = dlist.get(dlist.size()-1).getLabel();
		}
		return contractVer;
	}
	
	public static String getVersionByLoanCode(String loanCode){
		String contractVer = "1.4";
		CarCustomerConsultationDao carCustomerConsultationDao = SpringContextHolder.getBean(CarCustomerConsultationDao.class);
		CarCustomerConsultation carCustomerConsultation = carCustomerConsultationDao.selectByLoanCode(loanCode);
		Date consultTime = carCustomerConsultation.getCreateTime();
  	    Date onLineDate = DateUtils.convertStringToDate(CarSystemConfigConstant.CAR_LOAN_ONLINE_DATE);
  	    Date onLineDate16 = DateUtils.convertStringToDate(CarSystemConfigConstant.CAR_LOAN_ONLINE_DATE_16);
  	    List<Dict> dlist = DictCache.getInstance().getListByType("jk_car_contract_version");
  	    if(null!=dlist&&dlist.size()>0){
  	    	if(DateUtils.dateAfter(consultTime, onLineDate16)){//1.6合同版本
				contractVer = dlist.get(dlist.size() - 1).getLabel();//1.6
			}else {
				if (DateUtils.dateAfter(consultTime, onLineDate)) {//根据咨询时间来判断 合同版本号
					contractVer = dlist.get(dlist.size() - 2).getLabel();//1.5
				} else {//老版本号
					contractVer = dlist.get(dlist.size() - 3).getLabel();//1.4
				}
			}
  		}
		return contractVer;
	}
	
	/**
	 * 
	 * @param loanCode   被展期的借款信息
	 * @param newLoanCode 展期的借款信息
	 * @return
	 */
	public static String getExtendVersionByLoanCode(String loanCode,String newLoanCode){
		String contractVersion = "1.4";
		List<Dict> dlist = DictCache.getInstance().getListByType("jk_car_contract_version");
		Date onLineDate = DateUtils.convertStringToDate(CarSystemConfigConstant.CAR_LOAN_ONLINE_DATE);
		Date onLineDate16 = DateUtils.convertStringToDate(CarSystemConfigConstant.CAR_LOAN_ONLINE_DATE_16);
		if(loanCode!=null && !"".equals(loanCode)){
			CarLoanInfoService carLoanInfoService = SpringContextHolder.getBean(CarLoanInfoService.class);
			CarLoanInfo oldInfo = carLoanInfoService.selectByLoanCode(loanCode);
			CarLoanInfo extendLoanInfo = carLoanInfoService.selectByLoanCode(newLoanCode);
			if(oldInfo!=null){
				//判断展期数据合同版本--开始
				List<CarLoanInfo> infos = carLoanInfoService.selectByLoanAddtionAppid(oldInfo.getId());//上一次借款信息ID
				if(infos.size()>0){//存在展期放弃
					Date createTime = infos.get(0).getCreateTime();
					if(DateUtils.dateAfter(createTime, onLineDate16)){//1.6合同版本
						contractVersion = dlist.get(dlist.size() - 1).getLabel();//1.6
					}else {
						if (DateUtils.dateAfter(createTime, onLineDate)) {
							contractVersion = dlist.get(dlist.size() - 2).getLabel();//1.5
						}
					}
				}else if(extendLoanInfo!=null){//存在已保存的展期数据
					Date time = extendLoanInfo.getCreateTime();
					if(DateUtils.dateAfter(time, onLineDate16)){//1.6合同版本
						contractVersion = dlist.get(dlist.size() - 1).getLabel();//1.6
					}else {
						if (DateUtils.dateAfter(time, onLineDate)) {
							contractVersion = dlist.get(dlist.size() - 2).getLabel();//1.5
						}
					}
				}else{//根据当前时间来判断
					if(DateUtils.dateAfter(new Date(), onLineDate16)){//1.6合同版本
						contractVersion = dlist.get(dlist.size() - 1).getLabel();//1.6
					}else {
						if (DateUtils.dateAfter(new Date(), onLineDate)) {
							contractVersion = dlist.get(dlist.size() - 2).getLabel();//1.5
						}
					}
				}
				//判断展期数据合同版本--结束
			}
		}
		return contractVersion;
	}
	
	public static String replaceSpot(String name){
		if(StringUtils.isNotEmpty(name)){
			name = name.replace("&middot;", "·");
		}
		return name;
	}
}
