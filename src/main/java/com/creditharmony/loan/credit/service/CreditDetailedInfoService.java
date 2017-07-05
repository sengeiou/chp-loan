package com.creditharmony.loan.credit.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.core.approve.type.ApproveCheckType;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.CreditReportRisk;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.credit.dao.CreditOccupationInfoDao;
import com.creditharmony.loan.credit.dao.CreditReportDao;
import com.creditharmony.loan.credit.dao.CreditReportDetailedDao;
import com.creditharmony.loan.credit.dao.CreditReportSimpleDao;
import com.creditharmony.loan.credit.dao.CreditliveInfoDao;
import com.creditharmony.loan.credit.entity.CreditLiveInfo;
import com.creditharmony.loan.credit.entity.CreditOccupationInfo;
import com.creditharmony.loan.credit.entity.CreditReportDetailed;
import com.creditharmony.loan.credit.entity.CreditReportSimple;
import com.creditharmony.loan.credit.entity.ex.DetailedParamEx;

/**
 * 个人征信详版
 * @Class Name CreditDetailedInfoService
 * @author 李文勇
 * @Create In 2016年2月18日
 */
@Service
@Transactional(value="loanTransactionManager",readOnly=true)
public class CreditDetailedInfoService extends CoreManager<CreditReportDetailedDao,CreditReportDetailed>{

	@Autowired
	private CreditReportDetailedDao creditReportDetailedDao;
	@Autowired
	private CreditliveInfoDao creditliveInfoDao;
	@Autowired
	private CreditOccupationInfoDao creditOccupationInfoDao;
	@Autowired
	private CreditReportDao creditReportDao;
	@Autowired
	CreditReportSimpleDao creditReportSimpleDao;
	@Autowired
	LoanCoborrowerDao loanCoborrowerDao;
	@Autowired
	LoanCustomerDao loanCustomerDao;
	@Autowired
	private ReconsiderApplyDao reconsiderApplyDao;
	
	/**
	 * 保存数据
	 * 2016年2月18日
	 * By 李文勇
	 * @param param
	 * @return none
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void saveData(DetailedParamEx param) throws IllegalAccessException, InvocationTargetException{
		if(param != null){
			String detailID = "";
			DetailedParamEx detail = new DetailedParamEx();
			detail.setLoanCode(param.getLoanCode());
			detail.setrCustomerCoborrowerId(param.getrCustomerCoborrowerId());
			detail.setDictCustomerType(param.getDictCustomerType());
			
			CreditReportDetailed detailInfo = creditReportDetailedDao.getIdByParam(detail);
			if(detailInfo != null &&  StringUtils.isNotEmpty(detailInfo.getId())){
				param.setId(detailInfo.getId());
			}
			// 根据id判断是否该数据已存在数据库
			if(StringUtils.isNotEmpty(param.getId())){// 更新数据
				detailID = param.getId();
				CreditReportDetailed creditReportDetailed = new CreditReportDetailed();
				BeanUtils.copyProperties(creditReportDetailed,param);
				creditReportDetailed.preUpdate();
				creditReportDetailedDao.updataById(creditReportDetailed);
				//更新risk表
				CreditReportRisk creditReportRisk = new CreditReportRisk();
				creditReportRisk.setLoanCode(param.getLoanCode());
				creditReportRisk.setrId(param.getrCustomerCoborrowerId());
				creditReportRisk.setDictCustomerType(param.getDictCustomerType());
				creditReportRisk.setRiskCreditVersion("1");
				this.savePersonCreditData(creditReportRisk);
				// 获取简版数据
				CreditReportSimple creditReportSimple = new CreditReportSimple();
				creditReportSimple.setLoanCode(param.getLoanCode());
				creditReportSimple.setDictCustomerType(param.getDictCustomerType());
				creditReportSimple.setrCustomerCoborrowerId(param.getrCustomerCoborrowerId());
				CreditReportSimple simpeResult = creditReportSimpleDao.selectByCreditReportSimple(creditReportSimple);
				if(simpeResult != null){
					// 简版设置为逻辑删除
					simpeResult.setDelFlag("1");
					creditReportSimpleDao.updatDelFlag(simpeResult);
				}
				
			}else{// ID为空，说明为第一次保存用insert
				CreditReportDetailed creditReportDetailed = new CreditReportDetailed();
				BeanUtils.copyProperties(creditReportDetailed,param);
				creditReportDetailed.setIsNewRecord(false);
				creditReportDetailed.preInsert();
				detailID = creditReportDetailed.getId();
				creditReportDetailedDao.saveData(creditReportDetailed);
				
				CreditReportRisk creditReportRisk = new CreditReportRisk();
				creditReportRisk.setLoanCode(param.getLoanCode());
				creditReportRisk.setrId(param.getrCustomerCoborrowerId());
				creditReportRisk.setDictCustomerType(param.getDictCustomerType());
				creditReportRisk.setRiskCreditVersion("1");
				this.savePersonCreditData(creditReportRisk);
				// 获取简版数据
				CreditReportSimple creditReportSimple = new CreditReportSimple();
				creditReportSimple.setLoanCode(param.getLoanCode());
				creditReportSimple.setDictCustomerType(param.getDictCustomerType());
				creditReportSimple.setrCustomerCoborrowerId(param.getrCustomerCoborrowerId());
				CreditReportSimple simpeResult = creditReportSimpleDao.selectByCreditReportSimple(creditReportSimple);
				if(simpeResult != null){
					// 简版设置为逻辑删除
					simpeResult.setDelFlag("1");
					creditReportSimpleDao.updatDelFlag(simpeResult);
				}
			}
			// 居住信息
			if(param.getLiveList() != null && param.getLiveList().size() > 0 && StringUtils.isNotEmpty(detailID)){
				List<CreditLiveInfo> liveList = param.getLiveList();
				for( int i = 0; i < liveList.size(); i++ ){
					if( StringUtils.isNotEmpty(liveList.get(i).getId()) ){// 该数据已存在数据库，进行更新
						CreditLiveInfo creditLiveInfo = liveList.get(i);
						creditLiveInfo.preUpdate();
						creditliveInfoDao.updataById(creditLiveInfo);
					}else{// 该数据未存在数据库，进行添加保存
						CreditLiveInfo creditLiveInfo = liveList.get(i);
						creditLiveInfo.setIsNewRecord(false);
						creditLiveInfo.setRelationId(detailID);// 居住信息关联ID
						creditLiveInfo.preInsert();
						creditliveInfoDao.saveData(creditLiveInfo);
					}
				}
			}
			// 职业信息
			if(param.getOccupationList() != null && param.getOccupationList().size() > 0 && StringUtils.isNotEmpty(detailID)){
				List<CreditOccupationInfo> occupationList = param.getOccupationList();
				for( int w = 0; w < occupationList.size(); w++ ){
					if(StringUtils.isNotEmpty(occupationList.get(w).getId())){// 该数据已存在数据库，进行更新
						CreditOccupationInfo creditOccupationInfo = occupationList.get(w);
						creditOccupationInfo.preUpdate();
						creditOccupationInfoDao.updataById(creditOccupationInfo);
					}else{// 该数据未存在数据库，进行添加保存
						CreditOccupationInfo creditOccupationInfo = occupationList.get(w);
						creditOccupationInfo.setIsNewRecord(false);
						creditOccupationInfo.setRelationId(detailID);
						creditOccupationInfo.preInsert();
						creditOccupationInfoDao.saveData(creditOccupationInfo);
					}
				}
			}
		}
	}
	
	/**
	 * 查询数据（个人征信详版，居住信息，职业信息）
	 * 2016年2月18日
	 * By 李文勇
	 * @param param
	 * @return DetailedParamEx
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public DetailedParamEx showData(CreditReportDetailed param) throws IllegalAccessException, InvocationTargetException{
		
		DetailedParamEx detailedParamEx = new DetailedParamEx();
		CreditReportDetailed detailInfo = creditReportDetailedDao.getIdByParam(param);
		
		if(detailInfo != null && StringUtils.isNotEmpty(detailInfo.getId())){
			BeanUtils.copyProperties(detailedParamEx,detailInfo);// 个人基本信息
			// 居住信息
			List<CreditLiveInfo> resultLiveList = creditliveInfoDao.getByParam(detailInfo.getId());
			if(resultLiveList != null && resultLiveList.size() >0){
				detailedParamEx.setLiveList(resultLiveList);
			}
			// 职位信息
			List<CreditOccupationInfo> resultOccList = creditOccupationInfoDao.getByParam(detailInfo.getId());
			if(resultOccList != null && resultOccList.size() >0){
				detailedParamEx.setOccupationList(resultOccList);
			}
		}
		return detailedParamEx;
	}
	
	/**
	 * 删除居住信息
	 * 2016年2月19日
	 * By 李文勇
	 * @param param
	 * @return 操作成功数
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public int removeReportHouse(CreditLiveInfo param){
		int result = creditliveInfoDao.deleteData(param);
		return result;
	}
	
	/**
	 * 删除职位信息
	 * 2016年2月19日
	 * By 李文勇
	 * @param param
	 * @return 操作成功数
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public int removeReportWork(CreditOccupationInfo param){
		int result = creditOccupationInfoDao.deleteData(param);
		return result;
	}
	
	/**
	 * 查询征信核查数据
	 * 2016年3月14日
	 * By 李文勇
	 * @return
	 */
	public List<CreditReportRisk> getPersonCreditReportDetailedByCode(){
		
		
		return null;
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
		// 如果大于0，说明已经存在该条数据
		if(riskList != null && riskList.size() > 0){
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
			
			for(int i = 0; i < riskList.size(); i++){
				if(riskList.get(i).getLoanCode().equals(creditReportRisk.getLoanCode())
					&& riskList.get(i).getrId().equals(creditReportRisk.getrId())
					&& riskList.get(i).getDictCustomerType().equals(creditReportRisk.getDictCustomerType())
					&&( checkType.equals(riskList.get(i).getDictCheckType()) ) ){
					creditReportRisk.setId(riskList.get(i).getId());
					creditReportRisk.preUpdate();
					result = creditReportDao.updataById(creditReportRisk);
					break;
				}else{
					continue;
				}
			}
		}else{// 否则说明没有该条数据
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("loanCode",creditReportRisk.getLoanCode());
			ReconsiderApply recon = reconsiderApplyDao.selectByParam(map);
			if(recon != null && StringUtils.isNotEmpty(recon.getId())){
				// 先插入一条信审数据
				creditReportRisk.setDictCheckType(ApproveCheckType.XS_APPROVE_CHECK_TYPE.getCode());
				creditReportRisk.preInsert();
				result = creditReportDao.asyncSaveCreditReportRiskInfo(creditReportRisk);
				// 
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
	 * 根据借款编号获取全部征信核查数据
	 * 2016年3月15日
	 * By 李文勇
	 * @param param
	 * @return
	 */
	public List<CreditReportRisk> getCreditReportDetailedByCode(CreditReportRisk param){
		List<CreditReportRisk> returnList = new ArrayList<CreditReportRisk>();
		List<CreditReportRisk> result = creditReportDao.getPersonCreditReportDetailedByCode(param);
		if(ArrayHelper.isNotEmpty(result)){
			for(int i = 0; i < result.size(); i++){
				if(ApproveCheckType.FY_APPROVE_CHECK_TYPE.getCode().equals(result.get(i).getDictCheckType())){
					returnList.add(result.get(i));
				}
			}
		}
		if(ArrayHelper.isEmpty(returnList)){
			returnList = result;
		}
		return returnList;
	}
	
	/**
	 * 根据关联ID获取共借人身份证号
	 * 2016年5月26日
	 * By 李文勇
	 * @return
	 */
	public LoanCoborrower selectCoboNameAndCertNum(String rId){
		
		Map<String,Object> result = (Map<String,Object>) loanCoborrowerDao.selectCoboNameAndCertNum(rId);
		String certNum =(String)result.get("cobocertnum");
		String coboName =(String)result.get("coboname");
		LoanCoborrower loanCoborrower = new LoanCoborrower();
		// 身份证号
		loanCoborrower.setCoboCertNum(certNum);
		// 共借人姓名
		loanCoborrower.setCoboName(coboName);
		
		return loanCoborrower;
		
	}
	
	/**
	 * 根据ID获取主借人数据
	 * 2016年5月26日
	 * By 李文勇
	 * @param id
	 * @return
	 */
	public LoanCustomer getCustomer(String id){
		LoanCustomer result = loanCustomerDao.getById(id);
		return result;
	}
}
