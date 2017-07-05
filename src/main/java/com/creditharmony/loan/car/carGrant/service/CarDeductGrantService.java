package com.creditharmony.loan.car.carGrant.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.BigDecimalTools;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.lend.type.LendConstants;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.OperateMatching;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.UrgeCounterofferResult;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.loan.borrow.payback.dao.PaybackTransferOutDao;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.car.carGrant.ex.CarDeductCostRecoverEx;
import com.creditharmony.loan.car.carGrant.ex.CarPaybackTransferInfo;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeOpeEx;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesCheckApply;
import com.creditharmony.loan.car.carGrant.ex.CarUrgeServicesMoneyEx;
import com.creditharmony.loan.car.common.dao.CarDeductGrantDao;
import com.creditharmony.loan.car.common.dao.CarPaybackTransferDetailDao;
import com.creditharmony.loan.car.common.dao.CarUrgeOpeDao;
import com.creditharmony.loan.car.common.dao.CarUrgeServicesMoneyDao;
import com.creditharmony.loan.car.common.entity.CarUrgeOpe;
import com.creditharmony.loan.car.common.entity.CarUrgeServicesMoney;
import com.creditharmony.loan.common.constants.PaybackConstants;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.utils.CeUtils;

/**
 * 获取   划扣费用待追回列表
 * @Class Name CarDeductGrantService
 * @author 李静辉
 * @Create In 2016年2月29日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarDeductGrantService extends CoreManager<CarDeductGrantDao, CarDeductCostRecoverEx> {
	
	@Autowired
	private CarPaybackTransferDetailDao carPaybackTransferDetailDao;
	@Autowired
	private PaybackTransferOutDao paybackTransferOutDao;
	@Autowired
	private CarUrgeServicesMoneyDao urgeServiceDao;
	@Autowired
	private CarUrgeOpeDao carUrgeOpeDao;
	@Autowired
	private HistoryService historyService;
	
	/**
	 * 根据条件 获取划扣费用待追回列表
	 * 2016年2月29日
	 * By 李静辉
	 * @param page
	 * @param carDeductCostRecoverEx
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CarDeductCostRecoverEx> selectGuaranteeMoneyList(
			Page<CarDeductCostRecoverEx> page,
			CarDeductCostRecoverEx carDeductCostRecoverEx) {
			PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
			// 设置产品类型为车借
			carDeductCostRecoverEx.setCarProductsType(LendConstants.PRODUCTS_TYPE_CAR_CREDIT);
	        PageList<CarDeductCostRecoverEx> pageList = (PageList<CarDeductCostRecoverEx>)dao.selectGuaranteeMoneyList(carDeductCostRecoverEx, pageBounds);
	        PageUtil.convertPage(pageList, page);
	        return page;
	}
	
	/**
	 * 查询款项匹配列表
	 * 2016年6月20日
	 * By 朱静越
	 * @param page
	 * @param carDeductCostRecoverEx
	 * @return
	 */
	public Page<CarDeductCostRecoverEx> selCheckInfo(Page<CarDeductCostRecoverEx > page,CarDeductCostRecoverEx carDeductCostRecoverEx){
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		// 设置产品类型为车借
		carDeductCostRecoverEx.setCarProductsType(LendConstants.PRODUCTS_TYPE_CAR_CREDIT);
        PageList<CarDeductCostRecoverEx> pageList = (PageList<CarDeductCostRecoverEx>)dao.selCheckInfo(carDeductCostRecoverEx, pageBounds);
        PageUtil.convertPage(pageList, page);
        return page;
	}
	
	/**
	 * 查询不带分页的款项匹配列表
	 * 2016年6月20日
	 * By 朱静越
	 * @param carDeductCostRecoverEx
	 * @return
	 */
	public List<CarDeductCostRecoverEx> getUrgeList(CarDeductCostRecoverEx carDeductCostRecoverEx){
		// 设置产品类型为车借
		carDeductCostRecoverEx.setCarProductsType(LendConstants.PRODUCTS_TYPE_CAR_CREDIT);
		return dao.selCheckInfo(carDeductCostRecoverEx);
	}
	
	/**
	 * 查询不带分页的催收保证金待催收页面
	 * 2016年6月17日
	 * By 朱静越
	 * @param carDeductCostRecoverEx
	 * @return
	 */
	public List<CarDeductCostRecoverEx> selGuaranteeList(CarDeductCostRecoverEx carDeductCostRecoverEx){
		// 设置产品类型为车借
		carDeductCostRecoverEx.setCarProductsType(LendConstants.PRODUCTS_TYPE_CAR_CREDIT);
		return dao.selectGuaranteeMoneyList(carDeductCostRecoverEx);
	}
	
	/**
	 * 查询查账账款列表 
	 * 2016年6月17日
	 * By 朱静越
	 * @param carPaybackTransferInfo
	 * @return
	 */
	public List<CarPaybackTransferInfo> findUrgeTransfer(
			CarPaybackTransferInfo carPaybackTransferInfo) {
		return dao.findUrgeTransfer(carPaybackTransferInfo);
	}
	
	/**
	 * 查询查账申请表
	 * 2016年6月20日
	 * By 朱静越
	 * @param carUrgeServicesCheckApply
	 * @return
	 */
	public List<CarUrgeServicesCheckApply> findUrgeApplyList(CarUrgeServicesCheckApply carUrgeServicesCheckApply){
		return dao.findUrgeApplyList(carUrgeServicesCheckApply);
	}
	/**
	 * 发起催收服务费申请 by 朱静越
	 * @param files
	 * @param urgeServicesMoneyEx
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveFirstApply(MultipartFile[] files, CarDeductCostRecoverEx urgeServicesMoneyEx) {
		
		// 判断，如果是处理中导出的数据提交查账，需要将拆分表中的该单子的处理状态为处理中导出的删除，根据催收主表id
		CarUrgeServicesMoney urge = urgeServiceDao.find(urgeServicesMoneyEx.getUrgeId());
		if (UrgeCounterofferResult.PROCESSED.getCode().equals(urge.getDictDealStatus())) {
			CarUrgeServicesMoneyEx urgeServices = new CarUrgeServicesMoneyEx();
			urgeServices.setUrgeId("'" + urgeServicesMoneyEx.getUrgeId() + "'");
			urgeServices.setSplitResult(UrgeCounterofferResult.PROCESSED.getCode());
			List<CarUrgeServicesMoneyEx> delList = urgeServiceDao.selProcess(urgeServices);
			// 对拆分表的操作
			if (delList.size()>0) {
				for (int i = 0; i < delList.size(); i++) {
					urgeServiceDao.delProcess("'"+delList.get(i).getId()+"'");
				}
			}			
		}
		
		// 保存催收服务费申请表
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setUrgeApplyStatus(
				UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setUrgeMethod(
				RepayChannel.NETBANK_CHARGE.getCode());
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setUrgeReallyAmount(BigDecimal.ZERO);
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setIsNewRecord(false);
		urgeServicesMoneyEx.getUrgeServicesCheckApply().setDictDepositAccount(
				urgeServicesMoneyEx.getPaybackTransferInfo().getStoresInAccount());
		urgeServicesMoneyEx.getUrgeServicesCheckApply().preInsert();
		dao.saveUrgeApply(urgeServicesMoneyEx.getUrgeServicesCheckApply());
		
		// 保存催收服务费汇款数据
		this.savePayBackTransferInfo(urgeServicesMoneyEx, files);
		
		// 更新催收服务费主表
		saveUrgeServiceAmount(urgeServicesMoneyEx.getUrgeServicesCheckApply().getrServiceChargeId());
		
		// 催收服务费操作历史（申请,关联id为申请id）
		CarUrgeOpeEx paybackOpes = new CarUrgeOpeEx(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId(),
				RepaymentProcess.SEND_CHECK, TargetWay.SERVICE_FEE,
				PaybackOperate.APPLY_SUCEED.getCode(), "催收服务费发起查账申请，申请金额："
						+ urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeApplyAmount());
		// 插入历史，保证事务的同步
		CarUrgeOpe paybackOpe = new CarUrgeOpe();
		paybackOpe.setrUrgeId(paybackOpes.getrUrgeId());
		paybackOpe.setDictLoanStatus(paybackOpes.getDictLoanStatus());
		paybackOpe.setDictRDeductType(paybackOpes.getDictRDeductType());
		paybackOpe.setRemarks(paybackOpes.getRemark());
		paybackOpe.setOperateResult(paybackOpes.getOperateResult());
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		paybackOpe.preInsert();
		carUrgeOpeDao.insert(paybackOpe);
	}
	
	/**
	 * 待办发起催收服务费申请 by zhangfeng
	 * @param files
	 * @param urgeServicesMoneyEx
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateFirstApply(MultipartFile[] files,
			CarDeductCostRecoverEx urgeServicesMoneyEx) {

		CarUrgeServicesCheckApply urgeApply = new CarUrgeServicesCheckApply();
		urgeApply.setrServiceChargeId(urgeServicesMoneyEx.getUrgeId());
		urgeApply.setUrgeApplyAmount(urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeApplyAmount());
		urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
		urgeApply.setDictDepositAccount(urgeServicesMoneyEx.getPaybackTransferInfo().getStoresInAccount());
		urgeApply.preUpdate();
		dao.updateUrgeApply(urgeApply);

		// 删除旧文件和数据
		this.deleteTransferInfo(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
		
		// 新增汇款数据
		this.savePayBackTransferInfo(urgeServicesMoneyEx, files);
		
		// 更新催收服务费主表
		this.saveUrgeServiceAmount(urgeServicesMoneyEx.getUrgeServicesCheckApply().getrServiceChargeId());
		
		// 催收服务费申请操作历史（重新发起,关联id为）
		CarUrgeOpeEx paybackOpes = new CarUrgeOpeEx(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId(),
				RepaymentProcess.SEND_CHECK, TargetWay.SERVICE_FEE,
				PaybackOperate.APPLY_SUCEED.getCode(), "催收服务费重新发起查账申请，申请金额："
						+ urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeApplyAmount());
		// 插入历史，保证事务的同步
		CarUrgeOpe paybackOpe = new CarUrgeOpe();
		paybackOpe.setrUrgeId(paybackOpes.getrUrgeId());
		paybackOpe.setDictLoanStatus(paybackOpes.getDictLoanStatus());
		paybackOpe.setDictRDeductType(paybackOpes.getDictRDeductType());
		paybackOpe.setRemarks(paybackOpes.getRemark());
		paybackOpe.setOperateResult(paybackOpes.getOperateResult());
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		paybackOpe.preInsert();
		carUrgeOpeDao.insert(paybackOpe);
	}
	
	
	/**
	 * 更新info表中的内容 2015年12月11日 By zhujignyue
	 * 
	 * @param urgeServicesMoneyEx
	 * @param files
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void savePayBackTransferInfo(
			CarDeductCostRecoverEx urgeServicesMoneyEx, MultipartFile[] files) {
		String dictDeposit = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getDictDeposit();
		String tranDepositTime = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getTranDepositTimeStr();
		String storesInAccount = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getStoresInAccount();
		String reallyAmount = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getReallyAmountStr();
		String depositName = urgeServicesMoneyEx.getPaybackTransferInfo()
				.getDepositName();
		String[] dictDeposits = dictDeposit.split(",");
		String[] tranDepositTimes = tranDepositTime.split(",");
		String[] reallyAmounts = reallyAmount.split(",");
		String[] depositNames = depositName.split(",");
		for (int i = 0; i < reallyAmounts.length; i++) {
			if (dictDeposits.length != 0 && dictDeposits != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDictDeposit(
						dictDeposits[i].trim());
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDictDeposit(
						null);
			}
			if (tranDepositTimes.length != 0 && tranDepositTimes != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo()
						.setTranDepositTime(
								DateUtils.parseDate(tranDepositTimes[i].trim()));
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo()
						.setTranDepositTime(null);
			}
			if (!StringUtils.isEmpty(storesInAccount)
					&& storesInAccount != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo()
						.setStoresInAccount(storesInAccount);
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo()
						.setStoresInAccount(null);
			}
			if (depositNames.length != 0 && depositNames != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDepositName(
						depositNames[i].trim());
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDepositName(
						null);
			}
			if (reallyAmounts.length != 0 && reallyAmounts != null) {
				urgeServicesMoneyEx.getPaybackTransferInfo().setReallyAmount(
						new BigDecimal(reallyAmounts[i].trim()));
			} else {
				urgeServicesMoneyEx.getPaybackTransferInfo().setDepositName(
						null);
			}
			// 上传新文件
			if (files[i].getSize() > 0) {
				DocumentBean db = CeUtils.uploadFile(files[i],
						TargetWay.SERVICE_FEE.getCode(),
						CeFolderType.URGE_UPLOAD);
				
				if (!ObjectHelper.isEmpty(db)) {
					urgeServicesMoneyEx.getPaybackTransferInfo().setUploadPath(db.getDocId());
					urgeServicesMoneyEx.getPaybackTransferInfo().setUploadFilename(db.getDocTitle());
				}
			}
			urgeServicesMoneyEx.getPaybackTransferInfo().setAuditStatus(
					BankSerialCheck.CHECKE_SUCCEED.getCode());
			urgeServicesMoneyEx.getPaybackTransferInfo().setContractCode(
					urgeServicesMoneyEx.getUrgeServicesCheckApply()
							.getContractCode());
			urgeServicesMoneyEx.getPaybackTransferInfo().setLoanCode(
					urgeServicesMoneyEx.getLoanCode());
			urgeServicesMoneyEx.getPaybackTransferInfo().setUploadName(
					UserUtils.getUser().getId());
			urgeServicesMoneyEx.getPaybackTransferInfo().setModifyBy(
					UserUtils.getUser().getUserCode());
			urgeServicesMoneyEx.getPaybackTransferInfo().setUploadDate(
					new Date());
			urgeServicesMoneyEx.getPaybackTransferInfo()
					.setrPaybackApplyId(urgeServicesMoneyEx.getId());
			urgeServicesMoneyEx.getPaybackTransferInfo().setRelationType(
					TargetWay.SERVICE_FEE.getCode());
			urgeServicesMoneyEx.getPaybackTransferInfo().setIsNewRecord(
					false);
			urgeServicesMoneyEx.getPaybackTransferInfo().preInsert();
			dao.savePayBackTransferInfo(urgeServicesMoneyEx);
		}
	}
	
	/**
	 * 匹配规则
	 * 2016年4月26日
	 * By 朱静越
	 * @param urgeId 催收服务费主表ID
	 * @param applyId 催收服务费申请表ID
	 * @param contractCode 合同编号
	 * @param blueAmount 蓝补金额
	 * @return boolean 
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean matchingRule(String urgeId, String applyId, String contractCode) {

		// 取该申请表中的info表中的数据，未查账和查账失败的状态进行匹配
		List<CarPaybackTransferInfo> infoList = new ArrayList<CarPaybackTransferInfo>();
		CarPaybackTransferInfo pi = new CarPaybackTransferInfo();
		
		int sum = 0; // 汇款单匹配次数
		
		pi.setrPaybackApplyId(applyId);
		pi.setAuditStatus("'"+ BankSerialCheck.CHECKE_SUCCEED.getCode() + "','"+BankSerialCheck.CHECKE_FAILED.getCode()+"'");
		pi.setRelationType(TargetWay.SERVICE_FEE.getCode());
		infoList = dao.findUrgeTransfer(pi);
		if(ArrayHelper.isNotEmpty(infoList)){
			for(CarPaybackTransferInfo info: infoList){
				// 实际存款人如果是（存现、现金、转帐、转款、支付宝、无 ），则不参加匹配
				if (!validationDepositName(info.getDepositName())) {
					// 查询流水（存入日期，存入银行，存入金额，存入人）匹配成功返回流水ID
					String urgeMatchingOutId = urgeMatchingSuccess(info);
					if(StringUtils.isNotEmpty(urgeMatchingOutId)){
						updateMatching(info, urgeMatchingOutId);
						sum++;
						continue;
					}
				}
				// 存款人长度大于2才参加模糊匹配
				if (validationDepositNameLength(info.getDepositName())) {
					// 查询流水（存入日期，存入银行，存入金额，备注包含存款人）匹配成功返回流水ID
					String matchingRemarkOutId = urgeMatchingRemarkSuccess(info);
					if(StringUtils.isNotEmpty(matchingRemarkOutId)){
						updateMatching(info, matchingRemarkOutId);
						sum++;
						continue;
					}
				}
				
				// 查询流水（存入日期，存入银行，存入金额） 存款人不一样匹配失败
				if(urgeMatchingOfflineSuccess(info)){
					// 未完全匹配
					info.setAuditStatus(BankSerialCheck.OFFLINE_CHECK.getCode());
					dao.updateInfoStatus(info);
				}else{
					// 汇款匹配失败
					info.setAuditStatus(BankSerialCheck.CHECKE_FAILED.getCode());
					dao.updateInfoStatus(info);
				}
			}
			
			// 汇款条数和汇款条数想等，该申请匹配成功
			if (StringUtils.equals(String.valueOf(infoList.size()), String.valueOf(sum))) {
				updateUrgeMatchingTaskSuccess(contractCode, applyId, urgeId);
				return true;
			} else {
				updateUrgeMatchingTaskfailed(contractCode, applyId, urgeId);
			}
		}
		return false;
	}
	
	/**
	 * 匹配失败操作
	 * @param contractCode
	 * @param applyId
	 * @param urgeId
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updateUrgeMatchingTaskfailed(String contractCode, String applyId,
			String urgeId) {
		// 更新申请表状态
		updateUrgeCheckApplyFailed(applyId);
		// 匹配失败操作历史
		CarUrgeOpeEx paybackOpes = new CarUrgeOpeEx(applyId,
				RepaymentProcess.MATCHING, TargetWay.SERVICE_FEE,
				PaybackOperate.MATCH_FAILED.getCode(), "批量匹配失败");
		// 插入历史，保证事务的同步
		CarUrgeOpe paybackOpe = new CarUrgeOpe();
		paybackOpe.setrUrgeId(paybackOpes.getrUrgeId());
		paybackOpe.setDictLoanStatus(paybackOpes.getDictLoanStatus());
		paybackOpe.setDictRDeductType(paybackOpes.getDictRDeductType());
		paybackOpe.setRemarks(paybackOpes.getRemark());
		paybackOpe.setOperateResult(paybackOpes.getOperateResult());
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		paybackOpe.preInsert();
		carUrgeOpeDao.insert(paybackOpe);
	}
	
	/**
	 * 
	 * 查账成功，更新查账申请表的状态和实际到账金额
	 * 2016年4月26日
	 * By 朱静越
	 * @param paybackTransferInfo
	 * @param urgeApply
	 * @param applyAmount
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateUrgeCheckApplyFailed(String applyId) {
		CarUrgeServicesCheckApply urgeApply = new CarUrgeServicesCheckApply();
		urgeApply.setId(applyId);
		urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.VERIFIED_FAILED.getCode());
		urgeApply.preUpdate();
		dao.updateUrgeApply(urgeApply);
	}
	
	/**
	 * 匹配成功更新信息
	 * @param info
	 * @param matchingOutId 
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updateMatching(CarPaybackTransferInfo info, String outId) {

		// 匹配成功更新汇款表
		info.setAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
		info.setRelationType(TargetWay.SERVICE_FEE.getCode());
		info.preUpdate();
		dao.updateInfoStatus(info);
		
		// 银行导入流水存入匹配成功的申请ID,不修改查账状态,多条统一修改
		PaybackTransferOut out = new PaybackTransferOut();
		out.setId(outId);
		out.setTransferAccountsId(info.getId());
		out.setrPaybackApplyId(info.getrPaybackApplyId());
		out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		out.setRelationType(TargetWay.SERVICE_FEE.getCode());
		out.preUpdate();
		paybackTransferOutDao.updateOutStatuById(out);

		// 更新催收服务费查账申请表的实际到账金额
		CarUrgeServicesCheckApply urgeApply = new CarUrgeServicesCheckApply();
		urgeApply.setId(info.getrPaybackApplyId());
		urgeApply = dao.getUrgeApplyById(urgeApply);
		if(!ObjectHelper.isEmpty(urgeApply)){
			if(StringUtils.isNotEmpty(String.valueOf(urgeApply.getUrgeApplyAmount()))){
				urgeApply.setUrgeReallyAmount(info.getReallyAmount().add(urgeApply.getUrgeReallyAmount()));
				urgeApply.preUpdate();
				dao.updateUrgeApply(urgeApply);	
			}
		}
	}
	
	/**
	 * 匹配成功操作
	 * @param contractCode
	 * @param applyId
	 * @param urgeId
	 * @param blueAmount 蓝补金额 暂时不需要，进行删除了
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private void updateUrgeMatchingTaskSuccess(String contractCode, String applyId,
			String urgeId) {
		// 更新催收服务费查账申请表的实际到账金额
		CarUrgeServicesCheckApply urgeApply = new CarUrgeServicesCheckApply();
		urgeApply.setId(applyId);
		urgeApply = dao.getUrgeApplyById(urgeApply);
		if (!ObjectHelper.isEmpty(urgeApply)) {
			if (StringUtils.isNotEmpty(String.valueOf(urgeApply.getUrgeApplyAmount()))) {
				BigDecimal applyAmount = urgeApply.getUrgeReallyAmount(); 
				// 全部匹配成功修改导入流水状态，查账历史
				updateTransferOutStatus(applyId, contractCode);
				// 更新催收服务费查账申请表的实际到账金额
				updateUrgeCheckApplySuccess(applyId, applyAmount);
				// 修改催收服务费主表
				updateUrgeServicesMoney(urgeId, applyAmount);
				//TODO  修改蓝补 ：蓝补金额 = 已划扣金额 + 查账金额 - 催收金额,因为还款还没有做，所以暂时不进行更新蓝补操作

				// 匹配成功操作历史
				CarUrgeOpeEx paybackOpes = new CarUrgeOpeEx(applyId,
						RepaymentProcess.MATCHING, TargetWay.SERVICE_FEE,
						PaybackOperate.MATCH_SUCCEED.getCode(), "批量匹配成功");
				// 插入历史，保证事务的同步
				CarUrgeOpe paybackOpe = new CarUrgeOpe();
				paybackOpe.setrUrgeId(paybackOpes.getrUrgeId());
				paybackOpe.setDictLoanStatus(paybackOpes.getDictLoanStatus());
				paybackOpe.setDictRDeductType(paybackOpes.getDictRDeductType());
				paybackOpe.setRemarks(paybackOpes.getRemark());
				paybackOpe.setOperateResult(paybackOpes.getOperateResult());
				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				paybackOpe.preInsert();
				carUrgeOpeDao.insert(paybackOpe);
			}
		}
	}
	
	/**
	 * 更新导入银行流水表的查账状态
	 * 2016年4月26日
	 * By 朱静越
	 * @param paybackTransferInfo
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateTransferOutStatus(String applyId, String contractCode) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		PaybackTransferOut puts = new PaybackTransferOut();
		puts.setrPaybackApplyId(applyId);
		puts.setContractCode(contractCode);
		puts.setOutTimeCheckAccount(new Date());
		puts.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
		puts.setRelationType(TargetWay.SERVICE_FEE.getCode());
		paybackTransferOutDao.updateOutStatuByApplyId(puts);
		
		// 查账流水历史
		outList = paybackTransferOutDao.findList(puts);
		for(PaybackTransferOut out:outList){
			// 记录匹配成功流水的历史,记录款项历史
			PaybackOpeEx paybackOpes = null;
			paybackOpes = new PaybackOpeEx(out.getId(), null,
					RepaymentProcess.MATCHING, TargetWay.SERVICE_FEE,
					PaybackOperate.MATCH_SUCCEED.getCode(), "批量匹配，合同编号:"
							+ contractCode + "匹配成功！");
			historyService.insertPaybackOpe(paybackOpes);
		}
	}
	
	/**
	 * 
	 * 查账成功，更新查账申请表的状态和实际到账金额,同时查账成功之后，将退回原因更新为空
	 * 2016年4月26日
	 * By 朱静越
	 * @param urgeApply
	 * @param applyAmount
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateUrgeCheckApplySuccess(String applyId, BigDecimal applyAmount) {
		CarUrgeServicesCheckApply urgeApply = new CarUrgeServicesCheckApply();
		urgeApply.setId(applyId);
		urgeApply.setUrgeReallyAmount(applyAmount);
		urgeApply.setUrgeBackReason("");
		urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode());
		urgeApply.preUpdate();
		dao.updateUrgeApply(urgeApply);
	}
	
	/**
	 * 更新催收主表的状态和金额
	 * 2016年4月26日
	 * By 朱静越
	 * @param urgeMoney
	 * @param urgeSerMoney
	 * @param applyAmount
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateUrgeServicesMoney(String urgeId, BigDecimal applyAmount) {
		CarUrgeServicesMoney um = new CarUrgeServicesMoney();
		um.setId("'" + urgeId + "'");
		um.setAuditAmount(applyAmount);
		um.setDictDealStatus(UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode());
		um.preUpdate();
		urgeServiceDao.updateUrge(um);
	}
	
	/**
	 * 单笔处理提交匹配
	 * 2016年4月26日
	 * By 朱静越
	 * @param applyId
	 * @param infoId
	 * @param outId
	 * @param contractCode
	 * @param outReallyAmount
	 * @param applyReallyAmount
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveSingleAutoMatch(String applyId, String infoId,
			String outId, String contractCode, String outReallyAmount,
			String applyReallyAmount) {
		CarUrgeServicesCheckApply urgeApply = new CarUrgeServicesCheckApply();
		CarPaybackTransferInfo info = new CarPaybackTransferInfo();
		PaybackTransferOut out = new PaybackTransferOut();
		info.setId(infoId);
		info.setAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
		info.preUpdate();
		out.setId(outId);
		out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		out.setrPaybackApplyId(applyId);
		out.setTransferAccountsId(infoId);
		out.preUpdate();
		dao.updateInfoStatus(info);
		paybackTransferOutDao.updateOutStatuById(out);
		// 更新催收服务费查账申请表的实际到账金额
		urgeApply.setId(applyId);
		urgeApply.setUrgeReallyAmount(BigDecimalTools.add(new BigDecimal(
				outReallyAmount), new BigDecimal(applyReallyAmount)));
		urgeApply.preUpdate();
		dao.updateUrgeApply(urgeApply);
	}
	
	/**
	 * 保存匹配审核结果 
	 * 2016年4月25日 By 张永生
	 * @param urgeServicesMoneyEx
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveMatchAuditResult(CarDeductCostRecoverEx urgeServicesMoneyEx) {
		CarUrgeServicesMoney urgeSerMoney = new CarUrgeServicesMoney();
		CarUrgeServicesMoney urgeMoney = new CarUrgeServicesMoney();
		CarUrgeServicesCheckApply urgeApply = new CarUrgeServicesCheckApply();
		if (StringUtils.equals(urgeServicesMoneyEx.getDictPayResult(),
				OperateMatching.SUCCESS_MATCHING.getCode())) {
			// 修改导入流水表状态
			PaybackTransferOut out = new PaybackTransferOut();
			out.setrPaybackApplyId(urgeServicesMoneyEx
					.getUrgeServicesCheckApply().getId());
			out.setContractCode(urgeServicesMoneyEx.getContractCode());
			out.setOutTimeCheckAccount(new Date());
			out.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
			paybackTransferOutDao.updateOutStatuByApplyId(out);

			// 更新催收服务费查账申请表的实际到账金额,申请状态更新为查账成功,退回原因更新为空
			urgeApply.setId(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
			urgeApply.setUrgeReallyAmount(urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeReallyAmount());
			urgeApply.setUrgeBackReason("");
			urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode());
			urgeApply.preUpdate();
			dao.updateUrgeApply(urgeApply);
			urgeMoney = urgeServiceDao.find(urgeServicesMoneyEx.getUrgeId());

			// 修改催收服务费主表
			urgeSerMoney.setId("'" + urgeServicesMoneyEx.getUrgeId() + "'");
			BigDecimal auditAmount = urgeMoney.getAuditAmount();
			BigDecimal urgeAmount = urgeMoney.getUrgeMoeny();
			auditAmount = auditAmount == null ? BigDecimal.ZERO : auditAmount;
			urgeAmount = urgeAmount == null ? BigDecimal.ZERO : urgeAmount;
			urgeSerMoney.setAuditAmount(urgeServicesMoneyEx
					.getUrgeServicesCheckApply().getUrgeReallyAmount()
					.add(auditAmount));
			urgeSerMoney.setDictDealStatus(UrgeCounterofferResult.ACCOUNT_VERIFIED.getCode());
			urgeSerMoney.preUpdate();
			urgeServiceDao.updateUrge(urgeSerMoney);
			//TODO 修改蓝补 ：蓝补金额 = 已划扣金额 + 查账金额 - 催收金额，因为车借暂时没有还款，所以没有办法更新蓝补
			// 操作历史
			CarUrgeOpeEx paybackOpes = new CarUrgeOpeEx(urgeApply.getId(),
					RepaymentProcess.MATCHING,
					TargetWay.SERVICE_FEE,
					PaybackOperate.MATCH_SUCCEED.getCode(), "手动匹配，合同编号:"
							+ urgeApply.getContractCode());
			// 插入历史，保证事务的同步
			CarUrgeOpe paybackOpe = new CarUrgeOpe();
			paybackOpe.setrUrgeId(paybackOpes.getrUrgeId());
			paybackOpe.setDictLoanStatus(paybackOpes.getDictLoanStatus());
			paybackOpe.setDictRDeductType(paybackOpes.getDictRDeductType());
			paybackOpe.setRemarks(paybackOpes.getRemark());
			paybackOpe.setOperateResult(paybackOpes.getOperateResult());
			paybackOpe.setOperator(UserUtils.getUser().getId());
			paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
			paybackOpe.setOperateTime(new Date());
			paybackOpe.preInsert();
			carUrgeOpeDao.insert(paybackOpe);
		} else {
			// 退回标识  false退回数据
			Boolean isExistFlag = this.isUrgeExist(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
			if (!isExistFlag) {

				// 更新催收主表
				urgeSerMoney.setId("'" + urgeServicesMoneyEx.getUrgeId() + "'");
				urgeSerMoney.setDictDealStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
				
				// 更新催收服务费查账状态为【查账退回】
				urgeApply.setId(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
				urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
				urgeApply.setUrgeReallyAmount(new BigDecimal(0.00));
				urgeApply.preUpdate();
				dao.updateUrgeApply(urgeApply);
				
				// 更新info
				CarPaybackTransferInfo info = new CarPaybackTransferInfo();
				info.setrPaybackApplyId(urgeServicesMoneyEx.getUrgeServicesCheckApply().getId());
				info.setAuditStatus(BankSerialCheck.CHECKE_FAILED.getCode());
				dao.updateInfoStatus(info);

				// 退回操作历史
				CarUrgeOpeEx paybackOpes = new CarUrgeOpeEx(urgeApply.getId(),
						RepaymentProcess.RETURN, TargetWay.SERVICE_FEE, PaybackOperate.RETURN_SUCCESS.getCode(), "手动退回，退回原因:"
								+ urgeServicesMoneyEx.getUrgeServicesCheckApply().getUrgeBackReason());
				// 插入历史，保证事务的同步
				CarUrgeOpe paybackOpe = new CarUrgeOpe();
				paybackOpe.setrUrgeId(paybackOpes.getrUrgeId());
				paybackOpe.setDictLoanStatus(paybackOpes.getDictLoanStatus());
				paybackOpe.setDictRDeductType(paybackOpes.getDictRDeductType());
				paybackOpe.setRemarks(paybackOpes.getRemark());
				paybackOpe.setOperateResult(paybackOpes.getOperateResult());
				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				paybackOpe.preInsert();
				carUrgeOpeDao.insert(paybackOpe);
				
				urgeSerMoney.preUpdate();
				urgeServiceDao.updateUrge(urgeSerMoney);
			}
		}
	}
	
	/**
	 * 批量退回
	 * 2016年4月26日
	 * By 朱静越
	 * @param id
	 * @param contractCode
	 * @param applyBackMsg
	 * @param urgeId
	 * @return 
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean urgeMatchingBack(String id, String contractCode, String urgeId) {

			// 退回标识  false退回数据
			Boolean isExistFlag = this.isUrgeExist(id);
			if (!isExistFlag) {
				CarUrgeServicesCheckApply urgeApply = new CarUrgeServicesCheckApply();
				CarUrgeServicesMoney urgeMoney = new CarUrgeServicesMoney();
				
				// 修改申请表
				urgeApply.setId(id);
				urgeApply.setContractCode(contractCode);
				urgeApply.setUrgeApplyStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
				urgeApply.setUrgeReallyAmount(new BigDecimal(0.00));
				urgeApply.preUpdate();
				dao.updateUrgeApply(urgeApply);
				
				// 修改催收主表
				urgeMoney.setId("'"+urgeId+"'");
				urgeMoney.setDictDealStatus(UrgeCounterofferResult.ACCOUNT_RETURN.getCode());
				urgeMoney.preUpdate();
				urgeServiceDao.updateUrge(urgeMoney);
				
				// 更新info表的查账状态为【查账失败】
				CarPaybackTransferInfo info = new CarPaybackTransferInfo();
				info.setrPaybackApplyId(id);
				info.setAuditStatus(BankSerialCheck.CHECKE_FAILED.getCode());
				dao.updateInfoStatus(info);
	
				
				// 退回操作历史
				CarUrgeOpeEx paybackOpes = new CarUrgeOpeEx(urgeApply.getId(),
						RepaymentProcess.RETURN, TargetWay.SERVICE_FEE, PaybackOperate.RETURN_SUCCESS.getCode(), "批量匹配，退回原因：款项尚未到账，或者存入日期/存入账户/存入金额有误，请核实！");
				// 插入历史，保证事务的同步
				CarUrgeOpe paybackOpe = new CarUrgeOpe();
				paybackOpe.setrUrgeId(paybackOpes.getrUrgeId());
				paybackOpe.setDictLoanStatus(paybackOpes.getDictLoanStatus());
				paybackOpe.setDictRDeductType(paybackOpes.getDictRDeductType());
				paybackOpe.setRemarks(paybackOpes.getRemark());
				paybackOpe.setOperateResult(paybackOpes.getOperateResult());
				paybackOpe.setOperator(UserUtils.getUser().getId());
				paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
				paybackOpe.setOperateTime(new Date());
				paybackOpe.preInsert();
				carUrgeOpeDao.insert(paybackOpe);
			}
			return isExistFlag;
	}
	
	/**
	 * 退回验证流水是否存在
	 * @param id
	 * @return boolean
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	private Boolean isUrgeExist(String id) {
		List<CarPaybackTransferInfo> infoList = new ArrayList<CarPaybackTransferInfo>();
		CarPaybackTransferInfo info = new CarPaybackTransferInfo();
		info.setrPaybackApplyId(id);
		info.setAuditStatus("'" + BankSerialCheck.CHECKE_SUCCEED.getCode()
				+ "','" + BankSerialCheck.CHECKE_FAILED.getCode() + "','"
				+ BankSerialCheck.OFFLINE_CHECK.getCode() + "'");
		info.setRelationType(TargetWay.SERVICE_FEE.getCode());
		infoList = dao.findUrgeTransfer(info);
		if (ArrayHelper.isNotEmpty(infoList)) {
			for (CarPaybackTransferInfo pInfo : infoList) {
				List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
				PaybackTransferOut out = new PaybackTransferOut();
				out.setOutDepositTime(pInfo.getTranDepositTime());
				out.setOutEnterBankAccount(pInfo.getStoresInAccount());
				out.setOutReallyAmount(pInfo.getReallyAmount());
				out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
				outList = paybackTransferOutDao.findAuditedList(out);
				if (ArrayHelper.isNotEmpty(outList)) {
					return true;
				}else{
					break;
				}
			}
		}
		return false;
	}
	
	/**
	 * （存入日期，存入银行，存入金额，存入人）匹配成功 by zhangfeng
	 * @param info
	 * @return boolean
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private String urgeMatchingSuccess(CarPaybackTransferInfo info) {
		
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		PaybackTransferOut out = new PaybackTransferOut();
		// 存入日期
		out.setOutDepositTime(info.getTranDepositTime());
		// 存入银行
		out.setOutEnterBankAccount(info.getStoresInAccount());
		// 存入金额
		out.setOutReallyAmount(info.getReallyAmount());
		// 存入人
		out.setOutDepositName(info.getDepositName());
		// 查账状态为未查账
		out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		
		outList = paybackTransferOutDao.getAutoNoAuditedList(out);
		if(ArrayHelper.isNotEmpty(outList)){
			// 匹配成功返回outId，多条返回第一条outId
			return outList.get(0).getId();
		}
		return null;
	}
	
	/**
	 * 查询流水（存入日期，存入银行，存入金额） 存款人不一样匹配失败
	 * @param info
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private boolean urgeMatchingOfflineSuccess(CarPaybackTransferInfo info) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		PaybackTransferOut out = new PaybackTransferOut();
		// 存入日期
		out.setOutDepositTime(info.getTranDepositTime());
		// 存入银行
		out.setOutEnterBankAccount(info.getStoresInAccount());
		// 存入金额
		out.setOutReallyAmount(info.getReallyAmount());
		// 查账状态为查账
		out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		
		outList = paybackTransferOutDao.getAutoNoAuditedList(out);
		if(ArrayHelper.isNotEmpty(outList)){
			return true;
		}
		return false;
	}
	
	/**
	 * 更新催收服务费主表 by zhangfeng
	 * @param applyId
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveUrgeServiceAmount(String applyId) {
		CarUrgeServicesMoney urgeServicesMoney = new CarUrgeServicesMoney();
		urgeServicesMoney.setId("'" + applyId + "'");
		urgeServicesMoney.setDictDealStatus(UrgeCounterofferResult.TO_ACCOUNT_VERIFY.getCode());
		urgeServicesMoney.preUpdate();
		urgeServiceDao.updateUrge(urgeServicesMoney);
	}
	
	/**
	 * 删除汇款表数据 by zhangfeng
	 * @param paybackApply
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void deleteTransferInfo(String applyId) {
		
		// 删除旧文件
		CarPaybackTransferInfo info = new CarPaybackTransferInfo();
		info.setrPaybackApplyId(applyId);
		List<CarPaybackTransferInfo> infoList = findUrgeTransfer(info);
		if (ArrayHelper.isNotEmpty(infoList)) {
			for (CarPaybackTransferInfo in : infoList) {
				CeUtils.deleteFile(in.getUploadPath());
			}
		}
		
		// 删除汇款单
		dao.deletePaybackTransferInfo(info);
	}
	
	/**
	 * （存入日期，存入银行，存入金额）匹配三项，备注包含存款人匹配成功
	 * @param info
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private String urgeMatchingRemarkSuccess(CarPaybackTransferInfo info) {
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		PaybackTransferOut out = new PaybackTransferOut();
		// 存入日期
		out.setOutDepositTime(info.getTranDepositTime());
		// 存入银行
		out.setOutEnterBankAccount(info.getStoresInAccount());
		// 存入金额
		out.setOutReallyAmount(info.getReallyAmount());
		// 备注模糊存入人前3个字
		if(StringUtils.isNotEmpty(info.getDepositName())){
			out.setOutRemark(info.getDepositName().substring(0, 3));
		}
		// 查账状态为查账
		out.setOutAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
		
		outList = paybackTransferOutDao.getAutoNoAuditedList(out);
		if(ArrayHelper.isNotEmpty(outList)){
			// 匹配成功返回outId，多条返回第一条outId
			return outList.get(0).getId();
		}
		return null;
	}
	
	/**
	 * 验证实际存款人（存现、现金、转帐、转款、支付宝、无）
	 * 2016年06月02日
	 * By zhangfeng
	 * @param depositName 
	 * @return boolean
	 */
	private boolean validationDepositName(String depositName) {
		if (StringUtils.isNotEmpty(depositName)) {
			for (int i = 0; i < PaybackConstants.VALIDATION_DEPOSITNAME.length; i++) {
				if (depositName.contains(PaybackConstants.VALIDATION_DEPOSITNAME[i])) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 验证实际存款人长度
	 * 2016年06月02日
	 * By zhangfeng
	 * @param depositName 
	 * @return boolean
	 */
	private boolean validationDepositNameLength(String depositName) {
		if (StringUtils.isNotEmpty(depositName)) {
			if (depositName.length() > 2) {
				return true;
			}
		}
		return false;
	}
}
