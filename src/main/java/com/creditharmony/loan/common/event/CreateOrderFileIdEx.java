/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.eventCreateOrderFileIdEx.java
 * @Create By 张灏
 * @Create In 2016年3月14日 上午11:25:48
 */
package com.creditharmony.loan.common.event;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.ExEvent;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.borrow.trusteeship.view.TrusteeshipView;
import com.creditharmony.loan.channel.goldcredit.constants.ExportFlagConstants;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;

/**
 * 获取生成排序字段
 * @Class Name CreateOrderFileIdEx
 * @author 张灏
 * @Create In 2016年3月14日
 */
@Service("ex_hj_loanflow_createOrderId")
public class CreateOrderFileIdEx extends BaseService implements ExEvent {

    @Autowired
    private ApplyLoanInfoDao loanInfoDao;
    
    @Autowired
    private ContractDao contractDao;
    
    @Override
    public void invoke(WorkItemView workItem){
        
        String applyId = workItem.getBv().getApplyId();
        String response = workItem.getResponse();
        BaseBusinessView bv = null;
        ContractBusiView contractBusiView = null;
        GrantDealView grantDealView = null;
        TrusteeshipView trusteeshipView = null;
        String stepName = workItem.getStepName();
        String operType = null;
        Map<String,String> param = new HashMap<String,String>();
        param.put("applyId", applyId);
        LoanInfo loanInfo = loanInfoDao.findLoanLinkedContract(param);
        
        Contract contractInfo = contractDao.findByApplyId(applyId);
        
        String urgentFlag = loanInfo.getLoanUrgentFlag();
        String frozenFlag = null;
        if(ObjectHelper.isEmpty(loanInfo) || 
                StringUtils.isEmpty(loanInfo.getFrozenCode()) ||
                    StringUtils.isEmpty(loanInfo.getFrozenCode().trim())){
            frozenFlag = "00";
        }else{
            frozenFlag = "01";
        }
        if(workItem.getBv() instanceof ContractBusiView){
        	contractBusiView = (ContractBusiView)workItem.getBv();
        	operType = contractBusiView.getOperType();
        }else if(workItem.getBv() instanceof GrantDealView){
        	grantDealView = (GrantDealView)workItem.getBv();
        	operType = grantDealView.getOperateType();
        }else if(workItem.getBv() instanceof TrusteeshipView){
            trusteeshipView = (TrusteeshipView) workItem.getBv(); 
            operType = trusteeshipView.getOperateType();
        }
      
        /**
         *正常提交、退回时operType为空 
         *更新渠道标识时operType为0
         *更新冻结标识时operType为1 
         *正常提交、退回时才走下面的逻辑 
         */
        if(!YESNO.NO.getCode().equals(operType) && !YESNO.YES.getCode().equals(operType)){
            // 利率审核
            if(ContractConstant.RATE_AUDIT.equals(stepName)){
                if(LoanFlowRoute.CONFIRMSIGN.equals(response)){   // 确认签署
                    Contract contract = contractDao.findByApplyId(applyId);
                    String backFlag = contract.getBackFlag();
                    if(StringUtils.isEmpty(backFlag)){
                        backFlag = "00";
                    }else{
                        backFlag = "0"+backFlag;
                    }
                    String code = contractBusiView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+backFlag;
                    OrderFiled filed = OrderFiled.parseByCode(code);
                        String orderField = filed.getOrderId(); 
                        if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                            orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                        }
                        contractBusiView.setOrderField(orderField);
                    bv = contractBusiView;
                }
            // 确认签署
            }else if(ContractConstant.CONFIRM_SIGN.equals(stepName)){
                if(LoanFlowRoute.CONTRACTMAKE.equals(response)){   // 合同制作
                    Contract contract = contractDao.findByApplyId(applyId);
                    String backFlag = contract.getBackFlag();
                    if(StringUtils.isEmpty(backFlag)){
                        backFlag = "00";
                    }else{
                        backFlag = "0"+backFlag;
                    }
                    String againflag = "00";  //复议标识
               		if("HJ0002".equals(applyId.substring(0, 6)) && "1".equals(loanInfo.getNodeFlag())){
               			 againflag = "01";
               		}
                    String code = contractBusiView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+backFlag+"-"+againflag;
                    OrderFiled filed = OrderFiled.parseByCode(code);
                    String orderField = filed.getOrderId(); 
                    if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                        orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                    }
                    contractBusiView.setOrderField(orderField);
                    bv = contractBusiView; 
                }
                // 合同制作
            }else if(ContractConstant.CTR_CREATE.equals(stepName)){
                    if(LoanFlowRoute.RATECHECK.equals(response)){  // 退回至利率审核
                        Contract contract = contractDao.findByApplyId(applyId);
                        String firstFlag = "00";
                        String backFlag = contract.getBackFlag();
                        if(StringUtils.isEmpty(backFlag)){
                            backFlag = "00";
                        }else{
                            backFlag = "0"+backFlag;
                        }
                        String againflag = "00";  //复议标识
	               		if("HJ0002".equals(applyId.substring(0, 6)) && "1".equals(loanInfo.getNodeFlag())){
	               			 againflag = "01";
	               		}
                        String code = contractBusiView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+backFlag+"-"+firstFlag+"-"+againflag;
                        if(contractInfo!=null){
	                        if(contractInfo.getContractCode().endsWith("00001")){
	                       	 firstFlag = "01";
	                       	 code = contractBusiView.getDictLoanStatusCode()+"-"+firstFlag;
	                        }
                        }
                        OrderFiled filed = OrderFiled.parseByCode(code);
                        String orderField = filed.getOrderId(); 
                        if(!ObjectHelper.isEmpty(loanInfo.getAuditTime())){
                            orderField +="-"+DateUtils.formatDate(loanInfo.getAuditTime(), "yyyy.MM.dd HH:mm:ss");
                        }
                        contractBusiView.setOrderField(orderField);
                        bv = contractBusiView; 
                    }else if(LoanFlowRoute.CONTRACTSIGN.equals(response)){  // 提交至合同签订
                        Contract contract = contractDao.findByApplyId(applyId);
                        String backFlag = contract.getBackFlag();
                        if(StringUtils.isEmpty(backFlag)){
                            backFlag = "00";
                        }else{
                            backFlag = "0"+backFlag;
                        }
                        String code = contractBusiView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+backFlag;
                        OrderFiled filed = OrderFiled.parseByCode(code);
                        String orderField = filed.getOrderId(); 
                        if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                            orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                        }
                        contractBusiView.setOrderField(orderField);
                        bv = contractBusiView;
                    }else if(LoanFlowRoute.CONFIRMSIGN.equals(response)){
                        Contract contract = contractDao.findByApplyId(applyId);
                        String backFlag = contract.getBackFlag();
                        if(StringUtils.isEmpty(backFlag)){
                            backFlag = "00";
                        }else{
                            backFlag = "0"+backFlag;
                        }
                        String code = contractBusiView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+backFlag;
                        OrderFiled filed = OrderFiled.parseByCode(code);
                            String orderField = filed.getOrderId(); 
                            if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                                orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                            }
                            contractBusiView.setOrderField(orderField);
                        bv = contractBusiView;
                    }
                    // 合同签订
                }else if(ContractConstant.CUST_SERVICE_SIGN.equals(stepName)){
                    if(LoanFlowRoute.CONTRACTCHECK.equals(response)){
                        Contract contract = contractDao.findByApplyId(applyId);
                        String backFlag = contract.getBackFlag();
                        if(StringUtils.isEmpty(backFlag)){
                            backFlag = "00";
                        }else{
                            backFlag = "0"+backFlag;
                        }
                        String code = contractBusiView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-"+backFlag;
                        OrderFiled filed = OrderFiled.parseByCode(code);
                        String orderField = filed.getOrderId(); 
                        if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                            orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                        }
                        // 更新数据库
                        Map<String,Object> loanParam = new HashMap<String,Object>();
                        loanParam.put("loanCode", contractInfo.getLoanCode());
                        loanParam.put("orderField", orderField);
                        loanInfoDao.updOrderField(loanParam);
                        contractBusiView.setOrderField(orderField);
                        bv = contractBusiView;
                    }else if(LoanFlowRoute.CONFIRMSIGN.equals(response)){
                        Contract contract = contractDao.findByApplyId(applyId);
                        String backFlag = contract.getBackFlag();
                        if(StringUtils.isEmpty(backFlag)){
                            backFlag = "00";
                        }else{
                            backFlag = "0"+backFlag;
                        }
                        String code = contractBusiView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+backFlag;
                        OrderFiled filed = OrderFiled.parseByCode(code);
                            String orderField = filed.getOrderId(); 
                            if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                                orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                            }
                            contractBusiView.setOrderField(orderField);
                        bv = contractBusiView;
                    }
                    // 合同审核
                }else if(ContractConstant.CTR_AUDIT.equals(stepName)){
                    if(LoanFlowRoute.PAYCONFIRM.equals(response)/* || LoanFlowRoute.KING_OPEN.equals(response)*/){
                      String backFlag = "00";
                  	  Contract contract = contractDao.findByApplyId(applyId);
                        backFlag = contract.getBackFlag();
                        //判断是不是退回的
                        if(StringUtils.isEmpty(backFlag)){
                      	  backFlag = "00";
                        }else{
                            backFlag = "0"+backFlag;
                        }
                        String channelFlag=contract.getChannelFlag();
                        String code = contractBusiView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-"+backFlag;
                        if(ChannelFlag.ZCJ.getCode().equals(channelFlag)){
                        	code=code+"-05";
                        }
                        
                        OrderFiled filed = OrderFiled.parseByCode(code);
                        String orderField = filed.getOrderId(); 
                        Date modifyTime = new Date();
		                if(ObjectHelper.isEmpty(contractInfo.getModifyTime())){
		                	orderField +="-"+DateUtils.formatDate(contractInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
		                } else {
		                	orderField +="-"+DateUtils.formatDate(modifyTime, "yyyy.MM.dd HH:mm:ss");
		                }
                        contractBusiView.setOrderField(orderField);
                        // 更新数据库
                        Map<String,Object> loanParam = new HashMap<String,Object>();
                        loanParam.put("loanCode", contract.getLoanCode());
                        loanParam.put("orderField", orderField);
                        loanInfoDao.updOrderField(loanParam);
                        bv = contractBusiView;
                    }
                } else if(ContractConstant.DIS_CARD.equals(stepName)){
                	if(LoanFlowRoute.DELIVERYCARD.equals(response)) {
	                	String code = grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-00";
	                    OrderFiled filed = OrderFiled.parseByCode(code);
	                    String orderField = filed.getOrderId(); 
	                    if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
	                        orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
	                    }
	                    grantDealView.setOrderField(orderField);
	                    bv = grantDealView;
                	} else if(LoanFlowRoute.GOLDCREDIT_RETURN.equals(response) || LoanFlowRoute.GOLDCREDIT_TO_CONTRACT_CHECK.equals(response)) {
                        String code = grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-01";
                        OrderFiled filed = OrderFiled.parseByCode(code);
                        String orderField = filed.getOrderId(); 
                        if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                            orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                        }
                        grantDealView.setOrderField(orderField);
                        bv = grantDealView;
                    }
                }else if(ContractConstant.GRANT_SURE.equals(stepName)){ // 放款确认，
                	if(LoanFlowRoute.DELIVERYCARD.equals(response)) {
	                	Contract contract = contractDao.findByApplyId(applyId);
	                    String backFlag = contract.getBackFlag();
	                    if(StringUtils.isEmpty(backFlag)){
	                        backFlag = "00";
	                    }else{
	                        backFlag = "0"+backFlag;
	                    }
	                    String code = grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-"+backFlag;
	                    OrderFiled filed = OrderFiled.parseByCode(code);
	                    String orderField = filed.getOrderId(); 
	                    if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
	                        orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
	                    }
	                    grantDealView.setOrderField(orderField);
	                    bv = grantDealView;
                	}else if(LoanFlowRoute.CONTRACTCHECK.equals(response)){
                	    String code = grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-01";
                        OrderFiled filed = OrderFiled.parseByCode(code);
                        String orderField = filed.getOrderId(); 
                        if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                            orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                        }
                        grantDealView.setOrderField(orderField);
                        bv = grantDealView;
                	}
                }else if (LoanFlowRoute.GOLDCREDIT_TO_CONFIRM.equals(response) && !ContractConstant.KIND_ACCOUNT_OPEN.equals(stepName)) { 
                	 //TODO 金信债权列表将数据返回到贷款项确认列表或债权列表取消金信标识,因为在待款项确认需要进行导出，所以需要更新数据库。
                	if (!ObjectHelper.isEmpty(contractInfo)) {
                		String backFlag = contractInfo.getBackFlag();
                		String code = "";
                		 if(StringUtils.isEmpty(backFlag)){
                             backFlag = "00";
                         }else{
                             backFlag = "0"+backFlag;
                         }
                		 if (ChannelFlag.ZCJ.getName().equals(grantDealView.getLoanFlag())) {
                			 code = grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-00"+"-05";
						}else {
							 code = grantDealView.getDictLoanStatusCode()+"-1"+urgentFlag+"-"+frozenFlag+"-00";
						}
                		 OrderFiled filed = OrderFiled.parseByCode(code);
                         String orderField = filed.getOrderId(); 
                		 if (!ObjectHelper.isEmpty(contractInfo.getModifyTime())) {
                			 orderField +="-"+DateUtils.formatDate(contractInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                		 }
                		 grantDealView.setOrderField(orderField);
                		 // 更新数据库
                         Map<String,Object> loanParam = new HashMap<String,Object>();
                         loanParam.put("loanCode", contractInfo.getLoanCode());
                         loanParam.put("orderField", orderField);
                         loanInfoDao.updOrderField(loanParam);
                		 bv = grantDealView;
                	}
               }else if(ContractConstant.GOLDCREDIT_RETURN.equals(stepName)) { //TODO 金信债权退回，大金融债权退回到合同审核，这时候需要给合同审核进行排序
                    String code = grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-01";
                    OrderFiled filed = OrderFiled.parseByCode(code);
                    String orderField = filed.getOrderId(); 
                    if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                        orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                    }
                    grantDealView.setOrderField(orderField);
                    bv = grantDealView;
               }else if(GrantCommon.GRANT_NAME.equals(stepName)){ // 放款
                   String backFlag = "";
                   if(LoanFlowRoute.GOLDCREDIT_TO_CONTRACT_FROM_PAY.equals(response) ||
                		   LoanFlowRoute.GOLDCREDIT_TO_CONTRACT_CHECK.equals(response)){ // 金信放款退回到合同审核
                       backFlag = "01";
                       String code = "";
                       //判断是不是取消的金信标识
                       code =  grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-"+backFlag;
                       OrderFiled filed = OrderFiled.parseByCode(code);
                       String orderField = filed.getOrderId(); 
                       if(!ObjectHelper.isEmpty(contractInfo.getModifyTime())){
                       orderField +="-"+DateUtils.formatDate(contractInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                       }
                       grantDealView.setOrderField(orderField);
                       bv = grantDealView;
                   }else if(LoanFlowRoute.TO_ZCJ_REJECT.equals(response)){ //TODO 放款发出到大金融债券退回列表，这个时候进行债券列表的排序
                	   backFlag = "00";
                       String code = "";
                       //判断是不是取消的金信标识
                       code =  grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-"+backFlag;
                       OrderFiled filed = OrderFiled.parseByCode(code);
                       String orderField = filed.getOrderId(); 
                       if(!ObjectHelper.isEmpty(contractInfo.getModifyTime())){
                       orderField +="-"+DateUtils.formatDate(contractInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                       }
                       grantDealView.setOrderField(orderField);
                       // 更新数据库
                       Map<String,Object> loanParam = new HashMap<String,Object>();
                       Contract contract = contractDao.findByApplyId(applyId);
                       loanParam.put("loanCode", contract.getLoanCode());
                       loanParam.put("orderField", orderField);
                       loanInfoDao.updOrderField(loanParam);
                       bv = grantDealView;
                   }
                }else if(ContractConstant.KIND_ACCOUNT_OPEN.equals(stepName)){
                   if(LoanFlowRoute.PAYCONFIRM.equals(response)){
                       Contract contract = contractDao.findByApplyId(applyId);
                       String backFlag = contract.getBackFlag();
                       if(StringUtils.isEmpty(backFlag)){
                           backFlag = "00";
                       }else{
                           backFlag = "0"+backFlag;
                       }
                       String code = trusteeshipView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-"+backFlag;
                       OrderFiled filed = OrderFiled.parseByCode(code);
                       String orderField = filed.getOrderId(); 
                       if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                           orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                       }
                       trusteeshipView.setOrderField(orderField);
                       // 更新数据库
                       Map<String,Object> loanParam = new HashMap<String,Object>();
                       loanParam.put("loanCode", contract.getLoanCode());
                       loanParam.put("orderField", orderField);
                       loanInfoDao.updOrderField(loanParam);
                       bv = grantDealView;
                   } 
                } 
            
        }else if(YESNO.YES.getCode().equals(operType)){  // 更新冻结标识，更改排序字段，savaData的时候调用的else。
            if(ContractConstant.CTR_AUDIT.equals(stepName)){
                Contract contract = contractDao.findByApplyId(applyId);
                String backFlag = contract.getBackFlag();
                if(StringUtils.isEmpty(backFlag)){
                    backFlag = "00";
                }else{
                    backFlag = "0"+backFlag;
                }
                String code = contractBusiView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-"+backFlag;
                OrderFiled filed = OrderFiled.parseByCode(code);
                String orderField = filed.getOrderId(); 
                if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                    orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                }
                contractBusiView.setOrderField(orderField);
                // 更新数据库
                Map<String,Object> loanParam = new HashMap<String,Object>();
                loanParam.put("loanCode", contractInfo.getLoanCode());
                loanParam.put("orderField", orderField);
                loanInfoDao.updOrderField(loanParam);
                bv = contractBusiView;
            }else if(GrantCommon.GRANT_SURE_NAME.equals(stepName)){
            	 Contract contract = contractDao.findByApplyId(applyId);
            	 String backFlag = contract.getBackFlag();
                 if(StringUtils.isEmpty(backFlag)){
                     backFlag = "00";
                 }else{
                     backFlag = "0"+backFlag;
                 }
                 String code = "";
                 //判断是不是取消的金信标识
                 if (StringUtils.isNotBlank(grantDealView.getFlagStatus()) && ExportFlagConstants.GOLD_CREDIT_CANCEL.equals(grantDealView.getFlagStatus())) {
                	 code =  grantDealView.getDictLoanStatusCode()+"-1"+urgentFlag + "-"+ frozenFlag + "-00";
                 } else {
                	 code =  grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-"+backFlag;
                 }
                 String channelFlag=contractInfo.getChannelFlag();
                 
                 if (ChannelFlag.ZCJ.getCode().equals(channelFlag)) {
                	 code = code + "-05";
                 }
                 OrderFiled filed = OrderFiled.parseByCode(code);
                 String orderField = filed.getOrderId(); 
                 if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                     orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                 }
                 grantDealView.setOrderField(orderField);
                 // 更新数据库
                 Map<String,Object> loanParam = new HashMap<String,Object>();
                 loanParam.put("loanCode", contractInfo.getLoanCode());
                 loanParam.put("orderField", orderField);
                 loanInfoDao.updOrderField(loanParam);
                 bv = grantDealView;
            }else if(ContractConstant.DIS_CARD.equals(stepName)){
            	grantDealView.setDictLoanStatusCode(LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
                String code = grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-00";
                OrderFiled filed = OrderFiled.parseByCode(code);
                String orderField = filed.getOrderId(); 
                if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                    orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                }
                grantDealView.setOrderField(orderField);
                // 更新数据库
                Map<String,Object> loanParam = new HashMap<String,Object>();
                loanParam.put("loanCode", contractInfo.getLoanCode());
                loanParam.put("orderField", orderField);
                loanInfoDao.updOrderField(loanParam);
                bv = grantDealView;
            }else if(ContractConstant.GOLDCREDIT_RETURN.equals(stepName)) { //TODO 为驳回申请的时候进行的排序
            	 String code = grantDealView.getDictLoanStatusCode()+"-0"+urgentFlag+"-"+frozenFlag+"-00";
                 OrderFiled filed = OrderFiled.parseByCode(code);
                 String orderField = filed.getOrderId(); 
                 if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                     orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
                 }
                 grantDealView.setOrderField(orderField);
                 // 更新数据库
                 Map<String,Object> loanParam = new HashMap<String,Object>();
                 loanParam.put("loanCode", contractInfo.getLoanCode());
                 loanParam.put("orderField", orderField);
                 loanInfoDao.updOrderField(loanParam);
                 bv = grantDealView;
            }
        }
        if(!ObjectHelper.isEmpty(bv)){
            workItem.setBv(bv); 
        }
    }

}
