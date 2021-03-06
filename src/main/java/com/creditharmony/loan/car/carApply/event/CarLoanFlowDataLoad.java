package com.creditharmony.loan.car.carApply.event;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.BusinessLoadCallBack;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.users.remote.OrgService;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.contract.dao.PaperlessPhotoDao;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carApply.service.CarLoanCoborrowerService;
import com.creditharmony.loan.car.carApply.view.CarLaunchView;
import com.creditharmony.loan.car.carApply.view.ReviewMeetApplyBusinessView;
import com.creditharmony.loan.car.carApply.view.ReviewMeetFirstAuditBusinessView;
import com.creditharmony.loan.car.carApply.view.UploadView;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerBaseService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerConsultationService;
import com.creditharmony.loan.car.carConsultation.service.CarCustomerService;
import com.creditharmony.loan.car.carConsultation.service.CarLoanInfoService;
import com.creditharmony.loan.car.carConsultation.service.CarVehicleInfoService;
import com.creditharmony.loan.car.carContract.service.CarAuditResultService;
import com.creditharmony.loan.car.carContract.service.CarCheckRateService;
import com.creditharmony.loan.car.carContract.service.CarContractService;
import com.creditharmony.loan.car.carContract.view.CarContractProLaunchView;
import com.creditharmony.loan.car.carContract.view.CarRateCheckLaunchView;
import com.creditharmony.loan.car.carContract.view.CarSigningCheckLaunchView;
import com.creditharmony.loan.car.common.consts.CarLoanFlowStepName;
import com.creditharmony.loan.car.common.dao.CarApplicationInterviewInfoDao;
import com.creditharmony.loan.car.common.dao.CarContractFileDao;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarContractFile;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.service.CarImageService;
import com.creditharmony.loan.car.common.util.CarCommonUtil;
import com.creditharmony.loan.car.common.util.NumberUtils;
import com.creditharmony.loan.common.consts.CityInfoConstant;
import com.creditharmony.loan.common.consts.SysConstant;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.dao.MiddlePersonDao;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.utils.DateUtil;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 
 * @Class Name LoanFlowLoad
 * @author 陈伟东
 * @Create In 2016年2月3日
 */
@Service("load_hj_carLoanFlow")
public class CarLoanFlowDataLoad extends BaseService implements BusinessLoadCallBack {
	//查看客户基本信息service
	@Autowired
	private CarCustomerBaseService carCustomerBaseService;
			
	//查看客户信息service
	@Autowired
	private CarCustomerService carCustomerService;
			
	//车辆详细信息service
	@Autowired
	private CarVehicleInfoService carVehicleInfoService;
	//省市
	@Autowired
	private CityInfoDao cityInfoDao;
	//客户咨询信息
	@Autowired
	private CarCustomerConsultationService carCustomerConsultationService;
	
	//借款信息
	@Autowired
	private CarLoanInfoService carLoanInfoService;
			
	//历史记录
	@Autowired
	private CarHistoryService carHistoryService;
	
	//共借人信息
	@Autowired
	private CarLoanCoborrowerService crLoanCoborrowerService;

	// 审批记录
	@Autowired
	private CarAuditResultService carAuditResultService;

	// 合同记录
	@Autowired
	private CarContractService carContractService;
	
	// 共借人记录
	@Autowired
	private CarLoanCoborrowerService carLoanCoborrowerService;
	//获取银行账户信息
	@Autowired
	private CarCustomerBankInfoService carCustomerBankInfoService;  
	
	//获取合同费率信息
	@Autowired
	private CarCheckRateService carCheckRateService;  
	@Autowired
	CarApplicationInterviewInfoDao carApplicationInterviewInfoDao;
	@Autowired
	private MiddlePersonDao middlePersonDao;
	@Autowired
	private OrgService orgService;
	@Autowired
	private ProvinceCityManager provinceCityManager;
	@Autowired
    private PaperlessPhotoDao paperlessPhotoDao;
	@Autowired
    private CarContractFileDao contractFileDao;
	@Autowired
    private  CarImageService  carImageService;
    /**
     *工作流回调方法，初始化办理页面数据
     * 用于不同节点待办详情页面数据初始化
     *@param applyId
     *@param stepName 
     *@return BaseBusinessView
     */
    @Override
    public BaseBusinessView load(String applyId, String stepName) {
    	BaseBusinessView view = null;
    	//公共获取信息
    	CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId); // 借款信息
    	String customerCode = carLoanInfo.getCustomerCode();
    	CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
    	String loanCode = carLoanInfo.getLoanCode();
    	CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode); // 客户信息
    	CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode); // 车辆信息
    	
        if(CarLoanFlowStepName.REVIEW_APPLY.equals(stepName)){ //面审申请加载数据
        	ReviewMeetApplyBusinessView  reviewMeetApplyBusinessView= new ReviewMeetApplyBusinessView();
        	//用戶個人資料加載
        	/*CarLoanInfo carLoanInfo = carLoanInfoService.selectByApplyId(applyId);
        	String customerCode = carLoanInfo.getCustomerCode();
        	CarCustomer carCustomer = carCustomerService.selectByCustomerCode(customerCode);*/
        	//CarCustomerBase carCustomerBase = carCustomerBaseService.selectCarCustomerBase(customerCode);
        	//获取省市
        	Map<String,Object> params = new HashMap<String,Object>();
            params.put("parentId", CityInfoConstant.ROOT_ID);
            List<CityInfo> provinceList = cityInfoDao.findByParams(params);
            reviewMeetApplyBusinessView.setProvinceList(provinceList);
        	
        	ReflectHandle.copy(carCustomer, reviewMeetApplyBusinessView);
        	ReflectHandle.copy(carCustomerBase, reviewMeetApplyBusinessView);
        	String imageurl = carImageService.getImageUrl(stepName, loanCode);
        	reviewMeetApplyBusinessView.setImageUrl(imageurl);
        	view = reviewMeetApplyBusinessView;
        }else if(CarLoanFlowStepName.UPLOAD_DATA.equals(stepName)){//上传资料
        	UploadView uploadView = new UploadView();
        	//共借人姓名
        	List<CarLoanCoborrower> carLoanCoborrowers = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	if(carLoanCoborrowers != null){
        		List<String> coboNames = new ArrayList<String>();
        		for(CarLoanCoborrower c: carLoanCoborrowers){
        			coboNames.add(c.getCoboName());
        		}
        		uploadView.setCoboNames(coboNames);
        	}
        	// 共借人基本信息  2016-05-21 WangJ start
        	uploadView.setCarLoanCoborrowers(carLoanCoborrowers);
        	// 共借人基本信息  2016-05-21 WangJ start
        	ReflectHandle.copy(carCustomer, uploadView);
        	
        	//getDictLabel转后台取
        	//uploadView.setCustomerSex(DictCache.getInstance().getDictLabel("jk_sex", uploadView.getCustomerSex()));
        	//uploadView.setDictCertType(DictCache.getInstance().getDictLabel("com_certificate_type", uploadView.getDictCertType()));
        	//uploadView.setDictEducation(DictCache.getInstance().getDictLabel("jk_degree", uploadView.getDictEducation()));
        	//uploadView.setDictMarryStatus(DictCache.getInstance().getDictLabel("jk_marriage", uploadView.getDictMarryStatus()));
        	String imageurl = carImageService.getImageUrl(stepName, loanCode);
        	uploadView.setImageUrl(imageurl);
        	view = uploadView;
        } else if (CarLoanFlowStepName.APPRAISER_ENTRY.equals(stepName)){//评估师录入
        	CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.selectByLoanCode(loanCode);
        	CarLaunchView carLaunchView = new CarLaunchView();
        	ReflectHandle.copy(carCustomerBase, carLaunchView);
			ReflectHandle.copy(carCustomer, carLaunchView);
			ReflectHandle.copy(carCustomerConsultation, carLaunchView);
			ReflectHandle.copy(carVehicleInfo, carLaunchView);
			carLaunchView.setMileage(NumberUtils.doubleTrans(carVehicleInfo.getMileage()));
			Double storeAssessAmount = carVehicleInfo.getStoreAssessAmount().doubleValue();
			carLaunchView.setStoreAssessAmount(storeAssessAmount);
			view = carLaunchView;
        } else if (CarLoanFlowStepName.RATE_CHECK.equals(stepName)) {
        	CarRateCheckLaunchView rateView = new CarRateCheckLaunchView();
        	carLoanInfo.setDictGpsRemaining(DictCache.getInstance().getDictLabel("jk_yes_or_no", carLoanInfo.getDictGpsRemaining()));
        	carLoanInfo.setDictIsGatherFlowFee(DictCache.getInstance().getDictLabel("jk_yes_or_no", carLoanInfo.getDictIsGatherFlowFee()));
        	carLoanInfo.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", carLoanInfo.getLoanFlag()));
        	rateView.setCarLoanInfo(carLoanInfo);
        	rateView.setCarCustomer(carCustomer);
        	rateView.setCarVehicleInfo(carVehicleInfo);
        	
        	String bornDateStr = carCustomer.getCustomerCertNum().substring(6, 14);
        	Date bornDate = null;
        	try {
				bornDate = DateUtils.parseDate(bornDateStr, "yyyyMMdd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
        	
        	rateView.setCustomerAge(DateUtil.diffYear(new Date(), bornDate));
        	CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode); // 审批通过记录
        	rateView.setCarAuditResult(carAuditResult);
        	
        	CarContract carContract = carContractService.getByLoanCode(loanCode);
        	rateView.setCarContract(carContract);
        	
        	List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	rateView.setCarLoanCoborrowers(carLoanCoborrower);
        	
        	MiddlePerson middlePerson = new MiddlePerson();
        	List<MiddlePerson> middlePersons = middlePersonDao.selectMiddlePerson(middlePerson);
        	rateView.setMiddlePersons(middlePersons);
        	
        	BigDecimal auditAmount = carAuditResult.getAuditAmount();
        	if (auditAmount.subtract(new BigDecimal(SysConstant.LARGE_AMOUNT)).doubleValue() > 0) {
        		rateView.setIsLargeAmount(YESNO.YES.getCode());
        	} else {
        		rateView.setIsLargeAmount(YESNO.NO.getCode());
        	}
        	
        	view = rateView;
        }else if(CarLoanFlowStepName.CONFIRM_SIGNING.equals(stepName)){
        	//获取页面需要数据  属性拷贝至 view对象 回显页面   签署办理页面
        	CarSigningCheckLaunchView carSigningCheckLaunchView = new CarSigningCheckLaunchView();
        	carSigningCheckLaunchView.setLoanCode(loanCode);
        	carSigningCheckLaunchView.setApplyId(carLoanInfo.getApplyId());
        	
        	carSigningCheckLaunchView.setCarCustomerBankInfo(carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode));
        	carSigningCheckLaunchView.setCarCustomer(carCustomer);
        	List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	carSigningCheckLaunchView.setCarLoanCoborrowers(carLoanCoborrower);
        	carSigningCheckLaunchView.setCarLoanInfo(carLoanInfo);
        	carSigningCheckLaunchView.setCarVehicleInfo(carVehicleInfo);
        	
        	CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode); // 审批通过记录
        	carSigningCheckLaunchView.setCarAuditResult(carAuditResult);
        	
        	CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
        	carSigningCheckLaunchView.setCarCheckRate(carCheckRate);
        	
        	CarLoanStatusHis carLoanStatusHis = carHistoryService.checkIsContractCheckBackSign(loanCode);
        	carSigningCheckLaunchView.setIsConCheckBack(carLoanStatusHis == null ? YESNO.NO.getCode() : YESNO.YES.getCode());
        	
        	carSigningCheckLaunchView.setCarContract(carContractService.getByLoanCode(loanCode));
        	
        	 //  省份初始化查询
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("parentId", CityInfoConstant.ROOT_ID);
            List<CityInfo> provinceList = cityInfoDao.findByParams(params);
            carSigningCheckLaunchView.setProvinceList(provinceList);
            ///手机号必须验证；同一客户如果在一个月内再次申请不同车辆的借款，手机号无需再次验证；超过一个月的需要重新验证
            List<CarCustomer> selectCustomer = carCustomerService.selectCustomer(carCustomer.getCustomerCode());
            if(null!= selectCustomer && selectCustomer.size()>0){
	            // 短信验证取消 carCustomer
	            if(!"1".equals(carCustomer.getCaptchaIfConfirm())){
	                Date endDay = carCustomer.getConfirmTimeout();
	                if(!ObjectHelper.isEmpty(endDay)){
	                    Date currDay = new Date();
	                  /*  if(currDay.getTime() > endDay.getTime()){
	                    	carCustomer.setCustomerPin("");
	
	                    }*/
	                    Date confirmTimeout = selectCustomer.get(0).getConfirmTimeout();
	                    Long timeOut=confirmTimeout.getTime();
	                    long timeCurr = currDay.getTime();
	                    if( timeCurr>timeOut ){
	                    	//超时就发短信
	                    	carCustomer.setCustomerPin("");
	                    	carCustomer.setCaptchaIfConfirm("0");
	                    }else{
	                    	//	一个月再次申请
	                    	carCustomer.setCustomerPin(selectCustomer.get(0).getCustomerPin());
	                    	carCustomer.setCaptchaIfConfirm("1");
	
	
	                    }
	                }
	            }
            }
            
        	view= carSigningCheckLaunchView;
        }else if(CarLoanFlowStepName.CONTRACT_PRODUCTION.equals(stepName)){
        	// 合同制作办理详情页面
        	CarContractProLaunchView carContractProLaunchView = new CarContractProLaunchView();
        	
        	carContractProLaunchView.setCarCustomerBankInfo(carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode));
        	carContractProLaunchView.setCarCustomer(carCustomer);
        	carLoanInfo.setLoanFlag(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", carLoanInfo.getLoanFlag()));
        	carContractProLaunchView.setCarLoanInfo(carLoanInfo);
        	carContractProLaunchView.setCarVehicleInfo(carVehicleInfo);
        	
        	List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	carContractProLaunchView.setCarLoanCoborrowers(carLoanCoborrower);

        	CarContract carContract = carContractService.getByLoanCode(loanCode);
        	carContractProLaunchView.setCarContract(carContract);
        	
        	CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode); // 审批通过记录
        	carContractProLaunchView.setCarAuditResult(carAuditResult); 
        	
        	CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
        	carContractProLaunchView.setCarCheckRate(carCheckRate);
        	
        	MiddlePerson middlePerson = new MiddlePerson();
        	List<MiddlePerson> middlePersons = middlePersonDao.selectMiddlePerson(middlePerson);
        	carContractProLaunchView.setMiddlePersons(middlePersons);
        	
        	view = carContractProLaunchView;
        }else if(CarLoanFlowStepName.CONTRACT_SIGNING_UPLOAD.equals(stepName)){
        	// 合同签约上传 办理页面
        	CarContractProLaunchView carContractProLaunchView = new CarContractProLaunchView();
        	carContractProLaunchView.setCarCustomerBankInfo(carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode));
        	
        	carContractProLaunchView.setCarCustomer(carCustomer);
        	carContractProLaunchView.setCarLoanInfo(carLoanInfo);
        	
        	CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode); // 审批通过记录
        	carContractProLaunchView.setCarAuditResult(carAuditResult);
        	CarContract carContract = carContractService.getByLoanCode(loanCode);
        	carContractProLaunchView.setCarContract(carContract);
        	List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	carContractProLaunchView.setCarLoanCoborrowers(carLoanCoborrower);
        	//抓取无纸化需要的缓存的照片
            Map<String,String> paperlessPhotoParam = new HashMap<String,String>();
            if(!ObjectHelper.isEmpty(carCustomer)){
                   paperlessPhotoParam.put("relationId", carCustomer.getId());
                   PaperlessPhoto p = paperlessPhotoDao.getByRelationId(paperlessPhotoParam);
                   if(!ObjectHelper.isEmpty(p)){
                	   carCustomer.setIdCardId(p.getIdPhotoId());
                	   carCustomer.setCurPlotId(p.getSpotPhotoId());
                   }
            }
           String imageurl = carImageService.getImageUrl(stepName, loanCode);
           
           carContractProLaunchView.setImageurl(imageurl);
        	// 如果无纸化标识为1，则查询相关缓存文件
            if(null!=carContract.getPaperLessFlag()&&YESNO.YES.getCode().equals(carContract.getPaperLessFlag())){
                List<PaperlessPhoto> pList = paperlessPhotoDao.getByLoanCode(carContract.getLoanCode());
                if(!ObjectHelper.isEmpty(carLoanCoborrower)){
                    for(CarLoanCoborrower b:carLoanCoborrower){
                        for(PaperlessPhoto pp:pList){
                            if(b.getId().equals(pp.getRelationId())){
                                b.setIdCardId(pp.getIdPhotoId());
                                b.setCurPlotId(pp.getSpotPhotoId());
                            }
                        }
                    }
                }
             }
            List<CarContractFile> files = contractFileDao.findContractFileByContractCode(carContract.getContractCode());
            carContractProLaunchView.setFiles(files);
        	view = carContractProLaunchView;
        }else if(CarLoanFlowStepName.CONTRACT_CHECK.equals(stepName)){
        	//合同审核 办理页面
        	CarContractProLaunchView carContractProLaunchView = new CarContractProLaunchView();
        	
        	carContractProLaunchView.setCarCustomerBankInfo(carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode));
        	carContractProLaunchView.setCarCustomer(carCustomer);
        	carContractProLaunchView.setCarLoanInfo(carLoanInfo);
        	carContractProLaunchView.setCarVehicleInfo(carVehicleInfo);
        	
        	List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	carContractProLaunchView.setCarLoanCoborrowers(carLoanCoborrower);

        	CarContract carContract = carContractService.getByLoanCode(loanCode);
        	carContractProLaunchView.setCarContract(carContract);
        	
        	CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode); // 审批通过记录
        	carContractProLaunchView.setCarAuditResult(carAuditResult);
        	
        	CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
        	carContractProLaunchView.setCarCheckRate(carCheckRate);
        	
        	BigDecimal auditAmount = carContract.getAuditAmount();
        	if (auditAmount == null) {
        		auditAmount = new BigDecimal(0);
        	}
        	if (new BigDecimal(SysConstant.LARGE_AMOUNT).subtract(auditAmount).doubleValue() <= 0) {
        		carContractProLaunchView.setIsLargeAmount(YESNO.YES.getCode());
        	} else {
        		carContractProLaunchView.setIsLargeAmount(YESNO.NO.getCode());
        	}
        	String imageurl = carImageService.getImageUrl(stepName, loanCode);
        	carContractProLaunchView.setImageurl(imageurl);
        	
        	view = carContractProLaunchView;
        }else if(CarLoanFlowStepName.FIRST_AUDIT.equals(stepName)){ // 初审
        	ReviewMeetFirstAuditBusinessView reviewMeetBV = new ReviewMeetFirstAuditBusinessView();
        	//获取管辖城市
    		String storeCode = carLoanInfo.getStoreCode();
    		if(!StringUtils.isEmpty(storeCode)){
    			String cityId = orgService.getOrg(storeCode).getCityId();
    			String cityName = "";
    			if (!StringUtils.isEmpty(cityId)) {
    				cityName = provinceCityManager.get(cityId).getAreaName();
    			}
    			reviewMeetBV.setJurisdictionCity(cityName);
    		}
    		//获取客户经理
    		String managerCode =carLoanInfo.getManagerCode();
    		if(!StringUtils.isEmpty(managerCode)){
    			String managerName = UserUtils.get(managerCode).getName();
    			reviewMeetBV.setManagerName(managerName);
    		}
    		//获取团队经理
    		String teamManagerCode = carLoanInfo.getConsTeamManagercode();
    		if(!StringUtils.isEmpty(teamManagerCode)){
    			String teamManagerName = UserUtils.get(teamManagerCode).getName();	
    			reviewMeetBV.setConsTeamManagerName(teamManagerName);
    		}
    		reviewMeetBV.setCarLoanInfo(carLoanInfo);
    		reviewMeetBV.setCarCustomer(carCustomer);
    		reviewMeetBV.setCarVehicleInfo(carVehicleInfo);
    		reviewMeetBV.setCarApplicationInterviewInfo(carApplicationInterviewInfoDao.selectByLoanCode(carLoanInfo.getLoanCode()));
    		List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
    		if(carLoanCoborrower != null && carLoanCoborrower.size() > 0){
    			reviewMeetBV.setCarLoanCoborrowers(carLoanCoborrower);
    		}
    		
    		//getDictLabel转后台取
    		reviewMeetBV.setFourLabel(DictCache.getInstance().getDictLabel("jk_car_loan_result", "4"));
    		reviewMeetBV.setOneLabel(DictCache.getInstance().getDictLabel("jk_car_loan_result", "1"));
    		reviewMeetBV.setThreeLabel(DictCache.getInstance().getDictLabel("jk_car_loan_result", "3"));
    		reviewMeetBV.getCarLoanInfo().setDictGpsRemaining(DictCache.getInstance().getDictLabel("jk_yes_or_no", reviewMeetBV.getCarLoanInfo().getDictGpsRemaining()));
    		reviewMeetBV.getCarLoanInfo().setDictIsGatherFlowFee(DictCache.getInstance().getDictLabel("jk_yes_or_no", reviewMeetBV.getCarLoanInfo().getDictIsGatherFlowFee()));
    		String imageurl = carImageService.getImageUrl(stepName, loanCode);
    		reviewMeetBV.setImageUrl(imageurl);
    		reviewMeetBV.setContractVersion(CarCommonUtil.getVersionByLoanCode(loanCode));
    		view = reviewMeetBV;
        }else{
        	//TODO (如果必要加入其它节点的表单初始化数据的逻辑)此处编写打开面审申请待办详情表单时，加载需要展示的的业务数据
        }
        return view;
    }
}
