package com.creditharmony.loan.borrow.payback.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.TargetWay;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.dao.AppointmentDudctDao;
import com.creditharmony.loan.borrow.payback.entity.Appointment;
import com.creditharmony.loan.borrow.payback.entity.PaybackOpe;
import com.creditharmony.loan.common.constants.AppointmentConstant;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;

/**
 * 集中拆分列表service
 * @Class Name PaybackSplitService
 * @author zhaojinping
 * @Create In 2015年12月11日
 */
@Service
public class AppointmentDudctService {

	@Autowired
	private AppointmentDudctDao appointmentDudctDao;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	
	SimpleDateFormat format = new SimpleDateFormat("HH:mm");  
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");  
	SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm" ); 

	/**
	 * 获取集中划扣已拆分列表中的数据列表
	 * 2015年12月11日
	 * By zhaojinping
	 * @param page
	 * @param map
	 * @return 分页对象
	 */
	public Page<Appointment> queryList(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		Page<Appointment> page = new Page<Appointment>(request, response);
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<Appointment> pageList = (PageList<Appointment>) appointmentDudctDao.queryList(pageBounds,map);
		PageUtil.convertPage(pageList, page);
		return page;
	}

	/**
	 * 查询逾期期数
	 * @return
	 */
	public List<String> queryOverCount() {
		return appointmentDudctDao.queryOverCount();
	}

	/*
	 * 新增
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void insert(Appointment appointment) {
		appointment.preInsert();
		appointmentDudctDao.insert(appointment);
		insertPaybackOpe(appointment,"insert");
		
	}

	/**
	 * 修改
	 * @param appointment
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void update(Appointment appointment) {
		appointment.preUpdate();
		appointmentDudctDao.update(appointment);
		insertPaybackOpe(appointment,"update");
		
	}
	
	/**
	 * 查询预约列表
	 * @param map
	 * @return
	 */
	public  List<Appointment>  queryList(Map<String,Object> map){
		return appointmentDudctDao.queryList(map);
	}
	
	/**
	 * 查询预约列表
	 * @param map
	 * @return
	 */
	public  List<Appointment>  queryList(Appointment bean){
		return appointmentDudctDao.queryParamList(bean);
	}


	/**
	 * 生效失效方法
	 * @param bean
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void changEffect(Appointment bean) {
		appointmentDudctDao.updateOrDelete(bean);
		if(bean.getStatus().equals(AppointmentConstant.effect)){
			insertPaybackOpe(bean,"effect");
		}else{
			insertPaybackOpe(bean,"invalid");
		}
	}

	/**
	 * 批量生效失效
	 * @param list
	 * @param sign
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void changEffect(List<Appointment> list, String sign) {
		for(Appointment appt  : list){
			Appointment bean = new Appointment();
			bean.preUpdate();
			bean.setId(appt.getId());
			bean.setStatus(sign);
			appointmentDudctDao.updateOrDelete(bean);
			if(sign.equals(AppointmentConstant.effect)){
				insertPaybackOpe(appt,"effect");
			}else{
				insertPaybackOpe(appt,"invalid");
			}
		}
	}

	/**
	 * 批量删除
	 * @param list
	 */
	public void deleteData(List<Appointment> list) {
		for(Appointment appt : list){
			appointmentDudctDao.delete(appt);
			insertPaybackOpe(appt,"delete");
		}
	}
	
	/**
	 * 插入操作历史 
	 * 2015年1月6日 By 翁私
	 * @param deductReqList
	 * @param deductType
	 * @param b
	 * @param string
	 * @return none
	 */
	public void insertPaybackOpe(Appointment appointment,String flag) {
		PaybackOpe paybackOpe = new PaybackOpe();
		paybackOpe.setrPaybackApplyId(appointment.getId());
		if(flag.equals("insert")){
			paybackOpe.setDictLoanStatus("预约划扣新增");//步奏
			paybackOpe.setRemarks("预约划扣新增 "); // 备注
		}else if(flag.equals("update")){
			paybackOpe.setDictLoanStatus("预约划扣修改");//步奏
			paybackOpe.setRemarks("预约划扣修改 "); // 备注
		}else if(flag.equals("delete")){
			paybackOpe.setDictLoanStatus("预约划扣删除");//步奏
			paybackOpe.setRemarks("预约划扣删除 "); // 备注
		}else if(flag.equals("effect")){
			paybackOpe.setDictLoanStatus("预约划扣生效");//步奏
			paybackOpe.setRemarks("预约划扣生效 "); // 备注
		}else if(flag.equals("invalid")){
			paybackOpe.setDictLoanStatus("预约划扣失效");//步奏
			paybackOpe.setRemarks("预约划扣失效 "); // 备注
		}else if(flag.equals("appointmentRule")){
			paybackOpe.setDictLoanStatus("预约规则设置");//步奏
			paybackOpe.setRemarks("预约规则设置 "); // 备注
		}
		paybackOpe.setDictRDeductType(TargetWay.PAYMENT.getCode()+"1"); //
		paybackOpe.setOperateResult("成功"); // 结果
		paybackOpe.preInsert();
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		loanStatusHisDao.insertPaybackOpe(paybackOpe);
	}

	/**
	 * 根据规则添加数据
	 * @param bean
	 */
	public void appointmentRule(Appointment bean) {
		
		String ymr = format1.format(bean.getAppointmentDay());
		List<Appointment> list =   appointmentDudctDao.queryRuleDataList(bean);
		for(Appointment appment: list){
			String time = format.format(appment.getAppointmentDay());
			try {
				Date adate = sdf.parse(ymr+" "+time);
				appment.setAppointmentDay(adate);
			} catch (ParseException e) {
				System.out.print("转化失败");;
				e.printStackTrace();
			}
			appment.preInsert();
			appointmentDudctDao.insert(appment);
			insertPaybackOpe(appment,"appointmentRule");
		}
	}


	/**
	 * 查询这个时间这个规则是否已经预约了
	 * @param bean
	 * @return
	 */
	public List<Appointment> queryRuleList(Appointment bean) {
		return appointmentDudctDao.queryRuleList(bean);
	}

	/**
	 * 查询规则数据
	 * @param bean
	 * @return
	 */
	public List<Appointment> queryRuleDataList(Appointment bean) {
		return  appointmentDudctDao.queryRuleDataList(bean);
	}

}
