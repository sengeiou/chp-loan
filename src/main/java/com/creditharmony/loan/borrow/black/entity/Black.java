/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.black.entityBlack.java
 * @Create By 张灏
 * @Create In 2015年12月15日 上午9:47:46
 */
package com.creditharmony.loan.borrow.black.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * @Class Name Black
 * @author 张灏
 * @Create In 2015年12月15日
 */
public class Black extends DataEntity<Black> {
   
    private static final long serialVersionUID = 8308264900730114471L;
    private String loanCode;
    private String dictBlackType;
    private String blackMsg;
    private String dictSource;
    private String dictMarkType;
    
    public String getLoanCode() {
        return loanCode;
    }
    public void setLoanCode(String loanCode) {
        this.loanCode = loanCode;
    }
    public String getDictBlackType() {
        return dictBlackType;
    }
    public void setDictBlackType(String dictBlackType) {
        this.dictBlackType = dictBlackType;
    }
    public String getBlackMsg() {
        return blackMsg;
    }
    public void setBlackMsg(String blackMsg) {
        this.blackMsg = blackMsg;
    }
    public String getDictSource() {
        return dictSource;
    }
    public void setDictSource(String dictSource) {
        this.dictSource = dictSource;
    }
    public String getDictMarkType() {
        return dictMarkType;
    }
    public void setDictMarkType(String dictMarkType) {
        this.dictMarkType = dictMarkType;
    }
    
}
