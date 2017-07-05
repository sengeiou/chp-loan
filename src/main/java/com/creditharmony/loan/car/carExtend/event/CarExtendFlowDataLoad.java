package com.creditharmony.loan.car.carExtend.event;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.BusinessLoadCallBack;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.common.util.ArrayHelper;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.loan.type.CarLoanProductType;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.users.remote.OrgService;
import com.creditharmony.core.users.service.ProvinceCityManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.contract.dao.PaperlessPhotoDao;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;
import com.creditharmony.loan.car.carApply.service.CarCustomerBankInfoService;
import com.creditharmony.loan.car.carApply.service.CarLoanCoborrowerService;
import com.creditharmony.loan.car.carApply.view.ReviewMeetApplyBusinessView;
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
import com.creditharmony.loan.car.carExtend.view.CarExtendAppraiserView;
import com.creditharmony.loan.car.carExtend.view.CarExtendFirstAuditView;
import com.creditharmony.loan.car.carExtend.view.CarExtendSigningView;
import com.creditharmony.loan.car.carExtend.view.CarExtendUploadDataView;
import com.creditharmony.loan.car.common.consts.CarLoanFlowStepName;
import com.creditharmony.loan.car.common.dao.CarApplicationInterviewInfoDao;
import com.creditharmony.loan.car.common.dao.CarContractFileDao;
import com.creditharmony.loan.car.common.entity.CarAuditResult;
import com.creditharmony.loan.car.common.entity.CarCheckRate;
import com.creditharmony.loan.car.common.entity.CarContract;
import com.creditharmony.loan.car.common.entity.CarContractFile;
import com.creditharmony.loan.car.common.entity.CarCustomer;
import com.creditharmony.loan.car.common.entity.CarCustomerBankInfo;
import com.creditharmony.loan.car.common.entity.CarCustomerBase;
import com.creditharmony.loan.car.common.entity.CarCustomerConsultation;
import com.creditharmony.loan.car.common.entity.CarLoanCoborrower;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.common.entity.CarLoanStatusHis;
import com.creditharmony.loan.car.common.entity.CarVehicleInfo;
import com.creditharmony.loan.car.common.service.CarHistoryService;
import com.creditharmony.loan.car.common.service.CarImageService;
import com.creditharmony.loan.car.common.util.CarCommonUtil;
import com.creditharmony.loan.common.consts.CityInfoConstant;
import com.creditharmony.loan.common.consts.SysConstant;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.service.CityInfoService;
import com.creditharmony.loan.common.service.MiddlePersonService;
import com.creditharmony.loan.common.utils.ReflectHandle;

/**
 * 
 * @Class Name CarExtendFlowDataLoad
 * @author 陈伟东
 * @Create In 2016年2月3日
 */
@Service("load_hj_carExtendFlow")
public class CarExtendFlowDataLoad extends BaseService implements BusinessLoadCallBack {
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
	private MiddlePersonService middlePersonService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private ProvinceCityManager provinceCityManager;
	@Autowired
	private CityInfoService cityInfoService;
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
    	String loanAdditionalApplyid = carLoanInfo.getLoanAdditionalApplyid();
    	CarCustomer carCustomer = carCustomerService.selectByLoanCode(loanCode); // 客户信息
    	CarVehicleInfo carVehicleInfo = carVehicleInfoService.selectCarVehicleInfo(loanCode); // 车辆信息
    	String imageUrl = carImageService.getExendImageUrl(stepName, loanCode);
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
        	reviewMeetApplyBusinessView.setImageUrl(imageUrl);
        	view = reviewMeetApplyBusinessView;
        }else if(CarLoanFlowStepName.UPLOAD_DATA.equals(stepName)){//上传资料
        	CarExtendUploadDataView carExtendUploadDataView = new CarExtendUploadDataView();
        	ReflectHandle.copy(carCustomerBase, carExtendUploadDataView);
        	ReflectHandle.copy(carCustomer, carExtendUploadDataView);
        	ReflectHandle.copy(carVehicleInfo, carExtendUploadDataView);
        	carExtendUploadDataView.setApplyId(applyId);
        	//得到合同编号
        	String originalLoanCode = carLoanInfo.getLoanRawcode();
        	String contractCode = carContractService.getExtendContractNo(originalLoanCode, loanCode, YESNO.NO.getCode());
        	List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	carExtendUploadDataView.setCarLoanCoborrowers(carLoanCoborrower);
        	carExtendUploadDataView.setContractCode(contractCode);
        	carExtendUploadDataView.setImageUrl(imageUrl);
        	view = carExtendUploadDataView;
        } else if (CarLoanFlowStepName.APPRAISER_ENTRY.equals(stepName)){//评估师录入
        	String  loanRawcode = carLoanInfo.getLoanRawcode();
//        	String loanAdditionalApplyid2 = carLoanInfo.getLoanAdditionalApplyid();
        	CarContract carContract = null; 
        	CarVehicleInfo carVehicleInfo2 = null;
        	if( StringUtils.isNotEmpty(loanAdditionalApplyid)){
        		CarLoanInfo carLoanInfo2 = carLoanInfoService.get(loanAdditionalApplyid);
        		if(carLoanInfo2 != null){
        			if(carLoanInfo2.getDictProductType() == null){
        				carLoanInfo2.setDictProductType(carLoanInfo.getDictProductType());
        			}
        			carVehicleInfo2 = carVehicleInfoService.selectCarVehicleInfo(carLoanInfo2.getLoanCode());
        			carCustomer = carCustomerService.selectByLoanCode(carLoanInfo2.getLoanCode());
        			carContract = carContractService.getByLoanCode(carLoanInfo2.getLoanCode());
        			carLoanInfo = carLoanInfo2;
        		}
        		if(null==carVehicleInfo2){
        			carVehicleInfo2 = carVehicleInfoService.selectCarVehicleInfo(carLoanInfo2.getLoanRawcode());
        			carCustomer = carCustomerService.selectByLoanCode(loanRawcode);
        		}
        	}else{
        		carVehicleInfo2 = carVehicleInfoService.selectCarVehicleInfo(loanRawcode);
        		carContract = carContractService.getByLoanCode(loanRawcode);
        	}
        	CarVehicleInfo carve = carVehicleInfoService.selectCarVehicleInfo(loanCode);
        	if (carve != null) {
        		carVehicleInfo2 = carve;
        	} else {
        		if (carVehicleInfo2 == null) {
        			carVehicleInfo2 = new CarVehicleInfo();
        			carVehicleInfo2.setSuggestLoanAmount(null);
        		}
        	}
        	BigDecimal auditAmount= carContract.getContractAmount();
        	CarCustomerConsultation carCustomerConsultation = carCustomerConsultationService.selectByCustomerCode(customerCode);
        	CarExtendAppraiserView carExtendAppraiserView = new CarExtendAppraiserView();
        	ReflectHandle.copy(carCustomerBase, carExtendAppraiserView);
			ReflectHandle.copy(carCustomer, carExtendAppraiserView);
			ReflectHandle.copy(carCustomerConsultation, carExtendAppraiserView);
			ReflectHandle.copy(carVehicleInfo2, carExtendAppraiserView);
			ReflectHandle.copy(carLoanInfo, carExtendAppraiserView);
			carExtendAppraiserView.setApplyId(applyId);
			carExtendAppraiserView.setLoanCode(loanCode);
			carExtendAppraiserView.setAuditAmount(auditAmount);
			carExtendAppraiserView.setImageUrl(imageUrl);
			
			view = carExtendAppraiserView;
        } else if (CarLoanFlowStepName.RATE_CHECK.equals(stepName)) {// 审核费率办理详情页
        	CarRateCheckLaunchView rateView = new CarRateCheckLaunchView();
        	
        	rateView.setCarLoanInfo(carLoanInfo);
        	rateView.setCarCustomer(carCustomer);
        	rateView.setCarVehicleInfo(carVehicleInfo);

        	// 合同信息
        	CarContract carContract = carContractService.getByLoanCode(loanCode);
        	carContract.setProductType(CarLoanProductType.parseByCode(carContract.getProductType()).getName());
        	rateView.setCarContract(carContract);
        	
        	List<CarContract> list = carContractService.getExtendContractByLoanCode(loanCode);
        	rateView.setCarContracts(list);
        	
        	CarCustomerBankInfo carCustomerBankInfo = carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode);
        	rateView.setCarCustomerBankInfo(carCustomerBankInfo);
        	
        	List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	rateView.setCarLoanCoborrowers(carLoanCoborrower);
        	
        	MiddlePerson middlePerson = new MiddlePerson();
        	List<MiddlePerson> middlePersons = middlePersonService.selectMiddlePerson(middlePerson);
        	rateView.setMiddlePersons(middlePersons);

        	BigDecimal auditAmount = carContract.getAuditAmount();
        	if (auditAmount.subtract(new BigDecimal(SysConstant.LARGE_AMOUNT)).doubleValue() > 0) {
        		rateView.setIsLargeAmount(YESNO.YES.getCode());
        	} else {
        		rateView.setIsLargeAmount(YESNO.NO.getCode());
        	}
        	view = rateView;
        }else if(CarLoanFlowStepName.CONFIRM_SIGNING.equals(stepName)){
        	// 获取页面需要数据  属性拷贝至 view对象 回显页面        签署办理页面
        	Page<CarLoanStatusHis> page = new Page<CarLoanStatusHis>();
        	CarLoanStatusHis carLoanStatusHis = new CarLoanStatusHis();
        	CarExtendSigningView carExtendSigningView = new CarExtendSigningView();
        	List<CarContract> carContracts = carContractService.getExtendContractByLoanCode(loanCode);
        	// 展期历史信息
        	carExtendSigningView.setCarContracts(carContracts);
        	CarContract carContract = carContracts.get(carContracts.size() - 1);
        	Date contractDate = null;
        	if (carContract != null) {
        		contractDate = DateUtils.addDays(carContract.getContractEndDay(), 1);
        	}
        	carExtendSigningView.setLastContractEndDateSecond(contractDate);
        	// 客户银行卡信息
        	carExtendSigningView.setCarCustomerBankInfo(carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode));
        	// 客户信息
        	carExtendSigningView.setCarCustomer(carCustomer);
        	// 客户基本信息
        	carExtendSigningView.setCarCustomerBase(carCustomerBase);
        	List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	// 共借人信息
        	carExtendSigningView.setCarLoanCoborrowers(carLoanCoborrower);
        	// 借款信息
        	carExtendSigningView.setCarLoanInfo(carLoanInfo);
        	// 车辆信息
        	carExtendSigningView.setCarVehicleInfo(carVehicleInfo);
        	// 审批通过记录
        	CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode); 
        	carAuditResult.setDictProductType(CarLoanProductType.parseByCode(carAuditResult.getDictProductType()).getName());
        	carExtendSigningView.setCarAuditResult(carAuditResult);
        	// 合同信息
        	carExtendSigningView.setCarContract(carContractService.getByLoanCode(loanCode));
        	//费率信息
        	carExtendSigningView.setCarCheckRate(carCheckRateService.selectCarCheckRateByLoanCode(loanCode));
        	 //  省份初始化查询
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("parentId", CityInfoConstant.ROOT_ID);
            List<CityInfo> provinceList = cityInfoDao.findByParams(params);
            carExtendSigningView.setProvinceList(provinceList);
            carExtendSigningView.setApplyId(applyId);
            // 查看此条展期是否有 待确认签署状态的历史记录
            carLoanStatusHis.setLoanCode(loanCode);
            Page<CarLoanStatusHis> carLoanStatusHisView = carHistoryService.findCarLoanStatusHisList(page, carLoanStatusHis);
            if (carLoanStatusHisView != null) {
                List<CarLoanStatusHis>  carLoanStatusHisList = carLoanStatusHisView.getList();
                if (ArrayHelper.isNotEmpty(carLoanStatusHisList)) {
					for (int i = 0; i < carLoanStatusHisList.size(); i++) {
						if (CarLoanStatus.PENDING_CONFIRMED_SIGN.getCode().equals(carLoanStatusHisList.get(i).getDictLoanStatus())) {
							carExtendSigningView.setCarLoanStatusHis(carLoanStatusHisList.get(i));
						}
					}
				}
			}
          ///手机号必须验证；同一客户如果在一个月内再次申请不同车辆的借款，手机号无需再次验证；超过一个月的需要重新验证
            List<CarCustomer> selectCustomer = carCustomerService.selectCustomer(carCustomer.getCustomerCode());
            if(null!= selectCustomer && selectCustomer.size()>0){
	            // 短信验证取消 carCustomer
	            if(!"1".equals(carCustomer.getCaptchaIfConfirm())){
	                Date endDay = carCustomer.getConfirmTimeout();
	                if(!ObjectHelper.isEmpty(endDay)){
	                    Date currDay = new Date();
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
	            /*CarCustomerBase carcustomerbase =  carCustomerBaseService.selectCarCustomerBase(carCustomer.getCustomerCode());
	            if(null!=carcustomerbase&&null!=carcustomerbase.getIsTelephoneModify()&&"1".equals(carcustomerbase.getIsTelephoneModify())){
	            	carCustomer.setCustomerPin("");
                	carCustomer.setCaptchaIfConfirm("0");
	            }*/
            }
           /* if(carLoanCoborrower.size()>0){
            	 for (CarLoanCoborrower cobos : carLoanCoborrower) {
 					if(null!=cobos.getIstelephonemodify()&&cobos.getIstelephonemodify().equals("1")){
 						cobos.setCustomerPin("");
 						cobos.setCaptchaIfConfirm("0");
 					}
 				}
            }*/
            
        	view= carExtendSigningView;
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
        	
        	List<CarContract> carcontracts = carContractService.getExtendContractByLoanCode(loanCode);
        	carContractProLaunchView.setCarContracts(carcontracts);

        	CarContract carContract = carContractService.getByLoanCode(loanCode);
        	carContract.setProductType(CarLoanProductType.parseByCode(carContract.getProductType()).getName());
        	carContractProLaunchView.setCarContract(carContract);

        	MiddlePerson middlePerson = new MiddlePerson();
        	List<MiddlePerson> middlePersons = middlePersonService.selectMiddlePerson(middlePerson);
        	carContractProLaunchView.setMiddlePersons(middlePersons);
        	
        	CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode); // 审批通过记录
        	carContractProLaunchView.setCarAuditResult(carAuditResult);
        	
        	CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
        	carContractProLaunchView.setCarCheckRate(carCheckRate);
        	
        	view = carContractProLaunchView;
        }else if(CarLoanFlowStepName.CONTRACT_SIGNING_UPLOAD.equals(stepName)){
        	// 合同签约上传 办理页面
        	CarContractProLaunchView carContractProLaunchView = new CarContractProLaunchView();
        	
        	carContractProLaunchView.setCarCustomer(carCustomer);
        	carContractProLaunchView.setCarLoanInfo(carLoanInfo);
        	carContractProLaunchView.setCarVehicleInfo(carVehicleInfo);
        	carContractProLaunchView.setCarCustomerBankInfo(carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode));

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
           
        	// 如果无纸化标识为1，则查询相关缓存文件
            if(YESNO.YES.getCode().equals(carContract.getPaperLessFlag())){
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
            CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
        	carContractProLaunchView.setCarCheckRate(carCheckRate);
            carContractProLaunchView.setImageurl(imageUrl);
        	view = carContractProLaunchView;
        }else if(CarLoanFlowStepName.CONTRACT_CHECK.equals(stepName)){
        	//合同审核 办理页面
        	CarContractProLaunchView carContractProLaunchView = new CarContractProLaunchView();
        	
        	carContractProLaunchView.setCarCustomerBankInfo(carCustomerBankInfoService.selectCarCustomerBankInfo(loanCode));
        	carContractProLaunchView.setCarCustomer(carCustomer);
        	carContractProLaunchView.setCarLoanInfo(carLoanInfo);
        	carContractProLaunchView.setCarVehicleInfo(carVehicleInfo);
        	List<CarContract> carContracts = carContractService.getExtendContractByLoanCode(loanCode);
        	// 展期历史信息
        	carContractProLaunchView.setCarContracts(carContracts);
        	
        	List<CarLoanCoborrower> carLoanCoborrower = carLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	carContractProLaunchView.setCarLoanCoborrowers(carLoanCoborrower);

        	CarContract carContract = carContractService.getByLoanCode(loanCode);
        	carContract.setProductType(CarLoanProductType.parseByCode(carContract.getProductType()).getName());
        	carContractProLaunchView.setCarContract(carContract);
        	
        	CarAuditResult carAuditResult = carAuditResultService.getLastThroughRecord(loanCode); // 审批通过记录
        	carAuditResult.setDictProductType(CarLoanProductType.parseByCode(carAuditResult.getDictProductType()).getName());
        	carContractProLaunchView.setCarAuditResult(carAuditResult);
        	
        	CarCheckRate carCheckRate = carCheckRateService.selectCarCheckRateByLoanCode(loanCode);
        	carContractProLaunchView.setCarCheckRate(carCheckRate);

        	BigDecimal auditAmount = carAuditResult.getAuditAmount();
        	if (auditAmount.subtract(new BigDecimal(SysConstant.LARGE_AMOUNT)).doubleValue() > 0) {
        		carContractProLaunchView.setIsLargeAmount(YESNO.YES.getCode());
        	} else {
        		carContractProLaunchView.setIsLargeAmount(YESNO.NO.getCode());
        	}
        	CarCustomerBase carcustomerbase =  carCustomerBaseService.selectCarCustomerBase(carCustomer.getCustomerCode());
        	carContractProLaunchView.setCarCustomerBase(carcustomerbase);
        	carContractProLaunchView.setImageurl(imageUrl);
        	view = carContractProLaunchView;
        }else if(CarLoanFlowStepName.FIRST_AUDIT.equals(stepName)){ // 初审
        	List<CarLoanCoborrower> carLoanCoborrowers = crLoanCoborrowerService.selectCoborrowerByLoanCode(loanCode);
        	CarExtendFirstAuditView carExtendFirstAuditView = new CarExtendFirstAuditView();
        	carExtendFirstAuditView.setFourLabel(DictCache.getInstance().getDictLabel("jk_car_loan_result", "4"));
        	carExtendFirstAuditView.setOneLabel(DictCache.getInstance().getDictLabel("jk_car_loan_result", "1"));
    		carExtendFirstAuditView.setThreeLabel(DictCache.getInstance().getDictLabel("jk_car_loan_result", "3"));
        	carExtendFirstAuditView.setCarCustomer(carCustomer);
        	carExtendFirstAuditView.setCarLoanInfo(carLoanInfo);
        	carExtendFirstAuditView.setCarVehicleInfo(carVehicleInfo);
        	carExtendFirstAuditView.setCarLoanCoborrowers(carLoanCoborrowers);
        	//加入团队经理名称
        	String teamManagerCode = carLoanInfo.getConsTeamManagercode();
    		if(!StringUtils.isEmpty(teamManagerCode)){
    			String teamManagerName = UserUtils.get(teamManagerCode).getName();
    			carExtendFirstAuditView.setConsTeamManagerName(teamManagerName);
    		}
    		//获取客户经理
    		String managerCode =carLoanInfo.getManagerCode();
    		if(!StringUtils.isEmpty(managerCode)){
    			if(UserUtils.get(managerCode)!=null)
    			{
	    			String managerName = UserUtils.get(managerCode).getName();
	    			carExtendFirstAuditView.setManagerName(managerName);
    			}
    		}
    		//获取管辖城市
    		String storeCode = carLoanInfo.getStoreCode();
    		if(!StringUtils.isEmpty(storeCode)){
    			String cityId = orgService.getOrg(storeCode).getCityId();
    			if(null!=cityId){
    				String cityName = provinceCityManager.get(cityId).getAreaName();
        			carExtendFirstAuditView.setCityName(cityName);
    			}
    		}
    		//获取省份
    		List<CityInfo> provinceList = cityInfoService.findProvince();
    		carExtendFirstAuditView.setProvinceList(provinceList);
    		//获得合同编号
    		if(StringUtils.isNotEmpty(carLoanInfo.getLoanRawcode())){
    			String rawCarLoan = carLoanInfo.getLoanRawcode();
    			if(StringUtils.isNotEmpty(rawCarLoan)){
    				String contractNo = carContractService.getExtendContractNo(rawCarLoan, loanCode, YESNO.NO.getCode());
    				carExtendFirstAuditView.setContractNo(contractNo);
    			}
    		}
    		carExtendFirstAuditView.setImageUrl(imageUrl);
    		carExtendFirstAuditView.setContractVersion(CarCommonUtil.getVersionByLoanCode(loanCode));
        	view = carExtendFirstAuditView;
        }else{
        	//TODO (如果必要加入其它节点的表单初始化数据的逻辑)此处编写打开面审申请待办详情表单时，加载需要展示的的业务数据
        }
        return view;
    }
}
