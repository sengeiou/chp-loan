package com.creditharmony.loan.borrow.payback.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.SpringContextHolder;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.mybatis.sql.MyBatisSql;
import com.creditharmony.core.mybatis.util.MyBatisSqlUtil;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesBackMoney;
import com.creditharmony.loan.borrow.grant.entity.UrgeServicesMoney;
import com.creditharmony.loan.borrow.payback.dao.PaybackDoneDao;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackMonth;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.service.PaybackService;

/**
 * 结清已办页面控制器
 * @Class Name PaybackDoneService
 * @author 赵金平
 * @Create In 2016年4月25日
 */
@Service("paybackDoneService")
public class PaybackDoneService {
	
	private static Logger logger = LoggerFactory.getLogger(PaybackDoneService.class);
	
	@Autowired
	public PaybackService paybackService;
	@Autowired
	public ConfirmPaybackService confirmPaybackService;
	@Autowired
	public PaybackDoneDao paybackDoneDao;
	
	/**
	 * 结清已办列表
	 * 2016年5月30日
	 * By 赵金平
	 * @param page
	 * @param payback
	 * @return
	 */
	@Transactional(value = "loanTransactionManager", readOnly = true)
	public Page<Payback> findPaybackList(Page<Payback> page, Payback payback) {
		BigDecimal bgSum = new BigDecimal("0.00");
		Page<Payback> paybackPage = findPayback(page, payback);
		if (ArrayHelper.isNotEmpty(paybackPage.getList()) && paybackPage.getList().size() > 0) {
			List<Org> orgList = OrgCache.getInstance().getList();
			for (int i = 0; i < paybackPage.getList().size(); i++) {
				// 设置返款金额(放款金额),实收催收服务费金额
				UrgeServicesMoney urgeServicesMoney = paybackPage.getList().get(i).getUrgeServicesMoney();
				if(ObjectHelper.isEmpty(urgeServicesMoney)){
					urgeServicesMoney = new UrgeServicesMoney();
					urgeServicesMoney.setUrgeMoeny(bgSum);
					urgeServicesMoney.setUrgeDecuteMoeny(bgSum);
					paybackPage.getList().get(i).setUrgeServicesMoney(urgeServicesMoney);
					
				}else{
					BigDecimal urgeMoney = urgeServicesMoney.getUrgeMoeny();
					if(ObjectHelper.isEmpty(urgeMoney)){
						paybackPage.getList().get(i).getUrgeServicesMoney().setUrgeMoeny(bgSum);
					}
					BigDecimal urgeDecuteMoeny = urgeServicesMoney.getUrgeDecuteMoeny();
					if(ObjectHelper.isEmpty(urgeDecuteMoeny)){
						paybackPage.getList().get(i).getUrgeServicesMoney().setUrgeDecuteMoeny(bgSum);
					}
				}
				Contract contract = paybackPage.getList().get(i).getContract();
				if(!ObjectHelper.isEmpty(contract)){
					// 设置列表中的门店名称的显示
					if (!ObjectHelper.isEmpty(paybackPage.getList().get(i).getLoanInfo()) && StringUtils.isNotEmpty(paybackPage.getList().get(i).getLoanInfo().getLoanStoreOrgId())) {
						for (Org org : orgList) {
							if(paybackPage.getList().get(i).getLoanInfo().getLoanStoreOrgId().equals(org.getId())) {
								paybackPage.getList().get(i).getLoanInfo().setLoanStoreOrgName(org.getName());
								break;
							}
						}
					// 门店
					/*	paybackPage.getList().get(i).getLoanInfo().setLoanStoreOrgName(
							String.valueOf(OrgCache.getInstance().get(paybackPage.getList().get(i).getLoanInfo().getLoanStoreOrgId())));
					*/
					}
				PaybackMonth paybackMonth = new PaybackMonth();
				paybackMonth.setContractCode(paybackPage.getList().get(i).getContractCode());
				paybackMonth = paybackDoneDao.findPaybackMonthSum(paybackMonth);
				String contractVersion = contract.getContractVersion();
				paybackMonth.setMonths(paybackPage.getList().get(i).getPaybackCurrentMonth());
				paybackMonth = confirmPaybackService.findDefaultConfirmInfo(paybackMonth,contractVersion);
				paybackPage.getList().get(i).setPaybackMonth(paybackMonth);
				}
			}
		}
		return paybackPage;
	}
	
	/**
	 * 结清已办列表导出
	 * 2016年08月04日
	 * By zhaowg
	 * @param payback
	 * @return List<Payback>
	 */
	public List<Payback> findPaybackList(Payback payback) {
		List<Payback> dataList = new ArrayList<Payback>();
		try {
			BigDecimal bgSum = new BigDecimal("0.00");
			// 使用
			SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) SpringContextHolder.getBean("sqlSessionFactory");
			SqlSession sqlSession = sqlSessionFactory.openSession();
			Connection connection = sqlSession.getConnection();
			MyBatisSql batisSql = MyBatisSqlUtil.getMyBatisSql(
				"com.creditharmony.loan.borrow.payback.dao.PaybackDoneDao.findPayback",
				payback, sqlSessionFactory);
			PreparedStatement ps = connection.prepareStatement(batisSql.toString());
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Payback p = new Payback();
				//
				LoanCustomer loanCustomer = new LoanCustomer();
				p.setLoanCustomer(loanCustomer);
				// 
				LoanInfo loanInfo = new LoanInfo();
				p.setLoanInfo(loanInfo);
				//
				Contract contract = new Contract();
				p.setContract(contract);
				//
				UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
				p.setUrgeServicesMoney(urgeServicesMoney);
				//
				LoanGrant loanGrant = new LoanGrant();
				p.setLoanGrant(loanGrant);
				//
				p.setContractCode(resultSet.getString("contract_code"));
				p.getLoanCustomer().setCustomerName(resultSet.getString("loanCustomer.customerName"));
				p.getLoanInfo().setLoanStoreOrgId(resultSet.getString("loanInfo.loanStoreOrgId"));
				// 合同版本号
				p.getContract().setContractVersion(resultSet.getString("contract.contractVersion"));
				if(null != resultSet.getString("contract.contractAmount")) {
					p.getContract().setContractAmount(new BigDecimal(resultSet.getString("contract.contractAmount")));
				}
				if(null != resultSet.getString("urgeServicesMoney.urgeMoeny")) {
					p.getUrgeServicesMoney().setUrgeMoeny(new BigDecimal(resultSet.getString("urgeServicesMoney.urgeMoeny")));
				}
				if(null != resultSet.getString("urgeServicesMoney.urgeDecuteMoeny")) {
					p.getUrgeServicesMoney().setUrgeDecuteMoeny(new BigDecimal(resultSet.getString("urgeServicesMoney.urgeDecuteMoeny")));
				}
				if(null != resultSet.getString("loanGrant.grantAmount")) {
					p.getLoanGrant().setGrantAmount(new BigDecimal(resultSet.getString("loanGrant.grantAmount")));
				}
				if(null != resultSet.getString("contract.contractMonths")) {
					p.getContract().setContractMonths(new BigDecimal(resultSet.getString("contract.contractMonths")));
				}
				if(null != resultSet.getDate("contract.contractReplayDay")) {
					p.getContract().setContractReplayDay(DateUtils.convertDate(resultSet.getDate("contract.contractReplayDay").toString()));

				}
				if(null != resultSet.getString("payback_max_overduedays")) {
					p.setPaybackMaxOverduedays(Integer.valueOf(resultSet.getString("payback_max_overduedays")));
				}
				// 当前月份
				if(null != resultSet.getString("payback_current_month")) {
					p.setPaybackCurrentMonth(Integer.valueOf(resultSet.getString("payback_current_month")));
				}
				// 
				if(null != resultSet.getDate("modify_time")) {
					p.setModifyTime(DateUtils.convertDate(resultSet.getDate("modify_time").toString()));
				}
				p.setDictPayStatus(resultSet.getString("dict_pay_status"));
				// p.getPaybackMonth().setCreditAmount()
				p.getLoanInfo().setLoanFlag(resultSet.getString("loanInfo.loanFlag"));
				p.getLoanInfo().setModel(resultSet.getString("loanInfo.model"));
				dataList.add(p);
			}
			if (ArrayHelper.isNotEmpty(dataList) && dataList.size() > 0) {
				List<Org> orgList = OrgCache.getInstance().getList();
				for (int i = 0; i < dataList.size(); i++) {
					// 设置返款金额(放款金额),实收催收服务费金额
					UrgeServicesMoney urgeServicesMoney = dataList.get(i).getUrgeServicesMoney();
					if(ObjectHelper.isEmpty(urgeServicesMoney)){
						urgeServicesMoney = new UrgeServicesMoney();
						urgeServicesMoney.setUrgeMoeny(bgSum);
						urgeServicesMoney.setUrgeDecuteMoeny(bgSum);
						dataList.get(i).setUrgeServicesMoney(urgeServicesMoney);
					}else{
						BigDecimal urgeMoney = urgeServicesMoney.getUrgeMoeny();
						if(ObjectHelper.isEmpty(urgeMoney)){
							dataList.get(i).getUrgeServicesMoney().setUrgeMoeny(bgSum);
						}
						BigDecimal urgeDecuteMoeny = urgeServicesMoney.getUrgeDecuteMoeny();
						if(ObjectHelper.isEmpty(urgeDecuteMoeny)){
							dataList.get(i).getUrgeServicesMoney().setUrgeDecuteMoeny(bgSum);
						}
					}
					Contract contract = dataList.get(i).getContract();
					if(!ObjectHelper.isEmpty(contract)){
						// 设置列表中的门店名称的显示
						if (!ObjectHelper.isEmpty(dataList.get(i).getLoanInfo()) && StringUtils.isNotEmpty(dataList.get(i).getLoanInfo().getLoanStoreOrgId())) {
							for (Org org : orgList) {
								if(dataList.get(i).getLoanInfo().getLoanStoreOrgId().equals(org.getId())) {
									dataList.get(i).getLoanInfo().setLoanStoreOrgName(org.getName());
									break;
								}
							}
							/*// 门店
							dataList.get(i).getLoanInfo().setLoanStoreOrgName(
								String.valueOf(OrgCache.getInstance().get(dataList.get(i).getLoanInfo().getLoanStoreOrgId())));*/
						}
						PaybackMonth paybackMonth = new PaybackMonth();
						paybackMonth.setContractCode(dataList.get(i).getContractCode());
						
						paybackMonth.setContractCode(dataList.get(i).getContractCode());
						paybackMonth = paybackDoneDao.findPaybackMonthSum(paybackMonth);
						paybackMonth.setMonths(dataList.get(i).getPaybackCurrentMonth());
						
						String contractVersion = contract.getContractVersion();
						paybackMonth = confirmPaybackService.findDefaultConfirmInfo(paybackMonth,contractVersion);
						dataList.get(i).setPaybackMonth(paybackMonth);
					}
				}
			}
		} catch (Exception e) {
			logger.error("结清已办列表导出excel查询数据异常。",e); 
		}
		return dataList;
	}
	
	/**
	 * 结清已办列表查看详情页面信息
	 * 2016年5月30日
	 * By 赵金平
	 * @param contractCode
	 * @return
	 */
	@Transactional(value = "loanTransactionManager" ,readOnly = true)
	public List<Payback> goConfirmPaybackFormInfo(String contractCode){
		Payback payback = new Payback();
		payback.setContractCode(contractCode);
		// 设置查询有效的催收服务费金额
		UrgeServicesMoney urgeServicesMoney = new UrgeServicesMoney();
		urgeServicesMoney.setReturnLogo(YESNO.NO.getCode());
		payback.setUrgeServicesMoney(urgeServicesMoney);
		List<Payback> paybackList = paybackDoneDao.findPayback(payback);
		if (ArrayHelper.isNotEmpty(paybackList)) {
			// 设置已收催收服务费金额
			 urgeServicesMoney = paybackList.get(0).getUrgeServicesMoney();
			 if(ObjectHelper.isEmpty(urgeServicesMoney)){
				 UrgeServicesMoney um = new UrgeServicesMoney();
				 um.setUrgeDecuteMoeny(new BigDecimal("0.00"));
				 paybackList.get(0).setUrgeServicesMoney(um);
			 }
			// 如果催收服务费返款信息表中的返款金额不为空
			UrgeServicesBackMoney urgeServicesBackMoney = confirmPaybackService
					.getUrgeBackAmount(contractCode);
			if (!ObjectHelper.isEmpty(urgeServicesBackMoney)) {
				paybackList.get(0).setPaybackBackAmount(
						urgeServicesBackMoney.getPaybackBackMoney());
			}
			// 设置列表中的门店名称的显示
			if (paybackList.get(0).getLoanInfo() == null ) {
				LoanInfo loanInfo = new LoanInfo();
				loanInfo.setLoanStoreOrgId(null);
				paybackList.get(0).setLoanInfo(loanInfo);
			} else {
				String orgId = paybackList.get(0).getLoanInfo().getLoanStoreOrgId();
				if(StringUtils.isNotEmpty(orgId)){
					paybackList.get(0).getLoanInfo().setLoanStoreOrgId(
					String.valueOf(OrgCache.getInstance().get(
					paybackList.get(0).getLoanInfo().getLoanStoreOrgId())));
				}
			}
			Contract contract = paybackList.get(0).getContract();
			if (!ObjectHelper.isEmpty(contract)) {
				PaybackMonth paybackMonth = new PaybackMonth();
				
				paybackMonth.setContractCode(paybackList.get(0)
						.getContractCode());
				paybackMonth = paybackDoneDao.findPaybackMonthSum(paybackMonth);
				String contractVersion = contract.getContractVersion();
				paybackMonth.setMonths(paybackList.get(0)
						.getPaybackCurrentMonth());

				paybackMonth = confirmPaybackService.findDefaultConfirmInfo(
						paybackMonth, contractVersion);
				if (!ObjectHelper.isEmpty(paybackMonth)) {
					paybackList.get(0).setPaybackMonth(paybackMonth);
				}
			} 
			// 减免人
			if(StringUtils.isNotEmpty(paybackList.get(0).getModifyBy()) && paybackList.get(0).getModifyBy().getBytes().length == paybackList.get(0).getModifyBy().length()){
				if(ObjectHelper.isNotEmpty(UserUtils.get(paybackList.get(0).getModifyBy())) && StringUtils.isNotEmpty(UserUtils.get(paybackList.get(0).getModifyBy()).getName())){
					paybackList.get(0).setRemissionBy(UserUtils.get(paybackList.get(0).getModifyBy()).getName());
				}
			}
			// 查询结清确认时输入的审核意见
			PaybackOpe paybackOpe = new PaybackOpe();
			paybackOpe.setrPaybackId(paybackList.get(0).getId());
			paybackOpe.setDictLoanStatus(RepaymentProcess.CONFIRM.getCode());
			//paybackOpe.setDictRDeductType(TargetWay.REPAYMENT.getCode());
			//paybackOpe.setOperateResult(PaybackOperate.CONFIRM_SUCCEED.getCode());
			List<PaybackOpe> paybackOpeList = confirmPaybackService
					.getConfirmedRemark(paybackOpe);
			if (ArrayHelper.isNotEmpty(paybackOpeList)) {
				if (!ObjectHelper.isEmpty(paybackOpeList.get(0))) {
					paybackList.get(0).setRemarks(
							paybackOpeList.get(0).getRemark());
				}
			}
		} 
		return paybackList;
	}
	
    /**
     * 结清已办列表查看页面
     * 2016年5月30日
     * By 赵金平
     * @param page
     * @param payback
     * @return
     */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<Payback> findPayback(Page<Payback> page, Payback payback) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		pageBounds.setCountBy("paybackId");
		PageList<Payback> pageList = (PageList<Payback>) paybackDoneDao.findPayback(payback, pageBounds);
		PageUtil.convertPage(pageList, page);
		return page;
	}
}
