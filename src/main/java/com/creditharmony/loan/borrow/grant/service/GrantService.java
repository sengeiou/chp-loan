package com.creditharmony.loan.borrow.grant.service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.DeductWay;
import com.creditharmony.core.loan.type.FeeReturn;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoansendResult;
import com.creditharmony.core.loan.type.PaymentWay;
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
import com.creditharmony.loan.borrow.grant.constants.ResultConstants;
import com.creditharmony.loan.borrow.grant.dao.GrantDao;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLFirstEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportTLSecondEx;
import com.creditharmony.loan.borrow.grant.entity.ex.GrantImportZJEx;
import com.creditharmony.loan.borrow.grant.event.GrantInsertUrgeService;
import com.creditharmony.loan.borrow.grant.util.GrantUtil;
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
@Service("GrantService")
public class GrantService extends CoreManager<GrantDao, GrantEx>{

	@Autowired
	private GrantDao grantDao;
	@Autowired
	private LoanGrantDao loanGrantDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
    private GrantCAService grantCAService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private GrantUrgeBackService grantUrgeBackService;
	@Autowired
	private GrantInsertUrgeService grantInsertUrgeService;
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
	public Page<LoanFlowWorkItemView> getGrantLists(Page<LoanFlowWorkItemView> page,LoanFlowQueryParam loanFlowQueryParam){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<LoanFlowWorkItemView> pageList = (PageList<LoanFlowWorkItemView>)grantDao.getGrantLists(pageBounds, loanFlowQueryParam);
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
	public List<LoanFlowWorkItemView> getGrantLists(LoanFlowQueryParam loanFlowQueryParam){
		return grantDao.getGrantLists(loanFlowQueryParam);
	}
	
	/**
	 * 选择退回原因，是否存在有不允许放款的原因
	 * 2016年5月17日
	 * By 朱静越
	 * @param map
	 * @return 提示信息
	 */
	public String isGrantBackMes(Map<String, Object> map) {
		String grantBackResult = null;
		int grantCount = grantDao.selGrantBackMes(map);
		if (grantCount > 0) {
			grantBackResult = "处理中的单子中有失败原因不允许操作放款";
		}
		return grantBackResult;
	}
	
	/**
	 * 线下放款表导出
	 * 2015年12月22日
	 * By 朱静越
	 * @param id
	 * @return
	 */
	public GrantEx getGrantList(String id){
		return grantDao.getGrantList(id);
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
	 * 放款表导出处理
	 * 2017年1月20日
	 * By 朱静越
	 * @param grantList
	 * @param totalAmount
	 * @param ids
	 * @param i
	 * @return
	 */
	public BigDecimal grantItemDao(List<GrantEx> grantList,
			BigDecimal totalAmount, String[] ids, int i) {
		String curAmount;
		String provinceName;
		String cityName;
		GrantEx gse = getGrantList(ids[i]);
		if (!ObjectHelper.isEmpty(gse)
				&& PaymentWay.NET_BANK.getCode().equals(
						gse.getDictLoanWay())) {
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
		    // 合同加盖废章
		    boolean result = grantCAService.caSignCancel(singleParam);
		    if(result){
		    	backBatchReason = URLDecoder.decode(backBatchReason, "UTF-8");
		    	// 修改放款表
		    	LoanGrant loanGrant = new LoanGrant();
		    	loanGrant.setContractCode(singleParam);
		    	loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
		    	loanGrant.preUpdate();
		    	loanGrantDao.updateLoanGrant(loanGrant);
		    	
		    	// 更新合同表的退回原因
		    	Contract contract = new Contract();
		    	contract.setContractCode(singleParam);
		    	contract.setContractBackResult(backBatchReason);
		    	contract.preUpdate();
				contractDao.updateContract(contract);
				//待放款退回到合同审核 修改orderField 用于排序
				Contract contractOrder=contractDao.findByContractCode(singleParam);
				LoanInfo loanInfoOrder=new LoanInfo();
				loanInfoOrder.setApplyId(contractOrder.getApplyId());
				loanInfoOrder.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_RETURN.getCode());
				createOrderFileldService.backContractCheckByGrant(loanInfoOrder);
				// 更新借款状态
		    	LoanInfo loanInfo = updateLoanStatus(contractDao.findByContractCode(singleParam).getLoanCode(),LoanApplyStatus.LOAN_SEND_RETURN.getCode());
		    	//退回到合同审核  添加分单功能
		    	assistService.updateAssistAddAuditOperator(contractDao.findByContractCode(singleParam).getLoanCode());
				// 插入历史
				historyService.saveLoanStatusHis(loanInfo,LoanApplyStatus.LOAN_SEND_RETURN.getName(), GrantCommon.SUCCESS,backBatchReason);
		    }else{
		        throw new ServiceException("合同加盖废章失败");
		    }
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
	public void offLineGrantDeal(GrantEx grantExItem){
		// 更新放款记录表
		LoanGrant loanGrant  = new LoanGrant();
		loanGrant.setContractCode(grantExItem.getContractCode());
		loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED
				.getCode()); // 放款的回执结果
		loanGrant.setGrantBatch(grantExItem.getGrantBatchCode()); // 手动输入的放款批次
		loanGrant.setLendingTime(new Date());
		loanGrant.setDictLoanType(DeductWay.OFFLINE.getCode()); // 放款方式--线下
		loanGrantDao.updateLoanGrant(loanGrant);
		
		// 更新借款状态
		Contract contract = contractDao.findByContractCode(grantExItem.getContractCode());
		LoanInfo loanInfo = updateLoanStatus(contract.getLoanCode(),LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
		
		// 放款成功插入催收服务费信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("applyId", contract.getApplyId());
		map.put("loanFlag", ChannelFlag.CHP.getCode());
		grantInsertUrgeService.urgeServiceInsertDeal(map);
		
		// 插入放款成功历史
		historyService.saveLoanStatusHis(loanInfo,"放款", GrantCommon.SUCCESS,"");
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
	public String returnStatusCheck(List<?> dataList,String grantWay){
		String returnStatus = null;
		String result = null;
		List<GrantEx> grantList = (List<GrantEx>) dataList;
		for (GrantEx grantExItem : grantList) {
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
	 * 线上放款发送划扣:1.更新借款状态为处理中  2.更新放款表 3.添加历史
	 * 2016年5月4日
	 * By 朱静越
	 * @param deductReqItem
	 * @param grantBatch
	 */
	@SuppressWarnings("finally")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String onLineGrant(DeductReq deductReqItem, String grantBatch){
		String failContractCode = null;
		DeResult t = TaskService.addTask(deductReqItem);
		try {
			if (t.getReCode().equals(
					ResultType.ADD_SUCCESS.getCode())) {
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setApplyId(deductReqItem.getBatId());
				loanInfo.setLoanCode(loanGrantDao.selLoanCode(deductReqItem.getBatId()));
				loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_DEALED.getCode());
				loanGrantDao.updateStatus(loanInfo);
				
				LoanGrant loanGrant = new LoanGrant();
				loanGrant.setContractCode(deductReqItem.getRequestId());
				// 设置放款批次
				loanGrant.setGrantBatch(grantBatch);
				loanGrant.setGrantRecepicResult(LoansendResult.LOAN_PROCESS.getCode());
				loanGrant.setLendingTime(new Date());
				loanGrant.setSubmitDeductTime(new Date());
				// 放款方式：线上线下
				loanGrant.setDictLoanType(DeductWay.ONLINE.getCode());
				loanGrantDao.updateLoanGrant(loanGrant);
				
				historyService.saveLoanStatusHis(loanInfo,"线上放款", GrantCommon.SUCCESS,"");
				TaskService.commit(t.getDeductReq());
			} else {
				TaskService.rollBack(t.getDeductReq());
				failContractCode = deductReqItem.getBusinessId();
			}
		} catch (Exception e) {
			logger.error("放款发送划扣失败",e);
			e.printStackTrace();
			failContractCode = deductReqItem.getBusinessId();
			TaskService.rollBack(t.getDeductReq());
			throw new ServiceException("线上放款发送划扣失败");
		}finally{
			return failContractCode;
		}
	}
	
	/**
	 * 导入中金处理
	 * 2017年2月10日
	 * By 朱静越
	 * @param loanGrant 放款参数
	 * @param strLoanCode 借款编码
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updZJExcel(LoanGrant loanGrant,String strLoanCode){
		if (StringUtils.isNotEmpty(strLoanCode)) {
			LoanInfo loanInfo = new LoanInfo();
			loanInfo.setLoanCode(strLoanCode);
			loanInfo.setApplyId(loanGrantDao.selApplyId(loanGrant.getContractCode()));
			loanInfo.setDictLoanStatus(LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
			loanGrantDao.updateStatus(loanInfo);
			historyService.saveLoanStatusHis(loanInfo,"放款", GrantCommon.SUCCESS,"线下导入中金");
		}
		loanGrant.preUpdate();
		loanGrantDao.updateLoanGrant(loanGrant);
		
		Contract contract = contractDao.findByContractCode(loanGrant.getContractCode());
		// 放款成功插入催收服务费信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("applyId", contract.getApplyId());
		map.put("loanFlag", ChannelFlag.CHP.getCode());
		grantInsertUrgeService.urgeServiceInsertDeal(map);
	}
	
	/**
	 * 通联模板1的导入,对list进行遍历，获得合同编号的分组
	 * 2016年5月11日
	 * By 朱静越
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void getInfo(List<?> datalist){
		List<GrantImportTLFirstEx> grantImportTLFirst = (List<GrantImportTLFirstEx>)datalist;
		List<String> contractsList = new ArrayList<String>();
		LoanGrant loanGrant = new LoanGrant();
		String remark = null;
		boolean isUpdLoan = false;
		int success = 0;
		int fail = 0;
		int deal = 0;
		int sum = 0;
		String contractCode = null;
		String[] lendingTimes = new String[datalist.size()];
		String[] grantPch = new String[datalist.size()];
		// 失败原因
		String [] failReason = new String[datalist.size()];
		// 成功金额
		BigDecimal[] tradeAmount = new BigDecimal[datalist.size()];
		// 失败金额
		BigDecimal[] failAmount = new BigDecimal[datalist.size()];
		// 获取合同编号
		for (int i = 0; i < grantImportTLFirst.size()-1; i++) {
			remark = grantImportTLFirst.get(i).getRemark();
            contractCode = remark.split("_")[0];
            if (!contractsList.contains(contractCode)) {
            	contractsList.add(contractCode);
			}
		}
		for (int j = 0; j < contractsList.size(); j++) {
			loanGrant.setContractCode(contractsList.get(j));
			tradeAmount[j] = new BigDecimal(0.00);
			failAmount[j] = new BigDecimal(0.00);
			for (int k = 0; k < grantImportTLFirst.size()-1; k++) {
				remark = grantImportTLFirst.get(k).getRemark();
	            contractCode = remark.split("_")[0];
	            if (contractsList.get(j).equals(contractCode)) {
	            	sum ++;
	            	grantPch[j] = grantImportTLFirst.get(k).getGrantBatchCode();
	            	lendingTimes[j] = grantImportTLFirst.get(k).getFinishTime();
					// 处理状态为成功
					if (GrantUtil.isLentMoneySuccess(grantImportTLFirst.get(k).getDealStatusName())) {
						BigDecimal tradeAmountItem = new BigDecimal(grantImportTLFirst.get(k).getTradeAmount());
						tradeAmount[j] = tradeAmount[j].add(tradeAmountItem.divide(new BigDecimal(100)));
						success ++;
						// 处理状态为失败
					}else{
						BigDecimal failAmountItem = new BigDecimal(grantImportTLFirst.get(k).getTradeAmount());
						failAmount[j] = failAmount[j].add(failAmountItem.divide(new BigDecimal(100)));
						failReason[j] = grantImportTLFirst.get(k).getReason();
						fail++;
					}
					if (ResultConstants.DEAL_ING_DESC.equals(grantImportTLFirst.get(j).getDealStatusName())) {
						deal++;
					}
				}
			}
			// 设置该合同编号单子的页面显示状态,如果没有进行拆分，只有一条数据
            if (sum == 1) {
				if (success > 0) { // 成功
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
					isUpdLoan = true;
				}else if (deal > 0) { // 处理中
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_PROCESS.getCode());
				}else{ // 失败
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				}
			}else{
				if (fail > 0 || deal > 0) { // 失败
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				}else{// 成功
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
					isUpdLoan = true;
				}
			}
            if (isUpdLoan) {
            	Contract contract = contractDao.findByContractCode(contractCode);
            	LoanInfo loanInfo = updateLoanStatus(contract.getLoanCode(),LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
            	loanInfo.setApplyId(contract.getApplyId());
            	historyService.saveLoanStatusHis(loanInfo,"放款", GrantCommon.SUCCESS,"线下导入通联1");
            	// 放款成功插入催收服务费信息
        		Map<String, String> map = new HashMap<String, String>();
        		map.put("applyId", contract.getApplyId());
        		map.put("loanFlag", ChannelFlag.CHP.getCode());
        		grantInsertUrgeService.urgeServiceInsertDeal(map);
			}
            loanGrant.setGrantFailResult(failReason[j]);
			loanGrant.setGrantFailAmount(failAmount[j]);
			loanGrant.setLendingTime(DateUtils.parseDate(lendingTimes[j]));
			loanGrant.setGrantBatch(grantPch[j]);
			loanGrantDao.updateLoanGrant(loanGrant);
		}		
	}
	
	/**
	 * 通联模板2的导入，根据合同编号,通联2导入的数据，金额为分
	 * 2016年5月12日
	 * By 朱静越
	 * @param datalist
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void getInfoTL2(List<?> datalist){
		List<GrantImportTLSecondEx> grantImportTLSecondExs = (List<GrantImportTLSecondEx>)datalist;
		String remark = null;
		int success = 0;
		int fail = 0;
		int sum = 0;
		int deal = 0;
		boolean isUpdLoan = false;
		LoanGrant loanGrant = new LoanGrant();
		String contractCode = null;
		List<String> contractsList = new ArrayList<String>();
		String[] grantPch = new String [datalist.size()];
		// 失败原因
		String [] failReason = new String [datalist.size()];
		// 成功金额
		BigDecimal[] tradeAmount = new BigDecimal[datalist.size()];
		// 失败金额
		BigDecimal[] failAmount = new BigDecimal[datalist.size()];
		// 获取合同编号
		for (int i = 0; i < grantImportTLSecondExs.size()-1; i++) {
			remark = grantImportTLSecondExs.get(i).getRemark();
            contractCode = remark.split("_")[0];
            if (!contractsList.contains(contractCode)) {
            	contractsList.add(contractCode);
			}
		}
		// 处理
		for (int j = 0; j < contractsList.size(); j++) {
			loanGrant.setContractCode(contractsList.get(j));
			tradeAmount[j] = new BigDecimal(0.00);
			failAmount[j] = new BigDecimal(0.00);
			for (int k = 0; k < grantImportTLSecondExs.size()-1; k++) {
				remark = grantImportTLSecondExs.get(k).getRemark();
	            contractCode = remark.split("_")[0];
	            if (contractsList.get(j).equals(contractCode)) {
	            	sum ++;
	            	grantPch[j] = grantImportTLSecondExs.get(k).getGrantBatchCode();
					// 处理状态为成功
					if (GrantUtil.isLentMoneySuccess(grantImportTLSecondExs.get(k).getFeedbackCode())) {
						BigDecimal tradeAmountItem = new BigDecimal(grantImportTLSecondExs.get(k).getGrantAmount());
						tradeAmount[j] = tradeAmount[j].add(tradeAmountItem.divide(new BigDecimal(100)));
						success ++;
						// 处理状态为失败
					}else{
						BigDecimal failAmountItem = new BigDecimal(grantImportTLSecondExs.get(k).getGrantAmount());
						failAmount[j] = failAmount[j].add(failAmountItem.divide(new BigDecimal(100)));
						failReason[j] = grantImportTLSecondExs.get(k).getReason();
						fail++;
					}
					if (ResultConstants.DEAL_ING_DESC.equals(grantImportTLSecondExs.get(k).getReason())) {
						deal++;
					}
				}
			}
			// 设置该合同编号单子的页面显示状态,如果没有进行拆分，只有一条数据
            if (sum == 1) {
				if (success > 0) { // 成功
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
					isUpdLoan = true;
				}else if (deal > 0) { // 处理中
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_PROCESS.getCode());
				}else{ // 失败
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				}
			}else{
				if (fail > 0 || deal > 0) { // 失败
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_FAILED.getCode());
				}else{// 成功
					loanGrant.setGrantRecepicResult(LoansendResult.LOAN_SENDED_SUCCEED.getCode());
					isUpdLoan = true;
				}
			}
            if (isUpdLoan) {
            	Contract contract = contractDao.findByContractCode(contractCode);
            	LoanInfo loanInfo = updateLoanStatus(contract.getLoanCode(),LoanApplyStatus.LOAN_SEND_AUDITY.getCode());
            	loanInfo.setApplyId(contract.getApplyId());
            	historyService.saveLoanStatusHis(loanInfo,"放款", GrantCommon.SUCCESS,"线下导入通联2");
            	// 放款成功插入催收服务费信息
        		Map<String, String> map = new HashMap<String, String>();
        		map.put("applyId", contract.getApplyId());
        		map.put("loanFlag", ChannelFlag.CHP.getCode());
        		grantInsertUrgeService.urgeServiceInsertDeal(map);
			}
            loanGrant.setGrantFailResult(failReason[j]);
			loanGrant.setGrantFailAmount(failAmount[j]);
			loanGrant.setLendingTime(new Date());
			loanGrant.setGrantBatch(grantPch[j]);
			loanGrantDao.updateLoanGrant(loanGrant);
		}
		}
	
	/**
	 * 放款批次的校验
	 * 2016年4月23日
	 * By 朱静越
	 * @param grantList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String grantPchCheck(List<?> dataList,String grantWay){
		String grantPch = null;
		String result = null;
		// 网银
		if (PaymentWay.NET_BANK.getCode().equals(grantWay)) {
			List<GrantEx> grantList = (List<GrantEx>) dataList;
			for(int i = 0;i<grantList.size()-1;i++){
				grantPch = grantList.get(i).getGrantBatchCode();
				if (StringUtils.isEmpty(grantPch)&&StringUtils.isNotEmpty(grantList.get(i).getContractCode())) {
					result = "放款批次不能为空";
					break;
				}
			}
		// 中金
		}else if(PaymentWay.ZHONGJIN.getCode().equals(grantWay)){
			List<GrantImportZJEx> grantPchZJ = (List<GrantImportZJEx>) dataList;
			for (int i = 0; i < grantPchZJ.size()-1;i++) {
				grantPch = grantPchZJ.get(i).getGrantBatchCode();
				if (StringUtils.isEmpty(grantPch)&&StringUtils.isNotEmpty(grantPchZJ.get(i).getRemark())) {
					result = "放款批次不能为空";
					break;
				}
			}
		}
		return result;
	}
	
}
