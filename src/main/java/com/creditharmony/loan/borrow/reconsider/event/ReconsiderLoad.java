/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.borrow.reconsider.eventReconsiderLoad.java
 * @Create By 张灏
 * @Create In 2016年1月15日 下午2:19:12
 */
package com.creditharmony.loan.borrow.reconsider.event;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creditharmony.bpm.frame.face.BusinessLoadCallBack;
import com.creditharmony.bpm.frame.face.base.BaseService;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.common.util.BigDecimalTools;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.PropertyUtil;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.fortune.type.DeductPlat;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanModel;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.role.type.LoanRole;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.Contact;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCompany;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCreditInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanHouse;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.entity.ex.ApplyInfoFlagEx;
import com.creditharmony.loan.borrow.applyinfo.service.DataEntryService;
import com.creditharmony.loan.borrow.applyinfo.service.LoanService;
import com.creditharmony.loan.borrow.applyinfo.service.StoreReviewService;
import com.creditharmony.loan.borrow.applyinfo.view.LaunchView;
import com.creditharmony.loan.borrow.contract.dao.CoeffReferJYJDao;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeTempDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFileDao;
import com.creditharmony.loan.borrow.contract.dao.ContractTempDao;
import com.creditharmony.loan.borrow.contract.dao.CustInfoDao;
import com.creditharmony.loan.borrow.contract.dao.PaperlessPhotoDao;
import com.creditharmony.loan.borrow.contract.dao.RateInfoDao;
import com.creditharmony.loan.borrow.contract.entity.CoeffReferJYJ;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractFeeTemp;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.entity.ContractTemp;
import com.creditharmony.loan.borrow.contract.entity.PaperlessPhoto;
import com.creditharmony.loan.borrow.contract.entity.RateInfo;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.CustInfo;
import com.creditharmony.loan.borrow.contract.entity.ex.FeeInfoEx;
import com.creditharmony.loan.borrow.contract.entity.ex.LoanCustomerEx;
import com.creditharmony.loan.borrow.contract.service.ContractCustInfoService;
import com.creditharmony.loan.borrow.contract.service.ContractFeeService;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contract.service.LoanBankService;
import com.creditharmony.loan.borrow.contract.service.OutsideCheckInfoService;
import com.creditharmony.loan.borrow.contract.util.ProductUtil;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.contract.view.CustomerView;
import com.creditharmony.loan.borrow.grant.constants.GrantCommon;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.service.GrantAuditService;
import com.creditharmony.loan.borrow.grant.service.LoanGrantService;
import com.creditharmony.loan.borrow.grant.view.GrantDealView;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.channel.goldcredit.dao.GCCeilingDao;
import com.creditharmony.loan.channel.jyj.service.JyjBorrowBankConfigureService;
import com.creditharmony.loan.common.consts.CityInfoConstant;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.dao.FyAreaCodeDao;
import com.creditharmony.loan.common.dao.GlBillDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanPrdMngDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.MiddlePersonDao;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.FyAreaCode;
import com.creditharmony.loan.common.entity.GlBill;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.MiddlePerson;
import com.creditharmony.loan.common.entity.OutsideCheckInfo;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.service.ImageService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.type.LoanProductCode;
import com.creditharmony.loan.common.utils.ApplyInfoConstant;
import com.creditharmony.loan.common.utils.LoanConsultDateUtils;
import com.creditharmony.loan.common.utils.ReckonFeeNew;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.utils.InnerBean;
import com.creditharmony.loan.utils.PhoneSecretSerivice;

/**
 * 复议流程查询数据使用
 * @Class Name ReconsiderLoad
 * @author 张灏
 * @Create In 2016年1月15日
 */
@Service("load_hj_reconsider")
public class ReconsiderLoad extends BaseService implements BusinessLoadCallBack {

    @Autowired
    private ContractService contractService;
    
    @Autowired
    private ContractFeeService contractFeeService;
    
    @Autowired
    private LoanBankService loanBankService;
    
    @Autowired
    private LoanCustomerDao loanCustomerDao;
    
    @Autowired
    private LoanCoborrowerDao loanCoborrowerDao;
    
    @Autowired
    private CustInfoDao custInfoDao;
    
    @Autowired
    private ContractCustInfoService contractCustInfoServcie;
    
    @Autowired
    private OutsideCheckInfoService outSideInfoService;

    @Autowired
    private GrantAuditService grantAuditService;
    
    @Autowired
    private LoanGrantService loanGrantService;

    @Autowired
    private LoanService loanService;
    
    @Autowired
    private StoreReviewService storeReviewService;
    
    @Autowired
    private DataEntryService dataEntryService;
    
    @Autowired
    private MiddlePersonDao middlePersonDao;
    
    @Autowired
    private LoanStatusHisDao loanStatusHisDao;
    
    @Autowired
    private CityInfoDao cityInfoDao;
    
    @Autowired
    private LoanPrdMngDao prdDao;
    
    @Autowired
    private GlBillDao glBillDao;
    
    @Autowired
	private LoanPrdMngService loanPrdMngService;
    
    @Autowired
    private AreaService areaService;
    
    @Autowired
    private FyAreaCodeDao fyAreaCodeDao;
    
    @Autowired
    private ImageService imageService;
    @Autowired
    private ApplyLoanInfoDao loanInfoDao;
    
    @Autowired
    private RateInfoDao rateInfoDao;
    @Autowired
    private ContractFileDao contractFileDao;
    @Autowired
    private PaperlessPhotoDao paperlessPhotoDao;
    @Autowired
    private GCCeilingDao gcCeilingDao;
    @Autowired
    private ContractTempDao contractTempDao;
    
    @Autowired
    private PhoneSecretSerivice phoneSecretSerivice;
    
    @Autowired
	private ContractFeeTempDao contractFeeTempDao;
    
    @Autowired
	private CoeffReferJYJDao coeffReferJYJDao;
    @Autowired
	private ContractDao contractDao;
    
    @Autowired
	private JyjBorrowBankConfigureService jyjBorrowBankConfigureService;

    /**
     *工作流回调方法，初始化办理页面数据
     *@param applyId
     *@param stepName 
     *@return BaseBusinessView
     */
    @Override
    public BaseBusinessView load(String applyId, String stepName) {
    	String borrowApplyId = areaService.getApplyId(applyId);
        BaseBusinessView view = null;
        Map<String,Object> queryParam = new HashMap<String,Object>();
        queryParam.put("applyId", applyId);
		if (LoanCustomerEx.RECONSIDER_BACK_STORE.equals(stepName)) {
			HashMap<String, Object> param=new HashMap<String, Object>();
        	param.put("applyId", borrowApplyId);
        	LoanInfo loanInfoObj=loanInfoDao.selectByApplyId(param);
        	String LoanInfoOldOrNewFlag=loanInfoObj.getLoanInfoOldOrNewFlag();
        	//根据标识区分跳转到旧版门店复核页面还是新版门店复核页面
        	if("1".equals(LoanInfoOldOrNewFlag)){
        		LaunchView launchView = new LaunchView();
        		//查询第一个页签的数据
        		LoanCustomer loanCustomer = contractCustInfoServcie.selectByApplyId(borrowApplyId);
        		Map<String,String> initParam = new HashMap<String,String>();
                initParam.put("customerCode",loanCustomer.getCustomerCode());
                initParam.put("consultId", loanInfoObj.getRid());
                initParam.put("flag", ApplyInfoConstant.NEW_APPLY_INFO_CUSTOMER);
                initParam.put("loanCode", loanInfoObj.getLoanCode());
                ApplyInfoFlagEx applyInfo = dataEntryService.getCustumerData_new(initParam);
                //复制数据
                BeanUtils.copyProperties(applyInfo, launchView);
                //设置省份list
                Map<String,Object> params = new HashMap<String,Object>();
        		params.put("parentId", CityInfoConstant.ROOT_ID);
        		List<CityInfo> provinceList = cityInfoDao.findByParams(params);
        		launchView.setProvinceList(provinceList);
        		//设置consultId,customerCode
        		launchView.setConsultId(loanInfoObj.getRid());
        		launchView.setCustomerCode(loanCustomer.getCustomerCode());
        		launchView.setLoanCode(loanInfoObj.getLoanCode());
        		//设置preResponse
        		String dictLoanStatus = areaService.getStatusByApplyId(borrowApplyId);
        		if(LoanApplyStatus.RECONSIDER_BACK_STORE.getCode().equals(dictLoanStatus)) {
        			launchView.setPreResponse("STORE_BACK_CHECK");
        		}
        		//
        		User user = UserUtils.getUser();
        		List<String> roleIds = user.getRoleIdList();
        		String isStoreAssistant = YESNO.NO.getCode();
        		if(!ObjectHelper.isEmpty(roleIds)){
        			for(String roleId:roleIds){
        				if(LoanRole.STORE_ASSISTANT.id.equals(roleId)){
        					isStoreAssistant = YESNO.YES.getCode();
        					break;
        				}
        			}
        		}
        		launchView.setIsStoreAssistant(isStoreAssistant);
        		launchView.setLastLoanStatus(dictLoanStatus);
        		//设置applyId
        		launchView.setApplyId(applyId);
        		//设置影响地址
        		String imageUrl = imageService.getImageUrl(stepName,launchView.getLoanCustomer().getLoanCode());
        		launchView.setImageUrl(imageUrl);
        		
        		view = launchView;
        	}else{
        		LaunchView launchView = new LaunchView();
        		Map<String,Object> params = new HashMap<String,Object>();
        		params.put("parentId", CityInfoConstant.ROOT_ID);
        		List<CityInfo> provinceList = cityInfoDao.findByParams(params);
        		launchView.setProvinceList(provinceList);
        		if (StringUtils.isNotEmpty(borrowApplyId)) {
        			LoanCustomer lc = new LoanCustomer();
        			lc.setLoanCode(areaService.getLoanCodeByApplyId(borrowApplyId));
        			launchView.setLoanCustomer(lc);
        			ApplyInfoFlagEx applyInfo = dataEntryService.getAllInfo(borrowApplyId);				
        			BeanUtils.copyProperties(applyInfo, launchView);
        		}
        		String dictLoanStatus = areaService.getStatusByApplyId(borrowApplyId);
        		if(LoanApplyStatus.RECONSIDER_BACK_STORE.getCode().equals(dictLoanStatus)) {
        			launchView.setPreResponse("STORE_BACK_CHECK");
        		}
        		launchView.setLastLoanStatus(dictLoanStatus);
        		// 根据产品编号查询产品类型
        		if (!ObjectHelper.isEmpty(launchView.getLoanInfo())) {
        			LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
        			loanPrd.setProductCode(launchView.getLoanInfo().getProductType());
        			List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
        			if (productList.size() != 0) {
        				launchView.setProductName(productList.get(0).getProductName());
        			}						
        		}
        		// 查询产品列表	
        		LoanPrdMngEntity loanPrd1 = new LoanPrdMngEntity();	
        		List<LoanPrdMngEntity> productList1 = loanPrdMngService.selPrd(loanPrd1);
        		launchView.setProductList(productList1);
        		launchView.setApplyId(borrowApplyId);
        		areaService.coboAreaChange(launchView);
        		// 房产省市区数据更换成名字
        		areaService.houseAreaChange(launchView);
        		// 开户行省市数据添加
        		areaService.bankAreaChange(launchView);
        		// 申请信息,管辖城市
        		areaService.applyAreaChange(launchView);
        		// 公司地址省市id与name转换
        		areaService.companyAreaChange(launchView);
        		// 配偶公司地址
        		areaService.mateAreaChange(launchView);
        		// 共借人
        		areaService.setCoborrowers(launchView); 
        		// 借款信息-录入人
        		if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanCustomerService())) {
        			String service = areaService.getUserName(launchView.getLoanInfo().getLoanCustomerService());
        			launchView.getLoanInfo().setLoanCustomerServiceName(service);
        		}
        		if (StringUtils.isNotEmpty(launchView.getLoanInfo().getLoanManagerCode())) {
        			launchView.getLoanInfo().setLoanManagerName(areaService.getUserName(launchView.getLoanInfo().getLoanManagerCode()));
        		}	
        		String imageUrl = imageService.getImageUrl(stepName,launchView.getLoanCustomer().getLoanCode());
        		launchView.setImageUrl(imageUrl);
        		// 缓存码表取值
        		// 客户信息
        		LoanCustomer lc = launchView.getLoanCustomer();
        		lc.setCustomerSexLabel(DictCache.getInstance().getDictLabel("jk_sex", lc.getCustomerSex()));
        		lc.setDictCertTypeLabel(DictCache.getInstance().getDictLabel("com_certificate_type", lc.getDictCertType()));
        		lc.setDictMarryLabel(DictCache.getInstance().getDictLabel("jk_marriage", lc.getDictMarry()));
        		lc.setDictEducationLabel(DictCache.getInstance().getDictLabel("jk_degree", lc.getDictEducation()));
        		// 申请信息
        		LoanInfo loanInfo = launchView.getLoanInfo();
        		loanInfo.setDictLoanUserLabel(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfo.getDictLoanUse()));
        		loanInfo.setLoanUrgentFlagLabel(DictCache.getInstance().getDictLabel("jk_urgent_flag", loanInfo.getLoanUrgentFlag()));
        		// 信用资料
        		List<LoanCreditInfo> list = launchView.getLoanCreditInfoList();
        		for (LoanCreditInfo loanCreditInfo : list) {
        			loanCreditInfo.setDictMortgageTypeLabel(DictCache.getInstance().getDictLabel("jk_pledge_flag", loanCreditInfo.getDictMortgageType()));
        		}
        		// 职业信息/公司资料
        		LoanCompany loanCompany = launchView.getCustomerLoanCompany();
        		loanCompany.setCompPostLabel(DictCache.getInstance().getDictLabel("jk_job_type", loanCompany.getCompPost()));
        		loanCompany.setDictCompTypeLabel(DictCache.getInstance().getDictLabel("jk_unit_type", loanCompany.getDictCompType()));
        		// 房产资料
        		List<LoanHouse> hostList = launchView.getCustomerLoanHouseList();
        		for (LoanHouse loanHouse : hostList) {
        			loanHouse.setHouseBuywayLabel(DictCache.getInstance().getDictLabel("jk_house_buywayg", loanHouse.getHouseBuyway()));
        			loanHouse.setHousePledgeFlagLabel(DictCache.getInstance().getDictLabel("jk_pledge_flag", loanHouse.getHousePledgeFlag()));
        		}
        		// 联系人资料
        		List<Contact> contactList = launchView.getCustomerContactList();
        		for (Contact contact : contactList) {
        			contact.setRelationTypeLabel(DictCache.getInstance().getDictLabel("jk_relation_type", contact.getRelationType()));
        			if ("0".equals(contact.getRelationType())) {
        				contact.setContactRelationLabel(DictCache.getInstance().getDictLabel("jk_loan_family_relation", contact.getContactRelation()));					
        			}
        			if ("1".equals(contact.getRelationType())) {
        				contact.setContactRelationLabel(DictCache.getInstance().getDictLabel("jk_loan_workmate_relation", contact.getContactRelation()));					
        			}
        			if ("2".equals(contact.getRelationType())) {
        				contact.setContactRelationLabel(DictCache.getInstance().getDictLabel("jk_loan_other_relation", contact.getContactRelation()));					
        			}
        		}
        		// 银行卡资料
        		LoanBank loanBank = launchView.getLoanBank();
        		loanBank.setBankNameLabel(DictCache.getInstance().getDictLabel("jk_open_bank", loanBank.getBankName()));
        		loanBank.setBankSigningPlatformName(DictCache.getInstance().getDictLabel("jk_deduct_plat",loanBank.getBankSigningPlatform()));
        		launchView.setApplyId(applyId);
        		User user = UserUtils.getUser();
        		List<String> roleIds = user.getRoleIdList();
        		String isStoreAssistant = YESNO.NO.getCode();
        		if(!ObjectHelper.isEmpty(roleIds)){
        			for(String roleId:roleIds){
        				if(LoanRole.STORE_ASSISTANT.id.equals(roleId)){
        					isStoreAssistant = YESNO.YES.getCode();
        					break;
        				}
        			}
        		}
        		launchView.setIsStoreAssistant(isStoreAssistant);
        		view = launchView;
        	}
		}
         else if(ContractConstant.RATE_AUDIT.equals(stepName)){
            ContractBusiView rateAuditView = new ContractBusiView();
            CustInfo custInfo = custInfoDao.findReconsiderCustInfo(queryParam);
            String loanCode = null;         
            CustInfo auditInfo = custInfoDao.findAuditInfo(applyId);
            Contract contract = contractService.findByApplyId(applyId);
            //查询外访距离，外访标志
            Map<String,String> outSide=custInfoDao.findOutSide(custInfo.getLoanCode());
            if(outSide!=null){
            	rateAuditView.setOutside_flag(outSide.get("outside_flag"));
            	if(!"1".equals(outSide.get("outside_flag")) || null == outSide.get("item_distance") || "".equals(outSide.get("item_distance"))){
            		rateAuditView.setItem_distance("0公里");
            	}else{
            		rateAuditView.setItem_distance(outSide.get("item_distance")+"公里");
            	}
            }else{
            	rateAuditView.setItem_distance("0公里");
            }	
            /**查询借款信息 start*/
            Map<String,Object> param=new HashMap<String,Object>();
            param.put("loanCode", custInfo.getLoanCode());
            LoanInfo loanInfoObj=loanInfoDao.selectByLoanCode(param);
            // 判断单子是否拆分
 			rateAuditView.setIssplit(loanInfoObj.getIssplit());
            rateAuditView.setLoanInfo(loanInfoObj);
            /**查询借款信息 end*/
            if(ObjectHelper.isEmpty(contract)){
                contract = new Contract();
            }
            ContractFee contractFee = null;
            ContractFeeTemp contractFeeTempZCJ = null;
			ContractFeeTemp contractFeeTempJINXIN = null;
            try{
                if(!ObjectHelper.isEmpty(custInfo)){
                    ReflectHandle.copy(custInfo, rateAuditView);
                    loanCode = custInfo.getLoanCode();
                    if(StringUtils.isNotEmpty(loanCode)){
                    	List<LoanCoborrower> coborrowers=null;
                    	if("1".equals(loanInfoObj.getLoanInfoOldOrNewFlag())){//如果是新的，取最优自然人
                    		coborrowers = loanCoborrowerDao.selectByLoanCodeOne(loanCode);
                    	}else{//取所有共借人
                    		coborrowers = loanCoborrowerDao.selectByLoanCode(loanCode);
                    	}
                    	rateAuditView.setCoborrowers(coborrowers);
                    	String imageUrl = imageService.getImageUrl(stepName, loanCode);
                    	rateAuditView.setImageUrl(imageUrl);
                    }
                    // 设置门店名称
                    Org org = OrgCache.getInstance().get(rateAuditView.getLoanStoreOrgId());
                    if(!ObjectHelper.isEmpty(org)){
                        rateAuditView.setApplyOrgName(org.getName());
                    }
                  }
                if(!ObjectHelper.isEmpty(auditInfo)){
                    ReflectHandle.copy(auditInfo,rateAuditView);
                    ReflectHandle.copy(auditInfo,contract);
                }
                if(!ObjectHelper.isEmpty(contract)){
                  contract.setLoanCode(loanCode);
                  rateAuditView.setContract(contract);
                  if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
						String contractCodeZcj = contract.getContractCode() + "-1";
						String contractCodeJinxin = contract.getContractCode() + "-2";
						ContractTemp contractTempZCJ = contractTempDao.selectByContractCode(contractCodeZcj);
						rateAuditView.setContractZCJ(contractTempZCJ);
						ContractTemp contractTempJinxin = contractTempDao.selectByContractCode(contractCodeJinxin);
						rateAuditView.setContractJINXIN(contractTempJinxin);
					}
                }
               }catch (Exception e) {
                 e.printStackTrace();
               }
                String contractCode = contract.getContractCode();
                if(StringUtils.isNotEmpty(contractCode)){
                    contractFee = contractFeeService.findByContractCode(contractCode);
                }
              if(ObjectHelper.isEmpty(contractFee)){
                   contractFee = new ContractFee();
               }
              if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
  				// zcj
  				String contractCodeTempZCJ = contract.getContractCode() + "-1";
  				if (StringUtils.isNotEmpty(contractCodeTempZCJ)) {
  					contractFeeTempZCJ = contractFeeTempDao.selectByContractCode(contractCodeTempZCJ);
  				}
  				// jinxin
  				String contractCodeTempJINXIN = contract.getContractCode() + "-2";
  				if (StringUtils.isNotEmpty(contractCodeTempJINXIN)) {
  					contractFeeTempJINXIN = contractFeeTempDao.selectByContractCode(contractCodeTempJINXIN);
  				}

  				if (ObjectHelper.isEmpty(contractFeeTempZCJ)) {
  					contractFeeTempZCJ = new ContractFeeTemp();
  				}
  				if (ObjectHelper.isEmpty(contractFeeTempJINXIN)) {
  					contractFeeTempJINXIN = new ContractFeeTemp();
  				}
  			}
               // 计算信访费
               if(StringUtils.isNotEmpty(loanCode)){
                   Integer petitionFee = 0;
               List<OutsideCheckInfo> outSideInfos = outSideInfoService.selectMaxDistance(loanCode);
                if(!ObjectHelper.isEmpty(outSideInfos)){
                    OutsideCheckInfo outSideInfo = outSideInfos.get(0);
                   if(!ObjectHelper.isEmpty(outSideInfo) && !ObjectHelper.isEmpty(outSideInfo.getItemDistance()) && outSideInfo.getItemDistance().doubleValue()!=0){
                     Double distance = outSideInfo.getItemDistance().doubleValue();
                     petitionFee =ReckonFeeNew.PetitionFee(distance);  
                   }
                      
                }else{
                    
                }
                contractFee.setFeePetition(new BigDecimal(petitionFee));
				if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
					contractFeeTempZCJ.setFeePetition(new BigDecimal(petitionFee)
							.multiply(loanInfoObj.getZcj().divide(BigDecimal.valueOf(100))));
					contractFeeTempJINXIN.setFeePetition(new BigDecimal(petitionFee)
							.multiply(loanInfoObj.getJinxin().divide(BigDecimal.valueOf(100))));
				}
                
               }
               String formatVal = null;
               if(!ObjectHelper.isEmpty(contractFee.getFeeMonthRate())){
                   java.text.DecimalFormat myformat=new java.text.DecimalFormat("#,##0.000");
                   formatVal = myformat.format(contractFee.getFeeMonthRate().floatValue());
                   contractFee.setFeeMonthRateS(formatVal);
               }
               if(!ObjectHelper.isEmpty(contractFee.getFeeLoanRate())){
                   contractFee.setFeeLoanRatef(contractFee.getFeeLoanRate().setScale(3).floatValue());
               }
               if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
   				// 转换利率-zcj
   				if (!ObjectHelper.isEmpty(contractFeeTempZCJ.getFeeMonthRate())) {
   					java.text.DecimalFormat myformat = new java.text.DecimalFormat("#,##0.000");
   					formatVal = myformat.format(contractFeeTempZCJ.getFeeMonthRate().floatValue());
   					contractFeeTempZCJ.setFeeMonthRateS(formatVal);
   				}
   				// 转换利率-jinxin
   				if (!ObjectHelper.isEmpty(contractFeeTempJINXIN.getFeeMonthRate())) {
   					java.text.DecimalFormat myformat = new java.text.DecimalFormat("#,##0.000");
   					formatVal = myformat.format(contractFeeTempJINXIN.getFeeMonthRate().floatValue());
   					contractFeeTempJINXIN.setFeeMonthRateS(formatVal);
   				}

   				// zcj
   				if (!ObjectHelper.isEmpty(contractFeeTempZCJ.getFeeLoanRate())) {
   					contractFeeTempZCJ.setFeeLoanRatef(contractFeeTempZCJ.getFeeLoanRate().setScale(3).floatValue());
   				}
   				// jinxin
   				if (!ObjectHelper.isEmpty(contractFeeTempJINXIN.getFeeLoanRate())) {
   					contractFeeTempJINXIN
   							.setFeeLoanRatef(contractFeeTempJINXIN.getFeeLoanRate().setScale(3).floatValue());
   				}
   			}
              rateAuditView.setCtrFee(contractFee);
              if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
  				rateAuditView.setCtrFeeZCJ(contractFeeTempZCJ);
  				rateAuditView.setCtrFeeJINXIN(contractFeeTempJINXIN);
  			}
              FeeInfoEx feeInfoEx = ReckonFeeNew.FeeFormat(rateAuditView.getCtrFee(), rateAuditView.getContract(),ContractConstant.MONEY_FORMAT);
              rateAuditView.setFeeInfo(feeInfoEx);
              if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
  				// zcj
  				Contract contractZCJ = new Contract();
  				ReflectHandle.copy(rateAuditView.getContractZCJ(), contractZCJ);
  				ContractFee contractFeeZCJ = new ContractFee();
  				ReflectHandle.copy(rateAuditView.getCtrFeeZCJ(), contractFeeZCJ);
  				FeeInfoEx feeInfoExZCJ = ReckonFeeNew.FeeFormat(contractFeeZCJ, contractZCJ,
  						ContractConstant.MONEY_FORMAT);
  				rateAuditView.setFeeInfoZCJ(feeInfoExZCJ);
  				// jinxin
  				Contract contractJINXIN = new Contract();
  				ReflectHandle.copy(rateAuditView.getContractJINXIN(), contractJINXIN);
  				ContractFee contractFeeJINXIN = new ContractFee();
  				ReflectHandle.copy(rateAuditView.getCtrFeeJINXIN(), contractFeeJINXIN);
  				FeeInfoEx feeInfoExJINXIN = ReckonFeeNew.FeeFormat(contractFeeJINXIN, contractJINXIN,
  						ContractConstant.MONEY_FORMAT);
  				rateAuditView.setFeeInfoJINXIN(feeInfoExJINXIN);
  			}
              rateAuditView.setApplyId(applyId);
              LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
              if(StringUtils.isNotEmpty(rateAuditView.getProductType())){
                loanPrd.setProductCode(rateAuditView.getProductType());
                List<LoanPrdMngEntity> products = prdDao.selPrd(loanPrd);
                LoanPrdMngEntity product = products.get(0);
                rateAuditView.setProductName(product.getProductName());
               // rateAuditView.getCtrFee().setFeeAllRaio(new BigDecimal(product.getInterestRate()));
              }
              Contract con=contractDao.findByContractCode(contractCode);	
	              // 查询风险等级
	              List<Map<String,String>> list = rateInfoDao.getRiskLevel(contract.getLoanCode());
	              String riskLevel = "";
	              if(ObjectHelper.isNotEmpty(list) && !ObjectHelper.isEmpty(list.get(0))){
	                  if(list.get(0).get("reconsiderrisklevel")!=null&&!"".equals(list.get(0).get("reconsiderrisklevel"))){
	                      riskLevel = list.get(0).get("reconsiderrisklevel");
	                  }else{
	                      riskLevel = list.get(0).get("verifyrisklevel");
	                  }
	                  rateAuditView.setRiskLevel(riskLevel);
	              }
	              // 设置费率
	              rateAuditView.getCtrFee().setFeeAllRaio(auditInfo.getGrossRate());
	              if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
	  				// 设置费率-zcj
	  				rateAuditView.getCtrFeeZCJ().setFeeAllRaio(auditInfo.getGrossRate());
	  				// 设置费率-jinxin
	  				rateAuditView.getCtrFeeJINXIN().setFeeAllRaio(auditInfo.getGrossRate());
              }

              if(contract.getAuditAmount().compareTo(new BigDecimal(300000))>0){
                  String largeAmountImgUrl = imageService.getImageUrl(FlowStep.LAGE_AMOUNT_VIEW.getName(), custInfo.getLoanCode());
                  rateAuditView.setLargeAmountImageUrl(largeAmountImgUrl);
              }else{
                  rateAuditView.setLargeAmountFlag(YESNO.NO.getCode());
              }
              LoanModel loanModel = LoanModel.parseByCode(rateAuditView.getModel());
              if(!ObjectHelper.isEmpty(loanModel)){
               rateAuditView.setModelName(loanModel.getName());
              }
              if(!ObjectHelper.isEmpty(rateAuditView.getCoborrowers())){
                  this.setCoborrowers(rateAuditView);
              }
              LoanInfo loanInfo = loanInfoDao.getByLoanCode(loanCode);
         
              Date consultDate = LoanConsultDateUtils.findTimeByLoanCode(loanCode);
              Date onLineDate = DateUtils.convertStringToDate(SystemConfigConstant.LOAN_ONLINE_DATE);
              if(DateUtils.dateAfter(consultDate,onLineDate)){//根据咨询时间来判断
            	  List<CoeffReferJYJ> coeffReferJYJList=null;
            	  if(con.getProductType().equals(ProductUtil.PRODUCT_JYJ.getCode()) || con.getProductType().equals(ProductUtil.PRODUCT_NXD.getCode())){	
  					CoeffReferJYJ c=new CoeffReferJYJ();
  					c.setMonths(contract.getContractMonths().intValue());
  					c.setProductRate(auditInfo.getAuditRate());  //待获取
  					c.setProductCode(con.getProductType());
  					coeffReferJYJList= coeffReferJYJDao.selectCoeffRefer(c);
  					if(coeffReferJYJList.size()>0){
  						rateAuditView.setCurRate(coeffReferJYJList.get(0).getRate() + "");
  						rateAuditView.getCtrFee().setFeeMonthRate(new BigDecimal(coeffReferJYJList.get(0).getRate()));
  						if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
  							rateAuditView.getCtrFeeZCJ().setFeeMonthRate(new BigDecimal(coeffReferJYJList.get(0).getRate()));
  							rateAuditView.getCtrFeeJINXIN().setFeeMonthRate(new BigDecimal(coeffReferJYJList.get(0).getRate()));
  						}
  					}
  				}else{
	                  RateInfo rateInfo = rateInfoDao.findRateInfoByMonths(contract.getContractMonths());
	                  rateAuditView.setCurRate(rateInfo.getRate()+"");
	                  rateAuditView.getCtrFee().setFeeMonthRate(new BigDecimal(rateInfo.getRate()));
	                  if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
	  					rateAuditView.getCtrFeeZCJ().setFeeMonthRate(new BigDecimal(rateInfo.getRate()));
	  					rateAuditView.getCtrFeeJINXIN().setFeeMonthRate(new BigDecimal(rateInfo.getRate()));
	  				}
  				}
                  rateAuditView.setHasOnlineTime(Global.YES);
                  try {
                      ReckonFeeNew.ReckonFeeOneStep(rateAuditView.getContract(),rateAuditView.getCtrFee(),con,coeffReferJYJList);
                      rateAuditView.getFeeInfo().setFeePaymentAmount(rateAuditView.getCtrFee().getFeePaymentAmount()+"");
                      rateAuditView.getFeeInfo().setFeeConsult(rateAuditView.getCtrFee().getFeeConsult()+"");
                      rateAuditView.getFeeInfo().setMonthFeeConsult(rateAuditView.getCtrFee().getMonthFeeConsult()+"");
                      rateAuditView.getFeeInfo().setContractAmount(rateAuditView.getContract().getContractAmount()+"");
                      rateAuditView.getFeeInfo().setFeeAuditAmount(rateAuditView.getCtrFee().getFeeAuditAmount()+"");
                      rateAuditView.getFeeInfo().setMonthMidFeeService(rateAuditView.getCtrFee().getMonthMidFeeService()+"");
                      rateAuditView.getFeeInfo().setContractMonthRepayAmount(rateAuditView.getContract().getContractMonthRepayAmount()+"");
                      rateAuditView.getFeeInfo().setFeeService(rateAuditView.getCtrFee().getFeeService()+"");
                      rateAuditView.getFeeInfo().setMonthFeeService(rateAuditView.getCtrFee().getMonthFeeService()+"");
                      rateAuditView.getFeeInfo().setFeeInfoService(rateAuditView.getCtrFee().getFeeInfoService()+"");
                      rateAuditView.getFeeInfo().setFeeUrgedService(rateAuditView.getCtrFee().getFeeUrgedService()+"");
                      rateAuditView.getFeeInfo().setMonthPayTotalAmount(rateAuditView.getContract().getMonthPayTotalAmount()+"");
                      rateAuditView.getFeeInfo().setFeeCount(rateAuditView.getCtrFee().getFeeCount()+"");
                      rateAuditView.getFeeInfo().setContractExpectAmount(rateAuditView.getContract().getContractExpectAmount()+"");
                     
                      if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
  						// zcj
  						ReckonFeeNew.ReckonFeeOneStepTemp(rateAuditView.getContractZCJ(), rateAuditView.getCtrFeeZCJ(),con,coeffReferJYJList);
  						rateAuditView.getFeeInfoZCJ()
  								.setFeePaymentAmount(rateAuditView.getCtrFeeZCJ().getFeePaymentAmount() + "");
  						rateAuditView.getFeeInfoZCJ().setFeeConsult(rateAuditView.getCtrFeeZCJ().getFeeConsult() + "");
  						rateAuditView.getFeeInfoZCJ()
  								.setMonthFeeConsult(rateAuditView.getCtrFeeZCJ().getMonthFeeConsult() + "");
  						rateAuditView.getFeeInfoZCJ()
  								.setContractAmount(rateAuditView.getContractZCJ().getContractAmount() + "");
  						rateAuditView.getFeeInfoZCJ()
  								.setFeeAuditAmount(rateAuditView.getCtrFeeZCJ().getFeeAuditAmount() + "");
  						rateAuditView.getFeeInfoZCJ()
  								.setMonthMidFeeService(rateAuditView.getCtrFeeZCJ().getMonthMidFeeService() + "");
  						rateAuditView.getFeeInfoZCJ().setContractMonthRepayAmount(
  								rateAuditView.getContractZCJ().getContractMonthRepayAmount() + "");
  						rateAuditView.getFeeInfoZCJ().setFeeService(rateAuditView.getCtrFeeZCJ().getFeeService() + "");
  						rateAuditView.getFeeInfoZCJ()
  								.setMonthFeeService(rateAuditView.getCtrFeeZCJ().getMonthFeeService() + "");
  						rateAuditView.getFeeInfoZCJ()
  								.setFeeInfoService(rateAuditView.getCtrFeeZCJ().getFeeInfoService() + "");
  						rateAuditView.getFeeInfoZCJ()
  								.setFeeUrgedService(rateAuditView.getCtrFeeZCJ().getFeeUrgedService() + "");
  						rateAuditView.getFeeInfoZCJ()
  								.setMonthPayTotalAmount(rateAuditView.getContractZCJ().getMonthPayTotalAmount() + "");
  						rateAuditView.getFeeInfoZCJ().setFeeCount(rateAuditView.getCtrFeeZCJ().getFeeCount() + "");
  						rateAuditView.getFeeInfoZCJ()
  								.setContractExpectAmount(rateAuditView.getContractZCJ().getContractExpectAmount() + "");

  						// jinxin
  						ReckonFeeNew.ReckonFeeOneStepTemp(rateAuditView.getContractJINXIN(),
  								rateAuditView.getCtrFeeJINXIN(),con,coeffReferJYJList);
  						rateAuditView.getFeeInfoJINXIN()
  								.setFeePaymentAmount(rateAuditView.getCtrFeeJINXIN().getFeePaymentAmount() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setFeeConsult(rateAuditView.getCtrFeeJINXIN().getFeeConsult() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setMonthFeeConsult(rateAuditView.getCtrFeeJINXIN().getMonthFeeConsult() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setContractAmount(rateAuditView.getContractJINXIN().getContractAmount() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setFeeAuditAmount(rateAuditView.getCtrFeeJINXIN().getFeeAuditAmount() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setMonthMidFeeService(rateAuditView.getCtrFeeJINXIN().getMonthMidFeeService() + "");
  						rateAuditView.getFeeInfoJINXIN().setContractMonthRepayAmount(
  								rateAuditView.getContractJINXIN().getContractMonthRepayAmount() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setFeeService(rateAuditView.getCtrFeeJINXIN().getFeeService() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setMonthFeeService(rateAuditView.getCtrFeeJINXIN().getMonthFeeService() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setFeeInfoService(rateAuditView.getCtrFeeJINXIN().getFeeInfoService() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setFeeUrgedService(rateAuditView.getCtrFeeJINXIN().getFeeUrgedService() + "");
  						rateAuditView.getFeeInfoJINXIN().setMonthPayTotalAmount(
  								rateAuditView.getContractJINXIN().getMonthPayTotalAmount() + "");
  						rateAuditView.getFeeInfoJINXIN()
  								.setFeeCount(rateAuditView.getCtrFeeJINXIN().getFeeCount() + "");
  						rateAuditView.getFeeInfoJINXIN().setContractExpectAmount(
  								rateAuditView.getContractJINXIN().getContractExpectAmount() + "");
  					}
                      
                      String urgentFlag = loanInfo.getLoanUrgentFlag();
                      boolean isUrge = false;
                      if(YESNO.YES.getCode().equals(urgentFlag)){
                          isUrge = true;
                      }
                      BigDecimal feeExpedited = ReckonFeeNew.urgeFee(isUrge, rateAuditView.getContract(),rateAuditView.getCtrFee());
                      rateAuditView.getFeeInfo().setFeeExpedited(feeExpedited+"");
                      rateAuditView.getCtrFee().setFeePaymentAmount(rateAuditView.getCtrFee().getFeePaymentAmount().subtract(feeExpedited));
                      
                      if (loanInfoObj.getIssplit().equals(ContractConstant.ISSPLIT_1)) {
  						// zcj
  						BigDecimal feeExpeditedZCJ = ReckonFeeNew.urgeFeeTemp(isUrge, rateAuditView.getContractZCJ(),
  								rateAuditView.getCtrFeeZCJ());
  						rateAuditView.getFeeInfoZCJ().setFeeExpedited(feeExpeditedZCJ + "");
  						rateAuditView.getCtrFeeZCJ().setFeePaymentAmount(
  								rateAuditView.getCtrFeeZCJ().getFeePaymentAmount().subtract(feeExpeditedZCJ));
  						// jinxin
  						BigDecimal feeExpeditedJINXIN = ReckonFeeNew.urgeFeeTemp(isUrge,
  								rateAuditView.getContractJINXIN(), rateAuditView.getCtrFeeJINXIN());
  						rateAuditView.getFeeInfoJINXIN().setFeeExpedited(feeExpeditedJINXIN + "");
  						rateAuditView.getCtrFeeJINXIN().setFeePaymentAmount(
  								rateAuditView.getCtrFeeJINXIN().getFeePaymentAmount().subtract(feeExpeditedJINXIN));
  					}
//                    ReflectHandle.copy(rateAuditView.getContract(), rateAuditView.getFeeInfo());
//                    ReflectHandle.copy(rateAuditView.getCtrFee(), rateAuditView.getFeeInfo());
                    } catch (Exception e) {
                      e.printStackTrace();
                    }
              }else{
                  rateAuditView.setHasOnlineTime(Global.NO);
                  // 查询在当前时间里有效的利率
                  RateInfo query = new RateInfo();
                  query.setEffectiveFlag(YESNO.YES.getCode());
                  query.setCurDate(DateUtils.formatDate(new Date(), "HH:mm:ss"));
                  List<RateInfo> rateInfoList = rateInfoDao.findList(query);
                  if(ObjectHelper.isEmpty(rateInfoList)){
                      rateInfoList = new ArrayList<RateInfo>(); 
                  }
                  // 判断当前利率是否有效
                  String rateEffectiveFlag = YESNO.NO.getCode();
                  if(StringUtils.isNotEmpty(formatVal)){
                      for(RateInfo cur:rateInfoList){
                          if(cur.getRate().equals(formatVal)){
                              rateEffectiveFlag = YESNO.YES.getCode();
                              break;
                          }
                      }
                  }
                  // 将当前利率加入到列表中
                  if(YESNO.NO.getCode().equals(rateEffectiveFlag) && StringUtils.isNotEmpty(formatVal)){
                      RateInfo rate = new RateInfo();
                      rate.setRate(formatVal);
                      rateInfoList.add(rate);
                  }
                  // 设置当前利率跟有效性
                  rateAuditView.setCurRate(formatVal);
                  rateAuditView.setRateEffectiveFlag(rateEffectiveFlag);
                  rateAuditView.setRateInfoList(rateInfoList);
              }
             if(con.getProductType().equals(ProductUtil.PRODUCT_NXD.getCode())){//农信贷 实放金额=合同金额
 				 rateAuditView.getCtrFee().setFeePaymentAmount(rateAuditView.getContract().getContractAmount());
 		    }
              view = (BaseBusinessView)this.getLabel(rateAuditView);
        }else if(ContractConstant.CONFIRM_SIGN.equals(stepName)){
        	
        	String back_time = PropertyUtil.getStrValue(
 					"application.properties", "back_time", "");
     		long backTime = 0;
     		if(back_time!=null && !back_time.equals("")){
					backTime=Long.parseLong(back_time);
				
     		}
           ContractBusiView confirmSign = new ContractBusiView();
           confirmSign.setReconsiderFlag("1");
           confirmSign.setApplyId(applyId);
           CustInfo custInfo = custInfoDao.findReconsiderCustInfo(queryParam);
           confirmSign.setEmail(custInfo.getCustomerEmail());
           Map<String,Object> param = new HashMap<String,Object>();
           param.put("loanCode", custInfo.getLoanCode());
           LoanInfo loanInfo = loanInfoDao.selectByLoanCode(param);
           
           List<ContractTemp> contractTemps = contractTempDao.selectConfirmContract(custInfo.getLoanCode());
           confirmSign.setContractTemps(contractTemps);
           if(loanInfo!=null){
        	  if(loanInfo.getCustomerIntoTime().getTime()>backTime){
        		  confirmSign.setBackTimeFlag("0");
        	  }else{
        		  confirmSign.setBackTimeFlag("1");
        	  }
           }else{
        	   confirmSign.setBackTimeFlag("1");
           }
           confirmSign.setLoanInfo(loanInfo);
           CustInfo auditInfo = custInfoDao.findAuditInfo(applyId);
           Contract contract = contractService.findByApplyId(applyId);
           //List<LoanCoborrower> coborrowers= loanService.selectByLoanCode(custInfo.getLoanCode());
           List<LoanCoborrower> coborrowers=null;
	       if("1".equals(loanInfo.getLoanInfoOldOrNewFlag())){//如果是新的，取最优自然人
	    		coborrowers = loanCoborrowerDao.selectByLoanCodeOne(custInfo.getLoanCode());
	       }else{//取所有共借人
	    		coborrowers = loanCoborrowerDao.selectByLoanCode(custInfo.getLoanCode());
	       }
	       DictCache dict = DictCache.getInstance();
	       if(coborrowers!=null){
	    	   for(LoanCoborrower loanCoborrower :coborrowers){
	    		   loanCoborrower.setDictCertTypeName(dict.getDictLabel("com_certificate_type", loanCoborrower.getDictCertType()));
	    		   if(StringUtils.isNotEmpty(loanCoborrower.getCoboMobile())){
		    		   InnerBean InnerBean=new InnerBean();
		    		   InnerBean.setCreateBy("");
		    		   InnerBean.setCreateTime(null);
		    		   InnerBean.setMobileNums(loanCoborrower.getCoboMobile());//手机号
		    		   InnerBean.setCertNum("");//证件号
		    		   InnerBean.setTableName("T_JK_LOAN_COBORROWER");//表名
		    		   InnerBean.setCol("cobo_mobile");//字段
		    		   String phone= phoneSecretSerivice.disDecrypt(InnerBean);
		    		   loanCoborrower.setCoboMobile(phone);
		    	   }
	    	   }
	       }
       	   confirmSign.setCoborrowers(coborrowers);
           try{
               if(!ObjectHelper.isEmpty(custInfo)){
                   ReflectHandle.copy(custInfo,confirmSign);
               }
               if(!ObjectHelper.isEmpty(auditInfo)){
                   ReflectHandle.copy(auditInfo,confirmSign);
               }
               if(ObjectHelper.isEmpty(contract)){
                   contract = new Contract();
               }
               if(!ObjectHelper.isEmpty(contract.getCreateTime())){
                   Calendar calendar = Calendar.getInstance();
                   confirmSign.setSignStartDay(contract.getCreateTime());
                   calendar.setTime(contract.getCreateTime());
                   calendar.add(Calendar.DAY_OF_MONTH, 10);
                   confirmSign.setSignEndDay(calendar.getTime());
               }
               confirmSign.setContract(contract);
               List<LoanBank> loanBanks = loanBankService.selectByLoanCode(custInfo.getLoanCode(),ContractConstant.TOP_FLAG) ;
               if(!ObjectHelper.isEmpty(loanBanks)){
                 confirmSign.setLoanBank(loanBanks.get(0));
                 }
               }catch (Exception e) {
                 e.printStackTrace();
              }
             if(!ObjectHelper.isEmpty(contract)){
                String contractCode = contract.getContractCode();
                if(StringUtils.isNotEmpty(contractCode)){
                   ContractFee ctrFee=contractFeeService.findByContractCode(contractCode);
                   if(!ObjectHelper.isEmpty(ctrFee)){
                     confirmSign.setCtrFee(ctrFee);
                   }else{
                     confirmSign.setCtrFee(new ContractFee());
                 }
                }
              }else{
                 confirmSign.setCtrFee(new ContractFee());
              }
             // 短信验证取消
             if(!"1".equals(confirmSign.getCaptchaIfConfirm())){
                 Date endDay = confirmSign.getConfirmTimeout();
                 if(!ObjectHelper.isEmpty(endDay)){
                     Date currDay = new Date();
                     if(currDay.getTime() > endDay.getTime()){
                         confirmSign.setCustomerPin("");
                     }
                 }
             }
             // 产品code转Name
             LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
             if(StringUtils.isNotEmpty(confirmSign.getProductType())){
               loanPrd.setProductCode(confirmSign.getProductType());
               List<LoanPrdMngEntity> products = prdDao.selPrd(loanPrd);
               LoanPrdMngEntity product = products.get(0);
               confirmSign.setProductName(product.getProductName());
               if("A020".equals(confirmSign.getProductType())){
					confirmSign.setJyjBank(jyjBorrowBankConfigureService.queryList(1, LoanProductCode.PRO_JIAN_YI_JIE));
				}
             } 
             //  省份初始化查询
             Map<String,Object> params = new HashMap<String,Object>();
             params.put("parentId", CityInfoConstant.ROOT_ID);
             List<CityInfo> provinceList = cityInfoDao.findByParams(params);
             confirmSign.setProvinceList(provinceList);
             // 金账户省份初始化
             if(LoanModel.TG.getCode().equals(confirmSign.getModel())){
                 Map<String,Object> acParam = new HashMap<String,Object>();
                  acParam.put("areaType", ContractConstant.FY_PROVINCE_TYPE);
                  List<FyAreaCode> fyProvinceList = fyAreaCodeDao.queryACByParam(acParam);
                  confirmSign.setFyProvinceList(fyProvinceList);
             }
             LoanModel loanModel = LoanModel.parseByCode(confirmSign.getModel());
             if(!ObjectHelper.isEmpty(loanModel)){
                 confirmSign.setModelName(loanModel.getName());
             }
             User user = UserUtils.getUser();
             List<String> roleIds = user.getRoleIdList();
             String isStoreAssistant = YESNO.NO.getCode();
             String type="1";
             if(!ObjectHelper.isEmpty(roleIds)){
                for(String roleId:roleIds){
                  if(LoanRole.STORE_ASSISTANT.id.equals(roleId)){
                     isStoreAssistant = YESNO.YES.getCode();
                     break;
                   }
                }
                if("0".equals(confirmSign.getBackTimeFlag())){
                	 for(String roleId:roleIds){
                         if(LoanRole.STORE_ASSISTANT.id.equals(roleId)){
                        	 type="0";
                            break;
                          }
                       }
                }
              }
             if("0".equals(type)){
            	 confirmSign.setBackTimeFlag("0");
             }else{
            	 confirmSign.setBackTimeFlag("1");
             }
             confirmSign.setIsStoreAssistant(isStoreAssistant);
             
             if(StringUtils.isNotEmpty(confirmSign.getCustomerPhoneFirst())){
            	   InnerBean InnerBean=new InnerBean();
	    		   InnerBean.setCreateBy("");
	    		   InnerBean.setCreateTime(null);
	    		   InnerBean.setMobileNums(confirmSign.getCustomerPhoneFirst());//手机号
	    		   InnerBean.setCertNum("");//证件号
	    		   InnerBean.setTableName("T_JK_LOAN_CUSTOMER");//表名
	    		   InnerBean.setCol("customer_phone_first");//字段
	    		   String phone= phoneSecretSerivice.disDecrypt(InnerBean);
	    		   confirmSign.setCustomerPhoneFirst(phone);
            }
             view = (BaseBusinessView) this.getLabel(confirmSign);
         }else if(ContractConstant.CUST_SERVICE_SIGN.equals(stepName)){
            CustomerView custServiceSign = new CustomerView();
            custServiceSign.setStepName(stepName);
            custServiceSign.setApplyId(applyId);
            custServiceSign.setReconsiderFlag("1");
            Map<String,String> param = new HashMap<String,String>();
            param.put("applyId", applyId);
            LoanInfo loanInfo = loanInfoDao.findLoanLinkedContract(param);
            Map<String, Object> par = new HashMap<String, Object>();
			par.put("loanCode", loanInfo.getOldLoanCode());
			LoanCustomer loanCustomer = loanCustomerDao.selectByLoanCode(par);
            Contract contract = contractService.findByApplyId(applyId);
            CustInfo auditInfo  = custInfoDao.findAuditInfo(applyId);
            custServiceSign.setLegalMan(auditInfo.getAuditLegalMan());
            custServiceSign.setCompanyName(auditInfo.getAuditEnsureName());
            custServiceSign.setLoanInfoOldOrNewFlag(loanInfo.getLoanInfoOldOrNewFlag());
            custServiceSign.setOldLoanCode(loanInfo.getOldLoanCode());
            if(!ObjectHelper.isEmpty(contract)){
                custServiceSign.setContractDueDay(contract.getContractDueDay());
                custServiceSign.setCurDay(new Date());
                custServiceSign.setContractCode(contract.getContractCode());
                List<LoanBank> loanBanks = loanBankService.selectByLoanCode(contract.getLoanCode(),ContractConstant.TOP_FLAG) ;
                if(!ObjectHelper.isEmpty(loanBanks)){
                    custServiceSign.setLoanBank(loanBanks.get(0));
                }
            }
            //抓取无纸化需要的缓存的照片
            Map<String,String> paperlessPhotoParam = new HashMap<String,String>();
            if(!ObjectHelper.isEmpty(loanCustomer)){
                 ReflectHandle.copy(loanCustomer,custServiceSign);
             	 custServiceSign.setLoanCode(loanInfo.getLoanCode());
				 custServiceSign.setApplyId(loanInfo.getApplyId());
                 custServiceSign.setCustomerId(loanCustomer.getId());
                 paperlessPhotoParam.put("relationId", loanCustomer.getId());
                 PaperlessPhoto p = paperlessPhotoDao.getByRelationId(paperlessPhotoParam);
                 if(!ObjectHelper.isEmpty(p)){
                     custServiceSign.setIdCardId(p.getIdPhotoId());
                     custServiceSign.setCurrPlotId(p.getSpotPhotoId());
                 }
                 custServiceSign.setIdValidFlag(loanCustomer.getIdValidFlag());
            }
            List<LoanCoborrower> coborrowerList = null;
            if("1".equals(loanInfo.getLoanInfoOldOrNewFlag())){//如果是新的，取最优自然人
            	coborrowerList = loanCoborrowerDao.selectByLoanCodeOne(contract.getLoanCode());
            }else{
            	coborrowerList = loanCoborrowerDao.selectByLoanCode(contract.getLoanCode());
            }
            // 如果无纸化标识为1，则查询相关缓存文件
            if(YESNO.YES.getCode().equals(contract.getPaperLessFlag())){
                List<PaperlessPhoto> pList = paperlessPhotoDao.getByLoanCode(loanInfo.getOldLoanCode());
                if(!ObjectHelper.isEmpty(coborrowerList)){
                    for(LoanCoborrower b:coborrowerList){
                        for(PaperlessPhoto pp:pList){
                            if(b.getId().equals(pp.getRelationId())){
                                b.setIdCardId(pp.getIdPhotoId());
                                b.setCurPlotId(pp.getSpotPhotoId());
                            }
                        }
                    }
                }
            }
            List<ContractFile> files = contractFileDao.findContractFileByContractCode(contract.getContractCode());
            custServiceSign.setFiles(files);
            custServiceSign.setPaperlessFlag(contract.getPaperLessFlag());
            custServiceSign.setCoborrowerList(coborrowerList);
            custServiceSign.setLoanFlagCode(loanInfo.getLoanFlag());
            custServiceSign.setDictLoanStatus(loanInfo.getDictLoanStatus());
            custServiceSign.setApplyId(applyId);
            custServiceSign.setModel(loanInfo.getModel());
            String imageUrl = imageService.getImageUrl(FlowStep.CONTRACT_SIGN.getName(), loanInfo.getOldLoanCode());
            custServiceSign.setImageUrl(imageUrl);
            LoanModel loanModel = LoanModel.parseByCode(loanInfo.getModel());
            if(!ObjectHelper.isEmpty(loanModel)){
                custServiceSign.setModelName(loanModel.getName());
            }
            User user = UserUtils.getUser();
            List<String> roleIds = user.getRoleIdList();
            String isStoreAssistant = YESNO.NO.getCode();
            if(!ObjectHelper.isEmpty(roleIds)){
               for(String roleId:roleIds){
                 if(LoanRole.STORE_ASSISTANT.id.equals(roleId)){
                    isStoreAssistant = YESNO.YES.getCode();
                    break;
                  }
               }
             }
            custServiceSign.setIsStoreAssistant(isStoreAssistant);
            custServiceSign.setIssplit(loanInfo.getIssplit());
            view =(CustomerView)this.getLabel(custServiceSign);
         }else if(ContractConstant.CTR_CREATE.equals(stepName)){
            String loanCode = null;
            ContractBusiView contractCreate = new ContractBusiView();
            contractCreate.setApplyId(applyId);
            CustInfo custInfo = custInfoDao.findReconsiderCustInfo(queryParam);
            CustInfo auditInfo = custInfoDao.findAuditInfo(applyId);
           //查询外访距离，外访标志
            Map<String,String> outSide=custInfoDao.findOutSide(custInfo.getLoanCode());
            if(outSide!=null){
            	contractCreate.setOutside_flag(outSide.get("outside_flag"));
            	if(!"1".equals(outSide.get("outside_flag")) || null == outSide.get("item_distance") || "".equals(outSide.get("item_distance"))){
            		contractCreate.setItem_distance("0公里");
            	}else{
            		contractCreate.setItem_distance(outSide.get("item_distance")+"公里");
            	}
            }else{
            	contractCreate.setItem_distance("0公里");
            }
            Contract contract = contractService.findByApplyId(applyId);
            /**查询借款信息 start*/
            Map<String,Object> param=new HashMap<String,Object>();
            param.put("loanCode", custInfo.getLoanCode());
            LoanInfo loanInfo=loanInfoDao.selectByLoanCode(param);
            contractCreate.setLoanInfo(loanInfo);
            contractCreate.setIssplit(loanInfo.getIssplit());
            /**查询借款信息 end*/
            try{
                if(!ObjectHelper.isEmpty(custInfo)){
                   ReflectHandle.copy(custInfo,contractCreate);
                   loanCode = custInfo.getLoanCode();
                   if(StringUtils.isNotEmpty(loanCode)){
                	   List<LoanCoborrower> coborrowers =null;
                	   if("1".equals(loanInfo.getLoanInfoOldOrNewFlag())){//如果是新的，取最优自然人
                   			coborrowers = loanCoborrowerDao.selectByLoanCodeOne(loanCode);
                	   }else{
                		    coborrowers = loanCoborrowerDao.selectByLoanCode(loanCode);
                	   }
                	   contractCreate.setCoborrowers(coborrowers);
                   }
                   // 设置门店名称
                   Org org = OrgCache.getInstance().get(contractCreate.getLoanStoreOrgId());
                   if(!ObjectHelper.isEmpty(org)){
                       contractCreate.setApplyOrgName(org.getName());
                   }
                }
                if(!ObjectHelper.isEmpty(auditInfo)){
                    ReflectHandle.copy(auditInfo,contractCreate);
                }
                if(ObjectHelper.isEmpty(contract)){
                    contract = new Contract();
                }
                List<MiddlePerson> middlePersons = middlePersonDao.selectMiddleByName(new PageBounds(), new MiddlePerson());
                if(!ObjectHelper.isEmpty(middlePersons)){
                    for(MiddlePerson person:middlePersons){
                        if(GrantCommon.XIA_JING.equals(person.getMiddleName())){
                            contract.setMidId(person.getId());
                            contract.setMidName(person.getMiddleName());  
                            break;
                        }
                    }
                }
                List<LoanBank> loanBanks = loanBankService.selectByLoanCode(custInfo.getLoanCode(),ContractConstant.TOP_FLAG) ;
                if(!ObjectHelper.isEmpty(loanBanks)){
                    LoanBank loanBank = loanBanks.get(0);
                    loanBank.setBankSigningPlatformName(DeductPlat.getDeductPlat(loanBank.getBankSigningPlatform()));
                    contractCreate.setLoanBank(loanBank);
                }
                contractCreate.setMiddlePersons(middlePersons);
                contractCreate.setContract(contract);
               }catch (Exception e) {
                e.printStackTrace();
               }
            if(!ObjectHelper.isEmpty(contract)){
                String contractCode = contract.getContractCode();
                if(StringUtils.isNotEmpty(contractCode)){
                	if(ContractConstant.ISSPLIT_1.equals(loanInfo.getIssplit())){
                 	   ContractFee contractFee= new ContractFee();
                 	   contractFee.setFeePetition(new BigDecimal(0));
                 	   contract.setAuditAmount(new BigDecimal(0));
                        contractCreate.setCtrFee(contractFee);
                        HashMap<String, String> paramMap = new HashMap<String, String>();
                        paramMap.put("contractCode", contractCode+ContractConstant.SPLIT_ZCJ_FIX);
                       // paramMap.put("isreceive", YESNO.YES.getCode());
                 	   ContractFeeTemp zcjFeeTemp=contractFeeService.searchContractFeeTempByContractCode(paramMap);
                        if(zcjFeeTemp!=null){
                     	   contract.setContractReplayDay(zcjFeeTemp.getContractReplayDay());
                     	   contract.setContractDueDay(zcjFeeTemp.getContractDueDay());
                     	   contractFee.setFeePetition(BigDecimalTools.add(contractFee.getFeePetition(), zcjFeeTemp.getFeePetition()));
                     	   zcjFeeTemp.setMonthFeeServiceTotal(BigDecimalTools.mul(zcjFeeTemp.getMonthFeeService(), zcjFeeTemp.getContractMonths()));
                     	   contract.setAuditAmount(BigDecimalTools.add(contract.getAuditAmount(), zcjFeeTemp.getAuditAmount()));
                     	   zcjFeeTemp.setChannelFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag", zcjFeeTemp.getChannelFlag()));
                     	   contractCreate.setZcjFeeTemp(zcjFeeTemp);
                     	   contractFee.setFeeAllRaio(zcjFeeTemp.getFeeAllRaio());
                     	   contractFee.setComprehensiveServiceRate(zcjFeeTemp.getComprehensiveServiceRate());
                     	   contractFee.setMonthRateService(zcjFeeTemp.getMonthRateService());
                     	   contractFee.setFeeMonthRate(zcjFeeTemp.getFeeMonthRate());
                        }else{
                     	   contractCreate.setZcjFeeTemp(new ContractFeeTemp());
                        }
                        paramMap.put("contractCode", contractCode+ContractConstant.SPLIT_JX_FIX);
                        ContractFeeTemp jinXinFeeTemp=contractFeeService.searchContractFeeTempByContractCode(paramMap);
                        if(jinXinFeeTemp!=null){
                     	   contract.setContractReplayDay(jinXinFeeTemp.getContractReplayDay());
                     	   contract.setContractDueDay(jinXinFeeTemp.getContractDueDay());
                     	   contractFee.setFeePetition(BigDecimalTools.add(contractFee.getFeePetition(), jinXinFeeTemp.getFeePetition()));
                     	   jinXinFeeTemp.setChannelFlagName(DictCache.getInstance().getDictLabel("jk_channel_flag", jinXinFeeTemp.getChannelFlag()));
                     	   jinXinFeeTemp.setMonthFeeServiceTotal(BigDecimalTools.mul(jinXinFeeTemp.getMonthFeeService(), jinXinFeeTemp.getContractMonths()));
                     	   contract.setAuditAmount(BigDecimalTools.add(contract.getAuditAmount(), jinXinFeeTemp.getAuditAmount()));
                     	   contractCreate.setJinXinFeeTemp(jinXinFeeTemp);
                     	   contractFee.setFeeAllRaio(jinXinFeeTemp.getFeeAllRaio());
                     	   contractFee.setComprehensiveServiceRate(jinXinFeeTemp.getComprehensiveServiceRate());
                     	   contractFee.setMonthRateService(jinXinFeeTemp.getMonthRateService());
                     	   contractFee.setFeeMonthRate(jinXinFeeTemp.getFeeMonthRate());
                        }else{
                     	   contractCreate.setJinXinFeeTemp(new ContractFeeTemp());
                        }
                        if(ObjectHelper.isNotEmpty(zcjFeeTemp)&& ObjectHelper.isNotEmpty(jinXinFeeTemp)&&
                     		   (YESNO.NO.getCode().equals(zcjFeeTemp.getIsreceive())||YESNO.NO.getCode().equals(jinXinFeeTemp.getIsreceive()))){
                     	   zcjFeeTemp.setFeePetition(contractFee.getFeePetition());
                     	   jinXinFeeTemp.setFeePetition(contractFee.getFeePetition());
                        }
                	}else{
                		 ContractFee contractFee=contractFeeService.findByContractCode(contractCode);
                         if(!ObjectHelper.isEmpty(contractFee)){
                             contractCreate.setCtrFee(contractFee);
                         }else{
                             contractCreate.setCtrFee(new ContractFee());
                         }
                	}
                	
                  
                 }
             }else{
                 contractCreate.setCtrFee(new ContractFee());
             }
            LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
            if(StringUtils.isNotEmpty(contractCreate.getProductType())){
              loanPrd.setProductCode(contractCreate.getProductType());
              List<LoanPrdMngEntity> products = prdDao.selPrd(loanPrd);
              LoanPrdMngEntity product = products.get(0);
              contractCreate.setProductName(product.getProductName());
            }
            LoanModel loanModel = LoanModel.parseByCode(contractCreate.getModel());
            if(!ObjectHelper.isEmpty(loanModel)){
                contractCreate.setModelName(loanModel.getName());
            }
            List<Map<String,String>> list = rateInfoDao.getRiskLevel(contract.getLoanCode());
            String riskLevel = "";
            if(ObjectHelper.isNotEmpty(list) && !ObjectHelper.isEmpty(list.get(0))){
                if(list.get(0).get("reconsiderrisklevel")!=null&&!"".equals(list.get(0).get("reconsiderrisklevel"))){
                    riskLevel = list.get(0).get("reconsiderrisklevel");
                }else{
                    riskLevel = list.get(0).get("verifyrisklevel");
                }
                contractCreate.setRiskLevel(riskLevel);
            }
            view = (BaseBusinessView)this.getLabel(contractCreate);
         }else if(ContractConstant.CTR_AUDIT.equals(stepName)){
            String loanCode = null;
            ContractBusiView contractAudit = new ContractBusiView();
            contractAudit.setApplyId(applyId);
            CustInfo custInfo =  custInfoDao.findReconsiderCustInfo(queryParam);
            CustInfo auditInfo = custInfoDao.findAuditInfo(applyId);
            //查询外访距离，外访标志
            Map<String,String> outSide=custInfoDao.findOutSide(custInfo.getLoanCode());
            if(outSide!=null){
            	contractAudit.setOutside_flag(outSide.get("outside_flag"));
            	if(!"1".equals(outSide.get("outside_flag")) || null == outSide.get("item_distance") || "".equals(outSide.get("item_distance"))){
            		contractAudit.setItem_distance("0公里");
            	}else{
            		contractAudit.setItem_distance(outSide.get("item_distance")+"公里");
            	}
            }else{
            	contractAudit.setItem_distance("0公里");
            }
            Contract contract = contractService.findByApplyId(applyId);
            /**查询借款信息 start*/
            Map<String,Object> param=new HashMap<String,Object>();
            param.put("loanCode", custInfo.getLoanCode());
            LoanInfo loanInfo=loanInfoDao.selectByLoanCode(param);
            contractAudit.setLoanInfo(loanInfo);
            /**查询借款信息 end*/
            Float score = custInfo.getIdValidScore();
            if(!ObjectHelper.isEmpty(score)){
                if(ContractConstant.SCORE_MIN<score && score<=ContractConstant.SCORE_MAX){
                    contractAudit.setIdValidMessage(ContractConstant.ID_VALID_MESSAGE);
                }
              }
            try{
                if(!ObjectHelper.isEmpty(custInfo)){
                  ReflectHandle.copy(custInfo,contractAudit);
                  loanCode = custInfo.getLoanCode();
                  if(StringUtils.isNotEmpty(loanCode)){
                	  List<LoanCoborrower> coborrowers = null;
                      if("1".equals(loanInfo.getLoanInfoOldOrNewFlag())){//如果是新的，取最优自然人
                 			coborrowers = loanCoborrowerDao.selectByLoanCodeOne(loanCode);
                      }else{
                    	  	coborrowers = loanCoborrowerDao.selectByLoanCode(loanCode);
                      }
                      for(LoanCoborrower c:coborrowers){
                          Float cobScore = c.getIdValidScore();
                          if(!ObjectHelper.isEmpty(cobScore)){
                            if(ContractConstant.SCORE_MIN<cobScore && cobScore<=ContractConstant.SCORE_MAX){
                                c.setIdValidMessage(ContractConstant.ID_VALID_MESSAGE);
                            }
                          }
                      }
                      contractAudit.setCoborrowers(coborrowers);
                  }
                  // 设置门店名称
                  Org org = OrgCache.getInstance().get(contractAudit.getLoanStoreOrgId());
                  if(!ObjectHelper.isEmpty(org)){
                      contractAudit.setApplyOrgName(org.getName());
                  }
                }
                if(!ObjectHelper.isEmpty(auditInfo)){
                  ReflectHandle.copy(auditInfo,contractAudit);
                }
                if(ObjectHelper.isEmpty(contract)){
                    contract = new Contract();
                }
                contractAudit.setContract(contract);;
                
               }catch (Exception e) {
                 e.printStackTrace();
               }
                LoanModel loanModel = LoanModel.parseByCode(contractAudit.getModel());
                if(!ObjectHelper.isEmpty(loanModel)){
                    contractAudit.setModelName(loanModel.getName());
                }
               if(!ObjectHelper.isEmpty(contract)){
                 String contractCode = contract.getContractCode();
                 if(StringUtils.isNotEmpty(contractCode)){
                    ContractFee contractFee=contractFeeService.findByContractCode(contractCode);
                    
                    if(!ObjectHelper.isEmpty(contractFee)){
                        contractAudit.setCtrFee(contractFee);
                        if(ChannelFlag.JINXIN.getCode().equals(custInfo.getLoanFlag())){
                            Map<String,Object> maps = gcCeilingDao.getJXCeillingData();
                            BigDecimal feePaymentAmount = contractFee.getFeePaymentAmount();
                            if(maps!=null){
                                BigDecimal remainLimit = (BigDecimal)maps.get("remainlimit");
                                if(remainLimit.compareTo(feePaymentAmount)<0){
                                    contractAudit.setUpLimit(YESNO.YES.getCode()); 
                                }else{
                                    contractAudit.setUpLimit(YESNO.NO.getCode()); 
                                    contractAudit.setJxId((String)maps.get("id"));
                                    contractAudit.setJxVersion(String.valueOf(maps.get("version")));
                                }
                            }
                        }
                    }else{
                        contractAudit.setCtrFee(new ContractFee());
                 }
               }
                 if(StringUtils.isNotEmpty(loanCode)){
                     List<LoanBank> loanBanks = loanBankService.selectByLoanCode(loanCode,ContractConstant.TOP_FLAG);
                     if(!ObjectHelper.isEmpty(loanBanks)){
                         Map<String,Object> params = new HashMap<String,Object>();
                         CityInfo cityInfo = null;
                         LoanBank loanBank = loanBanks.get(0);
                         if(StringUtils.isEmpty(loanBank.getBankProvince())){
                             params.put("areaCode", loanBank.getBankProvince());
                             cityInfo = cityInfoDao.findOneArea(params);
                             loanBank.setBankProvinceName(cityInfo.getAreaName());
                         }
                         if(StringUtils.isEmpty(loanBank.getBankCity())){
                          params.put("areaCode", loanBank.getBankCity());
                          cityInfo = cityInfoDao.findOneArea(params);
                          loanBank.setBankCityName(cityInfo.getAreaName());
                         }
                         contractAudit.setLoanBank(loanBank);
                     }
                 }
              }else{
                  contractAudit.setCtrFee(new ContractFee());
              }
               LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
               if(StringUtils.isNotEmpty(contractAudit.getProductType())){
                 loanPrd.setProductCode(contractAudit.getProductType());
                 List<LoanPrdMngEntity> products = prdDao.selPrd(loanPrd);
                 LoanPrdMngEntity product = products.get(0);
                 contractAudit.setProductName(product.getProductName());
               }
              // 查询账单日
               Date contractDueDay = contract.getContractDueDay();     // 还款日
               if(!ObjectHelper.isEmpty(contractDueDay)){
                   Calendar calendar = Calendar.getInstance();
                   calendar.setTime(contractDueDay);
                   GlBill glBill = new GlBill();
                   glBill.setSignDay(calendar.get(Calendar.DAY_OF_MONTH));
                   if(ProductUtil.PRODUCT_NXD.getCode().equals(contractAudit.getProductType())){
   					
	   					contractAudit.setBillDay(calendar.get(Calendar.DATE));
	   				}else{
	   					GlBill tagGlBill = glBillDao.findBySignDay(glBill);
	   					contractAudit.setBillDay(tagGlBill.getBillDay());
	   				}
               }
               String midId = contract.getMidId();
               MiddlePerson midPerson = middlePersonDao.selectById(midId);
               if(!ObjectHelper.isEmpty(midPerson)){
                 contract.setMidName(midPerson.getMiddleName());
               }
               String imageUrl = imageService.getImageUrl(FlowStep.CONTRACT_AUDIT_CONTRACT.getName(), custInfo.getLoanCode());
               contractAudit.setImageUrl(imageUrl);
              if(contract.getAuditAmount().compareTo(new BigDecimal(300000))>0){
                  String largeAmountImgUrl = imageService.getImageUrl(FlowStep.LAGE_AMOUNT_VIEW.getName(), custInfo.getLoanCode());
                  contractAudit.setLargeAmountImageUrl(largeAmountImgUrl);
               }else{
                  contractAudit.setLargeAmountFlag(YESNO.NO.getCode());
               }
              String riskLevel = "";
              List<Map<String,String>> list = rateInfoDao.getRiskLevel(contract.getLoanCode());
              if(ObjectHelper.isNotEmpty(list) && !ObjectHelper.isEmpty(list.get(0))){
                  if(list.get(0).get("reconsiderrisklevel")!=null&&!"".equals(list.get(0).get("reconsiderrisklevel"))){
                      riskLevel = list.get(0).get("reconsiderrisklevel");
                  }else{
                      riskLevel = list.get(0).get("verifyrisklevel");
                  }
                  contractAudit.setRiskLevel(riskLevel);
              }
               String protocolImgUrl = imageService.getImageUrl(FlowStep.PROTOCOL_VIEW.getName(), custInfo.getLoanCode());  
               contractAudit.setProtocolImgUrl(protocolImgUrl);
               List<ContractFile> files = contractFileDao.findContractFileByContractCode(contract.getContractCode());
               contractAudit.setFiles(files);
               view = (BaseBusinessView) this.getLabel(contractAudit);
          // 放款明细确认办理页面初始化
        }else if (GrantCommon.GRANT_SURE_NAME.equals(stepName)) {
            LoanGrantEx lg=new LoanGrantEx();
            GrantDealView gqp=new GrantDealView();
            // 根据applyId查询页面中显示放款确认的字段
            lg=loanGrantService.queryReconsiderGrantDeal(applyId);
            if (!ObjectHelper.isEmpty(lg)) {
                try {
                    BeanUtils.copyProperties(lg, gqp);
                    gqp.setApplyId(applyId);
                } catch (Exception e) {
                    e.printStackTrace();
                } 
            }
            view=gqp;
        }
        return view;
    }
    /**
	 * 设置共借人信息 2015年11月24日 By zhanghao
	 * 
	 * @param launchView
	 * @return none
	 */
	private void setCoborrowers(ContractBusiView contractView) {
		List<LoanCoborrower> coborrowers = contractView.getCoborrowers();
		StringBuffer coborrowerBuffer = new StringBuffer();
		StringBuffer coboCerNumBuf = new StringBuffer();
		for (LoanCoborrower cur : coborrowers) {
			if (coborrowerBuffer.length() == 0) {
				coborrowerBuffer.append(cur.getCoboName());
				coboCerNumBuf.append(cur.getCoboCertNum());
			} else {
				coborrowerBuffer.append("," + cur.getCoboName());
				coboCerNumBuf.append(","+cur.getCoboCertNum());
			}
		}
		if (coborrowerBuffer.length() == 0) {
			coborrowerBuffer.append("");
			coboCerNumBuf.append("");
		}
		contractView.getContract().setCoboName(coborrowerBuffer.toString());
		contractView.getContract().setCoboCertNum(coboCerNumBuf.toString());

	}
	// 码值转换为中文
			private  Object getLabel(Object obj){
			    Object retObj = null;
				DictCache dict = DictCache.getInstance();
				if(!ObjectHelper.isEmpty(obj)){
					if(obj instanceof ContractBusiView){
						ContractBusiView contractView = (ContractBusiView)obj;
					   if(!ObjectHelper.isEmpty(contractView)){
						contractView.setMainCertTypeName(dict.getDictLabel("com_certificate_type", contractView.getMainCertType()));
						contractView.setLoanFlagCode(contractView.getLoanFlag());
						contractView.setLoanFlag(dict.getDictLabel("jk_channel_flag", contractView.getLoanFlagCode()));
						contractView.setMainCertSexName(dict.getDictLabel("jk_sex", contractView.getMainCertSex()));
						List<LoanCoborrower> coborrowers =  contractView.getCoborrowers();
						LoanBank loanBank = contractView.getLoanBank();
						Contract contract = contractView.getContract();
						if(!ObjectHelper.isEmpty(coborrowers) && coborrowers.size()>0){
							for(LoanCoborrower curr:coborrowers){
								curr.setDictCertTypeName(dict.getDictLabel("com_certificate_type", curr.getDictCertType()));
								curr.setCoboSexName(dict.getDictLabel("jk_sex", curr.getCoboSex()));
							}
							contractView.setCoborrowers(coborrowers);
						}
						if(!ObjectHelper.isEmpty(loanBank)){
							loanBank.setBankSigningPlatformName(dict.getDictLabel("jk_deduct_plat", loanBank.getBankSigningPlatform()));
							loanBank.setBankNameLabel(dict.getDictLabel("jk_open_bank", loanBank.getBankName()));
							contractView.setLoanBank(loanBank);
						}
						if(!ObjectHelper.isEmpty(contract)){
							contract.setDictRepayMethodName(dict.getDictLabel("jk_repay_interest_way", contract.getDictRepayMethod()));
							contractView.setContract(contract);
						}
						retObj = contractView;
					  }
				   }else if(obj instanceof CustomerView){
					   CustomerView customerView = (CustomerView)obj;
					   if(!ObjectHelper.isEmpty(customerView)){
						   customerView.setDictCertTypeName(dict.getDictLabel("com_certificate_type", customerView.getDictCertType()));
						   customerView.setDictMarryName(dict.getDictLabel("jk_marriage", customerView.getDictMarry()));
						   customerView.setCustomerSexName(dict.getDictLabel("jk_sex", customerView.getCustomerSex()));
						   customerView.setDictEducationName(dict.getDictLabel("jk_degree", customerView.getDictEducation()));
						   customerView.setLoanFlag(dict.getDictLabel("jk_channel_flag", customerView.getLoanFlagCode()));
					   }
					   retObj = customerView;
				   }
				}
				return retObj;
			}
}
