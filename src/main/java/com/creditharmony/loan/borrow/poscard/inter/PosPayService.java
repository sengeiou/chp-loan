package com.creditharmony.loan.borrow.poscard.inter;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.service.pos.Pos_PayBaseService;
import com.creditharmony.adapter.service.pos.bean.Pos_Pay_InBean;
import com.creditharmony.adapter.service.pos.bean.Pos_Pay_OutBean;
import com.creditharmony.loan.borrow.poscard.entity.PosInterPay;
import com.creditharmony.loan.borrow.poscard.entity.PosInterResult;
import com.creditharmony.loan.borrow.poscard.service.PosCodMsInterService;

/**
 * pos催收付款交易接口访问控制类
 * 
 * @Class Name
 * @author wzq
 * @Create In 2016年2月23日
 */
@Service
public class PosPayService extends Pos_PayBaseService {

    // pos催收接口核心控制类
    @Autowired
    private PosCodMsInterService posInterfaceService;

    /**
     * 接口组访问汇金业务调用方法
     * 
     * @param inParam 传入参数
     * @author outParam 返回参数
     */
    @Override
    public Pos_Pay_OutBean doExec(Pos_Pay_InBean inParam) {
        // 传入参数赋值
        PosInterPay info = new PosInterPay();
        // 四项匹配即可 订单号、金额、交易参考号、时间
        info.setEmployeeID(inParam.getEmployeeID());
        info.setPosSn(inParam.getPosSn());
        info.setOrderNo(inParam.getOrderNo()); // 订单号
        info.setAmount(new BigDecimal(inParam.getAmount())); // 金额
        info.setReferNo(inParam.getReferNo()); // 交易参考号
        // 付款交易数据匹配
        posInterfaceService.getPayService(info);
        // 返回参数赋值
        Pos_Pay_OutBean outParam = new Pos_Pay_OutBean();
        outParam.setResultCode(PosInterResult.PAY__RESULT_SUCCESS.id);
        outParam.setResultMsg(PosInterResult.PAY__RESULT_SUCCESS.name);
        outParam.setReferNo(info.getReferNo());
        outParam.setOrderNo(info.getOrderNo());
        return outParam;
    }

}
