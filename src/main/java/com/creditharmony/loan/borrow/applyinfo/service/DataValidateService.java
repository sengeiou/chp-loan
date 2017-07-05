/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.serviceDataValidateService.java
 * @Create By 张灏
 * @Create In 2016年1月29日 上午11:56:21
 */
package com.creditharmony.loan.borrow.applyinfo.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.adapter.bean.in.approvewechat.ApproveWeChatPayResultInBean;
import com.creditharmony.adapter.bean.in.img.Img_GetExistImgBarCodeListInBean;
import com.creditharmony.adapter.bean.out.approvewechat.ApproveWeChatPayResultOutBean;
import com.creditharmony.adapter.bean.out.img.Img_GetExistImgBarCodeListOutBean;
import com.creditharmony.adapter.constant.ReturnConstant;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.exception.ServiceException;
import com.creditharmony.core.loan.type.FamilyRelation;
import com.creditharmony.core.loan.type.HouseCommonType;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.Marriage;
import com.creditharmony.core.loan.type.RelationType;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCompManageDao;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;
import com.creditharmony.loan.borrow.applyinfo.entity.FileCategoryEmpty;
import com.creditharmony.loan.borrow.applyinfo.entity.FileCategoryType;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompManage;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompany;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanHouse;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanMate;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.channel.jyj.entity.JyjBorrowBankConfigure;
import com.creditharmony.loan.channel.jyj.service.JyjBorrowBankConfigureService;
import com.creditharmony.loan.common.dao.FileDiskInfoDao;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.entity.CreditReportRisk;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.type.LoanProductCode;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.DateUtil;
import com.creditharmony.loan.credit.dao.CreditReportDetailedDao;
import com.creditharmony.loan.credit.entity.CreditReportDetailed;
import com.creditharmony.loan.credit.entity.CreditReportSimple;
import com.creditharmony.loan.credit.service.CreditDetailedInfoService;
import com.creditharmony.loan.credit.service.CreditReportSimpleService;

/**
 * 用于录入资料的验证
 * @Class Name DataValidateService
 * @author 张灏
 * @Create In 2016年1月29日
 */
@Service("dataValidateService")
public class DataValidateService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FileDiskInfoDao diskInfoDao;
	@Autowired
	private ApplyLoanInfoDao applyLoanInfoDao;
	@Autowired
	private DataEntryService dataEntryService;
	@Autowired
	private CreditReportSimpleService creditReportSimpleService;
	@Autowired
	private CreditReportDetailedDao creditReportDetailedDao;
	@Autowired
	private CreditDetailedInfoService creditDetailedInfoService;
	@Autowired
	private LoanCustomerDao loanCustomerDao;
	@Autowired
	private LoanBankDao loanBankDao;
	@Autowired
	private LoanCompManageDao loanCompManageDao;
	@Autowired
	private JyjBorrowBankConfigureService jyjBorrowBankConfigureService;
    
    /**
     *对申请录入的资料进行验证  此方法适用于旧版申请表，新版申请表请见validate_new方法
     *@author zhanghao
     *@create In 2016年1月29日 
     *@param  tempApplyInfo
     *@return Map<String,Object>
     */
    public Map<String,Object> validate(ApplyInfoFlagEx tempApplyInfo){
    	Map<String,Object> result = new HashMap<String,Object>(); 
        LoanCustomer loanCustomer = tempApplyInfo.getLoanCustomer();
        
        LoanInfo loanInfo = tempApplyInfo.getLoanInfo();
        List<Contact> contacts = tempApplyInfo.getCustomerContactList();
        // 主借人信息必填
        if(StringUtils.isEmpty(loanCustomer.getId())){
            result.put("success", false);
            result.put("flag","1");
            result.put("message", ApplyInfoConstant.MAIN_BORROWER_NEEDED);
            return result;
        } else{
//			做自然人保证人需求是加的校验，现在需求取消，留着做新申请表用
//     		验证主借人的年龄  
//        	if("1".equals(tempApplyInfo.getOneedition())){
//        		// 自然人保证人页签为空
//        		List<LoanCoborrower> loanCoborrower = tempApplyInfo.getLoanCoborrower();
//        		//如果为空
//        		if(ObjectHelper.isEmpty(loanCoborrower) || StringUtils.isEmpty(loanCoborrower.get(0).getId())){
//        			String cardNumber = loanCustomer.getCustomerCertNum();
//        			String birthDay = this.getBirthDateFromCard(cardNumber);
//        			Date birthDate = null;
//        			try {
//        				birthDate = DateUtils.parseDate(birthDay, "yyyy-MM-dd");
//        				long diff = Math.abs(new Date().getTime() - birthDate.getTime());
//        				long dayTime = (1000 * 60 * 60 * 24 );
//        				long dayCount;
//        				long yearCount;
//        				if(diff % dayTime == 0 ) {
//        					dayCount = diff / dayTime ;
//        				}else{
//        					dayCount = diff / dayTime + 1;
//        				}
//        				yearCount = dayCount/ApplyInfoConstant.DAY_OF_YEAR;
//        				if(yearCount>=ApplyInfoConstant.LIMIT_OF_YEAR){
//        					List<LoanCoborrower> coborrowers = tempApplyInfo.getLoanCoborrower(); 
//        					if(ObjectHelper.isEmpty(coborrowers) || StringUtils.isEmpty(coborrowers.get(0).getId())){
//        						result.put("success", false);
//        						result.put("flag","2");
//        						result.put("message",ApplyInfoConstant.COBORROWER_NEEDED);  
//        						return result;
//        					}
//        				}
//        			} catch (ParseException e) {
//        				e.printStackTrace();
//        			}
//        		}
//        	}
        }
        String dictMarry = loanCustomer.getDictMarry();
        // 婚姻状况判定
        if(Marriage.MARRIED.getCode().equals(dictMarry)){
        	LoanMate loanMate = tempApplyInfo.getLoanMate();
        	if(ObjectHelper.isEmpty(loanMate.getMateName())){
        		result.put("success", false);
        		result.put("flag","3");
                result.put("message", ApplyInfoConstant.MATE_NEEDED);
                return result;
            }
        	
        	
        }else if(!Marriage.MARRIED.getCode().equals(dictMarry)){
            LoanMate loanMate = tempApplyInfo.getLoanMate();
            if(!ObjectHelper.isEmpty(loanMate) && StringUtils.isNotEmpty(loanMate.getId())){
                result.put("success", false);
                result.put("flag","4");
                result.put("tagId", loanMate.getId());
                result.put("itemType", "MATE");
                result.put("message", ApplyInfoConstant.NOT_NEED_MATE);
                return result;
            }
            if(Marriage.NO_MARRIED.getCode().equals(dictMarry)){  // 如果为未婚状态时，判断房子的共有情况不能为夫妻共有
            	String dictHouseCommon = null;
                List<LoanHouse> loanHouses = tempApplyInfo.getCustomerLoanHouseList();
                if(!ObjectHelper.isEmpty(loanHouses)){
                    for(LoanHouse house:loanHouses){
                        if(!ObjectHelper.isEmpty(house)){
                            dictHouseCommon = house.getDictHouseCommon();
                            if(HouseCommonType.SPOUSE.getCode().equals(dictHouseCommon)){
                                result.put("success", false);
                                result.put("flag","5");
                                result.put("message", ApplyInfoConstant.HOUSE_COMMON_NOT_SPOUSE);
                                return result;
                            }
                        }
                    }
                }
            }
        }
        // 申请信息是否填写
        if(ObjectHelper.isEmpty(loanInfo) || StringUtils.isEmpty(loanInfo.getId())){
        	result.put("success", false);
            result.put("flag","6");
            result.put("message",ApplyInfoConstant.APPLYINFO_NEEDED);
            return result;
        }else{// 是否填写申请产品
        	String productType = loanInfo.getProductType();
        	if(StringUtils.isEmpty(productType)){
        		result.put("success", false);
        		result.put("flag","7");
        		result.put("message",ApplyInfoConstant.PRODUCT_NEEDED);
        		return result; 
            // 企业借、老板借、小微企业借校验      
            }else if(ApplyInfoConstant.BORROW_COMPANY.equals(productType) 
                     || ApplyInfoConstant.BORROW_BOSS.equals(productType)
                     || ApplyInfoConstant.BORROW_MINI_COMPANY.equals(productType)){
            	
                LoanCompany loanCompany = tempApplyInfo.getLoanCompany();
            
                if(ObjectHelper.isEmpty(loanCompany) || StringUtils.isEmpty(loanCompany.getId())){
                	result.put("success", false);
                	result.put("flag","8");
                	result.put("message",ApplyInfoConstant.COMPANY_NEEDED);
                	return result;
                }
            // 楼易借、优房借校验
            }else if(ApplyInfoConstant.BORROW_BUILDING.equals(productType)
                     || ApplyInfoConstant.BORROW_GREATHOUSE.equals(productType)){
            	
            	List<LoanHouse> loanHouses = tempApplyInfo.getCustomerLoanHouseList();
            	if(ObjectHelper.isEmpty(loanHouses)||ObjectHelper.isEmpty(loanHouses.get(0))|| StringUtils.isEmpty(loanHouses.get(0).getId())){
            		result.put("success", false);
            		result.put("flag","9");
            		result.put("message",ApplyInfoConstant.HOUSE_NEEDED);
            		return result; 
            	}
            }
//        	做自然人保证人需求是加的校验，现在需求取消，留着做新申请表用
//        	if(loanInfo.getLoanApplyAmount()!=null){
//        		// 自然人保证人页签为空
//        		List<LoanCoborrower> loanCoborrower = tempApplyInfo.getLoanCoborrower();
//        		//如果为空
//        		if(ObjectHelper.isEmpty(loanCoborrower) || StringUtils.isEmpty(loanCoborrower.get(0).getId())){
//        			BigDecimal a= 	loanInfo.getLoanApplyAmount();
//        			BigDecimal b=BigDecimal.valueOf(80000);
//        			if((a.compareTo(b)==1) ==true ){
//        				result.put("success", false);
//        				result.put("flag","13");
//        				result.put("message", ApplyInfoConstant.CONTACT_ODD);
//        				return result;
//        			}
//        		}else{
//        			BigDecimal a= 	loanInfo.getLoanApplyAmount();
//        			BigDecimal b=BigDecimal.valueOf(150000);
//                    //借款大于15W,并且借款人已婚
//                    if((a.compareTo(b)==1) ==true && Marriage.MARRIED.getCode().equals(dictMarry)){
//                    	LoanMate loanMate = tempApplyInfo.getLoanMate();
//                    	//信息证件是身份证 并且 配偶身份证与自然人保证人身份证相同
//                    	if(CertificateType.SFZ.getCode().equals(loanMate.getDictCertType()) 
//                    			&& !(loanMate.getMateCertNum().equals(loanCoborrower.get(0).getCoboCertNum()))){
//                    		result.put("success", false);
//                            result.put("flag","14");
//                            result.put("message", ApplyInfoConstant.REPEAT_PERSION);
//                            return result;
//                    	}
//                    }
//                }
//            }
        }
        // 共借人校验
        if(ObjectHelper.isEmpty(contacts) || StringUtils.isEmpty(contacts.get(0).getId())){
            result.put("success", false);
            result.put("flag","10");
            result.put("message", ApplyInfoConstant.CONTACT_NEEDED);
            return result;
        }
        
        if(contacts.size()<ApplyInfoConstant.CONTACT_MIN_COUNT){
            result.put("success", false);
            result.put("flag","11");
            result.put("message", ApplyInfoConstant.CONTACT_COUNT_MESSAGE);
            return result;
        }
        result.put("success", true);
        result.put("flag","12");
        result.put("message",ApplyInfoConstant.SUCCESS_MESSAGE);
        return result;
    }
    /**
     * 此方法适用于新版申请表
     */
    public Map<String,Object> validate_new(ApplyInfoFlagEx tempApplyInfo){
    	Map<String,Object> result = new HashMap<String,Object>(); 
    	//查询条件
    	Map<String, Object> param = new HashMap<>();
    	LoanCustomer loanCustomer = tempApplyInfo.getLoanCustomer();
        Date now = new Date();
        Date sixtyDayAfter =DateUtils.addSeconds(DateUtils.addDays(DateUtil.getDayBegin(now), 59), -1) ;
        // 主借人信息必填
        if(StringUtils.isEmpty(loanCustomer.getId())){
            result.put("success", false);
            result.put("flag","1");
            result.put("message", ApplyInfoConstant.MAIN_BORROWER_NEEDED);
            return result;
        }else if(loanCustomer.getIdEndDay() != null && sixtyDayAfter.after(loanCustomer.getIdEndDay())){
    		result.put("success", false);
            result.put("flag","1");
            result.put("message", ApplyInfoConstant.CUSTOMER_ID_END_DAY_BETWEEN_ENTER_DAY_LESS_SIXTY_DAY);
            return result;
        }

        LoanInfo loanInfo = tempApplyInfo.getLoanInfo();
        // 借款意愿是否填写
        if(ObjectHelper.isEmpty(loanInfo) || StringUtils.isEmpty(loanInfo.getId())){
        	result.put("success", false);
            result.put("flag","6");
            result.put("message",ApplyInfoConstant.APPLYINFO_NEEDED_NEW);
            return result;
        }else{
        	param.put("loanCode", loanInfo.getLoanCode());
        	// 是否填写申请产品
        	String productType = loanInfo.getProductType();
        	if(StringUtils.isEmpty(productType)){
        		result.put("success", false);
        		result.put("flag","2");
        		result.put("message",ApplyInfoConstant.PRODUCT_NEEDED);
        		return result; 
            // 企业借、老板借、小微企业借校验      
            }else if(ApplyInfoConstant.BORROW_COMPANY.equals(productType) 
                     || ApplyInfoConstant.BORROW_BOSS.equals(productType)
                     || ApplyInfoConstant.BORROW_MINI_COMPANY.equals(productType)){
            	
            	LoanCompManage loanCompManage = tempApplyInfo.getLoanCompManage();
            	
            	if(ObjectHelper.isEmpty(loanCompManage) || StringUtils.isEmpty(loanCompManage.getId())){
            		result.put("success", false);
                	result.put("flag","7");
                	result.put("message",ApplyInfoConstant.COMPANY_MANAGE_NEEDED);
                	return result;
            	}
            	//大于30W，法人保证人必须填写
            	if(loanInfo.getLoanApplyAmount() != null && loanInfo.getLoanApplyAmount().compareTo(ApplyInfoConstant.THIRTY_K) == 1){
            		
            		//法人保证人姓名身份证必填
            		if(StringUtils.isEmpty(loanCompManage.getCorporateRepresent()) && StringUtils.isEmpty(loanCompManage.getCertNum())){
            			result.put("success", false);
                    	result.put("flag","7");
                    	result.put("message",ApplyInfoConstant.COMPANY_MANAGER_NEEDED);
                    	return result;
            		}
            		//证件有效期距进件日期小于60天，请重新提供身份证件。
            		if(loanCompManage.getIdEndDay() != null && sixtyDayAfter.after(loanCompManage.getIdEndDay())){
            			result.put("success", false);
                        result.put("flag","7");
                        result.put("message", ApplyInfoConstant.CORPORATEREPRESENT_ID_END_DAY_BETWEEN_ENTER_DAY_LESS_SIXTY_DAY);
                        return result;
            		}
            	}
            	
            	//申请金额大于30万，信用代码和组织机构码不能同时为空
            	if(loanInfo.getLoanApplyAmount() != null && loanInfo.getLoanApplyAmount().compareTo(ApplyInfoConstant.THIRTY_K) == 1
            			&& StringUtils.isEmpty(loanCompManage.getCreditCode()) && StringUtils.isEmpty(loanCompManage.getOrgCode())){
            		
            		result.put("success", false);
                	result.put("flag","7");
                	result.put("message",ApplyInfoConstant.COMPANY_CREDITCODE_AND_ORGCODE_NEEDED);
                	return result;
            	}
            	param.put("businessLicenseRegisterNum", loanCompManage.getBusinessLicenseRegisterNum());
            	//营业执照注册号 存在未结清的借款
            	Long loanCount = loanCompManageDao.queryLoanCompManageCountByBusinessLicenseRegisterNum(param);
            	if(loanCount != null && loanCount > 0){
            		result.put("success", false);
                	result.put("flag","7");
                	result.put("message",ApplyInfoConstant.BUSINESS_LICENSE_IN_UNSETTLE_DATA);
                	return result;
            	}
            	
            // 楼易借、优房借校验
            }else if(ApplyInfoConstant.BORROW_BUILDING.equals(productType) || ApplyInfoConstant.BORROW_GREATHOUSE.equals(productType)){
            	
            	List<LoanHouse> loanHouses = tempApplyInfo.getCustomerLoanHouseList();
            	if(ObjectHelper.isEmpty(loanHouses)||ObjectHelper.isEmpty(loanHouses.get(0))|| StringUtils.isEmpty(loanHouses.get(0).getId())){
            		result.put("success", false);
            		result.put("flag","6");
            		result.put("message",ApplyInfoConstant.HOUSE_NEEDED);
            		return result; 
            	}
            }
        }
        
        //工作信息
		LoanCompany loanCompany = tempApplyInfo.getLoanCompany();
		if ((ObjectHelper.isEmpty(loanCompany) || StringUtils.isEmpty(loanCompany.getId())) && !(ApplyInfoConstant.BORROW_NONGXINJIE.equals(loanInfo.getProductType())) ) {
			result.put("success", false);
			result.put("flag", "3");
			result.put("message", ApplyInfoConstant.COMPANY_NEEDED_NEW);
			return result;
		}
		
		List<Contact> contacts = tempApplyInfo.getCustomerContactList();
		// 联系人校验
        if(ObjectHelper.isEmpty(contacts) || StringUtils.isEmpty(contacts.get(0).getId())){
            result.put("success", false);
            result.put("flag","40");
            result.put("message", ApplyInfoConstant.CONTACT_NEEDED);
            return result;
        }
        //联系人校验
        if("1".equals(tempApplyInfo.getIsBorrow())){
        	if(contacts.size()<ApplyInfoConstant.CONTACT_MIN_COUNT_NEW_ISBORROW){
        		result.put("success", false);
        		result.put("flag","40");
        		result.put("message", ApplyInfoConstant.CONTACT_COUNT_MESSAGE_NEW_ISBORROW);
        		return result;
        	}
        }else{
        	if(contacts.size()<ApplyInfoConstant.CONTACT_MIN_COUNT_NEW){
        		result.put("success", false);
        		result.put("flag","40");
        		result.put("message", ApplyInfoConstant.CONTACT_COUNT_MESSAGE_NEW);
        		return result;
        	}
        }
        
        String dictMarry = loanCustomer.getDictMarry();
        // 婚姻状况判定
        if(Marriage.MARRIED.getCode().equals(dictMarry)){
        	LoanMate loanMate = tempApplyInfo.getLoanMate();
        	if(ObjectHelper.isEmpty(loanMate.getMateName())){
        		result.put("success", false);
        		result.put("flag","40");
                result.put("message", ApplyInfoConstant.MATE_NEEDED);
                return result;
            }
        	param.put("certNum", loanMate.getMateCertNum());
        	List<Map<String, Integer>> mateUnSettleMapList = applyLoanInfoDao.selectUnSettleData(param);
        	Map<String, Integer> mateUnSettleMap = null;
        	for(Map<String, Integer> map : mateUnSettleMapList){
        		if(map.get("count") > 0){
        			mateUnSettleMap = map;
        			break;
        		}
        	}
        	
        	//配偶是尚未结清的主借人
        	if(mateUnSettleMap != null && mateUnSettleMap.get("role") == 1){
        		result.put("success", false);
        		result.put("flag","41");
    			result.put("message", ApplyInfoConstant.MATE_IN_UNSETTLE_CUSTOMER_DATA);
    			return result;
        	}
        	//配偶是尚未结清的主借人配偶        	
        	if(mateUnSettleMap != null && mateUnSettleMap.get("role") == 2){
        		result.put("success", false);
        		result.put("flag","42");
    			result.put("message", ApplyInfoConstant.MATE_IN_UNSETTLE_MATE_DATA);
    			return result;
        	}
        	//配偶是尚未结清的共借人(旧版申请表)
        	if(mateUnSettleMap != null && mateUnSettleMap.get("role") == 3){
        		result.put("success", false);
        		result.put("flag","43");
    			result.put("message", ApplyInfoConstant.MATE_IN_UNSETTLE_COBO_DATA);
    			return result;
        	}
        	//配偶是尚未结清的自然人保证人(新版申请表)
        	if(mateUnSettleMap != null && mateUnSettleMap.get("role") == 4){
        		result.put("success", false);
        		result.put("flag","44");
    			result.put("message", ApplyInfoConstant.MATE_IN_UNSETTLE_BEST_NATURALGUARANTOR_DATA);
    			return result;
        	}
        	
        }else if(!Marriage.MARRIED.getCode().equals(dictMarry)){
            LoanMate loanMate = tempApplyInfo.getLoanMate();
            if(!ObjectHelper.isEmpty(loanMate) && StringUtils.isNotEmpty(loanMate.getId())){
                result.put("success", false);
                result.put("flag","4");
                result.put("tagId", loanMate.getId());
                result.put("itemType", "MATE");
                result.put("message", ApplyInfoConstant.NOT_NEED_MATE);
                return result;
            }
            if(Marriage.NO_MARRIED.getCode().equals(dictMarry)){  // 如果为未婚状态时，判断房子的共有情况不能为夫妻共有
            	String dictHouseCommon = null;
                
            	List<LoanHouse> loanHouses = tempApplyInfo.getCustomerLoanHouseList();
                if(!ObjectHelper.isEmpty(loanHouses)){
                    for(LoanHouse house:loanHouses){
                        if(!ObjectHelper.isEmpty(house)){
                            dictHouseCommon = house.getDictHouseCommon();
                            if(HouseCommonType.SPOUSE.getCode().equals(dictHouseCommon)){
                                result.put("success", false);
                                result.put("flag","40");
                                result.put("message", ApplyInfoConstant.HOUSE_COMMON_NOT_SPOUSE);
                                return result;
                            }
                        }
                    }
                }
                
//                LoanPersonalCertificate loanPersonalCertificate = tempApplyInfo.getLoanPersonalCertificate();
//                if(!ObjectHelper.isEmpty(loanPersonalCertificate) && loanPersonalCertificate.getWeddingTime() != null 
//                		&& StringUtils.isNotEmpty(loanPersonalCertificate.getLicenseIssuingAgency())){
//                	
//                	result.put("success", false);
//                    result.put("flag","8");
//                    result.put("message", ApplyInfoConstant.CERTIFICATE_WEDDING_TIME_AND_LICENSEISSUING_AGENCY_NOT_NEED);
//                    return result;
//                }
            }
        }
        
        //主借人生日
        Date customerBirthDate = DateUtils.convertDate(this.getBirthDateFromCard(loanCustomer.getCustomerCertNum()));
        //主借人50岁日期
        Date customerFiftyYearsOldDate = DateUtils.addYears(customerBirthDate, 50); 
        //自然人保证人
        List<LoanCoborrower> loanCoborrowerList = tempApplyInfo.getLoanCoborrower();
        //主借人 (借款金额大于等于6W并且借款产品是农信借 或者 主借人年龄大于50岁)  必须填自然人保证人

		boolean needCobo = false;
		if((now.equals(customerFiftyYearsOldDate) || now.after(customerFiftyYearsOldDate))){
			needCobo = true;
			//自然人保证人为空
			if(ObjectHelper.isEmpty(loanCoborrowerList) || StringUtils.isEmpty(loanCoborrowerList.get(0).getId())){
				result.put("success", false);
				result.put("flag","5");
				result.put("message", ApplyInfoConstant.CUSTOMER_FIFTY_YEAR_OLD_COBORROWER_NEEDED);
				return result;
			}
		}
		if((loanInfo.getLoanApplyAmount() != null
				&& (loanInfo.getLoanApplyAmount().compareTo(ApplyInfoConstant.SIXTY_K) == 0
				|| loanInfo.getLoanApplyAmount().compareTo(ApplyInfoConstant.SIXTY_K) == 1)
				&& LoanProductCode.PRO_NONG_XIN_JIE.equals(loanInfo.getProductType()))){
			needCobo = true;
			//自然人保证人为空
			if(ObjectHelper.isEmpty(loanCoborrowerList) || StringUtils.isEmpty(loanCoborrowerList.get(0).getId())){
				result.put("success", false);
				result.put("flag","5");
				result.put("message", ApplyInfoConstant.COBORROWER_NEEDED);
				return result;
			}
		}
		if(needCobo){
        	boolean flag = false;
        	Map<String, Integer> coboUnSettleMap = null;
        	Map<String, Integer> coboMateUnSettleMap = null;
        	for(LoanCoborrower loanCoborrower : loanCoborrowerList){
        		
        		//多个自然人保证人，证件结束时间距离进件时间都要大于60天
        		if(loanCoborrower != null && loanCoborrower.getIdEndDay() != null && sixtyDayAfter.after(loanCoborrower.getIdEndDay())){
        			flag = true;
            	}
        		param.put("certNum", loanCoborrower.getCoboCertNum());
        		//查自然人保证人未结清数据
        		List<Map<String, Integer>> coboUnSettleMapList = applyLoanInfoDao.selectUnSettleData(param);
            	for(Map<String, Integer> map : coboUnSettleMapList){
            		if(map.get("count") > 0){
            			coboUnSettleMap = map;
            			break;
            		}
            	}
            	//查自然人保证人配偶未结清数据
            	List<Contact> contactList = loanCoborrower.getCoborrowerContactList();
        		for(Contact contact : contactList){
        			if(RelationType.FAMILY_CONTACTS.getCode().equals(contact.getRelationType()) && FamilyRelation.MATES.getCode().equals(contact.getContactRelation()) && StringUtils.isNotEmpty(contact.getCertNum())){
        				param.put("certNum", contact.getCertNum());
        				List<Map<String, Integer>> coboMateUnSettleMapList = applyLoanInfoDao.selectUnSettleData(param);
                    	for(Map<String, Integer> map : coboMateUnSettleMapList){
                    		if(map.get("count") > 0){
                    			coboMateUnSettleMap = map;
                    			break;
                    		}
                    	}
        			}
        			if(coboMateUnSettleMap != null){
        				break;
        			}
        		}
        		
        	}
        	
        	if(flag){
        		result.put("success", false);
                result.put("flag","5");
                result.put("message", ApplyInfoConstant.NATURALGUARANTOR_ID_END_DAY_BETWEEN_ENTER_DAY_LESS_SIXTY_DAY);
                return result;
        	}
        	
        	//自然人保证人是尚未结清的主借人
        	if(coboUnSettleMap != null && coboUnSettleMap.get("role") == 1){
        		result.put("success", false);
        		result.put("flag","50");
    			result.put("message", ApplyInfoConstant.COBO_IN_UNSETTLE_CUSTOMER_DATA);
    			return result;
        	}
        	//自然人保证人是尚未结清的主借人配偶        	
        	if(coboUnSettleMap != null && coboUnSettleMap.get("role") == 2){
        		result.put("success", false);
        		result.put("flag","51");
    			result.put("message", ApplyInfoConstant.COBO_IN_UNSETTLE_MATE_DATA);
    			return result;
        	}
        	//自然人保证人是尚未结清的共借人(旧版申请表)
        	if(coboUnSettleMap != null && coboUnSettleMap.get("role") == 3){
        		result.put("success", false);
        		result.put("flag","52");
    			result.put("message", ApplyInfoConstant.COBO_IN_UNSETTLE_COBO_DATA);
    			return result;
        	}
        	//自然人保证人是尚未结清的自然人保证人(新版申请表)
        	if(coboUnSettleMap != null && coboUnSettleMap.get("role") == 4){
        		result.put("success", false);
        		result.put("flag","53");
    			result.put("message", ApplyInfoConstant.COBO_IN_UNSETTLE_BEST_NATURALGUARANTOR_DATA);
    			return result;
        	}
        	
        	//自然人保证人配偶是尚未结清的主借人
        	if(coboMateUnSettleMap != null && coboMateUnSettleMap.get("role") == 1){
        		result.put("success", false);
        		result.put("flag","54");
    			result.put("message", ApplyInfoConstant.COBO_MATE_IN_UNSETTLE_CUSTOMER_DATA);
    			return result;
        	}
        	//自然人保证人配偶是尚未结清的主借人配偶        	
        	if(coboMateUnSettleMap != null && coboMateUnSettleMap.get("role") == 2){
        		result.put("success", false);
        		result.put("flag","55");
    			result.put("message", ApplyInfoConstant.COBO_MATE_IN_UNSETTLE_MATE_DATA);
    			return result;
        	}
        	//自然人保证人配偶是尚未结清的共借人(旧版申请表)
        	if(coboMateUnSettleMap != null && coboMateUnSettleMap.get("role") == 3){
        		result.put("success", false);
        		result.put("flag","56");
    			result.put("message", ApplyInfoConstant.COBO_MATE_IN_UNSETTLE_COBO_DATA);
    			return result;
        	}
        	//自然人保证人配偶是尚未结清的自然人保证人(新版申请表)
        	if(coboMateUnSettleMap != null && coboMateUnSettleMap.get("role") == 4){
        		result.put("success", false);
        		result.put("flag","57");
    			result.put("message", ApplyInfoConstant.COBO_MATE_IN_UNSETTLE_BEST_NATURALGUARANTOR_DATA);
    			return result;
        	}
        	
        }
		//银行卡信息
		LoanBank loanBank=tempApplyInfo.getLoanBank();
		
		if(loanBank == null || StringUtils.isEmpty(loanBank.getId())){
			result.put("success", false);
			result.put("flag", "9");
			result.put("message", ApplyInfoConstant.BANK_NEED_SAVE);
			return result;
		}
		
		Integer bankIsRareword=loanBank.getBankIsRareword();
		String bankAccountName=loanBank.getBankAccountName();
		String customerName=loanCustomer.getCustomerName();
		if(bankIsRareword!=null && bankAccountName!=null){
			if(bankIsRareword==0){
				if(!customerName.equals(bankAccountName)){
					result.put("success", false);
					result.put("flag", "9");
					result.put("message", ApplyInfoConstant.BANK_ACCOUNTNAME_NO_DIFFERENCE);
					return result;
				}
			}else if(bankIsRareword==1){
				if(customerName.equals(bankAccountName)){
					result.put("success", false);
					result.put("flag", "9");
					result.put("message", ApplyInfoConstant.BANK_ACCOUNTNAME_DIFFERENCE);
					return result;
				}
			}
		}
		
		//简易借开户行校验
		if(LoanProductCode.PRO_JIAN_YI_JIE.equals(loanInfo.getProductType()) || LoanProductCode.PRO_NONG_XIN_JIE.equals(loanInfo.getProductType())){
			boolean flag = false;
			List<JyjBorrowBankConfigure> jyjBorrowBankList = jyjBorrowBankConfigureService.queryList(1, loanInfo.getProductType());
			for(JyjBorrowBankConfigure jyjBorrowBankConfigure : jyjBorrowBankList){
				if(jyjBorrowBankConfigure.getBankCode().equals(loanBank.getBankName())){
					flag = true;
					break;
				}
			}
			if(!flag){
				result.put("success", false);
				result.put("flag", "9");
				result.put("message", ApplyInfoConstant.BANK_ERROR);
				return result;
			}
		}
		
		//征信信息(提交到汇诚时校验)
		if(StringUtils.isNotEmpty(tempApplyInfo.getLoanInfo().getDictLoanStatus())){
			CreditReportRisk creditReportRisk = new CreditReportRisk();
			creditReportRisk.setLoanCode(tempApplyInfo.getLoanInfo().getLoanCode());
			List<CreditReportRisk> riskList = creditDetailedInfoService.getCreditReportDetailedByCode(creditReportRisk);
			String loanCustomerCredit = null;
			String naturalGuarantorCredit = null;
			if(riskList!=null && riskList.size()>0){
				for (CreditReportRisk reportRisk : riskList) {
					String dictCustomerType = reportRisk.getDictCustomerType();
					if(dictCustomerType.equals("0")){
						loanCustomerCredit = "0";
					}else if(dictCustomerType.equals("1")){
						naturalGuarantorCredit = "1";
					}
				}
				if(StringUtils.isEmpty(loanCustomerCredit)){
					result.put("success", false);
					result.put("message", ApplyInfoConstant.LOAN_CUSTOMER_CREDIT_EMPTY);
					return result;
				}
				if(loanCoborrowerList!=null && loanCoborrowerList.size()>0 && StringUtils.isNotEmpty(loanCoborrowerList.get(0).getId())){
					if(StringUtils.isEmpty(naturalGuarantorCredit)){
						result.put("success", false);
						result.put("message", ApplyInfoConstant.NATURAL_GUARANTOR_CREDIT_EMPTY);
						return result;
					}
				}
			}else{
				result.put("success", false);
				result.put("message", ApplyInfoConstant.LOAN_CUSTOMER_CREDIT_EMPTY);
				return result;
			}
		}

        result.put("success", true);
        result.put("flag","10");
        result.put("message",ApplyInfoConstant.SUCCESS_MESSAGE);
		
        return result;
    }
    /**
     * 待确认签署办理页面提交时检查客户姓名和银行账户名的一致性
     */
    public Map<String, Object> validateConfirmSignAccountName(String loanCode, Integer bankIsRareword, String bankAccountName) {
    	Map<String,Object> result = new HashMap<String,Object>();
    	result.put("success", true);
    	
    	HashMap<String, Object> param=new HashMap<String, Object>();
    	param.put("loanCode", loanCode);
    	LoanCustomer loanCustomer=loanCustomerDao.selectByLoanCode(param);
    	String customerName=loanCustomer.getCustomerName();
    	
    	if(ObjectHelper.isEmpty(bankIsRareword)){
			if(!customerName.equals(bankAccountName)){
				result.put("success", false);
				result.put("message", ApplyInfoConstant.BANK_ACCOUNTNAME_NO_DIFFERENCE);
				return result;
			}
		}else if(bankIsRareword==1){
			if(customerName.equals(bankAccountName)){
				result.put("success", false);
				result.put("message", ApplyInfoConstant.BANK_ACCOUNTNAME_DIFFERENCE);
				return result;
			}
		}
    	return result;
    }
    
    /**
     * 服务调用：检查影像资料、个人信用体检报告费用
     */
    public Map<String, Object> checkService(String loanCode) {
    	Map<String,Object> result = new HashMap<String,Object>(); //返回参数
    	//影像文件夹校验
    	result = this.validateFileCategoryEmpty(loanCode);
    	//检查个人信用体检报告费
    	if((Boolean) result.get("success")){
    		result = this.checkPhysicalFee(loanCode);
    	}
    	return result;
    }
    /**
     * 影像文件夹校验
     */
    public Map<String, Object> validateFileCategoryEmpty(String loanCode) {
    	Map<String,Object> result = new HashMap<String,Object>(); //返回参数
    	
    	Map<String, Object> param=new HashMap<String, Object>();
    	param.put("loanCode", loanCode);
    	LoanInfo loanInfo=applyLoanInfoDao.selectByLoanCode(param);
    	Date customerIntoTime=loanInfo.getCustomerIntoTime();
    	//获取汇金影像插件的索引与部件
		Map<String,String> diskMap = diskInfoDao.getIndexComponentByQueryTime(DateUtils.formatDate(customerIntoTime, "yyyyMMdd"), "0");
		//影像插件文件夹是否为空判断
		ClientPoxy service = new ClientPoxy(ServiceType.Type.IMG_GET_EXIST_IMG_BARCODE);
		Img_GetExistImgBarCodeListInBean inParam = new Img_GetExistImgBarCodeListInBean();
		inParam.setIndex(diskMap.get("image_index_hj"));
		inParam.setParts(diskMap.get("image_component_hj"));
		inParam.setBatchNo(loanCode);
		if (customerIntoTime!=null) {
			inParam.setSerachDate(customerIntoTime);
		} else {
			try {
				//进件时间为空，截取借款编号中的年月日
				inParam.setSerachDate(DateUtils.parseDate(loanCode.substring(2, 10), "yyyyMMdd"));
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ServiceException(e);
			}
		}
		//影像系统_取得有有影像数据的文件夹的id集合
		Img_GetExistImgBarCodeListOutBean outParam = (Img_GetExistImgBarCodeListOutBean) service.callService(inParam);
		//不为空的影像文件夹的id集合
		String barCodesString = "";
		//接口返回
		if(ReturnConstant.SUCCESS.equals(outParam.getRetCode())){
			if(ArrayHelper.isNotEmpty(outParam.getBarCodeList())){
				barCodesString = outParam.getBarCodeList().toString();
				//影像文件夹是否为空的信息存到一个实体类中
				FileCategoryEmpty fileCategoryEmpty=this.setFileCategoryEmpty(barCodesString,loanInfo.getLoanInfoOldOrNewFlag());
				//查询申请资料信息(******汇金影像校验在新版申请表上开发，所以这里调用新版申请表查询方法*****)
				ApplyInfoFlagEx applyInfo = dataEntryService.getAllInfoByLoanCode_new(loanCode);
				//影像校验规则
				result=this.getFileCategoryVerifyResult(fileCategoryEmpty,applyInfo);
			}else{
				result.put("success", false);
				result.put("message", "请上传影像资料！");
				return result;
			}
		}else{
			result.put("success", false);
			result.put("message", "影像校验异常！");
			return result;
		}
		
		return result;
	}
    
	/**
     * 影像文件夹是否为空的信息封装到实体类中
     */
    private FileCategoryEmpty setFileCategoryEmpty(String unEmptyCatoryIds, String loaninfoOldornewFlag) {
    	FileCategoryEmpty fileCategoryEmpty=new FileCategoryEmpty();
    	
    	// 居住证明及资产证明 中的 居住证明文件夹为空
		fileCategoryEmpty.setLivingMaterialEmpty(unEmptyCatoryIds
				.indexOf(FileCategoryType.LIVING_MATERIAL.getCode()) > -1 ? false : true);
		// 居住证明及资产证明 中的 房产证明文件夹为空
		fileCategoryEmpty.setRealEastateEmpty(unEmptyCatoryIds
				.indexOf(FileCategoryType.REAL_EASTATE.getCode()) > -1 ? false : true);
		// 婚姻证明文件夹为空
		fileCategoryEmpty.setMarriageDocEmpty(unEmptyCatoryIds
				.indexOf(FileCategoryType.MARRIAGE_DOC.getCode()) > -1 ? false : true);
		// 身份证明文件夹空
		fileCategoryEmpty.setIdentityDocEmpty(unEmptyCatoryIds.
				indexOf(FileCategoryType.IDENTITY_DOC.getCode()) > -1 ? false : true);
		// 申请证明文件夹空
		fileCategoryEmpty.setApplyMaterialEmpty(unEmptyCatoryIds
				.indexOf(FileCategoryType.APPLY_MATERIAL.getCode()) > -1 ? false : true);
		// 工作证明文件夹为空
		fileCategoryEmpty.setWorkDocEmpty(unEmptyCatoryIds
				.indexOf(FileCategoryType.WORK_DOC.getCode()) > -1 ? false : true);
		// 经营证明文件夹为空
		fileCategoryEmpty.setManageDocEmpty(unEmptyCatoryIds
						.indexOf(FileCategoryType.MANAGE_DOC.getCode()) > -1 ? false : true);
		// 征信报告 企业征信文件夹为空
    	fileCategoryEmpty.setCreditEnterpriseEmpty(unEmptyCatoryIds
    					.indexOf(FileCategoryType.CREDIT_REPORT_ENTERPRISE.getCode()) > -1 ? false : true);
		// 征信报告文件夹 个人详版 为空
		fileCategoryEmpty.setCreditReportDetailEmpty(unEmptyCatoryIds
						.indexOf(FileCategoryType.CREDIT_REPORT_DETAIL.getCode()) > -1 ? false : true);
		// 征信报告文件夹 个人简版 为空
		fileCategoryEmpty.setCreditReportSimpleEmpty(unEmptyCatoryIds
						.indexOf(FileCategoryType.CREDIT_REPORT_SIMPLE.getCode()) > -1 ? false : true);
		// 常用储蓄文件夹为空
		fileCategoryEmpty.setBankSavingsEmpty(unEmptyCatoryIds
				.indexOf(FileCategoryType.BANK_SAVINGS.getCode()) > -1 ? false : true);
		// 对公流水文件夹为空
		fileCategoryEmpty.setCompanyBankAccountEmpty(unEmptyCatoryIds
						.indexOf(FileCategoryType.COMPANY_BANK_ACCOUNT.getCode()) > -1 ? false : true);
		// 工资流水文件夹
		fileCategoryEmpty.setSalaryDocEmpty(unEmptyCatoryIds
						.indexOf(FileCategoryType.SALARY_DOC.getCode()) > -1 ? false : true);
		// 其他文件夹空
		fileCategoryEmpty.setOtherDocEmpty(unEmptyCatoryIds
				.indexOf(FileCategoryType.OTHER_DOC.getCode()) > -1 ? false : true);
		if ("0".equals(loaninfoOldornewFlag)) {
			// 共同借款人文件夹为空
			fileCategoryEmpty.setCoborrowDocEmpty(unEmptyCatoryIds
							.indexOf(FileCategoryType.COBORROW_DOC.getCode()) > -1 ? false : true);
			// 自然人保证人文件夹为空
			fileCategoryEmpty.setNaturalPersonDocEmpty(false);
			// 法人保证人文件夹为空
			fileCategoryEmpty.setLegalPersonDocEmpty(false);
		} else if ("1".equals(loaninfoOldornewFlag)){
			// 共同借款人文件夹为空
			fileCategoryEmpty.setCoborrowDocEmpty(false);
			// 自然人保证人文件夹为空
			fileCategoryEmpty.setNaturalPersonDocEmpty(unEmptyCatoryIds
							.indexOf(FileCategoryType.NATURALPERSON_DOC.getCode()) > -1 ? false : true);
			// 法人保证人文件夹为空
			fileCategoryEmpty.setLegalPersonDocEmpty(unEmptyCatoryIds
							.indexOf(FileCategoryType.LEGALPERSON_DOC.getCode()) > -1 ? false : true);
		}
		return fileCategoryEmpty;		
	}
    
    /**
     * 影像校验规则(请参加文档：汇金\信借五期\12月\汇金文件夹判断.doc，注释太多不再累述)
     * @param fileCategoryEmpty
     * @return
     */
    private Map<String, Object> getFileCategoryVerifyResult(FileCategoryEmpty fileCategoryEmpty, ApplyInfoFlagEx applyInfo) {
    	Map<String, Object> result=new HashMap<String, Object>();
    	result.put("success", true);
    	
    	//获取产品类型
    	String productType=applyInfo.getLoanInfo().getProductType();
    	//查询主借人征信核查表判断主借人是详版征信还是简版征信
    	CreditReportRisk creditReportRisk = new CreditReportRisk();
		creditReportRisk.setLoanCode(applyInfo.getLoanInfo().getLoanCode());
		creditReportRisk.setDictCustomerType("0"); //主借人
		creditReportRisk.setrId(applyInfo.getLoanCustomer().getId());
    	List<CreditReportRisk> riskList = creditDetailedInfoService.getCreditReportDetailedByCode(creditReportRisk); //查询主借人的，查出来只有一条记录
    	//征信报告版本（详版还是简版）
    	String riskCreditVersion=null;
    	if(riskList!=null && riskList.size()>0){
    		riskCreditVersion=riskList.get(0).getRiskCreditVersion();
    	}
    	
    	//房产证明文件夹为空
    	/*if(fileCategoryEmpty.isRealEastateEmpty()){
    		if(productType.equals(LoanProductCode.PRO_YFJ) || productType.equals(LoanProductCode.PRO_LYJ)){
    			result.put("success", false);
    			result.put("message", "请补充主借人房产证明");
    			return result;
    		}
    	}*/
    	//房产证明文件夹为空
    	//(需求变动：只要填写了房产信息页签则房产证明文件夹不能为空不管什么产品,汇金\信借六期期\17年2月\所有产品类型验证房产证明文件夹是否为空需求-席蓓蓓.doc)
    	if(fileCategoryEmpty.isRealEastateEmpty()){
    		List<LoanHouse> loanHouses = applyInfo.getCustomerLoanHouseList();
    		if(ObjectHelper.isNotEmpty(loanHouses) && ObjectHelper.isNotEmpty(loanHouses.get(0)) && StringUtils.isNotEmpty(loanHouses.get(0).getId())){
    			result.put("success", false);
    			result.put("message", "请补充主借人房产证明");
    			return result;
    		}
    	}
    	//婚姻证明文件夹为空
    	if(fileCategoryEmpty.isMarriageDocEmpty()){
    		if(productType.equals(LoanProductCode.PRO_XWQYJ) || productType.equals(LoanProductCode.PRO_LBJ)){
    			String dictMarry=applyInfo.getLoanCustomer().getDictMarry();
    			if(dictMarry.equals("1") || dictMarry.equals("2")){
    				result.put("success", false);
        			result.put("message", "经营类产品，申请人非未婚，请提供婚姻证明");
        			return result;
    			}
    		}
    	}
    	//工作证明文件夹为空
    	if(fileCategoryEmpty.isWorkDocEmpty()){
    		if(productType.equals(LoanProductCode.PRO_YKJ) || productType.equals(LoanProductCode.PRO_YFJ) || 
    				productType.equals(LoanProductCode.PRO_JYJ_A) || productType.equals(LoanProductCode.PRO_JYJ_B) || 
    				productType.equals(LoanProductCode.PRO_XSJ) || productType.equals(LoanProductCode.PRO_JYJ)){
    			result.put("success", false);
    			result.put("message", "请上传申请人工作证明");
    			return result;
    		}
    	}
    	//经营证明文件夹为空
    	if(fileCategoryEmpty.isManageDocEmpty()){
    		if(productType.equals(LoanProductCode.PRO_XWQYJ) || productType.equals(LoanProductCode.PRO_LBJ)){
    			result.put("success", false);
    			result.put("message", "请上传申请人经营证明");
    			return result;
    		}
    	}
    	//常用储蓄文件夹为空
    	if(fileCategoryEmpty.isBankSavingsEmpty()){
    		if(productType.equals(LoanProductCode.PRO_LBJ)){
    			if(fileCategoryEmpty.isCompanyBankAccountEmpty()){
    				result.put("success", false);
        			result.put("message", "请上传主借人常用储蓄或对公流水");
        			return result;
    			}
    		}
    		if(productType.equals(LoanProductCode.PRO_XSJ)){
    			if(fileCategoryEmpty.isSalaryDocEmpty()){
    				result.put("success", false);
        			result.put("message", "请上传主借人常用储蓄或工资流水");
        			return result;
    			}
    		}
    		if(productType.equals(LoanProductCode.PRO_XWQYJ)){
    			result.put("success", false);
    			result.put("message", "请上传主借人常用储蓄和对公流水");
    			return result;
    		}
    	}
    	//对公流水文件夹为空
    	if(fileCategoryEmpty.isCompanyBankAccountEmpty()){
    		if(productType.equals(LoanProductCode.PRO_XWQYJ)){
    			result.put("success", false);
    			result.put("message", "请上传主借人常用储蓄和对公流水");
    			return result;
    		}
    	}
    	//工资流水文件夹为空
    	if(fileCategoryEmpty.isSalaryDocEmpty()){
    		if(productType.equals(LoanProductCode.PRO_YKJ) || productType.equals(LoanProductCode.PRO_YFJ) ||  
    				productType.equals(LoanProductCode.PRO_JYJ_A) || productType.equals(LoanProductCode.PRO_JYJ_B) || 
    				productType.equals(LoanProductCode.PRO_JYJ) || productType.equals(LoanProductCode.PRO_XYJ)){
    			result.put("success", false);
    			result.put("message", "请上传主借人工资流水");
    			return result;
    		}
    	}
    	//企业征信文件夹为空
    	if(fileCategoryEmpty.isCreditEnterpriseEmpty()){
    		if(productType.equals(LoanProductCode.PRO_XWQYJ)){
    			result.put("success", false);
    			result.put("message", "检测到企业征信文件夹未上传资料，请补充主借人企业征信报告至该文件夹或转为老板借申请");
    			return result;
    		}
    	}
    	//居住证明文件夹为空
    	if(fileCategoryEmpty.isLivingMaterialEmpty()){
    		if(!productType.equals(LoanProductCode.PRO_XYJ)){
    			if(fileCategoryEmpty.isRealEastateEmpty()){
    				result.put("success", false);
        			result.put("message", "请补充主借人居住证明或房产证明");
        			return result;
    			}
    		}
    	}
    	//身份证明文件夹为空
    	if(fileCategoryEmpty.isIdentityDocEmpty()){
    		result.put("success", false);
			result.put("message", "请上传申请人身份证明");
			return result;
    	}
    	//申请证明文件夹为空
    	if(fileCategoryEmpty.isApplyMaterialEmpty()){
    		if(!productType.equals(LoanProductCode.PRO_JIAN_YI_JIE)){
    			result.put("success", false);
    			result.put("message", "请上传申请证明");
    			return result;
    		}
    	}
    	//个人简版文件夹为空
    	if(fileCategoryEmpty.isCreditReportSimpleEmpty()){
    		if(!productType.equals(LoanProductCode.PRO_JIAN_YI_JIE)){
    			if(riskCreditVersion!=null && riskCreditVersion.equals("2")){
        			CreditReportSimple simple=new CreditReportSimple();
        			simple.setLoanCode(applyInfo.getLoanInfo().getLoanCode());
        			simple.setDictCustomerType("0"); //主借人
        			simple.setrCustomerCoborrowerId(applyInfo.getLoanCustomer().getId());
        			CreditReportSimple creditReportSimple = creditReportSimpleService.simpInit(simple);
        			if(creditReportSimple!=null){
        				result.put("success", false);
        				result.put("message", "请上传主借人个人简版征信报告");
        				return result;
        			}
        		}
    		}
    	}
    	//个人详版文件夹为空
    	if(fileCategoryEmpty.isCreditReportDetailEmpty()){
    		if(!productType.equals(LoanProductCode.PRO_JIAN_YI_JIE)){
    			if(riskCreditVersion!=null && riskCreditVersion.equals("1")){
        			CreditReportDetailed detail=new CreditReportDetailed();
        			detail.setLoanCode(applyInfo.getLoanInfo().getLoanCode());
        			detail.setDictCustomerType("0"); //主借人
        			detail.setrCustomerCoborrowerId(applyInfo.getLoanCustomer().getId());
        			CreditReportDetailed detailInfo = creditReportDetailedDao.getIdByParam(detail);
        			if(detailInfo!=null){
        				result.put("success", false);
        				result.put("message", "请上传主借人个人详版征信报告");
        				return result;
        			}
        		}
    		}
    	}
    	//共借人文件夹为空
    	if(fileCategoryEmpty.isCoborrowDocEmpty()){
    		if(applyInfo.getLoanInfo().getLoanInfoOldOrNewFlag().equals("0")){
    			List<LoanCoborrower> loanCoborrowerList=applyInfo.getLoanCoborrower();
    			if(loanCoborrowerList!=null && loanCoborrowerList.size()>0 && loanCoborrowerList.get(0).getId()!=null){
    				result.put("success", false);
        			result.put("message", "提供共借人，请上传共借人资料");
        			return result;
    			}
    		}
    	}
    	//自然人保证人文件夹为空
    	if(fileCategoryEmpty.isNaturalPersonDocEmpty()){
    		if(applyInfo.getLoanInfo().getLoanInfoOldOrNewFlag().equals("1")){
    			List<LoanCoborrower> loanCoborrowerList=applyInfo.getLoanCoborrower();
    			if(loanCoborrowerList!=null && loanCoborrowerList.size()>0 && loanCoborrowerList.get(0).getId()!=null){
    				result.put("success", false);
        			result.put("message", "提供自然人保证人，请上传自然人保证人资料");
        			return result;
    			}
    		}
    	}
    	//法人保证人的文件夹为空
    	if(fileCategoryEmpty.isLegalPersonDocEmpty()){
    		BigDecimal loanApplyAmount=applyInfo.getLoanInfo().getLoanApplyAmount();
    		if(loanApplyAmount.compareTo(new BigDecimal(300000))==1){ //根据申请金额判断：申请金额大于30万 法人保证人必填
    			result.put("success", false);
    			result.put("message", "提供法人保证人，请上传法人保证人资料");
    			return result;
    		}
    	}
		return result;
	}
    
    /**
     * 检查个人信用体检报告费
     */
    public Map<String, Object> checkPhysicalFee(String loanCode) {
    	Map<String,Object> result = new HashMap<String,Object>(); //返回参数
    	Map<String, Object> param=new HashMap<String, Object>();
    	param.put("loanCode", loanCode);
    	//loanCustomer
    	LoanCustomer loanCustomer=loanCustomerDao.selectByLoanCode(param);
    	String customerTelesalesFlag=loanCustomer.getCustomerTelesalesFlag();
    	String customerCertNum=loanCustomer.getCustomerCertNum();
    	//loanInfo
    	LoanInfo loanInfo=applyLoanInfoDao.selectByLoanCode(param);
		//Date customerIntoTime=loanInfo.getCustomerIntoTime();
		if(LoanProductCode.PRO_NONG_XIN_JIE.equals(loanInfo.getProductType())){
			result.put("success", true);
			return result;
		}
    	//只检查门店进件
//    	if(!"0".equals(customerTelesalesFlag)){
//    		result.put("success", true);
//			return result;
//    	}
    	//只检查门店复核状态提交
    	if(!LoanApplyStatus.STORE_REVERIFY.getCode().equals(loanInfo.getDictLoanStatus())){
    		result.put("success", true);
			return result;
    	}
    	//接口调用
    	ClientPoxy service = new ClientPoxy(ServiceType.Type.WECHAT_PAYRESULT_SERVICE);
		ApproveWeChatPayResultInBean inBean = new ApproveWeChatPayResultInBean();
		inBean.setIdNum(customerCertNum); 
		ApproveWeChatPayResultOutBean outBean = (ApproveWeChatPayResultOutBean) service.callService(inBean); 
		//接口返回
		if(ReturnConstant.SUCCESS.equals(outBean.getRetCode())){
			if("1".equals(outBean.getPayStatus())){ //1表示已支付
				String payDate = outBean.getPayDate();
				if(StringUtils.isNotEmpty(payDate)){
					try {
						String payTimeParsePattern="yyyyMMdd";
						
						Date payTime = DateUtils.parseDate(DateUtils.formatDate(DateUtils.parseDate(payDate), payTimeParsePattern), payTimeParsePattern);
						Calendar payTimeCalendar = Calendar.getInstance();
						Calendar customerIntoCalendar = Calendar.getInstance();
						
						payTimeCalendar.setTime(payTime);
						customerIntoCalendar.setTime(DateUtils.parseDate(DateUtils.formatDate(new Date(), payTimeParsePattern),payTimeParsePattern));
						
						payTimeCalendar.add(Calendar.DAY_OF_MONTH, 15 - 1);
						
						if(payTimeCalendar.compareTo(customerIntoCalendar) < 0){
							result.put("success", false);
							result.put("message", "对不起，客户支付时间与进件时间已大于15天，请重新交付后在提交！");
							return result;
						}
						result.put("success", true);
					} catch (ParseException e) {
						e.printStackTrace();
						logger.error(e.getMessage());
						throw new ServiceException(e);
					}
				}else{
					throw new ServiceException("调用个人信用体检报告费用接口返回字符时间错误！");
				}
			}else{
				result.put("success", false);
				result.put("message", "对不起，客户未支付个人信用体检报告费用，请交付后再提交！");
				return result;
			}
		}else if(ReturnConstant.FAIL.equals(outBean.getRetCode())){
			result.put("success", false);
			result.put("message", "对不起，客户未支付个人信用体检报告费用，请交付后再提交！");
			return result;
		}else{
			result.put("success", false);
			result.put("message", "个人信用体检报告费用校验异常！");
			return result;
		}
		
    	return result;
    }
    
    
    /**
     *根据身份证号获取客户的出生日期
     *@author zhangho
     *@create in 2016年1月29日 
     *@param cardNumber 
     *@return  String 
     */
    private String getBirthDateFromCard(String cardNumber){
        String card = cardNumber.trim();
        String year;
        String month;
        String day;
        if(card.length()==18){
            year = card.substring(6, 10);
            month = card.substring(10, 12);
            day = card.substring(12, 14);
        }else{
            year = card.substring(6, 8);
            month = card.substring(8, 10);
            day = card.substring(10, 12);
            year = "19"+year;
        }
        if(month.length()==1){
            month="0"+month;
        }
        if(day.length()==1){
            day="0"+day;
        }
        return year+"-"+month+"-"+day;
    }
}
