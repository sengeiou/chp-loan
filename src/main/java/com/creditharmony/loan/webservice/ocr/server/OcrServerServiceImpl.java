package com.creditharmony.loan.webservice.ocr.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.loan.webservice.ocr.bean.DictionaryInfo;
import com.creditharmony.loan.webservice.ocr.dao.OcrDao;

/**
 * OCR二期信借接口实现类
 * @author 王俊杰
 * @date 2016-4-1
 */
@WebService
@SOAPBinding(style = Style.RPC)
@Transactional(readOnly = true,value="loanTransactionManager")
@Service
public class OcrServerServiceImpl implements OcrServerService {
	
	private Logger logger = LoggerFactory.getLogger(OcrServerServiceImpl.class);
	
	@Autowired
	private OcrDao ocrDao;

	/**
	 * 获取二期信借数据
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	@Override
	public String getConsultationInfo() {
		List<DictionaryInfo> customerSourceList = ocrDao.getDictList("jk_cm_src");
		List<DictionaryInfo> industryList = ocrDao.getDictList("jk_industry_type");
		List<DictionaryInfo> loanUseList = ocrDao.getDictList("jk_loan_use");
		List<DictionaryInfo> accountBankList = ocrDao.getDictList("jk_open_bank");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("customerSourceList", customerSourceList);
		map.put("industryList", industryList);
		map.put("loanUseList", loanUseList);
		map.put("accountBankList", accountBankList);
		String json = JsonMapper.nonDefaultMapper().toJson(map);
		logger.info("获取录入客户咨询前所需要的信息:\t" + json);
		return json;
	}
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	@Override
	public String getBankAddressList(String jsonStr) {
		
		return null;
	}
	
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
	public String getLoanApplyInfo(String jsonStr) {
		return null;
	}
	
	@Transactional(readOnly = false,value="loanTransactionManager")
	@Override
	public String saveApplyInfo(String jsonStr) {
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
	public String getCoBorrowingInfo(String jsonStr) {
		return null;
	}
	
	@Transactional(readOnly = false,value="loanTransactionManager")
	@Override
	public String saveCoBorrowingInfo(String jsonStr) {
		return null;
	}
	
	@Transactional(readOnly = true,value="loanTransactionManager")
	@Override
	public String queryHistoryApplyInfoList(String name) {
		return null;
	}

//	@Transactional(readOnly = true)
//	@Override
//	public String login(String jsonStr) {
//		logger.info("OCR登录用户信息:\t" + jsonStr);
//		
//		UserBean userBean = JsonMapper.nonDefaultMapper().fromJson(jsonStr, UserBean.class);
//		
//		User user = SpringContextHolder.getBean(SystemManager.class).getUserByLoginName(userBean.getLoginName());
//		if (user != null) {
//			UserValidate.validateUser(user);
//			try {
//				FileNetContextHelper.login(userBean.getLoginName(), userBean.getPassword());
//				return null;
//			} catch (InvalidSessionException e) {
//				//密码不正确
//				logger.info("");
//				ResultBean rb = new ResultBean(false, "密码不正确");
//				String json = JsonMapper.nonDefaultMapper().toJson(rb);
//				logger.info(json);
//				return json;
//			}
//		} else {
//			//用户不存在
//			logger.info("");
//			ResultBean rb = new ResultBean(false, "用户名不存在");
//			String json = JsonMapper.nonDefaultMapper().toJson(rb);
//			logger.info(json);
//			return json;
//		}
//	}
}
