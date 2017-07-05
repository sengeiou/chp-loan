package com.creditharmony.loan.borrow.poscard.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.Matching;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.service.ApplyPaybackUseService;
import com.creditharmony.loan.borrow.payback.service.ConfirmPaybackService;
import com.creditharmony.loan.borrow.payback.service.DealPaybackService;
import com.creditharmony.loan.borrow.payback.service.DoStoreService;
import com.creditharmony.loan.borrow.poscard.entity.PosBacktage;
import com.creditharmony.loan.borrow.poscard.service.PosBacktageInfoService;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.entity.NumTotal;
import com.creditharmony.loan.common.service.LoanDeductService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.utils.MD5Util;
import com.google.common.collect.Maps;

/**
 * pos后台数据操作
 * @Class Name PosBacktageListController
 * @author 管洪昌
 * @Create In 2016年1月20日
 */
@Controller
@RequestMapping(value = "${adminPath}/borrow/poscard/posBacktageList")
public class PosBacktageListController extends BaseController{
	
	@Autowired
	private PosBacktageInfoService posBacktageInfoService;
	
	@Autowired
	private PaybackService applyPayService;
	
	@Autowired
	private DoStoreService doStoreService;
	
	@Autowired
	private DealPaybackService dealPaybackService;
	
	@Autowired
	private LoanDeductService loanDeductService;
	
	@Autowired
	private MiddlePersonService middlePersonService;
	
	@Autowired
	private ApplyPaybackUseService applyPaybackUseService;
	
	@Autowired
	private ConfirmPaybackService confirmPaybackService;
	
	Page<PosBacktage> paybackApplyPage;
	
	Page<PosBacktage> posBackListpage;
	
	public List<PosBacktage> posBacktageList;
	
    SimpleDateFormat date= new SimpleDateFormat("EEE MMM DD HH:mm:ss z yyyy", Locale.ENGLISH); 
    
    SimpleDateFormat dateFmat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    
    SimpleDateFormat date1= new SimpleDateFormat("yyyy-mm-dd"); 
    
	private static String POS_ERROR = "修改POS密码失败，旧密码错误";
	
	private static String POS_SUCSS = "修改POS密码成功";
	
	/**
	 * pos后台数据列表，查询的状态为：匹配状态，未匹配，已匹配，已查账
	 * 2016年1月20日
	 * By 管洪昌
	 * @param model
	 * @param posBacktage
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value="posBacktageInfo")
    public String  posBacktageInfo(Model model,PosBacktage posBacktage,
    		HttpServletRequest request,HttpServletResponse response){
	    // 初始化的匹配状态；未匹配、已匹配、已查账
		posBackListpage = posBacktageInfoService.selectPosBacktageList(new Page<PosBacktage>(request,response), posBacktage);
		
		for(PosBacktage pb:posBackListpage.getList()){
			String matchingState=DictCache.getInstance().getDictLabel("jk_matching",pb.getMatchingState());
			pb.setMatchingStateLabel(matchingState);
		}
		model.addAttribute("posBackListpage", posBackListpage);
		model.addAttribute("PosBacktage", posBacktage);
    	return "borrow/poscard/posBacktageList";
    }
	
	
	/**
	 * POS已匹配列表  查看页面
	 * 2015年12月9日
	 * By 李强
	 * @param model
	 * @param loanServiceBureau
	 * @return
	 */
	@RequestMapping(value = "seeStoresAlreadyDo")
	public String seeStoresAlreadyDo(HttpServletRequest request, HttpServletResponse response, PaybackApply paybackApply, Model model) {
		List<PaybackApply> paybackApplyList = new ArrayList<PaybackApply>();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		// 初始化存入银行下拉框
		Page<MiddlePerson> middlePersonPage = middlePersonService.selectAllMiddle(new Page<MiddlePerson>(request, response), null);
		if (ArrayHelper.isNotEmpty(middlePersonPage.getList())) {
			model.addAttribute("middlePersonList", middlePersonPage.getList());
		} else {
			model.addAttribute("middlePersonList", null);
		}
		paybackApplyList = applyPayService.findApplyPayback(paybackApply);
		if (StringUtils.equals(paybackApplyList.get(0).getDictPayUse(),
				RepayType.EARLY_SETTLE.getCode())) {
			PaybackMonth paybackMonth = new PaybackMonth();
			paybackMonth.setContractCode(paybackApplyList.get(0).getContractCode());
			paybackMonth.setMonths(1);
			//paybackApplyList.get(0).setPaybackMonth(confirmPaybackService.findDefaultConfirmInfo(paybackMonth));
		}
		if (ArrayHelper.isNotEmpty(paybackApplyList)) {
			if (paybackApplyList.get(0).getDictRepayMethod().equals(RepayChannel.NETBANK_CHARGE.getCode())) {
				PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
				paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getId());
				paybackTransferInfoList = dealPaybackService.findTransfer(paybackTransferInfo);
				if(ArrayHelper.isNotEmpty(paybackTransferInfoList)){
					for (int i = 0; i < paybackTransferInfoList.size(); i++) {
						// 上传人
						paybackTransferInfoList.get(i).setUploadName(UserUtils.getUser().getName());
					}
				}
				model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
			} else {
				PaybackTransferInfo PaybackTransferInfo = new PaybackTransferInfo();
				PaybackTransferInfo.setUploadName(UserUtils.getUser().getName());
				PaybackTransferInfo.setUploadDate(new Date());
				paybackTransferInfoList.add(PaybackTransferInfo);
				model.addAttribute("paybackTransferInfoList", paybackTransferInfoList);
			}
			logger.debug("invoke ConfirmPaybackController method: list, contarctCode is: "
					+ paybackApplyList.get(0).getContractCode());
		} else {
			logger.debug("invoke ConfirmPaybackController method: gotoConfirmInfo, paybackList is null");
		}
		
		String  dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",paybackApplyList.get(0).getLoanInfo().getDictLoanStatus());
		paybackApplyList.get(0).getLoanInfo().setDictLoanStatusLabel(dictLoanStatus);
		model.addAttribute("paybackApply", paybackApplyList.get(0));
		return "borrow/poscard/seeStoresAlreadyPos";
	}
	
	/**
	 * pos已匹配成功列表
	 * 2016年2月22日
	 * By 管洪昌
	 * @param model
	 * @param paybackApply
	 * @param request
	 * @param response
	 * @return 要进行跳转的页面
	 */
	@RequestMapping(value="posAlreadyMatchInfo")
    public String  posAlreadyMatchInfo(Model model,PosBacktage posBacktage,
    		HttpServletRequest request,HttpServletResponse response){
	    // 初始化的匹配状态；未匹配、已匹配、已查账
		posBacktage.setMatchingState( Matching.MATCHED.getCode());
		//还款状态为 POS机刷卡的 
		posBacktage.setDictRepayMethod(RepayChannel.POS.getCode());
		//
	    paybackApplyPage = posBacktageInfoService.findApplyPosCard(new Page<PosBacktage>(request, response), posBacktage);
		NumTotal numTotal = new  NumTotal();
	    for(PosBacktage pb:paybackApplyPage.getList()){
	    	String dictLoanStatus=DictCache.getInstance().getDictLabel("jk_loan_status",pb.getDictLoanStatus());
	    	pb.setDictLoanStatusLabel(dictLoanStatus);
	    	
	    	String dictPayUse=DictCache.getInstance().getDictLabel("jk_repay_type",pb.getDictPayUse());
	    	pb.setDictPayUseLabel(dictPayUse);
	    	
	    	String dictPayStatus=DictCache.getInstance().getDictLabel("jk_repay_status",pb.getDictPayStatus());
	    	pb.setDictPayStatusLabel(dictPayStatus);
	    	
	    	String dictPayResult=DictCache.getInstance().getDictLabel("jk_counteroffer_result",pb.getDictPayResult());
	    	pb.setDictPayResultLabel(dictPayResult);
	    	
	    	String loanFlag=DictCache.getInstance().getDictLabel("jk_channel_flag",pb.getLoanFlag());
	    	pb.setLoanFlagLabel(loanFlag);
	    	
	    	String customerTelesalesFlag=DictCache.getInstance().getDictLabel("jk_telemarketing",pb.getCustomerTelesalesFlag());
	    	pb.setCustomerTelesalesFlagLabel(customerTelesalesFlag);
			numTotal.setNum(pb.getSumNumber());
			numTotal.setTotal(pb.getSumAmont());
	    }
	    
	    
	    model.addAttribute("numTotal",numTotal);
		model.addAttribute("paybackApplyPage",paybackApplyPage);
		model.addAttribute("PosBacktage", posBacktage);
    	return "borrow/poscard/posAlreadyMatchInfoList";
    }
	
	/**
	 * 导出POS后台数据列表
	 * 2016年1月21日
	 * By 管洪昌
	 * @param request
	 * @param response
	 * @param checkVal
	 * @return none
	 */
	@RequestMapping(value="exportPosBackList")
	public String exportPosBackList(HttpServletRequest request,
			HttpServletResponse response,PosBacktage posBacktage,String checkVal) {
		String idVal = posBacktage.getId();
		if(idVal!= null && !"".equals(idVal)){
			posBacktage.setId(appendString(idVal));
		}
		Map<String ,Object> queryMap = Maps.newHashMap();
		posBacktage.setIds(checkVal);
		String [] ids={};
		if (StringUtils.isNotEmpty(checkVal)) {
			 ids = checkVal.split(",");
		}
		queryMap.put("params", posBacktage);
		queryMap.put("applyIds", ids);
		/*String idVal = posBacktage.getId();
		if(idVal!= null && !"".equals(idVal)){
			posBacktage.setId(appendString(idVal));
		}*/
		try {
			//ExportPosHelper.exportData(posBacktage, response);
			ExportPosHelper.exportData(queryMap, response);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + adminPath + "/borrow/poscard/posBacktageList/posBacktageInfo";
		}
	}
	
	/**
	 * 拼接字符串， 2016年1月8日 By wengsi
	 * 
	 * @param ids
	 * @return idstring 拼接好的id
	 */
	public String appendString(String ids) {
		String[] idArray = null;
		StringBuilder parameter = new StringBuilder();
		idArray = ids.split(",");
		for (int i =0;i<idArray.length;i++){
			String id  = idArray[i];
				if (i == 0){
					parameter.append("'" +id +"'");
				}else {
					parameter.append(",'" +id + "'");
				}
		}
		return parameter.toString();
	}
	
    /**
     * 加载单条POS机刷卡记录 2016年1月22日 
     * by 管洪昌
     * @param model
     * @param id
     * @param matchingState
     * @param auditDate
     * @param contractCode
     * @return 要进行跳转的页面
     */
    @RequestMapping(value = { "findPosConsult" })
    public String findPosConsult(Model model,String id,String matchingState,String auditDate,String contractCode) {
        model.addAttribute("id",id);
        model.addAttribute("matchingState",matchingState);
        if(!StringUtils.isBlank(auditDate)){
        	try {
    			model.addAttribute("auditDate",date.parse(auditDate));
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
        }
        model.addAttribute("contractCode", contractCode);
        return "borrow/poscard/poscardManagementForm";
    }

	/**
     * 修改pos单条数据 2015年12月3日 
     * By 管洪昌
     * @param posBacktage
     * @param model
     * @return 修改状态
     */
    @RequestMapping(value="updatePosConsult")
    @ResponseBody
    public String updatePosConsult(PosBacktage posBacktage, Model model){
    	posBacktageInfoService.updatePosBacktage(posBacktage);
        return BooleanType.TRUE;
    }
    
	/**
	 * 修改POS登录密码
	 * @param posOldPassword
	 * @param posNewPassword
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "posModifyPwd")
	public String posModifyPwd(String posOldPassword, String posNewPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(posOldPassword) && StringUtils.isNotBlank(posNewPassword)){
			if (posPassword(user.getId(),MD5Util.string2MD5(posOldPassword))){
				posBacktageInfoService.updatePosPasswordById(user.getId(), user.getLoginName(), MD5Util.string2MD5(posNewPassword));
				model.addAttribute("message", POS_SUCSS);
			}else{
				model.addAttribute("message", POS_ERROR);
			}
		}
		if(!StringUtils.isNotBlank(posOldPassword) && StringUtils.isNotBlank(posNewPassword)){
			if (posPassword(user.getId(),posOldPassword)){
				posBacktageInfoService.updatePosPasswordById(user.getId(), user.getLoginName(), MD5Util.string2MD5(posNewPassword));
				model.addAttribute("message", POS_SUCSS);
			}else{
				model.addAttribute("message", POS_ERROR);
			}
		}
		model.addAttribute("user", user);
		return "borrow/poscard/posModifyPwd";
	}

	/**
	 * 校验POS旧密码是否正确
	 * @param oldPassword
	 * @param id
	 * @return
	 */
	private boolean posPassword(String id, String posOldPassword) {
		//查这个人的原来POS密码
		posBacktageList=posBacktageInfoService.findPosPwd(id);
		if(!StringUtils.isNotBlank(posBacktageList.get(0).getPospwd())){
			return true;
		}else{
			if(posOldPassword.equals(posBacktageList.get(0).getPospwd())){
				return true;
			}else {
				return false;
			}
		}
	}
	
	/**
	 * POS已匹列表转详细页面
	 * 2016年1月6日
	 * By guanhongchang
	 * @param request
	 * @param response
	 * @param paybackApply
	 * @param model
	 * @return page
	 */
	@RequestMapping(value = "form")
	public String goDoStoreInfoForm(HttpServletRequest request, HttpServletResponse response, PaybackApply paybackApply, Model model) {
		List<PaybackApply> paybackApplyList = new ArrayList<PaybackApply>();
		List<PaybackTransferInfo> paybackTransferInfoList = new ArrayList<PaybackTransferInfo>();
		// 初始化存入银行下拉框
		Page<MiddlePerson> middlePersonPage = middlePersonService.selectAllMiddle(new Page<MiddlePerson>(request, response),null);
		if(ArrayHelper.isNotEmpty(middlePersonPage.getList())){
			model.addAttribute("middlePersonList", middlePersonPage.getList());
		}else{
			model.addAttribute("middlePersonList", null);
		}
		paybackApplyList = applyPayService.findApplyPayback(paybackApply);
		if(StringUtils.equals(paybackApplyList.get(0).getDictPayUse(), RepayType.EARLY_SETTLE.getCode())){
			PaybackMonth paybackMonth = new PaybackMonth();
			paybackMonth.setContractCode(paybackApplyList.get(0).getContractCode());
			paybackMonth.setMonths(1);
			//paybackApplyList.get(0).setPaybackMonth(confirmPaybackService.findDefaultConfirmInfo(paybackMonth));
		}
		if(ArrayHelper.isNotEmpty(paybackApplyList)){
			PaybackTransferInfo paybackTransferInfo = new PaybackTransferInfo();
			paybackTransferInfo.setrPaybackApplyId(paybackApplyList.get(0).getId());
			paybackTransferInfoList = dealPaybackService.findTransfer(paybackTransferInfo);
	 		if(paybackApplyList.get(0).getDictRepayMethod().equals(RepayChannel.NETBANK_CHARGE.getCode())){
				model.addAttribute("paybackTransferInfoList",paybackTransferInfoList);
			}else{
				PaybackTransferInfo  PaybackTransferInfo = new PaybackTransferInfo();
				PaybackTransferInfo.setUploadName(UserUtils.getUser().getName());
				PaybackTransferInfo.setUploadDate(new Date());
				paybackTransferInfoList.add(PaybackTransferInfo);
				model.addAttribute("paybackTransferInfoList",paybackTransferInfoList);
			}
			logger.debug("invoke ConfirmPaybackController method: list, contarctCode is: "+ paybackApplyList.get(0).getContractCode());
		}else{
			logger.debug("invoke ConfirmPaybackController method: gotoConfirmInfo, paybackList is null");
		}
		model.addAttribute("paybackApply", paybackApplyList.get(0));
		return "borrow/poscard/posInfo";
	}
	
	
	/**
	 * 查询POS后台数据列表中是否有相关的POS刷卡数据
	 * 2016年2月16日
	 * By guanhongchang 
	 * @param chaCode
	 * @param posnum
	 * @param srDate
	 * @param srDumnet
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "checkRefPosStr", method = RequestMethod.POST)
	public String checkRefPosStr(String chaCode,String posnum,Date srDate,String srDumnet) {
		PosBacktage  posBacktage  =new PosBacktage();
		//chaCode  参考号
		posBacktage.setReferCode(chaCode);
		//posnum   订单号
		posBacktage.setPosOrderNumber(posnum);
		//srDate   到账日期
		posBacktage.setPaybackDate(srDate);
		//srDumnet   金额
		posBacktage.setApplyReallyAmount(new BigDecimal(srDumnet));
		//未匹配
		posBacktage.setMatchingState(Matching.NO_MATCH.getCode());
		List<PosBacktage> paybackList=new ArrayList<PosBacktage>();
		paybackList = posBacktageInfoService.checkRefPosStr(posBacktage);
		if(paybackList!=null && !paybackList.isEmpty()){
			return JsonMapper.nonDefaultMapper().toJson(1);
		}else {
			return JsonMapper.nonDefaultMapper().toJson(-1);
		}
	}
	
	
	
}
