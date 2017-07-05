package com.creditharmony.loan.car.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 待还款匹配明细信息
 * @Class Name CarRepayMatchDeatilInfo
 * @author 蒋力
 * @Create In 2016年3月4日
 */
public class CarRepayMatchDetailInfo extends DataEntity<CarRepayMatchDetailInfo>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7340980817115689844L;
	
	//关联转账信息表ID
	private String transferId;
	//存款时间
	private Date tranDepositTime;
	//实际到账金额
	private BigDecimal reallyAmount;
	//实际存款人
	private String depositName;
	//存款方式
	private String dictDeposit;
	//上传人
	private String uploadName;
	//上传时间
	private Date uploadDate;
	//上传文件名
	private String uploadFilename;
	//上传文件路径
	private String uploadPath;
	//匹配结果
	private String matchingResult;
	
	public BigDecimal getReallyAmount() {
		return reallyAmount;
	}
	public void setReallyAmount(BigDecimal reallyAmount) {
		this.reallyAmount = reallyAmount;
	}
	public String getMatchingResult() {
		return matchingResult;
	}
	public void setMatchingResult(String matchingResult) {
		this.matchingResult = matchingResult;
	}
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public String getDepositName() {
		return depositName;
	}
	public void setDepositName(String depositName) {
		this.depositName = depositName;
	}
	public String getDictDeposit() {
		return dictDeposit;
	}
	public void setDictDeposit(String dictDeposit) {
		this.dictDeposit = dictDeposit;
	}
	public String getUploadName() {
		return uploadName;
	}
	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getUploadFilename() {
		return uploadFilename;
	}
	public void setUploadFilename(String uploadFilename) {
		this.uploadFilename = uploadFilename;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public Date getTranDepositTime() {
		return tranDepositTime;
	}
	public void setTranDepositTime(Date tranDepositTime) {
		this.tranDepositTime = tranDepositTime;
	}
	
}