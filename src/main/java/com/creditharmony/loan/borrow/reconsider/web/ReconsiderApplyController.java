package com.creditharmony.loan.borrow.reconsider.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.creditharmony.bpm.frame.service.FlowService;
import com.creditharmony.bpm.frame.utils.ApplyIdUtils;
import com.creditharmony.bpm.frame.view.BaseTaskItemView;
import com.creditharmony.bpm.frame.view.TaskBean;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.type.UseFlag;
import com.creditharmony.core.lend.type.LoanAccountType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.core.web.BaseController;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompany;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCreditInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanHouse;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.applyinfo.service.DataEntryService;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCoborrowerService;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.reconsider.entity.ex.ReconsiderApplyEx;
import com.creditharmony.loan.borrow.reconsider.entity.ex.ReconsiderEx;
import com.creditharmony.loan.borrow.reconsider.service.ReconsiderApplyService;
import com.creditharmony.loan.borrow.reconsider.view.ReconsiderBusinessView;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.borrow.transate.entity.ex.LoanMinuteEx;
import com.creditharmony.loan.borrow.transate.service.LoanMinuteService;
import com.creditharmony.loan.common.consts.CityInfoConstant;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowQueue;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.query.ProcessQueryBuilder;

/**
 * 获取复议待办列表跟审批信息 
 * @Class Name ReconsiderApplyController
 * @author 张灏
 * @Create In 2015年12月1日
 */
@Controller
@RequestMapping(value = "${adminPath}/apply/reconsiderApply")
public class ReconsiderApplyController extends BaseController {

	@Resource(name="appFrame_flowServiceImpl")
	private FlowService flowService;
	@Autowired
    private ReconsiderApplyService reconsiderApplyService;
	@Autowired
    private LoanPrdMngService loanPrdMngService;
	@Autowired
    private DataEntryService dataEntryService;
	@Autowired
    private AreaService areaService;
	@Autowired
    private UserManager userManager;
	@Autowired
	private LoanCoborrowerService loanCoborrowerService;
	@Autowired
	private LoanMinuteService lms;
	@Autowired
	private CityInfoDao cityInfoDao;
	
	
	/**
	 * 流程发起
	 * 2015年12月1日
	 * By 张灏
	 * @param model
	 * @param launchView
	 * @param itemView
	 * @return 重定向queryReconsiderList.jsp
	 */
    @RequestMapping(value = "launchFlow")
    public String launchFlow(Model model,ReconsiderBusinessView launchView,WorkItemView itemView,String teleFlag) {
        String applyId = ApplyIdUtils.builderApplyId(itemView.getFlowType());
        ReconsiderEx param = new ReconsiderEx();
        List<String> dictLoanStatus = new ArrayList<String>();
        dictLoanStatus.add(LoanApplyStatus.RECHECK_REJECT.getCode());
        dictLoanStatus.add(LoanApplyStatus.GROUP_CHECK_REJECT.getCode());
        dictLoanStatus.add(LoanApplyStatus.FINAL_CHECK_REJECT.getCode());
        param.setQueryType("1");
        param.setDictLoanStatusList(dictLoanStatus);
        param.setQueryDictStatus(dictLoanStatus.toArray(new String[dictLoanStatus.size()]));
        param.setApplyId(launchView.getOldApplyId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        param.setQueryDay(calendar.getTime());
        Calendar confirmQueryDay = Calendar.getInstance();
        confirmQueryDay.setTime(new Date());
        param.setConfirmQueryDay(this.getConfirmStartTime(confirmQueryDay));
        param.setConfirmCode(LoanApplyStatus.SIGN_CONFIRM.getCode());
        param.setProductType("products_type_loan_credit");
        param.setProductStatus(UseFlag.QY.value);
        List<ReconsiderEx> reconsiders = reconsiderApplyService.queryReconsiderList(param);
        logger.debug("根据ApplyId查询当前业务数据，applyId= "+param.getApplyId());
        String loanCode = null;
        List<LoanCoborrower> coborrowers = null;
        ReconsiderEx reconsider = reconsiders.get(0);
        String name = null;
        String id = null;
        User tempUser = null;
        LoanApplyStatus loanApplyStatus = null;
        loanCode = reconsider.getLoanCode();
        // 设置共借人
        coborrowers = loanCoborrowerService.selectByLoanCode(loanCode);
        this.setCoborrowers(reconsider, coborrowers);
        // 设置团队经理
        id = reconsider.getTeamManagerCode();
        launchView.setLoanTeamManagerCode(id);
        if(StringUtils.isNotEmpty(id)){
          tempUser = userManager.get(id);
          if(!ObjectHelper.isEmpty(tempUser)){
            name = tempUser.getName();
            if(StringUtils.isNotEmpty(name)){
               reconsider.setTeamManagerName(name);
               launchView.setLoanTeamManagerName(name);
             }
           }
         }
         // 设置客户经理
         id = reconsider.getCustomerManagerCode();
         launchView.setLoanManagerCode(id);
         if(StringUtils.isNotEmpty(id)){
            tempUser = userManager.get(id);
            if(!ObjectHelper.isEmpty(tempUser)){
               name = tempUser.getName();
               if(StringUtils.isNotEmpty(name)){
                   reconsider.setCustomerManagerName(name);
                   launchView.setLoanManagerName(name);
               }
            }
         }
         id = reconsider.getAgentCode();
         
         
         if(StringUtils.isNotEmpty(id)){
             tempUser = userManager.get(id);
             if(!ObjectHelper.isEmpty(tempUser)){
                launchView.setAgentCode(tempUser.getUserCode());
                name = tempUser.getName();
                if(StringUtils.isNotEmpty(name)){
                    reconsider.setAgentName(name);
                    launchView.setAgentName(name);
                }
             }
          }
         // 设置门店信息
        String storeOrgId = reconsider.getStoreOrgId();
        launchView.setStoreOrgId(storeOrgId);
        launchView.setOrgCode(reconsider.getStoreCode());
        launchView.setOrgName(reconsider.getStoreName());
             
        loanApplyStatus = LoanApplyStatus.parseByCode(reconsider.getLoanStatusCode());
        String curStatusName = loanApplyStatus.getName();
        reconsider.setLoanStatusName(curStatusName);
        // 确认签署的节点，需要结束信借流程
        if(LoanApplyStatus.SIGN_CONFIRM.getName().equals(curStatusName)){
            ProcessQueryBuilder queryBuilder = new ProcessQueryBuilder();
            queryBuilder.put("applyId", launchView.getOldApplyId());
            String queueName =LoanFlowQueue.CUSTOMER_AGENT;
            TaskBean taskBean = flowService.fetchTaskItems(queueName, queryBuilder, BaseTaskItemView.class);
            if(taskBean.getItemList()!=null && taskBean.getItemList().size()>0){
            	@SuppressWarnings("unchecked")
				BaseTaskItemView flowItemView = ((List<BaseTaskItemView>) taskBean.getItemList()).get(0);
            	WorkItemView tagWorkItem = new WorkItemView();
            	ReflectHandle.copy(flowItemView, tagWorkItem);
            	ContractBusiView cbView = new ContractBusiView();
            	cbView.setApplyId(launchView.getOldApplyId());
            	cbView.setOperType(YESNO.NO.getCode());
            	tagWorkItem.setResponse(LoanFlowRoute.GIVEUP);
            	tagWorkItem.setBv(cbView);
            	tagWorkItem.setCheckDealUser(false);
            	flowService.dispatch(tagWorkItem);
            }
            launchView.setNodeFlag("1");
        }
        launchView.setApplyId(applyId);
        launchView.getReconsiderApply().setApplyId(applyId);
        
        if(ObjectHelper.isEmpty(launchView.getReconsiderApplyEx())){
            launchView.setReconsiderApplyEx(new ReconsiderApplyEx());   
        }
        ReconsiderApplyEx reconsiderApplyEx = launchView.getReconsiderApplyEx();
        logger.debug("将查询出来的数据转封装到流程View中");
        ReflectHandle.copy(reconsider, reconsiderApplyEx);
        if(StringUtils.isEmpty(reconsiderApplyEx.getAdditionalFlag())){
            reconsiderApplyEx.setAdditionalFlag(YESNO.NO.getCode());
        }
        String dictLoanType = reconsider.getDictLoanType();
        if(StringUtils.isNotEmpty(dictLoanType)){
             launchView.setDictLoanType(dictLoanType);
             launchView.setDictLoanTypeName(LoanAccountType.parseByCode(dictLoanType).getName());
        }
        String  ChannelCode = reconsider.getChannelCode();
        if(StringUtils.isNotEmpty(ChannelCode)){
            ChannelFlag channelFlag = ChannelFlag.parseByCode(ChannelCode);
            launchView.setLoanFlag(channelFlag.getName());
            launchView.setLoanFlagCode(channelFlag.getCode());
        }
        // 设置申请产品信息
        String productCode =reconsiderApplyEx.getApplyProductCode(); 
        LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
        loanPrd.setProductCode(productCode);
        List<LoanPrdMngEntity> productList = loanPrdMngService
                .selPrd(loanPrd);
        if (!ObjectHelper.isEmpty(productList)) {
            reconsiderApplyEx.setApplyProductName(productList.get(0).getProductName());
        }
        //设置新旧申请表标识
        reconsiderApplyEx.setLoanInfoOldOrNewFlag(reconsider.getLoanInfoOldOrNewFlag());
        launchView.setModel(reconsider.getModel());
        launchView.setConsTelesalesOrgcode(reconsider.getConsTelesalesOrgcode());
        launchView.setReconsiderApplyEx(reconsiderApplyEx);
        // 设置流程状态
        launchView.setDictLoanStatus(LoanApplyStatus.RECONSIDER_CHECK.getName());
        launchView.setDictLoanStatusCode(LoanApplyStatus.RECONSIDER_CHECK.getCode());
        itemView.setBv(launchView);
        flowService.launchFlow(itemView);
        if("1".equals(teleFlag)){
        	return "redirect:" + adminPath + "/apply/reconsiderApply/queryTelesalesReconsiderList";
        }else{
        	return "redirect:" + adminPath + "/apply/reconsiderApply/queryReconsiderList";
        }
    }
    
	/**
	 * 获取需要发起复议的信息列表 2015年12月1日 By 张灏
	 * 
	 * @param model
	 * @param param
	 * @return reconsider_workItems.jsp
	 */
	@RequestMapping("queryReconsiderList")
	public String queryReconsiderList(Model model, ReconsiderEx param) {

		List<String> dictLoanStatus = new ArrayList<String>();
		dictLoanStatus.add(LoanApplyStatus.RECHECK_REJECT.getCode());
		dictLoanStatus.add(LoanApplyStatus.GROUP_CHECK_REJECT.getCode());
		dictLoanStatus.add(LoanApplyStatus.FINAL_CHECK_REJECT.getCode());
		param.setDictLoanStatusList(dictLoanStatus);
		//
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		
		Calendar confirmQueryDay = Calendar.getInstance();
		confirmQueryDay.setTime(new Date());
		
		String queryCode = "";
		//查询状态为空（拒借）
		if (ObjectHelper.isEmpty(param.getQueryDictStatus()) || param.getQueryDictStatus().length == 0) {
			//复审拒借、终审拒借、高级终审拒借
			param.setQueryDictStatus(dictLoanStatus.toArray(new String[dictLoanStatus.size()]));
			//拒借可以发起复议的时间为3个自然日内
			param.setQueryDay(calendar.getTime());
			//待确认签署
			param.setConfirmCode(LoanApplyStatus.SIGN_CONFIRM.getCode());
			//待确认签署可以发起复议的时间为3个工作日内
			param.setConfirmQueryDay(this.getConfirmStartTime(confirmQueryDay));
			//查询中的借款状态为空时 设置为1
			param.setQueryType("1");
		//查询状态不为空
		} else if (!ObjectHelper.isEmpty(param.getQueryDictStatus()) && param.getQueryDictStatus().length != 0) {
			queryCode = param.getQueryDictStatus()[0];
			//查询状态等于待确认签署
			if (queryCode.equals(LoanApplyStatus.SIGN_CONFIRM.getCode())) {
				param.setConfirmCode(LoanApplyStatus.SIGN_CONFIRM.getCode());
				//查询中的借款状态不为空时 并且查询状态等于待确认签署 设置为3
				param.setQueryType("3");
				//待确认签署可以发起复议的时间位3个工作日内
				param.setConfirmQueryDay(this.getConfirmStartTime(confirmQueryDay));
			} else {
				param.setQueryDay(calendar.getTime());
				//查询中的借款状态不为空时 并且查询状态不等于待确认签署时 设置为2
				param.setQueryType("2");
			}
		}
		User user = UserUtils.getUser();
		// 设置本门店可以发起的复议信息
		param.setStoreOrgId(user.getDepartment().getId());

		param.setProductType("products_type_loan_credit");
		param.setProductStatus(UseFlag.QY.value);
		List<ReconsiderEx> reconsiders = reconsiderApplyService.queryReconsiderList(param);
		String loanCode = null;
		List<LoanCoborrower> coborrowers = null;
		ReconsiderEx reconsider;
		String name = null;
		String id = null;
		User tempUser = null;
		ChannelFlag channelFlag = null;
		LoanApplyStatus loanApplyStatus = null;
		//产品
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		LoanModel loanModel = null;
		if (!ObjectHelper.isEmpty(reconsiders)) {
			for (int i = 0; i < reconsiders.size(); i++) {
				reconsider = reconsiders.get(i);
				loanCode = reconsider.getLoanCode();
				coborrowers = loanCoborrowerService.selectByLoanCode(loanCode);
				this.setCoborrowers(reconsider, coborrowers);
				//设置团队经理
				id = reconsider.getTeamManagerCode();
				if (StringUtils.isNotEmpty(id)) {
					tempUser = userManager.get(id);
					if (!ObjectHelper.isEmpty(tempUser)) {
						name = tempUser.getName();
						if (StringUtils.isNotEmpty(name)) {
							reconsider.setTeamManagerName(name);
						}
					}
				}
				//设置客户经理
				id = reconsider.getCustomerManagerCode();
				if (StringUtils.isNotEmpty(id)) {
					tempUser = userManager.get(id);
					if (!ObjectHelper.isEmpty(tempUser)) {
						name = tempUser.getName();
						if (StringUtils.isNotEmpty(name)) {
							reconsider.setCustomerManagerName(name);
						}
					}
				}
				//借款状态
				loanApplyStatus = LoanApplyStatus.parseByCode(reconsider.getLoanStatusCode());
				//渠道标示
				channelFlag = ChannelFlag.parseByCode(reconsider.getChannelCode());
				if (!ObjectHelper.isEmpty(channelFlag)) {
					reconsider.setChannelName(channelFlag.getName());
				}
				//批复产品
				if (StringUtils.isNotEmpty(reconsider.getReplyProductCode())) {
					String replyProductCode = reconsider.getReplyProductCode();
					for (LoanPrdMngEntity e : productList) {
						if (replyProductCode.equals(e.getProductCode())) {
							reconsider.setReplyProductName(e.getProductName());
							break;
						}
					}
				}
				loanModel = LoanModel.parseByCode(reconsider.getModel());
				if (!ObjectHelper.isEmpty(loanModel)) {
					reconsider.setModelName(loanModel.getName());
				}
				reconsider.setLoanStatusName(loanApplyStatus.getName());
				reconsiders.set(i, reconsider);
			}
		}
		model.addAttribute("queryCode", queryCode);
		model.addAttribute("productList", productList);
		model.addAttribute("itemList", reconsiders);
		model.addAttribute("param", param);
		model.addAttribute("queryStatusList", getQueryStatusList());
		model.addAttribute("teleFlag", YESNO.NO.getCode());
		return "/borrow/borrowlist/reconsider_workItems";
	}
	/**
	 * 电销部用的发起复议列表
	 */
	@RequestMapping("queryTelesalesReconsiderList")
	public String queryTelesalesReconsiderList(Model model, ReconsiderEx param) {

		List<String> dictLoanStatus = new ArrayList<String>();
		dictLoanStatus.add(LoanApplyStatus.RECHECK_REJECT.getCode());
		dictLoanStatus.add(LoanApplyStatus.GROUP_CHECK_REJECT.getCode());
		dictLoanStatus.add(LoanApplyStatus.FINAL_CHECK_REJECT.getCode());
		param.setDictLoanStatusList(dictLoanStatus);
		//
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		
		Calendar confirmQueryDay = Calendar.getInstance();
		confirmQueryDay.setTime(new Date());
		
		String queryCode = "";
		//查询状态为空（拒借）
		if (ObjectHelper.isEmpty(param.getQueryDictStatus()) || param.getQueryDictStatus().length == 0) {
			//复审拒借、终审拒借、高级终审拒借
			param.setQueryDictStatus(dictLoanStatus.toArray(new String[dictLoanStatus.size()]));
			//拒借可以发起复议的时间为3个自然日内
			param.setQueryDay(calendar.getTime());
			//待确认签署
			param.setConfirmCode(LoanApplyStatus.SIGN_CONFIRM.getCode());
			//待确认签署可以发起复议的时间为3个工作日内
			param.setConfirmQueryDay(this.getConfirmStartTime(confirmQueryDay));
			//查询中的借款状态为空时 设置为1
			param.setQueryType("1");
		//查询状态不为空
		} else if (!ObjectHelper.isEmpty(param.getQueryDictStatus()) && param.getQueryDictStatus().length != 0) {
			queryCode = param.getQueryDictStatus()[0];
			//查询状态等于待确认签署
			if (queryCode.equals(LoanApplyStatus.SIGN_CONFIRM.getCode())) {
				param.setConfirmCode(LoanApplyStatus.SIGN_CONFIRM.getCode());
				//查询中的借款状态不为空时 并且查询状态等于待确认签署 设置为3
				param.setQueryType("3");
				//待确认签署可以发起复议的时间位3个工作日内
				param.setConfirmQueryDay(this.getConfirmStartTime(confirmQueryDay));
			} else {
				param.setQueryDay(calendar.getTime());
				//查询中的借款状态不为空时 并且查询状态不等于待确认签署时 设置为2
				param.setQueryType("2");
			}
		}
		User user = UserUtils.getUser();
		//设置只查询电销的
		param.setTelesalesFlag(YESNO.YES.getCode());
		//设置只查询当前登录人员的数据（本列表只开放给电销录单专员，只查询自己取过的单）
		param.setConsServiceUserCode(user.getId());

		param.setProductType("products_type_loan_credit");
		param.setProductStatus(UseFlag.QY.value);
		List<ReconsiderEx> reconsiders = reconsiderApplyService.queryReconsiderList(param);
		String loanCode = null;
		List<LoanCoborrower> coborrowers = null;
		ReconsiderEx reconsider;
		String name = null;
		String id = null;
		User tempUser = null;
		ChannelFlag channelFlag = null;
		LoanApplyStatus loanApplyStatus = null;
		//产品
		LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
		List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
		LoanModel loanModel = null;
		if (!ObjectHelper.isEmpty(reconsiders)) {
			for (int i = 0; i < reconsiders.size(); i++) {
				reconsider = reconsiders.get(i);
				loanCode = reconsider.getLoanCode();
				coborrowers = loanCoborrowerService.selectByLoanCode(loanCode);
				this.setCoborrowers(reconsider, coborrowers);
				//设置团队经理
				id = reconsider.getTeamManagerCode();
				if (StringUtils.isNotEmpty(id)) {
					tempUser = userManager.get(id);
					if (!ObjectHelper.isEmpty(tempUser)) {
						name = tempUser.getName();
						if (StringUtils.isNotEmpty(name)) {
							reconsider.setTeamManagerName(name);
						}
					}
				}
				//设置客户经理
				id = reconsider.getCustomerManagerCode();
				if (StringUtils.isNotEmpty(id)) {
					tempUser = userManager.get(id);
					if (!ObjectHelper.isEmpty(tempUser)) {
						name = tempUser.getName();
						if (StringUtils.isNotEmpty(name)) {
							reconsider.setCustomerManagerName(name);
						}
					}
				}
				//借款状态
				loanApplyStatus = LoanApplyStatus.parseByCode(reconsider.getLoanStatusCode());
				//渠道标示
				channelFlag = ChannelFlag.parseByCode(reconsider.getChannelCode());
				if (!ObjectHelper.isEmpty(channelFlag)) {
					reconsider.setChannelName(channelFlag.getName());
				}
				//批复产品
				if (StringUtils.isNotEmpty(reconsider.getReplyProductCode())) {
					String replyProductCode = reconsider.getReplyProductCode();
					for (LoanPrdMngEntity e : productList) {
						if (replyProductCode.equals(e.getProductCode())) {
							reconsider.setReplyProductName(e.getProductName());
							break;
						}
					}
				}
				loanModel = LoanModel.parseByCode(reconsider.getModel());
				if (!ObjectHelper.isEmpty(loanModel)) {
					reconsider.setModelName(loanModel.getName());
				}
				reconsider.setLoanStatusName(loanApplyStatus.getName());
				reconsiders.set(i, reconsider);
			}
		}
		model.addAttribute("queryCode", queryCode);
		model.addAttribute("productList", productList);
		model.addAttribute("itemList", reconsiders);
		model.addAttribute("param", param);
		model.addAttribute("queryStatusList", getQueryStatusList());
		model.addAttribute("teleFlag", YESNO.YES.getCode());
		return "/borrow/borrowlist/reconsider_workItems";
	}
    
    /**
     * 设置共借人信息 2015年11月24日 By zhanghao
     * 
     * @param launchView
     * @return none
     */
    private void setCoborrowers(ReconsiderEx reconsiderEx,List<LoanCoborrower> coborrowers) {
        StringBuffer coborrowerBuffer = new StringBuffer();
        for (LoanCoborrower cur : coborrowers) {
            if (coborrowerBuffer.length() == 0) {
                coborrowerBuffer.append(cur.getCoboName());
            } else {
                coborrowerBuffer.append("," + cur.getCoboName());
            }
        }
        if (coborrowerBuffer.length() == 0) {
            coborrowerBuffer.append("");
        }
        reconsiderEx.setCoborrowerName(coborrowerBuffer.toString());
    }
    /**
     *获取确认签署查询的起始时间 
     * 
     * 
     */
    private Date getConfirmStartTime(Calendar cal){
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.FRIDAY) {
			cal.add(Calendar.DAY_OF_MONTH, -3);
		} else if(dayOfWeek == Calendar.SATURDAY){
			cal.add(Calendar.DAY_OF_MONTH, -4);
		} else if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.TUESDAY || dayOfWeek == Calendar.WEDNESDAY) {
			cal.add(Calendar.DAY_OF_MONTH, -5);
		}
		return cal.getTime();
    }
    /**
     * 2015年12月3日
     * By zhanghao
     * @param model model模型
     * @param viewName 返回页面
     * @param loanCode 借款编码
     * @return reconsider.jsp
     */
    @RequestMapping(value = "getAllApplyInfo")
    public String getAllApplyInfo(Model model,String viewName,String loanCode) {
    	LaunchView launchView = new LaunchView();
    	Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId", CityInfoConstant.ROOT_ID);
        List<CityInfo> provinceList = cityInfoDao.findByParams(params);
        launchView.setProvinceList(provinceList);
    	ApplyInfoFlagEx applyInfo = areaService.getAllInfo(loanCode);
		BeanUtils.copyProperties(applyInfo, launchView);
		// 根据产品编号查询产品类型
		if (!ObjectHelper.isEmpty(launchView.getLoanInfo())) {
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			loanPrd.setProductCode(launchView.getLoanInfo().getProductType());
			List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
			if (productList.size() != 0) {
				launchView.setProductName(productList.get(0).getProductName());
			}						
		}
		// 查询产品列表	
		LoanPrdMngEntity loanPrd1 = new LoanPrdMngEntity();	
		List<LoanPrdMngEntity> productList1 = loanPrdMngService.selPrd(loanPrd1);
		launchView.setProductList(productList1);
		launchView.setApplyId(applyInfo.getLoanInfo().getApplyId());
		areaService.coboAreaChange(launchView);
		// 房产省市区数据更换成名字
		areaService.houseAreaChange(launchView);
		// 开户行省市数据添加
		areaService.bankAreaChange(launchView);
		// 申请信息,管辖城市	
		areaService.applyAreaChange(launchView);
		// 公司地址省市id与name转换
		areaService.companyAreaChange(launchView);
		// 借款信息-录入人
		if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanCustomerService())) {
			String service = areaService.getUserName(launchView.getLoanInfo().getLoanCustomerService());
			launchView.getLoanInfo().setLoanCustomerService(service);
		}
		if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanManagerCode())) {
			launchView.getLoanInfo().setLoanManagerName(areaService.getUserName(launchView.getLoanInfo().getLoanManagerCode()));
		}
		LoanMinuteEx loanMinute = lms.loanMinute(loanCode);
		loanMinute.setLoanCode(loanCode);
		
		String customerSex = DictCache.getInstance().getDictLabel("jk_sex",launchView.getLoanCustomer().getCustomerSex());
		launchView.getLoanCustomer().setCustomerSexLabel(customerSex);
		
		String dictMarry = DictCache.getInstance().getDictLabel("jk_marriage",launchView.getLoanCustomer().getDictMarry());
		launchView.getLoanCustomer().setDictMarryLabel(dictMarry);
		
		String dictCertType = DictCache.getInstance().getDictLabel("jk_certificate_type",launchView.getLoanCustomer().getDictCertType());
		launchView.getLoanCustomer().setDictCertTypeLabel(dictCertType);
		
		String dictEducation = DictCache.getInstance().getDictLabel("jk_degree",launchView.getLoanCustomer().getDictEducation());
		launchView.getLoanCustomer().setDictEducationLabel(dictEducation);
		
		String dictCustomerSource = DictCache.getInstance().getDictLabel("jk_cm_src",launchView.getLoanCustomer().getDictCustomerSource());
		launchView.getLoanCustomer().setDictCustomerSourceLabel(dictCustomerSource);
		
		String dictLoanUse = DictCache.getInstance().getDictLabel("jk_loan_use",launchView.getLoanInfo().getDictLoanUse());
		launchView.getLoanInfo().setDictLoanUserLabel(dictLoanUse);
		
		String loanUrgentFlag = DictCache.getInstance().getDictLabel("jk_urgent_flag",launchView.getLoanInfo().getLoanUrgentFlag());
		launchView.getLoanInfo().setLoanUrgentFlagLabel(loanUrgentFlag);
		
		String compPost = DictCache.getInstance().getDictLabel("jk_job_type",launchView.getCustomerLoanCompany().getCompPost());
		launchView.getCustomerLoanCompany().setCompPostLabel(compPost);
		
		String dictCompType = DictCache.getInstance().getDictLabel("jk_unit_type",launchView.getCustomerLoanCompany().getDictCompType());
		launchView.getCustomerLoanCompany().setDictCompTypeLabel(dictCompType);
		
		String bankSigningPlatform = DictCache.getInstance().getDictLabel("jk_deduct_plat",launchView.getLoanBank().getBankSigningPlatform());
		launchView.getLoanBank().setBankSigningPlatformName(bankSigningPlatform);
		
		String bankName = DictCache.getInstance().getDictLabel("jk_open_bank",launchView.getLoanBank().getBankName());
		launchView.getLoanBank().setBankNameLabel(bankName);
		
		for(LoanCreditInfo lc:launchView.getLoanCreditInfoList()){
			String dictMortgageType = DictCache.getInstance().getDictLabel("jk_pledge_flag",lc.getDictMortgageType());
			lc.setDictMortgageTypeLabel(dictMortgageType);
		}
		
		for(LoanHouse lh:launchView.getCustomerLoanHouseList()){
			String houseBuyway = DictCache.getInstance().getDictLabel("jk_house_buywayg",lh.getHouseBuyway());
			lh.setHouseBuywayLabel(houseBuyway);
			
			String housePledgeFlag = DictCache.getInstance().getDictLabel("jk_pledge_flag",lh.getHousePledgeFlag());
			lh.setHousePledgeFlagLabel(housePledgeFlag);
		}
		
		for(Contact ct:launchView.getCustomerContactList()){
			String relationType = DictCache.getInstance().getDictLabel("jk_relation_type",ct.getRelationType());
			ct.setRelationTypeLabel(relationType);
			
			switch(ct.getRelationType()){
			case "0":
				String contactRelation1 = DictCache.getInstance().getDictLabel("jk_loan_family_relation",ct.getContactRelation());
				ct.setContactRelationLabel(contactRelation1);
				break;
			case "1":
				String contactRelation2 = DictCache.getInstance().getDictLabel("jk_loan_workmate_relation",ct.getContactRelation());
				ct.setContactRelationLabel(contactRelation2);
				break;
			case "2":
				String contactRelation3 = DictCache.getInstance().getDictLabel("jk_loan_other_relation",ct.getContactRelation());
				ct.setContactRelationLabel(contactRelation3);
				break;
			default:ct.setContactRelationLabel(ct.getContactRelation());
			}
		}		
		model.addAttribute("lm", loanMinute);
		model.addAttribute("bview", launchView);
        return "borrow/reconsider/" + viewName;
    }
    /**
     * 点击查看申请按钮跳转到新版页面
     * By zmq
     * @param model model模型
     * @param viewName 返回页面
     * @param loanCode 借款编码
     * @return reconsider.jsp
     */
    @RequestMapping(value = "getAllApplyInfoNew")
    public String getAllApplyInfoNew(Model model,String viewName,String loanCode) {
    	LaunchView launchView = new LaunchView();
    	Map<String,Object> params = new HashMap<String,Object>();
        params.put("parentId", CityInfoConstant.ROOT_ID);
        List<CityInfo> provinceList = cityInfoDao.findByParams(params);
        launchView.setProvinceList(provinceList);
    	ApplyInfoFlagEx applyInfo = areaService.getAllInfo(loanCode);
		BeanUtils.copyProperties(applyInfo, launchView);
		// 根据产品编号查询产品类型
		if (!ObjectHelper.isEmpty(launchView.getLoanInfo())) {
			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
			loanPrd.setProductCode(launchView.getLoanInfo().getProductType());
			List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
			if (productList.size() != 0) {
				launchView.setProductName(productList.get(0).getProductName());
			}						
		}
		// 查询产品列表	
		LoanPrdMngEntity loanPrd1 = new LoanPrdMngEntity();	
		List<LoanPrdMngEntity> productList1 = loanPrdMngService.selPrd(loanPrd1);
		launchView.setProductList(productList1);
		launchView.setApplyId(applyInfo.getLoanInfo().getApplyId());
		areaService.coboAreaChange(launchView);
		// 房产省市区数据更换成名字
		areaService.houseAreaChange(launchView);
		// 开户行省市数据添加
		areaService.bankAreaChange(launchView);
		// 申请信息,管辖城市	
		areaService.applyAreaChange(launchView);
		// 公司地址省市id与name转换
		areaService.companyAreaChange(launchView);
		//配偶地址省市id 与name转换
		areaService.mateAddressChange(launchView);
		//经营地址省市id与name转换
		areaService.loanCompManageAddressChange(launchView);
		//证件信息户主页地址省市id与name转换
		areaService.masterCertAddressChange(launchView);
		// 借款信息-录入人
		if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanCustomerService())) {
			String service = areaService.getUserName(launchView.getLoanInfo().getLoanCustomerService());
			launchView.getLoanInfo().setLoanCustomerService(service);
		}
		if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanManagerCode())) {
			launchView.getLoanInfo().setLoanManagerName(areaService.getUserName(launchView.getLoanInfo().getLoanManagerCode()));
		}
		LoanMinuteEx loanMinute = lms.loanMinute(loanCode);
		loanMinute.setLoanCode(loanCode);
		// 缓存码表取值
      	// 客户信息
        LoanCustomer lc = launchView.getLoanCustomer();
      	lc.setCustomerSexLabel(DictCache.getInstance().getDictLabel("jk_sex", lc.getCustomerSex()));
       	lc.setDictCertTypeLabel(DictCache.getInstance().getDictLabel("com_certificate_type", lc.getDictCertType()));
      	lc.setDictMarryLabel(DictCache.getInstance().getDictLabel("jk_marriage", lc.getDictMarry()));
       	lc.setDictEducationLabel(DictCache.getInstance().getDictLabel("jk_degree", lc.getDictEducation()));
       	lc.setDictCustomerSourceLabel(DictCache.getInstance().getDictLabel("jk_cm_src", lc.getDictCustomerSource()));
       	// 申请信息
       	LoanInfo loanInfo = launchView.getLoanInfo();
       	loanInfo.setDictLoanUserLabel(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfo.getDictLoanUse()));
       	loanInfo.setLoanUrgentFlagLabel(DictCache.getInstance().getDictLabel("jk_urgent_flag", loanInfo.getLoanUrgentFlag()));   
       	loanInfo.setDictLoanSource(DictCache.getInstance().getDictLabel("jk_repay_source_new", loanInfo.getDictLoanSource()));
       	loanInfo.setDictLoanUse(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfo.getDictLoanUse()));
       	loanInfo.setLoanUrgentFlagLabel(DictCache.getInstance().getDictLabel("yes_no", loanInfo.getLoanUrgentFlag()));
        // 信用资料
       	List<LoanCreditInfo> list = launchView.getLoanCreditInfoList();
       	for (LoanCreditInfo loanCreditInfo : list) {
       		loanCreditInfo.setDictMortgageTypeLabel(DictCache.getInstance().getDictLabel("jk_pledge_flag", loanCreditInfo.getDictMortgageType()));
		}
       	// 职业信息/公司资料
       	LoanCompany loanCompany = launchView.getCustomerLoanCompany();
       	loanCompany.setCompPostLabel(DictCache.getInstance().getDictLabel("jk_job_type", loanCompany.getCompPost()));
       	loanCompany.setDictCompTypeLabel(DictCache.getInstance().getDictLabel("jk_unit_type", loanCompany.getDictCompType()));
       	//所属行业
       	loanCompany.setDictCompIndustry(DictCache.getInstance().getDictLabel("jk_industry_type", loanCompany.getDictCompIndustry()));
       	loanCompany.setDictSalaryPay(DictCache.getInstance().getDictLabel("jk_paysalary_way", loanCompany.getDictSalaryPay()));
    	loanCompany.setCompPostLevel(DictCache.getInstance().getDictLabel("jk_job_type", loanCompany.getCompPostLevel()));
    	// 房产资料
       	List<LoanHouse> hostList = launchView.getCustomerLoanHouseList();
       	for (LoanHouse loanHouse : hostList) {
       		loanHouse.setHouseBuywayLabel(DictCache.getInstance().getDictLabel("jk_house_buywayg", loanHouse.getHouseBuyway()));
       		loanHouse.setHousePledgeFlagLabel(DictCache.getInstance().getDictLabel("jk_pledge_flag", loanHouse.getHousePledgeFlag()));
		}
       	// 联系人资料
       	List<Contact> contactList = launchView.getCustomerContactList();
       	//将联系人分类
       	Map<String,List<Contact>> contactMap=new HashMap<String,List<Contact>>();
       	//将联系人分为小类，家庭联系人
       	List<Contact>  familyList=new ArrayList<Contact>();
       	//将联系人分为小类，工作证明人
       	List<Contact>  workmateList=new ArrayList<Contact>();
       	//将联系人分为小类，其他联系人
       	List<Contact>  otherRelationList=new ArrayList<Contact>();
       	for (Contact contact : contactList) {
       		contact.setRelationTypeLabel(DictCache.getInstance().getDictLabel("jk_relation_type", contact.getRelationType()));
       		if ("0".equals(contact.getRelationType())) {
       			contact.setContactRelationLabel(DictCache.getInstance().getDictLabel("jk_loan_family_relation", contact.getContactRelation()));		
       			//将家属联系人单独列出
       			familyList.add(contact);
       			contactMap.put("family_relation", familyList);
			}
       		if ("1".equals(contact.getRelationType())) {
       			contact.setContactRelationLabel(DictCache.getInstance().getDictLabel("jk_loan_workmate_relation", contact.getContactRelation()));	
       			//将工作证明人单独列出
       			workmateList.add(contact);
       			contactMap.put("workmate_relation", workmateList);
			}
       		if ("2".equals(contact.getRelationType())) {
       			//contact.setContactRelationLabel(DictCache.getInstance().getDictLabel("jk_loan_other_relation", contact.getContactRelation()));	
       			//直接取备注里的值
       			contact.getRemarks();
       			//将其他联系人单独列出
       			otherRelationList.add(contact);
       			contactMap.put("other_relation", otherRelationList);
			}
		}
       	//将分类好的联系人加载进实体launchView中
       	launchView.setContactMap(contactMap);
       	// 银行卡资料
       	LoanBank loanBank = launchView.getLoanBank();
       	loanBank.setBankNameLabel(DictCache.getInstance().getDictLabel("jk_open_bank", loanBank.getBankName()));
       	loanBank.setBankSigningPlatformName(DictCache.getInstance().getDictLabel("jk_deduct_plat",loanBank.getBankSigningPlatform()));
        model.addAttribute("lm", loanMinute);
        model.addAttribute("bview", launchView);
        model.addAttribute("loanCode",loanCode);
        model.addAttribute("contactMap", contactMap);
        return "borrow/reconsider/" + viewName;
    }
    
    /**
     *抓取复议筛选下拉框数据 
     *By zhanghao
     *2016年05月13日 
     *@param none
     *@return List<Map<String,Object>>
     */ 
    private List<Map<String,Object>> getQueryStatusList(){
     List<Map<String,Object>> queryStatusList = new ArrayList<Map<String,Object>>();
     Map<String,Object> queryMap = new HashMap<String,Object>(); 
     queryMap.put("code", LoanApplyStatus.RECHECK_REJECT.getCode());
     queryMap.put("name", LoanApplyStatus.RECHECK_REJECT.getName());
     queryStatusList.add(queryMap);
     queryMap = new HashMap<String,Object>(); 
     queryMap.put("code", LoanApplyStatus.GROUP_CHECK_REJECT.getCode());
     queryMap.put("name", LoanApplyStatus.GROUP_CHECK_REJECT.getName());
     queryStatusList.add(queryMap);
     queryMap = new HashMap<String,Object>(); 
     queryMap.put("code", LoanApplyStatus.FINAL_CHECK_REJECT.getCode());
     queryMap.put("name", LoanApplyStatus.FINAL_CHECK_REJECT.getName());
     queryStatusList.add(queryMap);
     queryMap = new HashMap<String,Object>(); 
     queryMap.put("code", LoanApplyStatus.SIGN_CONFIRM.getCode());
     queryMap.put("name", LoanApplyStatus.SIGN_CONFIRM.getName());
     queryStatusList.add(queryMap);
     return queryStatusList;
    }
}
