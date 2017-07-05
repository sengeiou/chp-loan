package com.creditharmony.loan.borrow.zhongjin.view;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 中金划扣
 * @Class Name PaybackOrder
 * @author 吴建佳
 * @Create In 2016年3月8日
 */
public class PaybackOrder{

    private String id;//主键
    private String cpcnId;//中金划扣记录id
    private String deductDate;//预约日期
    private String deductTime;//预约时间点
    private Date taskTime;//任务执行时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpcnId() {
        return cpcnId;
    }

    public void setCpcnId(String cpcnId) {
        this.cpcnId = cpcnId;
    }

    public String getDeductDate() {
        return deductDate;
    }

    public void setDeductDate(String deductDate) {
        this.deductDate = deductDate;
    }

    public String getDeductTime() {
        return deductTime;
    }

    public void setDeductTime(String deductTime) {
        this.deductTime = deductTime;
    }

    public Date getTaskTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            taskTime = dateFormat.parse(getDeductDate()+" "+getDeductTime());
        } catch (ParseException e) {
            System.err.println("时间转换错误！====================================");
            System.err.println(e.getMessage());
        }
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }
}
