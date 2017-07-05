package com.creditharmony.loan.borrow.poscard.inter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.service.pos.Pos_SelectBaseService;
import com.creditharmony.adapter.service.pos.bean.Pos_Select_InBean;
import com.creditharmony.adapter.service.pos.bean.Pos_Select_OutBean;
import com.creditharmony.loan.borrow.poscard.entity.PosInterResult;
import com.creditharmony.loan.borrow.poscard.entity.PosInterSelect;
import com.creditharmony.loan.borrow.poscard.service.PosCodMsInterService;

/**
 * pos催收查询接口访问控制类
 * 
 * @Class Name PosSelectService
 * @author wzq
 * @Create In 2016年2月23日
 */
@Service
public class PosSelectService extends Pos_SelectBaseService {

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
    public Pos_Select_OutBean doExec(Pos_Select_InBean inParam) {
        // 传入参数赋值
        PosInterSelect info = new PosInterSelect();
        info.setLoginName(inParam.getEmployeeID());
        info.setPosSn(inParam.getPosSn());
        info.setOrderNo(inParam.getOrderNo());
        // 查询数据库
        PosInterSelect outfo = posInterfaceService.getSelectService(info);
        // 返回参数赋值
        Pos_Select_OutBean outParam = new Pos_Select_OutBean();
        outParam.setResultCode(PosInterResult.SELECT_RESULT_SUCCESS.id);
        outParam.setResultMsg(PosInterResult.SELECT_RESULT_SUCCESS.name);
        outParam.setEmployeeID(outfo.getLoginName());
        outParam.setCompanyCode(outfo.getCompanyCode());
        outParam.setCompanyTel(outfo.getCompanyTel());
        outParam.setCompanyAddr(outfo.getCompanyAddr());
        outParam.setOrderNo(outfo.getOrderNo());
        outParam.setReceiverOrderNo(outfo.getReceiverOrderNo());
        outParam.setReceiverName(outfo.getReceiverName());
        outParam.setRceiverAddr(outfo.getRceiverAddr());
        outParam.setRceiverTel(outfo.getRceiverTel());
        outParam.setAmount(outfo.getAmount());
        outParam.setOrderStatus(outfo.getOrderStatus());
        outParam.setOrderStatusMsg(outfo.getOrderStatusMsg());
        return outParam;
    }

}
