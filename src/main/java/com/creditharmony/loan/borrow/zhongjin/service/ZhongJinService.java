package com.creditharmony.loan.borrow.zhongjin.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.deduct.TaskService;
import com.creditharmony.core.deduct.bean.in.DeductReq;
import com.creditharmony.core.deduct.bean.out.DeResult;
import com.creditharmony.core.deduct.bean.out.LoanDeductEntity;
import com.creditharmony.core.deduct.type.DeductFlagType;
import com.creditharmony.core.deduct.type.DeductPlatType;
import com.creditharmony.core.deduct.type.DeductType;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.deduct.type.ResultType;
import com.creditharmony.core.deduct.type.TaskStatusType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.loan.borrow.zhongjin.dao.ZhongJinDao;
import com.creditharmony.loan.borrow.zhongjin.entity.PaybackCpcnIn;
import com.creditharmony.loan.borrow.zhongjin.entity.PaybackCpcnOut;
import com.creditharmony.loan.borrow.zhongjin.view.History;
import com.creditharmony.loan.borrow.zhongjin.view.PaybackCpcn;
import com.creditharmony.loan.borrow.zhongjin.view.PaybackCpcnModel;
import com.creditharmony.loan.borrow.zhongjin.view.PaybackOrder;
import com.google.common.collect.Lists;

/**
 * 中金划扣
 * 
 * @Class Name ZhongJinDao
 * @author WJJ
 * @Create In 2016年3月3日
 */
@Service
public class ZhongJinService {

	@Autowired
	private ZhongJinDao zhongJinDao;
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取列表总条数和总金额
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<Map<String, Object>> getSelectCount(PaybackCpcnModel params) {
		return zhongJinDao.getSelectCount(params);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 分页中金列表
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<PaybackCpcn> getListByParam(Page<PaybackCpcn> page,
			PaybackCpcnModel params) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),
				page.getPageSize());

		PageList<PaybackCpcn> pageList = (PageList<PaybackCpcn>) zhongJinDao
				.selectByParam(pageBounds, params);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 不分页中金列表
	 */
	public List<PaybackCpcn> getListByParam(PaybackCpcnModel params) {
		return zhongJinDao.findByParams(params);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 中金划扣导出Excel数据
	 */
	public List<PaybackCpcnOut> exportList(PaybackCpcnModel params) {
		return zhongJinDao.exportList(params);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 按序号查询个数
	 */
	public long getCount(String serialNum) {
		return zhongJinDao.getCount(serialNum);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 添加中金数据
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void save(PaybackCpcn params) {
		Date dt = new Date(new Date().getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(dt);
		params.setNowDate(date);
		params.setCpcnId(IdGen.uuid());
		zhongJinDao.insert(params);
		History h = new History();
		h.setId(IdGen.uuid());
		h.setCpcnId(params.getCpcnId());
		h.setOperName("添加中金信息");
		h.setOperResult("成功");
		h.setOperNotes("添加中金信息");
		h.setCreateBy(params.getCreatuserId());
		h.setModifyBy(params.getCreatuserId());
		this.addHistory(h);
		// saveLog(entity.getId(), user, "新增", null);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 修改中金数据
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void update(PaybackCpcn params) {
		zhongJinDao.update(params);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取省编号
	 */
	public List<String> getProvinceName(String provinceName) {
		return zhongJinDao.getProvinceName(provinceName);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取市级编号
	 */
	public List<String> getCityName(String provinceName, String cityName) {
		return zhongJinDao.getCityName(provinceName, cityName);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取银行编码
	 */
	public String getBankValue(String bankName) {
		return zhongJinDao.getBankValue(bankName);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取证件类型编码
	 */
	public String getCerTypeValue(String cerName) {
		return zhongJinDao.getCerTypeValue(cerName);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 导入中金数据
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertList(List<PaybackCpcnIn> payList) throws Exception {
		for (int i = 0; i < payList.size(); i++) {
			PaybackCpcnIn cpcnOut = payList.get(i);
			PaybackCpcn cpcn = new PaybackCpcn();
			cpcn.setCpcnId(IdGen.uuid());
			cpcn.setSerialNum(cpcnOut.getSerialNum());
			cpcn.setAccountNum(cpcnOut.getAccountNum());
			cpcn.setAccountName(cpcnOut.getAccountName());
			cpcn.setDealMoney(cpcnOut.getDealMoney());
			cpcn.setBankName(cpcnOut.getBankName());
			cpcn.setBankNum(cpcnOut.getBankNum());
			cpcn.setAccountType(cpcnOut.getAccountType());
			cpcn.setAccounProvice(cpcnOut.getAccounProvice());
			cpcn.setAccounCity(cpcnOut.getAccounCity());
			cpcn.setCertType(cpcnOut.getCertType());
			cpcn.setCertNum(cpcnOut.getCertNum());
			cpcn.setContractCode(cpcnOut.getContractCode());
			cpcn.setNote(cpcnOut.getNote());
			cpcn.setCreatuserId(cpcnOut.getCreatuserId());

			cpcn.setAppoint("0");
			cpcn.setStatus("0");
			cpcn.setCreateTime(new Date());

			History h = new History();
			h.setId(IdGen.uuid());
			h.setCpcnId(cpcn.getCpcnId());
			h.setOperName("导入中金信息");
			h.setOperResult("成功");
			h.setOperNotes("导入中金信息");
			h.setCreateBy(cpcn.getCreatuserId());
			h.setModifyBy(cpcn.getCreatuserId());
			this.addHistory(h);
			zhongJinDao.insert(cpcn);
		}
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 查询中金划扣数据，并发送
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void bathUpdateStatus(List<PaybackCpcn> entities, User user){
		List<DeductReq> list = Lists.newArrayList();
		String batch_sn = IdGen.uuid();
		for (PaybackCpcn entity : entities) {
			String flowname = "";
			if ("1".equals(entity.getStatus())) {
				flowname = "发送中金实时";
				entity.setBackResult("3");
				entity.setBatchSN(batch_sn);
			} else if ("2".equals(entity.getStatus())) {
				flowname = "发送中金批量";
				entity.setBackResult("3");
				entity.setBatchSN(batch_sn);
			} else if ("3".equals(entity.getStatus())) {
				flowname = "放弃";
				// entity.setBackResult("4");
			}
			entity.setOpearTime(new Date());
			if (entity.getAccountNum() == null) {
				PaybackCpcnModel pm = new PaybackCpcnModel();
				pm.setId(entity.getCpcnId());
				pm.setDeductStatus("1");
				/*if ("1".equals(entity.getStatus())) {
					pm.setDeductStatus("1");
				}
				if ("3".equals(entity.getStatus())) {
					pm.setDeductStatus("2");
				}*/
				List<PaybackCpcn> pcList = zhongJinDao.findByParams(pm);
				if (!pcList.isEmpty()) {
					PaybackCpcn pc = pcList.get(0);
					DeductReq deductReqList = new DeductReq();
					deductReqList.setRemarks("线下划扣");
					deductReqList.setAccountName(pc.getAccountName());// 账户名称
					deductReqList.setAccountNo(pc.getAccountNum());// 账号
					deductReqList.setAmount(pc.getDealMoney());// 金额
					deductReqList.setBankCity(pc.getAccounCity());// 支行市
					deductReqList.setBankId(pc.getBankNum());// 银行ID
					deductReqList.setBankName(pc.getBankName());// 支行名
					deductReqList.setBankProv(pc.getAccounProvice());// 支行省
					deductReqList.setBatId(pc.getCpcnId());// 请求批次ID
					deductReqList.setBusinessId(pc.getContractCode());// 业务信息用（汇金合同号，财富出借编号等）
					deductReqList.setDeductFlag(DeductFlagType.COLLECTION.getCode());// 划扣标志
					deductReqList.setIdNo(pc.getCertNum());// 证件编号
					deductReqList.setIdType(pc.getCertType());// 证件类型
					deductReqList.setMobile("");// 联系方式(手机号码)
					deductReqList.setRefId(pc.getCpcnId());// 关联ID
					//deductReqList.setRequestId(batch_sn);// 请求ID
					deductReqList.setStatus(TaskStatusType.PENDING.getCode());// 状态(0:待处理,1:处理中,2:已处理)
					deductReqList.setSysId(DeductWays.ZJ_01.getCode());// 系统处理ID
					if ("1".equals(entity.getStatus())) {
						deductReqList.setRule(DeductPlat.ZHONGJIN.getCode()+ ":" + DeductType.REALTIME.getCode());// 规则
						list.add(deductReqList);
					}
					if ("2".equals(entity.getStatus())) {
						deductReqList.setRule(DeductPlat.ZHONGJIN.getCode()+ ":" + DeductType.BATCH.getCode());// 规则
						list.add(deductReqList);
					}
					DeResult t = TaskService.addTask(deductReqList);
					try {
						if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
							TaskService.commit(t.getDeductReq());
							zhongJinDao.update(entity);
							History h = new History();
							h.setId(IdGen.uuid());
							h.setCpcnId(pc.getCpcnId());
							h.setOperName(flowname);
							h.setOperResult("成功");
							h.setOperNotes(flowname);
							h.setCreateBy(user.getId());
							h.setModifyBy(user.getId());
							this.addHistory(h);
						} else {
							TaskService.rollBack(t.getDeductReq());
						}
					} catch (Exception e) {
						TaskService.rollBack(t.getDeductReq());
					}
					
				}
			} else {
				DeductReq deductReqList = new DeductReq();
				deductReqList.setRemarks("线下划扣");
				deductReqList.setAccountName(entity.getAccountName());// 账户名称
				deductReqList.setAccountNo(entity.getAccountNum());// 账号
				deductReqList.setAmount(entity.getDealMoney());// 金额
				deductReqList.setBankCity(entity.getAccounCity());// 支行市
				deductReqList.setBankId(entity.getBankNum());// 银行ID
				deductReqList.setBankName(entity.getBankName());// 支行名
				deductReqList.setBankProv(entity.getAccounProvice());// 支行省
				deductReqList.setBatId(entity.getCpcnId());// 请求批次ID
				deductReqList.setBusinessId(entity.getContractCode());// 业务信息用（汇金合同号，财富出借编号等）
				deductReqList.setDeductFlag(DeductFlagType.COLLECTION.getCode());// 划扣标志
				deductReqList.setIdNo(entity.getCertNum());// 证件编号
				deductReqList.setIdType(entity.getCertType());// 证件类型
				deductReqList.setMobile("");// 联系方式(手机号码)
				deductReqList.setRefId(entity.getCpcnId());// 关联ID
				//deductReqList.setRequestId(batch_sn);// 请求ID
				deductReqList.setStatus(TaskStatusType.PENDING.getCode());// 状态(0:待处理,1:处理中,2:已处理)
				deductReqList.setSysId(DeductWays.ZJ_01.getCode());// 系统处理ID
				// deductReqList.setUnsplitAmount(unsplitAmount);//未拆分金额
				// deductReqList.setUnsplitTimes(unsplitTimes);//剩余可拆分次数
				if ("1".equals(entity.getStatus())) {
					deductReqList.setRule(DeductPlat.ZHONGJIN.getCode()+ ":" + DeductType.REALTIME.getCode());// 规则
					list.add(deductReqList);
				}
				if ("2".equals(entity.getStatus())) {
					deductReqList.setRule(DeductPlat.ZHONGJIN.getCode()+ ":" + DeductType.BATCH.getCode());// 规则
					list.add(deductReqList);
				}
				DeResult t = TaskService.addTask(deductReqList);
				try {
					if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
						TaskService.commit(t.getDeductReq());
						PaybackCpcn cpcn = new PaybackCpcn();
						cpcn.setCpcnId(entity.getCpcnId());
						cpcn.setStatus(entity.getStatus());
						cpcn.setBatchSN(batch_sn);
						cpcn.setOpearTime(new Date());
						cpcn.setBackResult(entity.getBackResult());
						zhongJinDao.update(cpcn);
						History h = new History();
						h.setId(IdGen.uuid());
						h.setCpcnId(entity.getCpcnId());
						h.setOperName(flowname);
						h.setOperResult("成功");
						h.setOperNotes(flowname);
						h.setCreateBy(user.getId());
						h.setModifyBy(user.getId());
						this.addHistory(h);
					} else {
						TaskService.rollBack(t.getDeductReq());
					}
				} catch (Exception e) {
					TaskService.rollBack(t.getDeductReq());
				}
			}
		}
		/*if (!list.isEmpty()) {
			sendZhongJin(list);
		}*/
	}
	
	
	/**
	 * 放弃
	 * @param entities
	 * @param user
	 */
	
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void giveUpdateStatus(List<PaybackCpcn> entities, User user){
		String batch_sn = IdGen.uuid();
		for (PaybackCpcn entity : entities) {
			PaybackCpcn cpcn = new PaybackCpcn();
			cpcn.setCpcnId(entity.getCpcnId());
			cpcn.setStatus(entity.getStatus());
			cpcn.setBatchSN(batch_sn);
			cpcn.setOpearTime(new Date());
			cpcn.setBackResult(entity.getBackResult());
			zhongJinDao.update(cpcn);
			History h = new History();
			h.setId(IdGen.uuid());
			h.setCpcnId(entity.getCpcnId());
			h.setOperName("放弃");
			h.setOperResult("成功");
			h.setOperNotes("放弃");
			h.setCreateBy(user.getId());
			h.setModifyBy(user.getId());
			this.addHistory(h);
		}
	}
	
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 发送中金划扣数据
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	private void sendZhongJin(List<DeductReq> list) {
		for (DeductReq req : list) {
			DeResult t = TaskService.addTask(req);
			try {
				if (t.getReCode().equals(ResultType.ADD_SUCCESS.getCode())) {
					TaskService.commit(t.getDeductReq());
				} else {
					TaskService.rollBack(t.getDeductReq());
				}
			} catch (Exception e) {
				TaskService.rollBack(t.getDeductReq());
			}

		}
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 添加中金划扣时间
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void insertPaybackOrder(PaybackOrder paybackOrder) {
		zhongJinDao.insertPaybackOrder(paybackOrder);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 添加中金历史
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void addHistory(History params) {
		zhongJinDao.addHistory(params);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 获取中金历史
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public List<History> getHistory(String cpcnId) {
		return zhongJinDao.getHistory(cpcnId);
	}
	/**
	 * 
	 * 2016年3月3日
	 * By WJJ
	 * @return 取消中金预约划扣
	 */
	@Transactional(readOnly = false,value="loanTransactionManager")
	public void delOrder(List<PaybackCpcn> list, String userId)
			throws Exception {
		for (int i = 0; i < list.size(); i++) {
			PaybackCpcn cpcn = list.get(0);
			PaybackCpcn p = new PaybackCpcn();
			p.setStatus("5");
			p.setCpcnId(cpcn.getCpcnId());
			p.setBackResult("");
			zhongJinDao.update(p);
			zhongJinDao.delOrder(cpcn.getCpcnId());

			History h = new History();
			h.setId(IdGen.uuid());
			h.setCpcnId(cpcn.getCpcnId());
			h.setOperName("取消预约");
			h.setOperResult("成功");
			h.setOperNotes("取消预约");
			h.setCreateBy(userId);
			h.setModifyBy(userId);
			this.addHistory(h);
		}
	}
	
	/**
	 * 
	 * 2016年3月3日
	 * By wbb
	 * @return 修改中金中金回盘
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateDeductResult(LoanDeductEntity deductResult) {
		PaybackCpcn params = new PaybackCpcn();
		params.setId(deductResult.getRefId());
		BigDecimal successMoney = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(deductResult.getDeductSucceedMoney()))
		{
			successMoney = new BigDecimal(deductResult.getDeductSucceedMoney());
		}
		BigDecimal applyMoney = new BigDecimal(deductResult.getApplyAmount());
		
		if(successMoney.compareTo(applyMoney)>=0)
		{
			params.setBackResult(CounterofferResult.PAYMENT_SUCCEED.getCode());
		}
		else
		{
			params.setBackResult(CounterofferResult.PAYMENT_FAILED.getCode());
		}
		params.setApplyReallyAmount(successMoney);
		params.setBackReason(deductResult.getFailReason());
		params.setBatchSN(deductResult.getBatId());
		params.setBackTime(new Date());
		params.setCpcnId(deductResult.getRefId());
		zhongJinDao.update(params);
		
		History h = new History();
		h.setId(IdGen.uuid());
		h.setCpcnId(params.getCpcnId());
		h.setOperName("回盘");
		h.setOperResult("成功");
		h.setOperNotes(params.getBackReason());
		h.setCreateBy("系统");
		h.setModifyBy("系统");
		zhongJinDao.addHistory(h);
	}

}
