package com.creditharmony.loan.borrow.poscard.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.IntoAccount;
import com.creditharmony.core.loan.type.Matching;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.type.UserStatus;
import com.creditharmony.loan.borrow.payback.entity.PaybackBuleAmont;
import com.creditharmony.loan.borrow.poscard.dao.PosCodMsInterDao;
import com.creditharmony.loan.borrow.poscard.entity.PosBacktage;
import com.creditharmony.loan.borrow.poscard.entity.PosInterLogin;
import com.creditharmony.loan.borrow.poscard.entity.PosInterPay;
import com.creditharmony.loan.borrow.poscard.entity.PosInterResult;
import com.creditharmony.loan.borrow.poscard.entity.PosInterSelect;
/**
 * pos催收接口 核心控制service类
 * 
 * @Class Name PosInterfaceService
 * @author wzq
 * @Create In 2016年2月23日
 */
@Service
@Transactional(readOnly = true, value = "transactionManager")
public class PosCodMsInterService {

    /** pos催收接口数据传输对象 */
    @Autowired
    private PosCodMsInterDao posInterfaceDao;
    

    /**
     * pos催收接口，登录查询数据。验证用户名和密码是否正确
     * 
     * @param info 传入数据
     * @return resfo 返回数据
     */
    public PosInterLogin getLoginService(PosInterLogin info) {
        PosInterLogin resfo = new PosInterLogin();
        //团队经理
        info.setTeamManager(LoanRole.TEAM_MANAGER.id);
        //客服
        info.setCustomerServ(LoanRole.CUSTOMER_SERVICE.id);
        //外访人员
        info.setVisitPerson(LoanRole.VISIT_PERSON.id);
        //服务经理
        info.setStoreAssistant(LoanRole.STORE_SERVICE_MANAGER.id);
        //门店经理
        info.setStoreManager(LoanRole.STORE_MANAGER.id);
        //用户是否可登陆
        info.setStatus(UserStatus.ON);
        info.setHasLogin(User.HAS_LOGIN_YES);
        //查询数据库，验证用户名和密码
        List<PosInterLogin> lst = posInterfaceDao.getPosLoginList(info);
        if (lst.size() > 0) {
            //返回赋值
            resfo = lst.get(0);
        }
        return resfo;
    }

    /**
     * pos催收接口，查询交易。查询订单信息
     * 
     * @param info 传入数据
     * @return 返回数据
     */
    public PosInterSelect getSelectService(PosInterSelect info) {

        //查询交易接口 查询订单并返回
        //查询还款方式为pos机刷卡
        info.setDictRepayMethod(RepayChannel.POS.getCode()); 
        //还款状态为待还款   待还款
        info.setDictPaybackStatus(RepayApplyStatus.TO_PAYMENT.getCode());  
        PosInterSelect resinfo = posInterfaceDao.getPosSelectParam(info);
        if(resinfo == null){
            resinfo = new PosInterSelect();
            //查无此单
            resinfo.setLoginName(info.getLoginName());
            resinfo.setOrderNo(info.getOrderNo());
            resinfo.setReceiverOrderNo(info.getOrderNo());
            resinfo.setOrderStatus(PosInterResult.SELECT_ORDER_STATUS_CWCD.id);
            resinfo.setOrderStatusMsg(PosInterResult.SELECT_ORDER_STATUS_CWCD.name);
        }else{
            //订单存在，由于查询订单属于未还款或者逾期订单。设定订单状态为“未支付，未签收”
            resinfo.setOrderStatus(PosInterResult.SELECT_ORDER_STATUS_WZFWQS.id);
            resinfo.setOrderStatusMsg(PosInterResult.SELECT_ORDER_STATUS_WZFWQS.name); 
        }
         return resinfo;
    }

    /**
     * pos催收接口，付款交易。 传入数据与门店待办进行匹配,根据四项查询，若存在，则匹配成功。订单号、金额、交易参考号、时间
     * 若匹配，修改还款状态。在pos已匹配成功列表中，修改交易信息 若不匹配，pos后台数据列表中新增交易信息。
     * 
     * @param info 传入数据
     */
    public void getPayService(PosInterPay info) {
        // 查询 还款申请交易数据是否存在
        PosInterPay outfo = posInterfaceDao.getPosPayParam(info);
        PosBacktage pb = new PosBacktage();
        pb.preInsert();   //id
        pb.setPosOrderNumber(info.getOrderNo());  //pos订单号
        pb.setReferCode(info.getReferNo());  //参考号
        pb.setApplyReallyAmount(info.getAmount());  //金额
        pb.setPaybackDate(new Date());  //到账日期
        pb.setCreateBy(info.getEmployeeID());
        pb.setDepositedAccount(IntoAccount.YIBAO.getCode());  //默认存入易宝账户
        if (outfo != null) {
           //已匹配
           //还款状态修改为【已还款】4，，失败原因修改为【交易成功】
            info.setSplitBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());  //回盘结果修改为【成功】2
            info.setDictPaybackStatus(RepayApplyStatus.HAS_PAYMENT.getCode());  //已还款
            //修改还款信息
            posInterfaceDao.updatePosPay(info);
            //修改蓝补
            updateBuleAmont(outfo.getContractCode(),info.getAmount());
            //蓝补
            PaybackBuleAmont(info,outfo);
            pb.setAuditDate(new Date());   //查账日期
            pb.setPayBackApplyId(outfo.getId());
            pb.setMatchingState(Matching.MATCHED.getCode());
            pb.setContractCode(outfo.getContractCode());
        } else {
            //未匹配
            pb.setMatchingState(Matching.NO_MATCH.getCode());
        }
        //新增pos催收列表信息
        posInterfaceDao.savePosBackList(pb);
    }

    /**
     * 蓝补操作
     * @param info  业务传入数据
     * @param outfo  查询获得数据
     */
    @Transactional(value = "loanTransactionManager",readOnly = false)
    private void PaybackBuleAmont(PosInterPay info,PosInterPay outfo){
        PaybackBuleAmont paybackBuleAmont = new PaybackBuleAmont();
        paybackBuleAmont.preInsert();

        paybackBuleAmont.setTradeType(TradeType.TRANSFERRED.getCode());// 交易类型
        paybackBuleAmont.setTradeAmount(info.getAmount());// 交易金额
        paybackBuleAmont.setSurplusBuleAmount(outfo.getPaybackBuleAmount().add(info.getAmount()));// 蓝补余额

        paybackBuleAmont.setOperator(posInterfaceDao.getUserId(info.getEmployeeID()));// 操作人
        paybackBuleAmont.setContractCode(outfo.getContractCode());//合同编号

        paybackBuleAmont.setDealTime(new Date());// 交易时间
        
        posInterfaceDao.addBackBuleAmont(paybackBuleAmont);
    }
    
    /**
     * 修改蓝补金额
     * @param applyId
     * @param acount
     */
    private void updateBuleAmont(String contractCode,BigDecimal amount){
        PosInterPay info = new PosInterPay();
        info.setContractCode(contractCode);
        info.setAmount(amount);
        posInterfaceDao.updateBuleAmont(info);
    }

}
