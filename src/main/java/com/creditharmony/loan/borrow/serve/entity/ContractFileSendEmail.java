package com.creditharmony.loan.borrow.serve.entity;

import java.util.Date;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 电子协议申请
 * 
 * @Class Name ContractFileSendEmail
 * @author 方强
 * @Create In 2016年11月7日
 */
public class ContractFileSendEmail extends DataEntity<ContractFileSendEmail> {

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

	@ExcelField(title = "收件人电话", type = 1, align = 0, sort = 50, groups = 2)
	private String receiverPhone;	//收件人电话
	@ExcelField(title = "收件人邮箱", type = 1, align = 0, sort = 60, groups = 2)
	private String receiverEmail;	//收件人邮箱
	
//	@ExcelField(title = "分类(合同/结清证明)", type = 1, align = 0, sort = 70, groups = 2, dictType = "jk_cm_admin_file_type")
	private String fileType;		//文件类型(0:合同; 1:结清证明)
	
	private String fileTypeName;
	private String sendStatus;
	private String sendStatusLab;
	private String sendStatusName;//电子协议申请状态(0:未申请; 1:待审核; 2:已发送; 3:申请退回;)
	private String productType;		//产品类型
	private String loanMonths;		//借款期限
	private String contractAmount;	//合同金额
	private Date contractEndDay;	//合同到期日期
	private Date settleDay;			//结清日期
	private String creditStatus;	//借款状态(0:还款中; 1:逾期; 2:结清; 3:提前结清; 4:提前结清待审核; 5:结清待确认)
	private String creditStatusName;	
	
	@ExcelField(title = "合同签订日期", type = 1, align = 0, sort = 100, groups = 1)
	private Date factDay;			//实际签署日期
	
	private String pdfId;			//下载PDF文件的id
	private String wordId;			//下载word文件的id
	
	private String settleCause;			//结清原因
	private String settleCauseElse;		//结清原因其他

	private Date contractFactDay;			//实际签署日期
	private String loanFlag;    //渠道    
	private String loanFlagName;
	public String getLoanFlagName() {
		return loanFlagName;
	}
	public void setLoanFlagName(String loanFlagName) {
		this.loanFlagName = loanFlagName;
	}
	private String model;      //模式
	

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
	public String getReceiverEmail() {
		return receiverEmail;
	}
	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public String getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
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
	
	public String getCreditStatusName() {
		return creditStatusName;
	}
	public void setCreditStatusName(String creditStatusName) {
		this.creditStatusName = creditStatusName;
	}

	
}
