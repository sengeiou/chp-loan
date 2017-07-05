/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.viewCustomerView.java
 * @Create By 张灏
 * @Create In 2015年12月1日 下午4:58:04
 */
package com.creditharmony.loan.borrow.contract.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.common.entity.LoanBank;
/**
 * @Class Name CustomerView
 * @author 张灏
 * @Create In 2015年12月1日
 */
public class CustomerView extends BaseBusinessView {
   
    // 客户ID
    private String customerId;
    // 客户编码
    private String customerCode; 
    // 借款编号
    private String loanCode;  
    // 客户姓名
    private String customerName; 
    // 性别
    private String customerSex;
    // 性别名称
    private String customerSexName;
    // 出生日期
    private Date customerBirthday;
    // 身份证有效期开始时间
    private Date idStartDay;
    // 身份证有效期结束时间
    private Date idEndDay;
    // 婚姻状况
    private String dictMarry;  
    // 婚姻状况名称
    private String dictMarryName;
    // 学历
    private String dictEducation;
    // 学历名称
    private String dictEducationName;
    // 证件类型
    private String dictCertType;
    // 证件类型名称
    private String dictCertTypeName;
    // 标识（Code）
    private String loanFlagCode;
    // 标识 名称
    private String loanFlag;
    // 证件号码
    private String customerCertNum;  
    // 保证人名称
    private String companyName;
    // 法人代表
    private String legalMan;
    // 经营地址
    private String maddress;
    // 客户AppSign
    private String appSignFlag;
    // 保证人AppSign
    private String legalAppSign;
    // 预约签署日期
    private Date contractDueDay; 
    // 当前日期
    private Date curDay;
    // 合同编号
    private String contractCode;
    // 身份证照片ID
    private String idCardId;
    // 现场照片ID
    private String currPlotId;
    // 开户行    
    private LoanBank loanBank = new LoanBank();  
    // 共借人
    private  List<LoanCoborrower> coborrowerList = new ArrayList<LoanCoborrower>();
	// 合同文件列表
    private List<ContractFile> files = new ArrayList<ContractFile>();
    // 模式
    private String model;
    // 模式名字
    private String modelName;
    //
    private String paperlessFlag;
    // 当前状态
    private String dictLoanStatus;
    private String imageUrl;
    private String isStoreAssistant;
    // 身份验证标识
    private String idValidFlag;
    //新旧标识
    private String loanInfoOldOrNewFlag;
    //复议标识
    private String reconsiderFlag;
    //建议放弃按钮 显示标识
    private String proposeFlag="1";
    
    private LoanInfo loanInfo=new LoanInfo();
    
    
    
	public LoanInfo getLoanInfo() {
		return loanInfo;
	}
	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}
	public String getProposeFlag() {
		return proposeFlag;
	}
	public void setProposeFlag(String proposeFlag) {
		this.proposeFlag = proposeFlag;
	}
	public String getReconsiderFlag() {
		return reconsiderFlag;
	}
	public void setReconsiderFlag(String reconsiderFlag) {
		this.reconsiderFlag = reconsiderFlag;
	}
    //拆单标记
    private String issplit;
    	
    //节点
    private String stepName;
    
    //原loancode
    private String oldLoanCode;
    
    
    
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getIssplit() {
		return issplit;
	}
	public void setIssplit(String issplit) {
		this.issplit = issplit;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public void setLoanCode(String loanCode) {
		this.loanCode = loanCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerSex() {
		return customerSex;
	}
	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}
	public Date getCustomerBirthday() {
		return customerBirthday;
	}
	public void setCustomerBirthday(Date customerBirthday) {
		this.customerBirthday = customerBirthday;
	}
	public Date getIdStartDay() {
		return idStartDay;
	}
	public void setIdStartDay(Date idStartDay) {
		this.idStartDay = idStartDay;
	}
	public Date getIdEndDay() {
		return idEndDay;
	}
	public void setIdEndDay(Date idEndDay) {
		this.idEndDay = idEndDay;
	}
	public String getDictMarry() {
		return dictMarry;
	}
	public void setDictMarry(String dictMarry) {
		this.dictMarry = dictMarry;
	}
	public String getDictEducation() {
		return dictEducation;
	}
	public void setDictEducation(String dictEducation) {
		this.dictEducation = dictEducation;
	}
	public String getDictCertType() {
        return dictCertType;
    }
    public void setDictCertType(String dictCertType) {
        this.dictCertType = dictCertType;
    }
    public String getCustomerCertNum() {
		return customerCertNum;
	}
	public void setCustomerCertNum(String customerCertNum) {
		this.customerCertNum = customerCertNum;
	}
    public Date getContractDueDay() {
        return contractDueDay;
    }
    public void setContractDueDay(Date contractDueDay) {
        this.contractDueDay = contractDueDay;
    }
    public Date getCurDay() {
        return curDay;
    }
    public void setCurDay(Date curDay) {
        this.curDay = curDay;
    }
    /**
     * @return the contractCode
     */
    public String getContractCode() {
        return contractCode;
    }
    /**
     * @param contractCode the String contractCode to set
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    public LoanBank getLoanBank() {
        return loanBank;
    }
    public void setLoanBank(LoanBank loanBank) {
        this.loanBank = loanBank;
    }
	/**
     * @return the coborrowerList
     */
    public List<LoanCoborrower> getCoborrowerList() {
        return coborrowerList;
    }
    /**
     * @param coborrowerList the List<LoanCoborrower> coborrowerList to set
     */
    public void setCoborrowerList(List<LoanCoborrower> coborrowerList) {
        this.coborrowerList = coborrowerList;
    }
    /**
     * @return the files
     */
    public List<ContractFile> getFiles() {
        return files;
    }
    /**
     * @param files the List<ContractFile> files to set
     */
    public void setFiles(List<ContractFile> files) {
        this.files = files;
    }
    public String getCustomerSexName() {
		return customerSexName;
	}
	public void setCustomerSexName(String customerSexName) {
		this.customerSexName = customerSexName;
	}
	public String getDictMarryName() {
		return dictMarryName;
	}
	public void setDictMarryName(String dictMarryName) {
		this.dictMarryName = dictMarryName;
	}
	public String getDictEducationName() {
		return dictEducationName;
	}
	public void setDictEducationName(String dictEducationName) {
		this.dictEducationName = dictEducationName;
	}
	public String getDictCertTypeName() {
		return dictCertTypeName;
	}
	public void setDictCertTypeName(String dictCertTypeName) {
		this.dictCertTypeName = dictCertTypeName;
	}
    /**
     * @return the loanFlagCode
     */
    public String getLoanFlagCode() {
        return loanFlagCode;
    }
    /**
     * @param loanFlagCode the String loanFlagCode to set
     */
    public void setLoanFlagCode(String loanFlagCode) {
        this.loanFlagCode = loanFlagCode;
    }
    /**
     * @return the loanFlag
     */
    public String getLoanFlag() {
        return loanFlag;
    }
    /**
     * @param loanFlag the String loanFlag to set
     */
    public void setLoanFlag(String loanFlag) {
        this.loanFlag = loanFlag;
    }
    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }
    /**
     * @param customerId the String customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * @param companyName the String companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    /**
     * @return the legalMan
     */
    public String getLegalMan() {
        return legalMan;
    }
    /**
     * @param legalMan the String legalMan to set
     */
    public void setLegalMan(String legalMan) {
        this.legalMan = legalMan;
    }
    /**
     * @return the maddress
     */
    public String getMaddress() {
        return maddress;
    }
    /**
     * @param maddress the String maddress to set
     */
    public void setMaddress(String maddress) {
        this.maddress = maddress;
    }
    /**
     * @return the appSignFlag
     */
    public String getAppSignFlag() {
        return appSignFlag;
    }
    /**
     * @param appSignFlag the String appSignFlag to set
     */
    public void setAppSignFlag(String appSignFlag) {
        this.appSignFlag = appSignFlag;
    }
    /**
     * @return the legalAppSign
     */
    public String getLegalAppSign() {
        return legalAppSign;
    }
    /**
     * @param legalAppSign the String legalAppSign to set
     */
    public void setLegalAppSign(String legalAppSign) {
        this.legalAppSign = legalAppSign;
    }
    public String getIdCardId() {
        return idCardId;
    }
    public void setIdCardId(String idCardId) {
        this.idCardId = idCardId;
    }
    public String getCurrPlotId() {
        return currPlotId;
    }
    public void setCurrPlotId(String currPlotId) {
        this.currPlotId = currPlotId;
    }
    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }
    /**
     * @param model the String model to set
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }
    /**
     * @param modelName the String modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    /**
     * @return the paperlessFlag
     */
    public String getPaperlessFlag() {
        return paperlessFlag;
    }
    /**
     * @param paperlessFlag the String paperlessFlag to set
     */
    public void setPaperlessFlag(String paperlessFlag) {
        this.paperlessFlag = paperlessFlag;
    }
    /**
     * @return the dictLoanStatus
     */
    public String getDictLoanStatus() {
        return dictLoanStatus;
    }
    /**
     * @param dictLoanStatus the String dictLoanStatus to set
     */
    public void setDictLoanStatus(String dictLoanStatus) {
        this.dictLoanStatus = dictLoanStatus;
    }
    /**
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }
    /**
     * @param imageUrl the String imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    /**
     * @return the isStoreAssistant
     */
    public String getIsStoreAssistant() {
        return isStoreAssistant;
    }
    /**
     * @param isStoreAssistant the String isStoreAssistant to set
     */
    public void setIsStoreAssistant(String isStoreAssistant) {
        this.isStoreAssistant = isStoreAssistant;
    }
    /**
     * @return the idValidFlag
     */
    public String getIdValidFlag() {
        return idValidFlag;
    }
    /**
     * @param idValidFlag the String idValidFlag to set
     */
    public void setIdValidFlag(String idValidFlag) {
        this.idValidFlag = idValidFlag;
    }
	public String getLoanInfoOldOrNewFlag() {
		return loanInfoOldOrNewFlag;
	}
	public void setLoanInfoOldOrNewFlag(String loanInfoOldOrNewFlag) {
		this.loanInfoOldOrNewFlag = loanInfoOldOrNewFlag;
	}
	public String getOldLoanCode() {
		return oldLoanCode;
	}
	public void setOldLoanCode(String oldLoanCode) {
		this.oldLoanCode = oldLoanCode;
	}
	
    
   
	
}
