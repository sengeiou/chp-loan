/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.entity.exIDCardMessageEx.java
 * @Create By 张灏
 * @Create In 2016年4月21日 下午9:05:57
 */
package com.creditharmony.loan.borrow.contract.entity.ex;

/**
 * 身份证校验实体类
 * @Class Name IDCardMessageEx
 * @author 张灏
 * @Create In 2016年4月21日
 */
public class IDCardMessageEx {
   // 文件ID
   private String docId;
   // 身份证号码OCR
   private String idNumOCR;
   // 身份证名字
   private String customerNameOCR;
   // 客户ID
   private String customerId;
   // 客户类型
   private String customerType;
/**
 * @return the docId
 */
public String getDocId() {
    return docId;
}
/**
 * @param docId the String docId to set
 */
public void setDocId(String docId) {
    this.docId = docId;
}
/**
 * @return the idNumOCR
 */
public String getIdNumOCR() {
    return idNumOCR;
}
/**
 * @param idNumOCR the String idNumOCR to set
 */
public void setIdNumOCR(String idNumOCR) {
    this.idNumOCR = idNumOCR;
}
/**
 * @return the customerNameOCR
 */
public String getCustomerNameOCR() {
    return customerNameOCR;
}
/**
 * @param customerNameOCR the String customerNameOCR to set
 */
public void setCustomerNameOCR(String customerNameOCR) {
    this.customerNameOCR = customerNameOCR;
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
}
