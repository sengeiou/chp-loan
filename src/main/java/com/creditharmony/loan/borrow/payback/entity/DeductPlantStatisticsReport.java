package com.creditharmony.loan.borrow.payback.entity;

import java.math.BigDecimal;

public class DeductPlantStatisticsReport {

	
	    private String plantCode; // 划扣平台
	    
	    private BigDecimal zjnotEnoughProportion; // 中金余额不足比例
	    private BigDecimal fynotEnoughProportion; // 富友余额不足比例
	    private BigDecimal klnotEnoughProportion; // 卡联余额不足比例
	    private BigDecimal cjnotEnoughProportion; // 畅捷余额不足比例
	    private BigDecimal tlnotEnoughProportion; // 通联余额不足比例

	    private BigDecimal zjfailureRate;  // 中金失败率
	    private BigDecimal fyfailureRate;  // 富友失败率
	    private BigDecimal klfailureRate;  // 卡联失败率
	    private BigDecimal cjfailureRate;  // 畅捷失败率
	    private BigDecimal tlfailureRate;  // 通联失败率

	    private int zjfailureNumber; // 中金失败条数
	    private int fyfailureNumber; // 富友失败条数
	    private int klfailureNumber; // 卡联失败条数
	    private int cjfailureNumber; // 畅捷失败条数
	    private int tlfailureNumber; // 通联失败条数

	    private int zjdeductNumber;  //中金划扣笔数
	    private int fydeductNumber;  //富友划扣笔数
	    private int kldeductNumber;  //卡联划扣笔数
	    private int cjdeductNumber;  //畅捷划扣笔数
	    private int tldeductNumber;  //通联划扣笔数
	    
	    
	    private int  zjnotEnoughNumber; // 中金余额不足条数
	    private int  fynotEnoughNumber; // 富友余额不足条数
	    private int  klnotEnoughNumber; // 卡联余额不足条数
	    private int  cjnotEnoughNumber; // 畅捷余额不足条数
	    private int  tlnotEnoughNumber; // 通联余额不足条数

	    
		public String getPlantCode() {
			return plantCode;
		}
		public BigDecimal getZjnotEnoughProportion() {
			if(zjnotEnoughProportion == null ){
				zjnotEnoughProportion = new BigDecimal(0);
			}
			return zjnotEnoughProportion;
		}
		public BigDecimal getFynotEnoughProportion() {
			if(fynotEnoughProportion == null){
				fynotEnoughProportion =  new BigDecimal(0); 
			}
			return fynotEnoughProportion;
		}
		public BigDecimal getKlnotEnoughProportion() {
			if(klnotEnoughProportion == null){
				klnotEnoughProportion =  new BigDecimal(0); 
			}
			return klnotEnoughProportion;
		}
		public BigDecimal getCjnotEnoughProportion() {
			if(cjnotEnoughProportion == null){
				cjnotEnoughProportion =  new BigDecimal(0); 
			}
			return cjnotEnoughProportion;
		}
		public BigDecimal getTlnotEnoughProportion() {
			if(tlnotEnoughProportion == null){
				tlnotEnoughProportion =  new BigDecimal(0); 
			}
			return tlnotEnoughProportion;
		}
		public BigDecimal getZjfailureRate() {
			if(zjfailureRate == null){
				zjfailureRate =  new BigDecimal(0); 
			}
			return zjfailureRate;
		}
		public BigDecimal getFyfailureRate() {
			if(fyfailureRate == null){
				fyfailureRate =  new BigDecimal(0); 
			}
			return fyfailureRate;
		}
		public BigDecimal getKlfailureRate() {
			if(klfailureRate == null){
				klfailureRate =  new BigDecimal(0); 
			}
			return klfailureRate;
		}
		public BigDecimal getCjfailureRate() {
			if(cjfailureRate == null){
				cjfailureRate =  new BigDecimal(0); 
			}
			return cjfailureRate;
		}
		public BigDecimal getTlfailureRate() {
			if(tlfailureRate == null){
				tlfailureRate =  new BigDecimal(0); 
			}
			return tlfailureRate;
		}
		public int getZjfailureNumber() {
			return zjfailureNumber;
		}
		public int getFyfailureNumber() {
			return fyfailureNumber;
		}
		public int getKlfailureNumber() {
			return klfailureNumber;
		}
		public int getCjfailureNumber() {
			return cjfailureNumber;
		}
		public int getTlfailureNumber() {
			return tlfailureNumber;
		}
		public int getZjdeductNumber() {
			return zjdeductNumber;
		}
		public int getFydeductNumber() {
			return fydeductNumber;
		}
		public int getKldeductNumber() {
			return kldeductNumber;
		}
		public int getCjdeductNumber() {
			return cjdeductNumber;
		}
		public int getTldeductNumber() {
			return tldeductNumber;
		}
		public void setPlantCode(String plantCode) {
			this.plantCode = plantCode;
		}
		public void setZjnotEnoughProportion(BigDecimal zjnotEnoughProportion) {
			this.zjnotEnoughProportion = zjnotEnoughProportion;
		}
		public void setFynotEnoughProportion(BigDecimal fynotEnoughProportion) {
			this.fynotEnoughProportion = fynotEnoughProportion;
		}
		public void setKlnotEnoughProportion(BigDecimal klnotEnoughProportion) {
			this.klnotEnoughProportion = klnotEnoughProportion;
		}
		public void setCjnotEnoughProportion(BigDecimal cjnotEnoughProportion) {
			this.cjnotEnoughProportion = cjnotEnoughProportion;
		}
		public void setTlnotEnoughProportion(BigDecimal tlnotEnoughProportion) {
			this.tlnotEnoughProportion = tlnotEnoughProportion;
		}
		public void setZjfailureRate(BigDecimal zjfailureRate) {
			this.zjfailureRate = zjfailureRate;
		}
		public void setFyfailureRate(BigDecimal fyfailureRate) {
			this.fyfailureRate = fyfailureRate;
		}
		public void setKlfailureRate(BigDecimal klfailureRate) {
			this.klfailureRate = klfailureRate;
		}
		public void setCjfailureRate(BigDecimal cjfailureRate) {
			this.cjfailureRate = cjfailureRate;
		}
		public void setTlfailureRate(BigDecimal tlfailureRate) {
			this.tlfailureRate = tlfailureRate;
		}
		public void setZjfailureNumber(int zjfailureNumber) {
			this.zjfailureNumber = zjfailureNumber;
		}
		public void setFyfailureNumber(int fyfailureNumber) {
			this.fyfailureNumber = fyfailureNumber;
		}
		public void setKlfailureNumber(int klfailureNumber) {
			this.klfailureNumber = klfailureNumber;
		}
		public void setCjfailureNumber(int cjfailureNumber) {
			this.cjfailureNumber = cjfailureNumber;
		}
		public void setTlfailureNumber(int tlfailureNumber) {
			this.tlfailureNumber = tlfailureNumber;
		}
		public void setZjdeductNumber(int zjdeductNumber) {
			this.zjdeductNumber = zjdeductNumber;
		}
		public void setFydeductNumber(int fydeductNumber) {
			this.fydeductNumber = fydeductNumber;
		}
		public void setKldeductNumber(int kldeductNumber) {
			this.kldeductNumber = kldeductNumber;
		}
		public void setCjdeductNumber(int cjdeductNumber) {
			this.cjdeductNumber = cjdeductNumber;
		}
		public void setTldeductNumber(int tldeductNumber) {
			this.tldeductNumber = tldeductNumber;
		}
		public int getZjnotEnoughNumber() {
			return zjnotEnoughNumber;
		}
		public int getFynotEnoughNumber() {
			return fynotEnoughNumber;
		}
		public int getKlnotEnoughNumber() {
			return klnotEnoughNumber;
		}
		public int getCjnotEnoughNumber() {
			return cjnotEnoughNumber;
		}
		public int getTlnotEnoughNumber() {
			return tlnotEnoughNumber;
		}
		public void setZjnotEnoughNumber(int zjnotEnoughNumber) {
			this.zjnotEnoughNumber = zjnotEnoughNumber;
		}
		public void setFynotEnoughNumber(int fynotEnoughNumber) {
			this.fynotEnoughNumber = fynotEnoughNumber;
		}
		public void setKlnotEnoughNumber(int klnotEnoughNumber) {
			this.klnotEnoughNumber = klnotEnoughNumber;
		}
		public void setCjnotEnoughNumber(int cjnotEnoughNumber) {
			this.cjnotEnoughNumber = cjnotEnoughNumber;
		}
		public void setTlnotEnoughNumber(int tlnotEnoughNumber) {
			this.tlnotEnoughNumber = tlnotEnoughNumber;
		}
	    
	    
}
