package com.creditharmony.loan.car.carContract.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanResult;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.loan.car.carContract.event.CarWorkFlowCommonService;
import com.creditharmony.loan.car.carContract.ex.CarFirstDeferEx;
import com.creditharmony.loan.car.carContract.view.CarCheckRateBusinessView;
import com.creditharmony.loan.car.carExtend.ex.CarExportCustomerDataExColumn;
import com.creditharmony.loan.car.common.consts.CarLoanProductType;
import com.creditharmony.loan.car.common.dao.CarAuditResultDao;
import com.creditharmony.loan.car.common.dao.CarCheckRateDao;
import com.creditharmony.loan.car.common.dao.CarContractDao;
import com.creditharmony.loan.car.common.dao.CarCustomerBankInfoDao;
import com.creditharmony.loan.car.common.dao.CarCustomerConsultationDao;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.util.Arith;
import com.creditharmony.loan.car.common.util.CarCommonUtil;
import com.creditharmony.loan.common.consts.NumberManager;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.dao.NumberMasterDao;
import com.creditharmony.loan.common.entity.NumberMaster;
import com.creditharmony.loan.common.utils.IdentifierRule;

/**
 * 车借--合同
 * @Class Name CarContractService
 * @author 申诗阔
 * @Create In 2016年2月18日
 */
@Service
public class CarContractService extends CarWorkFlowCommonService {
   
	@Autowired
	private CarContractDao carContractDao;
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
	@Autowired
	private CarAuditResultDao carAuditResultDao;
	@Autowired
	private NumberMasterDao numberMasterDao;
	@Autowired
	private CarCheckRateDao carCheckRateDao;
	@Autowired
	private CityInfoDao cityInfoDao;
	@Autowired
	private CarCustomerBankInfoDao carCustomerBankInfoDao;
	@Autowired
	private CarCustomerConsultationDao carCustomerConsultationDao;
	
	/**
	 * 根据借款编号获取合同
	 * 2016年2月18日
	 * By 申诗阔
	 * @param loanCode
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarContract getByLoanCode(String loanCode) {
		return carContractDao.selectByLoanCode(loanCode);
	}

	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String generalContractNo(String applyId) {
		CarLoanInfo carLoanInfo = carLoanInfoDao.selectByApplyId(applyId); // 通过aplyId获取借款信息
		Org org = OrgCache.getInstance().get(carLoanInfo.getStoreCode()); // 通过借款信息表中storeCode（目前存储的是门店id）,来获取门店
		CarContract carContract = carContractDao.selectByLoanCode(carLoanInfo.getLoanCode());
		String contractCode = null;
		if (carContract == null) { // 合同不存在
			// 获取最终汇诚终审通过（含附条件通过）的记录，用于获取审批金额，期限等
			String loanCode = carLoanInfo.getLoanCode();
			CarAuditResult carAuditResult = getThroughAuditResult(loanCode);
			
			NumberMaster numberMaster = new NumberMaster();
			
			numberMaster.setDealDate(DateUtils.getYear());
			numberMaster.setDealCyc(NumberManager.UPDATE_CYC_YEAR);
			numberMaster.setEffective(NumberManager.EFFECTIVE);
			
			String carLoanCode = org.getCarLoanCode();
			String cityId = org.getCityId();
			String cityCode = cityInfoDao.get(cityId).getCityCarCode();
			if (carAuditResult.getProductTypeName().equals(CarLoanProductType.CAR_LOAN_GPS.getName())) {
				numberMaster.setDealPart(CarLoanProductType.CAR_LOAN_GPS.getCode() + org.getId());
			} else if (carAuditResult.getProductTypeName().equals(CarLoanProductType.CAR_LOAN_YJ.getName())) {
				numberMaster.setDealPart(CarLoanProductType.CAR_LOAN_YJ.getCode() + org.getId());
			} else if (carAuditResult.getProductTypeName().equals(CarLoanProductType.CAR_LOAN_ZY.getName())) {
				numberMaster.setDealPart(CarLoanProductType.CAR_LOAN_ZY.getCode() + org.getId());
			}
			// 通过一些属性获取此记录的count值，要么为0，要么为数据库存有的值，并同时把数值加1入库
			int count = getNumberMasterRecord(numberMaster);
			
			String proShortName = "";
			if (!StringUtils.isEmpty(org.getProvinceId())) {
				proShortName = cityInfoDao.get(org.getProvinceId()).getShortName();
			}
			contractCode = IdentifierRule.generalFullContract(carAuditResult.getProductTypeName(), proShortName, cityCode, carLoanCode, count);
			
			CarContract car = new CarContract();
			car.setAuditAmount(carAuditResult.getAuditAmount());
			car.setContractAmount(carAuditResult.getAuditAmount());
			car.setContractCode(contractCode);
			int auditMonths = Integer.parseInt(carAuditResult.getDictAuditMonths());
			if (auditMonths == 30 || auditMonths == 90) {
				car.setDictRepayMethod("1"); // 定期付息,到期还本
			} else {
				car.setDictRepayMethod("0"); // 每期还本付息
			}
			if (auditMonths == 30) {
				car.setDictFeeMethod("1"); // 一次支付
			} else {
				car.setDictFeeMethod("0"); // 按月支付
			}
			car.setContractMonths(auditMonths);
			car.setProductType(carAuditResult.getDictProductType());
			car.setGrossRate(carAuditResult.getGrossRate());
			car.preInsert();
			car.setLoanCode(loanCode);
			car.setPaperLessFlag(YESNO.YES.getCode());
			car.setContractVersion(CarCommonUtil.getVersionByLoanCode(loanCode));
			carContractDao.insert(car);
		} else {
			contractCode = carContract.getContractCode();
			CarAuditResult carAuditResult = getThroughAuditResult(carContract.getLoanCode());
			carContract.setAuditAmount(carAuditResult.getAuditAmount());
			carContract.setContractAmount(carAuditResult.getAuditAmount());
			int auditMonths = Integer.parseInt(carAuditResult.getDictAuditMonths());
			if (auditMonths == 30 || auditMonths == 90) {
				carContract.setDictRepayMethod("1"); // 定期付息,到期还本
			} else {
				carContract.setDictRepayMethod("0"); // 每期等额本息
			}
			if (auditMonths == 30) {
				carContract.setDictFeeMethod("1"); // 一次支付
			} else {
				carContract.setDictFeeMethod("0"); // 按月支付
			}
			carContract.setContractMonths(auditMonths);
			carContract.setProductType(carAuditResult.getDictProductType());
			carContract.setGrossRate(carAuditResult.getGrossRate());
			//carContract.setContractVersion(CarCommonUtil.getVersionByLoanCode(carContract.getLoanCode()));
			carContract.preUpdate();
			carContractDao.update(carContract);
		}
		return contractCode;
	}

	private int getNumberMasterRecord(NumberMaster numbaerMaster) {
		NumberMaster numberData = numberMasterDao.get(numbaerMaster);
		int count = 0;
		if (numberData != null) {
			count = numberData.getSerialNo() + NumberManager.STEP;
			numberData.setSerialNo(count);
			numberMasterDao.update(numberData);
		} else {
			numbaerMaster.preInsert();
			numbaerMaster.setSerialNo(NumberManager.START);
			numberMasterDao.insert(numbaerMaster);
		}
		return count;
	}
	
	/**
	 * 根据查询条件获得首次展期列表
	 * 2016年3月4日
	 * By 甘泉
	 * @param CarFirstDeferEx
	 * @return CarFirstDeferEx
	 */
	public Page<CarFirstDeferEx> selectDefer(Page<CarFirstDeferEx> page,CarFirstDeferEx carFirstDeferEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		PageList<CarFirstDeferEx> pageList = (PageList<CarFirstDeferEx>) carContractDao.
				selectDefer(pageBounds,carFirstDeferEx);
		PageUtil.convertPage(pageList, page);
        return page;
	}
	
	private CarAuditResult getThroughAuditResult(String loanCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanCode", loanCode);
		map.put("checkType", CarLoanSteps.FINAL_AUDIT.getCode());
		List<String> codes = new ArrayList<String>();
		codes.add(CarLoanResult.THROUGH.getCode());
		codes.add(CarLoanResult.CONDITIONAL_THROUGH.getCode());
		map.put("throughCodes", codes);
		return carAuditResultDao.getLastThroughRecord(map);
	}
	
	/**
	 * 
	 * 2016年3月9日
	 * By 申诗阔
	 * @param carLoanCode 车借loanCode
	 * @param extendLoanCode 展期loanCode
	 * @param flag 是否生成合同后，插入数据库
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getExtendContractNo(String carLoanCode, String extendLoanCode, String flag) {
		
		CarContract carContract = carContractDao.selectByLoanCode(extendLoanCode); // 判定是否已经生成合同编号
		String contractCode = null;
		if (carContract == null) { // 合同不存在，则生成合同编号
			Map<String, String> map = new HashMap<String, String>();
			map.put("carLoanCode", carLoanCode);
			map.put("extendLoanCode", extendLoanCode);
			contractCode = carContractDao.getExtendContractCode(map);
			if (YESNO.YES.getCode().equals(flag)) { // 生成合同编号后，插入数据库一条记录
				CarAuditResult carAuditResult = getThroughAuditResult(extendLoanCode);
				try {
					CarContract car = new CarContract();
					car.setAuditAmount(carAuditResult.getAuditAmount());
					car.setContractAmount(carAuditResult.getAuditAmount());
					car.setContractCode(contractCode);
					car.setContractMonths(Integer.parseInt(carAuditResult.getDictAuditMonths()));
					car.setProductType(carAuditResult.getDictProductType());
					car.setGrossRate(carAuditResult.getGrossRate());
					car.preInsert();
					CarContract carOld = carContractDao.calculateLastContractAmount(extendLoanCode);
					Double subAmount = Arith.sub(carOld.getContractAmount().doubleValue(), carAuditResult.getAuditAmount().doubleValue());
					car.setDerate(new BigDecimal(subAmount));
					car.setLoanCode(extendLoanCode);
					car.setContractVersion(CarCommonUtil.getExtendVersionByLoanCode(carLoanCode,extendLoanCode));
					car.setPaperLessFlag(YESNO.YES.getCode());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
					Date currDate = sdf.parse(sdf.format(carOld.getContractEndDay()));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(currDate);
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					car.setContractFactDay(calendar.getTime());
					calendar.setTime(currDate);
					calendar.add(Calendar.DAY_OF_MONTH, 30);
					car.setContractEndDay(calendar.getTime());
					carContractDao.insert(car);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
			
		} else {
			contractCode = carContract.getContractCode();
			if("3".equals(flag)){
				return contractCode;
			}
			CarAuditResult carAuditResult = getThroughAuditResult(carContract.getLoanCode());
			carContract.setAuditAmount(carAuditResult.getAuditAmount());
			carContract.setContractAmount(carAuditResult.getAuditAmount());
			int auditMonths = Integer.parseInt(carAuditResult.getDictAuditMonths());
			carContract.setContractMonths(auditMonths);
			carContract.setProductType(carAuditResult.getDictProductType());
			carContract.setGrossRate(carAuditResult.getGrossRate());
			carContract.preUpdate();
			carContractDao.update(carContract);
		}
		return contractCode;
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<CarContract> getExtendContractByLoanCode(String loanCode) {
		return carContractDao.getExtendContractByLoanCode(loanCode);
	}
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<CarContract> getExtendByContractCode(String contractCode) {
		return carContractDao.getExtendByContractCode(contractCode);
	}
	
	/**
	 * 
	 * 2016年2月16日
	 * By 申诗阔
	 * @param workItem
	 */
	public void waitHandle(WorkItemView workItem) {
		CarCheckRateBusinessView flowView = (CarCheckRateBusinessView) workItem.getBv();
		String stepName = workItem.getStepName();
		flowView.setOperResultName(CarLoanOperateResult.CONTRACT_MAKE_DEFEATED.getCode());
		carContractHandle(flowView, stepName);
	}
	
	/**
	 * 
	 * 2016年2月16日
	 * By 申诗阔
	 * @param carContract
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updateByLoanCode(CarContract carContract) {
		return carContractDao.update(carContract);
	}
	/**
	 * 
	 * 2016年4月5日
	 * By 申诗阔
	 * @param carContract
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int insert(CarContract carContract) {
		return carContractDao.insert(carContract);
	}
	
	/**
	 * 通过展期loancode得到原车借合同总费率
	 * 2016年5月8日
	 * By 申诗阔
	 * @param loanCode
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Double selectOriginalGrossRate(String loanCode) {
		return carContractDao.selectOriginalGrossRate(loanCode);
	}
	
	/**
	 * 通过合同编号获取合同信息
	 * 2016年5月11日
	 * By 申诗阔
	 * @param contractCode
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarContract selectByContractCode(String contractCode) {
		return carContractDao.selectByContractCode(contractCode);
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<CarExportCustomerDataExColumn> getContractCustomList(List<String> contractCodeList){
		return carContractDao.getContractCustomList(contractCodeList);
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarContract selectExByLoanCode(String loanCode){
		return carContractDao.selectExByLoanCode(loanCode);
	}
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public int selectExtendNumByContractCode(String loanCode){
		return carContractDao.selectExtendNumByContractCode(loanCode);
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarExportCustomerDataExColumn getContractCustomColumnByContractCode(String contractCode) {
		return carContractDao.getContractCustomColumnByContractCode(contractCode);
	}
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void carSigningCheckHandle(CarCustomerBankInfo carCustomerBankInfo,CarContract carContract) {
		carCustomerBankInfoDao.update(carCustomerBankInfo);
		carContractDao.update(carContract);
	}
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Double getContractAmountByLoanCode(String loanCode) {
		return carContractDao.getContractAmountByLoanCode(loanCode);
	}
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Integer getExtendCountByLoanCode(String loanCode) {
		return carContractDao.getExtendCountByLoanCode(loanCode);
	}
}
