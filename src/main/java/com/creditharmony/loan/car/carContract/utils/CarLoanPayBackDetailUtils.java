package com.creditharmony.loan.car.carContract.utils;

import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.loan.car.carContract.ex.CarLoanPaybackBrief;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.util.Arith;

import java.math.BigDecimal;
import java.util.*;

/**
 * 计算还款明细工具类
 *
 * @author 陈伟丽
 */
public class CarLoanPayBackDetailUtils {

    /**
     * carLoanPaybackDetail 还款明细表 计算还款明细
     *
     * @return map
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public synchronized static Map getPayBack(CarLoanInfo carLoanInfo, CarContract carContract, CarCheckRate carCheckRate) {
        // 总费率
        double zfl = getZfl(carContract.getGrossRate().doubleValue());

        // 把每一条数据放到List里面
        List<CarLoanPaybackBrief> list = new ArrayList<CarLoanPaybackBrief>();

        Map map = new HashMap();
        String loanMon = carContract.getContractMonths() + "";
        /*
         * 借款天数为30 的还款明细
		 */
        if ("30".equals(loanMon)) {
            // 还款明细实体
            CarLoanPaybackBrief carLoanPaybackDetail = new CarLoanPaybackBrief();
            carLoanPaybackDetail.setCurrentLimitTimme(1);
            // 计算首期还款日
            Date date = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar = Calendar.getInstance();
            Date repaymentDate = null;
            if (date != null) {
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 29);
                repaymentDate = calendar.getTime();
                carLoanPaybackDetail.setRepaymentDate(repaymentDate);
            }
            map.put("repaymentDateq", repaymentDate);
            map.put("repaymentDatez", repaymentDate);
            carLoanPaybackDetail.setCurrentInterest(carContract
                    .getContractAmount().doubleValue());
            //carLoanPaybackDetail.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail);
			/*
			 * 借款天数为90 的还款明细
			 */
        } else if ("90".equals(loanMon)) {
            // 还款明细实体1
            CarLoanPaybackBrief carLoanPaybackDetail = new CarLoanPaybackBrief();
            carLoanPaybackDetail.setCurrentLimitTimme(1);
            // 计算首期还款日
            Date date = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar = Calendar.getInstance();
            Date repaymentDate = null;
            if (date != null) {
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 29);
                repaymentDate = calendar.getTime();
                carLoanPaybackDetail.setRepaymentDate(repaymentDate);
            }
            map.put("repaymentDateq", repaymentDate);
            // 月还款金额 = 月利息+综合服务费
            BigDecimal yueLix = carCheckRate.getMonthlyInterest();
            BigDecimal zhfwf = carCheckRate.getComprehensiveServiceFee();
            Double yuehuanjine = Arith.add(yueLix.doubleValue(), zhfwf.doubleValue());
            carLoanPaybackDetail.setCurrentInterest(yuehuanjine);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额*1.02
                BigDecimal htje = carContract.getContractAmount();
                Double yicixinghuankuan = Arith.mul(htje.doubleValue(), 1.02);
                carLoanPaybackDetail.setPrepayment(yicixinghuankuan);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail.setPrepayment(calatePrepayment(carContract, 1, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail.setPrepayment(calatePrepayment(carContract, 1, 0D, zfl));
            }

            //carLoanPaybackDetail.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail);

            // 还款明细实体2
            CarLoanPaybackBrief carLoanPaybackDetail2 = new CarLoanPaybackBrief();
            carLoanPaybackDetail2.setCurrentLimitTimme(2);
            // 计算首期还款日
            Date date2 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar2 = Calendar.getInstance();
            Date repaymentDate2 = null;
            if (date2 != null) {
                calendar2.setTime(date2);
                calendar2.add(Calendar.DAY_OF_MONTH, 59);
                repaymentDate2 = calendar2.getTime();
                carLoanPaybackDetail2.setRepaymentDate(repaymentDate2);
            }
            // 月还款金额 = 月利息+综合服务费
            BigDecimal yueLix2 = carCheckRate.getMonthlyInterest();
            BigDecimal zhfwf2 = carCheckRate.getComprehensiveServiceFee();
            Double yuehuanjine2 = Arith.add(yueLix2.doubleValue(), zhfwf2.doubleValue());
            carLoanPaybackDetail2.setCurrentInterest(yuehuanjine2);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额*1.015
                BigDecimal htje2 = carContract.getContractAmount();
                Double yicixinghuankuan2 = Arith.mul(htje2.doubleValue(), 1.015);
                carLoanPaybackDetail2.setPrepayment(yicixinghuankuan2);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail2.setPrepayment(calatePrepayment(carContract, 2, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail2.setPrepayment(calatePrepayment(carContract, 2, 0D, zfl));
            }
            //carLoanPaybackDetail2.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail2);

            // 还款明细实体3
            CarLoanPaybackBrief carLoanPaybackDetail3 = new CarLoanPaybackBrief();
            carLoanPaybackDetail3.setCurrentLimitTimme(3);
            // 计算首期还款日
            Date date3 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar3 = Calendar.getInstance();
            Date repaymentDate3 = null;
            if (date3 != null) {
                calendar3.setTime(date3);
                calendar3.add(Calendar.DAY_OF_MONTH, 89);
                repaymentDate3 = calendar3.getTime();
                carLoanPaybackDetail3.setRepaymentDate(repaymentDate3);
            }
            map.put("repaymentDatez", repaymentDate3);
            // 月还款金额 = 合同金额 （第三期）
            carLoanPaybackDetail3.setCurrentInterest(carContract
                    .getContractAmount().doubleValue());
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额 (第三期)
                Double yicixinghuankuan3 = carContract.getContractAmount().doubleValue();
                carLoanPaybackDetail3.setPrepayment(yicixinghuankuan3);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail3.setPrepayment(calatePrepayment(carContract, 3, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail3.setPrepayment(calatePrepayment(carContract, 3, 0D, zfl));
            }
            //carLoanPaybackDetail3.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail3);

        } else if ("180".equals(loanMon)) {
            // 还款明细实体1
            CarLoanPaybackBrief carLoanPaybackDetail = new CarLoanPaybackBrief();
            carLoanPaybackDetail.setCurrentLimitTimme(1);
            // 计算首期还款日
            Date date = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar = Calendar.getInstance();
            Date repaymentDate = null;
            if (date != null) {
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 29);
                repaymentDate = calendar.getTime();
                carLoanPaybackDetail.setRepaymentDate(repaymentDate);
            }
            map.put("repaymentDateq", repaymentDate);
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a = Arith.div(
                    carContract.getContractAmount().doubleValue(), 6);
            Double a1 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine = Arith.add(a, a1);
            carLoanPaybackDetail.setCurrentInterest(yuehuanjine);
            // 停车费
            carLoanPaybackDetail.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail.setXiaoji(yuehuanjine);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s2 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan = Arith
                        .add(Arith.add(carContract.getContractAmount().doubleValue(), s), s2);
                carLoanPaybackDetail.setPrepayment(yicixinghuankuan);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail.setPrepayment(calatePrepayment(carContract, 1, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail.setPrepayment(calatePrepayment(carContract, 1, 0D, zfl));
            }
            //carLoanPaybackDetail.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail);

            // 还款明细实体2
            CarLoanPaybackBrief carLoanPaybackDetail2 = new CarLoanPaybackBrief();
            carLoanPaybackDetail2.setCurrentLimitTimme(2);
            // 计算首期还款日
            Date date2 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar2 = Calendar.getInstance();
            Date repaymentDate2 = null;
            if (date2 != null) {
                calendar2.setTime(date2);
                calendar2.add(Calendar.DAY_OF_MONTH, 59);
                repaymentDate2 = calendar2.getTime();
                carLoanPaybackDetail2.setRepaymentDate(repaymentDate2);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a2 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 6);
            Double a3 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine2 = Arith.add(a2, a3);
            carLoanPaybackDetail2.setCurrentInterest(yuehuanjine2);
            // 停车费
            carLoanPaybackDetail2.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail2.setXiaoji(yuehuanjine);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s3 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 6),
                        5d);
                Double s4 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s5 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan2 = Arith.add(Arith.add(s3, s4), s5);
                carLoanPaybackDetail2.setPrepayment(yicixinghuankuan2);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail2.setPrepayment(calatePrepayment(carContract, 2, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail2.setPrepayment(calatePrepayment(carContract, 2, 0D, zfl));
            }
            //carLoanPaybackDetail2.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail2);

            // 还款明细实体3
            CarLoanPaybackBrief carLoanPaybackDetail3 = new CarLoanPaybackBrief();
            carLoanPaybackDetail3.setCurrentLimitTimme(3);
            // 计算首期还款日
            Date date3 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar3 = Calendar.getInstance();
            Date repaymentDate3 = null;
            if (date3 != null) {
                calendar3.setTime(date3);
                calendar3.add(Calendar.DAY_OF_MONTH, 89);
                repaymentDate3 = calendar3.getTime();
                carLoanPaybackDetail3.setRepaymentDate(repaymentDate3);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a4 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 6);
            Double a5 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine3 = Arith.add(a4, a5);
            carLoanPaybackDetail3.setCurrentInterest(yuehuanjine3);
            // 此处只有移交类才有停车费
            Double tcf = new Double(0);
            if (CarLoanProductType.TRANSFER.getCode().equals(carContract.getProductType())) {
                // 停车费
                tcf = Arith.mul(carLoanInfo
                        .getParkingFee().longValue(), 3);
            }
            carLoanPaybackDetail3.setTingchefei(tcf);
            // 小计
            carLoanPaybackDetail3.setXiaoji(Arith.add(yuehuanjine3, tcf));
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s6 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 6), 4);

                Double s7 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s8 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan3 = Arith.add(Arith.add(s6, s7), s8);
                carLoanPaybackDetail3.setPrepayment(yicixinghuankuan3);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail3.setPrepayment(calatePrepayment(carContract, 3, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail3.setPrepayment(calatePrepayment(carContract, 3, 0D, zfl));
            }
            //carLoanPaybackDetail3.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail3);

            // 还款明细实体4
            CarLoanPaybackBrief carLoanPaybackDetail4 = new CarLoanPaybackBrief();
            carLoanPaybackDetail4.setCurrentLimitTimme(4);
            // 计算首期还款日
            Date date4 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar4 = Calendar.getInstance();
            Date repaymentDate4 = null;
            if (date4 != null) {
                calendar4.setTime(date4);
                calendar4.add(Calendar.DAY_OF_MONTH, 119);
                repaymentDate4 = calendar4.getTime();
                carLoanPaybackDetail4.setRepaymentDate(repaymentDate4);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a6 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 6);
            Double a7 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine4 = Arith.add(a6, a7);
            carLoanPaybackDetail4.setCurrentInterest(yuehuanjine4);
            // 停车费
            carLoanPaybackDetail4.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail4.setXiaoji(yuehuanjine4);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.03
                Double s9 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 6), 3);

                Double s10 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s11 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.03);
                Double yicixinghuankuan4 = Arith.add(Arith.add(s9, s10), s11);
                carLoanPaybackDetail4.setPrepayment(yicixinghuankuan4);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail4.setPrepayment(calatePrepayment(carContract, 4, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail4.setPrepayment(calatePrepayment(carContract, 4, 0D, zfl));
            }
            //carLoanPaybackDetail4.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail4);

            // 还款明细实体5
            CarLoanPaybackBrief carLoanPaybackDetail5 = new CarLoanPaybackBrief();
            carLoanPaybackDetail5.setCurrentLimitTimme(5);
            // 计算首期还款日
            Date date5 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar5 = Calendar.getInstance();
            Date repaymentDate5 = null;
            if (date5 != null) {
                calendar5.setTime(date5);
                calendar5.add(Calendar.DAY_OF_MONTH, 149);
                repaymentDate5 = calendar5.getTime();
                carLoanPaybackDetail5.setRepaymentDate(repaymentDate5);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a8 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 6);
            Double a9 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine5 = Arith.add(a8, a9);
            carLoanPaybackDetail5.setCurrentInterest(yuehuanjine5);
            // 停车费
            carLoanPaybackDetail5.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail5.setXiaoji(yuehuanjine5);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.02
                Double s12 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 6),
                        2);
                Double s13 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s14 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.02);
                Double yicixinghuankuan5 = Arith.add(Arith.add(s12, s13), s14);
                carLoanPaybackDetail5.setPrepayment(yicixinghuankuan5);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail5.setPrepayment(calatePrepayment(carContract, 5, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail5.setPrepayment(calatePrepayment(carContract, 5, 0D, zfl));
            }
            //carLoanPaybackDetail5.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail5);

            // 还款明细实体6
            CarLoanPaybackBrief carLoanPaybackDetail6 = new CarLoanPaybackBrief();
            carLoanPaybackDetail6.setCurrentLimitTimme(6);
            // 计算首期还款日
            Date date6 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar6 = Calendar.getInstance();
            Date repaymentDate6 = null;
            if (date6 != null) {
                calendar6.setTime(date6);
                calendar6.add(Calendar.DAY_OF_MONTH, 179);
                repaymentDate6 = calendar6.getTime();
                carLoanPaybackDetail6.setRepaymentDate(repaymentDate6);
            }
            map.put("repaymentDatez", repaymentDate6);
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a10 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 6);
            Double a11 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine6 = Arith.add(a10, a11);
            carLoanPaybackDetail6.setCurrentInterest(yuehuanjine6);
            // 停车费
            carLoanPaybackDetail6.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail6.setXiaoji(yuehuanjine6);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 月还金额 最后一期算法独特
                carLoanPaybackDetail6.setPrepayment(yuehuanjine6);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail6.setPrepayment(calatePrepayment(carContract, 6, yuehuanjine6, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail6.setPrepayment(calatePrepayment(carContract, 6, yuehuanjine6, zfl));
            }
            //carLoanPaybackDetail6.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail6);

        } else if ("270".equals(loanMon)) {
            // 还款明细实体1
            CarLoanPaybackBrief carLoanPaybackDetail = new CarLoanPaybackBrief();
            carLoanPaybackDetail.setCurrentLimitTimme(1);
            // 计算首期还款日
            Date date = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar = Calendar.getInstance();
            Date repaymentDate = null;
            if (date != null) {
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 29);
                repaymentDate = calendar.getTime();
                carLoanPaybackDetail.setRepaymentDate(repaymentDate);
            }
            map.put("repaymentDateq", repaymentDate);
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a = Arith.div(
                    carContract.getContractAmount().doubleValue(), 9);
            Double a1 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine = Arith.add(a, a1);
            carLoanPaybackDetail.setCurrentInterest(yuehuanjine);
            // 停车费
            carLoanPaybackDetail.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail.setXiaoji(yuehuanjine);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s2 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan = Arith
                        .add(Arith.add(carContract.getContractAmount().doubleValue(), s), s2);
                carLoanPaybackDetail.setPrepayment(yicixinghuankuan);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail.setPrepayment(calatePrepayment(carContract, 1, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail.setPrepayment(calatePrepayment(carContract, 1, 0D, zfl));
            }
            //carLoanPaybackDetail.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail);

            // 还款明细实体2
            CarLoanPaybackBrief carLoanPaybackDetail2 = new CarLoanPaybackBrief();
            carLoanPaybackDetail2.setCurrentLimitTimme(2);
            // 计算首期还款日
            Date date2 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar2 = Calendar.getInstance();
            Date repaymentDate2 = null;
            if (date2 != null) {
                calendar2.setTime(date2);
                calendar2.add(Calendar.DAY_OF_MONTH, 59);
                repaymentDate2 = calendar2.getTime();
                carLoanPaybackDetail2.setRepaymentDate(repaymentDate2);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a2 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 9);
            Double a3 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine2 = Arith.add(a2, a3);
            carLoanPaybackDetail2.setCurrentInterest(yuehuanjine2);
            // 停车费
            carLoanPaybackDetail2.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail2.setXiaoji(yuehuanjine);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s3 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 9),
                        8);
                Double s4 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s5 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan2 = Arith.add(Arith.add(s3, s4), s5);
                carLoanPaybackDetail2.setPrepayment(yicixinghuankuan2);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail2.setPrepayment(calatePrepayment(carContract, 2, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail2.setPrepayment(calatePrepayment(carContract, 2, 0D, zfl));
            }
            //carLoanPaybackDetail2.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail2);

            // 还款明细实体3
            CarLoanPaybackBrief carLoanPaybackDetail3 = new CarLoanPaybackBrief();
            carLoanPaybackDetail3.setCurrentLimitTimme(3);
            // 计算首期还款日
            Date date3 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar3 = Calendar.getInstance();
            Date repaymentDate3 = null;
            if (date3 != null) {
                calendar3.setTime(date3);
                calendar3.add(Calendar.DAY_OF_MONTH, 89);
                repaymentDate3 = calendar3.getTime();
                carLoanPaybackDetail3.setRepaymentDate(repaymentDate3);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a4 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 9);
            Double a5 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine3 = Arith.add(a4, a5);
            carLoanPaybackDetail3.setCurrentInterest(yuehuanjine3);
            // 此处只有移交类才有停车费
            Double tcf = new Double(0);
            if (CarLoanProductType.TRANSFER.getCode().equals(carContract.getProductType())) {

                // 停车费
                if (carLoanInfo.getParkingFee() != null) {
                    tcf = Arith.mul(new Double(carLoanInfo
                            .getParkingFee().longValue()), 3);
                }

            }
            carLoanPaybackDetail3.setTingchefei(tcf);
            // 小计
            carLoanPaybackDetail3.setXiaoji(Arith.add(yuehuanjine3, tcf));
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s6 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 9),
                        7);
                Double s7 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s8 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan3 = Arith.add(Arith.add(s6, s7), s8);
                carLoanPaybackDetail3.setPrepayment(yicixinghuankuan3);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail3.setPrepayment(calatePrepayment(carContract, 3, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail3.setPrepayment(calatePrepayment(carContract, 3, 0D, zfl));
            }
            //carLoanPaybackDetail3.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail3);

            // 还款明细实体4
            CarLoanPaybackBrief carLoanPaybackDetail4 = new CarLoanPaybackBrief();
            carLoanPaybackDetail4.setCurrentLimitTimme(4);
            // 计算首期还款日
            Date date4 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar4 = Calendar.getInstance();
            Date repaymentDate4 = null;
            if (date4 != null) {
                calendar4.setTime(date4);
                calendar4.add(Calendar.DAY_OF_MONTH, 119);
                repaymentDate4 = calendar4.getTime();
                carLoanPaybackDetail4.setRepaymentDate(repaymentDate4);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a6 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 9);
            Double a7 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine4 = Arith.add(a6, a7);
            carLoanPaybackDetail4.setCurrentInterest(yuehuanjine4);
            // 停车费
            carLoanPaybackDetail4.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail4.setXiaoji(yuehuanjine4);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s9 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 9),
                        6);
                Double s10 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s11 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan4 = Arith.add(Arith.add(s9, s10), s11);
                carLoanPaybackDetail4.setPrepayment(yicixinghuankuan4);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail4.setPrepayment(calatePrepayment(carContract, 4, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail4.setPrepayment(calatePrepayment(carContract, 4, 0D, zfl));
            }
            //carLoanPaybackDetail4.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail4);

            // 还款明细实体5
            CarLoanPaybackBrief carLoanPaybackDetail5 = new CarLoanPaybackBrief();
            carLoanPaybackDetail5.setCurrentLimitTimme(5);
            // 计算首期还款日
            Date date5 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar5 = Calendar.getInstance();
            Date repaymentDate5 = null;
            if (date5 != null) {
                calendar5.setTime(date5);
                calendar5.add(Calendar.DAY_OF_MONTH, 149);
                repaymentDate5 = calendar5.getTime();
                carLoanPaybackDetail5.setRepaymentDate(repaymentDate5);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a8 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 9);
            Double a9 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine5 = Arith.add(a8, a9);
            carLoanPaybackDetail5.setCurrentInterest(yuehuanjine5);
            // 停车费
            carLoanPaybackDetail5.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail5.setXiaoji(yuehuanjine5);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.03
                Double s12 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 9),
                        5);
                Double s13 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s14 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.03);
                Double yicixinghuankuan5 = Arith.add(Arith.add(s12, s13), s14);
                carLoanPaybackDetail5.setPrepayment(yicixinghuankuan5);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail5.setPrepayment(calatePrepayment(carContract, 5, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail5.setPrepayment(calatePrepayment(carContract, 5, 0D, zfl));
            }
            //carLoanPaybackDetail5.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail5);

            // 还款明细实体6
            CarLoanPaybackBrief carLoanPaybackDetail6 = new CarLoanPaybackBrief();
            carLoanPaybackDetail6.setCurrentLimitTimme(6);
            // 计算首期还款日
            Date date6 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar6 = Calendar.getInstance();
            Date repaymentDate6 = null;
            if (date6 != null) {
                calendar6.setTime(date6);
                calendar6.add(Calendar.DAY_OF_MONTH, 179);
                repaymentDate6 = calendar6.getTime();
                carLoanPaybackDetail6.setRepaymentDate(repaymentDate6);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a10 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 9);
            Double a11 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine6 = Arith.add(a10, a11);
            carLoanPaybackDetail6.setCurrentInterest(yuehuanjine6);
            // 此处只有移交类才有停车费
            Double tcf1 = new Double(0);
            if (CarLoanProductType.TRANSFER.getCode().equals(carContract.getProductType())) {
                // 停车费
                if (carLoanInfo.getParkingFee() != null) {
                    tcf1 = Arith.mul(new Double(carLoanInfo
                            .getParkingFee().longValue()), 3);
                }

            }
            carLoanPaybackDetail6.setTingchefei(tcf1);
            // 小计
            carLoanPaybackDetail6.setXiaoji(Arith.add(yuehuanjine6, tcf1));
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.03
                Double s15 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 9),
                        4);
                Double s16 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s17 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.03);
                Double yicixinghuankuan6 = Arith.add(Arith.add(s15, s16), s17);
                carLoanPaybackDetail6.setPrepayment(yicixinghuankuan6);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail6.setPrepayment(calatePrepayment(carContract, 6, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail6.setPrepayment(calatePrepayment(carContract, 6, 0D, zfl));
            }
//			carLoanPaybackDetail6.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail6);

            // 还款明细实体7
            CarLoanPaybackBrief carLoanPaybackDetail7 = new CarLoanPaybackBrief();
            carLoanPaybackDetail7.setCurrentLimitTimme(7);
            // 计算首期还款日
            Date date7 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar7 = Calendar.getInstance();
            Date repaymentDate7 = null;
            if (date7 != null) {
                calendar7.setTime(date7);
                calendar7.add(Calendar.DAY_OF_MONTH, 209);
                repaymentDate7 = calendar7.getTime();
                carLoanPaybackDetail7.setRepaymentDate(repaymentDate7);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a13 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 9);
            Double a14 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine7 = Arith.add(a13, a14);
            carLoanPaybackDetail7.setCurrentInterest(yuehuanjine7);
            // 停车费
            carLoanPaybackDetail7.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail7.setXiaoji(yuehuanjine7);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.02
                Double l = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 9),
                        3);
                Double l1 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double l2 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.02);
                Double yicixinghuankuan7 = Arith.add(Arith.add(l, l1), l2);
                carLoanPaybackDetail7.setPrepayment(yicixinghuankuan7);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail7.setPrepayment(calatePrepayment(carContract, 7, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail7.setPrepayment(calatePrepayment(carContract, 7, 0D, zfl));
            }
//			carLoanPaybackDetail7.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail7);

            // 还款明细实体8
            CarLoanPaybackBrief carLoanPaybackDetail8 = new CarLoanPaybackBrief();
            carLoanPaybackDetail8.setCurrentLimitTimme(8);
            // 计算首期还款日
            Date date8 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar8 = Calendar.getInstance();
            Date repaymentDate8 = null;
            if (date8 != null) {
                calendar8.setTime(date8);
                calendar8.add(Calendar.DAY_OF_MONTH, 239);
                repaymentDate8 = calendar8.getTime();
                carLoanPaybackDetail8.setRepaymentDate(repaymentDate8);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a15 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 9);
            Double a16 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine8 = Arith.add(a15, a16);
            carLoanPaybackDetail8.setCurrentInterest(yuehuanjine8);
            // 停车费
            carLoanPaybackDetail8.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail8.setXiaoji(yuehuanjine8);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.02
                Double l3 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 9),
                        2);
                Double l4 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double l5 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.02);
                Double yicixinghuankuan8 = Arith.add(Arith.add(l3, l4), l5);
                carLoanPaybackDetail8.setPrepayment(yicixinghuankuan8);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail8.setPrepayment(calatePrepayment(carContract, 8, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail8.setPrepayment(calatePrepayment(carContract, 8, 0D, zfl));
            }
            //carLoanPaybackDetail8.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail8);

            // 还款明细实体9
            CarLoanPaybackBrief carLoanPaybackDetail9 = new CarLoanPaybackBrief();
            carLoanPaybackDetail9.setCurrentLimitTimme(9);
            // 计算首期还款日
            Date date9 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar9 = Calendar.getInstance();
            Date repaymentDate9 = null;
            if (date9 != null) {
                calendar9.setTime(date9);
                calendar9.add(Calendar.DAY_OF_MONTH, 269);
                repaymentDate9 = calendar9.getTime();
                carLoanPaybackDetail9.setRepaymentDate(repaymentDate9);
            }
            map.put("repaymentDatez", repaymentDate9);
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double b = Arith.div(
                    carContract.getContractAmount().doubleValue(), 9);
            Double b1 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine9 = Arith.add(b, b1);
            carLoanPaybackDetail9.setCurrentInterest(yuehuanjine9);
            // 停车费
            carLoanPaybackDetail9.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail9.setXiaoji(yuehuanjine9);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 月还金额 最后一期算法独特
                carLoanPaybackDetail9.setPrepayment(yuehuanjine9);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail9.setPrepayment(calatePrepayment(carContract, 9, yuehuanjine9, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail9.setPrepayment(calatePrepayment(carContract, 9, yuehuanjine9, zfl));
            }
            //carLoanPaybackDetail9.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail9);
        } else if ("360".equals(loanMon)) {
            // 还款明细实体1
            CarLoanPaybackBrief carLoanPaybackDetail = new CarLoanPaybackBrief();
            carLoanPaybackDetail.setCurrentLimitTimme(1);
            // 计算首期还款日
            Date date = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar = Calendar.getInstance();
            Date repaymentDate = null;
            if (date != null) {
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 29);
                repaymentDate = calendar.getTime();
                carLoanPaybackDetail.setRepaymentDate(repaymentDate);
            }
            map.put("repaymentDateq", repaymentDate);
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double a1 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine = Arith.add(a, a1);
            carLoanPaybackDetail.setCurrentInterest(yuehuanjine);
            // 停车费
            carLoanPaybackDetail.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail.setXiaoji(yuehuanjine);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s2 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan =
                        Arith.add(Arith.add(carContract.getContractAmount().doubleValue(), s), s2);
                carLoanPaybackDetail.setPrepayment(yicixinghuankuan);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail.setPrepayment(calatePrepayment(carContract, 1, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail.setPrepayment(calatePrepayment(carContract, 1, 0D, zfl));
            }
            //carLoanPaybackDetail.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail);

            // 还款明细实体2
            CarLoanPaybackBrief carLoanPaybackDetail2 = new CarLoanPaybackBrief();
            carLoanPaybackDetail2.setCurrentLimitTimme(2);
            // 计算首期还款日
            Date date2 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar2 = Calendar.getInstance();
            Date repaymentDate2 = null;
            if (date2 != null) {
                calendar2.setTime(date2);
                calendar2.add(Calendar.DAY_OF_MONTH, 59);
                repaymentDate2 = calendar2.getTime();
                carLoanPaybackDetail2.setRepaymentDate(repaymentDate2);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a2 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double a3 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine2 = Arith.add(a2, a3);
            carLoanPaybackDetail2.setCurrentInterest(yuehuanjine2);
            // 停车费
            carLoanPaybackDetail2.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail2.setXiaoji(yuehuanjine);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s3 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        11);
                Double s4 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s5 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan2 = Arith.add(Arith.add(s3, s4), s5);
                carLoanPaybackDetail2.setPrepayment(yicixinghuankuan2);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail2.setPrepayment(calatePrepayment(carContract, 2, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail2.setPrepayment(calatePrepayment(carContract, 2, 0D, zfl));
            }
            //carLoanPaybackDetail2.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail2);

            // 还款明细实体3
            CarLoanPaybackBrief carLoanPaybackDetail3 = new CarLoanPaybackBrief();
            carLoanPaybackDetail3.setCurrentLimitTimme(3);
            // 计算首期还款日
            Date date3 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar3 = Calendar.getInstance();
            Date repaymentDate3 = null;
            if (date3 != null) {
                calendar3.setTime(date3);
                calendar3.add(Calendar.DAY_OF_MONTH, 89);
                repaymentDate3 = calendar3.getTime();
                carLoanPaybackDetail3.setRepaymentDate(repaymentDate3);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a4 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double a5 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine3 = Arith.add(a4, a5);
            carLoanPaybackDetail3.setCurrentInterest(yuehuanjine3);
            // 此处只有移交类才有停车费
            Double tcf = new Double(0);
            if (CarLoanProductType.TRANSFER.getCode().equals(carContract.getProductType())) {
                // 停车费
                tcf = Arith.mul(new Double(carLoanInfo
                        .getParkingFee().longValue()), 3);
            }
            carLoanPaybackDetail3.setTingchefei(tcf);
            // 小计
            carLoanPaybackDetail3.setXiaoji(Arith.add(yuehuanjine3, tcf));
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s6 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        10);
                Double s7 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s8 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan3 = Arith.add(Arith.add(s6, s7), s8);
                carLoanPaybackDetail3.setPrepayment(yicixinghuankuan3);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail3.setPrepayment(calatePrepayment(carContract, 3, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail3.setPrepayment(calatePrepayment(carContract, 3, 0D, zfl));
            }
            //carLoanPaybackDetail3.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail3);

            // 还款明细实体4
            CarLoanPaybackBrief carLoanPaybackDetail4 = new CarLoanPaybackBrief();
            carLoanPaybackDetail4.setCurrentLimitTimme(4);
            // 计算首期还款日
            Date date4 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar4 = Calendar.getInstance();
            Date repaymentDate4 = null;
            if (date4 != null) {
                calendar4.setTime(date4);
                calendar4.add(Calendar.DAY_OF_MONTH, 119);
                repaymentDate4 = calendar4.getTime();
                carLoanPaybackDetail4.setRepaymentDate(repaymentDate4);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a6 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double a7 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine4 = Arith.add(a6, a7);
            carLoanPaybackDetail4.setCurrentInterest(yuehuanjine4);
            // 停车费
            carLoanPaybackDetail4.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail4.setXiaoji(yuehuanjine4);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s9 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        9);
                Double s10 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s11 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan4 = Arith.add(Arith.add(s9, s10), s11);
                carLoanPaybackDetail4.setPrepayment(yicixinghuankuan4);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail4.setPrepayment(calatePrepayment(carContract, 4, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail4.setPrepayment(calatePrepayment(carContract, 4, 0D, zfl));
            }
//			carLoanPaybackDetail4.setLoanPaybackId(hjLoanPayback.getLoanPaybackId());

            list.add(carLoanPaybackDetail4);

            // 还款明细实体5
            CarLoanPaybackBrief carLoanPaybackDetail5 = new CarLoanPaybackBrief();
            carLoanPaybackDetail5.setCurrentLimitTimme(5);
            // 计算首期还款日
            Date date5 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar5 = Calendar.getInstance();
            Date repaymentDate5 = null;
            if (date5 != null) {
                calendar5.setTime(date5);
                calendar5.add(Calendar.DAY_OF_MONTH, 149);
                repaymentDate5 = calendar5.getTime();
                carLoanPaybackDetail5.setRepaymentDate(repaymentDate5);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a8 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double a9 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine5 = Arith.add(a8, a9);
            carLoanPaybackDetail5.setCurrentInterest(yuehuanjine5);
            // 停车费
            carLoanPaybackDetail5.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail5.setXiaoji(yuehuanjine5);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s12 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        8);
                Double s13 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s14 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan5 = Arith.add(Arith.add(s12, s13), s14);
                carLoanPaybackDetail5.setPrepayment(yicixinghuankuan5);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail5.setPrepayment(calatePrepayment(carContract, 5, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail5.setPrepayment(calatePrepayment(carContract, 5, 0D, zfl));
            }
//			carLoanPaybackDetail5.setLoanPaybackId(hjLoanPayback
//					.getLoanPaybackId());
            list.add(carLoanPaybackDetail5);

            // 还款明细实体6
            CarLoanPaybackBrief carLoanPaybackDetail6 = new CarLoanPaybackBrief();
            carLoanPaybackDetail6.setCurrentLimitTimme(6);
            // 计算首期还款日
            Date date6 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar6 = Calendar.getInstance();
            Date repaymentDate6 = null;
            if (date6 != null) {
                calendar6.setTime(date6);
                calendar6.add(Calendar.DAY_OF_MONTH, 179);
                repaymentDate6 = calendar6.getTime();
                carLoanPaybackDetail6.setRepaymentDate(repaymentDate6);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a10 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double a11 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine6 = Arith.add(a10, a11);
            carLoanPaybackDetail6.setCurrentInterest(yuehuanjine6);
            // 停车费
            // 此处只有移交类才有停车费
            Double tcf1 = new Double(0);
            if (CarLoanProductType.TRANSFER.getCode().equals(carContract.getProductType())) {
                // 停车费
                tcf1 = Arith.mul(new Double(carLoanInfo
                        .getParkingFee().longValue()), 3);
            }
            carLoanPaybackDetail6.setTingchefei(tcf1);
            // 小计
            carLoanPaybackDetail6.setXiaoji(Arith.add(yuehuanjine6, tcf1));
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.05
                Double s15 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        7);
                Double s16 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double s17 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.05);
                Double yicixinghuankuan6 = Arith.add(Arith.add(s15, s16), s17);
                carLoanPaybackDetail6.setPrepayment(yicixinghuankuan6);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail6.setPrepayment(calatePrepayment(carContract, 6, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail6.setPrepayment(calatePrepayment(carContract, 6, 0D, zfl));
            }
//			carLoanPaybackDetail6.setLoanPaybackId(hjLoanPayback
//					.getLoanPaybackId());
            list.add(carLoanPaybackDetail6);

            // 还款明细实体7
            CarLoanPaybackBrief carLoanPaybackDetail7 = new CarLoanPaybackBrief();
            carLoanPaybackDetail7.setCurrentLimitTimme(7);
            // 计算首期还款日
            Date date7 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar7 = Calendar.getInstance();
            Date repaymentDate7 = null;
            if (date7 != null) {
                calendar7.setTime(date7);
                calendar7.add(Calendar.DAY_OF_MONTH, 209);
                repaymentDate7 = calendar7.getTime();
                carLoanPaybackDetail7.setRepaymentDate(repaymentDate7);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a13 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double a14 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine7 = Arith.add(a13, a14);
            carLoanPaybackDetail7.setCurrentInterest(yuehuanjine7);
            // 停车费
            carLoanPaybackDetail7.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail7.setXiaoji(yuehuanjine7);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.03
                Double l = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        6);
                Double l1 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double l2 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.03);
                Double yicixinghuankuan7 = Arith.add(Arith.add(l, l1), l2);
                carLoanPaybackDetail7.setPrepayment(yicixinghuankuan7);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail7.setPrepayment(calatePrepayment(carContract, 7, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail7.setPrepayment(calatePrepayment(carContract, 7, 0D, zfl));
            }
//			carLoanPaybackDetail7.setLoanPaybackId(hjLoanPayback
//					.getLoanPaybackId());
            list.add(carLoanPaybackDetail7);

            // 还款明细实体8
            CarLoanPaybackBrief carLoanPaybackDetail8 = new CarLoanPaybackBrief();
            carLoanPaybackDetail8.setCurrentLimitTimme(8);
            // 计算首期还款日
            Date date8 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar8 = Calendar.getInstance();
            Date repaymentDate8 = null;
            if (date8 != null) {
                calendar8.setTime(date8);
                calendar8.add(Calendar.DAY_OF_MONTH, 239);
                repaymentDate8 = calendar8.getTime();
                carLoanPaybackDetail8.setRepaymentDate(repaymentDate8);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double a15 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double a16 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine8 = Arith.add(a15, a16);
            carLoanPaybackDetail8.setCurrentInterest(yuehuanjine8);
            // 停车费
            carLoanPaybackDetail8.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail8.setXiaoji(yuehuanjine8);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.03
                Double l3 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        5);
                Double l4 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double l5 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.03);
                Double yicixinghuankuan8 = Arith.add(Arith.add(l3, l4), l5);
                carLoanPaybackDetail8.setPrepayment(yicixinghuankuan8);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail8.setPrepayment(calatePrepayment(carContract, 8, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail8.setPrepayment(calatePrepayment(carContract, 8, 0D, zfl));
            }
//			carLoanPaybackDetail8.setLoanPaybackId(hjLoanPayback
//					.getLoanPaybackId());
            list.add(carLoanPaybackDetail8);

            // 还款明细实体9
            CarLoanPaybackBrief carLoanPaybackDetail9 = new CarLoanPaybackBrief();
            carLoanPaybackDetail9.setCurrentLimitTimme(9);
            // 计算首期还款日
            Date date9 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar9 = Calendar.getInstance();
            Date repaymentDate9 = null;
            if (date9 != null) {
                calendar9.setTime(date9);
                calendar9.add(Calendar.DAY_OF_MONTH, 269);
                repaymentDate9 = calendar9.getTime();
                carLoanPaybackDetail9.setRepaymentDate(repaymentDate9);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double b = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double b1 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine9 = Arith.add(b, b1);
            carLoanPaybackDetail9.setCurrentInterest(yuehuanjine9);
            // 此处只有移交类才有停车费
            Double tcf2 = new Double(0);
            if (CarLoanProductType.TRANSFER.getCode().equals(carContract.getProductType())) {
                // 停车费
                tcf2 = Arith.mul(new Double(carLoanInfo
                        .getParkingFee().longValue()), 3);
            }
            carLoanPaybackDetail9.setTingchefei(tcf2);
            // 小计
            carLoanPaybackDetail9.setXiaoji(Arith.add(yuehuanjine9, tcf2));
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.02
                Double l6 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        4);
                Double l7 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double l8 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.02);
                Double yicixinghuankuan9 = Arith.add(Arith.add(l6, l7), l8);
                carLoanPaybackDetail9.setPrepayment(yicixinghuankuan9);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail9.setPrepayment(calatePrepayment(carContract, 9, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail9.setPrepayment(calatePrepayment(carContract, 9, 0D, zfl));
            }
//			carLoanPaybackDetail9.setLoanPaybackId(hjLoanPayback
//					.getLoanPaybackId());
            list.add(carLoanPaybackDetail9);

            // 还款明细实体10
            CarLoanPaybackBrief carLoanPaybackDetail10 = new CarLoanPaybackBrief();
            carLoanPaybackDetail10.setCurrentLimitTimme(10);
            // 计算首期还款日
            Date date10 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar10 = Calendar.getInstance();
            Date repaymentDate10 = null;
            if (date10 != null) {
                calendar10.setTime(date10);
                calendar10.add(Calendar.DAY_OF_MONTH, 299);
                repaymentDate10 = calendar10.getTime();
                carLoanPaybackDetail10.setRepaymentDate(repaymentDate10);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double b2 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double b3 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine10 = Arith.add(b2, b3);
            carLoanPaybackDetail10.setCurrentInterest(yuehuanjine10);
            // 停车费
            carLoanPaybackDetail10.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail10.setXiaoji(yuehuanjine10);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.02
                Double l9 = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        3);
                Double l10 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double l11 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.02);
                Double yicixinghuankuan10 = Arith.add(Arith.add(l9, l10), l11);
                carLoanPaybackDetail10.setPrepayment(yicixinghuankuan10);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail10.setPrepayment(calatePrepayment(carContract, 10, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail10.setPrepayment(calatePrepayment(carContract, 10, 0D, zfl));
            }
//			carLoanPaybackDetail10.setLoanPaybackId(hjLoanPayback
//					.getLoanPaybackId());
            list.add(carLoanPaybackDetail10);

            // 还款明细实体11
            CarLoanPaybackBrief carLoanPaybackDetail11 = new CarLoanPaybackBrief();
            carLoanPaybackDetail11.setCurrentLimitTimme(11);
            // 计算首期还款日
            Date date11 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar11 = Calendar.getInstance();
            Date repaymentDate11 = null;
            if (date11 != null) {
                calendar11.setTime(date11);
                calendar11.add(Calendar.DAY_OF_MONTH, 329);
                repaymentDate11 = calendar11.getTime();
                carLoanPaybackDetail11.setRepaymentDate(repaymentDate11);
            }
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double b4 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double b5 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine11 = Arith.add(b4, b5);
            carLoanPaybackDetail11.setCurrentInterest(yuehuanjine11);
            // 停车费
            carLoanPaybackDetail11.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail11.setXiaoji(yuehuanjine11);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 合同金额/借款期数*（借款期数-当前期数+1）+合同金额*总费率+合同金额*0.02
                Double x = Arith.mul(Arith.div(
                        carContract.getContractAmount().doubleValue(), 12),
                        2);
                Double x1 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), zfl);
                Double x2 = Arith.mul(
                        carContract.getContractAmount().doubleValue(), 0.02);
                Double yicixinghuankuan11 = Arith.add(Arith.add(x, x1), x2);
                carLoanPaybackDetail11.setPrepayment(yicixinghuankuan11);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail11.setPrepayment(calatePrepayment(carContract, 11, 0D, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail11.setPrepayment(calatePrepayment(carContract, 11, 0D, zfl));
            }
//			carLoanPaybackDetail11.setLoanPaybackId(hjLoanPayback
//					.getLoanPaybackId());
            list.add(carLoanPaybackDetail11);

            // 还款明细实体12
            CarLoanPaybackBrief carLoanPaybackDetail12 = new CarLoanPaybackBrief();
            carLoanPaybackDetail12.setCurrentLimitTimme(12);
            // 计算首期还款日
            Date date12 = carContract.getContractDueDay();// 合同签订日期
            Calendar calendar12 = Calendar.getInstance();
            Date repaymentDate12 = null;
            if (date12 != null) {
                calendar12.setTime(date12);
                calendar12.add(Calendar.DAY_OF_MONTH, 359);
                repaymentDate12 = calendar12.getTime();
                carLoanPaybackDetail12.setRepaymentDate(repaymentDate12);
            }
            map.put("repaymentDatez", repaymentDate12);
            // 月还款金额 = 合同金额/借款期数+合同金额*总费率
            Double b6 = Arith.div(
                    carContract.getContractAmount().doubleValue(), 12);
            Double b7 = Arith.mul(
                    carContract.getContractAmount().doubleValue(), zfl);
            Double yuehuanjine12 = Arith.add(b6, b7);
            carLoanPaybackDetail12.setCurrentInterest(yuehuanjine12);
            // 停车费
            carLoanPaybackDetail12.setTingchefei(new Double(0));
            // 小计
            carLoanPaybackDetail12.setXiaoji(yuehuanjine12);
            if ("1.4".equals(carContract.getContractVersion()) || "1.3".equals(carContract.getContractVersion())) {
                // 一次性还款金额 = 月还金额（最后一期算法独特）
                carLoanPaybackDetail12.setPrepayment(yuehuanjine12);
            } else if ("1.5".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail12.setPrepayment(calatePrepayment(carContract, 12, yuehuanjine12, zfl));
            } else if ("1.6".equals(carContract.getContractVersion())) {
                carLoanPaybackDetail12.setPrepayment(calatePrepayment(carContract, 12, yuehuanjine12, zfl));
            }
//			carLoanPaybackDetail12.setLoanPaybackId(hjLoanPayback
//					.getLoanPaybackId());
            list.add(carLoanPaybackDetail12);

        }
        map.put("list", list);
        return map;
    }

    /**
     * 总费率转换
     *
     * @param TotalRate
     * @return
     */
    public synchronized static double getZfl(Double TotalRate) {
        double zfl = Arith.div(TotalRate.doubleValue(), 100);
        return zfl;
    }

    /**
     * 计算提前结清金额
     *
     * @param carContract       合同
     * @param currentLimitTimme 当前期数
     * @param qigongjine        期供金额
     * @param zfl               总费率
     * @return 当期提前结清金额
     */
    public synchronized static Double calatePrepayment(CarContract carContract, int currentLimitTimme, Double qigongjine, double zfl) {
        String contractVersion = carContract.getContractVersion();
        if (carContract != null && contractVersion != null && !"".equals(contractVersion)) {
            BigDecimal yicixinghuankuan = BigDecimal.ZERO;
            double count = carContract.getContractMonths() / 30;//总期数
            BigDecimal amount = carContract.getContractAmount();//合同金额

            switch (contractVersion) {
                case "1.3": {//1.3版本合同
                    break;
                }
                case "1.4": {//1.4版本合同
                    break;
                }
                case "1.6": {//1.6版本合同

                }
                case "1.5": {//1.5版本合同
                    /**
                     * 3期：1~2期：合同金额*1.05，3期：合同金额
                     * 6/9/12期：1~5/8/11期：（合同金额/总期数）*（总期数-当前期数+1）*1.05 + 合同金额 * 总费率   6/9/12期：最后一期期供
                     */
                    if (count == 3) {
                        if (currentLimitTimme == count) {//最后一期
                            yicixinghuankuan = amount;
                        } else {
                            yicixinghuankuan = amount.multiply(new BigDecimal(1.05));
                        }
                    } else if (count == 6 || count == 9 || count == 12) {
                        if (currentLimitTimme == count) {//最后一期
                            yicixinghuankuan = new BigDecimal(qigongjine);
                        } else {
                            yicixinghuankuan = amount.divide(new BigDecimal(count), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal((count - currentLimitTimme + 1) * 1.05))
                                    .add(amount.multiply(new BigDecimal(zfl)));
                        }
                    }
                    break;
                }
                default: {

                }
            }
            return yicixinghuankuan.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        return 0D;
    }

}
