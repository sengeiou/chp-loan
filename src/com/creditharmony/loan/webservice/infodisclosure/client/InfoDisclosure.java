
package com.creditharmony.loan.webservice.infodisclosure.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>infoDisclosure complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="infoDisclosure"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="annualIncome" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="bankCardInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="compAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="compPost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="compWorkExperience" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="contactInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="contractAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="contractCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="contractMonths" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="creditReport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="customerAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="customerBirthday" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="customerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="customerSex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dictCompIndustry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dictCustomerDiff" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dictEducation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dictMarry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dictRepayMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="feeAllRaio" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="fieldCertification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="identityAuthentication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="incomeCertification" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loanApplicationCount" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="loanApplyAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="loanCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="loanDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="overdueCount" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="overduePrincipal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="pledgeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="realyUse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="successfulLoanCount" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="zczmPledgeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoDisclosure", propOrder = {
    "annualIncome",
    "bankCardInfo",
    "compAddress",
    "compPost",
    "compWorkExperience",
    "contactInfo",
    "contractAmount",
    "contractCode",
    "contractMonths",
    "creditReport",
    "customerAddress",
    "customerBirthday",
    "customerName",
    "customerSex",
    "dictCompIndustry",
    "dictCustomerDiff",
    "dictEducation",
    "dictMarry",
    "dictRepayMethod",
    "feeAllRaio",
    "fieldCertification",
    "identityAuthentication",
    "incomeCertification",
    "loanApplicationCount",
    "loanApplyAmount",
    "loanCode",
    "loanDescription",
    "overdueCount",
    "overduePrincipal",
    "pledgeFlag",
    "realyUse",
    "successfulLoanCount",
    "zczmPledgeFlag"
})
public class InfoDisclosure {

    protected BigDecimal annualIncome;
    protected String bankCardInfo;
    protected String compAddress;
    protected String compPost;
    protected String compWorkExperience;
    protected String contactInfo;
    protected BigDecimal contractAmount;
    protected String contractCode;
    protected String contractMonths;
    protected String creditReport;
    protected String customerAddress;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar customerBirthday;
    protected String customerName;
    protected String customerSex;
    protected String dictCompIndustry;
    protected String dictCustomerDiff;
    protected String dictEducation;
    protected String dictMarry;
    protected String dictRepayMethod;
    protected BigDecimal feeAllRaio;
    protected String fieldCertification;
    protected String identityAuthentication;
    protected String incomeCertification;
    protected long loanApplicationCount;
    protected BigDecimal loanApplyAmount;
    protected String loanCode;
    protected String loanDescription;
    protected long overdueCount;
    protected BigDecimal overduePrincipal;
    protected String pledgeFlag;
    protected String realyUse;
    protected long successfulLoanCount;
    protected String zczmPledgeFlag;

    /**
     * 获取annualIncome属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    /**
     * 设置annualIncome属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAnnualIncome(BigDecimal value) {
        this.annualIncome = value;
    }

    /**
     * 获取bankCardInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankCardInfo() {
        return bankCardInfo;
    }

    /**
     * 设置bankCardInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankCardInfo(String value) {
        this.bankCardInfo = value;
    }

    /**
     * 获取compAddress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompAddress() {
        return compAddress;
    }

    /**
     * 设置compAddress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompAddress(String value) {
        this.compAddress = value;
    }

    /**
     * 获取compPost属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompPost() {
        return compPost;
    }

    /**
     * 设置compPost属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompPost(String value) {
        this.compPost = value;
    }

    /**
     * 获取compWorkExperience属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompWorkExperience() {
        return compWorkExperience;
    }

    /**
     * 设置compWorkExperience属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompWorkExperience(String value) {
        this.compWorkExperience = value;
    }

    /**
     * 获取contactInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * 设置contactInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactInfo(String value) {
        this.contactInfo = value;
    }

    /**
     * 获取contractAmount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    /**
     * 设置contractAmount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setContractAmount(BigDecimal value) {
        this.contractAmount = value;
    }

    /**
     * 获取contractCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * 设置contractCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractCode(String value) {
        this.contractCode = value;
    }

    /**
     * 获取contractMonths属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractMonths() {
        return contractMonths;
    }

    /**
     * 设置contractMonths属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractMonths(String value) {
        this.contractMonths = value;
    }

    /**
     * 获取creditReport属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditReport() {
        return creditReport;
    }

    /**
     * 设置creditReport属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditReport(String value) {
        this.creditReport = value;
    }

    /**
     * 获取customerAddress属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * 设置customerAddress属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerAddress(String value) {
        this.customerAddress = value;
    }

    /**
     * 获取customerBirthday属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCustomerBirthday() {
        return customerBirthday;
    }

    /**
     * 设置customerBirthday属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCustomerBirthday(XMLGregorianCalendar value) {
        this.customerBirthday = value;
    }

    /**
     * 获取customerName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置customerName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * 获取customerSex属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerSex() {
        return customerSex;
    }

    /**
     * 设置customerSex属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerSex(String value) {
        this.customerSex = value;
    }

    /**
     * 获取dictCompIndustry属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDictCompIndustry() {
        return dictCompIndustry;
    }

    /**
     * 设置dictCompIndustry属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDictCompIndustry(String value) {
        this.dictCompIndustry = value;
    }

    /**
     * 获取dictCustomerDiff属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDictCustomerDiff() {
        return dictCustomerDiff;
    }

    /**
     * 设置dictCustomerDiff属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDictCustomerDiff(String value) {
        this.dictCustomerDiff = value;
    }

    /**
     * 获取dictEducation属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDictEducation() {
        return dictEducation;
    }

    /**
     * 设置dictEducation属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDictEducation(String value) {
        this.dictEducation = value;
    }

    /**
     * 获取dictMarry属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDictMarry() {
        return dictMarry;
    }

    /**
     * 设置dictMarry属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDictMarry(String value) {
        this.dictMarry = value;
    }

    /**
     * 获取dictRepayMethod属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDictRepayMethod() {
        return dictRepayMethod;
    }

    /**
     * 设置dictRepayMethod属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDictRepayMethod(String value) {
        this.dictRepayMethod = value;
    }

    /**
     * 获取feeAllRaio属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFeeAllRaio() {
        return feeAllRaio;
    }

    /**
     * 设置feeAllRaio属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFeeAllRaio(BigDecimal value) {
        this.feeAllRaio = value;
    }

    /**
     * 获取fieldCertification属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldCertification() {
        return fieldCertification;
    }

    /**
     * 设置fieldCertification属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldCertification(String value) {
        this.fieldCertification = value;
    }

    /**
     * 获取identityAuthentication属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentityAuthentication() {
        return identityAuthentication;
    }

    /**
     * 设置identityAuthentication属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentityAuthentication(String value) {
        this.identityAuthentication = value;
    }

    /**
     * 获取incomeCertification属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncomeCertification() {
        return incomeCertification;
    }

    /**
     * 设置incomeCertification属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncomeCertification(String value) {
        this.incomeCertification = value;
    }

    /**
     * 获取loanApplicationCount属性的值。
     * 
     */
    public long getLoanApplicationCount() {
        return loanApplicationCount;
    }

    /**
     * 设置loanApplicationCount属性的值。
     * 
     */
    public void setLoanApplicationCount(long value) {
        this.loanApplicationCount = value;
    }

    /**
     * 获取loanApplyAmount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLoanApplyAmount() {
        return loanApplyAmount;
    }

    /**
     * 设置loanApplyAmount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLoanApplyAmount(BigDecimal value) {
        this.loanApplyAmount = value;
    }

    /**
     * 获取loanCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanCode() {
        return loanCode;
    }

    /**
     * 设置loanCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanCode(String value) {
        this.loanCode = value;
    }

    /**
     * 获取loanDescription属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoanDescription() {
        return loanDescription;
    }

    /**
     * 设置loanDescription属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoanDescription(String value) {
        this.loanDescription = value;
    }

    /**
     * 获取overdueCount属性的值。
     * 
     */
    public long getOverdueCount() {
        return overdueCount;
    }

    /**
     * 设置overdueCount属性的值。
     * 
     */
    public void setOverdueCount(long value) {
        this.overdueCount = value;
    }

    /**
     * 获取overduePrincipal属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOverduePrincipal() {
        return overduePrincipal;
    }

    /**
     * 设置overduePrincipal属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOverduePrincipal(BigDecimal value) {
        this.overduePrincipal = value;
    }

    /**
     * 获取pledgeFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPledgeFlag() {
        return pledgeFlag;
    }

    /**
     * 设置pledgeFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPledgeFlag(String value) {
        this.pledgeFlag = value;
    }

    /**
     * 获取realyUse属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealyUse() {
        return realyUse;
    }

    /**
     * 设置realyUse属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealyUse(String value) {
        this.realyUse = value;
    }

    /**
     * 获取successfulLoanCount属性的值。
     * 
     */
    public long getSuccessfulLoanCount() {
        return successfulLoanCount;
    }

    /**
     * 设置successfulLoanCount属性的值。
     * 
     */
    public void setSuccessfulLoanCount(long value) {
        this.successfulLoanCount = value;
    }

    /**
     * 获取zczmPledgeFlag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZczmPledgeFlag() {
        return zczmPledgeFlag;
    }

    /**
     * 设置zczmPledgeFlag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZczmPledgeFlag(String value) {
        this.zczmPledgeFlag = value;
    }

}
