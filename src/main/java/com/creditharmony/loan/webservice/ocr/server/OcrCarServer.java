package com.creditharmony.loan.webservice.ocr.server;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * OCR二期车借接口服务
 * @author 王俊杰
 * @date 2016-4-1
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface OcrCarServer {

	/**
	 * 身份证号校验
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param certNum
	 * @return
	 */
	String checkCertNum(String certNum);
	
	/**
	 * 录入客户咨询保存
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String saveConsultationInfo(String jsonStr);
	
	/**
	 * 补录银行卡查询
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String getBankInfo(String jsonStr);
	
	/**
	 * 补录银行卡保存
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String saveBankInfo(String jsonStr);
	
	/**
	 * 评估车辆信息列表查询
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param name
	 * @return
	 */
	String queryCarInfoList(String name);
	
	/**
	 * 评估车辆信息保存
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String saveCarInfo(String jsonStr);
	
	/**
	 * 历史列表查询
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param name
	 * @return
	 */
	String queryHistoryApplyInfoList(String name);
}
