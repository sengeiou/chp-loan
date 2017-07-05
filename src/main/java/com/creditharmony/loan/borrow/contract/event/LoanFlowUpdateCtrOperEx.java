/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.contract.eventLoanFlowUpdateCtrOperEx.java
 * @Create By 张灏
 * @Create In 2015年12月11日 上午11:38:55
 */
package com.creditharmony.loan.borrow.contract.event;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.ContractLog;
import com.creditharmony.core.loan.type.ContractResult;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanTrustState;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractOperateInfoDao;
import com.creditharmony.loan.borrow.contract.dao.ContractTempDao;
import com.creditharmony.loan.borrow.contract.dao.CustInfoDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractOperateInfo;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.CustInfo;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contract.util.ProductUtil;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.common.consts.SystemSetFlag;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;

/**
 * 状态设置回调方法
 * 
 * @Class Name LoanFlowUpdateCtrOperEx
 * @author 张灏
 * @Create In 2015年12月11日
 */
@Service("ex_hj_loanflow_updateCtrOper")
@Transactional(readOnly = false, value = "loanTransactionManager")
public class LoanFlowUpdateCtrOperEx extends BaseService implements ExEvent {
    private static final org.slf4j.Logger log = LoggerFactory
            .getLogger(LoanFlowUpdateCtrOperEx.class);
    @Autowired
    private ContractOperateInfoDao contractOperateInfoDao;

    @Autowired
    private ContractService contractService;

    @Autowired
    private LoanStatusHisDao loanStatusHisDao;

    @Autowired
    private ApplyLoanInfoDao loanInfoDao;

    @Autowired
    private ReconsiderApplyDao reconsiderApplyDao;

    @Autowired
    private SystemSetMaterDao systemSetMaterDao;
    
    @Autowired
    private CustInfoDao custInfoDao;
    
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ContractTempDao contractTempDao;
    @Autowired
    private AssistService assistService;

    @Override
    public void invoke(WorkItemView workItem) {
        String stepName = workItem.getStepName();
        Contract contract = null;
        ContractOperateInfo contractOperateInfo = new ContractOperateInfo();
        String applyId = workItem.getBv().getApplyId();
        String response = workItem.getResponse();
        String countFlag="0";
        log.info(stepName
                + "节点获取response时错误。--------------invoke response is : "
                + response);
        ContractBusiView contractBusiView = (ContractBusiView) workItem.getBv();
        String operType = contractBusiView.getOperType();
        /**
         * 正常提交、退回时operType 为空 更新渠道标识时 operType为0 更新冻结标识时operType为1
         * 正常提交、退回才走下面的逻辑
         */
        if (!YESNO.NO.getCode().equals(operType)
                && !YESNO.YES.getCode().equals(operType)) {
            contract = contractBusiView.getContract();
            String remark = contractBusiView.getRemarks();
            String operResultCode = contractBusiView.getDictOperateResult();
            String operResultName = null;
            ContractResult contractResult = ContractResult
                    .parseByCode(operResultCode);
            if(contractResult!=null){
            	operResultName = contractResult.getName();
            }
            //手动验证结果
            //退回原因
            String returnReason=contractBusiView.getReturnReason();
            //手动验证不通过
            String verification=contractBusiView.getVerification();
            ContractResult contractResultTwo = ContractResult.parseByCode(verification);
            //转换后的Name名称
            String verificationName=null;
            if(contractResultTwo!=null){
            	verificationName=contractResultTwo.getName();
            }
            String kingStatus = null;
            /*
             * // 退回 if(ContractResult.CONTRACT_BACKUP.equals(operResultCode)){
             * operResultName = ContractConstant.CONTRACT_BACKUP_NAME; // 拒绝
             * }else
             * if(ContractConstant.CONTRACT_REJECT.equals(operResultCode)){
             * operResultName = ContractConstant.CONTRACT_REJECT_NAME; // 提交（通过）
             * }else
             * if(ContractConstant.CONTRACT_SUBMIT.equals(operResultCode)){
             * operResultName = ContractConstant.CONTRACT_SUBMIT_NAME; // 客户放弃
             * }else
             * if(ContractConstant.CONTRACT_GIVEUP.equals(operResultCode)){
             * operResultName = ContractConstant.CONTRACT_GIVEUP_NAME; }
             */
            String status = "00";
            String statusName = "";
            String backFlag = null; // 标明合同阶段退回 1表示有退回
            String backReason = "";
            if (ContractConstant.RATE_AUDIT.equals(stepName)) { // 利率审核
                if (LoanFlowRoute.CONFIRMSIGN.equals(response)) {
                    status = LoanApplyStatus.SIGN_CONFIRM.getCode();
                    statusName = LoanApplyStatus.SIGN_CONFIRM.getName();
                    // 设置签约超时时间,通过ApplyId（信借、复议）查询历史，判定有没有到过确认签署
                    LoanStatusHis queryParam = new LoanStatusHis();
                    queryParam.setApplyId(applyId);
                    queryParam.setDictSysFlag(ModuleName.MODULE_LOAN.value);
                    queryParam.setDictLoanStatus(LoanApplyStatus.SIGN_CONFIRM
                            .getCode());
                    List<LoanStatusHis> hisList = loanStatusHisDao
                            .findWantedNoteByApplyId(queryParam);
                    if (ObjectHelper.isEmpty(hisList) || hisList.size() == 0) {
                        // 判断是否是复议过来的单子
                        if (applyId.startsWith("HJ0002")) {
                            
                            LoanStatusHis loanStatusHis = new LoanStatusHis();
                            loanStatusHis.setLoanCode(contractBusiView
                                    .getContract().getLoanCode());
                            loanStatusHis
                                    .setDictSysFlag(ModuleName.MODULE_LOAN.value);
                            loanStatusHis
                                    .setDictLoanStatus(LoanApplyStatus.SIGN_CONFIRM
                                            .getCode());
                            List<LoanStatusHis> hisStatus = loanStatusHisDao
                                    .findWantedNoteByLoanCode2(loanStatusHis);
                            // 汇诚阶段发起的复议
                            if(ObjectHelper.isEmpty(hisStatus) || hisStatus.size()==0){
                                contractBusiView.setTimeOutFlag(YESNO.YES.getCode());
                                Calendar cal = this.getTimeOutPoint(new Date());
                                contractBusiView.setTimeOutPoint(cal.getTime()); 
                                LoanInfo loanInfo =new LoanInfo();
                                if(ProductUtil.PRODUCT_NXD.getCode().equals(contractBusiView.getProductType())){
                                	loanInfo.setFeeCredit(new BigDecimal(100));
                                }else{
                                	loanInfo.setFeeCredit(new BigDecimal(0));
                                }
                                loanInfo.setLoanCode(contractBusiView
                                        .getContract().getLoanCode());
                                loanInfo.setTimeOut(cal.getTime());
                                loanInfoDao.updateLoanInfo(loanInfo);
                            }else{  // 确认签署发起的复议
                                ReconsiderApply reconsiderApply = reconsiderApplyDao.selectByApply(applyId);
                                // 获取复议发起时间
                                Calendar reconsiderTime = Calendar.getInstance();
                                reconsiderTime.setTime(reconsiderApply.getCreateTime());
                                Calendar reconsiderDay = Calendar.getInstance();
                                reconsiderDay.set(Calendar.YEAR,reconsiderTime.get(Calendar.YEAR));
                                reconsiderDay.set(Calendar.MONTH,reconsiderTime.get(Calendar.MONTH));
                                reconsiderDay.set(Calendar.DAY_OF_MONTH,reconsiderTime.get(Calendar.DAY_OF_MONTH));
                                // 获取当前时间
                                Calendar nowTime = Calendar.getInstance();
                                nowTime.setTime(new Date());
                                Calendar nowDay = Calendar.getInstance();
                                nowDay.set(Calendar.YEAR, nowTime.get(Calendar.YEAR));
                                nowDay.set(Calendar.MONTH,nowTime.get(Calendar.MONTH));
                                nowDay.set(Calendar.DAY_OF_MONTH,nowTime.get(Calendar.DAY_OF_MONTH));
                                Long result = nowDay.getTimeInMillis()-reconsiderDay.getTimeInMillis();
                                // 如果复议发起时间 跟当前时间相等，则超时时间不更新，维持为原先设定的时间
                                if (result == 0) {
                                    contractBusiView.setTimeOutFlag(YESNO.YES
                                        .getCode());
                                    Calendar cal = this.getTimeOutPoint(hisStatus
                                        .get(0).getOperateTime());
                                    contractBusiView.setTimeOutPoint(cal.getTime());
                                    LoanInfo loanInfo =new LoanInfo();
                                    if(ProductUtil.PRODUCT_NXD.getCode().equals(contractBusiView.getProductType())){
                                    	loanInfo.setFeeCredit(new BigDecimal(100));
                                    }else{
                                    	loanInfo.setFeeCredit(new BigDecimal(0));
                                    }
                                    loanInfo.setLoanCode(contractBusiView
                                            .getContract().getLoanCode());
                                    loanInfo.setTimeOut(cal.getTime());
                                    loanInfoDao.updateLoanInfo(loanInfo);
                                    /**
                                     * 如果复议发起时间 跟当前时间不相等，
                                     * 如果复议成功（金额或者期限改变）则超时时间的计算方式跟正常信借相同， 如果不成功
                                     * 则在原先信借的截至时间上延长时段，时段的计算方式为（复议通过的时间-复议发起的时间）
                                     * 
                                     */
                                } else { // 复议时间不等
                                    LoanInfo loanInfo = loanInfoDao.getByLoanCode(contractBusiView.getContract().getLoanCode());
                                    // 查询正常信借的审批信息
                                    CustInfo verify= custInfoDao.findAuditInfo(loanInfo.getApplyId());
                                    // 查询复议的审批信息
                                    CustInfo reconsider = custInfoDao.findAuditInfo(applyId);
                                    int amountResult = verify.getAuditAmount().compareTo(reconsider.getAuditAmount());
                                    int limit = verify.getContractMonths().compareTo(reconsider.getContractMonths());
                                    // 复议成功
                                    if(amountResult!=0 || limit!=0){
                                        contractBusiView.setTimeOutFlag(YESNO.YES.getCode());
                                        Calendar cal = this.getTimeOutPoint(new Date());
                                        contractBusiView.setTimeOutPoint(cal.getTime()); 
                                        LoanInfo loanInfo1 =new LoanInfo();
                                        loanInfo1.setLoanCode(contractBusiView
                                                .getContract().getLoanCode());
                                        loanInfo1.setTimeOut(cal.getTime());
                                        if(ProductUtil.PRODUCT_NXD.getCode().equals(contractBusiView.getProductType())){
                                        	loanInfo1.setFeeCredit(new BigDecimal(100));
                                        }else{
                                        	loanInfo1.setFeeCredit(new BigDecimal(0));
                                        }
                                        loanInfoDao.updateLoanInfo(loanInfo1);
                                    }else{ // 复议失败
                                        contractBusiView.setTimeOutFlag(YESNO.YES.getCode());
                                        Calendar cal = this.getTimeOutPoint(hisStatus.get(0).getOperateTime());
                                        long frozenDay = result/(1000 * 60 * 60 * 24);
                                        cal.add(Calendar.DAY_OF_MONTH, (int)frozenDay);
                                        contractBusiView.setTimeOutPoint(cal.getTime());
                                        LoanInfo loanInfo1 =new LoanInfo();
                                        if(ProductUtil.PRODUCT_NXD.getCode().equals(contractBusiView.getProductType())){
                                        	loanInfo1.setFeeCredit(new BigDecimal(100));
                                        }else{
                                        	loanInfo1.setFeeCredit(new BigDecimal(0));
                                        }
                                        loanInfo1.setLoanCode(contractBusiView
                                                .getContract().getLoanCode());
                                        loanInfo1.setTimeOut(cal.getTime());
                                        loanInfoDao.updateLoanInfo(loanInfo1);
                                    }
                                }
                            }
                        } else {
                            contractBusiView.setTimeOutFlag(YESNO.YES.getCode());
                            Calendar cal = this.getTimeOutPoint(new Date());
                            contractBusiView.setTimeOutPoint(cal.getTime());
                            LoanInfo loanInfo =new LoanInfo();
                            if(ProductUtil.PRODUCT_NXD.getCode().equals(contractBusiView.getProductType())){
                            	loanInfo.setFeeCredit(new BigDecimal(100));
                            }else{
                            	loanInfo.setFeeCredit(new BigDecimal(0));
                            }
                            loanInfo.setLoanCode(contractBusiView
                                    .getContract().getLoanCode());
                            loanInfo.setTimeOut(cal.getTime());
                            loanInfoDao.updateLoanInfo(loanInfo);
                        }
                    }
                } else if (LoanFlowRoute.FINALJUDGTEAM.equals(response)) {
                    status = LoanApplyStatus.RARE_BACK.getCode();
                    statusName = LoanApplyStatus.RARE_BACK.getName();
                    backFlag = YESNO.YES.getCode();
                    backReason = remark;
                } else if (LoanFlowRoute.LETTERREVIEW.equals(response)) {
                    status = LoanApplyStatus.RARE_BACK.getCode();
                    statusName = LoanApplyStatus.RARE_BACK.getName();
                    backFlag = YESNO.YES.getCode();
                    backReason = remark;
                } else if (LoanFlowRoute.FINALJUDG.equals(response)) {
                    status = LoanApplyStatus.RARE_BACK.getCode();
                    statusName = LoanApplyStatus.RARE_BACK.getName();
                    backFlag = YESNO.YES.getCode();
                    backReason = remark;
                } else if (LoanFlowRoute.RECONSIDERREVIEW.equals(response)) {
                    status = LoanApplyStatus.RARE_BACK.getCode();
                    statusName = LoanApplyStatus.RARE_BACK.getName();
                    backFlag = YESNO.YES.getCode();
                    backReason = remark;
                } else if (LoanFlowRoute.RECONSIDERFINALJUDG.equals(response)) {
                    status = LoanApplyStatus.RARE_BACK.getCode();
                    statusName = LoanApplyStatus.RARE_BACK.getName();
                    backFlag = YESNO.YES.getCode();
                    backReason = remark;
                }
                contractOperateInfo
                        .setDictOperateType(ContractLog.RATE_TO_VERIFY
                                .getCode());
            }
            if (ContractConstant.CONFIRM_SIGN.equals(stepName)) { // 确认签署
                contract = contractService.findByApplyId(applyId);
                contractOperateInfo.setRemarks(null);
                if (LoanFlowRoute.CONTRACTMAKE.equals(response)) {
                    status = LoanApplyStatus.CONTRACT_MAKE.getCode();
                    statusName = LoanApplyStatus.CONTRACT_MAKE.getName();
                } else if (LoanFlowRoute.GIVEUP.equals(response)) {
                    status = LoanApplyStatus.CUSTOMER_GIVEUP.getCode();
                    statusName = LoanApplyStatus.CUSTOMER_GIVEUP.getName();
                } else if (LoanFlowRoute.BACKSTORE.equals(response)) {
                    status = LoanApplyStatus.BACK_STORE.getCode();
                    statusName = LoanApplyStatus.BACK_STORE.getName();
                    backFlag = YESNO.YES.getCode();
                    backReason = remark;
                } else if (LoanFlowRoute.RECONSIDERBACKSTORE.equals(response)) {
                    status = LoanApplyStatus.RECONSIDER_BACK_STORE.getCode();
                    statusName = LoanApplyStatus.RECONSIDER_BACK_STORE
                            .getName();
                    backFlag = YESNO.YES.getCode();
                    backReason = remark;
                }else if(LoanFlowRoute.TO_PROPOSE_OUT1.equals(response)){
                	 countFlag="1";
                	 status = LoanApplyStatus.PROPOSE_OUT.getCode();
                     statusName = LoanApplyStatus.PROPOSE_OUT.getName();
                     backFlag = YESNO.YES.getCode();
                }
                contractBusiView.getContract().setLoanCode(contract.getLoanCode());
                contractOperateInfo.setDictOperateType(ContractLog.SIGN_CONFIRM
                        .getCode());
                backReason = remark;
                
                String isreceive =contractBusiView.getIsreceive();
                if(StringUtils.isNotEmpty(isreceive)){
                	Map<String,String> param=new HashMap<String, String>();
                	param.put("loanCode", contract.getLoanCode());
                	contractTempDao.updateContractTemp1(param);
                	for(String str :isreceive.split(",")){
                		param.put("isreceive", str);
                		contractTempDao.updateContractTemp(param);
                	}
                	if(isreceive.indexOf(",")<0){
                		//一条数据取外访费之和
                		contractTempDao.updateContractFeeTemp(param);
                	}else{
                		//多条取原始外访费
                		//contractTempDao.updateContractFeeTemp1(param);
                	}
                }
            } else if (ContractConstant.CTR_CREATE.equals(stepName)) { // 合同制作
            	 Map<String,Object> param =new HashMap<String, Object>();
                 param.put("loanCode", contractBusiView.getContract().getLoanCode());
                if (LoanFlowRoute.CONTRACTSIGN.equals(response)) {
                    status = LoanApplyStatus.CONTRACT_MAKING.getCode();
                    statusName = LoanApplyStatus.CONTRACT_MAKING.getName();
                    LoanInfo loanInfo= loanInfoDao.selectByLoanCode(param);
                    if(!"1".equals(loanInfo.getIssplit())){
                    	addFileNet(workItem);
                    }
                } else if (LoanFlowRoute.CONFIRMSIGN.equals(response)) {
                    status = LoanApplyStatus.SIGN_CONFIRM.getCode();
                    statusName = LoanApplyStatus.SIGN_CONFIRM.getName();
                    backFlag = ContractConstant.BACK_FLAG;
                    backReason = remark;
                    contractTempDao.updateContractFeeTempPetition(param);
                } else if (LoanFlowRoute.RATECHECK.equals(response)) {
                    status = LoanApplyStatus.RATE_TO_VERIFY.getCode();
                    statusName = LoanApplyStatus.RATE_TO_VERIFY.getName();
                    backFlag = ContractConstant.BACK_FLAG;
                    backReason = remark;
                    contractTempDao.updateContractFeeTempPetition(param);
                }
                contractOperateInfo
                        .setDictOperateType(ContractLog.CONTRACT_MAKE.getCode());
            } else if (ContractConstant.CUST_SERVICE_SIGN.equals(stepName)) { // 合同签订上传
                if (LoanFlowRoute.CONTRACTCHECK.equals(response)) {
                    status = LoanApplyStatus.CONTRACT_AUDIFY.getCode();
                    statusName = LoanApplyStatus.CONTRACT_AUDIFY.getName();
                    // 停止签约时间检测
                    contractBusiView.setTimeOutFlag(YESNO.NO.getCode());
                    Map<String,Object> param =new HashMap<String, Object>();
                    param.put("loanCode", contractBusiView.getContract().getLoanCode());
                    LoanInfo loanInfo= loanInfoDao.selectByLoanCode(param);
                    if(!"1".equals(loanInfo.getIssplit())){
                    	addFileNet(workItem);
                    }
                    assistService.updateAssistAddAuditOperator(contractBusiView.getContract().getLoanCode());
                    
                } else if (LoanFlowRoute.CONFIRMSIGN.equals(response)) {
                    status = LoanApplyStatus.SIGN_CONFIRM.getCode();
                    statusName = LoanApplyStatus.SIGN_CONFIRM.getName();
                    backFlag = ContractConstant.BACK_FLAG;
                    backReason = remark;
                } else if (LoanFlowRoute.BACKSTORE.equals(response)) {
                    status = LoanApplyStatus.BACK_STORE.getCode();
                    statusName = LoanApplyStatus.BACK_STORE.getName();
                    backFlag = YESNO.YES.getCode();
                } else if (LoanFlowRoute.RECONSIDERBACKSTORE.equals(response)) {
                    status = LoanApplyStatus.RECONSIDER_BACK_STORE.getCode();
                    statusName = LoanApplyStatus.RECONSIDER_BACK_STORE
                            .getName();
                    backFlag = YESNO.YES.getCode();
                }else if (LoanFlowRoute.TO_PROPOSE_OUT2.equals(response)) {
                    if (ContractResult.CONTRACT_GIVEUP1.getCode().equals(
                            operResultCode)) {
                        status = LoanApplyStatus.PROPOSE_OUT.getCode();
                        statusName = LoanApplyStatus.PROPOSE_OUT.getName();
                        countFlag="1";
                    } else if (ContractResult.CONTRACT_REJECT1.getCode().equals(
                            operResultCode)) {
                        status = LoanApplyStatus.PROPOSE_REFUSE.getCode();
                        statusName = LoanApplyStatus.PROPOSE_REFUSE.getName();
                        countFlag="2";
                    }
                    backFlag = YESNO.YES.getCode();
                    backReason = remark;
                }else if (StringUtils.isEmpty(response)
                        || LoanFlowRoute.GIVEUP.equals(response)) {
                    if (ContractResult.CONTRACT_GIVEUP.getCode().equals(
                            operResultCode)) {
                        status = LoanApplyStatus.CUSTOMER_GIVEUP.getCode();
                        statusName = LoanApplyStatus.CUSTOMER_GIVEUP.getName();
                    } else if (ContractResult.CONTRACT_REJECT.getCode().equals(
                            operResultCode)) {
                        status = LoanApplyStatus.STORE_REJECT.getCode();
                        statusName = LoanApplyStatus.STORE_REJECT.getName();
                    }
                }
                contractOperateInfo
                        .setDictOperateType(ContractLog.CONTRACT_UPLOAD
                                .getCode());
            } else if (ContractConstant.CTR_AUDIT.equals(stepName)) { // 合同审核
                if (LoanFlowRoute.PAYCONFIRM.equals(response)) { // 放款确认
                    status = LoanApplyStatus.LOAN_SEND_CONFIRM.getCode();
                    statusName = LoanApplyStatus.LOAN_SEND_CONFIRM.getName();
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                    try{
                    	contractBusiView.setCheckTime(sdf.parse(sdf.format(new Date())));
                    }catch(Exception e){
                    	
                    }
                } else if (LoanFlowRoute.KING_OPEN.equals(response)) { // 金帐户开户
                    status = LoanApplyStatus.KING_TO_OPEN.getCode();
                    statusName = LoanApplyStatus.KING_TO_OPEN.getName();
                    kingStatus = LoanTrustState.WKH.value; // 设置金账户状态为未开户
                } else if (LoanFlowRoute.CONFIRMSIGN.equals(response)) { // 签约确认
                    status = LoanApplyStatus.SIGN_CONFIRM.getCode();
                    statusName = LoanApplyStatus.SIGN_CONFIRM.getName();
                    backFlag = ContractConstant.BACK_FLAG;
                  //手动验证不通过退回得原因
                    if(returnReason!=null && !returnReason.equals("")){
                    	backReason = returnReason;
                    }else{
                    	//合同审核退回得原因
                        if(remark!=null && !remark.equals("")){
                        	backReason = remark;
                        }
                    }                    // 开始签约时间检测
                    contractBusiView.setTimeOutFlag(YESNO.YES.getCode());
                } else if (LoanFlowRoute.CONTRACTSIGN.equals(response)) { // 签订合同
                    status = LoanApplyStatus.CONTRACT_UPLOAD.getCode();
                    statusName = LoanApplyStatus.CONTRACT_UPLOAD.getName();
                    backFlag = ContractConstant.BACK_FLAG;
                  //手动验证不通过退回得原因
                    if(returnReason!=null && !returnReason.equals("")){
                    	backReason = returnReason;
                    }else{
                    	//合同审核退回得原因
                        if(remark!=null && !remark.equals("")){
                        	backReason = remark;
                        }
                    }
                    // 开始签约时间检测
                    contractBusiView.setTimeOutFlag(YESNO.YES.getCode());
                }
                Map<String,Object> param =new HashMap<String, Object>();
                param.put("loanCode", contractBusiView.getContract().getLoanCode());
                LoanInfo loanInfo= loanInfoDao.selectByLoanCode(param);
                if(!"1".equals(loanInfo.getIssplit())){
                	addFileNet(workItem);
                }
                contractOperateInfo
                        .setDictOperateType(ContractLog.CONTRACT_AUDIFY
                                .getCode());
            }
            Map<String, Object> query = new HashMap<String, Object>();
            query.put("loanCode", contractBusiView.getContract().getLoanCode());
            query.put("dictOperateResult",
                    ContractResult.CONTRACT_REBACK.getCode());
            ContractOperateInfo coi = contractOperateInfoDao
                    .findBackNode(query);
            // 用下一个状态跟首次退回的节点比对,如果比对上，而且backFlag不为1 则取消退回标识
            if (!ObjectHelper.isEmpty(coi)
                    && !ContractConstant.BACK_FLAG.equals(backFlag)
                    && contractOperateInfo.getDictOperateType().equals(
                            coi.getDictOperateType())) {
                backFlag = ContractConstant.CANCEL_BACK_FLAG;
            }
	        if (ContractConstant.CTR_AUDIT.equals(stepName)) { // 合同审核 
	            if(LoanFlowRoute.PAYCONFIRM.equals(response) || LoanFlowRoute.KING_OPEN.equals(response)){
	            	 int count= contractDao.getHiscontract(contract.getLoanCode());
	            	 if(count>0){
	            		 backFlag = ContractConstant.BACK_FLAG;
	            	 }else{
	            		 backFlag = ContractConstant.CANCEL_BACK_FLAG;
	            	 }
	            }
            }
            if (StringUtils.isNotEmpty(backReason)) {
                if (backReason.trim().startsWith(",")
                        || backReason.trim().endsWith(",")) {
                    backReason = backReason.replace(",", "");

                }
            }
            contractBusiView.setKingStatus(kingStatus);
            contractBusiView.setDictLoanStatus(statusName);
            contractBusiView.setDictLoanStatusCode(status);
            contractBusiView.setBackFlag(backFlag);
            contractBusiView.setGrantSureBackReason(backReason);
            User user = UserUtils.getUser();
            contract.setBackFlag(backFlag);
            contractBusiView.getContract().setBackFlag(backFlag);
            // 审核意见
            if(operResultCode!=null && !("".equals(operResultCode))){
            	contractOperateInfo.setRemarks(contractBusiView.getRemarks());
                contractOperateInfo.setDictOperateResult(operResultCode);
            }
            //手动验证的结果
            if(verification!=null && !("".equals(verification))){
            	contractOperateInfo.setVerification(verification);
                contractOperateInfo.setReturnReason(returnReason);
            }
            contractOperateInfo.setContractCode(contract.getContractCode());
            contractOperateInfo.setLoanCode(contract.getLoanCode());
            // 下一个节点
            contractOperateInfo.setDictContractNextNode(statusName);
            contractOperateInfo.setOperator(user.getUserCode());
            contractOperateInfo.setOperateTime(new Date());
            contractOperateInfo.setOperateOrgCode(user.getOrgIds());
            contractOperateInfo.setIsNewRecord(false);
            contractOperateInfo.preInsert();
            Map<String, Object> loanParam = new HashMap<String, Object>();
            loanParam.put("applyId", applyId);
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("modifyBy", user.getId());
            param.put("modifyTime", new Date());
            if("2".equals(countFlag)){
            	 param.put("refuse_flag", "1");
            }
            if("1".equals(countFlag)){
            	param.put("out_flag", "1");
            }
            LoanInfo loanInfo = loanInfoDao.selectByApplyId(loanParam);
            if (!ObjectHelper.isEmpty(loanInfo)) {

                param.put("applyId", applyId);
                param.put("dictLoanStatus", status);
                loanInfoDao.updateLoanStatus(param);
            } else {
                List<ReconsiderApply> applys = reconsiderApplyDao
                        .findReconsiderApply(loanParam);
                if (!ObjectHelper.isEmpty(applys)) {
                    ReconsiderApply apply = applys.get(0);
                    if (!ObjectHelper.isEmpty(apply)) {
                        param.put("loanCode", apply.getLoanCode());
                        param.put("dictLoanStatus", status);
                        loanInfoDao.updateLoanStatus(param);
                    }
                }
            }
            //更新合同退回状态 为退回
            if(contract.getBackFlag() !=null && !"".equals(contract.getBackFlag()) ){
            	contractService.updateContractForBack(contract);
            }
            //插入合同操作记录表
            contractOperateInfoDao.insertSelective(contractOperateInfo);
            LoanStatusHis record = new LoanStatusHis();
            // APPLY_ID
            record.setApplyId(applyId);
            // 借款Code
            if (!ObjectHelper.isEmpty(contractBusiView.getContract())) {
                if (StringUtils.isNotEmpty(contractBusiView.getContract()
                        .getLoanCode()))
                    record.setLoanCode(contractBusiView.getContract()
                            .getLoanCode());
            }
            // 状态
            record.setDictLoanStatus(status);
            // 操作步骤(回退,放弃,拒绝 等)
            record.setOperateStep(stepName);
            if(verificationName!=null && !("".equals(verificationName))){
            	record.setOperateResult(verificationName);
            }
            // 操作结果
            if(operResultName!=null && !("".equals(operResultName))){
            	record.setOperateResult(operResultName);
            }

            /*if (StringUtils.isNotEmpty(remark)) {
                if (remark.trim().startsWith(",")
                        || remark.trim().endsWith(",")) {
                    remark = remark.replace(",", "");

                }
            }*/
            // 备注
            record.setRemark(backReason);
            // 系统标识
            record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
            // 设置Crud属性值
            record.preInsert();
            // 操作时间
            record.setOperateTime(record.getCreateTime());
            // 操作人记录当前登陆系统用户名称
            record.setOperator(user.getName());
            if (!ObjectHelper.isEmpty(user.getRole())) {
                // 操作人角色
                record.setOperateRoleId(user.getRole().getId());
            }
            if (!ObjectHelper.isEmpty(user.getDepartment())) {
                // 机构编码
                record.setOrgCode(user.getDepartment().getId());
            }
            loanStatusHisDao.insertSelective(record);
            workItem.setBv(contractBusiView);
        }
    }
    /**
     * 保存工作流字段到数据库
     * @param workItem
     * @author FuLiXin
     * @date 2017年2月21日 下午3:31:45
     */
    private void addFileNet(WorkItemView workItem) {
    	loanInfoDao.deleteFileNet(workItem.getBv().getApplyId());
    	loanInfoDao.addFileNet(workItem);
	}

	private Calendar getTimeOutPoint(Date date) {
        SystemSetting settingParam = new SystemSetting();
        settingParam.setSysFlag(SystemSetFlag.SYS_SIGN_TIME_OUT_FLAG);
        SystemSetting setting = systemSetMaterDao.get(settingParam);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(setting.getSysValue()));
        return cal;
    }
}
