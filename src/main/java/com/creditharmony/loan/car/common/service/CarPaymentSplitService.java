package com.creditharmony.loan.car.common.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.IdGen;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.CounterofferResult;
import com.creditharmony.core.loan.type.DeductPlat;
import com.creditharmony.core.loan.type.DeductTime;
import com.creditharmony.core.loan.type.OperateType;
import com.creditharmony.core.loan.type.SplitDataStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.loan.borrow.payback.entity.PaybackApply;
import com.creditharmony.loan.borrow.payback.entity.PaybackSplit;
import com.creditharmony.loan.car.common.dao.CarPaybackSplitDao;
import com.creditharmony.loan.common.consts.OperateFlagConstant;
import com.creditharmony.loan.common.consts.PlatRule;
import com.creditharmony.loan.common.dao.PaybackSplitCountDao;
import com.creditharmony.loan.common.dao.PlatformRuleDao;
import com.creditharmony.loan.common.entity.PaybackSplitCount;
import com.creditharmony.loan.common.entity.PlatformRule;
import com.creditharmony.loan.common.utils.IdentifierRule;

/**
 * 还款拆分
 * @Class Name PaymentSplitService
 * @author 王彬彬
 * @Create In 2015年12月21日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CarPaymentSplitService {

	public static PlatRule platrule = null;

	@Autowired
	private PlatformRuleDao platRuleDao;

	@Autowired
	private CarPaybackSplitDao paybackSplitDao;

	@Autowired
	private PaybackSplitCountDao splitCountDao;

	/**
	 * 待还款数据拆分 2015年12月23日 By 王彬彬
	 * 
	 * @param toSpilt
	 *            待拆分列表
	 * @param deductType
	 *            关联类型（还款/催收服务费）
	 * @param deductTime
	 *            批量/实时
	 * @param plat
	 *            划扣平台（指定的场合使用，可以为空，使用还款账户划扣平台）
	 * @return 划扣结果
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public boolean splitList(List<PaybackApply> toSpilt, String deductType,
			DeductTime deductTime, DeductPlat plat) {
		// 查询对应平台
		List<PlatformRule> platformList = new ArrayList<PlatformRule>();
		PlatformRule plateformrule = new PlatformRule();
		platformList = platRuleDao.findAllList(plateformrule);
		if (ArrayHelper.isNotEmpty(platformList)) {
			platrule = new PlatRule(platformList);
		} else {
			throw new ServiceException("平台规则异常！");
		}

		for (PaybackApply apply : toSpilt) {
			if (apply.getApplyAmount().compareTo(BigDecimal.ZERO) > 0) {
				splitObject(apply, deductType, deductTime, plat);
			}
		}
		return true;
	}

	/**
	 * 根据规则拆分 
	 * 2015年12月22日 By 王彬彬
	 * @param apply 待拆分对象
	 * @param plat 拆分平台（可为空，空的场合默认按照申请平台处理）
	 * @return
	 */
	@SuppressWarnings("static-access")
	@Transactional(readOnly = false, value = "loanTransactionManager")
	private boolean splitObject(PaybackApply apply, String deductType,
			DeductTime deductTime, DeductPlat plat) {
		List<PaybackSplit> splitList = new ArrayList<PaybackSplit>();

		Map<String, BigDecimal> map = platrule.getPlatMap();
		// 待划扣金额
		BigDecimal applyAmount = apply.getApplyAmount();
		// 关联 拆分表统计表用 uuid
		String uuId = IdGen.uuid();
		// 批次号
		String pchNo = String.valueOf(System.currentTimeMillis());

		// 平台（有默认平台时使用默认，无平台时候使用还款签约平台）
		if ((plat == null) && !StringUtils.isNotEmpty(apply.getDictDealType())) {
			throw new ServiceException("还款人划扣平台异常!");
		}

		String key = plat == null ? apply.getDictDealType() : plat.getCode();

		// 单笔最大限额(元)
		BigDecimal singlLimit = map.get(key + apply.getApplyBankName()
				+ deductTime.getCode());
		if (singlLimit == null || singlLimit.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ServiceException("平台单笔限额异常!");
		}

		// 可拆分笔数（可以整除的部分，余数不考虑）
		BigDecimal divideCount = applyAmount.divideAndRemainder(singlLimit)[0];
		// 不可拆分笔数（余数部分）
		BigDecimal remainderAmount = applyAmount.divideAndRemainder(singlLimit)[1];

		// 拆分笔数
		int splitCount = 0;

		int splitUp = 0;

		// 拆分后对象
		if (divideCount.compareTo(BigDecimal.ZERO) > 0) {
			for (int i = 0; i < divideCount.intValue(); i++) {
				splitUp++;
				splitList.add(apply2split(apply, singlLimit, pchNo, uuId, key,
						deductType, deductTime, splitUp));
			}

			splitCount = divideCount.intValue();
		}

		// 余数大于0的场合，新增一条划扣记录
		if (remainderAmount.compareTo(BigDecimal.ZERO) > 0) {
			splitUp++;
			splitList.add(apply2split(apply, remainderAmount, pchNo, uuId, key,
					deductType, deductTime, splitUp));

			splitCount++;
		}

		PaybackSplitCount setSplitCount = setSplitCount(pchNo, applyAmount,
				splitCount);
		setSplitCount.setId(uuId);

		// 拆分明细插入
		paybackSplitDao.batchInsertSplitData(splitList);
		// 拆分统计表插入
		// splitCountDao.insert(setSplitCount);

		return true;
	}

	/**
	 * 拆分后对象 初始化数据 
	 * 2015年12月22日 By 王彬彬
	 * @param apply 基础数据对象（要拆分的数据对象）
	 * @param splitedAmount 拆分金额
	 * @param pucNo 批次号
	 * @param rid 关联ID(拆分统计表用ID)
	 * @param key 关联key
	 * @param deductType 拆分类型
	 * @param deductTime 拆分时间
	 * @param splitUp 拆分企业流水号
	 * @return 拆分后数据
	 */
	private PaybackSplit apply2split(PaybackApply apply,
			BigDecimal splitedAmount, String pucNo, String rid, String key,
			String deductType, DeductTime deductTime, int splitUp) {
		Date now = new Date();
		PaybackSplit paybackSplit = new PaybackSplit();

		paybackSplit.setId(IdGen.uuid()); // 插入用ID
		paybackSplit.setrId(apply.getId()); // 关联还款申请表ID
		paybackSplit.setrId2(rid); // 关联拆分统计表ID
		paybackSplit.setSplitPch(pucNo);// 批次号
		paybackSplit.setDictRDeductType(deductType);// 关联类型
		paybackSplit.setApplyDeductAmount(splitedAmount);// 拆分金额
		paybackSplit
				.setSplitBackResult(CounterofferResult.PREPAYMENT.getCode());// 回盘结果
		paybackSplit.setSplitBackDate(now);
		paybackSplit.setBatchFlag(deductTime.getCode());// 是否批量
		paybackSplit.setSplitFailResult(StringUtils.EMPTY);// 回盘失败原因
		paybackSplit.setDictDealType(key); // 划扣平台
		paybackSplit.setDictDealStatus(SplitDataStatus.NO_PROCESS.getCode());// 处理状态（未处理）
		paybackSplit.setDictOptType(apply.getDictDeductType());// 操作类型(集中划扣/还款申请)
		paybackSplit.setIsSend(YESNO.NO.getCode());
		paybackSplit.setSplitFatherId(apply.getSplitFatherId()); // 原拆分父ID
		paybackSplit.setLoanCode(apply.getLoanCode()); // 借款编码
		paybackSplit.setTimeFlag(YESNO.NO.getCode()); // 时间标示默认否
		paybackSplit.setPaybackFlag(apply.getPaybackFlag() == null ? YESNO.YES
				.getCode() : apply.getPaybackFlag());
		paybackSplit.setEnterpriseSerialno(IdentifierRule.getEnterpriseName(
				apply.getContractCode(), OperateFlagConstant.FEECOLLECTION,
				pucNo, splitUp)); // 企业流水号
		if (OperateType.COLLECT_DEDUCT.getCode().equals(
				apply.getDictDeductType())) {
			paybackSplit.setRemark(IdentifierRule.getEnterpriseName(
					apply.getContractCode(), OperateFlagConstant.COLLECTION,
					pucNo, splitUp)); // 备注
		} else if (OperateType.PAYMENT_DEDUCT.getCode().equals(
				apply.getDictDeductType())) {
			paybackSplit.setRemark(IdentifierRule.getEnterpriseName(
					apply.getContractCode(), OperateFlagConstant.PENFREPAYMENT,
					pucNo, splitUp)); // 备注
		} else if (OperateType.SERVICE_FEE.getCode().equals(
				apply.getDictDeductType())) {
			paybackSplit.setRemark(IdentifierRule.getEnterpriseName(
					apply.getContractCode(), OperateFlagConstant.FEECOLLECTION,
					pucNo, splitUp)); // 备注
		}
		return paybackSplit;
	}

	/**
	 * 拆分统计表插入 
	 * 2015年12月22日 By 王彬彬
	 * @param pchNo 批次号
	 * @param splitTotal 拆分总金额
	 * @param bishu 拆分笔数
	 * @return 拆分统计信息
	 */
	private PaybackSplitCount setSplitCount(String pchNo,
			BigDecimal splitTotal, int bishu) {
		PaybackSplitCount splitCount = new PaybackSplitCount();

		splitCount.setSplitPch(pchNo);// 批次号
		splitCount.setSplitAmount(splitTotal);// 拆分金额
		splitCount.setSplitBishu(bishu);// 拆分笔数

		splitCount.setSplitDate(new Date());

		return splitCount;
	}

}
