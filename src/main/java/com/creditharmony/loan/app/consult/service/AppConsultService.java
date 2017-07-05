package com.creditharmony.loan.app.consult.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.app.consult.dao.AppConsultDao;
import com.creditharmony.loan.app.consult.entity.AppConsult;
import com.creditharmony.loan.app.consult.type.ConsultType;
import com.creditharmony.loan.app.consult.util.AppConsultHelper;
import com.creditharmony.loan.app.consult.view.AppConsultView;
import com.creditharmony.loan.borrow.applyinfo.dao.CustomerBaseInfoDao;
import com.creditharmony.loan.borrow.consult.dao.ConsultDao;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
import com.creditharmony.loan.common.service.NumberMasterService;

/**
 * app客户咨询处理
 * @Class Name AppConsultService
 * @author 朱静越
 * @Create In 2016年6月10日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class AppConsultService extends CoreManager<AppConsultDao, AppConsult> {
   
    @Autowired
    private ConsultDao consultDao;
    
    @Autowired
    private CustomerBaseInfoDao customerBaseInfoDao;
    
    @Autowired
    private NumberMasterService numberMasterService;
    
    /**
     * 获得app客户咨询列表
     * 2016年6月10日
     * By 朱静越
     * @param page
     * @return
     */
    @Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<AppConsult> findList(Page<AppConsult> page,
			AppConsultView consultView) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<AppConsult> pageList = (PageList<AppConsult>)dao.findList(pageBounds,consultView);
		PageUtil.convertPage(pageList, page);
		return page;
	}

    /**
	 * 根据id查询详细页面
	 * 2016年6月10日
	 * By 朱静越
	 * @param id
	 * @return
	 */
	public AppConsult getConsultForm(String id){
		return dao.getConsult(id);
	}
	
	/**
	 * 根据身份证号查询客户base表中该客户是否存在
	 * 2016年6月10日,使用咨询的dao
	 * By 朱静越
	 * @param certNum 身份证号
	 * @return
	 */
	public String findCustomerByMcNum(String mateCertNum){
		return consultDao.findCustomerByMcNum(mateCertNum);
	}
	
	/**
	 * 业务处理
	 * 2016年6月11日
	 * By 朱静越
	 * @param insertFlag
	 * @param consultView
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveConsult(Boolean insertFlag, AppConsultView consultView){
		AppConsult oldAppConsult = dao.getConsult(consultView.getId());
		Consult consult = AppConsultHelper.convertBaseConsult(consultView);
		CustomerBaseInfo customerBaseInfo = consult.getCustomerBaseInfo();
		ConsultRecord consultRecord = consult.getConsultRecord();
		if (insertFlag) {
			customerBaseInfo.preInsert();
			customerBaseInfoDao.insertCustomerBaseInfo(customerBaseInfo);
		}else{
			customerBaseInfo.preUpdate();
			customerBaseInfoDao.update(customerBaseInfo);
		}
		// 客户咨询表的insert
		consult.preInsert();
		consult.setProductCode(oldAppConsult != null ? oldAppConsult.getProductCode() : "");
		consult.setLoanMonth(oldAppConsult != null ? oldAppConsult.getLoanMonth() : null);
		consult.setConsultationType(ConsultType.APP);
		consultDao.insert(consult);
		// 插入客户咨询表的log
		consultRecord.preInsert();
		consultDao.insertConsultRecord(consult);
		// 更新app主表,设置来源系统为CHP
		consultView.setApplyType(ConsultType.CHP);
		AppConsult appConsult = new AppConsult();
		BeanUtils.copyProperties(consultView, appConsult);
		dao.updateAppConsult(appConsult);
	}
	
}
