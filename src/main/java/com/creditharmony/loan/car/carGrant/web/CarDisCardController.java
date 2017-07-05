package com.creditharmony.loan.car.carGrant.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.CarLoanThroughFlag;
import com.creditharmony.core.loan.type.PaymentWay;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.role.type.BaseRole;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.car.carGrant.ex.CarDistachParamEx;
import com.creditharmony.loan.car.carGrant.ex.CarLoanGrantEx;
import com.creditharmony.loan.car.carGrant.ex.CarParamEx;
import com.creditharmony.loan.car.carGrant.service.CarGrantCAService;
import com.creditharmony.loan.car.carGrant.service.CarGrantSureService;
import com.creditharmony.loan.car.carGrant.view.CarGrantDealView;
import com.creditharmony.loan.car.common.consts.CarLoanResponses;
import com.creditharmony.loan.car.common.consts.CarLoanWorkQueues;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarGrantCommon;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.view.CarLoanFlowQueryView;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.users.entity.UserInfo;
import com.creditharmony.loan.users.service.UserInfoService;
import com.query.ProcessQueryBuilder;

/**
 * 分配卡号列表处理事件
 * 
 * @Class Name CarDisCardController
 * @Create In 2016年1月26日
 */
@Controller
@RequestMapping(value = "${adminPath}/car/grant/disCard")
public class CarDisCardController extends BaseController {
	@Resource(name = "appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
	private MiddlePersonService middlePersonService;
	@Autowired
	private CarHistoryService historyService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private CarGrantSureService grantSureService;
	@Autowired
	private CarContractDao carContractDao;		
	@Autowired
	private CarGrantCAService carGrantCAService;
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;


	/**
	 * 分配卡号处理，获得多个单子的applyId,分割，在同一个类中，
	 * 2016年1月26日
	 * @param model
	 * @param apply
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "disCardJump")
	public String disCardJump(Model model,String checkVal,CarLoanFlowQueryView carLoanFlowQueryView) {
		CarLoanGrantEx lg=new CarLoanGrantEx();
		List<WorkItemView> list=new ArrayList<WorkItemView>();
		List<BaseTaskItemView> workItems=new ArrayList<BaseTaskItemView>();
		// 根据applyId查询页面中显示放款确认的字段
		String[] apply = null;
		try {
			if (StringUtils.isNotEmpty(checkVal)) {
				if (checkVal.indexOf(",") != CarGrantCommon.ONE) {
					apply = checkVal.split(",");
				} else {
					apply = new String[1];
					apply[0] = checkVal;
				}
				// 查询待办列表，获得流程属性
				ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
				ReflectHandle.copy(carLoanFlowQueryView, queryParam);
				queryParam.put("applyId", apply);
				TaskBean taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_COMMISSIONER
						.getWorkQueue(), queryParam,
						BaseTaskItemView.class);
				workItems = (List<BaseTaskItemView>) taskBean.getItemList();
			}else{
				ProcessQueryBuilder queryParam = new ProcessQueryBuilder();
				ReflectHandle.copy(carLoanFlowQueryView, queryParam);
				TaskBean taskBean = flowService.fetchTaskItems(CarLoanWorkQueues.HJ_CAR_LOAN_BALANCE_COMMISSIONER
						.getWorkQueue(), queryParam,
						BaseTaskItemView.class);
				workItems = (List<BaseTaskItemView>) taskBean.getItemList();
			}
			
			for (int i = 0; i < workItems.size(); i++) {
				CarGrantDealView gqp=new CarGrantDealView();
				WorkItemView workItem = new WorkItemView();
				BaseTaskItemView baseView=workItems.get(i);
				ReflectHandle.copy(baseView, workItem);
				
				lg=grantSureService.queryDisDeal(baseView.getApplyId());
				if (lg!=null) {
					ReflectHandle.copy(lg,gqp);
					gqp.setApplyId(baseView.getApplyId());
					//将开户行的码值转换为汉字
					gqp.setCardBank(DictCache.getInstance().getDictLabel("jk_open_bank", gqp.getCardBank()));
					gqp.setDictLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", lg.getLoanFlag()));
					workItem.setBv(gqp);
					list.add(workItem);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("list", list);
		return "car/grant/carLoanflow_disCard_approve_0";
	}
	
	/**
	 * 显示选择页面，进行中间人、放款人员的选择
	 * 2016年1月26日
	 * @return
	 */
	@RequestMapping(value = "showSelectPage")
	public String showMiddlePerson() {

		return "car/grant/carDisCardMiddle";
	}

	/**
	 * iframe显示中间人信息
	 * 2016年1月27日
	 *  @param model
	 * @param request
	 * @param response
	 * @param midPerson 中间人实体
	 * @return
	 *  
	 */
	@RequestMapping(value = "showMidPerson")
	public String grantSureDealJumpMid(Model model,HttpServletRequest request,
			HttpServletResponse response,MiddlePerson midPerson) {
		if( !("".equals(midPerson.getMidBankName())) || !("".equals(midPerson.getMiddleName())) ){
			midPerson.setFirstFlag(null);
		}else {
			midPerson.setFirstFlag("1");
		}
		if (null == midPerson.getMidBankName() || null == midPerson.getMiddleName()) 
			midPerson.setFirstFlag("1");
		
		List<MiddlePerson> middlePage = middlePersonService.selectMiddlePerson(midPerson);
		if (midPerson!=null) {
			model.addAttribute("midPerson", midPerson);
		}
		model.addAttribute("middlePage", middlePage);
		return "car/grant/carMidPerson";
	}
	/**
	 * iframe显示放款人员信息
	 * 2016年1月27日
	 * @param request
	 * @param response
	 * @param model
	 * @param middleId 点击确认，暂存中间人id
	 * @param userInfo 用户实体
	 * 
	 */
	@RequestMapping(value = "showDisPerson")
	public String showDisPerson(HttpServletRequest request,HttpServletResponse response,
			Model model,UserInfo userInfo) {
	
		// 获得放款人员信息,用户类型为放款人员
		 Map<String,String> mapUser = new HashMap<String,String>();
		 mapUser.put("roleId", BaseRole.DELIVERY_PERSON.id);
		 mapUser.put("name", userInfo.getName());
		 mapUser.put("userCode", userInfo.getUserCode());
		 List<UserInfo> user=userInfoService.getRoleUser(mapUser);
		 
		 model.addAttribute("user", user);
		 model.addAttribute("userInfo", userInfo);
		return "car/grant/carDisCardPerson";
	}

	/**
	 * 选择放款人员，回显中间人数据信息
	 * 2016年1月29日
	 * @param userCode 用户编码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "disPersonSure")
	public MiddlePerson disPersonSure(String middleId ,String userCode) {
		
		MiddlePerson middlePerson = null;
		// 根据中间人id查询中间人信息
		try {
			if (StringUtils.isNotEmpty(userCode)) {
				// 根据id查询中间人信息
				middlePerson = middlePersonService.selectById(middleId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return middlePerson;
	}

	/**
	 * 分配卡号节点结束，调用流程更新单子的借款状态，更新放款记录表，放款途径
	 * 2016年2月1日
	 * @param model
	 * @param middleId
	 * @param userCode 用户编码
	 * @param param
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "disCardCommit")
	public String disCardCommit(Model model,String middleId,String userCode,CarParamEx param) {
			logger.info("车借分配卡号签章。。。");
		    List<CarDistachParamEx> list=param.getList();
	    	for (int i = 0; i < list.size(); i++) {
		    	WorkItemView workItem = list.get(i).getWorkItemView();
		    	workItem.setResponse(CarLoanResponses.TO_GRANT.getCode());
				CarGrantDealView gqp = new CarGrantDealView();
				gqp.setApplyId(list.get(i).getApplyId());
				gqp.setContractCode(list.get(i).getContractCode());
				gqp.setLoanCode(list.get(i).getLoanCode());

				gqp.setMidId(middleId);
				// 根据中间人id查找中间人信息
				MiddlePerson middlePerson=middlePersonService.selectById(middleId);
				// 中间人卡号
				if (StringUtils.isNotEmpty(middlePerson.getBankCardNo())) {
					gqp.setMidBankCardNo(middlePerson.getBankCardNo());
				}
				// 中间人开户行
				if (StringUtils.isNotEmpty(middlePerson.getMidBankName())) {
					gqp.setMidBankName(middlePerson.getMidBankName());
				}
				// 中间人姓名
				if (StringUtils.isNotEmpty(middlePerson.getMiddleName())) {
					gqp.setMidBankCardName(middlePerson.getMiddleName());
				}
				// 放款人员编码
				gqp.setGrantPersons(userCode);
				// 根据中间人id查询中间人姓名，获得放款途径
				String middleName = middlePersonService.selectById(middleId)
						.getMiddleName();
				if ("中金放款".equals(middleName)) {
					// 从字典表中取值,流程中存放款路径编码
					gqp.setDictLoanWay(PaymentWay.ZHONGJIN.getCode());
					
				}else if("通联放款".equals(middleName)){
					gqp.setDictLoanWay(PaymentWay.TONG_LIAN.getCode());
				} else {
					gqp.setDictLoanWay(PaymentWay.NET_BANK.getCode());
				
				}
				workItem.setBv(gqp);
				WorkItemView workItemView = flowService.loadWorkItemView(list.get(i).getApplyId(), workItem.getWobNum(), workItem.getToken());
				workItem.setFlowProperties(workItemView.getFlowProperties());
				try {
					// TODO 执行ca签章操作
					CarLoanInfo carLoanInfo = carLoanInfoDao.selectByLoanCode(list.get(i).getLoanCode());
					   String channelFlag = carLoanInfo.getLoanFlag();
					   boolean result = false;
					   if(CarLoanThroughFlag.HARMONY.getCode().equals(channelFlag) || 
							   CarLoanThroughFlag.P2P.getCode().equals(channelFlag)){
						   result = carGrantCAService.signUpCA(list.get(i).getLoanCode(), list.get(i).getContractVersion());
					   }else{
						   result = true;
					   }
					  // boolean result = true;
						/**
						 *CA签章成功则到下一个节点 
						 *CA签章失败则更新签章失败属性 
						 */
						if(result){
							// 设置单子状态，存放的为字典表中的数据,待放款
							gqp.setDictLoanStatus(CarLoanStatus.PENDING_LOAN.getCode());
						    gqp.setSignUpFlag(YESNO.YES.getCode());
						    workItem.setBv(gqp);
						    flowService.dispatch(workItem);
						}else{
							// 设置单子状态，存放的为字典表中的数据,待放款
							gqp.setDictLoanStatus(CarLoanStatus.PENDING_ASSIGNED_CARD_NUMBER.getCode());
						    gqp.setSignUpFlag(YESNO.NO.getCode());
				            workItem.setBv(gqp);
						    flowService.saveData(workItem);
						    return BooleanType.FALSE;
						  //  failedContractCode = distachParamItem.getContractCode();
						}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return BooleanType.TRUE;
	}
}
