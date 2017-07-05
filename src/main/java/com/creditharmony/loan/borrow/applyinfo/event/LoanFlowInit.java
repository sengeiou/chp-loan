/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.applyinfo.eventLoanFlowInit.java
 * @Create By 张灏
 * @Create In 2015年12月15日 下午1:29:41
 */
package com.creditharmony.loan.borrow.applyinfo.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.InitViewData;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.loan.type.SexFlag;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.applyinfo.service.DataEntryService;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.consult.dao.ConsultDao;
import com.creditharmony.loan.borrow.consult.entity.Consult;
import com.creditharmony.loan.borrow.consult.service.CustomerManagementService;
import com.creditharmony.loan.borrow.consult.view.ConsultSearchView;
import com.creditharmony.loan.common.consts.CityInfoConstant;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.TokenUtils;

/**
 * @Class Name LoanFlowInit
 * @author 张灏
 * @Create In 2015年12月15日
 */
@Service("init_hj_loanFlow")
public class LoanFlowInit extends BaseService implements InitViewData {

   @Autowired
   private DataEntryService dataEntryService;
   
   @Autowired
   private CityInfoDao cityInfoDao;
   
   @Autowired
   private ApplyLoanInfoDao applyLoanInfoDao;
   
   @Autowired
   private ConsultDao consultDao;
   
   
   
   
    @SuppressWarnings("rawtypes")
	@Override
    public BaseBusinessView initViewData(Map parameterMap) {
    	//客户编号
        String[] customerCodes =(String[]) parameterMap.get("customerCode"); 
        //咨询ID
        String[] consultIds = (String[])parameterMap.get("consultId");
        //
        String[] messages = (String[])parameterMap.get("message");
        //
        String[] defTokenIds = (String[])parameterMap.get("defTokenIds");
        
        LaunchView launchView = new LaunchView();
        if(customerCodes!=null && customerCodes.length>0){
            Map<String,String> initParam = new HashMap<String,String>();
            initParam.put("customerCode",customerCodes[0]);
            initParam.put("consultId", consultIds[0]);
            LoanInfo loanInfoObj=applyLoanInfoDao.selectByConsultId(initParam);
            ApplyInfoFlagEx applyInfo = null;
            if(!ObjectHelper.isEmpty(loanInfoObj)){
            	String loanInfoOldOrNewFlag=loanInfoObj.getLoanInfoOldOrNewFlag();
            	if(loanInfoOldOrNewFlag.equals("0")){
                	initParam.put("flag", ApplyInfoConstant.APPLY_INFO_CUSTOMER);
                	applyInfo = dataEntryService.getCustumerData(initParam);
                }else if(loanInfoOldOrNewFlag.equals("1")){
                	initParam.put("flag", ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);
                	applyInfo = dataEntryService.getCustumerData_new(initParam);
                }
            }else{
            	initParam.put("flag", ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);
            	applyInfo = dataEntryService.getCustumerData_new(initParam);
            }
            if(StringUtils.isEmpty(applyInfo.getCustomerSex())){
                String certNum = applyInfo.getMateCertNum();
                String num = null;
                if(certNum.length()==15){
                    num= certNum.substring(certNum.length()-1);  
                }
                if(certNum.length()==18){
                    num= certNum.substring(certNum.length()-2,certNum.length()-1);
                }
                int remain = Integer.valueOf(num)%2;
                if(remain==1){
                    applyInfo.setCustomerSex(SexFlag.MALE.getCode());
                }
                if(remain==0){
                    applyInfo.setCustomerSex(SexFlag.FEMALE.getCode());
                }
            }
            BeanUtils.copyProperties(applyInfo, launchView);
            Consult consult = consultDao.get(consultIds[0]);
            LoanCustomer loanCustomer = launchView.getLoanCustomer();
            // 抓取电销标识
            if(StringUtils.isEmpty(loanCustomer.getCustomerTelesalesFlag())){
                loanCustomer.setCustomerTelesalesFlag(consult.getConsTelesalesFlag());
            }
            // 抓取电销来源
            if(StringUtils.isEmpty(loanCustomer.getCustomerTeleSalesSource())){
                loanCustomer.setCustomerTeleSalesSource(consult.getConsTelesalesSource());  
            }
            if(StringUtils.isNotEmpty(loanCustomer.getCustomerName())){
                launchView.setCustomerName(loanCustomer.getCustomerName());
            }
            loanCustomer.setCustomerCode(customerCodes[0]);
            if(StringUtils.isEmpty(loanCustomer.getLoanCode())){
              LoanInfo loanInfo =  applyLoanInfoDao.selectByConsultId(initParam);
              if(!ObjectHelper.isEmpty(loanInfo)){
                 String loanCode = loanInfo.getLoanCode();
                 if(StringUtils.isNotEmpty(loanCode)){
                     loanCustomer.setLoanCode(loanCode);
                 }
              }
            }
            launchView.setLoanCustomer(loanCustomer);
            launchView.setLoanInfo(loanInfoObj);
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("parentId", CityInfoConstant.ROOT_ID);
            List<CityInfo> provinceList = cityInfoDao.findByParams(params);
            launchView.setProvinceList(provinceList);
            //launchView.getLoanInfo().setLoanCode();
            //借么标识
            if(!ObjectHelper.isEmpty(consult.getIsBorrow())){
            	launchView.setIsBorrow(consult.getIsBorrow());
            }
        }
        if(!ObjectHelper.isEmpty(consultIds)){
            launchView.setConsultId(consultIds[0]);  
        }
        if(!ObjectHelper.isEmpty(messages)){
            launchView.setMessage(messages[0]);
        }
        if(!ObjectHelper.isEmpty(defTokenIds)){
            TokenUtils.removeToken(defTokenIds[0]);
        }
        Map<String,String> tokenMap = TokenUtils.createToken();
        launchView.setDefTokenId(tokenMap.get("tokenId"));
        launchView.setDefToken(tokenMap.get("token"));
        TokenUtils.saveToken(tokenMap.get("tokenId"), tokenMap.get("token"));
        //自然人保证人需求已取消，合并新版申请表需求
        launchView.setOneedition("-1");
        return launchView;
        
    }
    
         
 
    

}
