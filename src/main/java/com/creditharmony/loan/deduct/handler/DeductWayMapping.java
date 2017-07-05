package com.creditharmony.loan.deduct.handler;

import java.util.concurrent.ConcurrentHashMap;

import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.loan.car.common.consts.CarDeductWays;


public class DeductWayMapping {
	
	/**
	 * 财富划扣
	 */
	public static final String CF_DEDUCT = DeductWays.CF_01.getCode();
	
	/**
	 * 财富划扣索引
	 */
	public static final int CF_DEDUCT_INDEX = 201;
	
	/**
	 * 财富回款
	 */
	public static final String CF_PAYMENT = DeductWays.CF_02.getCode();
	
	/**
	 * 财富回款索引
	 */
	public static final int CF_PAYMENT_INDEX = 202;
	
	/**
	 * 财富回息
	 */
	public static final String CF_SETTLE_INTEREST = DeductWays.CF_03.getCode();
	
	/**
	 * 财富回息索引
	 */
	public static final int CF_SETTLE_INTEREST_INDEX = 203;
	
	/**
	 * 汇金集中回款
	 */
	public static final String HJ_CENTRALIZED_PAYMENT = DeductWays.HJ_01.getCode();
	
	/**
	 * 汇金集中回款索引
	 */
	public static final int HJ_CENTRALIZED_PAYMENT_INDEX = 301;
	
	/**
	 * 汇金非集中回款
	 */
	public static final String HJ_UNCENTRALIZED_PAYMENT = DeductWays.HJ_02.getCode();
	
	/**
	 * 汇金非集中回款索引
	 */
	public static final int HJ_UNCENTRALIZED_PAYMENT_INDEX = 302;
	
	/**
	 * 汇金放款
	 */
	public static final String HJ_RELEASE_MONEY = DeductWays.HJ_03.getCode();
	
	/**
	 * 汇金放款索引
	 */
	public static final int HJ_RELEASE_MONEY_INDEX = 303;
	
	/**
	 * 汇金服务费催收
	 */
	public static final String HJ_SERVICE_CHARGE_COLLECT = DeductWays.HJ_04.getCode();
	
	/**
	 * 汇金服务费催收索引
	 */
	public static final int HJ_SERVICE_CHARGE_COLLECT_INDEX = 304;
	
	/**
	 * 车借放款
	 */
//	public static final String CJ_RELEASE_MONEY = CarDeductWays.CJ_01.getCode();
	
	/**
	 * 车借放款索引
	 */
//	public static final int CJ_RELEASE_MONEY_INDEX = 305;
	
	/**
	 * 车借催收服务划扣
	 */
//	public static final String CJ_SERVICE_CHARGE_DEDUCT = CarDeductWays.CJ_02.getCode();
	
	/**
	 * 车借催收服务划扣索引
	 */
//	public static final int CJ_SERVICE_CHARGE_DEDUCT_INDEX = 306;
	
	/**
	 * 车借服务费退款
	 */
//	public static final String CJ_SERVICE_CHARGE_RETURN_MONEY = CarDeductWays.CJ_03.getCode();
	
	/**
	 * 车借服务费退款索引
	 */
//	public static final int CJ_SERVICE_CHARGE_RETURN_MONEY_INDEX = 307;
	
	private static ConcurrentHashMap<String, Integer> dataMap = new ConcurrentHashMap<String, Integer>();
	
	public static Integer getCaseIndex(String deductWay){
		if(dataMap.isEmpty()){
			dataMap.put(CF_DEDUCT, CF_DEDUCT_INDEX);
			dataMap.put(CF_PAYMENT, CF_PAYMENT_INDEX);
			dataMap.put(CF_SETTLE_INTEREST, CF_SETTLE_INTEREST_INDEX);
			dataMap.put(HJ_CENTRALIZED_PAYMENT, HJ_CENTRALIZED_PAYMENT_INDEX);
			dataMap.put(HJ_UNCENTRALIZED_PAYMENT, HJ_UNCENTRALIZED_PAYMENT_INDEX);
			dataMap.put(HJ_RELEASE_MONEY, HJ_RELEASE_MONEY_INDEX);
			dataMap.put(HJ_SERVICE_CHARGE_COLLECT, HJ_SERVICE_CHARGE_COLLECT_INDEX);
//			dataMap.put(CJ_RELEASE_MONEY, CJ_RELEASE_MONEY_INDEX);
//			dataMap.put(CJ_SERVICE_CHARGE_DEDUCT, CJ_SERVICE_CHARGE_DEDUCT_INDEX);
//			dataMap.put(CJ_SERVICE_CHARGE_RETURN_MONEY, CJ_SERVICE_CHARGE_RETURN_MONEY_INDEX);
		}
		return dataMap.get(deductWay);
	}
	
}
