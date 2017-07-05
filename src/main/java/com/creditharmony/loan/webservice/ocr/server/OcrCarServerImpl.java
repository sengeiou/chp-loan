package com.creditharmony.loan.webservice.ocr.server;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.loan.webservice.ocr.dao.OcrCarDao;

/**
 * OCR二期车借接口实现类
 * @author 王俊杰
 * @date 2016-4-1
 */
@WebService
@SOAPBinding(style = Style.RPC)
@Transactional(readOnly = true,value="loanTransactionManager")
@Service
public class OcrCarServerImpl implements OcrCarServer {
	
	private Logger logger = LoggerFactory.getLogger(OcrCarServerImpl.class);
	
	@Autowired
	private OcrCarDao ocrCarDao;
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	@Override
	public String checkCertNum(String certNum) {
		return null;
	}

	@Transactional(readOnly = false,value="loanTransactionManager")
	@Override
	public String saveConsultationInfo(String jsonStr) {
		return null;
	}
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	@Override
	public String getBankInfo(String jsonStr) {
		return null;
	}
	
	@Transactional(readOnly = false,value="loanTransactionManager")
	@Override
	public String saveBankInfo(String jsonStr) {
		return null;
	}

	@Transactional(readOnly = true,value="loanTransactionManager")
	@Override
	public String queryCarInfoList(String name) {
		return null;
	}
	
	@Transactional(readOnly = false,value="loanTransactionManager")
	@Override
	public String saveCarInfo(String jsonStr) {
		return null;
	}
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	@Override
	public String queryHistoryApplyInfoList(String name) {
		return null;
	}

}
