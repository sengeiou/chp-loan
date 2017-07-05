/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.entityFyAreaCode.java
 * @Create By 张灏
 * @Create In 2016年3月8日 下午4:58:17
 */
package com.creditharmony.loan.common.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * 富友银行地区编码
 * @Class Name FyAreaCode
 * @author 张灏
 * @Create In 2016年3月8日
 */
public class FyAreaCode extends DataEntity<FyAreaCode> {
    /**
     * long serialVersionUID 
     */
    private static final long serialVersionUID = 1778001012262758988L;
    // 地区名称 
    private String areaName;
    // 地区编码
    private String areaCode;
    // 地区类型
    private String areaType;
    // 父级编号
    private String parentId;
    
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    public String getAreaType() {
        return areaType;
    }
    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
}
