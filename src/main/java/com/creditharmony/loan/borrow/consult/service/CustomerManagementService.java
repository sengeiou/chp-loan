package com.creditharmony.loan.borrow.consult.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.consult.dao.CustomerManagementDao;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.view.ConsultSearchView;
import com.creditharmony.loan.borrow.consult.view.InviteCustomerDetailView;
import com.creditharmony.loan.borrow.consult.view.InviteCustomerView;

/**
 * 客户管理Service
 * 
 * @Class Name CustomerManagementService
 * @author 张平
 * @Create In 2015年12月3日
 */
@Service("customerManagementService")
@Transactional(readOnly=true,value = "loanTransactionManager")
public class CustomerManagementService extends
		CoreManager<CustomerManagementDao, ConsultSearchView> {

	/**
	 * 查询数据
	 */
	public Page<ConsultSearchView> findPage(Page<ConsultSearchView> page,
			ConsultSearchView consultSearchView) {
		return super.findPage(page, consultSearchView);
	}
	
	/**
	 * 
	 * 查询客户咨询列表 
	 * By 任志远 2017年4月27日
	 *
	 * @param page
	 * @param consultSearchView
	 * @return
	 */
	public Page<ConsultSearchView> findCustomerConsultionPage(Page<ConsultSearchView> page, ConsultSearchView consultSearchView) {
		consultSearchView.setPage(page);
		page.setList(dao.findCustomerConsultionList(consultSearchView));
		return page;
	}
    
	
	public Page<ConsultSearchView> findApplyPage(Page<ConsultSearchView> page,
            ConsultSearchView consultSearchView){
	    consultSearchView.setPage(page);
	    page.setList(dao.findPageList(consultSearchView));
	    return page;
	}
	/**
	 * 查询历史数据 2015年12月3日 
	 * By 张平
	 * @param page
	 * @param consultSearchView
	 * @return
	 */
	public Page<ConsultSearchView> findHisPage(Page<ConsultSearchView> page,
			ConsultSearchView consultSearchView) {
		page.setCountBy("countid");
		consultSearchView.setPage(page);
		return page.setList(dao.findHisPage(consultSearchView));
	}
	
	
	

	/**
	 * 更改客户状态 2015年12月3日 
	 * By 张平
	 * @param id
	 */
	public void updateStatus(String id) {

		dao.updateStatus(id);
	}

	 /**
     *通过指定下一步为“继续跟踪”查询最早建立咨询的时间 
     *@author zhanghao
     *@Create In 2016年2月1日 
     *@param consultSearchView
     *@Return List<ConsultSearchView> 
     */
    public List<ConsultSearchView> findEarliestLog(ConsultSearchView consultSearchView){
      
        return dao.findEarliestLog(consultSearchView);
    }
    
    /**
     *通过状态“门店拒绝”查询最新的一条记录 
     *@author zhanghao
     *@Create In 2012年2月1日
     *@param consultSearchView
     *@return List<ConsultSearchView>
     * 
     */
    public List<ConsultSearchView> findLastestLog(ConsultSearchView consultSearchView){
       
        return dao.findList(consultSearchView);
    }
    
    /**
     *查询当前客户是否已经咨询且没有进件  
     *@author zhanghao
     *@Create In 2016年05月03日 
     *@param param customerCertNum、notTelsaleFlag、notTelOperStatus、telsaleFlag、telOperStatusList
     *@Return Consult  
     *
     */
    public Consult findConsultMess(Map<String,Object> param){
        return dao.findConsultMess(param);
    }




    /**
     *获得首次咨询时间
     *@author guanhongchang
     *@Create In 2016年05月03日 
     *@param param initParam
     *@Return Consult  
     *
     */
	public ConsultSearchView getConsultationTime(String string) {
		// TODO Auto-generated method stub
		return dao.getConsultationTime(string);
	}


    /**
     *获得首次咨询时间
     *@author guanhongchang
     *@Create In 2016年05月03日 
     *@param param initParam
     * @throws ParseException 
     *@Return Consult  
     *
     */
	public String getOneeditio(String consultIds) throws ParseException {
		// TODO Auto-generated method stub
	     ConsultSearchView   consultSearchView  = getConsultationTime(consultIds);
	       //获得的日期进行比对 暂定 8 20 号之前的 为 申请单 1.0版本  8.20号之后的为 1.1版本
	        //获得 的咨询时间  和常量的版本时间进行比对
	       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	       //格式化的时间
	       String CommunicateDate = dateFormat.format(consultSearchView.getConsCommunicateDate());
	       //格式化的时间 版本比较时间
	       String ONEEDITION =  SystemConfigConstant.JK_LOAN_ONLINE_DATE;
	       int i= compare_date(CommunicateDate, ONEEDITION);
	       if(i==1){
	      	 //1.0版本
	      	return "1";
	       } else 
	       if(i==-1 || i==0){
	      	 //旧版本
	      	return "-1";
	       }
	       //如果出现其他情况 则按照旧版本来算
		   return "-1";
		   
		  
	
		
	}
	
	//时间比对
    public static int compare_date(String DATE1, String DATE2) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
    }

    /** 
     * 查询邀请客户列表
	 * By 任志远 2017年5月10日
	 *
	 * @param inviteCustomerView
	 * @return
	 */
	public List<InviteCustomerView> findInviteCustomerView(InviteCustomerView inviteCustomerView) {
		List<InviteCustomerView> inviteCustomerViewList = dao.findInviteCustomerList(inviteCustomerView);
		return inviteCustomerViewList;
	}
    
	/**
	 * 查询邀请客户列表
	 * By 任志远 2017年5月5日
	 *
	 * @param page
	 * @param inviteCustomerView
	 * @return
	 */
	public Page<InviteCustomerView> findInviteCustomerPage(Page<InviteCustomerView> page, InviteCustomerView inviteCustomerView) {
		inviteCustomerView.setPage(page);
		List<InviteCustomerView> inviteCustomerViewList = this.findInviteCustomerView(inviteCustomerView);
		page.setList(inviteCustomerViewList);
		return page;
	}

	/**
	 * 
	 * By 任志远 2017年5月8日
	 *
	 * @return
	 */
	public List<InviteCustomerDetailView> findInviteCustomerDetailList(String customerId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		List<InviteCustomerDetailView> inviteCustomerDetailList = dao.findInviteCustomerDetailList(params);
		return inviteCustomerDetailList;
	}

	/** 
	 * By 任志远 2017年5月9日
	 *
	 * @param inviteCustomerView
	 */
	@Transactional(readOnly=false)
	public void doAllotInviteCustomer(InviteCustomerView inviteCustomerView) {
		inviteCustomerView.preUpdate();
		dao.updateAppCustomerInfo(inviteCustomerView);
	}
    
}