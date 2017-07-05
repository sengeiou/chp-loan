/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.test.contractBillDay.java
 * @Create By 张灏
 * @Create In 2016年4月7日 上午11:43:22
 */
package com.creditharmony.loan.test.contract;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.creditharmony.common.util.DateUtils;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.common.dao.GlBillDao;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.test.base.AbstractTestCase;

/**
 * @Class Name BillDay
 * @author 张灏
 * @Create In 2016年4月7日
 */
public class BillDay extends AbstractTestCase {

    @Autowired
    private GlBillDao glBillDao;
    @Test
    public void getBillDay() throws ParseException{
        String dateStr = "2016-02-01";
        String[] dateStrArray = {"2016-02-01","2016-01-30","2016-01-31","2016-02-02","2016-01-01","2016-01-05","2016-04-11"};
        Date date1 = DateUtils.parseDate("2016-01-30", "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.MONTH, 1);
        System.out.println("1:  "+DateUtils.formatDateTime(calendar.getTime()));
        calendar.set(Calendar.MONTH, 2);
        System.out.println("2:  "+DateUtils.formatDateTime(calendar.getTime()));
        for(String curDate:dateStrArray){
            System.out.println("#############");
            System.out.println(curDate);
            Date date = DateUtils.parseDate(curDate, "yyyy-MM-dd");
            Calendar cal =  this.getBillDay(date);
            System.out.println(cal.getTime());
            System.out.println(DateUtils.formatDateTime(cal.getTime()));
        }
    }
    private Calendar getBillDay(Date contractDueDay){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(contractDueDay);
        GlBill glBill = new GlBill();
        Integer signDay = calendar.get(Calendar.DAY_OF_MONTH);
        glBill.setSignDay(signDay);
        GlBill tagGlBill = glBillDao.findBySignDay(glBill);
        Integer billDay = tagGlBill.getBillDay();
       
        // 针对1 2号签署的合同作特别处理.当月的最后一天就得还款
        if(signDay.intValue()!=ContractConstant.FIRST_DAY_OF_MONTH && signDay.intValue()!=ContractConstant.SECOND_DAY_OF_MONTH){
          calendar.add(Calendar.MONTH, 1); 
          Integer lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
          if(lastDayOfMonth<billDay){
              calendar.set(Calendar.DAY_OF_MONTH,lastDayOfMonth); 
          }else{
              calendar.set(Calendar.DAY_OF_MONTH,billDay);
          }
        }else if(signDay.intValue()==ContractConstant.FIRST_DAY_OF_MONTH || signDay.intValue()==ContractConstant.SECOND_DAY_OF_MONTH){
            Integer lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            if(lastDayOfMonth<billDay){
                calendar.set(Calendar.DAY_OF_MONTH,lastDayOfMonth); 
            }else{
                calendar.set(Calendar.DAY_OF_MONTH,billDay);
            }
        }
        return calendar;
    }
}
