package com.creditharmony.loan.borrow.applyinfo.service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.Marriage;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.ChangerInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.ContactDao;
import com.creditharmony.loan.borrow.applyinfo.dao.CustomerLivingsDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCompManageDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCompanyDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCreditInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanHouseDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanInfoCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanMateDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanPersonalCertificateDao;
import com.creditharmony.loan.borrow.applyinfo.dao.StoreReviewDao;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;
import com.creditharmony.loan.borrow.applyinfo.entity.CustomerLivings;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompManage;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompany;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCreditInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanHouse;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfoCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanMate;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanPersonalCertificate;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.utils.ChangeInfoUtils;
import com.creditharmony.loan.utils.EncryptUtils;

/**
 * 门店复核
 * @Class Name StoreReviewService
 * @author zhangerwei
 * @Create In 2015年12月25日
 */
@Service
@Transactional(readOnly = true,value = "loanTransactionManager")
public class StoreReviewService extends CoreManager<StoreReviewDao, LoanCustomer> {
	
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	@Autowired
	private LoanMateDao loanMateDao;
	
	@Autowired
	private LoanCoborrowerDao loanCoborrowerDao;
	
	@Autowired
	private ContactDao contactDao;
	
	@Autowired
	private LoanCreditInfoDao loanCreditInfoDao;
	
	@Autowired
	private LoanCompanyDao loanCompanyDao;
	
	@Autowired
	private LoanHouseDao loanHouseDao;
	
	@Autowired
	private LoanBankDao loanBankDao;
	
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	
	@Autowired
	private CustomerLivingsDao customerLivingsDao;
	
	@Autowired
	private ChangerInfoDao changerInfoDao;
	
	@Autowired
	private LoanInfoCoborrowerDao loanInfoCoborrowerDao;
	
	@Autowired
	private LoanPersonalCertificateDao loanPersonalCertificateDao;
	
	@Autowired
	private LoanCompManageDao loanCompManageDao;

	// 暂时编码，定义主借人还是共借人状态
	String loanCustomerType1 = "1";// 主借人
	String loanCustomerType0 = "0";// 共借人
	String loanCustomerType2 = "2";// 配偶信息
	
	/**
	 * 修改客户信息，旧版申请表和旧版申请表都调用此方法
	 * 2016年1月15日
	 * By lirui
	 * @param loanCustomer 修改后的客户信息
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateLoanCustomer(LoanCustomer loanCustomer,String preResponse){
		//主借人信息加密
	    EncryptUtils.encrypt(loanCustomer);
		if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
	        LoanCustomer savedCust = loanCustomerDao.getById(loanCustomer.getId());
	        ChangeInfoUtils.insertCustomer(loanCustomer, savedCust, savedCust.getApplyId(), changerInfoDao);
	        if(!savedCust.getCustomerName().equals(loanCustomer.getCustomerName())){
	            Map<String,Object> param = new HashMap<String,Object>();
	            param.put("loanCode",savedCust.getLoanCode());
	            LoanInfo savedLoanInfo = loanInfoDao.selectByLoanCode(param);
	            savedLoanInfo.setLoanCustomerName(loanCustomer.getCustomerName());
	            savedLoanInfo.preUpdate();
	            loanInfoDao.updateLoanInfo(savedLoanInfo);
	            LoanBank savedBank = loanBankDao.selectByLoanCode(savedCust.getLoanCode());
	            savedBank.setBankAuthorizer(loanCustomer.getCustomerName());
	            savedBank.preUpdate();
	            loanBankDao.update(savedBank);
	        }
	    }
	    String oldDictMarry = loanCustomer.getOldDictMarry();
	    String curDictMarry = loanCustomer.getDictMarry();
	    if(Marriage.MARRIED.getCode().equals(oldDictMarry) && !oldDictMarry.equals(curDictMarry)){
	        Map<String,Object> param = new HashMap<String,Object>();
	        param.put("rid", loanCustomer.getId());
	        param.put("loanCustomterType",LoanManFlag.MAIN_LOAN.getCode());
	        LoanMate savedMate = loanMateDao.selectByLoanCode(loanCustomer.getLoanCode(), LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isNotEmpty(savedMate)){
	            param.put("rid", savedMate.getId());
                loanCompanyDao.deleteByRid(param);
                param.put("rid", loanCustomer.getId());
	            loanMateDao.deleteByRId(param);
	            
	        }
	        
	    }
	    loanCustomer.setCustomerName(StringEscapeUtils.unescapeHtml4(loanCustomer.getCustomerName()));
	    loanCustomer.preUpdate();
		//同步更新
		sycnUpdate(loanCustomer);
		return loanCustomerDao.update(loanCustomer);
	}
	/**
	 * 修改客户姓名同步更新借款主表中的客户姓名
	 */
	private void sycnUpdate(LoanCustomer loanCustomer){
		//更新借款主表中的客户姓名
		LoanInfo loanInfo=new LoanInfo();
		loanInfo.setLoanCode(loanCustomer.getLoanCode());
		loanInfo.setLoanCustomerName(loanCustomer.getCustomerName());
		loanInfoDao.update(loanInfo);
		//更新银行户名和授权人
		LoanBank loanBank=new LoanBank();
		loanBank.setBankAccountName(loanCustomer.getCustomerName());
	    loanBank.setBankAuthorizer(loanCustomer.getCustomerName()); 
	    loanBank.setLoanCode(loanCustomer.getLoanCode());
	    loanBankDao.updateAccountNameAndAuthorizer(loanBank);
	}
	
	/**
	 * 配偶资料修改
	 * 2016年1月18日
	 * By lirui
	 * @param loanMate 配偶资料
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateLoanMate(LaunchView launchView,String preResponse) { 
		int msg = 0;
		LoanMate loanMate = launchView.getLoanMate();
		loanMate.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("loanCode", loanMate.getLoanCode());
		LoanCustomer savedCust = loanCustomerDao.selectByLoanCode(param);
		loanMate.setRcustomerCoborrowerId(savedCust.getId());
		//配偶信息加密
		EncryptUtils.encrypt(loanMate);
		loanMate.setMateName(StringEscapeUtils.unescapeHtml4(loanMate.getMateName()));
		// 如果配偶信息存在则执行修改方法,如果不存在则执行新增方法
		if (StringUtils.isEmpty(loanMate.getId())) {
			loanMate.preInsert();
			msg = loanMateDao.insert(loanMate);
		}else{
		    // 插入修改记录
		    if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
		       LoanMate savedMate = loanMateDao.selectByLoanCode(loanMate.getLoanCode(), loanMate.getLoanCustomterType());
		       ChangeInfoUtils.insertMate(loanMate, savedMate, "", changerInfoDao);
		    }
			loanMate.preUpdate();
			msg = loanMateDao.update(loanMate);			
		}
		loanMate.getMateLoanCompany().setLoanCode(loanMate.getLoanCode());
		loanMate.getMateLoanCompany().setRid(loanMate.getId());
		loanMate.getMateLoanCompany().setDictrCustomterType(LoanManFlag.MATE.getCode());
		this.updateLoanCompany(loanMate.getMateLoanCompany(),preResponse);
		return msg;
		}
	
	/**
	 * 信用资料编辑
	 * 2016年1月22日
	 * By lirui
	 * @param loanCreditInfo 信用资料数据
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateLoanCreditInfo(LoanCreditInfo loanCreditInfo) {
		// 如果信用资料存在则执行修改方法,如果信用资料不存在则执行新增方法
		if (StringUtils.isNotEmpty(loanCreditInfo.getId())) {
			loanCreditInfo.preUpdate();
			return loanCreditInfoDao.update(loanCreditInfo);			
		} else {
			loanCreditInfo.preInsert();
			return loanCreditInfoDao.insert(loanCreditInfo);
		}
	}
	
	/**
	 * 共借人资料编辑	旧版申请表调用这个方法
	 * 2016年1月22日
	 * By lirui
	 * @param loanCoborrower 共借人资料
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")	
	public int updateLoanCoborrower(LaunchView launchView,String preResponse) {
		int msg = 0;
		LoanCoborrower currCoborrower = null;
		for (int i = 0; i < launchView.getLoanCoborrower().size(); i++) {
		    currCoborrower = launchView.getLoanCoborrower().get(i);
		    currCoborrower.setCoboName(StringEscapeUtils.unescapeHtml4(currCoborrower.getCoboName())); 
		    currCoborrower.setLoanCode(launchView.getLoanInfo().getLoanCode());
			// 如果id存在执行修改方法,否则执行新增方法
			if (StringUtils.isNotEmpty(currCoborrower.getId())) {
			    // 插入修改记录
	            if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
	                LoanCoborrower savedCobo = loanCoborrowerDao.get(currCoborrower.getId());
	                ChangeInfoUtils.insertCoborrower(currCoborrower, savedCobo, launchView.getApplyId(), changerInfoDao);
	            }
			    currCoborrower.preUpdate();
				msg = loanCoborrowerDao.update(currCoborrower);
				
			} else {
			    currCoborrower.preInsert();
				msg = loanCoborrowerDao.insert(currCoborrower);
			}
			// 共借人公司资料
			launchView.getLoanCoborrower().get(i).getCoboCompany().setLoanCode(launchView.getLoanInfo().getLoanCode());	
			launchView.getLoanCoborrower().get(i).getCoboCompany().setRid(currCoborrower.getId());
			launchView.getLoanCoborrower().get(i).getCoboCompany().setDictrCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
			this.updateLoanCompany(launchView.getLoanCoborrower().get(i).getCoboCompany(),preResponse);	
			// 共借人房屋居住情况
			launchView.getLoanCoborrower().get(i).getCoboLivings().setLoanCode(launchView.getLoanInfo().getLoanCode());
			this.updateCustomerLivings(launchView.getLoanCoborrower().get(i).getCoboLivings());
			// 共借人联系人遍历修改
			for (int j = 0; j < launchView.getLoanCoborrower().get(i).getCoborrowerContactList().size(); j++) {
				launchView.getLoanCoborrower().get(i).getCoborrowerContactList().get(j).setLoanCode(launchView.getLoanInfo().getLoanCode());
				launchView.getLoanCoborrower().get(i).getCoborrowerContactList().get(j).setRcustomerCoborrowerId(launchView.getLoanCoborrower().get(i).getId());
				launchView.getLoanCoborrower().get(i).getCoborrowerContactList().get(j).setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
				this.updateContact(launchView.getLoanCoborrower().get(i).getCoborrowerContactList().get(j),preResponse);
			}
		}
		return msg;
	}
	
	/**
	 * 修改自然人保证人，新版申请表调用这个方法
	 * By 任志远	2016年10月18日
	 * 
	 * @param launchView
	 * @param preResponse
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")	
	public int updateNaturalGuarantor(LaunchView launchView,String preResponse) {
		int msg = 0;
		
		for (int i = 0; i < launchView.getLoanCoborrower().size(); i++) {
			LoanCoborrower currCoborrower = launchView.getLoanCoborrower().get(i);
		    currCoborrower.setCoboName(StringEscapeUtils.unescapeHtml4(currCoborrower.getCoboName())); 
		    currCoborrower.setLoanCode(launchView.getLoanInfo().getLoanCode());
		    //自然人保证加密
		    EncryptUtils.encrypt(currCoborrower);
			// 如果id存在执行修改方法,否则执行新增方法
			if (StringUtils.isNotEmpty(currCoborrower.getId())) {
			    // 插入修改记录
	            if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
	                LoanCoborrower savedCobo = loanCoborrowerDao.get(currCoborrower.getId());
	                ChangeInfoUtils.insertCoborrower(currCoborrower, savedCobo, launchView.getApplyId(), changerInfoDao);
	            }
			    currCoborrower.preUpdate();
				msg = loanCoborrowerDao.update(currCoborrower);
				
			} else {
			    currCoborrower.preInsert();
				msg = loanCoborrowerDao.insert(currCoborrower);
			}
			// 修改自然人保证人借款意愿
			launchView.getLoanCoborrower().get(i).getLoanInfoCoborrower().setLoanCode(launchView.getLoanInfo().getLoanCode());
			launchView.getLoanCoborrower().get(i).getLoanInfoCoborrower().setRid(currCoborrower.getId());
			this.saveLoanInfoCoborrower(launchView.getLoanCoborrower().get(i).getLoanInfoCoborrower());
			// 修改自然人保证人公司
			launchView.getLoanCoborrower().get(i).getCoboCompany().setLoanCode(launchView.getLoanInfo().getLoanCode());	
			launchView.getLoanCoborrower().get(i).getCoboCompany().setRid(currCoborrower.getId());
			launchView.getLoanCoborrower().get(i).getCoboCompany().setDictrCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
			this.updateLoanCompany(launchView.getLoanCoborrower().get(i).getCoboCompany(),preResponse);	
			// 修改自然人保证人联系人
			for (int j = 0; j < launchView.getLoanCoborrower().get(i).getCoborrowerContactList().size(); j++) {
				launchView.getLoanCoborrower().get(i).getCoborrowerContactList().get(j).setLoanCode(launchView.getLoanInfo().getLoanCode());
				launchView.getLoanCoborrower().get(i).getCoborrowerContactList().get(j).setRcustomerCoborrowerId(launchView.getLoanCoborrower().get(i).getId());
				launchView.getLoanCoborrower().get(i).getCoborrowerContactList().get(j).setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
				this.updateContact(launchView.getLoanCoborrower().get(i).getCoborrowerContactList().get(j),preResponse);
			}
		}
		return msg;
	}
	/**
	 * 公司信息/职业资料编辑
	 * 2016年1月20日
	 * By lirui
	 * @param loanCompany 公司资料
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateLoanCompany(LoanCompany loanCompany,String preResponse){
		// 如果申请信息存在则执行修改方法,如果申请信息不存在则执行新增方法
		if (StringUtils.isEmpty(loanCompany.getId())) {
			loanCompany.preInsert();
			return loanCompanyDao.insert(loanCompany);
		}else{
		    // 插入修改记录
            if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
                LoanCompany savedComp = loanCompanyDao.get(loanCompany.getId());
                // 插入变更信息
                ChangeInfoUtils.insertCompany(loanCompany, savedComp, "", changerInfoDao);
            }
            //解决Sql 字段=null 时, 不修改的问题
			loanCompany.setCompSalary(loanCompany.getCompSalary() == null ? new BigDecimal(0) : loanCompany.getCompSalary());
			
			loanCompany.preUpdate();
			return loanCompanyDao.update(loanCompany); 		
		}
	}
	
	/**
	 * 银行卡资料编辑
	 * 2016年1月20日
	 * By lirui
	 * @param loanBank 银行卡资料
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateLoanBank(LoanBank loanBank){
	    
	    loanBank.setBankAccountName(StringEscapeUtils.unescapeHtml4(loanBank.getBankAccountName()));
	    loanBank.setBankAuthorizer(StringEscapeUtils.unescapeHtml4(loanBank.getBankAuthorizer())); 
		// 如果银行卡信息存在则执行修改方法,如果申请信息不存在则执行新增方法
		if (StringUtils.isEmpty(loanBank.getId())) {
			loanBank.preInsert();
			return loanBankDao.insert(loanBank);
		}else{
			loanBank.preUpdate();
			return loanBankDao.update(loanBank);			
		}
	}
	
	/**
	 * 联系人资料编辑
	 * 2016年1月22日
	 * By lirui
	 * @param contact 联系人资料
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateContact(Contact contact,String preResponse) {
        contact.setContactName(StringEscapeUtils.unescapeHtml4(contact.getContactName())); 
        //联系人信息加密
        EncryptUtils.encrypt(contact);
		// 如果联系人id存在则执行修改方法,否则执行新增方法
		if (StringUtils.isNotEmpty(contact.getId())) {
		    // 插入修改记录
            if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
                Contact savedContact = contactDao.get(contact.getId());
                ChangeInfoUtils.insertContact(contact, savedContact, "", changerInfoDao);
            }
            contact.preUpdate();
            return contactDao.update(contact);		
		} else {
			contact.preInsert();
			return contactDao.insert(contact);
		} 
	}
	
	/**
	 * 房产资料编辑
	 * 2016年1月22日
	 * By lirui
	 * @param loanHouse 房产资料
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateLoanLoanHouse(LoanHouse loanHouse) {
		// 如果房产id存在则执行修改方法,不存在则执行新增方法
		if (StringUtils.isNotEmpty(loanHouse.getId())) {
			loanHouse.preUpdate();
			
			//解决Sql 字段=null 时, 不修改的问题
		    loanHouse.setHouseAmount(loanHouse.getHouseAmount() == null ? new BigDecimal(0) : loanHouse.getHouseAmount());
		    loanHouse.setHouseLoanAmount(loanHouse.getHouseLoanAmount() == null ? new BigDecimal(0) : loanHouse.getHouseLoanAmount());
		    loanHouse.setHouseLessAmount(loanHouse.getHouseLessAmount() == null ? new BigDecimal(0) : loanHouse.getHouseLessAmount());
		    loanHouse.setHouseMonthRepayAmount(loanHouse.getHouseMonthRepayAmount() == null ? new BigDecimal(0) : loanHouse.getHouseMonthRepayAmount());
		    loanHouse.setHouseLoanYear(loanHouse.getHouseLoanYear() == null ? new BigDecimal(0) : loanHouse.getHouseLoanYear());
			
			return loanHouseDao.update(loanHouse);			
		}else {
			loanHouse.preInsert();
			return loanHouseDao.insert(loanHouse);
		}
	}
	
	/**
	 * 申请信息编辑
	 * 2016年1月18日
	 * By lirui
	 * @param loanInfo 申请信息
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateLoanInfo(LoanInfo loanInfo){
		// 如果申请信息存在则执行修改方法,如果申请信息不存在则执行新增方法
		if (StringUtils.isEmpty(loanInfo.getId())) {
			loanInfo.preInsert();
			return loanInfoDao.insert(loanInfo);
		}else{
			loanInfo.preUpdate();
			return loanInfoDao.update(loanInfo);		
		}
	}
	
	/**
	 * 客户房屋居住信息编辑
	 * 2016年1月15日
	 * By lirui
	 * @param customerLivings 房屋居住信息
	 * @return 执行记录
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateCustomerLivings(CustomerLivings customerLivings){
		// 如果客户房屋居住信息存在则执行修改方法,否则执行新增方法
		if (StringUtils.isNotEmpty(customerLivings.getId())) {
			customerLivings.preUpdate();
			return customerLivingsDao.update(customerLivings);			
		}else{
			customerLivings.preInsert();
			return customerLivingsDao.insert(customerLivings);
		}
	}
	
	/**
	 * 删除联系人
	 * 2016年1月28日
	 * By lirui
	 * @param contractId 联系人id
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void delContact(String contactId,String preResponse) { 
	    Map<String , Object> param = new HashMap<String, Object>();
        param.put("id", contactId);
	    // 插入修改记录
        if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
            Contact savedContact = contactDao.get(contactId);
            ChangeInfoUtils.insertContact(null, savedContact, "", changerInfoDao);
        }
		contactDao.deleteByCondition(param);
	}
	/**
     * 删除共借人及其联系人
     * 2016年06月22日
     * By zhanghao
     * @param coboId 共借人id
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public String delCoborrower(String coboId,String preResponse){
	    Map<String,Object> delParam = new HashMap<String,Object>();
	    String loanCode = null;
	    LoanCoborrower savedCobo = loanCoborrowerDao.get(coboId);
	    // 插入修改记录
        if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
            List<Contact> savedContacts = contactDao.findListByLinkId(savedCobo.getLoanCode(), coboId, LoanManFlag.COBORROWE_LOAN.getCode());
            for(Contact savedContact:savedContacts){
                ChangeInfoUtils.insertContact(null, savedContact, "", changerInfoDao);
                delParam.put("id", savedContact.getId()); 
                contactDao.deleteByCondition(delParam);
            }
            ChangeInfoUtils.insertCoborrower(null, savedCobo, "", changerInfoDao);
        }else{
            delParam.put("rid", coboId);
            contactDao.deleteByRid(delParam);
        }
	    delParam.put("id", coboId);
	    loanCode = savedCobo.getLoanCode();
        loanCoborrowerDao.deleteById(delParam);
        return loanCode;
	}
	
	/**
     * 删除自然人保证人及其关联的联系人，工作信息，借款意愿
     * By 任志远 2016年06月22日
     * @param coboId 自然人保证人id
     */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public String delNaturalGuarantor(String coboId,String preResponse){
	    Map<String,Object> delParam = new HashMap<String,Object>();
	    String loanCode = null;
	    LoanCoborrower savedCobo = loanCoborrowerDao.get(coboId);
	    // 插入修改记录
        if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
            List<Contact> savedContacts = contactDao.findListByLinkId(savedCobo.getLoanCode(), coboId, LoanManFlag.COBORROWE_LOAN.getCode());
            for(Contact savedContact:savedContacts){
                ChangeInfoUtils.insertContact(null, savedContact, "", changerInfoDao);
                delParam.put("id", savedContact.getId()); 
                contactDao.deleteByCondition(delParam);
            }
            ChangeInfoUtils.insertCoborrower(null, savedCobo, "", changerInfoDao);
        }else{
            delParam.put("rid", coboId);
            contactDao.deleteByRid(delParam);
        }
	    delParam.put("id", coboId);
	    delParam.put("rid", coboId);
	    loanCode = savedCobo.getLoanCode();
	    loanInfoCoborrowerDao.deleteByRid(delParam);
	    loanCompanyDao.deleteByRid(delParam);
        loanCoborrowerDao.deleteById(delParam);
        return loanCode;
	}
	
	/**
	 * 自然人保证人信息编辑
	 * By 任志远	2016年09月23日
	 * @param loanInfoCoborrower	自然人保证人
	 * @return
	 */
	public int saveLoanInfoCoborrower(LoanInfoCoborrower loanInfoCoborrower){
		
		if(StringUtils.isBlank(loanInfoCoborrower.getId())){
			loanInfoCoborrower.preInsert();
			return loanInfoCoborrowerDao.insert(loanInfoCoborrower);
		}else{
			loanInfoCoborrower.preUpdate();
			return  loanInfoCoborrowerDao.update(loanInfoCoborrower);
		}
	}
	
	/**
	 * 证件信息修改
	 * By 任志远	2016年09月23日
	 * @param 	自然人保证人
	 * @return
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateCertificate(LoanPersonalCertificate loanPersonalCertificate){
		
		if(StringUtils.isBlank(loanPersonalCertificate.getId())){
			loanPersonalCertificate.preInsert();
			return loanPersonalCertificateDao.insert(loanPersonalCertificate);
		}else{
			loanPersonalCertificate.preUpdate();
			return  loanPersonalCertificateDao.update(loanPersonalCertificate);
		}
	}
	/**
	 * 经营信息修改
	 * By 张美青	2016年09月24日
	 * @param 	loanCompManage
	 * @return
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateManager(LoanCompManage loanCompManage, String preResponse) {
		//经营信息加密
		EncryptUtils.encrypt(loanCompManage);
		if(StringUtils.isBlank(loanCompManage.getId())){
			loanCompManage.preInsert();
			return loanCompManageDao.insert(loanCompManage);
		}else{
			// 插入修改记录
            if("STORE_TO_CHECK".equals(preResponse) || "STORE_BACK_CHECK".equals(preResponse)){
            	LoanCompManage savedLoanCompManage = loanCompManageDao.get(loanCompManage.getId());
                ChangeInfoUtils.insertLoanCompManage(loanCompManage, savedLoanCompManage, "", changerInfoDao);
            }
			loanCompManage.preUpdate();
			return  loanCompManageDao.update(loanCompManage);
		}
	}
	
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public int updateLoanCustomer(LoanCustomer loanCustomer){
	    
		loanCustomer.preUpdate();
		return loanCustomerDao.update(loanCustomer);
		
	}
}
