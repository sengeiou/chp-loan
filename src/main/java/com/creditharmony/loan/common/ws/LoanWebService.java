/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.webserviceContractNumber.java
 * @Create By 王彬彬
 * @Create In 2016年1月11日 下午5:01:15
 */
package com.creditharmony.loan.common.ws;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.creditharmony.loan.common.view.LoanWebServiceView;

/**
 * 合同编号生产规则
 * 
 * @Class Name ContractNumber
 * @author 王彬彬
 * @Create In 2016年1月11日
 */
@WebService
public interface LoanWebService {

	/**
	 * 离开汇诚处理（合同号生成，渠道标识判断）
	 * 2016年3月2日
	 * 王彬彬
	 * @param applyId 流程编号
	 * @param loanCode 借款编码
	 * @return
	 * @throws Exception
	 */
	public LoanWebServiceView loanInit(
			@WebParam(name = "applyId") String applyId,
			@WebParam(name = "loanCode") String loanCode,
			@WebParam(name = "storeOrgId") String storeOrgId) throws Exception;

}
