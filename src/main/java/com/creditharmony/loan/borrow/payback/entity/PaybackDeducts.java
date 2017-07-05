/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.payback.entityPaybackDeducts.java
 * @Create By zhaojinping
 * @Create In 2015年12月11日 下午1:25:19
 */

package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;

/**划扣记录表
 * @Class Name PaybackDeducts
 * @author zhaojinping
 * @Create In 2015年12月4日
 */
@SuppressWarnings("serial")
public class PaybackDeducts extends DataEntity<PaybackDeducts>{
	
	// 置顶
		public static final int TOP_FLAG = 1;
	    // 主键ID
	    private String id;
        // 关联ID（还款拆分表/催收服务费表）
	    private String rId;
        // 关联ID2(账户表)
	    private String rId2;
        // 合同编号
	    private String contractCode;
        // 划扣金额
	    private BigDecimal deductAmount;
        // 关联类型（还款/催收服务费）
	    private String dictRDeductType;
        // 线上录入时间
	    private Date deductOnlinetime;
        // 划扣标志(线上/线下)
	    private String dictDecuctFlag;
        // 划扣方式（实时/批量）
	    private String dictDeductsType;
        // 划扣时间
	    private Date decuctTime;
        // 划扣操作者
	    private String decuctUser;
        // 回盘结果(0:待划扣，1:划扣失败、2:划扣成功、3处理中)
	    private String dictBackResult;
        // 失败原因
	    private String decuctFailResult;
        // 划扣状态
	    private String dictDeductStatus;
        // 创建人
	    private String createBy;
        // 创建时间
	    private Date createTime;
        // 修改人
	    private String modifyBy;
        // 最后修改时间
	    private Date modifyTime;
	    //划扣与蓝补的总金额 数据库不保存
	    private BigDecimal sumAmount;
	    // 请求id
	    private String requestId;
	    
	    //private String R_SPLIT_URGE_ID

	    
	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getrId() {
	        return rId;
	    }

	    public void setrId(String rId) {
	        this.rId = rId;
	    }

	    public String getrId2() {
	        return rId2;
	    }

	    public void setrId2(String rId2) {
	        this.rId2 = rId2;
	    }

	    public String getContractCode() {
	        return contractCode;
	    }

	    public void setContractCode(String contractCode) {
	        this.contractCode = contractCode;
	    }

	    public BigDecimal getDeductAmount() {
	        return deductAmount;
	    }

	    public void setDeductAmount(BigDecimal deductAmount) {
	        this.deductAmount = deductAmount;
	    }

	    public String getDictRDeductType() {
	        return dictRDeductType;
	    }

	    public void setDictRDeductType(String dictRDeductType) {
	        this.dictRDeductType = dictRDeductType == null ? null : dictRDeductType.trim();
	    }

	    public Date getDeductOnlinetime() {
	        return deductOnlinetime;
	    }

	    public void setDeductOnlinetime(Date deductOnlinetime) {
	        this.deductOnlinetime = deductOnlinetime;
	    }

	    public String getDictDecuctFlag() {
	        return dictDecuctFlag;
	    }

	    public void setDictDecuctFlag(String dictDecuctFlag) {
	        this.dictDecuctFlag = dictDecuctFlag == null ? null : dictDecuctFlag.trim();
	    }

	    public String getDictDeductsType() {
	        return dictDeductsType;
	    }

	    public void setDictDeductsType(String dictDeductsType) {
	        this.dictDeductsType = dictDeductsType == null ? null : dictDeductsType.trim();
	    }

	    public Date getDecuctTime() {
	        return decuctTime;
	    }

	    public void setDecuctTime(Date decuctTime) {
	        this.decuctTime = decuctTime;
	    }

	    public String getDecuctUser() {
	        return decuctUser;
	    }

	    public void setDecuctUser(String decuctUser) {
	        this.decuctUser = decuctUser == null ? null : decuctUser.trim();
	    }

	    public String getDictBackResult() {
	        return dictBackResult;
	    }

	    public void setDictBackResult(String dictBackResult) {
	        this.dictBackResult = dictBackResult == null ? null : dictBackResult.trim();
	    }

	    public String getDecuctFailResult() {
	        return decuctFailResult;
	    }

	    public void setDecuctFailResult(String decuctFailResult) {
	        this.decuctFailResult = decuctFailResult == null ? null : decuctFailResult.trim();
	    }

	    public String getDictDeductStatus() {
	        return dictDeductStatus;
	    }

	    public void setDictDeductStatus(String dictDeductStatus) {
	        this.dictDeductStatus = dictDeductStatus == null ? null : dictDeductStatus.trim();
	    }

	    public String getCreateBy() {
	        return createBy;
	    }

	    public void setCreateBy(String createBy) {
	        this.createBy = createBy == null ? null : createBy.trim();
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public String getModifyBy() {
	        return modifyBy;
	    }

	    public void setModifyBy(String modifyBy) {
	        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
	    }

	    public Date getModifyTime() {
	        return modifyTime;
	    }

	    public void setModifyTime(Date modifyTime) {
	        this.modifyTime = modifyTime;
	    }

		public BigDecimal getSumAmount() {
			return sumAmount;
		}

		public void setSumAmount(BigDecimal sumAmount) {
			this.sumAmount = sumAmount;
		}

		public String getRequestId() {
			return requestId;
		}

		public void setRequestId(String requestId) {
			this.requestId = requestId;
		}

	
}
