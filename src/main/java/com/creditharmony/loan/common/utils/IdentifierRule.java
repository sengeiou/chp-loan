/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.utilsIdentifierRule.java
 * @Create By 王彬彬
 * @Create In 2015年12月27日 下午11:15:28
 */
package com.creditharmony.loan.common.utils;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.car.common.consts.CarLoanProductType;
import com.creditharmony.loan.common.consts.NumberManager;

/**
 * 各种编号生成规则
 * 
 * @Class Name IdentifierRule
 * @author 汇金系统
 * @Create In 2015年12月27日
 */
public class IdentifierRule {

	/**
	 * 合同编号生成、修改规则 
	 * 2015年12月28日  By 王彬彬
	 * 
	 * @param loaninfo
	 *            借款信息
	 * @param count
	 *            借款总件数
	 * @param serialNoType
	 *            编号生成修改规则
	 * @param contractNo
	 *            已有合同编号（复议，修改，上调时使用）
	 * @return
	 */
	public static String getFullContract(LoanInfo loaninfo, Integer count,
			SerialNoType serialNoType, String oldContractNo) {
		if (loaninfo == null) {
			return null;
		}

		StringBuffer contractCode = new StringBuffer();
		
		//新生成合同编号
		if (serialNoType.equals(SerialNoType.CONTRACT)) {
			contractCode.append(getContract(loaninfo.getStoreCode(), count));
			return contractCode.toString();
		}

		//合同编号变更的场合
		if (!StringUtils.isNotBlank(oldContractNo)) {
			oldContractNo = getContract(loaninfo.getStoreCode(), count);
		}
		
		Integer splitIndex = oldContractNo.indexOf("-");
		int start = NumberManager.START;//起始编号（1）
		
		if (splitIndex > 0) {
			start = Integer.valueOf(oldContractNo.substring(splitIndex + 2,
					oldContractNo.length())) + NumberManager.STEP;
			
			oldContractNo = oldContractNo.substring(0, splitIndex - 1);
		}
		
		@SuppressWarnings("unused")
		String endcode = String.format("%02d", start);
		
		//根据类型生成不同编号（尾号变化）
		// 无纸化规则，复议无许修改合同编号，使用原有合同号码
		//oldContractNo = oldContractNo + "-" + serialNoType.getCode() + endcode;
		
		contractCode.append(oldContractNo);

		return contractCode.toString();

	}

	/**
	 * 信借合同编号生成规则 
	 * 2015年12月27日 
	 * By 王彬彬
	 * 
	 * @param loaninfo
	 *            借款信息
	 * @param count
	 *            借款总笔数
	 * @param createType
	 *            编号生成规则
	 * @return
	 */
	private static String getContract(String storeCode, int count) {
		// 五位流水生成
		if (count <= 0) {
			count = 1;
		}
		String serial = String.format("%05d", count);
		String contractNum = storeCode + serial;
		return contractNum;
	}

	/**
	 * 借款编号生成规则 
	 * 2015年12月28日  By 王彬彬
	 * 
	 * @param serialNo 借款笔数（最新使用编号）
	 * @return
	 */
	public static String getLoanCode(int serialNo, String dateFlag,
			SerialNoType serialNoType) {
		return serialNoType.getCode() + dateFlag
				+ String.format("%08d", serialNo);
	}
	
	/**
	 * 客户编号生成规则 (JKKH+年月日+6位流水号)
	 * 2016年1月11日
	 * By 王彬彬
	 * @param serialNo
	 * @param dateFlag
	 * @param serialNoType
	 * @return
	 */
	public static String getCustomerCode(int serialNo, String dateFlag,
			SerialNoType serialNoType) {
		return serialNoType.getCode() + dateFlag
				+ String.format("%06d", serialNo);
	}
	
	/**
	 * 企业流水号（没有中文）
	 * 导出富友模板里的【企业流水账号】导出规则为：
	 * 合同编号_还款类型（集中/非集中）_唯一码（上传回执结果表时用于更新系统状态的唯一识别码）
	 * 2016年1月11日
	 * By 王彬彬
	 * @param ContractNo 合同编号
	 * @param operateType 操作类型
	 * @param pch 批次号
	 * @param uniqueNo 唯一编号（连续序号）
	 * @return
	 */
	public static String getEnterpriseCode(String ContractNo,
			String operateType, String pch, int uniqueNo) {
		return pch+String.format("%02d", uniqueNo);
	}
	/**
	 * 企业流水号(有中文)
	 * 导出富友模板里的【企业流水账号】导出规则为：
	 * 合同编号_还款类型（集中/非集中）_唯一码（上传回执结果表时用于更新系统状态的唯一识别码）
	 * 2016年1月11日
	 * By 王彬彬
	 * @param ContractNo 合同编号
	 * @param operateType 操作类型
	 * @param pch 批次号
	 * @param uniqueNo 唯一编号（连续序号）
	 * @return
	 */
	public static String getEnterpriseName(String ContractNo,
			String operateType, String pch, int uniqueNo) {
		return ContractNo+"_" + operateType+"_" +pch+String.format("%02d", uniqueNo);
	}
	
	public static String generalFullContract(String productTypeName, String proShortName, String cityCode, String carLoanCode, Integer count) {
		StringBuffer contractCode = new StringBuffer();
		contractCode.append(CarLoanProductType.parseByName(productTypeName).getCode());
		contractCode.append("（" + proShortName + "）");
		contractCode.append("借字（" + DateUtils.getYear() + "）第");
		contractCode.append(cityCode);
		//新生成合同编号:质（黑）借字（2014）第01010001号--------G（省会简称）借字（年份）第xx（城市编号） xx（门店编号）  xxxx（序号/4位数字）号
		contractCode.append(getSerialContract(carLoanCode, count));
		contractCode.append("号");
		return contractCode.toString();
	}
	
	private static String getSerialContract(String carLoanCode, int count) {
		// 四位流水生成
		if (count <= 0) {
			count = 1;
		}
		String serial = String.format("%04d", count);
		String contractNum = carLoanCode + serial;
		return contractNum;
	}
}
