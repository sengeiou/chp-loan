package com.creditharmony.loan.borrow.serve.entity;

import java.util.Date;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 合同寄送
 * 
 * @Class Name ContractFileSend
 * @author 王俊杰
 * @Create In 2016年2月1日
 */
public class ContractFileJqSend extends DataEntity<ContractFileJqSend> {

	/**
	 * 注解说明 type（ 0：导出导入；1：仅导出；2：仅导入） align（0：自动；1：靠左；2：居中；3：靠右）
	 * sort（导出字段字段排序（升序）） dictType（如果是字典类型，请设置字典的type值）
	 */
	
	private static final long serialVersionUID = 1L;
	
	private String applyId;			//APPLY_ID
	private String loanCode;		//借款编号
	
	@ExcelField(title = "客户姓名", type = 1, align = 0, sort = 10, groups = {1,2})
	private String customerName;	//客户姓名
	
	@ExcelField(title = "合同编号", type = 0, align = 0, sort = 20, groups = {1,2,3})
	private String contractCode;	//合同编号
	
	@ExcelField(title = "门店", type = 1, align = 0, sort = 30, groups = {1,2})
	private String storeName;		//门店名称
	
	private Date applyTime;			//门店申请日期
	
	@ExcelField(title = "收件人姓名", type = 1, align = 0, sort = 40, groups = 2)
	private String receiverName;	//收件人姓名

	@ExcelField(title = "收件人地址", type = 1, align = 0, sort = 50, groups = 2)
	private String receiverAddress;	//收件人地址
	
	@ExcelField(title = "收件人电话", type = 1, align = 0, sort = 60, groups = 2)
	private String receiverPhone;	//收件人电话
	
//	@ExcelField(title = "分类(合同/结清证明)", type = 1, align = 0, sort = 70, groups = 2, dictType = "jk_cm_admin_file_type")
	private String fileType;		//文件类型(0:合同; 1:结清证明)
	private String fileTypeName;
	private String emergentLevel;
	private String emergentLevelName;//紧急程度(0:正常; 1:重要 ; 2:加急(顺丰到付))
	private String sendStatus;
	private String sendStatusLab;
	private String sendStatusName;//邮寄状态(0:待制作; 1:待查找; 2:待邮寄; 3:已邮寄; 4:制作中; 5:制作失败)
	private String productType;		//产品类型
	private String loanMonths;		//借款期限
	private String contractAmount;	//合同金额
	private Date contractEndDay;	//合同到期日期
	private Date settleDay;			//结清日期
	private String creditStatus;	//借款状态(0:还款中; 1:逾期; 2:结清; 3:提前结清; 4:提前结清待审核; 5:结清待确认)
	private String creditStatusName;
	@ExcelField(title = "快递编号", type = 2, sort = 80, groups = 3)
	private String expressNumber;	//快递编号
	
	@ExcelField(title = "快递公司", type = 2, sort = 90, groups = 3)
	private String sendCompany;		//邮寄公司(0:申通 ; 1:顺丰加急)
	
	private String sendCompanyLoabe;	
	
	@ExcelField(title = "合同签订日期", type = 1, align = 0, sort = 100, groups = 1)
	private Date factDay;			//实际签署日期
	
	private String pdfId;			//下载PDF文件的id
	private String wordId;			//下载word文件的id
	
	private String settleCause;			//结清原因
	private String settleCauseElse;		//结清原因其他

	private Date contractFactDay;			//实际签署日期
	
	public String getProtocolId() {
		return protocolId;
	}
	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}
	@ExcelField(title = "协议编号", type = 1, align = 0, sort = 60, groups = 2)
	private String protocolId;  //协议编号
	
	public String getSendCompanyLoabe() {
		return sendCompanyLoabe;
	}
	public void setSendCompanyLoabe(String sendCompanyLoabe) {
		this.sendCompanyLoabe = sendCompanyLoabe;
	}
	public Date getContractFactDay() {
		return contractFactDay;
	}
	public void setContractFactDay(Date contractFactDay) {
		this.contractFactDay = contractFactDay;
	}
	public String getSettleCause() {
		return settleCause;
	}
	public void setSettleCause(String settleCause) {
		this.settleCause = settleCause;
	}
	public String getSettleCauseElse() {
		return settleCauseElse;
	}
	public void setSettleCauseElse(String settleCauseElse) {
		this.settleCauseElse = settleCauseElse;
	}
	public String getSendStatusLab() {
		return sendStatusLab;
	}
	public void setSendStatusLab(String sendStatusLab) {
		this.sendStatusLab = sendStatusLab;
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
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getEmergentLevel() {
		return emergentLevel;
	}
	public void setEmergentLevel(String emergentLevel) {
		this.emergentLevel = emergentLevel;
	}
	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
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
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	public String getSendCompany() {
		return sendCompany;
	}
	public void setSendCompany(String sendCompany) {
		this.sendCompany = sendCompany;
	}
	public String getFactDay() {
		return DateUtils.formatDate(factDay, "yyyy-MM-dd");
	}
	public void setFactDay(Date factDay) {
		this.factDay = factDay;
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
	public String getFileTypeName() {
		return fileTypeName;
	}
	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}
	
	public String getEmergentLevelName() {
		return emergentLevelName;
	}
	public void setEmergentLevelName(String emergentLevelName) {
		this.emergentLevelName = emergentLevelName;
	}
	public String getSendStatusName() {
		return sendStatusName;
	}
	public void setSendStatusName(String sendStatusName) {
		this.sendStatusName = sendStatusName;
	}
	public String getCreditStatusName() {
		return creditStatusName;
	}
	public void setCreditStatusName(String creditStatusName) {
		this.creditStatusName = creditStatusName;
	}

	
}
