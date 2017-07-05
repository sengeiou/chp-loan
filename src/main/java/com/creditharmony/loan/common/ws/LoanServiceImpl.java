/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.wsContractWebServiceImpl.java
 * @Create By 王彬彬
 * @Create In 2016年1月11日 下午5:13:02
 */
package com.creditharmony.loan.common.ws;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.loan.common.service.ContractCommonService;
import com.creditharmony.loan.common.view.LoanWebServiceView;

/**
 * @Class Name ContractWebServiceImpl
 * @author 王彬彬
 * @Create In 2016年1月11日
 */
@WebService
public class LoanServiceImpl implements LoanWebService {
	@Autowired
	private ContractCommonService contractCommonService;

	/**
	 * 离开汇诚处理（合同号生成，渠道标识判断,无纸化标识添加）
	 */
	@Override
	public LoanWebServiceView loanInit(String applyId, String loanCode,
			String storeOrgId) throws Exception {
		return contractCommonService.loanInit(applyId, loanCode, storeOrgId);
	}
}
