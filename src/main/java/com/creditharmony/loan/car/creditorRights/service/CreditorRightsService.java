package com.creditharmony.loan.car.creditorRights.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditharmony.common.util.StringUtils;
import com.creditharmony.core.cache.DictCache;
import com.creditharmony.core.claim.dto.SyncClaim;
import com.creditharmony.core.common.util.PageUtil;
import com.creditharmony.core.config.Global;
import com.creditharmony.core.fortune.type.CreditSrc;
import com.creditharmony.core.loan.type.CarLoanStatus;
import com.creditharmony.core.loan.type.YESNO;
import com.creditharmony.core.mybatis.paginator.domain.PageBounds;
import com.creditharmony.core.mybatis.paginator.domain.PageList;
import com.creditharmony.core.persistence.Page;
import com.creditharmony.core.service.CoreManager;
import com.creditharmony.loan.car.carApply.ex.CoborrowerCreditJson;
import com.creditharmony.loan.car.common.dao.CarLoanInfoDao;
import com.creditharmony.loan.car.common.entity.CarLoanInfo;
import com.creditharmony.loan.car.creditorRights.dao.CreditorRightsDao;
import com.creditharmony.loan.car.creditorRights.entity.Coborrower;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRights;
import com.creditharmony.loan.car.creditorRights.entity.CreditorRightsImport;
import com.creditharmony.loan.car.creditorRights.view.CreditorLog;
import com.creditharmony.loan.car.creditorRights.view.CreditorRightView;
import com.google.common.collect.Maps;
	
/**
 * 
 * @Class Name CarAuditResultService
 * @author 陈伟东
 * @Create In 2016年3月5日
 */
@Service
@Transactional(readOnly = true, value = "loanTransactionManager")
public class CreditorRightsService extends CoreManager<CreditorRightsDao, CreditorRights> {
	
	@Autowired
	private CarLoanInfoDao carLoanInfoDao;
   
	/**
	 * 添加债权
	 * 2016年3月5日
	 * By 陈伟东
	 * @param entity
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insert(CreditorRights entity){
		entity.setRightsType(CreditorRights.RIGHTS_TYPE_INPUT);
		entity.preInsert();
		dao.insert(entity);
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void saveCreditorRights(CreditorRights entity,List<Map<String,Object>> paramList,CoborrowerCreditJson json){
		entity.setRightsType(CreditorRights.RIGHTS_TYPE_INPUT);
		entity.setLoanStatus(CarLoanStatus.REPAYMENT_IN.getCode());
		entity.setIssendWealth(Global.HIDE);
		String customerName = entity.getLoanCustomerName();
		String customerCert = entity.getCustomerCertNum();
		if(entity.getCustomerCobo()!=null&&Global.YES.equals(entity.getCustomerCobo())){
			List<Coborrower> coborrower = json.getCoborrower();
			if(null != coborrower && coborrower.size()>0){
				for(Coborrower cobo:coborrower){
					customerName+=";"+cobo.getLoanCustomerName();
					customerCert+=";"+cobo.getCustomerCertNum();
				}
			}
		}
		entity.setLoanCustomerName(customerName);
		entity.setCustomerCertNum(customerCert);
		if(StringUtils.isEmpty(entity.getId())){
			insert(entity);
			CreditorLog log = new CreditorLog();
			log.preInsert();
			log.setLoanCode(entity.getLoanCode());
			log.setOperMsg("新建债权信息");
			insertCreLog(log);
		}else{
			update(entity);
		}
	}
	
	/**
	 * 更新债权
	 * 2016年3月7日
	 * By 陈伟东
	 * @param entity
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void update(CreditorRights entity){
		entity.preUpdate();
		dao.update(entity);
	}
	
	/**
	 * 更新借款状态
	 * 2016年3月7日
	 * By 陈伟东
	 * @param creditorRights
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateLoanStatus(String id,String loanStatus){
		CreditorRights entity = new CreditorRights();
		if(CarLoanStatus.SETTLE_EARLY.getCode().equals(loanStatus)){
			entity.setSettledDate(new Date());
		}
		entity.setId(id);
		entity.setLoanStatus(loanStatus);
		entity.setCreditType(CreditorRights.CREDIT_TYPE_EARLYSETTLE);
		dao.updateLoanStatus(entity);
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateIssendWealth(Boolean flag,CreditorRights creditorRights){
		String issendWealth = YESNO.NO.getCode();
		if (flag) {
			issendWealth = YESNO.YES.getCode();
		}
		creditorRights.setIssendWealth(issendWealth);
		dao.updateIssendWealth(creditorRights);
		CreditorLog log = new CreditorLog();
		log.preInsert();
		log.setLoanCode(creditorRights.getLoanCode());
		log.setOperMsg("批量发送债权");
		insertCreLog(log);
	}
	
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertCreditorCoborrower(Coborrower coborrower){
		dao.insertCreditorCoborrower(coborrower);
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public CreditorRightView getCreditRight(String id){
		CreditorRightView creditRight = dao.getCreditRight(id);
		return creditRight;
	}

	/** 
	 * 债权信息-编辑
	 * 获取共同借款人数据
	 * @param creditorRightId
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<SyncClaim> getCoborrowerData(String creditorRightId){
		return dao.getCoborrowerData(creditorRightId);
	}
	
	/**
	 * 查询要推送到财富的数据对象   债权列表用
	 * 2016年3月8日
	 * By 张振强
	 * @param loanCode 参数
	 * @return 要同步的对象
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<SyncClaim> querySendFortune(CreditorRightView creditorRightView){
		return dao.querySendFortune(creditorRightView);
	}
	
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public List<SyncClaim> querySendFortuneByLoanCode(List<String> loanCode){
		return dao.querySendFortuneByLoanCode(loanCode);
	}
	
	/**
	 * 获取债权列表
	 * 2016年3月5日
	 * By 陈伟东
	 * @param page
	 * @param view
	 * @return
	 */
	@Transactional(readOnly = true, value = "loanTransactionManager")
	public Page<CreditorRightView> list(Page<CreditorRightView> page,CreditorRightView view){
		view.setRightsType(CreditorRights.RIGHTS_TYPE_INPUT);
		PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
		List<CreditorRightView> list = dao.getCreditorRights(view, pageBounds);
		for (CreditorRightView creditorRightView : list) {
			creditorRightView.setCreditorRigthSource(CreditSrc.getCreditSrc(CreditSrc.CJ.value));
			creditorRightView.setStatus(DictCache.getInstance().getDictLabel("yes_no", creditorRightView.getStatus()));
			creditorRightView.setUsageOfLoan(DictCache.getInstance().getDictLabel("jk_loan_use", creditorRightView.getUsageOfLoan()));
			creditorRightView.setJob(DictCache.getInstance().getDictLabel("car_occupation_case", creditorRightView.getJob()));
			creditorRightView.setLoanStatus(DictCache.getInstance().getDictLabel("jk_car_loan_status", creditorRightView.getLoanStatus()));
			creditorRightView.setContractReplayWay(DictCache.getInstance().getDictLabel("jk_car_repay_interest_way", creditorRightView.getContractReplayWay()));
			creditorRightView.setChannelType(DictCache.getInstance().getDictLabel("jk_car_throuth_flag", creditorRightView.getChannelType()));
		}
		PageUtil.convertPage( (PageList<CreditorRightView>)list, page);
		return page;
	}


	/** 
	 * 获取合同表借款编码
	 * @param contractCode
	 * @return
	 */
	public String getCjContractLoanCode(String contractCode){
		return dao.getCjContractLoanCode(contractCode);
	}
	
	/** 
	 *  添加债权(导入Excel)  
	 * @param creditorRightsImport
	 */
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void insertBatch(List<CreditorRightsImport> list){
		Map<String,List<CreditorRightsImport>> map = Maps.newHashMap();
		map.put("list", list);
		for(CreditorRightsImport c:list){
			c.setIssendWealth(Global.HIDE);
			dao.insertCreditorRightsImport(c);
			CreditorLog log = new CreditorLog();
			log.preInsert();
			log.setLoanCode(c.getLoanCode());
			log.setOperMsg("债权信息导入");
			insertCreLog(log);
		}
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void updateCarCreditorForConfirm(Map<String,Object> map){
		dao.updateCarCreditorForConfirm(map);
	}
	
	@Transactional(readOnly = false, value = "loanTransactionManager")
	public void settleCarCreditor(String id,String loanCode,String loanStatus){
		updateLoanStatus(id, loanStatus);
		CarLoanInfo carLoanInfo = new CarLoanInfo();
		carLoanInfo.setLoanCode(loanCode);
		carLoanInfo.setDictLoanStatus(loanStatus);
		carLoanInfoDao.update(carLoanInfo);
		String settleStatus = "债权信息列表-提前结清";
		if(loanStatus.equals(CarLoanStatus.SETTLE.getCode())){
			settleStatus  = "债权信息列表-结清";
		}
		CreditorLog log = new CreditorLog();
		log.preInsert();
		log.setLoanCode(carLoanInfo.getLoanCode());
		log.setOperMsg(settleStatus);
		insertCreLog(log);
	}

	/**
	 * 插入债权历史数据
	 * @param log
	 */
	public void insertCreLog(CreditorLog log){
		dao.insertCreLog(log);
	}

	/**
	 * 根据债权历史ID查询数据
	 * @param log
	 */
	public Page<CreditorLog> getCreLog(Page<CreditorLog> page,String loanCode){
			PageBounds pageBounds = new PageBounds(page.getPageNo(),page.getPageSize());
			List<CreditorLog> list = dao.getCreLog(loanCode, pageBounds);
			PageUtil.convertPage( (PageList<CreditorLog>)list, page);
			return page;
	}
}
