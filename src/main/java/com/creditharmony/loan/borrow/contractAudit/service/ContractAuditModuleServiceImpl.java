package com.creditharmony.loan.borrow.contractAudit.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.adapter.bean.in.csh.CshLoanConfirmByResultInBean;
import com.creditharmony.adapter.bean.in.csh.CshLoanConfirmBySendInBean;
import com.creditharmony.adapter.bean.in.csh.CshLoanConfirmByUpdateInBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmByResultDetailOutBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmByResultOutBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmBySendOutBean;
import com.creditharmony.adapter.bean.out.csh.CshLoanConfirmByUpdateOutBean;
import com.creditharmony.adapter.constant.ServiceType;
import com.creditharmony.adapter.core.client.ClientPoxy;
import com.creditharmony.bpm.frame.view.BaseBusinessView;
import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.cache.OrgCache;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ContractLog;
import com.creditharmony.core.loan.type.ContractResult;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.LoanTrustState;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.core.users.entity.Org;
import com.creditharmony.core.users.entity.User;
import com.creditharmony.core.users.service.UserManager;
import com.creditharmony.core.users.util.UserUtils;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.dao.LoanCoborrowerDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanCoborrower;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.applyinfo.service.DataEntryService;
import com.creditharmony.loan.borrow.applyinfo.service.LoanService;
import com.creditharmony.loan.borrow.applyinfo.service.StoreReviewService;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFileDao;
import com.creditharmony.loan.borrow.contract.dao.ContractOperateInfoDao;
import com.creditharmony.loan.borrow.contract.dao.CustInfoDao;
import com.creditharmony.loan.borrow.contract.dao.PaperlessPhotoDao;
import com.creditharmony.loan.borrow.contract.dao.PostponeDao;
import com.creditharmony.loan.borrow.contract.dao.RateInfoDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractFee;
import com.creditharmony.loan.borrow.contract.entity.ContractFile;
import com.creditharmony.loan.borrow.contract.entity.ContractOperateInfo;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.service.ContractAndAlreadyService;
import com.creditharmony.loan.borrow.contract.service.ContractAndPersonInfoService;
import com.creditharmony.loan.borrow.contract.service.ContractCustInfoService;
import com.creditharmony.loan.borrow.contract.service.ContractFeeService;
import com.creditharmony.loan.borrow.contract.service.ContractFileService;
import com.creditharmony.loan.borrow.contract.service.ContractFlagService;
import com.creditharmony.loan.borrow.contract.service.ContractService;
import com.creditharmony.loan.borrow.contract.service.CustInfoService;
import com.creditharmony.loan.borrow.contract.service.DelayService;
import com.creditharmony.loan.borrow.contract.service.LoanBankService;
import com.creditharmony.loan.borrow.contract.service.OutsideCheckInfoService;
import com.creditharmony.loan.borrow.contract.service.PaperLessService;
import com.creditharmony.loan.borrow.contract.service.PaperlessPhotoService;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.contractAudit.dao.ContractAuditModuleDao;
import com.creditharmony.loan.borrow.contractAudit.entity.Assist;
import com.creditharmony.loan.borrow.contractAudit.entity.ContractAuditBanli;
import com.creditharmony.loan.borrow.contractAudit.entity.ContractAuditDatas;
import com.creditharmony.loan.borrow.grant.dao.LoanGrantDao;
import com.creditharmony.loan.borrow.grant.entity.LoanGrant;
import com.creditharmony.loan.borrow.grant.entity.ex.LoanGrantEx;
import com.creditharmony.loan.borrow.grant.service.GrantAuditService;
import com.creditharmony.loan.borrow.grant.service.GrantSureService;
import com.creditharmony.loan.borrow.payback.entity.Payback;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.borrow.stores.service.AreaService;
import com.creditharmony.loan.borrow.transate.dao.LoanMinuteDao;
import com.creditharmony.loan.borrow.transate.service.LoanInfoService;
import com.creditharmony.loan.borrow.transate.service.LoanMinuteService;
import com.creditharmony.loan.channel.goldcredit.dao.GCCeilingDao;
import com.creditharmony.loan.channel.goldcredit.service.GCCeilingService;
import com.creditharmony.loan.common.dao.CityInfoDao;
import com.creditharmony.loan.common.dao.FileDiskInfoDao;
import com.creditharmony.loan.common.dao.FyAreaCodeDao;
import com.creditharmony.loan.common.dao.GlBillDao;
import com.creditharmony.loan.common.dao.LoanBankDao;
import com.creditharmony.loan.common.dao.LoanCustomerDao;
import com.creditharmony.loan.common.dao.LoanPrdMngDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.MiddlePersonDao;
import com.creditharmony.loan.common.dao.PaybackDao;
import com.creditharmony.loan.common.dao.SystemSetMaterDao;
import com.creditharmony.loan.common.entity.CityInfo;
import com.creditharmony.loan.common.entity.LoanBank;
import com.creditharmony.loan.common.entity.LoanCustomer;
import com.creditharmony.loan.common.entity.LoanPrdMngEntity;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.common.entity.ex.FlowStep;
import com.creditharmony.loan.common.event.CreateOrderFileIdEx;
import com.creditharmony.loan.common.service.HistoryService;
import com.creditharmony.loan.common.service.ImageService;
import com.creditharmony.loan.common.service.LoanPrdMngService;
import com.creditharmony.loan.common.workFlow.consts.LoanFlowRoute;
import com.creditharmony.loan.utils.PhoneSecretSerivice;
import com.creditharmony.loan.yunwei.overtime.service.impl.LoanFlowServiceImpl;

@Service
public class ContractAuditModuleServiceImpl implements
		IContractAuditModuleService {
	
	protected Logger logger = LoggerFactory.getLogger(ContractAuditModuleServiceImpl.class);
    @Resource
    private ContractAuditModuleDao contractAuditModuleDao;
	

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
    private CustInfoService custInfoService;
    
    @Autowired
    private ContractCustInfoService contractCustInfoServcie;
	
    @Autowired
    private OutsideCheckInfoService outSideInfoService;

    @Autowired
    private GrantAuditService grantAuditService;
    
    @Autowired
    private GrantSureService grantSureService;

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
    private LoanPrdMngDao prdDao;

      
    @Autowired
    private ContractOperateInfoDao contractOperateInfoDao;
    
    @Autowired
    private GlBillDao glBillDao;
    
    
    @Autowired
    private FyAreaCodeDao fyAreaCodeDao;
    
    
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
    private PhoneSecretSerivice phoneSecretSerivice;
    

	@Autowired
	private ContractFlagService ctrFlagService;

	@Autowired
	private LoanPrdMngService loanPrdMngService;
	
	
	@Autowired
	private ContractAndPersonInfoService contractPersonService;
	
	@Autowired
	private ContractAndAlreadyService contractAndAlreadyService;
	
	//sjw
	@Autowired
	private CityInfoDao cityInfoDao;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private LoanMinuteService lms;
	
	@Autowired
	private ContractFileService contractFileService;
	
	@Autowired
	private LoanInfoService ls;
	
	@Autowired
    private PaperLessService paperLessService;
    
    @Autowired
    private PaperlessPhotoService paperlessPhotoService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private GCCeilingService service;
    
    @Autowired
    private FileDiskInfoDao diskInfoDao;
    
 
    
    @Autowired
    private LoanInfoService loanInfoService; 
 
    
    @Autowired
    private PaybackDao paybackDao;
    
    @Autowired
    private ContractFeeDao contractFeeDao;
    
    @Autowired
    private LoanBankDao loanBankDao;
    @Autowired
    private UserManager userManager;
    
 
    
    @Autowired
	private HistoryService historyService;
    
    @Autowired
    private DelayService delayService;
    
    @Autowired
    private LoanFlowServiceImpl loanFlowServiceImpl;
    
    @Autowired
    private PostponeDao postponeDao;
    
    
    
    
    
    

    @Autowired
    private ReconsiderApplyDao reconsiderApplyDao;

    @Autowired
    private SystemSetMaterDao systemSetMaterDao;
    
    @Autowired
    private CustInfoDao custInfoDao;
    
    @Autowired
    private ContractDao contractDao;
    
    
	
	@Autowired
	private LoanGrantDao loanGrantDao;
    @Autowired
    private LoanMinuteDao loanMinuteDao;
    @Resource
    private CreateOrderFileIdEx createOrderFileIdEx;
    
    @Resource
    private AssistService assistService;
    
    
    
	@Override
	public Page<ContractAuditDatas> searchContractAuditDatas(ContractAuditDatas ctrQryParam,
			Page<ContractAuditDatas> page) {
		PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
		pageBounds.setFilterOrderBy("ORDER_FIELD");
		pageBounds.setCountBy("loan_code");
        PageList<ContractAuditDatas> pageList = (PageList<ContractAuditDatas>)contractAuditModuleDao.findList(ctrQryParam, pageBounds);
        PageUtil.convertPage(pageList, page);
		return page;
	}

	@Override
	public ContractAuditBanli getOneContractAudit(ContractAuditDatas ctrQryParam) {
		 String loanCode = ctrQryParam.getLoanCode();
		 ContractAuditBanli contractAudit = new ContractAuditBanli();
		 String applyId= ctrQryParam.getApplyId();
		 contractAudit = contractAuditModuleDao.getContractAudit(ctrQryParam);
		 String oldLoanCode= contractAudit.getOldLoanCode();//拆分前的loanCode
	        //查询外访距离，外访标志
	        Map<String,String> outSide=custInfoService.findOutSide(contractAudit.getOldLoanCode());
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
	        Float score = contractAudit.getIdValidScore();
	        if(!ObjectHelper.isEmpty(score)){
	          if(ContractConstant.SCORE_MIN<score && score<=ContractConstant.SCORE_MAX){
	              contractAudit.setIdValidMessage(ContractConstant.ID_VALID_MESSAGE);
	          }
	        }
	        try{
	                  List<LoanCoborrower> coborrowers = null;
	                  if("1".equals(contractAudit.getLoanInfoOldOrNewFlag())){//如果是新的，取最优自然人
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
	              // 设置门店名称
	              Org org = OrgCache.getInstance().get(contractAudit.getLoanStoreOrgId());
	              if(!ObjectHelper.isEmpty(org)){
	                  contractAudit.setApplyOrgName(org.getName());
	              }
	         
	            if(ObjectHelper.isEmpty(contract)){
	                contract = new Contract();
	            }
	            contractAudit.setContract(contract);;
	            
	           }catch (Exception e) {
	             e.printStackTrace();
	           }
	        
	           if(!ObjectHelper.isEmpty(contract)){
	             String contractCode = contract.getContractCode();
	             if(StringUtils.isNotEmpty(contractCode)){
	                ContractFee contractFee=contractFeeService.findByContractCode(contractCode);
	                if(!ObjectHelper.isEmpty(contractFee)){
	                    contractAudit.setCtrFee(contractFee);
	                    // upLimit为空表示非金信标识 ，1、0表示金信标识
	                    if(ChannelFlag.JINXIN.getCode().equals(contractAudit.getLoanFlag())){
	                        Map<String,Object> maps = gcCeilingDao.getJXCeillingData();
	                        BigDecimal feePaymentAmount = contractFee.getFeePaymentAmount();
	                        if(maps!=null){
	                            BigDecimal remainLimit = (BigDecimal)maps.get("remainlimit");
	                            if(remainLimit.compareTo(feePaymentAmount)<0){
	                                contractAudit.setUpLimit(YESNO.YES.getCode()); 
	                                contractAudit.setLimitId((String)maps.get("id"));
	                            }else{
	                            	contractAudit.setLimitId((String)maps.get("id"));
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
	         
	            String imageUrl = imageService.getImageUrl(FlowStep.CONTRACT_AUDIT_CONTRACT.getName(), oldLoanCode);
	            if(contract.getAuditAmount().compareTo(new BigDecimal(300000))>0){
	              String largeAmountImgUrl = imageService.getImageUrl(FlowStep.LAGE_AMOUNT_VIEW.getName(), oldLoanCode);
	              contractAudit.setLargeAmountImageUrl(largeAmountImgUrl);
	             }else{
	                 contractAudit.setLargeAmountFlag(YESNO.NO.getCode());
	             }
	            contractAudit.setImageUrl(imageUrl);
	            List<ContractFile> files = contractFileDao.findContractFileByContractCode(contract.getContractCode());
	            contractAudit.setFiles(files);
	            
	            //转换
	            DictCache dict = DictCache.getInstance();
				   if(!ObjectHelper.isEmpty(contractAudit)){
					List<LoanCoborrower> coborrowers =  contractAudit.getCoborrowers();
					LoanBank loanBank = contractAudit.getLoanBank();
					Contract contractTwo = contractAudit.getContract();
					if(!ObjectHelper.isEmpty(coborrowers) && coborrowers.size()>0){
						for(LoanCoborrower curr:coborrowers){
							curr.setDictCertTypeName(dict.getDictLabel("com_certificate_type", curr.getDictCertType()));
							curr.setCoboSexName(dict.getDictLabel("jk_sex", curr.getCoboSex()));
						}
						contractAudit.setCoborrowers(coborrowers);
					}
					if(!ObjectHelper.isEmpty(loanBank)){
						loanBank.setBankSigningPlatformName(dict.getDictLabel("jk_deduct_plat", loanBank.getBankSigningPlatform()));
						loanBank.setBankNameLabel(dict.getDictLabel("jk_open_bank", loanBank.getBankName()));
						contractAudit.setLoanBank(loanBank);
					}
					if(!ObjectHelper.isEmpty(contractTwo)){
						contractTwo.setDictRepayMethodName(dict.getDictLabel("jk_repay_interest_way", contractTwo.getDictRepayMethod()));
						contractAudit.setContract(contractTwo);
					}
				   }
	           return contractAudit;
	}
	
	/**
	 * 合同审核提交 逻辑
	 */
	@Override
	@Transactional(readOnly = false,value = "loanTransactionManager")
	public BaseBusinessView commitContractAudit(WorkItemView workItem,ContractBusiView busiView) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		 //更新的合同
        Contract  contract = busiView.getContract();
        String response = workItem.getResponse();
        //response 数据流向 说明	
        //'TO_KING_OPEN'>'金账户开户' ； 'TO_PAY_CONFIRM'>‘待款项确认’ ；“TO_CONTRACT_SIGN">"待上传合同" ； “TO_CONFIRM_SIGN">"待确认签署"
        String dictOperateResult = busiView.getDictOperateResult();
        //dictOperateResult 审批结果 说明		
        //"2">"通过" ； "0">"不通过退回"
        String remark = busiView.getRemarks(); //remark  备注  
        String loanCode = busiView.getContract().getLoanCode();
        Date now = new Date();//当前时间
        
        //查询相关信息插入到呼叫中心回访表
		Contract contractOld=contractService.findByContractCode(contract.getContractCode());
    	//信易借产品  不推送数据到呼叫中心
		boolean isFlag=true;
		if(contractOld!=null&&"A013".equals(contractOld.getProductType())){
			isFlag=false;
		}
		//// 审批通过 && 非信易借 
        if(("TO_PAY_CONFIRM".equals(response)||"TO_KING_OPEN".equals(response))&&isFlag){
			
			//当合同审核提交时,往呼叫中心推送数据，同时将工作流和数据库的回访状态改为待回访
			//判断时间，旧数据不走流程
			String loancode=busiView.getLoanCode();
			if(loancode==null){
				loancode=contract.getLoanCode();
			}
			
			LoanInfo loanInfoOld=loanInfoService.findStatusByLoanCode(loancode);
			Map<String,Object> maploancode=new HashMap<String,Object>();
			maploancode.put("loanCode", loancode);
			LoanCustomer loanCustomerOld=loanCustomerDao.selectByLoanCode(maploancode);
			Map<String,Object> mapcontractcode=new HashMap<String,Object>();
			mapcontractcode.put("contractCode", contract.getContractCode());
			Map<String,Object> mapPay=new HashMap<String,Object>();
			mapPay.put("contractCode", contract.getContractCode());
			Payback payback=paybackDao.selectpayBack(mapPay);
			ContractFee contractFee=contractFeeDao.findByContractCode(contract.getContractCode());
			LoanBank loanBank=loanBankDao.selectByLoanCode(loancode);
		
				logger.info("调用放款前回访查询接口>>>开始" + contract.getContractCode());
				
				//调用接口查询  
				CshLoanConfirmByResultInBean resInBean = new CshLoanConfirmByResultInBean(); 
				List<String> param = new ArrayList<String>(); 
				param.add(contract.getContractCode()); 
				resInBean.setContractCodeList(param); 
				ClientPoxy serviceQue = new ClientPoxy(ServiceType.Type.CSH_LOAN_CONFIRM_RESULT); 
				CshLoanConfirmByResultOutBean outBean = (CshLoanConfirmByResultOutBean) serviceQue.callService(resInBean); 
				logger.info("回访查询 接口返回参数：" + contract.getContractCode() + " "+outBean.getParam());
				if(outBean.getParam().contains("0000")&&outBean.getItems()==null){//查询结果为空，表示首次插入
					//调接口插入数据 更新回访状态为待回访，推送次数为一，客户状态为放款前，推送时间 为当前时间
					logger.info("调用放款前回访插入接口" + contract.getContractCode());
					CshLoanConfirmBySendInBean inBean=new CshLoanConfirmBySendInBean();
					if(contractFee!=null){
						inBean.setOutboundFee(contractFee.getFeePetition()); 
					}
					if(payback!=null){
						if(payback.getPaybackDay()!=null){
							inBean.setReimbursementDate(payback.getPaybackDay());
						}
					}
					if(loanBank!=null){
						
						inBean.setReimbursementBank(DictCache.getInstance().getDictLabel("tz_open_bank", loanBank.getBankName())); 
						inBean.setBankAccount(loanBank.getBankAccount()); 
					}
					if(loanCustomerOld!=null){
						inBean.setSex(DictCache.getInstance().getDictLabel("jk_sex", loanCustomerOld.getCustomerSex())); 
						inBean.setMobilePhone(loanCustomerOld.getCustomerPhoneFirst());
					}
					if(loanInfoOld!=null){
						inBean.setLoanUse(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfoOld.getDictLoanUse())); 
						User loanManager = userManager.get(loanInfoOld.getLoanManagerCode());
						if(loanManager!=null){
							inBean.setCustomerManager(loanManager.getName()); 
						}
						
						if(loanInfoOld.getCustomerIntoTime()!=null){
							inBean.setNewTime(sdf.format(loanInfoOld.getCustomerIntoTime())); 
						}
						inBean.setIsUrgent(DictCache.getInstance().getDictLabel("jk_urgent_flag", loanInfoOld.getLoanUrgentFlag())); 
						OrgCache orgCache = OrgCache.getInstance();
						Org storeOrg = orgCache.get(loanInfoOld.getLoanStoreOrgId());
						if(storeOrg!=null){
							inBean.setStoreName(storeOrg.getName()); 
						}
						LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
						List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
						for(LoanPrdMngEntity entity:productList){
							if(entity.getProductCode().equals(loanInfoOld.getProductType())){
								inBean.setProductType(entity.getProductName()); 
							}
						}
						
					}
					if(contractOld!=null){
						inBean.setContractCode(contractOld.getContractCode()); 
						inBean.setCustomerName(contractOld.getLoanName()); 
						inBean.setCertNum(contractOld.getLoanCertNum()); 
						inBean.setMonthPayment(contractOld.getMonthPayTotalAmount()); 
						if(contractOld.getContractMonths()!=null){
							inBean.setLoanPeriods(contractOld.getContractMonths().intValue()); 
						}
						if(contractOld.getContractFactDay()!=null){
							inBean.setContractSignDate(sdf.format(contractOld.getContractFactDay()));
						}
						inBean.setContractFee(contractOld.getContractAmount()); 
						
						inBean.setMark(DictCache.getInstance().getDictLabel("jk_channel_flag", contractOld.getChannelFlag())); 
						inBean.setCorborrow(contractOld.getCoboName()); 
					}
					
					//inBean.setAdFee("1"); //征信费
					//调用插入接口
					ClientPoxy serviceIns = new ClientPoxy(ServiceType.Type.CSH_LOAN_CONFIRM_SEND); 
					CshLoanConfirmBySendOutBean snedOutBean = (CshLoanConfirmBySendOutBean) serviceIns.callService(inBean); 
					logger.info("commitContractAudit:合同审核推送呼叫中心返回结果 CshLoanConfirmBySendOutBean：" + contract.getContractCode() + " "+ snedOutBean.getParam());
					if(contract!=null){
						//0表示待回访
						contract.setRevisitStatus("0");
						contract.setPushTime(now);
						contract.setRevisitReason("");
						if(contract.getPushNumber()!=null){
							//推送次数，为之前的次数加一
							contract.setPushNumber(contract.getPushNumber()+1);
						}else{
							//首次推送
							contract.setPushNumber(1);
						}
					}
				}else if(outBean.getItems()!=null){
					CshLoanConfirmByResultDetailOutBean detailOutBean=outBean.getItems().get(0);
					//查询回访失败且回访次数小于3 或待回访数据
					if((detailOutBean.getRevisitStatus().equals("0"))||(detailOutBean.getRevisitStatus().equals("-1")&&detailOutBean.getRevisitNumber()<3)){
						//调接口update 更新回访状态为待回访，推送次数加一，客户状态为放款前，推送时间 更新
						logger.info("调用放款前回访修改接口 " + contract.getContractCode());
						CshLoanConfirmByUpdateInBean updateInBean = new CshLoanConfirmByUpdateInBean(); 
						if(contractFee!=null){
							updateInBean.setOutboundFee(contractFee.getFeePetition()); 
						}
						if(payback!=null){
							if(payback.getPaybackDay()!=null){
								updateInBean.setReimbursementDate(payback.getPaybackDay());
							}
						}
						if(loanBank!=null){
							updateInBean.setReimbursementBank(DictCache.getInstance().getDictLabel("tz_open_bank", loanBank.getBankName())); 
							updateInBean.setBankAccount(loanBank.getBankAccount()); 
						}
						if(loanCustomerOld!=null){//jk_sex
							updateInBean.setSex(DictCache.getInstance().getDictLabel("jk_sex", loanCustomerOld.getCustomerSex())); 
							updateInBean.setMobilePhone(loanCustomerOld.getCustomerPhoneFirst());
							
						}
						if(loanInfoOld!=null){
							updateInBean.setLoanUse(DictCache.getInstance().getDictLabel("jk_loan_use", loanInfoOld.getDictLoanUse())); 
							User loanManager = userManager.get(loanInfoOld.getLoanManagerCode());
							if(loanManager!=null){
								updateInBean.setCustomerManager(loanManager.getName()); 
							}
							if(loanInfoOld.getCustomerIntoTime()!=null){
								updateInBean.setNewTime(sdf.format(loanInfoOld.getCustomerIntoTime())); 
							}
							updateInBean.setIsUrgent(DictCache.getInstance().getDictLabel("jk_urgent_flag", loanInfoOld.getLoanUrgentFlag())); 
							OrgCache orgCache = OrgCache.getInstance();
							Org storeOrg = orgCache.get(loanInfoOld.getLoanStoreOrgId());
							if(storeOrg!=null){
								updateInBean.setStoreName(storeOrg.getName()); 
							}
							LoanPrdMngEntity loanPrd = new LoanPrdMngEntity();
							List<LoanPrdMngEntity> productList = loanPrdMngService.selPrd(loanPrd);
							for(LoanPrdMngEntity entity:productList){
								if(entity.getProductCode().equals(loanInfoOld.getProductType())){
									updateInBean.setProductType(entity.getProductName()); 
								}
							}
						}
						if(contractOld!=null){
							updateInBean.setContractCode(contractOld.getContractCode()); 
							updateInBean.setCustomerName(contractOld.getLoanName()); 
							updateInBean.setCertNum(contractOld.getLoanCertNum()); 
							updateInBean.setMonthPayment(contractOld.getMonthPayTotalAmount()); 
							if(contractOld.getContractMonths()!=null){
								updateInBean.setLoanPeriods(contractOld.getContractMonths().intValue()); 
							}
							if(contractOld.getContractFactDay()!=null){
								updateInBean.setContractSignDate(sdf.format(contractOld.getContractFactDay()));
							}
							updateInBean.setContractFee(contractOld.getContractAmount()); 
							updateInBean.setMark(DictCache.getInstance().getDictLabel("jk_channel_flag", contractOld.getChannelFlag())); 
							updateInBean.setCorborrow(contractOld.getCoboName()); 
						}
						//更新回访状态为待回访
						updateInBean.setRevisitStatus("0");
						//更新客户状态为放款前
						updateInBean.setCustomerStatus("0");
						//更新推送次数+1
						Contract contractByNum=contractService.findByContractCode(contract.getContractCode());
						if(contractByNum!=null){
							Integer number=contractByNum.getPushNumber();
							if(number!=null){
								updateInBean.setPushNumber(number.intValue()+1);
							}
							
						}
						//更新推送时间
						updateInBean.setPushTime(now);
						ClientPoxy service = new ClientPoxy(ServiceType.Type.CSH_LOAN_CONFIRM_UPDATE); 
						CshLoanConfirmByUpdateOutBean updateOBean = (CshLoanConfirmByUpdateOutBean) service.callService(updateInBean); 
						logger.info("commitContractAudit ：合同审核推送呼叫中心返回结果 CshLoanConfirmByUpdateOutBean："+ contract.getContractCode() +updateOBean.getParam()); 
						if(contract!=null){
							//0表示待回访
							contract.setRevisitStatus("0");
							contract.setPushTime(now);
							contract.setRevisitReason("");
							if(contract.getPushNumber()!=null){
								//推送次数，为之前的次数加一
								contract.setPushNumber(contract.getPushNumber()+1);
							}else{
								//首次推送
								contract.setPushNumber(1);
							}
						}
					}
				}
				
		}
        
        
    	
        ////登陆操作用户
        User user = UserUtils.getUser();
        String userCode = user.getUserCode();
        //手动验证退回的原因
        String returnReason=busiView.getReturnReason();
        String applyId = busiView.getApplyId();
        //定义一些变量
        //定义一些变量 
        String backFlag = null; // 标明合同阶段退回 1表示有退回 | 如果是流向“签约确认”，“签订合同”则设置为 1 ，如果是流向“待款项确认”，“金账户开户” 需要再次判断
        String backReason = "";//审核不通过 的退回原因
        String timeOutFlag = "";//超时标志 1 - 开启 0 - 不开始
		  String status = "00";//将要流向的 状态码
          String statusName = ""; //将要流向的 状态描述
          String kingStatus = null;
          // 合同审核  设置状态
          if (LoanFlowRoute.PAYCONFIRM.equals(response)) { // 放款确认
              status = LoanApplyStatus.LOAN_SEND_CONFIRM.getCode();
              statusName = LoanApplyStatus.LOAN_SEND_CONFIRM.getName();
          } else if (LoanFlowRoute.KING_OPEN.equals(response)) { // 金帐户开户
              status = LoanApplyStatus.KING_TO_OPEN.getCode();
              statusName = LoanApplyStatus.KING_TO_OPEN.getName();
              kingStatus = LoanTrustState.WKH.value; // 设置金账户状态为未开户
          } else if (LoanFlowRoute.CONFIRMSIGN.equals(response)) { // 签约确认
              status = LoanApplyStatus.SIGN_CONFIRM.getCode();
              statusName = LoanApplyStatus.SIGN_CONFIRM.getName();
          } else if (LoanFlowRoute.CONTRACTSIGN.equals(response)) { // 签订合同
              status = LoanApplyStatus.CONTRACT_UPLOAD.getCode();
              statusName = LoanApplyStatus.CONTRACT_UPLOAD.getName();
          }
          
          //更新合同 退回状态1)
            if(LoanFlowRoute.PAYCONFIRM.equals(response) || LoanFlowRoute.KING_OPEN.equals(response)){
            	 int count= contractDao.getHiscontract(contract.getLoanCode());
            	 if(count>0){
            		 backFlag = ContractConstant.BACK_FLAG;
            	 }else{
            		 backFlag = ContractConstant.CANCEL_BACK_FLAG;
            	 }
            }
            contract.setBackFlag(backFlag);
            	// 退回原因， 位置是否合理
          if (StringUtils.isNotEmpty(backReason)) {
              if (backReason.trim().startsWith(",")
                      || backReason.trim().endsWith(",")) {
                  backReason = backReason.replace(",", "");
              }
          }
          		//更新合同退回状态 为退回
          if(contract.getBackFlag() !=null && !"".equals(contract.getBackFlag()) ){
          	contractService.updateContractForBack(contract);
          }
          
//          busiView.setKingStatus(kingStatus);
//          busiView.setDictLoanStatus(statusName);
//          busiView.setDictLoanStatusCode(status);
//          busiView.setBackFlag(backFlag);
//          busiView.setGrantSureBackReason(backReason);
          // 插入合同操作记录
          ContractOperateInfo contractOperateInfo = new ContractOperateInfo();
          		// 1)审核意见
          if(dictOperateResult!=null && !("".equals(dictOperateResult))){
          		contractOperateInfo.setRemarks(remark);
          		contractOperateInfo.setDictOperateResult(dictOperateResult);
          }
          		//2)手动验证的结果
          String verification=busiView.getVerification();
          if(verification!=null && !("".equals(verification))){
          	contractOperateInfo.setVerification(verification);
              contractOperateInfo.setReturnReason(returnReason);
          }
          contractOperateInfo.setContractCode(contract.getContractCode());
          contractOperateInfo.setLoanCode(contract.getLoanCode());
          		// 3)下一个节点
          contractOperateInfo.setDictContractNextNode(statusName);
          contractOperateInfo.setOperator(userCode);
          contractOperateInfo.setOperateTime(now);
          contractOperateInfo.setOperateOrgCode(user.getOrgIds());
          contractOperateInfo.setIsNewRecord(false);
          contractOperateInfo.setDictOperateType(ContractLog.CONTRACT_AUDIFY
        		  .getCode());
          contractOperateInfo.preInsert();
          		//4)插入合同操作记录表
          
          contractOperateInfoDao.insertSelective(contractOperateInfo);
        
        
		////////LoanFlowUpdCtrEx 回调方法 
        
        ContractFee contractFee = busiView.getCtrFee(); 
        String stepName = workItem.getStepName();
//        String dictCheckStatus = busiView.getDictLoanStatusCode(); //前面代码赋值
        contract.setDictCheckStatus(status); 
        
        ////if是合同审批通过的处理
        if(ContractConstant.CONTRACT_SUBMIT.equals(dictOperateResult)){
            Contract oldContract = contractService.findByLoanCode(loanCode); 
            ///向放款表 插入记录
            LoanGrant loanGrant = new LoanGrant();
            loanGrant.setContractCode(contract.getContractCode());
            loanGrant.setLoanCode(loanCode);
            BigDecimal feePaymentAmount = contractFee.getFeePaymentAmount();
            if(feePaymentAmount!=null){
                loanGrant.setGrantAmount(feePaymentAmount);
                loanGrant.setGrantFailAmount(new BigDecimal(0));
            }
            loanGrant.setMidId(contract.getMidId());
            LoanGrantEx loan = loanGrantDao.findGrant(loanGrant);
            if(ObjectHelper.isEmpty(loan)){
                loanGrant.preInsert();
                loanGrantDao.insertGrant(loanGrant);
            }else{
                loanGrant.setGrantBackMes(" ");
                loanGrant.preUpdate();
                loanGrantDao.updateLoanGrant(loanGrant);
            }
            /// 修改合同信息 审核次数
            Integer auditCount = oldContract.getAuditCount();
            if(ObjectHelper.isEmpty(auditCount)){
                auditCount = 1; 
            }else{
                auditCount+= 1;
            }
            // 如果没有超出金信额度 逻辑处理 金信额度相应减少   前面已经有相应判断了渠道为金信
            if(YESNO.NO.getCode().equals(busiView.getUpLimit())){
                Map<String,Object> maps = gcCeilingDao.getJXCeillingData(); 
                float usedLimit = ((BigDecimal)maps.get("kinnobu_used_limit")).floatValue();
                Map<String,Object> updMap = new HashMap<String,Object>();
                Integer version = (Integer) maps.get("version");
                updMap.put("id",busiView.getJxId());
                
                updMap.put("srcVersion",version);
                updMap.put("tagVersion", version+1);
                updMap.put("usedLimit", usedLimit+busiView.getFeePaymentAmount());
                gcCeilingDao.updusedLimit(updMap);
                //TODO 没有取消金信标识的时候 设置渠道标识 多次一举 准备删除
            }else if(StringUtils.isEmpty(busiView.getUpLimit())){
                LoanInfo loanInfo = new LoanInfo();
                loanInfo.setLoanFlag(contract.getChannelFlag());
                loanInfo.setLoanCode(contract.getLoanCode());
                loanInfoDao.updateLoanInfo(loanInfo);
                //  取消金信标识逻辑
            }else if(YESNO.YES.getCode().equals(busiView.getUpLimit())){
                Contract curContract = contractDao.findByLoanCode(contract.getLoanCode());
                LoanInfo loanInfo = new LoanInfo();
                loanInfo.setLoanFlag(contract.getChannelFlag());
                loanInfo.setLoanCode(contract.getLoanCode());
                loanInfoDao.updateLoanInfo(loanInfo);
                String oldChannelFlag = curContract.getChannelFlag();
                busiView.setOldChannelFlag(ChannelFlag.parseByCode(oldChannelFlag).getName());
                busiView.setChannelFlagAdd(YESNO.NO.getCode());
                this.insertHistory(busiView);
            }
            contract.setAuditCount(auditCount);
            contract.preUpdate();
            contractDao.updateContract(contract);
            ///else 是合同退回的处理
        }else{
            Contract contractNew = new Contract();
            contractNew.setContractCode(contract.getContractCode());
            contractNew.setDictCheckStatus(status);
            contractNew.setBackFlag(contract.getBackFlag());
            contractNew.preUpdate();
            contractDao.updateContract(contractNew);
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("loanCode", contract.getLoanCode());
            LoanInfo curLoanInfo = loanInfoDao.selectByLoanCode(param);
            if(StringUtils.isNotEmpty(curLoanInfo.getFrozenCode())){ // 合同审核退回的时候，自动将已经冻结的单子驳回冻结申请。
                LoanInfo loanInfo = new LoanInfo();
                loanInfo.setLoanCode(contract.getLoanCode());
                loanInfo.setFrozenCode(" ");
                loanInfo.setFrozenReason(" ");
                loanInfo.setFrozenLastApplyTime(now);
                loanInfo.preUpdate();
                loanInfoDao.update(loanInfo);
            }
            
            
            backFlag = ContractConstant.BACK_FLAG;
            //手动验证不通过退回得原因
            if(returnReason!=null){
            	backReason = returnReason;
            }
            //合同审核退回得原因
            if(remark!=null){
            	backReason = remark;
            }
            // 开始签约时间检测
            timeOutFlag =YESNO.YES.getCode();
        }
	
          
          
          //////【1】更新借款表状态  包括复议的 数据
          Map<String, Object> loanParam = new HashMap<String, Object>();
          loanParam.put("applyId", applyId);
          Map<String, Object> param = new HashMap<String, Object>();
          param.put("modifyBy", user.getId());
          param.put("modifyTime", now);
          Map<String, Object> loanParam1 = new HashMap<String, Object>();
          loanParam1.put("loanCode", loanCode);
          LoanInfo loanInfo = loanInfoDao.selectByLoanCode(loanParam1);
          if (!ObjectHelper.isEmpty(loanInfo)) {

              param.put("loanCode", loanCode);
              param.put("dictLoanStatus", status);
              loanInfoDao.updateLoanStatus(param);
          } else {
              List<ReconsiderApply> applys = reconsiderApplyDao
                      .findReconsiderApply(loanParam);
              if (!ObjectHelper.isEmpty(applys)) {
                  ReconsiderApply apply = applys.get(0);
                  if (!ObjectHelper.isEmpty(apply)) {
                      param.put("loanCode", apply.getLoanCode());
                      param.put("dictLoanStatus", status);
                      loanInfoDao.updateLoanStatus(param);
                  }
              }
          }

          
          
          
          
          ////////历史记录 ok
          LoanStatusHis record = new LoanStatusHis();
          // APPLY_ID
          record.setApplyId(applyId);
          // 借款Code
          record.setLoanCode(busiView.getContract().getLoanCode());
          // 状态
          record.setDictLoanStatus(status);
          // 操作步骤(回退,放弃,拒绝 等)
          record.setOperateStep(stepName);
          
          // // 操作结果 
          		//手动验证的操作结果
          ContractResult contractResultTwo = ContractResult.parseByCode(verification);
          String verificationName=null;
          if(contractResultTwo!=null){
          	verificationName=contractResultTwo.getName();
          }
          if(verificationName!=null && !("".equals(verificationName))){
          	record.setOperateResult(verificationName);
          }
          		// 没有手动验证的操作结果
          String operResultName = null;
          ContractResult contractResult = ContractResult.parseByCode(dictOperateResult);
          if(contractResult!=null){
          	operResultName = contractResult.getName();
          }
          
          if(operResultName!=null && !("".equals(operResultName))){
          	record.setOperateResult(operResultName);
          }

          // 备注
          record.setRemark(backReason);
          // 系统标识
          record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
          // 设置Crud属性值
          record.preInsert();
          // 操作时间
          record.setOperateTime(record.getCreateTime());
          // 操作人记录当前登陆系统用户名称
          record.setOperator(user.getName());
          if (!ObjectHelper.isEmpty(user.getRole())) {
              // 操作人角色
              record.setOperateRoleId(user.getRole().getId());
          }
          if (!ObjectHelper.isEmpty(user.getDepartment())) {
              // 机构编码
              record.setOrgCode(user.getDepartment().getId());
          }
          loanStatusHisDao.insertSelective(record);
		 
	        
	        
	        /////设置审核人 ///回掉方法 LoanFlowUpdateAuditingEx
			if (LoanFlowRoute.PAYCONFIRM.equals(response) || LoanFlowRoute.KING_OPEN.equals(response)) { // 放款确认、金帐户开户
				contract.setAuditingTime(now);
				contract.setAuditingBy(user.getLoginName());
				contractService.updateContractAuditing(contract);
			}
			
			/////////更新委托划扣标识 TrustFlagEx
			

		       String operType = busiView.getOperType();
		       String trustFlag = busiView.getTrustFlag();
		        //TODO 不知道为什么是 3
		        if("3".equals(operType)){
		            if(YESNO.NO.getCode().equals(trustFlag)){
		                loanMinuteDao.updateTrustCash(loanCode);
		            }else if(YESNO.YES.getCode().equals(trustFlag)){
		                loanMinuteDao.updateTrustRecharge(loanCode);
		            }
		        }
			
		        
		     ////////// 排序 createOrderFileIdEx
                if(LoanFlowRoute.PAYCONFIRM.equals(response)/* || LoanFlowRoute.KING_OPEN.equals(response)*/){
                  String backFlag1 = "00";
                    backFlag1 = contract.getBackFlag();
                    //判断是不是退回的
                    if(StringUtils.isEmpty(backFlag1)){
                  	  backFlag1 = "00";
                    }else{
                        backFlag1 = "0"+backFlag1;
                    }
                    String channelFlag=contract.getChannelFlag();
                    String frozenFlag = null;
                    if(ObjectHelper.isEmpty(loanInfo) || 
                            StringUtils.isEmpty(loanInfo.getFrozenCode()) ||
                                StringUtils.isEmpty(loanInfo.getFrozenCode().trim())){
                        frozenFlag = "00";
                    }else{
                        frozenFlag = "01";
                    }
                    String urgentFlag = loanInfo.getLoanUrgentFlag();
                    String code = status+"-0"+urgentFlag+"-"+frozenFlag+"-"+backFlag1;
                    if(ChannelFlag.ZCJ.getCode().equals(channelFlag)){
                    	code=code+"-05";
                    }
                    
                    OrderFiled filed = OrderFiled.parseByCode(code);
                    String orderField = filed.getOrderId(); 
                    Date modifyTime = new Date();
	                if(ObjectHelper.isEmpty(contract.getModifyTime())){
	                	orderField +="-"+DateUtils.formatDate(contract.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
	                } else {
	                	orderField +="-"+DateUtils.formatDate(modifyTime, "yyyy.MM.dd HH:mm:ss");
	                }
                   
                    // 更新数据库
                    Map<String,Object> loanParam2 = new HashMap<String,Object>();
                    loanParam2.put("loanCode", contract.getLoanCode());
                    loanParam2.put("orderField", orderField);
                    loanInfoDao.updOrderField(loanParam2);
                }  
		     /////////更新辅助表  金账户状态  更新退回原因  设置审核时间   审核人
			Assist assist = new Assist();
			assist.setLoan_code(loanCode);
			assist.setAuditreturnreason(backReason);
			assist.setAudittime(now);
			assist.setAuditusercode(userCode);
			assist.setKingstatus(kingStatus);
			assist.setTimeOutFlag(timeOutFlag); 
			assistService.updateLoanInfoAssist(assist);
		return busiView;
	}
	
	@Override
	public void updateFrozenStatus(ContractBusiView ctrView) {
		LoanInfo loanInfo = new LoanInfo();
		// loanInfo.setApplyId(contractBusiView.getApplyId());
		loanInfo.setLoanCode(ctrView.getLoanCode());
		loanInfo.setFrozenCode(ctrView.getFrozenCode());
		loanInfo.setDictLoanStatus(ctrView.getDictLoanStatusCode());
		loanInfo.setFrozenFlag(ctrView.getFrozenFlag());
		loanInfo.setFrozenReason(ctrView.getFrozenReason());
		loanInfo.setFrozenLastApplyTime(new Date());
		loanInfo.preUpdate();
		loanInfoDao.update(loanInfo);
		this.insertHistory(ctrView);
	}
	
	
//	 private Calendar getTimeOutPoint(Date date) {
//	        SystemSetting settingParam = new SystemSetting();
//	        settingParam.setSysFlag(SystemSetFlag.SYS_SIGN_TIME_OUT_FLAG);
//	        SystemSetting setting = systemSetMaterDao.get(settingParam);
//	        Calendar cal = Calendar.getInstance();
//	        cal.setTime(date);
//	        cal.set(Calendar.HOUR_OF_DAY, 23);
//	        cal.set(Calendar.MINUTE, 59);
//	        cal.set(Calendar.SECOND, 59);
//	        cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(setting.getSysValue()));
//	        return cal;
//	    }
	 
	 
	 
	// 插入历史记录
	    public void insertHistory(ContractBusiView contractBusiView){
	        String dictLoanStatus = null;
	        String operateStep = null;
	        String operFlag = contractBusiView.getChannelFlagAdd();
	        if(StringUtils.isEmpty(operFlag)){
	            dictLoanStatus = ContractConstant.REJECT_FROZEN;
	            operateStep = ContractConstant.REJECT_FROZEN;
	        }else if(YESNO.YES.getCode().equals(operFlag)){
	            String channelFlag = contractBusiView.getLoanFlag();
	            dictLoanStatus = ContractConstant.ADD_CHANNELFLAG.replace("${CHANNELFLAG}", channelFlag);
	            operateStep = ContractConstant.ADD_CHANNELFLAG.replace("${CHANNELFLAG}", channelFlag);
	        }else if(YESNO.NO.getCode().equals(operFlag)){
	            String oldChannelFlag = contractBusiView.getOldChannelFlag();
	            if(StringUtils.isEmpty(oldChannelFlag)){
	                oldChannelFlag = "";
	            }
	            dictLoanStatus = ContractConstant.CANCEL_CHANNELFLAG.replace("${CHANNELFLAG}", oldChannelFlag);
	            operateStep = ContractConstant.CANCEL_CHANNELFLAG.replace("${CHANNELFLAG}", oldChannelFlag);
	        }
	        User user = UserUtils.getUser();
	        LoanStatusHis record = new LoanStatusHis();
	        // APPLY_ID
	        record.setApplyId(contractBusiView.getApplyId());
	        // 借款Code
	        record.setLoanCode(contractBusiView.getLoanCode());
	        // 状态
	        record.setDictLoanStatus(dictLoanStatus);
	        // 操作步骤(回退,放弃,拒绝 等)
	        record.setOperateStep(operateStep);
	        // 操作结果
	        record.setOperateResult(ContractResult.CONTRACT_SUCCEED.getName());
	        // 备注
	        String remarks= contractBusiView.getRemarks();
	        if(StringUtils.isNotEmpty(remarks)){
	            if(remarks.trim().startsWith(",")||remarks.trim().endsWith(",")){
	                remarks = remarks.trim().replace(",", "");
	                
	            }
	            record.setRemark(remarks);
	        }
	       
	        // 系统标识
	        record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
	        // 设置Crud属性值
	        record.preInsert();
	        // 操作时间
	        record.setOperateTime(record.getCreateTime());
	        // 操作人记录当前登陆系统用户名称
	        record.setOperator(user.getName());
	        if(!ObjectHelper.isEmpty(user.getRole())){
	            // 操作人角色
	            record.setOperateRoleId(user.getRole().getId());
	        }
	        if(!ObjectHelper.isEmpty(user.getDepartment())){
	            // 机构编码
	            record.setOrgCode(user.getDepartment().getId());
	        }
	        loanStatusHisDao.insertSelective(record);
		
	    }
}
