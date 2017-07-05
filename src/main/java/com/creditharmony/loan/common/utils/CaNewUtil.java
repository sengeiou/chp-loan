package com.creditharmony.loan.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditharmony.adapter.bean.in.ca.Ca_AgentSignInBean;
import com.creditharmony.adapter.bean.in.ca.Ca_GuaranteeSignInBean;
import com.creditharmony.adapter.bean.in.ca.Ca_UnitSignInBean;
import com.creditharmony.adapter.bean.out.ca.Ca_AgentSignOutBean;
import com.creditharmony.adapter.bean.out.ca.Ca_GuaranteeSignOutBean;
import com.creditharmony.adapter.bean.out.ca.Ca_UnitSignOutBean;
import com.creditharmony.adapter.constant.CaKeyWordPosType;
import com.creditharmony.adapter.constant.CaUnitSignType;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.LoanCASignType;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.contract.dao.GuaranteeRegisterDao;
import com.creditharmony.loan.borrow.contract.entity.GuaranteeRegister;
import com.creditharmony.loan.common.consts.CAKeyWord;
import com.creditharmony.loan.common.entity.CaCustomerSign;
import com.creditharmony.loan.common.entity.CaSignRegist;

/**
 * CA操作工具类
 * 
 * @Class Name CaUtil
 * @author 王彬彬
 * @Create In 2016年2月23日
 */
public class CaNewUtil {

	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(CaNewUtil.class);

	/**
	 * 签章 2016年4月29日 By 王彬彬
	 * 
	 * @param loanCASignType
	 *            签章类型文件枚举
	 * @param pdfId
	 *            签章文件
	 * @param param
	 *            签章信息
	 * @param securityCustomerParam
	 * 			 自然人保证人签章信息
	 * 
	 * @return CA签章不成功，则返回null
	 */
	public static String caSign(LoanCASignType loanCASignType, String pdfId,
			CaCustomerSign param,CaCustomerSign securityCustomerParam,String legalMan) {
		String docId = StringHelper.EMPTY;
		if (loanCASignType.getCode().equals(
				LoanCASignType.PER_COM_APPROVE.getCode())) {
			docId = signXy(pdfId, param,securityCustomerParam,legalMan);
		}
		if (loanCASignType.getCode().equals(LoanCASignType.ALL_SIGN_APPROVE.getCode())) {
			docId = signAllCompanyAndCustomer(pdfId, param,securityCustomerParam,legalMan);
		}
		if (loanCASignType.getCode().equals(LoanCASignType.CUSTOMER.getCode())) {
			docId = CaNewUtil.signCustomer(pdfId, param).getDocId();
		}
		if (StringHelper.isNotEmpty(docId)) {
			return docId;
		}
		return null;
	}
	
	/**
	 * 作废 2016年5月10日 By 王彬彬
	 * 
	 * @param loanCASignType
	 * @param pdfId
	 * @param param
	 * @return
	 */
	public static boolean caSignCancel(LoanCASignType loanCASignType,
			String pdfId, CaCustomerSign param) {
	    boolean result = false;
	    Ca_UnitSignOutBean outBean = signCompanyCancel(pdfId, CaUnitSignType.CF_CANCEL, param.getContractCode(),
	            param.getKeyword());
	    if(!ObjectHelper.isEmpty(outBean) && ReturnConstant.SUCCESS.equals(outBean.getRetCode())){
	        String[] tempDocId = {pdfId};
	        CeUtils.deleteFile(tempDocId);
	        result = true;
	    }
		return result;
	}
	
	/**
	 * 合同作废 2016年3月11日 
	 * By 王彬彬
	 * 
	 * @param pdfId
	 * @return
	 */
	public static Ca_UnitSignOutBean signCompanyCancel(String pdfId,
			CaUnitSignType caTidType, String contractCode, String keyWord) {
		Ca_UnitSignInBean inParam = new Ca_UnitSignInBean();

		inParam.setDocId(pdfId);
		inParam.setSubType(contractCode);
		inParam.setBatchNo(CeFolderType.CONTRACT_CANCEL.getName());
		inParam.setBusinessType(DmService.BUSI_TYPE_LOAN);
		inParam.setUnitSignType(caTidType);
		inParam.setKeyWord(keyWord);
		inParam.setKeyWordPos(CaKeyWordPosType.RIGHT);
		inParam.setKeyWordOffset("10");

		ClientPoxy service = new ClientPoxy(ServiceType.Type.CA_UNIT_SIGN);
		Ca_UnitSignOutBean outInfo = (Ca_UnitSignOutBean) service
				.callService(inParam);

		return outInfo;
	}

	/**
	 * 借款协议(保证人)+自然人保证人  或者  借款协议(保证人)+法定代表人+自然人保证人
	 * 2016年10月28日
	 * By 朱静越
	 * @param pdfId 文件id
	 * @param param 主借人信息
	 * @param securityCustomerParam 自然人保证人信息
	 * @param legalMan 是否有法定代表人标识
	 * @return
	 */
	public static String signXy( String pdfId,
			CaCustomerSign param,CaCustomerSign securityCustomerParam,String legalMan) {
		String docId = StringHelper.EMPTY;
        String resultDocId = StringHelper.EMPTY;
        // 夏总签名（乙方）
		Ca_UnitSignOutBean caOutInfo = new Ca_UnitSignOutBean();
		caOutInfo = CaNewUtil.signPersion(pdfId, param.getContractCode());

		// 个人代理签名（甲方）
		Ca_AgentSignOutBean caAgentOut = new Ca_AgentSignOutBean();
		if (ReturnConstant.SUCCESS.equals(caOutInfo.getRetCode())) {
			docId = docId + "," + caOutInfo.getDocId();// 记录临时文件
			caAgentOut = CaNewUtil.signCustomer(caOutInfo.getDocId(), param);
			resultDocId = caAgentOut.getDocId();
		}
		
		
		if (ReturnConstant.SUCCESS.equals(caAgentOut.getRetCode())) { // 甲方签章成功之后进行自然人保证人的签字
			// 丙方
			Ca_AgentSignOutBean caSecurityAgentOut = new Ca_AgentSignOutBean();
			if (StringUtils.isEmpty(legalMan)) {  // 如果没有保证人，
				docId = docId + "," + caAgentOut.getDocId();// 记录临时文件
				caSecurityAgentOut = CaNewUtil.signSecurCustomerNO(caAgentOut.getDocId(), securityCustomerParam,CaKeyWordPosType.RIGHT,"10");
				resultDocId = caSecurityAgentOut.getDocId();
			}else {
				// 法定代表人签字
				Ca_GuaranteeSignOutBean guaranteeSignResult = null; 
			    CaSignRegist caSign = new CaSignRegist();
			    GuaranteeRegisterDao guaranteeRegisterDao = SpringContextHolder.getBean(GuaranteeRegisterDao.class);
			    GuaranteeRegister queryParam = new GuaranteeRegister();
			    queryParam.setContractCode(param.getContractCode());
			    GuaranteeRegister result = guaranteeRegisterDao.get(queryParam);
			    docId = docId + "," + caAgentOut.getDocId();// 记录临时文件
			    caSign.setCertContainer(result.getCertContainer());
			    caSign.setTransID(result.getTransId());
			    caSign.setCompanyName(result.getCompanyName());
			    caSign.setGuaranteeName(result.getGuaranteeName());
			    guaranteeSignResult = CaNewUtil.guaranteeSign(caSign, caAgentOut.getDocId(),CAKeyWord.GUARANTEE_SIGN,param.getContractCode());
			    resultDocId = guaranteeSignResult.getDocId();   // 如果有保证人，那么给返回的DOCID重新赋值。否则返回个人代理签字
			    
				// 需要再次调用代理签章,法定代表人签字
			    docId = docId + "," + guaranteeSignResult.getDocId();// 记录临时文件
			    CaCustomerSign guaranteeCustomerSign = new CaCustomerSign(caSign.getGuaranteeName(), securityCustomerParam.getKeyword(),
			    		securityCustomerParam.getContractCode(), result.getGuaranteeIdNum(), result.getGuaranteeMobile());
			    Ca_AgentSignOutBean caGuaranteeRegisterOut = new Ca_AgentSignOutBean();
			    caGuaranteeRegisterOut = CaNewUtil.signSecurCustomer(guaranteeSignResult.getDocId(), guaranteeCustomerSign,110,"-90");
			    resultDocId = caGuaranteeRegisterOut.getDocId();
			    
				// 自然人保证人签字
				caSecurityAgentOut = CaNewUtil.signSecurCustomer(caGuaranteeRegisterOut.getDocId(), securityCustomerParam,110,"-130");
				docId = docId + "," + caGuaranteeRegisterOut.getDocId();// 记录临时文件
				resultDocId = caSecurityAgentOut.getDocId();
			}
		}
	
		
		// 删除重复文件
		if (StringHelper.isNotEmpty(docId)) {
			String[] tempDocId = docId.replaceFirst(",", "").split(",");
			CeUtils.deleteFile(tempDocId);
		}

		return resultDocId;
	}

	/**
	 * 个人代理签字+全部信和公司签章+自然人保证人  或者  个人代理签字+公司签章+法定代表人+自然人保证人
	 * 2016年10月28日
	 * By 朱静越
	 * @param pdfId  id
	 * @param param 主借人信息
	 * @param securityCustomerParam 自然人保证人信息
	 * @param legalMan 是否有法定代表人字段判断
	 * @return
	 */
	public static String signAllCompanyAndCustomer(String pdfId,
			CaCustomerSign param,CaCustomerSign securityCustomerParam,String legalMan) {
		String docId = StringHelper.EMPTY;
		String resultDocId = StringHelper.EMPTY;

		// 个人代理签字
		Ca_AgentSignOutBean caAgentOut = new Ca_AgentSignOutBean();
		caAgentOut = CaNewUtil.signCustomer(pdfId, param);
		resultDocId = caAgentOut.getDocId();

		// 联合签章
		Ca_UnitSignOutBean caOutUnit = new Ca_UnitSignOutBean();

		// 汇金签章 CATidType
		if (ReturnConstant.SUCCESS.equals(caAgentOut.getRetCode())) {
			docId = docId + "," + caAgentOut.getDocId();// 记录临时文件
			caOutUnit = CaNewUtil.signCompany(caAgentOut.getDocId(),
					CaUnitSignType.HJ, param.getContractCode(),
					CAKeyWord.HJ_SIGN);
		}
		// 汇诚签章 CATidType
		if (ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())) {
			docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
			caOutUnit = CaNewUtil.signCompany(caOutUnit.getDocId(),
					CaUnitSignType.HC, param.getContractCode(),
					CAKeyWord.HC_SIGN);
		}

		// 惠民签章(所有签章都可以后修改为caOutUnit)
		if (ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())) {
			docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
			caOutUnit = CaNewUtil.signCompany(caOutUnit.getDocId(),
					CaUnitSignType.HM, param.getContractCode(),
					CAKeyWord.HM_SIGN);
		}
		// 财富签章
		if (ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())) {
			docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
			caOutUnit = CaNewUtil.signCompany(caOutUnit.getDocId(),
					CaUnitSignType.CF, param.getContractCode(),
					CAKeyWord.CF_SIGN);
			resultDocId = caOutUnit.getDocId();
		}
		
       // 保证人签字
		if(ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())){
			// 丙方
			Ca_AgentSignOutBean caSecurityAgentOut = new Ca_AgentSignOutBean();
			if (StringUtils.isEmpty(legalMan)) { // 法人代表为空，自己签章自然人保证人
				docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
				caSecurityAgentOut = CaNewUtil.signSecurCustomerNO(caOutUnit.getDocId(), securityCustomerParam,CaKeyWordPosType.RIGHT,"10");
				resultDocId = caSecurityAgentOut.getDocId();
			}else {
				// 法定代表人签字
				CaSignRegist caSign = new CaSignRegist();
	            GuaranteeRegisterDao guaranteeRegisterDao = SpringContextHolder.getBean(GuaranteeRegisterDao.class);
	            GuaranteeRegister queryParam = new GuaranteeRegister();
	            queryParam.setContractCode(param.getContractCode());
	            GuaranteeRegister result = guaranteeRegisterDao.get(queryParam);
	            if(!ObjectHelper.isEmpty(result)){
	            	docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
	                caSign.setCertContainer(result.getCertContainer());
	                caSign.setTransID(result.getTransId());
	                caSign.setCompanyName(result.getCompanyName());
	                caSign.setGuaranteeName(result.getGuaranteeName());
	                Ca_GuaranteeSignOutBean guaranteeSignResult = CaNewUtil.guaranteeSign(caSign, caOutUnit.getDocId(),CAKeyWord.SURE_SIGN,param.getContractCode());
				    resultDocId = guaranteeSignResult.getDocId();   // 如果有保证人，那么给返回的DOCID重新赋值。否则返回个人代理签字
				    
				    // 需要再次调用代理签章,法定代表人签字
				    docId = docId + "," + guaranteeSignResult.getDocId();// 记录临时文件
				    CaCustomerSign guaranteeCustomerSign = new CaCustomerSign(caSign.getGuaranteeName(), securityCustomerParam.getKeyword(),
				    		securityCustomerParam.getContractCode(), result.getGuaranteeIdNum(), result.getGuaranteeMobile());
				    Ca_AgentSignOutBean caGuaranteeRegisterOut = new Ca_AgentSignOutBean();
				    caGuaranteeRegisterOut = CaNewUtil.signSecurCustomer(guaranteeSignResult.getDocId(), guaranteeCustomerSign,110,"-90");
				    resultDocId = caGuaranteeRegisterOut.getDocId();
				    
					// 自然人保证人签字
					caSecurityAgentOut = CaNewUtil.signSecurCustomer(caGuaranteeRegisterOut.getDocId(), securityCustomerParam,110,"-130");
					docId = docId + "," + caGuaranteeRegisterOut.getDocId();// 记录临时文件
					resultDocId = caSecurityAgentOut.getDocId();
	            }
			}
         }
		
		
		// 删除重复文件
		if (StringHelper.isNotEmpty(docId)) {
			String[] tempDocId = docId.replaceFirst(",", "").split(",");
			CeUtils.deleteFile(tempDocId);
		}

		return resultDocId;
	}

	/**
	 * 个人签章(乙方)2016年3月11日 By 王彬彬
	 * 夏总
	 * @param pdfId
	 * @return
	 */
	private static Ca_UnitSignOutBean signPersion(String pdfId,
			String contractCode) {
		Ca_UnitSignInBean inParam = new Ca_UnitSignInBean();

		inParam.setDocId(pdfId);
		inParam.setSubType(contractCode);
		inParam.setBatchNo(CeFolderType.CONTRACT_SIGN.getName());
		inParam.setBusinessType(DmService.BUSI_TYPE_LOAN);
		inParam.setUnitSignType(CaUnitSignType.XJ);
		inParam.setKeyWord(CAKeyWord.XJ_SIGN);
		inParam.setKeyWordPos(CaKeyWordPosType.RIGHT);
		inParam.setKeyWordOffset("10");

		ClientPoxy service = new ClientPoxy(ServiceType.Type.CA_UNIT_SIGN);
		Ca_UnitSignOutBean outInfo = (Ca_UnitSignOutBean) service
				.callService(inParam);

		return outInfo;
	}

	/**
	 * 公司签章 2016年3月11日 
	 * By 王彬彬
	 * 
	 * @param pdfId
	 * @return
	 */
	public static Ca_UnitSignOutBean signCompany(String pdfId,
			CaUnitSignType caTidType, String contractCode, String keyWord) {
		Ca_UnitSignInBean inParam = new Ca_UnitSignInBean();

		inParam.setDocId(pdfId);
		inParam.setSubType(contractCode);
		inParam.setBatchNo(CeFolderType.CONTRACT_SIGN.getName());
		inParam.setBusinessType(DmService.BUSI_TYPE_LOAN);
		inParam.setUnitSignType(caTidType);
		inParam.setKeyWord(keyWord);
		inParam.setKeyWordPos(CaKeyWordPosType.RIGHT);
		inParam.setKeyWordOffset("10");

		ClientPoxy service = new ClientPoxy(ServiceType.Type.CA_UNIT_SIGN);
		Ca_UnitSignOutBean outInfo = (Ca_UnitSignOutBean) service
				.callService(inParam);

		return outInfo;
	}

	/**
	 * 代理签章（个人） 2016年4月29日 By 王彬彬
	 * 客户
	 * @param pdfId
	 *            签章文件doc_id
	 * @param param
	 *            签章基本信息
	 * @return
	 */
	public static Ca_AgentSignOutBean signCustomer(String pdfId,
			CaCustomerSign param) {

		Ca_AgentSignInBean inParam = new Ca_AgentSignInBean();
		inParam.setSignerUserName(param.getCustName());
		inParam.setSignerUserIDCard(param.getCertNum());
		inParam.setSignerUserPhone(param.getPhoneNum());
		inParam.setSubType(param.getContractCode());
		inParam.setDocId(pdfId); // docId
		inParam.setBatchNo(CeFolderType.CONTRACT_SIGN.getName()); // 需要存放的文件夹名称
		inParam.setBusinessType(DmService.BUSI_TYPE_LOAN);
		inParam.setKeyWord(param.getKeyword()); 
		inParam.setKeyWordPos(CaKeyWordPosType.RIGHT); 
		inParam.setKeyWordOffset("10");

		ClientPoxy service = new ClientPoxy(ServiceType.Type.CA_AGENT_SIGN);
		Ca_AgentSignOutBean outInfo = (Ca_AgentSignOutBean) service
				.callService(inParam);

		return outInfo;
	}
	
	/**
	 * 代理签章（自然人保证人）签章，如果有法人代表人的时候，签章的位置要放到右下，同时偏移量要发生变化
	 * 2016年10月27日
	 * By 朱静越
	 * @param pdfId docId
	 * @param param
	 * @return
	 */
	public static Ca_AgentSignOutBean signSecurCustomer(String pdfId,
			CaCustomerSign param,int offsetX,String offsetY) {

		Ca_AgentSignInBean inParam = new Ca_AgentSignInBean();
		inParam.setSignerUserName(param.getCustName());
		inParam.setSignerUserIDCard(param.getCertNum());
		inParam.setSignerUserPhone(param.getPhoneNum());
		inParam.setSubType(param.getContractCode());
		inParam.setDocId(pdfId); 
		inParam.setBatchNo(CeFolderType.CONTRACT_SIGN.getName());
		inParam.setBusinessType(DmService.BUSI_TYPE_LOAN);

		inParam.setKeyWord(param.getKeyword()); 
		inParam.setKeyWordXOffset(String.valueOf(offsetX)); 
		inParam.setKeyWordYOffset(offsetY);

		ClientPoxy service = new ClientPoxy(ServiceType.Type.CA_AGENT_SIGN);
		Ca_AgentSignOutBean outInfo = (Ca_AgentSignOutBean) service
				.callService(inParam);

		return outInfo;
	}
	
	/**
	 * 代理签章（自然人保证人），在没有法定代表人的时候，进行调用，使用的还是原来的
	 * 2016年12月1日
	 * By 朱静越
	 * @param pdfId
	 * @param param
	 * @param caKeyWordPosType
	 * @param offSetString
	 * @return 签名的返回id
	 */
	public static Ca_AgentSignOutBean signSecurCustomerNO(String pdfId,
			CaCustomerSign param,CaKeyWordPosType caKeyWordPosType,String offSetString) {

		Ca_AgentSignInBean inParam = new Ca_AgentSignInBean();
		inParam.setSignerUserName(param.getCustName());
		inParam.setSignerUserIDCard(param.getCertNum());
		inParam.setSignerUserPhone(param.getPhoneNum());
		inParam.setSubType(param.getContractCode());
		inParam.setDocId(pdfId); 
		inParam.setBatchNo(CeFolderType.CONTRACT_SIGN.getName());
		inParam.setBusinessType(DmService.BUSI_TYPE_LOAN);

		inParam.setKeyWord(param.getKeyword()); 
		inParam.setKeyWordPos(caKeyWordPosType); 
		inParam.setKeyWordOffset(offSetString);

		ClientPoxy service = new ClientPoxy(ServiceType.Type.CA_AGENT_SIGN);
		Ca_AgentSignOutBean outInfo = (Ca_AgentSignOutBean) service
				.callService(inParam);

		return outInfo;
	}

	/**
	 * 保证人签字 2016年5月20日
	 * By 王彬彬
	 * 
	 * @return 保证人签名
	 */
	public static Ca_GuaranteeSignOutBean guaranteeSign(CaSignRegist caSign,String pdfId,String keyWord,String contractCode) {
		Ca_GuaranteeSignInBean inParam = new Ca_GuaranteeSignInBean();

		inParam.setCertContainer(caSign.getCertContainer()); //注册返回应用名
		inParam.setTransID(caSign.getTransID());//注册返回缓存号
		
		inParam.setDocId(pdfId);
		
		inParam.setBatchNo(CeFolderType.CONTRACT_SIGN.getName());
		inParam.setBusinessType(DmService.BUSI_TYPE_LOAN);
		inParam.setSubType(contractCode);
		
		inParam.setCompanyName(caSign.getCompanyName());
		inParam.setGuaranteeName(caSign.getGuaranteeName());
		inParam.setKeyWord(keyWord);
		inParam.setKeyWordPos(CaKeyWordPosType.RIGHT);
		inParam.setKeyWordOffset("10");

		
		ClientPoxy service = new ClientPoxy(ServiceType.Type.CA_GUARANTEE_SIGN);
		Ca_GuaranteeSignOutBean outInfo = (Ca_GuaranteeSignOutBean) service
				.callService(inParam);

		return outInfo;
	}
}
