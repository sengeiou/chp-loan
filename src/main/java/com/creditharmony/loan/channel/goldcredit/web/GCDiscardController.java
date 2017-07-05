package com.creditharmony.loan.channel.goldcredit.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.ajax.dto.AjaxNotify;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.ApplyLoanInfoService;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCoborrowerService;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.DisCardEx;
import com.creditharmony.loan.borrow.grant.entity.ex.DistachParamEx;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.ParamEx;
import com.creditharmony.loan.borrow.grant.service.GrantCAService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.util.AjaxNotifyHelper;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.channel.goldcredit.constants.ExportFlagConstants;
import com.creditharmony.loan.common.constants.MiddlePersonWay;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.TokenUtils;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 金信待分配卡号列表
 * @Class Name GoldCreditController
 * @author 路志友
 * @Create In 2016年2月23日
 */
@Controller
@RequestMapping(value = "${adminPath}/channel/goldcredit/discard/")
public class GCDiscardController extends BaseController {
	@Autowired
	private LoanGrantService loanGrantService;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private ApplyLoanInfoService loanInfoService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private GrantCAService grantCAService;
	@Autowired
	private LoanCoborrowerService loanCoborrowerService;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;
	@Autowired
	private AssistService assistService;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	
	/**
	 * 查询金信待分配卡号信息
	 * @author songfeng
	 * @Create 2017年2月19日
	 * @param model
	 * @param grtQryParam
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getGCDiscardInfo")
	protected String getGCDiscardInfo(Model model, LoanFlowQueryParam grtQryParam,HttpServletRequest request, HttpServletResponse response) throws Exception{
		//将门店id置空
		GrantUtil.setStoreOrgIdQuery(grtQryParam);
		//废除工作流  从数据库中查询数据
		Page<DisCardEx> gcDiscardPage =loanGrantService.getGCDiscardList(new Page<DisCardEx>(request, response), grtQryParam);
		
		List<DisCardEx> workItems = gcDiscardPage.getList();
		//查询城市信息
		List<CityInfo> provinceList = cityManager.findProvince();
		if (StringUtils.isNotEmpty(grtQryParam.getProvinceCode())) {
			List<CityInfo> cityList = cityManager.findCity(grtQryParam.getProvinceCode());
			model.addAttribute("citiesList", cityList);
		}
		//查询产品信息
		List<LoanGrantEx> productList = loanGrantService.findProduct();
		//获取提交批次
		Map<String,String> batch = Maps.newHashMap();
		batch.put("loanFlag", ChannelFlag.JINXIN.getCode());
		batch.put("batch", "grantPch");
		batch.put("dictLoanStatus", LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
		List<String> submitBatchList = loanGrantService.findSubmitBatchList(batch);
		model.addAttribute("LoanFlowQueryParam", grtQryParam);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("productList", productList);
		model.addAttribute("workItems", workItems);
		model.addAttribute("submitBatchList", submitBatchList);
		model.addAttribute("gcDiscardPage", gcDiscardPage);
		return "channel/goldcredit/goldCredit_disCardList";
	}
	
	/**
	 * 金信拒绝：到信借的待款项确认，修改借款状态为：金信拒绝，修改标识为财富
	 * 2016年3月5日
	 * xiaoniu.hu
	 * @param businessView
	 * @param checkVal
	 * @return
	 */
	@RequestMapping(value = "refuseBackTo")
	@ResponseBody
	public String refuseBackTo(GrantDealView businessView, String checkVal) {
		String[] checked = null;
		String res =BooleanType.TRUE;
		if (StringUtils.isNotEmpty(checkVal)) {
			checked=checkVal.split(";");
			for(int i = 0 ; i < checked.length ; i++){
				try {
					//废除工作流 操作数据库 金信拒绝
					String []condition = null;
					if (StringUtils.isNotEmpty(checked[i])){
						condition = checked[i].split(",");
						if (condition.length == GrantCommon.SIX) {
							//修改 借款表 借款状态  借款渠道
							LoanInfo loanInfo=new LoanInfo();
							loanInfo.setLoanCode(condition[0]);
							loanInfo.setApplyId(condition[5]);
							loanInfo.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_REJECT.getCode());
							loanInfo.setLoanFlag(ChannelFlag.CHP.getCode());
							//依据借款编号查到合同  修改渠道标识  退回标识
							Contract contract = contractService.findByLoanCode(condition[0]);
							contract.setChannelFlag(ChannelFlag.CHP.getCode());
							contract.setBackFlag(YESNO.YES.getCode());
							//修改放款表中的金信处理状态
							LoanGrant loanGrant=new LoanGrant();
							loanGrant.setContractCode(contract.getContractCode());
							// 退回的场合，重置金信状态
							loanGrant.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_INIT);
							loanGrantService.cancelJXFlag(loanInfo, contract, loanGrant, ExportFlagConstants.GOLD_CREDIT_CANCEL, LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getName());
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("方法refuseBackTo，金信待分配卡号发起金信拒绝发生异常："+e.toString());
					res=BooleanType.FALSE;
				}
			}
		}
		return res;
	}
	
	/**
	 * 金信退回
	 * 2016年3月5日
	 * xiaoniu.hu
	 * @param businessView
	 * @param checkVal
	 * @return
	 */
	@RequestMapping(value = "returnBackTo")
	@ResponseBody
	public String returnBackTo(GrantDealView businessView, String checkVal,String reason) {
		String[] checked = null;
		String res =BooleanType.TRUE;
		String grantBackReason="";
		//解决中文乱码问题
		if(businessView!=null&&businessView.getGrantSureBackReason()!=null){
			try{
				grantBackReason = new String(businessView.getGrantSureBackReason().getBytes("ISO-8859-1"),"UTF-8");
				grantBackReason = java.net.URLDecoder.decode(grantBackReason , "UTF-8");
				businessView.setGrantSureBackReason(grantBackReason);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(checkVal)) {
			checked=checkVal.split(";");
			for(int i = 0; i < checked.length ; i ++){
				try {
					//废除工作流  操作数据库 金信退回
					String []condition = null;
					if (StringUtils.isNotEmpty(checked[i])){
						condition = checked[i].split(",");
						if (condition.length == GrantCommon.SIX) {
							//修改借款表  借款状态
							LoanInfo loanInfo=new LoanInfo();
							loanInfo.setLoanCode(condition[0]);
							loanInfo.setApplyId(condition[5]);
							loanInfo.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
							//分配卡号到金信退回  修改orderField 用于排序
							LoanInfo loanInfoOrder=new LoanInfo();
							loanInfoOrder.setApplyId(condition[5]);
							loanInfoOrder.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
							String orderField=createOrderFileldService.backCheckContractByDiscard(loanInfoOrder);
							loanInfo.setOrderField(orderField);
							
							//依据借款编号查到合同  修改渠道标识  退回标识
							Contract contract = contractService.findByLoanCode(condition[0]);
							contract.setBackFlag(YESNO.YES.getCode());
							//修改放款表金信处理状态
							LoanGrant loanGrant=new LoanGrant();
							loanGrant.setContractCode(contract.getContractCode());
							//退回的场合，重置金信状态
							loanGrant.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_INIT);
							//金信退回  操作数据库相关表
							loanGrantService.cancelJXFlag(loanInfo, contract, loanGrant, businessView.getGrantSureBackReason(), LoanApplyStatus.GOLDCREDIT_RETURN.getName());
							//退回到合同审核  添加分单功能
							assistService.updateAssistAddAuditOperator(condition[0]);
						}
					}
					
				} catch (Exception e) {
					logger.error(e.toString());
					res=BooleanType.FALSE;
				}
			}
		}
		return res;
	}
	
	/**
	 * 驳回申请
	 * 
	 * @param apply
	 *            传送过来的流程属性
	 * @param responseUrl
	 *            要进行退回的节点的Code
	 * @param grantSureBackReason
	 *            退回原因
	 * @param contractCode
	 *            合同编号,根据合同编号进行处理
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "disCardRejectBack")
	public String grantSureRejectBack(String params) {
		String[] paramArrays = {};
		if (StringUtils.isNotEmpty(params)){
			paramArrays = params.split(";");
		}
		if (paramArrays.length != 0) {
			for (String paramArray : paramArrays) {
				String []condition = null;
				if (StringUtils.isNotEmpty(paramArray)){
					condition = paramArray.split(",");
					if (condition.length == GrantCommon.SIX) {
						//废除工作流 操作数据库   改冻结状态
						LoanInfo loanInfo = new LoanInfo();
						loanInfo.setLoanCode(condition[0]);
						loanInfo.setApplyId(condition[5]);
						loanInfo.setFrozenFlag("0");
						loanInfo.setFrozenCode("");
						loanInfo.setFrozenReason("");
						loanInfo.setFrozenLastApplyTime(new Date());
						//分配卡号  驳回申请   修改orderField 用于排序
						LoanInfo loanInfoOrder=new LoanInfo();
						loanInfoOrder.setApplyId(condition[5]);
						String orderField=createOrderFileldService.cancelJxByDiscard(loanInfoOrder);
						loanInfo.setOrderField(orderField);
						loanGrantService.dealBackFrozen(loanInfo);
					}
				}
			}
		}
		return BooleanType.TRUE;
	}
	/**
	 * 分配卡号处理，获得多个单子的loanCode说
	 * ,分割，在同一个类中，
	 * 2016年2月25日
	 * By 路志友
	 * @param model
	 * @param checkVal
	 * @return String
	 */
	@RequestMapping(value = "disCardJump")
	public String disCardJump(Model model,String checkVal) {
		List<DisCardEx> gcDiscardList =new ArrayList<DisCardEx>();
		// 根据loanCodes查询页面中显示放款确认的字段
		String[] checkeds = null;
		
		// 添加token 
	    Map<String,String> tokenMap = TokenUtils.createToken();
	    model.addAttribute("deftokenId", tokenMap.get("tokenId"));
	    model.addAttribute("deftoken", tokenMap.get("token"));
	    TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
		try {
			if (StringUtils.isNotEmpty(checkVal)) {
				if (checkVal.indexOf(",") != GrantCommon.ONE) {
					checkeds = checkVal.split(",");
				} else {
					checkeds = new String[1];
					checkeds[0] = checkVal;
				}
				for (String item : checkeds) {
					//依据loanCode 查询数据库的数据
					List<DisCardEx> gcDiscard =new ArrayList<DisCardEx>();
					LoanFlowQueryParam grtQryParam=new LoanFlowQueryParam();
					grtQryParam.setLoanCode(item);
					gcDiscard =loanGrantService.getGCDiscardList(grtQryParam);
					//将依据loanCode查询的信息都加入到gcDiscardList  
					if(!gcDiscard.isEmpty()&&gcDiscard.size()>0){
						for(DisCardEx discard:gcDiscard){
							gcDiscardList.add(discard);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
		model.addAttribute("list", gcDiscardList);
		model.addAttribute("flag","JX_disCard");
		return "channel/goldcredit/goldcredit_disCard_approve_0";
	}
	
	/**
	 * 金信分配卡号页面
	 * 2016年5月22日
	 * By 王彬彬
	 * @return
	 */
	@RequestMapping(value = "selectMiddle")
	public String selectMiddle(Model model, HttpServletRequest request,
			HttpServletResponse response, MiddlePerson midPerson) {
		midPerson.setWayFlag(MiddlePersonWay.GC_CARD.getCode());// 金信放款方式为9
		Page<MiddlePerson> middlePage = middlePersonService.selectAllMiddle(
				new Page<MiddlePerson>(request, response), midPerson);
		if (midPerson != null) {
			model.addAttribute("midPerson", midPerson);
		}
		model.addAttribute("middlePage", middlePage);
		model.addAttribute("sureFlag", "grantAudit");
		return "borrow/grant/disCardMiddle";
	}
	
	/**
	 * 分配卡号节点结束，调用流程更新单子的借款状态，
	 * 更新放款记录表，放款途径。在提交节点，设置dealUser, 
	 * 门店申请冻结的单子，不允许提交分配卡号操作
	 * 2016年2月23日 By 张建雄
	 * 
	 * @param model
	 * @param apply
	 * @param userCode
	 *            用户编码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "disCardCommit")
	public String disCardCommit(Model model, String userCode,String middleId, ParamEx param,String deftokenId,String deftoken) {
		List<DistachParamEx> list = param.getList();
		String failedContractCode = null;
		List<String> failedCodeList = Lists.newArrayList();
		AjaxNotify notify = new AjaxNotify();
		int successNum = 0;
		int failNum = 0;
		boolean result = false;
		synchronized(this){  
    	    result = TokenUtils.validToken(deftokenId, deftoken);
    	    TokenUtils.removeToken(deftokenId);
		} 
		if(result){
			if (ArrayHelper.isNotEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					try {
						logger.info("分配卡号开始：" + list.get(i).getContractCode());
						//废除工作流  改借款状态  中间人信息  银行信息 加历史
						LoanInfo searchLoanInfo = new LoanInfo();
						searchLoanInfo.setLoanCode(list.get(i).getLoanCode());
						searchLoanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
						searchLoanInfo.setFrozenFlag(YESNO.YES.getCode());
						int frozenInt = applyLoanInfoDao.findFrozenInt(searchLoanInfo);
						if (frozenInt == 0) {
							LoanInfo loanInfo=new LoanInfo();
							loanInfo.setLoanCode(list.get(i).getLoanCode());
							loanInfo.setContractCode(list.get(i).getContractCode());
							loanInfo.setApplyId(list.get(i).getApplyId());
							loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_TO_SEND.getCode());
							//修改放款表中间人信息
							LoanGrant loanGrant=new LoanGrant();
							loanGrant.setContractCode(list.get(i).getContractCode());
							loanGrant.setMidId(middleId);
							loanGrant.setLendingUserId(userCode);
							loanGrantService.disCardInfo(loanInfo, loanGrant);
							logger.info("分配卡号结束：" + list.get(i).getLoanCode());
						}else {
							failedContractCode=list.get(i).getContractCode();
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("出现异常的借款编号："+list.get(i).getLoanCode());
						logger.error(e.toString());
						failedContractCode=list.get(i).getContractCode();
					}
					if(StringUtils.isNotEmpty(failedContractCode)){
						failedCodeList.add(failedContractCode);
						failNum++;
					}else{
						successNum++;
					}
				}
				// 表示单笔处理
				if (list.size()==1) {
					if (successNum == 1) {
						notify.setSuccess(BooleanType.TRUE);
						notify.setMessage("分配成功");
					}else{
						notify.setSuccess(BooleanType.FALSE);
						notify.setMessage("分配失败");
					}
					// 多笔处理
				}else{
					if (ArrayHelper.isNotEmpty(failedCodeList)) {
						AjaxNotifyHelper.wrapperNotifyInfo(notify, failedCodeList, successNum, failNum);
					}else{
						notify.setSuccess(BooleanType.TRUE);
						notify.setMessage("全部分配成功");
					}
				}
			}else{
				notify.setSuccess(BooleanType.FALSE);
				notify.setMessage("分配失败，没有要处理的数据");
			}
		}else{
			notify.setSuccess(BooleanType.FALSE);
			notify.setMessage("已经提交，请不要重复提交");
		}
		return jsonMapper.toJson(notify);
		
	}


}
