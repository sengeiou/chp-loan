/**
 * @Probject Name: chp-loan
 * @Path: com.creditharmony.loan.common.service.ContractCommonService.java
 * @Create By 王彬彬
 * @Create In 2015年12月21日 上午11:56:36
 */
package com.creditharmony.loan.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.taskdefs.Sleep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.bpm.frame.view.WorkItemView;
import com.creditharmony.common.util.DateUtils;
import com.creditharmony.common.util.Global;
import com.creditharmony.common.util.ObjectHelper;
import com.creditharmony.common.util.StringHelper;
import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.common.type.SystemConfigConstant;
import com.creditharmony.core.common.type.UseableType;
import com.creditharmony.core.loan.type.ChannelFlag;
import com.creditharmony.core.loan.type.ContractVer;
import com.creditharmony.core.loan.type.LoanApplyStatus;
import com.creditharmony.core.loan.type.SerialNoType;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.core.type.ModuleName;
import com.creditharmony.loan.borrow.applyinfo.dao.ApplyLoanInfoDao;
import com.creditharmony.loan.borrow.applyinfo.entity.LoanInfo;
import com.creditharmony.loan.borrow.contract.dao.ContractDao;
import com.creditharmony.loan.borrow.contract.dao.ContractFeeDao;
import com.creditharmony.loan.borrow.contract.dao.ContractTempDao;
import com.creditharmony.loan.borrow.contract.dao.CustInfoDao;
import com.creditharmony.loan.borrow.contract.dao.SplitDao;
import com.creditharmony.loan.borrow.contract.entity.Contract;
import com.creditharmony.loan.borrow.contract.entity.ContractTemp;
import com.creditharmony.loan.borrow.contract.entity.Split;
import com.creditharmony.loan.borrow.contract.entity.ex.ContractConstant;
import com.creditharmony.loan.borrow.contract.entity.ex.CustInfo;
import com.creditharmony.loan.borrow.contract.service.ContractTempService;
import com.creditharmony.loan.borrow.contract.util.ProductUtil;
import com.creditharmony.loan.borrow.contract.view.ContractBusiView;
import com.creditharmony.loan.borrow.reconsider.dao.ReconsiderApplyDao;
import com.creditharmony.loan.borrow.reconsider.entity.ReconsiderApply;
import com.creditharmony.loan.channel.goldcredit.dao.GCCeilingDao;
import com.creditharmony.loan.common.consts.NumberManager;
import com.creditharmony.loan.common.dao.LoanNosDao;
import com.creditharmony.loan.common.dao.LoanStatusHisDao;
import com.creditharmony.loan.common.dao.NumberMasterDao;
import com.creditharmony.loan.common.entity.CoeffRefer;
import com.creditharmony.loan.common.entity.LoanNos;
import com.creditharmony.loan.common.entity.LoanStatusHis;
import com.creditharmony.loan.common.entity.NumberMaster;
import com.creditharmony.loan.common.entity.OrderFiled;
import com.creditharmony.loan.common.entity.OrgGl;
import com.creditharmony.loan.common.utils.IdentifierRule;
import com.creditharmony.loan.common.utils.LoanConsultDateUtils;
import com.creditharmony.loan.common.utils.ReflectHandle;
import com.creditharmony.loan.common.view.LoanWebServiceView;

/**
 * 合同信息新增
 * 
 * @Class Name ContractCommonService
 * @author 张灏
 * @Create In 2015年12月22日
 */
@Service
public class ContractCommonService extends CoreManager<ContractDao, Contract> {

	@Autowired
	private ContractDao contractDao;

	@Autowired
	private CustInfoDao custInfoDao;

	@Autowired
	private ContractFeeDao contractFeeDao;

	@Autowired
	private ReconsiderApplyDao reconsiderApplyDao;

	@Autowired
	private ApplyLoanInfoDao loanInfoDao;

	@Autowired
	private NumberMasterDao numberMasterDao;

	@Autowired
	private GCCeilingDao dao;

	@Autowired
	private OrgGlService orgService;

	@Autowired
	private LoanNosDao loanNosDao;
	
	@Autowired
	private ContractTempService contractTempService;
	
	@Autowired
	private ContractTempDao contractTempDao;
	
	@Autowired
	private SplitDao splitDao;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private LoanStatusHisDao loanStatusHisDao;
	/**
	 * 初始化方法 2016年3月2日 By 王浩
	 * 
	 * @param applyId
	 * @param loanCode
	 * @return LoanWebServiceView 数据封装类
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public LoanWebServiceView loanInit(String applyId, String loanCode,String storeOrgId) throws Exception {
		Thread.sleep(30000);
		LoanWebServiceView loanWebServiceView = new LoanWebServiceView();
        LoanInfo loanInfo  = loanInfoDao.getByLoanCode(loanCode);
        log.info("loaninfo.loanflag++++++++"+loanInfo.getLoanFlag());
        String loanStoreOrgId = loanInfo.getLoanStoreOrgId();
		log.info("生成合同编号开始，applyId：" + applyId);
		Contract contract = insertContract(applyId,loanStoreOrgId,loanInfo);
		String contractCode = contract.getContractCode();
		loanWebServiceView.setContractCode(contractCode);
		log.info("生成合同编号结束，合同编号：" + contractCode);

		log.info("添加无纸化标识开始(工作流属性用)，合同编号：" + contractCode);
		String paperLess = contract.getPaperLessFlag();
		loanWebServiceView.setPaperLessFlag(paperLess);
		log.info("添加无纸化标识开始结束(工作流属性用)，合同编号：" + contractCode);
		String backFlag = null;
		if(StringUtils.isEmpty(contract.getBackFlag())){
		    backFlag  = "00";
		}else{
		    backFlag = "0"+contract.getBackFlag();
		}
		 String againflag = "00";  //复议标识
		 if("HJ0002".equals(applyId.substring(0, 6))  && "1".equals(loanInfo.getNodeFlag())){
			 againflag = "01";
		 }
		 String urgentFlag = loanInfo.getLoanUrgentFlag();
		 String firstFlag = "00";  // 门店第一单标识
         String code = LoanApplyStatus.RATE_TO_VERIFY.getCode()+"-0"+urgentFlag+"-"+backFlag+"-"+firstFlag+"-"+againflag;
         if(contractCode.endsWith("00001")){
        	 firstFlag = "01";
        	 code = LoanApplyStatus.RATE_TO_VERIFY.getCode()+"-"+firstFlag;
         }
         OrderFiled filed = OrderFiled.parseByCode(code);
          String orderField = filed.getOrderId(); 
             orderField +="-"+DateUtils.formatDate(loanInfo.getModifyTime(), "yyyy.MM.dd HH:mm:ss");
		// 排序字段
		loanWebServiceView.setOrderField(orderField);

		// 合同版本号
		loanWebServiceView.setContractVersion(contract.getContractVersion());
		 LoanInfo lif  = loanInfoDao.getByLoanCode(loanCode);
		if(contract.getAuditAmount().compareTo(BigDecimal.valueOf(200000))==1){
			if(lif.getIssplit().equals("1")){
				loanWebServiceView.setChannelCode(ChannelFlag.LIANHE.getCode());
				loanWebServiceView.setChannelName(ChannelFlag.LIANHE.getName());
			}
		}else{
			if(loanInfo.getLoanFlag().equals(ChannelFlag.LIANHE.getCode())){
				loanWebServiceView.setChannelCode(ChannelFlag.JINXIN.getCode());
				loanWebServiceView.setChannelName(ChannelFlag.JINXIN.getName());
			}
		}
		return loanWebServiceView;
	}

	/**
	 * 合同信息新增 /修改 2016年3月2日 王彬彬
	 * 
	 * @param applyId
	 *            流程申请号
	 * @return String 合同编号
	 */
	private Contract insertContract(String applyId,String storeOrgId,LoanInfo loaninfo) {
		logger.info("合同信息新增 /修改 Start");
		Contract c=contractDao.findByLoanCode(loaninfo.getLoanCode());
		String contractVer=null;
		if(c!=null){
			contractVer=c.getContractVersion();
		}
		//批借金额
		BigDecimal auditAmount=BigDecimal.valueOf(0);
		 if("HJ0002".equals(applyId.substring(0, 6))  && "1".equals(loaninfo.getNodeFlag()) && c!=null){
			 if(c.getAuditAmount()!=null){
				 auditAmount=c.getAuditAmount();
			 }
		 }
		CustInfo custInfo = null;
		custInfo = custInfoDao.findCustInfo(applyId);// 查询批复信息
		if(ObjectHelper.isEmpty(custInfo)){
		    Map<String,Object> queryCust = new HashMap<String,Object>();
		    queryCust.put("applyId", applyId);
		    custInfo = custInfoDao.findReconsiderCustInfo(queryCust);// 查询批复信息 
		}
		CustInfo auditInfo = custInfoDao.findAuditInfo(applyId); // 查询批复信息
		String paperLess = getPaperLessFlag(storeOrgId);// 无纸化标识

		// 生成合同编号
		log.info("生成合同编号处理开始");
		Contract contract = getContractCode(applyId);
		log.info("生成合同编号处理结束");

		contract.setApplyId(applyId);
		contract.setAuditAmount(auditInfo.getAuditAmount());
		contract.setContractMonths(auditInfo.getContractMonths());
		contract.setProductType(auditInfo.getProductType());
		if(contract.getAuditAmount().compareTo(BigDecimal.valueOf(200000))!=1 && loaninfo.getLoanFlag().equals(ChannelFlag.LIANHE.getCode())){
			contract.setChannelFlag(ChannelFlag.JINXIN.getCode());
		}else{
			contract.setChannelFlag(custInfo.getLoanFlag());
		}       
        contract.setModel(custInfo.getModel());
		// 合同信息增加无纸化标识
		if(StringHelper.isNotEmpty(paperLess))
		{
			contract.setPaperLessFlag(paperLess);
		}
		else
		{
			contract.setPaperLessFlag(YESNO.YES.getCode());
		}
		Date consultDate = LoanConsultDateUtils.findTimeByLoanCode(custInfo.getLoanCode());
		Date onLineDate = DateUtils.convertStringToDate(SystemConfigConstant.LOAN_ONLINE_DATE);
		String zcjversion = Global.getConfig("zcjversion");
		String contractVersion = Global.getConfig("contractVersion");
		String jxcontractVersion = Global.getConfig("jxcontractVersion");
		
		if(ProductUtil.PRODUCT_NXD.getCode().equals(loaninfo.getProductType())){
			contract.setContractVersion(ContractVer.VER_ONE_ZERO_NXD.getCode());// 合同版本号（农信贷）
		}else{
			if(custInfo.getLoanFlag().equals(ChannelFlag.ZCJ.getCode())){
				if(contractVer!=null){
					contract.setContractVersion(contractVer);// 合同版本号（资产家）
				}else{
					contract.setContractVersion(ContractVer.VER_ONE_TWO_ZCJ.getCode());// 合同版本号（资产家）
				}			
			}else if(custInfo.getLoanFlag().equals(ChannelFlag.JINXIN.getCode())){
//			if(loaninfo.getLoanCustomerSource()!=null && loaninfo.getLoanCustomerSource().equals("1")){
//				contract.setContractVersion(ContractVer.VER_ONE_EIGHT.getCode());// 合同版本号（金信）-买金网
//			}else{
				contract.setContractVersion(jxcontractVersion);// 合同版本号（金信）
//			}			
			}else if(custInfo.getLoanFlag().equals(ChannelFlag.LIANHE.getCode())){
				contract.setContractVersion(jxcontractVersion);// 合同版本号-联合
			}else{
				contract.setContractVersion(contractVersion);// 合同版本号（暂时固定）
			}
		}
		contract.preInsert();
		contractDao.insertSelective(contract);
		//更新loanInfo表拆分属性
		LoanInfo loaninfo2=new LoanInfo();
		loaninfo2.setLoanCode(contract.getLoanCode());
		loaninfo2.setIssplit(ContractConstant.ISSPLIT_0);
		if(contract.getAuditAmount().compareTo(BigDecimal.valueOf(200000))!=1 && loaninfo.getLoanFlag().equals(ChannelFlag.LIANHE.getCode())){
			loaninfo2.setLoanFlag(ChannelFlag.JINXIN.getCode());
		}      
		loanInfoDao.updateLoanInfo(loaninfo2);
		logger.info("合同信息新增 /修改 End");
		if(contract.getAuditAmount().compareTo(BigDecimal.valueOf(200000))==1){
			Split split=splitDao.findBySplit();
			if(split!=null){
				BigDecimal zcj=split.getZcj();
				BigDecimal jinxin=split.getJinxin();
				logger.info("合同拆分 Start");
				 if("HJ0002".equals(applyId.substring(0, 6))  && "1".equals(loaninfo.getNodeFlag())){
					 if(loaninfo.getFlFlag().equals(ContractConstant.FLFLAG_1) && loaninfo.getZcj()!=null && loaninfo.getJinxin()!=null && auditAmount.equals(contract.getAuditAmount())){
							zcj=loaninfo.getZcj();
							jinxin=loaninfo.getJinxin();
					 }
				 }else{
					 if(loaninfo.getFlFlag().equals(ContractConstant.FLFLAG_1) && loaninfo.getZcj()!=null && loaninfo.getJinxin()!=null){
							zcj=loaninfo.getZcj();
							jinxin=loaninfo.getJinxin();
						}
				 }
				 if(!zcj.equals(BigDecimal.valueOf(Double.valueOf(100))) && !jinxin.equals(BigDecimal.valueOf(Double.valueOf(100)))){
					contractTempDao.deleteByContractCode(contract.getContractCode()+"-1");
					contractTempDao.deleteByContractCode(contract.getContractCode()+"-2");
					ContractTemp contractTemp=new ContractTemp();
					//大金融
					ReflectHandle.copy(contract, contractTemp);				
					contractTemp.setContractCode(contractTemp.getContractCode()+"-1");
					contractTemp.setChannelFlag(ChannelFlag.ZCJ.getCode());
					contractTemp.setContractVersion(ContractVer.VER_ONE_TWO_ZCJ.getCode());
					
					contractTemp.setAuditAmount(contractTemp.getAuditAmount().multiply(zcj.divide(BigDecimal.valueOf(100))));
					contractTemp.preInsert();
					contractTempDao.insertSelective(contractTemp);
					//金信
					ReflectHandle.copy(contract, contractTemp);	
					contractTemp.setContractCode(contractTemp.getContractCode()+"-2");
					contractTemp.setChannelFlag(ChannelFlag.JINXIN.getCode());
//					if(loaninfo.getLoanCustomerSource()!=null && loaninfo.getLoanCustomerSource().equals("1")){
//						contractTemp.setContractVersion(ContractVer.VER_ONE_EIGHT.getCode());
//					}else{
						contractTemp.setContractVersion(jxcontractVersion);
//					}					
					contractTemp.setAuditAmount(contractTemp.getAuditAmount().multiply(jinxin.divide(BigDecimal.valueOf(100))));
					contractTemp.preInsert();
					contractTempDao.insertSelective(contractTemp);
					//更新loanInfo表拆分属性
					LoanInfo loaninfo1=new LoanInfo();
					loaninfo1.setLoanCode(contract.getLoanCode());
					loaninfo1.setIssplit(ContractConstant.ISSPLIT_1);
					loaninfo1.setZcj(zcj);
					loaninfo1.setJinxin(jinxin);
					loaninfo1.setLoanFlag(ChannelFlag.LIANHE.getCode());
					loanInfoDao.updateLoanInfo(loaninfo1);
					
					// 插入日志
					LoanStatusHis record = new LoanStatusHis();
					record.setApplyId(contract.getApplyId());
					record.setLoanCode(contract.getLoanCode());
					// 状态
					record.setDictLoanStatus(LoanApplyStatus.RATE_TO_VERIFY.getCode());
					// 操作步骤(回退,放弃,拒绝 等)
					record.setOperateStep("占比分配");
					record.setOperateResult("成功");
					// 备注
					record.setRemark("拆分占比分配（大金融：" + zcj + "%,金信：" + jinxin + "%）");
					// 系统标识
					record.setDictSysFlag(ModuleName.MODULE_LOAN.value);
					// 设置Crud属性值
					record.preInsert();
					// 操作时间
					record.setOperateTime(record.getCreateTime());
		//			// 操作人记录当前登陆系统用户名称
					record.setOperator("系统");
					loanStatusHisDao.insertSelective(record);
					logger.info("合同拆分 end");
				 }
			}
		}

		return contract;
	}

	/**
	 * 合同编号使用规则判定 2016年1月11日 By 王彬彬
	 * 
	 * @param applyId
	 * @return Contract
	 */
	private Contract getContractCode(String applyId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applyId", applyId);

		List<ReconsiderApply> reconsiderApplys = reconsiderApplyDao
				.findReconsiderApply(param);

		String oldContractNo = null;
		String loanCode = null;
		SerialNoType serialNoType = null;

		LoanInfo loanInfo = null;
		Contract contract = null;
		String contractCode = null;
		String backFlag = null;
		Integer auditCount = null;
		String contractBackResult = null;
		Contract newContract = new Contract();
		// 复议编号生成
		if (!ObjectHelper.isEmpty(reconsiderApplys)) {
			loanCode = reconsiderApplys.get(0).getLoanCode();
			contract = contractDao.findByLoanCode(loanCode);
            
			serialNoType = SerialNoType.RECONSIDE;

			if (!ObjectHelper.isEmpty(contract)) {
				oldContractNo = contract.getContractCode();
				backFlag = contract.getBackFlag();
				auditCount = contract.getAuditCount();
				contractBackResult = contract.getContractBackResult();
				contractFeeDao.deleteByContractCode(oldContractNo);
				contractDao.deleteByLoanCode(loanCode);
			}

			param.put("loanCode", loanCode);
			loanInfo = loanInfoDao.selectByLoanCode(param);
		} else {
			loanInfo = loanInfoDao.selectByApplyId(param);
			loanCode = loanInfo.getLoanCode();
			contract = contractDao.findByLoanCode(loanCode);

			serialNoType = SerialNoType.CONTRACT;

			if (!ObjectHelper.isEmpty(contract)) {
				contractCode = contract.getContractCode();
				backFlag = contract.getBackFlag();
				auditCount = contract.getAuditCount();
                contractBackResult = contract.getContractBackResult();
				contractFeeDao.deleteByContractCode(contractCode);
				contractDao.deleteByLoanCode(loanCode);
			}
		}

		// 生成新的合同编号
		if (StringUtils.isEmpty((contractCode))) {
			contractCode = getContractNumber(loanInfo, serialNoType,
					oldContractNo);
		}

		if (ObjectHelper.isEmpty(contract)) {
			contract = new Contract();
		}
		newContract.setAuditCount(auditCount);
		newContract.setContractBackResult(contractBackResult);
		newContract.setBackFlag(backFlag);
		newContract.setContractCode(contractCode);
		newContract.setLoanCode(loanCode);
		newContract.setAuditTime(loanInfo.getAuditTime());//汇诚批复时间
		return newContract;
	}

	/**
	 * 合同编号生成 2016年1月11日 By 王彬彬
	 * 
	 * @param loaninfo
	 *            借款信息
	 * @param serialNoType
	 *            编号生成类型
	 * @param oldContractNo
	 *            旧合同编号
	 * @return String
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public String getContractNumber(LoanInfo loaninfo,
			SerialNoType serialNoType, String oldContractNo) {
		LoanNos loanNos = new LoanNos();

		loanNos.setNoType(NumberManager.CONTRACT_NO_TYPE);
		loanNos.setNoKeys(loaninfo.getStoreCode());

		int count = getContractNos(loanNos);

		if (SerialNoType.CONTRACT.equals(serialNoType)
				|| SerialNoType.RECONSIDE.equals(serialNoType)
				|| SerialNoType.CHANGE.equals(serialNoType)
				|| SerialNoType.RAISE.equals(serialNoType)) {
			return IdentifierRule.getFullContract(loaninfo, count,
					serialNoType, oldContractNo);
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 获取合同编号 2016年4月19日 By 王彬彬
	 * 
	 * @param loanNos
	 * @return 合同编号
	 */
	private int getContractNos(LoanNos loanNos) {
		LoanNos loanNosData = loanNosDao.get(loanNos);
		int count = 0;

		if (loanNosData != null) {
			count = loanNosData.getNoCount() + NumberManager.STEP;
			loanNos.setNoCount(count);
			loanNosDao.update(loanNos);
		} else {
			loanNos.setNoCount(NumberManager.START);
			loanNosDao.insert(loanNos);
			count = NumberManager.START;
		}
		return count;
	}

	/**
	 * 取得编号规则信息 2015年12月29日 By 王彬彬
	 * 
	 * @param numbaerMaster
	 * @return int
	 */
	@Deprecated
	public int getNumberMaster(NumberMaster numbaerMaster) {
		NumberMaster numberData = numberMasterDao.get(numbaerMaster);
		int count = 0;

		if (numberData != null) {
			count = numberData.getSerialNo() + NumberManager.STEP;
			numberData.setSerialNo(count);
			numberMasterDao.update(numberData);
		} else {
			numbaerMaster.preInsert();
			numbaerMaster.setCreateBy("workflow");
			numbaerMaster.setModifyBy("workflow");
			numbaerMaster.setSerialNo(NumberManager.START);
			numberMasterDao.insert(numbaerMaster);
		}
		return count;
	}

	/**
	 * 获取无纸化标识 2016年4月16日 By 王彬彬
	 * 
	 * @param storeOrgId
	 *            门店ID
	 * @return 无纸化标识（信借）
	 */
	private String getPaperLessFlag(String storeOrgId) {
		OrgGl org = orgService.getOrgByOrgid(storeOrgId);
		if (ObjectHelper.isEmpty(org)) {
			return UseableType.YES;
		}
		return org.getCreditPaperless();
	}
}
