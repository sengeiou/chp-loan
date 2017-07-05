package com.creditharmony.loan.webservice.ocr.server;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 * OCR二期信借接口服务
 * @author 王俊杰
 * @date 2016-4-1
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface OcrServerService {
	
	/**
	 * 获取录入客户咨询前所需要的信息
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @return
	 */
	String getConsultationInfo();
	
	/**
	 * 获取银行卡省市
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String getBankAddressList(String jsonStr);
	
	/**
	 * 身份证有效性验证
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
	 * 获取借款申请前所需要的信息(录入和补录)
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String getLoanApplyInfo(String jsonStr);
	
	/**
	 * 借款申请表保存(录入和补录)
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String saveApplyInfo(String jsonStr);

	/**
	 * 补录银行卡信息查询
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String getBankInfo(String jsonStr);
	
	/**
	 * 补录银行卡信息保存
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String saveBankInfo(String jsonStr);
	
	/**
	 * 补录共借人信息查询
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String getCoBorrowingInfo(String jsonStr);
	
	/**
	 * 补录共借人信息保存
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param jsonStr
	 * @return
	 */
	String saveCoBorrowingInfo(String jsonStr);
	
	/**
	 * 历史列表查询
	 * @author 王俊杰
	 * @date 2016-4-1
	 * @param name
	 * @return
	 */
	String queryHistoryApplyInfoList(String name);
}
