package com.creditharmony.loan.borrow.poscard.inter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.service.pos.Pos_LoginBaseService;
import com.creditharmony.adapter.service.pos.bean.Pos_Login_InBean;
import com.creditharmony.adapter.service.pos.bean.Pos_Login_OutBean;
import com.creditharmony.loan.borrow.poscard.entity.PosInterLogin;
import com.creditharmony.loan.borrow.poscard.entity.PosInterResult;
import com.creditharmony.loan.borrow.poscard.service.PosCodMsInterService;

/**
 * pos催收登录接口访问控制类
 * 
 * @Class Name
 * @author wzq
 * @Create In 2016年2月23日
 */
@Service
public class PosLoginService extends Pos_LoginBaseService {

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
    public Pos_Login_OutBean doExec(Pos_Login_InBean inParam) {
        // 传入参数赋值
        PosInterLogin info = new PosInterLogin();
        info.setLoginName(inParam.getEmployeeID());
        info.setPospwd(inParam.getPassword());
        // 获取返回参数
        PosInterLogin outfo = posInterfaceService.getLoginService(info);
        // 返回参数赋值
        Pos_Login_OutBean outParam = new Pos_Login_OutBean();
        if (outfo.getId() == null) { 
            //未查询到该用户
            outParam.setResultCode(PosInterResult.LOGIN_RESULT_FAIL.id);
            outParam.setResultMsg(PosInterResult.LOGIN_RESULT_FAIL.name);
        } else {
            //用户名和pos密码匹配，运行登录
            outParam.setResultCode(PosInterResult.LOGIN_RESULT_SUCCESS.id);
            outParam.setResultMsg(PosInterResult.LOGIN_RESULT_SUCCESS.name);
            outParam.setEmployeeID(outfo.getLoginName());
            outParam.setEmployeeName(outfo.getName());
            outParam.setCompanyCode(outfo.getCompanyId());
            outParam.setCompanyName(outfo.getCompanyName());
            outParam.setCompanyAddr(outfo.getAddress());
            outParam.setCompanyTel(outfo.getPhone());
        }
        return outParam;
    }

}
