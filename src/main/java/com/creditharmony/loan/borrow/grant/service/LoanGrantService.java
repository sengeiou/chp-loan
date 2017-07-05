package com.creditharmony.loan.borrow.grant.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.MaintainType;
import com.creditharmony.core.loan.type.VerityStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.LoanCoborrowerService;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.dao.GCAuditGrantDao;
import com.creditharmony.loan.borrow.grant.dao.GCDiscardDao;
import com.creditharmony.loan.borrow.grant.dao.GCGrantSureDao;
import com.creditharmony.loan.borrow.grant.dao.GCLoanGrantDao;
import com.creditharmony.loan.borrow.grant.dao.GrantCustomerDao;
import com.creditharmony.loan.borrow.grant.dao.GrantDao;
import com.creditharmony.loan.borrow.grant.dao.GrantSumDao;
import com.creditharmony.loan.borrow.grant.dao.JXCreditorBackDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.dao.SendMoneyDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.DisCardEx;
import com.creditharmony.loan.borrow.grant.entity.ex.DistachParamEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantAuditEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantCustomerEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLFirstEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLSecondEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantSumEx;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.SendMoneyEx;
import com.creditharmony.loan.borrow.grant.event.GrantInsertUrgeService;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.channel.jyj.dao.JyjBorrowBankConfigureDao;
import com.creditharmony.loan.channel.jyj.entity.JyjBorrowBankConfigure;
import com.creditharmony.loan.common.dao.GlBillHzDao;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.MiddlePersonDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.dao.PaybackMonthDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.type.LoanProductCode;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 放款确认service，用来声明放款确认过程中的各种方法
 * @Class Name GrantSureService
 * @author 朱静越
 * @Create In 2015年12月3日
 */
@Service("loanGrantService")
public class LoanGrantService extends CoreManager<LoanGrantDao, LoanGrantEx>{
	
	@Autowired
	private GrantUrgeBackService grantUrgeBackService;
	@Autowired
	private GrantCustomerDao grantCustomerDao;
	@Autowired
	private SendMoneyDao sendMoneyDao;
	@Autowired
	private GrantSumDao grantSumDao;
	@Autowired
	private GrantDao grantDao;
	@Autowired
	private MiddlePersonDao middlePersonDao;
	@Autowired
	private CityInfoService cityManager;
	@Autowired
    private GrantCAService grantCAService;
	@Autowired
	private LoanCoborrowerService loanCoborrowerService;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private GCGrantSureDao gCGrantSureDao;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private JXCreditorBackDao jXCreditorBackDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private GCLoanGrantDao gCLoanGrantDao;
	@Autowired
	private GrantInsertUrgeService grantInsertUrgeService;
	@Autowired
	private GrantAuditService grantAuditService;
	@Autowired
	private GCAuditGrantDao gCAuditGrantDao;
	@Autowired
	private GCDiscardDao gCDiscardDao;
	@Autowired
	private AssistService assistService;
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;

	@Autowired
	private ContractFeeDao contractFeeDao;

	@Autowired
	private PaybackMonthDao paybackMonthDao;

	@Autowired
	private GlBillHzDao glBillHzDao;
	@Autowired
	private PaybackDao paybackDao;

	@Autowired
	private LoanCustomerDao loanCustomerDao;
	@Autowired
	private LoanBankDao loanBankDao;
	@Autowired
	private JyjBorrowBankConfigureDao jyjBorrowBankConfigureDao;
	/**
	 * 查询复议流程过来的数据 
	 * 2016年03月03日
	 * By zhanghao
	 * @param applyCode 
	 * @return LoanGrantEx 
	 */
	public LoanGrantEx queryReconsiderGrantDeal(String applyCode){
        return dao.queryReconsiderGrantDeal(applyCode);
    }
	
	public LoanGrantEx findGrant(LoanGrant loanGrant){
		return dao.findGrant(loanGrant);
	}
	
	/**
	 * 根据applyId查询要发送到批处理的list
	 * 2016年2月24日
	 * By 朱静越
	 * @param applyId 流程id
	 * @return 要发送的list
	 */
	public List<DeductReq> selSendsList(LoanGrantEx loanGrantEx){
		//　取得规则
		String rule = loanGrantEx.getRule();
		List<DeductReq> list =  dao.selSendList(loanGrantEx);
		for(DeductReq deductReq:list){
			// 设置划扣标志为代付
			deductReq.setDeductFlag(DeductFlagType.PAY.getCode());
			// 设置划扣规则，从前台接收过来的规则
			deductReq.setRule(rule);
			// 设置备注
			deductReq.setRemarks(deductReq.getBusinessId()+"_放款");
			//  系统处理ID，设置为催收处理
			deductReq.setSysId(DeductWays.HJ_03.getCode());
		}
		return list;
	}
	
	/**
	 * 根据applyId查询loanCode
	 * 2016年2月23日
	 * By 朱静越
	 * @param applyId
	 * @return loanCode
	 */
	public String selLoanCode(String applyId){
		return dao.selLoanCode(applyId);
	}
	
	/**
	 * 分配卡号
	 * 2015年12月31日
	 * By 朱静越
	 * @param applyCode
	 * @return
	 */
	public LoanGrantEx queryDisCardDeal(String loanCode){
		return dao.queryDisCardDeal(loanCode);
	}
	
	/**
	 * 更新单子标识,更新借款主表
	 * 2015年12月3日
	 * By 朱静越
	 * @param applyId
	 * @param loanFlag
	 * @return  
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateFlag(String applyId,String loanFlag){
		LoanInfo loanInfo=new LoanInfo();
		loanInfo.setApplyId(applyId);
		loanInfo.setLoanFlag(loanFlag);
		loanInfo.preUpdate();
		return dao.updateFlag(loanInfo);
	}
	
	/**
	 * 更新单子的状态
	 * 2015年12月3日
	 * By 朱静越
	 * @param applyId
	 * @param dictStatus
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateStatus(String applyId,String dictStatus){
		LoanInfo loanInfo=new LoanInfo();
		loanInfo.setApplyId(applyId);
		loanInfo.setDictLoanStatus(dictStatus);
		loanInfo.preUpdate();
	    return dao.updateStatus(loanInfo);
	}
	
	/**
	 * 更新放款记录表
	 * 2015年12月7日
	 * By 朱静越
	 * @param loanGrant 放款记录表实体
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateLoanGrant(LoanGrant loanGrant){
		loanGrant.preUpdate();
		return dao.updateLoanGrant(loanGrant);
	}
	
	/**
	 * 选择退回原因
	 * 2016年5月17日
	 * By 朱静越
	 * @param map
	 * @return
	 */
	public int selGrantBackMes(Map<String, Object> map){
		return grantDao.selGrantBackMes(map);
	}
	
	/**
	 * 导出客户信息表
	 * 2015年12月21日
	 * By 朱静越
	 * @param id 放款id
	 * @return
	 */
	public List<GrantCustomerEx> getCustomerList(Map<String, Object> applyId){
		return grantCustomerDao.getCustomerList(applyId);
	}
	
	/**
	 * 导出打款表
	 * 2015年12月22日
	 * By 朱静越 
	 * @param id 放款id
	 * @return
	 */
	public List<SendMoneyEx> getMoneyList(Map<String, Object> ids){
		return sendMoneyDao.getMoneyList(ids);
	}
	/**
	 * 导出打款表
	 * 2015年12月22日
	 * By 张建雄
	 * @param id 放款id
	 * @return
	 */
	public List<SendMoneyEx> getAllMoneyList(List<String> loanCodes){
		List<SendMoneyEx> sendList = sendMoneyDao.getAllMoneyList(loanCodes);
		return sendList;
	}
	/**
	 * 查询门店申请冻结的贷款表信息
	 * 2016年3月7日
	 * By 张建雄
	 * @param contractCodes 合同编号
	 * @return
	 */
	public List<SendMoneyEx> findStoreFrozenList(List<String> contractCodes){
		return sendMoneyDao.findStoreFrozenList(contractCodes);
	}
	
	/**
	 * 根据借款编号查询冻结的个数
	 * 2017年5月26日
	 * By 朱静越
	 * @param loanCodes
	 * @return
	 */
	public int getFrozenByLoanCodes(List<String> loanCodes){
		return dao.getFrozenByLoanCodes(loanCodes);
	}
	/**
	 * 导出汇总表
	 * 2015年12月22日
	 * By 朱静越
	 * @param id 放款表合同编号
	 * @return
	 */
	public List<GrantSumEx> getSumList(List<String> loanCodes){
		return grantSumDao.getSumListByloanCodes(loanCodes);
	}
	/**
	 * 导出汇总表
	 * 2015年12月22日
	 * By 张建雄
	 * @param id 放款表合同编号
	 * @return
	 */
	public List<GrantSumEx> getSumList(Map<String, Object> id){
		return grantSumDao.getSumList(id);
	}
	
	/**
	 * 根据applyId查询签约平台
	 * 2016年1月18日
	 * By 朱静越
	 * @param applyId 流程id
	 * @return 签约平台
	 */
	public String selPlat(String applyId){
		return dao.selPlat(applyId);
	}
	
	/**
	 * 查询所有的产品名称
	 * 2016年1月25日
	 * By 朱静越
	 * @return 产品名称集合
	 */
	public List<LoanGrantEx> findProduct(){
		return dao.findProduct();
	}
	
	/**
	 * 查询所有放款批次号
	 * 2016年3月22日
	 * By 朱静越
	 * @return
	 */
	public List<LoanGrant> selGrantPch(){
		return dao.selGrantPch();
	}
	
	/**
	 * 查找最大的批次号 
	 * 2016年2月29日 By zhanghao
	 * @param param
	 * letter 信借 urgentFlag 加急
	 * @return List<LoanGrantEx>
	 */
    public List<LoanGrantEx> findMaxGrantPch(Map<String,String> param){
    	return dao.findMaxJINXINGrantPch(param);
    }
	
	/**
	 * 根据借款编号获取客户信息
	 * 2016年3月4日
	 * xiaoniu.hu
	 * @param loanCodes
	 * @return
	 */
	public List<GrantCustomerEx> getCustomerByLoanCodes(LoanFlowQueryParam loanFlowQueryParam){
		return grantCustomerDao.getCustomerByLoanCodes(loanFlowQueryParam);
	}
	/**
	 * 获取提交批次不重复的集合
	 * 2016年3月12日
	 * 张建雄
	 * @return
	 * @throws Exception 
	 */
	public List<String> findSubmitBatchList(Map<String,String> batch) throws Exception {
		if (batch.size() == 0){
			throw new Exception("借款标识不能够为空，无法完成相应的操作！");
		}
		return grantDao.findSubmitBatchList(batch);
	}
	
	/**
	 * 根据合同编号查找借款表的信息
	 * 2016年12月20日
	 * By 朱静越
	 * @param param
	 * @return
	 */
	public LoanInfo findLoanLinkedContract(Map<String, String> param){
		return applyLoanInfoDao.findLoanLinkedContract(param);
	}
	
	/**
	 * 根据applyId查询被门店申请冻结的单子的合同编号
	 * 2016年3月24日
	 * By 朱静越
	 * @param applyId
	 * @return
	 */
	public String selFrozenContract(String contractCode){
		return dao.selFrozenContract(contractCode);
	}
	
	/**
	 * 根据用户code查询用户id
	 * 2016年2月24日
	 * By 朱静越
	 * @param userCode 用户code
	 * @return 用户id
	 */
	public String selUserName(String userCode){
		return dao.selUserName(userCode);
	}
	
	@SuppressWarnings("unchecked")
	public String grantPchCheckTL(List<?> dataList,String grantWay){
		String grantPch = null;
		String result = null;
		// 通联1
		if (GrantCommon.TL_FIRST.equals(grantWay)) {
			List<GrantImportTLFirstEx> grantList = (List<GrantImportTLFirstEx>) dataList;
			for (int i = 0; i < grantList.size()-1;i++) {
				grantPch = grantList.get(i).getGrantBatchCode();
				if (StringUtils.isEmpty(grantPch)&&StringUtils.isNotEmpty(grantList.get(i).getRemark())) {
					result = "放款批次不能为空";
					break;
				}
			}
		// 通联2
		}else{
			List<GrantImportTLSecondEx> grantPchTL = (List<GrantImportTLSecondEx>) dataList;
			for (int i = 0; i < grantPchTL.size()-1;i++) {
				grantPch = grantPchTL.get(i).getGrantBatchCode();
				if (StringUtils.isEmpty(grantPch)&&StringUtils.isNotEmpty(grantPchTL.get(i).getRemark())) {
					result = "放款批次不能为空";
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 根据借款编号修改放款渠道标识
	 * @param loanCode
	 * @param loanFlag
	 * @return
	 */
	public int updateFlagForLoanCode(String loanCode, String loanFlag) {
		LoanInfo loanInfo=new LoanInfo();
		loanInfo.setLoanCode(loanCode);
		loanInfo.setLoanFlag(loanFlag);
		loanInfo.preUpdate();
		return dao.updateFlag(loanInfo);
	}
	
	/**
	 * 查询待款项确认列表信息
	 * @author songfeng
	 * @Create 2017年2月14日
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<LoanFlowWorkItemView> getGCGrantSureList(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)gCGrantSureDao.getGCGrantSureList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询待款项确认列表信息-不分页
	 * @author songfeng
	 * @Create 2017年2月16日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGCGrantSureList(LoanFlowQueryParam loanFlowQueryParam){
		return gCGrantSureDao.getGCGrantSureList(loanFlowQueryParam);
	}
	/**
	 * 取消金信标志  操作数据库相关信息,同时进行排序
	 * @author songfeng 
	 * @Create 2017年2月17日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void cancelJXFlag(LoanInfo loanInfo,Contract contract,LoanGrant loanGrant,String flagStatus,String LoanStep){
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			historyService.saveLoanStatusHis(loanInfo,LoanStep,"成功",flagStatus);
		}
		if(contract!=null){
			contract.preUpdate();
			contractDao.updateContract(contract);
		}
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
		}
		JXOrder(contract,loanInfo.getLoanCode());
	}
	
	/**
	 * 取消金信标识之后，需要进行排序，取消金信标识的调用了这个排序方法
	 * 2017年3月10日
	 * By 朱静越
	 * @param contract 合同参数
	 * @param loanCode 借款编码
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void JXOrder(Contract contract,String loanCode){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loanCode", loanCode);
		LoanInfo loanInfo = applyLoanInfoDao.selectByLoanCode(param);
		String urgentFlag = loanInfo.getLoanUrgentFlag();
		String frozenFlag = YESNO.YES.getCode().equals(loanInfo.getFrozenFlag())?"01":"00";
		 String backFlag = contract.getBackFlag();
         if(StringUtils.isEmpty(backFlag)){
             backFlag = "00";
         }else{
             backFlag = "0"+backFlag;
         }
         String code = "";
         code =  loanInfo.getDictLoanStatus()+"-1"+urgentFlag + "-"+ frozenFlag + "-"+backFlag; 
         OrderFiled filed = OrderFiled.parseByCode(code);
         if (ObjectHelper.isNotEmpty(filed)) {
        	 String orderField = filed.getOrderId(); 
             if(!ObjectHelper.isEmpty(loanInfo.getModifyTime())){
                 orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
             }
             // 更新数据库
             Map<String,Object> loanParam = new HashMap<String,Object>();
             loanParam.put("loanCode", loanCode);
             loanParam.put("orderField", orderField);
             applyLoanInfoDao.updOrderField(loanParam);
		}
	}
	
	/**
	 * 金信待款项确认退回到合同审核  操作数据库相关信息
	 * @author songfeng 
	 * @Create 2017年2月17日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void sendBackToContractCheck(LoanInfo loanInfo,Contract contract,String result){
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			//退回到合同审核   添加分单功能
			assistService.updateAssistAddAuditOperator(loanInfo.getLoanCode());
			historyService.saveLoanStatusHis(loanInfo,LoanApplyStatus.PAYMENT_BACK.getName(),"成功",result);
		}
		if(contract!=null){
			contract.preUpdate();
			contractDao.updateContract(contract);
		}
	}
	
	/**
	 * 金信处理驳回申请，修改表中驳回状态和历史
	 * 2017年3月24日
	 * @param loanInfo
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void dealBackFrozen(LoanInfo loanInfo){
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			historyService.saveLoanStatusHis(loanInfo, ContractConstant.REJECT_FROZEN, GrantCommon.SUCCESS, 
					loanInfo.getRemark());
		}
	}
	
	/**
	 * 查询债权退回列表信息
	 * @author songfeng
	 * @Create 2017年2月19日
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<LoanFlowWorkItemView> getJXCreditorList(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)jXCreditorBackDao.getJXCreditorBackList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询债权退回列表信息-不分页
	 * @author songfeng
	 * @Create 2017年2月19日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getJXCreditorList(LoanFlowQueryParam loanFlowQueryParam){
		return jXCreditorBackDao.getJXCreditorBackList(loanFlowQueryParam);
	}
	
	/**
	 * 债权退回到合同审核  操作数据库相关信息
	 * @author songfeng 
	 * @Create 2017年2月21日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void sendBackCreditorInfo(LoanInfo loanInfo,LoanGrant loanGrant,String result){
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			//退回到合同审核  添加分单功能
			assistService.updateAssistAddAuditOperator(loanInfo.getLoanCode());
			loanInfo.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_RIGHT_RETURN.getCode());
			historyService.saveLoanStatusHis(loanInfo,LoanApplyStatus.GOLDCREDIT_RIGHT_RETURN.getName(),"成功",result);
		}
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
		}
	}
	
	/**
	 * 查询金信待分配卡号列表信息
	 * @author songfeng
	 * @Create 2017年2月19日
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<DisCardEx> getGCDiscardList(Page<DisCardEx> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<DisCardEx> pageList = (PageList<DisCardEx>)gCDiscardDao.getGCDiscardList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询金信待分配卡号列表信息-不分页
	 * @author songfeng
	 * @Create 2017年2月19日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<DisCardEx> getGCDiscardList(LoanFlowQueryParam loanFlowQueryParam){
		return gCDiscardDao.getGCDiscardList(loanFlowQueryParam);
	}
	
	/**
	 * 金信分配卡号 操作数据库相关信息
	 * @author songfeng 
	 * @Create 2017年2月17日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void disCardInfo(LoanInfo loanInfo,LoanGrant loanGrant){
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getCode());
			historyService.saveLoanStatusHis(loanInfo,LoanApplyStatus.LOAN_CARD_DISTRIBUTE.getName(),"成功","");
		}
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
			Contract contract = new Contract();
			contract = contractDao.findByContractCode(loanGrant.getContractCode());
			// 计算首次放款和尾次放款金额
			if(ObjectHelper.isNotEmpty(contract)&&LoanProductCode.PRO_JIAN_YI_JIE.equals(contract.getProductType())){
	    		JyjBorrowBankConfigure j=new JyjBorrowBankConfigure();
	    		j.setFlag(1);
	    		j.setLoanCode(contract.getLoanCode());
	    		j.setProductCode(contract.getProductType());
				JyjBorrowBankConfigure jb=jyjBorrowBankConfigureDao.selectBank(j);
				BigDecimal firstLoan=jb.getFirstLoanProportion().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);
				BigDecimal endloan=jb.getEndLoanProportion().divide(new BigDecimal(ContractConstant.PERCENT),8,BigDecimal.ROUND_HALF_UP);
				ContractFee contractfee = new ContractFee();
				contractfee.setContractCode(contract.getContractCode());
				contractfee.setFirstGrantAmount(contract.getContractAmount().multiply(firstLoan));
				contractfee.setLastGrantAmount(contract.getContractAmount().multiply(endloan));
				contractFeeDao.updateByBankCode(contractfee);
	    	}
		}
	}
	/**
	 * 查询金信待放款列表信息
	 * @author songfeng
	 * @Create 2017年2月20日
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<LoanFlowWorkItemView> getGCGrantList(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)gCLoanGrantDao.getGCGrantList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询金信待放款列表信息-不分页
	 * @author songfeng
	 * @Create 2017年2月20日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getGCGrantList(LoanFlowQueryParam loanFlowQueryParam){
		return gCLoanGrantDao.getGCGrantList(loanFlowQueryParam);
	}
	
	/**
	 * 放款确认 操作数据库相关信息 更新放款表  借款表中借款状态  历史表   催收服务费
	 * @author songfeng 
	 * @Create 2017年2月21日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void grantSureInfo(LoanInfo loanInfo,LoanGrant loanGrant){
		//更新借款表借款状态  并加历史
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			historyService.saveLoanStatusHis(loanInfo,LoanApplyStatus.LOAN_TO_SEND.getName(),"成功","");
		}
		//更新放款表  
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
		}
		//放款成功插入催收服务费信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("applyId", loanInfo.getApplyId());
		map.put("loanFlag", ChannelFlag.JINXIN.getCode());
		grantInsertUrgeService.urgeServiceInsertDeal(map);
	}
	
	/**
	 * 待放款退回到合同审核  操作数据库相关信息
	 * @author songfeng 
	 * @Create 2017年2月21日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void sendBackGrantInfo(LoanInfo loanInfo,LoanGrant loanGrant,String result){
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			//退回到合同审核  添加分单功能
			assistService.updateAssistAddAuditOperator(loanInfo.getLoanCode());
			loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_RETURN.getCode());
			historyService.saveLoanStatusHis(loanInfo,"放款退回","成功",result);
		}
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
			// 更新合同表中的退回标识
			Contract contract = new Contract();
			contract.setContractCode(loanGrant.getContractCode());
			contract.setContractBackResult(result);
			contract.setBackFlag(YESNO.YES.getCode());
			contract.setContractBackResult(result);
			contractDao.updateContract(contract);
		}
	}
	
	/**
	 * 查询金信放款审核列表信息
	 * @author songfeng
	 * @Create 2017年2月20日
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<GrantAuditEx> getAuditGrantList(Page<GrantAuditEx> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<GrantAuditEx> pageList = (PageList<GrantAuditEx>)gCAuditGrantDao.getGCAuditGrantList(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询金信放款审核列表信息-不分页
	 * @author songfeng
	 * @Create 2017年2月20日
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<GrantAuditEx> getAuditGrantList(LoanFlowQueryParam loanFlowQueryParam){
		return gCAuditGrantDao.getGCAuditGrantList(loanFlowQueryParam);
	}
	
	/**
	 * 金信放款审核退回  操作数据库相关信息
	 * @author songfeng 
	 * @Create 2017年2月22日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void aduitGrantBackInfo(LoanInfo loanInfo,LoanGrant loanGrant,String result){
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			historyService.saveLoanStatusHis(loanInfo.getApplyId(),this.selLoanCode(loanInfo.getApplyId()),
					GrantCommon.GRANT_AUDIT_BACK, GrantCommon.SUCCESS,result);
		}
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
		}
		//放款审核退回，催收服务费的处理
		DistachParamEx distachParamItem=new DistachParamEx();
		distachParamItem.setContractCode(loanGrant.getContractCode());
		grantAuditService.urgeDeal(distachParamItem);
	}
	
	/**
	 * 金信放款审核通过  操作数据库相关信息
	 * @author songfeng 
	 * @Create 2017年2月22日
	 * @param loanInfo
	 * @param contract
	 * @param flagStatus
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void aduitGrantSureInfo(LoanInfo loanInfo,LoanGrant loanGrant,Contract contract){
		if(loanInfo!=null){
			loanInfo.preUpdate();
			applyLoanInfoDao.updateLoanInfo(loanInfo);
			
			// 更新已还款为新增状态，可以使用
			LoanBank record = new LoanBank();
			record.setModifyBy("admin");
			record.setModifyTime(new Date());
			record.setDictMaintainType(MaintainType.ADD.getCode());
			record.setLoanCode(loanInfo.getLoanCode());
			loanBankDao.updateMaintainType(record);
			
			historyService.saveLoanStatusHis(loanInfo.getApplyId(),loanInfo.getLoanCode(),"放款审核", VerityStatus.PASS.getName(), "");
		}
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
			
			// 更新还款主表
			Payback payback = new Payback();
			payback.setContractCode(loanGrant.getContractCode());
			payback.setDictPayStatus(YESNO.NO.getCode());
			payback.setEffectiveFlag(YESNO.YES.getCode());
			paybackDao.updatePayback(payback);
			
			// 更新合同表的审核状态为还款中
			Contract contract1 = new Contract();
			contract1.setContractCode(loanGrant.getContractCode());
			contract1.setDictCheckStatus(LoanApplyStatus.REPAYMENT.getCode());
			contractDao.updateContract(contract1);
		}
	}
	
}
