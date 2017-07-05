package com.creditharmony.loan.app.consult.service;


import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.dict.entity.Dict;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.loan.app.consult.dao.AppConsultDao;
import com.creditharmony.loan.app.consult.entity.AppConsult;
import com.creditharmony.loan.app.consult.type.ConsultType;
import com.creditharmony.loan.app.consult.util.AssistantConsultHelper;
import com.creditharmony.loan.app.consult.view.AssistantConsultView;
import com.creditharmony.loan.borrow.applyinfo.dao.CustomerBaseInfoDao;
import com.creditharmony.loan.borrow.consult.dao.ConsultDao;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.entity.ConsultRecord;
import com.creditharmony.loan.borrow.consult.entity.CustomerBaseInfo;
import com.creditharmony.loan.common.service.NumberMasterService;

@Service
public class AssistantConsultService{
 
	@Autowired
	private AppConsultDao appConsultDao;
	@Autowired
	private CustomerBaseInfoDao customerBaseInfoDao;
	@Autowired
    private ConsultDao consultDao;
	@Autowired
	private NumberMasterService numberMasterService;
	
	/**
	 * 获取门店ocr客户咨询列表数据
	 * @param page
	 * @param appConsult
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<AppConsult> findPage(Page<AppConsult> page,AssistantConsultView assistantConsultView){
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		PageList<AppConsult> pageList = (PageList<AppConsult>) appConsultDao
				.findAssistantConsult(pageBounds, assistantConsultView);
		PageUtil.convertPage(pageList, page);
		return page;
	}
	
	/**
	 * 根据id查询详细信息
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public AppConsult getConsult(String id){
		return appConsultDao.getConsult(id);
	}
	
	/**
	 * 根据身份证号查询客户基本信息表中是否已存在该客户
	 * @param certNum
	 * @return
	 */
	public String findCustomerByCertNum(String certNum){
		return consultDao.findCustomerByMcNum(certNum);
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveConsult(AssistantConsultView consultView) throws Exception{
		AppConsult oldAppConsult = appConsultDao.getConsult(consultView.getId());
		Boolean insertFlag = false;
		String customerCode = findCustomerByCertNum(consultView.getCertNum());
		if (StringUtils.isNotEmpty(customerCode)) {
			consultView.setCustomerId(customerCode);
		} else {
			// 生成客户编码，进行插入
			String customerId = numberMasterService.getCustomerNumber(SerialNoType.CUSTOMER); 
			consultView.setCustomerId(customerId);
			insertFlag = true;
		}
		Consult consult = new Consult();
		consultView.setCommunicateDate(new Date());
		ConsultRecord consultRecord = new ConsultRecord();
		CustomerBaseInfo customerBaseInfo = new CustomerBaseInfo();
		AssistantConsultHelper.convertCustomerBaseInfo(consultView, customerBaseInfo);
		AssistantConsultHelper.wrapperConsultRecord(consultView, consultRecord);
		AssistantConsultHelper.wrapperConsult(consultView, consult, consultRecord);
		if (insertFlag) {
			customerBaseInfo.preInsert();
			customerBaseInfoDao.insertCustomerBaseInfo(customerBaseInfo);
		}else{
			customerBaseInfo.preUpdate();
			customerBaseInfoDao.update(customerBaseInfo);
		}
		consultView.setApplyType(ConsultType.CHP);
		consultView.setIsNew(YESNO.YES.getCode());
		AppConsult appConsult = new AppConsult();
		BeanUtils.copyProperties(consultView, appConsult);
		appConsult.preUpdate();
		appConsultDao.updateAssistantConsult(appConsult);
		consult.preInsert();
		Dict bankDict = DictCache.getInstance().get(oldAppConsult.getBankId());
		consult.setBankId(bankDict != null ? bankDict.getValue() : "");
		consult.setBankProvince(oldAppConsult.getBankProvince());
		consult.setBankCity(oldAppConsult.getBankCity());
		consult.setBranch(oldAppConsult.getBranch());            //支行
		consult.setAccountId(oldAppConsult.getAccountId());      //银行卡号
		consult.setAccountBank(bankDict != null ? bankDict.getValue() : "");  //字典表中银行ID
		consult.setCustomerCode(consultView.getCustomerId());
		
		consult.setAccountIdPic(oldAppConsult.getAccountIdPic());
		consult.setAccountidPicName(oldAppConsult.getAccountidPicName());
		consult.setNamepic(oldAppConsult.getNamepic());
		consult.setNamePicName(oldAppConsult.getNamePicName());
		consult.setCertNumPic(oldAppConsult.getCertNumPic());
		consult.setCertNumPicName(oldAppConsult.getCertNumPicName());
		consult.setConsultationType(ConsultType.OCR);
		
		consultDao.insert(consult);
		// 插入咨询历史
		consultRecord.preInsert();
		consultDao.insertConsultRecord(consult);
	}
}
