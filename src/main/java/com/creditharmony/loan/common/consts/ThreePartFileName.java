/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.constsThreePartFileName.java
 * @Create By 王彬彬
 * @Create In 2016年3月18日 上午12:01:46
 */
package com.creditharmony.loan.common.consts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.config.Global;
import com.creditharmony.loan.channel.goldcredit.constants.ExportFlagConstants;
import com.creditharmony.loan.common.entity.NumberMaster;
import com.creditharmony.loan.common.service.NumberMasterService;

/**
 * 三方导入导出文件名
 * @Class Name ThreePartFileName
 * @author 王彬彬
 * @Create In 2016年3月18日
 */
@Service
public class ThreePartFileName {
	/**
	 * 中金代付标记（更新周期为天）
	 */
	public static final String ZJDF_FLAG ="001572_F";
	
	/**
	 * 中金代付标记（更新周期为天）
	 */
	public static final String ZJDS_FLAG ="001572_S";
	
	/**
	 * 信和商户号（通联）
	 */
	public static final String tlBusinessCode =Global.getConfig("creditharmony.tonglian.business.code");
	
	/**
	 * 信和商户号（好易联）
	 */
	public static final String hylBusinessCode =Global.getConfig("creditharmony.haoyilian.business.code");
	/**
	 * 富友代收标记
	 */
	public static final String FYDS_FLAG ="AC01";
	
	/**
	 * 汇金打款表文件名
	 */
	public static final String HJ_GRANT_TABLE ="hj_grant_table";
	
	public static final String HJ_GRANT_SUM_TABLE = "hj_grant_sum_table";
	/**
	 * 信借 加急提交批次
	 */
	public static final String LOAN_CUR = "loan_cur";
	
	public static final String URGE_CUR = "urge_cur";
	/**
	 * 金信打款表文件名
	 */
	public static final String GOLD_CREDIT_TABLE ="gold_credit_table";
	
	public static final String GOLD_CREDIT_SUM_TABLE = "gold_credit_sum_table";
	/**
	 * 金信债权退回表文件名
	 */
	public static final String GOLD_CREDIT_RETURN_TABLE ="gold_credit_table";
	
	public static final String GOLD_CREDIT_RETURN_SUM_TABLE = "gold_credit_sum_table";
	
	//资产家打款表文件名
	public static final String GOLD_ZCJ_TABLE ="gold_zcj_table";
	//资产家汇总表文件名
	public static final String GOLD_ZCJ_SUMMARY_TABLE ="gold_zcj_summary_table";
	@Autowired
	private NumberMasterService numberService;
	
	/**
	 * （代付）中金代付导出模版对文件名的要求：001572_F年月日四位数字，例如：001572_F201602030001
	 * 2016年3月18日
	 * By 王彬彬
	 * @return 生成中金代付文件名
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getZjDfExportFileName()
	{
		NumberMaster numberMaster =new NumberMaster();

		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(ZJDF_FLAG);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);

		StringBuffer s = new StringBuffer();
		s.append(ZJDF_FLAG);
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append(String.format("%04d", i));
		
		return s.toString();
	}
	
	/**
	 * （代收）中金代收导出模版对文件名的要求：001572_S年月日四位数字，例如：001572_S201602030001
	 * 2016年3月18日
	 * By 王彬彬
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getZjDsExportFileName(){
		NumberMaster numberMaster =new NumberMaster();

		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(ZJDS_FLAG);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);

		StringBuffer s = new StringBuffer();
		s.append(ZJDS_FLAG);
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append(String.format("%04d", i));
		
		return s.toString();
	}
	
	/**
	 * （代收）通联代收导出模版对文件名的要求：
	 * 200604000000445_S02导出时年月日_五位数字从00001开始，
	 * 例如：200604000000445_S0220160215_00001 
	 * 2016年3月18日 By 王彬彬
	 * 
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getTlDsExportFileName(){
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(tlBusinessCode+"_S02");
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(tlBusinessCode+"_S02");
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append("_");
		s.append(String.format("%05d", i));
		
		return s.toString();
	}
	
	/**
	 * （代付）通联代收导出模版对文件名的要求：
	 * 200604000000445_F02导出时年月日_五位数字从00001开始，
	 * 例如：200604000000445_F0220160215_00001 
	 * 2016年3月18日 By 王彬彬
	 * 
	 * @return 通联代付
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getTlDfExportFileName(){
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(tlBusinessCode+"_F02");
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(tlBusinessCode+"_F02");
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append("_");
		s.append(String.format("%05d", i));
		
		return s.toString();
	}
	
	/**
	 * （代收）富友代收导出模版对文件名的要求：AC01_导出时的年月日_四位数字从0001开始，例如：AC01_20160303_0001
	 * 2016年3月18日
	 * By 王彬彬
	 * @return 富友代收导出模版对文件名
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getFyDsExportFileName()
	{
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(FYDS_FLAG);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append("AC01_");
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append("_");
		s.append(String.format("%04d", i));
		
		return s.toString();
	}
	
	/**
	 * (代收)好易联代收导出EXCEL、TXT模版对文件名的要求：000191400200580_S02导出时的年月日_五位数字从0001开始，
	 * 例如：000191400200580_S0220160215_00001
	 * 2016年3月18日
	 * By 王彬彬
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getHylDsExportFileName()
	{
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(hylBusinessCode+"_S02");
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(hylBusinessCode+"_S02");
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append("_");
		s.append(String.format("%05d", i));
		
		return s.toString();
	}
	
	
	/**
	 * (代收)好易联代收导出EXCEL、TXT模版对文件名的要求：000191400200580_S02导出时的年月日_五位数字从0001开始，
	 * 例如：000191400200580_S0220160215_00001
	 * 2016年3月18日
	 * By 王彬彬
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getHjGrantExportFileName()
	{
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(HJ_GRANT_TABLE);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(FileExtension.SEND_MONEY);
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append("_");
		s.append(String.valueOf(i));
		
		return s.toString();
	}
	
	/**
	 * 放款明细确认时模版对文件名的要求：导出时的年月日_1开始，
	 * 例如：汇总表_20160215_1
	 * 2016年3月18日
	 * By 王彬彬
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getHjGrantSumExportFileName()
	{
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(HJ_GRANT_SUM_TABLE);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(FileExtension.GRANT_SUM);
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append("_");
		s.append(String.valueOf(i));
		
		return s.toString();
	}
	
	/**
	 * 获得信借提交批次，格式为：信借1，信借2
	 * 2016年5月6日
	 * By 朱静越
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getLoanCur(){
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(LOAN_CUR);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(FileExtension.LOAN_NAME);
		s.append(String.valueOf(i));
		
		return s.toString();
	}
	
	/**
	 * 获得金信提交批次，格式为：金信1，金信2
	 * 2016年5月6日
	 * By 朱静越
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getJXLoanCur(){
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(LOAN_CUR);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(FileExtension.JX_LOAN_NAME);
		s.append(String.valueOf(i));
		
		return s.toString();
	}
	
	/**
	 * 获得大金融提交批次，格式为：信借1，信借2
	 * 2016年5月6日
	 * By 朱静越
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getBigFinanceCur(){
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(LOAN_CUR);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(FileExtension.BIG_FININCE_NAME);
		s.append(String.valueOf(i));
		
		return s.toString();
	}
	
	/**
	 * 获得加急提交批次
	 * 2016年5月6日
	 * By 朱静越
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getUrgeCur(){
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		numberMaster.setDealPart(URGE_CUR);
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(FileExtension.URGE_NAME);
		s.append(String.valueOf(i));
		
		return s.toString();
	}
	
	
	
	/**
	 * 放款明细确认时模版对文件名的要求：导出时的年月日_1开始，
	 * 例如：汇总表_20160215_1
	 * 2016年3月18日
	 * By 王彬彬
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getGoldCreditSumExportFileName(String flag)
	{
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		if (StringUtils.isNotBlank(flag) && ExportFlagConstants.GOLD_CREDIT_SURE.equals(flag)) {
			numberMaster.setDealPart(GOLD_CREDIT_SUM_TABLE);
		} else if (StringUtils.isNotBlank(flag) && ExportFlagConstants.GOLD_CREDIT_RETURN.equals(flag)) {
			numberMaster.setDealPart(GOLD_CREDIT_RETURN_SUM_TABLE);
		}
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(FileExtension.GRANT_SUM);
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append("_");
		s.append(String.valueOf(i));
		
		return s.toString();
	}
	/**
	 * (代收)好易联代收导出EXCEL、TXT模版对文件名的要求：000191400200580_S02导出时的年月日_五位数字从0001开始，
	 * 例如：000191400200580_S0220160215_00001
	 * 2016年3月18日
	 * By 王彬彬
	 * @return
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getGoldCreditExportFileName(String flag)
	{
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		if (StringUtils.isNotBlank(flag) && ExportFlagConstants.GOLD_CREDIT_SURE.equals(flag)) {
			numberMaster.setDealPart(GOLD_CREDIT_TABLE);
		} else if (StringUtils.isNotBlank(flag) && ExportFlagConstants.GOLD_CREDIT_RETURN.equals(flag)) {
			numberMaster.setDealPart(GOLD_CREDIT_RETURN_TABLE);
		}
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		s.append(FileExtension.SEND_MONEY);
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append("_");
		s.append(String.valueOf(i));
		
		return s.toString();
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getGoldZcjExportFileName(String flag)
	{
		NumberMaster numberMaster =new NumberMaster();
		
		numberMaster.setDealDate(DateUtils.getDate("yyyyMMdd"));
		numberMaster.setDealCyc(NumberManager.UPDATE_CYC_DAY);
		if (StringUtils.isNotBlank(flag) && ExportFlagConstants.GOLD_ZCJ_MONEY.equals(flag)) {
			numberMaster.setDealPart(GOLD_ZCJ_TABLE);
		} else if (StringUtils.isNotBlank(flag) && ExportFlagConstants.GOLD_ZCJ_SUMMARY.equals(flag)) {
			numberMaster.setDealPart(GOLD_ZCJ_SUMMARY_TABLE);
		}
		numberMaster.setEffective(NumberManager.EFFECTIVE);

		int i = numberService.getNumberMaster(numberMaster);
		
		StringBuffer s = new StringBuffer();
		if (StringUtils.isNotBlank(flag) && ExportFlagConstants.GOLD_ZCJ_MONEY.equals(flag)) {
			s.append(FileExtension.SEND_MONEY);
		} else if (StringUtils.isNotBlank(flag) && ExportFlagConstants.GOLD_ZCJ_SUMMARY.equals(flag)) {
			s.append(FileExtension.GRANT_SUM);
		}
		s.append(DateUtils.getDate("yyyyMMdd"));
		s.append("_");
		s.append(String.valueOf(i));
		
		return s.toString();
	}
	
}
