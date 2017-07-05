package com.creditharmony.loan.borrow.applyinfo.entity.ex;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
/**
 * 修改详细记录(数据容器)
 * @Class Name ModifyInfoEx
 * @author lirui
 * @Create In 2016年2月18日
 */
@SuppressWarnings("serial")
public class ModifyInfoEx extends CustomerBaseInfo {
	// 姓名
	private String coboName;
	// 身份证号
	private String coboCertNum;
	public String getCoboName() {
		return coboName;
	}
	public void setCoboName(String coboName) {
		this.coboName = coboName;
	}
	public String getCoboCertNum() {
		return coboCertNum;
	}
	public void setCoboCertNum(String coboCertNum) {
		this.coboCertNum = coboCertNum;
	}
	
	
}
