package com.creditharmony.loan.car.common.entity;

import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 合同文件表信息
 * 
 * @author gezhichao
 */
public class CarContractFile extends DataEntity<CarContractFile> {

    private static final long serialVersionUID = 1L;
    // 合同编号
    private String contractCode;
    // 文件名
    private String fileName;
    // 文档ID
    private String docId;
    // 推送标识(默认值：0，0：未推送，1：已推送，3：已确认)
    private String sendFlag;
    // 备注
    private String remarks;
    // 下载标识(0：不可下载；1可下载，默认值为0)
    private String downloadFlag;
    // 文件显示顺序
    private String fileShowOrder;
    // 合同文件名
    private String contractFileName;
    // 签约文件ID
    private String signDocId;
    // 创建时间
    private Date createTime;
    // 创建人
    private String createBy;
    // 修改时间
    private Date modifyTime;
    // 修改人
    private String modifyBy;

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(String sendFlag) {
        this.sendFlag = sendFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the downloadFlag
     */
    public String getDownloadFlag() {
        return downloadFlag;
    }

    /**
     * @param downloadFlag
     *            the String downloadFlag to set
     */
    public void setDownloadFlag(String downloadFlag) {
        this.downloadFlag = downloadFlag;
    }

    /**
     * @return the fileShowOrder
     */
    public String getFileShowOrder() {
        return fileShowOrder;
    }

    /**
     * @param fileShowOrder the String fileShowOrder to set
     */
    public void setFileShowOrder(String fileShowOrder) {
        this.fileShowOrder = fileShowOrder;
    }

    /**
     * @return the contractFileName
     */
    public String getContractFileName() {
        return contractFileName;
    }

    /**
     * @param contractFileName the String contractFileName to set
     */
    public void setContractFileName(String contractFileName) {
        this.contractFileName = contractFileName;
    }

    /**
     * @return the signDocId
     */
    public String getSignDocId() {
        return signDocId;
    }

    /**
     * @param signDocId the String signDocId to set
     */
    public void setSignDocId(String signDocId) {
        this.signDocId = signDocId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    @Override
    public String toString() {
        return "ContractFile [contractCode=" + contractCode + ", fileName="
                + fileName + ", docId=" + docId + ", sendFlag=" + sendFlag
                + ", remarks=" + remarks + ", createTime=" + createTime
                + ", createBy=" + createBy + ", modifyTime=" + modifyTime
                + ", modifyBy=" + modifyBy + "]";
    }

}
