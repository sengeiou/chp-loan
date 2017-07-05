package com.creditharmony.loan.car.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.creditharmony.core.persistence.DataEntity;
/**
 * 
 * @Class Name CarPaybackTransferDetail
 * @author 李静辉
 * @Create In 2016年3月9日
 */
public class CarPaybackTransferDetail extends DataEntity<CarPaybackTransferDetail> {
    /**
	 * long serialVersionUID 
	 */
	private static final long serialVersionUID = 5823114480200771526L;

	private String id;

    private String transferId;

    private Date tranDepositTime;

    private BigDecimal reallyAmount;

    private String depositName;

    private String uploadName;

    private Date uploadDate;

    private String uploadFilename;

    private String uploadPath;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;
    
    private String dictDeposit;

    //申请查账明细凭条 ID
    private String transferDetailId;
    //单笔实际到账金额
    private BigDecimal transferDetailReallyAmount;
    //匹配结果
    private String matchingResult;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId == null ? null : transferId.trim();
    }

    public Date getTranDepositTime() {
        return tranDepositTime;
    }

    public void setTranDepositTime(Date tranDepositTime) {
        this.tranDepositTime = tranDepositTime;
    }

    public BigDecimal getReallyAmount() {
        return reallyAmount;
    }

    public void setReallyAmount(BigDecimal reallyAmount) {
        this.reallyAmount = reallyAmount;
    }

    public String getDepositName() {
        return depositName;
    }

    public void setDepositName(String depositName) {
        this.depositName = depositName == null ? null : depositName.trim();
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName == null ? null : uploadName.trim();
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
        this.uploadFilename = uploadFilename == null ? null : uploadFilename.trim();
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath == null ? null : uploadPath.trim();
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

	public String getTransferDetailId() {
		return transferDetailId;
	}

	public void setTransferDetailId(String transferDetailId) {
		this.transferDetailId = transferDetailId;
	}

	public BigDecimal getTransferDetailReallyAmount() {
		return transferDetailReallyAmount;
	}

	public void setTransferDetailReallyAmount(BigDecimal transferDetailReallyAmount) {
		this.transferDetailReallyAmount = transferDetailReallyAmount;
	}

	public String getDictDeposit() {
		return dictDeposit;
	}

	public void setDictDeposit(String dictDeposit) {
		this.dictDeposit = dictDeposit;
	}

	

	public String getMatchingResult() {
		return matchingResult;
	}

	public void setMatchingResult(String matchingResult) {
		this.matchingResult = matchingResult;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createBy == null) ? 0 : createBy.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result
				+ ((depositName == null) ? 0 : depositName.hashCode());
		result = prime * result
				+ ((dictDeposit == null) ? 0 : dictDeposit.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((modifyBy == null) ? 0 : modifyBy.hashCode());
		result = prime * result
				+ ((modifyTime == null) ? 0 : modifyTime.hashCode());
		result = prime * result
				+ ((reallyAmount == null) ? 0 : reallyAmount.hashCode());
		result = prime * result
				+ ((tranDepositTime == null) ? 0 : tranDepositTime.hashCode());
		result = prime
				* result
				+ ((transferDetailId == null) ? 0 : transferDetailId.hashCode());
		result = prime
				* result
				+ ((transferDetailReallyAmount == null) ? 0
						: transferDetailReallyAmount.hashCode());
		result = prime * result
				+ ((transferId == null) ? 0 : transferId.hashCode());
		result = prime * result
				+ ((uploadDate == null) ? 0 : uploadDate.hashCode());
		result = prime * result
				+ ((uploadFilename == null) ? 0 : uploadFilename.hashCode());
		result = prime * result
				+ ((uploadName == null) ? 0 : uploadName.hashCode());
		result = prime * result
				+ ((uploadPath == null) ? 0 : uploadPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarPaybackTransferDetail other = (CarPaybackTransferDetail) obj;
		if (createBy == null) {
			if (other.createBy != null)
				return false;
		} else if (!createBy.equals(other.createBy))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (depositName == null) {
			if (other.depositName != null)
				return false;
		} else if (!depositName.equals(other.depositName))
			return false;
		if (dictDeposit == null) {
			if (other.dictDeposit != null)
				return false;
		} else if (!dictDeposit.equals(other.dictDeposit))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modifyBy == null) {
			if (other.modifyBy != null)
				return false;
		} else if (!modifyBy.equals(other.modifyBy))
			return false;
		if (modifyTime == null) {
			if (other.modifyTime != null)
				return false;
		} else if (!modifyTime.equals(other.modifyTime))
			return false;
		if (reallyAmount == null) {
			if (other.reallyAmount != null)
				return false;
		} else if (!reallyAmount.equals(other.reallyAmount))
			return false;
		if (tranDepositTime == null) {
			if (other.tranDepositTime != null)
				return false;
		} else if (!tranDepositTime.equals(other.tranDepositTime))
			return false;
		if (transferDetailId == null) {
			if (other.transferDetailId != null)
				return false;
		} else if (!transferDetailId.equals(other.transferDetailId))
			return false;
		if (transferDetailReallyAmount == null) {
			if (other.transferDetailReallyAmount != null)
				return false;
		} else if (!transferDetailReallyAmount
				.equals(other.transferDetailReallyAmount))
			return false;
		if (transferId == null) {
			if (other.transferId != null)
				return false;
		} else if (!transferId.equals(other.transferId))
			return false;
		if (uploadDate == null) {
			if (other.uploadDate != null)
				return false;
		} else if (!uploadDate.equals(other.uploadDate))
			return false;
		if (uploadFilename == null) {
			if (other.uploadFilename != null)
				return false;
		} else if (!uploadFilename.equals(other.uploadFilename))
			return false;
		if (uploadName == null) {
			if (other.uploadName != null)
				return false;
		} else if (!uploadName.equals(other.uploadName))
			return false;
		if (uploadPath == null) {
			if (other.uploadPath != null)
				return false;
		} else if (!uploadPath.equals(other.uploadPath))
			return false;
		return true;
	}
	
    
}