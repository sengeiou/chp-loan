package com.creditharmony.loan.car.carGrant.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.type.CardOrBookType;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.CarLoanOperateResult;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.DeductWay;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServiceFyEx;
import com.creditharmony.loan.borrow.grant.entity.ex.UrgeServicesMoneyEx;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServiceHylEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServiceZJEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesMoneyEx;
import com.creditharmony.loan.car.common.consts.CarDeductWays;
import com.creditharmony.loan.car.common.dao.CarLoanStatusHisDao;
import com.creditharmony.loan.car.common.dao.CarUrgeServicesMoneyDao;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.CarUrgeServicesMoney;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.service.CarPaymentSplitService;
import com.creditharmony.loan.common.consts.FileExtension;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.SystemSetting;
import com.creditharmony.loan.common.utils.ExcelUtils;

/**
 * 放款当日待划扣列表
 * @Class Name GrantDeductsService
 */
@Service("carGrantDeductsService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarGrantDeductsService extends CoreManager<CarUrgeServicesMoneyDao, CarUrgeServicesMoneyEx>{
	@Autowired
	private ProvinceCityManager cityManager;
	@Autowired
	private SystemSetMaterDao systemSetMaterDao;
	@Autowired
	private CarLoanStatusHisDao carLoanStatusHisDao;
	@Autowired
	private CarHistoryService carHistoryService;
	@Autowired
	private CarPaymentSplitService paymentSplitService;
	
	/**
	 * 放款当日待划扣列表查询
	 * @param page
	 * @param urgeServicesMoneyEx
	 * @return 分页
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarUrgeServicesMoneyEx> selectUrgeList(Page<CarUrgeServicesMoneyEx> page,CarUrgeServicesMoneyEx urgeServicesMoneyEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("urge_id");
		PageList<CarUrgeServicesMoneyEx> pageList = (PageList<CarUrgeServicesMoneyEx>)dao.selectDeductsList(pageBounds, urgeServicesMoneyEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 放款当日，以往待划扣列表，根据查询条件不分页的list
	 * @param urgeServicesMoneyEx 查询条件
	 * @return 未分页的结果集
	 */
	@Transactional(readOnly = true, value = "transactionManager")
	public List<CarUrgeServicesMoneyEx> selectUrgeListNo(CarUrgeServicesMoneyEx urgeServicesMoneyEx){
		return dao.selectDeductsList(urgeServicesMoneyEx);
	}
	
	/**
	 * 查询放款划扣已办列表
	 * @param page
	 * @param urgeServicesMoneyEx
	 * @return 放款划扣已办集合
	 */
	@Transactional(readOnly = true, value = "transactionManager")
	public Page<UrgeServicesMoneyEx> selectDeductsSuccess(Page<UrgeServicesMoneyEx> page,UrgeServicesMoneyEx urgeServicesMoneyEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<UrgeServicesMoneyEx> pageList = (PageList<UrgeServicesMoneyEx>)dao.selectDeductsSuccess(pageBounds, urgeServicesMoneyEx);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据查询条件查询进行导出的单子
	 * 2016年2月23日
	 * @param urgeServicesMoneyEx 查询条件
	 * @return 要导出的单子
	 */
	@Transactional(readOnly = true, value = "transactionManager")
	public List<UrgeServicesMoneyEx> selectDeductsSuccessNo(UrgeServicesMoneyEx urgeServicesMoneyEx){
		return dao.selectDeductsSuccess(urgeServicesMoneyEx);
	}
	
	/**
	 * 线下导入之后，根据拆分表的更新，获得要更新的催收主表的信息
	 * 2016年2月19日
	 * @param remark 合同编号
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<CarUrgeServicesMoney> selSuccess(String enterpriseSerialno){
		return dao.selSuccess(enterpriseSerialno);
	}
	
	/**
	 * 根据拆分表id查询划扣平台
	 * 2016年1月12日
	 * @param id 拆分表id
	 * @return 拆分实体
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public UrgeServicesMoneyEx getDealType(String id){
		return dao.getDealType(id);
	}
	

	/**
	 * 根据催收主表id查询催收主表的划扣回盘结果
	 * 2016年3月5日
	 * @param id 催收主表id
	 * @return 催收回盘结果
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarUrgeServicesMoney find(String id){
		return dao.find(id);
	}
	
	/**
	 * 根据催收id和回盘结果查询拆分表，返回list
	 * 2016年2月17日
	 * @param urgeServicesMoneyEx 查询条件
	 * @return 集合
	 */
	@Transactional(readOnly = true, value = "transactionManager")
	public List<CarUrgeServicesMoneyEx> selProcess(CarUrgeServicesMoneyEx urgeServicesMoneyEx){
		return dao.selProcess(urgeServicesMoneyEx);
	}
	
	/**
	 * 根据查询出来的拆分表中的集合进行删除
	 * 2016年2月17日
	 * @param urgeId 回盘结果为处理中的拆分表中的单子的id
	 * @return 删除结果
	 */
	@Transactional(readOnly = false, value = "transactionManager")
	public int delProcess(String id){
		return dao.delProcess(id);
	}
	/**
	 * 更新催收主表
	 * 2016年2月17日
	 * @param urgeMoney 更新实体
	 * @return 更新结果
	 */
	@Transactional(readOnly = false, value = "transactionManager")
	public int updateUrge(CarUrgeServicesMoney urgeMoney){
		urgeMoney.preUpdate();
		return dao.updateUrge(urgeMoney);
	}
	
	/**
	 * 更新拆分表
	 * 2016年3月3日
	 * @param urgeMoney 催收服务费扩展实体
	 * @return 更新拆分结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updUrgeSplit(CarUrgeServicesMoneyEx urgeMoney){
		urgeMoney.preUpdate();
		return dao.updUrgeSplit(urgeMoney);
	}
	
	/**
	 * 拆分时，改变拆分前的单子置为无效
	 * 2016年3月1日
	 * @param urgeMoneyEx
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int updSplitStatus(CarUrgeServicesMoneyEx urgeMoneyEx){
		urgeMoneyEx.preUpdate();
		return dao.updSplitStatus(urgeMoneyEx);
	}
	
	/**
	 * 富友平台导出
	 * 2016年1月6日
	 * @param urgeMoneyEx 封装拆分表中的id和父id
	 * @return 富友平台需要的数据的集合
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<UrgeServiceFyEx> getDeductsFy(UrgeServicesMoneyEx urgeMoneyEx){
		return dao.getDeductsFy(urgeMoneyEx);
	}
	
	/**
	 * 好易联、通联平台导出
	 * 2016年3月2日
	 * @param urgeMoneyEx 封装拆分表id和父id
	 * @return 好易联、通联平台需要的数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<CarUrgeServiceHylEx> getDeductsHyl(CarUrgeServicesMoneyEx urgeMoneyEx){
		return dao.getDeductsHyl(urgeMoneyEx);
	}
	/**
	 * 中金平台导出
	 * 2016年3月2日
	 * @param urgeMoneyEx 封装拆分表id和父id
	 * @return 中金平台需要的数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<CarUrgeServiceZJEx> getDeductsZJ(CarUrgeServicesMoneyEx urgeMoneyEx){
		return dao.getDeductsZJ(urgeMoneyEx);
	}
	
	/**
	 * 将催收服务费主表拆分
	 * 2016年1月13日
	 * @param id 催收主键
	 * @return 要进行拆分的实体
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<PaybackApply> queryUrgeList(String id){
		return dao.queryUrgeList(id);
	}
	
	
	/**
	 * 停止或开启滚动划扣方法
	 * 2016年3月2日
	 * @param sysValue 
	 * @return BooleanType
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int changeDeductsRule(HashMap<Object, Object> hashMap) {
	
		return dao.changeDeductsRule(hashMap);
	}
	/**
	 * 查询   SystemSetting
	 * 2016年3月2日
	 * @param systemSetting 
	 * @return SystemSetting
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public SystemSetting getSystemSetting(SystemSetting systemSetting) {
	
		return systemSetMaterDao.get(systemSetting);
	}
	
	
	
	/**
	 *  查询要划扣的数据  DeductReq  车借
	 * @Create In 2016年2月17日
	 * @param paramMap
	 * @return List<DeductReq> 要划扣的数据 
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<DeductReq> queryUrgeDeductReq(HashMap<Object, Object> hashMap,String rule){
		
        //　取得规则
		List<DeductReq> deductReqList =  dao.queryUrgeDeductReq(hashMap);
		
		if (ArrayHelper.isNotEmpty(deductReqList)) {
			
			for (int i = 0; i < deductReqList.size(); i++) {
				deductReqList.get(i).setDeductFlag(DeductFlagType.COLLECTION.getCode());
				// 设置划扣规则
				deductReqList.get(i).setRule(rule);
				//  系统处理ID
				deductReqList.get(i).setSysId(CarDeductWays.CJ_02.getCode());
				// 设置账户类型
				deductReqList.get(i).setAccountType(CardOrBookType.BANKCARD.getCode());
				
				
			}
			
		}
	
	
		return deductReqList;
	}
	
	/**
	 * 单笔数据
	 *  查询要划扣的数据  DeductReq  车借
	 * @Create In 2016年2月17日
	 * @param paramMap
	 * @return DeductReq 要划扣的数据 
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public DeductReq queryOneUrgeDeductReq(HashMap<Object, Object> hashMap,String rule){
		
        //　取得规则
		List<DeductReq> deductReqList =  dao.queryUrgeDeductReq(hashMap);
		
		if (ArrayHelper.isNotEmpty(deductReqList)) {
			for (int i = 0; i < deductReqList.size(); i++) {
				deductReqList.get(i).setDeductFlag(DeductFlagType.COLLECTION.getCode());
				// 设置划扣规则
				deductReqList.get(i).setRule(rule);
				//  系统处理ID
				deductReqList.get(i).setSysId(CarDeductWays.CJ_02.getCode());
			}
			return deductReqList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据合同编号查询要进行放款审核退回时催收服务费的处理状态
	 * 2016年3月8日
	 * @param contractCode 合同编号
	 * @return  处理状态
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public String getDealCount(String contractCode){
		return dao.getDealCount(contractCode);
	}
	
	// 催收服务费处理
	@SuppressWarnings("finally")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String onlineGrantDeducts(CarUrgeServicesMoneyEx carUrgeItem,
			String rule, String result, String deductsType, String BEFORE) {
		// 通过催收主表id获得要传送到批处理的list,在SQL中控制要传送给批处理的单子只能为失败的单子
		String message = "";
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("id", "'" + carUrgeItem.getUrgeId() + "'");
		hashMap.put("dictDealStatus",UrgeCounterofferResult.PAYMENT_FAILED.getCode());
		DeductReq deductReqList = queryOneUrgeDeductReq(hashMap, rule);
		if (ObjectHelper.isNotEmpty(deductReqList)) {
			// 发送到批处理
			DeResult t = TaskService.addTask(deductReqList);
			try {
				if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
					// 将单子的划扣平台更新为要发送的平台，更新催收主表的划扣平台
					CarUrgeServicesMoney urge = new CarUrgeServicesMoney();
					urge.setId("'" + carUrgeItem.getUrgeId() + "'");
					urge.setDictDealStatus(UrgeCounterofferResult.PROCESS
							.getCode());
					urge.setDictDealType(deductsType);
					urge.preUpdate();
					dao.updateUrge(urge);
					// 同时插入历史
					if (result.equals(BEFORE)) {
						insertDeductHis(carUrgeItem.getLoanCode(),CarLoanSteps.PAST_DEDUCTS.getCode(),CarLoanOperateResult.PROCESS.getCode(), "", "");
					} else {
						insertDeductHis(carUrgeItem.getLoanCode(),CarLoanSteps.CURRENT_DEDUCTS.getCode(),CarLoanOperateResult.PROCESS.getCode(), "", "");
					}
					CarUrgeServicesMoneyEx delurgeEx = new CarUrgeServicesMoneyEx();
					// 删除拆分表中已经存在的单子,拆分表中处理状态为划扣失败的单子
					delurgeEx.setUrgeId("'" + carUrgeItem.getUrgeId() + "'");
					delurgeEx.setSplitResult(CounterofferResult.PAYMENT_SUCCEED
							.getCode());
					List<CarUrgeServicesMoneyEx> delList = selProcess(delurgeEx);
					delSplit(delList);
					TaskService.commit(t.getDeductReq());
				} else {
					CarUrgeServicesMoney urge = new CarUrgeServicesMoney();
					urge.setId("'" + carUrgeItem.getUrgeId() + "'");
					// 将催收主表中回盘结果设置为处理中
					urge.setDictDealStatus(UrgeCounterofferResult.PAYMENT_FAILED
							.getCode());
					urge.setDictDealType(deductsType);
					updateUrge(urge);
					
					
					TaskService.rollBack(t.getDeductReq());	
					message = t.getReMge();
					TaskService.rollBack(t.getDeductReq());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("车借催收服务费发送划扣失败",e);
				TaskService.rollBack(t.getDeductReq());
				throw new ServiceException("车借催收服务费发送划扣失败");
			}finally{
				return message;
			}
		}
		return message;
	}
	
	/**
	 * 插入历史，为了控制事务，单独拿出来的一个方法
	 * 2016年7月5日
	 * By 朱静越
	 * @param loanCode
	 * @param operateStep
	 * @param operateResult
	 * @param remark
	 * @param loanStatusCode
	 */
	public void insertDeductHis(String loanCode, String operateStep,
			String operateResult, String remark, String loanStatusCode){
		CarLoanStatusHis record = new CarLoanStatusHis();
		record.preInsert();
		record.setLoanCode(loanCode);
		record.setOperateStep(operateStep);
		record.setOperateResult(operateResult);
		record.setRemark(remark);
		record.setDictLoanStatus(loanStatusCode);
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		record.setOperateTime(record.getCreateTime());
		User user = UserUtils.getUser();
		if (user != null) {
			record.setOperator(user.getName());// 操作人记录当前登陆系统用户名称
			if (!ObjectHelper.isEmpty(user)) {
				record.setOperatorRoleId(user.getId());// 操作人角色
			}
			if (!ObjectHelper.isEmpty(user.getDepartment())) {
				record.setOrgCode(user.getDepartment().getId()); // 机构编码
			}
		}
		carLoanStatusHisDao.insert(record);
	}
	
	
	/**
	 * 对要进行导出的单子，将拆分表中划扣失败的单子重新合并成要拆分的集合，同时删除拆分表中划扣失败的单子
	 * 2016年3月1日
	 * @param delList 拆分表中存在的
	 * @return 要进行重新拆分的集合
	 */
	public void delSplit(List<CarUrgeServicesMoneyEx> delList){
		if (delList.size() >0) {
			// 获得拆分list
			String id = null;
			StringBuilder parameterSplit = new StringBuilder();
			for (int i = 0; i < delList.size(); i++) {
				parameterSplit.append("'"+delList.get(i).getId()+"',");
				}
			id = parameterSplit.toString().substring(0,parameterSplit.lastIndexOf(","));
			// 删除存在的list
			dao.delProcess(id);
		}
	}
	
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void deductsHylExl(List<CarUrgeServicesMoneyEx> delList,String idstring,List<PaybackApply> payList,String deductsType,CarUrgeServicesMoneyEx urgeEx,String titleFlag,HttpServletResponse response,CarUrgeServicesMoney urgeServicesMoney){
		List<CarUrgeServiceHylEx> urgeHylList=new ArrayList<CarUrgeServiceHylEx>();
		ExcelUtils excelutil = new ExcelUtils();
		List<CarUrgeServiceZJEx> urgeZJList=new ArrayList<CarUrgeServiceZJEx>();
		if (ArrayHelper.isNotEmpty(delList)) {// 删除拆分表中的失败数据
			delSplit(delList);
		}
		if (StringUtils.isNotEmpty(idstring) && ArrayHelper.isNotEmpty(payList)) {
			if (DeductPlat.HAOYILIAN.getCode().equals(deductsType)) { // 好易联平台
				// 进行拆分，插入到拆分表,拆分表中默认的处理状态为待划扣
				getSplit(payList,DeductPlat.HAOYILIAN.getName());
				urgeHylList = getDeductsHyl(urgeEx);
				excelutil.exportExcel(urgeHylList,titleFlag,null, CarUrgeServiceHylEx.class, FileExtension.XLSX,
						FileExtension.OUT_TYPE_TEMPLATE, response, null);
				// 更新催收服务费主表
				urgeServicesMoney.setDictDealType(DeductPlat.HAOYILIAN.getCode());
				urgeServicesMoney.setDictDealStatus(CounterofferResult.PROCESSED.getCode());
				updateUrge(urgeServicesMoney);
			}else if(DeductPlat.TONGLIAN.getCode().equals(deductsType)){ // 通联平台
				// 进行拆分，插入到拆分表,拆分表中默认的处理状态为待划扣
				getSplit(payList,DeductPlat.TONGLIAN.getName());

				urgeHylList = getDeductsHyl(urgeEx);
				excelutil.exportExcel(urgeHylList,"放款当日待划扣",null, CarUrgeServiceHylEx.class, FileExtension.XLSX,
						FileExtension.OUT_TYPE_DATA, response, null);
				
				urgeServicesMoney.setDictDealType(DeductPlat.TONGLIAN.getCode());
				urgeServicesMoney.setDictDealStatus(CounterofferResult.PROCESSED.getCode());
				updateUrge(urgeServicesMoney);
			}else if(DeductPlat.ZHONGJIN.getCode().equals(deductsType)){ // 中金平台
				
				// 进行拆分，插入到拆分表,拆分表中默认的处理状态为待划扣
				getSplit(payList,DeductPlat.ZHONGJIN.getName());
				urgeZJList = getDeductsZJ(urgeEx);
				
				excelutil.exportExcel(urgeZJList,"放款当日待划扣",null, CarUrgeServiceZJEx.class, FileExtension.XLSX,
						FileExtension.OUT_TYPE_DATA, response, null);
				
				urgeServicesMoney.setDictDealType(DeductPlat.ZHONGJIN.getCode());
				urgeServicesMoney.setDictDealStatus(CounterofferResult.PROCESSED.getCode());
				updateUrge(urgeServicesMoney);
			}
			
			// 单子导出之后，将拆分表中的回盘结果更新为处理中
			if (ArrayHelper.isNotEmpty(urgeHylList)) {
				for (int i = 0; i < urgeHylList.size(); i++) {
					urgeEx.setId(urgeHylList.get(i).getId());
					urgeEx.setSplitResult(CounterofferResult.PROCESSED.getCode());
					updSplitStatus(urgeEx);
				}
			}
			if (ArrayHelper.isNotEmpty(urgeZJList)) {
				for (int i = 0; i < urgeZJList.size(); i++) {
					urgeEx.setId(urgeZJList.get(i).getId());
					urgeEx.setSplitResult(CounterofferResult.PROCESSED.getCode());
					updSplitStatus(urgeEx);
				}
			}
		}
	}
	
	/**
	 * 线下导出拆分，拆分之后，获得拆分后的list；
	 * 2016年3月1日
	 * @param id 要进行拆分的催收id
	 * @param splitPlat 划扣平台
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void getSplit(List<PaybackApply> payList,String splitPlat){
		// 平台
		DeductPlat plat=null;
		// 获得划扣平台
		for(DeductPlat rate:DeductPlat.values()){  
	          if (rate.getName().equals(splitPlat)) {
	        	  plat=rate;
			} 
	    }		
		// 拆分
		try {
			paymentSplitService.splitList(payList, TargetWay.CAR_SERVICE_FEE.getCode(),DeductTime.RIGHTNOW,plat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void manualSure(List<CarUrgeServicesMoneyEx> delList,String splitBackResult,String splitFailResult,int i,List<CarUrgeServicesMoneyEx> urgeList,String BEFORE,String result){
		if (ArrayHelper.isNotEmpty(delList)) {
			for (int j = 0; j < delList.size(); j++) {
				CarUrgeServicesMoneyEx urgeMoneyEx = new CarUrgeServicesMoneyEx();
				// 设置拆分id
				urgeMoneyEx.setId(delList.get(i).getId());
				// 设置划扣标识
				urgeMoneyEx.setPaybackFlag(DeductWay.OFFLINE.getCode());
				// 设置回盘时间
				urgeMoneyEx.setSplitBackDate(new Date());
				if (splitBackResult.equals(CounterofferResult.PAYMENT_SUCCEED
						.getCode())) {
				// 回盘结果为成功
					urgeMoneyEx.setSplitResult(CounterofferResult.PAYMENT_SUCCEED
									.getCode());
				} else {
					urgeMoneyEx.setSplitResult(CounterofferResult.PAYMENT_FAILED
									.getCode());
					// 获得原因
					urgeMoneyEx.setSplitFailResult(splitFailResult);
				}
				try{
					updUrgeSplit(urgeMoneyEx);
				}catch(Exception e){
				}
			}
		}
		CarUrgeServicesMoney updateMoney = new CarUrgeServicesMoney();
		if (ArrayHelper.isNotEmpty(urgeList)) {
			// 设置催收id
			updateMoney.setId("'"+urgeList.get(0).getUrgeId()+"'");
			// 设置已划扣金额
			updateMoney.setUrgeDecuteMoeny(urgeList.get(0).getUrgeMoeny());
			// 设置划扣日期
			updateMoney.setUrgeDecuteDate(new Date());
			
			if (splitBackResult.equals(CounterofferResult.PAYMENT_SUCCEED
					.getCode())) {
				
			// 回盘结果为成功
				updateMoney.setDictDealStatus(CounterofferResult.PAYMENT_SUCCEED
								.getCode());
			} else {
				updateMoney.setDictDealStatus(CounterofferResult.PAYMENT_FAILED
								.getCode());
				// 获得原因
				updateMoney.setSplitFailResult(splitFailResult);
			}
			updateUrge(updateMoney);
			if (result.equals(BEFORE) && splitBackResult.equals(CounterofferResult.PAYMENT_SUCCEED.getCode())) {
				// 如果是以往待划,并划扣成功
				carHistoryService.saveCarLoanStatusHis(urgeList.get(0).getLoanCode(), CarLoanSteps.PAST_DEDUCTS.getCode(), 
						CarLoanOperateResult.PAST_DRAW_SUCCESS.getCode(), "", "");
				
			}else if(result.equals(BEFORE) && splitBackResult.equals(CounterofferResult.PAYMENT_FAILED.getCode())){
				// 如果是以往待划,并划扣失败
				carHistoryService.saveCarLoanStatusHis(urgeList.get(0).getLoanCode(), CarLoanSteps.PAST_DEDUCTS.getCode(), 
						CarLoanOperateResult.PAST_DRAW_DEFEATED.getCode(), "", "");
			}else if(!result.equals(BEFORE) && splitBackResult.equals(CounterofferResult.PAYMENT_SUCCEED.getCode())){
				// 如果是当日待划,并划扣成
				carHistoryService.saveCarLoanStatusHis(urgeList.get(0).getLoanCode(), CarLoanSteps.CURRENT_DEDUCTS.getCode(), 
						CarLoanOperateResult.DAY_DRAW_SUCCESS.getCode(), "", "");
			}else if( !result.equals(BEFORE) && splitBackResult.equals(CounterofferResult.PAYMENT_FAILED.getCode())){
				// 如果是当日待划,并划扣失败
				carHistoryService.saveCarLoanStatusHis(urgeList.get(0).getLoanCode(), CarLoanSteps.CURRENT_DEDUCTS.getCode(), 
						CarLoanOperateResult.DAY_DRAW_DEFEATED.getCode(), "", "");
			}else{
				if(result.equals(BEFORE)){
					carHistoryService.saveCarLoanStatusHis(urgeList.get(0).getLoanCode(), CarLoanSteps.CURRENT_DEDUCTS.getCode(), 
							CarLoanOperateResult.DAY_DRAW_DEFEATED.getCode(), "", "");
				}else{
					carHistoryService.saveCarLoanStatusHis(urgeList.get(0).getLoanCode(), CarLoanSteps.PAST_DEDUCTS.getCode(), 
							CarLoanOperateResult.PAST_DRAW_DEFEATED.getCode(), "", "");
				}
			}
		}

	}
	
	@SuppressWarnings("finally")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String sendUrgeDeduct(DeductReq deductReq,CarUrgeServicesMoney urgeServicesMoney,String rPaybackId){
		String flag = null;
		DeResult t = TaskService.addTask(deductReq);
		try {
			CarUrgeServicesMoneyEx urgeServices = new CarUrgeServicesMoneyEx();
			if(t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())){
				urgeServicesMoney.setId("'"+rPaybackId+"'");
				urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.PROCESS.getCode());
				urgeServicesMoney.setDeductStatus(UrgeCounterofferResult.PROCESS.getCode());
				urgeServicesMoney.setUrgeDecuteDate(new Date());
				dao.updateUrge(urgeServicesMoney);
				
				// 同时插入历史,获得划扣平台
//				carHistoryService.saveCarLoanStatusHis(carDeductCostRecoverEx.getLoanCode(), CarLoanSteps.STORE_DEDUCTS.getCode(), 
//						CarLoanOperateResult.PROCESS.getCode(), "", "");
				
				// 将拆分表中为失败的和处理中的单子删除
				urgeServices.setUrgeId("'" + rPaybackId + "'");
				urgeServices.setDictDealStatus(UrgeCounterofferResult.PROCESSED.getCode());
				// 发送成功之后，获得将拆分表中处理状态为处理中（导出）和失败的单子进行删除
				List<CarUrgeServicesMoneyEx> delList = selProcess(urgeServices);
				// 删除
				if (delList.size()>0) {
					for (int i = 0; i < delList.size(); i++) {
						dao.delProcess("'"+delList.get(i).getId()+"'");
					}
				}
				flag = "success";
				
				// 插入历史
				PaybackOpe paybackOpe = new PaybackOpe();
				paybackOpe.setrPaybackApplyId(null);
				paybackOpe.setrPaybackId(rPaybackId);
				paybackOpe.setDictLoanStatus(RepaymentProcess.SEND_DEDUCT.getCode());
				paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
				paybackOpe.setRemarks(GrantCommon.SEND_DEDUCT);
				paybackOpe.setOperateResult(PaybackOperate.SEND_SUCCESS.getCode());
				paybackOpe.preInsert();

				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				
				TaskService.commit(t.getDeductReq());
			}else {
				
				// 插入历史
				PaybackOpe paybackOpe = new PaybackOpe();
				paybackOpe.setrPaybackApplyId(null);
				paybackOpe.setrPaybackId(rPaybackId);
				paybackOpe.setDictLoanStatus(RepaymentProcess.SEND_DEDUCT.getCode());
				paybackOpe.setDictRDeductType(TargetWay.SERVICE_FEE.getCode());
				paybackOpe.setRemarks(GrantCommon.SEND_DEDUCT);
				paybackOpe.setOperateResult(PaybackOperate.SEND_FAILED.getCode());
				paybackOpe.preInsert();

				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				
				flag = "划扣失败"+t.getReMge();
				TaskService.rollBack(t.getDeductReq());
			}
		} catch (Exception e) {
			e.printStackTrace();
			TaskService.rollBack(t.getDeductReq());
			logger.error("提交划扣失败，发生异常",e);
			throw new ServiceException("提交划扣失败");
		}finally{
			return flag;
		}
	}
	
}
