package com.creditharmony.loan.borrow.stores.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.core.loan.type.RemarkType;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.ContactDao;
import com.creditharmony.loan.borrow.applyinfo.dao.CustomerLivingsDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCompManageDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCompanyDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCreditInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanHouseDao;
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
import com.creditharmony.loan.borrow.applyinfo.entity.LoanMate;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanPersonalCertificate;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanRemark;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.applyinfo.service.DataEntryService;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.reconsider.entity.ex.ReconsiderApplyEx;
import com.creditharmony.loan.borrow.reconsider.view.ReconsiderBusinessView;
import com.creditharmony.loan.borrow.stores.dao.AreaPreDao;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.utils.EncryptUtils;

/**
 * 区域相关功能service
 * @Class Name AreaService
 * @author lirui
 * @Create In 2016年1月26日
 */
@Service
@Transactional(readOnly = true,value="loanTransactionManager")
public class AreaService {
	
	@Autowired
	private AreaPreDao areaDao;
	
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	
	@Autowired
	private CustomerLivingsDao customerLivingsDao;
	
	@Autowired
	private LoanMateDao loanMateDao;
	
	@Autowired
	private LoanCompanyDao loanCompanyDao;
	
	@Autowired
	private ApplyLoanInfoDao loanInfoDao;
	
	@Autowired
	private LoanCoborrowerDao loanCoborrowerDao;
	
	@Autowired
	private ContactDao contactDao;
	
	@Autowired
	private LoanCreditInfoDao loanCreditInfoDao;
	
	@Autowired
	private LoanHouseDao loanHouseDao;
	
	@Autowired
	private LoanBankDao loanBankDao;
	
	@Autowired
	private LoanRemarkDao loanRemarkDao;
	
    @Autowired
    private DataEntryService dataEntryService;
    
    @Autowired
    private LoanCompManageDao loanCompManageDao;
    
    @Autowired
    private LoanPersonalCertificateDao loanPersonalCertificateDao;
	
	/**
	 * 根据区域id获取区域名称
	 * 2016年1月26日
	 * By lirui
	 * @param addressId 区域id
	 * @return 区域名称
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getAreaName(String addressId) {
		return areaDao.getAreaName(addressId);
	}
	
	/**
	 * 根据用户id获取用户姓名
	 * 2016年1月27日
	 * By lirui
	 * @param userId 用户id
	 * @return 用户姓名
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getUserName(String userId) {
		return areaDao.getUserName(userId);
	}
	
	/**
	 * 根据组织机构id获取组织机构名
	 * 2016年1月27日
	 * By lirui
	 * @param orgId
	 * @return
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getOreName(String orgId) {
		return areaDao.getOrgName(orgId);
	}
	
	/**
	 * 根据applyId获取当前流程状态
	 * 2016年2月15日
	 * By lirui
	 * @param applyId
	 * @return 当前流程code
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getStatusByApplyId(String applyId) {
		return areaDao.getStatusByApplyId(applyId);
	}
	
	/**
	 * 通过借款编码查询退回原因
	 * 2016年2月18日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 退回原因
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getReasons(String loanCode) {
		return areaDao.getBackReason(loanCode);		
	}
	
	/**
	 * 获取共借人住址/户籍省市名称
	 * 2016年2月23日
	 * By lirui
	 * @param launchView
	 * return none
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public void coboAreaChange(LaunchView launchView) {
		// 获取共借人住址省市名字
					if (launchView.getLoanCoborrower().size() != 0) {
						for (int i = 0; i < launchView.getLoanCoborrower().size(); i++) {
							LoanCoborrower cobo = launchView.getLoanCoborrower().get(i);
							// 共借人户籍省
							if (StringUtils.isNotEmpty(cobo.getCoboHouseholdProvince())) {
								String province = this.getAreaName(cobo.getCoboHouseholdProvince());
								cobo.setCoboHouseholdProvinceName(province);
							}
							// 共借人户籍市
							if (StringUtils.isNotEmpty(cobo.getCoboHouseholdCity())) {
								String city = this.getAreaName(cobo.getCoboHouseholdCity());
								cobo.setCoboHouseholdCityName(city);
							}	
							// 共借人户籍区
							if (StringUtils.isNotEmpty(cobo.getCoboHouseholdArea())) {
								String area = this.getAreaName(cobo.getCoboHouseholdArea());
								cobo.setCoboHouseholdAreaName(area);;
							}	
							// 共借人现住址省
							if (StringUtils.isNotEmpty(cobo.getCoboLiveingProvince())) {
								String provinceLiv = this.getAreaName(cobo.getCoboLiveingProvince());
								cobo.setCoboLiveingProvinceName(provinceLiv);;
							}
							// 共借人现住址市
							if (StringUtils.isNotEmpty(cobo.getCoboLiveingCity())) {
								String cityLiv = this.getAreaName(cobo.getCoboLiveingCity());
								cobo.setCoboLiveingCityName(cityLiv);
							}
							// 共借人现住址区
							if (StringUtils.isNotEmpty(cobo.getCoboLiveingArea())) {
								String areaLiv = this.getAreaName(cobo.getCoboLiveingArea());
								cobo.setCoboLiveingAreaName(areaLiv);
							}
						}
					}
	}
	
	/**
	 * 获取房产地址省市名称
	 * 2016年2月23日
	 * By lirui
	 * @param launchView
	 * return none
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public void houseAreaChange(LaunchView launchView) {
	    
		if (ObjectHelper.isNotEmpty(launchView.getCustomerLoanHouseList()) && launchView.getCustomerLoanHouseList().size() != 0) {
			for (int i = 0; i < launchView.getCustomerLoanHouseList().size(); i++) {
				if (StringUtils.isNotEmpty(launchView.getCustomerLoanHouseList().get(i).getHouseProvince())) {
					String province = this.getAreaName(launchView.getCustomerLoanHouseList().get(i).getHouseProvince());
					launchView.getCustomerLoanHouseList().get(i).setHouseProvinceName(province);						
				}
				if (StringUtils.isNotEmpty(launchView.getCustomerLoanHouseList().get(i).getHouseCity())) {
					String city = this.getAreaName(launchView.getCustomerLoanHouseList().get(i).getHouseCity());
					launchView.getCustomerLoanHouseList().get(i).setHouseCityName(city);
				}
				if (StringUtils.isNotEmpty(launchView.getCustomerLoanHouseList().get(i).getHouseArea())) {
					String area = this.getAreaName(launchView.getCustomerLoanHouseList().get(i).getHouseArea());
					launchView.getCustomerLoanHouseList().get(i).setHouseAreaName(area);
				}
			}				
		}
	}
	
	/**
	 * 开户行省市名称
	 * 2016年2月23日
	 * By lirui
	 * @param launchView
	 * return none
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public void bankAreaChange(LaunchView launchView) {
	    if(ObjectHelper.isNotEmpty(launchView.getLoanBank())){
	        if (StringUtils.isNotEmpty(launchView.getLoanBank().getBankProvince())) {
	            String bankProvinceName = this.getAreaName(launchView.getLoanBank().getBankProvince());
	            launchView.getLoanBank().setBankProvinceName(bankProvinceName);
	        }
		    if (StringUtils.isNotEmpty(launchView.getLoanBank().getBankCity())) {
		        String bankCityName = this.getAreaName(launchView.getLoanBank().getBankCity());
		        launchView.getLoanBank().setBankCityName(bankCityName);
		    }
	    }
	}

    /**
     * 配偶单位省市名称
     * 2016年2月23日
     * By lirui
     * @param launchView
     * return none
     */
    @Transactional(readOnly = true,value="loanTransactionManager")
    public void mateAreaChange(LaunchView launchView) {
        if(ObjectHelper.isNotEmpty(launchView.getLoanMate().getMateLoanCompany())){
            if (StringUtils.isNotEmpty(launchView.getLoanMate().getMateLoanCompany().getCompProvince())) {
                String provinceName = this.getAreaName(launchView.getLoanMate().getMateLoanCompany().getCompProvince());
                launchView.getLoanMate().getMateLoanCompany().setComProvinceName(provinceName);
            }
            if (StringUtils.isNotEmpty(launchView.getLoanMate().getMateLoanCompany().getCompCity())) {
                String cityName = this.getAreaName(launchView.getLoanMate().getMateLoanCompany().getCompCity());
                launchView.getLoanMate().getMateLoanCompany().setComCityName(cityName);
            }
            if (StringUtils.isNotEmpty(launchView.getLoanMate().getMateLoanCompany().getCompArer())) {
                String areaName = this.getAreaName(launchView.getLoanMate().getMateLoanCompany().getCompArer());
                launchView.getLoanMate().getMateLoanCompany().setComArerName(areaName);
            }
        }
    }
	/**
	 * 借款信息/管辖城市省市名称
	 * 2016年2月23日
	 * By lirui
	 * @param launchView
	 * return none
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public void applyAreaChange(LaunchView launchView) {
	    if(ObjectHelper.isNotEmpty(launchView.getLoanInfo())){
	        if (StringUtils.isNotEmpty(launchView.getLoanInfo().getStoreProviceCode())) {
	            String storeProvinceName = this.getAreaName(launchView.getLoanInfo().getStoreProviceCode());
	            launchView.getLoanInfo().setStoreProviceName(storeProvinceName);
	        }
	        if (StringUtils.isNotEmpty(launchView.getLoanInfo().getStoreCityCode())) {
	            String storeCityName = this.getAreaName(launchView.getLoanInfo().getStoreCityCode());
	            launchView.getLoanInfo().setStoreCityName(storeCityName);
	        }
	        if (StringUtils.isEmpty(launchView.getLoanInfo().getStoreProviceCode()) || StringUtils.isEmpty(launchView.getLoanInfo().getStoreCityCode())) {
	            this.getOrgName(launchView);
	        }
	    }
	}
	
	/**
	 * 公司地址省市名称
	 * 2016年2月23日
	 * By lirui
	 * @param launchView
	 * return none
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public void companyAreaChange(LaunchView launchView) {
		if (StringUtils.isNotEmpty(launchView.getCustomerLoanCompany().getCompProvince())) {
			String compProvince = this.getAreaName(launchView.getCustomerLoanCompany().getCompProvince());
			launchView.getCustomerLoanCompany().setComProvinceName(compProvince);
		}
		if (StringUtils.isNotEmpty(launchView.getCustomerLoanCompany().getCompCity())) {
			String compCity = this.getAreaName(launchView.getCustomerLoanCompany().getCompCity());
			launchView.getCustomerLoanCompany().setComCityName(compCity);
		}
		if (StringUtils.isNotEmpty(launchView.getCustomerLoanCompany().getCompArer())) {
			String compArea = this.getAreaName(launchView.getCustomerLoanCompany().getCompArer());
			launchView.getCustomerLoanCompany().setComArerName(compArea);
		}
	}
	
	 /**
     * 配偶地址省市名称
     * 2016年9月23日
     * By songfeng
     * @param launchView
     * return none
     */
    @Transactional(readOnly = true,value="loanTransactionManager")
    public void mateAddressChange(LaunchView launchView) {
        if(ObjectHelper.isNotEmpty(launchView.getLoanMate().getMateAddressProvince())){
            if (StringUtils.isNotEmpty(launchView.getLoanMate().getMateAddressProvince())) {
                String provinceName = this.getAreaName(launchView.getLoanMate().getMateAddressProvince());
                launchView.getLoanMate().setMateAddressProvince(provinceName);
            }
            if (StringUtils.isNotEmpty(launchView.getLoanMate().getMateAddressCity())) {
                String cityName = this.getAreaName(launchView.getLoanMate().getMateAddressCity());
                launchView.getLoanMate().setMateAddressCity(cityName);
            }
            if (StringUtils.isNotEmpty(launchView.getLoanMate().getMateAddressArea())) {
                String areaName = this.getAreaName(launchView.getLoanMate().getMateAddressArea());
                launchView.getLoanMate().setMateAddressArea(areaName);
            }
        }
    }
    
    /**
     * 经营地址省市名称
     * 2016年9月23日
     * By songfeng
     * @param launchView
     * return none
     */
    @Transactional(readOnly = true,value="loanTransactionManager")
    public void loanCompManageAddressChange(LaunchView launchView) {
        if(ObjectHelper.isNotEmpty(launchView.getLoanCompManage().getManageAddressProvince())){
            if (StringUtils.isNotEmpty(launchView.getLoanCompManage().getManageAddressProvince())) {
                String provinceName = this.getAreaName(launchView.getLoanCompManage().getManageAddressProvince());
                launchView.getLoanCompManage().setManageAddressProvince(provinceName);
            }
            if (StringUtils.isNotEmpty(launchView.getLoanCompManage().getManageAddressCity())) {
                String cityName = this.getAreaName(launchView.getLoanCompManage().getManageAddressCity());
                launchView.getLoanCompManage().setManageAddressCity(cityName);
            }
            if (StringUtils.isNotEmpty(launchView.getLoanCompManage().getManageAddressArea())) {
                String areaName = this.getAreaName(launchView.getLoanCompManage().getManageAddressArea());
                launchView.getLoanCompManage().setManageAddressArea(areaName);
            }
        }
    }
    
    /**
     * 证件信息户主页地址省市名称
     * 2016年9月23日
     * By songfeng
     * @param launchView
     * return none
     */
    @Transactional(readOnly = true,value="loanTransactionManager")
    public void masterCertAddressChange(LaunchView launchView) {
        if(ObjectHelper.isNotEmpty(launchView.getLoanPersonalCertificate().getMasterAddressProvince())){
            if (StringUtils.isNotEmpty(launchView.getLoanPersonalCertificate().getMasterAddressProvince())) {
                String provinceName = this.getAreaName(launchView.getLoanPersonalCertificate().getMasterAddressProvince());
                launchView.getLoanPersonalCertificate().setMasterAddressProvince(provinceName);
            }
            if (StringUtils.isNotEmpty(launchView.getLoanPersonalCertificate().getMasterAddressCity())) {
                String cityName = this.getAreaName(launchView.getLoanPersonalCertificate().getMasterAddressCity());
                launchView.getLoanPersonalCertificate().setMasterAddressCity(cityName);
            }
            if (StringUtils.isNotEmpty(launchView.getLoanPersonalCertificate().getMasterAddressArea())) {
                String areaName = this.getAreaName(launchView.getLoanPersonalCertificate().getMasterAddressArea());
                launchView.getLoanPersonalCertificate().setMasterAddressArea(areaName);
            }
        }
    }
	
	/**
	 * 根据借款编码查询客户信息
	 * 2016年2月24日
	 * By lirui
	 * @param loanCode 借款编码
	 * @return 客户信息View
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public ApplyInfoFlagEx getAllInfo(String loanCode) {
		String oldLoanCode="";
		if(loanCode.contains("-")){
			oldLoanCode=loanCode.substring(0,loanCode.length()-2);
		}else{
			oldLoanCode=loanCode;
		}
	    ApplyInfoFlagEx applyInFoFlagEx = new ApplyInfoFlagEx();
	    Map<String,Object> param = new HashMap<String,Object>();
	    	param.put("loanCode", loanCode);
	    	Map<String,Object> param1 = new HashMap<String,Object>();
	    	param1.put("loanCode", oldLoanCode);
	    	// 获取客户信息
	    	LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(param);
	    	if(ObjectHelper.isEmpty(loanCustomer)){
	    		loanCustomer = new LoanCustomer();
	    	}
	    	//解密
	    	EncryptUtils.decrypt(loanCustomer);
	        // 获取主借人的住房信息
	        CustomerLivings customerLivings = customerLivingsDao.selectByLoanCode(oldLoanCode,LoanManFlag.MAIN_LOAN.getCode());
            if(ObjectHelper.isEmpty(customerLivings)){
            	customerLivings = new CustomerLivings();  
            }	        
		    applyInFoFlagEx.setLoanCustomer(loanCustomer);
		    applyInFoFlagEx.setCustomerLivings(customerLivings);
		    // 获取借款信息备注信息
            List<LoanRemark> loanRemark = null;
            param.put("dictRemarkType", RemarkType.LoanFlag.getCode());
            param1.put("dictRemarkType", RemarkType.LoanFlag.getCode());
            loanRemark = loanRemarkDao.findByLoanCode(param1);
            if (loanRemark.size() != 0) {
            	applyInFoFlagEx.setLoanRemark(loanRemark.get(0));				
			}
	        // 获取主借人客户配偶信息
	        LoanMate loanMate = null;
	        // 获取主借人配偶信息的公司职业信息（set到主借人客户配偶信息对象中用于页面显示）
	        LoanCompany mateLoanCompany = null;
	        loanMate = loanMateDao.selectByLoanCode(oldLoanCode, LoanManFlag.MAIN_LOAN.getCode());
	        mateLoanCompany = loanCompanyDao.selectByLoanCode(oldLoanCode,LoanManFlag.MATE.getCode());
	        if(ObjectHelper.isEmpty(loanMate)){
	            loanMate = new LoanMate();
	        }
	        if(ObjectHelper.isEmpty(mateLoanCompany)){
	            mateLoanCompany = new LoanCompany();
	        }
	        loanMate.setMateLoanCompany(mateLoanCompany);
	        //解密
	        EncryptUtils.decrypt(loanMate);
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
	
	        loanCoborrower = loanCoborrowerDao.selectByLoanCode(oldLoanCode);
	        // 获取共借人的住房信息
	        CustomerLivings coboLivings =null;
	        LoanCompany coboCompany = null;
	        Map<String,Object> queryParam = new HashMap<String,Object>();
	        if(!ObjectHelper.isEmpty(loanCoborrower)){
	           for(LoanCoborrower coborrow:loanCoborrower){
	              coborrowId = coborrow.getId();
	              queryParam.put("loanCode", oldLoanCode);
                  queryParam.put("relateId", coborrowId);
                  queryParam.put("customerType", LoanManFlag.COBORROWE_LOAN.getCode());
	             
                  coborrowerContactList = contactDao.findListByLinkId(oldLoanCode,
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
	               for(Contact contact : coborrowerContactList){
	            	   EncryptUtils.decrypt(contact);
	               }
	               
	               EncryptUtils.decrypt(coborrow);
	               
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
	        loanCreditInfoList = loanCreditInfoDao.findListByLoanCode(oldLoanCode);
	        if(ObjectHelper.isEmpty(loanCreditInfoList)){
	            LoanCreditInfo creditInfo = new LoanCreditInfo();
	            loanCreditInfoList = new LinkedList<LoanCreditInfo>();  
	            loanCreditInfoList.add(creditInfo);
	        }
	        applyInFoFlagEx.setLoanCreditInfoList(loanCreditInfoList);
	 	    // 主借人职业信息/公司资料（set到客户基本信息对象里用于页面显示）
	        LoanCompany customerLoanCompany = null; 
	        customerLoanCompany = loanCompanyDao.selectByLoanCode(oldLoanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isEmpty(customerLoanCompany)){
	            customerLoanCompany = new LoanCompany(); 
	        }
	        applyInFoFlagEx.setCustomerLoanCompany(customerLoanCompany);
	  	    // 主借人房产资料集合（set到客户基本信息对象里用于页面显示）
	        List<LoanHouse> customerLoanHouseList = null;
	        customerLoanHouseList = loanHouseDao.findListByLoanCode(oldLoanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isEmpty(customerLoanHouseList)){
	            LoanHouse loanHouse = new LoanHouse();
	            customerLoanHouseList = new LinkedList<LoanHouse>();
	            customerLoanHouseList.add(loanHouse);
	        }
	        applyInFoFlagEx.setCustomerLoanHouseList(customerLoanHouseList);
	        // 主借人客户联系人信息（set到客户基本信息对象中用于页面显示）
	        List<Contact> customerContactList = null;
	        customerContactList = contactDao.findListByLoanCode(oldLoanCode,
                    LoanManFlag.MAIN_LOAN.getCode());
	        if(ObjectHelper.isEmpty(customerContactList)){
	            Contact contact = new Contact();
	            customerContactList = new LinkedList<Contact>();
	            customerContactList.add(contact);
	        }else{
	        	for(Contact contact : customerContactList){
	        		EncryptUtils.decrypt(contact);
	        	}
	        }
	        applyInFoFlagEx.setCustomerContactList(customerContactList);
	        // 借款人账户信息
	        LoanBank loanBank = null; 
	        loanBank = loanBankDao.selectByLoanCode(loanCode);
	        if(ObjectHelper.isEmpty(loanBank)){
	            loanBank = new LoanBank(); 
	         }
	        applyInFoFlagEx.setLoanBank(loanBank);
	        
	        //借款人经营信息
	        LoanCompManage loanCompManage=null;
	        loanCompManage=loanCompManageDao.findCompManageByLoanCode(oldLoanCode);
	        if(ObjectHelper.isEmpty(loanCompManage)){
	        	loanCompManage = new LoanCompManage(); 
	        }
	        EncryptUtils.decrypt(loanCompManage);
	        applyInFoFlagEx.setLoanCompManage(loanCompManage);
	        //借款人证件信息
	        LoanPersonalCertificate loanPersonalCertificate=null;
	        loanPersonalCertificate=loanPersonalCertificateDao.findByLoanCode(oldLoanCode);
	        if(ObjectHelper.isEmpty(loanPersonalCertificate)){
	        	loanPersonalCertificate = new LoanPersonalCertificate(); 
	        }
	        applyInFoFlagEx.setLoanPersonalCertificate(loanPersonalCertificate);
	        return applyInFoFlagEx;
	}
	
	/**
	 * 产品code转换产品名
	 * 2016年2月25日
	 * By lirui
	 * @param productCode 产品编号
	 * @return 产品名
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getProductNameByCode(String productCode) {
		return areaDao.getProductNameByCode(productCode);
	}
	
	/**
	 * 拷贝LaunchView数据到ReconsiderBusinessView(复议退回更新流程数据)
	 * 2016年2月29日
	 * By lirui
	 * @param launchView 
	 * return ReconsiderBusinessView
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public ReconsiderBusinessView copyView(LaunchView launchView) {
		ReconsiderBusinessView rb = new ReconsiderBusinessView();
		ReconsiderApplyEx ra = new ReconsiderApplyEx();
		if (!ObjectHelper.isEmpty(launchView)) {
			if (StringUtils.isNotEmpty(launchView.getApplyId())) {
				// applyId
				rb.setApplyId(launchView.getApplyId());
				/**如果是复议流程，根据新旧申请表标识走不同的查询方法 start*/
				ApplyInfoFlagEx applyInfo = null;
				HashMap<String, Object> param=new HashMap<String, Object>();
				param.put("loanCode", launchView.getLoanCode());
				LoanInfo loanInfo=loanInfoDao.selectByLoanCode(param);
				if(loanInfo!=null && "1".equals(loanInfo.getLoanInfoOldOrNewFlag())){
					applyInfo = dataEntryService.getAllInfoByLoanCode_new(launchView.getLoanCode());
				}else{
					String borrowApplyId = this.getApplyId(launchView.getApplyId());
					applyInfo = dataEntryService.getAllInfo(borrowApplyId);				
				}
				/**如果是复议流程，根据新旧申请表标识走不同的查询方法 end*/
				BeanUtils.copyProperties(applyInfo, launchView);
			}
			if (!ObjectHelper.isEmpty(launchView.getLoanCustomer())) {
				if (StringUtils.isNotEmpty(launchView.getLoanCustomer().getLoanCode())) {
					// 借款编码
					ra.setLoanCode(launchView.getLoanCustomer().getLoanCode());					
				}
				if (StringUtils.isNotEmpty(launchView.getLoanCustomer().getCustomerName())) {
                    // 借款编码
                    ra.setCustomerName(launchView.getLoanCustomer().getCustomerName());                 
                }
				if(ObjectHelper.isNotEmpty(launchView.getLoanInfo())){
				    if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanTeamManagerCode())) {
				        // 团队经理编号
				        ra.setTeamManagerCode(launchView.getLoanInfo().getLoanTeamManagerCode());
				        rb.setLoanTeamManagerCode(launchView.getLoanInfo().getLoanTeamManagerCode());
				    }
				    if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanTeamManagerName())) {
				        // 团队经理名
				        ra.setTeamManagerName(launchView.getLoanInfo().getLoanTeamManagerName());	
				        rb.setLoanTeamManagerName(launchView.getLoanInfo().getLoanTeamManagerName());
				    }
				    if (!ObjectHelper.isEmpty(launchView.getLoanInfo().getCustomerIntoTime())) {
				        // 进件时间
				        ra.setIntoLoanTime(launchView.getLoanInfo().getCustomerIntoTime());					
				    }
				    if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanRaiseFlag())) {
				        // 上调标识
				        ra.setRaiseFlag(launchView.getLoanInfo().getLoanRaiseFlag());					
				    }
				    if (StringUtils.isNotEmpty(launchView.getLoanInfo().getDictIsAdditional())) {
				        // 追加标识
				        ra.setAdditionalFlag(launchView.getLoanInfo().getDictIsAdditional());					
				    }
				    if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanUrgentFlag())) {
				        // 加急标识
				        ra.setUrgentFlag(launchView.getLoanInfo().getLoanUrgentFlag());					
				    }
				}
			}
			if (!ObjectHelper.isEmpty(launchView.getLoanCustomer())) {
				if (StringUtils.isNotEmpty(launchView.getLoanCustomer().getCustomerName())) {
					// 用户名
					ra.setCustomerName(launchView.getLoanCustomer().getCustomerName());					
				}
				if (StringUtils.isNotEmpty(launchView.getLoanCustomer().getCustomerCertNum())) {
					// 证件号码
					ra.setIdentityCode(launchView.getLoanCustomer().getCustomerCertNum());					
				}
				if (StringUtils.isNotEmpty(launchView.getLoanCustomer().getCustomerTelesalesFlag())) {
					// 电销标识
					ra.setTelesalesFlag(launchView.getLoanCustomer().getCustomerTelesalesFlag());					
				}
			}
			if (StringUtils.isNotEmpty(launchView.getCoborrowerNames())) {
				// 共借人
				ra.setCoborrowerName(launchView.getCoborrowerNames());				
			}
			if (StringUtils.isNotEmpty(launchView.getOrgProvinceCode())) {
				// 省code
				ra.setProvinceCode(launchView.getOrgProvinceCode());				
			}
			if (StringUtils.isNotEmpty(launchView.getOrgProvince())) {
				// 省名
				ra.setProvinceName(launchView.getOrgProvince());				
			}
			if (StringUtils.isNotEmpty(launchView.getOrgCityCode())) {
				// 市code
				ra.setCityCode(launchView.getOrgCityCode());				
			}
			if (StringUtils.isNotEmpty(launchView.getOrgCity())) {
				// 市名
				ra.setCityName(launchView.getOrgCity());				
			}
			if (StringUtils.isNotEmpty(launchView.getStoreOrgId())) {
				// 门店id
				ra.setStoreOrgId(launchView.getStoreOrgId());	
				rb.setStoreOrgId(launchView.getStoreOrgId());
			}
			if (StringUtils.isNotEmpty(launchView.getOrgCode())) {
				// 门店code
				ra.setStoreCode(launchView.getOrgCode());
				rb.setOrgCode(launchView.getOrgCode());
			}
			if (StringUtils.isNotEmpty(launchView.getOrgName())) {
				// 门店名
				ra.setStoreName(launchView.getOrgName());
				rb.setOrgName(launchView.getOrgName());
			}
			if (StringUtils.isNotEmpty(launchView.getLoanFlag())) {
				// 标识
				rb.setLoanFlag(launchView.getLoanFlag());				
			}
			if (StringUtils.isNotEmpty(launchView.getProductName())) {
				// 产品名称
				ra.setApplyProductName(launchView.getProductName());				
			}
			if (StringUtils.isNotEmpty(launchView.getProductCode())) {
				// 产品编号
				ra.setApplyProductCode(launchView.getProductCode());							
			}
			if (StringUtils.isNotEmpty(launchView.getDictLoanStatus())) {
				// 申请状态名
				rb.setDictLoanStatus(launchView.getDictLoanStatus());				
			}
			if (StringUtils.isNotEmpty(launchView.getDictLoanStatusCode())) {				
				// 申请状态code
				rb.setDictLoanStatusCode(launchView.getDictLoanStatusCode());
			}
			if (launchView.getLoanInfo().getLoanApplyAmount()!=null) {				
				// 申请额度
				ra.setApplyMoney(launchView.getLoanInfo().getLoanApplyAmount().doubleValue());
			}
			if (launchView.getLoanInfo().getLoanMonths()!=null) {				
				// 申请期限
				ra.setApplyMonth(launchView.getLoanInfo().getLoanMonths());
			}
			if (StringUtils.isNotEmpty(launchView.getRejectReason())) {                
                // 退回原因
                rb.setRejectReason(launchView.getRejectReason());
            }
			if (!ObjectHelper.isEmpty(ra)) {
				rb.setReconsiderApplyEx(ra);				
			}
			if(StringUtils.isNotEmpty(launchView.getBankIsRareword())){
			    
			    rb.setBankIsRareword(launchView.getBankIsRareword());
			}
		}
		return rb;
	}
	
	/**
	 * 根据applyId获得loanCode
	 * 2016年3月3日
	 * By lirui
	 * @param applyId
	 * @return loanCode
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getLoanCodeByApplyId(String applyId) {
		if (StringUtils.isNotEmpty(applyId)) {
			String loanCode = areaDao.getLoanCodeByApplyId(applyId);
			return loanCode;		
		}else {
			return "";
		}
	}
	
	/**
	 * 根据loanCode获得applyId
	 * 2016年5月13日
	 * t_jk_reconsider_apply复议申请表，
	 * 根据loanCode查询复议申请表是否存在记录，如果存在取复议申请表中的applyid，如果不存在取t_jk_loan_info借款表的applyid
	 * By chenyl
	 * @param loanCode
	 * @return applyId
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getApplyIdByLoanCode(String loanCode) {
		if (StringUtils.isNotEmpty(loanCode)) {
			String applyId = areaDao.getApplyIdByLoanCode(loanCode);
			return applyId;		
		}else {
			return "";
		}
	}
	
	/**
	 * 设置共借人名
	 * 2016年3月3日
	 * By lirui
	 * @param launchView
	 * return none
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public void setCoborrowers(LaunchView launchView) {
		List<LoanCoborrower> coborrowers = launchView.getLoanCoborrower();
		StringBuffer coborrowerBuffer = new StringBuffer();
		for (LoanCoborrower cur : coborrowers) {
			if (StringUtils.isNotEmpty(cur.getCoboName())) {
				if (coborrowerBuffer.length() == 0) {
					coborrowerBuffer.append(cur.getCoboName());
				} else {
					coborrowerBuffer.append("," + cur.getCoboName());
				}				
			}
		}
		if (coborrowerBuffer.length() == 0) {
			coborrowerBuffer.append("");
		}
		launchView.setCoborrowerNames(coborrowerBuffer.toString());
	}
	
	/**
	 * 查询管辖城市
	 * 2016年3月5日
	 * By lirui
	 * @param launchView
	 * return none
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public void getOrgName(LaunchView launchView) {
		if (!ObjectHelper.isEmpty(launchView.getLoanInfo())) {
			if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanStoreOrgId())) {
				String provinceId = areaDao.getProvinceId(launchView.getLoanInfo().getLoanStoreOrgId());
				if (StringUtils.isNotEmpty(provinceId)) {
					String provinceName = areaDao.getAreaName(provinceId);
					if (StringUtils.isNotEmpty(provinceName)) {
						launchView.getLoanInfo().setStoreProviceName(provinceName);
					}
				}
				String cityId = areaDao.getCityId(launchView.getLoanInfo().getLoanStoreOrgId());
				if (StringUtils.isNotEmpty(cityId)) {
					String cityName = areaDao.getAreaName(cityId);
					if (StringUtils.isNotEmpty(cityName)) {
						launchView.getLoanInfo().setStoreCityName(cityName);
					}
				}				
			}
		}
	} 
	
	/**
	 * 根据复议applyId查询信审applyId
	 * 2016年3月7日
	 * By lirui
	 * @param applyId 复议流程id
	 * @return 信审applyId
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String getApplyId(String applyId) {	
		return areaDao.getApplyId(applyId);
	};
	
	/**
	 * 通过用户code获取用户姓名
	 * 2016年4月18日
	 * By lirui
	 * @param userCode 用户code
	 * @return 用户姓名
	 */
	@Transactional(readOnly = true,value="loanTransactionManager")
	public String selectNameByCode(String userCode) {
		return areaDao.selectNameByCode(userCode);
	}
}
