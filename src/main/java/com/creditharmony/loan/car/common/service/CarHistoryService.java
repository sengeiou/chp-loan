/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.HistoryService.java
 * @Create By 张进
 * @Create In 2015年12月1日 下午2:10:16
 */
package com.creditharmony.loan.car.common.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.mail.MailInfo;
import com.creditharmony.adapter.bean.out.mail.MailOutInfo;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ListUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.BooleanType;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.CarLoanSteps;
import com.creditharmony.core.loan.type.EmailTemplateType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mapper.JsonMapper;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.car.common.dao.CarLoanStatusHisDao;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.ex.CarLoanGrantHaveEx;
import com.creditharmony.loan.car.common.entity.ex.CarLoanStatusHisEx;
import com.creditharmony.loan.car.common.util.MailUtil;
import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarHistoryService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CarLoanStatusHisDao carLoanStatusHisDao;

	/**
	 * 添加操作历史
	 * 
	 * @param loanCode
	 *            借款编码
	 * @param operateStep
	 *            操作步骤code（节点code，参考CarLoanSteps）
	 * @param operateResult
	 *            操作结果（路由）
	 * @param remark
	 *            备注
	 * @param loanStatusCode
	 *            借款状态编码（参考CarLoanStatus）
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public int saveCarLoanStatusHis(String loanCode, String operateStep,
			String operateResult, String remark, String loanStatusCode) {
		CarLoanStatusHis record = new CarLoanStatusHis();
		// 设置Crud属性值
		record.preInsert();
		// APPLY_ID
		record.setLoanCode(loanCode);
		// 操作节点，如上传资料、初审、终审等的code值
		record.setOperateStep(operateStep);
		// 操作结果，用中文表示，如初审通过，终审拒绝等
		record.setOperateResult(operateResult);
		// 备注
		record.setRemark(remark);
		// 借款状态，当前节点操作后的状态code值
		record.setDictLoanStatus(loanStatusCode);
		// 系统标识
		record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		// 操作时间
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
		return carLoanStatusHisDao.insert(record);
	}

	/**
	 * 分页查询历史数据列表
	 * 
	 * @param page
	 *            分页对象
	 * @param CarLoanStatusHis
	 *            查询条件，获取appId
	 * @return 分页数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarLoanStatusHis> findCarLoanStatusHisList(
			Page<CarLoanStatusHis> page, CarLoanStatusHis CarLoanStatusHis) {
		CarLoanStatusHis.setPage(page);
		CarLoanStatusHis.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		page.setList(carLoanStatusHisDao.findPage(CarLoanStatusHis));
		return page;
	}
	
	/**
	 * 分页查询2.0历史数据列表
	 * 
	 * @param page
	 *            分页对象
	 * @param CarLoanStatusHis
	 *            查询条件，获取appId
	 * @return 分页数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarLoanStatusHis> findOldCarLoanStatusHisList(
			Page<CarLoanStatusHis> page, CarLoanStatusHis CarLoanStatusHis) {
		CarLoanStatusHis.setPage(page);
		CarLoanStatusHis.setDictSysFlag(ModuleName.MODULE_LOAN.value);
		page.setList(carLoanStatusHisDao.findOldPage(CarLoanStatusHis));
		return page;
	}

	/**
	 * 查询车借已办列表 2016年2月26日 By 陈伟东
	 * 
	 * @param page
	 * @param carLoanStatusHisEx
	 * @param isIn
	 *            in前是否加not（仅限节点查询前），为1，则不加，其他，则在in前加not
	 * @param isQueryAll
	 *            是否查询全部（主要用于车借数据列表），为1，则查询全部，若为其他，则查询部分
	 * @param nodeValueLists
	 *            节点列表值，用逗号","隔开，如：1,2,3等，适用于in内部（仅限节点查询前）
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarLoanStatusHisEx> findDoneList(Page page,
			CarLoanStatusHisEx carLoanStatusHisEx, String isIn,
			String isQueryAll, String nodeValueLists, String grossFlag) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		pageBounds.setCountBy("apply_Id");
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		if ("1".equals(carLoanStatusHisEx.getIsAllData())) {
			pageBounds.setLimit(99999);
			page.setPageSize(999999999);
		}
		carLoanStatusHisEx.setIsIn(isIn);
		carLoanStatusHisEx.setIsQueryAll(isQueryAll);
		List<String> nodeValLists = null;
		if (nodeValueLists != null && !"".equals(nodeValueLists)
				&& nodeValueLists.split(",").length > 0) {
			nodeValLists = new ArrayList<String>();
			for (String val : nodeValueLists.split(",")) {
				nodeValLists.add(val.trim());
			}
		}
		carLoanStatusHisEx.setNodeValList(nodeValLists);

		String storeList = carLoanStatusHisEx.getStoreCode();
		List<String> storeCodeList = Lists.newArrayList();
		// 门店查询传入storeCode格式为1,2,3，需要改为List<String>格式，便于sql查询
		if (storeList != null && storeList.length() > 0) {
			storeCodeList = new ArrayList<String>();
			for (String storeCode : storeList.split(",")) {
				storeCodeList.add(storeCode.trim());
			}
		}
		carLoanStatusHisEx.setStoreCodeList(storeCodeList);
		carLoanStatusHisEx.setGrossFlag(grossFlag);
		
		Map<String, Object> filter = JsonMapper.nonDefaultMapper()
				.convertValue(carLoanStatusHisEx, Map.class);
	
		filter.put("urgeDecuteDateStart", carLoanStatusHisEx.getUrgeDecuteDateStart());
		Date urgeDecuteDateEnd = carLoanStatusHisEx.getUrgeDecuteDateEnd();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		if(urgeDecuteDateEnd != null){
			try {
				Date date = sdf.parse(sdf.format(urgeDecuteDateEnd));
				Calendar instance = Calendar.getInstance();
				instance.setTime(date);
				instance.add(Calendar.DAY_OF_MONTH, 1);
				filter.put("urgeDecuteDateEnd", instance.getTime());
			} catch (ParseException e) {
				logger.error("划扣已办查询时划扣日期转化异常", e);
			}
		}
		String lendingTimeStart = carLoanStatusHisEx.getLendingTimeStart();
		if(!StringUtils.isEmpty(lendingTimeStart)){
			try {
				lendingTimeStart = lendingTimeStart.substring(0, 10) + " 00:00:00";
				Date parse = sdf.parse(lendingTimeStart);
				filter.put("lendingTimeStart", parse);
			} catch (ParseException e) {
				logger.error("已办查询放款日期转化异常", e);
			}
		}
		String lendingTimeEnd = carLoanStatusHisEx.getLendingTimeEnd();
		if(!StringUtils.isEmpty(lendingTimeEnd)){
			try {
				lendingTimeEnd = lendingTimeEnd.substring(0, 10) + " 00:00:00";
				Date parse = sdf.parse(lendingTimeEnd);
				Calendar instance = Calendar.getInstance();
				instance.setTime(parse);
				instance.add(Calendar.DAY_OF_MONTH, 1);
				filter.put("lendingTimeEnd", instance.getTime());
			} catch (ParseException e) {
				logger.error("已办查询放款日期转化异常", e);
			}
		}
		
		PageList<CarLoanStatusHisEx> pageList = (PageList<CarLoanStatusHisEx>) carLoanStatusHisDao
				.findDoneList(filter, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<CarLoanGrantHaveEx> findDoneListForXls(Page page,
			CarLoanStatusHisEx carLoanStatusHisEx, String isIn,
			String isQueryAll, String nodeValueLists, String grossFlag) {
		carLoanStatusHisEx.setIsIn(isIn);
		carLoanStatusHisEx.setIsQueryAll(isQueryAll);
		List<String> nodeValLists = null;
		if (nodeValueLists != null && !"".equals(nodeValueLists)
				&& nodeValueLists.split(",").length > 0) {
			nodeValLists = new ArrayList<String>();
			for (String val : nodeValueLists.split(",")) {
				nodeValLists.add(val.trim());
			}
		}
		carLoanStatusHisEx.setNodeValList(nodeValLists);
		
		String storeList = carLoanStatusHisEx.getStoreCode();
		List<String> storeCodeList = Lists.newArrayList();
		// 门店查询传入storeCode格式为1,2,3，需要改为List<String>格式，便于sql查询
		if (storeList != null && storeList.length() > 0) {
			storeCodeList = new ArrayList<String>();
			for (String storeCode : storeList.split(",")) {
				storeCodeList.add(storeCode.trim());
			}
		}
		carLoanStatusHisEx.setStoreCodeList(storeCodeList);
		carLoanStatusHisEx.setGrossFlag(grossFlag);
		
		Map<String, Object> filter = JsonMapper.nonDefaultMapper()
				.convertValue(carLoanStatusHisEx, Map.class);
		
		filter.put("urgeDecuteDateStart", carLoanStatusHisEx.getUrgeDecuteDateStart());
		Date urgeDecuteDateEnd = carLoanStatusHisEx.getUrgeDecuteDateEnd();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		if(urgeDecuteDateEnd != null){
			try {
				Date date = sdf.parse(sdf.format(urgeDecuteDateEnd));
				Calendar instance = Calendar.getInstance();
				instance.setTime(date);
				instance.add(Calendar.DAY_OF_MONTH, 1);
				filter.put("urgeDecuteDateEnd", instance.getTime());
			} catch (ParseException e) {
				logger.error("划扣已办查询时划扣日期转化异常", e);
			}
		}
		String lendingTimeStart = carLoanStatusHisEx.getLendingTimeStart();
		if(!StringUtils.isEmpty(lendingTimeStart)){
			try {
				lendingTimeStart = lendingTimeStart.substring(0, 10) + " 00:00:00";
				Date parse = sdf.parse(lendingTimeStart);
				filter.put("lendingTimeStart", parse);
			} catch (ParseException e) {
				logger.error("已办查询放款日期转化异常", e);
			}
		}
		String lendingTimeEnd = carLoanStatusHisEx.getLendingTimeEnd();
		if(!StringUtils.isEmpty(lendingTimeEnd)){
			try {
				lendingTimeEnd = lendingTimeEnd.substring(0, 10) + " 00:00:00";
				Date parse = sdf.parse(lendingTimeEnd);
				Calendar instance = Calendar.getInstance();
				instance.setTime(parse);
				instance.add(Calendar.DAY_OF_MONTH, 1);
				filter.put("lendingTimeEnd", instance.getTime());
			} catch (ParseException e) {
				logger.error("已办查询放款日期转化异常", e);
			}
		}
		List<CarLoanGrantHaveEx> pageList = carLoanStatusHisDao.findDoneListForXls(filter);
		return pageList;
	}

	/**
	 * 查询车借展期已办列表 2016年3月11日 By 申诗阔
	 * 
	 * @param page
	 * @param carLoanStatusHisEx
	 * @param isIn
	 *            in前是否加not（仅限节点查询前），为1，则不加，其他，则在in前加not
	 * @param isQueryAll
	 *            是否查询全部（主要用于车借数据列表），为1，则查询全部，若为其他，则查询部分
	 * @param nodeValueLists
	 *            节点列表值，用逗号","隔开，如：1,2,3等，适用于in内部（仅限节点查询前）
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<CarLoanStatusHisEx> findExtendDoneList(Page page,
			CarLoanStatusHisEx carLoanStatusHisEx, String isIn,
			String isQueryAll, String nodeValueLists, String grossFlag) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		pageBounds.setCountBy("apply_Id");
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		carLoanStatusHisEx.setIsIn(isIn);
		carLoanStatusHisEx.setIsQueryAll(isQueryAll);
		List<String> nodeValLists = null;
		if (nodeValueLists != null && !"".equals(nodeValueLists)
				&& nodeValueLists.split(",").length > 0) {
			nodeValLists = new ArrayList<String>();
			for (String val : nodeValueLists.split(",")) {
				nodeValLists.add(val.trim());
			}
		}
		carLoanStatusHisEx.setNodeValList(nodeValLists);

		String storeList = carLoanStatusHisEx.getStoreCode();
		List<String> storeCodeList = Lists.newArrayList();
		// 门店查询传入storeCode格式为1,2,3，需要改为List<String>格式，便于sql查询
		if (storeList != null && storeList.length() > 0) {
			storeCodeList = new ArrayList<String>();
			for (String storeCode : storeList.split(",")) {
				storeCodeList.add(storeCode.trim());
			}
		}
		carLoanStatusHisEx.setStoreCodeList(storeCodeList);
		carLoanStatusHisEx.setGrossFlag(grossFlag);
		Map<String, Object> filter = JsonMapper.nonDefaultMapper()
				.convertValue(carLoanStatusHisEx, Map.class);
		PageList<CarLoanStatusHisEx> pageList = (PageList<CarLoanStatusHisEx>) carLoanStatusHisDao
				.findExtendDoneList(filter, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 查询车借展期合同已办列表 2016年3月11日 By 申诗阔
	 * 
	 * @param page
	 * @param carLoanStatusHisEx
	 * @param isIn
	 *            in前是否加not（仅限节点查询前），为1，则不加，其他，则在in前加not
	 * @param isQueryAll
	 *            是否查询全部（主要用于车借数据列表），为1，则查询全部，若为其他，则查询部分
	 * @param nodeValueLists
	 *            节点列表值，用逗号","隔开，如：1,2,3等，适用于in内部（仅限节点查询前）
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page<CarLoanStatusHisEx> findExtendContractDoneList(Page page,
			CarLoanStatusHisEx carLoanStatusHisEx, String isIn,
			String isQueryAll, String nodeValueLists, String grossFlag) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		pageBounds.setCountBy("apply_Id");
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		carLoanStatusHisEx.setIsIn(isIn);
		carLoanStatusHisEx.setIsQueryAll(isQueryAll);
		List<String> nodeValLists = null;
		if (nodeValueLists != null && !"".equals(nodeValueLists)
				&& nodeValueLists.split(",").length > 0) {
			nodeValLists = new ArrayList<String>();
			for (String val : nodeValueLists.split(",")) {
				nodeValLists.add(val.trim());
			}
		}
		carLoanStatusHisEx.setNodeValList(nodeValLists);

		String storeList = carLoanStatusHisEx.getStoreCode();
		List<String> storeCodeList = Lists.newArrayList();
		// 门店查询传入storeCode格式为1,2,3，需要改为List<String>格式，便于sql查询
		if (storeList != null && storeList.length() > 0) {
			storeCodeList = new ArrayList<String>();
			for (String storeCode : storeList.split(",")) {
				storeCodeList.add(storeCode.trim());
			}
		}
		carLoanStatusHisEx.setStoreCodeList(storeCodeList);
		carLoanStatusHisEx.setGrossFlag(grossFlag);
		Map<String, Object> filter = JsonMapper.nonDefaultMapper()
				.convertValue(carLoanStatusHisEx, Map.class);
		PageList<CarLoanStatusHisEx> pageList = (PageList<CarLoanStatusHisEx>) carLoanStatusHisDao
				.findExtendContractDoneList(filter, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询车借数据列表-包含2.0迁移数据 2016年4月22日 By JiangLi
	 * 
	 * @param page
	 * @param carLoanStatusHisEx
	 * @param isIn
	 *            in前是否加not（仅限节点查询前），为1，则不加，其他，则在in前加not
	 * @param isQueryAll
	 *            是否查询全部（主要用于车借数据列表），为1，则查询全部，若为其他，则查询部分
	 * @param nodeValueLists
	 *            节点列表值，用逗号","隔开，如：1,2,3等，适用于in内部（仅限节点查询前）
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarLoanStatusHisEx> findLoanDataList(Page page,
			CarLoanStatusHisEx carLoanStatusHisEx, String isIn,
			String isQueryAll, String nodeValueLists, String grossFlag) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		pageBounds.setCountBy("apply_Id");
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		if ("1".equals(carLoanStatusHisEx.getIsAllData())) {
			page.setPageSize(999999999);
		}
		carLoanStatusHisEx.setIsIn(isIn);
		carLoanStatusHisEx.setIsQueryAll(isQueryAll);
		List<String> nodeValLists = null;
		if (nodeValueLists != null && !"".equals(nodeValueLists)
				&& nodeValueLists.split(",").length > 0) {
			nodeValLists = new ArrayList<String>();
			for (String val : nodeValueLists.split(",")) {
				nodeValLists.add(val.trim());
			}
		}
		carLoanStatusHisEx.setNodeValList(nodeValLists);

		String storeList = carLoanStatusHisEx.getStoreCode();
		List<String> storeCodeList = Lists.newArrayList();
		// 门店查询传入storeCode格式为1,2,3，需要改为List<String>格式，便于sql查询
		if (storeList != null && storeList.length() > 0) {
			storeCodeList = new ArrayList<String>();
			for (String storeCode : storeList.split(",")) {
				storeCodeList.add(storeCode.trim());
			}
		}
		carLoanStatusHisEx.setStoreCodeList(storeCodeList);
		carLoanStatusHisEx.setGrossFlag(grossFlag);
		Map<String, Object> filter = JsonMapper.nonDefaultMapper()
				.convertValue(carLoanStatusHisEx, Map.class);
		PageList<CarLoanStatusHisEx> pageList = (PageList<CarLoanStatusHisEx>) carLoanStatusHisDao
				.findLoanDataList(filter, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CarLoanStatusHis checkIsContractCheckBackSign(String loanCode) {
		return carLoanStatusHisDao.checkIsContractCheckBackSign(loanCode);
	}

	/**
	 * 2016年5月12日
	 * By陈伟丽
	 * @param page
	 * @param carLoanStatusHisEx
	 * @param isIn
	 * @param isQueryAll
	 * @param nodeValueLists
	 * @param grossFlag
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarLoanStatusHisEx> findCheckDoneList(Page<CarLoanStatusHisEx> page,CarLoanStatusHisEx carLoanStatusHisEx, String isIn,String isQueryAll, String nodeValueLists, String grossFlag){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		pageBounds.setCountBy("apply_Id");
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		if ("1".equals(carLoanStatusHisEx.getIsAllData())) {
			page.setPageSize(999999999);
		}
		
		System.out.println(carLoanStatusHisEx.getLoanCustomerName());
		carLoanStatusHisEx.setIsIn(isIn);
		carLoanStatusHisEx.setIsQueryAll(isQueryAll);
		List<String> nodeValLists = null;
		if (nodeValueLists != null && !"".equals(nodeValueLists)
				&& nodeValueLists.split(",").length > 0) {
			nodeValLists = new ArrayList<String>();
			for (String val : nodeValueLists.split(",")) {
				nodeValLists.add(val.trim());
			}
		}
		carLoanStatusHisEx.setNodeValList(nodeValLists);

		String storeList = carLoanStatusHisEx.getStoreCode();
		List<String> storeCodeList = Lists.newArrayList();
		// 门店查询传入storeCode格式为1,2,3，需要改为List<String>格式，便于sql查询
		if (storeList != null && storeList.length() > 0) {
			storeCodeList = new ArrayList<String>();
			for (String storeCode : storeList.split(",")) {
				storeCodeList.add(storeCode.trim());
			}
		}
		carLoanStatusHisEx.setStoreCodeList(storeCodeList);
		carLoanStatusHisEx.setGrossFlag(grossFlag);
		Map<String, Object> filter = JsonMapper.nonDefaultMapper()
				.convertValue(carLoanStatusHisEx, Map.class);
		PageList<CarLoanStatusHisEx> pageList = (PageList<CarLoanStatusHisEx>) carLoanStatusHisDao
				.findCheckDoneList(filter, pageBounds);
		PageUtil.convertPage(pageList, page);
		
		return page;
	}
	
	/**
	 * 
	 * 车借申请列表
	 * 申请电子协议
	 * 显示客户基本信息
	 */
	public CarLoanStatusHisEx getCustomerMsg(CarLoanStatusHisEx ex){
		return carLoanStatusHisDao.getCustomerMsg(ex).get(0);
	}
	
	/**
	 * 车借申请列表
	 * 申请电子协议
	 * 修改协议状态
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateContractArgType(CarLoanStatusHisEx ex){
		carLoanStatusHisDao.updateContractArgType(ex);
		String step = "";
		if(ex.getDictOperStatus().equals(YESNO.YES.getCode())){
			step = "车借申请电子协议";
		}else{
			step = "展期申请电子协议";
		}
		ex.preInsert();
		ex.setOperateStep(step);
		ex.setRemark(ex.getApplyReason());
		carLoanStatusHisDao.insertAgrLog(ex);
	}

	/**
	 * 电子协议申请列表
	 */
	public Page<CarLoanStatusHisEx> getEleAgreementList(Page<CarLoanStatusHisEx> page,CarLoanStatusHisEx ex){
			PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
			PageList<CarLoanStatusHisEx> pageList = (PageList<CarLoanStatusHisEx>) carLoanStatusHisDao
					.getCustomerMsg(pageBounds, ex);
			PageUtil.convertPage(pageList, page);
			return page;
	}	
	
	/**
	 * 电子协议申请列表
	 * 发送/拒绝
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateSendOrReturn(CarLoanStatusHisEx ex){
//		//如果是批量发送，执行
		if(StringUtils.isNotEmpty(ex.getIds())){
			ex.setApplyIdList(Arrays.asList(ex.getIds().split(",")));
			List<CarLoanStatusHisEx> s = carLoanStatusHisDao.getCustomerMsg(ex);
			if(ListUtils.isNotEmptyList(s)){
				for(CarLoanStatusHisEx cex : s){
					List<CarLoanStatusHisEx> fileMsgs = carLoanStatusHisDao.getFileNameList(cex);
					String [] docIds = new String[fileMsgs.size()];
					if(ListUtils.isNotEmptyList(fileMsgs)){
						for(int i=0;i<fileMsgs.size();i++){
							CarLoanStatusHisEx carEx = fileMsgs.get(i);
							if(StringUtils.isNotEmpty(carEx.getSignDocId()))
								docIds[i] = carEx.getSignDocId();
						}
					}
					String remark = "成功";
					ex.setId(cex.getId());
					ex.setContractCode(cex.getContractCode());
					//判断邮箱是否为空，不为空执行
					if(StringUtils.isNotEmpty(cex.getCustomerEmail())){
						//调用发送邮件方法，如果返回true，发送成功，如果返回false，发送失败
						//发送失败设置为协议类型为拒绝
						boolean b = sendMail(cex.getCustomerEmail(),docIds,cex.getLoanCustomerName(),cex.getContractCode());
				        if(!b){
				        	ex.setAgreementType(YESNO.NO.getCode());
				        	remark = "失败";
				        }
					}
					//如果邮箱为空，直接设置为拒绝状态
					else{
			        	ex.setAgreementType(YESNO.NO.getCode());
			        	remark = "失败";
					}
					//更新电子协议状态
					carLoanStatusHisDao.updateSendOrReturn(ex);
					ex.preInsert();
					ex.setOperateStep("批量发送");
					ex.setRemark(remark);
					carLoanStatusHisDao.insertAgrLog(ex);
				}
			}
		}
		//如果是发送电子协议，执行
		else if(StringUtils.isNotEmpty(ex.getId())){
			String step = "退回";
			String remark = "成功";
			//判断是否发送，如果是发送则发送邮件
			if(ex.getAgreementType().equals(CarLoanSteps.RECHECK_AUDIT.getCode()))
			{
				List<CarLoanStatusHisEx> fileMsgs = carLoanStatusHisDao.getFileNameList(ex);
				String [] docIds = new String[fileMsgs.size()];
				if(ListUtils.isNotEmptyList(fileMsgs))
				{
					if(ListUtils.isNotEmptyList(fileMsgs)){
						for(int i=0;i<fileMsgs.size();i++){
							CarLoanStatusHisEx carEx = fileMsgs.get(i);
							if(StringUtils.isNotEmpty(carEx.getSignDocId()))
								docIds[i] = carEx.getSignDocId();
						}
					}
					boolean b = sendMail(ex.getCustomerEmail(),docIds,ex.getLoanCustomerName(),ex.getContractCode());
			        if(!b){
			        	ex.setAgreementType(YESNO.NO.getCode());
				        remark = "失败";
			        }
				}
		        step = "发送电子协议";
			}
			carLoanStatusHisDao.updateSendOrReturn(ex);
			ex.preInsert();
			ex.setOperateStep(step);
			ex.setRemark(remark+"<br/>原因："+ex.getDictDealReason());
			carLoanStatusHisDao.insertAgrLog(ex);
		}
	}

	/**
	 * 实时发送邮件
	 * @param recp 收件人地址
	 * @param attach 附件
	 * @param subject 主题
	 * @param content 邮件内容
	 */
	public boolean sendMail(String recp,String [] attach,String subject,String contractCode){
		ClientPoxy service = new ClientPoxy(ServiceType.Type.SEND_MAIL);
		// 发送邮件
		MailInfo mailInfo = new MailInfo();
		// 收件人地址
		String[] toAddrArray = {recp};
		mailInfo.setToAddrArray(toAddrArray);
		// 邮件附件
		if(attach.length > 0){
			mailInfo.setDocIdArray(attach);
			// chp3.0附件
			mailInfo.setDocType("1");
		}		
		//查询数据库设置的模板
		Map<String, String> m = carLoanStatusHisDao.getEmailTemplate(CarLoanSteps.RECHECK_AUDIT.getCode());

		// 邮件主题
		mailInfo.setSubject(m.get("email_description").replaceAll("customerName", subject));
		// 邮件内容
		String startImg = "<img src='http://adapter.prod.creditharmony.cn/chp-adapter-web/static/images/hjlogo.jpg'></br></br>";
		String endImg = "<br></br><img src='http://adapter.prod.creditharmony.cn/chp-adapter-web/static/images/ranmeizhiji.jpg'>";
		StringBuffer s = new StringBuffer();
		String emailContent = m.get("template_content"); 
		s.append(emailContent.replaceAll("startImg", startImg).replaceAll("customerName", subject).replaceAll("contractCode", contractCode).replaceAll("endImg", endImg));
		mailInfo.setContent(s.toString());
		//发送
		MailOutInfo mailOutInfo = (MailOutInfo) service.callService(mailInfo); 
		if (ReturnConstant.SUCCESS.equals(mailOutInfo.getRetCode())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * 申请电子协议
	 * 显示历史
	 */
	public  List<CarLoanStatusHisEx> selectActAgrLogList(String contractCode){
		return carLoanStatusHisDao.selectActAgrLogList(contractCode);
	}

	/**
	 * 查询车借划扣已办列表 2016年11月17日 By 高欢
	 * 
	 * @param page
	 * @param carLoanStatusHisEx
	 * @param isIn
	 *            in前是否加not（仅限节点查询前），为1，则不加，其他，则在in前加not
	 * @param isQueryAll
	 *            是否查询全部（主要用于车借数据列表），为1，则查询全部，若为其他，则查询部分
	 * @param nodeValueLists
	 *            节点列表值，用逗号","隔开，如：1,2,3等，适用于in内部（仅限节点查询前）
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarLoanStatusHisEx> findDrawDoneList(Page page,
			CarLoanStatusHisEx carLoanStatusHisEx, String isIn,
			String isQueryAll, String nodeValueLists, String grossFlag) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());
		pageBounds.setCountBy("apply_Id");
		pageBounds.setFilterOrderBy(BooleanType.FALSE);
		if ("1".equals(carLoanStatusHisEx.getIsAllData())) {
			pageBounds.setLimit(99999);
			page.setPageSize(999999999);
		}
		carLoanStatusHisEx.setIsIn(isIn);
		carLoanStatusHisEx.setIsQueryAll(isQueryAll);
		List<String> nodeValLists = null;
		if (nodeValueLists != null && !"".equals(nodeValueLists)
				&& nodeValueLists.split(",").length > 0) {
			nodeValLists = new ArrayList<String>();
			for (String val : nodeValueLists.split(",")) {
				nodeValLists.add(val.trim());
			}
		}
		carLoanStatusHisEx.setNodeValList(nodeValLists);

		String storeList = carLoanStatusHisEx.getStoreCode();
		List<String> storeCodeList = Lists.newArrayList();
		// 门店查询传入storeCode格式为1,2,3，需要改为List<String>格式，便于sql查询
		if (storeList != null && storeList.length() > 0) {
			storeCodeList = new ArrayList<String>();
			for (String storeCode : storeList.split(",")) {
				storeCodeList.add(storeCode.trim());
			}
		}
		carLoanStatusHisEx.setStoreCodeList(storeCodeList);
		carLoanStatusHisEx.setGrossFlag(grossFlag);
		
		Map<String, Object> filter = JsonMapper.nonDefaultMapper()
				.convertValue(carLoanStatusHisEx, Map.class);
	
		filter.put("urgeDecuteDateStart", carLoanStatusHisEx.getUrgeDecuteDateStart());
		Date urgeDecuteDateEnd = carLoanStatusHisEx.getUrgeDecuteDateEnd();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		if(urgeDecuteDateEnd != null){
			try {
				Date date = sdf.parse(sdf.format(urgeDecuteDateEnd));
				Calendar instance = Calendar.getInstance();
				instance.setTime(date);
				instance.add(Calendar.DAY_OF_MONTH, 1);
				filter.put("urgeDecuteDateEnd", instance.getTime());
			} catch (ParseException e) {
				logger.error("划扣已办查询时划扣日期转化异常", e);
			}
		}
		String lendingTimeStart = carLoanStatusHisEx.getLendingTimeStart();
		if(!StringUtils.isEmpty(lendingTimeStart)){
			try {
				lendingTimeStart = lendingTimeStart.substring(0, 10) + " 00:00:00";
				Date parse = sdf.parse(lendingTimeStart);
				filter.put("lendingTimeStart", parse);
			} catch (ParseException e) {
				logger.error("已办查询放款日期转化异常", e);
			}
		}
		String lendingTimeEnd = carLoanStatusHisEx.getLendingTimeEnd();
		if(!StringUtils.isEmpty(lendingTimeEnd)){
			try {
				lendingTimeEnd = lendingTimeEnd.substring(0, 10) + " 00:00:00";
				Date parse = sdf.parse(lendingTimeEnd);
				Calendar instance = Calendar.getInstance();
				instance.setTime(parse);
				instance.add(Calendar.DAY_OF_MONTH, 1);
				filter.put("lendingTimeEnd", instance.getTime());
			} catch (ParseException e) {
				logger.error("已办查询放款日期转化异常", e);
			}
		}
		
		PageList<CarLoanStatusHisEx> pageList = (PageList<CarLoanStatusHisEx>) carLoanStatusHisDao
				.findDrawDoneList(filter, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
}