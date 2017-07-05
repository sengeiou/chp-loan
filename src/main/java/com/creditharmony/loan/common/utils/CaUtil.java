package com.creditharmony.loan.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.creditharmony.adapter.bean.in.ca.Ca_AgentSignInBean;
import com.creditharmony.adapter.bean.in.ca.Ca_GuaranteeRegisterInBean;
import com.creditharmony.adapter.bean.in.ca.Ca_GuaranteeSignInBean;
import com.creditharmony.adapter.bean.in.ca.Ca_UnitSignInBean;
import com.creditharmony.adapter.bean.out.ca.Ca_AgentSignOutBean;
import com.creditharmony.adapter.bean.out.ca.Ca_GuaranteeRegisterOutBean;
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
public class CaUtil {

	/**
	 * 日志对象
	 */
	protected static Logger logger = LoggerFactory.getLogger(CaUtil.class);

	/**
	 * 签章 2016年4月29日 By 王彬彬
	 * 
	 * @param loanCASignType
	 *            签章类型文件枚举
	 * @param pdfId
	 *            签章文件
	 * @param param
	 *            签章信息
	 * @return CA签章不成功，则返回null
	 */
	public static String caSign(LoanCASignType loanCASignType, String pdfId,
			CaCustomerSign param) {
		String docId = StringHelper.EMPTY;
		// 借款协议或者待保证人
		if (loanCASignType.getCode().equals(
				LoanCASignType.PER_COM_APPROVE.getCode())
				|| loanCASignType.getCode().equals(
						LoanCASignType.PER_COM.getCode())||loanCASignType.getCode().equals(LoanCASignType.PER_COM_ALL_SIGN_APPROVE.getCode())) {
			docId = signXy(loanCASignType, pdfId, param);
		}
		// 全部签名
		if (loanCASignType.getCode().equals(LoanCASignType.ALL_SIGN.getCode())) {
			docId = signAllCompanyAndCustomer(pdfId, param);
		}
		// 客户代理签名
		if (loanCASignType.getCode().equals(LoanCASignType.CUSTOMER.getCode())) {
			docId = CaUtil.signCustomer(pdfId, param).getDocId();
		}
		
		// 富友协议签字
		if (loanCASignType.getCode().equals(LoanCASignType.TG_SIGN.getCode())) {
			docId = CaUtil.signCustomer(pdfId, param).getDocId();
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
	 * 借款协议或者待保证人 2016年4月29日 By 王彬彬
	 * 
	 * @param loanCASignType
	 *            CA签章类型（LoanCASignType.PER_COM或者 PER_COM_APPROVE）
	 * @param pdfId
	 *            需要签章的文件
	 * @param param
	 *            CA基本信息
	 * @return
	 */
	public static String signXy(LoanCASignType loanCASignType, String pdfId,
			CaCustomerSign param) {
		String docId = StringHelper.EMPTY;
        String resultDocId = StringHelper.EMPTY;
		Ca_UnitSignOutBean caOutInfo = new Ca_UnitSignOutBean();
		// 夏总签名（乙方）
		caOutInfo = CaUtil.signPersion(pdfId, param.getContractCode());

		Ca_AgentSignOutBean caAgentOut = new Ca_AgentSignOutBean();
		Ca_GuaranteeSignOutBean guaranteeSignResult = null; // 保证人签字
		// 个人代理签名（甲方）
		if (ReturnConstant.SUCCESS.equals(caOutInfo.getRetCode())) {
			docId = docId + "," + caOutInfo.getDocId();// 记录临时文件
			// 个人代理签字
			caAgentOut = CaUtil.signCustomer(caOutInfo.getDocId(), param);
			resultDocId = caAgentOut.getDocId();
		}

		// 保证人签字
		if (loanCASignType.getCode().equals(
				LoanCASignType.PER_COM_APPROVE.getCode())) {
		    CaSignRegist caSign = new CaSignRegist();
		    GuaranteeRegisterDao guaranteeRegisterDao = SpringContextHolder.getBean(GuaranteeRegisterDao.class);
		    GuaranteeRegister queryParam = new GuaranteeRegister();
		    queryParam.setContractCode(param.getContractCode());
		    GuaranteeRegister result = guaranteeRegisterDao.get(queryParam);
		    caSign.setCertContainer(result.getCertContainer());
		    caSign.setTransID(result.getTransId());
		    caSign.setCompanyName(result.getCompanyName());
		    caSign.setGuaranteeName(result.getGuaranteeName());
		    guaranteeSignResult = CaUtil.guaranteeSign(caSign, caAgentOut.getDocId(),CAKeyWord.GUARANTEE_SIGN,param.getContractCode());
		    resultDocId = guaranteeSignResult.getDocId();   // 如果有保证人，那么给返回的DOCID重新赋值。否则返回个人代理签字
			docId = docId + "," + caAgentOut.getDocId();// 记录临时文件
			
			// 调用代理签章，保证人签字，签章升级使用
			docId = docId + "," + guaranteeSignResult.getDocId();// 记录临时文件
		    CaCustomerSign guaranteeCustomerSign = new CaCustomerSign(caSign.getGuaranteeName(), CAKeyWord.GUARANTEE_SIGN,
		    		param.getContractCode(), result.getGuaranteeIdNum(), result.getGuaranteeMobile());
		    Ca_AgentSignOutBean caGuaranteeRegisterOut = new Ca_AgentSignOutBean();
		    caGuaranteeRegisterOut = CaNewUtil.signSecurCustomer(guaranteeSignResult.getDocId(), guaranteeCustomerSign,110,"-90");
		    resultDocId = caGuaranteeRegisterOut.getDocId();
		}
		
		//全部签名
		if(loanCASignType.getCode().equals(LoanCASignType.PER_COM_ALL_SIGN_APPROVE.getCode())){
			Ca_UnitSignOutBean caOutUnit = new Ca_UnitSignOutBean();

			 resultDocId = StringHelper.EMPTY;
			// 汇金签章 CATidType
			if (ReturnConstant.SUCCESS.equals(caAgentOut.getRetCode())) {
				docId = docId + "," + caAgentOut.getDocId();// 记录临时文件
				caOutUnit = CaUtil.signCompany(caAgentOut.getDocId(),
						CaUnitSignType.HJ, param.getContractCode(),
						CAKeyWord.HJ_SECOND_SIGN);
				System.out.println("doc_id==========="+caOutUnit.getDocId());
			}
			// 汇诚签章 CATidType
			if (ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())) {
				docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
				caOutUnit = CaUtil.signCompany(caOutUnit.getDocId(),
						CaUnitSignType.HC, param.getContractCode(),
						CAKeyWord.HC_SIGN);
				System.out.println("doc_id==========="+caOutUnit.getDocId());
			}

			// 惠民签章(所有签章都可以后修改为caOutUnit)
			if (ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())) {
				docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
				caOutUnit = CaUtil.signCompany(caOutUnit.getDocId(),
						CaUnitSignType.HM, param.getContractCode(),
						CAKeyWord.HM_SIGN);
				System.out.println("doc_id==========="+caOutUnit.getDocId());
			}
			// 财富签章
			if (ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())) {
				docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
				caOutUnit = CaUtil.signCompany(caOutUnit.getDocId(),
						CaUnitSignType.CF, param.getContractCode(),
						CAKeyWord.CF_SIGN);
				System.out.println("doc_id==========="+caOutUnit.getDocId());
				resultDocId = caOutUnit.getDocId();// 记录临时文件
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
	 * 车借1.6合同签章（借款人+出借人+借款人+汇金）
	 * 
	 * @param loanCASignType
	 *            CA签章类型（LoanCASignType.PER_COM或者 PER_COM_APPROVE）
	 * @param pdfId
	 *            需要签章的文件
	 * @param param
	 *            CA基本信息
	 * @return
	 */
	public static String signHjXy(LoanCASignType loanCASignType, String pdfId,
			CaCustomerSign param) {
		String docId = StringHelper.EMPTY;
        String resultDocId = StringHelper.EMPTY;
		Ca_UnitSignOutBean caOutInfo = new Ca_UnitSignOutBean();
		// 夏总签名（乙方）
		caOutInfo = CaUtil.signPersion(pdfId, param.getContractCode());

		Ca_AgentSignOutBean caAgentOut = new Ca_AgentSignOutBean();
		Ca_GuaranteeSignOutBean guaranteeSignResult = null; // 保证人签字
		// 个人代理签名（甲方）
		if (ReturnConstant.SUCCESS.equals(caOutInfo.getRetCode())) {
			docId = docId + "," + caOutInfo.getDocId();// 记录临时文件
			// 个人代理签字
			caAgentOut = CaUtil.signCustomer(caOutInfo.getDocId(), param);
			resultDocId = caAgentOut.getDocId();
		}
		
		//汇金签章
		if(loanCASignType.getCode().equals(LoanCASignType.COMPANY_HJ.getCode())){
			 resultDocId = StringHelper.EMPTY;
			// 汇金签章 CATidType
			if (ReturnConstant.SUCCESS.equals(caAgentOut.getRetCode())) {
				docId = docId + "," + caAgentOut.getDocId();// 记录临时文件
				Ca_UnitSignOutBean caOutUnit = CaUtil.signCompany(caAgentOut.getDocId(),
						CaUnitSignType.HJ, param.getContractCode(),
						CAKeyWord.HJ_SECOND_SIGN);
				resultDocId = caOutUnit.getDocId();// 记录临时文件
				System.out.println("doc_id==========="+caOutUnit.getDocId());
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
	 * 个人代理签字+全部信和公司签章 2016年4月29日 By 王彬彬
	 * 
	 * @param pdfId
	 *            签章文件
	 * @param param
	 *            签章基本信息
	 * @return
	 */
	public static String signAllCompanyAndCustomer(String pdfId,
			CaCustomerSign param) {
		String docId = StringHelper.EMPTY;
		String resultDocId = StringHelper.EMPTY;

		Ca_AgentSignOutBean caAgentOut = new Ca_AgentSignOutBean();
		// 个人代理签字
		caAgentOut = CaUtil.signCustomer(pdfId, param);
		resultDocId = caAgentOut.getDocId();

		Ca_UnitSignOutBean caOutUnit = new Ca_UnitSignOutBean();

		// 汇金签章 CATidType
		if (ReturnConstant.SUCCESS.equals(caAgentOut.getRetCode())) {
			docId = docId + "," + caAgentOut.getDocId();// 记录临时文件
			caOutUnit = CaUtil.signCompany(caAgentOut.getDocId(),
					CaUnitSignType.HJ, param.getContractCode(),
					CAKeyWord.HJ_SIGN);
		}
		// 汇诚签章 CATidType
		if (ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())) {
			docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
			caOutUnit = CaUtil.signCompany(caOutUnit.getDocId(),
					CaUnitSignType.HC, param.getContractCode(),
					CAKeyWord.HC_SIGN);
		}

		// 惠民签章(所有签章都可以后修改为caOutUnit)
		if (ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())) {
			docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
			caOutUnit = CaUtil.signCompany(caOutUnit.getDocId(),
					CaUnitSignType.HM, param.getContractCode(),
					CAKeyWord.HM_SIGN);
		}
		// 财富签章
		if (ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())) {
			docId = docId + "," + caOutUnit.getDocId();// 记录临时文件
			caOutUnit = CaUtil.signCompany(caOutUnit.getDocId(),
					CaUnitSignType.CF, param.getContractCode(),
					CAKeyWord.CF_SIGN);
			resultDocId = caOutUnit.getDocId();
		}
       // 保证人签字
		if(ReturnConstant.SUCCESS.equals(caOutUnit.getRetCode())){
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
                Ca_GuaranteeSignOutBean guaranteeSignResult = CaUtil.guaranteeSign(caSign, caOutUnit.getDocId(),CAKeyWord.SURE_SIGN,param.getContractCode());
                caOutUnit.setDocId(guaranteeSignResult.getDocId());
                caOutUnit.setRetCode(guaranteeSignResult.getRetCode());
                caOutUnit.setRetMsg(guaranteeSignResult.getRetMsg());
                caOutUnit.setSerialNum(guaranteeSignResult.getSerialNum());
                resultDocId = guaranteeSignResult.getDocId();
                
                // 调用代理签章，保证人签字，签章升级使用
    			docId = docId + "," + guaranteeSignResult.getDocId();// 记录临时文件
    		    CaCustomerSign guaranteeCustomerSign = new CaCustomerSign(caSign.getGuaranteeName(), CAKeyWord.SURE_SIGN,
    		    		param.getContractCode(), result.getGuaranteeIdNum(), result.getGuaranteeMobile());
    		    Ca_AgentSignOutBean caGuaranteeRegisterOut = new Ca_AgentSignOutBean();
    		    caGuaranteeRegisterOut = CaNewUtil.signSecurCustomer(guaranteeSignResult.getDocId(), guaranteeCustomerSign,110,"-90");
    		    resultDocId = caGuaranteeRegisterOut.getDocId();
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
	 * 车借1.6合同签章（个人签字+汇金）
	 *
	 * @param pdfId
	 *            签章文件
	 * @param param
	 *            签章基本信息
	 * @return
	 */
	public static String signHJCompanyAndCustomer(String pdfId,
															  CaCustomerSign param) {
		String docId = StringHelper.EMPTY;
		String resultDocId = StringHelper.EMPTY;
		Ca_AgentSignOutBean caAgentOut = new Ca_AgentSignOutBean();
		// 个人代理签字
		caAgentOut = CaUtil.signCustomer(pdfId, param);

		Ca_UnitSignOutBean caOutUnit = new Ca_UnitSignOutBean();

		// 汇金签章 CATidType
		if (ReturnConstant.SUCCESS.equals(caAgentOut.getRetCode())) {
			docId = docId + "," + caAgentOut.getDocId();// 记录临时文件
			caOutUnit = CaUtil.signCompany(caAgentOut.getDocId(),
					CaUnitSignType.HJ, param.getContractCode(),
					CAKeyWord.HJ_SIGN);
			resultDocId = caOutUnit.getDocId();
		}

		// 删除重复文件
		if (StringHelper.isNotEmpty(docId)) {
			String[] tempDocId = docId.replaceFirst(",", "").split(",");
			CeUtils.deleteFile(tempDocId);
		}

		return resultDocId;
	}

	/**
	 * 个人签章(乙方) 2016年3月11日 By 王彬彬
	 * 
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
	 * 
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
		inParam.setDocId(pdfId);
		inParam.setBatchNo(CeFolderType.CONTRACT_SIGN.getName());
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
	 * 代理签章（保证人签字），使用坐标的形式进行
	 * 2016年12月1日
	 * By 朱静越
	 * @param pdfId
	 * @param param
	 * @param offsetX
	 * @param offsetY
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
	 * 保证人信息注册 2016年4月29日 By 王彬彬
	 * 
	 * @param caSign
	 *            注册信息
	 * @return
	 */
	public static Ca_GuaranteeRegisterOutBean signCAinfo(CaSignRegist caSign) {
		Ca_GuaranteeRegisterInBean caInfo = new Ca_GuaranteeRegisterInBean();
		caInfo.setGuaranteeName(caSign.getGuaranteeName());
		caInfo.setGuaranteeIdNum(caSign.getGuaranteeIdNum());
		caInfo.setGuaranteeMail(caSign.getGuaranteeMail());
		caInfo.setGuaranteeMobile(caSign.getGuaranteeMobile());
		caInfo.setGuaranteeTel(caSign.getGuaranteeTel());

		caInfo.setCompanyName(caSign.getCompanyName());
		caInfo.setCompanyProvince(caSign.getCompanyProvince());
		caInfo.setCompanyPaperId(caSign.getCompanyPaperId());
		caInfo.setCompanyRegisteredNo(caSign.getCompanyRegisteredNo());

		ClientPoxy service = new ClientPoxy(
				ServiceType.Type.CA_GUARANTEE_REGISTER);
		Ca_GuaranteeRegisterOutBean outInfo = (Ca_GuaranteeRegisterOutBean) service
				.callService(caInfo);
		logger.info("企业注册成功：" + outInfo.getTransID());
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
	
	/**
	 * 公司签章 2016年3月11日 
	 * By 王彬彬
	 * 
	 * @param pdfId
	 * @return
	 */
	public static Ca_UnitSignOutBean signEndCompany(String pdfId,
			CaUnitSignType caTidType, String contractCode, String keyWord) {
		Ca_UnitSignInBean inParam = new Ca_UnitSignInBean();

		inParam.setDocId(pdfId);
		inParam.setSubType(contractCode);
		inParam.setBatchNo(CeFolderType.OTHER_UPLOAD.getName());
		inParam.setBusinessType(DmService.BUSI_TYPE_LOAN);
		inParam.setUnitSignType(caTidType);
		inParam.setKeyWord(keyWord);
		inParam.setKeyWordPos(CaKeyWordPosType.CENTRE);
		inParam.setKeyWordOffset("0");

		ClientPoxy service = new ClientPoxy(ServiceType.Type.CA_UNIT_SIGN);
		Ca_UnitSignOutBean outInfo = (Ca_UnitSignOutBean) service
				.callService(inParam);

		return outInfo;
	}

}
