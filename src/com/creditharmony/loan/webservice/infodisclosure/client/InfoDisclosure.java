
package com.creditharmony.loan.webservice.infodisclosure.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>infoDisclosure complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡannualIncome���Ե�ֵ��
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
     * ����annualIncome���Ե�ֵ��
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
     * ��ȡbankCardInfo���Ե�ֵ��
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
     * ����bankCardInfo���Ե�ֵ��
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
     * ��ȡcompAddress���Ե�ֵ��
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
     * ����compAddress���Ե�ֵ��
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
     * ��ȡcompPost���Ե�ֵ��
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
     * ����compPost���Ե�ֵ��
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
     * ��ȡcompWorkExperience���Ե�ֵ��
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
     * ����compWorkExperience���Ե�ֵ��
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
     * ��ȡcontactInfo���Ե�ֵ��
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
     * ����contactInfo���Ե�ֵ��
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
     * ��ȡcontractAmount���Ե�ֵ��
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
     * ����contractAmount���Ե�ֵ��
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
     * ��ȡcontractCode���Ե�ֵ��
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
     * ����contractCode���Ե�ֵ��
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
     * ��ȡcontractMonths���Ե�ֵ��
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
     * ����contractMonths���Ե�ֵ��
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
     * ��ȡcreditReport���Ե�ֵ��
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
     * ����creditReport���Ե�ֵ��
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
     * ��ȡcustomerAddress���Ե�ֵ��
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
     * ����customerAddress���Ե�ֵ��
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
     * ��ȡcustomerBirthday���Ե�ֵ��
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
     * ����customerBirthday���Ե�ֵ��
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
     * ��ȡcustomerName���Ե�ֵ��
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
     * ����customerName���Ե�ֵ��
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
     * ��ȡcustomerSex���Ե�ֵ��
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
     * ����customerSex���Ե�ֵ��
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
     * ��ȡdictCompIndustry���Ե�ֵ��
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
     * ����dictCompIndustry���Ե�ֵ��
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
     * ��ȡdictCustomerDiff���Ե�ֵ��
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
     * ����dictCustomerDiff���Ե�ֵ��
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
     * ��ȡdictEducation���Ե�ֵ��
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
     * ����dictEducation���Ե�ֵ��
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
     * ��ȡdictMarry���Ե�ֵ��
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
     * ����dictMarry���Ե�ֵ��
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
     * ��ȡdictRepayMethod���Ե�ֵ��
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
     * ����dictRepayMethod���Ե�ֵ��
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
     * ��ȡfeeAllRaio���Ե�ֵ��
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
     * ����feeAllRaio���Ե�ֵ��
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
     * ��ȡfieldCertification���Ե�ֵ��
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
     * ����fieldCertification���Ե�ֵ��
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
     * ��ȡidentityAuthentication���Ե�ֵ��
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
     * ����identityAuthentication���Ե�ֵ��
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
     * ��ȡincomeCertification���Ե�ֵ��
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
     * ����incomeCertification���Ե�ֵ��
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
     * ��ȡloanApplicationCount���Ե�ֵ��
     * 
     */
    public long getLoanApplicationCount() {
        return loanApplicationCount;
    }

    /**
     * ����loanApplicationCount���Ե�ֵ��
     * 
     */
    public void setLoanApplicationCount(long value) {
        this.loanApplicationCount = value;
    }

    /**
     * ��ȡloanApplyAmount���Ե�ֵ��
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
     * ����loanApplyAmount���Ե�ֵ��
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
     * ��ȡloanCode���Ե�ֵ��
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
     * ����loanCode���Ե�ֵ��
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
     * ��ȡloanDescription���Ե�ֵ��
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
     * ����loanDescription���Ե�ֵ��
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
     * ��ȡoverdueCount���Ե�ֵ��
     * 
     */
    public long getOverdueCount() {
        return overdueCount;
    }

    /**
     * ����overdueCount���Ե�ֵ��
     * 
     */
    public void setOverdueCount(long value) {
        this.overdueCount = value;
    }

    /**
     * ��ȡoverduePrincipal���Ե�ֵ��
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
     * ����overduePrincipal���Ե�ֵ��
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
     * ��ȡpledgeFlag���Ե�ֵ��
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
     * ����pledgeFlag���Ե�ֵ��
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
     * ��ȡrealyUse���Ե�ֵ��
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
     * ����realyUse���Ե�ֵ��
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
     * ��ȡsuccessfulLoanCount���Ե�ֵ��
     * 
     */
    public long getSuccessfulLoanCount() {
        return successfulLoanCount;
    }

    /**
     * ����successfulLoanCount���Ե�ֵ��
     * 
     */
    public void setSuccessfulLoanCount(long value) {
        this.successfulLoanCount = value;
    }

    /**
     * ��ȡzczmPledgeFlag���Ե�ֵ��
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
     * ����zczmPledgeFlag���Ե�ֵ��
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
