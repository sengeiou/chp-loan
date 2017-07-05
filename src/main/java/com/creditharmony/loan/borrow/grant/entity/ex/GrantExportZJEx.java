/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.grant.entity.exGrantExportEx.java
 * @Create By 张灏
 * @Create In 2016年2月25日 下午3:51:15
 */
package com.creditharmony.loan.borrow.grant.entity.ex;

import com.creditharmony.core.excel.annotation.ExcelField;
import com.creditharmony.core.persistence.DataEntity;

/**
 * 封装导出信息
 * @Class Name GrantExportEx
 * @author 张灏
 * @Create In 2016年2月25日
 */
public class GrantExportZJEx extends DataEntity<GrantExportZJEx>{

  private static final long serialVersionUID = 3580693282842452822L;
 
  // 流水号
  @ExcelField(title = "明细流水号", type = 0, align = 2, sort = 10)
  private String serialNum;
  // 金额
  @ExcelField(title = "金额(元)", type = 0, align = 2, sort = 20)
  private Double grantAmount;
  // 银行代码
  @ExcelField(title = "银行代码", type = 0, align = 2, sort = 30)
  private String bankCode;
  // 账户类型
  @ExcelField(title = "帐户类型", type = 0, align = 2, sort = 40)
  private String accountType;
  // 账户名称
  @ExcelField(title = "账户名称", type = 0, align = 2, sort = 50)
  private String accountName;
  // 账户号码
  @ExcelField(title = "账户号码", type = 0, align = 2, sort = 60)
  private String accountNumber;
  // 分支行
  @ExcelField(title = "分支行", type = 0, align = 2, sort = 70)
  private String bankBranch;
  // 省Code 
  private String provinceCode;
  // 省名字
  @ExcelField(title = "省份", type = 0, align = 2, sort = 80)
  private String provinceName;
  // 城市Code
  private String cityCode;
  // 城市名称
  @ExcelField(title = "城市", type = 0, align = 2, sort = 90)
  private String cityName;
  // 备注
  @ExcelField(title = "备注", type = 0, align = 2, sort = 100)
  private String remark;
  //证件类型（code）
  @ExcelField(title = "证件类型", type = 0, align = 2, sort = 110, dictType = "jk_certificate_type")
  private String certType;
  // 证件号码
  @ExcelField(title = "证件号码", type = 0, align = 2, sort = 120)
  private String identityCode;
  // 手机号码
  @ExcelField(title = "手机号", type = 0, align = 2, sort = 130)
  private String telePhone;
  // 电子邮箱
  @ExcelField(title = "电子邮箱", type = 0, align = 2, sort = 140)
  private String email;
  // 征信费
  @ExcelField(title = "征信费", type = 0, align = 2, sort = 150)
  private Double feeCredit;
  // 信访费
  @ExcelField(title = "信访费", type = 0, align = 2, sort = 160)
  private Double feePetiton;
  // 催收服务费
  @ExcelField(title = "催收服务费", type = 0, align = 2, sort = 170)
  private Double feeUrgeService;
  // 费用总计:催收费+信访费+征信费
  @ExcelField(title = "费用总计", type = 0, align = 2, sort = 180)
  private Double feeSum;
  // 合同编号
  private String contractCode;
  // 产品Code
  private String productCode;
  // 批次号
 // @ExcelField(title = "批次号", type = 0, align = 2, sort = 150)
  private String grantPch;
  public String getSerialNum() {
    return serialNum;
  }
 
  public void setSerialNum(String serialNum) {
   
      this.serialNum = serialNum;
  }
  
  public Double getGrantAmount() {
  
      return grantAmount;
  }
  public void setGrantAmount(Double grantAmount) {
  
      this.grantAmount = grantAmount;
  }
  public String getBankCode() {
  
      return bankCode;
  }
  public void setBankCode(String bankCode) {
   
      this.bankCode = bankCode;
  }
  public String getAccountType() {
  
      return accountType;
  }
  public void setAccountType(String accountType) {
   
      this.accountType = accountType;
  }
  public String getAccountName() {
   
      return accountName;
  }
  public void setAccountName(String accountName) {
      this.accountName = accountName;
  }
  public String getAccountNumber() {
   
      return accountNumber;
  }
  public void setAccountNumber(String accountNumber) {
    
      this.accountNumber = accountNumber;
  }
  public String getBankBranch() {
   
      return bankBranch;
  }
  public void setBankBranch(String bankBranch) {
   
      this.bankBranch = bankBranch;
  }
  public String getProvinceCode() {
    
      return provinceCode;
  }
  public void setProvinceCode(String provinceCode) {
    
      this.provinceCode = provinceCode;
  }
  public String getProvinceName() {
    
      return provinceName;
  }
  public void setProvinceName(String provinceName) {
    
      this.provinceName = provinceName;
  }
  public String getCityCode() {
    
      return cityCode;
  }
  public void setCityCode(String cityCode) {
    
      this.cityCode = cityCode;
  }
  public String getCityName() {
    
      return cityName;
  }
  public void setCityName(String cityName) {
    
      this.cityName = cityName;
  }
  public String getRemark() {
    
      return remark;
  }
  public void setRemark(String remark) {
   
      this.remark = remark;
  }
  public String getCertType() {
   
      return certType;
  }
  public void setCertType(String certType) {
   
      this.certType = certType;
  }
  public String getIdentityCode() {
    
      return identityCode;
  }
  public void setIdentityCode(String identityCode) {
   
      this.identityCode = identityCode;
  }
  public String getTelePhone() {
    
      return telePhone;
  }
  public void setTelePhone(String telePhone) {
   
      this.telePhone = telePhone;
  }
  public String getEmail() {
    
      return email;
  }
  public void setEmail(String email) {
   
      this.email = email;
  }

public String getContractCode() {
    return contractCode;
}

public void setContractCode(String contractCode) {
    this.contractCode = contractCode;
}

public String getProductCode() {
    return productCode;
}

public void setProductCode(String productCode) {
    this.productCode = productCode;
}

public String getGrantPch() {
    return grantPch;
}

public void setGrantPch(String grantPch) {
    this.grantPch = grantPch;
}

public Double getFeeCredit() {
	return feeCredit;
}

public void setFeeCredit(Double feeCredit) {
	this.feeCredit = feeCredit;
}

public Double getFeePetiton() {
	return feePetiton;
}

public void setFeePetiton(Double feePetiton) {
	this.feePetiton = feePetiton;
}

public Double getFeeUrgeService() {
	return feeUrgeService;
}

public void setFeeUrgeService(Double feeUrgeService) {
	this.feeUrgeService = feeUrgeService;
}

public Double getFeeSum() {
	return feeSum;
}

public void setFeeSum(Double feeSum) {
	this.feeSum = feeSum;
}
}
