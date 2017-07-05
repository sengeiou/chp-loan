package com.creditharmony.loan.car.carRefund.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.service.PaybackTransferOutService;
import com.creditharmony.loan.car.carGrant.service.CarGrantDeductsService;
import com.creditharmony.loan.car.carRefund.service.CarPendingRepayMatchService;
import com.creditharmony.loan.car.common.consts.CarPendingMatchStatus;
import com.creditharmony.loan.car.common.entity.CarPendingRepayMatchInfo;
import com.creditharmony.loan.car.common.entity.CarRepayMatchDetailInfo;
import com.creditharmony.loan.car.common.entity.CarUrgeServicesMoney;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.MiddlePersonService;



/**
 * 放款审核进行处理
 * 
 * @Class Name CarRefundAuditController
 * @author 蒋力
 * @Create In 2016年2月29日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/refund/carPendingRepayMatch")
public class CarPendingRepayMatchController extends BaseController {

	@Autowired
	private CarPendingRepayMatchService carPendingRepayMatchService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private PaybackTransferOutService paybackTransferOutService;
	@Autowired
	private CarGrantDeductsService grantDeductsService;
	/**
	 * 待还款匹配列表
	 * 
	 * @param model
	 *            中存放要在页面中显示的list
	 * @param checkVal
	 *            
	 * @return 要进行跳转的页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "pendingMatchJump")
	public String pendingMatchJump(Model model,CarPendingRepayMatchInfo carPendingRepayMatchInfo,String result,
				HttpServletRequest request,HttpServletResponse response) {
		if(carPendingRepayMatchInfo.getApplyRepayAmountUp()!=null&&!"".equals(carPendingRepayMatchInfo.getApplyRepayAmountUp()))
		{
			carPendingRepayMatchInfo.setAmountUp(Double.parseDouble(carPendingRepayMatchInfo.getApplyRepayAmountUp()));
		}
		if(carPendingRepayMatchInfo.getApplyRepayAmountDown()!=null&&!"".equals(carPendingRepayMatchInfo.getApplyRepayAmountDown()))
		{
			carPendingRepayMatchInfo.setAmountDown(Double.parseDouble(carPendingRepayMatchInfo.getApplyRepayAmountDown()));
		}
		Page<CarPendingRepayMatchInfo> page = carPendingRepayMatchService.selectPendingMatchList(new Page<CarPendingRepayMatchInfo>(request, response), carPendingRepayMatchInfo);
		Page<MiddlePerson> middlePersonPage = middlePersonService.selectAllMiddle(new Page<MiddlePerson>(request, response), null);
		
		for(CarPendingRepayMatchInfo view:page.getList()){
			view.setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_car_loan_status", view.getDictLoanStatus()));
		}
		
		model.addAttribute("pendingList", page);	
		model.addAttribute("middlePersonList", middlePersonPage.getList());
		return "car/carRefund/pendingRepayMatchList";
	}
	
	/**
	 * 待还款匹配-划扣追回
	 * 
	 * @param model
	 *            中存放要在页面中显示的list
	 * @param checkVal
	 *            
	 * @return 要进行跳转的页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "pendingMatchProcess")
	public String pendingMatchProcess(Model model,CarPendingRepayMatchInfo carPendingRepayMatchInfo,String result,
				HttpServletRequest request,HttpServletResponse response) {
			
		Page<CarPendingRepayMatchInfo> page = carPendingRepayMatchService.selectPendingMatchList(new Page<CarPendingRepayMatchInfo>(request, response), carPendingRepayMatchInfo);
		List<CarPendingRepayMatchInfo> pendingList = page.getList();
		Page<MiddlePerson> middlePersonPage = middlePersonService.selectAllMiddle(new Page<MiddlePerson>(request, response), null);
		CarRepayMatchDetailInfo carRepayMatchDetailInfo = new CarRepayMatchDetailInfo();
		carRepayMatchDetailInfo.setTransferId(carPendingRepayMatchInfo.getId());
		List<CarRepayMatchDetailInfo> detailList = carPendingRepayMatchService.selectMatchDetailList(carRepayMatchDetailInfo);
		
		pendingList.get(0).setDictLoanStatus(DictCache.getInstance().getDictLabel("jk_car_loan_status", pendingList.get(0).getDictLoanStatus()));
		
		model.addAttribute("paybackMatch", pendingList.get(0));	
		model.addAttribute("middlePersonList", middlePersonPage.getList());
		model.addAttribute("pendingDetailList", detailList);
		return "car/carRefund/pendingRepayMatchDo";
	}
	
	/**
	 * 待还款匹配提交，更新匹配结果和审批意见
	 * 
	 * @param param
	 *            属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "repayMatchSubmit")
	public String repayMatchSubmit(HttpServletRequest request,
			HttpServletResponse response,CarPendingRepayMatchInfo carPendingRepayMatchInfo) {
		
		String result = carPendingRepayMatchInfo.getMatchingResult();
		CarUrgeServicesMoney urgeServicesMoney = new CarUrgeServicesMoney();
		urgeServicesMoney.setId("'"+carPendingRepayMatchInfo.getUrgeId()+"'");
		urgeServicesMoney.preUpdate();
		if(CarPendingMatchStatus.REPAY_MATCH_STATUS_SUCCESS.getCode().equals(result))
		{//匹配成功，更新车借_催收服务费信息表处理状态为划扣成功
			urgeServicesMoney.setDictDealStatus(CounterofferResult.PAYMENT_SUCCEED.getCode());
			grantDeductsService.updateUrge(urgeServicesMoney);
		}else{//匹配退回,更新车借_催收服务费信息表处理状态为划扣失败
			urgeServicesMoney.setDictDealStatus(CounterofferResult.PAYMENT_FAILED.getCode());
			grantDeductsService.updateUrge(urgeServicesMoney);
		}
		carPendingRepayMatchInfo.preUpdate();
		carPendingRepayMatchService.updateRepayMatch(carPendingRepayMatchInfo);
		
		return "redirect:"
		+ adminPath
		+ "/car/refund/carPendingRepayMatch/pendingMatchJump";
	}
	
	/**
	 * 手动匹配从还款_汇账外部导入数据获取列表
	 * 2016年3月4日
	 * By 蒋力
	 * @param request
	 * @param response
	 * @param paybackTransferOut
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "getTransferOutMatchList")
	public String getTransferOutMatchList(HttpServletRequest request, HttpServletResponse response, PaybackTransferOut paybackTransferOut) {
		List<PaybackTransferOut> transferOutList = new ArrayList<PaybackTransferOut>();
		paybackTransferOut.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		transferOutList = paybackTransferOutService.findList(paybackTransferOut);
		return JsonMapper.nonDefaultMapper().toJson(transferOutList);
	}
	
	/**
	 * 手动匹配提交
	 * 2016年3月5日
	 * By 蒋力
	 * @param request
	 * @param response
	 * @param applyId
	 * @param infoId
	 * @param outId
	 * @param contractCode
	 * @param outReallyAmount
	 * @param blueAmount
	 * @param applyReallyAmount
	 * @return msg
	 */
	@ResponseBody
	@RequestMapping(value = "handMatching")
	public String handMatching(HttpServletRequest request, HttpServletResponse response, String applyId, 
			String infoId, String outId) {
		CarRepayMatchDetailInfo info = new CarRepayMatchDetailInfo();
		PaybackTransferOut out = new PaybackTransferOut();
		info.setId(infoId);
		info.setMatchingResult(BankSerialCheck.CHECKE_OVER.getCode());
		info.preUpdate();
		out.setId(outId);
		out.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
		out.setrPaybackApplyId(applyId);
		out.setTransferAccountsId(infoId);
		out.setRelationType(TargetWay.SERVICE_FEE.getCode());//催收服务费追回匹配
		out.preUpdate();
		carPendingRepayMatchService.updateRepayDetailMatch(info);//更新明细表记录为已匹配
		paybackTransferOutService.updateOutStatuById(out);//更新对应的银行流水表为已查账
		
		return BooleanType.TRUE;
	}
	
	/**
	 * 待还款匹配列表-批量匹配
	 * 2016年3月5日
	 * By 蒋力
	 * @param request
	 * @param response
	 * @param matchingIds
	 * @return msg
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "repayBatchMatch")
	public String repayBatchMatch(HttpServletRequest request, HttpServletResponse response, String matchingIds) throws ParseException {
		String msg = null;
		StringBuilder parameter = new StringBuilder();
		String ids="";
		if (!StringUtils.isEmpty(matchingIds)) {
			String[] infoId = matchingIds.split(",");
			for (int i = 0; i < infoId.length; i++) {
				parameter.append("'" + infoId[i] + "',");
			}
			ids = parameter.toString();
			ids = ids.substring(0, parameter.lastIndexOf(","));
			CarPendingRepayMatchInfo carPendingRepayMatchInfo = new CarPendingRepayMatchInfo();
			carPendingRepayMatchInfo.setId(ids);
			//加载选中项的转账信息列表
			List<CarPendingRepayMatchInfo> infoList = carPendingRepayMatchService.getCheckedMatchList(carPendingRepayMatchInfo);
			for (int i = 0; i < infoList.size(); i++) {
				//申请还款金额
				BigDecimal applyRepayAmount = infoList.get(i).getApplyRepayAmount();
				//本条转账信息已匹配明细实际转账金额之和
				BigDecimal alreadyAmount = new BigDecimal(0);
				//通过选中转账记录信息的ID查询明细信息
				CarRepayMatchDetailInfo carRepayMatchDetailInfo = new CarRepayMatchDetailInfo();
				carRepayMatchDetailInfo.setTransferId(infoId[i]);
				List<CarRepayMatchDetailInfo> detailList = carPendingRepayMatchService.selectMatchDetailList(carRepayMatchDetailInfo);
				if(detailList!=null&&detailList.size()>0)
				{
					for(int j = 0; j < detailList.size(); j++){
						//通过(存款时间/实际到账金额/存入账户)从还款_汇账外部导入数据找寻匹配项
						PaybackTransferOut paybackTransferOut= new PaybackTransferOut();
						//存款时间格式化，只取年月日	START
						Date depostieTime = detailList.get(j).getTranDepositTime();
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
						String dateStr=sdf.format(depostieTime);
						Date depostieTimeDate =  sdf.parse(dateStr);
						//存款时间格式化，只取年月日	END
						paybackTransferOut.setOutDepositTime(depostieTimeDate);
						paybackTransferOut.setOutReallyAmount(detailList.get(j).getReallyAmount());
						paybackTransferOut.setOutEnterBankAccount(infoList.get(i).getStoresInAccount());
						List<PaybackTransferOut> transferOutList = new ArrayList<PaybackTransferOut>();
						paybackTransferOut.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
						transferOutList = paybackTransferOutService.findList(paybackTransferOut);
						if(transferOutList!=null&&transferOutList.size()>0)
						{//存在对应银行流水，此明细更新为已匹配
							CarRepayMatchDetailInfo detail = new CarRepayMatchDetailInfo();
							PaybackTransferOut out = new PaybackTransferOut();
							detail.setId(detailList.get(j).getId());
							detail.setMatchingResult(BankSerialCheck.CHECKE_OVER.getCode());
							detail.preUpdate();
							out.setId(transferOutList.get(0).getId());
							out.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
							out.setrPaybackApplyId(infoList.get(i).getId());
							out.setTransferAccountsId(detailList.get(j).getId());
							out.setRelationType(TargetWay.SERVICE_FEE.getCode());//催收服务费追回匹配
							out.preUpdate();
							carPendingRepayMatchService.updateRepayDetailMatch(detail);//更新明细表记录为已匹配
							paybackTransferOutService.updateOutStatuById(out);//更新对应的银行流水表为已查账
							BigDecimal reallyAmount = detailList.get(j).getReallyAmount();
							alreadyAmount = reallyAmount.add(reallyAmount);
						}else{
							//不存在对应银行流水，此明细不匹配
						}
					}
				}
				if((applyRepayAmount.compareTo(alreadyAmount)==-1)||(applyRepayAmount.compareTo(alreadyAmount)==0))
				{//已匹配明细的实际转账金额和超过申请还款金额时更新本条转账信息为匹配成功,同时更新催收服务费信息表处理状态
					CarPendingRepayMatchInfo updInfo = new CarPendingRepayMatchInfo();
					updInfo.setId(infoList.get(i).getId());
					updInfo.setMatchingResult(CarPendingMatchStatus.REPAY_MATCH_STATUS_SUCCESS.getCode());
					updInfo.setAuditCheckExamine("批量自动匹配");
					updInfo.preUpdate();
					carPendingRepayMatchService.updateRepayMatch(updInfo);
					
					CarUrgeServicesMoney urgeServicesMoney = new CarUrgeServicesMoney();
					urgeServicesMoney.setId("'"+infoList.get(i).getUrgeId()+"'");
					urgeServicesMoney.setDictDealStatus(CounterofferResult.PAYMENT_SUCCEED.getCode());
					urgeServicesMoney.preUpdate();
					grantDeductsService.updateUrge(urgeServicesMoney);
				}
			}
			msg="批量匹配完毕!";
		}else{
			msg="批量匹配失败!";
		}
		return msg;
	}
	
	/**
	 * 待还款匹配列表-批量退回
	 * 2016年3月5日
	 * By 蒋力
	 * @param request
	 * @param response
	 * @param matchingIds
	 * @return msg
	 */
	@ResponseBody
	@RequestMapping(value = "repayBatchBack")
	public String repayBatchBack(HttpServletRequest request, HttpServletResponse response, String backIds,String backMsg) {
		String msg = null;
		StringBuilder parameter = new StringBuilder();
		String ids="";
		String urgeIds="";
		if (!StringUtils.isEmpty(backIds)) {
			String[] infoId = backIds.split(",");
			for (int i = 0; i < infoId.length; i++) {
				parameter.append("'" + infoId[i] + "',");
			}
			ids = parameter.toString();
			ids = ids.substring(0, parameter.lastIndexOf(","));
			CarPendingRepayMatchInfo carPendingRepayMatchInfo = new CarPendingRepayMatchInfo();
			carPendingRepayMatchInfo.setId(ids);
			List<CarPendingRepayMatchInfo> infoList = carPendingRepayMatchService.getCheckedMatchList(carPendingRepayMatchInfo);
			parameter = new StringBuilder();
			for (int i = 0; i < infoList.size(); i++) {
				parameter.append("'" + infoList.get(i).getUrgeId() + "',");
			}
			urgeIds = parameter.toString();
			urgeIds = urgeIds.substring(0,parameter.lastIndexOf(","));
			
			//退回失败把转账信息匹配状态改为退回，保存退回原因，同步修改催收服务费表处理状体为划扣失败
			CarPendingRepayMatchInfo updInfo = new CarPendingRepayMatchInfo();
			updInfo.setId(ids);
			updInfo.setMatchingResult(CarPendingMatchStatus.REPAY_MATCH_STATUS_BACK.getCode());
			updInfo.setAuditCheckExamine(backMsg);
			updInfo.preUpdate();
			carPendingRepayMatchService.updateRepayBatchBack(updInfo);
			
			CarUrgeServicesMoney urgeServicesMoney = new CarUrgeServicesMoney();
			urgeServicesMoney.setId(urgeIds);
			urgeServicesMoney.setDictDealStatus(CounterofferResult.PAYMENT_FAILED.getCode());
			urgeServicesMoney.preUpdate();
			grantDeductsService.updateUrge(urgeServicesMoney);
			
			msg="批量退回成功!";
		}else{
			msg="批量退回失败!";
		}
		return msg;
	}
}