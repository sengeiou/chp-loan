package com.creditharmony.loan.borrow.serve.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.creditharmony.common.util.StringHelper;
import com.creditharmony.loan.borrow.serve.entity.ContractFileSendEmail;

/**
 * 电子协议申请
 * 
 * @Class Name ContractFileSendEmailView
 * @author 方强
 * @Create In 2016年11月7日
 */
public class ContractFileSendEmailView extends ContractFileSendEmail {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9061426735736834117L;
	
	private String orgCode;
	private String orgCodeIds;
	private String isDelete = "0";	//0表示未删除，1标识已删除（默认未删除）
	private String ids;
	private List<String> idsList;
	private String operateStep;		//操作步骤，用来记录历史
	private String excelFlag;		//导出Excel标记
	private String flag;           //合同还是通知书标志
	private String contractcode;  //合同编号
	private String sendStatus;  //发送状态
	private String id;  //合同编号
	private String applyId;			//APPLY_ID
	private String loanCode;		//借款编号
	private String customerName;	//客户姓名
	private String contractCode;	//合同编号
	private String coboCertNum;     //证件号码
	private String storeName;		//门店名称
	private String applyFor;        //申请人
	private Date applyTime;			//门店申请日期
	private String receiverName;	//收件人姓名
    private String receiverSex;      //收件人性别
	private String receiverEmail;	//收件人邮箱
	private String receiverPhone;	//收件人电话
	private String fileType;		//文件类型(0:合同; 1:结清证明)
	private String fileTypeName;
	private String sendStatusLab;
	private String sendStatusName;//电子协议申请状态(0:未申请; 1:待审核; 2:已发送; 3:申请退回;)
	private String productType;		//产品类型
	private String loanMonths;		//借款期限
	private String contractAmount;	//合同金额
	private Date contractEndDay;	//合同到期日期
	private Date settleDay;			//结清日期
	private String creditStatus;	//借款状态(0:还款中; 1:逾期; 2:结清; 3:提前结清; 4:提前结清待审核; 5:结清待确认)
	private String creditStatusName;
	private String pdfId;			//下载PDF文件的id
	private String wordId;			//下载word文件的id
	private String docId;			//下载PDF文件的id
	private String settleCause;    //结清证明原因
	private String settleCauseElse;  //结清证明其他
	private String protocolId;  //协议编号
	private String remark;  //备注
	private String dictDealReason;  //退回原因
	private String loanFlag;    //渠道      
	private String model;      //模式
	private List fileName;//名件名称
	private String subject;//邮件标题
	private String emailContent;//邮件内容
	private String emailTemplateType;//邮件模板类型
	private String emailNumber;//结清证明电子号
	private String dictFileType;//类型
	private List<ContractFileIdAndFileNameView> fileNameOne; //合同文件名称
	 
	
	public List<ContractFileIdAndFileNameView> getFileNameOne() {
		return fileNameOne;
	}
	public void setFileNameOne(List<ContractFileIdAndFileNameView> fileNameOne) {
		this.fileNameOne = fileNameOne;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApplyFor() {
		return applyFor;
	}
	public void setApplyFor(String applyFor) {
		this.applyFor = applyFor;
	}
	public String getCoboCertNum() {
		return coboCertNum;
	}
	public void setCoboCertNum(String coboCertNum) {
		this.coboCertNum = coboCertNum;
	}
	public String getReceiverSex() {
		return receiverSex;
	}
	public void setReceiverSex(String receiverSex) {
		this.receiverSex = receiverSex;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getProtocolId() {
		return protocolId;
	}
	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}
	public String getSettleCauseElse() {
		return settleCauseElse;
	}
	public void setSettleCauseElse(String settleCauseElse) {
		this.settleCauseElse = settleCauseElse;
	}
	public String getSettleCause() {
		return settleCause;
	}
	public void setSettleCause(String settleCause) {
		this.settleCause = settleCause;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getLoanCode() {
		return loanCode;
	}
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
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
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileTypeName() {
		return fileTypeName;
	}
	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}
	
	public String getSendStatusLab() {
		return sendStatusLab;
	}
	public void setSendStatusLab(String sendStatusLab) {
		this.sendStatusLab = sendStatusLab;
	}
	public String getSendStatusName() {
		return sendStatusName;
	}
	public void setSendStatusName(String sendStatusName) {
		this.sendStatusName = sendStatusName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getLoanMonths() {
		return loanMonths;
	}
	public void setLoanMonths(String loanMonths) {
		this.loanMonths = loanMonths;
	}
	public String getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}
	public Date getContractEndDay() {
		return contractEndDay;
	}
	public void setContractEndDay(Date contractEndDay) {
		this.contractEndDay = contractEndDay;
	}
	public Date getSettleDay() {
		return settleDay;
	}
	public void setSettleDay(Date settleDay) {
		this.settleDay = settleDay;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public String getCreditStatusName() {
		return creditStatusName;
	}
	public void setCreditStatusName(String creditStatusName) {
		this.creditStatusName = creditStatusName;
	}
	
	public String getPdfId() {
		return pdfId;
	}
	public void setPdfId(String pdfId) {
		this.pdfId = pdfId;
	}
	public String getWordId() {
		return wordId;
	}
	public void setWordId(String wordId) {
		this.wordId = wordId;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getContractcode() {
		return contractcode;
	}
	public void setContractcode(String contractcode) {
		this.contractcode = contractcode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgCodeIds() {
		if (orgCode != null){
			String[] strArray = orgCode.split(",");
			StringBuffer sb = new StringBuffer();
			for (String str : strArray){
				sb.append("'" + str + "',");
			}
			this.orgCodeIds = sb.toString().substring(0, sb.toString().length() - 1);
		}
		return orgCodeIds;
	}
	public void setOrgCodeIds(String orgCodeIds) {
		this.orgCodeIds = orgCodeIds;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public List<String> getIdsList() {
		if(StringHelper.isNotEmpty(ids))
		{
		idsList = Arrays.asList(ids.split(","));
		return idsList;
		}
		else
		{
			return null;
		}
	}
	public void setIdsList(List<String> idsList) {
		this.idsList = idsList;
	}
	public String getOperateStep() {
		return operateStep;
	}
	public void setOperateStep(String operateStep) {
		this.operateStep = operateStep;
	}
	public String getExcelFlag() {
		return excelFlag;
	}
	public void setExcelFlag(String excelFlag) {
		this.excelFlag = excelFlag;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getDictDealReason() {
		return dictDealReason;
	}
	public void setDictDealReason(String dictDealReason) {
		this.dictDealReason = dictDealReason;
	}
	public List getFileName() {
		return fileName;
	}
	public void setFileName(List fileName) {
		this.fileName = fileName;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public String getEmailTemplateType() {
		return emailTemplateType;
	}
	public void setEmailTemplateType(String emailTemplateType) {
		this.emailTemplateType = emailTemplateType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEmailNumber() {
		return emailNumber;
	}
	public void setEmailNumber(String emailNumber) {
		this.emailNumber = emailNumber;
	}
	public String getDictFileType() {
		return dictFileType;
	}
	public void setDictFileType(String dictFileType) {
		this.dictFileType = dictFileType;
	}

}
