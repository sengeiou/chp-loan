/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.serviceGrantCAService.java
 * @Create By 张灏
 * @Create In 2016年4月25日 下午3:58:05
 */
package com.creditharmony.loan.borrow.grant.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.out.ca.Ca_GuaranteeRegisterOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.ContractType;
import com.creditharmony.core.loan.type.LoanCASignType;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFileDao;
import com.creditharmony.loan.borrow.contract.dao.CustInfoDao;
import com.creditharmony.loan.borrow.contract.dao.GuaranteeRegisterDao;
import com.creditharmony.loan.borrow.contract.dao.PaperlessPhotoDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.entity.GuaranteeRegister;
import com.creditharmony.loan.borrow.contract.service.ContractFileService;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.common.consts.CAKeyWord;
import com.creditharmony.loan.common.dao.FileDiskInfoDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.CaCustomerSign;
import com.creditharmony.loan.common.entity.CaSignRegist;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.utils.CaNewUtil;
import com.creditharmony.loan.common.utils.CaUtil;

/**
 * @Class Name GrantCAService
 * @author 张灏
 * @Create In 2016年4月25日
 */
@Service
public class GrantCAService {

    @Autowired
    private CustInfoDao custInfoDao;
    @Autowired
    private LoanCustomerDao loanCustomerDao;
    @Autowired
    private LoanCoborrowerDao loanCoborrowerDao;
    @Autowired
    private PaperlessPhotoDao paperlessPhotoDao;
    @Autowired
    private ContractFileDao contractFileDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ContractFileService contractFileService;
    @Autowired
    private FileDiskInfoDao fileDiskInfoDao;
    @Autowired
    private ApplyLoanInfoDao loanInfoDao;
    @Autowired
    private GuaranteeRegisterDao registerDao;
    @Autowired
	private ContractService contractService;
    
    /**
     * 根据不同模式执行不同签章模式
     * 2016年5月29日
     * By 王彬彬
     * @param loanModel
     * @param applyId
     * @return
     */
	public boolean businessSign(LoanModel loanModel, String applyId) {
		// 如果保证人为空或者已经注册成功则跳过注册
		Contract contract = contractService.findByApplyId(applyId);
		Map<String,Object> retMap=null;
		if (!LoanModel.TG.getCode().equals(loanModel.getCode())) {
			if (YESNO.YES.getCode().equals(contract.getIsRegister())
					|| StringUtils.isEmpty(contract.getLegalMan())) {
				return true;
			} else {
				retMap = registCA(applyId, contract.getLoanCode());
				boolean registResult = (boolean) retMap.get("registResult");
				return registResult;
			}
		}
		else if (LoanModel.TG.getCode().equals(loanModel.getCode())) {
			boolean registResult = signUpCA(contract.getLoanCode());
			return registResult;
		}
		return true;
	}
    
    /**
     *CA注册
     *@author zhanghao
     *@create In 2016年04月25日
     *@return boolean 
     * 
     */
    public Map<String,Object> registCA(String applyId,String loanCode){
        boolean registResult = false;
        String message = "";
        Map<String,Object> resultMap = new HashMap<String,Object>();
        // 获取审批信息 保证人信息   
       	Contract contract = new Contract();
       	GuaranteeRegister queryRegister = new GuaranteeRegister();
       	queryRegister.setLoanCode(loanCode);
	    GuaranteeRegister register = registerDao.get(queryRegister);
	    CaSignRegist caSignRegist = new CaSignRegist();
	    caSignRegist.setGuaranteeName(register.getGuaranteeName());
	    caSignRegist.setGuaranteeIdNum(register.getGuaranteeIdNum());
	    caSignRegist.setGuaranteeMail(register.getGuaranteeMail());
	    caSignRegist.setGuaranteeMobile(register.getGuaranteeMobile());
	    caSignRegist.setGuaranteeTel(register.getGuaranteeTel());
        caSignRegist.setCompanyName(register.getCompanyName());
	    caSignRegist.setCompanyProvince(register.getCompanyProvince());
	    caSignRegist.setCompanyPaperId(register.getCompanyPaperId());
	    caSignRegist.setCompanyRegisteredNo(register.getCompanyRegisteredNo());
	    Ca_GuaranteeRegisterOutBean result = null;
	    for(int j=0;j<5;j++){
		      result =  CaUtil.signCAinfo(caSignRegist);
		      if(ReturnConstant.SUCCESS.equals(result.getRetCode())){
		          contract.setApplyId(applyId);
		          contract.setIsRegister(YESNO.YES.getCode());
		          contract.setContractBackResult("");
		          contractDao.updateContract(contract);
		          registResult = true;
		          register.setTransId(result.getTransID());
		          register.setCertContainer(result.getCertContainer());
		          register.setRegisterDate(new Date());
		          register.preUpdate();
		          registerDao.update(register);
		          break; 
		     } else{
		        message = result.getRetMsg();
		     }
		  }
	    resultMap.put("registResult", registResult);
	    resultMap.put("message", message);
       return resultMap;
        
    }
    
	/**
	 * CA签章
	 *
	 * @author zhanghao
	 * @create In 2016年04月25日
	 * @param
	 * @return boolean
	 * 
	 */
    @Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean signUpCA(String loanCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loanCode", loanCode);
		LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
		List<LoanCoborrower> coborrowerList = loanCoborrowerDao
				.selectByLoanCode(loanCode);
		StringBuffer custName = new StringBuffer();
		StringBuffer cerNum = new StringBuffer();
		custName.append(loanCustomer.getCustomerName());
		cerNum.append(loanCustomer.getCustomerCertNum());
		LoanInfo info = loanInfoDao.selectByLoanCode(param);
		if(info !=null && !"1".equals(info.getLoanInfoOldOrNewFlag())){
			if (!ObjectHelper.isEmpty(coborrowerList)) {
				for (LoanCoborrower c : coborrowerList) {
					custName.append(" ").append(c.getCoboName());
				}
			}
		}
		// 查询不可下载的合同文件
		param.put("downloadFlag", YESNO.NO.getCode());
		List<ContractFile> files = contractFileDao
				.getContractFileByParam(param);
		boolean result = false;
		String contractCode = null;
		if (!ObjectHelper.isEmpty(files)) {
			contractCode = files.get(0).getContractCode();
		}
		LoanCASignType loanCaSignType = null;
		CaCustomerSign customerSign = new CaCustomerSign(custName.toString(),
				CAKeyWord.CUSTOMER_SIGN, contractCode, cerNum.toString(),loanCustomer.getCustomerPhoneFirst());
		if(!ObjectHelper.isEmpty(files)){
		    for (ContractFile file : files) {
		        for(int i = 0;i<5;i++){
		            if (StringUtils.isEmpty(file.getSignDocId())) {
		                result = false;
		                if (ContractType.CONTRACT_PROTOCOL_ASSURER.getName().equals(
		                        file.getContractFileName())) {
		                	customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
		                    loanCaSignType = LoanCASignType.PER_COM_APPROVE;
		                } else if (ContractType.CONTRACT_PROTOCOL.getName().equals(
		                        file.getContractFileName())) {
		                	customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
		                    loanCaSignType = LoanCASignType.PER_COM;
		                } else if (ContractType.CONTRACT_MANAGE.getName().equals(
		                        file.getContractFileName())) {
		                	customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
		                    loanCaSignType = LoanCASignType.ALL_SIGN;
		                } else if (ContractType.CONTRACT_RETURN_MANAGE.getName().equals(file.getContractFileName())) {
		                	customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
		                    loanCaSignType = LoanCASignType.CUSTOMER;
		                }
		                else if (ContractType.CONTRACT_TG.getName().equals(file.getContractFileName())) {
		                	customerSign.setKeyword(CAKeyWord.TG_SIGN);
		                    loanCaSignType = LoanCASignType.TG_SIGN;
		                }
		                String outInfoDocid = CaUtil.caSign(loanCaSignType,
		                        file.getDocId(), customerSign);
		                if (!ObjectHelper.isEmpty(outInfoDocid)) {
		                    file.setSignDocId(outInfoDocid);
		                    contractFileService.updateCtrFile(file);
		                    result = true;
		                    break;
		                } 
		            }else{
		         result = true;
		    }}
		    if(!result){
		        break;
		    }
		    } }else{
		        result = true;
		    }
		return result;
	}
    
    /**
     * 新版申请表签章处理,因为合同文件名称不一样，所以需要新添加方法调用
     * 2016年10月28日
     * By 朱静越
     * @param loanCode 借款编号
     * @return
     */
    @Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean signUpCANew(String loanCode,LoanCoborrower coborrower) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loanCode", loanCode);
		LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
		StringBuffer custName = new StringBuffer();
		StringBuffer cerNum = new StringBuffer();
		custName.append(loanCustomer.getCustomerName());
		cerNum.append(loanCustomer.getCustomerCertNum());
		// 查询不可下载的合同文件
		param.put("downloadFlag", YESNO.NO.getCode());
		List<ContractFile> files = contractFileDao
				.getContractFileByParam(param);
		boolean result = false;
		String contractCode = null;
		String legalMan = null;
		if (!ObjectHelper.isEmpty(files)) {
			contractCode = files.get(0).getContractCode();
		}
		LoanCASignType loanCaSignType = null;
		CaCustomerSign customerSign = new CaCustomerSign(custName.toString(),
				CAKeyWord.CUSTOMER_SIGN, contractCode, cerNum.toString(),loanCustomer.getCustomerPhoneFirst());
		//TODO 获得自然人保证人信息
		CaCustomerSign securityCustomerSign = new CaCustomerSign(coborrower.getCoboName(), CAKeyWord.HC_SIGN, contractCode, 
				coborrower.getCoboCertNum(), coborrower.getCoboMobile());
		if(!ObjectHelper.isEmpty(files)){
		    for (ContractFile file : files) {
		        for(int i = 0;i<5;i++){
		            if (StringUtils.isEmpty(file.getSignDocId())) {
		                result = false;
		                if (ContractType.CONTRACT_PROTOCOL2_ASSURER.getName().equals( // 借款协议(保证人)
		                        file.getContractFileName())) {
		                	customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN);
		                	securityCustomerSign.setKeyword(CAKeyWord.HC_SIGN); 
		                    loanCaSignType = LoanCASignType.PER_COM_APPROVE; // 甲乙+保证人
		                } else if (ContractType.CONTRACT_MANAGE2_ASSURER.getName().equals( // 信用咨询及管理服务协议(保证人)
		                        file.getContractFileName())) {
		                	customerSign.setKeyword(CAKeyWord.CUSTOMER_SIGN); 
		                	securityCustomerSign.setKeyword(CAKeyWord.SURE_SIGN); 
		                    loanCaSignType = LoanCASignType.ALL_SIGN_APPROVE; // 全部签名+保证人
		                } else if (ContractType.CONTRACT_RETURN_MANAGE2_ASSURER.getName().equals(file.getContractFileName())) { // 还款管理
		                	customerSign.setKeyword(CAKeyWord.LOAN_SIGN);
		                    loanCaSignType = LoanCASignType.CUSTOMER;
		                }
		                // TODO 查询该单子是否有法人代表人
		                Contract contract = contractService.findByContractCode(contractCode);
		                if (ObjectHelper.isNotEmpty(contract)&&StringUtils.isNotEmpty(contract.getLegalMan())) {
		                	legalMan = contract.getLegalMan();
						}
		                String outInfoDocid = CaNewUtil.caSign(loanCaSignType,
		                        file.getDocId(), customerSign,securityCustomerSign,legalMan);
		                if (!ObjectHelper.isEmpty(outInfoDocid)) {
		                    file.setSignDocId(outInfoDocid);
		                    contractFileService.updateCtrFile(file);
		                    result = true;
		                    break;
		                } 
		            }else{
		         result = true;
		    }}
		    if(!result){
		        break;
		    }
		    } }else{
		        result = true;
		    }
		return result;
	}
    
    /**
     *CA签章作废 
     *@author zhanghao
     *@Create In 2016-05-23 
     *@param loanCode
     *@return boolean 
     * 
     */ 
    public boolean caSignCancel(String contractCode){
        boolean result = true;
        boolean tempResult = true;
        List<ContractFile> files = contractFileDao
                .findContractFileByContractCode(contractCode);
       LoanCASignType loanCASignType = LoanCASignType.CANCEL_SIGN;
        //针对不可下载的合同文件，加盖废章并清除合同文件表中加盖的文件ID 
        if(ObjectHelper.isNotEmpty(files)){
            CaCustomerSign customerSign = null;
            for(ContractFile file:files){
                if(YESNO.NO.getCode().equals(file.getDownloadFlag())&&StringUtils.isNotEmpty(file.getSignDocId())){
                    if(ContractType.CONTRACT_RETURN_MANAGE.getName().equals(file.getContractFileName())||
                    		ContractType.CONTRACT_RETURN_MANAGE2_ASSURER.getName().equals(file.getContractFileName())){
                        customerSign = new CaCustomerSign(null,CAKeyWord.HJ_CANCEL_B,file.getContractCode(),null,null);
                    }else if(ContractType.CONTRACT_PROTOCOL.getName().equals(file.getContractFileName())||
                            ContractType.CONTRACT_PROTOCOL_ASSURER.getName().equals(file.getContractFileName())||
                            ContractType.CONTRACT_MANAGE.getName().equals(file.getContractFileName()) ||
                            ContractType.CONTRACT_PROTOCOL2_ASSURER.getName().equals(file.getContractFileName()) ||
                            ContractType.CONTRACT_MANAGE2_ASSURER.getName().equals(file.getContractFileName())){
                        customerSign = new CaCustomerSign(null,CAKeyWord.HJ_CANCEL_A,file.getContractCode(),null,null);
                    }
                    tempResult = CaUtil.caSignCancel(loanCASignType, file.getSignDocId(), customerSign);
                    if(!tempResult){
                        result = tempResult; 
                    }else{
                        contractFileDao.clearSignDocId(file); 
                    }
                }
            }
        }
        return result;
    }
}
