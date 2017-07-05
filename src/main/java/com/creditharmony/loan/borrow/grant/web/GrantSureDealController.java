package com.creditharmony.loan.borrow.grant.web;

import java.net.URLDecoder;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.service.GrantCAService;
import com.creditharmony.loan.borrow.grant.service.GrantSureService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.GrantCallUtil;
import com.creditharmony.loan.common.consts.ThreePartFileName;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 放款确认处理页面处理
 * @Class Name GrantSureDealController
 * @author 朱静越
 * @Create In 2015年11月27日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/grant/grantSureDeal")
public class GrantSureDealController extends BaseController {
	
	@Autowired
	private ContractService contractService;
	@Autowired
    private LoanGrantService loanGrantService;
	@Autowired
	private GrantSureService grantSureService;
	@Autowired
    private GrantCAService grantCAService;
	@Autowired
	private ThreePartFileName threePartFileName;
    
	/**
	 * 手动待款项确认处理，单笔处理，
	 * 2017年2月9日
	 * By 朱静越
	 * @param model
	 * @param contractCode 合同编号
	 * @param urgentFlag 加急标识
	 * @param listFlag 区别标识，TG和财富
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value="updGrantSureStatus")
	public String updGrantSureStatus(Model model,String contractCode,String urgentFlag,String listFlag){
    	String message = null;
    	try {
    		logger.debug("方法：updGrantSureStatus,单笔待款项确认处理开始");
            String curLetter = threePartFileName.getLoanCur();
            String curUrgentFlag = threePartFileName.getUrgeCur();
            Contract contract = contractService.findByContractCode(contractCode);
            if (YESNO.YES.getCode().equals(urgentFlag)) {
				urgentFlag = "加急";
			}
            message = grantSureService.saveImportSend(contract, listFlag, curLetter, 
					curUrgentFlag,new Date(),urgentFlag);
            logger.debug("方法：updGrantSureStatus,单笔待款项确认处理结束");
            if (StringUtils.isEmpty(message)) {
            	message = BooleanType.TRUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法updGrantSureStatus发生异常，款项确认失败",e);
			message = "放款确认失败："+e.getMessage();
		}
		return message;
	}
    
	/**
	 * 退回处理，单子退回到合同审核列表，更新借款状态
	 * 2017年2月9日
	 * By 朱静越
	 * @param workItem 参数，包括借款编号，合同编号，applyId
	 * @return
	 */
    @ResponseBody
	@RequestMapping(value="grantSureBack")
	public String  grantSureBack(LoanFlowWorkItemView workItem){
    	String resuString = BooleanType.TRUE;
    	try {
    		workItem.setBackReason(URLDecoder.decode(workItem.getBackReason(), "UTF-8"));
    		grantSureService.updGrantSureBack(workItem);
    		try {
    			//给呼叫中心推送数据 (不需要推送：1.回访成功，2回访失败and回访次数大于等于3)
    			GrantCallUtil.grantToCallUpdateRevisitStatus(workItem.getContractCode(),GrantCommon.GRANTCALL_CUSTOMER_STATUS_MODIFY);
			} catch (Exception e) {
				logger.error("方法：grantSureBack 异常，退回，给呼叫中心推送数据失败",e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法：grantSureBack发生异常，款项确认退回失败",e);
			resuString = "退回失败"+e.getMessage();
		}
		return resuString;
	}
    
    /**
     * 单笔处理，添加P2P标识，将财富标识更新为P2P标识
     * 2017年2月9日
     * By 朱静越
     * @param applyId applyId
     * @param contractCode 合同编号
     * @param loanCode 借款编码
     * @param loanMarking 标识
     * @return
     */
    @ResponseBody
   	@RequestMapping(value="grantUpdFlag")
    public String grantUpdFlag(String applyId,String contractCode,String loanCode,String loanMarking){
    	String message = BooleanType.TRUE;
    	String[] applyParam = {applyId,contractCode,loanCode};
		try {
			logger.debug("方法grantUpdFlag，单笔添加P2P标识，处理开始");
			grantSureService.updateFlagParam(applyParam, loanMarking);
			logger.debug("方法grantUpdFlag，单笔添加P2P标识，处理结束");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("方法：grantUpdFlag发生异常，标识更改失败", e);
			message = "标识修改失败：" + e.getMessage();
		}
    	return message;
    }
    
    /**
     * 驳回申请，将冻结的单子解冻，使单子能够进行正常的处理，能够往下流转
     * 2017年2月9日
     * By 朱静越
     * @param workItem 参数
     * @param rejectReason 驳回申请原因
     * @return
     */
    @ResponseBody
   	@RequestMapping(value="backApply")
    public String backApply(LoanFlowWorkItemView workItem,String rejectReason){
    	String message = BooleanType.TRUE;
		try {
			grantSureService.updBackFrozen(workItem, rejectReason);
			try {
    			//给呼叫中心推送数据 (不需要推送：1.回访成功，2回访失败and回访次数大于等于3)
    			GrantCallUtil.grantToCallUpdateRevisitStatus(workItem.getContractCode(),GrantCommon.GRANTCALL_CUSTOMER_STATUS_GRANTBEFORE);
			} catch (Exception e) {
				logger.error("方法：backApply 异常，驳回申请，给呼叫中心推送数据失败",e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("驳回申请异常",e);
			message = "驳回申请失败" + e.getMessage();
		}
		return message;
    }
	
}
