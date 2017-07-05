/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.serviceImageService.java
 * @Create By 张灏
 * @Create In 2016年3月15日 下午8:25:26
 */
package com.creditharmony.loan.car.common.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.core.lend.type.LoanManFlag;
import com.creditharmony.loan.car.common.dao.CarChangerInfoDao;
import com.creditharmony.loan.car.common.dao.CarCustomerDao;
import com.creditharmony.loan.car.common.dao.CarLoanCoborrowerDao;
import com.creditharmony.loan.car.common.entity.CarChangerInfo;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;

/**
 * 变更历史Service
 * 
 * @Class Name CarChangerInfoService
 * @author 葛志超
 * @Create In 2016年6月25日
 */
@Service
public class CarChangerInfoService {
	
	@Autowired
	private CarChangerInfoDao carChangerInfoDao;
	@Autowired
	private CarCustomerDao carCustomerDao;
	@Autowired
	private CarLoanCoborrowerDao carLoanCoborrowerDao;
	
	// 插入主借人信息变更记录
	@Transactional(readOnly = false, value = "loanTransactionManager")
    public  void insertCustomer(CarCustomer lastCarCustomer,CarCustomer carCustomer,CarCustomerBase carCustomerBase,String applyId){
		try{
			
		        //新建变更记录
		        CarChangerInfo info = null;
		        if(carChangerInfoDao.selectByUpdateId(carCustomer.getId()).size()>0){
		        	 info = carChangerInfoDao.selectByUpdateId(carCustomer.getId()).get(0);
		        }
				if(null!=info){
					if(!info.getMobileChangeAfter().equals(carCustomerBase.getCustomerMobilePhone())||
							!info.getMeailChangeAfter().equals(carCustomer.getCustomerEmail())){
						if(null!=info.getMobileChangeAfter()&&!info.getMobileChangeAfter().equals(carCustomerBase.getCustomerMobilePhone())){
							if(null!=info.getMobileChangeAfter()){
								info.setMobileChangeBegin(info.getMobileChangeAfter());
							}
							//if(null!=info.getMeailChangeAfter()){
								info.setMobileChangeAfter(carCustomerBase.getCustomerMobilePhone());
							//}
								CarCustomer cacus = new CarCustomer();
								cacus.setCustomerPin("");
								cacus.setCaptchaIfConfirm("0");
								cacus.setLoanCode(carCustomer.getLoanCode());
								carCustomerDao.update(cacus);
						}
						if(null!=info.getMeailChangeAfter()&&!info.getMeailChangeAfter().equals(carCustomer.getCustomerEmail())){
							info.setEmailChangeBegin(info.getMeailChangeAfter());
							info.setMeailChangeAfter(carCustomer.getCustomerEmail());
						}
						info.preInsert();
						carChangerInfoDao.insertChangerInfo(info);
					}
				}else{
					info = new CarChangerInfo();
			        info.setApplyId(applyId);
			        info.setChangeCode(carCustomer.getLoanCode());
			        info.setChangeType(LoanManFlag.MAIN_LOAN.getCode());
			        info.setUpdateId(carCustomer.getId());
			        info.setDealFlag("0");   // 修改
					info.setMobileChangeBegin(lastCarCustomer.getCustomerPhoneFirst());
			        info.setEmailChangeBegin(lastCarCustomer.getCustomerEmail());
			        info.setMobileChangeAfter(lastCarCustomer.getCustomerPhoneFirst());
			        info.setMeailChangeAfter(lastCarCustomer.getCustomerEmail());
		        	if(null!=carCustomerBase.getIsEmailModify()&&carCustomerBase.getIsEmailModify().equals("1")){
				        if(!carCustomer.getCustomerEmail().equals(lastCarCustomer.getCustomerEmail())){
				        	info.setMeailChangeAfter(carCustomer.getCustomerEmail());
				        }
			        }
			        if(null!=carCustomerBase.getIsTelephoneModify()&&carCustomerBase.getIsTelephoneModify().equals("1")){
			        	 if(!carCustomerBase.getCustomerMobilePhone().equals(lastCarCustomer.getCustomerPhoneFirst())){
			        		 info.setMobileChangeAfter(carCustomerBase.getCustomerMobilePhone());
					        		CarCustomer cacus = new CarCustomer();
									cacus.setCustomerPin("");
									cacus.setCaptchaIfConfirm("0");
									cacus.setLoanCode(carCustomer.getLoanCode());
									carCustomerDao.update(cacus);
					        }
			        	
			        }
			        if(!carCustomer.getCustomerEmail().equals(lastCarCustomer.getCustomerEmail())||
			        		!carCustomerBase.getCustomerMobilePhone().equals(lastCarCustomer.getCustomerPhoneFirst())){
			        	info.preInsert();
						carChangerInfoDao.insertChangerInfo(info);
			        }
				}	
		}catch(Exception e){
					
		}
        
    }
    
    // 插入共借人信息变更记录
	@Transactional(readOnly = false, value = "loanTransactionManager")
    public  void insertCoborrower(CarLoanCoborrower carLoanCoborrower,String applyId){
		try{
	        //新建变更记录
			CarChangerInfo info = null;
	        if(carChangerInfoDao.selectByUpdateId(carLoanCoborrower.getId()).size()>0){
	        	 info = carChangerInfoDao.selectByUpdateId(carLoanCoborrower.getId()).get(0);
	        }
			if(null!=info){
				if((null!=carLoanCoborrower.getIsemailmodify()&&carLoanCoborrower.getIsemailmodify().equals("1"))||(null!=carLoanCoborrower.getIstelephonemodify()&&carLoanCoborrower.getIstelephonemodify().equals("1"))){
					if((null!=info.getMobileChangeAfter()&&!carLoanCoborrower.getMobile().equals(info.getMobileChangeAfter()))||
							(null!=info.getMeailChangeAfter()&&!info.getMeailChangeAfter().equals(carLoanCoborrower.getEmail()))){
						if(null!=info.getMeailChangeAfter()&&carLoanCoborrower.getIsemailmodify().equals("1")&&!info.getMeailChangeAfter().equals(carLoanCoborrower.getEmail())){
							info.setEmailChangeBegin(info.getMeailChangeAfter());
							info.setMeailChangeAfter(carLoanCoborrower.getEmail());
						}
						if(null!=info.getMobileChangeAfter()&&carLoanCoborrower.getIstelephonemodify().equals("1")&&!carLoanCoborrower.getMobile().equals(info.getMobileChangeAfter())){
							info.setMobileChangeBegin(info.getMobileChangeAfter());
							info.setMobileChangeAfter(carLoanCoborrower.getMobile());
								CarLoanCoborrower cobo =new CarLoanCoborrower();
								cobo.setId(carLoanCoborrower.getId());
								cobo.setCustomerPin("");
								cobo.setCaptchaIfConfirm("0");
								carLoanCoborrowerDao.update(cobo);
						}
						info.preInsert();
						carChangerInfoDao.insertChangerInfo(info);
					}
				}
			}else{
				info = new CarChangerInfo();
		        info.setApplyId(applyId);
		        info.setChangeCode(carLoanCoborrower.getLoanCode());
		        info.setChangeType(LoanManFlag.COBORROWE_LOAN.getCode());
		        info.setUpdateId(carLoanCoborrower.getId());
		        info.setDealFlag("0");   // 修改
		        info.setMobileChangeBegin(carLoanCoborrower.getMobile());
		        info.setEmailChangeBegin(carLoanCoborrower.getEmail());
		        info.setMobileChangeAfter(carLoanCoborrower.getMobile());
		        info.setMeailChangeAfter(carLoanCoborrower.getEmail());
		        if((null!=carLoanCoborrower.getIsemailmodify()&&carLoanCoborrower.getIsemailmodify().equals("1"))||(null!=carLoanCoborrower.getIstelephonemodify()&&carLoanCoborrower.getIstelephonemodify().equals("1"))){
					if(null!=carLoanCoborrower.getIsemailmodify()&&carLoanCoborrower.getIsemailmodify().equals("1")){
						info.setEmailChangeBegin(carLoanCoborrower.getEmail());
						info.setMeailChangeAfter(carLoanCoborrower.getEmail());
					}
					if(null!=carLoanCoborrower.getIstelephonemodify()&&carLoanCoborrower.getIstelephonemodify().equals("1")){
						info.setMobileChangeBegin(carLoanCoborrower.getMobile());
						info.setMobileChangeAfter(carLoanCoborrower.getMobile());
					}
						CarLoanCoborrower cobo =new CarLoanCoborrower();
						cobo.setId(carLoanCoborrower.getId());
						cobo.setCustomerPin("");
						cobo.setCaptchaIfConfirm("0");
						carLoanCoborrowerDao.update(cobo);
					info.preInsert();
					carChangerInfoDao.insertChangerInfo(info);
		        }
			}
		}catch(Exception e){
			
		}

   }
	
}
