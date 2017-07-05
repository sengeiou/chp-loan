package com.creditharmony.loan.borrow.payback.service;

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
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.payback.dao.AppointmentDudctTemplateDao;
import com.creditharmony.loan.borrow.payback.entity.AppointmentTemplate;
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
public class AppointmentDudctTemplateService {

	@Autowired
	private AppointmentDudctTemplateDao appointmentDudctDao;
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	
	/**
	 * 获取集中划扣已拆分列表中的数据列表
	 * 2015年12月11日
	 * By zhaojinping
	 * @param page
	 * @param map
	 * @return 分页对象
	 */
	public Page<AppointmentTemplate> queryList(Map<String, Object> map, HttpServletRequest request,
			HttpServletResponse response) {
		Page<AppointmentTemplate> page = new Page<AppointmentTemplate>(request, response);
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<AppointmentTemplate> pageList = (PageList<AppointmentTemplate>) appointmentDudctDao.queryList(pageBounds,map);
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
	public void insert(AppointmentTemplate appointment) {
		appointment.preInsert();
		appointmentDudctDao.insert(appointment);
		insertPaybackOpe(appointment,"insert");
		
	}
	
	/*
	 * 导入数据
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void insertImportRuleData(List<AppointmentTemplate> list) {
		
		for(AppointmentTemplate appointment : list){
			appointment.preInsert();
			appointment.setStatus(YESNO.NO.getCode());
			appointmentDudctDao.insert(appointment);
			insertPaybackOpe(appointment,"import");
		}
		
	}

	/**
	 * 修改
	 * @param appointment
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void update(AppointmentTemplate appointment) {
		appointment.preUpdate();
		appointmentDudctDao.update(appointment);
		insertPaybackOpe(appointment,"update");
		
	}
	
	/**
	 * 查询预约列表
	 * @param map
	 * @return
	 */
	public  List<AppointmentTemplate>  queryList(Map<String,Object> map){
		return appointmentDudctDao.queryList(map);
	}
	
	/**
	 * 查询预约列表
	 * @param map
	 * @return
	 */
	public  List<AppointmentTemplate>  queryList(AppointmentTemplate bean){
		return appointmentDudctDao.queryParamList(bean);
	}


	/**
	 * 生效失效方法
	 * @param bean
	 */
	@Transactional(value = "loanTransactionManager", readOnly = false)
	public void changEffect(AppointmentTemplate bean) {
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
	public void changEffect(List<AppointmentTemplate> list, String sign) {
		for(AppointmentTemplate appt  : list){
			AppointmentTemplate bean = new AppointmentTemplate();
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
	public void deleteData(List<AppointmentTemplate> list) {
		for(AppointmentTemplate appt : list){
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
	public void insertPaybackOpe(AppointmentTemplate appointment,String flag) {
		PaybackOpe paybackOpe = new PaybackOpe();
		paybackOpe.setrPaybackApplyId(appointment.getId());
		if(flag.equals("insert")){
			paybackOpe.setDictLoanStatus("预约划扣模板新增");//步奏
			paybackOpe.setRemarks("预约划扣模板新增 "); // 备注
		}else if(flag.equals("update")){
			paybackOpe.setDictLoanStatus("预约划扣模板修改");//步奏
			paybackOpe.setRemarks("预约划扣模板修改 "); // 备注
		}else if(flag.equals("delete")){
			paybackOpe.setDictLoanStatus("预约划扣模板删除");//步奏
			paybackOpe.setRemarks("预约划扣模板删除 "); // 备注
		}else if(flag.equals("effect")){
			paybackOpe.setDictLoanStatus("预约划扣模板生效");//步奏
			paybackOpe.setRemarks("预约划扣模板生效 "); // 备注
		}else if(flag.equals("invalid")){
			paybackOpe.setDictLoanStatus("预约划扣模板失效");//步奏
			paybackOpe.setRemarks("预约划扣模板失效 "); // 备注
		}else if(flag.equals("import")){
			paybackOpe.setDictLoanStatus("预约划扣模板导入");//步奏
			paybackOpe.setRemarks("预约划扣模板导入 "); // 备注
		}
		paybackOpe.setDictRDeductType(TargetWay.PAYMENT.getCode()+"1"); //
		paybackOpe.setOperateResult("成功"); // 结果
		paybackOpe.preInsert();
		paybackOpe.setOperator(UserUtils.getUser().getId());
		paybackOpe.setOperateCode(UserUtils.getUser().getUserCode());
		paybackOpe.setOperateTime(new Date());
		loanStatusHisDao.insertPaybackOpe(paybackOpe);
	}
}
