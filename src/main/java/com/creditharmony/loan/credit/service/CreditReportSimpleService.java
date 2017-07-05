package com.creditharmony.loan.credit.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.out.pbc.CreditCardDetails;
import com.creditharmony.adapter.bean.out.pbc.CreditLoanDetails;
import com.creditharmony.adapter.bean.out.pbc.CreditQueryRecord;
import com.creditharmony.adapter.bean.out.pbc.PbcGetReportOutInfo;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.approve.type.ApproveCheckType;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.CreditReportRisk;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.credit.constants.EnterpriseCreditConstants;
import com.creditharmony.loan.credit.dao.CreditReportDao;
import com.creditharmony.loan.credit.dao.CreditReportDetailedDao;
import com.creditharmony.loan.credit.dao.CreditReportSimpleDao;
import com.creditharmony.loan.credit.entity.CreditCardInfo;
import com.creditharmony.loan.credit.entity.CreditLoanInfo;
import com.creditharmony.loan.credit.entity.CreditReportDetailed;
import com.creditharmony.loan.credit.entity.CreditReportSimple;

/**
 * 简版信用报告Service
 * @Class Name CreditReportSimpleService
 * @author zhanghu
 * @Create In 2016年1月29日
 */
@Service
@Transactional(value="loanTransactionManager",readOnly=true)
public class CreditReportSimpleService extends  CoreManager<CreditReportSimpleDao,CreditReportSimple>{
	
	@Autowired
	private LoanCoborrowerDao loanCoborrowerDao;
	
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	@Autowired
	private CreditCardInfoService creditCardInfoService;
	
	@Autowired
	private CreditLoanInfoService creditLoanInfoService;
	
	@Autowired
	private CreditQueryRecordService creditQueryRecordService;
	
	@Autowired
	private CreditReportDao creditReportDao;
	
	@Autowired
	private CreditReportDetailedDao creditReportDetailedDao;
	
	@Autowired
	private ReconsiderApplyDao reconsiderApplyDao;
	
	/**
     * 根据征信信息对象查询征信信息
     * 2016年2月2日
     * By zhanghu
     * @param record
     * @return 征信信息对象
     */
    public CreditReportSimple selectByCreditReportSimple(CreditReportSimple record) {		
    	return this.dao.selectByCreditReportSimple(record);    	
    }
    
	/**
	 * 根据个人征信简版id更新有值列信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param creditReportSimple 个人征信简版
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public String updateCreditReportSimple(CreditReportSimple creditReportSimple) {		
		CreditReportSimple recond = this.selectByCreditReportSimple(creditReportSimple);
		
		// 初始化默认数据
		if (recond != null) {
			creditReportSimple.preUpdate();
			int resutl = this.dao.updateByPrimaryKeySelective(creditReportSimple);
			if (resutl > 0) {
				CreditReportRisk creditReportRisk = new CreditReportRisk();
				creditReportRisk.setLoanCode(creditReportSimple.getLoanCode());
				creditReportRisk.setrId(creditReportSimple.getrCustomerCoborrowerId());
				creditReportRisk.setDictCustomerType(creditReportSimple.getDictCustomerType());
				creditReportRisk.setRiskCreditVersion("2");
				this.savePersonCreditData(creditReportRisk);
				
				CreditReportDetailed creditReportDetailed = new CreditReportDetailed();
				creditReportDetailed.setLoanCode(creditReportSimple.getLoanCode());
				creditReportDetailed.setDictCustomerType(creditReportSimple.getDictCustomerType());
				creditReportDetailed.setrCustomerCoborrowerId(creditReportSimple.getrCustomerCoborrowerId());
				CreditReportDetailed detailResult = creditReportDetailedDao.getIdByParam(creditReportDetailed);
				if (detailResult != null) {
					detailResult.setDelFlag("1");
					creditReportDetailedDao.updatDelFlag(detailResult);
				}
				return creditReportSimple.getId();
			} else {
				return "";
			}
		} else {
			String id = creditReportSimple.getId();
			creditReportSimple.preInsert();
			creditReportSimple.setId(id);
			int resutl = this.dao.insertCreditReportSimple(creditReportSimple);
			if(resutl > 0){
				
				CreditReportRisk creditReportRisk = new CreditReportRisk();
				creditReportRisk.setLoanCode(creditReportSimple.getLoanCode());
				creditReportRisk.setrId(creditReportSimple.getrCustomerCoborrowerId());
				creditReportRisk.setDictCustomerType(creditReportSimple.getDictCustomerType());
				creditReportRisk.setRiskCreditVersion("2");
				this.savePersonCreditData(creditReportRisk);
				
				CreditReportDetailed creditReportDetailed = new CreditReportDetailed();
				creditReportDetailed.setLoanCode(creditReportSimple.getLoanCode());
				creditReportDetailed.setDictCustomerType(creditReportSimple.getDictCustomerType());
				creditReportDetailed.setrCustomerCoborrowerId(creditReportSimple.getrCustomerCoborrowerId());
				CreditReportDetailed detailResult = creditReportDetailedDao.getIdByParam(creditReportDetailed);
				if (detailResult != null) {
					detailResult.setDelFlag("1");
					creditReportDetailedDao.updatDelFlag(detailResult);
				}
				return creditReportSimple.getId();
			} else {
				return "";
			}
		}
	}
	
	
	/**
	 * 保存/更新征信核查数据
	 * 2016年3月14日
	 * By 李文勇
	 * @return
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public int savePersonCreditData(CreditReportRisk creditReportRisk){
		int result = 0;
		List<CreditReportRisk> riskList = creditReportDao.getPersonCreditReportDetailedByCode(creditReportRisk);
		
		String checkType = "";
		//如果信审类型为null 则设置为""
		for(int t = 0; t<riskList.size(); t++){
			if(StringUtils.isEmpty(riskList.get(t).getDictCheckType())){
				riskList.get(t).setDictCheckType("");
			}
		}
		// 如果存在复议，，则把变量checkType设置为1
		for(int w = 0; w<riskList.size(); w++){
			if(ApproveCheckType.FY_APPROVE_CHECK_TYPE.getCode().equals(riskList.get(w).getDictCheckType())){
				checkType = riskList.get(w).getDictCheckType();
				break;
			}
		}
		// 如果变量checkType为空，，并且有信审初审的值，则把checkType赋值0
		for(int n = 0; n < riskList.size(); n ++){
			if(StringUtils.isEmpty(checkType) && ApproveCheckType.XS_APPROVE_CHECK_TYPE.getCode().equals(riskList.get(n).getDictCheckType())){
				checkType = riskList.get(n).getDictCheckType();
				break;
			}
		}
		
		// 如果大于0，说明已经存在该条数据
		if(riskList != null && riskList.size() > 0){
			for(int i = 0; i < riskList.size(); i ++){
				if (riskList.get(i).getLoanCode().equals(creditReportRisk.getLoanCode())
					&& riskList.get(i).getrId().equals(creditReportRisk.getrId())
					&& riskList.get(i).getDictCustomerType().equals(creditReportRisk.getDictCustomerType())
					&&( checkType.equals(riskList.get(i).getDictCheckType()) ) ){
					creditReportRisk.setId(riskList.get(i).getId());
					creditReportRisk.preUpdate();
					result = creditReportDao.updataById(creditReportRisk);
					break;
				} else {
					continue;
				}
			}
		} else {// 否则说明没有该条数据
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("loanCode",creditReportRisk.getLoanCode());
			ReconsiderApply recon = reconsiderApplyDao.selectByParam(map);
			if(recon != null && StringUtils.isNotEmpty(recon.getId())){
				// 先插入一条信审数据
				creditReportRisk.setDictCheckType(ApproveCheckType.XS_APPROVE_CHECK_TYPE.getCode());
				creditReportRisk.preInsert();
				result = creditReportDao.asyncSaveCreditReportRiskInfo(creditReportRisk);
				// 插入一条复议数据
				creditReportRisk.setDictCheckType(ApproveCheckType.FY_APPROVE_CHECK_TYPE.getCode());
			}else{
				creditReportRisk.setDictCheckType(ApproveCheckType.XS_APPROVE_CHECK_TYPE.getCode());
			}
			creditReportRisk.preInsert();
			result = creditReportDao.asyncSaveCreditReportRiskInfo(creditReportRisk);
		}
		return result;
	}
	
	 /**
     * 简版征信爬虫成功后，更新risk表和详版表
     * 2016年7月12日
     * By 李文勇
     */
	@Transactional(value = "loanTransactionManager", readOnly = false)
    public void correlatSave(CreditReportSimple param){
    	
    	CreditReportRisk creditReportRisk = new CreditReportRisk();
		creditReportRisk.setLoanCode(param.getLoanCode());
		creditReportRisk.setrId(param.getrCustomerCoborrowerId());
		creditReportRisk.setDictCustomerType(param.getDictCustomerType());
		creditReportRisk.setRiskCreditVersion("2");
		this.savePersonCreditData(creditReportRisk);
		
		CreditReportDetailed creditReportDetailed = new CreditReportDetailed();
		creditReportDetailed.setLoanCode(param.getLoanCode());
		creditReportDetailed.setDictCustomerType(param.getDictCustomerType());
		creditReportDetailed.setrCustomerCoborrowerId(param.getrCustomerCoborrowerId());
		CreditReportDetailed detailResult = creditReportDetailedDao.getIdByParam(creditReportDetailed);
		if (detailResult != null) {
			detailResult.setDelFlag("1");
			creditReportDetailedDao.updatDelFlag(detailResult);
		}
    }
	
	/**
	 * 新增个人征信简版信息
	 * 2016年2月3日
	 * By zhanghu
	 * @param creditReportSimple 个人征信简版
	 * @return 执行条数
	 */
	@Transactional(value="loanTransactionManager",readOnly=false)
	public int insertCreditReportSimple(CreditReportSimple creditReportSimple) {
		// 初始化默认数据
		creditReportSimple.preInsert();
		return this.dao.insertCreditReportSimple(creditReportSimple);
	}
	
	/**
	 *通过LoanCode查询共借人 
	 *@author zhanghu
     *@Create In 2015年12月25日
	 *@param loanCode
	 *@return List<LoanCoborrower>
	 */
	public List<LoanCoborrower> selectByLoanCode(String loanCode) {
		return this.loanCoborrowerDao.selectByLoanCode(loanCode);
	}
	
    /**
     * 2015年12月25日
     * @author zhanghao
     * @param applyId
     * @return LoanCustomer
     */
    public LoanCustomer selectByApplyId(String applyId) {
    	return this.loanCustomerDao.selectByApplyId(applyId);
    }    
    
    /**
     * 初始化征信信息
     * 2016年2月2日
     * By zhanghu
     * @param record
     * @return 征信信息对象
     */
    @Transactional(value="loanTransactionManager",readOnly=false)
    public CreditReportSimple initCreditReportSimple(CreditReportSimple record) {    	
    	// 查询征信信息
		CreditReportSimple creditReportSimpleOld = this.selectByCreditReportSimple(record);

		// 判断有无征信信息
		if (null == creditReportSimpleOld) {
			//共借人
	    	record.setDictCustomerType(EnterpriseCreditConstants.LOAN_MAN_FLAG_2);
	    	//主借人
	    	if (StringUtils.isNotEmpty(record.getrCustomerCoborrowerId()) &&
	    			record.getrCustomerCoborrowerId().equals(record.getCustomerId())) {
	    		record.setDictCustomerType(EnterpriseCreditConstants.LOAN_MAN_FLAG_1);
	    	}
			//征信信息初始化
			this.insertCreditReportSimple(record);
			return record;
		}     	
    	return creditReportSimpleOld;    	
    }
    
    /**
     * 初始化征信信息
     * 2016年2月2日
     * By zhanghu
     * @param record
     * @return 征信信息对象
     */
    @Transactional(value="loanTransactionManager",readOnly=false)
    public CreditReportSimple simpInit(CreditReportSimple record) {    	
    	// 查询征信信息
		CreditReportSimple creditReportSimpleOld = this.selectByCreditReportSimple(record);
    	return creditReportSimpleOld;
    }  
    
    /**
     * 初始化征信信息-爬虫
     * 2016年2月2日
     * By zhanghu
     * @param pbcGetReportOutInfo
     * @param creditReportSimple
     */
    @Transactional(value="loanTransactionManager",readOnly=false)
	public void playReport(PbcGetReportOutInfo pbcGetReportOutInfo, CreditReportSimple creditReportSimple) {
    	
    	String creditReportSimpleId = creditReportSimple.getId();    	
    	// 基本信息
    	CreditReportSimple creditReportSimpleNew = new CreditReportSimple();
    	creditReportSimpleNew.setId(creditReportSimpleId);
    	creditReportSimpleNew.setQueryTime(new Date());
    	creditReportSimpleNew.setHtmlUrl(pbcGetReportOutInfo.getHtmlUrl());//静态网页
    	creditReportSimpleNew.setLoanCode(creditReportSimple.getLoanCode());
    	creditReportSimpleNew.setDictCustomerType(creditReportSimple.getDictCustomerType());
    	creditReportSimpleNew.setrCustomerCoborrowerId(creditReportSimple.getrCustomerCoborrowerId());
    	creditReportSimpleNew.setCreditSource(creditReportSimple.getCreditSource());
    	// 从爬虫信息中获取婚姻状况与姓名
    	creditReportSimpleNew.setMarryStatus(getDicCode("jk_marriage", pbcGetReportOutInfo.getMarriageStatus()));
    	creditReportSimpleNew.setName(pbcGetReportOutInfo.getRealName());
    	
    	this.updateCreditReportSimple(creditReportSimpleNew);    	
    	// 清空原有信用卡数据
		creditCardInfoService.deleteByRelationId(creditReportSimpleId);
		// 清空原有贷款数据
		creditLoanInfoService.deleteByRelationId(creditReportSimpleId);
		// 清空原有查询记录数据
		creditQueryRecordService.deleteByRelationId(creditReportSimpleId);
    	
    	// 信用卡信息list
		try {
	    	List<CreditCardDetails> creditCardInfoList = pbcGetReportOutInfo.getCreditCardInfoList();
			if (ArrayHelper.isNotEmpty(creditCardInfoList)) {				
				for (CreditCardDetails creditCardDetails : creditCardInfoList) {
					CreditCardInfo creditCardInfoNew = new CreditCardInfo();
					
					//数据转换
					creditCardInfoNew.setRelationId(creditReportSimpleId);					
					//账户状态
					creditCardInfoNew.setAccountStatus(getDicCode("jk_credit_cardinfo_accountstatus", creditCardDetails.getAccountStatus()));
					//币种
					creditCardInfoNew.setCurrency(getDicCode("jk_credit_currency", creditCardDetails.getCurrency()));
					// 如果币种未匹配，将币种设置为其他
					if (StringUtils.isEmpty(creditCardInfoNew.getCurrency())) {
						creditCardInfoNew.setCurrency(EnterpriseCreditConstants.CARD_CURRENCY_OTHER);
					}
					
					// 是否发生过逾期
					if (!StringUtils.isNotEmpty(creditCardDetails.getIsOverdue())) {
						creditCardDetails.setIsOverdue(EnterpriseCreditConstants.ISOVERDUE_1);
					}
					creditCardInfoNew.setIsOverdue(getDicCode("jk_credit_isoverdue", creditCardDetails.getIsOverdue()));
					//发放日期
					creditCardInfoNew.setIssueDay(DateUtils.parseDate((Object)(creditCardDetails.getIssueDay())));
					//截至年月
					creditCardInfoNew.setAbortDay(DateUtils.parseDate((Object)(creditCardDetails.getAbortDay())));
					//额度
					creditCardInfoNew.setLimit(getBigDecimal(creditCardDetails.getLimit()));
					//已使用额度
					creditCardInfoNew.setUsedLimit(getBigDecimal(creditCardDetails.getUsedLimit()));
					//逾期金额
					creditCardInfoNew.setOverdueAmount(getBigDecimal(creditCardDetails.getOverdueAmount()));
					//最近5年逾期次数
					creditCardInfoNew.setOverdueNo(getInteger(creditCardDetails.getOverdueNo()));
					//最近五年90天以上逾期次数
					creditCardInfoNew.setOverdueForNo(getInteger(creditCardDetails.getOverdueForNo()));
					//销户年月
					creditCardInfoNew.setCancellationDay(DateUtils.parseDate((Object)(creditCardDetails.getCancellationDay())));
					// 销户年月不为空，且账户状态为逾期，将账户状态更新为销户
					if (creditCardInfoNew.getCancellationDay() != null
							&& creditCardInfoNew.getAccountStatus().equals(EnterpriseCreditConstants.CARD_ACCOUNT_STATUS_OVERDUE)) {						
						creditCardInfoNew.setAccountStatus(EnterpriseCreditConstants.CARD_ACCOUNT_STATUS_CANCEL);					
					}								
					creditCardInfoService.insertCreditCardInfo(creditCardInfoNew);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 贷款明细信息list
		try {
			List<CreditLoanDetails> creditLoanInfoList = pbcGetReportOutInfo.getCreditLoanInfoList();
			if (ArrayHelper.isNotEmpty(creditLoanInfoList)) {				
				for (CreditLoanDetails creditLoanDetails : creditLoanInfoList) {
					CreditLoanInfo creditLoanInfoNew = new CreditLoanInfo();					
					//数据转换
					creditLoanInfoNew.setRelationId(creditReportSimpleId);
					//账户状态
					creditLoanInfoNew.setAccountStatus(getDicCode("jk_credit_loaninfo_accountstatus", creditLoanDetails.getAccountStatus()));
					//贷款种类
					creditLoanInfoNew.setCurrency(getDicCode("jk_credit_loan_type_flag", creditLoanDetails.getCurrency()));
					//是否发生过逾期
					if (!StringUtils.isNotEmpty(creditLoanDetails.getIsOverdue())) {
						creditLoanDetails.setIsOverdue(EnterpriseCreditConstants.ISOVERDUE_1);
					}
					creditLoanInfoNew.setIsOverdue(getDicCode("jk_credit_isoverdue", creditLoanDetails.getIsOverdue()));
					//发放日期
					creditLoanInfoNew.setIssueDay(DateUtils.parseDate((Object)(creditLoanDetails.getIssueDay())));
					//截至年月
					creditLoanInfoNew.setAbortDay(DateUtils.parseDate((Object)(creditLoanDetails.getAbortDay())));
					//截至年月
					creditLoanInfoNew.setActualDay(DateUtils.parseDate((Object)(creditLoanDetails.getActualDay())));
					//贷款合同金额
					creditLoanInfoNew.setConteactAmount(getBigDecimal(creditLoanDetails.getConteactAmount()));
					//贷款余额
					creditLoanInfoNew.setLoanBalance(getBigDecimal(creditLoanDetails.getLoanBalance()));
					//逾期金额
					creditLoanInfoNew.setOverdueAmount(getBigDecimal(creditLoanDetails.getOverdueAmount()));
					//最近5年逾期次数
					creditLoanInfoNew.setOverdueNo(getInteger(creditLoanDetails.getOverdueNo()));
					//最近五年90天以上逾期次数
					creditLoanInfoNew.setOverdueForNo(getInteger(creditLoanDetails.getOverdueForNo()));
					//结清年月
					creditLoanInfoNew.setSettleDay(DateUtils.parseDate((Object)(creditLoanDetails.getSettleDay())));
					// 结清年月不为空，且账户状态为逾期，将账户状态更新为结清
					if (creditLoanInfoNew.getSettleDay() != null
							&& creditLoanInfoNew.getAccountStatus().equals(EnterpriseCreditConstants.LOAN_ACCOUNT_STATUS_OVERDUE)) {						
						creditLoanInfoNew.setAccountStatus(EnterpriseCreditConstants.LOAN_ACCOUNT_STATUS_CLEAR);						
					}
					creditLoanInfoService.insertCreditLoanInfo(creditLoanInfoNew);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		// 查询信息list
		try {
			List<CreditQueryRecord> creditQueryRecordList = pbcGetReportOutInfo.getCreditQueryRecordList();
			if (ArrayHelper.isNotEmpty(creditQueryRecordList)) {				
				for (CreditQueryRecord creditQueryRecord : creditQueryRecordList) {
					com.creditharmony.loan.credit.entity.CreditQueryRecord creditQueryRecordNew = new com.creditharmony.loan.credit.entity.CreditQueryRecord();					
					//数据转换
					creditQueryRecordNew.setRelationId(creditReportSimpleId);
					//发放日期
					creditQueryRecordNew.setQueryDay(DateUtils.parseDate((Object)(creditQueryRecord.getQueryDate())));
					String queryCause = StringUtils.isNotEmpty(creditQueryRecord.getQueryCause()) 
							? creditQueryRecord.getQueryCause().replace("（", "(").replace("）", ")")
							: "";
					//查询原因
					creditQueryRecordNew.setQueryType(getDicCode("jk_credit_queryrecord_querytype", queryCause));

					creditQueryRecordService.insertCreditQueryRecord(creditQueryRecordNew);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    	
	}

    /**
     * 获取数据字典code-爬虫
     * 2016年2月2日
     * By zhanghu
     * @param type
     * @param str
     * @return String 
     */
    private String getDicCode(String type, String str) {
    	// 空直接返回
		if (!StringUtils.isNotEmpty(str)) {
			return "";
		}
    	// 获取数据字典list
    	List<Dict> dictList = DictCache.getInstance().getListByType(type);
    	if (ArrayHelper.isNotEmpty(dictList)) {
    		for (Dict dict : dictList) {    			
    			if (dict.getLabel().equals(str)) {
    				return dict.getValue();
    			}
    		}    		
    	}
    	
    	return "";
    }
    
    /**
     * 获取BigDecimal
     * 2016年2月2日
     * By zhanghu
     * @param type
     * @param str
     * @return String 
     */
    private BigDecimal getBigDecimal(String str) {
    	// 空返回0
    	if (!StringUtils.isNotEmpty(str)) {
    		return new BigDecimal(0);
    	}
    	
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("str:" + str);
		}
		
		return new BigDecimal(0);
    }
    
    /**
     * 获取Integer
     * 2016年2月2日
     * By zhanghu
     * @param str
     * @return Integer 
     */
    private Integer getInteger(String str) {
    	// 空直接返回
    	if (!StringUtils.isNotEmpty(str)) {
    		return 0;
    	}
    	
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("str:" + str);
		}
		
		return 0;
    }    
    
}
