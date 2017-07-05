package com.creditharmony.loan.channel.goldcredit.view;


public class SettingCellingNumEntity {
	// CHP上限数量1
	private Integer CHP1;
	// CHP上限数量2
	private Integer CHP2;
	// CHP上限数量3
	private Integer CHP3;
	// CHP上限数量4
	private Integer CHP4;
	// CHP上限数量5
	private Integer CHP5;
	// CHP剩余数量
	private Integer CHPResidual;

	// 金信上限数量1
	private Integer goldCredit1;
	// 金信上限数量2
	private Integer goldCredit2;
	// 金信上限数量3
	private Integer goldCredit3;
	// 金信上限数量4
	private Integer goldCredit4;
	// 金信上限数量5
	private Integer goldCredit5;
	// 金信剩余数量
	private Integer goldCreditResidual;
	
	
	// CHP上限数量1
	private Integer zcj1;
	// CHP上限数量2
	private Integer zcj2;
	// CHP上限数量3
	private Integer zcj3;
	// CHP上限数量4
	private Integer zcj4;
	// CHP上限数量5
	private Integer zcj5;
	// CHP剩余数量
	private Integer zcjResidual;
	
	// p2p上限数量1
	private Integer p2p1;
	// p2p上限数量2
	private Integer p2p2;
	// p2p上限数量3
	private Integer p2p3;
	// p2p上限数量4
	private Integer p2p4;
	// p2p上限数量5
	private Integer p2p5;
	// p2p剩余数量
	private Integer p2pResidual;

	private Integer posit = 1;	 // 位置
	private Integer positNum = 0;	 // 位置数量
	private Integer positXyj = 1;	 // 位置
	private Integer positNumXyj = 0;	 // 位置数量
	// 创建人员
	private String userName;
	// 启用标识
	private int usingFlag;
	//版本号
	private int version;

	public Integer getCHP1() {
		return CHP1;
	}

	public void setCHP1(Integer cHP1) {
		CHP1 = cHP1 == null ? 0 : cHP1;
		positNum = CHP1;
	}

	public Integer getCHP2() {
		return CHP2;
	}

	public void setCHP2(Integer cHP2) {
		CHP2 = cHP2 == null ? 0 : cHP2;
	}

	public Integer getCHP3() {
		return CHP3;
	}

	public void setCHP3(Integer cHP3) {
		CHP3 = cHP3 == null ? 0 : cHP3;
	}

	public Integer getCHP4() {
		return CHP4;
	}

	public void setCHP4(Integer cHP4) {
		CHP4 = cHP4 == null ? 0 : cHP4;
	}

	public Integer getCHP5() {
		return CHP5;
	}

	public void setCHP5(Integer cHP5) {
		CHP5 = cHP5 == null ? 0 : cHP5;
	}

	public Integer getCHPResidual() {
		return CHPResidual;
	}

	public void setCHPResidual(Integer cHPResidual) {
		CHPResidual = cHPResidual;
	}

	public Integer getGoldCredit1() {
		return goldCredit1;
	}

	public void setGoldCredit1(Integer goldCredit1) {
		this.goldCredit1 = goldCredit1 == null ? 0 : goldCredit1;
	}

	public Integer getGoldCredit2() {
		return goldCredit2;
	}

	public void setGoldCredit2(Integer goldCredit2) {
		this.goldCredit2 = goldCredit2 == null ? 0 : goldCredit2;
	}

	public Integer getGoldCredit3() {
		return goldCredit3;
	}

	public void setGoldCredit3(Integer goldCredit3) {
		this.goldCredit3 = goldCredit3 == null ? 0 : goldCredit3;
	}

	public Integer getGoldCredit4() {
		return goldCredit4;
	}

	public void setGoldCredit4(Integer goldCredit4) {
		this.goldCredit4 = goldCredit4 == null ? 0 : goldCredit4;
	}

	public Integer getGoldCredit5() {
		return goldCredit5;
	}

	public void setGoldCredit5(Integer goldCredit5) {
		this.goldCredit5 = goldCredit5 == null ? 0 : goldCredit5;
	}

	public Integer getGoldCreditResidual() {
		return goldCreditResidual;
	}

	public void setGoldCreditResidual(Integer goldCreditResidual) {
		this.goldCreditResidual = goldCreditResidual;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUsingFlag() {
		return usingFlag;
	}

	public void setUsingFlag(int usingFlag) {
		this.usingFlag = usingFlag;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getPosit() {
		return posit;
	}

	public void setPosit(int posit) {
		this.posit = posit;
	}

	public int getPositNum() {
		return positNum;
	}

	public void setPositNum(int positNum) {
		this.positNum = positNum;
	}

	public Integer getZcj1() {
		return zcj1;
	}

	public void setZcj1(Integer zcj1) {
		this.zcj1 = zcj1== null ? 0 : zcj1;
	}

	public Integer getZcj2() {
		return zcj2;
	}

	public void setZcj2(Integer zcj2) {
		this.zcj2 = zcj2== null ? 0 : zcj2;
	}

	public Integer getZcj3() {
		return zcj3;
	}

	public void setZcj3(Integer zcj3) {
		this.zcj3 = zcj3 == null ? 0 : zcj3;
	}

	public Integer getZcj4() {
		return zcj4;
	}

	public void setZcj4(Integer zcj4) {
		this.zcj4 = zcj4== null ? 0 : zcj4;
	}

	public Integer getZcj5() {
		return zcj5;
	}

	public void setZcj5(Integer zcj5) {
		this.zcj5 = zcj5 == null ? 0 : zcj5;
	}

	public Integer getZcjResidual() {
		return zcjResidual;
	}

	public void setZcjResidual(Integer zcjResidual) {
		this.zcjResidual = zcjResidual;
	}

	public Integer getP2p1() {
		return p2p1;
	}

	public void setP2p1(Integer p2p1) {
		this.p2p1 = p2p1 == null ? 0 :p2p1;
		positNumXyj = this.p2p1;
	}

	public Integer getP2p2() {
		return p2p2;
	}

	public void setP2p2(Integer p2p2) {
		this.p2p2 = p2p2 == null ? 0 :p2p2;
	}

	public Integer getP2p3() {
		return p2p3;
	}

	public void setP2p3(Integer p2p3) {
		this.p2p3 = p2p3 == null ? 0 :p2p3;
	}

	public Integer getP2p4() {
		return p2p4;
	}

	public void setP2p4(Integer p2p4) {
		this.p2p4 = p2p4 == null ? 0 :p2p4;
	}

	public Integer getP2p5() {
		return p2p5;
	}

	public void setP2p5(Integer p2p5) {
		this.p2p5 = p2p5 == null ? 0 :p2p5;
	}

	public Integer getP2pResidual() {
		return p2pResidual;
	}

	public void setP2pResidual(Integer p2pResidual) {
		this.p2pResidual = p2pResidual;
	}

	public Integer getPositXyj() {
		return positXyj;
	}

	public Integer getPositNumXyj() {
		return positNumXyj;
	}

	
}
