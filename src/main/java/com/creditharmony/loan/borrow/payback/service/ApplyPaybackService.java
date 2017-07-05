package com.creditharmony.loan.borrow.payback.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.creditharmony.adapter.bean.in.djrcreditor.DjrSendApplyInfoInBean;
import com.creditharmony.adapter.bean.out.djrcreditor.DjrSendApplyInfoOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.deduct.type.DeductWays;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.BankSerialCheck;
import com.creditharmony.core.loan.type.CeFolderType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ChargeType;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.Matching;
import com.creditharmony.core.loan.type.PaybackOperate;
import com.creditharmony.core.loan.type.RepayApplyStatus;
import com.creditharmony.core.loan.type.RepayChannel;
import com.creditharmony.core.loan.type.RepayType;
import com.creditharmony.core.loan.type.RepaymentProcess;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.loan.type.TradeType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.dm.bean.DocumentBean;
import com.creditharmony.dm.service.DmService;
import com.creditharmony.loan.borrow.payback.dao.ApplyPaybackDao;
import com.creditharmony.loan.borrow.payback.entity.DeductPlantLimit;
import com.creditharmony.loan.borrow.payback.entity.DeductStatistics;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferInfo;
import com.creditharmony.loan.borrow.payback.entity.PaybackTransferOut;
import com.creditharmony.loan.borrow.payback.entity.ex.PaybackOpeEx;
import com.creditharmony.loan.borrow.poscard.entity.PosBacktage;
import com.creditharmony.loan.borrow.poscard.service.PosBacktageInfoService;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.service.DeductUpdateService;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.LoanDeductService;
import com.creditharmony.loan.common.service.PaybackService;
import com.creditharmony.loan.common.utils.CeUtils;
import com.creditharmony.loan.common.utils.LoanFileUtils;

/**
 * 发起还款申请业务处理service
 * 
 * @Class Name ApplyPaybackService
 * @author zhangfeng
 * @Create In 2015年11月24日
 */
@Service("ApplyPaybackService")
@Transactional(readOnly = true, value = "loanTransactionManager")
public class ApplyPaybackService extends CoreManager<ApplyPaybackDao, PaybackApply> {

	@Autowired
	private PaybackService paybackService;
	@Autowired
	private LoanDeductService loanDeductService;
	@Autowired
	private DeductUpdateService deductUpdateService;
	@Autowired
	private PosBacktageInfoService posBacktageInfoService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private PaybackTransferOutService paybackTransferOutService;

	/**
	 * 根据借款编码查询客户账户信息 
	 * 2015年12月11日 
	 * By zhangfeng
	 * @param loanCode
	 * @return list
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<LoanBank> findCustomerByLoanCode(String loanCode,String id) {
		return dao.findCustomerByLoanCode(loanCode,id);
	}
	
	/**
	 * 生成POS机还款订单
	 * 2015年2月18日
	 * By guanhongchang
	 * @param files 
	 * @param payback
	 */
	public String creatPosOrder(Payback payback) {
		//生成POS机订单编号
		List<PaybackApply> paybackApply = new ArrayList<PaybackApply>();
		//以合同编号 为条件查询还款申请表 该合同是否已有POS机订单编号
		payback.getPaybackApply().setContractCode(payback.getContractCode());
		//还款方式为POS机刷卡
		payback.getPaybackApply().setDictRepayMethod(RepayChannel.POS.getCode());
		//查询订单号的日期为当天创建的日期
		String creDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		payback.getPaybackApply().setDateCre(creDate);
		//当天日期+合同编号+还款方式（POS机刷卡） 查询当天最大的 订单编号 拿过来修改
		paybackApply =	dao.selectPosOrder(payback.getPaybackApply());
		//先查询该合同是否有订单编号 //如果有编号则 当天流水+1修改
		if(paybackApply!= null && !paybackApply.isEmpty()  ){
			//如果有订单编号  则 把后三位   合同号+当前日期+现有的订单编号+1 保存
			if(StringUtils.isNotEmpty(paybackApply.get(0).getPosBillCode())){
			//获取当前日期
			String strDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			//获取当前合同编号
			String contractCode=payback.getContractCode();
			//截取当前最大的POS订单号的后三位流水号
			String billCode=paybackApply.get(0).getPosBillCode();
			String billCodeNew=billCode.substring(billCode.length()-3,billCode.length());
			long bill= Long.parseLong(billCodeNew)+1;
		    String orderS=Long.toString(bill);
		    String billS=null;
		        if(orderS.length()==1){
		        	billS="00"+orderS;
		        }else if (orderS.length()==2){
		        	billS="0"+orderS;
		        }else{
		        	billS=orderS;
		        }
			//新的POS机订单编号
		    	if(contractCode.lastIndexOf('-')!=-1){
					String c=contractCode.substring(contractCode.lastIndexOf('-'));
				    String contractCodeStr=  contractCode.replace(c, "");
					String posOrder =contractCodeStr+strDate+billS;
					return posOrder;
				}else{
					
					String posOrder =contractCode+strDate+billS;
					return posOrder;
				}
		  }
		}
		//第一次申请POS机刷卡查账
		//获取当前日期
		String strDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		//获取当前合同编号
		String contractCode=payback.getContractCode();
		//新的POS机订单编号
	 	if(contractCode.lastIndexOf('-')!=-1){
	 		String c=contractCode.substring(contractCode.lastIndexOf('-'));
		    String contractCodeStr=  contractCode.replace(c, "");
			String posOrder =contractCodeStr+strDate+"001";
			return posOrder;
		}else{
			String posOrder=contractCode+strDate+"001";
			return posOrder;
		}
	}

	/**
	 * 保存还款申请信息(申请信息) 
	 * 2016年1月12日 
	 * By zhangfeng
	 * @param payback
	 * @return none
	 */

	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveApplyPayback(PaybackApply pa) {
		pa.setContractCode(pa.getContractCode());
		if (StringUtils.equals(pa.getDictRepayMethod(), RepayChannel.POS_CHECK.getCode())) {
			pa.setApplyReallyAmount(pa.getDeductAmountPosCard());
		} else {
			pa.setApplyReallyAmount(BigDecimal.ZERO);
		}
		pa.setApplyPayDay(new Date());
		pa.setSplitFlag(RepayApplyStatus.SPLITED.getCode());
		pa.setEffectiveFlag(YESNO.YES.getCode());
		pa.setLanuchBy(UserUtils.getUser().getId());
		pa.setOrgCode(UserUtils.getUser().getDepartment().getId());
		pa.setIsNewRecord(false);
		pa.preInsert();
		dao.saveApplyPayback(pa);
	}

	/**
	 * 验证账户信息重复 
	 * 2015年12月11日 
	 * By zhangfeng
	 * @param files
	 * @param info
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String findPayBackTransferOut(PaybackTransferInfo info) {
		String msg = null;
		List<PaybackTransferOut> outList = new ArrayList<PaybackTransferOut>();
		// 存款时间
		String tranDepositTime = info.getTranDepositTimeStr();
		// 存入账号
		String storesInAccount = info.getStoresInAccount();
		// 实际到账金额
		String reallyAmount = info.getReallyAmountStr();
		// 存款人
		String depositName = info.getDepositName();
		
		String[] tranDepositTimes = tranDepositTime.split(",");
		String[] reallyAmounts = reallyAmount.split(",");
		String[] depositNames = depositName.split(",");
		for (int i = 0; i < reallyAmounts.length; i++) {
			PaybackTransferOut out = new PaybackTransferOut();
			out.setOutDepositTime(DateUtils.parseDate(tranDepositTimes[i]));
			out.setOutEnterBankAccount(storesInAccount);
			out.setOutReallyAmount(new BigDecimal(reallyAmounts[i]));
			out.setOutDepositName(depositNames[i]);
			out.setOutAuditStatus(BankSerialCheck.CHECKE_OVER.getCode());
			outList = paybackTransferOutService.findAuditedList(out);
			if (ArrayHelper.isNotEmpty(outList)) {
				msg = "系统已经存在查账成功相同的汇款单，请检查数据是否确认提交？";
			}
		}
		return msg;
	}
	
	/**
	 * 保存还款申请信息(账户信息) 
	 * 2015年12月11日 
	 * By zhangfeng
	 * @param files
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void savePayBackTransferInfo(MultipartFile[] files, PaybackTransferInfo info) throws Exception{
		String dictDeposit = info.getDictDeposit();
		String tranDepositTime = info.getTranDepositTimeStr();
		String storesInAccount = info.getStoresInAccount();
		String storesInAccountname = info.getStoresInAccountname();
		String reallyAmount = info.getReallyAmountStr();
		String depositName = info.getDepositName();
		String accountBranch = info.getAccountBranch();
		String applyTime = info.getApplyTimeStr();
		String[] dictDeposits = dictDeposit.split(",");
		String[] tranDepositTimes = tranDepositTime.split(",");
		String[] reallyAmounts = reallyAmount.split(",");
		String[] depositNames = depositName.split(",");
		String[] accountBranchs = accountBranch.split(",");
		String[] applyTimes = applyTime.split(",");
		for (int i = 0; i < reallyAmounts.length; i++) {
			if (StringUtils.isNotEmpty(dictDeposits[i])) {
				info.setDictDeposit(dictDeposits[i].trim());
			} else {
				info.setDictDeposit(null);
			}
			if (StringUtils.isNotEmpty(tranDepositTimes[i])) {
				info.setTranDepositTime(DateUtils.parseDate(tranDepositTimes[i].trim()));
			} else {
				info.setTranDepositTime(null);
			}
			if ((applyTimes.length-1)>=i && StringUtils.isNotEmpty(applyTimes[i])) {
				info.setApplyTime(DateUtils.parseDate(tranDepositTimes[i].trim()+" "+applyTimes[i].trim()));
			} else {
				info.setApplyTime(null);
			}
			if (StringUtils.isNotEmpty(storesInAccount)) {
				info.setStoresInAccount(storesInAccount.trim());
			} else {
				info.setStoresInAccount(null);
			}
			if (StringUtils.isNotEmpty(storesInAccountname)) {
				info.setStoresInAccountname(storesInAccountname.trim());
			} else {
				info.setStoresInAccount(null);
			}
			if (StringUtils.isNotEmpty(depositNames[i])) {
				info.setDepositName(depositNames[i].trim());
			} else {
				info.setDepositName(null);
			}
			if ((accountBranchs.length-1)>=i && StringUtils.isNotEmpty(accountBranchs[i])) {
				info.setAccountBranch(accountBranchs[i].trim());
			} else {
				info.setAccountBranch(null);
			}
			if (StringUtils.isNotEmpty(reallyAmounts[i])) {
				info.setReallyAmount(new BigDecimal(reallyAmounts[i].trim()));
			} else {
				info.setDepositName(null);
			}
			DocumentBean db = CeUtils.uploadFile(files[i], info.getContractCode(), CeFolderType.PAYMENT_UPLOAD);
			if (!ObjectHelper.isEmpty(db)) {
				info.setUploadPath(db.getDocId());
				info.setUploadFilename(db.getDocTitle());
			}else{
				throw new ServiceException("上传汇款单异常！");
			}
			info.setAuditStatus(BankSerialCheck.CHECKE_SUCCEED.getCode());
			info.setRelationType(TargetWay.REPAYMENT.getCode());
			info.setIsNewRecord(false);
			info.preInsert();
			info.setUploadName(UserUtils.getUser().getId());
			info.setUploadDate(new Date());
			dao.savePayBackTransferInfo(info);
		}
	}

	/**
	 * POS刷卡查账申请 保存
	 * 2016年2月26日 
	 * By zhangfeng
	 * @param files
	 * @reutrn object
	 */
	public void savePosCheck(MultipartFile[] files, Payback payback) {
		//存款方式
		String dictDepositPosCard =payback.getPosCardInfo().getDictDepositPosCard();
		//到账日期
		String paybackDate        =payback.getPosCardInfo().getPaybackDateStr();
		//实际到账金额
		String  applyReallyAmountPosCard =payback.getPosCardInfo().getApplyReallyAmountPosCard();
		//参考号
		String  referCode          =payback.getPosCardInfo().getReferCode();
		//订单号
		String  posOrderNumber     =payback.getPosCardInfo().getPosOrderNumber();
		String[]  dictDepositPosCards= dictDepositPosCard.split(",");
		String[]  paybackDates       =paybackDate.split(",");	
		String[]  applyReallyAmountPosCards =applyReallyAmountPosCard.split(",");
		String[]  referCodes         = referCode.split(","); 
		String[]  posOrderNumbers    = posOrderNumber.split(",");	
		for (int i = 0; i < referCodes.length; i++) {
			if (dictDepositPosCards.length != 0 && dictDepositPosCards != null) {
				payback.getPosCardInfo().setDictDepositPosCard(dictDepositPosCards[i]);
			} else {
				payback.getPosCardInfo().setDictDepositPosCard(null);
			}
			if (referCodes.length != 0 && referCodes != null) {
				payback.getPosCardInfo().setReferCode(referCodes[i]);
			} else {
				payback.getPosCardInfo().setReferCode(null);
			}
			if (posOrderNumbers.length != 0 && posOrderNumbers != null) {
				payback.getPosCardInfo().setPosOrderNumber(posOrderNumbers[i]);
			} else {
				payback.getPosCardInfo().setPosOrderNumber(null);
			}
			if (applyReallyAmountPosCards.length != 0 && applyReallyAmountPosCards != null) {
				payback.getPosCardInfo().setApplyReallyAmount(new BigDecimal(applyReallyAmountPosCards[i]));
			} else {
				payback.getPosCardInfo().setApplyReallyAmount(null);
			}
			if (paybackDates.length != 0 && paybackDates != null) {
				payback.getPosCardInfo().setPaybackDate(DateUtils.parseDate(paybackDates[i]));
			} else {
				payback.getPosCardInfo().setPaybackDate(null);
			}
			DocumentBean db = CeUtils.uploadFile(files[i],payback.getContractCode(),CeFolderType.PAYMENT_UPLOAD);
			if (!ObjectHelper.isEmpty(db)) {
				payback.getPosCardInfo().setUploadPath(db.getDocId());
				payback.getPosCardInfo().setUploadFilename(db.getDocTitle());
			}
			payback.getPosCardInfo().setIsNewRecord(false);
			payback.getPosCardInfo().preInsert();
			//上传人 上传时间
			payback.getPosCardInfo().setUploadNamePosCard(UserUtils.getUser().getId());
			payback.getPosCardInfo().setUploadDatePosCard(new Date());
			//合同编号
			payback.getPosCardInfo().setContractCode(payback.getContractCode());
			//修改已匹配列表
			PosBacktage posBacktage =new PosBacktage();
			//合同编号
			posBacktage.setContractCode(payback.getContractCode());
			//查账时间
			posBacktage.setAuditDate(new Date());
			//匹配状态改成已查账
			posBacktage.setMatchingState(Matching.CHECKED.getCode());
			//金额
			posBacktage.setApplyReallyAmount(payback.getPosCardInfo().getApplyReallyAmount());
			//到账时间
			posBacktage.setPaybackDate(payback.getPosCardInfo().getPaybackDate());
			//订单号
			posBacktage.setPosOrderNumber(payback.getPosCardInfo().getPosOrderNumber());
			//参考号
			posBacktage.setReferCode(payback.getPosCardInfo().getReferCode());
			//修改POS后台数据列表状态
			posBacktageInfoService.updatePosMaching(posBacktage);
			dao.savePayPosCardInfo(payback);
		}
		
	}

	/**
	 * POS机刷卡申请小票凭证
	 * 2016年2月26日 
	 * By guanhongchang
	 * @param files
	 * @reutrn object
	 */
	@RequestMapping(value = "uploadPosFile", method = RequestMethod.POST)
	public DocumentBean uploadPosFile(MultipartFile files) {
		DmService dmService = DmService.getInstance();
		FileInputStream is = null;
		DocumentBean doc = null;
		try {
			File f = LoanFileUtils.multipartFile2File(files);
			is = new FileInputStream(f);
			doc = dmService.createDocument(f.getName(), is, DmService.BUSI_TYPE_LOAN, "batchNo001", "subType001",
					UserUtils.getUser().getId());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * 修改蓝补金额
	 * 2015年12月28日 
	 * By zhangfeng
	 * @param payback
	 * @return none
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void updateBlueMon(Payback payback) {
		payback.preInsert();
		//蓝补金额
		payback.setPaybackBuleAmount(payback.getPaybackApply().getApplyAmount());
		//更新蓝补对账单
		deductUpdateService.
		addPaybackBuleAmont(null, payback.getPaybackBuleAmount(), payback.getPaybackBuleAmount(),TradeType.TRANSFERRED,
				null, ChargeType.CHARGE_STORE);
	}

	/**
	 * 保存汇款申请
	 * 2016年2月26日 
	 * By zhangfeng
	 * @param payback
	 * @param files 
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void savePaybackMatching(Payback p, MultipartFile[] files) throws Exception{
		PaybackTransferInfo info = p.getPaybackTransferInfo();
		PaybackApply pa = p.getPaybackApply();
		pa.setContractCode(p.getContractCode());
		if (StringUtils.isNotEmpty(String.valueOf(p.getPaybackDay()))) {
			Format f = new SimpleDateFormat("dd");
			String newDate = f.format(new Date());
			p.setLoanBank(null);
			if (StringUtils.equals(newDate, String.valueOf(p.getPaybackDay()))) {
				// 还款日还款
				pa.setDictPayUse(RepayType.ACCOUNT_CHECK.getCode());
			} else {
				// 正常还款
				pa.setDictPayUse(RepayType.PAYBACK_APPLY.getCode());
			}
		}
		pa.setDictDealType(null);
		pa.setApplyAccountName(null);
		pa.setApplyDeductAccount(null);
		pa.setApplyBankName(null);
		pa.setApplyAmount(pa.getTransferAmount());
		pa.setDictPaybackStatus(RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode());
		pa.setDictDepositAccount(info.getStoresInAccount());
		this.saveApplyPayback(pa);

		info.setContractCode(p.getContractCode());
		info.setrPaybackApplyId(pa.getId());
		this.savePayBackTransferInfo(files, info);
		// 还款申请操作历史
		this.saveApplyHistory(pa, p);
	}

	/**
	 * 保存划扣信息
	 * 2016年2月26日 
	 * By zhangfeng
	 * @param payback
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void savePaybackDeduct(Payback p) {
		PaybackApply pa = p.getPaybackApply();
		pa.setContractCode(p.getContractCode());
		p.setPaybackTransferInfo(null);
		// 修改划扣账号信息
		if (StringUtils.isNotEmpty(p.getLoanBank().getNewId())) {
			p.getLoanBank().setBankTopFlag(PaybackApply.TOP_FLAG_NO);
			p.getLoanBank().preUpdate();
			LoanBank loanBank = new LoanBank();
			loanBank.setId(p.getLoanBank().getNewId());
			loanBank.setBankTopFlag(PaybackApply.TOP_FLAG);
			loanBank.preUpdate();
			loanDeductService.updateTopFlag(p.getLoanBank());
			loanDeductService.updateTopFlag(loanBank);
		}
		// 还款方式为 划扣，插入申请表时将回盘结果置为 待划扣 0
		pa.setSplitBackResult(CounterofferResult.PREPAYMENT.getCode());
		pa.setDictPayUse(RepayType.PAYBACK_APPLY.getCode());
		pa.setApplyAmount(pa.getDeductAmount());
		pa.setDictPaybackStatus(RepayApplyStatus.PRE_PAYMENT.getCode());
		pa.setDictDeductType(DeductWays.HJ_02.getCode());
		this.saveApplyPayback(pa);
		// 还款申请操作历史
		this.saveApplyHistory(pa, p);
		//将待划扣申请数据推送给大金融
		if(p.getContract().getChannelFlag().equals(ChannelFlag.ZCJ.getCode()))
			sendDataToDjr(pa);
	}

	/**
	 * 保存POS信息
	 * 2016年2月26日 
	 * By zhangfeng
	 * @param payback
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void savePaybackPos(Payback p) {
		PaybackApply pa = p.getPaybackApply();
		pa.setContractCode(p.getContractCode());
		// 还款申请状态 待还款 还款申请状态
		pa.setDictPaybackStatus(RepayApplyStatus.TO_PAYMENT.getCode());
		// 申请划卡金额
		pa.setApplyAmount(pa.getDeductAmountPosCard());
		// 划扣平台
		pa.setDictDealType(null);
		// 帐号姓名
		pa.setApplyAccountName(null);
		// 划扣帐号
		pa.setApplyDeductAccount(null);
		// 还款类型
		pa.setDictPayUse(RepayType.PAYBACK_APPLY.getCode());
		// 开户行名称
		pa.setApplyBankName(null);
		// POS机订单编号生成
		pa.setPosBillCode(creatPosOrder(p));
		this.saveApplyPayback(pa);
		// 还款申请操作历史
		this.saveApplyHistory(pa, p);
	}

	/**
	 * 保存POS查账信息
	 * 2016年2月26日 
	 * By zhangfeng
	 * @param files 
	 * @param payback
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void savePaybackPosCheck(Payback p, MultipartFile[] files) {
		PaybackApply pa = p.getPaybackApply();
		pa.setContractCode(p.getContractCode());
		// 还款申请状态 待还款 还款申请状态
		pa.setDictPaybackStatus(RepayApplyStatus.HAS_PAYMENT.getCode());
		// 申请划卡金额
		pa.setApplyAmount(pa.getDeductAmountPosCard());
		// 划扣平台
		pa.setDictDealType(null);
		// 帐号姓名
		pa.setApplyAccountName(null);
		// 划扣帐号
		pa.setApplyDeductAccount(null);
		// 还款类型
		pa.setDictPayUse(RepayType.PAYBACK_APPLY.getCode());
		// 开户行名称
		pa.setApplyBankName(null);
		this.saveApplyPayback(pa);

		this.savePosCheck(files,p);
		
		this.saveApplyHistory(pa, p);
	}

	/**
	 * 还款申请操作历史
	 * 2016年2月26日 
	 * By zhangfeng
	 * @param payback
	 * @param pa
	 */
	public void saveApplyHistory(PaybackApply pa, Payback p) {
		// 还款申请操作历史
		PaybackOpeEx paybackOpes = new PaybackOpeEx(pa.getId(), p.getId(),
				RepaymentProcess.REPAYMENT_APPLY, TargetWay.REPAYMENT,
				PaybackOperate.APPLY_SUCEED.getCode(), "合同编号："
						+ p.getContractCode() + "，申请金额："
						+ pa.getApplyAmount());
		historyService.insertPaybackOpe(paybackOpes);
	}

	/**
	 * 还款申请同一合同编号，存入银行重复验证
	 * 2016年2月26日 
	 * By zhangfeng
	 * @param p
	 * @return
	 */
	public String findTransferInfoByStoresInAccount(Payback p) {
		String msg = null;
		List<PaybackApply> paList = new ArrayList<PaybackApply>();
		PaybackApply pa = new PaybackApply();
		pa.setContractCode(p.getContractCode());
		pa.setDictDepositAccount(p.getPaybackTransferInfo().getStoresInAccount());
		pa.setDictPaybackStatus("'"+RepayApplyStatus.MATCH_FAILEN.getCode()+"','"+RepayApplyStatus.REPAYMENT_RETURN.getCode()+"','"+RepayApplyStatus.PRE_ACCOUNT_VERIFY.getCode()+"'");
		paList = paybackService.findApplyPayback(pa);
		if (ArrayHelper.isNotEmpty(paList)) {
			msg = "您录入的汇款单存入银行已经重复，请确认数据是否正确！";
		}
		return msg;
	}
	
	/**
	 * 将划扣申请数据推送给大金融
	 * @author 于飞
	 * @Create 2016年9月28日
	 */
	public void sendDataToDjr(PaybackApply apply){
		logger.info("----正常划扣开始给大金融推送数据，合同编号是【"+apply.getContractCode()+"】----");
		ClientPoxy service = new ClientPoxy(ServiceType.Type.DJR_SEND_APPLY_SERVICE);
		DjrSendApplyInfoInBean inParam = new DjrSendApplyInfoInBean();
		
		inParam.setDataTransferId(System.currentTimeMillis()+"");
		inParam.setOrderId(apply.getId());
		inParam.setContractCode(apply.getContractCode());
		inParam.setApplyTime(apply.getApplyPayDay());
		inParam.setApplyMoney(apply.getApplyAmount());
		inParam.setTransferType("1");
		
		DjrSendApplyInfoOutBean outParam = (DjrSendApplyInfoOutBean) service.callService(inParam); 
		if (ReturnConstant.SUCCESS.equals(outParam.getRetCode())) { 
			// TODO 成功 
			logger.info("----正常划扣给大金融推送数据成功，合同编号是【"+apply.getContractCode()+"】----");
		} else { 
			// TODO 失败 
			logger.info("----正常划扣给大金融推送数据失败，合同编号是【"+apply.getContractCode()+"，"+outParam.getRetMsg()+"】----"); 
		} 
		logger.info("----正常划扣给大金融推送数据结束，合同编号是【"+apply.getContractCode()+"】----");
	}

	/**
	 * 查询门店划扣限制
	 * @author 翁私
	 * @Create 2017年5月10日
	 * @param org
	 * @return
	 */
	public List<DeductPlantLimit> queryDeductCondition(Org org) {
		return dao.queryDeductCondition(org);
	}

	/**
	 * 查询划扣统计信息
	 * @author 翁私
	 * @Create 2017年5月10日
	 * @param org
	 * @return
	 */
	public List<DeductStatistics> queryDeductStatistics(DeductStatistics ts) {
		return dao.queryDeductStatistics(ts);
	}
	
}
