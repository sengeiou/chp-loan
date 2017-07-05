package com.creditharmony.loan.borrow.poscard.dao;

import java.util.List;

import com.creditharmony.core.persistence.CrudDao;
import com.creditharmony.core.persistence.annotation.LoanBatisDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.poscard.entity.PosBacktage;
import com.creditharmony.loan.borrow.poscard.entity.PosInterLogin;
import com.creditharmony.loan.borrow.poscard.entity.PosInterPay;
import com.creditharmony.loan.borrow.poscard.entity.PosInterSelect;

/**
 * pos催收接口数据传输dao控制类
 * 
 * @Class Name PosInterfaceDao
 * @author wzq
 * @Create In 2016年2月23日
 */
@LoanBatisDao
public interface PosCodMsInterDao extends CrudDao<PosInterLogin> {

    /**
     * 登录接口
     * 
     * @param info
     * @return
     */
    public List<PosInterLogin> getPosLoginList(PosInterLogin info);

    /**
     * 查询接口
     * 
     * @param info
     * @return
     */
    public PosInterSelect getPosSelectParam(PosInterSelect info);

    /**
     * 付款交易接口查询匹配情况
     * 
     * @param info
     * @return
     */
    public PosInterPay getPosPayParam(PosInterPay info);
    
    /**
     * 付款交易接口修改还款申请信息
     * @param info
     */
    public void updatePosPay(PosInterPay info);

    /**
     * 付款交易接口 新增pos催收列表信息
     * 
     * @param info
     */
    public void savePosBackList(PosBacktage info);
    
    /**
     * 修改蓝补
     * @param info
     */
    public void updateBuleAmont(PosInterPay info);
    
    /**
     * 保存蓝补信息 
     * 
     * @param paybackBuleAmont
     * @return none
     */
    public void addBackBuleAmont(PaybackBuleAmont paybackBuleAmont);
    
    /**
     * 获取用户id
     * @param userCode
     * @return
     */
    public String getUserId(String userCode);
}
