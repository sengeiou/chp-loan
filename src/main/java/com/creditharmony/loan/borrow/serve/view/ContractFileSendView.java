package com.creditharmony.loan.borrow.serve.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.creditharmony.common.util.StringHelper;
import com.creditharmony.loan.borrow.serve.entity.ContractFileSend;

/**
 * 合同寄送
 * 
 * @Class Name ContractFileSendView
 * @author 王俊杰
 * @Create In 2016年2月1日
 */
public class ContractFileSendView extends ContractFileSend {

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
	private String sendStatus;  //合同编号
	private String id;  //合同编号
	
	
	
	private String applyId;			//APPLY_ID
	private String loanCode;		//借款编号

	private String customerName;	//客户姓名

	private String contractCode;	//合同编号
	

	private String storeName;		//门店名称
	
	private Date applyTime;			//门店申请日期
	

	private String receiverName;	//收件人姓名


	private String receiverAddress;	//收件人地址
	
	private String receiverPhone;	//收件人电话
	private String fileType;		//文件类型(0:合同; 1:结清证明)
	private String fileTypeName;
	private String emergentLevel;
	private String emergentLevelName;//紧急程度(0:正常; 1:重要 ; 2:加急(顺丰到付))
	private String sendStatusLab;
	private String sendStatusName;//邮寄状态(0:待制作; 1:待查找; 2:待邮寄; 3:已邮寄; 4:制作中; 5:制作失败)
	private String productType;		//产品类型
	private String loanMonths;		//借款期限
	private String contractAmount;	//合同金额
	private Date contractEndDay;	//合同到期日期
	private Date settleDay;			//结清日期
	private String creditStatus;	//借款状态(0:还款中; 1:逾期; 2:结清; 3:提前结清; 4:提前结清待审核; 5:结清待确认)
	private String creditStatusName;

	private String expressNumber;	//快递编号
	

	private String sendCompany;		//邮寄公司(0:申通 ; 1:顺丰加急)
	
	private String pdfId;			//下载PDF文件的id
	private String wordId;			//下载word文件的id
	private String docId;			//下载PDF文件的id
	private String settleCause;    //结清证明原因
	private String settleCauseElse;  //结清证明其他
	
	private String protocolId;  //协议编号
	private String markName;//渠道名称
	private String mark;//渠道
	private String loanFlag;	//渠道
	private String loanFlagName;
	private String modelName;//模式名称
	private String model;		//	模式
	private String paperlessFlag; //无纸化标识
	
	private String conts; //批量查询合同编号
	
	
	public String getPaperlessFlag() {
		return paperlessFlag;
	}
	public void setPaperlessFlag(String paperlessFlag) {
		this.paperlessFlag = paperlessFlag;
	}
	public String getLoanFlagName() {
		return loanFlagName;
	}
	public void setLoanFlagName(String loanFlagName) {
		this.loanFlagName = loanFlagName;
	}
	public String getLoanFlag() {
		return loanFlag;
	}
	public void setLoanFlag(String loanFlag) {
		this.loanFlag = loanFlag;
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
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
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
	public String getEmergentLevel() {
		return emergentLevel;
	}
	public void setEmergentLevel(String emergentLevel) {
		this.emergentLevel = emergentLevel;
	}
	public String getEmergentLevelName() {
		return emergentLevelName;
	}
	public void setEmergentLevelName(String emergentLevelName) {
		this.emergentLevelName = emergentLevelName;
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
	public String getMarkName() {
		return markName;
	}
	public void setMarkName(String markName) {
		this.markName = markName;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public String getConts() {
		return conts;
	}
	public void setConts(String conts) {
		this.conts = conts;
	}
	
}
