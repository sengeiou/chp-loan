package com.creditharmony.loan.borrow.applyinfo.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.creditharmony.loan.common.type.LoanProductCode;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.LoanType;
import com.creditharmony.core.loan.type.RelationType;
import com.creditharmony.core.loan.type.RemarkType;
import com.creditharmony.core.loan.type.SystemFromFlag;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.ContactDao;
import com.creditharmony.loan.borrow.applyinfo.dao.CustomerBaseInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.CustomerLivingsDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCompManageDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCompanyDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCreditInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanHouseDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanInfoCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanMateDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanPersonalCertificateDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanRemarkDao;
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
import com.creditharmony.loan.borrow.applyinfo.entity.LoanRemark;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.consult.dao.ConsultDao;
import com.creditharmony.loan.borrow.consult.dao.CustomerManagementDao;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.service.CustomerManagementService;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.type.OpenBankKLCode;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.utils.EncryptUtils;

/**
 * @Class Name DataEntryService
 * @author zhangping
 * @Create In 2015年12月3日 
 */
@Service("dataEntryService")
public class DataEntryService extends CoreManager<ConsultDao, Consult> {

	@Autowired
	private CustomerBaseInfoDao customerBaseInfoDao;
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	@Autowired
	private CustomerLivingsDao customerLivingsDao;
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
	private LoanRemarkDao loanRemarkDao;
	@Autowired
	private ConsultDao consultDao;
	@Autowired
	private LoanInfoCoborrowerDao loanInfoCoborrowerDao;
	@Autowired
	private LoanCompManageDao loanCompManageDao;
	@Autowired
	private LoanPersonalCertificateDao loanPersonalCertificateDao;
	
	private static final Integer BANK_TOP_FLAG = 1;  // 银行卡置顶标识
	
	/**
	 * 获取客户资料以及其附属资料信息 ，此方法适用于旧版申请表，新版申请表请见getCustumerData_new()方法
	 * 2015年12月3日 
	 * By 张平
	 * @param initParam
	 * @return ApplyInfoFlagEx对象数据
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public ApplyInfoFlagEx getCustumerData(Map<String,String> initParam) {
	    String customerCode = initParam.get("customerCode");
	    String loanCode = initParam.get("loanCode");
	    String flag = initParam.get("flag");
	    String consultId = initParam.get("consultId");
	    ApplyInfoFlagEx applyInFoFlagEx  = new ApplyInfoFlagEx();
	    Map<String,Object> param = new HashMap<String,Object>();
	    param.put("loanCode", loanCode);
	    param.put("customerCode", customerCode);
	    logger.info("发起基础信息页   借款编号："+loanCode +" 客户编号："+customerCode+" 页签类型："+flag+" 咨询ID:"+consultId);
	    if(ApplyInfoConstant.APPLY_INFO_CUSTOMER.equals(flag)){
	        logger.info("获取主借人信息");
		    // 获取客户基本信息
	        applyInFoFlagEx = customerBaseInfoDao.selectByPrimaryKey(customerCode);
	      
	        if(ObjectHelper.isEmpty(applyInFoFlagEx)){
	          
	           applyInFoFlagEx = new ApplyInfoFlagEx();
	       }
	        LoanCustomer loanCustomer = null;
	        // 获取主借人客户居住情况
	        CustomerLivings customerLivings = null;
		    // 获取上一次借款客户信息
	        loanCustomer = loanCustomerDao.selectByConsultId(consultId);
	  
		    if(!ObjectHelper.isEmpty(loanCustomer)){
	            String tempLoanCode = loanCustomer.getLoanCode();
                // 获取主借人的住房信息
               customerLivings = customerLivingsDao.selectByLoanCode(tempLoanCode,
                LoanManFlag.MAIN_LOAN.getCode());
               if(ObjectHelper.isEmpty(customerLivings)){
                   customerLivings = new CustomerLivings();  
               }
            
	        }else {
                loanCustomer = new LoanCustomer();  
                customerLivings = new CustomerLivings();  
	        }
		    applyInFoFlagEx.setLoanCustomer(loanCustomer);
		    applyInFoFlagEx.setCustomerLivings(customerLivings);
		    
	    }else if(ApplyInfoConstant.APPLY_INFO_MATE.equals(flag)){
	        logger.info("获取主借人的配偶信息");
	        // 获取主借人客户配偶信息
	        LoanMate loanMate = null;
	        // 获取主借人配偶信息的公司职业信息（set到主借人客户配偶信息对象中用于页面显示）
	        LoanCompany mateLoanCompany = null;
	        LoanCustomer tempCustomer = loanCustomerDao.selectByConsultId(consultId);
	        if(ObjectHelper.isEmpty(tempCustomer) || !ApplyInfoConstant.MARRAY_STATE.equals(tempCustomer.getDictMarry())){
	            applyInFoFlagEx.setIsMarried(ApplyInfoConstant.NOT_MARRIED); 
	        }else{
	            applyInFoFlagEx.setIsMarried(ApplyInfoConstant.MARRIED);  
	        }
	        loanMate = loanMateDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
	        mateLoanCompany = loanCompanyDao.selectByLoanCode(loanCode,LoanManFlag.MATE.getCode());
	        if(ObjectHelper.isEmpty(loanMate)){
	            loanMate = new LoanMate();
	        }
	        if(ObjectHelper.isEmpty(mateLoanCompany)){
	            mateLoanCompany = new LoanCompany();
	        }
	        loanMate.setMateLoanCompany(mateLoanCompany);
	        applyInFoFlagEx.setLoanMate(loanMate); 
	    }else if (ApplyInfoConstant.APPLY_INFO_LOANINFO.equals(flag)){
	        logger.info("获取申请信息");
	        LoanInfo loanInfo = null;
	        loanInfo = loanInfoDao.selectByLoanCode(param);
	        if(ObjectHelper.isEmpty(loanInfo)){
	            loanInfo = new LoanInfo();
	        }else{
	           if(ObjectHelper.isEmpty(loanInfo.getLoanApplyTime())){
	               loanInfo.setLoanApplyTime(new Date());
	           }
	        }
	         Consult consult = consultDao.get(consultId);
	         if(!ObjectHelper.isEmpty(consult)){
	             if(ObjectHelper.isEmpty(loanInfo.getLoanApplyAmount())){
	               loanInfo.setLoanApplyAmount(consult.getLoanApplyMoney());  
	             }
	             if(ObjectHelper.isEmpty(loanInfo.getDictLoanUse())){
	                 loanInfo.setDictLoanUse(consult.getDictLoanUse());
	             }
	             if(ObjectHelper.isEmpty(loanInfo.getLoanMonths())){
	                 loanInfo.setLoanMonths(consult.getLoanMonth());
	             }
	             if(ObjectHelper.isEmpty(loanInfo.getProductType())){
                     loanInfo.setProductType(consult.getProductCode());
                 }
	             loanInfo.setLoanManagerCode(consult.getManagerCode());
	             loanInfo.setIsBorrow(consult.getIsBorrow());
	         }
	         applyInFoFlagEx.setLoanInfo(loanInfo);
	         List<LoanRemark> loanRemarks = loanRemarkDao.findByLoanCode(param);
	         if(!ObjectHelper.isEmpty(loanRemarks) && !ObjectHelper.isEmpty(loanRemarks.get(0))){
	           applyInFoFlagEx.setLoanRemark(loanRemarks.get(0));  
	         }
	    }else if(ApplyInfoConstant.APPLY_INFO_COBORROWER.equals(flag)){
	        logger.info("获取共借人信息");
	         // 共同借款人信息
	        List<LoanCoborrower> loanCoborrower = null; 
	        String coborrowId = null;
	        // 共同借款人联系人信息(set到共同借款人对象中，用于返回页面显示)
	        List<Contact> coborrowerContactList = null;
	
	        loanCoborrower = loanCoborrowerDao.selectByLoanCode(loanCode);
	        // 获取共借人的住房信息
	        CustomerLivings coboLivings = null;
	        LoanCompany coboCompany = null;
	        Map<String,Object> queryParam = new HashMap<String,Object>();
	        if(!ObjectHelper.isEmpty(loanCoborrower)){
	           for(LoanCoborrower coborrow:loanCoborrower){
	              coborrowId = coborrow.getId();
	              queryParam.put("loanCode", loanCode);
                  queryParam.put("relateId", coborrowId);
                  queryParam.put("customerType",LoanManFlag.COBORROWE_LOAN.getCode());
	             
                  coborrowerContactList = contactDao.findListByLinkId(loanCode,
                		  LoanManFlag.COBORROWE_LOAN.getCode(),coborrowId);
	              
	              coboLivings = customerLivingsDao.findByParam(queryParam);
	              
	              coboCompany = loanCompanyDao.findByParam(queryParam);
	              
	              if(ObjectHelper.isEmpty(loanCoborrower)){
	                  coborrowerContactList = new LinkedList<Contact>(); 
	                  coborrowerContactList.add(new Contact());
	              }
	              if(ObjectHelper.isEmpty(coboLivings)){
	                  coboLivings = new CustomerLivings(); 
                  }
	              if(ObjectHelper.isEmpty(coboCompany)){
	                  coboCompany = new LoanCompany(); 
                  }
	               coborrow.setCoboCompany(coboCompany);
	               coborrow.setCoboLivings(coboLivings);
	               coborrow.setCoborrowerContactList(coborrowerContactList);
	           }
	        }else{
	            coborrowerContactList = new LinkedList<Contact>();
	            coborrowerContactList.add(new Contact());
	            loanCoborrower = new LinkedList<LoanCoborrower>();
	            LoanCoborrower loanCoborrow = new LoanCoborrower();
	            loanCoborrow.setCoborrowerContactList(coborrowerContactList);
	            loanCoborrower.add(loanCoborrow);
	        }
	        applyInFoFlagEx.setLoanCoborrower(loanCoborrower);
	    }else if (ApplyInfoConstant.APPLY_INFO_CREDITINFO.equals(flag)) {
	        logger.info("获取信用卡信息");
	        // 信用资料信息集合（set到客户基本信息对象中用于页面显示）
	        List<LoanCreditInfo> loanCreditInfoList = null;
	        loanCreditInfoList = loanCreditInfoDao.findListByLoanCode(loanCode);
	        if(ObjectHelper.isEmpty(loanCreditInfoList)){
	            LoanCreditInfo creditInfo = new LoanCreditInfo();
	            loanCreditInfoList = new LinkedList<LoanCreditInfo>();  
	            loanCreditInfoList.add(creditInfo);
	        }
	        applyInFoFlagEx.setLoanCreditInfoList(loanCreditInfoList);
	    } else if (ApplyInfoConstant.APPLY_INFO_COMPANY.equals(flag)) {
	        logger.info("获取公司/职业信息");
	        // 主借人职业信息/公司资料（set到客户基本信息对象里用于页面显示）
	        LoanCompany customerLoanCompany = null; 
	        customerLoanCompany = loanCompanyDao.selectByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isEmpty(customerLoanCompany)){
	            customerLoanCompany = new LoanCompany(); 
	        }
	        applyInFoFlagEx.setCustomerLoanCompany(customerLoanCompany);
	    }else if (ApplyInfoConstant.APPLY_INFO_HOUSE.equals(flag)) {
	        logger.info("获取房产信息");
	        // 主借人房产资料集合（set到客户基本信息对象里用于页面显示）
	        List<LoanHouse> customerLoanHouseList = null;
	        customerLoanHouseList = loanHouseDao.findListByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isEmpty(customerLoanHouseList)){
	            LoanHouse loanHouse = new LoanHouse();
	            customerLoanHouseList = new LinkedList<LoanHouse>();
	            customerLoanHouseList.add(loanHouse);
	        }
	        applyInFoFlagEx.setCustomerLoanHouseList(customerLoanHouseList);
	    } else if (ApplyInfoConstant.APPLY_INFO_CONTACT.equals(flag)) {
	        logger.info("获取联系人信息");
	        // 主借人客户联系人信息（set到客户基本信息对象中用于页面显示）
	        List<Contact> customerContactList = null;
	        customerContactList = contactDao.findListByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        applyInFoFlagEx.setCustomerContactList(customerContactList);
	    }else if (ApplyInfoConstant.APPLY_INFO_BANK.equals(flag)) {
	        logger.info("获取银行卡信息");
	        // 借款人账户信息
	        LoanBank loanBank = null; 
	        Consult consult = consultDao.get(consultId);
	        loanBank = loanBankDao.selectByLoanCode(loanCode);
	        if(ObjectHelper.isEmpty(loanBank)){
	            loanBank = new LoanBank(); 
	         }
	        if(!ObjectHelper.isEmpty(consult)){
	            if(ObjectHelper.isEmpty(loanBank.getBankProvince())){
	                loanBank.setBankProvince(consult.getBankProvince());
	            }
	            if(ObjectHelper.isEmpty(loanBank.getBankCity())){
                    loanBank.setBankCity(consult.getBankCity());
                }
	            if(ObjectHelper.isEmpty(loanBank.getBankName())){
                    loanBank.setBankName(consult.getAccountBank());
                }
	            if(ObjectHelper.isEmpty(loanBank.getBankBranch())){
                    loanBank.setBankBranch(consult.getBranch());
                }
	            if(ObjectHelper.isEmpty(loanBank.getBankAccount())){
                    loanBank.setBankAccount(consult.getAccountId());
                }
	        }
	        applyInFoFlagEx.setLoanBank(loanBank);
	    }
    	return applyInFoFlagEx;
	}

	/**
	 * 此方法适用于新版申请表
	 */
	@Transactional(readOnly = true,value = "loanTransactionManager")
	public ApplyInfoFlagEx getCustumerData_new(Map<String,String> initParam) {
	    String customerCode = initParam.get("customerCode");
	    String loanCode = initParam.get("loanCode");
	    String flag = initParam.get("flag");
	    String consultId = initParam.get("consultId");
	    ApplyInfoFlagEx applyInFoFlagEx  = new ApplyInfoFlagEx();
	    Map<String,Object> param = new HashMap<String,Object>();
	    param.put("loanCode", loanCode);
	    param.put("customerCode", customerCode);
	    logger.info("发起基础信息页   借款编号："+loanCode +" 客户编号："+customerCode+" 页签类型："+flag+" 咨询ID:"+consultId);
		if (ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER.equals(flag)) {
			logger.info("获取个人基本信息");
			// 获取客户基本信息
			applyInFoFlagEx = customerBaseInfoDao.selectByPrimaryKey(customerCode);
			if (ObjectHelper.isEmpty(applyInFoFlagEx)) {
				applyInFoFlagEx = new ApplyInfoFlagEx();
			}
			LoanCustomer loanCustomer = null;
			// 获取主借人客户居住情况
			CustomerLivings customerLivings = null;
			// 获取上一次借款客户信息
			loanCustomer = loanCustomerDao.selectByConsultId(consultId);
			if (!ObjectHelper.isEmpty(loanCustomer)) {
				String tempLoanCode = loanCustomer.getLoanCode();
				// 获取主借人的住房信息
				customerLivings = customerLivingsDao.selectByLoanCode(tempLoanCode, LoanManFlag.MAIN_LOAN.getCode());
				if (ObjectHelper.isEmpty(customerLivings)) {
					customerLivings = new CustomerLivings();
				}
			} else {
				loanCustomer = new LoanCustomer();
				customerLivings = new CustomerLivings();
			}
			//主借人信息解密
			EncryptUtils.decrypt(loanCustomer);
			applyInFoFlagEx.setLoanCustomer(loanCustomer);
			applyInFoFlagEx.setCustomerLivings(customerLivings);
		} else if (ApplyInfoConstant.NEW_APPLY_INFO_LOANINFO.equals(flag)){ 
			logger.info("获取借款意愿");
			LoanInfo loanInfo = loanInfoDao.selectByLoanCode(param);
			loanInfo = this.queryLoanInfo(loanInfo, consultId);
			applyInFoFlagEx.setLoanInfo(loanInfo);
			List<LoanRemark> loanRemarks = loanRemarkDao.findByLoanCode(param);
			if (!ObjectHelper.isEmpty(loanRemarks) && !ObjectHelper.isEmpty(loanRemarks.get(0))) {
				applyInFoFlagEx.setLoanRemark(loanRemarks.get(0));
			}
	    } else if (ApplyInfoConstant.NEW_APPLY_INFO_COMPANY.equals(flag)) { //新页签常量已经新建，请查看常量类
	    	logger.info("获取公司/职业信息");
	    	// 主借人职业信息/公司资料（set到客户基本信息对象里用于页面显示）
	    	LoanCompany customerLoanCompany = loanCompanyDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
	        applyInFoFlagEx.setCustomerLoanCompany(customerLoanCompany == null ? new LoanCompany() : customerLoanCompany);
	    
	        //主借人借款意愿
	        LoanInfo loanInfo = loanInfoDao.selectByLoanCode(param);
			loanInfo = this.queryLoanInfo(loanInfo, consultId);
			applyInFoFlagEx.setLoanInfo(loanInfo);
	    } else if(ApplyInfoConstant.NATURAL_GUARANTOR.equals(flag)){
	    	logger.info("获取自然人保证人信息");
	    	//自然人保证人
	    	List<LoanCoborrower> loanCoborrowerList = loanCoborrowerDao.selectByLoanCode(loanCode);
	    	loanCoborrowerList = this.queryNaturalGuarantor(loanCoborrowerList, loanCode);
	        applyInFoFlagEx.setLoanCoborrower(loanCoborrowerList);
	        //主借人借款意愿
	        LoanInfo loanInfo = loanInfoDao.selectByLoanCode(param);
			loanInfo = this.queryLoanInfo(loanInfo, consultId);
			applyInFoFlagEx.setLoanInfo(loanInfo);
	    }else if (ApplyInfoConstant.APPLY_INFO_CREDITINFO.equals(flag)) { //新页签常量已经新建，请查看常量类
	        logger.info("获取信用卡信息");
	        // 信用资料信息集合（set到客户基本信息对象中用于页面显示）
	        List<LoanCreditInfo> loanCreditInfoList = null;
	        loanCreditInfoList = loanCreditInfoDao.findListByLoanCode(loanCode);
	        if(ObjectHelper.isEmpty(loanCreditInfoList)){
	            LoanCreditInfo creditInfo = new LoanCreditInfo();
	            loanCreditInfoList = new LinkedList<LoanCreditInfo>();  
	            loanCreditInfoList.add(creditInfo);
	        }
	        applyInFoFlagEx.setLoanCreditInfoList(loanCreditInfoList);
	    }else if (ApplyInfoConstant.NEW_APPLY_INFO_HOUSE.equals(flag)) { //新页签常量已经新建，请查看常量类
	        logger.info("获取房产信息");
	        // 主借人房产资料集合（set到客户基本信息对象里用于页面显示）
	        List<LoanHouse> customerLoanHouseList = null;
	        customerLoanHouseList = loanHouseDao.findListByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isEmpty(customerLoanHouseList)){ 
	        	LoanHouse loanHouse = new LoanHouse(); 
	        	customerLoanHouseList = new LinkedList<LoanHouse>(); 
	        	LoanCustomer customer = loanCustomerDao.selectByConsultId(consultId); 
	        	if (!ObjectHelper.isEmpty(customer)){ 
		        	loanHouse.setHouseProvince(customer.getCustomerLiveProvince()); 
		        	loanHouse.setHouseCity(customer.getCustomerLiveCity()); 
		        	loanHouse.setHouseArea(customer.getCustomerLiveArea()); 
		        	loanHouse.setHouseAddress(customer.getCustomerAddress()); 
	        	} 
	        	customerLoanHouseList.add(loanHouse); 
        	}
	        applyInFoFlagEx.setCustomerLoanHouseList(customerLoanHouseList);
		} else if (ApplyInfoConstant.NEW_APPLY_INFO_CONTACT.equals(flag)) { // 新页签常量已经新建，请查看常量类
			logger.info("获取联系人信息");
			// 主借人客户联系人信息（set到客户基本信息对象中用于页面显示）
			List<Contact> customerContactList = null;
			customerContactList = contactDao.findListByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
			if (ObjectHelper.isEmpty(customerContactList)) {
				customerContactList = new ArrayList<Contact>();
			}
			//联系人信息解密
			EncryptUtils.decryptMulti(customerContactList);
			
			for (Contact contact : customerContactList) {
				
				if (RelationType.FAMILY_CONTACTS.getCode().equals(contact.getRelationType())) {
					applyInFoFlagEx.getRelationContactList().add(contact);
				}
				if (RelationType.WORK_VOUCHER.getCode().equals(contact.getRelationType())) {
					applyInFoFlagEx.getWorkProveContactList().add(contact);
				}
				if (RelationType.OTHER_CONTACTS.getCode().equals(contact.getRelationType())) {
					applyInFoFlagEx.getOtherContactList().add(contact);
				}
			}

			LoanCustomer loanCustomer = null;
			loanCustomer = loanCustomerDao.selectByConsultId(initParam.get("consultId"));

			// 已婚执行保存
			if (loanCustomer != null && "1".equals(loanCustomer.getDictMarry())) {
				LoanCompany mateLoanCompany = loanCompanyDao.selectByLoanCode(loanCode, LoanManFlag.MATE.getCode());
				LoanMate loanMate = loanMateDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
				if (loanMate == null) {
					loanMate = new LoanMate();
				}
				if (loanMate.getMateAddressProvince() == null && loanCustomer != null) {
					loanMate.setMateAddressProvince(loanCustomer.getCustomerLiveProvince());
					loanMate.setMateAddressCity(loanCustomer.getCustomerLiveCity());
					loanMate.setMateAddressArea(loanCustomer.getCustomerLiveArea());
					loanMate.setMateAddress(loanCustomer.getCustomerAddress());
				}
				if (!ObjectHelper.isEmpty(loanMate) && !ObjectHelper.isEmpty(mateLoanCompany)) {
					loanMate.setMateLoanCompany(mateLoanCompany);
				}
				//配偶信息解密
				EncryptUtils.decrypt(loanMate);
				applyInFoFlagEx.setLoanMate(loanMate);
			}

			Map<String, Object> loanCodeMap = new HashMap<String, Object>();
			loanCodeMap.put("loanCode", loanCode);

			applyInFoFlagEx.setLoanCustomer(loanCustomer);
			applyInFoFlagEx.setCustomerContactList(customerContactList);

		} else if (ApplyInfoConstant.NEW_APPLY_INFO_BANK.equals(flag)) {
	        logger.info("获取银行卡信息");
	        // 借款人账户信息
	        LoanBank loanBank = null; 
	        Consult consult = consultDao.get(consultId);
	        loanBank = loanBankDao.selectByLoanCode(loanCode);
	        if(ObjectHelper.isEmpty(loanBank)){
	            loanBank = new LoanBank(); 
	         }
	        if(!ObjectHelper.isEmpty(consult)){
	            if(ObjectHelper.isEmpty(loanBank.getBankProvince())){
	                loanBank.setBankProvince(consult.getBankProvince());
	            }
	            if(ObjectHelper.isEmpty(loanBank.getBankCity())){
                    loanBank.setBankCity(consult.getBankCity());
                }
	            if(ObjectHelper.isEmpty(loanBank.getBankName())){
                    loanBank.setBankName(consult.getAccountBank());
                }
	            if(ObjectHelper.isEmpty(loanBank.getBankBranch())){
                    loanBank.setBankBranch(consult.getBranch());
                }
	            if(ObjectHelper.isEmpty(loanBank.getBankAccount())){
                    loanBank.setBankAccount(consult.getAccountId());
                }
	        }
	        applyInFoFlagEx.setLoanBank(loanBank);
	        //获取客户信息
	        if(StringUtils.isNotEmpty(loanCode)){
	        	HashMap<String, Object> para=new HashMap<String, Object>();
	        	para.put("loanCode", loanCode);
	        	LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(para);
	        	applyInFoFlagEx.setLoanCustomer(loanCustomer);
	        }
	    }else if(ApplyInfoConstant.NEW_APPLY_INFO_MANAGER.equals(flag)){
	    	logger.info("获取经营信息");
	        // 主借人客户经营信息（set到客户基本信息对象中用于页面显示）
	    	LoanCompManage loanCompManage = null;
	    	loanCompManage = loanCompManageDao.findCompManageByLoanCode(loanCode);
//	    	if(!ObjectHelper.isEmpty(loanCompManage.getAverageMonthTurnover())){
//	    		BigDecimal averageMonthTurnover = loanCompManage.getAverageMonthTurnover().divide(new BigDecimal(10000));
//	    		loanCompManage.setAverageMonthTurnover(averageMonthTurnover);
//	    	}
//	    	if(!ObjectHelper.isEmpty(loanCompManage.getCompRegisterCapital())){
//	    		BigDecimal compRegisterCapital = loanCompManage.getCompRegisterCapital().divide(new BigDecimal(10000));
//	    		loanCompManage.setCompRegisterCapital(compRegisterCapital);
//	    	}
	    	
    	 	LoanInfo loanInfo = null;
	        loanInfo = loanInfoDao.selectByLoanCode(param);
	        if(ObjectHelper.isEmpty(loanInfo)){
	            loanInfo = new LoanInfo();
	        }
	        if(ObjectHelper.isNotEmpty(loanCompManage)){
	        	//经营信息解密
	        	EncryptUtils.decrypt(loanCompManage);
	        }
	        applyInFoFlagEx.setLoanInfo(loanInfo);
	    	applyInFoFlagEx.setLoanCompManage(loanCompManage);
	    }else if(ApplyInfoConstant.NEW_APPLY_INFO_CERTIFICATE.equals(flag)){
	    	logger.info("获取证件信息");
	    	// 主借人客户证件信息（set到客户基本信息对象中用于页面显示）
	        LoanPersonalCertificate loanPersonalCertificate = null;
	        LoanCustomer loancustomer = loanCustomerDao.selectByConsultId(consultId);
	        loanPersonalCertificate = loanPersonalCertificateDao.findByLoanCode(loanCode);
	        if (null == loanPersonalCertificate && (!ObjectHelper.isEmpty(loancustomer))){
	        	loanPersonalCertificate = new LoanPersonalCertificate();
	        	loanPersonalCertificate.setCustomerName(loancustomer.getCustomerName());
	        	loanPersonalCertificate.setCustomerCertNum(loancustomer.getCustomerCertNum());
	        	loanPersonalCertificate.setMasterAddressProvince(loancustomer.getCustomerLiveProvince());
	        	loanPersonalCertificate.setMasterAddressCity(loancustomer.getCustomerLiveCity());
	        	loanPersonalCertificate.setMasterAddressArea(loancustomer.getCustomerLiveArea());
	        	loanPersonalCertificate.setMasterAddress(loancustomer.getCustomerAddress());
	        	loanPersonalCertificate.setDictMarry(loancustomer.getDictMarry());
	        }
	        applyInFoFlagEx.setLoanCustomer(loancustomer);
	        applyInFoFlagEx.setLoanPersonalCertificate(loanPersonalCertificate);
	    }
		//查询借么标识
	    Consult con = consultDao.get(consultId);
	    if(!ObjectHelper.isEmpty(con.getIsBorrow())){
	    	applyInFoFlagEx.setIsBorrow(con.getIsBorrow());
	    }
	    
    	return applyInFoFlagEx;
	}

	/**
	 * 设置借款意愿
	 * By 任志远 2016年10月20日
	 *
	 * @param loanInfo
	 * @param consultId
	 * @return
	 */
	public LoanInfo queryLoanInfo(LoanInfo loanInfo, String consultId) {

		if (ObjectHelper.isEmpty(loanInfo)) {
			loanInfo = new LoanInfo();
		} else {
			if (ObjectHelper.isEmpty(loanInfo.getLoanApplyTime())) {
				loanInfo.setLoanApplyTime(new Date());
			}
		}
		Consult consult = consultDao.get(consultId);
		if (!ObjectHelper.isEmpty(consult)) {
			if (ObjectHelper.isEmpty(loanInfo.getLoanApplyAmount())) {
				loanInfo.setLoanApplyAmount(consult.getLoanApplyMoney());
			}
			if (ObjectHelper.isEmpty(loanInfo.getDictLoanUse())) {
				loanInfo.setDictLoanUse(consult.getDictLoanUse());
				if (ObjectHelper.isEmpty(loanInfo.getDictLoanUseNewOther())) {
					loanInfo.setDictLoanUseNewOther(consult.getDictLoanUseRemark());
				}
			}
			if (ObjectHelper.isEmpty(loanInfo.getLoanMonths())) {
				loanInfo.setLoanMonths(consult.getLoanMonth());
			}
			if (ObjectHelper.isEmpty(loanInfo.getProductType())) {
				loanInfo.setProductType(consult.getProductCode());
			}
			loanInfo.setLoanManagerCode(consult.getManagerCode());
			loanInfo.setIsBorrow(consult.getIsBorrow());
			loanInfo.setConsTelesalesFlag(consult.getConsTelesalesFlag());
		}
		return loanInfo;
	}
	
	/**
	 * 查询自然人保证人关联的借款意愿，公司信息，联系人信息
	 * @param loanCoborrowerList	自然人保证人集合
	 * @param loanCode				借款编码
	 * @return
	 */
	public List<LoanCoborrower> queryNaturalGuarantor(List<LoanCoborrower> loanCoborrowerList, String loanCode){
		//查询条件
        Map<String,Object> queryParam = new HashMap<String,Object>();
        if(!ObjectHelper.isEmpty(loanCoborrowerList)){
        	
        	//自然人保证人解密
    		EncryptUtils.decryptMulti(loanCoborrowerList);
        	
        	for(LoanCoborrower coborrow:loanCoborrowerList){
        		queryParam.put("loanCode", loanCode);
        		queryParam.put("relateId", coborrow.getId());
        		queryParam.put("customerType",LoanManFlag.COBORROWE_LOAN.getCode());
        		
        		//自然人保证人借款意愿
        		LoanInfoCoborrower loanInfoCoborrower =  loanInfoCoborrowerDao.queryLoanInfoCoborrowerByRid(queryParam);
        		coborrow.setLoanInfoCoborrower(loanInfoCoborrower == null ? new LoanInfoCoborrower() : loanInfoCoborrower);
        		//自然人保证人公司信息
        		LoanCompany coboCompany = loanCompanyDao.findByParam(queryParam);
        		coborrow.setCoboCompany(coboCompany == null ? new LoanCompany() : coboCompany);
        		//自然人保证人联系人
        		List<Contact> coborrowerContactList = contactDao.findListByLinkId(loanCode, LoanManFlag.COBORROWE_LOAN.getCode(), coborrow.getId());
        		//把联系人按类型分开，方便前台迭代
        		if(coborrowerContactList != null){
        			//自然人保证人联系人解密
    				EncryptUtils.decryptMulti(coborrowerContactList);
    				
        			coborrow.setCoborrowerContactList(coborrowerContactList);
        			for(Contact contact : coborrowerContactList){
        				
        				if(RelationType.FAMILY_CONTACTS.getCode().equals(contact.getRelationType())){
        					coborrow.getRelativesContactList().add(contact);
        				}
        				if(RelationType.WORK_VOUCHER.getCode().equals(contact.getRelationType())){
        					coborrow.getWorkTogetherContactList().add(contact);
        				}
        				if(RelationType.OTHER_CONTACTS.getCode().equals(contact.getRelationType())){
        					coborrow.getOtherContactList().add(contact);
        				}
        			}
        		}else{
        			coborrow.setCoborrowerContactList(new ArrayList<Contact>());
        		}
        	}
        }else{
        	loanCoborrowerList = new LinkedList<LoanCoborrower>();
        	LoanCoborrower loanCoborrowr = new LoanCoborrower();
        	List<Contact> coborrowerContactList = new LinkedList<Contact>();
            coborrowerContactList.add(new Contact());
            loanCoborrowr.setCoborrowerContactList(coborrowerContactList);
            loanCoborrowerList.add(loanCoborrowr);
        }
        
        return loanCoborrowerList;
	}

	/**
	 * 保存客户信息 
	 * 2015年12月3日 
	 * By 张平
	 * @param applyInfoFlagEx
	 * @param insert
	 * @return none
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveApplyInfo(ApplyInfoFlagEx applyInfoFlagEx,boolean insert) throws Exception {
	    String loanCode = null;
	    String flag = applyInfoFlagEx.getFlag();
	    LoanInfo loanInfo = applyInfoFlagEx.getLoanInfo();
		if (insert) {
		      Map<String,String> param = new HashMap<String,String>();
		      param.put("consultId", applyInfoFlagEx.getConsultId());
		      LoanInfo preLoanInfo = loanInfoDao.selectByConsultId(param);
		      if(!ObjectHelper.isEmpty(preLoanInfo)){
		          throw new Exception("当前客户信息正在录入，请勿重复操作");
		      }
		      loanInfo.preInsert();
		      loanInfo.setRid(applyInfoFlagEx.getConsultId());
		      loanInfo.setLoanInfoOldOrNewFlag(ApplyInfoConstant.LOANINFO_OLDORNEW_FLAG_OLD);
			  loanInfoDao.insertLoanInfo(loanInfo);
			  applyInfoFlagEx.setLoanInfo(loanInfo);
		}
		loanCode = loanInfo.getLoanCode();  
		logger.info("当前页签类型： "+flag);
		if (ApplyInfoConstant.APPLY_INFO_CUSTOMER.equals(flag)) {
		    LoanCustomer loanCustomer = applyInfoFlagEx.getLoanCustomer();
		    loanCustomer.setCustomerName(StringEscapeUtils.unescapeHtml4(loanCustomer.getCustomerName()));
		    CustomerLivings customerLivings = applyInfoFlagEx.getCustomerLivings();
			if (StringUtils.isBlank(loanCustomer.getId())) {
			    loanCustomer.setLoanCode(loanCode);
			    loanCustomer.preInsert();
			    loanCustomerDao.insert(loanCustomer);
			} else {
			    if(StringUtils.isEmpty(loanCustomer.getLoanCode())){
			        loanCustomer.setLoanCode(loanCode); 
			    }
			    loanCustomer.preUpdate();
				loanCustomerDao.update(loanCustomer);
			}
			if (StringUtils.isBlank(customerLivings.getId())) {
			    customerLivings.setLoanCode(loanCode);
			    customerLivings.preInsert();
			    customerLivings.setRcustomerCoborrowerId(loanCustomer.getId());
			    customerLivings.setLoanCustomerType(LoanManFlag.MAIN_LOAN.getCode());
				customerLivingsDao.insert(customerLivings);
			} else {
			    customerLivings.preUpdate();
			    customerLivings.setLoanCode(loanCode);
				customerLivingsDao.update(customerLivings);
			}
			applyInfoFlagEx.setLoanCustomer(loanCustomer);
			applyInfoFlagEx.setCustomerLivings(customerLivings);
			
			//如果先保存的其他页签然后才保存的主借人信息，，则同步主借人的id到联系人表、公司表、房产表中的关联id（根据loan_code和关联类型同步）
			HashMap<String, Object> params=new HashMap<String, Object>();
			params.put("loanCode", loanCode);
			params.put("customerType", LoanManFlag.MAIN_LOAN.getCode());
			params.put("loanCustomerId", loanCustomer.getId());
			SyncRid(params);
		} else if (ApplyInfoConstant.APPLY_INFO_MATE.equals(flag)) {
		    LoanMate loanMate = applyInfoFlagEx.getLoanMate();
		    loanMate.setMateName(StringEscapeUtils.unescapeHtml4(loanMate.getMateName()));
			if (StringUtils.isBlank(loanMate.getId())) {
			    loanMate.setLoanCode(loanCode);
			    loanMate.preInsert();
			    loanMate.setRcustomerCoborrowerId(applyInfoFlagEx.getLoanCustomer().getId());
				// 设置关联类型（测试）
			    loanMate.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
				loanMateDao.insert(loanMate);
			} else {
			    if(StringUtils.isEmpty(loanMate.getLoanCode())){
			      loanMate.setLoanCode(loanCode);
			    }
			    loanMate.preUpdate();
				loanMateDao.update(loanMate);
			}
			LoanCompany  mateLoanCompany = applyInfoFlagEx.getLoanMate().getMateLoanCompany();
			if (StringUtils.isBlank(mateLoanCompany.getId())) {
			    mateLoanCompany.setLoanCode(loanCode);
			    mateLoanCompany.preInsert();
			    mateLoanCompany.setRid(loanMate.getId());
			    mateLoanCompany.setDictrCustomterType(LoanManFlag.MATE.getCode());
				loanCompanyDao.insert(mateLoanCompany);
			} else {
			    if(StringUtils.isEmpty(mateLoanCompany.getLoanCode())){
			        mateLoanCompany.setLoanCode(loanCode);
			    }
			    mateLoanCompany.preUpdate();
				loanCompanyDao.update(mateLoanCompany);
			}
			loanMate.setMateLoanCompany(mateLoanCompany);
			applyInfoFlagEx.setLoanMate(loanMate);
		} else if (ApplyInfoConstant.APPLY_INFO_LOANINFO.equals(flag)) {
		    LoanInfo curLoan = applyInfoFlagEx.getLoanInfo();
		    String consultId = applyInfoFlagEx.getConsultId();
		    Consult consult = consultDao.get(consultId);
		    OrgCache orgCache = OrgCache.getInstance();
		    User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
            curLoan.setLoanCustomerService(user.getId());
           
		    // 如果是电销人员操作则取值为咨询阶段的门店ID，否则取当前登录者的门店ID
		    if(YESNO.YES.getCode().equals(consult.getConsTelesalesFlag())){
		        String storeOrgId = consult.getStoreOrgid();
		        Org storeOrg = orgCache.get(storeOrgId);
		        curLoan.setLoanStoreOrgId(storeOrgId);
		        curLoan.setConsTelesalesOrgcode(consult.getTeleSalesOrgid());//电销组织机构编码
                if(!ObjectHelper.isEmpty(storeOrg)){
                    curLoan.setStoreCode(storeOrg.getStoreCode());
                }
		    }else{
		        Org storeOrg = orgCache.get(user.getDepartment().getId());
		        curLoan.setLoanStoreOrgId(user.getDepartment().getId());
		        if(!ObjectHelper.isEmpty(storeOrg)){
		            curLoan.setStoreCode(storeOrg.getStoreCode());
		        }
		     }
		    if(!ObjectHelper.isEmpty(consult)){
		         curLoan.setLoanManagerCode(consult.getManagerCode());
		         curLoan.setLoanTeamManagerCode(consult.getLoanTeamEmpcode());  
		         curLoan.setLoanTeamOrgId(consult.getLoanTeamOrgId());
            }
		       curLoan.setDictSourceType(SystemFromFlag.THREE.getCode());  // 更新来源系统标识
		       curLoan.setDictClassType(LoanType.HONOUR_LOAN.getCode());   // 更新借款类型 ：信借
		       curLoan.setLoanFlag(ChannelFlag.CHP.getCode());
		       curLoan.setModel(LoanModel.CHP.getCode());
		       curLoan.preUpdate();
			loanInfoDao.updateLoanInfo(curLoan);
			LoanRemark loanRemark = applyInfoFlagEx.getLoanRemark();
			if(!ObjectHelper.isEmpty(loanRemark)){
			    loanRemark.setLoanCode(curLoan.getLoanCode());
			    if(StringUtils.isNotEmpty(loanRemark.getId())){
			        loanRemark.preUpdate();
			        loanRemark.setRemarkTime(loanRemark.getModifyTime());
			        loanRemarkDao.updateByIdSelective(loanRemark);
			    }else{
			        loanRemark.preInsert();
			        loanRemark.setRemarkTime(loanRemark.getModifyTime());
			        loanRemark.setDictRemarkType(RemarkType.LoanFlag.getCode());
			        loanRemarkDao.insertRemark(loanRemark);
			    }
			    applyInfoFlagEx.setLoanRemark(loanRemark);
			}
			applyInfoFlagEx.setLoanInfo(curLoan);
			
			//保存共借人信息
		} else if (ApplyInfoConstant.APPLY_INFO_COBORROWER.equals(flag)) {
			//共借人基本信息
			     List<LoanCoborrower> borrows = applyInfoFlagEx.getLoanCoborrower();
			     
			     if(!ObjectHelper.isEmpty(borrows)){
			         Contact contact = null;
			         List<Contact> contacts = null;
			         LoanCoborrower coborrow = null;
			         List<Contact> returnContacts = null;
			         LoanCoborrower retCoborrow = new LoanCoborrower();
			         List<LoanCoborrower> retBorrows = new ArrayList<LoanCoborrower>();
			         for(int i=0; i<borrows.size(); i++){
			            coborrow = borrows.get(i);
			            coborrow.setCoboName(StringEscapeUtils.unescapeHtml4(coborrow.getCoboName()));
			            contacts = coborrow.getCoborrowerContactList();
			            returnContacts = new ArrayList<Contact>();
			            // 如果共借人都为空，那么共借人的联系人必定是空
			            if(StringUtils.isBlank(coborrow.getId())){
			               coborrow.setLoanCode(loanCode);
			               coborrow.preInsert();
			               loanCoborrowerDao.insert(coborrow);
			               if(!ObjectHelper.isEmpty(contacts)){
			                for(int j = 0 ;j<contacts.size();j++){
			                     contact = contacts.get(j); 
			                     contact.setContactName(StringEscapeUtils.unescapeHtml4(contact.getContactName()));
			                     contact.setLoanCode(loanCode);
  	                             contact.preInsert();
			                     contact.setRcustomerCoborrowerId(coborrow.getId());
			                     contact.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
			                     contactDao.insert(contact);
			                     returnContacts.add(contact);
			                  }
			                retCoborrow = coborrow;
			                retCoborrow.setCoborrowerContactList(returnContacts);
			                }
			               retBorrows.add(retCoborrow);
			            }else{
			               if(StringUtils.isEmpty(coborrow.getLoanCode())){
			                   coborrow.setLoanCode(loanCode);
			               }
			               coborrow.preUpdate();
		                   loanCoborrowerDao.update(coborrow); 
		                   if(contacts != null){
	                            for(int j = 0 ; j<contacts.size(); j++){
	                               contact = contacts.get(j); 
	                               contact.setContactName(StringEscapeUtils.unescapeHtml4(contact.getContactName())) ;
	                               if (StringUtils.isBlank(contact.getId())) {
	                                  contact.setLoanCode(loanCode);
	                                  contact.preInsert();
	                                  contact.setRcustomerCoborrowerId(coborrow.getId());
	                                  contact.setLoanCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
	                                  contactDao.insert(contact);
	                                } else {
	                                        if(StringUtils.isEmpty(contact.getLoanCode())){
	                                            contact.setLoanCode(loanCode);   
	                                        }
	                                        contact.preUpdate();
	                                        contactDao.update(contact);
	                                }
	                               returnContacts.add(contact);
	                             }
	                            retCoborrow = coborrow;
	                            retCoborrow.setCoborrowerContactList(returnContacts);
	                        }
		                }
			            LoanCompany coboCompany = coborrow.getCoboCompany(); 
			            if(coboCompany!=null ){
			                if(StringUtils.isEmpty(coboCompany.getId())){
				                coboCompany.preInsert();
				                coboCompany.setLoanCode(loanCode);
				                coboCompany.setDictrCustomterType(LoanManFlag.COBORROWE_LOAN.getCode());
				                coboCompany.setRid(coborrow.getId());
				                loanCompanyDao.insert(coboCompany);
				            }else{
				                coboCompany.preUpdate();
				                loanCompanyDao.update(coboCompany);
				            }
				            
			            	
			            }
			            
			      
			            
			            //客户居住情况信息
			            CustomerLivings coboLivings = coborrow.getCoboLivings();
			            
			            if(coboLivings!=null){
			            	   if(StringUtils.isEmpty(coboLivings.getId())){
					                coboLivings.preInsert();
					                coboLivings.setLoanCode(loanCode);
					                coboLivings.setLoanCustomerType(LoanManFlag.COBORROWE_LOAN.getCode());
					                coboLivings.setRcustomerCoborrowerId(coborrow.getId());
		                            customerLivingsDao.insert(coboLivings);
		                        }else{
		                            coboLivings.preUpdate();
		                            customerLivingsDao.update(coboLivings);
		                        }
			            }
			            
			         
			            retCoborrow.setCoboCompany(coboCompany);
			            retCoborrow.setCoboLivings(coboLivings);
			            retBorrows.add(retCoborrow);
			        }
			         applyInfoFlagEx.setLoanCoborrower(retBorrows);
			     }
		} else if (ApplyInfoConstant.APPLY_INFO_CREDITINFO.equals(flag)) {
		      List<LoanCreditInfo> creditList = new ArrayList<LoanCreditInfo>();
			for (LoanCreditInfo loanCreditInfo : applyInfoFlagEx
					.getLoanCreditInfoList()) {
				if (StringUtils.isBlank(loanCreditInfo.getId())) {
				    loanCreditInfo.setLoanCode(loanCode);
					loanCreditInfo.preInsert();
					loanCreditInfoDao.insert(loanCreditInfo);
				} else {
				    if(StringUtils.isEmpty(loanCreditInfo.getLoanCode())){
				        loanCreditInfo.setLoanCode(loanCode);
				    }
					loanCreditInfo.preUpdate();
					loanCreditInfoDao.update(loanCreditInfo);
				}
				creditList.add(loanCreditInfo);
			}
			applyInfoFlagEx.setLoanCreditInfoList(creditList);
			
		} else if (ApplyInfoConstant.APPLY_INFO_COMPANY.equals(flag)) {
			LoanCompany loanCompany = applyInfoFlagEx.getLoanCompany();
			synchronized (this) {
				LoanCompany loanComp = loanCompanyDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
				if(loanComp==null){
					loanCompany.setLoanCode(loanCode);
				    loanCompany.preInsert();
				    loanCompany.setDictrCustomterType(LoanManFlag.MAIN_LOAN.getCode());
				    loanCompany.setRid(applyInfoFlagEx.getLoanCustomer().getId());
					loanCompanyDao.insert(loanCompany);
				}else{
					if(StringUtils.isEmpty(loanCompany.getLoanCode())){
				        loanCompany.setLoanCode(loanCode);
				    }
				    loanCompany.preUpdate();
					loanCompanyDao.update(loanCompany);
				}
			}
			/*  ****此块代码重写，查询LoanCompany对象必须从数据库中查,注意并发****
		        LoanCompany loanCompany = applyInfoFlagEx.getLoanCompany();
			if (StringUtils.isBlank(loanCompany.getId())) {
			    loanCompany.setLoanCode(loanCode);
			    loanCompany.preInsert();
			    loanCompany.setDictrCustomterType(LoanManFlag.MAIN_LOAN.getCode());
			    loanCompany.setRid(applyInfoFlagEx.getLoanCustomer().getId());
				loanCompanyDao.insert(loanCompany);
			} else {
			    if(StringUtils.isEmpty(loanCompany.getLoanCode())){
			        loanCompany.setLoanCode(loanCode);
			    }
			    loanCompany.preUpdate();
				loanCompanyDao.update(loanCompany);
			}
			*/
			applyInfoFlagEx.setLoanCompany(loanCompany);
			
		} else if (ApplyInfoConstant.APPLY_INFO_HOUSE.equals(flag)) {
		    List<LoanHouse> houses = new ArrayList<LoanHouse>();
			for (LoanHouse loanHouse : applyInfoFlagEx
					.getCustomerLoanHouseList()) {
				if (StringUtils.isBlank(loanHouse.getId())) {
				    loanHouse.setLoanCode(loanCode);
					loanHouse.preInsert();
					loanHouse.setRcustomerCoborrowerId(applyInfoFlagEx
					        .getLoanCustomer().getId());
					loanHouse.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
					loanHouseDao.insert(loanHouse);
				} else {
				    if(StringUtils.isEmpty(loanHouse.getLoanCode())){
				        loanHouse.setLoanCode(loanCode);
				    }
					loanHouse.preUpdate();
					loanHouseDao.update(loanHouse);
				}
				houses.add(loanHouse);
			}
			applyInfoFlagEx.setCustomerLoanHouseList(houses);
			
		} else if (ApplyInfoConstant.APPLY_INFO_CONTACT.equals(flag)) {
		    List<Contact> contacts = new ArrayList<Contact>();
			for (Contact contact : applyInfoFlagEx.getCustomerContactList()) {
			    contact.setContactName(StringEscapeUtils.unescapeHtml4(contact.getContactName()));
				if (StringUtils.isBlank(contact.getId())) {
				    contact.setLoanCode(loanCode);
					contact.preInsert();
					contact.setRcustomerCoborrowerId(applyInfoFlagEx
					        .getLoanCustomer().getId());
					contact.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
					contactDao.insert(contact);
				} else {
				    if(StringUtils.isEmpty(contact.getLoanCode())){
				        contact.setLoanCode(loanCode);
				    }
					contact.preUpdate();
					contactDao.update(contact);
				}
				contacts.add(contact);
			}
			applyInfoFlagEx.setCustomerContactList(contacts);
			
		} else if (ApplyInfoConstant.APPLY_INFO_BANK.equals(flag)) {
		    LoanBank loanBank = applyInfoFlagEx.getLoanBank();
		    if(ObjectHelper.isEmpty(loanBank.getBankTopFlag())){
		        loanBank.setBankTopFlag(BANK_TOP_FLAG);
		    }
		    if(ObjectHelper.isEmpty(loanBank.getBankIsRareword())){
		        loanBank.setBankIsRareword(0);
		    }
		    loanBank.setBankAccountName(StringEscapeUtils.unescapeHtml4(loanBank.getBankAccountName()));
		    loanBank.setBankAuthorizer(StringEscapeUtils.unescapeHtml4(loanBank.getBankAuthorizer())) ;
			if (StringUtils.isBlank(loanBank.getId())) {
			    loanBank.setLoanCode(loanCode);
			    loanBank.setBankNo(OpenBankKLCode.getOpenBankByKL(loanBank.getBankName()));
			    loanBank.preInsert();
			    logger.info("当前借款编号： "+loanCode+" ，银行ID： "+loanBank.getId()+"  ,执行时间："+System.currentTimeMillis());
				loanBankDao.insert(loanBank);
			} else {
			    if(StringUtils.isEmpty(loanBank.getLoanCode())){
			        loanBank.setLoanCode(loanCode);
			    }
			    loanBank.setBankNo(OpenBankKLCode.getOpenBankByKL(loanBank.getBankName()));
			    loanBank.preUpdate();
				loanBankDao.update(loanBank);
			}
			applyInfoFlagEx.setLoanBank(loanBank);
		}
	}
	
	/**
	 * 如果先保存的其他页签然后才保存的主借人信息，，则同步主借人的id到联系人表、公司表、房产表中的关联id（根据loan_code和关联类型同步）
	 */
	private void SyncRid(HashMap<String, Object> params) {
		loanInfoDao.syncRid(params);
	}

	/**
	 * 适用于新版申请表
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void saveApplyInfo_new(ApplyInfoFlagEx applyInfoFlagEx,boolean insert) throws Exception {
	    String loanCode = null;
	    String flag = applyInfoFlagEx.getFlag();
	    LoanInfo loanInfo = applyInfoFlagEx.getLoanInfo();
		if (insert) {
		      Map<String,String> param = new HashMap<String,String>();
		      param.put("consultId", applyInfoFlagEx.getConsultId());
		      LoanInfo preLoanInfo = loanInfoDao.selectByConsultId(param);
		      if(!ObjectHelper.isEmpty(preLoanInfo)){
		          throw new Exception("当前客户信息正在录入，请勿重复操作");
		      }
		      loanInfo.preInsert();
		      loanInfo.setRid(applyInfoFlagEx.getConsultId());
		      loanInfo.setLoanInfoOldOrNewFlag(ApplyInfoConstant.LOANINFO_OLDORNEW_FLAG_NEW);
		      if(ApplyInfoConstant.NEW_APPLY_INFO_LOANINFO.equals(flag)){
		    	  //主要借款用途
		    	  String dictLoanUse=loanInfo.getDictLoanUse();
				  if(!"12".equals(dictLoanUse)){ //12表示其他
					  loanInfo.setDictLoanUseNewOther("");
				  }
				  //主要还款来源
				  String dictLoanSource=loanInfo.getDictLoanSource();
				  if(!"7".equals(dictLoanSource)){ //7表示其他
					  loanInfo.setDictLoanSourceOther("");
				  }
				  //其他收入来源
				  String dictLoanSourceElse=loanInfo.getDictLoanSourceElse();
				  if(dictLoanSourceElse!=null){
					  //其他收入来源选择其他时的备注字段
					  if(dictLoanSourceElse.indexOf("5")==-1){
						  loanInfo.setDictLoanSourceElseOther("");
					  }
				  }else{
					  loanInfo.setDictLoanSourceElse("");
					  loanInfo.setDictLoanSourceElseOther("");
				  }
				//其他月收入
				if(loanInfo.getOtherMonthIncome()==null){
					loanInfo.setOtherMonthIncome(new BigDecimal(0));
				}
				//同业在还借款总笔数
				if(loanInfo.getOtherCompanyPaybackCount().equals("")){
					loanInfo.setOtherCompanyPaybackCount("0");
				}
				//月还款总额
				if(loanInfo.getOtherCompanyPaybackTotalmoney()==null){
					loanInfo.setOtherCompanyPaybackTotalmoney(new BigDecimal(0));
				}
		      }
		      loanInfo.setCustomerIntoTime(new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01"));
			  loanInfoDao.insertLoanInfo(loanInfo);
			  applyInfoFlagEx.setLoanInfo(loanInfo);
		}
		loanCode = loanInfo.getLoanCode();  
		logger.info("当前页签类型： "+flag);
		if (ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER.equals(flag)) {
		    LoanCustomer loanCustomer = applyInfoFlagEx.getLoanCustomer();
		    loanCustomer.setCustomerName(StringEscapeUtils.unescapeHtml4(loanCustomer.getCustomerName()));
		    //主借人信息加密
		    EncryptUtils.encrypt(loanCustomer);
		    CustomerLivings customerLivings = applyInfoFlagEx.getCustomerLivings();
			if (StringUtils.isBlank(loanCustomer.getId())) {
			    loanCustomer.setLoanCode(loanCode);
			    loanCustomer.preInsert();
			    //由何处了解到我公司字段
			    String[] dictCustomerSourceNewStr=loanCustomer.getDictCustomerSourceNewStr();
			    if(dictCustomerSourceNewStr!=null){
			    	String temp="";
			    	for (String dictCustomerSourceNew : dictCustomerSourceNewStr) {
			    		temp+=dictCustomerSourceNew+",";
			    	}
			    	loanCustomer.setDictCustomerSourceNew(temp);
			    	//由何处了解到我公司选择其他时的备注字段
			    	if(temp.indexOf("6")==-1){
			    		loanCustomer.setDictCustomerSourceNewOther("");
			    	}
			    }
			    loanCustomerDao.insert(loanCustomer);
			} else {
			    if(StringUtils.isEmpty(loanCustomer.getLoanCode())){
			        loanCustomer.setLoanCode(loanCode);
			    }
			    loanCustomer.preUpdate();
			    //由何处了解到我公司字段
			    String[] dictCustomerSourceNewStr=loanCustomer.getDictCustomerSourceNewStr();
			    if(dictCustomerSourceNewStr!=null){
			    	String temp="";
			    	for (String dictCustomerSourceNew : dictCustomerSourceNewStr) {
			    		temp+=dictCustomerSourceNew+",";
			    	}
			    	loanCustomer.setDictCustomerSourceNew(temp);
			    	//由何处了解到我公司选择其他时的备注字段
			    	if(temp.indexOf("6")==-1){
			    		loanCustomer.setDictCustomerSourceNewOther("");
			    	}
			    }
				loanCustomerDao.update(loanCustomer);
			}
			if (StringUtils.isBlank(customerLivings.getId())) {
			    customerLivings.setLoanCode(loanCode);
			    customerLivings.preInsert();
			    customerLivings.setRcustomerCoborrowerId(loanCustomer.getId());
			    customerLivings.setLoanCustomerType(LoanManFlag.MAIN_LOAN.getCode());
			    //住宅类别
			    String customerHouseHoldProperty=customerLivings.getCustomerHouseHoldProperty();
			    if(!"7".equals(customerHouseHoldProperty)){ //7表示其他
			    	customerLivings.setCustomerHouseHoldPropertyNewOther("");
			    }
				customerLivingsDao.insert(customerLivings);
			} else {
			    customerLivings.preUpdate();
			    customerLivings.setLoanCode(loanCode);
			    //住宅类别
			    String customerHouseHoldProperty=customerLivings.getCustomerHouseHoldProperty();
			    if(!"7".equals(customerHouseHoldProperty)){ //7表示其他
			    	customerLivings.setCustomerHouseHoldPropertyNewOther("");
			    }
				customerLivingsDao.update(customerLivings);
			}
			applyInfoFlagEx.setLoanCustomer(loanCustomer);
			applyInfoFlagEx.setCustomerLivings(customerLivings);

			//如果先保存的其他页签然后才保存的主借人信息，，则同步主借人的id到联系人表、公司表、房产表中的关联id（根据loan_code和关联类型同步）
			HashMap<String, Object> params=new HashMap<String, Object>();
			params.put("loanCode", loanCode);
			params.put("customerType", LoanManFlag.MAIN_LOAN.getCode());
			params.put("loanCustomerId", loanCustomer.getId());
			SyncRid(params);
		} else if (ApplyInfoConstant.NEW_APPLY_INFO_LOANINFO.equals(flag)) {
			LoanInfo curLoan = applyInfoFlagEx.getLoanInfo();
			String consultId = applyInfoFlagEx.getConsultId();
			Consult consult = consultDao.get(consultId);
			OrgCache orgCache = OrgCache.getInstance();
			User user = (User) UserUtils.getSession().getAttribute(SystemConfigConstant.SESSION_USER_INFO);
			curLoan.setLoanCustomerService(user.getId());

			// 如果是电销人员操作则取值为咨询阶段的门店ID，否则取当前登录者的门店ID
			if (YESNO.YES.getCode().equals(consult.getConsTelesalesFlag())) {
				String storeOrgId = consult.getStoreOrgid();
				Org storeOrg = orgCache.get(storeOrgId);
				curLoan.setLoanStoreOrgId(storeOrgId);
				curLoan.setConsTelesalesOrgcode(consult.getTeleSalesOrgid());//电销组织机构编码
				if (!ObjectHelper.isEmpty(storeOrg)) {
					curLoan.setStoreCode(storeOrg.getStoreCode());
				}
			} else {
				Org storeOrg = orgCache.get(user.getDepartment().getId());
				curLoan.setLoanStoreOrgId(user.getDepartment().getId());
				if (!ObjectHelper.isEmpty(storeOrg)) {
					curLoan.setStoreCode(storeOrg.getStoreCode());
				}
			}
			if (!ObjectHelper.isEmpty(consult)) {
				curLoan.setLoanManagerCode(consult.getManagerCode());
				curLoan.setLoanTeamManagerCode(consult.getLoanTeamEmpcode());
				curLoan.setLoanTeamOrgId(consult.getLoanTeamOrgId());
			}
			curLoan.setDictSourceType(SystemFromFlag.THREE.getCode());  // 更新来源系统标识
			curLoan.setDictClassType(LoanType.HONOUR_LOAN.getCode());   // 更新借款类型 ：信借
			if(!LoanProductCode.PRO_NONG_XIN_JIE.equals(curLoan.getProductType())){
				curLoan.setLoanFlag(ChannelFlag.CHP.getCode());
			}
			curLoan.setModel(LoanModel.CHP.getCode());
			curLoan.preUpdate();
			//主要借款用途
			String dictLoanUse = curLoan.getDictLoanUse();
			if (!"12".equals(dictLoanUse)) { //12表示其他
				curLoan.setDictLoanUseNewOther("");
			}
			//主要还款来源
			String dictLoanSource = curLoan.getDictLoanSource();
			if (!"7".equals(dictLoanSource)) { //7表示其他
				curLoan.setDictLoanSourceOther("");
			}
			//其他收入来源
			String dictLoanSourceElse = curLoan.getDictLoanSourceElse();
			if (dictLoanSourceElse != null) {
				//其他收入来源选择其他时的备注字段
				if (dictLoanSourceElse.indexOf("5") == -1) {
					curLoan.setDictLoanSourceElseOther("");
				}
			} else {
				curLoan.setDictLoanSourceElse("");
				curLoan.setDictLoanSourceElseOther("");
			}
			//其他月收入
			if (curLoan.getOtherMonthIncome() == null) {
				curLoan.setOtherMonthIncome(new BigDecimal(0));
			}
			//同业在还借款总笔数
			if (curLoan.getOtherCompanyPaybackCount().equals("")) {
				curLoan.setOtherCompanyPaybackCount("0");
			}
			//月还款总额
			if (curLoan.getOtherCompanyPaybackTotalmoney() == null) {
				curLoan.setOtherCompanyPaybackTotalmoney(new BigDecimal(0));
			}
			loanInfoDao.updateLoanInfo(curLoan);
			LoanRemark loanRemark = applyInfoFlagEx.getLoanRemark();
			if (!ObjectHelper.isEmpty(loanRemark)) {
				loanRemark.setLoanCode(curLoan.getLoanCode());
				if (StringUtils.isNotEmpty(loanRemark.getId())) {
					loanRemark.preUpdate();
					loanRemark.setRemarkTime(loanRemark.getModifyTime());
					loanRemarkDao.updateByIdSelective(loanRemark);
				} else {
					loanRemark.preInsert();
					loanRemark.setRemarkTime(loanRemark.getModifyTime());
					loanRemark.setDictRemarkType(RemarkType.LoanFlag.getCode());
					loanRemarkDao.insertRemark(loanRemark);
				}
				applyInfoFlagEx.setLoanRemark(loanRemark);
			}
			applyInfoFlagEx.setLoanInfo(curLoan);
			
		}else if (ApplyInfoConstant.NEW_APPLY_INFO_COMPANY.equals(flag)) {
			//保存公司信息
			this.saveLoanCompany(applyInfoFlagEx.getLoanCompany(), loanCode, applyInfoFlagEx.getLoanCustomer().getId(), LoanManFlag.MAIN_LOAN);
		} else if (ApplyInfoConstant.NATURAL_GUARANTOR.equals(flag)) {//保存共借人信息
			
			List<LoanCoborrower> borrows = applyInfoFlagEx.getLoanCoborrower();
			if(!ObjectHelper.isEmpty(borrows)){
				//自然人保证人加密
				EncryptUtils.encryptMulti(borrows);
				for(LoanCoborrower coborrow : borrows){
					//保存自然人保证人 基本信息
					coborrow.setCoboName(StringEscapeUtils.unescapeHtml4(coborrow.getCoboName()));
					coborrow = this.saveLoanCoborrower(coborrow, loanCode);
					//保存借款意愿
					this.saveLoanInfoCoborrower(coborrow.getLoanInfoCoborrower(), loanCode, coborrow.getId());
					//保存公司信息
					this.saveLoanCompany(coborrow.getCoboCompany(), loanCode, coborrow.getId(), LoanManFlag.COBORROWE_LOAN);
					//保存联系人信息
					this.saveContact(coborrow.getCoborrowerContactList(), loanCode, coborrow.getId(), LoanManFlag.COBORROWE_LOAN);
				}
			}
		} else if (ApplyInfoConstant.NEW_APPLY_INFO_HOUSE.equals(flag)) { //新页签常量已经新建，请查看常量类
		    List<LoanHouse> houses = new ArrayList<LoanHouse>();
			for (LoanHouse loanHouse : applyInfoFlagEx.getCustomerLoanHouseList()) {
				if (StringUtils.isBlank(loanHouse.getId())) {
				    loanHouse.setLoanCode(loanCode);
					loanHouse.preInsert();
					loanHouse.setRcustomerCoborrowerId(applyInfoFlagEx.getLoanCustomer().getId());
					loanHouse.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
					loanHouseDao.insert(loanHouse);
				} else {
				    if(StringUtils.isEmpty(loanHouse.getLoanCode())){
				        loanHouse.setLoanCode(loanCode);
				    }
				    
				    //解决Sql 字段=null 时, 不修改的问题
				    loanHouse.setHouseAmount(loanHouse.getHouseAmount() == null ? new BigDecimal(0) : loanHouse.getHouseAmount());
				    loanHouse.setHouseLoanAmount(loanHouse.getHouseLoanAmount() == null ? new BigDecimal(0) : loanHouse.getHouseLoanAmount());
				    loanHouse.setHouseLessAmount(loanHouse.getHouseLessAmount() == null ? new BigDecimal(0) : loanHouse.getHouseLessAmount());
				    loanHouse.setHouseMonthRepayAmount(loanHouse.getHouseMonthRepayAmount() == null ? new BigDecimal(0) : loanHouse.getHouseMonthRepayAmount());
				    loanHouse.setHouseLoanYear(loanHouse.getHouseLoanYear() == null ? new BigDecimal(0) : loanHouse.getHouseLoanYear());
				    
					loanHouse.preUpdate();
					loanHouseDao.update(loanHouse);
				}
				houses.add(loanHouse);
			}
			applyInfoFlagEx.setCustomerLoanHouseList(houses);
			
		} else if (ApplyInfoConstant.NEW_APPLY_INFO_CONTACT.equals(flag)) { 
			
			//保存以上可知晓本次借款的联系人 （存主借人表里）
			LoanCustomer loanCustomer = applyInfoFlagEx.getLoanCustomer();
			
			if (StringUtils.isBlank(loanCustomer.getId())) {
				loanCustomer.setWhoCanKnowBorrow(applyInfoFlagEx.getLoanCustomer().getWhoCanKnowBorrow());
				loanCustomer.setWhoCanKnowTheBorrowingRemark(applyInfoFlagEx.getLoanCustomer().getWhoCanKnowTheBorrowingRemark());
			    loanCustomer.setLoanCode(loanCode);
			    loanCustomer.preInsert();
			    loanCustomerDao.insert(loanCustomer);
			} else {
			    if(StringUtils.isEmpty(loanCustomer.getLoanCode())){
			        loanCustomer.setLoanCode(loanCode); 
			    }
			    loanCustomer.setWhoCanKnowBorrow(applyInfoFlagEx.getLoanCustomer().getWhoCanKnowBorrow());
				loanCustomer.setWhoCanKnowTheBorrowingRemark(applyInfoFlagEx.getLoanCustomer().getWhoCanKnowTheBorrowingRemark());
			    loanCustomer.preUpdate();
				loanCustomerDao.updateWhoCanKnowBorrow(loanCustomer);
			}
			applyInfoFlagEx.setLoanCustomer(loanCustomer);
			
			//联系人信息加密
		    EncryptUtils.encryptMulti(applyInfoFlagEx.getCustomerContactList());
			//保存联系人
			for (Contact contact : applyInfoFlagEx.getCustomerContactList()) {
			    contact.setContactName(StringEscapeUtils.unescapeHtml4(contact.getContactName()));
			    
			    if (StringUtils.isBlank(contact.getId())) {
				    contact.setLoanCode(loanCode);
					contact.preInsert();
					contact.setRcustomerCoborrowerId(applyInfoFlagEx.getLoanCustomer().getId());
					contact.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
					contactDao.insert(contact);
				} else {
				    if(StringUtils.isEmpty(contact.getLoanCode())){
				        contact.setLoanCode(loanCode);
				    }
					contact.preUpdate();
					contactDao.update(contact);
				}
			}
			
			//查询数据库以保存主借人数据
			Map<String,Object> loanCodeMap= new HashMap<String,Object>();
			loanCodeMap.put("loanCode", loanCode);
			LoanCustomer loanCustomerSaved = loanCustomerDao.selectByLoanCode(loanCodeMap);
			
			//保存配偶
			if(loanCustomerSaved != null && "1".equals(loanCustomerSaved.getDictMarry())){
				LoanMate loanMate = applyInfoFlagEx.getLoanMate();
				//配偶信息加密
				EncryptUtils.encrypt(loanMate);
				if(StringUtils.isBlank(loanMate.getId())){
					loanMate.setLoanCode(loanCode);
				    loanMate.preInsert();
				    loanMate.setRcustomerCoborrowerId(applyInfoFlagEx.getLoanCustomer().getId());
					// 设置关联类型（测试）
				    loanMate.setLoanCustomterType(LoanManFlag.MAIN_LOAN.getCode());
					loanMateDao.insert(loanMate);
				}else{
					if(StringUtils.isEmpty(loanMate.getLoanCode())){
					      loanMate.setLoanCode(loanCode);
					}
					loanMate.preUpdate();
					loanMateDao.update(loanMate);
				}
				
				//保存配偶单位名称
				LoanCompany  mateLoanCompany = applyInfoFlagEx.getLoanMate().getMateLoanCompany();
				if (StringUtils.isBlank(mateLoanCompany.getId())) {
				    mateLoanCompany.setLoanCode(loanCode);
				    mateLoanCompany.preInsert();
				    mateLoanCompany.setRid(loanMate.getId());
				    mateLoanCompany.setDictrCustomterType(LoanManFlag.MATE.getCode());
					loanCompanyDao.insert(mateLoanCompany);
				} else {
				    if(StringUtils.isEmpty(mateLoanCompany.getLoanCode())){
				        mateLoanCompany.setLoanCode(loanCode);
				    }
				    mateLoanCompany.preUpdate();
					loanCompanyDao.update(mateLoanCompany);
				}
				loanMate.setMateLoanCompany(mateLoanCompany);
				applyInfoFlagEx.setLoanMate(loanMate);
			}
			
		}else if(ApplyInfoConstant.NEW_APPLY_INFO_MANAGER.equals(flag)){
//			if(!ObjectHelper.isEmpty(applyInfoFlagEx.getLoanCompManage().getAverageMonthTurnover())){
//				BigDecimal averageMonthTurnover=applyInfoFlagEx.getLoanCompManage().getAverageMonthTurnover().multiply(new BigDecimal(10000));
//				applyInfoFlagEx.getLoanCompManage().setAverageMonthTurnover(averageMonthTurnover);
//			}
//			if(!ObjectHelper.isEmpty(applyInfoFlagEx.getLoanCompManage().getCompRegisterCapital())){
//				BigDecimal compRegisterCapital=applyInfoFlagEx.getLoanCompManage().getCompRegisterCapital().multiply(new BigDecimal(10000));
//				applyInfoFlagEx.getLoanCompManage().setCompRegisterCapital(compRegisterCapital);
//			}
			
			LoanCompManage loanCompManage = applyInfoFlagEx.getLoanCompManage();
			EncryptUtils.encrypt(loanCompManage);
			if (StringUtils.isBlank(loanCompManage.getId())) {
				loanCompManage.setLoanCode(loanCode);
				loanCompManage.preInsert();
				loanCompManageDao.insert(loanCompManage);
			} else {
			    if(StringUtils.isEmpty(loanCompManage.getLoanCode())){
			    	loanCompManage.setLoanCode(loanCode);
			    }
			    loanCompManage.preUpdate();
			    loanCompManageDao.update(loanCompManage);
			}			
			
		}else if(ApplyInfoConstant.NEW_APPLY_INFO_CERTIFICATE.equals(flag)){
			LoanPersonalCertificate loanPersonalCertificate = applyInfoFlagEx.getLoanPersonalCertificate();
			if (StringUtils.isBlank(loanPersonalCertificate.getId())) {
				loanPersonalCertificate.setLoanCode(loanCode);
				loanPersonalCertificate.preInsert();
				loanPersonalCertificateDao.insert(loanPersonalCertificate);
			} else {
			    if(StringUtils.isEmpty(loanPersonalCertificate.getLoanCode())){
			    	loanPersonalCertificate.setLoanCode(loanCode);
			    }
			    loanPersonalCertificate.preUpdate();
			    loanPersonalCertificateDao.update(loanPersonalCertificate);
			}
		}
		else if (ApplyInfoConstant.NEW_APPLY_INFO_BANK.equals(flag)) { 
		    LoanBank loanBank = applyInfoFlagEx.getLoanBank();
		    if(ObjectHelper.isEmpty(loanBank.getBankTopFlag())){
		        loanBank.setBankTopFlag(BANK_TOP_FLAG);
		    }
		    if(ObjectHelper.isEmpty(loanBank.getBankIsRareword())){
		        loanBank.setBankIsRareword(0);
		    }
		    loanBank.setBankAccountName(StringEscapeUtils.unescapeHtml4(loanBank.getBankAccountName()));
		    loanBank.setBankAuthorizer(StringEscapeUtils.unescapeHtml4(loanBank.getBankAuthorizer())) ;
			if (StringUtils.isBlank(loanBank.getId())) {
			    loanBank.setLoanCode(loanCode);
			    loanBank.setBankNo(OpenBankKLCode.getOpenBankByKL(loanBank.getBankName()));
			    loanBank.preInsert();
			    logger.info("当前借款编号： "+loanCode+" ，银行ID： "+loanBank.getId()+"  ,执行时间："+System.currentTimeMillis());
				loanBankDao.insert(loanBank);
			} else {
			    if(StringUtils.isEmpty(loanBank.getLoanCode())){
			        loanBank.setLoanCode(loanCode);
			    }
			    loanBank.setBankNo(OpenBankKLCode.getOpenBankByKL(loanBank.getBankName()));
			    loanBank.preUpdate();
				loanBankDao.update(loanBank);
			}
			applyInfoFlagEx.setLoanBank(loanBank);
		}
	}
	
	/**
	 * 保存自然人保证人基本信息
	 * @param loanCoborrower
	 * @param loanCode
	 * @return
	 */
	public LoanCoborrower saveLoanCoborrower(LoanCoborrower loanCoborrower, String loanCode){
		
		if(StringUtils.isBlank(loanCoborrower.getId())){
			loanCoborrower.setLoanCode(loanCode);
			loanCoborrower.preInsert();
			loanCoborrowerDao.insert(loanCoborrower);
		}else{
			if(StringUtils.isEmpty(loanCoborrower.getLoanCode())){
				loanCoborrower.setLoanCode(loanCode);
			}
			loanCoborrower.preUpdate();
			loanCoborrowerDao.update(loanCoborrower);
		}
		
		return loanCoborrower;
	}
	/**
	 * 保存自然人保证人 借款意愿
	 * @param loanInfoCoborrower
	 * @param loanCode
	 * @return
	 */
	public LoanInfoCoborrower saveLoanInfoCoborrower(LoanInfoCoborrower loanInfoCoborrower, String loanCode, String rid){
		
		if(StringUtils.isBlank(loanInfoCoborrower.getId())){
			loanInfoCoborrower.preInsert();
			loanInfoCoborrower.setLoanCode(loanCode);
			loanInfoCoborrower.setRid(rid);
			loanInfoCoborrowerDao.insert(loanInfoCoborrower);
		}else{
			if(StringUtils.isEmpty(loanInfoCoborrower.getLoanCode())){
				loanInfoCoborrower.setLoanCode(loanCode);
			}
			loanInfoCoborrower.preUpdate();
			loanInfoCoborrowerDao.update(loanInfoCoborrower);
		}
		
		return loanInfoCoborrower;
	}
	
	/**
	 * 保存自然人保证人 联系人信息
	 * @param contacts	联系人集合
	 * @param loanCode	借款编号
	 * @return
	 */
	public List<Contact> saveContact(List<Contact> contacts, String loanCode, String rid, LoanManFlag loanManFlag){
		
		if(!ObjectHelper.isEmpty(contacts)){

			//联系人信息加密
			EncryptUtils.encryptMulti(contacts);
			
			for(Contact contact: contacts){
				contact.setContactName(StringEscapeUtils.unescapeHtml4(contact.getContactName())) ;
				
				if (StringUtils.isBlank(contact.getId())) {
					contact.setLoanCode(loanCode);
					contact.preInsert();
					contact.setRcustomerCoborrowerId(rid);
					contact.setLoanCustomterType(loanManFlag.getCode());
					contactDao.insert(contact);
				} else {
					if(StringUtils.isEmpty(contact.getLoanCode())){
						contact.setLoanCode(loanCode);   
					}
					contact.preUpdate();
					contactDao.update(contact);
				}
			}
		}
		
		return contacts;
	}
	
	/**
	 * 保存自然人保证人 公司信息
	 * @param loanCompany
	 * @param loanCode
	 * @return
	 */
	public LoanCompany saveLoanCompany(LoanCompany loanCompany, String loanCode, String rid, LoanManFlag loanManFlag){
		if(loanCompany == null){
			loanCompany = new LoanCompany();
		}
		if(StringUtils.isEmpty(loanCompany.getId())){
			loanCompany.preInsert();
			loanCompany.setLoanCode(loanCode);
			loanCompany.setDictrCustomterType(loanManFlag.getCode());
			loanCompany.setRid(rid);
			loanCompanyDao.insert(loanCompany);
		}else{
			if(StringUtils.isEmpty(loanCompany.getLoanCode())){
		        loanCompany.setLoanCode(loanCode);
		    }
			//解决Sql 字段=null 时, 不修改的问题
			loanCompany.setCompSalary(loanCompany.getCompSalary() == null ? new BigDecimal(0) : loanCompany.getCompSalary());
			
			loanCompany.preUpdate();
			loanCompanyDao.update(loanCompany);
		}
		
		return loanCompany;
	}
	
	/**
	 * 获取客户资料以及其附属资料信息 
	 * 2015年12月3日 
	 * By 张平
	 * @param applyId
	 * @return ApplyInfoFlagEx对象数据
	 * @throws ParseException 
	 */
	public ApplyInfoFlagEx getAllInfo(String applyId)   {
	    ApplyInfoFlagEx applyInFoFlagEx = new ApplyInfoFlagEx();
	    Map<String,Object> param = new HashMap<String,Object>();
	    String loanCode = null;
	        LoanCustomer loanCustomer = null;
	        // 获取主借人客户居住情况
	        CustomerLivings customerLivings = null;
		    // 获取上一次借款客户信息
	        loanCustomer = loanCustomerDao.selectByApplyId(applyId);
	        //以上代码注释，直接改成-1，可咨询任志远
	        applyInFoFlagEx.setOneedition("-1");
	      
	       //获得当前咨询版本时间
		    if(!ObjectHelper.isEmpty(loanCustomer)){
		    	loanCode = loanCustomer.getLoanCode();
		        param.put("loanCode", loanCode);
	            String tempLoanCode = loanCustomer.getLoanCode();
                // 获取主借人的住房信息
               customerLivings = customerLivingsDao.selectByLoanCode(tempLoanCode,
                LoanManFlag.MAIN_LOAN.getCode());
               if(ObjectHelper.isEmpty(customerLivings)){
                   customerLivings = new CustomerLivings();  
               }
            
	        }else {
                loanCustomer = new LoanCustomer();  
                customerLivings = new CustomerLivings();  
	        }
		    applyInFoFlagEx.setLoanCustomer(loanCustomer);
		    applyInFoFlagEx.setCustomerLivings(customerLivings);
		    // 获取借款信息备注信息
            List<LoanRemark> loanRemark = null;
            param.put("dictRemarkType", RemarkType.LoanFlag.getCode());
            loanRemark = loanRemarkDao.findByLoanCode(param);
            if (loanRemark.size() != 0) {
            	applyInFoFlagEx.setLoanRemark(loanRemark.get(0));				
			}
	        // 获取主借人客户配偶信息
	        LoanMate loanMate = null;
	        // 获取主借人配偶信息的公司职业信息（set到主借人客户配偶信息对象中用于页面显示）
	        LoanCompany mateLoanCompany = null;
	        loanMate = loanMateDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
	        mateLoanCompany = loanCompanyDao.selectByLoanCode(loanCode,LoanManFlag.MATE.getCode());
	        if(ObjectHelper.isEmpty(loanMate)){
	            loanMate = new LoanMate();
	        }
	        if(ObjectHelper.isEmpty(mateLoanCompany)){
	            mateLoanCompany = new LoanCompany();
	        }
	        loanMate.setMateLoanCompany(mateLoanCompany);
	        applyInFoFlagEx.setLoanMate(loanMate); 
	  
	        LoanInfo loanInfo = null;
	        loanInfo = loanInfoDao.selectByLoanCode(param);
	        if(ObjectHelper.isEmpty(loanInfo)){
	            loanInfo = new LoanInfo();
	        }
	        applyInFoFlagEx.setLoanInfo(loanInfo);
	  
	         // 共同借款人信息
	        List<LoanCoborrower> loanCoborrower = null; 
	        String coborrowId = null;
	        // 共同借款人联系人信息(set到共同借款人对象中，用于返回页面显示)
	        List<Contact> coborrowerContactList = null;
	
	        loanCoborrower = loanCoborrowerDao.selectByLoanCode(loanCode);
	        // 获取共借人的住房信息
	        CustomerLivings coboLivings =null;
	        LoanCompany coboCompany = null;
	        Map<String,Object> queryParam = new HashMap<String,Object>();
	        if(!ObjectHelper.isEmpty(loanCoborrower)){
	           for(LoanCoborrower coborrow:loanCoborrower){
	              coborrowId = coborrow.getId();
	              queryParam.put("loanCode", loanCode);
                  queryParam.put("relateId", coborrowId);
                  queryParam.put("customerType", LoanManFlag.COBORROWE_LOAN.getCode());
	             
                  coborrowerContactList = contactDao.findListByLinkId(loanCode,
	                LoanManFlag.COBORROWE_LOAN.getCode(),coborrowId);
	              
	              coboLivings = customerLivingsDao.findByParam(queryParam);
	              
	              coboCompany = loanCompanyDao.findByParam(queryParam);
	              
	              if(ObjectHelper.isEmpty(loanCoborrower)){
	                  coborrowerContactList = new LinkedList<Contact>(); 
	                  coborrowerContactList.add(new Contact());
	              }
	              if(ObjectHelper.isEmpty(coboLivings)){
	                  coboLivings = new CustomerLivings(); 
                  }
	              if(ObjectHelper.isEmpty(coboCompany)){
	                  coboCompany = new LoanCompany(); 
                  }
	               coborrow.setCoboCompany(coboCompany);
	               coborrow.setCoboLivings(coboLivings);
	               coborrow.setCoborrowerContactList(coborrowerContactList);
	           }
	        }else{
	            coborrowerContactList = new LinkedList<Contact>();
	            coborrowerContactList.add(new Contact());
	            loanCoborrower = new LinkedList<LoanCoborrower>();
	            LoanCoborrower loanCoborrow = new LoanCoborrower();
	            loanCoborrow.setCoborrowerContactList(coborrowerContactList);
	            loanCoborrower.add(loanCoborrow);
	        }
	        applyInFoFlagEx.setLoanCoborrower(loanCoborrower);
	        
	        // 信用资料信息集合（set到客户基本信息对象中用于页面显示）
	        List<LoanCreditInfo> loanCreditInfoList = null;
	        loanCreditInfoList = loanCreditInfoDao.findListByLoanCode(loanCode);
	        if(ObjectHelper.isEmpty(loanCreditInfoList)){
	            LoanCreditInfo creditInfo = new LoanCreditInfo();
	            loanCreditInfoList = new LinkedList<LoanCreditInfo>();  
	            loanCreditInfoList.add(creditInfo);
	        }
	        applyInFoFlagEx.setLoanCreditInfoList(loanCreditInfoList);
	 	    // 主借人职业信息/公司资料（set到客户基本信息对象里用于页面显示）
	        LoanCompany customerLoanCompany = null; 
	        customerLoanCompany = loanCompanyDao.selectByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isEmpty(customerLoanCompany)){
	            customerLoanCompany = new LoanCompany(); 
	        }
	        applyInFoFlagEx.setCustomerLoanCompany(customerLoanCompany);
	  	    // 主借人房产资料集合（set到客户基本信息对象里用于页面显示）
	        List<LoanHouse> customerLoanHouseList = null;
	        customerLoanHouseList = loanHouseDao.findListByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isEmpty(customerLoanHouseList)){
	            LoanHouse loanHouse = new LoanHouse();
	            customerLoanHouseList = new LinkedList<LoanHouse>();
	            customerLoanHouseList.add(loanHouse);
	        }
	        applyInFoFlagEx.setCustomerLoanHouseList(customerLoanHouseList);
	        // 主借人客户联系人信息（set到客户基本信息对象中用于页面显示）
	        List<Contact> customerContactList = null;
	        customerContactList = contactDao.findListByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isEmpty(customerContactList)){
	            Contact contact = new Contact();
	            customerContactList = new LinkedList<Contact>();
	            customerContactList.add(contact);
	        }
	        applyInFoFlagEx.setCustomerContactList(customerContactList);
	        // 借款人账户信息
	        LoanBank loanBank = null; 
	        loanBank = loanBankDao.selectByLoanCode(loanCode);
	        if(ObjectHelper.isEmpty(loanBank)){
	            loanBank = new LoanBank(); 
	         }
	        applyInFoFlagEx.setLoanBank(loanBank);
           //
	        
    	return applyInFoFlagEx;
	}
	/**
	 * 2015年12月3日 此方法适用于旧版申请表，新版申请表请参见deleteItem_new方法
	 * By 张平
	 * @param delType
	 * @param tagId
	 * @return none
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void deleteItem(String delType,String tagId){
	   Map<String,Object> param = new HashMap<String,Object>();
	   param.put("id", tagId);
	   if("CONTACT".equals(delType)){
	      contactDao.deleteByCondition(param);
	   }else if("CREDIT".equals(delType)){
	      loanCreditInfoDao.deleteByCondition(param);
	   }else if("HOUSE".equals(delType)){
	      loanHouseDao.deleteByCondition(param);
	   }else if("COBORROWER".equals(delType)){
	       param.put("rid", tagId);
	       contactDao.deleteByRid(param);
	       loanCoborrowerDao.deleteById(param);
	   }else if("MATE".equals(delType)){
	       param.put("rid", tagId);
	       loanCompanyDao.deleteByRid(param);
	       loanMateDao.deleteById(param);
	   }
	}
	/**
	 * 此方法适用于新版申请表
	 */
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public void deleteItem_new(String delType,String tagId){
	   Map<String,Object> param = new HashMap<String,Object>();
	   param.put("id", tagId);
	   if("CONTACT".equals(delType)){
	      contactDao.deleteByCondition(param);
	   }else if("CREDIT".equals(delType)){
	      loanCreditInfoDao.deleteByCondition(param);
	   }else if("HOUSE".equals(delType)){
	      loanHouseDao.deleteByCondition(param);
	   }else if("COBORROWER".equals(delType)){
	       param.put("rid", tagId);
	       contactDao.deleteByRid(param);
	       loanCompanyDao.deleteByRid(param);
	       loanInfoCoborrowerDao.deleteByRid(param);
	       loanCoborrowerDao.deleteById(param);
	   }else if("MATE".equals(delType)){
	       param.put("rid", tagId);
	       loanCompanyDao.deleteByRid(param);
	       loanMateDao.deleteById(param);
	   }
	}
	
	/**
	 * 获取客户资料以及其附属资料信息 此方法适用于旧版申请表，新版申请表请见getAllInfoByLoanCode_new方法
	 * 2015年12月3日 
	 * By 张平
	 * @param loanCode
	 * @return ApplyInfoFlagEx数据对象
	 */
    public ApplyInfoFlagEx getAllInfoByLoanCode(String loanCode) {
        ApplyInfoFlagEx applyInFoFlagEx = new ApplyInfoFlagEx();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("loanCode", loanCode);
            LoanCustomer loanCustomer = null;
            // 获取主借人客户居住情况
            CustomerLivings customerLivings = null;
            // 获取上一次借款客户信息
            loanCustomer = loanCustomerDao.selectByLoanCode(param);
            
            if(!ObjectHelper.isEmpty(loanCustomer)){
               loanCode = loanCustomer.getLoanCode();
               String tempLoanCode = loanCustomer.getLoanCode();
                // 获取主借人的住房信息
               customerLivings = customerLivingsDao.selectByLoanCode(tempLoanCode,
                LoanManFlag.MAIN_LOAN.getCode());
               if(ObjectHelper.isEmpty(customerLivings)){
                   customerLivings = new CustomerLivings();  
               }
            }else {
                loanCustomer = new LoanCustomer();  
                customerLivings = new CustomerLivings();  
            }
            applyInFoFlagEx.setLoanCustomer(loanCustomer);
            applyInFoFlagEx.setCustomerLivings(customerLivings);
            // 获取借款信息备注信息
            List<LoanRemark> loanRemark = null;
            param.put("dictRemarkType", RemarkType.LoanFlag.getCode());
            loanRemark = loanRemarkDao.findByLoanCode(param);
            if (loanRemark.size() != 0) {
            	applyInFoFlagEx.setLoanRemark(loanRemark.get(0));				
			}
            // 获取主借人客户配偶信息
            LoanMate loanMate = null;
            // 获取主借人配偶信息的公司职业信息（set到主借人客户配偶信息对象中用于页面显示）
            LoanCompany mateLoanCompany = null;
            loanMate = loanMateDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
            mateLoanCompany = loanCompanyDao.selectByLoanCode(loanCode,LoanManFlag.MATE.getCode());
            if(ObjectHelper.isEmpty(loanMate)){
                loanMate = new LoanMate();
            }
            if(ObjectHelper.isEmpty(mateLoanCompany)){
                mateLoanCompany = new LoanCompany();
            }
            loanMate.setMateLoanCompany(mateLoanCompany);
            applyInFoFlagEx.setLoanMate(loanMate); 
      
            LoanInfo loanInfo = null;
            loanInfo = loanInfoDao.selectByLoanCode(param);
            if(ObjectHelper.isEmpty(loanInfo)){
                loanInfo = new LoanInfo();
            }
            applyInFoFlagEx.setLoanInfo(loanInfo);
      
            // 共同借款人信息
            List<LoanCoborrower> loanCoborrower = null; 
            String coborrowId = null;
            // 共同借款人联系人信息(set到共同借款人对象中，用于返回页面显示)
            List<Contact> coborrowerContactList = null;
    
            loanCoborrower = loanCoborrowerDao.selectByLoanCode(loanCode);
            // 获取共借人的住房信息
            CustomerLivings coboLivings =null;
            LoanCompany coboCompany = null;
            Map<String,Object> queryParam = new HashMap<String,Object>();
            if(!ObjectHelper.isEmpty(loanCoborrower)){
               for(LoanCoborrower coborrow:loanCoborrower){
                  coborrowId = coborrow.getId();
                  queryParam.put("loanCode", loanCode);
                  queryParam.put("relateId", coborrowId);
                  queryParam.put("customerType", LoanManFlag.COBORROWE_LOAN.getCode());
                 
                  coborrowerContactList = contactDao.findListByLinkId(loanCode,
                  LoanManFlag.COBORROWE_LOAN.getCode(),coborrowId);
                  
                  coboLivings = customerLivingsDao.findByParam(queryParam);
                  
                  coboCompany = loanCompanyDao.findByParam(queryParam);
                  
                  if(ObjectHelper.isEmpty(loanCoborrower)){
                      coborrowerContactList = new LinkedList<Contact>(); 
                      coborrowerContactList.add(new Contact());
                  }
                  if(ObjectHelper.isEmpty(coboLivings)){
                      coboLivings = new CustomerLivings(); 
                  }
                  if(ObjectHelper.isEmpty(coboCompany)){
                      coboCompany = new LoanCompany(); 
                  }
                   coborrow.setCoboCompany(coboCompany);
                   coborrow.setCoboLivings(coboLivings);
                   coborrow.setCoborrowerContactList(coborrowerContactList);
               }
            }else{
                coborrowerContactList = new LinkedList<Contact>();
                coborrowerContactList.add(new Contact());
                loanCoborrower = new LinkedList<LoanCoborrower>();
                LoanCoborrower loanCoborrow = new LoanCoborrower();
                loanCoborrow.setCoborrowerContactList(coborrowerContactList);
                loanCoborrower.add(loanCoborrow);
            }
            applyInFoFlagEx.setLoanCoborrower(loanCoborrower);
            
            // 信用资料信息集合（set到客户基本信息对象中用于页面显示）
            List<LoanCreditInfo> loanCreditInfoList = null;
            loanCreditInfoList = loanCreditInfoDao.findListByLoanCode(loanCode);
            if(ObjectHelper.isEmpty(loanCreditInfoList)){
                LoanCreditInfo creditInfo = new LoanCreditInfo();
                loanCreditInfoList = new LinkedList<LoanCreditInfo>();  
                loanCreditInfoList.add(creditInfo);
            }
            applyInFoFlagEx.setLoanCreditInfoList(loanCreditInfoList);
            // 主借人职业信息/公司资料（set到客户基本信息对象里用于页面显示）
            LoanCompany customerLoanCompany = null; 
            customerLoanCompany = loanCompanyDao.selectByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
            if(ObjectHelper.isEmpty(customerLoanCompany)){
                customerLoanCompany = new LoanCompany(); 
            }
            applyInFoFlagEx.setCustomerLoanCompany(customerLoanCompany);
            // 主借人房产资料集合（set到客户基本信息对象里用于页面显示）
            List<LoanHouse> customerLoanHouseList = null;
            customerLoanHouseList = loanHouseDao.findListByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
            if(ObjectHelper.isEmpty(customerLoanHouseList)){
                LoanHouse loanHouse = new LoanHouse();
                customerLoanHouseList = new LinkedList<LoanHouse>();
                customerLoanHouseList.add(loanHouse);
            }
            applyInFoFlagEx.setCustomerLoanHouseList(customerLoanHouseList);
            // 主借人客户联系人信息（set到客户基本信息对象中用于页面显示）
            List<Contact> customerContactList = null;
            customerContactList = contactDao.findListByLoanCode(loanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
            if(ObjectHelper.isEmpty(customerContactList)){
                Contact contact = new Contact();
                customerContactList = new LinkedList<Contact>();
                customerContactList.add(contact);
            }
            applyInFoFlagEx.setCustomerContactList(customerContactList);
            // 借款人账户信息
            LoanBank loanBank = null; 
            loanBank = loanBankDao.selectByLoanCode(loanCode);
            if(ObjectHelper.isEmpty(loanBank)){
                loanBank = new LoanBank(); 
             }
            applyInFoFlagEx.setLoanBank(loanBank);
        
        return applyInFoFlagEx;
    }
    /**
     * 此方法适用于新版申请表
     */
    public ApplyInfoFlagEx getAllInfoByLoanCode_new(String loanCode) {
    	ApplyInfoFlagEx applyInFoFlagEx = new ApplyInfoFlagEx();
    	Map<String,Object> param = new HashMap<String,Object>();
    	param.put("loanCode", loanCode);
		// 主借人信息
		LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
		// 获取主借人客户居住情况
		CustomerLivings customerLivings = null;
		if (!ObjectHelper.isEmpty(loanCustomer)) {
			loanCode = loanCustomer.getLoanCode();
			// 获取主借人的住房信息
			customerLivings = customerLivingsDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
		}
		applyInFoFlagEx.setLoanCustomer(ObjectHelper.isEmpty(loanCustomer) ? new LoanCustomer() : loanCustomer);
		applyInFoFlagEx.setCustomerLivings(ObjectHelper.isEmpty(customerLivings) ? new CustomerLivings() : customerLivings);
		// 获取借款信息备注信息
		param.put("dictRemarkType", RemarkType.LoanFlag.getCode());
		List<LoanRemark> loanRemark = loanRemarkDao.findByLoanCode(param);
		if (loanRemark.size() != 0) {
			applyInFoFlagEx.setLoanRemark(loanRemark.get(0));
		}

		// 主借人配偶信息
		LoanMate loanMate = loanMateDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());

		if(!ObjectHelper.isEmpty(loanMate)){
			// 主借人配偶信息的公司职业信息（set到主借人客户配偶信息对象中用于页面显示）
			LoanCompany mateLoanCompany = loanCompanyDao.selectByLoanCode(loanCode, LoanManFlag.MATE.getCode());
			loanMate.setMateLoanCompany(ObjectHelper.isEmpty(mateLoanCompany) ? new LoanCompany() : mateLoanCompany);
		}
		
		applyInFoFlagEx.setLoanMate(ObjectHelper.isEmpty(loanMate) ? new LoanMate() : loanMate);

		//主借人借款意愿
		LoanInfo loanInfo = loanInfoDao.selectByLoanCode(param);
		if (ObjectHelper.isEmpty(loanInfo)) {
			loanInfo = new LoanInfo();
		}
		applyInFoFlagEx.setLoanInfo(loanInfo);
		
		// 主借人工作信息
		LoanCompany customerLoanCompany = loanCompanyDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
		applyInFoFlagEx.setCustomerLoanCompany(ObjectHelper.isEmpty(customerLoanCompany) ?new LoanCompany() : customerLoanCompany);
		
		// 主借人联系人信息
		List<Contact> customerContactList = contactDao.findListByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
		if (ObjectHelper.isEmpty(customerContactList)) {
			Contact contact = new Contact();
			customerContactList = new LinkedList<Contact>();
			customerContactList.add(contact);
		}
		applyInFoFlagEx.setCustomerContactList(customerContactList);
		
		// 自然人保证人信息
		List<LoanCoborrower> loanCoborrowerList = loanCoborrowerDao.selectByLoanCode(loanCode);
		loanCoborrowerList = this.queryNaturalGuarantor(loanCoborrowerList, loanCode);
		applyInFoFlagEx.setLoanCoborrower(loanCoborrowerList);
		
		// 主借人房产信息
		List<LoanHouse> customerLoanHouseList = loanHouseDao.findListByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
		if (ObjectHelper.isEmpty(customerLoanHouseList)) {
			LoanHouse loanHouse = new LoanHouse();
			customerLoanHouseList = new LinkedList<LoanHouse>();
			customerLoanHouseList.add(loanHouse);
		}
		applyInFoFlagEx.setCustomerLoanHouseList(customerLoanHouseList);
		
		// 主借人客户经营信息
    	LoanCompManage loanCompManage = loanCompManageDao.findCompManageByLoanCode(loanCode);
    	applyInFoFlagEx.setLoanCompManage(loanCompManage);
    	
    	// 主借人客户证件信息
        LoanPersonalCertificate loanPersonalCertificate = loanPersonalCertificateDao.findByLoanCode(loanCode);
        applyInFoFlagEx.setLoanPersonalCertificate(loanPersonalCertificate);
    	
		// 主借人银行卡信息
		LoanBank loanBank = loanBankDao.selectByLoanCode(loanCode);
		applyInFoFlagEx.setLoanBank(ObjectHelper.isEmpty(loanBank) ? new LoanBank() : loanBank);
		
		//查询借么标识
	    Consult con = consultDao.get(loanInfo.getRid());
	    if(!ObjectHelper.isEmpty(con.getIsBorrow())){
	    	applyInFoFlagEx.setIsBorrow(con.getIsBorrow());
	    }

		return applyInFoFlagEx;
    }
    /**
     * 查询主借人信息
     * zmq
     * @param param
     * @return
     */
	public LoanCustomer queryLoanCustomer(Map<String,Object> param) {
		LoanCustomer loanCustomer=loanCustomerDao.selectByLoanCode(param);
		return loanCustomer;
	}
	/**
	 * 查询主借人的配偶信息
	 * zmq
	 * @return
	 */
	public LoanMate queryLoanMate(String loanCode) {
		LoanMate loanMate = loanMateDao.selectByLoanCode(loanCode, LoanManFlag.MAIN_LOAN.getCode());
		return loanMate;
	}
}
