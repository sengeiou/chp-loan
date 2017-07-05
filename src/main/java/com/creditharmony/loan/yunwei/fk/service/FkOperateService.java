package com.creditharmony.loan.yunwei.fk.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.yunwei.fk.dao.FkOperateDao;
import com.creditharmony.loan.yunwei.fk.entity.FkOperateObj;
import com.creditharmony.loan.yunwei.fk.entity.PaybackBlueAmount;
import com.sun.star.uno.Exception;

@Service("fkOperateService")
public class FkOperateService extends CoreManager<FkOperateDao, FkOperateObj> {
	
	@Autowired
	private FkOperateDao fkOperateDao;
	
	/**
	 * 生成SQL解决方案
	 * @return
	 */
	public boolean fkOperateTwo() {
		boolean returnFlag = false;
		// 查出要修复的数据
		List<FkOperateObj> dataList =  fkOperateDao.selectDataForFs();
		String filePathAndName = "";
		//String filePathAndName = "C:\\Users\\Administrator\\Desktop\\风控修改数据_20161117.sql";
		File writeFileObj = new File(filePathAndName);
		if(writeFileObj.exists()) {
			writeFileObj.delete();
		}
		try {
			writeFileObj.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(writeFileObj));
			// 生成SQL
			for (FkOperateObj fkOperateObj : dataList) {
				operate(fkOperateObj.getPaybackBuleAmount(),fkOperateObj.getRepaymentDate(),fkOperateObj.getContractCode(),out);
				out.flush(); 
			}
		} catch (IOException e) {
			
		} 
		return returnFlag;
	}
	
	
	 public static String dateFormat(String datetime) {  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = sdf.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		cl.add(Calendar.MONTH, 1);
		date = cl.getTime();
		return sdf.format(date);
	}
	
	 
 
	public static String dateStr1 = DateUtils.getDate("yyyyMMdd");
	
	/**
	 * 风控批量修改数据
	 * @return
	 */
	@Transactional
	public FkOperateObj fkOperate(FkOperateObj parmasObj) throws Exception, IOException {
		
		String dateStr = DateUtils.getDate("yyyyMMdd");
		String filePathAndName = "";
		//String filePathAndName = "C:\\Users\\Administrator\\Desktop\\风控修改数据_"+dateStr+".sql";
		// 将SQL语句写入文件
		File writeFileObj = new File(filePathAndName);
		if(writeFileObj.exists()) {
			writeFileObj.delete();
		}
		writeFileObj.createNewFile(); // 创建新文件  
		BufferedWriter out = new BufferedWriter(new FileWriter(writeFileObj));
		//==================================================================//
		String filePathAndName2 ="";
		//String filePathAndName2 = "C:\\Users\\Administrator\\Desktop\\风控修改数据_"+dateStr+"问题数据.sql";
		File writeFileObj2 = new File(filePathAndName2);
		if(writeFileObj2.exists()) {
			writeFileObj2.delete();
		}
		writeFileObj2.createNewFile(); // 创建新文件  
		BufferedWriter out2 = new BufferedWriter(new FileWriter(writeFileObj2));
		// 
		FkOperateObj returnObj = new FkOperateObj();
		// 获取合同列表
		// String [] contractArry = parmasObj.getContractCodes().split(",");
		// 查出要修复的数据
		List<FkOperateObj> xiufuDataList =  fkOperateDao.selectDataForXiufu();
		// 
		for (FkOperateObj xiufuObj : xiufuDataList) {
			// 要修复的合同编号
			String contractCode = xiufuObj.getContractCode();
			// 
			String firstXiuFuDate = xiufuObj.getFirstXiuFuDate();
			if(StringUtils.isEmpty(contractCode)) {
				continue;
			} else {
				contractCode = contractCode.trim();
			}
			out.write("\r\n----------------------------------"+ contractCode +"----------------------------------\r\n");
			// 查询期供和蓝补金额
			FkOperateObj blueAndQg = fkOperateDao.queryBlueMoneyAndQg(contractCode);
			if(null == blueAndQg) {
				blueAndQg = new FkOperateObj();
			}
			// 当前蓝补金额（查询数据库）查询蓝补金额、期供金额
			BigDecimal paybackBuleAmount = blueAndQg.getCurrentBlueMoney();
			// 
			BigDecimal qgMoney = blueAndQg.getQgMoney();
			// 将本期的SQL生成
			BigDecimal blueMoneyOne = operate(paybackBuleAmount,firstXiuFuDate,contractCode,out);
			HashMap<String, String> parmasMap = new HashMap<String, String>();
			parmasMap.put("firstXiuFuDate", firstXiuFuDate);
			parmasMap.put("contractCode", contractCode);
			List<String> repaymentDayList = fkOperateDao.selectRepaymentDayList(parmasMap);
			for (String repaymentDay : repaymentDayList) {
				Object[] returnObj1  = operateLater(blueMoneyOne,repaymentDay,contractCode,qgMoney,out,out2);
				if(!(Boolean) returnObj1[1]) {
					blueMoneyOne = (BigDecimal) returnObj1[0];
				} else {
					break;
				}
			}
			/*
			// 截取还款日
			String day = firstXiuFuDate.substring(firstXiuFuDate.length()-2,firstXiuFuDate.length());
			String month = firstXiuFuDate.substring(firstXiuFuDate.length()-5,firstXiuFuDate.length()-3);
			if("07".equals(month)) {
				// 判断8月进账
				String repaymentDate8 = "2016-08-" + day;
				Object[] returnObj8 = operateLater(blueMoneyOne,repaymentDate8,contractCode,qgMoney,out,out2);
				if (!(Boolean) returnObj8[1]) {
					BigDecimal blueMoneyTwo = (BigDecimal) returnObj8[0];
					// 判断9月进账
					String repaymentDate9 = "2016-09-" + day;
					Object[] returnObj9 = operateLater(blueMoneyTwo, repaymentDate9, contractCode, qgMoney, out, out2);
					if (!(Boolean) returnObj9[1]) {
						BigDecimal blueMoneyThree = (BigDecimal) returnObj9[0];
						// 判断10月进账
						String repaymentDate10 = "2016-10-" + day;
						Object[] returnObj10 = operateLater(blueMoneyThree, repaymentDate10, contractCode, qgMoney, out,out2);
						if (!(Boolean) returnObj10[1]) {
							BigDecimal blueMoneyFour = (BigDecimal) returnObj10[0];
							String repaymentDate11 = "2016-11-" + day;
							Object[] returnObj11 = operateLater(blueMoneyFour, repaymentDate11, contractCode, qgMoney,out, out2);
							if (!(Boolean) returnObj11[1]) {
								BigDecimal blueMoneyFive = (BigDecimal) returnObj11[0];
								String repaymentDate12 = "2016-12-" + day;
								Object[] returnObj12 = operateLater(blueMoneyFive, repaymentDate12, contractCode,qgMoney, out, out2);
								if (!(Boolean) returnObj12[1]) {
									BigDecimal blueMoneySix = (BigDecimal) returnObj12[0];
									String repaymentDate01 = "2017-01-" + day;
									Object[] returnObj01 = operateLater(blueMoneySix, repaymentDate01, contractCode,qgMoney, out, out2);
									if (!(Boolean) returnObj01[1]) {
										BigDecimal blueMoneySeven = (BigDecimal) returnObj01[0];
										if ("30".equals(day)) {
											day = "28";
										}
										String repaymentDate02 = "2017-02-" + day;
										Object[] returnObj02 = operateLater(blueMoneySeven, repaymentDate02,contractCode, qgMoney, out, out2);
										if (!(Boolean) returnObj02[1]) {
											BigDecimal blueMoney8 = (BigDecimal) returnObj02[0];
											if ("28".equals(day)) {
												day = "30";
											}
											String repaymentDate03 = "2017-03-" + day;
											Object[] returnObj03 = new Object[2];
											returnObj03 = operateLater(blueMoney8, repaymentDate03, contractCode,qgMoney, out, out2);
											logger.info("修改后最终蓝补金额：" + returnObj03[0]);
										}
									}
								}
							}
						}
					}
				}
			} else if("08".equals(month)) {
				// 判断9月进账
				String repaymentDate9 = "2016-09-" + day;
				Object[] returnObj9 = operateLater(blueMoneyOne,repaymentDate9,contractCode,qgMoney,out,out2);
				if(!(Boolean)returnObj9[1]){
					BigDecimal blueMoneyThree = (BigDecimal)returnObj9[0];
					// 判断10月进账
					String repaymentDate10 = "2016-10-" + day;
					Object[] returnObj10 = operateLater(blueMoneyThree,repaymentDate10,contractCode,qgMoney,out,out2);
					if(!(Boolean)returnObj10[1]){
						BigDecimal blueMoneyFour = (BigDecimal)returnObj10[0];
						String repaymentDate11 = "2016-11-" + day;
						Object[] returnObj11 =  operateLater(blueMoneyFour,repaymentDate11,contractCode,qgMoney,out,out2);
						if(!(Boolean)returnObj11[1]){
							BigDecimal blueMoneyFive = (BigDecimal)returnObj11[0];
							String repaymentDate12 = "2016-12-" + day;
							Object[] returnObj12 =  operateLater(blueMoneyFive,repaymentDate12,contractCode,qgMoney,out,out2);
							if(!(Boolean)returnObj12[1]){
								BigDecimal blueMoneySix = (BigDecimal)returnObj12[0];
								String repaymentDate01 = "2017-01-" + day;
								Object[] returnObj01 = operateLater(blueMoneySix,repaymentDate01,contractCode,qgMoney,out,out2);
								if(!(Boolean)returnObj01[1]) {
									BigDecimal blueMoneySeven = (BigDecimal)returnObj01[0];
									if("30".equals(day)) {
										day = "28";
									}
									String repaymentDate02 = "2017-02-" + day;
									Object[] returnObj02 = operateLater(blueMoneySeven, repaymentDate02,contractCode, qgMoney, out, out2);
									if (!(Boolean) returnObj02[1]) {
										BigDecimal blueMoney8 = (BigDecimal) returnObj02[0];
										if ("28".equals(day)) {
											day = "30";
										}
										String repaymentDate03 = "2017-03-" + day;
										Object[] returnObj03 = new Object[2];
										returnObj03 = operateLater(blueMoney8, repaymentDate03, contractCode,qgMoney, out, out2);
										logger.info("修改后最终蓝补金额：" + returnObj03[0]);
									}
								}
							}
						}
					}
				}
			} else if("09".equals(month)) {
				String repaymentDate10 = "2016-10-" + day;
				Object[] returnObj10 = operateLater(blueMoneyOne,repaymentDate10,contractCode,qgMoney,out,out2);
				if(!(Boolean)returnObj10[1]){
					BigDecimal blueMoneyFour = (BigDecimal)returnObj10[0];
					String repaymentDate11 = "2016-11-" + day;
					Object[] returnObj11 =  operateLater(blueMoneyFour,repaymentDate11,contractCode,qgMoney,out,out2);
					if(!(Boolean)returnObj11[1]){
						BigDecimal blueMoneyFive = (BigDecimal)returnObj11[0];
						String repaymentDate12 = "2016-12-" + day;
						Object[] returnObj12 =  operateLater(blueMoneyFive,repaymentDate12,contractCode,qgMoney,out,out2);
						if(!(Boolean)returnObj12[1]){
							BigDecimal blueMoneySix = (BigDecimal)returnObj12[0];
							String repaymentDate01 = "2017-01-" + day;
							Object[] returnObj01  = operateLater(blueMoneySix,repaymentDate01,contractCode,qgMoney,out,out2);
							if(!(Boolean)returnObj01[1]) {
								BigDecimal blueMoneySeven = (BigDecimal)returnObj01[0];
								if("30".equals(day)) {
									day = "28";
								}
								String repaymentDate02 = "2017-02-" + day;
								Object[] returnObj02 = operateLater(blueMoneySeven, repaymentDate02,contractCode, qgMoney, out, out2);
								if (!(Boolean) returnObj02[1]) {
									BigDecimal blueMoney8 = (BigDecimal) returnObj02[0];
									if ("28".equals(day)) {
										day = "30";
									}
									String repaymentDate03 = "2017-03-" + day;
									Object[] returnObj03 = new Object[2];
									returnObj03 = operateLater(blueMoney8, repaymentDate03, contractCode,qgMoney, out, out2);
									logger.info("修改后最终蓝补金额：" + returnObj03[0]);
								}
							}
						}
					}
				}
			} else if("10".equals(month)) {
				String repaymentDate11 = "2016-11-" + day;
				Object[] returnObj11 =  operateLater(blueMoneyOne,repaymentDate11,contractCode,qgMoney,out,out2);
				if(!(Boolean)returnObj11[1]){
					BigDecimal blueMoneyFive = (BigDecimal)returnObj11[0];
					String repaymentDate12 = "2016-12-" + day;
					Object[] returnObj12 =  operateLater(blueMoneyFive,repaymentDate12,contractCode,qgMoney,out,out2);
					if(!(Boolean)returnObj12[1]){
						BigDecimal blueMoneySix = (BigDecimal)returnObj12[0];
						String repaymentDate01 = "2017-01-" + day;
						Object[] returnObj01 =  operateLater(blueMoneySix,repaymentDate01,contractCode,qgMoney,out,out2);
						if(!(Boolean)returnObj01[1]) {
							BigDecimal blueMoneySeven = (BigDecimal)returnObj01[0];
							if("30".equals(day)) {
								day = "28";
							}
							String repaymentDate02 = "2017-02-" + day;
							Object[] returnObj02 = operateLater(blueMoneySeven, repaymentDate02,contractCode, qgMoney, out, out2);
							if (!(Boolean) returnObj02[1]) {
								BigDecimal blueMoney8 = (BigDecimal) returnObj02[0];
								if ("28".equals(day)) {
									day = "30";
								}
								String repaymentDate03 = "2017-03-" + day;
								Object[] returnObj03 = new Object[2];
								returnObj03 = operateLater(blueMoney8, repaymentDate03, contractCode,qgMoney, out, out2);
								logger.info("修改后最终蓝补金额：" + returnObj03[0]);
							}
						}
					}
				}
			} else if("11".equals(month)) {
				String repaymentDate12 = "2016-12-" + day;
				Object[] returnObj12 =  operateLater(blueMoneyOne,repaymentDate12,contractCode,qgMoney,out,out2);
				if(!(Boolean)returnObj12[1]){
					BigDecimal blueMoneySix = (BigDecimal)returnObj12[0];
					String repaymentDate01 = "2017-01-" + day;
					Object[] returnObj01 = operateLater(blueMoneySix,repaymentDate01,contractCode,qgMoney,out,out2);
					if(!(Boolean)returnObj01[1]) {
						BigDecimal blueMoneySeven = (BigDecimal)returnObj01[0];
						if("30".equals(day)) {
							day = "28";
						}
						String repaymentDate02 = "2017-02-" + day;
						Object[] returnObj02 = operateLater(blueMoneySeven, repaymentDate02,contractCode, qgMoney, out, out2);
						if (!(Boolean) returnObj02[1]) {
							BigDecimal blueMoney8 = (BigDecimal) returnObj02[0];
							if ("28".equals(day)) {
								day = "30";
							}
							String repaymentDate03 = "2017-03-" + day;
							Object[] returnObj03 = new Object[2];
							returnObj03 = operateLater(blueMoney8, repaymentDate03, contractCode,qgMoney, out, out2);
							logger.info("修改后最终蓝补金额：" + returnObj03[0]);
						}
					}
				}
			} else if("12".equals(month)) {
				String repaymentDate01 = "2017-01-" + day;
				Object[] returnObj01 = operateLater(blueMoneyOne,repaymentDate01,contractCode,qgMoney,out,out2);
				if(!(Boolean)returnObj01[1]) {
					BigDecimal blueMoneySeven = (BigDecimal)returnObj01[0];
					if("30".equals(day)) {
						day = "28";
					}
					String repaymentDate02 = "2017-02-" + day;
					Object[] returnObj02 = operateLater(blueMoneySeven, repaymentDate02,contractCode, qgMoney, out, out2);
					if (!(Boolean) returnObj02[1]) {
						BigDecimal blueMoney8 = (BigDecimal) returnObj02[0];
						if ("28".equals(day)) {
							day = "30";
						}
						String repaymentDate03 = "2017-03-" + day;
						Object[] returnObj03 = new Object[2];
						returnObj03 = operateLater(blueMoney8, repaymentDate03, contractCode,qgMoney, out, out2);
						logger.info("修改后最终蓝补金额：" + returnObj03[0]);
					}
				}
			} else if("01".equals(month)) {
				if("30".equals(day)) {
					day = "28";
				}
				String repaymentDate02 = "2017-02-" + day;
				Object[] returnObj02 = operateLater(blueMoneyOne, repaymentDate02,contractCode, qgMoney, out, out2);
				if (!(Boolean) returnObj02[1]) {
					BigDecimal blueMoney8 = (BigDecimal) returnObj02[0];
					if ("28".equals(day)) {
						day = "30";
					}
					String repaymentDate03 = "2017-03-" + day;
					Object[] returnObj03 = new Object[2];
					returnObj03 = operateLater(blueMoney8, repaymentDate03, contractCode,qgMoney, out, out2);
					logger.info("修改后最终蓝补金额：" + returnObj03[0]);
				}
			} else if("02".equals(month)) {
				if ("28".equals(day)) {
					day = "30";
				}
				String repaymentDate03 = "2017-03-" + day;
				Object[] returnObj03 = new Object[2];
				returnObj03 = operateLater(blueMoneyOne, repaymentDate03, contractCode,qgMoney, out, out2);
				logger.info("修改后最终蓝补金额：" + returnObj03[0]);
			}
			*/
			out.flush();  // 把缓存区内容压入文件 
			out2.flush();
		}
		out.close(); // 关闭流
		out2.close();
		logger.info("--------------------------------操作完成-----------------------------------");
		return returnObj;
	}
	
	// 处理当期之后的还款日
	private Object[] operateLater(BigDecimal paybackBuleAmount,
		String repaymentDate, String contractCode,BigDecimal qgMoney,
		BufferedWriter out,BufferedWriter out2) throws IOException {
		Object[] returnObj = new Object[2];
		BigDecimal returnMoney = BigDecimal.ZERO;
		boolean flagMoneyNotOk = false;
		// 获取当前划扣前五个工作日是否有进账
		boolean tqhjFlag = getTqhkMoney(repaymentDate,"5",contractCode,out2,qgMoney);
		// 获取当前划扣日的两个工作日后的日期
		List<String> repayDateAfterList = fkOperateDao.selectNowDayAfterDate(repaymentDate);
		String endDate = repayDateAfterList.get(2);
		PaybackBlueAmount parmarsObj = new PaybackBlueAmount();
		parmarsObj.setParContractCode(contractCode);
		parmarsObj.setStartDate(repaymentDate);
		parmarsObj.setEndDate(endDate);
		// 查询当前划扣日的两个工作日后的日期
		List<PaybackBlueAmount> actualMoneyList = fkOperateDao.queryActualRepayAmount(parmarsObj);
		// 查询是否有进账,进账金额和期供进行比对
		BigDecimal jzMoney = BigDecimal.ZERO;
		if(null != actualMoneyList && actualMoneyList.size() > 0) {
			for (PaybackBlueAmount paybackBlueAmount : actualMoneyList) {
				jzMoney = jzMoney.add(paybackBlueAmount.getActualRepayMoney());
			}
		}
		if(jzMoney.compareTo(qgMoney) >=0 || tqhjFlag) {
			returnMoney = operate(paybackBuleAmount,repaymentDate,contractCode,out);
		} else {
			String msg = "--两个工作日内无大于期供的金额入账，合同编号：" + contractCode + "还款日：" + repaymentDate + "\r\n";
			logger.info(msg);
			out2.write(msg);
			flagMoneyNotOk = true;
		}
		returnObj[0] = returnMoney;
		returnObj[1] = flagMoneyNotOk;
		return returnObj;
	}
	
	private boolean getTqhkMoney(String repaymentDate,String beforeDay,
		String contractCode,BufferedWriter out2,BigDecimal qgMoney) throws IOException {
		boolean tqhjFlag = false;
		HashMap<String, String> parmasMap = new HashMap<String, String>();
		parmasMap.put("repaymentDate", repaymentDate);
		parmasMap.put("beforeDay", beforeDay);
		List<String> repayDateBeforeList = fkOperateDao.selectNowDayBeforeDate(parmasMap);
		String startDate = repayDateBeforeList.get(4);
		PaybackBlueAmount parmarsObj = new PaybackBlueAmount();
		parmarsObj.setParContractCode(contractCode);
		parmarsObj.setStartDate(startDate);
		parmarsObj.setEndDate(repaymentDate);
		// 查询当前划扣日的两个工作日后的日期
		List<PaybackBlueAmount> actualMoneyList = fkOperateDao.queryActualRepayAmount(parmarsObj);
		// 查询是否有进账,进账金额和期供进行比对
		BigDecimal jzMoney = BigDecimal.ZERO;
		if(null != actualMoneyList && actualMoneyList.size() > 0) {
			for (PaybackBlueAmount paybackBlueAmount : actualMoneyList) {
				jzMoney = jzMoney.add(paybackBlueAmount.getActualRepayMoney());
			}
		}
		// 
		if(jzMoney.compareTo(qgMoney) >=0) {
			String msg = "--提前还款进账金额大于等于期供金额，合同编号：" + contractCode + "还款日：" + repaymentDate + "\r\n";
			logger.info(msg);
			out2.write(msg);
			tqhjFlag = true;
		} else {
			String msg = "--提前还款进账金额小于期供金额（"+ jzMoney +"元），合同编号：" + contractCode + "还款日：" + repaymentDate + "\r\n";
			logger.info(msg);
			// out2.write(msg);
		}
		return tqhjFlag;
	}
	
	/**
	 * 
	 * @param paybackBuleAmount 当前蓝补金额
	 * @param repaymentDate 还款日
	 * @param contractCode 合同编号
	 * @return 累加后的蓝补金额
	 * @throws IOException 
	 */
	private BigDecimal operate(BigDecimal paybackBuleAmount,String repaymentDate, String contractCode,BufferedWriter out) throws IOException {
		FkOperateObj parmasObj = new FkOperateObj();
		// 查询当期的还款明细
		parmasObj.setContractCode(contractCode);
		parmasObj.setRepaymentDate(repaymentDate);
		// 查询还款明细 repaymentDate+contractCode
		FkOperateObj repaymentMonthObj = fkOperateDao.queryCurrentRepayment(parmasObj);
		// 修改还款明细
		String updateRepaymentMonth = 
			"UPDATE jk.t_jk_payback_month m SET "
			+ "month_interest_punishactual =0,"
			+ "month_punish_reduction=month_interest_punishshould,"
			+ "month_penalty_actual= 0,"
			+ "month_penalty_reduction = (CASE WHEN c.contract_version >= '4' THEN 0 ELSE month_penalty_should  END),"
			+ "actual_month_late_fee = 0,"
			+ "month_late_fee_reduction = month_late_fee,"
			+ "dict_month_status = '3',"
			+ "modify_by_fk = 'FK_BATCH_"+dateStr1+"' "
			+ "FROM jk.t_jk_contract c WHERE c.contract_code = m.contract_code AND  "
			+ "m.id = '" + repaymentMonthObj.getPaybackMonthId() + "'"
			+ " ;";
		logger.info("修改还款明细sql：" + updateRepaymentMonth);
		out.write(updateRepaymentMonth + "\r\n");
		// 实还罚息 + 实还违约金  + 实还滞纳金
		BigDecimal wyjFxZnj = repaymentMonthObj.getMonthInterestPunishactual()
			.add(repaymentMonthObj.getMonthPenaltyActual())
			.add(repaymentMonthObj.getActualMonthLateFee()) ;
		BigDecimal jyhBlueMoney = BigDecimal.ZERO;
		// 如果实还大于0
		if(wyjFxZnj.compareTo(BigDecimal.ZERO) > 0) {
			// 交易用途
			String jyyt = "转出已入账"+ repaymentMonthObj.getMonthPayDay() +"冲抵违约金罚息金额"+ wyjFxZnj +"元";
			// 交易后蓝补金额
			jyhBlueMoney = paybackBuleAmount.add(wyjFxZnj);
			// 插入蓝补对账单
			String insertSql = "INSERT INTO JK.T_JK_PAYBACK_BULE_AMONT "
				+ "(ID, R_MONTH_ID, DEAL_TIME, TRADE_TYPE, OPERATOR, DICT_DEAL_USE, TRADE_AMOUNT, SURPLUS_BULE_AMOUNT, "
				+ "CREATE_BY, CREATE_TIME, modify_by_fk, MODIFY_TIME, DICT_OFFSET_TYPE, CONTRACT_CODE, DICT_SOURCE_TYPE, DICT_SOURCE_TYPE_PCL) "
				+ "VALUES(JK.GETUUID32(), NULL, NOW(), 0, '系统处理', "
				+ " '"+ jyyt +"' ," + wyjFxZnj + "," + jyhBlueMoney  // '转出已入账2016-7-23冲抵违约金罚息金额505.73元，交易金额505.73元', 505.73, 505.73,
				+ ", 'FK_BATCH_"+dateStr1+"', NOW(), 'FK_BATCH_"+dateStr1+"', NOW(), '', "
				+ " '"+ contractCode +"'"//合同编号
				+ ", '3', '3');";
			logger.info("插入蓝补对账单sql：" + insertSql);
			out.write(insertSql + "\r\n");
		
			// 查询蓝补对账单	
			List<PaybackBlueAmount> paybackBlueAmountList = fkOperateDao.queryPaybackBlueAmount(contractCode);
			// 要作废的ID
			String idsStr = "";
			StringBuffer idsBuffer = new StringBuffer("");
			for (PaybackBlueAmount paybackBlueAmount : paybackBlueAmountList) {
				if(paybackBlueAmount.getrMonthId().equals(repaymentMonthObj.getPaybackMonthId())
					&&(paybackBlueAmount.getDictDealUse().equals("3")
					||paybackBlueAmount.getDictDealUse().equals("4")
					||paybackBlueAmount.getDictDealUse().equals("5")
					||paybackBlueAmount.getDictDealUse().contains("违约金") 
					||paybackBlueAmount.getDictDealUse().contains("罚息") 
					||paybackBlueAmount.getDictDealUse().contains("滞纳金") )) {
					idsBuffer.append("'").append(paybackBlueAmount.getBlueAmountId()).append("',");
				}
			}
			if(idsBuffer.length()>0) {
				idsStr = idsBuffer.toString();
			}
			if(idsStr.length() > 0) {
				idsStr = idsStr.substring(0,idsStr.length()-1);
			}
			if(idsStr.length() > 0) {
				// 修改蓝补对账单（作废）
				String updatePaybackBlueAmountSql = "UPDATE JK.T_JK_PAYBACK_BULE_AMONT t SET modify_by_fk = 'FK_BATCH_"+dateStr1+"',"
					+ "DICT_DEAL_USE = ("
					+ "CASE WHEN tt.DICT_DEAL_USE = '0' THEN '冲分期服务费（作废）' "
					+ "WHEN tt.DICT_DEAL_USE = '1' THEN '冲利息（作废）'"
					+ "WHEN tt.DICT_DEAL_USE = '2' THEN '冲本金（作废）'"
					+ "WHEN tt.DICT_DEAL_USE = '3' THEN '冲违约金（作废）'"
					+ "WHEN tt.DICT_DEAL_USE = '4' THEN '冲罚息（作废）'"
					+ "WHEN tt.DICT_DEAL_USE = '5' THEN '冲滞纳金（作废）'"
					+ "ELSE tt.DICT_DEAL_USE || '（作废）' END "
					+ ") FROM JK.T_JK_PAYBACK_BULE_AMONT tt WHERE tt.id = t.id AND "
					+ "t.id IN (" + idsStr
					+ ") ;" ;
				logger.info("更新蓝补对账单sql：" + updatePaybackBlueAmountSql);
				out.write(updatePaybackBlueAmountSql + "\r\n");
			}
			// 修改蓝补金额
			String updatePaybackSql = "UPDATE jk.t_jk_payback SET modify_by_fk = 'FK_BATCH_"+dateStr1+"', payback_bule_amount=" + jyhBlueMoney
				+ " WHERE contract_code = '"+ contractCode +"' ;" ;
			logger.info("修改蓝补金额sql：" + updatePaybackSql);
			out.write(updatePaybackSql + "\r\n");
		}
		return jyhBlueMoney;
	}
}
