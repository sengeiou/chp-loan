package com.creditharmony.loan.borrow.poscard.entity;

import com.creditharmony.core.persistence.DataEntity;

/**
 * pos催收登录接口实现类
 * 
 * @Class Name PosInterLogin
 * @author wzq
 * @Create In 2016年2月23日
 */
@SuppressWarnings("serial")
public class PosInterLogin extends DataEntity<PosInterLogin> {

    /** 序号. */
    private String id;
    /** 登陆工号. */
    private String loginName;
    /** 密码. */
    private String pospwd;
    /** Pos 机编号. */
    private String posSn;
    /** 姓名. */
    private String name;
    /** 单位编号. */
    private String companyId;
    /** 单位名称. */
    private String companyName;
    /** 单位地址. */
    private String address;
    /** 单位电话. */
    private String phone;
    /** 团队经理. */
    private String teamManager;
    /** 客服. */
    private String customerServ;
    /** 外访人员. */
    private String visitPerson;
    /** 服务经理. */
    private String storeAssistant;
    /** 门店经理. */
    private String storeManager;
    /** 是否可用. */
    private String status;
    /** 是否登录用户与员工. */
    private String hasLogin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPospwd() {
        return pospwd;
    }

    public void setPospwd(String pospwd) {
        this.pospwd = pospwd;
    }

    public String getPosSn() {
        return posSn;
    }

    public void setPosSn(String posSn) {
        this.posSn = posSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTeamManager() {
        return teamManager;
    }

    public void setTeamManager(String teamManager) {
        this.teamManager = teamManager;
    }

    public String getCustomerServ() {
        return customerServ;
    }

    public void setCustomerServ(String customerServ) {
        this.customerServ = customerServ;
    }

    public String getVisitPerson() {
        return visitPerson;
    }

    public void setVisitPerson(String visitPerson) {
        this.visitPerson = visitPerson;
    }

    public String getStoreAssistant() {
        return storeAssistant;
    }

    public void setStoreAssistant(String storeAssistant) {
        this.storeAssistant = storeAssistant;
    }

    public String getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(String storeManager) {
        this.storeManager = storeManager;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHasLogin() {
        return hasLogin;
    }

    public void setHasLogin(String hasLogin) {
        this.hasLogin = hasLogin;
    }

}
