package com.creditharmony.loan.channel.jyj.service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.DeductWay;
import com.creditharmony.core.loan.type.FeeReturn;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contractAudit.service.AssistService;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.constants.ProvinceCity;
import com.creditharmony.loan.borrow.grant.dao.GrantDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.service.GrantUrgeBackService;
import com.creditharmony.loan.channel.common.constants.ChannelConstants;
import com.creditharmony.loan.channel.jyj.dao.JYJGrantDao;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantBFEx;
import com.creditharmony.loan.channel.jyj.entity.JYJGrantEx;
import com.creditharmony.loan.common.event.CreateOrderFileldService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.workFlow.view.LoanFlowQueryParam;
import com.creditharmony.loan.common.workFlow.view.LoanFlowWorkItemView;

/**
 * 放款确认service，用来声明放款过程中的各种方法
 * @Class Name GrantService
 * @author 朱静越
 * @Create In 2015年12月3日
 */
@Service("JYJGrantService")
public class JYJGrantService extends CoreManager<JYJGrantDao, JYJGrantEx>{

	@Autowired
	private GrantDao grantDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantUrgeBackService grantUrgeBackService;
	@Autowired
	private JYJGrantInsertUrgeService grantInsertUrgeService;
	@Autowired
	private CreateOrderFileldService createOrderFileldService;
	@Autowired
	private AssistService assistService;
	/**
	 * 获得放款列表分页对象
	 * 2017年1月20日
	 * By 朱静越
	 * @param page
	 * @param loanFlowQueryParam
	 * @return
	 */
	public Page<LoanFlowWorkItemView> getJyjGrantLists(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)dao.getJyjGrantLists(pageBounds, loanFlowQueryParam);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 不分页查询放款列表
	 * 2017年1月20日
	 * By 朱静越
	 * @param loanFlowQueryParam
	 * @return
	 */
	public List<LoanFlowWorkItemView> getJyjGrantLists(LoanFlowQueryParam loanFlowQueryParam){
		return dao.getJyjGrantLists(loanFlowQueryParam);
	}
	
	/**
	 * 线下放款表导出
	 * 2015年12月22日
	 * By 朱静越
	 * @param id
	 * @return
	 */
	public JYJGrantEx getGrantList(String id){
		return dao.getGrantList(id);
	}
	
	/**
	 * 获取退回原因
	 * 2015年12月25日
	 * By 朱静越
	 * @param contractCode 合同编号
	 * @return 退回原因
	 */
	public String selectBackRea(String contractCode){
		return grantDao.selectBackRea(contractCode);
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
	 * 首次和尾次放款列表导出宝付模板
	 * 2017年5月17日
	 * By 朱静越
	 * @param yJyjGrantBFEx
	 * @return
	 */
	public List<JYJGrantBFEx> getBFGrantList(JYJGrantBFEx yJyjGrantBFEx){
		return dao.getBFGrantList(yJyjGrantBFEx);
	}
	
	/**
	 * 放款表导出处理,查询出来的数据不为空，同时放款的回执结果为成功的时候，才能进行导出
	 * 2017年1月20日
	 * By 朱静越
	 * @param grantList
	 * @param totalAmount
	 * @param ids
	 * @param i
	 * @return
	 */
	public BigDecimal grantItemDao(List<JYJGrantEx> grantList,
			BigDecimal totalAmount, String id) {
		String curAmount;
		String provinceName;
		String cityName;
		JYJGrantEx gse = getGrantList(id);
		if (!ObjectHelper.isEmpty(gse)&&LoansendResult.LOAN_SENDED_SUCCEED
				.getCode().equals(gse.getGrantRecepicResult())) {
		    curAmount = gse.getGrantAmount();
		    provinceName = gse.getBankProvince();
		    cityName = gse.getBankCity();
		    // 省名字格式：***市  ***省  ***自治区
		    if(provinceName!=null){
		        if(provinceName.endsWith("省")||provinceName.endsWith("市")){
		            provinceName = provinceName.substring(0, provinceName.length()-1);
		        }else {
		            ProvinceCity provinceCity = ProvinceCity.parseByAreaName(provinceName);
		            if(provinceCity!=null){
		                provinceName = provinceCity.getMapName();
		            }
		        }
		        gse.setBankProvince(provinceName); 
		    }
		    // 市名字格式：***市  ***自治州  ***地区
		    if(cityName!=null){
		       if(cityName.endsWith("市")){
		            cityName = cityName.substring(0, cityName.length()-1);
		            if ("巴彦淖尔".equals(cityName)) {
		            	cityName = GrantCommon.BA_YAN_ZHUO;
					}
		            if ("乌兰察布".equals(cityName)) {
						cityName = GrantCommon.WU_LAN_CHA;
					}
		        }else if(cityName.endsWith("自治州")){
		            ProvinceCity provinceCity = ProvinceCity.parseByAreaName(cityName);
		            if(provinceCity!=null){
		                cityName = provinceCity.getMapName();
		            }
		        }
		        gse.setBankCity(cityName); 
		    }
		    if(StringUtils.isNotEmpty(curAmount)){
		        totalAmount = totalAmount.add(new BigDecimal(curAmount)); 
		    }
			grantList.add(gse);
		}
		return totalAmount;
	}
	
	/**
	 * 放款退回处理:1.加盖合同废章 2.更新放款记录表 3.更新合同表的退回原因 4.更新借款状态 5.插入历史
	 * 2016年4月23日
	 * By 朱静越
	 * @param str 合同编号
	 * @param backBatchReason 退回原因中文
	 * @param backBatchReasonCode 退回原因code
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void grantBackDeal(String singleParam,String backBatchReason,String backBatchReasonCode){
		try {

	    	backBatchReason = URLDecoder.decode(backBatchReason, "UTF-8");
	    	// 修改放款表
	    	LoanGrant loanGrant = new LoanGrant();
	    	loanGrant.setContractCode(singleParam);
	    	loanGrant.setGoldCreditStatus(ChannelConstants.GOLD_CREDIT_STATUS_INIT);
	    	loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
	    	loanGrant.preUpdate();
	    	loanGrantDao.updateLoanGrant(loanGrant);
	    	
	    	// 更新合同表的退回原因
	    	Contract contract = new Contract();
	    	contract.setContractCode(singleParam);
	    	contract.setContractBackResult(backBatchReason);
	    	contract.setBackFlag(YESNO.YES.getCode());
	    	contract.preUpdate();
			contractDao.updateContract(contract);
			//待放款退回到合同审核 修改orderField 用于排序
			Contract contractOrder=contractDao.findByContractCode(singleParam);
			LoanInfo loanInfoOrder=new LoanInfo();
			loanInfoOrder.setApplyId(contractOrder.getApplyId());
			loanInfoOrder.setDictLoanStatus(LoanApplyStatus.GOLDCREDIT_RETURN.getCode());
			createOrderFileldService.backContractCheckByGrant(loanInfoOrder);
			// 更新借款状态
	    	LoanInfo loanInfo = updateLoanStatus(contractDao.findByContractCode(singleParam).getLoanCode(),LoanApplyStatus.LOAN_SEND_RETURN.getCode());
	    	//退回到合同审核  添加分单功能
	    	assistService.updateAssistAddAuditOperator(contractDao.findByContractCode(singleParam).getLoanCode());
			// 插入历史
			historyService.saveLoanStatusHis(loanInfo,LoanApplyStatus.LOAN_SEND_RETURN.getName(), GrantCommon.SUCCESS,backBatchReason);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("放款退回失败，发生异常，合同编号为："+singleParam);
			throw new ServiceException("放款退回失败，发生异常");
		}
	}

	/**
	 * 线下放款处理:1.更新放款表 2.更新借款状态 3.插入催收服务费信息 4.插入历史
	 * 2016年4月23日
	 * By 朱静越
	 * @param grantExItem
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void offLineGrantDeal(JYJGrantEx grantExItem){
		// 更新放款记录表
		LoanGrant loanGrant  = new LoanGrant();
		loanGrant.setContractCode(grantExItem.getContractCode());
		loanGrant.setGrantFlag(YESNO.YES.getCode()); // 更新首次放款成功到放款审核
		loanGrant.setGrantBatch(grantExItem.getGrantBatchCode()); // 手动输入的放款批次
		loanGrant.setLendingTime(new Date());
		loanGrant.setDictLoanType(DeductWay.OFFLINE.getCode()); // 放款方式--线下
		loanGrantDao.updateLoanGrant(loanGrant);
		
		// 更新借款状态
		Contract contract = contractDao.findByContractCode(grantExItem.getContractCode());
		
		// 放款成功插入催收服务费信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("applyId", contract.getApplyId());
		grantInsertUrgeService.urgeServiceInsertDeal(map);
		
		if (ObjectHelper.isNotEmpty(contract)) {
			// 插入放款成功历史
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setLoanCode(contract.getLoanCode());
			loanInfo.setApplyId(contract.getApplyId());
			historyService.saveLoanStatusHis(loanInfo,"首次放款", GrantCommon.SUCCESS,"");
		}
	}
	
	/**
	 * 手动确认放款 操作数据库相关信息 更新放款表  借款表中借款状态  历史表   催收服务费
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
			historyService.saveLoanStatusHis(loanInfo,"首次放款","成功","");
		}
		//更新放款表  
		if(loanGrant!=null){
			loanGrant.preUpdate();
			loanGrantDao.updateLoanGrant(loanGrant);
		}
		//放款成功插入催收服务费信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("applyId", loanInfo.getApplyId());
		grantInsertUrgeService.urgeServiceInsertDeal(map);
	}
	
	/**
	 * 更新借款状态
	 * 2017年1月22日
	 * By 朱静越
	 * @param singleParam
	 * @param dictLoanStatus
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private LoanInfo updateLoanStatus(String singleParam,String dictLoanStatus) {
		LoanInfo loanInfo = new LoanInfo();
		loanInfo.setLoanCode(singleParam);
		loanInfo.setDictLoanStatus(dictLoanStatus);
		loanInfo.preUpdate();
		loanGrantDao.updateStatus(loanInfo);
		return loanInfo;
	}
	
	/**
	 * 退款状态的校验
	 * 2016年4月23日
	 * By 朱静越
	 * @param dataList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String returnStatusCheck(List<?> dataList){
		String returnStatus = null;
		String result = null;
		List<JYJGrantEx> grantList = (List<JYJGrantEx>) dataList;
		for (JYJGrantEx grantExItem : grantList) {
			returnStatus = grantUrgeBackService
					.getUnReturnCount(grantExItem.getContractCode());
			if (StringUtils.isNotEmpty(returnStatus)) {
				// 如果查询出来的状态为待退款，
				if (FeeReturn.RETURNING.getCode().equals(returnStatus)) {
					result = FeeReturn.RETURNING.getName();
					break;
				}
			}
		}
		return result;
	}
	

	/**
	 * 放款批次的校验
	 * 2016年4月23日
	 * By 朱静越
	 * @param grantList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String grantPchCheck(List<?> dataList){
		String grantPch = null;
		String result = null;
		List<JYJGrantEx> grantList = (List<JYJGrantEx>) dataList;
		for(int i = 0;i<grantList.size()-1;i++){
			grantPch = grantList.get(i).getGrantBatchCode();
			if (StringUtils.isEmpty(grantPch)&&StringUtils.isNotEmpty(grantList.get(i).getContractCode())) {
				result = "放款批次不能为空";
				break;
			}
		}
		return result;
	}
	
}
