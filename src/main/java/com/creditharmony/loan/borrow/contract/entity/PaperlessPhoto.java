/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entityPaperlessPhoto.java
 * @Create By 张灏
 * @Create In 2016年4月22日 下午3:17:54
 */
package com.creditharmony.loan.borrow.contract.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 无纸化，现场照片采集
 * @Class Name PaperlessPhoto
 * @author 张灏
 * @Create In 2016年4月22日
 */
public class PaperlessPhoto extends DataEntity<PaperlessPhoto> {
  
    /**
     * long serialVersionUID 
     */
    private static final long serialVersionUID = 8149934782050835151L;
    // 关联ID
    private String relationId;
    // 照片类型
    private String photoType;
    // 人员类型
    private String customerType;
    // 借款编号
    private String loanCode;
    // 身份证照片文件ID
    private String idPhotoId;
    //现场照片文件ID
    private String spotPhotoId;
    // 签字照片文件ID
    private String signPhotoId;
    /**
     * @return the relationId
     */
    public String getRelationId() {
        return relationId;
    }
    /**
     * @param relationId the String relationId to set
     */
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
    /**
     * @return the photoType
     */
    public String getPhotoType() {
        return photoType;
    }
    /**
     * @param photoType the String photoType to set
     */
    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }
    /**
     * @return the customerType
     */
    public String getCustomerType() {
        return customerType;
    }
    /**
     * @param customerType the String customerType to set
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    /**
     * @return the loanCode
     */
    public String getLoanCode() {
        return loanCode;
    }
    /**
     * @param loanCode the String loanCode to set
     */
    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }
    /**
     * @return the idPhotoId
     */
    public String getIdPhotoId() {
        return idPhotoId;
    }
    /**
     * @param idPhotoId the String idPhotoId to set
     */
    public void setIdPhotoId(String idPhotoId) {
        this.idPhotoId = idPhotoId;
    }
    /**
     * @return the spotPhotoId
     */
    public String getSpotPhotoId() {
        return spotPhotoId;
    }
    /**
     * @param spotPhotoId the String spotPhotoId to set
     */
    public void setSpotPhotoId(String spotPhotoId) {
        this.spotPhotoId = spotPhotoId;
    }
    /**
     * @return the signPhotoId
     */
    public String getSignPhotoId() {
        return signPhotoId;
    }
    /**
     * @param signPhotoId the String signPhotoId to set
     */
    public void setSignPhotoId(String signPhotoId) {
        this.signPhotoId = signPhotoId;
    }
 }
